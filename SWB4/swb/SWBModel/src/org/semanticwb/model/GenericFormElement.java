package org.semanticwb.model;

import java.util.Iterator;
import java.util.StringTokenizer;
import org.semanticwb.model.*;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

public class GenericFormElement extends FormElementBase implements FormElement 
{
    public GenericFormElement()
    {
        super(null);
    }

    public GenericFormElement(SemanticObject obj)
    {
        super(obj);
    }
    
    @Override
    public String render(SemanticObject obj, SemanticProperty prop, String type, String mode, String lang)
    {
        if(obj==null)obj=new SemanticObject();
        String ret="";
        if(type.endsWith("iphone"))
        {
            ret=renderIphone(obj, prop, type, mode, lang);
        }else
        {
            ret=renderXHTML(obj, prop, type, mode, lang);
        }
        return ret;
    }    
    
    public String renderIphone(SemanticObject obj, SemanticProperty prop, String type, String mode, String lang)
    {
        return "";
    }
    
    public String renderXHTML(SemanticObject obj, SemanticProperty prop, String type, String mode, String lang)
    {
        String ret="";
        String name=prop.getName();
        String label=prop.getDisplayName(lang);
        SemanticObject sobj=prop.getDisplayProperty();
        boolean required=false;
        String pmsg=null;
        String imsg=null;
        String selectValues=null;
        if(sobj!=null)
        {
            DisplayProperty dobj=new DisplayProperty(sobj);
            required=dobj.isRequired();
            pmsg=dobj.getPromptMessage();
            imsg=dobj.getInvalidMessage();
            selectValues=dobj.getSelectValues(lang);
        }
        
        if(imsg==null)
        {
            if(prop.isDataTypeProperty() && prop.isInt())
            {
                imsg="Número invalido.";
                if(lang.equals("en"))
                {
                    imsg="Invalid number.";
                }
            }
            
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
        
        String reqtxt=" &nbsp;";
        if(required)reqtxt=" <em>*</em>";
        
        if(prop.isDataTypeProperty())
        {
            //System.out.println("selectValues:"+selectValues);
            if(selectValues!=null)
            {
//                String value=obj.getProperty(prop);
//		ret= "  <fieldset>"
//                    +"      <legend>"+label+reqtxt+"</legend>";
//                StringTokenizer st=new StringTokenizer(selectValues,"|");
//                while(st.hasMoreTokens())
//                {
//                    String tok=st.nextToken();
//                    int ind=tok.indexOf(':');
//                    String id=tok;
//                    String val=tok;
//                    if(ind>0)
//                    {
//                        id=tok.substring(0,ind);
//                        val=tok.substring(ind+1);
//                    }
//                    ret+="      <label for=\""+name+id+"\"><input dojoType=\"dijit.form.RadioButton\" _id=\""+name+id+"\" name=\""+name+"\"";
//                    if(id.equals(value))ret+=" checked=\"checked\"";
//                    ret+=" value=\""+id+"\" type=\"radio\" />"+val+"</label>";
//                }
//                ret+="  </fieldset>";
                String value=obj.getProperty(prop);
                ret="<label for=\""+name+"\">"+label + reqtxt + "</label><span>";
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
                    ret+=" <label for=\""+name+id+"\"><input dojoType=\"dijit.form.RadioButton\" _id=\""+name+id+"\" name=\""+name+"\"";
                    if(id.equals(value))ret+=" checked=\"checked\"";
                    ret+=" value=\""+id+"\" type=\"radio\" />"+val+"</label>";
                }
                ret+="</span>";
                
            }else if(prop.isBoolean())
            {
                String checked="";
                boolean value=obj.getBooleanProperty(prop);
                if(value)checked="checked=\"checked\"";
                ret="<label for=\""+name+"\">"+label
                    + reqtxt
                    + "</label> <input type=\"checkbox\" id_=\""+name+"\" name=\""+name+"\" "+checked
                    + " dojoType=\"dijit.form.CheckBox\""
                    + " required=\""+required+"\""
//                    + " propercase=\"true\""
                    + " promptMessage=\""+pmsg+"\""
                    + " invalidMessage=\""+imsg+"\""
//                    + " trim=\"true\""
                + "/>";
            }else if(prop.isDateTime())
            {
                String value=obj.getProperty(prop);
                if(value==null)value="";
                ret="<label for=\""+name+"\">"+label
                    + reqtxt
                    + "</label>"
                    + " <span _id=\""+name+"\" name=\""+name+"\">"+value+"</span>";
            }else if(prop.isInt() || prop.isLong())
            {
                String value=obj.getProperty(prop);
                if(value==null)value="";
                if(mode.equals("edit") || mode.equals("create") )
                {
                    ret="<label for=\""+name+"\">"+label
                        + reqtxt
                        + "</label> <input _id=\""+name+"\" name=\""+name+"\" value=\""+value+"\""
                        + " dojoType=\"dijit.form.ValidationTextBox\""
                        + " regExp=\"\\d+\""
                        + " required=\""+required+"\""
    //                    + " propercase=\"true\""
                        + " promptMessage=\""+pmsg+"\""
                        + " invalidMessage=\""+imsg+"\""
    //                    + " trim=\"true\""
                    + "/>";
//                }else if(mode.equals("edit"))
//                {
//                    ret="<label for=\""+name+"\">"+label
//                        + reqtxt
//                        + "</label>"
//                        + "<p><span _id=\""+name+"\" dojoType=\"dijit.InlineEditBox\" editor=\"dijit.form.ValidationTextBox\" editorParams=\"{regExp:'\\\\d+'}\" autoSave=\"false\" name=\""+name+"\">"+value+"</span></p>";
                }else if(mode.equals("view"))
                {
                    ret="<label for=\""+name+"\">"+label
                        + reqtxt
                        + "</label>"
                        + " <span _id=\""+name+"\" name=\""+name+"\">"+value+"</span>";
                }
            }else
            {
                String value=obj.getProperty(prop);
                if(value==null)value="";
                if(mode.equals("edit") || mode.equals("create") )
                {
                    ret="<label for=\""+name+"\">"+label
                        + reqtxt
                        + "</label> <input _id=\""+name+"\" name=\""+name+"\" value=\""+value+"\""
                        + " dojoType=\"dijit.form.ValidationTextBox\""
                        + " required=\""+required+"\""
    //                    + " propercase=\"true\""
                        + " promptMessage=\""+pmsg+"\""
                        + " invalidMessage=\""+imsg+"\""
                        + " trim=\"true\""
                    + "/>";
                }else if(mode.equals("view"))
                {
                    ret="<label for=\""+name+"\">"+label
                        + reqtxt
                        + "</label>"
                        + " <span _id=\""+name+"\" name=\""+name+"\">"+value+"</span>";
                }
            }

        }else if(prop.isObjectProperty())
        {
            if(name.startsWith("has"))
            {
            
            }else
            {
                SemanticObject value=obj.getObjectProperty(prop);
                ret="<label for=\""+name+"\">"+label + reqtxt + "</label>";
                ret+=" <span>";
                if(value!=null)
                {
                    ret+="<a href=\"?suri="+value.getEncodedURI()+"\">"+value.getDisplayName()+"</a>";
                }
                ret+="</span>";
            }
        }
        return ret;
    }

    @Override
    public void validate(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang) throws FormValidateException 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang) 
    {
        //System.out.println("process...:"+obj.getURI()+" "+prop.getURI());
        if(prop.isDataTypeProperty())
        {
            String value=request.getParameter(prop.getName());
            if(prop.isBoolean())
            {
                if(value!=null)obj.setBooleanProperty(prop, true);
                else obj.setBooleanProperty(prop, false);
            }else
            {
                if(value!=null)
                {
                    if(value.length()>0)
                    {
                        if(prop.isFloat())obj.setFloatProperty(prop, Float.parseFloat(value));
                        if(prop.isInt())obj.setLongProperty(prop, Integer.parseInt(value));
                        if(prop.isString())obj.setProperty(prop, value);
                    }else
                    {
                        obj.removeProperty(prop);
                    }
                }
            }
        }
    }

}
