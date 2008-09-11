/*
 * XFSubmit.java
 *
 * Created on 1 de julio de 2008, 06:58 PM
 */

package org.semanticwb.portal.xforms.ui;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.xforms.lib.XformsBaseImp;
import org.semanticwb.portal.xforms.ElementsProp;

/**
 *
 * @author  jorge.jimenez
 */
public class XFButton extends XformsBaseImp 
{
    private static Logger log = SWBUtils.getLogger(XFButton.class);
    
    protected ElementsProp elementsProp=null;
    
    
    public XFButton(ElementsProp elementsProp){
        this.elementsProp=elementsProp;
        setRDFAttributes();
    }
    
    // Sets
    
    
    // Gets
    
    
    public void setRDFAttributes(){
        if(elementsProp.getId()!=null) {
            id=elementsProp.getId();
        }
        if(elementsProp.getSType()!=null) {
            subType=elementsProp.getSType();
        }
    }
    
    
    @Override
    public String getXml() {
        StringBuffer strbXml=new StringBuffer();
        try {
            strbXml.append("<");
            strbXml.append(subType);
            if(id!=null)
            {
                if(subType!=null && subType.equalsIgnoreCase("submit")) {
                   strbXml.append(" submission=\""+id+"\"");
                }else {
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
