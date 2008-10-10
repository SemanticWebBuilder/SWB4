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


package org.semanticwb.portal.admin.admresources;

import org.semanticwb.portal.admin.admresources.lib.WBAdmResourceAbs;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.w3c.dom.*;

/**
 * Objeto que administra Html estatico en una forma de html.
 * <p>
 * Object that administers static html  in a form
 * @author  Jorge Alberto Jim�nez
 */

public class HtmlFE extends WBAdmResourceAbs 
{
    private static Logger log = SWBUtils.getLogger(HtmlFE.class);
    
    private String html=null;
    protected Node tag=null;
    protected String value=null;
    protected String language=null;
    protected String src=null;
    
    public HtmlFE() {
        html = null;
    }
    
    public HtmlFE(String name,String value) {
        this.name=name;
        this.value=value;
    }
    
    
    public HtmlFE(String value) {
        this.value=value;
    }
    
    public HtmlFE(Node tag){
        this.tag=tag;
        setAttributes();
    }
    
    /**
    * Obtiene el html(xml) final del elemento para mostrar en la admin del recurso
    * obtains the final xml element to show in the resource admin
    */  
    public String getHtml() {
        String xml="";
        try 
        { 
            Document dom=SWBUtils.XML.getNewDocument(); 
            if(dom!=null && tag!=null)
            {
                Node child = dom.importNode(tag, true);
                dom.appendChild(child);
                xml=SWBUtils.XML.domToXml(dom, "ISO-8859-1", true);
                if(xml!=null && !"".equals(xml.trim())) 
                {
                    xml=xml.substring(xml.indexOf("<"+tag.getNodeName().toLowerCase()));
                    xml=xml.replaceFirst("\\{@webpath\\}", SWBPlatform.getContextPath());
                }
                else xml="";
            }
        } 
        catch(Exception e) { log.error(e); }
        return xml;
    }
    
   public void setName(String name){
        this.name=name;
    }
    
    public String getName(){
        return name;
    }
    
    public void setLanguage(String language){
        this.language=language;
    }
    
    
    public String getLanguage(){
        return language;
    }    
    
    public void setSrc(String src)
    {
        this.src = src;
    }
    
    public String getSrc()
    {
        return src;
    }
    
    public void setValue(String value){
        this.value=value;
    }
    
    public String getValue(){
        return value;
    }
    
    
    /**
    * Set attributes to class according with the xml tag element
    */
    public void setAttributes(){
        if(tag!=null)
        {
            NamedNodeMap nnodemap=tag.getAttributes();
            if(nnodemap.getLength()>0){
                for(int i=0;i<nnodemap.getLength();i++){
                    String attrName=nnodemap.item(i).getNodeName();
                    String attrValue=nnodemap.item(i).getNodeValue();
                    if(attrValue!=null && !attrValue.equals(""))
                    {
                        if(attrName.equalsIgnoreCase("name")) name=attrValue;
                        else if(attrName.equalsIgnoreCase("language")) language=attrValue;
                        else if(attrName.equalsIgnoreCase("src"))
                        {
                            src = attrValue;
                            if(src.toLowerCase().startsWith("{@webpath}"))
                                src=SWBPlatform.getContextPath() + src.substring(10);
                            continue;
                        }                        
                    }
                }
            }
            value= getFullText(tag);
        }
    }
    
 
    
    private String getFullText(Node node) 
    {
        if(node== null || (node!=null && !node.hasChildNodes())) return null;
        StringBuffer ret = new StringBuffer("");
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) 
        {
            Node child = children.item(i);
            int type = child.getNodeType();
            if (type == Node.TEXT_NODE  || type == Node.CDATA_SECTION_NODE) 
            {
                ret.append(child.getNodeValue());
            }
            else if (type == Node.ENTITY_REFERENCE_NODE) 
            {
                // The JAXP spec is unclear about whether or not it's
                // possible for entity reference nodes to appear in the
                // tree. Just in case, let's expand them recursively:
                ret.append(getFullText(child));
                // Validity does require that if they do appear their 
                // replacement text is pure text, no elements.
            }
        }
        return ret.toString();
    }
}
    