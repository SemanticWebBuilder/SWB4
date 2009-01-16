/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.model.base;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.FormElement;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.GenericObject;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


/**
 *
 * @author Jei
 */
public class FormElementBase extends GenericObjectBase implements FormElement, GenericObject
{
    public FormElementBase(SemanticObject obj)
    {
        super(obj);
    }

    public void validate(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang) throws FormValidateException 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang)
    {
        //System.out.println("process...:"+obj.getURI()+" "+prop.getURI());
        if(prop.isDataTypeProperty())
        {
            String value=request.getParameter(prop.getName());
            String old=obj.getProperty(prop);
            //System.out.println("com:"+old+"-"+value+"-");
            if(prop.isBoolean())
            {
                if(value!=null && old.equals("false"))obj.setBooleanProperty(prop, true);
                else if(value==null && (old==null || old.equals("true"))) obj.setBooleanProperty(prop, false);
            }else
            {
                if(value!=null)
                {
                    if(value.length()>0 && !value.equals(old))
                    {
                        if(prop.isFloat())obj.setFloatProperty(prop, Float.parseFloat(value));
                        if(prop.isInt())obj.setLongProperty(prop, Integer.parseInt(value));
                        if(prop.isString())obj.setProperty(prop, value);
                    }else if(value.length()==0 && old!=null)
                    {
                        obj.removeProperty(prop);
                    }
                }
            }
        }else if(prop.isObjectProperty())
        {
            String name=prop.getName();
            String uri=request.getParameter(name);
            if(uri!=null)
            {
                //System.out.println("**** obj:"+obj+" uri:"+uri+" name:"+name);
                if(name.startsWith("has"))
                {
                    //TODO:
                }else
                {
                    String ouri="";
                    SemanticObject old=obj.getObjectProperty(prop);
                    if(old!=null)ouri=old.getURI();
                    //System.out.println("uri:"+uri+" "+ouri);
                    if(!(""+uri).equals(""+ouri))
                    {
                        SemanticObject aux=SWBPlatform.getSemanticMgr().getOntology().getSemanticObject(uri);
                        if(aux!=null)
                        {
                            obj.setObjectProperty(prop, aux);
                        }else
                        {
                            obj.removeProperty(prop);
                        }
                    }
                }
            }
        }
    }

    public String renderLabel(SemanticObject obj, SemanticProperty prop, String type, String mode, String lang)
    {
        String ret="";
        String name=prop.getName();
        String label=prop.getDisplayName(lang);
        SemanticObject sobj=prop.getDisplayProperty();
        boolean required=prop.isRequired();

        String reqtxt=" &nbsp;";
        if(required)reqtxt=" <em>*</em>";

        ret="<label for=\""+name+"\">"+label + reqtxt + "</label>";
        return ret;
    }

    public String renderElement(SemanticObject obj, SemanticProperty prop, String type, String mode, String lang) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
