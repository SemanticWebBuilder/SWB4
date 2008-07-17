/*
 * XFSecret.java
 *
 * Created on 1 de julio de 2008, 06:58 PM
 */

package org.semanticwb.xforms.ui;

import java.sql.Array;
import org.w3c.dom.*;
import org.semanticwb.xforms.lib.XformsBaseImp;
import org.semanticwb.xforms.drop.RDFElement;

/**
 *
 * @author  jorge.jimenez
 */
public class XFSecret extends XformsBaseImp
{
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
    
    protected RDFElement rdfElement=null;
   
    public XFSecret(RDFElement rdfElement){
        this.rdfElement=rdfElement;
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
        if(rdfElement.getId()!=null) id=rdfElement.getId();
        if(rdfElement.getLabel()!=null) label=rdfElement.getLabel();
        if(rdfElement.getSize()>0) size=rdfElement.getSize();
        if(rdfElement.getMaxLength()>0) maxlength=rdfElement.getMaxLength();
        isrequired=rdfElement.isRequired();
        if(rdfElement.getSType()!=null) subType=rdfElement.getSType();
        if(rdfElement.getConstraint()!=null) constraint=rdfElement.getConstraint();
        if(rdfElement.getHelp()!=null) help=rdfElement.getHelp();
         
        if(rdfElement.getAlert()!=null) alert=rdfElement.getAlert();
        if(rdfElement.getHint()!=null) hint=rdfElement.getHint();
    }
    
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
    
    public String getXml() 
    {
        StringBuffer strbXml=new StringBuffer();
        try {
            strbXml.append("<secret id=\""+id+"\" bind=\"bind_"+id+"\">");
            
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
            strbXml.append("</secret>");
        }
        catch(Exception e) {com.infotec.appfw.util.AFUtils.log(e); }
        return strbXml.toString();
    }
    
    public void setXml(String xml) {
        this.xml=xml;
    }
}
