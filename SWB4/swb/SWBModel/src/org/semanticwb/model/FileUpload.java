package org.semanticwb.model;

import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

public class FileUpload extends org.semanticwb.model.base.FileUploadBase {

    public FileUpload(org.semanticwb.platform.SemanticObject base) {
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
            //Página ejemplo de implementación:http://blog.tremend.ro/2007/03/01/ajax-file-upload-monitoring-monitor-your-file-upload-with-dwr-and-commons-fileupload/
            //Fecha de implemetación:26/Febrero/2009
            ret = "<link rel=\"stylesheet\" type=\"text/css\" media=\"screen\" href=\"/swb/swbadmin/css/upload/upload.css\"/>"+
                  "<script type='text/javascript' src=\"/swb/dwr/util.js\"></script>"+
                  "<script type='text/javascript' src=\"/swb/dwr/engine.js\"></script>"+
                  "<script type=\"text/javascript\" src=\"/swb/dwr/interface/uploadProxy.js\"></script>"+
                  "<script type='text/javascript' src=\"/swb/swbadmin/js/upload/upload.js\"></script>";

            ret += "<iframe id='target_upload' name='target_upload' src='' style='display: none'></iframe>" +
                    "<input id=\"importFile\" name=\"importFile\" type=\"file\"> <br/>" +
                    "<input type=\"hidden\" name=\"uniqueFileIdentifier\" value=\"1234\"/>" +
                    "<a href=\"#\" onClick=\"javascript:if(uploadjs(document.forms[0])) {return startUploadMonitoring();}\">Subir</a>" + //En lugar de este boton p
                    "<div id=\"uploadStatus\">" +
                    "<div id=\"uploadProgressBar\" style=\"width:200px;\">" +
                    "<div id=\"uploadIndicator\"></div>" +
                    "</div>" +
                    "<div id=\"uploadPercentage\"></div>" +
                    "</div>";

            ret += "<script type=\"text/javascript\">" +
                    "function uploadjs(forma){" +
                    "  var encoding=forma.encoding;" +
                    "  forma.encoding='multipart/form-data';" +
                    "  var method=forma.method;" +
                    "  forma.method='post';" +
                    "  var action=forma.action;" +
                    "  forma.action='"+SWBPlatform.getContextPath()+"/Upload';" +
                    "  var target=forma.target;" +
                    "  forma.target='target_upload';" +
                    "  forma.submit();" +
                    "  forma.encoding=encoding;" +
                    "  forma.method=method;" +
                    "  forma.action=action;" +
                    "  forma.target=target;" +
                    "  return true;" +
                    "}" +
                    "</script>";
        } else if (mode.equals("view")) {
            ret = "<span _id=\"" + name + "\" name=\"" + name + "\">" + value + "</span>";
        }

        return ret;
    }
}
