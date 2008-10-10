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
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Portlet;
import org.semanticwb.portal.admin.admresources.util.WBAdmResourceUtils;
import org.w3c.dom.*;

/**
 * Objeto que administra un elemento de tipo File en una forma de html.
 * <p>
 * Object that administers an html File element in a form
 * @author  Jorge Alberto Jim�nez
 */

public class FileFE extends WBJsInputFEAbs
{   
    private static Logger log = SWBUtils.getLogger(CheckBoxFE.class);
    
    private String accept=null;
    private String accesskey=null;
    private String align=null;
    private boolean isdisabled=false;
    private int size=-1;
    private int maxlength=-1;
    private boolean isreadonly=false;
    private String value=null;
    private String xmltag=null;
    private boolean showfile=false;
    private String msgfile=null;
    private int width=0;
    private int height=0;
    private Node tag=null;
    private boolean isremovefile=false;
    private String removeMsg=null;
    private boolean replace=false;
    private String filetype=null;
    private String bydefault=null;
    private String msgbydefault=null;
    WBAdmResourceUtils admResUtils=new WBAdmResourceUtils();
    
    
    /** Creates a new instance of FileFE */
    public FileFE() {
    }
    
    /** Creates a new instance of FileFE with default parameters*/
    public FileFE(String name) {
        this.name=name;
    }
    
    /** Creates a new instance of FileFE with default parameters*/
    public FileFE(Node tag) {
        this.tag=tag;
        setAttributes();
    }
    
    
    /** This attribute specifies the MIME types that the form processing server and script should correctly handle. 
     A browser may use this information to filter out non-conforming files when prompting a user to select files to upload. 
     *values: Comma-separated list of MIME types.
     */ 
    public void setAccept(String accept){
        this.accept=accept;
    }
    
    /** This is a method of giving access/focus to an active HTML element using a keyboard character. 
     This is a common GUI paradigm also known as a "keyboard shortcut" or "keyboard accelerator" A single character is used as the value of this attribute. 
     In addition, a platform-dependent key is usually used in combination with the ACCESSKEY character to access the functionality of the active field. 
     *values: A single, case-insensitive alphanumeric character from a browser's character set.
     */
    public void setAccesskey(String accesskey){
        this.accesskey=accesskey;
    }
    
    /**This attribute specifies the alignment of text following the INPUT reference relative to the field on screen. 
     LEFT and RIGHT specify floating horizontal alignment of the form field in the browser window, and subsequent text will wrap around the form field.
     *values:Left | Right | Top | Texttop | Middle | Absmiddle | Baseline | Bottom | Absbottom 
     */
    public void setALign(String align){
        this.align=align;
    }
    
    /**This is a stand-alone attribute which indicates the element is initially non-functional. 
     Disabled form elements should not be submitted to the form processing script. 
     *values:NA
     */
    public void setDisable(boolean isdisabled){
        this.isdisabled=isdisabled;
    }
    
    private void setSize(int size){
        this.size=size;
    }
    
    private void setMaxLength(int maxlength){
        this.maxlength=maxlength;
    }
    
    /**This is a stand-alone attribute which tells the browser that content in the field may not be modified by the reader. 
     *values:NA
     */
    public void setReadOnly(boolean isreadonly){
        this.isreadonly=isreadonly;
    }
    
    public void setRemoveFile(boolean isremovefile,String removeMsg){
        this.isremovefile=isremovefile;
        this.removeMsg=removeMsg;
    }
    
    public void setRemoveFile(boolean isremovefile,String removeMsg,boolean replace){
        this.isremovefile=isremovefile;
        this.removeMsg=removeMsg;
        this.replace=replace;
    }
    
    public boolean getRemoveFile(){
        return isremovefile;
    }
    
    public void setValue(String value){
        this.value=value;
    }
    
    /*
    public void setFileType(String filetype){
        this.filetype=filetype;
    }*/
    
     /**determines de xml tag name the form element will be added in a resource.*/
    public void setXmlTag(String xmltag){
        this.xmltag=xmltag;
    }
    
    public void setByDefault(String bydefault){
        this.bydefault=bydefault;
    }   
    
    public void setMsgByDefault(String msgbydefault){
        this.msgbydefault=msgbydefault;
    }     
    
    public void showFile(boolean showfile,String msg,int width,int height){
        this.showfile=showfile;
        this.msgfile=msg;
        this.width=width;
        this.height=height;
    }
    
    //gets
    
    public String getAccept(){
        return accept;
    }
    
    public String getAccesskey(){
        return accesskey;
    }
    
    public String getALign(){
        return align;
    }
    
    public boolean getDisable(){
        return isdisabled;
    }
    
    public boolean getReadOnly(){
        return isreadonly;
    }
    
    private int getSize(int size){
        return size;
    }
    
    private int getMaxLength(){
        return maxlength;
    }
    
    public String getValue(){
        return value;
    }
    
     public String getXmlTag(){
        return xmltag;
    }
     /*
    public String getFileType(){
        return filetype;
    }*/
     
     public String getByDefault(){
        return this.bydefault;
    }     
     
     public String getMsgByDefault(){
        return this.msgbydefault;
    }      
     
    private Node getFile()
    {
        if(dbconnmgr!=null && dbconnmgr.getAttribute(name)!=null && !dbconnmgr.getAttribute(name).trim().equals(""))
        {
            try
            {
                Portlet base=dbconnmgr.getBase();
                
                //if (base!=null && (!"".equals(base.getAttribute("template", "")) && base.getAttribute("path", "").indexOf(base.getResourceWorkPath()) == -1)) 
                //  throw new Exception("Error while loading resource template: "+base.getId());
                Document dom=SWBUtils.XML.getNewDocument(); 
                String xml="";
                String filename=dbconnmgr.getAttribute(name);
                if(filename.endsWith(".gif") || filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".swf"))
                {
                    xml=admResUtils.displayImage(base, filename, width, height);
                    dom=SWBUtils.XML.xmlToDom(xml); 
                }
                else 
                {
                    if(msgfile!=null) xml+=msgfile;
                    else xml+="Archivo: ";
                    xml+="<A href=\""+ SWBPlatform.getWebWorkPath() + base.getWorkPath() +"/"+ filename +"\">"+filename+"</A>";
                    Element root = dom.createElement("wbmsg");
                    root.appendChild(dom.createTextNode(xml));
                    dom.appendChild(root);
                }
                if(dom!=null) return dom.getFirstChild();
            }
            catch(Exception e) { log.error(e); }
        }
        return null;
    }
    
    private Node getFileOptions()
    {
        if(dbconnmgr!=null && dbconnmgr.getAttribute(name)!=null && !dbconnmgr.getAttribute(name).trim().equals(""))
        {
            try
            {
                Document dom=SWBUtils.XML.getNewDocument(); 
                String xml="";

                if(dom!=null) return dom.getFirstChild();
            }
            catch(Exception e) { log.error(e); }
        }
        return null;
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
                Element root = dom.createElement("file");
                Element input = null;
                if(label!=null) 
                {
                    
                    input=dom.createElement("label");
                    input.appendChild(dom.createTextNode(label.trim()));
                }
                if(input!=null) root.appendChild(input);
                Element child=dom.createElement("input");
                child.setAttribute("type", "file");
                if(name!=null) child.setAttribute("name",name);
                if(size!=-1) child.setAttribute("size",String.valueOf(size));
                if(maxlength!=-1) child.setAttribute("maxlength",String.valueOf(maxlength));
                if(value!=null) child.setAttribute("value",value);
                if(accept!=null) child.setAttribute("accept",accept); 
                if(accesskey!=null) child.setAttribute("accesskey",accesskey);
                if(align!=null) child.setAttribute("align",align);
                if(isdisabled) child.setAttribute("disabled","true");
                if(isreadonly) child.setAttribute("readonly","true");
                if(style!=null) child.setAttribute("style",style);
                if(styleclass!=null) child.setAttribute("class",styleclass);
                if(moreattr!=null) child.setAttribute("moreattr",moreattr);
                if(input!=null) input.appendChild(child); 
                else root.appendChild(child);
                Node node=null;
                if(showfile) 
                {
                    node=getFile();
                    if(node!=null)
                    {
                        node=dom.importNode(node, true);
                        root.appendChild(node);
                    }
                }
                if(bydefault!=null && node==null)
                {
                    if(msgbydefault!=null) xml+=msgbydefault;
                    else msgbydefault+="Default: ";
                    xml+="<A href=\""+bydefault+"\">"+bydefault.substring(bydefault.lastIndexOf("/")+1)+"</A>";
                    child=dom.createElement("wbmsg");
                    child.appendChild(dom.createTextNode(xml));
                    root.appendChild(child);
                }
                if(dbconnmgr!=null && dbconnmgr.getAttribute(name)!=null && !dbconnmgr.getAttribute(name).trim().equals("")) 
                {
                       child=dom.createElement("input");
                       child.setAttribute("type", "hidden");
                       child.setAttribute("name", "wbfile_"+name);
                       child.setAttribute("value", dbconnmgr.getAttribute(name));
                       root.appendChild(child);
                       if(isremovefile) 
                       {
                           input=dom.createElement("label");
                           if(removeMsg!=null) input.appendChild(dom.createTextNode(removeMsg+" "));
                           else input.appendChild(dom.createTextNode("remove file "));
                           child=dom.createElement("input");
                           child.setAttribute("type", "checkbox");
                           child.setAttribute("name", "wbNoFile_"+name);
                           child.setAttribute("value", "1");
                           input.appendChild(child);
                           root.appendChild(input);
                       }
                       if(replace) 
                       {
                           child=dom.createElement("input");
                           child.setAttribute("type", "hidden");
                           child.setAttribute("name", "wbReplacefile_"+name);
                           child.setAttribute("value", dbconnmgr.getAttribute(name));
                           root.appendChild(child);
                       }
                }                
                dom.appendChild(root);
                xml=SWBUtils.XML.domToXml(dom, "ISO-8859-1", true);
                if(xml!=null && !"".equals(xml.trim())) 
                {
                    if(xml.indexOf("<label")!=-1 && xml.indexOf("<label") < xml.indexOf("<input")) xml=xml.substring(xml.indexOf("<label"), xml.indexOf("</file>"));
                    else xml=xml.substring(xml.indexOf("<input"), xml.indexOf("</file>"));
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
        boolean isshowfiletype=true;
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
                        else if(attrName.equalsIgnoreCase("align")) align=attrValue;
                        else if(attrName.equalsIgnoreCase("accept")) accept=attrValue;
                        else if(attrName.equalsIgnoreCase("disabled")) isdisabled=Boolean.valueOf(attrValue).booleanValue();
                        else if(attrName.equalsIgnoreCase("readonly")) isreadonly=Boolean.valueOf(attrValue).booleanValue();
                        else if(attrName.equalsIgnoreCase("size")) size=Integer.parseInt(attrValue);
                        else if(attrName.equalsIgnoreCase("maxlength")) maxlength=Integer.parseInt(attrValue);
                        else if(attrName.equalsIgnoreCase("value")) value=attrValue;
                        else if(attrName.equalsIgnoreCase("showfile")) showfile=Boolean.valueOf(attrValue).booleanValue();
                        else if(attrName.equalsIgnoreCase("msg")) msgfile=attrValue;
                        else if(attrName.equalsIgnoreCase("bydefault")) 
                        {
                            bydefault=attrValue;
                            if(bydefault.toLowerCase().startsWith("{@webpath}"))
                                bydefault=SWBPlatform.getContextPath() + bydefault.substring(10);
                            continue;                            
                        }                        
                        else if(attrName.equalsIgnoreCase("msgbydefault")) msgbydefault=attrValue;
                        else if(attrName.equalsIgnoreCase("width")) width=Integer.parseInt(attrValue);
                        else if(attrName.equalsIgnoreCase("height")) height=Integer.parseInt(attrValue);
                        else if(attrName.equalsIgnoreCase("label")) label=attrValue;
                        else if(attrName.equalsIgnoreCase("jsrequired")) required=Boolean.valueOf(attrValue).booleanValue();
                        else if(attrName.equalsIgnoreCase("minsize")) minsize=Integer.parseInt(attrValue);
                        else if(attrName.equalsIgnoreCase("jsvaltype")) svaltype=attrValue;
                        else if(attrName.equalsIgnoreCase("jsvalidchars")) sjsvalchars=attrValue;
                        else if(attrName.equalsIgnoreCase("isvalidchars")) isvalchars=Boolean.valueOf(attrValue).booleanValue();
                        else if(attrName.equalsIgnoreCase("isshowchars")) isshowchars=Boolean.valueOf(attrValue).booleanValue();
                        else if(attrName.equalsIgnoreCase("jspatron")) sjspatron=attrValue;
                        else if(attrName.equalsIgnoreCase("isshowpatron")) isshowpatron=Boolean.valueOf(attrValue).booleanValue();
                        else if(attrName.equalsIgnoreCase("filetype")) filetype=attrValue;
                        else if(attrName.equalsIgnoreCase("isshowfiletype")) isshowfiletype=Boolean.valueOf(attrValue).booleanValue();
                        else if(attrName.equalsIgnoreCase("isremovefile")) isremovefile=Boolean.valueOf(attrValue).booleanValue();
                        else if(attrName.equalsIgnoreCase("removemsg")) removeMsg=attrValue;
                        else if(attrName.equalsIgnoreCase("replace")) replace=Boolean.valueOf(attrValue).booleanValue();
                    }
                }
                if(minsize>0) setJsMinSize(minsize); 
                if(filetype!=null) setJsFileType(filetype,isshowfiletype);
                if(required) setJsIsRequired(true); 
                if(svaltype!=null) setJsValType(svaltype);
                if(sjsvalchars!=null) setJsValidChars(isvalchars,sjsvalchars, isshowchars);
                if(sjspatron!=null) setJsPatron(sjspatron,isshowpatron);
            }
        }
    }
    
}
