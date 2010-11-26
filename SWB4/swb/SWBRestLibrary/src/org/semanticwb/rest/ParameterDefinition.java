/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;

import java.util.ArrayList;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 *
 * @author victor.lorenzana
 */
public class ParameterDefinition {

    private final String path;
    private final String name;
    
    private final Class type;
    private final boolean isMultiple;
    private final ArrayList<ParameterDefinition> definitions=new ArrayList<ParameterDefinition>();
    private final Method method;
    private ParameterDefinition(String name,Class type,boolean isMultiple,String path,Method method)
    {
        this.name=name;
        this.type=type;
        this.isMultiple=isMultiple;
        this.path=path;
        this.method=method;
    }
    public Method getMethod()
    {
        return method;
    }
    public String getPath()
    {
        return path;
    }
    public boolean isMultiple()
    {
        return isMultiple;
    }
    public ParameterDefinition[] getParameterDefinitions()
    {
        return definitions.toArray(new ParameterDefinition[definitions.size()]);
    }
    public String getName()
    {
        return name;
    }
    public Class getType()
    {
        return type;
    }
    private static String getPrefix(Document doc,Element schema)
    {
        String getPrefix="";
        String targetNamespace=schema.getAttribute("targetNamespace");
        Element element=doc.getDocumentElement();
        NamedNodeMap atts=element.getAttributes();
        for(int i=0;i<atts.getLength();i++)
        {
            if(atts.item(i) instanceof Attr)
            {
                Attr attr=(Attr)atts.item(i);
                if(attr.getPrefix()!=null && attr.getPrefix().equals("xmlns"))
                {
                    String prefix=attr.getLocalName();
                    String ns=attr.getValue();
                    if(targetNamespace.equals(ns))
                    {
                        return prefix;
                    }
                }
            }

        }
        return getPrefix;
    }
    private static String getPath(Element element,Element schema) throws RestException
    {
        String getPath="";
        if(element.getParentNode()!=null && element.getParentNode() instanceof Element)
        {
            Element parent=(Element)element.getParentNode();
            getPath=getPath+getPath(parent,schema);
        }
        String name=element.getAttribute("name");
        if(!name.trim().equals(""))
        {
            getPath=getPath+"/"+getPrefix(element.getOwnerDocument(),schema)+":"+name;
        }
        return getPath;
        
    }
    public static ParameterDefinition createParameterDefinition(Element element,Method method,Element schema) throws RestException
    {
        String name=element.getAttribute("name");
        String type=element.getAttribute("type");
        String maxOccurs=element.getAttribute("maxOccurs");
        boolean multiple=false;
        if("unbounded".equals(maxOccurs))
        {
            multiple=true;
        }
        String path=getPath(element,schema);
        ParameterDefinition res=new ParameterDefinition(name, RestPublish.xsdToClass(type),multiple,path,method);
        if(element.getChildNodes().getLength()>0)
        {
            ResponseDefinition.extractDefinitions(element, res.definitions,method,schema);
        }
        return res;
    }
}
