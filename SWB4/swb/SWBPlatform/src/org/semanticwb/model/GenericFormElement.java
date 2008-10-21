package org.semanticwb.model;

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
            ret="<label>"+label+"</label><input type=\"text\" name=\""+name+"\" id=\""+label+"\" value=\""+value+"\"/>";
        }        
        return ret;
    }    
}
