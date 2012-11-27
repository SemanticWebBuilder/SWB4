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
package org.semanticwb.portal.xforms.ui.container;

import org.semanticwb.xforms.lib.WBXformsContainer;
import java.util.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.xforms.ElementsProp;
import org.semanticwb.portal.xforms.ui.action.XFSetvalue;
import org.semanticwb.xforms.drop.RDFElement;
import org.semanticwb.xforms.ui.*;
import org.semanticwb.xforms.ui.container.XFSelect;
import org.semanticwb.xforms.ui.container.XFSwitch;

// TODO: Auto-generated Javadoc
/**
 * The Class XFGroup.
 * 
 * @author  jorge.jimenez
 */
public class XFGroup extends WBXformsContainer 
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(XFSetvalue.class);
    
    /** The appearance. */
    protected String appearance=null;
    
    /** The elements prop. */
    protected ElementsProp elementsProp=null;
    
    /** The instance elements. */
    HashMap instanceElements=new HashMap();
    
    /**
     * Instantiates a new xF group.
     * 
     * @param elementsProp the elements prop
     */
    public XFGroup(ElementsProp elementsProp){
        this.elementsProp=elementsProp;
        setRDFAttributes();
    }
    
    /**
     * Instantiates a new xF group.
     * 
     * @param elementsProp the elements prop
     * @param instanceElements the instance elements
     */
    public XFGroup(ElementsProp elementsProp,HashMap instanceElements){
        this.elementsProp=elementsProp;
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
        if(elementsProp.getId()!=null) {
            id=elementsProp.getId();
        }
        if(elementsProp.getLabel()!=null) {
            label=elementsProp.getLabel();
        }
        if(elementsProp.getAppearance()!=null) {
            appearance=elementsProp.getAppearance();
        }
        
        //Checa si el elemento (grupo) tiene subelementos
        if(elementsProp.getElements().size()>0) {
            Iterator itElements=elementsProp.getElements().iterator();
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
                    XFGroup xfgroup = new XFGroup(elementsProp,instanceElements);
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
                }else if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("RANGE")) {
                    XFRange xfrange = new XFRange(rdfElement);
                    instanceElements.put(xfrange.getId(),xfrange.getValue());
                    this.add(xfrange);
                }else if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("STATICTEXT")) {
                    XFStaticText xfstaticText = new XFStaticText(rdfElement);
                    this.add(xfstaticText);
                }else if(rdfElement.getType()!=null && rdfElement.getType().equalsIgnoreCase("TRIGGER")) {
                    XFTrigger xfswitch = new XFTrigger(rdfElement,instanceElements);
                    this.add(xfswitch);
                }
            }
        }
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBaseImp#getXml()
     */
    /**
     * Gets the xml.
     * 
     * @return the xml
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
    /**
     * Gets the xml bind.
     * 
     * @return the xml bind
     */
    @Override
    public String getXmlBind() {
        return showBinds();
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBaseImp#setXml(java.lang.String)
     */
    /**
     * Sets the xml.
     * 
     * @param xml the new xml
     */
    @Override
    public void setXml(String xml) {
        this.xml=xml;
    }
}
