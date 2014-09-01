/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.model.base;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.FormElement;
import org.semanticwb.model.FormElementURL;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.GenericObject;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

// TODO: Auto-generated Javadoc
/**
 * The Class FormElementBase.
 * 
 * @author Jei
 */
public class FormElementBase extends GenericObjectBase implements FormElement, GenericObject
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(FormElementBase.class);

    /** The attributes. */
    protected HashMap attributes=null;
    
    /** The model. */
    private SemanticModel model=null;
    
    /** The filter html tags. */
    private boolean filterHTMLTags=true;

    //private String label=null;
    
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");    

    private Object formMgr=null;
    
    /**
     * Instantiates a new form element base.
     * 
     * @param obj the obj
     */
    public FormElementBase(SemanticObject obj)
    {
        super(obj);
        attributes=new HashMap();
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.FormElement#validate(javax.servlet.http.HttpServletRequest, org.semanticwb.platform.SemanticObject, org.semanticwb.platform.SemanticProperty)
     */
    @Override
    public void validate(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName) throws FormValidateException
    {

    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.FormElement#process(javax.servlet.http.HttpServletRequest, org.semanticwb.platform.SemanticObject, org.semanticwb.platform.SemanticProperty)
     */
    @Override
    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName)
    {
        String smode=request.getParameter("smode");
        //System.out.println("process...:"+obj.getURI()+" "+prop.getURI()+" "+smode);
        //if(smode!=null && smode.equals("create") && !prop.isRequired())return;
        boolean needDP=!(propName.indexOf('.')>0);
        if(needDP && prop.getDisplayProperty()==null)return;
        if(prop.isDataTypeProperty())
        {
            String value=request.getParameter(propName);
            String old=obj.getProperty(prop);
            //System.out.println("com:"+old+"-"+value+"-");
            if(prop.isBoolean())
            {
                //System.out.println("prop:"+prop);
                //System.out.println("value:"+value);
                //System.out.println("old:"+old);
                if(value!=null && (value.equals("true") || value.equals("on")) && (old==null || old.equals("false")))obj.setBooleanProperty(prop, true);
                else if((value==null || value.equals("false")) && old!=null && old.equals("true")) obj.setBooleanProperty(prop, false);
            } else if (prop.isDateTime()) {
                value = request.getParameter(propName+"_date");
                String tvalue = request.getParameter(propName+"_time");
                
                if (value != null && tvalue != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                    try {
                        Date dt = sdf.parse(value+tvalue);
                        Timestamp ts = new Timestamp(dt.getTime());
                        obj.setDateTimeProperty(prop, ts);
                    } catch (ParseException ex) {
                        log.error(ex);
                    }
                }
            } else
            {
                if(value!=null)
                {
                    if(value.length()>0 && !value.equals(old))
                    {
                        //System.out.println("old:"+old+" value:"+value);
//                        for(int x=0;x<value.length();x++)
//                        {
//                            System.out.print((int)value.charAt(x)+" ");
//                        }
                        if(prop.isFloat())obj.setFloatProperty(prop, Float.parseFloat(value));
                        if(prop.isDouble())obj.setDoubleProperty(prop, Double.parseDouble(value));
                        if(prop.isInt() || prop.isShort() || prop.isByte())obj.setIntProperty(prop, Integer.parseInt(value));
                        if(prop.isLong())obj.setLongProperty(prop, Long.parseLong(value));
                        try
                        {
                            if(prop.isDate())obj.setDateProperty(prop, format.parse(value));
                        }catch(Exception e){
                            log.error(e);
                        }
                        if(prop.isString())
                        {
                            if(isFilterHTMLTags())
                                obj.setProperty(prop, SWBUtils.XML.replaceXMLChars(value));
                            else
                                obj.setProperty(prop, value);
                        }
                    }else if(value.length()==0 && old!=null)
                    {
                        obj.removeProperty(prop);
                    }
                }
            }
        }else if(prop.isObjectProperty())
        {
            String name=propName;
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
                        SemanticObject aux=null;
                        if(uri.length()>0)
                        {
                            aux=SWBPlatform.getSemanticMgr().getOntology().getSemanticObject(uri);
                        }
                        //System.out.println("uri:"+uri+" ("+aux+")");
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


    /* (non-Javadoc)
     * @see org.semanticwb.model.FormElement#renderElement(javax.servlet.http.HttpServletRequest, org.semanticwb.platform.SemanticObject, org.semanticwb.platform.SemanticProperty, java.lang.String, java.lang.String, java.lang.String)
     */
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type, String mode, String lang)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.FormElement#setAttribute(java.lang.String, java.lang.String)
     */
    public void setAttribute(String name, String value)
    {
        if(value!=null)
        {
            attributes.put(name, value);
        }else
        {
            attributes.remove(name);
        }
    }

    /**
     * Gets the attributes.
     * 
     * @return the attributes
     */
    public String getAttributes()
    {
        StringBuffer ret=new StringBuffer();
        Iterator<Entry<String,String>> it=attributes.entrySet().iterator();
        while(it.hasNext())
        {
            Entry entry=it.next();
            ret.append(entry.getKey()+"="+"\""+entry.getValue()+"\"");
            if(it.hasNext())ret.append(" ");
        }
        return ret.toString();
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.FormElement#getRenderURL(org.semanticwb.platform.SemanticObject, org.semanticwb.platform.SemanticProperty, java.lang.String, java.lang.String, java.lang.String)
     */
    public FormElementURL getRenderURL(SemanticObject obj, SemanticProperty prop, String type, String mode, String lang)
    {
        return new FormElementURL(this,obj, prop, FormElementURL.URLTYPE_RENDER,type, mode, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.FormElement#getValidateURL(org.semanticwb.platform.SemanticObject, org.semanticwb.platform.SemanticProperty)
     */
    public FormElementURL getValidateURL(SemanticObject obj, SemanticProperty prop)
    {
        return new FormElementURL(this,obj, prop, FormElementURL.URLTYPE_VALIDATE,null, null, null);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.FormElement#getProcessURL(org.semanticwb.platform.SemanticObject, org.semanticwb.platform.SemanticProperty)
     */
    public FormElementURL getProcessURL(SemanticObject obj, SemanticProperty prop)
    {
        return new FormElementURL(this,obj, prop, FormElementURL.URLTYPE_PROCESS,null, null, null);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.FormElement#getLocaleString(java.lang.String, java.lang.String)
     */
    public String getLocaleString(String key, String lang)
    {
        String ret=null;
        try
        {
            ret=SWBUtils.TEXT.getLocaleString(this.getClass().getName(), key, new Locale(lang),this.getClass().getClassLoader());
        }catch(Exception e){log.error(e);}
        return ret;
    }

    /**
     * Gets the model.
     * 
     * @return the model
     */
    public SemanticModel getModel() {
        return model;
    }

    /**
     * Sets the model.
     * 
     * @param model the model to set
     */
    public void setModel(SemanticModel model) {
        this.model = model;
    }

    /**
     * Checks if is filter html tags.
     * 
     * @return the filterHTMLTags
     */
    public boolean isFilterHTMLTags() {
        return filterHTMLTags;
    }

    /**
     * Sets the filter html tags.
     * 
     * @param filterHTMLTags the filterHTMLTags to set
     */
    public void setFilterHTMLTags(boolean filterHTMLTags) {
        this.filterHTMLTags = filterHTMLTags;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.model.FormElement#renderLabel(javax.servlet.http.HttpServletRequest, org.semanticwb.platform.SemanticObject, org.semanticwb.platform.SemanticProperty, java.lang.String, java.lang.String, java.lang.String)
     */
    public String renderLabel(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type, String mode, String lang, String label)
    {
        String ret="";
        String name=propName;
        //String label=this.label;
        if(label==null)label=prop.getDisplayName(lang);
        SemanticObject sobj=prop.getDisplayProperty();
        boolean required=prop.isRequired();

        String reqtxt=" &nbsp;";
        if(!mode.equals("filter") && required)reqtxt=" <em>*</em>";

        ret="<label for=\""+name+"\">"+label + reqtxt + "</label>";
        return ret;
    }    
    
    public String renderLabel(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type, String mode, String lang)
    {
        return renderLabel(request, obj, prop, prop.getName(), type, mode, lang,null);
    }

    public String renderLabel(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang) {
        return renderLabel(request, obj, prop, prop.getName(), type, mode, lang);
    }

    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang) {
        return renderElement(request, obj, prop, prop.getName(), type, mode, lang);
    }

    public void validate(HttpServletRequest request, SemanticObject obj, SemanticProperty prop) throws FormValidateException {
        validate(request, obj, prop, prop.getName());
    }

    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop) {
        process(request, obj, prop, prop.getName());
    }

//    public void setLabel(String label)
//    {
//        this.label=label;
//    }

    @Override
    public Object getFormMgr() {
        return formMgr;
    }

    @Override
    public void setFormMgr(Object formMgr) {
        this.formMgr=formMgr;
    }

}
