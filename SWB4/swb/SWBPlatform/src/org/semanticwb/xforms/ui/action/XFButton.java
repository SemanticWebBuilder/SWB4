/*
 * XFText.java
 *
 * Created on 1 de julio de 2008, 06:58 PM
 */

package org.semanticwb.xforms.ui.action;

import org.semanticwb.xforms.lib.XformsBaseImp;
import org.semanticwb.xforms.drop.RDFElement;
import org.semanticwb.SWBUtils;
import org.semanticwb.Logger;

/**
 *
 * @author  jorge.jimenez
 */
public class XFButton extends XformsBaseImp
{
    
    private static Logger log=SWBUtils.getLogger(XFButton.class);
    protected RDFElement rdfElement=null;
   
    public XFButton(RDFElement rdfElement){
        this.rdfElement=rdfElement;
        setRDFAttributes();
    }
    
    // Sets
    
    // Gets        
    
    public void setRDFAttributes(){
        if(rdfElement.getId()!=null) {
            id=rdfElement.getId();
        }
        if(rdfElement.getSType()!=null) {
            subType=rdfElement.getSType();
        }        
    }
    
    @Override
    public String getXml() 
    {
        StringBuffer strbXml=new StringBuffer();
        try {
            strbXml.append("<");
            
            strbXml.append(subType);
            
            if(id!=null){
                if(subType!=null && subType.equalsIgnoreCase("send")){
                    strbXml.append(" submission=\""+id+"\"");
                }else{
                     strbXml.append(" id=\""+id+"\"");
                }
            }
            strbXml.append("/>");
        }
        catch(Exception e) {log.error(e); }
        return strbXml.toString();
    }
    
    @Override
    public void setXml(String xml) {
        this.xml=xml;
    }
}
