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
import org.semanticwb.portal.admin.admresources.lib.WBContainerFE;
import org.w3c.dom.*;


// TODO: Auto-generated Javadoc
/**
 * Objeto que administra un elemento de tipo Map en una forma de html.
 * <p>
 * Object that administers an html Map element in a form
 * @author  Jorge Alberto Jim�nez
 */

public class MapFE extends WBContainerFE
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(MapFE.class);
    
    /** The accesskey. */
    private String accesskey=null;
    
    /** The name. */
    private String name=null;
    
    /** The xmltag. */
    private String xmltag=null;
    
    /** The tag. */
    protected Node tag=null;

   
    /**
     * Creates a new instance of AppletFE.
     * 
     * @param name the name
     */
    public MapFE(String name) {
        this.name=name;
    }
    
    /**
     * Creates a new instwance with the default parameters.
     * 
     * @param tag the tag
     */
    public MapFE(Node tag){
        this.tag=tag;
        setAttributes();
    }
    
    /**
     * Sets the access key.
     * 
     * @param accesskey the new access key
     */
    public void setAccessKey(String accesskey){
        this.accesskey=accesskey;
    }
   
    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBAdmResourceAbs#setName(java.lang.String)
     */
    public void setName(String name){
        this.name=name;
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
     * Gets the access key.
     * 
     * @return the access key
     */
    public String getAccessKey(){
        return this.accesskey;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBAdmResourceAbs#getName()
     */
    public String getName(){
        return this.name;
    }
    
     /**
      * determines de xml tag name the form element will be added in a resource.
      * 
      * @return the xml tag
      */
    public String getXmlTag(){
        return this.xmltag;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBContainerFE#add(java.lang.Object)
     */
    public void add(Object obj){
       super.add(obj);
       setMapFEName(obj);
    }
    
    /**
     * Sets the map fe name.
     * 
     * @param obj the new map fe name
     */
    private void setMapFEName(Object obj){
       if(obj instanceof AreaMapFE){
           AreaMapFE objareafe=(AreaMapFE)obj;
           objareafe.setMapFEObj(this);
         }
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
                Element child=dom.createElement("map");
                if(accesskey!=null) child.setAttribute("accesskey",accesskey);
                if(id!=null) child.setAttribute("id",id);
                if(name!=null) {
                    child.setAttribute("name",name);
                    if(id==null) child.setAttribute("id",name);
                }
                if(moreattr!=null) child.setAttribute("moreattr",moreattr);
                
                Document area=SWBUtils.XML.xmlToDom("<map>"+show()+"</map>");
                NodeList ndl = area.getElementsByTagName("area");
                for(int i=0;  i < ndl.getLength();i++)
                {
                    child.appendChild(dom.importNode(ndl.item(i), true));
                }
                dom.appendChild(child);
                
                xml=SWBUtils.XML.domToXml(dom, "ISO-8859-1", true);
                if(xml!=null && !"".equals(xml.trim())) xml=xml.substring(xml.indexOf("<map"));
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

                        else if(attrName.equalsIgnoreCase("style")) style=attrValue;
                        else if(attrName.equalsIgnoreCase("class")) styleclass=attrValue;
                        else if(attrName.equalsIgnoreCase("moreattr")) moreattr=attrValue;
                        //propios
                        else if(attrName.equalsIgnoreCase("accesskey")) accesskey=attrValue;
                        else if(attrName.equalsIgnoreCase("name")) name=attrValue;
                    }
                }
            }
        }
    }
}
