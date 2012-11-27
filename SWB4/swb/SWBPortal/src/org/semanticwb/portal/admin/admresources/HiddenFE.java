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
package org.semanticwb.portal.admin.admresources;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.admin.admresources.db.AdmDBConnMgr;
import org.semanticwb.portal.admin.admresources.lib.*;
import org.w3c.dom.*;

// TODO: Auto-generated Javadoc
/**
 * Objeto que administra un elemento de tipo Hidden en una forma de html.
 * <p>
 * Object that administers an html Hidden element in a form
 * @author  Jorge Alberto Jim�nez
 */

public class HiddenFE extends WBAdmResourceAbs
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(HiddenFE.class);
    
    /** The value. */
    private String value=null;
    
    /** The xmltag. */
    private String xmltag=null;
    
    /** The tag. */
    protected Node tag=null;
    
    /**
     * Creates a new instance of HiddenFE.
     */
    public HiddenFE() {
    }
    
    /**
     * Creates a new instance of HiddenFE with default values.
     * 
     * @param name the name
     */
    public HiddenFE(String name) {
        this.name=name;
    }
    
    /**
     * Creates a new instance of HiddenFE with default values.
     * 
     * @param name the name
     * @param value the value
     */
    public HiddenFE(String name,String value) {
        this.name=name;
        this.value=value;
    }
    
    /**
     * Creates a new instwance with the default parameters.
     * 
     * @param tag the tag
     */
    public HiddenFE(Node tag){
        this.tag=tag;
        setAttributes();
    }
    
    //sets
    
    
    /**
     * Sets the value.
     * 
     * @param value the new value
     */
    public void setValue(String value){
        this.value=value;
    }
    
    /**
     * determines de xml tag name the form element will be added in a resource.
     * 
     * @param xmltag the new xml tag
     */
    public void setXmlTag(String xmltag){
        this.xmltag=xmltag;
    }
    
    //gets
    
    /**
     * Gets the value.
     * 
     * @return the value
     */
    public String getValue(){
        return value;
    }
    
     /**
      * determines de xml tag name the form element will be added in a resource.
      * 
      * @return the xml tag
      */
    public String getXmlTag(){
        return xmltag;
    }
    
    
    /**
     * Obtiene el html(xml) final del elemento para mostrar en la admin del recurso
     * obtains the final xml element to show in the resource admin.
     * 
     * @return the html
     */    
     public String getHtml(){
        String xml="";
        try 
        { 
            Document dom=SWBUtils.XML.getNewDocument(); 
            if(dom!=null)
            {
                Element child=dom.createElement("input");
                child.setAttribute("type", "hidden");
                if(id!=null) child.setAttribute("id",id);
                if(name!=null) {
                    child.setAttribute("name",name);
                    if(id==null) child.setAttribute("id",name);
                }
                if(value!=null) child.setAttribute("value",value);
                if(moreattr!=null) child.setAttribute("moreattr",moreattr);
                dom.appendChild(child);

                xml=SWBUtils.XML.domToXml(dom, "ISO-8859-1", true);
                if(xml!=null && !"".equals(xml.trim())) xml=xml.substring(xml.indexOf("<input"));
                else xml="";
            }
        } 
        catch(Exception e) { log.error(e); }
        return xml;            
    }
     
     
    /**
     * Set attributes to class according with the xml tag element.
     */ 
    public void setAttributes(){
        if(tag!=null){
            NamedNodeMap nnodemap=tag.getAttributes();
            if(nnodemap.getLength()>0){
                for(int i=0;i<nnodemap.getLength();i++){
                    String attrName=nnodemap.item(i).getNodeName();
                    String attrValue=nnodemap.item(i).getNodeValue();
                    if(attrValue!=null && !attrValue.equals("")){
                        //defecto
                        if(attrName.equalsIgnoreCase("name")) name=attrValue;
                        else if(attrName.equalsIgnoreCase("id")) id=attrValue;
                        else if(attrName.equalsIgnoreCase("style")) style=attrValue;
                        else if(attrName.equalsIgnoreCase("class")) styleclass=attrValue;
                        else if(attrName.equalsIgnoreCase("moreattr")) moreattr=attrValue;
                        //propios
                        else if(attrName.equalsIgnoreCase("value")) value=attrValue;
                    }
                }
            }
        }
    }
    
}
