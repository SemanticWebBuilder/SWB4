package org.semanticwb.model;

import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


public class FileUpload extends org.semanticwb.model.base.FileUploadBase
{
    public FileUpload(org.semanticwb.platform.SemanticObject base)
    {
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
        String ret="";
        String name=prop.getName();
        String label=prop.getDisplayName(lang);
        SemanticObject sobj=prop.getDisplayProperty();
        boolean required=prop.isRequired();
        String pmsg=null;
        String imsg=null;
        if(sobj!=null)
        {
            DisplayProperty dobj=new DisplayProperty(sobj);
            pmsg=dobj.getPromptMessage();
            imsg=dobj.getInvalidMessage();
        }

        if(imsg==null)
        {
            if(required)
            {
                imsg=label+" es requerido.";
                if(lang.equals("en"))
                {
                    imsg=label+" is required.";
                }
            }
        }

        if(pmsg==null)
        {
            pmsg="Captura "+label+".";
            if(lang.equals("en"))
            {
                pmsg="Enter "+label+".";
            }
        }

        String value=obj.getProperty(prop);
        if(value==null)value="";
        if(mode.equals("edit") || mode.equals("create") )
        {
            //upload request is made with a dummy target (an embedded invisible iframe)
            //The iframe is used to swallow the response to the upload request.
            //The form contains the upload fields and the button. The uploadStatus div will hold the progress monitor.
            //Talvez tenga que cambiar el enctype de la forma x multipart/form-data con javascript (this.form.encoding=multipart/form-data)
            //Cambiar tbn el metodo de la forma this.form.method=post
            //Poner una liga que tenga onsubmit=\"return startUploadMonitoring();\" (cambiar el onsubmit x OnClick)
            //Poner el target a la liga (target="target_upload")
//                ret="<iframe id='target_upload' name='target_upload' src='' style='display: none'></iframe>"+
//                "<form enctype=\"multipart/form-data\" name=\"form\" method=\"post\" action=\"Upload\"  onsubmit=\"return startUploadMonitoring();\" target=\"target_upload\">"+
//                   "<input id=\"importFile\" name=\"importFile\" type=\"file\"> <br/>"+
//                    "<input id=\"submitButton\" type=\"submit\" value=\"Upload\"/>"+ //En lugar de este boton p
//                "</form>"+
//                "<div id=\"uploadStatus\">"+
//                    "<div id=\"uploadProgressBar\" style=\"width:200px;\">"+
//                        "<div id=\"uploadIndicator\"></div>"+
//                    "</div>"+
//                    "<div id=\"uploadPercentage\"></div>"+
//                "</div>";

                   ret="<iframe id='target_upload' name='target_upload' src='' style='display: none'></iframe>"+
                   "<input id=\"importFile\" name=\"importFile\" type=\"file\"> <br/>"+
                    "<a href=\"#\" onClick=\"javascript:if(uploadjs(document.forms[0])) {alert('para ir');return startUploadMonitoring();}\">Subir</a>"+ //En lugar de este boton p
                "<div id=\"uploadStatus\">"+
                    "<div id=\"uploadProgressBar\" style=\"width:200px;\">"+
                        "<div id=\"uploadIndicator\"></div>"+
                    "</div>"+
                    "<div id=\"uploadPercentage\"></div>"+
                "</div>";

                ret+="<script type=\"text/javascript\">"+
                     "function uploadjs(forma){"+
                     "  forma.encoding='multipart/form-data';"+
                     "  forma.method='post';"+
                     "  forma.action='/swb/Upload';"+
                     "  forma.target='target_upload';"+
                     "  forma.submit();"+
                     "  return true;"+
                     "}"+
                     "</script>";                
        }else if(mode.equals("view"))
        {
            ret="<span _id=\""+name+"\" name=\""+name+"\">"+value+"</span>";
        }

        return ret;
    }
}
