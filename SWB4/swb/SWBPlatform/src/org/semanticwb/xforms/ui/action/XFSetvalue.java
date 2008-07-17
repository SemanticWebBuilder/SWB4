/*
 * XFSetvalue.java
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
public class XFSetvalue extends XformsBaseImp 
{
    
    private static Logger log=SWBUtils.getLogger(XFSetvalue.class);
    protected RDFElement rdfElement=null;
    protected String value=null;
    
    public XFSetvalue(RDFElement rdfElement){
        this.rdfElement=rdfElement;
        setRDFAttributes();
    }
    
    // Sets
    public void setValue(String value){
        this.value=value;
    }
    
    // Gets    
    public String getValue(){
        return value;
    }
    
    public void setRDFAttributes(){
        if(rdfElement.getId()!=null) {
            id=rdfElement.getId();
        }
        
    }
    
    @Override
    public String getXml() {
        StringBuffer strbXml=new StringBuffer();
        try {
            strbXml.append("<setvalue");
            
            if(id!=null){
                strbXml.append(" bind=\"bind_"+id+"\"");
            }
            
            if(value!=null){
                strbXml.append(" value=\""+value+"\"");
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
