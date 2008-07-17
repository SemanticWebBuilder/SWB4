/*
 * XFGroup.java
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
public class XFGroup extends WBXformsContainer {
    protected String appearance=null;
    
    protected RDFElement rdfElement=null;
    
    HashMap instanceElements=new HashMap();
    
    public XFGroup(RDFElement rdfElement){
        this.rdfElement=rdfElement;
        setRDFAttributes();
    }
    
    public XFGroup(RDFElement rdfElement,HashMap instanceElements){
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
                if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("TEXT")) {
                    XFText xftext = new XFText(rdfElement);
                    instanceElements.put(xftext.getId(),xftext.getValue());
                    this.add(xftext);
                }if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("OUTPUT")) {
                    XFOutput xfoutput = new XFOutput(rdfElement);
                    instanceElements.put(xfoutput.getId(),xfoutput.getValue());
                    this.add(xfoutput);
                }else if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("UPLOAD")) {
                    XFUpload xfupload = new XFUpload(rdfElement);
                    instanceElements.put(xfupload.getId(),xfupload.getValue());
                    this.add(xfupload);
                }else if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("TEXTAREA")) {
                    XFTextArea xftextarea = new XFTextArea(rdfElement);
                    instanceElements.put(xftextarea.getId(),xftextarea.getValue());
                    this.add(xftextarea);
                }else if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("SECRET")) {
                    XFSecret xfsecret = new XFSecret(rdfElement);
                    instanceElements.put(xfsecret.getId(),xfsecret.getValue());
                    this.add(xfsecret);
                }else if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("GROUP")) {
                    XFGroup xfgroup = new XFGroup(rdfElement,instanceElements);
                    this.add(xfgroup);
                }else if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("SELECT")) {
                    XFSelect xfselect = new XFSelect(rdfElement,instanceElements);
                    this.add(xfselect);
                }else if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("SWITCH")) {
                    XFSwitch xfswitch = new XFSwitch(rdfElement,instanceElements);
                    this.add(xfswitch);
                }else if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("BUTTON")) {
                    XFButton xfsubmit = new XFButton(rdfElement);
                    this.add(xfsubmit);
                }else if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("TRIGGER")) {
                    XFTrigger xfswitch = new XFTrigger(rdfElement,instanceElements);
                    this.add(xfswitch);
                }
            }
        }
    }
    
    public String getXml() {
        StringBuffer strbXml=new StringBuffer();
        try {
            strbXml.append("<group ");
            
            if(id!=null){
                strbXml.append("id=\""+id+"\"");
            }
            
            if(appearance!=null){
                strbXml.append("appearance=\""+appearance+"\"");
            }
            
            strbXml.append(">");
            
            if(label!=null) {
                strbXml.append("<label>");
                strbXml.append(label.trim());
                strbXml.append("</label>");
            }
            
            strbXml.append(this.show());
            
            strbXml.append("</group>");
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
