package org.semanticwb.bsc.formelement;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


public class DateElement extends org.semanticwb.bsc.formelement.base.DateElementBase 
{
    public DateElement(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
      @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type,
                                String mode, String lang) {
        if (obj == null) {
            obj = new SemanticObject();
        }

        // boolean IPHONE=false;
        // boolean XHTML=false;
        boolean DOJO = false;

        // if(type.equals("iphone"))IPHONE=true;
        // else if(type.equals("xhtml"))XHTML=true;
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
            if (imsg == null) {
                if (required) {
                    imsg = label + " es requerido.";

                    if (lang.equals("en")) {
                        imsg = label + " is required.";
                    }
                } else {
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

        String ext = "";

        if (disabled) {
            ext += " disabled=\"disabled\"";
        }

        String value = request.getParameter(propName);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (value == null) {
            Date dt = obj.getDateProperty(prop);

            if (dt != null) {
                value = format.format(dt);
            }
        }

        if (value == null) {
            value = "";
        }

        if (mode.equals("edit") || mode.equals("create")) {
            ret.append("<input name=\"" + name + "\" value=\"" + value + "\"");

            if (DOJO) {
                ret.append(" dojoType=\"dijit.form.DateTextBox\"");
                ret.append(" required=\"" + required + "\"");
                ret.append(" promptMessage=\"" + pmsg + "\"");
                ret.append(" invalidMessage=\"" + imsg + "\"");

                if (getConstraints() != null) {
                    ret.append(" constraints=\"" + getConstraints() + "\"");
                }
               
                if(getDateId()!=null) ret.append(" id=\"" + getDateId() + obj.getId() + "\"");
                
                if (getDateOnChange() != null) {
                    String attributeValue = getDateOnChange();
                    ret.append(" onchange=\"");
                    ret.append((attributeValue.indexOf("{replaceId}") != -1 
                                ? attributeValue.replace("{replaceId}", obj.getId())
                                : attributeValue));
                    ret.append("\"");
                }
            }

            ret.append(" " + getAttributes());

            if (DOJO) {
                ret.append(" trim=\"true\"");
            }

            ret.append(ext);
            ret.append("/>");
        } else if (mode.equals("view")) {
            ret.append("<span name=\"" + name + "\">" + value + "</span>");
        }

        return ret.toString();
    }
}
