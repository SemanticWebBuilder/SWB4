/*
 * XFText.java
 *
 * Created on 1 de julio de 2008, 06:58 PM
 */

package org.semanticwb.xforms.ui;

import org.semanticwb.xforms.drop.RDFElement;
import java.util.*;
import org.semanticwb.xforms.lib.WBXformsContainer;
import org.semanticwb.xforms.ui.container.*;
import org.semanticwb.SWBUtils;
import org.semanticwb.Logger;

/**
 *
 * @author  jorge.jimenez
 */
public class XFCaseSwitch extends WBXformsContainer {
    
    private static Logger log=SWBUtils.getLogger(XFCaseSwitch.class);
    
    protected RDFElement rdfElement=null;
    protected String value=null;
    protected boolean isselected=false;
    HashMap instanceElements=new HashMap();
    
    public XFCaseSwitch(RDFElement rdfElement){
        this.rdfElement=rdfElement;
        setRDFAttributes();
    }
    
    public XFCaseSwitch(RDFElement rdfElement,HashMap instanceElements){
        this.rdfElement=rdfElement;
        this.instanceElements=instanceElements;
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
        if(rdfElement.isSelected()!=false) {
            isselected=rdfElement.isSelected();
        }
        
        //Checa si el elemento (grupo) tiene subelementos
        if(rdfElement.getElements().size()>0) {
            Iterator itElements=rdfElement.getElements().iterator();
            while(itElements.hasNext()) {
                RDFElement rdfElementIT=(RDFElement)itElements.next();
                if(rdfElementIT.getType()!=null && rdfElementIT.getType().equalsIgnoreCase("TRIGGER")) {
                    XFTrigger xftriggger = new XFTrigger(rdfElementIT);
                    this.add(xftriggger);
                }else if(rdfElementIT.getType()!=null && rdfElementIT.getType().equalsIgnoreCase("GROUP")) {
                    XFGroup xfgroup = new XFGroup(rdfElementIT,instanceElements);
                    this.add(xfgroup);
                }
            }
        }
        
    }
    
    @Override
    public String getXml() {
        StringBuffer strbXml=new StringBuffer();
        try {
            strbXml.append("<case id=\""+id+"\"");
            
            if(isselected){
                strbXml.append(" selected=\""+isselected+"\"");
            }
            strbXml.append(">");
            
            strbXml.append(this.show());
            
            strbXml.append("</case>");
        }
        catch(Exception e) {log.error(e); }
        return strbXml.toString();
    }
    
    @Override
    public String getXmlBind() {
        return showBinds();
    }
    
    @Override
    public void setXml(String xml) {
        this.xml=xml;
    }
}
