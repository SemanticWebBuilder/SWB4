/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.wsdl.util;


import java.util.ArrayList;
import org.jdom.Namespace;
import org.semanticwb.wsdl.consume.ServiceException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 *
 * @author victor.lorenzana
 */
public class XMLDocumentUtil
{

    private static final String SCHEMA_NAMESPACE = "http://www.w3.org/2001/XMLSchema";
    /*public static Element[] getElement(String prefix, String name, Document doc, Element element,String type)
    {
    ArrayList<Element> getElement = new ArrayList<Element>();
    String nameElement = element.getAttribute("name");
    String prefixElement = element.getPrefix();

    String oldprefix=prefix;
    if (prefixElement == null)
    {
    prefixElement = "";
    }
    if (prefix == null)
    {
    prefix = prefixElement;
    }
    if (nameElement.equals(name) && prefix.equals(prefixElement) && type.equals(element.getLocalName()))
    {
    getElement.add(element);
    }
    prefix=oldprefix;
    NodeList list = element.getChildNodes();
    for (int i = 0; i < list.getLength(); i++)
    {
    if (list.item(i) instanceof Element)
    {
    Element child = (Element) list.item(i);
    getElement.addAll(Arrays.asList(getElement(prefix, name, doc, child,type)));
    }
    }

    return getElement.toArray(new Element[getElement.size()]);
    }*/

    public static boolean isBasic(String type, org.jdom.Document _jdom)
    {

        String namespace = "";

        int pos = type.indexOf(":");
        if (pos != -1)
        {
            String prefix = type.substring(0, pos);
            Namespace ns = _jdom.getRootElement().getNamespace(prefix);
            if (ns != null)
            {
                namespace = ns.getURI();
            }
            else
            {
                namespace = "";
            }
        }
        return namespace.equals(SCHEMA_NAMESPACE);
    }

    public static Element getElementByType(ArrayList<Element> schemas, String type, org.jdom.Document _jdom) throws ServiceException
    {
        String prefix = null;
        String namespace = null;
        String localname = type;
        int pos = type.indexOf(":");
        if (pos != -1)
        {
            prefix = type.substring(0, pos);
            localname = type.substring(pos + 1);
            Namespace ns = _jdom.getRootElement().getNamespace(prefix);
            if (ns != null)
            {
                namespace = ns.getURI();
            }
        }

        Element schema = null;
        for (Element _schema : schemas)
        {
            String targetNamespace = _schema.getAttribute("targetNamespace");
            if (targetNamespace.equals(namespace))
            {
                schema = _schema;
                break;
            }
        }
        if (schema == null)
        {
            throw new ServiceException("The namespace schema " + namespace + " was not found");
        }
        NodeList types = schema.getElementsByTagNameNS(SCHEMA_NAMESPACE, "complexType");
        for (int i = 0; i < types.getLength(); i++)
        {
            Element complexType = (Element) types.item(i);
            String name = complexType.getAttribute("name");
            if (name.equals(localname))
            {
                return complexType;
            }
        }
        types = schema.getElementsByTagNameNS(SCHEMA_NAMESPACE, "simpleType");
        for (int i = 0; i < types.getLength(); i++)
        {
            Element complexType = (Element) types.item(i);
            String name = complexType.getAttribute("name");
            if (name.equals(localname))
            {
                return complexType;
            }
        }
        return null;
    }

    public static Element getGlobalElement(ArrayList<Element> schemas, String name, org.jdom.Document _jdom)
    {
        String prefix = null;
        String namespace = null;
        String localname = name;
        int pos = name.indexOf(":");
        if (pos != -1)
        {
            prefix = name.substring(0, pos);
            localname = name.substring(pos + 1);
            Namespace ns = _jdom.getRootElement().getNamespace(prefix);
            if (ns != null)
            {
                namespace = ns.getURI();
            }
        }
        for (Element schema : schemas)
        {
            String targetNamespace = schema.getAttribute("targetNamespace");
            if (targetNamespace.equals(namespace))
            {
                NodeList childs = schema.getChildNodes();
                for (int i = 0; i < childs.getLength(); i++)
                {
                    if (childs.item(i) instanceof Element && ((Element) childs.item(i)).getLocalName().equals("element"))
                    {
                        Element element = (Element) childs.item(i);
                        String _name = element.getAttribute("name");
                        if (_name.equals(localname))
                        {
                            return element;
                        }
                    }
                }
            }
        }
        return null;
    }

    public static Element getElementByName(String name,String type, Document doc)
    {

        String prefix = null;
        String namespace = null;
        String localname = name;
        int pos = name.indexOf(":");
        if (pos != -1)
        {
            prefix = name.substring(0, pos);
            localname = name.substring(pos + 1);
            namespace=doc.lookupNamespaceURI(prefix);
        }
        NodeList list=null;
        if(namespace==null)
        {            
            list=doc.getElementsByTagNameNS("*",type);
        }
        else
        {
            list=doc.getElementsByTagNameNS(namespace, type);
        }
        
        for(int i=0;i<list.getLength();i++)
        {
            Element element=(Element)list.item(i);
            if(element.getAttribute("name").equals(localname))
            {
                return element;
            }
        }
        return null;
        
    }
    /*public static Element[] getElementByName(String name, Document doc,String type)
    {
    ArrayList<Element> getElement = new ArrayList<Element>();
    String prefix = null;
    int pos = name.indexOf(":");
    String localname = name;
    if (pos != -1)
    {
    prefix = name.substring(0, pos);
    localname = name.substring(pos + 1);
    if ("tns".equals(prefix))
    {
    prefix = null;
    }
    }
    else
    {
    prefix = null;
    }
    getElement.addAll(Arrays.asList(getElement(prefix, localname, doc, doc.getDocumentElement(),type)));
    return getElement.toArray(new Element[getElement.size()]);
    }*/
}
