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

import org.semanticwb.portal.admin.admresources.MapFE;
import org.semanticwb.portal.admin.admresources.lib.WBAdmResourceAbs;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.w3c.dom.*;

// TODO: Auto-generated Javadoc
/**
 * Objeto que administra un elemento de tipo area en una forma de html.
 * <p>
 * Object that administers an html area element in a form
 * @author  Jorge Alberto Jim�nez
 */


public class AreaMapFE extends WBAdmResourceAbs
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(AreaMapFE.class);
    
    /** The title. */
    private String title=null;
    
    /** The shape. */
    private String shape=null;
    
    /** The coords. */
    private String coords=null;
    
    /** The href. */
    private String href=null;
    
    /** The alt. */
    private String alt=null;
    
    /** The onclick. */
    private String onclick=null;
    
    /** The mapfe. */
    private MapFE mapfe=null;
    
    /** The tag. */
    protected Node tag=null;
    
    /**
     * Creates a new instance of OptionSelectFE.
     */
    public AreaMapFE() {
    }
    
    /**
     * Creates a new instance of OptionSelectFE width default values.
     * 
     * @param title the title
     * @param shape the shape
     * @param coords the coords
     * @param href the href
     * @param alt the alt
     * @param onclick the onclick
     */
    public AreaMapFE(String title, String shape, String coords, String href, String alt, String onclick) {
        this.title=title;
        this.shape=shape;
        this.coords=coords;
        this.href=href;
        this.alt=alt;
        this.onclick=onclick;
    }
    
    /**
     * Creates a new instwance with the default parameters.
     * 
     * @param tag the tag
     */
    public AreaMapFE(Node tag){
        this.tag=tag;
        setAttributes();
    }
    
    //sets
    /**
     * Sets the title.
     * 
     * @param title the new title
     */
    public void setTitle(String title){
        this.title=title;
    }
    
    /**
     * Sets the shape.
     * 
     * @param shape the new shape
     */
    public void setShape(String shape){
        this.shape=shape;
    }
    
 
    /**
     * Sets the coords.
     * 
     * @param coords the new coords
     */
    public void setCoords(String coords){
        this.coords=coords;
    }
    
    /**
     * Sets the href.
     * 
     * @param href the new href
     */
    public void setHref(String href){
        this.href=href;
    }    
    
    /**
     * Sets the alt.
     * 
     * @param alt the new alt
     */
    public void setAlt(String alt){
        this.alt=alt;
    }      

    /**
     * Sets the onclick.
     * 
     * @param onclick the new onclick
     */
    public void setOnclick(String onclick){
        this.onclick=onclick;
    }            
    
    /**
     * Sets the map fe obj.
     * 
     * @param mapfe the new map fe obj
     */
    public void setMapFEObj(MapFE mapfe){
        this.mapfe=mapfe;
    }
    
    //gets
    /**
     * Gets the title.
     * 
     * @return the title
     */
    public String getTitle(){
        return this.title;
    }

    /**
     * Gets the shape.
     * 
     * @return the shape
     */
    public String getShape(){
        return this.shape;
    }

   /**
    * Gets the coords.
    * 
    * @return the coords
    */
   public String getCoords(){
        return this.coords;
    }
 
    /**
     * Gets the href.
     * 
     * @return the href
     */
    public String getHref(){
        return this.href;
    }   

    /**
     * Gets the alt.
     * 
     * @return the alt
     */
    public String getAlt(){
        return this.alt;
    }   

    /**
     * Gets the onclick.
     * 
     * @return the onclick
     */
    public String getOnclick(){
        return this.onclick;
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
                Element child=dom.createElement("area");
                if(title!=null) child.setAttribute("title",title);
                if(shape!=null) child.setAttribute("shape",shape);
                if(coords!=null) child.setAttribute("coords",coords);
                if(href!=null) child.setAttribute("href",href);
                if(alt!=null) child.setAttribute("alt",alt);
                if(onclick!=null) child.setAttribute("onclick",onclick);
                if(moreattr!=null) child.setAttribute("moreattr",moreattr);
                dom.appendChild(child);

                xml=SWBUtils.XML.domToXml(dom, "ISO-8859-1", true);
                if(xml!=null && !"".equals(xml.trim())) xml=xml.substring(xml.indexOf("<area"));
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
                        if(attrName.equalsIgnoreCase("id")) id=attrValue;
                        else if(attrName.equalsIgnoreCase("style")) style=attrValue;
                        else if(attrName.equalsIgnoreCase("class")) styleclass=attrValue;
                        else if(attrName.equalsIgnoreCase("moreattr")) moreattr=attrValue;
                        //propios
                        else if(attrName.equalsIgnoreCase("title")) title=attrValue;
                        else if(attrName.equalsIgnoreCase("shape")) shape=attrValue;
                        else if(attrName.equalsIgnoreCase("coords")) coords=attrValue;
                        else if(attrName.equalsIgnoreCase("href")) href=attrValue;
                        else if(attrName.equalsIgnoreCase("alt")) alt=attrValue;
                        else if(attrName.equalsIgnoreCase("onclick")) onclick=attrValue;
                    }
                }
            }
        }
    }
    
}
