/*
 * XFSelect.java
 *
 * Created on 1 de julio de 2008, 06:58 PM
 */

package org.semanticwb.xforms.ui;

import java.sql.Array;
import org.w3c.dom.*;
import org.semanticwb.xforms.lib.XformsBaseImp;
import org.semanticwb.xforms.drop.RDFElement;
import org.semanticwb.xforms.lib.WBXformsContainer;
import java.util.*;
import org.semanticwb.xforms.ui.*;
import org.semanticwb.xforms.ui.container.*;
import org.semanticwb.xforms.ui.action.*;

/**
 *
 * @author  jorge.jimenez
 */
public class XFTrigger extends WBXformsContainer {
    
    protected String appearance=null;    
    protected RDFElement rdfElement=null;
    
    HashMap instanceElements=new HashMap();
    
    public XFTrigger(RDFElement rdfElement){
        this.rdfElement=rdfElement;
        setRDFAttributes();
    }
    
    public XFTrigger(RDFElement rdfElement,HashMap instanceElements){
        this.rdfElement=rdfElement;
        this.instanceElements=instanceElements;
        setRDFAttributes();
    }
    
    // Sets
    
    public void setAppearance(String appearance) {
        this.appearance=appearance;
    }
    
    // Gets
    
    public String getAppearance() {
        return appearance;
    }
    
    public void setRDFAttributes(){
        if(rdfElement.getId()!=null) id=rdfElement.getId();
        if(rdfElement.getLabel()!=null) label=rdfElement.getLabel();
        if(rdfElement.getAppearance()!=null) appearance=rdfElement.getAppearance();
        
        //Checa si el elemento (grupo) tiene subelementos
        if(rdfElement.getElements().size()>0) {
            Iterator itElements=rdfElement.getElements().iterator();
            while(itElements.hasNext()) {
                RDFElement rdfElement=(RDFElement)itElements.next();
                if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("ACTION")) {
                    XFAction xfaction = new XFAction(rdfElement);
                    this.add(xfaction);
                }
            }
        }
    }
    
    public String getXml() {
        StringBuffer strbXml=new StringBuffer();
        try {
            strbXml.append("<trigger");
            
            if(id!=null){
                strbXml.append(" id=\""+id+"\"");
            }
            
            if(appearance!=null){
                strbXml.append(" appearance=\""+appearance+"\"");
            }
            strbXml.append(">");
            
            if(label!=null) {
                strbXml.append("<label>");
                strbXml.append(label.trim());
                strbXml.append("</label>");
            }
            if(hint!=null) 
            {
                strbXml.append("<hint>");
                strbXml.append(hint.trim());
                strbXml.append("</hint>");
            }
            
            strbXml.append(this.show());
            strbXml.append("</trigger>");
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
