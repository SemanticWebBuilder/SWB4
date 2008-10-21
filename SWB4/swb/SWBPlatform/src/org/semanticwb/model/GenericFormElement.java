package org.semanticwb.model;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

public class GenericFormElement extends FormElementBase implements FormElement 
{
    public GenericFormElement()
    {
        super(null);
    }

    public GenericFormElement(SemanticObject obj)
    {
        super(obj);
    }
    
    @Override
    public String render(SemanticObject obj, SemanticProperty prop, String type, String mode, String lang)
    {
        String ret="";
        if(type.endsWith("iphone"))
        {
            ret=renderIphone(obj, prop, type, mode, lang);
        }else
        {
            ret=renderXHTML(obj, prop, type, mode, lang);
        }
        return ret;
    }    
    
    public String renderIphone(SemanticObject obj, SemanticProperty prop, String type, String mode, String lang)
    {
        return "";
    }
    
    public String renderXHTML(SemanticObject obj, SemanticProperty prop, String type, String mode, String lang)
    {
        String ret="";
        String name=prop.getName();
        String label=prop.getDisplayName(lang);
        if(prop.isDataTypeProperty())
        {
            String value=obj.getProperty(prop);
            if(value==null)value="";
            ret="<label for=\""+name+"\">"+label+" <em>*</em></label> <input id=\""+name+"\" name=\""+name+"\" value=\""+value+"\"/>";
        }        
        return ret;
    }

    @Override
    public void validate(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang) throws FormValidateException 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang) 
    {
        if(prop.isDataTypeProperty())
        {
            String value=request.getParameter(prop.getName());
            if(value!=null)
            {
                if(value.length()>0)
                {
                    if(prop.isBoolean())obj.setBooleanProperty(prop, Boolean.parseBoolean(value));
                    if(prop.isInt())obj.setLongProperty(prop, Integer.parseInt(value));
                    if(prop.isString())obj.setProperty(prop, value);
                }else
                {
                    obj.removeProperty(prop);
                }
            }
        }
    }

}
