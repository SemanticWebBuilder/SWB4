/**  
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
**/ 
 


/*
 * CheckBoxFE.java
 *
 * Created on 25 de febrero de 2004, 06:30 PM
 */

package org.semanticwb.portal.admin.admresources;

/**
 * Objeto que administra un elemento de tipo CheckBox en una forma de html.
 * <p>
 * Object that administers an html CheckBox element in a form
 * @author  Jorge Alberto Jim�nez
 */

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.admin.admresources.lib.WBAdmResourceAbs;
import org.w3c.dom.*;

public class CheckBoxFE extends WBAdmResourceAbs
{
    private static Logger log = SWBUtils.getLogger(CheckBoxFE.class);
    
    private String accesskey=null;
    private String align=null;
    private boolean isdisabled=false;
    private boolean isreadonly=false;
    private String value=null;
    private boolean ischecked=false;
    private String xmltag=null;
    private Node tag=null;

    protected boolean required=false;
    protected String promptMessage=null;
    protected String invalidMessage=null;
    
    /** Creates a new instance of CheckBoxFE */
    public CheckBoxFE() {
    }
    
    /** Creates a new instance of CheckBoxFE with default values */
    public CheckBoxFE(String name,String value) {
        this.name=name;
        this.value=value;
    }
    
     /** Creates a new instance of CheckBoxFE with default values */
    public CheckBoxFE(Node tag) {
        this.tag=tag;
        setAttributes();
    }
    
      // Sets
    
    public void setAccessKey(String accesskey){
        this.accesskey=accesskey;
    }
    
    public void setAlign(String align){
        this.align=align;
    }
    
    public void setDisabled(boolean isdisabled){
        this.isdisabled=isdisabled;
    }
    
    public void setReadOnly(boolean isreadonly){
        this.isreadonly=isreadonly;
    }
    
    public void setValue(String value){
        this.value=value;
    }
    
    public void setChecked(boolean ischecked){
        this.ischecked=ischecked;
    }
    
     /**determines de xml tag name the form element will be added in a resource.*/
    public void setXmlTag(String xmltag){
        this.xmltag=xmltag;
    }
    
    //gets
    
     public String getAccessKey(){
        return accesskey;
    }
    
    public String getAlign(){
        return align;
    }
    
    public boolean getDisabled(){
        return isdisabled;
    }
    
    public boolean getReadOnly(){
        return isreadonly;
    }
    
    public String getValue(){
        return value;
    }
    
    public boolean getChecked(){
        return ischecked;
    }
    
    public String getXmlTag(){
        return xmltag;
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
                Element root = null;
                if(label!=null) 
                {
                    root=dom.createElement("label");
                    root.appendChild(dom.createTextNode(label.trim()));
                }
                if(root!=null) dom.appendChild(root);
                Element child=dom.createElement("input");
                child.setAttribute("type", "checkbox");
                if(id!=null) child.setAttribute("id",id);
                if(name!=null) {
                    child.setAttribute("name",name);
                    if(id==null) child.setAttribute("id",name);
                }
                if(value!=null) child.setAttribute("value",value);
                if(align!=null) child.setAttribute("align",align);
                if(isdisabled) child.setAttribute("disabled","true");
                if(isreadonly) child.setAttribute("readonly","true");
                if(ischecked || (value!=null && dbconnmgr!=null && value.equalsIgnoreCase(dbconnmgr.getAttribute(name)))) child.setAttribute("checked","true");
                if(accesskey!=null) child.setAttribute("accesskey",accesskey);                
                if(style!=null) child.setAttribute("style",style);
                if(styleclass!=null) child.setAttribute("class",styleclass);
                if(moreattr!=null) child.setAttribute("moreattr",moreattr);

                setJsFrameworkAttributes(child);

                if(root!=null) root.appendChild(child); 
                else dom.appendChild(child);

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
     * Manejo de Frameworks de JavaScript
     * @param child
     */
    private void setJsFrameworkAttributes(Element child){
            String jsFramework=getFormFE().getJsFrameWork();
            if(jsFramework!=null){
                if(jsFramework.equalsIgnoreCase("dojo")){
                    child.setAttribute("dojoType","dijit.form.CheckBox");
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
