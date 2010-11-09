/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;

import bsh.Interpreter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import org.semanticwb.SWBUtils;
import org.semanticwb.rest.util.MemoryClassLoader;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public class RestSource {
    private static final String APPLICATION_XML = "application/xml";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String JSON_CONTENT_TYPE = "json";
    private static final String NL="\r\n";
    private final URL url;        
    public RestSource(URL url)
    {
        if(url==null)
        {
            throw new NullPointerException("the url is null");
        }
        this.url=url;
        if(!(url.getProtocol().toLowerCase().startsWith("http") || url.getProtocol().toLowerCase().startsWith("https")))
        {
            throw new IllegalArgumentException("The protocol "+url.getProtocol()+" is not suported (only http or https is supported)");
        }
    }
    public URL getUrl()
    {
        try
        {
            return new URL(url.toString());
        }
        catch(Exception e)
        {
            throw new IllegalStateException(e);
        }
    }
    public Document execute(HTTPMethod method) throws ExecutionRestException
    {
        return execute(null,method, null);
    }
    public Document execute(HTTPMethod method,Map<String,String> parameters) throws ExecutionRestException
    {
        return execute(null, method, parameters);
    }
    public Document execute(Document document,HTTPMethod method,Map<String,String> parameters) throws ExecutionRestException
    {
        switch(method)
        {
            case GET:
                return executeGET(parameters);                
            case PUT:
                /*if(document==null)
                {
                    throw new IllegalArgumentException("The document to "+ method +" is null");
                }*/
                executePUT(document,parameters);
                break;
            case POST:
                /*if(document==null)
                {
                    throw new IllegalArgumentException("The document to "+method +" is null");
                }*/
                executePOST(document,parameters);
                break;
             case DELETE:
                executeDELETE(parameters);
                break;
        }
        return null;
    }
    public void executePOST(Document document) throws ExecutionRestException
    {
        executePOST(document, null);
    }
    
    private void executePOSTOrPUT(HTTPMethod method,Document document,Map<String,String> parameters) throws ExecutionRestException
    {
        String urlToGet=convertToURLWithParameters(url, parameters);
        try
        {
            HttpURLConnection con=(HttpURLConnection)new URL(urlToGet).openConnection();
            con.setRequestMethod(method.toString());
            Charset charset=Charset.forName("utf-8");
            con.setRequestProperty(CONTENT_TYPE+", charset="+charset.name(), APPLICATION_XML);
            if(document!=null)
            {
                con.setDoOutput(true);
                OutputStream out=con.getOutputStream();                
                out.write(SWBUtils.XML.domToXml(document).getBytes(charset));
                out.close();
            }
            if(con.getResponseCode()!=200)
            {                
                throw new ServerSideExecutionRestException(method,url,con);
            }
            con.disconnect();
        }
        catch(IOException ioe)
        {
            throw new ExecutionRestException(method,url,ioe);
        }
    }
    public void executePOST(Document document,Map<String,String> parameters) throws ExecutionRestException
    {
        executePOSTOrPUT(HTTPMethod.POST, document, parameters);
    }

    public void executePUT(Document document) throws ExecutionRestException
    {
        executePUT(document, null);
    }
    private static String convertToURLWithParameters(URL url,Map<String,String> parameters)
    {
        String urlToGet=url.toString();
        if(parameters!=null)
        {
            int pos=urlToGet.indexOf("?");
            if(pos!=-1)
            {
                urlToGet=urlToGet.substring(0,pos);
            }
            urlToGet=urlToGet+"?";
            for(String key : parameters.keySet())
            {

                String data=URLEncoder.encode(key)+"&"+URLEncoder.encode(parameters.get(key));
                urlToGet=urlToGet+data;
            }
        }
        return urlToGet;
    }
    public void executePUT(Document document,Map<String,String> parameters) throws ExecutionRestException
    {
        executePOSTOrPUT(HTTPMethod.PUT, document, parameters);
    }

    public void executeDELETE() throws ExecutionRestException
    {
        executeDELETE(null);
    }
    public void executeDELETE(Map<String,String> parameters) throws ExecutionRestException
    {
        String urlToGet=convertToURLWithParameters(url, parameters);
        try
        {
            HttpURLConnection con=(HttpURLConnection)new URL(urlToGet).openConnection();
            if(con.getResponseCode()!=200)
            {                
                throw new ServerSideExecutionRestException(HTTPMethod.DELETE,url,con);
            }            
        }
        catch(IOException ioe)
        {
            throw new ExecutionRestException(HTTPMethod.DELETE,url,ioe);
        }
    }
    public Document executeGET() throws ExecutionRestException
    {
        return this.executeGET(null);
    }
    public Document executeGET(Map<String,String> parameters) throws ExecutionRestException
    {
        String urlToGet=convertToURLWithParameters(url, parameters);
        try
        {
            HttpURLConnection con=(HttpURLConnection)new URL(urlToGet).openConnection();
            if(con.getResponseCode()==200)
            {
                if(con.getHeaderField(CONTENT_TYPE)!=null && con.getHeaderField(CONTENT_TYPE).equalsIgnoreCase(APPLICATION_XML))
                {
                    InputStream in=con.getInputStream();
                    Document response=SWBUtils.XML.xmlToDom(in);
                    if(response==null)
                    {
                        throw new ExecutionRestException(HTTPMethod.GET,url,"The document response can not be loaded");
                    }
                    return response;
                }
                if(con.getHeaderField(CONTENT_TYPE)!=null && con.getHeaderField(CONTENT_TYPE).equalsIgnoreCase(JSON_CONTENT_TYPE))
                {
                    StringBuilder sb=new StringBuilder();
                    InputStream in=con.getInputStream();
                    byte[] buffer=new byte[8*1024];
                    int read=in.read(buffer);
                    while(read!=-1)
                    {
                        sb.append(new String(buffer,0,read));
                        read=in.read(buffer);
                    }                    
                    // TODO:
                    throw new ExecutionRestException(HTTPMethod.GET,url, "At this moment the library can not support rest services with json response");

                }
                else
                {
                    throw new ExecutionRestException(HTTPMethod.GET,url,"The response has a not valid Content-Type header: "+con.getHeaderField(CONTENT_TYPE)+"(only "+JSON_CONTENT_TYPE+","+APPLICATION_XML+" are valid)");
                }
            }
            else
            {                
                throw new ServerSideExecutionRestException(HTTPMethod.GET,url,con);
            }
        }
        catch(IOException ioe)
        {
            throw new ExecutionRestException(HTTPMethod.GET,url,ioe);
        }
        
        
    }
    private HashMap<String,String> getClasses(Document response)
    {
        HashMap<String,String> classes=new HashMap<String, String>();
        Element root=response.getDocumentElement();
        createClasses(root, classes);
        return classes;
    }
    private String getRootName(Document document)
    {
        return document.getDocumentElement().getTagName();
    }
    private void createClasses(Element element,HashMap<String,String> classes)
    {        
        String name=toUpperCase(element.getTagName());
        NodeList nodes=element.getChildNodes();
        String code=getCode(element);
        System.out.println(code);
        for(int i=0;i<nodes.getLength();i++)
        {
            Node node=nodes.item(i);
            if(node instanceof Element)
            {
                createClasses((Element)node,classes);
            }
        }
        classes.put(name, code);
    }
    private static String toUpperCase(String data)
    {
        String letter = data.substring(0, 1);
        return letter.toUpperCase() + data.substring(1).toLowerCase();
    }
    private String getCode(Element element)
    {
        StringBuilder sb=new StringBuilder();
        String className=toUpperCase(element.getTagName());
        sb.append("import java.util.ArrayList;"+NL);
        sb.append("import java.util.List;"+NL);
        sb.append("import org.w3c.dom.Element;"+NL);
        sb.append("import org.w3c.dom.Node;"+NL);
        sb.append("import org.w3c.dom.NodeList;"+NL);

        sb.append("public class "+className +" {"+NL);
        sb.append("     private final Element element;"+NL);
        sb.append("     public "+className+"(Element element)"+NL);
        sb.append("     {"+NL);
        sb.append("         this.element=element;"+NL);
        sb.append("     }"+NL);

        NamedNodeMap map=element.getAttributes();
        for(int i=0;i<map.getLength();i++)
        {
            Node node=map.item(i);
            if(node instanceof Attr)
            {
                Attr att=(Attr)node;
                String name=att.getName().toLowerCase();
                name=name.replace(':', '_');
                sb.append("     public String get"+name +"()"+NL);
                sb.append("     {"+NL);
                sb.append("         return element.getAttribute(\""+ att.getName() +"\");"+NL);
                sb.append("     }"+NL);
            }            
        }
        NodeList childs=element.getChildNodes();
        for(int i=0;i<childs.getLength();i++)
        {
            Node node=childs.item(i);
            if(node instanceof Element)
            {
                String elementName=((Element)node).getTagName();
                String name=toUpperCase(elementName);
                
                sb.append("     public List<"+name+"> list"+name+"()"+NL);
                sb.append("     {"+NL);
                sb.append("         ArrayList<"+name+"> elements=new ArrayList<"+name+">();"+NL);
                sb.append("         NodeList nodes=element.getChildNodes();"+NL);
                sb.append("         for(int i=0;i<nodes.getLength();i++)"+NL);
                sb.append("         {"+NL);
                sb.append("             Node node=nodes.item(i);"+NL);
                sb.append("             if(node instanceof Element)"+NL);
                sb.append("             {"+NL);
                sb.append("                 Element e=(Element)node;"+NL);
                sb.append("                 if(e.getTagName().equals(\""+elementName+"\"))"+NL);
                sb.append("                     elements.add(new "+name+"(e));"+NL);
                sb.append("             }"+NL);
                sb.append("         }"+NL);
                sb.append("         return elements;"+NL);
                sb.append("     }"+NL);


                sb.append("     public "+name+" get"+name+"()"+NL);
                sb.append("     {"+NL);                
                sb.append("         NodeList nodes=element.getChildNodes();"+NL);
                sb.append("         for(int i=0;i<nodes.getLength();i++)"+NL);
                sb.append("         {"+NL);
                sb.append("             Node node=nodes.item(i);"+NL);
                sb.append("             if(node instanceof Element)"+NL);
                sb.append("             {"+NL);
                sb.append("                 Element e=(Element)node;"+NL);
                sb.append("                 if(e.getTagName().equals(\""+elementName+"\"))"+NL);
                sb.append("                     return new "+name+"(e);"+NL);
                sb.append("             }"+NL);
                sb.append("         }"+NL);
                sb.append("         return null;"+NL);
                sb.append("     }"+NL);
            }
        }



        sb.append("     @Override"+NL);
        sb.append("     public String toString()"+NL);
        sb.append("     {"+NL);
        sb.append("         return element.getTextContent();"+NL);
        sb.append("     }"+NL);

        sb.append("}"+NL);        
        return sb.toString();
    }
    public ClassLoader getClassLoaderOfGET() throws bsh.EvalError,ExecutionRestException
    {
        Document response=executeGET();
        return getClassLoaderOfGET(response);
    }
    private ClassLoader getClassLoaderOfGET(Document response) throws bsh.EvalError,ExecutionRestException
    {        
        MemoryClassLoader mcls=new MemoryClassLoader(RestSource.class.getClassLoader());
        HashMap<String,String> classes=getClasses(response);
        if(!classes.isEmpty())
        {
            mcls.addAll(classes);
        }
        return mcls;
    }
    public Object getObjectOfGET() throws bsh.EvalError,ExecutionRestException
    {
        return getObjectOfGET((Map<String,String>)null);

    }
    public Object getObjectOfGET(Map<String,String> parameters) throws bsh.EvalError,ExecutionRestException
    {
        Document response=executeGET();
        return getObjectOfGET(response);
    }
    private Object getObjectOfGET(Document response) throws bsh.EvalError,ExecutionRestException
    {        
        ClassLoader mcls=getClassLoaderOfGET(response);
        String className=toUpperCase(getRootName(response));
        try
        {
            Class clazz=mcls.loadClass(className);
            Constructor c=clazz.getConstructor(Element.class);
            Object obj=c.newInstance(response.getDocumentElement());
            return obj;
        }
        catch(Exception clnfe)
        {
            throw new ExecutionRestException(HTTPMethod.GET,url,"Error creating a object response",clnfe);
        }        
    }
    public Interpreter getInterpreterOfGET() throws bsh.EvalError,ExecutionRestException
    {
        return this.getInterpreterOfGET(null);
    }
    public Interpreter getInterpreterOfGET(Map<String,String> parameters) throws bsh.EvalError,ExecutionRestException
    {
        Document response=executeGET();
        Interpreter i=new Interpreter();
        ClassLoader mcls=getClassLoaderOfGET(response);
        String className=getRootName(response);
        className=toUpperCase(className);
        try
        {           
            i.setClassLoader(mcls);
            Object obj=getObjectOfGET();
            i.set(className.toLowerCase(), obj);
            return i;
        }
        catch(Exception e)
        {
            throw new ExecutionRestException(HTTPMethod.GET,url,e);
        }
    }
    public static void main(String[] args)
    {
        //String url="http://www.thomas-bayer.com/sqlrest/";
        String url="http://www.thomas-bayer.com/sqlrest/INVOICE/";
        //String url="https://api.del.icio.us/v1/posts/get";
        //http://www.thomas-bayer.com/sqlrest/CUSTOMER/18/

        try
        { 
            RestSource source=new RestSource(new URL(url));
            Document doc=source.executeGET();
            System.out.println(SWBUtils.XML.domToXml(doc));
            //Interpreter i=source.getInterpreterOfGET();
            //i.eval("System.out.println(invoicelist.getInvoice().toString());");
        }        
        catch(ServerSideExecutionRestException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
