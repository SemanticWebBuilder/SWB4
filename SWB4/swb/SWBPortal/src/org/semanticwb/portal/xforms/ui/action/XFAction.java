/*
 * XFText.java
 *
 * Created on 1 de julio de 2008, 06:58 PM
 */

package org.semanticwb.portal.xforms.ui.action;

import org.semanticwb.xforms.lib.WBXformsContainer;
import java.util.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.xforms.ElementsProp;
import org.semanticwb.portal.xforms.ui.XFButton;

/**
 *
 * @author  jorge.jimenez
 */
public class XFAction extends WBXformsContainer
{
    private static Logger log = SWBUtils.getLogger(XFAction.class);
    
    protected ElementsProp elementsProp=null;
    protected HashMap instanceElements=new HashMap();
    
    public XFAction(ElementsProp elementsProp){
        this.elementsProp=elementsProp;
        setRDFAttributes();
    }
    
    public XFAction(ElementsProp elementsProp,HashMap instanceElements){
        this.elementsProp=elementsProp;
        this.instanceElements=instanceElements;
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
        
        if(elementsProp.getElements().size()>0) {
            Iterator itElements=elementsProp.getElements().iterator();
            while(itElements.hasNext()) {
                ElementsProp elementsProp=(ElementsProp)itElements.next();
                if(elementsProp.getType()!=null && elementsProp.getType().equalsIgnoreCase("BUTTON")) {
                    XFButton xfButton = new XFButton(elementsProp);
                    this.add(xfButton);
                }else if(elementsProp.getType()!=null && elementsProp.getType().equalsIgnoreCase("TOGGLE")) {
                    XFToggle xfToggle = new XFToggle(elementsProp);
                    this.add(xfToggle);
                }
            }
        }
    }
    
    @Override
    public String getXml() 
    {
        StringBuffer strbXml=new StringBuffer();
        try {
            strbXml.append("<action");
            
            if(id!=null){
                strbXml.append(" id=\""+id+"\"");
            }
            
            strbXml.append(">");
            
            strbXml.append(this.show());
            
            strbXml.append("</action>");
        }
        catch(Exception e) {log.error(e); }
        return strbXml.toString();
    }
    
    @Override
    public void setXml(String xml) {
        this.xml=xml;
    }
}
