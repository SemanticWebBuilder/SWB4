/*
 * XFRange.java
 *
 * Created on 1 de julio de 2008, 06:58 PM
 */

package org.semanticwb.portal.xforms.ui;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.xforms.ElementsProp;
import org.semanticwb.xforms.lib.XformsBaseImp;

/**
 *
 * @author  jorge.jimenez
 */
public class XFRange extends XformsBaseImp 
{

    private static Logger log = SWBUtils.getLogger(XFRange.class);
    protected int start=0;
    protected int end=0;
    protected int step=0;
    protected String value=null;
    
    protected ElementsProp elementsProp=null;
    
    public XFRange(ElementsProp elementsProp){
        this.elementsProp=elementsProp;
        setRDFAttributes();
    }
    
    // Sets
    
    public void setStart(int start){
        this.start=start;
    }
    
    public void setEnd(int end){
        this.end=end;
    }
    
    public void setStep(int step){
        this.step=step;
    }
    
    public void setValue(String value){
        this.value=value;
    }
    
    // Gets
    
    public int getStart(){
        return start;
    }
    
    public int getEnd(){
        return end;
    }
    
    public int getStep(){
        return step;
    }
    
    public String getValue(){
        return value;
    }
    
    public void setRDFAttributes(){
        if(elementsProp.getId()!=null) {
            id=elementsProp.getId();
        }
        if(elementsProp.getSType()!=null) {
            subType=elementsProp.getSType();
        }
        if(elementsProp.getLabel()!=null) {
            label=elementsProp.getLabel();
        }
        if(elementsProp.getHint()!=null) {
            hint=elementsProp.getHint();
        }
        if(elementsProp.getStart()>0) {
            start=elementsProp.getStart();
        }
        if(elementsProp.getEnd()>0) {
            end=elementsProp.getEnd();
        }
        if(elementsProp.getStep()>0) {
            step=elementsProp.getStep();
        }
    }
    
    @Override
    public String getXml() {
        StringBuffer strbXml=new StringBuffer();
        try {
            strbXml.append("<range");
            
            strbXml.append(" start=\""+start+"\"");
            
            strbXml.append(" end=\""+end+"\"");
            
            strbXml.append(" step=\""+step+"\">");
            
            if(label!=null) {
                strbXml.append("<label>");
                strbXml.append(label.trim());
                strbXml.append("</label>");
            }
            if(hint!=null) {
                strbXml.append("<hint>");
                strbXml.append(hint.trim());
                strbXml.append("</hint>");
            }
            strbXml.append("</range>");
        }
        catch(Exception e) {log.error(e); }
        return strbXml.toString();
    }
    
    @Override
    public void setXml(String xml) {
        this.xml=xml;
    }
}
