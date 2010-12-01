/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.rest;

import bsh.EvalError;
import bsh.Interpreter;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.jdom.Namespace;
import org.jdom.input.DOMBuilder;
import org.jdom.xpath.XPath;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 *
 * @author victor.lorenzana
 */
public class AtomXML extends RepresentationBase implements RepresentationRequest,RepresentationResponse
{
    public static final String APPLICATION_ATOM_XML = "application/atom+xml";
    private static final Logger log = SWBUtils.getLogger(AtomXML.class);
    private Document document;
    public static final String ATOM_NS = "http://www.w3.org/2005/Atom";
    private final int status;
    private final URL url;
    public AtomXML(Method method,int status,URL url)
    {
        //super("application/atom+xml", method);
        this.status=status;
        this.url=url;

    }
    public int getStatus()
    {
        return status;
    }
    public void setDocument(Document document)
    {
        if(this.document==null)
        {
            this.document=document;
        }
    }
    public Document getDocument()
    {
        return document;
    }
    private Document constructParameters(List<ParameterValue> values) throws RestException
    {
        Document doc = SWBUtils.XML.getNewDocument();
        Element feed = doc.createElementNS(ATOM_NS, "feed");
        doc.appendChild(feed);
        try
        {
            for (Parameter parameter : this.getRequiredParameters())
            {
                for (ParameterValue pvalue : values)
                {
                    if (pvalue.getName().equals(parameter.getName()))
                    {
                        Element eparam = doc.createElementNS(ATOM_NS, parameter.getName());
                        Text data = doc.createTextNode(pvalue.getValue().toString());
                        eparam.appendChild(data);
                    }
                }
            }
            for (Parameter parameter : this.getOptionalParameters())
            {
                for (ParameterValue pvalue : values)
                {
                    if (pvalue.getName().equals(parameter.getName()))
                    {
                        Element eparam = doc.createElementNS(ATOM_NS, parameter.getName());
                        Text data = doc.createTextNode(pvalue.getValue().toString());
                        eparam.appendChild(data);
                    }
                }
            }
            for (Parameter parameter : this.parameters)
            {
                if (parameter.isFixed())
                {
                    Element eparam = doc.createElementNS(ATOM_NS, parameter.getName());
                    Text data = doc.createTextNode(parameter.getFixedValue());
                    eparam.appendChild(data);
                }
            }


            for (Parameter parameter : this.method.getRequiredParameters())
            {
                for (ParameterValue pvalue : values)
                {
                    if (pvalue.getName().equals(parameter.getName()))
                    {
                        Element eparam = doc.createElementNS(ATOM_NS, parameter.getName());
                        Text data = doc.createTextNode(pvalue.getValue().toString());
                        eparam.appendChild(data);
                    }
                }
            }
            for (Parameter parameter : this.method.getOptionalParameters())
            {
                for (ParameterValue pvalue : values)
                {
                    if (pvalue.getName().equals(parameter.getName()))
                    {
                        Element eparam = doc.createElementNS(ATOM_NS, parameter.getName());
                        Text data = doc.createTextNode(pvalue.getValue().toString());
                        eparam.appendChild(data);
                    }
                }
            }
            for (Parameter parameter : this.method.getAllParameters())
            {
                if (parameter.isFixed())
                {
                    Element eparam = doc.createElementNS(ATOM_NS, parameter.getName());
                    Text data = doc.createTextNode(parameter.getFixedValue());
                    eparam.appendChild(data);
                }
            }

        }
        catch (Exception e)
        {
            log.debug(e);
            throw new RestException(e);
        }
        return doc;
    }

    public RepresentationResponse request(List<ParameterValue> values) throws RestException
    {
        checkParameters(values);
        URL url = this.getMethod().getResource().getPath();
        try
        {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(this.getMethod().getHTTPMethod().toString());
            String charset = Charset.defaultCharset().name();
            con.setRequestProperty(CONTENT_TYPE + "; charset=" + charset, APPLICATION_ATOM_XML);
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
            throw new ExecutionRestException(this.getMethod().getHTTPMethod(), url, ioe);
        }
    }

    public Interpreter getInterpreter() throws EvalError, RestException
    {
        if(document==null)
        {
            throw new NullPointerException();
        }
        Interpreter i=new Interpreter();
        ClassLoader mcls=getClassLoader();
        String className=ApplicationXML.getRootName(document);
        className=ApplicationXML.toUpperCase(className);
        try
        {
            i.setClassLoader(mcls);
            Object obj=getObject();
            i.set(className.toLowerCase(), obj);
            return i;
        }
        catch(Exception e)
        {
            throw new RestException(e);
        }
    }

    public ClassLoader getClassLoader() throws EvalError
    {
        if(document==null)
        {
            throw new NullPointerException();
        }
        MemoryClassLoader mcls=new MemoryClassLoader(RestSource.class.getClassLoader());
        HashMap<String,String> classes=ApplicationXML.getClasses(document);
        if(!classes.isEmpty())
        {
            mcls.addAll(classes);
        }
        return mcls;
    }

    private Object getObject() throws EvalError, RestException
    {
        if(document==null)
        {
            throw new NullPointerException();
        }
        ClassLoader mcls=getClassLoader();
        String className=ApplicationXML.toUpperCase(ApplicationXML.getRootName(document));
        try
        {
            Class clazz=mcls.loadClass(className);
            Constructor c=clazz.getConstructor(Element.class);
            Object obj=c.newInstance(document.getDocumentElement());
            return obj;
        }
        catch(Exception clnfe)
        {
            throw new RestException("Error creating a object response",clnfe);
        }
    }

    public Object getValue(ParameterDefinition definition) throws RestException
    {
         Object[] values=getValues(definition);
         if(values.length>0)
         {
             return values[0];
         }
         return null;
    }

    public Object[] getValues(ParameterDefinition definition) throws RestException
    {
        ArrayList<Object> values=new ArrayList<Object>();
        try
        {
            XPath xpath=XPath.newInstance(definition.getPath());
            List nodes=xpath.selectNodes(document);
            for(int i=0;i<nodes.size();i++)
            {
                Object node=nodes.get(i);
                if(node instanceof Element)
                {
                    Element e=(Element)node;
                    NodeList childs=e.getChildNodes();
                    for(int j=0;j<childs.getLength();j++)
                    {
                        if(childs.item(j) instanceof Text)
                        {
                            Text data=(Text)childs.item(j);
                            RestPublish.get(data.getNodeValue(),RestPublish.classToxsd(definition.getType()));
                        }
                    }
                }
            }
        }
        catch(Exception e)
        {
            throw new RestException(e);
        }
        return values.toArray(new Object[values.size()]);
    }
    public ParameterDefinition[] getParameterDefinitions()
    {
        for(ResponseDefinition def : this.getMethod().definitionResponses)
        {
            if(def.getStatus()==status)
            {
                return def.getParameters();
            }
        }
        return null;
    }
    public URL getLink(ParameterDefinition definition) throws RestException
    {
        URL[] temp=getLinks(definition);
        if(temp!=null && temp.length>0)
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
                    String prefixXlink="xlink";

                    org.jdom.Element e = (org.jdom.Element) node;
                    Namespace nslink=Namespace.getNamespace(prefixXlink, RestPublish.XLINK_NS);
                    org.jdom.Attribute attjdom = e.getAttribute("href", nslink);
                    if (attjdom != null)
                    {
                        String value=attjdom.getValue();
                        if (value != null && !value.trim().equals(""))
                        {
                            URL href=RestPublish.resolve(value, this.url.toURI()).toURL();
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

    public void setMethod(Method method)
    {
        this.method=method;
    }

    public String getMediaType()
    {
        return APPLICATION_ATOM_XML;
    }
    public void addParameter(Parameter parameter)
    {
        this.parameters.add(parameter);
    }

    public Object getResponse() throws RestException
    {
        return document;
    }
    public void process(HttpURLConnection con) throws ExecutionRestException
    {
        try
        {
            InputStream in=con.getInputStream();
            document=SWBUtils.XML.xmlToDom(in);
            if(document==null)
            {
                throw new ExecutionRestException(HTTPMethod.POST, con.getURL(), "The document is invalid");
            }
            in.close();
        }
        catch(Exception e)
        {
            throw new ExecutionRestException(this.method.getHTTPMethod(), url, e);
        }
    }
}
