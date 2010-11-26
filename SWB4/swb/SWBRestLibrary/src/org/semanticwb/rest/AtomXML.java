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
import org.jdom.xpath.XPath;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 *
 * @author victor.lorenzana
 */
public class AtomXML extends RepresentationBase implements RepresentationRequest,RepresentationResponse
{
    private static final Logger log = SWBUtils.getLogger(AtomXML.class);
    private Document document;
    public static final String ATOM_NS = "http://www.w3.org/2005/Atom";
    private final int status;
    public AtomXML(Method method,int status)
    {
        super("application/atom+xml", method);
        this.status=status;

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
            con.setRequestProperty(CONTENT_TYPE + "; charset=" + charset, this.mediaType);
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStream out = con.getOutputStream();
            Document requestAtomDocument = constructParameters(values);
            String xml = SWBUtils.XML.domToXml(requestAtomDocument, charset, true);
            out.write(xml.getBytes());
            out.close();
            if (con.getResponseCode() == 200)
            {
                if(con.getHeaderField(CONTENT_TYPE)!=null && (con.getHeaderField(CONTENT_TYPE).equalsIgnoreCase(APPLICATION_XML) || con.getHeaderField(CONTENT_TYPE).equalsIgnoreCase(TEXT_XML)))
                {
                    InputStream in = con.getInputStream();
                    Document response = SWBUtils.XML.xmlToDom(in);
                    if (response == null)
                    {
                        throw new RestException("The content of the url is invalid");
                    }
                    ApplicationXML resp = new ApplicationXML(response,this.getMethod(),con.getResponseCode());
                    return resp;
                }
                if (con.getHeaderField(CONTENT_TYPE) != null && con.getHeaderField(CONTENT_TYPE).equalsIgnoreCase(ATOM_NS))
                {
                    InputStream in = con.getInputStream();
                    Document response = SWBUtils.XML.xmlToDom(in);
                    if (response == null)
                    {
                        throw new RestException("The content of the url is invalid");
                    }
                    AtomXML resp = new AtomXML(this.method,con.getResponseCode());
                    resp.document=response;
                    return resp;
                }
                else
                {
                    throw new RestException("The response has a not valid Content-Type header: " + con.getHeaderField(CONTENT_TYPE) + "(only " + JSON_CONTENT_TYPE + "," + APPLICATION_XML + " are valid)");
                }
            }
            else
            {
                throw new RestException("The document was not found error code " + con.getResponseCode());
            }
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

    public Object getObject() throws EvalError, RestException
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

    public Object getValue(ParameterDefinition definition) throws EvalError, RestException
    {
         Object[] values=getValues(definition);
         if(values.length>0)
         {
             return values[0];
         }
         return null;
    }

    public Object[] getValues(ParameterDefinition definition) throws EvalError, RestException
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
        for(ResponseDefinition def : this.getMethod().responses)
        {
            if(def.getStatus()==status)
            {
                return def.getParameters();
            }
        }
        return null;
    }
}
