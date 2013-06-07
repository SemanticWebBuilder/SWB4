package org.semanticwb.bsc.formelement;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


public class UrlGo extends org.semanticwb.bsc.formelement.base.UrlGoBase 
{
    private static Logger log = SWBUtils.getLogger(Ordinal.class);
    
    public UrlGo(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type, String mode, String lang) {
        if (obj == null) {
            obj = new SemanticObject();
        }
        
//        boolean DOJO   = false;
//        
//        if (type.equals("dojo")) {
//            DOJO = true;
//        }
        
System.out.println("obj="+obj);
System.out.println("prop="+prop);
System.out.println("propName="+propName);
System.out.println("type="+type);
System.out.println("mode="+mode);
System.out.println("lang="+lang);
System.out.println("obj.getSemanticClass()="+obj.getSemanticClass());
System.out.println("obj.getSemanticClass().getClassName()="+obj.getSemanticClass().getClassName());
System.out.println("obj.getSemanticClass().getCanonicalName()()="+obj.getSemanticClass().getCanonicalName());
System.out.println("obj.getSemanticClass().getName()()="+obj.getSemanticClass().getName());
        
        
        StringBuilder   ret      = new StringBuilder();
//        String         name     = propName;
//        String         label    = prop.getDisplayName(lang);
//        SemanticObject sobj     = prop.getDisplayProperty();
//        boolean        required = prop.isRequired();
//        String         pmsg     = null;
//        String         imsg     = null;
//        boolean        disabled = false;

//        if (sobj != null) {
//            DisplayProperty dobj = new DisplayProperty(sobj);

//            pmsg     = dobj.getPromptMessage();
//            imsg     = dobj.getInvalidMessage();
//            disabled = dobj.isDisabled();
//        }

//        if (DOJO) {
//            if (required && imsg == null) {
//                imsg = label + " es requerido.";
//
//                if (lang.equals("en")) {
//                    imsg = label + " is required.";
//                }
//            }
//
//            if (pmsg == null) {
//                pmsg = "Captura " + label + ".";
//
//                if (lang.equals("en")) {
//                    pmsg = "Enter " + label + ".";
//                }
//            }
//        }

//        String ext = "";
//
//        if (disabled) {
//            ext += " disabled=\"disabled\"";
//        }
//
//        String value = request.getParameter(propName);
//
//        if (value == null) {
//            value = obj.getProperty(prop);
//        }
//
//        if (value == null) {
//            value = "";
//        }
//
//        value=value.replace("\"", "&quot;");
        
//        if (mode.equals("edit") || mode.equals("create")) {
            //ret.append("<a href=\"").append(obj.getSemanticClass().getName()).append("\" target=\"_blank\" title=\"Ver ayuda\">Ayuda</a>");
        ret.append("<a href=\"#\" onclick=\"window.open('").append(obj.getSemanticClass().getName()).append("','swbstgyhelp','location=0,menubar=0,status=0,toolbar=0,width=600,height=400',false)\" title=\"Ver ayuda\">Ayuda</a>");
//            ret.append("<input name=\"" + name + "\" size=\"30\" value=\"" + value + "\"");
//
//            if (DOJO) {
//                ret.append(" dojoType=\"dijit.form.ValidationTextBox\"");
//            }
//
//            if (DOJO) {
//                ret.append(" required=\"" + required + "\"");
//            }
//            
//            if (DOJO) {
//                ret.append(" promptMessage=\"" + pmsg + "\"");
//            }
//            
//            if (DOJO) {
//                ret.append(" invalidMessage=\"" + imsg + "\"");
//            }
//
//            ret.append(" " + getAttributes());
//                        
//            if (DOJO) {
//                ret.append(" trim=\"true\"");
//            }
//
//            ret.append(" style=\"width:300px;\"");
//            ret.append(ext);
//            ret.append("/>");

//            if (DOJO) {
//                if (!mode.equals("create") && prop.isLocaleable() && !obj.isVirtual()) {
//                    ret.append(" <a href=\"#\" onClick=\"javascript:showDialog('" + SWBPlatform.getContextPath()
//                               + "/swbadmin/jsp/propLocaleEdit.jsp?suri=" + obj.getEncodedURI() + "&prop="
//                               + prop.getEncodedURI() + "','Idiomas de la Propiedad " + prop.getDisplayName(lang)
//                               + "');\">locale</a>");
//                }
//            }
//        }
//        else if(mode.equals("view"))
//        {
//            ret.append("<span name=\"" + name + "\">" + value + "</span>");
//        }
        return ret.toString();
    }

    @Override
    public String renderLabel(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type, String mode, String lang, String label) {
        String ret="";
//        String name=propName;
//        if(label==null) {
//            label=prop.getDisplayName(lang);
//        }
//        SemanticObject sobj=prop.getDisplayProperty();
//        boolean required=prop.isRequired();
//
//        String reqtxt=" &nbsp;";
//        if(!mode.equals("filter") && required)reqtxt=" <em>*</em>";
//
//        ret="<label for=\""+name+"\">"+label + reqtxt + "</label>";
        return ret;
    }
    
}
