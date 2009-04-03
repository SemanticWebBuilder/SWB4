package org.semanticwb.model;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

public class FileUpload extends org.semanticwb.model.base.FileUploadBase {

    public FileUpload(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang) {
        if (obj == null) {
            obj = new SemanticObject();
        }
        String ret = "";

        if (type.endsWith("iphone")) {
            ret = renderIphone(request, obj, prop, type, mode, lang);
        } else {
            ret = renderXHTML(request, obj, prop, type, mode, lang);
        }
        return ret;
    }

    public String renderIphone(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang) {
        return "";
    }

    public String renderXHTML(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang) {
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
            String attchMsg="";
            if(request.getAttribute("attachCount")!=null){
                        attchMsg="Archivo(s) existentes:<br/>";
                        int count=Integer.parseInt((String)request.getAttribute("attachCount"));
                        for(int i=1;i<=count;i++){
                            String fileName=(String)request.getAttribute("attach_"+i);
                            int pos=fileName.lastIndexOf("/");
                            if(pos>-1){
                                fileName=fileName.substring(pos+1);
                            }
                            String target="";
                            if(request.getAttribute("attachTarget_"+i)!=null){
                                target=(String)request.getAttribute("attachTarget_"+i);
                            }

                            if(request.getAttribute("attachRemovePath_"+i)!=null){
                                attchMsg+="<a href=\""+request.getAttribute("attachRemovePath_"+i)+"\">X</a> ";
                            }
                            attchMsg+=i+")<a href=\""+ request.getAttribute("attach_"+i) +"\" target=\""+target+"\">"+fileName+"</a>";
                            attchMsg+="<br/>";
                        }
            }
            //Página ejemplo de implementación:http://blog.tremend.ro/2007/03/01/ajax-file-upload-monitoring-monitor-your-file-upload-with-dwr-and-commons-fileupload/
            //Fecha de implemetación:26/Febrero/2009
            ret = "<link rel=\"stylesheet\" type=\"text/css\" media=\"screen\" href=\"/swb/swbadmin/css/upload/upload.css\"/>\n"+
                  "<script type='text/javascript' src=\"/swb/dwr/util.js\"></script>\n"+
                  "<script type='text/javascript' src=\"/swb/dwr/engine.js\"></script>\n"+
                  "<script type=\"text/javascript\" src=\"/swb/dwr/interface/uploadProxy.js\"></script>\n"+
                  "<script type='text/javascript' src=\"/swb/swbadmin/js/upload/upload.js\"></script>\n";

            ret += "<iframe id='target_upload' name='target_upload' src='' style='display: none'></iframe><br/>" +
                    attchMsg+
                    "<input id=\"importFile\" name=\"importFile\" type=\"file\"> <br/>" +
                    "<input type=\"hidden\" name=\"uniqueFileIdentifier\" value=\"1234\"/>\n" +
                    "<a href=\"#\" onClick=\"javascript:if(uploadjs(document.forms[0])) {return startUploadMonitoring();}\">Subir</a>\n" + //En lugar de este boton p
                    "<div id=\"uploadStatus\">\n" +
                    "   <div id=\"uploadProgressBar\" style=\"width:200px;\">\n" +
                    "       <div id=\"uploadIndicator\"></div>\n" +
                    "   </div>\n" +
                    "   <div id=\"uploadPercentage\"></div>\n" +
                    "</div>\n";

            ret += "<script type=\"text/javascript\">\n" +
                    "function uploadjs(forma){\n" +
                    "if(forma.importFile.value==''){alert('El campo archivo no debe estar vacio');forma.importFile.focus(); return false;}" +
                    "  var encoding=forma.encoding;\n" +
                    "  forma.encoding='multipart/form-data';\n" +
                    "  var method=forma.method;\n" +
                    "  forma.method='post';\n" +
                    "  var action=forma.action;\n" +
                    "  forma.action='"+SWBPlatform.getContextPath()+"/Upload';\n" +
                    "  var target=forma.target;\n" +
                    "  forma.target='target_upload';\n" +
                    "  forma.submit();\n" +
                    "  forma.encoding=encoding;\n" +
                    "  forma.method=method;\n" +
                    "  forma.action=action;\n" +
                    "  forma.target=target;\n" +
                    "  return true;\n" +
                    "}\n" +
                    "</script>\n";
        } else if (mode.equals("view")) {
            ret = "<span _id=\"" + name + "\" name=\"" + name + "\">" + value + "</span>";
        }

        return ret;
    }
}
