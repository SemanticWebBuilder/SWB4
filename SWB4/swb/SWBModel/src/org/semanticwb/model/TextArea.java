package org.semanticwb.model;

import java.util.Iterator;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

public class TextArea extends TextAreaBase {

    public TextArea(SemanticObject base) {
        super(base);
    }

    @Override
    public String renderElement(SemanticObject obj, SemanticProperty prop, String type, String mode, String lang) {
        if (obj == null) {
            obj = new SemanticObject();
        }
        String ret = "";

        if (type.endsWith("iphone")) {
            ret = renderIphone(obj, prop, type, mode, lang);
        } else {
            ret = renderXHTML(obj, prop, type, mode, lang);
        }
        return ret;
    }

    public String renderIphone(SemanticObject obj, SemanticProperty prop, String type, String mode, String lang) {
        return "";
    }

    public String renderXHTML(SemanticObject obj, SemanticProperty prop, String type, String mode, String lang) {
        String ret = "";
        String name = prop.getName();
        String label = prop.getDisplayName(lang);
        SemanticObject sobj = prop.getDisplayProperty();
        boolean required = prop.isRequired();
        String pmsg = null;
        String imsg = null;
        if (sobj != null) {
            DisplayProperty dobj = new DisplayProperty(sobj);
            pmsg = dobj.getPromptMessage();
            imsg = dobj.getInvalidMessage();
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

        String value = obj.getProperty(prop);
        if (value == null) {
            value = "";
        }
        if (mode.equals("edit") || mode.equals("create")) {

            // *******************************************************

            Iterator itAttr=attributes.keySet().iterator();
            while(itAttr.hasNext()){
                String attrName=(String)itAttr.next();
                System.out.println("attrName:"+attrName+"attrValue:"+attributes.get(attrName));
            }

            String path = SWBPlatform.getContextPath() + "/resources/jsp/forum/images/emotion/";

            ret = "<script language=\"javascript\">" +
                    "function smilie(thesmilie) {" +
                    "var txtarea = document.http://www.semanticwebbuilder.org/swb4/forum#FrmThread/form."+name+";" +
                    "var newSmilie = ' ' + thesmilie + ' ';" +
                    "insertString(txtarea, newSmilie);" +
                    "}" +
                    "function insertString(txtarea, thetext) {" +
                    "if (txtarea.createTextRange && txtarea.caretPos) {" +
                    "var caretPos = txtarea.caretPos;" +
                    "var newText = thetext;" +
                    "if (caretPos.text.charAt(caretPos.text.length - 1) == ' ') newText = newText + ' ';" +
                    "if (caretPos.text.charAt(0) == ' ') newText = ' ' + newText;" +
                    "caretPos.text = newText;" +
                    "} else if (document.getElementById) {" +
                    "var selLength = txtarea.textLength;" +
                    "var selStart = txtarea.selectionStart;" +
                    "var selEnd = txtarea.selectionEnd;" +
                    "if (selEnd==1 || selEnd==2) selEnd = selLength;" +
                    "var s1 = (txtarea.value).substring(0, selStart);" +
                    "var s2 = (txtarea.value).substring(selStart, selEnd)" +
                    "var s3 = (txtarea.value).substring(selEnd, selLength);" +
                    "var newText = thetext;" +
                    "if (s2.charAt(s2.length - 1) == ' ') newText = newText + ' ';" +
                    "if (s2.charAt(0) == ' ') newText = ' ' + newText;" +
                    "txtarea.value = s1 + thetext + s3;" +
                    "} else {" +
                    "txtarea.value += thetext;" +
                    "}" +
                    "txtarea.focus();" +
                    "}" +
                    "</script>" +
                    "<a href=\"javascript:smilie('[:)]');\"><img src=\"" + path + "smile.gif\" alt=\"smile\" border=\"0\">" +
                    "<a href=\"javascript:smilie('[:(]');\"><img src=\"" + path + "sad.gif\" alt=\"sad\" border=\"0\" /></a>" +
                    "<a href=\"javascript:smilie('[:D]');\"><img src=\"" + path + "biggrin.gif\" alt=\"big grin\" border=\"0\" />" +
                    "<a href=\"javascript:smilie('[:))]');\"><img src=\"" + path + "laughing.gif\" alt=\"laughing\" border=\"0\" /></a>" +
                    "<a href=\"javascript:smilie('[:((]');\"><img src=\"" + path + "crying.gif\" alt=\"crying\" border=\"0\" /></a>" +
                    "<a href=\"javascript:smilie('[;)]');\"><img src=\"" + path + "wink.gif\" alt=\"wink\" border=\"0\" /></a>" +
                    "<a href=\"javascript:smilie('[:&quot;&gt;]');\"><img src=\"" + path + "blushing.gif\" alt=\"blushing\" border=\"0\" />" +
                    "<a href=\"javascript:smilie('[:p]');\"><img src=\"" + path + "tongue.gif\" alt=\"tongue\" border=\"0\" /></a>" +
                    "<a href=\"javascript:smilie('[B-)]');\"><img src=\"" + path + "cool.gif\" alt=\"cool\" border=\"0\" /></a>" +
                    "<a href=\"javascript:smilie('[:x]');\"><img src=\"" + path + "love.gif\" alt=\"love struck\" border=\"0\" /></a>" +
                    "<a href=\"javascript:smilie('[:-/]');\"><img src=\"" + path + "confused.gif\" alt=\"confused\" border=\"0\" />" +
                    "<a href=\"javascript:smilie('[&gt;:)]');\"><img src=\"" + path + "devilish.gif\" alt=\"devilish\" border=\"0\" />";
                    
                    ret=ret+ "<br/><textarea name=\"" + name + "\" dojoType_=\"dijit.Editor\" rows=\"" + getRows() + "\" cols=\"" + getCols() + "\" " + getAttributes() + ">" + value + "</textarea>";
        } else if (mode.equals("view")) {
            ret = "<span _id=\"" + name + "\" name=\"" + name + "\">" + value + "</span>";
        }

        return ret;
    }
}
