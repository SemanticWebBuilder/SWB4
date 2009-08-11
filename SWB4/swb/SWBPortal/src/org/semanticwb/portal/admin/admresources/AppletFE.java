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


import org.semanticwb.portal.admin.admresources.ParamAppletFE;
import org.semanticwb.portal.admin.admresources.lib.WBContainerFE;
import java.util.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.w3c.dom.*;

/**
 * Objeto que administra un elemento de tipo applet .
 * <p>
 * Object that administers an applet object
 * @author  Jorge Alberto Jim�nez
 */

public class AppletFE extends WBContainerFE
{
    private static Logger log = SWBUtils.getLogger(AppletFE.class);
    
    private String accesskey=null;
    private String name=null;
    private String code=null;
    private String codebase=null;
    private String archive=null;
    private int width=-1;
    private int height=-1;
    private int border=0;
    private String xmltag=null;
    protected Node tag=null;
    private String onchange=null;
   
    /** Creates a new instance of AppletFE */
    public AppletFE(String name) {
        this.name=name;
    }
    
    /** Creates a new instwance with the default parameters */
    public AppletFE(Node tag){
        this.tag=tag;
        setAttributes();
    }
    
    /**
     *
     * @param accesskey
     */    
    public void setAccessKey(String accesskey){
        this.accesskey=accesskey;
    }
   
    public void setName(String name){
        this.name=name;
    }
    
    public void setCode(String code){
        this.code=code;
    }
    
    public void setCodebase(String codebase){
        this.codebase=codebase;
    }    
    
    public void setArchive(String archive){
        this.archive=archive;
    }
    
    public void setWidth(int width){
        this.width=width;
    }
    
    public void setHeight(int height){
        this.height=height;
    }

    public void setBorder(int border){
        this.border=border;
    }
    
   
    /**determines de xml tag name the form element will be added in a resource.*/
    public void setXmlTag(String xmltag){
        this.xmltag=xmltag;
    }
    
    //gets
    public String getAccessKey(){
        return this.accesskey;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getCode(){
        return this.code;
    }
    
    public String getCodebase(){
        return this.codebase;
    }    
    
    public String getArchive(){
        return this.archive;
    }
    
    public int getWidth(){
        return this.width;
    }
    
    public int getHeight(){
        return this.height;
    }

    public int getBorder(){
        return this.border;
    }

   
     /**determines de xml tag name the form element will be added in a resource.*/
    public String getXmlTag(){
        return this.xmltag;
    }

    public void add(Object obj){
       super.add(obj);
       setAppletFEName(obj);
    }
    
    private void setAppletFEName(Object obj){
       if(obj instanceof ParamAppletFE){
           ParamAppletFE objparamfe=(ParamAppletFE)obj;
           objparamfe.setAppletFEObj(this);
         }
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
                Element child=dom.createElement("applet");
                if(accesskey!=null) child.setAttribute("accesskey",accesskey);
                if(id!=null) child.setAttribute("id",id);
                if(name!=null) {
                    child.setAttribute("name",name);
                    if(id==null) child.setAttribute("id",name);
                }
                if(code!=null) child.setAttribute("code",code);
                if(codebase!=null) child.setAttribute("codebase",codebase);
                if(archive!=null) child.setAttribute("archive",archive);                
                if(width > 0) child.setAttribute("width",String.valueOf(width));
                if(height > 0) child.setAttribute("height",String.valueOf(height));
                child.setAttribute("border",String.valueOf(border));
                if(moreattr!=null) child.setAttribute("moreattr",moreattr);
                
                Document param=SWBUtils.XML.xmlToDom("<applet>"+show()+"</applet>");
                NodeList ndl = param.getElementsByTagName("param");
                for(int i=0;  i < ndl.getLength();i++)
                {
                    child.appendChild(dom.importNode(ndl.item(i), true));
                }
                if(root!=null) root.appendChild(child); 
                else dom.appendChild(child);
                
                xml=SWBUtils.XML.domToXml(dom, "ISO-8859-1", true);
                if(xml!=null && !"".equals(xml.trim())) 
                {
                    if(xml.indexOf("<label")!=-1 && xml.indexOf("<label") < xml.indexOf("<applet")) xml=xml.substring(xml.indexOf("<label"));
                    else xml=xml.substring(xml.indexOf("<applet"));
                }
                else xml="";
            }
        } 
        catch(Exception e) { log.error(e); }
        return xml;
    }
    
    
    /**
     * parsea elemento xml de la definici�n para convertir sus atributos en propiedades
     * y en sub-objetos de esta clase.
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
                        if(attrName.equalsIgnoreCase("id")) id=attrValue;
                        else if(attrName.equalsIgnoreCase("style")) style=attrValue;
                        else if(attrName.equalsIgnoreCase("class")) styleclass=attrValue;
                        else if(attrName.equalsIgnoreCase("moreattr")) moreattr=attrValue;
                        //propios
                        else if(attrName.equalsIgnoreCase("label")) label=attrValue;
                        else if(attrName.equalsIgnoreCase("accesskey")) accesskey=attrValue;
                        else if(attrName.equalsIgnoreCase("name")) name=attrValue;
                        else if(attrName.equalsIgnoreCase("code")) code=attrValue;
                        else if(attrName.equalsIgnoreCase("codebase")) 
                        {
                            codebase=attrValue;
                            if(codebase.toLowerCase().startsWith("{@webpath}"))
                                codebase=SWBPlatform.getContextPath() + codebase.substring(10);
                            continue;                            
                        }
                        else if(attrName.equalsIgnoreCase("archive")) archive=attrValue;
                        else if(attrName.equalsIgnoreCase("width")) width=Integer.parseInt(attrValue);
                        else if(attrName.equalsIgnoreCase("height")) height=Integer.parseInt(attrValue);
                        else if(attrName.equalsIgnoreCase("border")) border=Integer.parseInt(attrValue);
                    }
                }
            }
        }
    }
    
    private Iterator cadenaValues(String cadena){
        ArrayList acoincide=new ArrayList();
        StringTokenizer st = new StringTokenizer(cadena,",");
        while (st.hasMoreElements()) {
            String token = st.nextToken();
            if (token == null) {
                continue;
            }
            acoincide.add(token);
       }
        return acoincide.iterator();
    }
    
}
