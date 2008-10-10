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

import org.semanticwb.portal.admin.admresources.lib.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.w3c.dom.*;

/**
 * Objeto que administra un elemento de tipo Param en un applet.
 * <p>
 * Object that administers a Param in an applet html element
 * @author  Jorge Alberto Jim�nez
 */


public class ParamAppletFE extends WBAdmResourceAbs
{
    private static Logger log = SWBUtils.getLogger(ParamAppletFE.class);
    
    private String name=null;
    private String value=null;
    private String label=null;
    private String input=null;
    private AppletFE appletfe=null;
    protected Node tag=null;
    
    
    /** Creates a new instance of OptionSelectFE */
    public ParamAppletFE() {
    }
    
    /** Creates a new instance of OptionSelectFE width default values*/
    public ParamAppletFE(String name, String value, String label, String input) {
        this.name=name;
        this.value=value;
        this.label=label;
        this.input=input;
    }
    
    /** Creates a new instwance with the default parameters */
    public ParamAppletFE(Node tag){
        this.tag=tag;
        setAttributes();
    }
    
    //sets
    public void setName(String name){
        this.name=name;
    }
    
    public void setValue(String value){
        this.value=value;
    }
    
 
    public void setLabel(String label){
        this.label=label;
    }
    
    public void setInput(String input){
        this.input=input;
    }    
    
    public void setAppletFEObj(AppletFE appletfe){
        this.appletfe=appletfe;
    }
    
    //gets
    public String getName(){
        return name;
    }

    public String getValue(){
        return value;
    }
    
   public String getLabel(){
        return label;
    }
   
   public String getInput(){
        return input;
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
                Element child=dom.createElement("param");
                if(name!=null) child.setAttribute("name",name);
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
