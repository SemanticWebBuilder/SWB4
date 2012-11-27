/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.xforms.ui;

import org.semanticwb.xforms.lib.XformsBaseImp;
import org.semanticwb.xforms.drop.RDFElement;
import org.semanticwb.SWBUtils;
import org.semanticwb.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class XFRange.
 * 
 * @author  jorge.jimenez
 */
public class XFRange extends XformsBaseImp {
    
    /** The log. */
    private static Logger log=SWBUtils.getLogger(XFRange.class);
    
    /** The start. */
    protected int start=0;
    
    /** The end. */
    protected int end=0;
    
    /** The step. */
    protected int step=0;
    
    /** The value. */
    protected String value=null;
    
    /** The rdf element. */
    protected RDFElement rdfElement=null;
    
    /**
     * Instantiates a new xF range.
     * 
     * @param rdfElement the rdf element
     */
    public XFRange(RDFElement rdfElement){
        this.rdfElement=rdfElement;
        setRDFAttributes();
    }
    
    // Sets
    
    /**
     * Sets the start.
     * 
     * @param start the new start
     */
    public void setStart(int start){
        this.start=start;
    }
    
    /**
     * Sets the end.
     * 
     * @param end the new end
     */
    public void setEnd(int end){
        this.end=end;
    }
    
    /**
     * Sets the step.
     * 
     * @param step the new step
     */
    public void setStep(int step){
        this.step=step;
    }
    
    /**
     * Sets the value.
     * 
     * @param value the new value
     */
    public void setValue(String value){
        this.value=value;
    }
    
    // Gets
    
    /**
     * Gets the start.
     * 
     * @return the start
     */
    public int getStart(){
        return start;
    }
    
    /**
     * Gets the end.
     * 
     * @return the end
     */
    public int getEnd(){
        return end;
    }
    
    /**
     * Gets the step.
     * 
     * @return the step
     */
    public int getStep(){
        return step;
    }
    
    /**
     * Gets the value.
     * 
     * @return the value
     */
    public String getValue(){
        return value;
    }
    
    /**
     * Sets the rdf attributes.
     */
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
    
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBaseImp#getXml()
     */
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
    
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBaseImp#setXml(java.lang.String)
     */
    @Override
    public void setXml(String xml) {
        this.xml=xml;
    }
}
