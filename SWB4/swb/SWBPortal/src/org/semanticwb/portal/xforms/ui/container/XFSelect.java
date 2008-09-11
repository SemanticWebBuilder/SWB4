/*
 * XFSelect.java
 *
 * Created on 1 de julio de 2008, 06:58 PM
 */

package org.semanticwb.portal.xforms.ui.container;

import org.semanticwb.xforms.drop.RDFElement;
import org.semanticwb.xforms.lib.WBXformsContainer;
import java.util.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.xforms.ElementsProp;
import org.semanticwb.portal.xforms.ui.action.XFSetvalue;
import org.semanticwb.xforms.ui.*;

/**
 *
 * @author  jorge.jimenez
 */
public class XFSelect extends WBXformsContainer 
{
    private static Logger log = SWBUtils.getLogger(XFSetvalue.class);
    protected String appearance=null;
    protected ElementsProp elementsProp=null;
    HashMap instanceElements=new HashMap();
    protected String value=null;
    
    
    public XFSelect(ElementsProp elementsProp){
        this.elementsProp=elementsProp;
        setRDFAttributes();
    }
    
    public XFSelect(ElementsProp elementsProp,HashMap instanceElements){
        this.elementsProp=elementsProp;
        this.instanceElements=instanceElements;
        setRDFAttributes();
    }
    
    // Sets
    
    public void setAppearance(String appearance) {
        this.appearance=appearance;
    }
    
    public void setValue(String value){
        this.value=value;
    }
    
    // Gets
    
    public String getAppearance() {
        return appearance;
    }
    
     public String getValue(){
        return value;
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
        if(elementsProp.getSType()!=null) {
            subType=elementsProp.getSType();
        }
        if(elementsProp.getValue()!=null) {
            value=elementsProp.getValue();
        }
        
        //Checa si el elemento (grupo) tiene subelementos
        if(elementsProp.getElements().size()>0) {
            Iterator itElements=elementsProp.getElements().iterator();
            while(itElements.hasNext()) {
                RDFElement rdfElement=(RDFElement)itElements.next();
                if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("ITEM")) {
                    XFItemSelect xfitemselect = new XFItemSelect(rdfElement);
                    this.add(xfitemselect);
                }
            }
        }
    }
    
    @Override
    public String getXml() {
        StringBuffer strbXml=new StringBuffer();
        try {
            strbXml.append("<select");
            
            if(subType!=null && subType.equalsIgnoreCase("ONE")){
                strbXml.append("1");
            }
            
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
            
            strbXml.append(this.show());
            
            strbXml.append("</select");
            
            if(subType!=null && subType.equalsIgnoreCase("ONE")){
                strbXml.append("1");
            }
            
            strbXml.append(">");
            
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
