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
package org.semanticwb.portal.xforms.ui;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.xforms.ElementsProp;
import org.semanticwb.xforms.lib.XformsBaseImp;

// TODO: Auto-generated Javadoc
/**
 * The Class XFText.
 * 
 * @author  jorge.jimenez
 */
public class XFText extends XformsBaseImp
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(XFText.class);
    
    /** The value. */
    protected String value=null;
    
    /** The size. */
    protected int size=-1;
    
    /** The maxlength. */
    protected int maxlength=-1;
    
    /** The accesskey. */
    protected String accesskey=null;
    
    /** The align. */
    protected String align=null;
    
    /** The isdisabled. */
    protected boolean isdisabled=false;
    
    /** The isreadonly. */
    protected boolean isreadonly=false;
    
    /** The isautocomplete. */
    protected boolean isautocomplete=true;
    
    /** The width. */
    protected int width=-1;
    
    /** The height. */
    protected int height=-1;
    
    /** The alert. */
    protected String alert=null;
    
    /** The constraint. */
    protected String constraint=null;
    
    /** The elements prop. */
    protected ElementsProp elementsProp=null;
   
    /**
     * Instantiates a new xF text.
     * 
     * @param elementsProp the elements prop
     */
    public XFText(ElementsProp elementsProp){
        this.elementsProp=elementsProp;
        setRDFAttributes();
    }
    
    // Sets
    
    /**
     * Sets the access key.
     * 
     * @param accesskey the new access key
     */
    public void setAccessKey(String accesskey){
        this.accesskey=accesskey;
    }
    
    /**
     * Sets the size.
     * 
     * @param size the new size
     */
    public void setSize(int size){
        this.size=size;
    }
    
    /**
     * Sets the max length.
     * 
     * @param maxlength the new max length
     */
    public void setMaxLength(int maxlength){
        this.maxlength=maxlength;
    }
    
    /**
     * Sets the width.
     * 
     * @param width the new width
     */
    public void setWidth(int width){
        this.width=width;
    }
    
    /**
     * Sets the height.
     * 
     * @param height the new height
     */
    public void setHeight(int height){
        this.height=height;
    }
    
    /**
     * Sets the value.
     * 
     * @param value the new value
     */
    public void setValue(String value){
        this.value=value;
    }
    
    /**
     * Sets the align.
     * 
     * @param align the new align
     */
    public void setAlign(String align){
        this.align=align;
    }
    
    /**
     * Sets the disabled.
     * 
     * @param isdisabled the new disabled
     */
    public void setDisabled(boolean isdisabled){
        this.isdisabled=isdisabled;
    }
    
    /**
     * Sets the read only.
     * 
     * @param isreadonly the new read only
     */
    public void setReadOnly(boolean isreadonly){
        this.isreadonly=isreadonly;
    }
    
    /**
     * Sets the auto complete.
     * 
     * @param isautocomplete the new auto complete
     */
    public void setAutoComplete(boolean isautocomplete){
        this.isautocomplete=isautocomplete;
    }
    
    /**
     * Sets the constraint.
     * 
     * @param constraint the new constraint
     */
    public void setConstraint(String constraint)
    {
        this.constraint=constraint;
    }
    
    // Gets
    
    /**
     * Gets the access key.
     * 
     * @return the access key
     */
    public String getAccessKey(){
        return accesskey;
    }
    
    /**
     * Gets the size.
     * 
     * @param size the size
     * @return the size
     */
    public int getSize(int size){
        return size;
    }
    
    /**
     * Gets the max length.
     * 
     * @return the max length
     */
    public int getMaxLength(){
        return maxlength;
    }
    
    /**
     * Gets the width.
     * 
     * @return the width
     */
    public int getWidth(){
        return width;
    }
    
    /**
     * Gets the height.
     * 
     * @return the height
     */
    public int getHeight(){
        return height;
    }
    
    /**
     * Gets the value.
     * 
     * @return the value
     */
    public String getValue(){
        return value;
    }
    
    /**
     * Gets the align.
     * 
     * @return the align
     */
    public String getAlign(){
        return align;
    }
    
    /**
     * Gets the disabled.
     * 
     * @return the disabled
     */
    private boolean getDisabled(){
        return isdisabled;
    }
    
    /**
     * Gets the read only.
     * 
     * @return the read only
     */
    public boolean getReadOnly(){
        return isreadonly;
    }
    
    /**
     * Gets the auto complete.
     * 
     * @return the auto complete
     */
    public boolean getAutoComplete(){
        return isautocomplete;
    }
    
    /**
     * Gets the constraint.
     * 
     * @return the constraint
     */
    public String getConstraint()
    {
        return constraint;
    }
    
    /**
     * Sets the rdf attributes.
     */
    public void setRDFAttributes(){
        if(elementsProp.getId()!=null) {
            id=elementsProp.getId();
        }
        if(elementsProp.getLabel()!=null) {
            label=elementsProp.getLabel();
        }
        if(elementsProp.getSize()>0) {
            size=elementsProp.getSize();
        }
        if(elementsProp.getMaxLength()>0) {
            maxlength=elementsProp.getMaxLength();
        }
        isrequired=elementsProp.isRequired();
        if(elementsProp.getSType()!=null) {
            subType=elementsProp.getSType();
        }
        if(elementsProp.getConstraint()!=null) {
            constraint=elementsProp.getConstraint();
        }
        if(elementsProp.getHelp()!=null) {
            help=elementsProp.getHelp();
        }
        if(elementsProp.getValue()!=null) {
            value=elementsProp.getValue();
        }         
        if(elementsProp.getAlert()!=null) {
            alert=elementsProp.getAlert();
        }
        if(elementsProp.getHint()!=null) {
            hint=elementsProp.getHint();
        }
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBaseImp#getXmlBind()
     */
    /**
     * Gets the xml bind.
     * 
     * @return the xml bind
     */
    @Override
   public String getXmlBind() 
    {
        StringBuffer strbXml=new StringBuffer();
        strbXml.append("<bind id=\"bind_"+id+"\" nodeset=\""+id+"\"");
        if(isrequired) {
            strbXml.append(" required=\"true()\" "); 
        }
        if(constraint!=null) {
            strbXml.append(" constraint=\""+constraint+"\" "); 
        }
        if(subType!=null) {
            strbXml.append(" type=\""+subType+"\" "); 
        }
        strbXml.append("/>");
        return strbXml.toString();
    }    
    
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBaseImp#getXml()
     */
    /**
     * Gets the xml.
     * 
     * @return the xml
     */
    @Override
    public String getXml() 
    {
        StringBuffer strbXml=new StringBuffer();
        try {
            strbXml.append("<input id=\""+id+"\" bind=\"bind_"+id+"\">");
            
            if(label!=null) 
            {
                strbXml.append("<label>");
                strbXml.append(label.trim());
                strbXml.append("</label>");
            }
            if(alert!=null) 
            {
                strbXml.append("<alert>");
                strbXml.append(alert.trim());
                strbXml.append("</alert>");
            }
            if(hint!=null) 
            {
                strbXml.append("<hint>");
                strbXml.append(hint.trim());
                strbXml.append("</hint>");
            }
            strbXml.append("</input>");
        }
        catch(Exception e) {log.error(e); }
        return strbXml.toString();
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBaseImp#setXml(java.lang.String)
     */
    /**
     * Sets the xml.
     * 
     * @param xml the new xml
     */
    @Override
    public void setXml(String xml) {
        this.xml=xml;
    }
}
