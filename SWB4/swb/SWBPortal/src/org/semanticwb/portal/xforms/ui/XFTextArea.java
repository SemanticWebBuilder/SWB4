/*
 * XFTextArea.java
 *
 * Created on 1 de julio de 2008, 06:58 PM
 */

package org.semanticwb.portal.xforms.ui;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.xforms.ElementsProp;
import org.semanticwb.xforms.lib.XformsBaseImp;
import org.semanticwb.xforms.drop.RDFElement;

/**
 *
 * @author  jorge.jimenez
 */
public class XFTextArea extends XformsBaseImp 
{
    private static Logger log = SWBUtils.getLogger(XFTextArea.class);
    private String accesskey=null;
    private int cols=-1;
    private int rows=-1;
    private boolean isdisabled=false;
    private boolean isreadonly=false;
    private int width=-1;
    private int height=-1;
    private String wrap=null;
    private String value=null;
    protected String alert=null;
    protected String constraint=null;
    protected boolean incremental=false;
    protected String mediatype=null;
    protected ElementsProp elementsProp=null;
    
    public XFTextArea(ElementsProp elementsProp){
        this.elementsProp=elementsProp;
        setRDFAttributes();
    }
    
    // Sets
    
    public void setAccessKey(String accesskey){
        this.accesskey=accesskey;
    }
    
    public void setWidth(int width){
        this.width=width;
    }
    
    public void setHeight(int height){
        this.height=height;
    }
    
    public void setValue(String value){
        this.value=value;
    }
    
    public void setDisabled(boolean isdisabled){
        this.isdisabled=isdisabled;
    }
    
    public void setReadOnly(boolean isreadonly){
        this.isreadonly=isreadonly;
    }
    
    public void setConstraint(String constraint) {
        this.constraint=constraint;
    }
    
    public void setIncremental(boolean incremental) {
        this.incremental=incremental;
    }
    
    public void setCols(int cols) {
        this.cols=cols;
    }
    
    public void setRows(int rows) {
        this.rows=rows;
    }
    
    public void setWrap(String wrap) {
        this.wrap=wrap;
    }
    
    // Gets
    
    public String getAccessKey(){
        return accesskey;
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public String getValue(){
        return value;
    }
    
    private boolean getDisabled(){
        return isdisabled;
    }
    
    public boolean getReadOnly(){
        return isreadonly;
    }
    
    public String getConstraint() {
        return constraint;
    }
    
    public boolean isIncremental() {
        return incremental;
    }
    
    public int getCols() {
        return cols;
    }
    
    public int getRows() {
        return rows;
    }
    
    public String getWrap() {
        return wrap;
    }
    
    public void setRDFAttributes(){
        if(elementsProp.getId()!=null) {
            id=elementsProp.getId();
        }
        if(elementsProp.getLabel()!=null) {
            label=elementsProp.getLabel();
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
        incremental=elementsProp.isIncremental();
        if(elementsProp.getCols()>0) {
            cols=elementsProp.getCols();
        }
        if(elementsProp.getRows()>0) {
            rows=elementsProp.getRows();
        }
        if(elementsProp.getWrap()!=null) {
            wrap=elementsProp.getWrap();
        }
        if(elementsProp.getMediatype()!=null) {
            mediatype=elementsProp.getMediatype();
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
    
    @Override
    public void setXml(String xml) {
        this.xml=xml;
    }
}
