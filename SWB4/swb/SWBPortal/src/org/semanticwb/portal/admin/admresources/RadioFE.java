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

/**
 * Objeto que administra un elemento html de tipo Radio.
 * <p>
 * Object that administers an element Radio in a html form
 * @author  Jorge Alberto Jim�nez
 */

import org.semanticwb.portal.admin.admresources.lib.WBAdmResourceAbs;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.w3c.dom.*;

// TODO: Auto-generated Javadoc
/**
 * The Class RadioFE.
 */
public class RadioFE extends WBAdmResourceAbs
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(RadioFE.class);
    
    /** The accesskey. */
    private String accesskey=null;
    
    /** The align. */
    private String align=null;
    
    /** The isdisabled. */
    private boolean isdisabled=false;
    
    /** The isreadonly. */
    private boolean isreadonly=false;
    
    /** The value. */
    private String value=null;
    
    /** The ischecked. */
    private boolean ischecked=false;
    
    /** The xmltag. */
    private String xmltag=null;
    
    /** The tag. */
    private Node tag=null;

    /** The required. */
    protected boolean required=false;
    
    /** The prompt message. */
    protected String promptMessage=null;
    
    /** The invalid message. */
    protected String invalidMessage=null;
    
    /**
     * Creates a new instance of RadioFE.
     */
    public RadioFE() {
    }
    
    /**
     * Creates a new instance of RadioFE width default values.
     * 
     * @param name the name
     * @param value the value
     * @param ischecked the ischecked
     */
    public RadioFE(String name,String value,boolean ischecked) {
        this.name=name;
        this.value=value;
        this.ischecked=ischecked;
    }
    
    /**
     * Creates a new instwance with the default parameters.
     * 
     * @param tag the tag
     */
    public RadioFE(Node tag){
        this.tag=tag;
        setAttributes();
    }
    
      // Sets
    
    /**
       * Sets the access key.
       * 
       * @param accesskey the new access key
       */
      public void setAccessKey(String accesskey){
        this.accesskey=accesskey;
    }
    
    /**
     * Sets the align.
     * 
     * @param align the new align
     */
    public void setAlign(String align){
        this.align=align;
    }
    
    /**
     * Sets the disabled.
     * 
     * @param isdisabled the new disabled
     */
    public void setDisabled(boolean isdisabled){
        this.isdisabled=isdisabled;
    }
    
    /**
     * Sets the read only.
     * 
     * @param isreadonly the new read only
     */
    public void setReadOnly(boolean isreadonly){
        this.isreadonly=isreadonly;
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
     * Sets the checked.
     * 
     * @param ischecked the new checked
     */
    public void setChecked(boolean ischecked){
        this.ischecked=ischecked;
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
        return accesskey;
    }
    
    /**
     * Gets the align.
     * 
     * @return the align
     */
    public String getAlign(){
        return align;
    }
    
    /**
     * Gets the disabled.
     * 
     * @return the disabled
     */
    public boolean getDisabled(){
        return isdisabled;
    }
    
    /**
     * Gets the read only.
     * 
     * @return the read only
     */
    public boolean getReadOnly(){
        return isreadonly;
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
     * Gets the checked.
     * 
     * @return the checked
     */
    public boolean getChecked(){
        return ischecked;
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
                Element root = null;
                if(label!=null) 
                {
                    root=dom.createElement("label");
                    root.appendChild(dom.createTextNode(label.trim()));
                }
                if(root!=null) dom.appendChild(root);
                Element child=dom.createElement("input");
                child.setAttribute("type", "radio");
                if(id!=null) child.setAttribute("id",id);
                if(name!=null) child.setAttribute("name",name);
                if(value!=null) child.setAttribute("value",value);
                if(align!=null) child.setAttribute("align",align);
                if(isdisabled) child.setAttribute("disabled","true");
                if(isreadonly) child.setAttribute("readonly","true");
                
                if(dbconnmgr.getAttribute(name)==null && ischecked){
                    child.setAttribute("checked","true");
                }
                if(dbconnmgr.getAttribute(name)!=null && value.equalsIgnoreCase(dbconnmgr.getAttribute(name))){
                    child.setAttribute("checked","true");
                }
                //if(ischecked || (value!=null && dbconnmgr!=null && value.equalsIgnoreCase(dbconnmgr.getAttribute(name)))) child.setAttribute("checked","true");
                if(accesskey!=null) child.setAttribute("accesskey",accesskey);
                if(style!=null) child.setAttribute("style",style);
                if(styleclass!=null) child.setAttribute("class",styleclass);
                if(moreattr!=null) child.setAttribute("moreattr",moreattr);
                if(root!=null) root.appendChild(child); 
                else dom.appendChild(child);

                setJsFrameworkAttributes(child);

                xml=SWBUtils.XML.domToXml(dom, "ISO-8859-1", true);
                if(xml!=null && !"".equals(xml.trim())) 
                {
                    if(xml.indexOf("<label")!=-1 && xml.indexOf("<label") < xml.indexOf("<input")) xml=xml.substring(xml.indexOf("<label"));
                    else xml=xml.substring(xml.indexOf("<input"));
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
                        else if(attrName.equalsIgnoreCase("accesskey")) accesskey=attrValue;
                        else if(attrName.equalsIgnoreCase("align")) align=attrValue;
                        else if(attrName.equalsIgnoreCase("disabled")) isdisabled=Boolean.valueOf(attrValue).booleanValue();
                        else if(attrName.equalsIgnoreCase("readonly")) isreadonly=Boolean.valueOf(attrValue).booleanValue();
                        else if(attrName.equalsIgnoreCase("value")) value=attrValue;
                        else if(attrName.equalsIgnoreCase("checked")) ischecked=Boolean.valueOf(attrValue).booleanValue();
                        else if(attrName.equalsIgnoreCase("label")) label=attrValue;
                        else if(attrName.equalsIgnoreCase("promptMessage")) promptMessage=attrValue;
                        else if(attrName.equalsIgnoreCase("invalidMessage")) invalidMessage=attrValue;
                    }
                }
            }
        }
    }
    
    /**
     * Manejo de Frameworks de JavaScript.
     * 
     * @param child the new js framework attributes
     */
    private void setJsFrameworkAttributes(Element child){
            String jsFramework=getFormFE().getJsFrameWork();
            if(jsFramework!=null){
                if(jsFramework.equalsIgnoreCase("dojo")){
                    child.setAttribute("dojoType","dijit.form.RadioButton");
                    if(required){
                        child.setAttribute("required","true");
                    }
                    if(promptMessage!=null){
                        child.setAttribute("promptMessage",promptMessage);
                    }
                    if(invalidMessage!=null){
                        child.setAttribute("invalidMessage",invalidMessage);
                    }                   
                }
            }
    }

}
