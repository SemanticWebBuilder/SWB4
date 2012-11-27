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
package org.semanticwb.xforms.ui;

import org.semanticwb.xforms.lib.XformsBaseImp;
import org.semanticwb.xforms.drop.RDFElement;
import org.semanticwb.SWBUtils;
import org.semanticwb.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class XFTextArea.
 * 
 * @author  jorge.jimenez
 */
public class XFTextArea extends XformsBaseImp 
{
    
    /** The log. */
    private static Logger log=SWBUtils.getLogger(XFTextArea.class);
    
    /** The accesskey. */
    private String accesskey=null;
    
    /** The cols. */
    private int cols=-1;
    
    /** The rows. */
    private int rows=-1;
    
    /** The isdisabled. */
    private boolean isdisabled=false;
    
    /** The isreadonly. */
    private boolean isreadonly=false;
    
    /** The width. */
    private int width=-1;
    
    /** The height. */
    private int height=-1;
    
    /** The wrap. */
    private String wrap=null;
    
    /** The value. */
    private String value=null;
    
    /** The alert. */
    protected String alert=null;
    
    /** The constraint. */
    protected String constraint=null;
    
    /** The incremental. */
    protected boolean incremental=false;
    
    /** The mediatype. */
    protected String mediatype=null;
    
    /** The rdf element. */
    protected RDFElement rdfElement=null;
    
    /**
     * Instantiates a new xF text area.
     * 
     * @param rdfElement the rdf element
     */
    public XFTextArea(RDFElement rdfElement){
        this.rdfElement=rdfElement;
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
     * Sets the constraint.
     * 
     * @param constraint the new constraint
     */
    public void setConstraint(String constraint) {
        this.constraint=constraint;
    }
    
    /**
     * Sets the incremental.
     * 
     * @param incremental the new incremental
     */
    public void setIncremental(boolean incremental) {
        this.incremental=incremental;
    }
    
    /**
     * Sets the cols.
     * 
     * @param cols the new cols
     */
    public void setCols(int cols) {
        this.cols=cols;
    }
    
    /**
     * Sets the rows.
     * 
     * @param rows the new rows
     */
    public void setRows(int rows) {
        this.rows=rows;
    }
    
    /**
     * Sets the wrap.
     * 
     * @param wrap the new wrap
     */
    public void setWrap(String wrap) {
        this.wrap=wrap;
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
     * Gets the constraint.
     * 
     * @return the constraint
     */
    public String getConstraint() {
        return constraint;
    }
    
    /**
     * Checks if is incremental.
     * 
     * @return true, if is incremental
     */
    public boolean isIncremental() {
        return incremental;
    }
    
    /**
     * Gets the cols.
     * 
     * @return the cols
     */
    public int getCols() {
        return cols;
    }
    
    /**
     * Gets the rows.
     * 
     * @return the rows
     */
    public int getRows() {
        return rows;
    }
    
    /**
     * Gets the wrap.
     * 
     * @return the wrap
     */
    public String getWrap() {
        return wrap;
    }
    
    /**
     * Sets the rdf attributes.
     */
    public void setRDFAttributes(){
        if(rdfElement.getId()!=null) {
            id=rdfElement.getId();
        }
        if(rdfElement.getLabel()!=null) {
            label=rdfElement.getLabel();
        }
        isrequired=rdfElement.isRequired();
        if(rdfElement.getSType()!=null) {
            subType=rdfElement.getSType();
        }
        if(rdfElement.getConstraint()!=null) {
            constraint=rdfElement.getConstraint();
        }
        if(rdfElement.getHelp()!=null) {
            help=rdfElement.getHelp();
        }
        incremental=rdfElement.isIncremental();
        if(rdfElement.getCols()>0) {
            cols=rdfElement.getCols();
        }
        if(rdfElement.getRows()>0) {
            rows=rdfElement.getRows();
        }
        if(rdfElement.getWrap()!=null) {
            wrap=rdfElement.getWrap();
        }
        if(rdfElement.getMediatype()!=null) {
            mediatype=rdfElement.getMediatype();
        }
        if(rdfElement.getValue()!=null) {
            value=rdfElement.getValue();
        }
       
        
        if(rdfElement.getAlert()!=null) {
            alert=rdfElement.getAlert();
        }
        if(rdfElement.getHint()!=null) {
            hint=rdfElement.getHint();
        }
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBaseImp#getXmlBind()
     */
    @Override
    public String getXmlBind() {
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
    @Override
    public String getXml() {
        StringBuffer strbXml=new StringBuffer();
        try {
            strbXml.append("<textarea id=\""+id+"\" bind=\"bind_"+id+"\"");
            
            if(cols>0)
            {
                strbXml.append(" rows=\""+rows+"\"");
            }
            
            if(rows>0)
            {
                strbXml.append(" cols=\""+cols+"\"");
            } 
            
            if(wrap!=null)
            {
                strbXml.append(" wrap=\""+wrap+"\"");
            } 
            
            if(incremental)
            {
                strbXml.append(" incremental=\""+incremental+"\"");
            }
            
            if(mediatype!=null)
            {
                strbXml.append(" mediatype=\""+mediatype+"\"");
            }
            
            strbXml.append(">");
                        
            if(label!=null) {
                strbXml.append("<label>");
                strbXml.append(label.trim());
                strbXml.append("</label>");
            }
            if(alert!=null) {
                strbXml.append("<alert>");
                strbXml.append(alert.trim());
                strbXml.append("</alert>");
            }
            if(hint!=null) {
                strbXml.append("<hint>");
                strbXml.append(hint.trim());
                strbXml.append("</hint>");
            }
            strbXml.append("</textarea>");
        }
        catch(Exception e) {log.error(e); }
        return strbXml.toString();
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBaseImp#setXml(java.lang.String)
     */
    @Override
    public void setXml(String xml) {
        this.xml=xml;
    }
}
