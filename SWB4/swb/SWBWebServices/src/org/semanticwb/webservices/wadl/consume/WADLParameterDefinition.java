/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.webservices.wadl.consume;

import java.util.ArrayList;
import java.util.HashSet;
import org.semanticwb.webservices.ParameterDefinition;
import org.semanticwb.webservices.ServiceException;
import org.semanticwb.webservices.ServiceInfo;
import org.semanticwb.webservices.util.SchemaClass;
import org.semanticwb.webservices.util.XMLDocumentUtil;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public class WADLParameterDefinition implements ParameterDefinition
{

    private final String name;
    private final String type;
    private final String classType;
    private final boolean isBasic;
    private final boolean isRequired;
    private final boolean isMultiple;
    private final Element definition;
    private final Element reference;
    private final ArrayList<Element> representations = new ArrayList<Element>();
    private final HashSet<WADLParameterDefinition> properties = new HashSet<WADLParameterDefinition>();
    private final ServiceInfo service;
    private SchemaClass schemaToClazz;
    public WADLParameterDefinition(Element definition, Element reference,ServiceInfo service) throws ServiceException
    {
        this.service=service;
        this.isMultiple = false;
        this.reference = reference;
        classType = definition.getAttribute("name");
        
        name = definition.getAttribute("name");
        String ns = XMLDocumentUtil.getTargetNamespace(definition.getOwnerDocument());
        type = XMLDocumentUtil.getPrefix(reference.getOwnerDocument(), ns) + ":" + definition.getAttribute("name");
        isBasic = false;
        this.isRequired = true;
        this.definition = definition;
        fill(definition);
        
    }

    public WADLParameterDefinition(Element eparam, String type, String name, boolean isRequired, String classType,ServiceInfo service)
    {
        this.service=service;
        this.isMultiple = false;
        this.reference = eparam;
        this.definition = eparam;
        this.classType = classType;
        this.name = name;
        this.type = type;
        isBasic = true;
        this.isRequired = isRequired;
    }

    private void fillProperties(Element element, boolean isRequired, boolean isMultiple) throws ServiceException
    {
    }

    private void fillProperties(Element element) throws ServiceException
    {
        String _name = element.getLocalName();
        if (_name.equals("complexType") || _name.equals("simpleType") || _name.equals("sequence") || _name.equals("simpleContent") || _name.equals("extension"))
        {
            NodeList childs = element.getChildNodes();
            for (int i = 0; i < childs.getLength(); i++)
            {
                if (childs.item(i) instanceof Element)
                {
                    Element childElement = (Element) childs.item(i);
                    fillProperties(childElement);
                }
            }
        }
        else if (_name.equals("restriction"))
        {
            boolean isEnumeration = false;
            NodeList childs = element.getChildNodes();
            for (int i = 0; i < childs.getLength(); i++)
            {
                if (childs.item(i) instanceof Element)
                {
                    Element childElement = (Element) childs.item(i);
                    if (childElement.getLocalName().equals("enumeration"))
                    {
                        isEnumeration = true;
                    }
                }
            }
        }
        else if (_name.equals("attribute"))
        {            
            
            boolean _isRequired = false;
            if ("required".equals(element.getAttribute("use")))
            {
                _isRequired = true;
            }
            boolean _isMultiple = false;            
            String _type=element.getAttribute("type");
            String _nameAttribute=element.getAttribute("name");
            String _classType = XMLDocumentUtil.getClassTypeFromSchemaName(_type).getCanonicalName();
            WADLParameterDefinition parameter = new WADLParameterDefinition(element, _type, _nameAttribute, _isRequired, _classType,service);
            this.properties.add(parameter);            
        }
        else if (_name.equals("element"))
        {
            String minOccurs = element.getAttribute("minOccurs");
            String maxOccurs = element.getAttribute("maxOccurs");
            boolean _isRequired = false;
            if ("1".equals(minOccurs))
            {
                _isRequired = true;
            }
            boolean _isMultiple = false;
            if ("unbounded".equals(maxOccurs))
            {
                _isMultiple = true;
            }
            if (XMLDocumentUtil.isRef(element)) // es una referencia
            {
                String ref = element.getAttribute("ref");                
                element = XMLDocumentUtil.getGlobalElement(ref, service);
                if (element == null)
                {
                    throw new ServiceException("The element " + ref + " was not found");
                }
                fillProperties(element, _isRequired, _isMultiple);

            }
            else if (!element.getAttribute("type").equals("")) // es de un tipo
            {
                String _type = element.getAttribute("type");                
                String _nameElement = element.getAttribute("name");
                if (XMLDocumentUtil.isBasic(_type, XMLDocumentUtil.toJdom(reference.getOwnerDocument())))
                {
                    String _classType = XMLDocumentUtil.getClassTypeFromSchemaName(_type).getCanonicalName();
                    WADLParameterDefinition parameter = new WADLParameterDefinition(element, _type, _nameElement, _isRequired, _classType,service);
                    this.properties.add(parameter);                    
                }
                else
                {
                    
                    Element elementToCode = XMLDocumentUtil.getElementByTypeFromSchema(_type, element.getOwnerDocument());
                    fill(elementToCode);
                }
            }
            else // continua la definición debajo del elemento
            {
                NodeList childs = element.getChildNodes();
                for (int i = 0; i < childs.getLength(); i++)
                {
                    if (childs.item(i) instanceof Element)
                    {
                        Element childElement = (Element) childs.item(i);
                        fillProperties(childElement);
                    }
                }
            }



        }
    }

    private void fill(Element element) throws ServiceException
    {
        NodeList childs = element.getChildNodes();
        for (int i = 0; i < childs.getLength(); i++)
        {
            if (childs.item(i) instanceof Element)
            {
                Element childElement = (Element) childs.item(i);
                fillProperties(childElement);
            }
        }
    }

    public void addRepresentations(Element representation)
    {
        this.representations.add(representation);
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public boolean isBasic()
    {
        return isBasic;
    }

    @Override
    public boolean isMultiple()
    {
        return isMultiple;

    }

    @Override
    public String getDefinitionType()
    {
        return type;
    }

    @Override
    public boolean isRequired()
    {
        return isRequired;
    }

    @Override
    public String getClassType()
    {
        return classType;
    }

    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public ParameterDefinition[] getProperties()
    {
        return this.properties.toArray(new ParameterDefinition[properties.size()]);
    }

    @Override
    public SchemaClass getDefinitionClass() throws ServiceException
    {
        if(schemaToClazz==null)
            schemaToClazz=new SchemaClass(this, service);
        return schemaToClazz;
    }
}
