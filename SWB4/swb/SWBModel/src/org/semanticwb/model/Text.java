package org.semanticwb.model;

import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

public class Text extends TextBase 
{
    public Text(SemanticObject base)
    {
        super(base);
    }

    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang)
    {
        if (obj == null) {
            obj = new SemanticObject();
        }
        String ret = "";

        if (type.endsWith("iphone")) {
            ret = renderIphone(request, obj, prop, type, mode, lang);
        } else {
            ret = renderXHTML(request, obj, prop, type, mode, lang);
        }
        return ret;
    }

    public String renderIphone(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang) {
        return "";
    }

    public String renderXHTML(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang) {
        StringBuffer ret = new StringBuffer();
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

        if (imsg == null) {
            if (required) {
                imsg = label + " es requerido.";
                if (lang.equals("en")) {
                    imsg = label + " is required.";
                }
            }
        }

        if (pmsg == null) {
            pmsg = "Captura " + label + ".";
            if (lang.equals("en")) {
                pmsg = "Enter " + label + ".";
            }
        }

        String ext="";
        if(disabled)
        {
            ext+=" disabled=\"disabled\"";
        }

        String value=request.getParameter(prop.getName());
        if(value==null)value=obj.getProperty(prop);
        if (value == null) {
            value = "";
        }

        //value=SWBUtils.TEXT.encodeExtendedCharacters(value);
//        System.out.println("value:"+value);
//        for(int x=0;x<value.length();x++)
//        {
//            System.out.println(" "+(int)value.charAt(x));
//        }

        if (mode.equals("edit") || mode.equals("create"))
        {
            ret.append("<input name=\""+name+"\" size=\"30\" value=\""+value+"\"");
            ret.append(" dojoType=\"dijit.form.ValidationTextBox\"");
            ret.append(" required=\""+required+"\"");
//            ret.append(" propercase=\"true\"");
            ret.append(" promptMessage=\""+pmsg+"\"");
            ret.append(((getRegExp()!=null)?(" regExp=\""+getRegExp()+"\""):""));
            ret.append(" invalidMessage=\""+imsg+"\"");
            ret.append(" " + getAttributes());
            ret.append(" trim=\"true\"");
            ret.append(ext);
            ret.append("/>");

            if(!mode.equals("create") && prop.isLocaleable())
            {
                ret.append(" <a href=\"#\" onClick=\"javascript:showDialog('"+SWBPlatform.getContextPath()+"/swbadmin/jsp/propLocaleEdit.jsp?suri="+obj.getEncodedURI()+"&prop="+prop.getEncodedURI()+"','Idiomas de la Propiedad "+prop.getDisplayName(lang)+"');\">locale</a>");
            }
        } else if (mode.equals("view")) {
            ret.append("<span name=\"" + name + "\">" + value + "</span>");
        }
        return ret.toString();
    }

}
