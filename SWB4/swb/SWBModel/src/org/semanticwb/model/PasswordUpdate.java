package org.semanticwb.model;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

public class PasswordUpdate extends PasswordUpdateBase
{

    private static final String passphrase = "{MD5}tq5RXfs6DGIXD6dlHUgeQA==";

    public PasswordUpdate(SemanticObject base)
    {
        super(base);
    }

    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang)
    {
        if (obj == null)
        {
            obj = new SemanticObject();
        }
        String ret = "";
        if (type.endsWith("iphone"))
        {
            ret = renderIphone(request, obj, prop, type, mode, lang);
        } else
        {
            ret = renderXHTML(request, obj, prop, type, mode, lang);
        }
        return ret;
    }

    public String renderIphone(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang)
    {
        return "";
    }

    public String renderXHTML(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang)
    {
        String ret = "";
        String name = prop.getName();
        String label = prop.getDisplayName(lang);
        SemanticObject sobj = prop.getDisplayProperty();
        boolean required = prop.isRequired();
        String pmsg = null;
        String imsg = null;
        if (sobj != null)
        {
            DisplayProperty dobj = new DisplayProperty(sobj);
            pmsg = dobj.getPromptMessage();
            imsg = dobj.getInvalidMessage();
        }

        if (imsg == null)
        {
            if (required)
            {
                imsg = label + " es requerido.";
                if (lang.equals("en"))
                {
                    imsg = label + " is required.";
                }
            }
        }

        if (pmsg == null)
        {
            pmsg = "Captura " + label + ".";
            if (lang.equals("en"))
            {
                pmsg = "Enter " + label + ".";
            }
        }

        String value=request.getParameter(prop.getName());
        if(value==null)value=obj.getProperty(prop);
        if (value == null)
        {
            value = "";
        }
        if (mode.equals("edit") || mode.equals("create"))
        {
            ret = "<input name=\"" + name + "\" type=\"password\" " + " dojoType=\"dijit.form.ValidationTextBox\"" + " required=\"" + required + "\"" //                    + " propercase=\"true\""
                    + " promptMessage=\"" + pmsg + "\"" + " invalidMessage=\"" + imsg + "\"" + " trim=\"true\"" + " value=\"" + passphrase + "\"" + ">";
            ret += "<br/><input name=\"pwd_verify\" type=\"password\" " + " dojoType=\"dijit.form.ValidationTextBox\"" + " required=\"" + required + "\"" //                    + " propercase=\"true\""
                    + " promptMessage=\"" + pmsg + "\"" + " invalidMessage=\"" + imsg + "\"" + " trim=\"true\"" + " value=\"" + passphrase + "\"" + ">";
        } else if (mode.equals("view"))
        {
            ret = "<span _id=\"" + name + "\" name=\"" + name + "\">" + value + "</span>";
        }

        return ret;
    }

    @Override
    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang)
    {
        if (prop.isDataTypeProperty())
        {
            String value = request.getParameter(prop.getName());
            String chkvalue = request.getParameter("pwd_verify");
            String old = obj.getProperty(prop);
            if (value != null && value.length() > 0 && chkvalue != null && value.equals(chkvalue))
            {
                if (value.length() > 0 && !value.equals(old) && !value.equals(passphrase))
                {
                    if (prop.isString())
                    {
                        obj.setProperty(prop, value);
                    }
                } else if (value.length() == 0 && old != null)
                {
                    obj.removeProperty(prop);
                }
            } else
            {
                throw new FormRuntimeException("Passwords mistmatch or invalid");
            }
        }
    }
}
