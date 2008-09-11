/*
 * XFSelect.java
 *
 * Created on 1 de julio de 2008, 06:58 PM
 */

package org.semanticwb.portal.xforms.ui.container;

import org.semanticwb.portal.xforms.lib.WBXformsContainer;
import java.util.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.xforms.ElementsProp;
import org.semanticwb.portal.xforms.ui.*;

/**
 *
 * @author  jorge.jimenez
 */
public class XFSwitch extends WBXformsContainer 
{
    
    private static Logger log = SWBUtils.getLogger(XFSwitch.class);
    protected String appearance=null;
    protected ElementsProp elementsProp=null;
    protected String cssclass=null;
    HashMap instanceElements=new HashMap();
    
    public XFSwitch(ElementsProp elementsProp){
        this.elementsProp=elementsProp;
        setRDFAttributes();
    }
    
    public XFSwitch(ElementsProp elementsProp,HashMap instanceElements){
        this.elementsProp=elementsProp;
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
        if(elementsProp.getId()!=null) {
            id=elementsProp.getId();
        }
        if(elementsProp.getLabel()!=null) {
            label=elementsProp.getLabel();
        }
        if(elementsProp.getAppearance()!=null) {
            appearance=elementsProp.getAppearance();
        }
        if(elementsProp.getCssClass()!=null) {
            cssclass=elementsProp.getCssClass();
        }
        if(elementsProp.getSType()!=null) {
            subType=elementsProp.getSType();
        }
        
        //Checa si el elemento (grupo) tiene subelementos
        if(elementsProp.getElements().size()>0) {
            Iterator itElements=elementsProp.getElements().iterator();
            while(itElements.hasNext()) {
                ElementsProp elementsProp=(ElementsProp)itElements.next();
                if(elementsProp.getType()!=null && elementsProp.getType().equalsIgnoreCase("CASE")) {
                    XFCaseSwitch xfcaseswitch = new XFCaseSwitch(elementsProp,instanceElements);
                    this.add(xfcaseswitch);
                }else if(elementsProp.getType()!=null && elementsProp.getType().equalsIgnoreCase("GROUP")) {
                    XFGroup xfgroup = new XFGroup(elementsProp,instanceElements);
                    this.add(xfgroup);
                }
            }
        }
    }
    
    @Override
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
