/*
 * XFSelect.java
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
public class XFSelect extends WBXformsContainer 
{
    
    private static Logger log=SWBUtils.getLogger(XFSelect.class);
    protected String appearance=null;
    protected RDFElement rdfElement=null;
    HashMap instanceElements=new HashMap();
    protected String value=null;
    
    
    public XFSelect(RDFElement rdfElement){
        this.rdfElement=rdfElement;
        setRDFAttributes();
    }
    
    public XFSelect(RDFElement rdfElement,HashMap instanceElements){
        this.rdfElement=rdfElement;
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
        if(rdfElement.getId()!=null) {
            id=rdfElement.getId();
        }
        if(rdfElement.getLabel()!=null) {
            label=rdfElement.getLabel();
        }
        if(rdfElement.getAppearance()!=null) {
            appearance=rdfElement.getAppearance();
        }
        if(rdfElement.getSType()!=null) {
            subType=rdfElement.getSType();
        }
        if(rdfElement.getValue()!=null) {
            value=rdfElement.getValue();
        }
        
        //Checa si el elemento (grupo) tiene subelementos
        if(rdfElement.getElements().size()>0) {
            Iterator itElements=rdfElement.getElements().iterator();
            while(itElements.hasNext()) {
                RDFElement rdfElementIT=(RDFElement)itElements.next();
                if(rdfElementIT.getType()!=null && rdfElementIT.getType().equalsIgnoreCase("ITEM")) {
                    XFItemSelect xfitemselect = new XFItemSelect(rdfElementIT);
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
