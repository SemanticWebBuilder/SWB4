/*
 * XFText.java
 *
 * Created on 1 de julio de 2008, 06:58 PM
 */

package org.semanticwb.xforms.ui;

import java.sql.Array;
import org.w3c.dom.*;
import org.semanticwb.xforms.lib.XformsBaseImp;
import org.semanticwb.xforms.drop.RDFElement;
import java.util.*;
import org.semanticwb.xforms.lib.WBXformsContainer;
import org.semanticwb.xforms.ui.container.*;

/**
 *
 * @author  jorge.jimenez
 */
public class XFCaseSwitch extends WBXformsContainer {
    
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
        if(rdfElement.getId()!=null) id=rdfElement.getId();
        if(rdfElement.isSelected()!=false) isselected=rdfElement.isSelected();
        
        //Checa si el elemento (grupo) tiene subelementos
        if(rdfElement.getElements().size()>0) {
            Iterator itElements=rdfElement.getElements().iterator();
            while(itElements.hasNext()) {
                RDFElement rdfElement=(RDFElement)itElements.next();
                if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("TRIGGER")) {
                    XFTrigger xftriggger = new XFTrigger(rdfElement);
                    this.add(xftriggger);
                }else if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("GROUP")) {
                    XFGroup xfgroup = new XFGroup(rdfElement,instanceElements);
                    this.add(xfgroup);
                }
            }
        }
        
    }
    
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
        catch(Exception e) {com.infotec.appfw.util.AFUtils.log(e); }
        return strbXml.toString();
    }
    
    public String getXmlBind() {
        return showBinds();
    }
    
    public void setXml(String xml) {
        this.xml=xml;
    }
}
