/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.webservices.wsdl.consume;

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
public class WSDLParameterDefinition implements ParameterDefinition
{
    private SchemaClass schemaToClazz;
    private final String name;
    private final String type;
    private final String classType;    
    private final Element definition;
    private final boolean isBasic;
    private final boolean isRequired;
    private final boolean isMultiple;
    private final Element part;
    private final ServiceInfo service;
    private final HashSet<WSDLParameterDefinition> properties = new HashSet<WSDLParameterDefinition>();
    public WSDLParameterDefinition(Element definition, String type, String name, boolean isRequired, String classType,ServiceInfo service)
    {
        this.service=service;
        this.name=name;
        this.type=type;
        isBasic=true;
        part=null;
        this.definition=definition;
        this.isRequired=isRequired;
        this.classType=classType;
        this.isMultiple=false;
    }
    public WSDLParameterDefinition(Element part,ServiceInfo service) throws ServiceException
    {
        this.service=service;
        this.part=part;
        this.isMultiple=false;
        
        String _name = "";
        if (part.getAttribute("name") != null)
        {
            _name = part.getAttribute("name");
        }
        this.name = _name;
        String _classType=null;
        String _type = null;
        Element _definition = null;
        boolean _isBasic=false;
        boolean _isRequired=false;
        if (!part.getAttribute("type").equals(""))
        {
            _type = part.getAttribute("type");
            _isBasic=XMLDocumentUtil.isBasic(_type, service.getJDom());
            if(!_isBasic)
            {              
                
                String _classTypeElement=XMLDocumentUtil.getClassTypeFromSchemaName(_type).getCanonicalName();
                _definition = XMLDocumentUtil.getElementByType(_type, service,part);
                fillProperties(part);                
            }            
            
        }
        else if (!part.getAttribute("element").equals(""))
        {
            _type = part.getAttribute("element");
            _isBasic=XMLDocumentUtil.isBasic(_type, service.getJDom());
            if(!_isBasic)
            {                
                String _classTypeElement=XMLDocumentUtil.getClassTypeFromSchemaName(_type).getCanonicalName();
                _definition = XMLDocumentUtil.getGlobalElement(_type, service);
                
                
            }
        }
        this.isRequired=_isRequired;
        this.classType=_classType;
        this.isBasic=_isBasic;
        this.type = _type;
        this.definition = _definition;
        if (!isBasic && definition == null)
        {
            throw new ServiceException("The element definition " + _type + " was not found");
        }
        if(definition!=null)
        {
            fillProperties(_definition);                
        }
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
        throw new UnsupportedOperationException("Not supported yet.");
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
            WSDLParameterDefinition parameter = new WSDLParameterDefinition(element, _type, _nameAttribute, _isRequired, _classType,service);
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
                element = XMLDocumentUtil.getGlobalElement(ref,service);
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
                if (XMLDocumentUtil.isBasic(_type, XMLDocumentUtil.toJdom(definition.getOwnerDocument())))
                {
                    String _classType = XMLDocumentUtil.getClassTypeFromSchemaName(_type).getCanonicalName();
                    WSDLParameterDefinition parameter = new WSDLParameterDefinition(element, _type, _nameElement, _isRequired, _classType,service);
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

    @Override
    public String toString()
    {
        return name;
    }
    @Override
    public SchemaClass getDefinitionClass() throws ServiceException
    {
        if(schemaToClazz==null)
            schemaToClazz=new SchemaClass(this, service);
        return schemaToClazz;
    }    
    
    
}
