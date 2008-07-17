/*
 * XFText.java
 *
 * Created on 1 de julio de 2008, 06:58 PM
 */

package org.semanticwb.xforms.ui.action;

import java.sql.Array;
import org.w3c.dom.*;
import org.semanticwb.xforms.lib.XformsBaseImp;
import org.semanticwb.xforms.drop.RDFElement;

/**
 *
 * @author  jorge.jimenez
 */
public class XFToggle extends XformsBaseImp {
    
    protected String actionCase=null;
    protected RDFElement rdfElement=null;
    
    public XFToggle(RDFElement rdfElement){
        this.rdfElement=rdfElement;
        setRDFAttributes();
    }
    
    // Sets
    
    // Gets    
    
    public void setRDFAttributes(){
        if(rdfElement.getId()!=null) id=rdfElement.getId();
        if(rdfElement.getActionCase()!=null) actionCase=rdfElement.getActionCase();
    }
    
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
        catch(Exception e) {com.infotec.appfw.util.AFUtils.log(e); }
        return strbXml.toString();
    }
    
    public void setXml(String xml) {
        this.xml=xml;
    }
}
