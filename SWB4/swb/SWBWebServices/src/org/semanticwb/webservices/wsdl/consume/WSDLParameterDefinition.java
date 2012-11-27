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
package org.semanticwb.webservices.wsdl.consume;

import java.util.HashSet;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.webservices.ParameterDefinition;
import org.semanticwb.webservices.ServiceException;
import org.semanticwb.webservices.ServiceInfo;
import org.semanticwb.webservices.util.SchemaClass;
import org.semanticwb.webservices.util.XMLDocumentUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 *
 * @author victor.lorenzana
 */
public class WSDLParameterDefinition implements ParameterDefinition
{

    private SchemaClass schemaToClazz;
    private final String name;
    private final String type;
    private final String namespace;
    private final String classType;
    private final Element definition;
    private org.jdom.Document jDomDefinition;
    private final boolean isBasic;
    private final boolean isRequired;
    private final boolean isMultiple;
    private final Element part;
    private final ServiceInfo service;
    private final HashSet<WSDLParameterDefinition> properties = new HashSet<WSDLParameterDefinition>();

    public WSDLParameterDefinition(String namespace, Element definition, String type, String name, boolean isRequired, ServiceInfo service)
    {
        this.namespace = namespace;
        this.service = service;
        this.name = name;
        this.type = type;
        isBasic = true;
        part = null;
        this.definition = definition;
        this.isRequired = isRequired;
        this.classType = XMLDocumentUtil.getClassTypeFromSchemaName(type).getCanonicalName();;
        this.isMultiple = false;
    }

    public WSDLParameterDefinition(Element part, ServiceInfo service) throws ServiceException
    {
        this.service = service;
        this.part = part;
        this.isMultiple = false;

        String _name = "";
        if (part.getAttribute("name") != null)
        {
            _name = part.getAttribute("name");
        }
        this.name = _name;
        String _classType = null;
        String _type = null;
        Element _definition = null;
        boolean _isBasic = false;
        boolean _isRequired = false;
        if (!part.getAttribute("type").equals(""))
        {
            _type = part.getAttribute("type");
            _isBasic = XMLDocumentUtil.isBasic(_type, service.getJDom());
            if (!_isBasic)
            {
                _definition = XMLDocumentUtil.getElementByType(_type, service, part);
                fillProperties(part);
            }

        }
        else if (!part.getAttribute("element").equals(""))
        {
            _type = part.getAttribute("element");
            _isBasic = XMLDocumentUtil.isBasic(_type, service.getJDom());
            if (!_isBasic)
            {
                _definition = XMLDocumentUtil.getGlobalElement(_type, service);
            }
        }
        this.isRequired = _isRequired;
        this.classType = _classType;
        this.isBasic = _isBasic;
        this.type = _type;
        this.definition = _definition;
        if (!isBasic && definition == null)
        {
            throw new ServiceException("The element definition " + _type + " was not found");
        }
        String _namespace = null;
        if (definition != null)
        {
            jDomDefinition=XMLDocumentUtil.toJdom(definition.getOwnerDocument());
            _namespace = XMLDocumentUtil.getTargetNamespace(definition.getOwnerDocument());
            fillProperties(_definition);

        }
        this.namespace = _namespace;
    }

    @Override
    public boolean isBasic()
    {
        return isBasic;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public boolean isMultiple()
    {
        return isMultiple;
    }

    @Override
    public String getDefinitionType()
    {
        return this.type;
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
    public ParameterDefinition[] getProperties()
    {
        return this.properties.toArray(new ParameterDefinition[properties.size()]);
    }

    private void fillProperties(Element element, boolean isRequired, boolean isMultiple, String name) throws ServiceException
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
                    fillProperties(childElement, isRequired, isMultiple, name);
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

            String base = element.getAttribute("base");
            if (XMLDocumentUtil.isBasic(base, service.getJDom()))
            {
                if (isEnumeration)
                {
                    String _namespace = XMLDocumentUtil.getTargetNamespace(element.getOwnerDocument());
                    EnnumerationParameterDefinition parameter = new EnnumerationParameterDefinition(_namespace, element, base, name, isRequired, service);
                    this.properties.add(parameter);
                }

            }

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
                fillProperties(element, _isRequired, _isMultiple, _name);

            }
            else if (!element.getAttribute("type").equals("")) // es de un tipo
            {
                String _type = element.getAttribute("type");
                String _nameElement = element.getAttribute("name");
                if (XMLDocumentUtil.isBasic(_type, jDomDefinition))
                {
                    String _namespace = XMLDocumentUtil.getTargetNamespace(element.getOwnerDocument());
                    WSDLParameterDefinition parameter = new WSDLParameterDefinition(_namespace, element, _type, _nameElement, _isRequired, service);
                    this.properties.add(parameter);
                }
                else
                {
                    Element elementToCode = XMLDocumentUtil.getElementByTypeFromSchema(_type, element.getOwnerDocument());
                    String nameElement = element.getAttribute("name");
                    fillProperties(elementToCode, _isRequired, _isMultiple, nameElement);
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
        else if (_name.equals("attribute"))
        {

            boolean _isRequired = false;
            if ("required".equals(element.getAttribute("use")))
            {
                _isRequired = true;
            }
            boolean _isMultiple = false;
            String _type = element.getAttribute("type");
            String _nameAttribute = element.getAttribute("name");
            String _namespace = XMLDocumentUtil.getTargetNamespace(element.getOwnerDocument());
            WSDLParameterDefinition parameter = new WSDLParameterDefinition(_namespace, element, _type, _nameAttribute, _isRequired, service);
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
                fillProperties(element, _isRequired, _isMultiple, _name);

            }
            else if (!element.getAttribute("type").equals("")) // es de un tipo
            {
                String _type = element.getAttribute("type");
                String _nameElement = element.getAttribute("name");
                if (XMLDocumentUtil.isBasic(_type, jDomDefinition))
                {
                    String _namespace = XMLDocumentUtil.getTargetNamespace(element.getOwnerDocument());
                    WSDLParameterDefinition parameter = new WSDLParameterDefinition(_namespace, element, _type, _nameElement, _isRequired, service);
                    this.properties.add(parameter);
                }
                else
                {
                    Element elementToCode = XMLDocumentUtil.getElementByTypeFromSchema(_type, element.getOwnerDocument());
                    String nameElement = element.getAttribute("name");
                    fillProperties(elementToCode, _isRequired, _isMultiple, nameElement);
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

    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public SchemaClass getDefinitionClass() throws ServiceException
    {
        if (schemaToClazz == null)
        {
            schemaToClazz = new SchemaClass(this, service);
        }
        return schemaToClazz;
    }

    public void add(Element element, Object value) throws ServiceException
    {
        Element elementProp = element.getOwnerDocument().createElementNS(namespace, this.name);
        if (!properties.isEmpty())
        {
            if (value instanceof JSONObject)
            {
                JSONObject json = (JSONObject) value;
                for (WSDLParameterDefinition parameter : this.properties)
                {
                    String _name = parameter.getName();
                    if (json.has(_name))
                    {
                        try
                        {
                            Object valueParameter = json.get(_name);
                            parameter.add(element, value);
                        }
                        catch (JSONException e)
                        {
                            throw new ServiceException(e);
                        }
                    }
                    else
                    {
                        if (parameter.isRequired)
                        {
                            throw new ServiceException("The parameter " + _name + " was not found");
                        }
                    }
                }
            }
            else
            {
                throw new ServiceException("The parameters are not included");
            }

        }
        else
        {

            Text text = element.getOwnerDocument().createTextNode(value.toString());
            elementProp.appendChild(text);
        }
        element.appendChild(elementProp);
    }

    public void add(Document doc, Object value) throws ServiceException
    {
        Element element = doc.createElementNS(namespace, this.name);
        if (!properties.isEmpty())
        {
            if (value instanceof JSONObject)
            {
                JSONObject json = (JSONObject) value;
                for (WSDLParameterDefinition parameter : this.properties)
                {
                    String _name = parameter.getName();
                    if (json.has(_name))
                    {
                        try
                        {
                            Object valueParameter = json.get(_name);
                            parameter.add(element, valueParameter);
                        }
                        catch (JSONException e)
                        {
                            throw new ServiceException(e);
                        }
                    }
                    else
                    {
                        if (parameter.isRequired)
                        {
                            throw new ServiceException("The parameter " + _name + " was not found");
                        }
                    }
                }
            }
            else
            {
                throw new ServiceException("The parameters are not included");
            }

        }
        doc.appendChild(element);
    }
}
