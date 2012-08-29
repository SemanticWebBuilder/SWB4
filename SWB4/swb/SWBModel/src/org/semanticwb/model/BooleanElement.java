package org.semanticwb.model;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
   /**
   * Elemento para valores booleanos configurable como CheckBox, Radio y Select
   * @author Hasdai Pacheco {ebenezer.sanchez@infotec.com.mx}
   */
public class BooleanElement extends org.semanticwb.model.base.BooleanElementBase 
{
    public BooleanElement(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type, String mode, String lang) {
        if (obj == null) {
            obj = new SemanticObject();
        }

        boolean DOJO   = false;

        if (type.equals("dojo")) {
            DOJO = true;
        }

        StringBuilder   ret         = new StringBuilder();
        String         name         = propName;
        String         label        = prop.getDisplayName(lang);
        SemanticObject sobj         = prop.getDisplayProperty();
        boolean        required     = prop.isRequired();
        String         pmsg         = null;
        String         imsg         = null;
        boolean        isDisabled   = false;
        String trueTitle            = getDisplayTrueTitle(lang);
        String falseTitle           = getDisplayFalseTitle(lang);
        String displayType          = getDisplayType();

        if (sobj != null) {
            DisplayProperty dobj = new DisplayProperty(sobj);
            pmsg         = dobj.getPromptMessage();
            imsg         = dobj.getInvalidMessage();
            isDisabled   = dobj.isDisabled();
        }

        String disabled = "";

        if (DOJO) {
            if (imsg == null) {
                if (required) {
                    imsg = label + " es requerido.";

                    if (lang.equals("en")) {
                        imsg = label + " is required.";
                    }
                } else {
                    imsg = "Dato invalido.";

                    if (lang.equals("en")) {
                        imsg = "Invalid data.";
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

        if (isDisabled) {
            disabled += " disabled=\"disabled\"";
        }

        if (prop.isDataTypeProperty() && prop.isBoolean()) {
            if (prop.isBoolean()) {
                String  checked = "";
                boolean value   = false;
                String  aux     = request.getParameter(propName);

                if (aux != null) {
                    value = true;
                    if (aux.equals("false")) {
                        value = false;
                    }
                } else {
                    value = obj.getBooleanProperty(prop);
                }

                if (value) {
                    checked = "checked=\"checked\"";
                }

                if (displayType.equals("checkbox")) {
                    ret.append("<input type=\"checkbox\" id_=\"").append(name).append("\" name=\"").append(name).append("\" ").append(checked);
                    if (DOJO) {
                        ret.append(" dojoType=\"dijit.form.CheckBox\"");
                        ret.append(" required=\"").append(required).append("\"");
                        ret.append(" promptMessage=\"").append(pmsg).append("\"");
                        ret.append(" invalidMessage=\"").append(imsg).append("\"");
                    }
                    ret.append(disabled);
                    if (mode.equals("view")) {
                        ret.append(" disabled=\"disabled\"");
                    }
                    ret.append("/>");
                } else if (displayType.equals("select")) {
                    ret.append("<select id_=\"").append(name).append("\" name=\"").append(name).append("\" ");
                    if (DOJO) {
                        ret.append(" dojoType=\"dijit.form.FilteringSelect\"");
                        ret.append(" required=\"").append(required).append("\"");
                        ret.append(" promptMessage=\"").append(pmsg).append("\"");
                        ret.append(" invalidMessage=\"").append(imsg).append("\"");
                    }
                    ret.append(disabled);
                    if (mode.equals("view")) {
                        ret.append(" disabled=\"disabled\"");
                    }
                    ret.append("/>");
                    ret.append("<option value=\"true\"").append(value?"selected":"").append(" >").append(trueTitle).append("</option>");
                    ret.append("<option value=\"false\"").append(!value?"selected":"").append(" >").append(falseTitle).append("</option>");
                    ret.append("</select>");
                } else if (displayType.equals("radio")) {
                    ret.append("<input type=\"radio\" id_=\"").append(name).append("\" id=\"").append(name).append("_True\" name=\"").append(name).append("\" ").append(value?"checked=\"checked\"":"").append(" value=\"true\"");
                    if (DOJO) {
                        ret.append(" dojoType=\"dijit.form.RadioButton\"");
                        ret.append(" required=\"").append(required).append("\"");
                        ret.append(" promptMessage=\"").append(pmsg).append("\"");
                        ret.append(" invalidMessage=\"").append(imsg).append("\"");
                    }
                    ret.append(disabled);
                    if (mode.equals("view")) {
                        ret.append(" disabled=\"disabled\"");
                    }
                    ret.append("/>");
                    ret.append("<label for=\"").append(name).append("_True\">").append(trueTitle).append("</label> ");
                    
                    ret.append("<input type=\"radio\" id_=\"").append(name).append("\" id=\"").append(name).append("_False\" name=\"").append(name).append("\" ").append(!value?"checked=\"checked\"":"").append(" value=\"false\"");
                    if (DOJO) {
                        ret.append(" dojoType=\"dijit.form.RadioButton\"");
                        ret.append(" required=\"").append(required).append("\"");
                        ret.append(" promptMessage=\"").append(pmsg).append("\"");
                        ret.append(" invalidMessage=\"").append(imsg).append("\"");
                    }
                    ret.append(disabled);
                    if (mode.equals("view")) {
                        ret.append(" disabled=\"disabled\"");
                    }
                    ret.append("/>");
                    ret.append("<label for=\"").append(name).append("_False\">").append(falseTitle).append("</label>");
                }
            } else {
                String value = request.getParameter(propName);

                if (value == null) {
                    value = obj.getProperty(prop);
                }

                if (value == null) {
                    value = "";
                }

                //System.out.print(value);
                value=value.replace("\"", "&quot;");
                //System.out.println(" "+value);

                if (mode.equals("edit") || mode.equals("create") || mode.equals("filter")) {
                    ret.append("<input _id=\"").append(name).append("\" name=\"").append(name).append("\" value=\"").append(value + "\"");

                    if (DOJO) {
                        ret.append(" dojoType=\"dijit.form.ValidationTextBox\"");
                    }

                    if (!mode.equals("filter") || DOJO) {
                        ret.append(" required=\"").append(required).append("\"");
                    }

                    // + " propercase=\"true\""
                    if (DOJO) {
                        ret.append(" promptMessage=\"").append(pmsg).append("\"");
                    }

                    if (DOJO) {
                        ret.append(" invalidMessage=\"").append(imsg).append("\"");
                    }

                    ret.append(" style=\"width:300px;\"");
                    ret.append(" ").append(getAttributes());

                    if (DOJO) {
                        ret.append(" trim=\"true\"");
                    }

                    ret.append(disabled);
                    ret.append("/>");
                } else if (mode.equals("view")) {
                    ret.append("<span _id=\"").append(name).append("\" name=\"").append(name).append("\">").append(value).append("</span>");
                }
            }
        }

        // System.out.println("ret:"+ret);
        return ret.toString();
    }
}
