package org.semanticwb.model;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


public class CodeEditor extends org.semanticwb.model.base.CodeEditorBase 
{
    public CodeEditor(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public String renderXHTML(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang)
    {
        StringBuffer ret = new StringBuffer(250);
        String id=obj.getURI()+"/"+prop.getName()+"_editArea";
        String name=prop.getName();
        String label=prop.getDisplayName(lang);
        SemanticObject sobj=prop.getDisplayProperty();
        boolean required=prop.isRequired();
        String pmsg=null;
        String imsg=null;
        String language="es";
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

        if(lang.equals("en"))
        {
            language=lang;
        }
        String value=request.getParameter(prop.getName());
        if(value==null)value=obj.getProperty(prop);
        if(value==null)value="";
        if(mode.equals("edit") || mode.equals("create") )
        {
            ret.append("<script language=\"javascript\" type=\"text/javascript\" charset=\"UTF-8\">\n");
            ret.append("editAreaLoader.init({\n");
            ret.append("    id : \"" + id + "\"\n");
            ret.append("    ,language: \"" + language + "\"\n");
            ret.append("    ,syntax: \"" + getLanguage().toLowerCase() + "\"\n");
            ret.append("    ,start_highlight: true\n");
            ret.append("    ,toolbar: \"search, go_to_line,");
            ret.append(" |, undo, redo, |, select_font,|, change_smooth_selection,");
            ret.append(" highlight, reset_highlight, |, help\"\n");
            ret.append("    ,is_multi_files: false\n");
            ret.append("    ,allow_toggle: false\n");
            ret.append("});\n");
            ret.append("\n");
            ret.append("  function my_save(id, content){\n");
            ret.append("\n");
            ret.append("  }\n");
            ret.append("</script>\n");
            ret.append("<textarea id=\"" + id + "\" name=\"" + name + "\" rows=\"");
            ret.append(getRows()+"\" cols=\""+getCols()+"\">");
            ret.append(value + "</textarea>\n");
            ret.append("\n");
        } else if (mode.equals("view"))
        {
            ret.append("<span _id=\"" + id + "\" name=\"" + name + "\">" + value + "</span>\n");
        }

        ret.append("Test:" + getLanguage());
        
        return ret.toString();
    }



}
