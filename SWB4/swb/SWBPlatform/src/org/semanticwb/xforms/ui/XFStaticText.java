/*
 * XFStaticText.java
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
public class XFStaticText extends XformsBaseImp
{
    protected String value=null;
    
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
    
    // Gets
    
    public String getValue()
    {
        return value;
    }
        
    public void setRDFAttributes(){
        if(rdfElement.getValue()!=null) value=rdfElement.getValue();         
    }
  
    public String getXml() 
    {
        StringBuffer strbXml=new StringBuffer();
        try {
            strbXml.append("<![CDATA[");
            
            strbXml.append(value);
            
            strbXml.append("]]>");
        }
        catch(Exception e) {com.infotec.appfw.util.AFUtils.log(e); }
        return strbXml.toString();
    }
    
    public void setXml(String xml) {
        this.xml=xml;
    }
}
