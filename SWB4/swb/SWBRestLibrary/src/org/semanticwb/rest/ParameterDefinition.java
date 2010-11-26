/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;

import java.util.ArrayList;
import org.w3c.dom.Element;

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
            getPath=getPath+"/"+name;
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
