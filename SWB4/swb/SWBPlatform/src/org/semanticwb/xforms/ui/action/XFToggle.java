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
public class XFToggle extends XformsBaseImp {
    
    private static Logger log=SWBUtils.getLogger(XFToggle.class);
    protected String actionCase=null;
    protected RDFElement rdfElement=null;
    
    public XFToggle(RDFElement rdfElement){
        this.rdfElement=rdfElement;
        setRDFAttributes();
    }
    
    // Sets
    
    // Gets    
    
    public void setRDFAttributes(){
        if(rdfElement.getId()!=null) {
            id=rdfElement.getId();
        }
        if(rdfElement.getActionCase()!=null) {
            actionCase=rdfElement.getActionCase();
        }
    }
    
    @Override
    public String getXml() {
        StringBuffer strbXml=new StringBuffer();
        try {
            strbXml.append("<toggle");
            
            if(id!=null){
                strbXml.append(" id=\""+id+"\"");
            }
            
            if(actionCase!=null){
                strbXml.append(" case=\""+actionCase+"\"");
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
