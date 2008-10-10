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

import java.util.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.admin.admresources.lib.WBAdmResourceAbs;
import org.w3c.dom.*;

/**
 * Objeto que administra un elemento de tipo Option en un input de una forma de html.
 * <p>
 * Object that administers an html Imput option element in a form
 * @author  Jorge Alberto Jim�nez
 */

public class OptionSelectFE extends WBAdmResourceAbs
{
    private static Logger log = SWBUtils.getLogger(OptionSelectFE.class);

    private String value=null;
    private String label=null;
    private boolean isselected=false;
    private SelectFE selectfe=null;
    protected Node tag=null;
    private boolean isdisabled=false;
    
    
    /** Creates a new instance of OptionSelectFE */
    public OptionSelectFE() {
    }
    
    /** Creates a new instance of OptionSelectFE width default values*/
    public OptionSelectFE(String value,String label,boolean isselected) {
        this.value=value;
        this.label=label;
        this.isselected=isselected;
    }
    
    /** Creates a new instwance with the default parameters */
    public OptionSelectFE(Node tag){
        this.tag=tag;
        setAttributes();
    }
    
    //sets
   
    public void setValue(String value){
        this.value=value;
    }
    
    public void setDisabled(boolean isdisabled){
        this.isdisabled=isdisabled;
    }
    
    public void setLabel(String label){
        this.label=label;
    }
    
    public void setSelected(boolean isselected){
        this.isselected=isselected;
    }
    
    public void setSelectFEObj(SelectFE selectfe){
        this.selectfe=selectfe;
    }
    
    //gets
    
    public String getValue(){
        return value;
    }
    
    public boolean getDisabled(){
        return isdisabled;
    }
    
    public String getLabel(){
        return label;
    }
    
    public boolean getSelected(){
        return isselected;
    }
    
    /**
    * Obtiene el html(xml) final del elemento para mostrar en la admin del recurso
    * obtains the final xml element to show in the resource admin
    */   
     public String getHtml()
     {
        boolean flag=false; 
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
                Element child=dom.createElement("option");
                if(value!=null) child.setAttribute("value",value);
                if(value!=null && dbconnmgr!=null)
                {
                    Iterator attrvalues=dbconnmgr.getAttributeValues(selectfe.getName()).iterator();
                    while(attrvalues.hasNext())
                    {
                        String attrvalue=(String)attrvalues.next();
                        if(attrvalue.equalsIgnoreCase(value))
                        {
                            flag=true;
                            child.setAttribute("selected","true");
                        }
                    }
                }
                if(!flag && isselected) child.setAttribute("selected","true");
                if(isdisabled) child.setAttribute("disabled","true");
                if(style!=null) child.setAttribute("style",style);
                if(styleclass!=null) child.setAttribute("class",styleclass);
                if(moreattr!=null) child.setAttribute("moreattr",moreattr);
                if(root!=null) root.appendChild(child); 
                else dom.appendChild(child);

                xml=SWBUtils.XML.domToXml(dom, "ISO-8859-1", true);
                if(xml!=null && !"".equals(xml.trim())) 
                {
                    if(xml.indexOf("<label")!=-1 && xml.indexOf("<label") < xml.indexOf("<option")) xml=xml.substring(xml.indexOf("<label"));
                    else xml=xml.substring(xml.indexOf("<option"));
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
                        else if(attrName.equalsIgnoreCase("value")) value=attrValue;
                        else if(attrName.equalsIgnoreCase("disabled")) isdisabled=Boolean.valueOf(attrValue).booleanValue();
                        else if(attrName.equalsIgnoreCase("selected")) isselected=Boolean.valueOf(attrValue).booleanValue();
                        else if(attrName.equalsIgnoreCase("label")) label=attrValue;
                    }
                }
            }
        }
    }
    
}
