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

import org.semanticwb.portal.admin.admresources.lib.WBJsInputFEAbs;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.w3c.dom.*;

/**
 * Objeto que administra un elemento html de tipo Text.
 * Object that administers an element Text in a html form
 * @author  Jorge Alberto Jim�nez
 */

public class TextFE extends WBJsInputFEAbs
{
    private static Logger log = SWBUtils.getLogger(TextFE.class);
    
    protected String value=null;
    protected String defvalue=null;
    protected int size=-1;
    protected int maxlength=-1;
    protected String accesskey=null;
    protected String align=null;
    protected boolean isdisabled=false;
    protected boolean isreadonly=false;
    protected boolean isautocomplete=true;
    protected int width=-1;
    protected int height=-1;
    protected Node tag=null;

    protected boolean required=false;
    protected String svaltype=null;
    protected String sjspatron=null;
    protected String promptMessage=null;
    protected String invalidMessage=null;
    protected String regExp=null;
    protected boolean trim=false;

    protected String xmltag=null;
    
    
    /** Creates a new instance of Text */
    public TextFE(){
    }
    
    /** Creates a new instwance with the default parameters */
    public TextFE(String name){
        this.name=name;
    }
    
    /** Creates a new instwance with the default parameters */
    public TextFE(Node tag){
        this.tag=tag;
        setAttributes();
    }
    
    // Sets
    
    public void setAccessKey(String accesskey){
        this.accesskey=accesskey;
    }
    
    public void setSize(int size){
        this.size=size;
    }
    
    public void setMaxLength(int maxlength){
        this.maxlength=maxlength;
    }
    
    public void setWidth(int width){
        this.width=width;
    }
    
    public void setHeight(int height){
        this.height=height;
    }
    
    public void setValue(String value){
        this.value=value;
    }
    
    public void setDefValue(String defvalue){
        this.defvalue=defvalue;
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
    
    public void setAutoComplete(boolean isautocomplete){
        this.isautocomplete=isautocomplete;
    }
    
    /**determines de xml tag name the form element will be added in a resource.*/
    public void setXmlTag(String xmltag){
        this.xmltag=xmltag;
    }


    // Gets
    
    public String getAccessKey(){
        return accesskey;
    }
    
    public int getSize(int size){
        return size;
    }
    
    public int getMaxLength(){
        return maxlength;
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public String getValue(){
        return value;
    }
    
    public String getDefValue(){
        return defvalue;
    }
    
    public String getAlign(){
        return align;
    }
    
    private boolean getDisabled(){
        return isdisabled;
    }
    
    public boolean getReadOnly(){
        return isreadonly;
    }
    
    public boolean getAutoComplete(){
        return isautocomplete;
    }
    
    /**determines de xml tag name the form element will be added in a resource.*/
    public String getXmlTag(){
        return xmltag;
    }
    
    /**
     * Obtiene el html(xml) final del elemento para mostrar en la admin del recurso
     * obtains the final xml element to show in the resource admin
     */    
    public String getHtml()
    {
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
                    if(required){
                        root.setAttribute("required", "*");
                    }
                }
                if(root!=null) dom.appendChild(root);
                Element child=dom.createElement("input");
                child.setAttribute("type", "text");
                if(id!=null) child.setAttribute("id",id);
                if(name!=null) {
                    child.setAttribute("name",name);
                    if(id==null) child.setAttribute("id",name);
                }
                if(size!=-1) child.setAttribute("size",String.valueOf(size));
                if(maxlength!=-1) child.setAttribute("maxlength",String.valueOf(maxlength));
                if(width!=-1) child.setAttribute("width",String.valueOf(width));
                if(height!=-1) child.setAttribute("height",String.valueOf(height));
                if(dbconnmgr!=null && dbconnmgr.getAttribute(name)!=null) child.setAttribute("value",dbconnmgr.getAttribute(name));
                else if(value!=null) child.setAttribute("value",value);
                String str=child.getAttribute("value");
                child.setAttribute("value",str.replaceAll("\"","&#34;"));
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
        catch(Exception e) { log.error(e);}        
        return xml;        
    }
    
    
    /**
     * Set attributes to class according with the xml tag element
     */    
    public void setAttributes(){        
        int minsize=0;
        String sjsvalchars=null;
        boolean isshowpatron=true;
        boolean isvalchars=true;
        boolean isshowchars=true;
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
                        else if(attrName.equalsIgnoreCase("id")) id=attrValue;
                        else if(attrName.equalsIgnoreCase("style")) style=attrValue;
                        else if(attrName.equalsIgnoreCase("class")) styleclass=attrValue;
                        else if(attrName.equalsIgnoreCase("moreattr")) moreattr=attrValue;
                        //propios
                        else if(attrName.equalsIgnoreCase("accesskey")) accesskey=attrValue;
                        else if(attrName.equalsIgnoreCase("size")) size=Integer.parseInt(attrValue);
                        else if(attrName.equalsIgnoreCase("maxlength")) maxlength=Integer.parseInt(attrValue);
                        else if(attrName.equalsIgnoreCase("width")) width=Integer.parseInt(attrValue);
                        else if(attrName.equalsIgnoreCase("height")) height=Integer.parseInt(attrValue);
                        else if(attrName.equalsIgnoreCase("value")) value=attrValue;
                        else if(attrName.equalsIgnoreCase("disabled")) isdisabled=Boolean.valueOf(attrValue).booleanValue();
                        else if(attrName.equalsIgnoreCase("readonly")) isreadonly=Boolean.valueOf(attrValue).booleanValue();
                        else if(attrName.equalsIgnoreCase("autocomplete")) isautocomplete=Boolean.valueOf(attrValue).booleanValue();
                        else if(attrName.equalsIgnoreCase("label")) label=attrValue;
                        else if(attrName.equalsIgnoreCase("jsrequired")) required=Boolean.valueOf(attrValue).booleanValue();
                        else if(attrName.equalsIgnoreCase("minsize")) minsize=Integer.parseInt(attrValue);
                        else if(attrName.equalsIgnoreCase("jsvaltype")) svaltype=attrValue;
                        else if(attrName.equalsIgnoreCase("jsvalidchars")) sjsvalchars=attrValue;
                        else if(attrName.equalsIgnoreCase("isvalidchars")) isvalchars=Boolean.valueOf(attrValue).booleanValue();
                        else if(attrName.equalsIgnoreCase("isshowchars")) isshowchars=Boolean.valueOf(attrValue).booleanValue();
                        else if(attrName.equalsIgnoreCase("jspatron")) sjspatron=attrValue;
                        else if(attrName.equalsIgnoreCase("isshowpatron")) isshowpatron=Boolean.valueOf(attrValue).booleanValue();
                        else if(attrName.equalsIgnoreCase("promptMessage")) promptMessage=attrValue;
                        else if(attrName.equalsIgnoreCase("invalidMessage")) invalidMessage=attrValue;
                        else if(attrName.equalsIgnoreCase("regExp")) regExp=attrValue;
                        else if(attrName.equalsIgnoreCase("trim")) trim=Boolean.valueOf(attrValue).booleanValue();
                    }
                }
                if(minsize>0) setJsMinSize(minsize); 
                if(required) setJsIsRequired(true); 
                if(sjsvalchars!=null) setJsValidChars(isvalchars,sjsvalchars, isshowchars);
                if(regExp!=null){
                    if(regExp.equals("\\d+")){ //Expresión regular para números
                        setJsValType("js_numbers");
                    }else if(regExp.equals("\\w+")){ //Expresión regular para alfanuméricos
                        setJsValType("js_alphabetic");
                    }
                }
                if(svaltype!=null) {
                    setJsValType(svaltype);
                    if(svaltype.equals("js_numbers")) regExp="\\d+";
                    else if(svaltype.equals("js_alphabetic")) regExp="\\w+";
                }
                if(sjspatron!=null) {
                    setJsPatron(sjspatron,isshowpatron);
                    regExp=sjspatron;
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
                    if(regExp!=null) child.setAttribute("regExp",regExp);
                }
            }
    }
    
}
