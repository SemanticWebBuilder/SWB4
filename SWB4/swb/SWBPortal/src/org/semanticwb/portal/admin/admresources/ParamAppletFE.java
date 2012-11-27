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

import org.semanticwb.portal.admin.admresources.lib.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.w3c.dom.*;

// TODO: Auto-generated Javadoc
/**
 * Objeto que administra un elemento de tipo Param en un applet.
 * <p>
 * Object that administers a Param in an applet html element
 * @author  Jorge Alberto Jim�nez
 */


public class ParamAppletFE extends WBAdmResourceAbs
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(ParamAppletFE.class);
    
    /** The name. */
    private String name=null;
    
    /** The value. */
    private String value=null;
    
    /** The label. */
    private String label=null;
    
    /** The input. */
    private String input=null;
    
    /** The appletfe. */
    private AppletFE appletfe=null;
    
    /** The tag. */
    protected Node tag=null;
    
    
    /**
     * Creates a new instance of OptionSelectFE.
     */
    public ParamAppletFE() {
    }
    
    /**
     * Creates a new instance of OptionSelectFE width default values.
     * 
     * @param name the name
     * @param value the value
     * @param label the label
     * @param input the input
     */
    public ParamAppletFE(String name, String value, String label, String input) {
        this.name=name;
        this.value=value;
        this.label=label;
        this.input=input;
    }
    
    /**
     * Creates a new instwance with the default parameters.
     * 
     * @param tag the tag
     */
    public ParamAppletFE(Node tag){
        this.tag=tag;
        setAttributes();
    }
    
    //sets
    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBAdmResourceAbs#setName(java.lang.String)
     */
    public void setName(String name){
        this.name=name;
    }
    
    /**
     * Sets the value.
     * 
     * @param value the new value
     */
    public void setValue(String value){
        this.value=value;
    }
    
 
    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBAdmResourceAbs#setLabel(java.lang.String)
     */
    public void setLabel(String label){
        this.label=label;
    }
    
    /**
     * Sets the input.
     * 
     * @param input the new input
     */
    public void setInput(String input){
        this.input=input;
    }    
    
    /**
     * Sets the applet fe obj.
     * 
     * @param appletfe the new applet fe obj
     */
    public void setAppletFEObj(AppletFE appletfe){
        this.appletfe=appletfe;
    }
    
    //gets
    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBAdmResourceAbs#getName()
     */
    public String getName(){
        return name;
    }

    /**
     * Gets the value.
     * 
     * @return the value
     */
    public String getValue(){
        return value;
    }
    
   /* (non-Javadoc)
    * @see org.semanticwb.portal.admin.admresources.lib.WBAdmResourceAbs#getLabel()
    */
   public String getLabel(){
        return label;
    }
   
   /**
    * Gets the input.
    * 
    * @return the input
    */
   public String getInput(){
        return input;
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
                Element root = null;
                if(label!=null) 
                {
                    root=dom.createElement("label");
                    root.appendChild(dom.createTextNode(label.trim()));
                }
                if(root!=null) dom.appendChild(root);
                Element child=dom.createElement("param");
                if(id!=null) child.setAttribute("id",id);
                if(name!=null) {
                    child.setAttribute("name",name);
                    if(id==null) child.setAttribute("id",name);
                }
                if(input!=null && (dbconnmgr!=null && dbconnmgr.getAttribute(input)!=null && !dbconnmgr.getAttribute(input).trim().equals(""))) 
                {
                    String obj=dbconnmgr.getAttribute(input).trim();
                    if(moreattr!=null && moreattr.startsWith("replace('#','')"))
                        obj=obj.replaceAll("#", "");
                    child.setAttribute("value",obj);
                }                  
                else if(value!=null) child.setAttribute("value",value);
                if(input!=null) child.setAttribute("input",input);
                if(moreattr!=null) child.setAttribute("moreattr",moreattr);
                if(root!=null) root.appendChild(child); 
                else dom.appendChild(child);

                xml=SWBUtils.XML.domToXml(dom, "ISO-8859-1", true);
                if(xml!=null && !"".equals(xml.trim())) 
                {
                    if(xml.indexOf("<label")!=-1 && xml.indexOf("<label") < xml.indexOf("<param")) xml=xml.substring(xml.indexOf("<label"));
                    else xml=xml.substring(xml.indexOf("<param"));
                }
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
                        else if(attrName.equalsIgnoreCase("name")) name=attrValue;
                        else if(attrName.equalsIgnoreCase("value")) value=attrValue;
                        else if(attrName.equalsIgnoreCase("label")) label=attrValue;
                        else if(attrName.equalsIgnoreCase("input")) input=attrValue;
                    }
                }
            }
        }
    }
    
}
