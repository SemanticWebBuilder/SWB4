/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.rest;

import java.util.ArrayList;
import javax.xml.XMLConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public final class ResponseDefinition
{

    private final int status;
    private final String mediaType;
    private ParameterDefinition[] parameters;

    private ResponseDefinition(int status, String mediaType)
    {
        this.status = status;
        this.mediaType = mediaType;        
    }

    public int getStatus()
    {
        return status;
    }

    public String getMediaType()
    {
        return mediaType;
    }

    private static Element getSchema(Document doc, String localname, String namespace)
    {
        NodeList schemas = doc.getElementsByTagNameNS(XMLConstants.W3C_XML_SCHEMA_NS_URI, "schema");
        for (int i = 0; i < schemas.getLength(); i++)
        {
            Element schema = (Element) schemas.item(i);
            String targetNamespace = schema.getAttribute("targetNamespace");
            if (namespace.equals(targetNamespace))
            {
                // this is the schema
                NodeList childs = schema.getChildNodes();
                for (int j = 0; j < childs.getLength(); j++)
                {
                    Node child = childs.item(j);
                    if (child instanceof Element && XMLConstants.W3C_XML_SCHEMA_NS_URI.equals(child.getNamespaceURI()))
                    {
                        Element eDefinition = (Element) child;
                        if (eDefinition.getLocalName().equals("element"))
                        {
                            String name = eDefinition.getAttribute("name");
                            if (localname.equals(name))
                            {
                                return schema;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
    private static Element getElementDefinition(Document doc, String localname, String namespace)
    {
        NodeList schemas = doc.getElementsByTagNameNS(XMLConstants.W3C_XML_SCHEMA_NS_URI, "schema");
        for (int i = 0; i < schemas.getLength(); i++)
        {
            Element schema = (Element) schemas.item(i);
            String targetNamespace = schema.getAttribute("targetNamespace");
            if (namespace.equals(targetNamespace))
            {
                // this is the schema
                NodeList childs = schema.getChildNodes();
                for (int j = 0; j < childs.getLength(); j++)
                {
                    Node child = childs.item(j);
                    if (child instanceof Element && XMLConstants.W3C_XML_SCHEMA_NS_URI.equals(child.getNamespaceURI()))
                    {
                        Element eDefinition = (Element) child;
                        if (eDefinition.getLocalName().equals("element"))
                        {
                            String name = eDefinition.getAttribute("name");
                            if (localname.equals(name))
                            {
                                return eDefinition;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public static void extractDefinitions(Element parent, ArrayList<ParameterDefinition> parameterDefinitions,Method method,Element schema)
    {
        NodeList childs = parent.getChildNodes();
        for (int j = 0; j < childs.getLength(); j++)
        {
            if (childs.item(j) instanceof Element)
            {
                Element child=(Element)childs.item(j);
                if(child.getLocalName().equals("complexType"))
                {
                    NodeList complexNodeChilds=child.getChildNodes();
                    for(int i=0;i<complexNodeChilds.getLength();i++)
                    {
                        if(complexNodeChilds.item(i) instanceof Element && ((Element)complexNodeChilds.item(i)).getLocalName().equals("sequence"))
                        {
                            Element sequence=(Element)complexNodeChilds.item(i);
                            extractDefinitions(sequence, parameterDefinitions,method,schema);
                        }
                    }
                }
                else
                {
                    if(child.getLocalName().equals("element") || child.getLocalName().equals("attribute"))
                    {
                        try
                        {
                            ParameterDefinition parameter = ParameterDefinition.createParameterDefinition(child,method,schema);
                            parameterDefinitions.add(parameter);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static ResponseDefinition[] createResponseDefinition(Element response, Method method) throws RestException
    {
        ArrayList<ResponseDefinition> definitions = new ArrayList<ResponseDefinition>();
        int istatus = 200;
        String status = response.getAttribute("status");
        if (status != null && !status.trim().equals(""))
        {
            istatus = Integer.parseInt(status);
        }
        NodeList childs = response.getChildNodes();
        for (int i = 0; i < childs.getLength(); i++)
        {
            if (childs.item(i) instanceof Element && ((Element) childs.item(i)).getNamespaceURI() != null && ((Element) childs.item(i)).getNamespaceURI().equals(response.getNamespaceURI()))
            {
                if (((Element) childs.item(i)).getTagName().equals("representation"))
                {
                    Element representation = (Element) childs.item(i);
                    if (representation.getAttribute("mediaType") == null || representation.getAttribute("mediaType").trim().equals(""))
                    {
                        throw new RestException("The attribute mediaType was not found");
                    }
                    String mediaType = representation.getAttribute("mediaType");
                    Element responseDefinition = null;
                    ResponseDefinition definition = new ResponseDefinition(istatus, mediaType);
                    definitions.add(definition);
                    if (representation.getAttribute("element") != null && !representation.getAttribute("element").trim().equals(""))
                    {
                        String element = representation.getAttribute("element");
                        String namespace = method.getResource().getServiceInfo().getNamespaceURI();
                        int pos = element.indexOf(":");
                        if (pos != -1)
                        {
                            String prefix = element.substring(0, pos);
                            element = element.substring(pos + 1);
                            namespace = response.getOwnerDocument().getDocumentElement().getAttribute("xmlns:" + prefix);
                            if (namespace == null || namespace.trim().equals(""))
                            {
                                throw new RestException("The namespace for the prefix " + prefix + " was not found");
                            }
                        }
                        // busca el elemento  en los schemas
                        responseDefinition = getElementDefinition(response.getOwnerDocument(), element, namespace);                        
                        if (responseDefinition == null)
                        {
                            throw new RestException("The element " + element + " was not found");
                        }
                        Element schema=getSchema(response.getOwnerDocument(), element, namespace);
                        ArrayList<ParameterDefinition> parameterDefinitions = new ArrayList<ParameterDefinition>();
                        extractDefinitions(responseDefinition, parameterDefinitions,method,schema);
                        definition.parameters=parameterDefinitions.toArray(new ParameterDefinition[parameterDefinitions.size()]);
                    }                    
                    
                }
            }
        }
        return definitions.toArray(new ResponseDefinition[definitions.size()]);
    }

    public ParameterDefinition[] getParameters()
    {
        return parameters;
    }
}
