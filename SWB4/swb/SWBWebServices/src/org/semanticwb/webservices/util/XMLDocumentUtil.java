/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.webservices.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.jdom.Namespace;
import org.jdom.input.DOMBuilder;
import org.jdom.input.SAXBuilder;
import org.jdom.output.DOMOutputter;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.webservices.ServiceException;
import org.semanticwb.webservices.ServiceInfo;
import org.semanticwb.webservices.wadl.consume.WADLServiceInfo;
import org.semanticwb.webservices.wsdl.consume.WSDLServiceInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author victor.lorenzana
 */
public class XMLDocumentUtil
{

    private static final HashMap<String, Document> schemas = new HashMap<String, Document>();
    private static final Logger log = SWBUtils.getLogger(WSDLServiceInfo.class);
    public static final String WSDL_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/";
    public static final String WADL_NS_2006 = "http://research.sun.com/wadl/2006/10";
    public static final String WADL_NS_2009 = "http://wadl.dev.java.net/2009/02";
    public static final String TEXT_XML = "text/xml";
    public static final String APPLICATION_XML = "application/xml";
    public static final String SCHEMA_NAMESPACE = "http://www.w3.org/2001/XMLSchema";
    public static final String SCHEMA_2001_NAMESPACE = "http://www.w3.org/2001/XMLSchema";

    static
    {
        {
            InputStream in = WADLServiceInfo.class.getResourceAsStream("/org/semanticwb/webservices/util/wadl_2006.xml");
            InputStreamReader inReader = new InputStreamReader(in, Charset.forName("utf-8"));
            InputSource source = new InputSource(inReader);
            SAXBuilder builder = new SAXBuilder();
            DOMOutputter out = new DOMOutputter();
            try
            {
                Document schema = out.output(builder.build(source));
                schemas.put(XMLDocumentUtil.WADL_NS_2006, schema);
            }
            catch (Exception e)
            {
                log.error(e);
            }
        }
        {
            InputStream in = WADLServiceInfo.class.getResourceAsStream("/org/semanticwb/webservices/util/wadl_2009.xml");
            InputStreamReader inReader = new InputStreamReader(in, Charset.forName("utf-8"));
            InputSource source = new InputSource(inReader);
            SAXBuilder builder = new SAXBuilder();
            DOMOutputter out = new DOMOutputter();
            try
            {
                Document schema = out.output(builder.build(source));
                schemas.put(XMLDocumentUtil.WADL_NS_2009, schema);
            }
            catch (Exception e)
            {
                log.error(e);
            }
        }
        {
            InputStream in = WADLServiceInfo.class.getResourceAsStream("/org/semanticwb/webservices/util/wsdlschema.xml");
            InputStreamReader inReader = new InputStreamReader(in, Charset.forName("utf-8"));
            InputSource source = new InputSource(inReader);
            SAXBuilder builder = new SAXBuilder();
            DOMOutputter out = new DOMOutputter();
            try
            {
                Document schema = out.output(builder.build(source));
                schemas.put(XMLDocumentUtil.WSDL_NAMESPACE, schema);
            }
            catch (Exception e)
            {
                log.error(e);
            }
        }
    }

    public static Document getSchema(String namespace)
    {
        Document schema = schemas.get(namespace);
        return schema;
    }

    public static boolean isRef(Element element)
    {
        String ref = element.getAttribute("ref");
        return !"".equals(ref);
    }

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

    public static Element getElementByType(String type, ServiceInfo service, Element reference) throws ServiceException
    {
        String prefix = null;
        String namespace = null;
        String localname = type;
        int pos = type.indexOf(":");
        if (pos != -1)
        {
            prefix = type.substring(0, pos);
            localname = type.substring(pos + 1);
            Namespace ns = service.getJDom().getRootElement().getNamespace(prefix);
            if (ns != null)
            {
                namespace = ns.getURI();
            }
            else
            {
                namespace = getTargetNamespace(service.getDocument());
                if (namespace.equals(""))
                {
                    namespace = getTargetNamespace(reference.getOwnerDocument());
                }
            }
        }
        else
        {
            namespace = getTargetNamespace(service.getDocument());
            if (namespace.equals(""))
            {
                namespace = getTargetNamespace(reference.getOwnerDocument());
            }
        }

        Document[] _schemas = service.getSchemas();
        for (Document schema : _schemas)
        {
            String targetNamespace = schema.getDocumentElement().getAttribute("targetNamespace");
            if (targetNamespace.equals(namespace))
            {
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
                    Element simpleTYpe = (Element) types.item(i);
                    String name = simpleTYpe.getAttribute("name");
                    if (name.equals(localname))
                    {
                        return simpleTYpe;
                    }
                }
            }
        }
        return null;
    }

    public static Element getElementByTypeFromSchema(String type, Document schema) throws ServiceException
    {
        String prefix = null;
        String namespace = null;
        String localname = type;
        int pos = type.indexOf(":");
        if (pos != -1)
        {
            prefix = type.substring(0, pos);
            localname = type.substring(pos + 1);
            Namespace ns = toJdom(schema).getRootElement().getNamespace(prefix);
            if (ns != null)
            {
                namespace = ns.getURI();
            }
            else
            {
                namespace = getTargetNamespace(schema);
            }
        }
        else
        {
            namespace = getTargetNamespace(schema);
        }


        String targetNamespace = schema.getDocumentElement().getAttribute("targetNamespace");
        if (targetNamespace.equals(namespace))
        {
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
        }


        return null;
    }

    public static String getTargetNamespace(Document doc)
    {
        return doc.getDocumentElement().getAttribute("targetNamespace");
    }

    public static Element getGlobalElement(String name, ServiceInfo service)
    {
        String prefix = null;
        String namespace = null;
        String localname = name;
        int pos = name.indexOf(":");
        if (pos != -1)
        {
            prefix = name.substring(0, pos);
            localname = name.substring(pos + 1);
            Namespace ns = service.getJDom().getRootElement().getNamespace(prefix);
            if (ns != null)
            {
                namespace = ns.getURI();
            }
        }
        ArrayList<Document> documents = new ArrayList<Document>();
        documents.addAll(Arrays.asList(service.getSchemas()));
        documents.add(service.getDocument());
        for (Document document : documents)
        {
            Element root = document.getDocumentElement();
            String targetNamespace = root.getAttribute("targetNamespace");
            if (targetNamespace.equals(namespace))
            {
                if (document.getDocumentElement().getLocalName().equals("schema") && document.getDocumentElement().getNamespaceURI().equals(SCHEMA_NAMESPACE))
                {

                    NodeList childs = root.getChildNodes();
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
                else
                {
                    NodeList nodes = document.getElementsByTagNameNS(namespace, localname);
                    if (nodes.getLength() > 0)
                    {
                        return (Element) nodes.item(0);
                    }
                }
            }


        }
        return null;
    }

    public static Element getElementByName(String name, String type, ServiceInfo service, Element reference)
    {

        String prefix = null;
        String namespace = null;
        String localname = name;
        int pos = name.indexOf(":");
        if (pos != -1)
        {
            prefix = name.substring(0, pos);
            localname = name.substring(pos + 1);
            if (prefix.equals("tns"))
            {
                namespace = reference.getNamespaceURI();
            }
            else
            {

                namespace = service.getJDom().getRootElement().getNamespace(prefix).getURI();
            }
        }
        NodeList list = null;
        if (namespace == null)
        {
            list = reference.getOwnerDocument().getElementsByTagNameNS("*", type);
        }
        else
        {
            list = service.getDocument().getElementsByTagNameNS(namespace, type);
        }

        for (int i = 0; i < list.getLength(); i++)
        {
            Element element = (Element) list.item(i);
            if (element.getAttribute("name").equals(localname))
            {
                return element;
            }
        }
        return null;

    }

    public static Document getDocument(URL url) throws ServiceException
    {
        try
        {

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            if (con.getResponseCode() == 200)
            {
                Charset charset = Charset.forName("utf-8");
                String contentType = con.getContentType();
                if (contentType != null)
                {
                    int pos = contentType.indexOf("charset=");
                    if (pos != -1)
                    {
                        String scharset = contentType.substring(pos + 8).trim();
                        contentType = contentType.substring(0, pos).trim();
                        if (contentType.endsWith(";"))
                        {
                            contentType = contentType.substring(0, contentType.length() - 1);
                        }
                        charset = Charset.forName(scharset);
                    }
                }
                if (contentType != null && (contentType.equalsIgnoreCase(APPLICATION_XML) || contentType.equalsIgnoreCase(TEXT_XML)))
                {
                    InputStreamReader reader = new InputStreamReader(con.getInputStream(), charset);
                    DOMOutputter out = new DOMOutputter();
                    SAXBuilder builder = new SAXBuilder();
                    Document response = out.output(builder.build(reader));
                    if (response == null)
                    {
                        throw new ServiceException("The content of the url is invalid");
                    }
                    return response;
                }
                else
                {
                    throw new ServiceException("The response has a not valid Content-Type header: " + contentType + "(only " + APPLICATION_XML + " or " + TEXT_XML + "are valid)");
                }
            }
            else
            {
                throw new ServiceException("The document was not found error code " + con.getResponseCode());
            }
        }
        catch (Exception ioe)
        {
            throw new ServiceException(ioe);
        }
    }

    public static boolean validate(Document doc, String namespace)
    {
        Document schema = getSchema(namespace);
        if (schema == null)
        {
            log.warn("The Schema for namespace " + namespace + " was not found");
            return false;
        }
        return true;
    }

    public static org.jdom.Document toJdom(org.w3c.dom.Document doc)
    {
        DOMBuilder dom = new DOMBuilder();
        org.jdom.Document jdom = dom.build(doc);
        return jdom;
    }
    
    public static JSONObject toJSON(org.w3c.dom.Document doc)
    {
        return null;
    }
    public static String getPrefix(org.w3c.dom.Document doc, String namespace)
    {
        org.jdom.Document jdom = toJdom(doc);
        return getPrefix(jdom, namespace);
    }

    public static String getPrefix(org.jdom.Document doc, String namespace)
    {
        List namespaces = doc.getRootElement().getAdditionalNamespaces();
        for (int i = 0; i < namespaces.size(); i++)
        {
            Object obj = namespaces.get(i);
            Namespace ns = (Namespace) obj;
            if (ns.getURI().equals(namespace))
            {
                return ns.getPrefix();
            }
        }
        return null;
    }

    public static Class getClassTypeFromSchemaName(String schemaName)
    {
        String localname = schemaName;
        int pos = localname.indexOf(":");
        if (pos != -1)
        {
            localname = localname.substring(pos + 1);
        }
        if (localname.equals("string"))
        {
            return String.class;
        }
        else if (localname.equals("int"))
        {
            return Integer.class;
        }
        else if (localname.equals("long"))
        {
            return Long.class;
        }
        else if (localname.equals("long"))
        {
            return Long.class;
        }
        else if (localname.equals("double"))
        {
            return Double.class;
        }
        else if (localname.equals("float"))
        {
            return Float.class;
        }
        else if (localname.equals("byte"))
        {
            return Byte.class;
        }
        else if (localname.equals("short"))
        {
            return Short.class;
        }
        else if (localname.equals("boolean"))
        {
            return Boolean.class;
        }
        else if (localname.equals("anyURI"))
        {
            return URI.class;
        }
        else if (localname.equals("date") || localname.equals("datetime"))
        {
            return Date.class;
        }
        else
        {
            return String.class;
        }
    }

    private static Object convert(Object value, Class clazz)
    {
        Object convert = null;
        if (clazz.getName().equals("java.lang.String"))
        {
            convert = value.toString();
        }
        if (clazz.getName().equals("java.net.URI"))
        {
            try
            {
                convert = new URI(value.toString());
            }
            catch (Exception e)
            {
                log.debug(e);
            }
        }
        if (clazz.getName().equals("java.lang.Double"))
        {
            try
            {
                convert = Double.parseDouble(value.toString());
            }
            catch (Exception e)
            {
                log.debug(e);
            }
        }
        if (clazz.getName().equals("java.lang.Long"))
        {
            try
            {
                convert = Long.parseLong(value.toString());
            }
            catch (Exception e)
            {
                log.debug(e);
            }
        }
        if (clazz.getName().equals("java.lang.Float"))
        {
            try
            {
                convert = Float.parseFloat(value.toString());
            }
            catch (Exception e)
            {
                log.debug(e);
            }
        }
        if (clazz.getName().equals("java.lang.Integer"))
        {
            try
            {
                convert = Integer.parseInt(value.toString());
            }
            catch (Exception e)
            {
                log.debug(e);
            }
        }
        return convert;
    }

    public static Document[] getSchemas(Document doc)
    {
        ArrayList<Document> getSchemas = new ArrayList<Document>();
        NodeList _schemas = doc.getElementsByTagNameNS(SCHEMA_NAMESPACE, "schema");
        for (int i = 0; i < _schemas.getLength(); i++)
        {
            Element schema = (Element) _schemas.item(i);
            Document docSchema = SWBUtils.XML.getNewDocument();
            Node newNode = docSchema.importNode(schema, true);
            docSchema.appendChild(newNode);
            getSchemas.add(docSchema);
        }
        return getSchemas.toArray(new Document[getSchemas.size()]);
    }

    public static boolean validate(JSONObject json, Document schema)
    {
        return false;
    }

    public static String toUpperCase(String data)
    {
        String letter = data.substring(0, 1);
        return "Class" + letter.toUpperCase() + data.substring(1).toLowerCase();
    }

    public static String basicToObject(String varType, ServiceInfo service) throws ServiceException
    {
        String type = varType;
        int pos = varType.indexOf(":");
        if (pos != -1)
        {
            String prefix = varType.substring(0, pos);
            varType = varType.substring(pos + 1);
            Namespace ns = service.getJDom().getRootElement().getNamespace(prefix);
            if (ns == null || !ns.getURI().equals(SCHEMA_NAMESPACE))
            {
                throw new ServiceException("The type " + type + " is not basic");
            }
        }
        if (varType.equalsIgnoreCase("boolean"))
        {
            return "java.lang.Boolean";
        }
        else if (varType.equalsIgnoreCase("int"))
        {
            return "java.lang.Integer";
        }
        else if (varType.equalsIgnoreCase("long"))
        {
            return "java.lang.Long";
        }
        else if (varType.equalsIgnoreCase("double"))
        {
            return "java.lang.Double";
        }
        else if (varType.equalsIgnoreCase("float"))
        {
            return "java.lang.Float";
        }
        else if (varType.equalsIgnoreCase("short"))
        {
            return "java.lang.Short";
        }
        else if (varType.equalsIgnoreCase("byte"))
        {
            return "java.lang.Byte";
        }
        else if (varType.equalsIgnoreCase("string"))
        {
            return "java.lang.String";
        }
        else if (varType.equalsIgnoreCase("anyURI"))
        {
            return "java.net.URI";
        }
        else if (varType.equalsIgnoreCase("datetime") || varType.equalsIgnoreCase("date"))
        {
            return "java.util.Date";
        }
        else
        {
            return toUpperCase(varType);
        }
    }

    public static Document getDocument(JSONObject json, Document schema)
    {
        return null;
    }
}
