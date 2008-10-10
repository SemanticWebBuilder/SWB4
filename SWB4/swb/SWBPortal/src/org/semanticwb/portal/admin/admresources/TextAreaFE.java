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


import org.semanticwb.portal.admin.admresources.lib.WBJsInputFEAbs;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.w3c.dom.*;

/**
 * Objeto que administra un elemento html de tipo TextArea.
 * <p>
 * Object that administers an element TextArea in a html form
 * @author  Jorge Alberto Jim�nez
 */

public class TextAreaFE extends WBJsInputFEAbs
{
    private static Logger log = SWBUtils.getLogger(TextAreaFE.class);
    
    
    private String accesskey=null;
    private int cols=-1;
    private int rows=-1;
    private boolean isdisabled=false;
    private boolean isreadonly=false;
    private int width=-1;
    private int height=-1;
    private String wrap=null;
    private String value=null;
    private String xmltag=null;
    private Node tag=null;
    
    /** Creates a new instance of TextAreaFE */
    public TextAreaFE() {
    }
    
    /** Creates a new instance of TextAreaFE */
    public TextAreaFE(int cols,int rows) {
        this.cols=cols;
        this.rows=rows;
    }
    
    /** Creates a new instance of TextAreaFE */
    public TextAreaFE(int cols,int rows,String name) {
        this.cols=cols;
        this.rows=rows;
        this.name=name;
    }
    
    /** Creates a new instance of TextAreaFE */
    public TextAreaFE(Node tag) {
        this.tag=tag;
        setAttributes();
    }
    
    
     //sets
    
    public void setAccessKey(String accesskey){
        this.accesskey=accesskey;
    }
    
    public void setCols(int cols){
        this.cols=cols;
    }
    
    public void setRows(int rows){
        this.rows=rows;
    }
    
    public void setDisabled(boolean isdisabled){
        this.isdisabled=isdisabled;
    }
    
    public void setReadOnly(boolean isreadonly){
        this.isreadonly=isreadonly;
    }
     
    public void setWidth(int width){
        this.width=width;
    }
    
    public void setHeight(int height) {
        this.height=height;
    }
     
    public void setWrap(String wrap) {
        this.wrap=wrap;
    } 
    
    public void setValue(String value){
        this.value=value;
    }
    
    /**determines de xml tag name the form element will be added in a resource.*/
    public void setXmlTag(String xmltag){
        this.xmltag=xmltag;
    }
    
     //gets
    
    public String getAccessKey(){
        return accesskey;
    }
    
    public int getCols(){
        return cols;
    }
    
    public int getRows(){
        return rows;
    }
    
    public boolean getDisabled(){
       return isdisabled;
    }
    
    public boolean getReadOnly(){
        return isreadonly;
    }
     
    public int getWidth(){
        return width;
    }
    
    public int getHeight() {
        return height;
    }
     
    public String getWrap() {
        return wrap;
    } 
    
    public String getValue(){
        return value;
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
                Element child=dom.createElement("textarea");
                if(name!=null) child.setAttribute("name",name);
                if(rows > 0) child.setAttribute("rows",String.valueOf(rows));
                if(cols > 0) child.setAttribute("cols",String.valueOf(cols));
                if(wrap!=null) child.setAttribute("wrap",wrap);
                if(width > 0) child.setAttribute("width",String.valueOf(width));
                if(height > 0) child.setAttribute("height",String.valueOf(height));
                if(isdisabled) child.setAttribute("disabled","true");
                if(isreadonly) child.setAttribute("readonly","true");
                if(accesskey!=null) child.setAttribute("accesskey",accesskey);
                if(style!=null) child.setAttribute("style",style);
                if(styleclass!=null) child.setAttribute("class",styleclass);
                if(moreattr!=null) child.setAttribute("moreattr",moreattr);
                if(dbconnmgr!=null && dbconnmgr.getAttribute(name)!=null) child.appendChild(dom.createTextNode(dbconnmgr.getAttribute(name)));
                else if(value!=null) child.appendChild(dom.createTextNode(value.trim()));
                if(root!=null) root.appendChild(child); 
                else dom.appendChild(child);

                xml=SWBUtils.XML.domToXml(dom, "ISO-8859-1", true);
                if(xml!=null && !"".equals(xml.trim())) 
                {
                    if(xml.indexOf("<label")!=-1 && xml.indexOf("<label") < xml.indexOf("<textarea")) xml=xml.substring(xml.indexOf("<label"));
                    else xml=xml.substring(xml.indexOf("<textarea"));
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
        boolean required=false;
        int minsize=0;
        String svaltype=null;
        String sjsvalchars=null;
        boolean isvalchars=true;
        boolean isshowchars=true;
        String sjspatron=null;
        boolean isshowpatron=true;
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
                        else if(attrName.equalsIgnoreCase("accesskey")) accesskey=attrValue;
                        else if(attrName.equalsIgnoreCase("rows")) rows=Integer.parseInt(attrValue);
                        else if(attrName.equalsIgnoreCase("cols")) cols=Integer.parseInt(attrValue);
                        else if(attrName.equalsIgnoreCase("width")) width=Integer.parseInt(attrValue);
                        else if(attrName.equalsIgnoreCase("height")) height=Integer.parseInt(attrValue);
                        else if(attrName.equalsIgnoreCase("value")) value=attrValue;
                        else if(attrName.equalsIgnoreCase("disabled")) isdisabled=Boolean.valueOf(attrValue).booleanValue();
                        else if(attrName.equalsIgnoreCase("readonly")) isreadonly=Boolean.valueOf(attrValue).booleanValue();
                        else if(attrName.equalsIgnoreCase("wrap")) wrap=attrValue;
                        else if(attrName.equalsIgnoreCase("label")) label=attrValue;
                        else if(attrName.equalsIgnoreCase("jsrequired")) required=Boolean.valueOf(attrValue).booleanValue();
                        else if(attrName.equalsIgnoreCase("minsize")) minsize=Integer.parseInt(attrValue);
                        else if(attrName.equalsIgnoreCase("jsvaltype")) svaltype=attrValue;
                        else if(attrName.equalsIgnoreCase("jsvalidchars")) sjsvalchars=attrValue;
                        else if(attrName.equalsIgnoreCase("isvalidchars")) isvalchars=Boolean.valueOf(attrValue).booleanValue();
                        else if(attrName.equalsIgnoreCase("isshowchars")) isshowchars=Boolean.valueOf(attrValue).booleanValue();
                        else if(attrName.equalsIgnoreCase("jspatron")) sjspatron=attrValue;
                        else if(attrName.equalsIgnoreCase("isshowpatron")) isshowpatron=Boolean.valueOf(attrValue).booleanValue();
                    }
                }
                if(minsize>0) setJsMinSize(minsize); 
                if(required) setJsIsRequired(true); 
                if(svaltype!=null) setJsValType(svaltype);
                if(sjsvalchars!=null) setJsValidChars(isvalchars,sjsvalchars, isshowchars);
                if(sjspatron!=null) setJsPatron(sjspatron,isshowpatron);
            }
           if(tag.hasChildNodes() && tag.getFirstChild().getNodeValue()!=null)  value=tag.getFirstChild().getNodeValue();
        }
    }
    
}
