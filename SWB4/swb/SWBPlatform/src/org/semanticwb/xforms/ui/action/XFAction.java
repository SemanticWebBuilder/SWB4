/*
 * XFText.java
 *
 * Created on 1 de julio de 2008, 06:58 PM
 */

package org.semanticwb.xforms.ui.action;

import org.semanticwb.xforms.drop.RDFElement;
import org.semanticwb.xforms.lib.WBXformsContainer;
import java.util.*;
import org.semanticwb.SWBUtils;
import org.semanticwb.Logger;

/**
 *
 * @author  jorge.jimenez
 */
public class XFAction extends WBXformsContainer
{
    private static Logger log=SWBUtils.getLogger(XFAction.class);
    protected RDFElement rdfElement=null;
    protected HashMap instanceElements=new HashMap();
    
    public XFAction(RDFElement rdfElement){
        this.rdfElement=rdfElement;
        setRDFAttributes();
    }
    
    public XFAction(RDFElement rdfElement,HashMap instanceElements){
        this.rdfElement=rdfElement;
        this.instanceElements=instanceElements;
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
        
        if(rdfElement.getElements().size()>0) {
            Iterator itElements=rdfElement.getElements().iterator();
            while(itElements.hasNext()) {
                RDFElement rdfElementIT=(RDFElement)itElements.next();
                if(rdfElementIT.getType()!=null && rdfElementIT.getType().equalsIgnoreCase("BUTTON")) {
                    XFButton xfButton = new XFButton(rdfElementIT);
                    this.add(xfButton);
                }else if(rdfElementIT.getType()!=null && rdfElementIT.getType().equalsIgnoreCase("TOGGLE")) {
                    XFToggle xfToggle = new XFToggle(rdfElementIT);
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
