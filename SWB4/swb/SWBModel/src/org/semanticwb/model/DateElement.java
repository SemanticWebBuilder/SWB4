package org.semanticwb.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


public class DateElement extends org.semanticwb.model.base.DateElementBase 
{
    private static Logger log=SWBUtils.getLogger(DateElement.class);
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public DateElement(org.semanticwb.platform.SemanticObject base)
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
        String label = prop.getDisplayName(lang);
        SemanticObject sobj = prop.getDisplayProperty();
        boolean required = prop.isRequired();
        String pmsg = null;
        String imsg = null;
        boolean disabled=false;
        if (sobj != null) {
            DisplayProperty dobj = new DisplayProperty(sobj);
            pmsg = dobj.getPromptMessage();
            imsg = dobj.getInvalidMessage();
            disabled=dobj.isDisabled();
        }

        if(DOJO)
        {
            if (imsg == null) {
                if (required) {
                    imsg = label + " es requerido.";
                    if (lang.equals("en")) {
                        imsg = label + " is required.";
                    }
                }else
                {
                    imsg = "Formato invalido.";
                    if (lang.equals("en")) {
                        imsg = "Invalid Format.";
                    }
                }
            }

            if (pmsg == null) {
                pmsg = "Captura " + label + ".";
                if (lang.equals("en")) {
                    pmsg = "Enter " + label + ".";
                }
            }
        }

        String ext="";
        if(disabled)
        {
            ext+=" disabled=\"disabled\"";
        }

        String value=request.getParameter(prop.getName());
        if(value==null)
        {
            Date dt=obj.getDateProperty(prop);
            if(dt!=null)
            {
                value=format.format(dt);
            }
        }
        if (value == null) {
            value = "";
        }

        if (mode.equals("edit") || mode.equals("create"))
        {
            ret.append("<input name=\""+name+"\" value=\""+value+"\"");
            if(DOJO)ret.append(" dojoType=\"dijit.form.DateTextBox\"");
            if(DOJO)ret.append(" required=\""+required+"\"");
//            ret.append(" propercase=\"true\"");
            if(DOJO)ret.append(" promptMessage=\""+pmsg+"\"");
            //if(DOJO)ret.append(((getRegExp()!=null)?(" regExp=\""+getRegExp()+"\""):""));
            if(DOJO)ret.append(" invalidMessage=\""+imsg+"\"");
            if(DOJO && getConstraints()!=null)ret.append(" constraints=\""+getConstraints()+"\"");
            ret.append(" " + getAttributes());
            if(DOJO)ret.append(" trim=\"true\"");
            ret.append(ext);
            ret.append("/>");
        } else if (mode.equals("view")) {
            ret.append("<span name=\"" + name + "\">" + value + "</span>");
        }
        return ret.toString();
    }

    @Override
    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop)
    {
        //System.out.println("process...:"+obj.getURI()+" "+prop.getURI());
        if(prop.getDisplayProperty()==null)return;

        String value=request.getParameter(prop.getName());
        String old=obj.getProperty(prop);
        //System.out.println("com:"+old+"-"+value+"-");
        if(value!=null)
        {
            if(value.length()>0 && !value.equals(old))
            {
                //System.out.println("old:"+old+" value:"+value);
                try
                {
                    obj.setDateProperty(prop, format.parse(value));
                }catch(Exception e)
                {
                    log.error(e);
                }
            }else if(value.length()==0 && old!=null)
            {
                obj.removeProperty(prop);
            }
        }
    }

}
