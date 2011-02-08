/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.rest.consume;

import org.semanticwb.rest.util.HTTPMethod;
import bsh.Interpreter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jdom.Namespace;
import org.jdom.input.DOMBuilder;
import org.jdom.input.SAXBuilder;
import org.jdom.output.DOMOutputter;
import org.jdom.xpath.XPath;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.rest.util.MemoryClassLoader;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 *
 * @author victor.lorenzana
 */
public class ApplicationXML extends RepresentationBase implements RepresentationRequest, XmlResponse
{

    public static final String APPLICATION_XML = "application/xml";
    private static final Logger log = SWBUtils.getLogger(ApplicationXML.class);
    private static final String NL = "\r\n";
    private Document document;
    private int status;
    private URL url;
    private String APPLICATION_NS = "http://www.demo.org.mx";

    public ApplicationXML()
    {
    }

    public void setMethod(Method method)
    {
        if (this.method == null)
        {
            this.method = method;
        }
    }

    public void setURL(URL url)
    {
        if (this.url == null)
        {
            this.url = url;
        }
    }

    public void setStatus(int status)
    {
        if (this.status == 0)
        {
            this.status = status;
        }
    }

    public Object getResponse()
    {
        return document;
    }

    public String getMediaType()
    {
        return "application/xml";
    }

    public Document getDocument()
    {
        return document;
    }

    public static void createClasses(Element element, HashMap<String, String> classes)
    {
        String name = toUpperCase(element.getTagName());
        NodeList nodes = element.getChildNodes();
        String code = getCode(element);
        for (int i = 0; i < nodes.getLength(); i++)
        {
            Node node = nodes.item(i);
            if (node instanceof Element && !classes.containsKey(name))
            {
                createClasses((Element) node, classes);
            }
        }
        if (!classes.containsKey(name))
        {
            classes.put(name, code);
        }
    }

    public static String toUpperCase(String data)
    {
        String letter = data.substring(0, 1);
        return letter.toUpperCase() + data.substring(1).toLowerCase();
    }

    private static String getCode(Element element)
    {
        StringBuilder sb = new StringBuilder();
        String className = toUpperCase(element.getTagName());
        sb.append("import java.util.ArrayList;" + NL);
        sb.append("import java.util.List;" + NL);
        sb.append("import org.w3c.dom.Element;" + NL);
        sb.append("import org.w3c.dom.Node;" + NL);
        sb.append("import org.w3c.dom.NodeList;" + NL);

        sb.append("public class " + className + " {" + NL);
        sb.append("     private final Element element;" + NL);
        sb.append("     public " + className + "(Element element)" + NL);
        sb.append("     {" + NL);
        sb.append("         this.element=element;" + NL);
        sb.append("     }" + NL);

        Set<String> methods = new HashSet<String>();

        NamedNodeMap map = element.getAttributes();
        for (int i = 0; i < map.getLength(); i++)
        {
            Node node = map.item(i);
            if (node instanceof Attr)
            {
                Attr att = (Attr) node;
                String name = att.getName().toLowerCase();
                name = name.replace(':', '_');

                if (!methods.contains("get" + name))
                {
                    sb.append("     public String get" + name + "()" + NL);
                    sb.append("     {" + NL);
                    sb.append("         return element.getAttribute(\"" + att.getName() + "\");" + NL);
                    sb.append("     }" + NL);
                    methods.add("get" + name);
                }
            }
        }



        NodeList childs = element.getChildNodes();
        for (int i = 0; i < childs.getLength(); i++)
        {
            Node node = childs.item(i);
            if (node instanceof Element)
            {
                String elementName = ((Element) node).getTagName();
                String name = toUpperCase(elementName);
                if (!methods.contains("list" + name))
                {
                    sb.append("     public List<" + name + "> list" + name + "()" + NL);
                    sb.append("     {" + NL);
                    sb.append("         ArrayList<" + name + "> elements=new ArrayList<" + name + ">();" + NL);
                    sb.append("         NodeList nodes=element.getChildNodes();" + NL);
                    sb.append("         for(int i=0;i<nodes.getLength();i++)" + NL);
                    sb.append("         {" + NL);
                    sb.append("             Node node=nodes.item(i);" + NL);
                    sb.append("             if(node instanceof Element)" + NL);
                    sb.append("             {" + NL);
                    sb.append("                 Element e=(Element)node;" + NL);
                    sb.append("                 if(e.getTagName().equals(\"" + elementName + "\"))" + NL);
                    sb.append("                     elements.add(new " + name + "(e));" + NL);
                    sb.append("             }" + NL);
                    sb.append("         }" + NL);
                    sb.append("         return elements;" + NL);
                    sb.append("     }" + NL);
                    methods.add("list" + name);
                }
                if (!methods.contains("get" + name))
                {
                    sb.append("     public " + name + " get" + name + "()" + NL);
                    sb.append("     {" + NL);
                    sb.append("         NodeList nodes=element.getChildNodes();" + NL);
                    sb.append("         for(int i=0;i<nodes.getLength();i++)" + NL);
                    sb.append("         {" + NL);
                    sb.append("             Node node=nodes.item(i);" + NL);
                    sb.append("             if(node instanceof Element)" + NL);
                    sb.append("             {" + NL);
                    sb.append("                 Element e=(Element)node;" + NL);
                    sb.append("                 if(e.getTagName().equals(\"" + elementName + "\"))" + NL);
                    sb.append("                     return new " + name + "(e);" + NL);
                    sb.append("             }" + NL);
                    sb.append("         }" + NL);
                    sb.append("         return null;" + NL);
                    sb.append("     }" + NL);
                    methods.add("get" + name);
                }
            }
        }



        sb.append("     @Override" + NL);
        sb.append("     public String toString()" + NL);
        sb.append("     {" + NL);
        sb.append("         return element.getTextContent();" + NL);
        sb.append("     }" + NL);

        sb.append("}" + NL);


        return sb.toString();
    }

    private Object getObject() throws bsh.EvalError, RestException
    {
        ClassLoader mcls = getClassLoader();
        String className = toUpperCase(getRootName(document));
        try
        {
            Class clazz = mcls.loadClass(className);
            Constructor c = clazz.getConstructor(Element.class);
            Object obj = c.newInstance(document.getDocumentElement());
            return obj;
        }
        catch (Exception clnfe)
        {
            throw new RestException("Error creating a object response", clnfe);
        }
    }

    public static HashMap<String, String> getClasses(Document response)
    {
        HashMap<String, String> classes = new HashMap<String, String>();
        Element root = response.getDocumentElement();
        createClasses(root, classes);
        return classes;
    }

    public static String getRootName(Document document)
    {
        return document.getDocumentElement().getTagName();
    }

    public ClassLoader getClassLoader() throws bsh.EvalError
    {
        MemoryClassLoader mcls = new MemoryClassLoader(RestSource.class.getClassLoader());
        HashMap<String, String> classes = getClasses(document);
        if (!classes.isEmpty())
        {
            mcls.addAll(classes);
        }
        return mcls;
    }

    public Interpreter getInterpreter() throws bsh.EvalError, RestException
    {
        Interpreter i = new Interpreter();
        ClassLoader mcls = getClassLoader();
        String className = getRootName(document);
        className = toUpperCase(className);
        try
        {
            i.setClassLoader(mcls);
            Object obj = getObject();
            i.set(className.toLowerCase(), obj);
            return i;
        }
        catch (Exception e)
        {
            throw new RestException(e);
        }
    }

    public Object getValue(ParameterDefinition definition) throws RestException
    {
        Object[] values = getValues(definition);
        if (values.length > 0)
        {
            return values[0];
        }
        return null;
    }

    public Object[] getValues(ParameterDefinition definition) throws RestException
    {
        ArrayList<Object> values = new ArrayList<Object>();
        try
        {
            DOMBuilder builder = new DOMBuilder();
            XPath xpath = XPath.newInstance(definition.getPath());
            Element root = definition.getMethod().getResource().getServiceInfo().getDocument().getDocumentElement();
            definition.getPath();
            for (int i = 0; i < root.getAttributes().getLength(); i++)
            {
                Node attnode = root.getAttributes().item(i);
                if (attnode instanceof Attr)
                {
                    Attr att = (Attr) attnode;
                    if ("xmlns".equals(att.getPrefix()))
                    {
                        String prefix = att.getLocalName();
                        String ns = att.getValue();
                        xpath.addNamespace(prefix, ns);
                    }
                }
            }

            List nodes = xpath.selectNodes(builder.build(document));
            for (int i = 0; i < nodes.size(); i++)
            {
                Object node = nodes.get(i);
                if (node instanceof org.jdom.Element)
                {
                    org.jdom.Element e = (org.jdom.Element) node;
                    Object value = RestPublish.get(e.getValue(), RestPublish.classToxsd(definition.getType()));
                    values.add(value);
                }
            }
        }
        catch (Exception e)
        {
            throw new RestException(e);
        }
        return values.toArray(new Object[values.size()]);
    }

    public ParameterDefinition[] getParameterDefinitions()
    {
        for (ResponseDefinition def : this.getMethod().definitionResponses)
        {
            if (def.getStatus() == status)
            {
                return def.getParameters();
            }
        }
        return null;
    }

    public int getStatus()
    {
        return status;
    }

    public URL getLink(ParameterDefinition definition) throws RestException
    {
        URL[] temp = getLinks(definition);
        if (temp != null && temp.length > 0)
        {
            return temp[0];
        }
        return null;
    }

    public URL[] getLinks(ParameterDefinition definition) throws RestException
    {
        ArrayList<URL> values = new ArrayList<URL>();
        try
        {
            DOMBuilder builder = new DOMBuilder();
            XPath xpath = XPath.newInstance(definition.getPath());
            Element root = definition.getMethod().getResource().getServiceInfo().getDocument().getDocumentElement();
            for (int i = 0; i < root.getAttributes().getLength(); i++)
            {
                Node attnode = root.getAttributes().item(i);
                if (attnode instanceof Attr)
                {
                    Attr att = (Attr) attnode;
                    if ("xmlns".equals(att.getPrefix()))
                    {
                        String prefix = att.getLocalName();
                        String ns = att.getValue();
                        xpath.addNamespace(prefix, ns);
                    }
                }
            }

            List nodes = xpath.selectNodes(builder.build(document));
            for (int i = 0; i < nodes.size(); i++)
            {
                Object node = nodes.get(i);
                if (node instanceof org.jdom.Element)
                {
                    String prefixXlink = "xlink";
                    Charset charset = Charset.defaultCharset();
                    String ns = document.getDocumentElement().getNamespaceURI();
                    org.jdom.Element e = (org.jdom.Element) node;
                    Namespace nslink = Namespace.getNamespace(prefixXlink, ns);
                    org.jdom.Attribute attjdom = e.getAttribute("href", nslink);
                    if (attjdom != null)
                    {
                        String value = attjdom.getValue();
                        if (value != null && !value.trim().equals(""))
                        {
                            URL href = RestPublish.resolve(value, this.url.toURI()).toURL();
                            values.add(href);
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            throw new RestException(e);
        }
        return values.toArray(new URL[values.size()]);
    }

    public Object getNativeResponse() throws RestException
    {
        return this.document;
    }

    public void process(HttpURLConnection con) throws ExecutionRestException
    {
        try
        {
            Charset charset = Charset.forName("utf-8");
            String contentType = con.getContentType();
            if (contentType != null)
            {
                int pos = contentType.indexOf("charset=");
                if (pos != -1)
                {
                    String scharset = contentType.substring(pos + 8).trim();
                    charset = Charset.forName(scharset);
                }
            }
            InputStreamReader reader = new InputStreamReader(con.getInputStream(), charset);
            DOMOutputter out = new DOMOutputter();
            SAXBuilder builder = new SAXBuilder();
            document = out.output(builder.build(reader));
            if (document == null)
            {
                throw new ExecutionRestException(HTTPMethod.POST, con.getURL(), "The document is invalid");
            }
            reader.close();
        }
        catch (Exception e)
        {
            throw new ExecutionRestException(this.method.getHTTPMethod(), url, e);
        }
    }

    

    protected Document constructParameters(List<ParameterValue> values) throws RestException
    {
        Document doc = SWBUtils.XML.getNewDocument();
        return doc;
    }

    private Parameter getDefinition(String name)
    {
        for (Parameter parameter : this.getAllParameters())
        {
            if (parameter.getName().equals(name))
            {
                return parameter;
            }
        }
        for (Parameter parameter : this.method.getAllParameters())
        {
            if (parameter.getName().equals(name))
            {
                return parameter;
            }
        }
        return null;
    }
    private String constructParametersToURL(List<ParameterValue> values) throws RestException
    {
        StringBuilder sb = new StringBuilder();
        for (ParameterValue pvalue : values)
        {
            Parameter definition = getDefinition(pvalue.getName());
            if (definition != null && "query".equals(definition.getStyle()))
            {
                try
                {
                    sb.append(pvalue.getName());
                    sb.append("=");
                    sb.append(URLEncoder.encode(pvalue.getValue().toString(), "utf-8"));
                    sb.append("&");
                }
                catch (Exception e)
                {
                    log.debug(e);
                    throw new RestException(e);
                }
            }

        }

        for (Parameter parameter : this.parameters)
        {
            if (parameter.isFixed())
            {
                try
                {
                    sb.append(parameter.getName());
                    sb.append("=");
                    sb.append(URLEncoder.encode(parameter.getFixedValue(), "utf-8"));
                    sb.append("&");
                }
                catch (Exception e)
                {
                    log.debug(e);
                    throw new RestException(e);
                }
            }
        }

        for (Parameter parameter : this.method.getAllParameters())
        {
            if (parameter.isFixed())
            {
                try
                {
                    sb.append(parameter.getName());
                    sb.append("=");
                    sb.append(URLEncoder.encode(parameter.getFixedValue(), "utf-8"));
                    sb.append("&");
                }
                catch (Exception e)
                {
                    log.debug(e);
                    throw new RestException(e);
                }
            }
        }


        /*try
        {
        for (Parameter parameter : this.getRequiredParameters())
        {
        for (ParameterValue pvalue : values)
        {
        if (pvalue.getName().equals(parameter.getName()))
        {
        sb.append(parameter.getName());
        sb.append("=");
        sb.append(URLEncoder.encode(pvalue.getValue().toString(), "utf-8"));
        sb.append("&");
        }
        }
        }
        for (Parameter parameter : this.getOptionalParameters())
        {
        for (ParameterValue pvalue : values)
        {
        if (pvalue.getName().equals(parameter.getName()))
        {
        sb.append(parameter.getName());
        sb.append("=");
        sb.append(URLEncoder.encode(pvalue.getValue().toString(), "utf-8"));
        sb.append("&");
        }
        }
        }
        for (Parameter parameter : this.parameters)
        {
        if (parameter.isFixed())
        {
        sb.append(parameter.getName());
        sb.append("=");
        sb.append(URLEncoder.encode(parameter.getFixedValue(), "utf-8"));
        sb.append("&");
        }
        }


        for (Parameter parameter : this.method.getRequiredParameters())
        {
        for (ParameterValue pvalue : values)
        {
        if (pvalue.getName().equals(parameter.getName()))
        {
        sb.append(parameter.getName());
        sb.append("=");
        sb.append(URLEncoder.encode(pvalue.getValue().toString(), "utf-8"));
        sb.append("&");
        }
        }
        }
        for (Parameter parameter : this.method.getOptionalParameters())
        {
        for (ParameterValue pvalue : values)
        {
        if (pvalue.getName().equals(parameter.getName()))
        {
        sb.append(parameter.getName());
        sb.append("=");
        sb.append(URLEncoder.encode(pvalue.getValue().toString(), "utf-8"));
        sb.append("&");
        }
        }
        }
        for (Parameter parameter : this.method.getAllParameters())
        {
        if (parameter.isFixed())
        {
        sb.append(parameter.getName());
        sb.append("=");
        sb.append(URLEncoder.encode(parameter.getFixedValue(), "utf-8"));
        sb.append("&");
        }
        }

        }
        catch (Exception e)
        {
        log.debug(e);
        throw new RestException(e);
        }*/
        return sb.toString();
    }

    public RepresentationResponse request(List<ParameterValue> values) throws RestException
    {
        checkParameters(values);
        URL _url = this.getMethod().getResource().getPath();
        String _parameters = constructParametersToURL(values);

        try
        {
            _url = new URL(_url.toString() + "?" + _parameters);
            HttpURLConnection con = (HttpURLConnection) _url.openConnection();
            con.setRequestMethod(this.getMethod().getHTTPMethod().toString());
            String charset = Charset.defaultCharset().name();
            con.setRequestProperty(CONTENT_TYPE, APPLICATION_XML + "; charset=" + charset);
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStream out = con.getOutputStream();
            Document requestAtomDocument = constructParameters(values);
            String xml = SWBUtils.XML.domToXml(requestAtomDocument, charset, true);
            out.write(xml.getBytes());
            out.close();
            return super.processResponse(con);
        }
        catch (Exception ioe)
        {
            throw new ExecutionRestException(this.getMethod().getHTTPMethod(), _url, ioe);
        }
    }

    public void addParameter(Parameter parameter)
    {
        this.parameters.add(parameter);
    }
}
