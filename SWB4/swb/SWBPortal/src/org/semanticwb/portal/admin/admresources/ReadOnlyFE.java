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
 


package org.semanticwb.portal.admin.admresources;


import org.semanticwb.portal.admin.admresources.lib.WBAdmResourceAbs;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.w3c.dom.*;

/**
 * Objeto que administra un elemento html de tipo ReadOnly.
 * <p>
 * Object that administers an element ReadOnly in a html form
 * @author  Jorge Alberto Jim�nez
 */

public class ReadOnlyFE extends WBAdmResourceAbs
{
    private static Logger log = SWBUtils.getLogger(ReadOnlyFE.class);
    
    private String value=null;
    private int size=-1;
    private int maxlength=-1;
    private String xmltag=null;
    protected Node tag=null;
    
    /** Creates a new instance of ReadOnlyFE */
    public ReadOnlyFE() {
    }
    
    /** Creates a new instance of ReadOnlyFE */
    public ReadOnlyFE(String name) {
        this.name=name;
    }
    
    /** Creates a new instwance with the default parameters */
    public ReadOnlyFE(Node tag){
        this.tag=tag;
        setAttributes();
    }
    
     //sets
    
    public void setValue(String value){
        this.value=value;
    }
    
     public void setSize(int size){
        this.size=size;
    }
    
    public void setMaxLength(int maxlength){
        this.maxlength=maxlength;
    }
    
    /**determines de xml tag name the form element will be added in a resource.*/
    public void setXmlTag(String xmltag){
        this.xmltag=xmltag;
    }
    
    //gets
    
    public String getValue(){
        return value;
    }
    
     public int getSize(int size){
        return size;
    }
    
    public int getMaxLength(){
        return maxlength;
    }
    
     /**determines de xml tag name the form element will be added in a resource.*/
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
                child.setAttribute("type", "readonly");
                if(id!=null) child.setAttribute("id",id);
                if(name!=null) {
                    child.setAttribute("name",name);
                    if(id==null) child.setAttribute("id",name);
                }
                if(value!=null) child.setAttribute("value",value);
                if(size!=-1) child.setAttribute("size",String.valueOf(size));
                if(maxlength!=-1) child.setAttribute("maxlength",String.valueOf(maxlength));
                if(style!=null) child.setAttribute("style",style);
                if(styleclass!=null) child.setAttribute("class",styleclass);
                if(moreattr!=null) child.setAttribute("moreattr",moreattr);
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
                        else if(attrName.equalsIgnoreCase("size")) size=Integer.parseInt(attrValue);
                        else if(attrName.equalsIgnoreCase("maxlength")) maxlength=Integer.parseInt(attrValue);
                        else if(attrName.equalsIgnoreCase("value")) value=attrValue;
                        else if(attrName.equalsIgnoreCase("label")) label=attrValue;
                    }
                }
            }
        }
    }
     
}
