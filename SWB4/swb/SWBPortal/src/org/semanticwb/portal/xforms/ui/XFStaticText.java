/*
 * XFStaticText.java
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
public class XFStaticText extends XformsBaseImp
{
    
    private static Logger log = SWBUtils.getLogger(XFStaticText.class);
    protected String value=null;    
    protected ElementsProp elementsProp=null;
    protected boolean isInhead=false;
   
    public XFStaticText(ElementsProp elementsProp){
        this.elementsProp=elementsProp;
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
        if(elementsProp.getValue()!=null) {
            value=elementsProp.getValue();
        }       
        isInhead=elementsProp.isInhead();
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
