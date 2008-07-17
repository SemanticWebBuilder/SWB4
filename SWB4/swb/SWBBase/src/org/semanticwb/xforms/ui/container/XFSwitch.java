/*
 * XFSelect.java
 *
 * Created on 1 de julio de 2008, 06:58 PM
 */

package org.semanticwb.xforms.ui.container;

import java.sql.Array;
import org.w3c.dom.*;
import org.semanticwb.xforms.lib.XformsBaseImp;
import org.semanticwb.xforms.drop.RDFElement;
import org.semanticwb.xforms.lib.WBXformsContainer;
import java.util.*;
import org.semanticwb.xforms.ui.*;
import org.semanticwb.xforms.ui.container.*;

/**
 *
 * @author  jorge.jimenez
 */
public class XFSwitch extends WBXformsContainer {
    
    protected String appearance=null;
    protected String cssclass=null;
    protected RDFElement rdfElement=null;
    HashMap instanceElements=new HashMap();
    
    public XFSwitch(RDFElement rdfElement){
        this.rdfElement=rdfElement;
        setRDFAttributes();
    }
    
    public XFSwitch(RDFElement rdfElement,HashMap instanceElements){
        this.rdfElement=rdfElement;
        this.instanceElements=instanceElements;
        setRDFAttributes();
    }
    
    // Sets
    
    public void setAppearance(String appearance) {
        this.appearance=appearance;
    }
    
    public void setCssClass(String cssclass) {
        this.cssclass=cssclass;
    }
    
    // Gets
    
    public String getAppearance() {
        return appearance;
    }
    
    public String getCssClass() {
        return cssclass;
    }
    
    public void setRDFAttributes(){
        if(rdfElement.getId()!=null) id=rdfElement.getId();
        if(rdfElement.getLabel()!=null) label=rdfElement.getLabel();
        if(rdfElement.getAppearance()!=null) appearance=rdfElement.getAppearance();
        if(rdfElement.getCssClass()!=null) cssclass=rdfElement.getCssClass();
        if(rdfElement.getSType()!=null) subType=rdfElement.getSType();
        
        //Checa si el elemento (grupo) tiene subelementos
        if(rdfElement.getElements().size()>0) {
            Iterator itElements=rdfElement.getElements().iterator();
            while(itElements.hasNext()) {
                RDFElement rdfElement=(RDFElement)itElements.next();
                if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("CASE")) {
                    XFCaseSwitch xfcaseswitch = new XFCaseSwitch(rdfElement,instanceElements);
                    this.add(xfcaseswitch);
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
            strbXml.append("<switch");
            
            if(id!=null){
                strbXml.append(" id=\""+id+"\"");
            }
            
            if(appearance!=null){
                strbXml.append(" appearance=\""+appearance+"\"");
            }
            
            if(cssclass!=null){
                strbXml.append(" class=\""+cssclass+"\"");
            }
            
            strbXml.append(">");
            
            if(label!=null) {
                strbXml.append("<label>");
                strbXml.append(label.trim());
                strbXml.append("</label>");
            }
            
            strbXml.append(this.show());
            
            strbXml.append("</switch>");
            
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
