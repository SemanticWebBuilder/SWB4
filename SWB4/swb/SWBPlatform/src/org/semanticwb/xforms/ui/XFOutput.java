/*
 * XFOutput.java
 *
 * Created on 1 de julio de 2008, 06:58 PM
 */

package org.semanticwb.xforms.ui;

import org.semanticwb.xforms.lib.XformsBaseImp;
import org.semanticwb.xforms.drop.RDFElement;
import org.semanticwb.SWBUtils;
import org.semanticwb.Logger;

/**
 *
 * @author  jorge.jimenez
 */
public class XFOutput extends XformsBaseImp
{
    private static Logger log=SWBUtils.getLogger(XFOutput.class);
    
    protected String value=null;
    protected String mediatype=null;    
    protected RDFElement rdfElement=null;
    
    public XFOutput(RDFElement rdfElement){
        this.rdfElement=rdfElement;
        setRDFAttributes();
    }
    
    // Sets
    
    public void setValue(String value){
        this.value=value;
    }
    
    public void setMediaType(String mediatype){
        this.mediatype=mediatype;
    }
    
    // Gets
    
    public String getValue(){
        return value;
    }
    
    public String getMediaType(){
       return mediatype;
    }
    
    public void setRDFAttributes(){
        if(rdfElement.getId()!=null) {
            id=rdfElement.getId();
        }
        if(rdfElement.getLabel()!=null) {
            label=rdfElement.getLabel();
        }
        if(rdfElement.getSType()!=null) {
            subType=rdfElement.getSType();
        }
        if(rdfElement.getHint()!=null) {
            hint=rdfElement.getHint();
        }
        if(rdfElement.getValue()!=null) {
            value=rdfElement.getValue();
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
            strbXml.append("<output bind=\"bind_"+id+"\"");
            
            strbXml.append(">");
            
            if(label!=null) 
            {
                strbXml.append("<label>");
                strbXml.append(label.trim());
                strbXml.append("</label>");
            }
           
            strbXml.append("</output>");
        }
        catch(Exception e) {log.error(e); }
        return strbXml.toString();
    }
    
   @Override
    public void setXml(String xml) {
        this.xml=xml;
    }
}
