/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;

import bsh.Interpreter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
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

    private static final String NL="\r\n";
    private final URL url;    
    
    public RestSource(URL url)
    {
        this.url=url;    
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
    public Document getDocumentService() throws RestException
    {
        String urlToGet=url.toString();        
        try
        {
            HttpURLConnection con=(HttpURLConnection)new URL(urlToGet).openConnection();
            if(con.getResponseCode()==200)
            {
                if(con.getHeaderField("Content-Type")!=null && con.getHeaderField("Content-Type").equalsIgnoreCase("application/xml"))
                {
                    InputStream in=con.getInputStream();
                    Document response=SWBUtils.XML.xmlToDom(in);
                    if(response==null)
                    {
                        throw new RestException("The document can not be loaded in url:"+url.toString());
                    }
                    return response;
                }
                else
                {
                    throw new RestException("The document is not a xml document: "+con.getHeaderField("Content-Type"));
                }
            }
            else
            {
                throw new RestException("The document can not be found error: "+con.getResponseCode());
            }
        }
        catch(IOException ioe)
        {
            throw new RestException(ioe);
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
        String name=element.getTagName();
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
    private String getCode(Element element)
    {
        StringBuilder sb=new StringBuilder();
        String className=element.getTagName();        
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
                String name=att.getName();
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
                String name=((Element)node).getTagName();
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
                sb.append("                 if(e.getTagName().equals(\""+name+"\"))");
                sb.append("                     elements.add(new "+name+"(e));"+NL);
                sb.append("             }"+NL);
                sb.append("         }"+NL);
                sb.append("         return elements;"+NL);
                sb.append("     }"+NL);


                sb.append("     public "+name+" $"+name+"()"+NL);
                sb.append("     {"+NL);                
                sb.append("         NodeList nodes=element.getChildNodes();"+NL);
                sb.append("         for(int i=0;i<nodes.getLength();i++)"+NL);
                sb.append("         {"+NL);
                sb.append("             Node node=nodes.item(i);"+NL);
                sb.append("             if(node instanceof Element)"+NL);
                sb.append("             {"+NL);
                sb.append("                 Element e=(Element)node;"+NL);
                sb.append("                 if(e.getTagName().equals(\""+name+"\"))");
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
    public ClassLoader getClassLoader() throws bsh.EvalError,RestException
    {
        Document response=getDocumentService();
        return getClassLoader(response);
    }
    private ClassLoader getClassLoader(Document response) throws bsh.EvalError,RestException
    {        
        MemoryClassLoader mcls=new MemoryClassLoader(RestSource.class.getClassLoader());
        HashMap<String,String> classes=getClasses(response);
        if(!classes.isEmpty())
        {
            mcls.addAll(classes);
        }
        return mcls;
    }
    public Object getResponse() throws bsh.EvalError,RestException
    {
        Document response=getDocumentService();
        return getResponse(response);
    }
    private Object getResponse(Document response) throws bsh.EvalError,RestException
    {        
        ClassLoader mcls=getClassLoader(response);
        String className=getRootName(response);
        try
        {
            Class clazz=mcls.loadClass(className);
            Constructor c=clazz.getConstructor(Element.class);
            Object obj=c.newInstance(response.getDocumentElement());
            return obj;
        }
        catch(ClassNotFoundException clnfe)
        {
            throw new RestException(clnfe);
        }
        catch(NoSuchMethodException clnfe)
        {
            throw new RestException(clnfe);
        }
        catch(InstantiationException clnfe)
        {
            throw new RestException(clnfe);
        }
        catch(IllegalAccessException clnfe)
        {
            throw new RestException(clnfe);
        }
        catch(InvocationTargetException clnfe)
        {
            throw new RestException(clnfe);
        }
    }
    public Interpreter getInterpreter() throws bsh.EvalError,RestException
    {
        Document response=getDocumentService();
        Interpreter i=new Interpreter();
        ClassLoader mcls=getClassLoader(response);
        String className=getRootName(response);        
        try
        {           
            i.setClassLoader(mcls);
            Object obj=getResponse();
            i.set(className, obj);
            return i;
        }
        catch(Exception e)
        {
            throw new RestException(e);
        }
    }
    public static void main(String[] args)
    {
        //String url="http://www.thomas-bayer.com/sqlrest/";
        String url="http://www.thomas-bayer.com/sqlrest/INVOICE/";

        try
        {
            
            RestSource source=new RestSource(new URL(url));            
            Interpreter i=source.getInterpreter();
            i.eval("System.out.println(INVOICEList.$INVOICE().toString());");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
