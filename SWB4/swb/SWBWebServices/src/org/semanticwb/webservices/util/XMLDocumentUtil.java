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
package org.semanticwb.webservices.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Validator;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.jdom.Namespace;
import org.jdom.input.DOMBuilder;
import org.jdom.input.SAXBuilder;
import org.jdom.output.DOMOutputter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
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
    
    public static JSONObject toCanonicalJSON(Document doc) throws ServiceException
    {
        try {
            String xml = domToXml(doc,"UTF-8",true);
            InputStream is = XMLDocumentUtil.class.getClass().getResourceAsStream("/org/semanticwb/webservices/util/xml-to-json.xsl");
            ByteArrayOutputStream dest = new ByteArrayOutputStream();
            
            TransformerFactory tfactory = TransformerFactory.newInstance("net.sf.saxon.TransformerFactoryImpl",null);
            Transformer transformer = tfactory.newTransformer(new StreamSource(is));
            transformer.transform(new StreamSource(getStreamFromString(xml)), new StreamResult(dest));
            
            JSONObject json = new JSONObject(dest.toString());
            return json;
        }catch(Exception e) {
            throw new ServiceException(e.getCause());
        }
    }
    
    public static JSONObject toJSON(Document doc) throws ServiceException {
        try {
            String xml = domToXml(doc, "UTF-8", true);
            JSONObject json = XML.toJSONObject(xml);
            return json;
        }catch(JSONException jse) {
            throw new ServiceException(jse.getCause());
        }
    }
    
    
    
    private static InputStream getStreamFromString(String str)
    {
        InputStream ret = null;
        if (str != null)
        {
            ret = new ByteArrayInputStream(str.getBytes());
        }
        return ret;
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

    public static Document getDocument(JSONObject json, Document schema) throws Exception
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();
        Element root = doc.createElement("r");
        doc.appendChild(root);
        getDocument(json, doc, root);
        return doc;
    }
    
    private static void getDocument(JSONObject json, Document doc, Element node) throws Exception {
        if(json==null) {
            return;
        }
        Iterator<String>it = json.keys();
        while(it.hasNext()) {
            String k = it.next();
            try {
                JSONObject jo = json.getJSONObject(k);            
                Element child = doc.createElement(k);
                getDocument(jo,doc,child);
                node.appendChild(child);
            }catch(JSONException jsone1) {
               try {
                    JSONArray ja = json.getJSONArray(k);
                    getDocument(ja,doc,node,k);
                }catch(JSONException jsone2) {
                    try {
                        Element child = doc.createElement(k);
                        if(!json.isNull(k)) {
                            child.appendChild(doc.createTextNode(String.valueOf(json.get(k))));
                        }
                        node.appendChild(child);
                    }catch(JSONException jsone3) {
                        jsone3.printStackTrace(System.out);
                        throw new Exception(jsone3);
                    }
                }
            }
        }
    }
    
    private static void getDocument(JSONArray jarr, Document doc, Element node, String nodeName) throws Exception {
        if(jarr.length()==0) {
            return;
        }else {
            for(int i=0; i<jarr.length(); i++) {
                try {
                    JSONObject o = jarr.getJSONObject(i);
                    Element child = doc.createElement(nodeName);
                    getDocument(o,doc,child);
                    node.appendChild(child);
                }catch(JSONException json1) {
                    try {
                        JSONArray j = jarr.getJSONArray(i);
                        getDocument(j,doc,node,nodeName);
                    }catch(JSONException jsone2) {
                        try {
                            Element child = doc.createElement(nodeName);
                            if(!jarr.isNull(i))
                                child.appendChild(doc.createTextNode(String.valueOf(jarr.get(i))));
                            node.appendChild(child);
                        }catch(JSONException jsone3) {
                            jsone3.printStackTrace(System.out);
                            throw new Exception(jsone3);
                        }
                    }
                }
            }
        }
    }
    
    public static Document getValidDocument(JSONObject json, String schemaPath) throws Exception
    {
        Document doc = getDocument(json, null);
        DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = parser.parse(new File("instance.xml"));

        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source schemaFile = new StreamSource(new File(schemaPath));
        Schema schema = factory.newSchema(schemaFile);
        Validator validator = schema.newValidator();
        validator.validate(new DOMSource(document));
        
        return doc;
    }
    
    public static String domToXml(Document dom, String encode, boolean indent) {
        ByteArrayOutputStream sw = new java.io.ByteArrayOutputStream();
        OutputStreamWriter osw = null;
        try
        {
            osw = new java.io.OutputStreamWriter(sw, encode);
            StreamResult streamResult = new StreamResult(osw);
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = null;
            synchronized (tFactory)
            {
                transformer = tFactory.newTransformer();
            }
            transformer.setOutputProperty(OutputKeys.ENCODING, encode);
            if (indent)
            {
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                try
                {
                    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                } catch (Exception noe)
                {/*No soportado en algunos xerses*/

                }
            }
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.transform(new DOMSource(dom), streamResult);
        } catch (Exception e)
        {
            e.printStackTrace(System.out);
        }
        return sw.toString();
    }
    
    private static String convertStreamToString(InputStream is) throws IOException {
        if(is != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(
                new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            }finally {
                is.close();
            }
            return writer.toString();
        }else {       
            return "";
        }
    }
    
    
    
    
    
    public static Document xmlToDom(String xml)
        {
            if (xml == null || xml.length() == 0)
            {
                return null;
            }
            Document dom = null;
            try
            {
                ByteArrayInputStream sr = new java.io.ByteArrayInputStream(xml.getBytes());
                dom = xmlToDom(sr);
            } catch (Exception e)
            {
                
            }
            return dom;
        }
    public static Document xmlToDom(InputStream xml)
        {
            Document dom = null;
            try
            {
                dom = xmlToDom(new InputSource(xml));
                //xml.close();
            } catch (Exception e)
            {
               
            }
            return dom;
        }
     public static Document xmlToDom(InputSource xml)
        {
            DocumentBuilderFactory dbf = null;
            DocumentBuilder db = null;
            Document dom = null;
            try
            {
                dbf = getDocumentBuilderFactory();
                synchronized (dbf)
                {
                    db = dbf.newDocumentBuilder();
                }
                if (xml != null)
                {
                    dom = db.parse(xml);
                        dom = copyDom(dom);
                }
            } catch (Exception e)
            {
                
            }
            return dom;
        }
      public static Document copyDom(Document dom) throws Exception
        {
            Document n = getNewDocument();
            if (dom != null && dom.hasChildNodes())
            {
                Node node = n.importNode(dom.getFirstChild(), true);
                n.appendChild(node);
            }
            return n;
        }
       public static Document getNewDocument() //throws SWBException
        {
            DocumentBuilderFactory dbf = getDocumentBuilderFactory();
            DocumentBuilder db = null;
            Document dom = null;
            try
            {
                synchronized (dbf)
                {
                    db = dbf.newDocumentBuilder();
                }
                dom = db.newDocument();
            } catch (Exception e)
            {
                
            }
            return dom;
        }
       public static DocumentBuilderFactory getDocumentBuilderFactory()
        {
            return m_dbf.get();
        }
       private static final ThreadLocal<DocumentBuilderFactory> m_dbf = new ThreadLocal<DocumentBuilderFactory>()
        {
            @Override
            public DocumentBuilderFactory  initialValue()
            {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                dbf.setNamespaceAware(true);
                dbf.setIgnoringElementContentWhitespace(true);                
                return  dbf;
            }
        };
}
