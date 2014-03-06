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

        boolean dojo = type.equals("dojo");

        StringBuilder ret = new StringBuilder(128);
        String name = propName;
        String label = prop.getDisplayName(lang);
        SemanticObject sobj = prop.getDisplayProperty();
        boolean required = prop.isRequired();
        String pmsg = null;
        String imsg = null;
        boolean disabled = false;
        Date dt = null;

        if (sobj != null) {
            DisplayProperty dobj = new DisplayProperty(sobj);
            pmsg = dobj.getPromptMessage();
            imsg = dobj.getInvalidMessage();
            disabled = dobj.isDisabled();
        }

        if (dojo) {
            if (imsg == null) {
                if (required) {
                    imsg = label + " es requerido.";

                    if (lang.equals("en")) {
                        imsg = label + " is required.";
                    }
                } else {
                    imsg = "Formato incorrecto.";

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

        String ext = disabled?" disabled=\"disabled\"":"";
        
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String value = request.getParameter(propName);
        if (value == null) {
            dt = obj.getDateProperty(prop);
            if (dt != null) {
                value = format.format(dt);
            }
        }
        if (value == null) {
            value = "";
        }
        
//        if (type.equals("dojo")) {
//            setAttribute("isValid",
//                    "return validateElement('" + propName + "','" + getValidateURL(obj, prop)
//                    + "',this.textbox.value);");
//        } else {
//            setAttribute("isValid", null);
//        }

        if (mode.equals("edit") || mode.equals("create")) {
            ret.append("<input name=\"" + name + "\" value=\"" + value + "\"");

            if (dojo) {
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
            if (dojo) {
                ret.append(" trim=\"true\"");
            }

            ret.append(ext);
            ret.append("/>");
        } else if (mode.equals("view")) {
            ret.append("<span name=\"" + name + "\">" + value + "</span>");
        } else if (mode.equals("inlineEdit")) {
            /*Al utilizar este modo, se debe incluir en el HTML las instrucciones 
             * dojo.require del InlineEditBox y dijit.form.DateTextBox, al menos*/
            StringBuilder viewString = new StringBuilder(128);
            String objectId = getDateId() + obj.getId();
            String url = (String) request.getAttribute("urlRequest");
            
            String significantOther = null;
            String attributeValue = null;
            boolean handleMaximum = false;
            
            //se obtiene identificador del otro control con el que se complementa el periodo en significantOther
            if (getDateOnChange() != null) {
                String delimiter = "byId('";
                attributeValue = getDateOnChange();
                if (attributeValue.indexOf("constraints.max") != -1) {
                    handleMaximum = true;
                }
                if (attributeValue.indexOf(delimiter) != -1) {
                    attributeValue = attributeValue.substring(attributeValue.indexOf(delimiter) + delimiter.length());
                }
                if (attributeValue.indexOf("').constraints.") != -1) {
                    significantOther = attributeValue.substring(0, attributeValue.indexOf("').constraints."));
                } else {
                    significantOther = attributeValue;
                }
                significantOther = (significantOther.indexOf("{replaceId}") != -1 
                            ? significantOther.replace("{replaceId}", obj.getId())
                            : significantOther);
            }
            
            viewString.append("<script type=\"text/javascript\">\n");
            viewString.append("  <!--\n");
            viewString.append("    var iledit_");
            viewString.append(objectId);
            viewString.append(";\n");
            
            viewString.append("    dojo.addOnLoad( function() {\n");
            viewString.append("      iledit_");
            viewString.append(objectId);
            viewString.append(" = new dijit.InlineEditBox({\n");
            viewString.append("        id: \"");
            viewString.append(objectId);
            viewString.append("\",\n");
            viewString.append("        autoSave: false,\n");
            viewString.append("        editor: \"dijit.form.DateTextBox\",\n");
            viewString.append("        editorParams: {required: true, width: '70px', value: new Date(");
            viewString.append(dt != null ? ("" + dt.getTime()) : "");
            viewString.append("), constraints: { ");
            viewString.append(!handleMaximum ? "max" : "min");
            viewString.append(": date_");
            viewString.append(significantOther);
            viewString.append("}},\n");
            viewString.append("        onChange: function(value) {\n");
            viewString.append("          getSyncHtml('");
            viewString.append(url);
            viewString.append("&propUri=");
            viewString.append(prop.getEncodedURI());
            viewString.append("&value='+value);\n");
            viewString.append("          if (dijit.byId('");
            viewString.append(significantOther);
            viewString.append("') && dijit.byId('");
            viewString.append(significantOther);
            viewString.append("').wrapperWidget && dijit.byId('");
            viewString.append(significantOther);
            viewString.append("').wrapperWidget.editWidget) {\n");
            viewString.append("            dijit.byId('");
            viewString.append(significantOther);
            viewString.append("').wrapperWidget.editWidget.constraints.");
            viewString.append(handleMaximum ? "max" : "min");
            viewString.append(" = this.wrapperWidget.editWidget.value;\n");
            viewString.append("          } else {\n");
            viewString.append("              dijit.byId('");
            viewString.append(significantOther);
            viewString.append("').editorParams.constraints.");
            viewString.append(handleMaximum ? "max" : "min");
            viewString.append(" = this.wrapperWidget.editWidget.value;\n");
            viewString.append("          }\n");
/*            viewString.append("");
            viewString.append("");*/
            viewString.append("        }\n");
            viewString.append("      }, 'eb_");
            viewString.append(objectId);
            viewString.append("');\n");
            viewString.append("      iledit_");
            viewString.append(objectId);
            viewString.append(".startup();\n");
            viewString.append("    });\n");
            viewString.append("-->\n");
            viewString.append("</script>\n");
            viewString.append("<span id=\"eb_");
            viewString.append(objectId);
            viewString.append("\" class=\"swb-ile\">");
            viewString.append(value);
            viewString.append("</span>");
            ret.append(viewString.toString());
        }

        return ret.toString();
    }
    
}
