/*
 * XFText.java
 *
 * Created on 1 de julio de 2008, 06:58 PM
 */

package org.semanticwb.portal.xforms.ui;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.xforms.ElementsProp;
import org.semanticwb.xforms.lib.XformsBaseImp;

/**
 *
 * @author  jorge.jimenez
 */
public class XFText extends XformsBaseImp
{
    private static Logger log = SWBUtils.getLogger(XFText.class);
    protected String value=null;
    protected int size=-1;
    protected int maxlength=-1;
    protected String accesskey=null;
    protected String align=null;
    protected boolean isdisabled=false;
    protected boolean isreadonly=false;
    protected boolean isautocomplete=true;
    protected int width=-1;
    protected int height=-1;
    protected String alert=null;
    protected String constraint=null;
    
    protected ElementsProp elementsProp=null;
   
    public XFText(ElementsProp elementsProp){
        this.elementsProp=elementsProp;
        setRDFAttributes();
    }
    
    // Sets
    
    public void setAccessKey(String accesskey){
        this.accesskey=accesskey;
    }
    
    public void setSize(int size){
        this.size=size;
    }
    
    public void setMaxLength(int maxlength){
        this.maxlength=maxlength;
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
    
    public void setAlign(String align){
        this.align=align;
    }
    
    public void setDisabled(boolean isdisabled){
        this.isdisabled=isdisabled;
    }
    
    public void setReadOnly(boolean isreadonly){
        this.isreadonly=isreadonly;
    }
    
    public void setAutoComplete(boolean isautocomplete){
        this.isautocomplete=isautocomplete;
    }
    
    public void setConstraint(String constraint)
    {
        this.constraint=constraint;
    }
    
    // Gets
    
    public String getAccessKey(){
        return accesskey;
    }
    
    public int getSize(int size){
        return size;
    }
    
    public int getMaxLength(){
        return maxlength;
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
    
    public String getAlign(){
        return align;
    }
    
    private boolean getDisabled(){
        return isdisabled;
    }
    
    public boolean getReadOnly(){
        return isreadonly;
    }
    
    public boolean getAutoComplete(){
        return isautocomplete;
    }
    
    public String getConstraint()
    {
        return constraint;
    }
    
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
    
    @Override
    public void setXml(String xml) {
        this.xml=xml;
    }
}
