/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;

import bsh.EvalError;
import bsh.Interpreter;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jdom.input.DOMBuilder;
import org.jdom.xpath.XPath;
import org.semanticwb.SWBUtils;
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
public final class ApplicationXML implements RepresentationResponse {

    private static final String NL="\r\n";
    private Document document;
    ApplicationXML(Document response)
    {
        this.document=response;
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
    public static void createClasses(Element element,HashMap<String,String> classes)
    {
        String name=toUpperCase(element.getTagName());
        NodeList nodes=element.getChildNodes();
        String code=getCode(element);        
        for(int i=0;i<nodes.getLength();i++)
        {
            Node node=nodes.item(i);
            if(node instanceof Element && !classes.containsKey(name))
            {
                createClasses((Element)node,classes);
            }
        }
        if(!classes.containsKey(name))
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

        Set<String> methods=new HashSet<String>();

        NamedNodeMap map=element.getAttributes();
        for(int i=0;i<map.getLength();i++)
        {
            Node node=map.item(i);
            if(node instanceof Attr)
            {
                Attr att=(Attr)node;
                String name=att.getName().toLowerCase();
                name=name.replace(':', '_');

                if(!methods.contains("get"+name))
                {
                    sb.append("     public String get"+name +"()"+NL);
                    sb.append("     {"+NL);
                    sb.append("         return element.getAttribute(\""+ att.getName() +"\");"+NL);
                    sb.append("     }"+NL);
                    methods.add("get"+name);
                }
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
                if(!methods.contains("list"+name))
                {
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
                    methods.add("list"+name);
                }
                if(!methods.contains("get"+name))
                {
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
                    methods.add("get"+name);
                }
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
    public Object getObject() throws bsh.EvalError,RestException
    {
        ClassLoader mcls=getClassLoader();
        String className=toUpperCase(getRootName(document));
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
    public static HashMap<String,String> getClasses(Document response)
    {
        HashMap<String,String> classes=new HashMap<String, String>();
        Element root=response.getDocumentElement();
        createClasses(root, classes);
        return classes;
    }
    public static String getRootName(Document document)
    {
        return document.getDocumentElement().getTagName();
    }
    public ClassLoader getClassLoader() throws bsh.EvalError
    {
        MemoryClassLoader mcls=new MemoryClassLoader(RestSource.class.getClassLoader());
        HashMap<String,String> classes=getClasses(document);
        if(!classes.isEmpty())
        {
            mcls.addAll(classes);
        }
        return mcls;
    }
    
    public Interpreter getInterpreter() throws bsh.EvalError,RestException
    {
        Interpreter i=new Interpreter();
        ClassLoader mcls=getClassLoader();
        String className=getRootName(document);
        className=toUpperCase(className);
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
            DOMBuilder builder=new DOMBuilder();
            System.out.println("definition.getPath(): "+definition.getPath());
            System.out.println("xml: "+SWBUtils.XML.domToXml(document));
            XPath xpath=XPath.newInstance(definition.getPath());
            Element root=definition.getMethod().getResource().getServiceInfo().getDocument().getDocumentElement();definition.getPath();
            for(int i=0;i<root.getAttributes().getLength();i++)
            {
                Node attnode=root.getAttributes().item(i);
                if(attnode instanceof Attr)
                {
                    Attr att=(Attr)attnode;
                    if("xmlns".equals(att.getPrefix()))
                    {
                        String prefix=att.getLocalName();
                        String ns=att.getValue();
                        xpath.addNamespace(prefix, ns);
                    }
                    if(att.getName().equals("xmlns"))
                    {
                        xpath.addNamespace("", att.getValue());
                    }
                }
            }
            
            List nodes=xpath.selectNodes(builder.build(document));
            for(int i=0;i<nodes.size();i++)
            {
                Object node=nodes.get(i);
                if(node instanceof org.jdom.Element)
                {
                    org.jdom.Element e=(org.jdom.Element)node;
                    List childs=e.getChildren();
                    for(int j=0;j<childs.size();j++)
                    {
                        if(childs.get(i) instanceof org.jdom.Text)
                        {
                            org.jdom.Text data=(org.jdom.Text)childs.get(j);
                            RestPublish.get(data.getText(),RestPublish.classToxsd(definition.getType()));
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
}
