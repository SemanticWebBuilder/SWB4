/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest.consume;

import org.w3c.dom.Element;

/**
 *
 * @author victor.lorenzana
 */
public class Parameter {
    private final Class type;
    private final boolean required;
    private final boolean fixed;
    private final String fixedvalue;
    private final String name;
    private final boolean repeating;
    private final String style;
    private Parameter(String name,String style,Class type,boolean required,boolean fixed,String fixedvalue,boolean repeating)
    {
        this.name=name;
        this.type=type;
        this.required=required;
        this.fixed=fixed;
        this.fixedvalue=fixedvalue;
        this.repeating=repeating;
        this.style=style;

    }
    private Parameter(String name,String style,Class type,boolean required)
    {
        this(name,style,type,required,false,null,false);
    }
    private Parameter(String name,String style,Class type)
    {
       this(name,style,type,false,false,null,false);

    }
    private Parameter(String name,String style)
    {
        this(name,style,String.class,false,false,null,false);

    }
    public String getFixedValue()
    {
        return fixedvalue;
    }
    static Parameter createParamterInfo(Element element) throws RestException
    {
        final String name=element.getAttribute("name");
        final String type=element.getAttribute("type");
        final String sfixed=element.getAttribute("fixed");
        final String srequired=element.getAttribute("required");
        final String srepeating=element.getAttribute("required");
        String style=element.getAttribute("style");

        if(style==null || "".equals(style))
        {
            style="query";
        }
        if(name==null)
        {
            throw new RestException("The name of paarameter is missing");
        }
        if(type==null)
        {
            throw new RestException("The type of parameter is missing");
        }
        boolean required=false;
        if(srequired!=null)
        {
            if(srequired.equals("true"))
            {
                required=true;
            }
        }
        boolean fixed=false;
        String fixedvalue=null;
        if(sfixed!=null && !sfixed.trim().equals(""))
        {            
            fixedvalue=sfixed;
            fixed=true;
        }
        boolean repeating=false;
        if(srepeating!=null && !srepeating.trim().equals(""))
        {
            if(srepeating.equals("true"))
            {
                repeating=true;
            }
        }
        Parameter p=new Parameter(name,style,RestPublish.xsdToClass(type),required, fixed, fixedvalue, repeating);
        return p;
    }
    
    public String getName()
    {
        return name;
    }
    public boolean isRequired()
    {
        return required;
    }
    public boolean isFixed()
    {
        return fixed;
    }
    public Class getType()
    {
        return type;
    }
    public String getStyle()
    {
        return style;
    }
}
