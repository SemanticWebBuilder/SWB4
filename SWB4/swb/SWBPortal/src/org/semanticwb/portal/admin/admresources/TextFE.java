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
import org.semanticwb.SWBUtils;
import org.w3c.dom.*;

// TODO: Auto-generated Javadoc
/**
 * Objeto que administra un elemento html de tipo Text.
 * Object that administers an element Text in a html form
 * @author  Jorge Alberto Jim�nez
 */

public class TextFE extends WBJsInputFEAbs
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(TextFE.class);
    
    /** The value. */
    protected String value=null;
    
    /** The defvalue. */
    protected String defvalue=null;
    
    /** The size. */
    protected int size=-1;
    
    /** The maxlength. */
    protected int maxlength=-1;
    
    /** The accesskey. */
    protected String accesskey=null;
    
    /** The align. */
    protected String align=null;
    
    /** The isdisabled. */
    protected boolean isdisabled=false;
    
    /** The isreadonly. */
    protected boolean isreadonly=false;
    
    /** The isautocomplete. */
    protected boolean isautocomplete=true;
    
    /** The width. */
    protected int width=-1;
    
    /** The height. */
    protected int height=-1;
    
    /** The tag. */
    protected Node tag=null;

    /** The required. */
    protected boolean required=false;
    
    /** The svaltype. */
    protected String svaltype=null;
    
    /** The sjspatron. */
    protected String sjspatron=null;
    
    /** The prompt message. */
    protected String promptMessage=null;
    
    /** The invalid message. */
    protected String invalidMessage=null;
    
    /** The reg exp. */
    protected String regExp=null;
    
    /** The trim. */
    protected boolean trim=false;

    /** The xmltag. */
    protected String xmltag=null;
    
    
    /**
     * Creates a new instance of Text.
     */
    public TextFE(){
    }
    
    /**
     * Creates a new instwance with the default parameters.
     * 
     * @param name the name
     */
    public TextFE(String name){
        this.name=name;
    }
    
    /**
     * Creates a new instwance with the default parameters.
     * 
     * @param tag the tag
     */
    public TextFE(Node tag){
        this.tag=tag;
        setAttributes();
    }
    
    // Sets
    
    /**
     * Sets the access key.
     * 
     * @param accesskey the new access key
     */
    public void setAccessKey(String accesskey){
        this.accesskey=accesskey;
    }
    
    /**
     * Sets the size.
     * 
     * @param size the new size
     */
    public void setSize(int size){
        this.size=size;
    }
    
    /**
     * Sets the max length.
     * 
     * @param maxlength the new max length
     */
    public void setMaxLength(int maxlength){
        this.maxlength=maxlength;
    }
    
    /**
     * Sets the width.
     * 
     * @param width the new width
     */
    public void setWidth(int width){
        this.width=width;
    }
    
    /**
     * Sets the height.
     * 
     * @param height the new height
     */
    public void setHeight(int height){
        this.height=height;
    }
    
    /**
     * Sets the value.
     * 
     * @param value the new value
     */
    public void setValue(String value){
        this.value=value;
    }
    
    /**
     * Sets the def value.
     * 
     * @param defvalue the new def value
     */
    public void setDefValue(String defvalue){
        this.defvalue=defvalue;
    }
    
    /**
     * Sets the align.
     * 
     * @param align the new align
     */
    public void setAlign(String align){
        this.align=align;
    }
    
    /**
     * Sets the disabled.
     * 
     * @param isdisabled the new disabled
     */
    public void setDisabled(boolean isdisabled){
        this.isdisabled=isdisabled;
    }
    
    /**
     * Sets the read only.
     * 
     * @param isreadonly the new read only
     */
    public void setReadOnly(boolean isreadonly){
        this.isreadonly=isreadonly;
    }
    
    /**
     * Sets the auto complete.
     * 
     * @param isautocomplete the new auto complete
     */
    public void setAutoComplete(boolean isautocomplete){
        this.isautocomplete=isautocomplete;
    }
    
    /**
     * determines de xml tag name the form element will be added in a resource.
     * 
     * @param xmltag the new xml tag
     */
    public void setXmlTag(String xmltag){
        this.xmltag=xmltag;
    }


    // Gets
    
    /**
     * Gets the access key.
     * 
     * @return the access key
     */
    public String getAccessKey(){
        return accesskey;
    }
    
    /**
     * Gets the size.
     * 
     * @param size the size
     * @return the size
     */
    public int getSize(int size){
        return size;
    }
    
    /**
     * Gets the max length.
     * 
     * @return the max length
     */
    public int getMaxLength(){
        return maxlength;
    }
    
    /**
     * Gets the width.
     * 
     * @return the width
     */
    public int getWidth(){
        return width;
    }
    
    /**
     * Gets the height.
     * 
     * @return the height
     */
    public int getHeight(){
        return height;
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
     * Gets the def value.
     * 
     * @return the def value
     */
    public String getDefValue(){
        return defvalue;
    }
    
    /**
     * Gets the align.
     * 
     * @return the align
     */
    public String getAlign(){
        return align;
    }
    
    /**
     * Gets the disabled.
     * 
     * @return the disabled
     */
    private boolean getDisabled(){
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
     * Gets the auto complete.
     * 
     * @return the auto complete
     */
    public boolean getAutoComplete(){
        return isautocomplete;
    }
    
    /**
     * determines de xml tag name the form element will be added in a resource.
     * 
     * @return the xml tag
     */
    public String getXmlTag(){
        return xmltag;
    }
    
    /**
     * Obtiene el html(xml) final del elemento para mostrar en la admin del recurso
     * obtains the final xml element to show in the resource admin.
     * 
     * @return the html
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
                    if( xml.indexOf("<label")!=-1 && xml.indexOf("<label") < xml.indexOf("<input") )
                        xml=xml.substring(xml.indexOf("<label"));
                    else
                        xml=xml.substring(xml.indexOf("<input"));
                }
                else xml="";
            }
        } 
        catch(Exception e) { log.error(e);}        
        return xml;        
    }
    
    
    /**
     * Set attributes to class according with the xml tag element.
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
                if(minsize>0)
                    setJsMinSize(minsize);
                if(required)
                    setJsIsRequired(true);
                if(sjsvalchars!=null)
                    setJsValidChars(isvalchars,sjsvalchars, isshowchars);
                if( regExp!=null ) {
                    if(regExp.equals("\\d+")){ //Expresión regular para números
                        setJsValType("js_numbers");
                    }else if(regExp.equals("\\w+")){ //Expresión regular para alfanuméricos
                        setJsValType("js_alphabetic");
                    }
                }
                if(svaltype!=null) {
                    setJsValType(svaltype);
                    if(svaltype.equals("js_numbers"))
                        regExp="\\d+";
                    else if(svaltype.equals("js_alphabetic"))
                        regExp="\\w+";
                }
                if(sjspatron!=null) {
                    setJsPatron(sjspatron,isshowpatron);
                    regExp=sjspatron;
                }
            }
        }
    }

    /**
     * Manejo de Frameworks de JavaScript.
     * 
     * @param child the new js framework attributes
     */
    private void setJsFrameworkAttributes(Element child) {
        String jsFramework = getFormFE().getJsFrameWork();
        if( jsFramework!=null ) {
            if( jsFramework.equalsIgnoreCase("dojo") ) {
                child.setAttribute("dojoType","dijit.form.ValidationTextBox");
                if(required) {
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
                if(regExp!=null)
                    child.setAttribute("regExp",regExp);
            }
        }
    }
    
}
