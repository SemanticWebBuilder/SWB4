/*
 * XFRange.java
 *
 * Created on 1 de julio de 2008, 06:58 PM
 */

package org.semanticwb.xforms.ui;

import org.semanticwb.xforms.lib.XformsBaseImp;
import org.semanticwb.xforms.drop.RDFElement;
import org.semanticwb.SWBUtils;
import org.semanticwb.Logger;

/**
 *
 * @author  jorge.jimenez
 */
public class XFRange extends XformsBaseImp {
    
    private static Logger log=SWBUtils.getLogger(XFRange.class);
    protected int start=0;
    protected int end=0;
    protected int step=0;
    protected String value=null;
    
    protected RDFElement rdfElement=null;
    
    public XFRange(RDFElement rdfElement){
        this.rdfElement=rdfElement;
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
        if(rdfElement.getId()!=null) {
            id=rdfElement.getId();
        }
        if(rdfElement.getSType()!=null) {
            subType=rdfElement.getSType();
        }
        if(rdfElement.getLabel()!=null) {
            label=rdfElement.getLabel();
        }
        if(rdfElement.getHint()!=null) {
            hint=rdfElement.getHint();
        }
        if(rdfElement.getStart()>0) {
            start=rdfElement.getStart();
        }
        if(rdfElement.getEnd()>0) {
            end=rdfElement.getEnd();
        }
        if(rdfElement.getStep()>0) {
            step=rdfElement.getStep();
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
