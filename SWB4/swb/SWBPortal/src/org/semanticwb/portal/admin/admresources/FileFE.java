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

import org.semanticwb.portal.admin.admresources.lib.WBJsInputFEAbs;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.admin.admresources.util.WBAdmResourceUtils;
import org.w3c.dom.*;

// TODO: Auto-generated Javadoc
/**
 * Objeto que administra un elemento de tipo File en una forma de html.
 * <p>
 * Object that administers an html File element in a form
 * @author  Jorge Alberto Jim�nez
 */

public class FileFE extends WBJsInputFEAbs
{   
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(CheckBoxFE.class);
    
    /** The accept. */
    private String accept=null;
    
    /** The accesskey. */
    private String accesskey=null;
    
    /** The align. */
    private String align=null;
    
    /** The isdisabled. */
    private boolean isdisabled=false;
    
    /** The size. */
    private int size=-1;
    
    /** The maxlength. */
    private int maxlength=-1;
    
    /** The isreadonly. */
    private boolean isreadonly=false;
    
    /** The value. */
    private String value=null;
    
    /** The xmltag. */
    private String xmltag=null;
    
    /** The showfile. */
    private boolean showfile=false;
    
    /** The msgfile. */
    private String msgfile=null;
    
    /** The width. */
    private int width=0;
    
    /** The height. */
    private int height=0;
    
    /** The tag. */
    private Node tag=null;
    
    /** The isremovefile. */
    private boolean isremovefile=false;
    
    /** The remove msg. */
    private String removeMsg=null;
    
    /** The replace. */
    private boolean replace=false;
    
    /** The filetype. */
    private String filetype=null;
    
    /** The bydefault. */
    private String bydefault=null;
    
    /** The msgbydefault. */
    private String msgbydefault=null;
    
    private String showMsg="Ver...";
    private String editMsg="Editar...";
    
    /** The adm res utils. */
    WBAdmResourceUtils admResUtils=new WBAdmResourceUtils();
    
    
    /**
     * Creates a new instance of FileFE.
     */
    public FileFE() {
    }
    
    /**
     * Creates a new instance of FileFE with default parameters.
     * 
     * @param name the name
     */
    public FileFE(String name) {
        this.name=name;
    }
    
    /**
     * Creates a new instance of FileFE with default parameters.
     * 
     * @param tag the tag
     */
    public FileFE(Node tag) {
        this.tag=tag;
        setAttributes();
    }
    
    
    /**
     * This attribute specifies the MIME types that the form processing server and script should correctly handle.
     * A browser may use this information to filter out non-conforming files when prompting a user to select files to upload.
     * values: Comma-separated list of MIME types.
     * 
     * @param accept the new accept
     */ 
    public void setAccept(String accept){
        this.accept=accept;
    }
    
    /**
     * This is a method of giving access/focus to an active HTML element using a keyboard character.
     * This is a common GUI paradigm also known as a "keyboard shortcut" or "keyboard accelerator" A single character is used as the value of this attribute.
     * In addition, a platform-dependent key is usually used in combination with the ACCESSKEY character to access the functionality of the active field.
     * values: A single, case-insensitive alphanumeric character from a browser's character set.
     * 
     * @param accesskey the new accesskey
     */
    public void setAccesskey(String accesskey){
        this.accesskey=accesskey;
    }
    
    /**
     * This attribute specifies the alignment of text following the INPUT reference relative to the field on screen.
     * LEFT and RIGHT specify floating horizontal alignment of the form field in the browser window, and subsequent text will wrap around the form field.
     * values:Left | Right | Top | Texttop | Middle | Absmiddle | Baseline | Bottom | Absbottom
     * 
     * @param align the new a lign
     */
    public void setALign(String align){
        this.align=align;
    }
    
    /**
     * This is a stand-alone attribute which indicates the element is initially non-functional.
     * Disabled form elements should not be submitted to the form processing script.
     * values:NA
     * 
     * @param isdisabled the new disable
     */
    public void setDisable(boolean isdisabled){
        this.isdisabled=isdisabled;
    }
    
    /**
     * Sets the size.
     * 
     * @param size the new size
     */
    private void setSize(int size){
        this.size=size;
    }
    
    /**
     * Sets the max length.
     * 
     * @param maxlength the new max length
     */
    private void setMaxLength(int maxlength){
        this.maxlength=maxlength;
    }
    
    /**
     * This is a stand-alone attribute which tells the browser that content in the field may not be modified by the reader.
     * values:NA
     * 
     * @param isreadonly the new read only
     */
    public void setReadOnly(boolean isreadonly){
        this.isreadonly=isreadonly;
    }
    
    /**
     * Sets the remove file.
     * 
     * @param isremovefile the isremovefile
     * @param removeMsg the remove msg
     */
    public void setRemoveFile(boolean isremovefile,String removeMsg){
        this.isremovefile=isremovefile;
        this.removeMsg=removeMsg;
    }
    
    /**
     * Sets the remove file.
     * 
     * @param isremovefile the isremovefile
     * @param removeMsg the remove msg
     * @param replace the replace
     */
    public void setRemoveFile(boolean isremovefile,String removeMsg,boolean replace){
        this.isremovefile=isremovefile;
        this.removeMsg=removeMsg;
        this.replace=replace;
    }
    
    /**
     * Gets the removes the file.
     * 
     * @return the removes the file
     */
    public boolean getRemoveFile(){
        return isremovefile;
    }
    
    /**
     * Sets the value.
     * 
     * @param value the new value
     */
    public void setValue(String value){
        this.value=value;
    }
    
    /*
    public void setFileType(String filetype){
        this.filetype=filetype;
    }*/
    
     /**
     * determines de xml tag name the form element will be added in a resource.
     * 
     * @param xmltag the new xml tag
     */
    public void setXmlTag(String xmltag){
        this.xmltag=xmltag;
    }
    
    /**
     * Sets the by default.
     * 
     * @param bydefault the new by default
     */
    public void setByDefault(String bydefault){
        this.bydefault=bydefault;
    }   
    
    /**
     * Sets the msg by default.
     * 
     * @param msgbydefault the new msg by default
     */
    public void setMsgByDefault(String msgbydefault){
        this.msgbydefault=msgbydefault;
    }     
    
    /**
     * Show file.
     * 
     * @param showfile the showfile
     * @param msg the msg
     * @param width the width
     * @param height the height
     */
    public void showFile(boolean showfile,String msg,int width,int height){
        this.showfile=showfile;
        this.msgfile=msg;
        this.width=width;
        this.height=height;
    }
    
    //gets
    
    /**
     * Gets the accept.
     * 
     * @return the accept
     */
    public String getAccept(){
        return accept;
    }
    
    /**
     * Gets the accesskey.
     * 
     * @return the accesskey
     */
    public String getAccesskey(){
        return accesskey;
    }
    
    /**
     * Gets the a lign.
     * 
     * @return the a lign
     */
    public String getALign(){
        return align;
    }
    
    /**
     * Gets the disable.
     * 
     * @return the disable
     */
    public boolean getDisable(){
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
     * Gets the size.
     * 
     * @param size the size
     * @return the size
     */
    private int getSize(int size){
        return size;
    }
    
    /**
     * Gets the max length.
     * 
     * @return the max length
     */
    private int getMaxLength(){
        return maxlength;
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
      * Gets the xml tag.
      * 
      * @return the xml tag
      */
     public String getXmlTag(){
        return xmltag;
    }
     /*
    public String getFileType(){
        return filetype;
    }*/
     
     /**
      * Gets the by default.
      * 
      * @return the by default
      */
     public String getByDefault(){
        return this.bydefault;
    }     
     
     /**
      * Gets the msg by default.
      * 
      * @return the msg by default
      */
     public String getMsgByDefault(){
        return this.msgbydefault;
    }      
     
    /**
     * Gets the file.
     * 
     * @return the file
     */
    private Node getFile() {
        if(dbconnmgr!=null && dbconnmgr.getAttribute(name)!=null && !dbconnmgr.getAttribute(name).trim().equals(""))
        {
            try
            {
                Resource base=dbconnmgr.getBase();
                
                //if (base!=null && (!"".equals(base.getAttribute("template", "")) && base.getAttribute("path", "").indexOf(base.getResourceWorkPath()) == -1)) 
                //  throw new Exception("Error while loading resource template: "+base.getId());
                Document dom=SWBUtils.XML.getNewDocument(); 
                String xml="";
                String filename=dbconnmgr.getAttribute(name);
                if(filename.endsWith(".gif") || filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".swf") || filename.endsWith(".png")) {
                    xml=admResUtils.displayImage(base, filename, width, height);
                    dom=SWBUtils.XML.xmlToDom(xml); 
                }else {
                    if(msgfile!=null)
                        xml+=msgfile;
                    else
                        xml+="Archivo: ";
                    if(filename.endsWith(".html") || filename.endsWith(".htm") || filename.endsWith(".xml") || filename.endsWith(".xsl") || filename.endsWith(".xslt") || filename.endsWith(".jsp"))
                        xml+="<a title=\""+editMsg+"\" href=\""+ SWBPlatform.getContextPath()+"/editfile?file="+SWBPortal.getWorkPath()+ base.getWorkPath() + "/" + filename + "&pathType=res&resUri="+base.getEncodedURI()+"\">"+filename+"</a>";
                    else
                        xml+="<a title=\""+editMsg+"\" href=\""+ SWBPortal.getWebWorkPath() + base.getWorkPath() +"/"+ filename +"\">"+filename+"</A>";
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
    
    /**
     * Gets the file options.
     * 
     * @return the file options
     */
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
     * obtains the final xml element to show in the resource admin.
     * 
     * @return the html
     */ 
    @Override
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
                if(input!=null)
                    root.appendChild(input);
                Element child=dom.createElement("input");
                child.setAttribute("type", "file");
                if(id!=null) child.setAttribute("id",id);
                if(name!=null) {
                    child.setAttribute("name",name);
                    if(id==null) child.setAttribute("id",name);
                }
                if(id!=null) child.setAttribute("id",id);
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
                    if(msgbydefault!=null)
                        xml+=msgbydefault;
                    else
                        msgbydefault+="Default: ";
                    if(bydefault.endsWith(".html") || bydefault.endsWith(".htm") || bydefault.endsWith(".xml") || bydefault.endsWith(".xsl") || bydefault.endsWith(".xslt") || bydefault.endsWith(".jsp")) {
                        String defFileName=bydefault.substring(bydefault.lastIndexOf("/")+1);
                        Resource base=dbconnmgr.getBase();
                        xml+="<a title=\""+showMsg+"\" href=\""+ SWBPlatform.getContextPath()+"/showfile?file="+bydefault+"&pathType=def&resUri="+base.getEncodedURI()+"&attr="+getName()+"\">"+defFileName+"</a>";
                    }else {
                        xml+="<a title=\""+showMsg+"\" href=\""+bydefault+"\">"+bydefault.substring(bydefault.lastIndexOf("/")+1)+"</a>";
                    }
                    child=dom.createElement("wbmsg");
                    child.appendChild(dom.createTextNode(xml));
                    root.appendChild(child);
                }
                if(dbconnmgr!=null && dbconnmgr.getAttribute(name)!=null && !dbconnmgr.getAttribute(name).trim().equals("")) 
                {
                       child=dom.createElement("input");
                       child.setAttribute("type", "hidden");
                       child.setAttribute("id", "wbfile_"+name);
                       child.setAttribute("name", "wbfile_"+name);
                       child.setAttribute("value", dbconnmgr.getAttribute(name));
                       root.appendChild(child);
                       if(isremovefile) {
                           input=dom.createElement("label");
                           if(removeMsg!=null)
                               input.appendChild(dom.createTextNode(removeMsg+" "));
                           else
                               input.appendChild(dom.createTextNode("Remove file "));
                           child=dom.createElement("input");
                           child.setAttribute("type", "checkbox");
                           child.setAttribute("name", "wbNoFile_"+name);
                           child.setAttribute("id", "wbNoFile_"+name);
                           child.setAttribute("value", "1");
                           input.appendChild(child);
                           root.appendChild(input);
                       }
                       if(replace) {
                           child=dom.createElement("input");
                           child.setAttribute("type", "hidden");
                           child.setAttribute("id", "wbReplacefile_"+name);
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
     * Set attributes to class according with the xml tag element.
     */
    @Override
    public void setAttributes(){
        boolean required=false;
//        int minsize=0;
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
                        else if(attrName.equalsIgnoreCase("id")) id=attrValue;
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
                                
                        else if(attrName.equalsIgnoreCase("showmsg")) showMsg=attrValue;
                        else if(attrName.equalsIgnoreCase("editmsg")) editMsg=attrValue;
                                
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
