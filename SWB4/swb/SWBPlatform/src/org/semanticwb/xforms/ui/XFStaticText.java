/*
 * XFStaticText.java
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
public class XFStaticText extends XformsBaseImp
{
    
    private static Logger log=SWBUtils.getLogger(XFStaticText.class);
    protected String value=null;
    protected boolean isInhead=false;
    
    protected RDFElement rdfElement=null;
   
    public XFStaticText(RDFElement rdfElement){
        this.rdfElement=rdfElement;
        setRDFAttributes();
    }
    
    // Sets
    
    public void setValue(String value)
    {
        this.value=value;
    }
    
    public void setisInhead(boolean isInhead){
        this.isInhead=isInhead;
    }
    
    // Gets
    
    public String getValue()
    {
        return value;
    }
    
    public boolean isInhead(){
        return isInhead;
    }
    
    public void setRDFAttributes(){
        if(rdfElement.getValue()!=null) {
            value=rdfElement.getValue();            
        }       
        isInhead=rdfElement.isInhead();
    }
  
    @Override
    public String getXml() 
    {
        StringBuffer strbXml=new StringBuffer();
        try {
            strbXml.append("<![CDATA[");
            
            strbXml.append(value);
            
            strbXml.append("]]>");
        }
        catch(Exception e) {log.error(e); }
        return strbXml.toString();
    }
    
    @Override
    public void setXml(String xml) {
        this.xml=xml;
    }
}
