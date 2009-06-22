package org.semanticwb.model;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

public class Label extends org.semanticwb.model.base.LabelBase 
{
    public Label(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang)
    {
        if(obj==null)obj=new SemanticObject();
        boolean IPHONE=false;
        boolean XHTML=false;
        boolean DOJO=false;
        if(type.equals("iphone"))IPHONE=true;
        else if(type.equals("xhtml"))XHTML=true;
        else if(type.equals("dojo"))DOJO=true;

        StringBuffer ret=new StringBuffer();
        String name = prop.getName();

        String value=request.getParameter(prop.getName());
        if(value==null)value=obj.getProperty(prop);
        if (value == null) {
            value = "";
        }

        ret.append("<span name=\"" + name + "\">" + value + "</span>");

        return ret.toString();
    }

}
