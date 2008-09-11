/*
 * XFText.java
 *
 * Created on 1 de julio de 2008, 06:58 PM
 */

package org.semanticwb.portal.xforms.ui.action;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.xforms.ElementsProp;
import org.semanticwb.xforms.lib.XformsBaseImp;

/**
 *
 * @author  jorge.jimenez
 */
public class XFToggle extends XformsBaseImp 
{

    private static Logger log = SWBUtils.getLogger(XFAction.class);
    protected String actionCase=null;
    protected ElementsProp elementsProp=null;
    
    public XFToggle(ElementsProp elementsProp){
        this.elementsProp=elementsProp;
        setRDFAttributes();
    }
    
    // Sets
    
    // Gets    
    
    public void setRDFAttributes(){
        if(elementsProp.getId()!=null) {
            id=elementsProp.getId();
        }
        if(elementsProp.getActionCase()!=null) {
            actionCase=elementsProp.getActionCase();
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
