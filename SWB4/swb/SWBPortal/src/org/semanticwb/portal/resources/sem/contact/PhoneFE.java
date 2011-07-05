package org.semanticwb.portal.resources.sem.contact;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


   /**
   * Elemento que muestra un componente grafico para capturar los datos telefónicos 
   */
public class PhoneFE extends org.semanticwb.portal.resources.sem.contact.base.PhoneFEBase 
{
    public PhoneFE(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type, String mode, String lang) {
        if (obj == null) {
            obj = new SemanticObject();
        }

//        boolean IPHONE = false;
//        boolean XHTML  = false;
        boolean DOJO   = false;

//        if (type.equals("iphone")) {
//            IPHONE = true;
//        } else if (type.equals("xhtml")) {
//            XHTML = true;
//        } else
        if (type.equals("dojo")) {
            DOJO = true;
        }

        StringBuilder   ret      = new StringBuilder();
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
            if (required && imsg == null) {
                imsg = label + " es requerido.";

                if (lang.equals("en")) {
                    imsg = label + " is required.";
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

        // value=SWBUtils.TEXT.encodeExtendedCharacters(value);
//      System.out.println("value:"+value);
//      for(int x=0;x<value.length();x++)
//      {
//          System.out.println(" "+(int)value.charAt(x));
//      }
        if (mode.equals("edit") || mode.equals("create")) {
//System.out.println("estoy creando un form element de phone.....");
            ret.append("<input name=\"" + name + "\" size=\"30\" value=\"" + value + "\"");

            if (DOJO) {
                ret.append(" dojoType=\"dijit.form.ValidationTextBox\"");
            }

            if (DOJO) {
                ret.append(" required=\"" + required + "\"");
            }

//          ret.append(" propercase=\"true\"");
            if (DOJO) {
                ret.append(" promptMessage=\"" + pmsg + "\"");
            }

            /*if (DOJO) {
                ret.append(((getRegExp() != null)
                            ? (" regExp=\"" + getRegExp() + "\"")
                            : ""));
            }*/

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

            if (DOJO) {
                if (!mode.equals("create") && prop.isLocaleable()) {
                    ret.append(" <a href=\"#\" onClick=\"javascript:showDialog('" + SWBPlatform.getContextPath()
                               + "/swbadmin/jsp/propLocaleEdit.jsp?suri=" + obj.getEncodedURI() + "&prop="
                               + prop.getEncodedURI() + "','Idiomas de la Propiedad " + prop.getDisplayName(lang)
                               + "');\">locale</a>");
                }
            }
        } else if (mode.equals("view")) {
            ret.append("<span name=\"" + name + "\">" + value + "</span>");
        }

        return ret.toString();
    }
}
