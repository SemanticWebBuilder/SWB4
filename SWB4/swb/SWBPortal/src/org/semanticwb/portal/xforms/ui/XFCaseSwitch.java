/*
 * XFText.java
 *
 * Created on 1 de julio de 2008, 06:58 PM
 */

package org.semanticwb.portal.xforms.ui;

import org.semanticwb.xforms.drop.RDFElement;
import java.util.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.xforms.ElementsProp;
import org.semanticwb.xforms.lib.WBXformsContainer;
import org.semanticwb.xforms.ui.XFTrigger;
import org.semanticwb.xforms.ui.container.*;

/**
 *
 * @author  jorge.jimenez
 */
public class XFCaseSwitch extends WBXformsContainer 
{
    
    private static Logger log = SWBUtils.getLogger(XFCaseSwitch.class);
    
    protected ElementsProp elementsProp=null;
    protected String value=null;
    protected boolean isselected=false;
    HashMap instanceElements=new HashMap();
    
    public XFCaseSwitch(ElementsProp elementsProp){
        this.elementsProp=elementsProp;
        setRDFAttributes();
    }
    
    public XFCaseSwitch(ElementsProp elementsProp,HashMap instanceElements){
        this.elementsProp=elementsProp;
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
        if(elementsProp.getId()!=null) {
            id=elementsProp.getId();
        }
        if(elementsProp.isSelected()!=false) {
            isselected=elementsProp.isSelected();
        }
        
        //Checa si el elemento (grupo) tiene subelementos
        if(elementsProp.getElements().size()>0) {
            Iterator itElements=elementsProp.getElements().iterator();
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
