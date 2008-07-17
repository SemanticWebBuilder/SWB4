/*
 * XFGroup.java
 *
 * Created on 1 de julio de 2008, 06:58 PM
 */

package org.semanticwb.xforms.ui.container;

import org.semanticwb.xforms.drop.RDFElement;
import org.semanticwb.xforms.lib.WBXformsContainer;
import java.util.*;
import org.semanticwb.xforms.ui.*;
import org.semanticwb.SWBUtils;
import org.semanticwb.Logger;

/**
 *
 * @author  jorge.jimenez
 */
public class XFGroup extends WBXformsContainer 
{
    private static Logger log=SWBUtils.getLogger(XFGroup.class);
    
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
        if(rdfElement.getId()!=null) {
            id=rdfElement.getId();
        }
        if(rdfElement.getLabel()!=null) {
            label=rdfElement.getLabel();
        }
        if(rdfElement.getAppearance()!=null) {
            appearance=rdfElement.getAppearance();
        }
        
        //Checa si el elemento (grupo) tiene subelementos
        if(rdfElement.getElements().size()>0) {
            Iterator itElements=rdfElement.getElements().iterator();
            while(itElements.hasNext()) {
                RDFElement rdfElementIT=(RDFElement)itElements.next();
                if(rdfElementIT.getType()!=null && rdfElementIT.getType().equalsIgnoreCase("TEXT")) {
                    XFText xftext = new XFText(rdfElementIT);
                    instanceElements.put(xftext.getId(),xftext.getValue());
                    this.add(xftext);
                }if(rdfElementIT.getType()!=null && rdfElementIT.getType().equalsIgnoreCase("OUTPUT")) {
                    XFOutput xfoutput = new XFOutput(rdfElementIT);
                    instanceElements.put(xfoutput.getId(),xfoutput.getValue());
                    this.add(xfoutput);
                }else if(rdfElementIT.getType()!=null && rdfElementIT.getType().equalsIgnoreCase("UPLOAD")) {
                    XFUpload xfupload = new XFUpload(rdfElementIT);
                    instanceElements.put(xfupload.getId(),xfupload.getValue());
                    this.add(xfupload);
                }else if(rdfElementIT.getType()!=null && rdfElementIT.getType().equalsIgnoreCase("TEXTAREA")) {
                    XFTextArea xftextarea = new XFTextArea(rdfElementIT);
                    instanceElements.put(xftextarea.getId(),xftextarea.getValue());
                    this.add(xftextarea);
                }else if(rdfElementIT.getType()!=null && rdfElementIT.getType().equalsIgnoreCase("SECRET")) {
                    XFSecret xfsecret = new XFSecret(rdfElementIT);
                    instanceElements.put(xfsecret.getId(),xfsecret.getValue());
                    this.add(xfsecret);
                }else if(rdfElementIT.getType()!=null && rdfElementIT.getType().equalsIgnoreCase("GROUP")) {
                    XFGroup xfgroup = new XFGroup(rdfElementIT,instanceElements);
                    this.add(xfgroup);
                }else if(rdfElementIT.getType()!=null && rdfElementIT.getType().equalsIgnoreCase("SELECT")) {
                    XFSelect xfselect = new XFSelect(rdfElementIT,instanceElements);
                    this.add(xfselect);
                }else if(rdfElementIT.getType()!=null && rdfElementIT.getType().equalsIgnoreCase("SWITCH")) {
                    XFSwitch xfswitch = new XFSwitch(rdfElementIT,instanceElements);
                    this.add(xfswitch);
                }else if(rdfElementIT.getType()!=null && rdfElementIT.getType().equalsIgnoreCase("BUTTON")) {
                    XFButton xfsubmit = new XFButton(rdfElementIT);
                    this.add(xfsubmit);
                }else if(rdfElementIT.getType()!=null && rdfElementIT.getType().equalsIgnoreCase("TRIGGER")) {
                    XFTrigger xfswitch = new XFTrigger(rdfElementIT,instanceElements);
                    this.add(xfswitch);
                }
            }
        }
    }
    
    @Override
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
