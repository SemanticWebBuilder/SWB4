package org.semanticwb.social;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


public class TextPhrase extends org.semanticwb.social.base.TextPhraseBase 
{
    public TextPhrase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.model.base.FormElementBase#renderElement(javax.servlet.http.HttpServletRequest, org.semanticwb.platform.SemanticObject, org.semanticwb.platform.SemanticProperty, java.lang.String, java.lang.String, java.lang.String)
     */
    /**
     * Render element.
     * 
     * @param request the request
     * @param obj the obj
     * @param prop the prop
     * @param type the type
     * @param mode the mode
     * @param lang the lang
     * @return the string
     */
    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type,
                                String mode, String lang) {
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
            ret.append("<input name=\"" + name + "\" size=\"30\" value=\"" + value + "\"");

            if (DOJO) {
                ret.append(" dojoType=\"dijit.form.ValidationTextBox\"");
            }

            if (DOJO) {
                if(required)ret.append(" required=\"" + required + "\"");
            }

//          ret.append(" propercase=\"true\"");
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

            if(getMaxLength()>0)
                ret.append(" maxlength=\""+getMaxLength()+"\"");
            
            if (DOJO) {
                ret.append(" trim=\"true\"");
            }

            ret.append(" style=\"width:300px;\"");
            ret.append(ext);
            ret.append("/>");

            if (DOJO) {
                if (!mode.equals("create") && prop.isLocaleable() && !obj.isVirtual()) {
                    ret.append(" <a href=\"#\" onClick=\"javascript:showDialog('" + SWBPlatform.getContextPath()
                               + "/swbadmin/jsp/propLocaleEdit.jsp?suri=" + obj.getEncodedURI() + "&prop="
                               + prop.getEncodedURI() + "','Idiomas de la Propiedad " + prop.getDisplayName(lang)
                               + "');\">locale</a>");
                }
            }
            //TODO:Hacer que desde la ontología se defina a que redes sociales aplica la propiedad
            
            
            if(getTf_socialNet()!= null && getTf_socialNet().trim().length() > 0){
                String socialNets[];
                socialNets = getTf_socialNet().split(",");
                for(int i = 0; i< socialNets.length; i++){
                    if(socialNets[i].trim().equalsIgnoreCase("T")){
                        ret.append("<div class=\"stream_twitter_icon\"><img src=\"/swbadmin/css/images/config-tw.png\" /></div>");   //Twitter
                    }else if(socialNets[i].trim().equalsIgnoreCase("F")){
                        ret.append("<div class=\"stream_facebook_icon\"><img src=\"/swbadmin/css/images/config-fb.png\" /></div>");   //Facebook
                    }else if(socialNets[i].trim().equalsIgnoreCase("Y")){
                     ret.append("<div class=\"stream_youtube_icon\"><img src=\"/swbadmin/css/images/config-yt.png\" /></div>");   //Youtube
                    }
                }
            }
            
            //ret.append("<img src=\"/swbadmin/css/images/config-tw.png\" />");   //Twitter
            //ret.append("<img src=\"/swbadmin/css/images/config-fb.png\" />");   //Facebook
            //ret.append("<img src=\"/swbadmin/css/images/config-yt.png\" />");   //Youtube
        } else if (mode.equals("view")) {
            ret.append("<span name=\"" + name + "\">" + value + "</span>");
        }

        return ret.toString();
    }
}
