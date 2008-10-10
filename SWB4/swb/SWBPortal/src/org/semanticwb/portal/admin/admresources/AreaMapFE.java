/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * AreaMapFE.java
 *
 * Created on 26 de febrero de 2004, 05:59 PM
 */

package org.semanticwb.portal.admin.admresources;

import org.semanticwb.portal.admin.admresources.MapFE;
import org.semanticwb.portal.admin.admresources.lib.WBAdmResourceAbs;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.w3c.dom.*;

/**
 * Objeto que administra un elemento de tipo area en una forma de html.
 * <p>
 * Object that administers an html area element in a form
 * @author  Jorge Alberto Jim�nez
 */


public class AreaMapFE extends WBAdmResourceAbs
{
    private static Logger log = SWBUtils.getLogger(AreaMapFE.class);
    
    private String title=null;
    private String shape=null;
    private String coords=null;
    private String href=null;
    private String alt=null;
    private String onclick=null;
    private MapFE mapfe=null;
    protected Node tag=null;
    
    /** Creates a new instance of OptionSelectFE */
    public AreaMapFE() {
    }
    
    /** Creates a new instance of OptionSelectFE width default values*/
    public AreaMapFE(String title, String shape, String coords, String href, String alt, String onclick) {
        this.title=title;
        this.shape=shape;
        this.coords=coords;
        this.href=href;
        this.alt=alt;
        this.onclick=onclick;
    }
    
    /** Creates a new instwance with the default parameters */
    public AreaMapFE(Node tag){
        this.tag=tag;
        setAttributes();
    }
    
    //sets
    public void setTitle(String title){
        this.title=title;
    }
    
    public void setShape(String shape){
        this.shape=shape;
    }
    
 
    public void setCoords(String coords){
        this.coords=coords;
    }
    
    public void setHref(String href){
        this.href=href;
    }    
    
    public void setAlt(String alt){
        this.alt=alt;
    }      

    public void setOnclick(String onclick){
        this.onclick=onclick;
    }            
    
    public void setMapFEObj(MapFE mapfe){
        this.mapfe=mapfe;
    }
    
    //gets
    public String getTitle(){
        return this.title;
    }

    public String getShape(){
        return this.shape;
    }

   public String getCoords(){
        return this.coords;
    }
 
    public String getHref(){
        return this.href;
    }   

    public String getAlt(){
        return this.alt;
    }   

    public String getOnclick(){
        return this.onclick;
    }       
    
    
    /**
    * Obtiene el html(xml) final del elemento para mostrar en la admin del recurso
    * obtains the final xml element to show in the resource admin
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
    * Set attributes to class according with the xml tag element
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
