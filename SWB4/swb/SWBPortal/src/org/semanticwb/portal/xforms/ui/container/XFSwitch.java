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

import org.semanticwb.portal.xforms.lib.WBXformsContainer;
import java.util.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.xforms.ElementsProp;
import org.semanticwb.portal.xforms.ui.*;

// TODO: Auto-generated Javadoc
/**
 * The Class XFSwitch.
 * 
 * @author  jorge.jimenez
 */
public class XFSwitch extends WBXformsContainer 
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(XFSwitch.class);
    
    /** The appearance. */
    protected String appearance=null;
    
    /** The elements prop. */
    protected ElementsProp elementsProp=null;
    
    /** The cssclass. */
    protected String cssclass=null;
    
    /** The instance elements. */
    HashMap instanceElements=new HashMap();
    
    /**
     * Instantiates a new xF switch.
     * 
     * @param elementsProp the elements prop
     */
    public XFSwitch(ElementsProp elementsProp){
        this.elementsProp=elementsProp;
        setRDFAttributes();
    }
    
    /**
     * Instantiates a new xF switch.
     * 
     * @param elementsProp the elements prop
     * @param instanceElements the instance elements
     */
    public XFSwitch(ElementsProp elementsProp,HashMap instanceElements){
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
    
    /**
     * Sets the css class.
     * 
     * @param cssclass the new css class
     */
    public void setCssClass(String cssclass) {
        this.cssclass=cssclass;
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
     * Gets the css class.
     * 
     * @return the css class
     */
    public String getCssClass() {
        return cssclass;
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
    
    /* (non-Javadoc)
     * @see org.semanticwb.xforms.lib.XformsBaseImp#getXml()
     */
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
