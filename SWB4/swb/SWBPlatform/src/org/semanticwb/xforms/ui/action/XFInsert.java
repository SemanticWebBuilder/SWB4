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
//TODO: Esta clase es para el manejo del componente repeat,x estos momentos voy a dejar pendientes todos los componentes de repeat 
//(insert,delete,setindex,etc)


public class XFInsert extends XformsBaseImp 
{
    
    private static Logger log=SWBUtils.getLogger(XFInsert.class);
    protected RDFElement rdfElement=null;
    protected String value=null;
    
    public XFInsert(RDFElement rdfElement){
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
            strbXml.append("<insert");
            
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
