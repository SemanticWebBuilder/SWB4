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
import org.semanticwb.xforms.ui.*;
import org.semanticwb.xforms.lib.WBXformsContainer;
import java.util.*;

/**
 *
 * @author  jorge.jimenez
 */
public class XFAction extends WBXformsContainer
{
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
        if(rdfElement.getId()!=null) id=rdfElement.getId();
        if(rdfElement.getSType()!=null) subType=rdfElement.getSType();
        
        if(rdfElement.getElements().size()>0) {
            Iterator itElements=rdfElement.getElements().iterator();
            while(itElements.hasNext()) {
                RDFElement rdfElement=(RDFElement)itElements.next();
                if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("BUTTON")) {
                    XFButton xfButton = new XFButton(rdfElement);
                    this.add(xfButton);
                }else if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("TOGGLE")) {
                    XFToggle xfToggle = new XFToggle(rdfElement);
                    this.add(xfToggle);
                }
            }
        }
    }
    
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
        catch(Exception e) {com.infotec.appfw.util.AFUtils.log(e); }
        return strbXml.toString();
    }
    
    public void setXml(String xml) {
        this.xml=xml;
    }
}
