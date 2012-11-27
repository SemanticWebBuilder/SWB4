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
package org.semanticwb.xforms.ui.container;

import org.semanticwb.xforms.drop.RDFElement;
import org.semanticwb.xforms.lib.WBXformsContainer;
import java.util.*;
import org.semanticwb.xforms.ui.*;
import org.semanticwb.SWBUtils;
import org.semanticwb.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class XFGroup.
 * 
 * @author  jorge.jimenez
 */
public class XFGroup extends WBXformsContainer 
{
    
    /** The log. */
    private static Logger log=SWBUtils.getLogger(XFGroup.class);
    
    /** The appearance. */
    protected String appearance=null;
    
    /** The rdf element. */
    protected RDFElement rdfElement=null;
    
    /** The instance elements. */
    HashMap instanceElements=new HashMap();
    
    /**
     * Instantiates a new xF group.
     * 
     * @param rdfElement the rdf element
     */
    public XFGroup(RDFElement rdfElement){
        this.rdfElement=rdfElement;
        setRDFAttributes();
    }
    
    /**
     * Instantiates a new xF group.
     * 
     * @param rdfElement the rdf element
     * @param instanceElements the instance elements
     */
    public XFGroup(RDFElement rdfElement,HashMap instanceElements){
        this.rdfElement=rdfElement;
        this.instanceElements=instanceElements;
        setRDFAttributes();
    }
    
    // Sets
    
    /**
     * Sets the appearance.
     * 
     * @param appearance the new appearance
     */
    public void setAppearance(String appearance) {
        this.appearance=appearance;
    }
    
    // Gets
    
    /**
     * Gets the appearance.
     * 
     * @return the appearance
     */
    public String getAppearance() {
        return appearance;
    }
    
    /**
     * Sets the rdf attributes.
     */
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
    
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBaseImp#getXml()
     */
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
    
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBaseImp#getXmlBind()
     */
    @Override
    public String getXmlBind() {
        return showBinds();
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBaseImp#setXml(java.lang.String)
     */
    @Override
    public void setXml(String xml) {
        this.xml=xml;
    }
}
