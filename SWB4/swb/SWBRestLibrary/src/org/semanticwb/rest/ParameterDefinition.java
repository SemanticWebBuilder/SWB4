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

    private final String name;
    private final Class type;
    private final boolean isMultiple;
    private final ArrayList<ParameterDefinition> definitions=new ArrayList<ParameterDefinition>();
    private ParameterDefinition(String name,Class type,boolean isMultiple)
    {
        this.name=name;
        this.type=type;
        this.isMultiple=isMultiple;
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
    public static ParameterDefinition createParameterDefinition(Element element) throws RestException
    {
        String name=element.getAttribute("name");
        String type=element.getAttribute("type");
        String maxOccurs=element.getAttribute("maxOccurs");
        boolean multiple=false;
        if("unbounded".equals(maxOccurs))
        {
            multiple=true;
        }
        ParameterDefinition res=new ParameterDefinition(name, RestPublish.xsdToClass(type),multiple);
        if(element.getChildNodes().getLength()>0)
        {
            ResponseDefinition.extractDefinitions(element, res.definitions);
        }
        return res;
    }
}
