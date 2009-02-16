package org.semanticwb.model;

import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


public class FileUpload extends org.semanticwb.model.base.RichTextBase
{
    public FileUpload(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
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
            ret="<script type=\"text/javascript\" src=\"/swb/swbadmin/jsp/upload/jquery-1.2.1.min.js\"></script>" +
            "<script type=\"text/javascript\" src=\"/swb/swbadmin/jsp/upload/jquery.flash.js\"></script>" +
            "<script type=\"text/javascript\" src=\"/swb/swbadmin/jsp/upload/jquery.jqUploader.js\"></script>" +

            "<script type=\"text/javascript\">" +
            "$(document).ready(function(){"+
                "$('#example1').jqUploader({background:'FFFFDF',barColor:'FFDD00',allowedExt:'*.doc; *.jpg; *.jpeg; *.txt',allowedExtDescr: 'what you want',validFileMessage: 'Thanks, now hit Upload!',endMessage: 'and don\'t you come back ;)',hideSubmit: false});"+
                "$(\"#example2\").jqUploader({"+
                    "afterScript:	\"<%=url.setAction(\"redirect\").toString()%>\","+
                    "background:	\"FFFFDF\","+
                    "barColor:	\"64A9F6\","+
                    "allowedExt:     \"*.txt; *.doc; *.jpeg; *.png\","+
                    "allowedExtDescr: \"Images and movies (*.avi; *.jpg; *.jpeg; *.png)\""+
                "});"+
                "$(\"#example3\").jqUploader({background:	\"FFFFDF\",barColor:	\"FF00FF\"});"+
            "});"+
            "</script>";

            ret+="<input name=\"MAX_FILE_SIZE\" value=\"1048576\" type=\"hidden\" />"+
            "<input name=\"myFile\"  id=\"example1_field\"  type=\"file\" />";
        }else if(mode.equals("view"))
        {
            ret="<span _id=\""+name+"\" name=\""+name+"\">"+value+"</span>";
        }

        return ret;
    }
}
