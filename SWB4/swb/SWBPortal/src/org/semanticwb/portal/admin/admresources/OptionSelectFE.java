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

import java.util.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.admin.admresources.lib.WBAdmResourceAbs;
import org.w3c.dom.*;

// TODO: Auto-generated Javadoc
/**
 * Objeto que administra un elemento de tipo Option en un input de una forma de html.
 * <p>
 * Object that administers an html Imput option element in a form
 * @author  Jorge Alberto Jim�nez
 */

public class OptionSelectFE extends WBAdmResourceAbs
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(OptionSelectFE.class);

    /** The value. */
    private String value=null;
    
    /** The label. */
    private String label=null;
    
    /** The isselected. */
    private boolean isselected=false;
    
    /** The selectfe. */
    private SelectFE selectfe=null;
    
    /** The tag. */
    protected Node tag=null;
    
    /** The isdisabled. */
    private boolean isdisabled=false;
    
    
    /**
     * Creates a new instance of OptionSelectFE.
     */
    public OptionSelectFE() {
    }
    
    /**
     * Creates a new instance of OptionSelectFE width default values.
     * 
     * @param value the value
     * @param label the label
     * @param isselected the isselected
     */
    public OptionSelectFE(String value,String label,boolean isselected) {
        this.value=value;
        this.label=label;
        this.isselected=isselected;
    }
    
    /**
     * Creates a new instwance with the default parameters.
     * 
     * @param tag the tag
     */
    public OptionSelectFE(Node tag){
        this.tag=tag;
        setAttributes();
    }
    
    //sets
   
    /**
     * Sets the value.
     * 
     * @param value the new value
     */
    public void setValue(String value){
        this.value=value;
    }
    
    /**
     * Sets the disabled.
     * 
     * @param isdisabled the new disabled
     */
    public void setDisabled(boolean isdisabled){
        this.isdisabled=isdisabled;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBAdmResourceAbs#setLabel(java.lang.String)
     */
    public void setLabel(String label){
        this.label=label;
    }
    
    /**
     * Sets the selected.
     * 
     * @param isselected the new selected
     */
    public void setSelected(boolean isselected){
        this.isselected=isselected;
    }
    
    /**
     * Sets the select fe obj.
     * 
     * @param selectfe the new select fe obj
     */
    public void setSelectFEObj(SelectFE selectfe){
        this.selectfe=selectfe;
    }
    
    //gets
    
    /**
     * Gets the value.
     * 
     * @return the value
     */
    public String getValue(){
        return value;
    }
    
    /**
     * Gets the disabled.
     * 
     * @return the disabled
     */
    public boolean getDisabled(){
        return isdisabled;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.admresources.lib.WBAdmResourceAbs#getLabel()
     */
    public String getLabel(){
        return label;
    }
    
    /**
     * Gets the selected.
     * 
     * @return the selected
     */
    public boolean getSelected(){
        return isselected;
    }
    
    /**
     * Obtiene el html(xml) final del elemento para mostrar en la admin del recurso
     * obtains the final xml element to show in the resource admin.
     * 
     * @return the html
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
