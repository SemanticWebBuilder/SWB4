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
import org.w3c.dom.*;

// TODO: Auto-generated Javadoc
/**
 * Objeto que administra un elemento html de tipo Password.
 * <p>
 * Object that administers an element Password in a html form
 * @author  Jorge Alberto Jim�nez
 */

public class PasswordFE extends TextFE
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(PasswordFE.class);
    
    /**
     * Creates a new instance of PasswordFE.
     */
    public PasswordFE() {
    }
    
    /**
     * Creates a new instance of PasswordFE with default values.
     * 
     * @param name the name
     */
    public PasswordFE(String name) {
        this.name=name;
    }
    
     /**
      * Creates a new instwance with the default parameters.
      * 
      * @param tag the tag
      */
    public PasswordFE(Node tag){
        this.tag=tag; 
        setAttributes();
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
                child.setAttribute("type", "password");
                if(id!=null) child.setAttribute("id",id);
                if(name!=null) {
                    child.setAttribute("name",name);
                    if(id==null) child.setAttribute("id",name);
                }
                if(size!=-1) child.setAttribute("size",String.valueOf(size));
                if(maxlength!=-1) child.setAttribute("maxlength",String.valueOf(maxlength));
                if(width!=-1) child.setAttribute("width",String.valueOf(width));
                if(height!=-1) child.setAttribute("height",String.valueOf(height));
                if(value!=null) child.setAttribute("value",value);
                if(align!=null) child.setAttribute("align",align);
                if(isdisabled) child.setAttribute("disabled","true");
                if(isreadonly) child.setAttribute("readonly","true");
                if(!isautocomplete) child.setAttribute("autocomplete","off");
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
     * Manejo de Frameworks de JavaScript.
     * 
     * @param child the new js framework attributes
     */
    private void setJsFrameworkAttributes(Element child){
            String jsFramework=getFormFE().getJsFrameWork();
            if(jsFramework!=null){
                if(jsFramework.equalsIgnoreCase("dojo")){
                    child.setAttribute("dojoType","dijit.form.ValidationTextBox");
                    if(required){
                        child.setAttribute("required","true");
                    }
                    if(promptMessage!=null){
                        child.setAttribute("promptMessage",promptMessage);
                    }
                    if(invalidMessage!=null){
                        child.setAttribute("invalidMessage",invalidMessage);
                    }
                    if(trim){
                        child.setAttribute("trim","true");
                    }
                    if(regExp!=null || sjspatron!=null){
                        if(regExp!=null) child.setAttribute("regExp",regExp);
                        if(sjspatron!=null) child.setAttribute("regExp",sjspatron);
                    }else if(svaltype!=null && svaltype.equalsIgnoreCase("js_numbers")) {
                        if(regExp!=null) child.setAttribute("regExp","\\d+");
                    }else if(svaltype!=null && svaltype.equalsIgnoreCase("js_alphabetic")) {
                        if(regExp!=null) child.setAttribute("regExp","\\w+");
                    }
                }
            }
    }

}
