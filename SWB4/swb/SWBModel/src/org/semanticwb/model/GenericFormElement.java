package org.semanticwb.model;

import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

public class GenericFormElement extends FormElementBase
{
    public GenericFormElement()
    {
        super(new SemanticObject());
    }

    public GenericFormElement(SemanticObject obj)
    {
        super(obj);
    }
    
    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang)
    {
        if(obj==null)obj=new SemanticObject();
        boolean IPHONE=false;
        boolean XHTML=false;
        boolean DOJO=false;
        if(type.equals("iphone"))IPHONE=true;
        else if(type.equals("xhtml"))XHTML=true;
        else if(type.equals("dojo"))DOJO=true;

        StringBuffer ret=new StringBuffer();
        String name=prop.getName();
        String label=prop.getDisplayName(lang);
        SemanticObject sobj=prop.getDisplayProperty();
        boolean required=prop.isRequired();
        String pmsg=null;
        String imsg=null;
        String selectValues=null;
        boolean disabled=false;
        if(sobj!=null)
        {
            DisplayProperty dobj=new DisplayProperty(sobj);
            pmsg=dobj.getPromptMessage();
            imsg=dobj.getInvalidMessage();
            selectValues=dobj.getDisplaySelectValues(lang);
            disabled=dobj.isDisabled();
        }

        String ext="";

        if(DOJO)
        {
            if(imsg==null)
            {
                if(prop.isDataTypeProperty() && prop.isNumeric())
                {
                    imsg=getLocaleString("inv_number", lang);
                }else
                {
                    imsg=getLocaleString("inv_data", lang);
                }
                if(required)
                {
                    imsg=label+" "+getLocaleString("required", lang);
                }
            }

            if(pmsg==null)
            {
                pmsg=getLocaleString("enter", lang)+" "+label;
            }
        }

        if(disabled)
        {
            ext+=" disabled=\"disabled\"";
        }
        
        
        if(prop.isDataTypeProperty())
        {
            if(selectValues!=null)
            {
                String value=request.getParameter(prop.getName());
                if(value==null)value=obj.getProperty(prop);
                ret.append("<span>");
                StringTokenizer st=new StringTokenizer(selectValues,"|");
                while(st.hasMoreTokens())
                {
                    String tok=st.nextToken();
                    int ind=tok.indexOf(':');
                    String id=tok;
                    String val=tok;
                    if(ind>0)
                    {
                        id=tok.substring(0,ind);
                        val=tok.substring(ind+1);
                    }
                    ret.append("<label for=\""+name+id+"\">");
                    ret.append("<input");
                    if(DOJO)ret.append(" dojoType=\"dijit.form.RadioButton\"");
                    if(mode.equals("view"))ret.append(" disabled=\"disabled\"");
                    ret.append(" id_=\""+name+id+"\" name=\""+name+"\"");
                    if(id.equals(value))ret.append(" checked=\"checked\"");
                    ret.append(" value=\""+id+"\" type=\"radio\" />"+val+"</label>");
                }
                ret.append("</span>");
                
            }else if(prop.isBoolean())
            {
                String checked="";
                boolean value=false;
                String aux=request.getParameter(prop.getName());
                if(aux!=null)value=true;
                else value=obj.getBooleanProperty(prop);
                
                if(value)checked="checked=\"checked\"";
                ret.append("<input type=\"checkbox\" id_=\""+name+"\" name=\""+name+"\" "+checked);
                if(DOJO)ret.append(" dojoType=\"dijit.form.CheckBox\"");
                if(DOJO)ret.append(" required=\""+required+"\"");
//                    + " propercase=\"true\""
                if(DOJO)ret.append(" promptMessage=\""+pmsg+"\"");
                if(DOJO)ret.append(" invalidMessage=\""+imsg+"\"");
//                    + " trim=\"true\""
                ret.append(ext);
                if(mode.equals("view"))ret.append(" disabled=\"disabled\"");
                ret.append("/>");
            }else if(prop.isDateTime())
            {
                String value=request.getParameter(prop.getName());
                if(value==null)value=obj.getProperty(prop);
                if(value==null)value="";
                ret.append("<span _id=\""+name+"\" name=\""+name+"\">"+value+"</span>");
            }else if(prop.isInt() || prop.isLong())
            {
                String value=request.getParameter(prop.getName());
                if(value==null)value=obj.getProperty(prop);
                if(value==null)value="";
                if(mode.equals("edit") || mode.equals("create") )
                {
                    ret.append("<input _id=\""+name+"\" name=\""+name+"\" value=\""+value+"\"");
                    if(DOJO)ret.append(" dojoType=\"dijit.form.ValidationTextBox\"");
                    if(DOJO)ret.append(" regExp=\"\\d+\"");
                    if(DOJO)ret.append(" required=\""+required+"\"");
    //                    + " propercase=\"true\""
                    if(DOJO)ret.append(" promptMessage=\""+pmsg+"\"");
                    if(DOJO)ret.append(" invalidMessage=\""+imsg+"\"");
                    ret.append(" style=\"width:100px;\"");
                    ret.append(" " + getAttributes());
    //                    + " trim=\"true\""
                    ret.append(ext);
                    ret.append("/>");
//                }else if(mode.equals("edit"))
//                {
//                    ret="<label for=\""+name+"\">"+label
//                        + reqtxt
//                        + "</label>"
//                        + "<p><span _id=\""+name+"\" dojoType=\"dijit.InlineEditBox\" editor=\"dijit.form.ValidationTextBox\" editorParams=\"{regExp:'\\\\d+'}\" autoSave=\"false\" name=\""+name+"\">"+value+"</span></p>";
                }else if(mode.equals("view"))
                {
                    ret.append("<span _id=\""+name+"\" name=\""+name+"\">"+value+"</span>");
                }
            }else
            {
                String value=request.getParameter(prop.getName());
                if(value==null)value=obj.getProperty(prop);
                if(value==null)value="";
                if(mode.equals("edit") || mode.equals("create") )
                {
                    ret.append("<input _id=\""+name+"\" name=\""+name+"\" value=\""+value+"\"");
                    if(DOJO)ret.append(" dojoType=\"dijit.form.ValidationTextBox\"");
                    if(DOJO)ret.append(" required=\""+required+"\"");
    //                    + " propercase=\"true\""
                    if(DOJO)ret.append(" promptMessage=\""+pmsg+"\"");
                    if(DOJO)ret.append(" invalidMessage=\""+imsg+"\"");
                    ret.append(" style=\"width:300px;\"");
                    ret.append(" " + getAttributes());
                    if(DOJO)ret.append(" trim=\"true\"");
                    ret.append(ext);
                    ret.append("/>");
                }else if(mode.equals("view"))
                {
                    ret.append("<span _id=\""+name+"\" name=\""+name+"\">"+value+"</span>");
                }
            }

        }else if(prop.isObjectProperty())
        {
            if(name.startsWith("has"))
            {
            
            }else
            {
                SemanticObject value=null;
                String aux=request.getParameter(prop.getName());
                if(aux!=null)value=SemanticObject.createSemanticObject(aux);
                else value=obj.getObjectProperty(prop);
                ret.append("<span>");
                if(value!=null)
                {
                    ret.append("<a href=\"?suri="+value.getEncodedURI()+"\" onclick=\"addNewTab('"+value.getURI()+"', null, '"+value.getDisplayName(lang)+"');return false;\">"+value.getDisplayName()+"</a>");
                }
                ret.append("</span>");
            }
        }
        //System.out.println("ret:"+ret);
        return ret.toString();
    }

}
