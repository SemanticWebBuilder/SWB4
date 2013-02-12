package org.semanticwb.process.model;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


public class WikiUrlFormElement extends org.semanticwb.process.model.base.WikiUrlFormElementBase 
{
    public WikiUrlFormElement(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type,
                                String mode, String lang) {
        if (obj == null) {
            obj = new SemanticObject();
        }

        boolean DOJO   = false;

        if (type.equals("dojo")) {
            DOJO = true;
        }

        StringBuffer   ret      = new StringBuffer();
        String         name     = propName;
        String         label    = prop.getDisplayName(lang);
        SemanticObject sobj     = prop.getDisplayProperty();
        boolean        required = prop.isRequired();
        String         pmsg     = null;
        String         imsg     = null;
        boolean        disabled = false;

        if (sobj != null) {
            DisplayProperty dobj = new DisplayProperty(sobj);

            pmsg     = dobj.getPromptMessage();
            imsg     = dobj.getInvalidMessage();
            disabled = dobj.isDisabled();
        }
        
        if (DOJO) {
            if (imsg == null || (imsg != null && imsg.trim().length() == 0)) {
                if (required) {
                    imsg = label + " es requerido.";

                    if (lang.equals("en")) {
                        imsg = label + " is required.";
                    }
                } else {
                    imsg = label + " no es válido";
                    if (lang.equals("en")) {
                        imsg = label + " is not valid";
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

        String ext = "";

        if (disabled) {
            ext += " disabled=\"disabled\"";
        }

        String value = request.getParameter(propName);

        if (value == null) {
            value = obj.getProperty(prop);
        }

        if (value == null) {
            value = "";
        }

        value=value.replace("\"", "&quot;");

        if (mode.equals("edit") || mode.equals("create")) {
            ret.append("<input name=\"" + name + "\" size=\"30\" value=\"" + value + "\"");

            if (DOJO) {
                ret.append(" dojoType=\"dijit.form.ValidationTextBox\"");
            }

            if (DOJO) {
                ret.append(" required=\"" + required + "\"");
            }

            if (DOJO) {
                ret.append(" promptMessage=\"" + pmsg + "\"");
            }

            if (DOJO) {
                ret.append(((getRegExp() != null)
                            ? (" regExp=\"" + getRegExp() + "\"")
                            : ""));
            }

            if (DOJO) {
                ret.append(" invalidMessage=\"" + imsg + "\"");
            }

            ret.append(" " + getAttributes());

            if (DOJO) {
                ret.append(" trim=\"true\"");
            }

            ret.append(" style=\"width:300px;\"");
            ret.append(ext);
            ret.append("/>");

        } else if (mode.equals("view")) {
            String lbl = value;
            String url = value;
            if (value.startsWith("[")&& value.endsWith("]")) {
                String [] temp = value.replaceAll("\\[", "").replaceAll("\\]", "").split("\\|");
                if (temp != null && temp.length == 2) {
                    url = temp[0];
                    lbl = temp[1];
                }
            }
            ret.append("<a href=\"" + url + "\">" + lbl + "</a>");
        }
        return ret.toString();
    }
}