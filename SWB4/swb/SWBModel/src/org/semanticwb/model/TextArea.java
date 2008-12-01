package org.semanticwb.model;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

public class TextArea extends TextAreaBase 
{
    public TextArea(SemanticObject base)
    {
        super(base);
    }

    @Override
    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang)
    {
        String value=request.getParameter(prop.getName());
        if(value!=null)
        {
            if(value.length()>0)
            {
                obj.setProperty(prop, value);
            }else
            {
                obj.removeProperty(prop);
            }
        }
    }

    @Override
    public String render(SemanticObject obj, SemanticProperty prop, String type, String mode, String lang) {
        if(obj==null)obj=new SemanticObject();
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
        SemanticObject sobj=prop.getDisplayProperty();
        boolean required=false;
        String pmsg=null;
        String imsg=null;
        if(sobj!=null)
        {
            DisplayProperty dobj=new DisplayProperty(sobj);
            required=dobj.isRequired();
            pmsg=dobj.getPromptMessage();
            imsg=dobj.getInvalidMessage();
        }

        if(imsg==null)
        {
            if(required)
            {
                imsg=label+" es requerido.";
                if(lang.equals("en"))
                {
                    imsg=label+" is required.";
                }
            }
        }

        if(pmsg==null)
        {
            pmsg="Captura "+label+".";
            if(lang.equals("en"))
            {
                pmsg="Enter "+label+".";
            }
        }

        String reqtxt=" &nbsp;";
        if(required)reqtxt=" <em>*</em>";

        String value=obj.getProperty(prop);
        if(value==null)value="";
        if(mode.equals("edit") || mode.equals("create") )
        {
            ret="<label for=\""+name+"\">"+label
                + reqtxt
                + "</label> <textarea name=\""+name+"\" dojoType_=\"dijit.Editor\" rows=\""+getRows()+"\" cols=\""+getCols()+"\">"
                + value
                + "</textarea>";
        }else if(mode.equals("view"))
        {
            ret="<label for=\""+name+"\">"+label
                + reqtxt
                + "</label>"
                + " <span _id=\""+name+"\" name=\""+name+"\">"+value+"</span>";
        }

        return ret;
    }


    @Override
    public void validate(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang) throws FormValidateException {
        super.validate(request, obj, prop, type, mode, lang);
    }


}
