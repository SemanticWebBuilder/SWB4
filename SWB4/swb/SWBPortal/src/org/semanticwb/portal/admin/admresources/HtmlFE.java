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

import java.util.ArrayList;
import java.util.Iterator;
import org.semanticwb.portal.admin.admresources.lib.WBAdmResourceAbs;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.w3c.dom.*;

// TODO: Auto-generated Javadoc
/**
 * Objeto que administra Html estatico en una forma de html.
 * <p>
 * Object that administers static html  in a form
 * @author  Jorge Alberto Jim�nez
 */

public class HtmlFE extends WBAdmResourceAbs 
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(HtmlFE.class);
    
    /** The html. */
    private String html=null;
    
    /** The tag. */
    protected Node tag=null;
    
    /** The value. */
    protected String value=null;
    
    /** The language. */
    protected String language=null;
    
    /** The src. */
    protected String src=null;
    
    /** The a attrs. */
    protected ArrayList aAttrs=new ArrayList();
    
    /** The base. */
    protected Resource base=null;
        
    /**
     * Instantiates a new html fe.
     */
    public HtmlFE() {
        html = null;
    }
    
    /**
     * Instantiates a new html fe.
     * 
     * @param name the name
     * @param value the value
     */
    public HtmlFE(String name,String value) {
        this.name=name;
        this.value=value;
    }
    
    
    /**
     * Instantiates a new html fe.
     * 
     * @param value the value
     */
    public HtmlFE(String value) {
        this.value=value;
    }
    
    /**
     * Instantiates a new html fe.
     * 
     * @param tag the tag
     */
    public HtmlFE(Node tag){
        this.tag=tag;
        setAttributes();
    }
    
    /**
     * Instantiates a new html fe.
     * 
     * @param tag the tag
     * @param base the base
     */
    public HtmlFE(Node tag, Resource base){
        this.tag=tag;
        setAttributes();
        this.base=base; 
    }
    
    /**
     * Obtiene el html(xml) final del elemento para mostrar en la admin del recurso
     * obtains the final xml element to show in the resource admin.
     * 
     * @return the html
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
                    Iterator <String>itAttr=aAttrs.iterator();
                    while(itAttr.hasNext()){
                        String attr=itAttr.next();
                        xml=SWBUtils.TEXT.replaceAll(xml, "{@db_"+attr+"}", base.getAttribute(attr));
                    }                    
                }
                else xml="";
            }
        } 
        catch(Exception e) { log.error(e); }
        return xml;
    }
    
   /* (non-Javadoc)
    * @see org.semanticwb.portal.admin.admresources.lib.WBAdmResourceAbs#setName(java.lang.String)
    */
   public void setName(String name){
        this.name=name;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBAdmResourceAbs#getName()
     */
    public String getName(){
        return name;
    }
    
    /**
     * Sets the language.
     * 
     * @param language the new language
     */
    public void setLanguage(String language){
        this.language=language;
    }
    
    
    /**
     * Gets the language.
     * 
     * @return the language
     */
    public String getLanguage(){
        return language;
    }    
    
    /**
     * Sets the src.
     * 
     * @param src the new src
     */
    public void setSrc(String src)
    {
        this.src = src;
    }
    
    /**
     * Gets the src.
     * 
     * @return the src
     */
    public String getSrc()
    {
        return src;
    }
    
    /**
     * Sets the value.
     * 
     * @param value the new value
     */
    public void setValue(String value){
        this.value=value;
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
     * Set attributes to class according with the xml tag element.
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
                        else if(attrName.equalsIgnoreCase("id")) id=attrValue;
                        else if(attrName.equalsIgnoreCase("language")) language=attrValue;
                        else if(attrName.equalsIgnoreCase("src"))
                        {
                            src = attrValue;
                            if(src.toLowerCase().startsWith("{@webpath}")){
                                src=SWBPlatform.getContextPath() + src.substring(10);
                            }
                            continue;
                        }                        
                    }
                }
            }
            value= getFullText(tag);
            //Revisa si encuentra constantes a sustituir por atributos de bd
            Document dom=null;
            try{
                dom=SWBUtils.XML.getNewDocument(); 
            }catch(Exception e){
                log.error(e);
            }
            if(dom!=null && tag!=null)
            {
                Node child = dom.importNode(tag, true);
                dom.appendChild(child);
                String xml=SWBUtils.XML.domToXml(dom, "ISO-8859-1", true);
                if(xml!=null && !"".equals(xml.trim())) 
                {
                    Iterator <String>it=SWBUtils.TEXT.findInterStr(xml, "{@db_" , "}");
                    while(it.hasNext()){
                        aAttrs.add(it.next());
                    }
                }
            }            
        }
    }
    
 
    
    /**
     * Gets the full text.
     * 
     * @param node the node
     * @return the full text
     */
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
    
