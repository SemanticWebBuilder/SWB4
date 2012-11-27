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

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.admin.admresources.lib.WBAdmResourceAbs;
import org.w3c.dom.*;

// TODO: Auto-generated Javadoc
/**
 * Objeto que administra un elemento de tipo calendario en una forma de html.
 * <p>
 * Object that administers an calendar element in a form
 * @author  Jorge Alberto Jim�nez
 */

public class CalendarFE extends WBAdmResourceAbs
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(CalendarFE.class);

    // General attributes
    /** The tag. */
    protected Node   tag;
    
    /** The xmltag. */
    protected String xmltag;
    
    // Attributes for the input tag
    /** The accesskey. */
    protected String accesskey;
    
    /** The value. */
    protected String value;    
    
    /** The isdisabled. */
    protected boolean isdisabled;
    
    /** The isreadonly. */
    protected boolean isreadonly;
    
    /** The size. */
    protected int size;
    
    /** The maxlength. */
    protected int maxlength;
    
    // Attributes for the img tag
    /** The align. */
    protected String align;
    
    /** The alt. */
    protected String alt;    
    
    /** The src. */
    protected String src;    
    
    /** The onclick. */
    protected String onclick;
    
    /** The border. */
    protected int border;
    
    /** The hspace. */
    protected int hspace;
    
    /** The vspace. */
    protected int vspace;
    
    /** The width. */
    protected int width;
    
    /** The height. */
    protected int height;     

    // Attributes for the script tag
    /** The script. */
    protected String script;    
    
    /** The language. */
    protected String language;

    /**
     * Inicializa la clase.
     */    
    public CalendarFE()
    {
        // General attributes
        this.tag=null;
        this.xmltag=null;
        
        // Attributes for the input tag
        this.accesskey=null;
        this.value=null;    
        this.isdisabled = false;
        this.isreadonly = true;
        this.size=11;
        this.maxlength=10;
        
        // Attributes for the img tag
        this.align="absbottom";
        this.alt=null;    
        this.src=SWBPlatform.getContextPath()+"wbadmin/images/show-calendar.gif";
        this.onclick=null;
        this.border=-1;
        this.hspace=-1;
        this.vspace=-1;
        this.width=-1;
        this.height=-1;  
        
        // Attributes for the script tag
        this.script=SWBPlatform.getContextPath()+"wbadmin/js/calendar.js";
        this.language="JavaScript";
    }

    /**
     * Inicializa la clase.
     * 
     * @param tag Nodo del elemento que biene de la definici�n del xml de la admin de recurso
     */    
    public CalendarFE(Node tag)
    {
        // General attributes
        this.tag=tag;
        this.xmltag=null;
        
        // Attributes for the input tag
        this.accesskey=null;
        this.value=null;    
        this.isdisabled = false;
        this.isreadonly = true;
        this.size=11;
        this.maxlength=10;
        
        // Attributes for the img tag
        this.align="absbottom";
        this.alt=null;    
        this.src=SWBPlatform.getContextPath()+"wbadmin/images/show-calendar.gif";
        this.onclick=null;
        this.border=-1;
        this.hspace=-1;
        this.vspace=-1;
        this.width=-1;
        this.height=-1;  
        
        // Attributes for the script tag
        this.script=SWBPlatform.getContextPath()+"wbadmin/js/calendar.js";
        this.language="JavaScript";
        setAttributes();
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
                }
                if(root!=null) dom.appendChild(root);
                Element child=dom.createElement("calendar");
                
                // Attributes for the input tag
                if(id!=null) child.setAttribute("id",id);
                if(name!=null) {
                    child.setAttribute("name",name);
                    if(id==null) child.setAttribute("id",name);
                }
                if(dbconnmgr!=null && dbconnmgr.getAttribute(name)!=null) child.setAttribute("value",dbconnmgr.getAttribute(name));
                else if(value!=null) child.setAttribute("value",value);
                if(accesskey!=null) child.setAttribute("accesskey",accesskey);
                if(isdisabled) child.setAttribute("disabled","true");
                if(isreadonly) child.setAttribute("readonly","true");
                if(size!=-1) child.setAttribute("size",String.valueOf(size));
                if(maxlength!=-1) child.setAttribute("maxlength",String.valueOf(maxlength));
                if(style!=null) child.setAttribute("style",style);
                if(styleclass!=null) child.setAttribute("class",styleclass);                
                
                // Attributes for the img tag
                if(src!=null) child.setAttribute("src",src);
                if(align!=null) child.setAttribute("align",align);
                if(alt!=null) child.setAttribute("alt",alt);
                if(border!=-1) child.setAttribute("border",String.valueOf(border));
                if(hspace!=-1) child.setAttribute("hspace",String.valueOf(hspace));
                if(vspace!=-1) child.setAttribute("vspace",String.valueOf(vspace));
                if(width!=-1) child.setAttribute("width",String.valueOf(width));
                if(height!=-1) child.setAttribute("height",String.valueOf(height));
                if(onclick!=null) child.setAttribute("onclick",onclick);
                else child.setAttribute("onclick", "show_calendar('forms[0]."+name+"');");
                if(moreattr!=null) child.setAttribute("moreattr",moreattr);

                // Attributes for the script tag
                if(script!=null) child.setAttribute("script",script);
                if(language!=null) child.setAttribute("language",language);

                if(root!=null) root.appendChild(child); 
                else dom.appendChild(child);

                xml=SWBUtils.XML.domToXml(dom, "ISO-8859-1", true);
                if(xml!=null && !"".equals(xml.trim())) 
                {
                    if(xml.indexOf("<label")!=-1 && xml.indexOf("<label") < xml.indexOf("<calendar")) xml=xml.substring(xml.indexOf("<label"));
                    else xml=xml.substring(xml.indexOf("<calendar"));
                }
                else xml="";
            }
        } 
        catch(Exception e) { log.error(e); }
        return xml;        
    }

    /**
     * Descompone los atributos del nodo del xml de admin del recurso en propiedades de
     * la clase
     * Set attributes to class according with the xml tag element.
     */    
    public void setAttributes()
    {
        if(tag != null)
        {
            NamedNodeMap nnodemap = tag.getAttributes();
            if(nnodemap.getLength() > 0)
            {
                for(int i = 0; i < nnodemap.getLength(); i++)
                {
                    String attrValue = nnodemap.item(i).getNodeValue();
                    if(attrValue == null || (attrValue!=null && attrValue.trim().equals("")))
                        continue;
                    String attrName = nnodemap.item(i).getNodeName();                    
                    if(attrName.equalsIgnoreCase("label"))
                    {
                        label = attrValue;
                        continue;
                    }                       
                    // Attributes for the input tag
                    if(attrName.equalsIgnoreCase("name"))
                    {
                        name = attrValue;
                        continue;
                    }else if(attrName.equalsIgnoreCase("id")) id=attrValue;
                    else if(attrName.equalsIgnoreCase("value"))
                    {
                        value = attrValue;
                        continue;
                    }
                    else if(attrName.equalsIgnoreCase("accesskey"))
                    {
                        accesskey = attrValue;
                        continue;
                    }
                    else if(attrName.equalsIgnoreCase("disabled"))
                    {
                        isdisabled = Boolean.valueOf(attrValue).booleanValue();
                        continue;
                    }
                    else if(attrName.equalsIgnoreCase("readonly"))
                    {
                        isreadonly = Boolean.valueOf(attrValue).booleanValue();
                        continue;
                    }  
                    else if(attrName.equalsIgnoreCase("size"))
                    {
                        size = Integer.parseInt(attrValue);
                        continue;
                    }
                    else if(attrName.equalsIgnoreCase("maxlength"))
                    {
                        maxlength = Integer.parseInt(attrValue);
                        continue;
                    }
                    else if(attrName.equalsIgnoreCase("style"))
                    {
                        style = attrValue;
                        continue;
                    }
                    else if(attrName.equalsIgnoreCase("class"))
                    {
                        styleclass = attrValue;
                        continue;
                    }                    
                    // Attributes for the img tag                    
                    else if(attrName.equalsIgnoreCase("src"))
                    {
                        src = attrValue;
                        if(src.toLowerCase().startsWith("{@webpath}"))
                            src=SWBPlatform.getContextPath() + src.substring(10);
                        continue;
                    }                    
                    else if(attrName.equalsIgnoreCase("align"))
                    {
                        align = attrValue;
                        continue;
                    }                  
                    else if(attrName.equalsIgnoreCase("alt"))
                    {
                        alt = attrValue;
                        continue;
                    }
                    else if(attrName.equalsIgnoreCase("border"))
                    {
                        border = Integer.parseInt(attrValue);
                        continue;
                    }
                    else if(attrName.equalsIgnoreCase("hspace"))
                    {
                        hspace = Integer.parseInt(attrValue);
                        continue;
                    }
                    else if(attrName.equalsIgnoreCase("vspace"))
                    {
                        vspace = Integer.parseInt(attrValue);
                        continue;
                    }
                    else if(attrName.equalsIgnoreCase("width"))
                    {
                        width = Integer.parseInt(attrValue);
                        continue;
                    }
                    else if(attrName.equalsIgnoreCase("height"))
                    {
                        height = Integer.parseInt(attrValue);
                        continue;
                    }                       
                    else if(attrName.equalsIgnoreCase("onclick"))
                    {
                        onclick = attrValue;
                        continue;
                    } 
                    else if(attrName.equalsIgnoreCase("moreattr"))
                    {
                        moreattr = attrValue;
                        continue;
                    }
                    // Attributes for the script tag
                    else if(attrName.equalsIgnoreCase("script"))
                    {
                        script = attrValue;
                        if(script.toLowerCase().startsWith("{@webpath}"))
                            script=SWBPlatform.getContextPath() + script.substring(10);
                        continue;
                    }                    
                    else if(attrName.equalsIgnoreCase("language"))
                    {
                        language = attrValue;
                        continue;
                    }
                }
            }
        }
    }

    /**
     * Gets the xml tag.
     * 
     * @return the xml tag
     */
    public String getXmlTag() {
        return this.xmltag;
    }

    /**
     * Sets the xml tag.
     * 
     * @param xmltag the new xml tag
     */
    public void setXmlTag(String xmltag) {
        this.xmltag = xmltag;
    }
    
    /**
     * Gets the access key.
     * 
     * @return the access key
     */
    public String getAccessKey() {
        return this.accesskey;
    }

    /**
     * Sets the access key.
     * 
     * @param accesskey the new access key
     */
    private void setAccessKey(String accesskey) {
        this.accesskey = accesskey;
    }    
    
    /**
     * Gets the value.
     * 
     * @return the value
     */
    public String getValue() {
        return this.value;
    }
    
    /**
     * Sets the value.
     * 
     * @param value the new value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the disabled.
     * 
     * @return the disabled
     */
    private boolean getDisabled() {
        return this.isdisabled;
    }

    /**
     * Sets the disabled.
     * 
     * @param isdisabled the new disabled
     */
    private void setDisabled(boolean isdisabled) {
        this.isdisabled = isdisabled;
    }

    /**
     * Gets the readonly.
     * 
     * @return the readonly
     */
    private boolean getReadonly() {
        return this.isreadonly;
    }      

    /**
     * Sets the readonly.
     * 
     * @param isreadonly the new readonly
     */
    private void setReadonly(boolean isreadonly) {
        this.isreadonly= isreadonly;
    }  

    /**
     * Gets the size.
     * 
     * @return the size
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Sets the size.
     * 
     * @param size the new size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Gets the maxlength.
     * 
     * @return the maxlength
     */
    public int getMaxlength() {
        return this.maxlength;
    }

    /**
     * Sets the maxlength.
     * 
     * @param maxlength the new maxlength
     */
    public void setMaxlength(int maxlength) {
        this.maxlength = maxlength;
    }    
    
    /**
     * Gets the align.
     * 
     * @return the align
     */
    public String getAlign() {
        return this.align;
    }

    /**
     * Sets the align.
     * 
     * @param align the new align
     */
    public void setAlign(String align) {
        this.align = align;
    }    

    /**
     * Gets the alt.
     * 
     * @return the alt
     */
    public String getAlt() {
        return this.alt;
    }

    /**
     * Sets the alt.
     * 
     * @param alt the new alt
     */
    public void setAlt(String alt) {
        this.alt = alt;
    }

    /**
     * Gets the src.
     * 
     * @return the src
     */
    public String getSrc() {
        return this.src;
    }
    
    /**
     * Sets the src.
     * 
     * @param src the new src
     */
    public void setSrc(String src) {
        this.src = src;
    }    

    /**
     * Gets the onclick.
     * 
     * @return the onclick
     */
    public String getOnclick() {
        return this.onclick;
    }
    
    /**
     * Sets the onclick.
     * 
     * @param onclick the new onclick
     */
    public void setOnclick(String onclick) {
        this.onclick = onclick;
    } 
 
    /**
     * Gets the border.
     * 
     * @return the border
     */
    public int getBorder() {
        return this.border;
    }

    /**
     * Sets the border.
     * 
     * @param border the new border
     */
    public void setBorder(int border) {
        this.border = border;
    }

    /**
     * Gets the hspace.
     * 
     * @param hspace the hspace
     * @return the hspace
     */
    public int getHspace(int hspace) {
        return this.hspace;
    }

    /**
     * Sets the hspace.
     * 
     * @param hspace the new hspace
     */
    public void setHspace(int hspace) {
        this.hspace = hspace;
    }

    /**
     * Gets the vspace.
     * 
     * @return the vspace
     */
    public int getVspace() {
        return this.vspace;
    }

    /**
     * Sets the vspace.
     * 
     * @param vspace the new vspace
     */
    public void setVspace(int vspace) {
        this.vspace = vspace;
    }

    /**
     * Gets the width.
     * 
     * @return the width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Sets the width.
     * 
     * @param width the new width
     */
    public void setWidth(int width) {
        this.width = width;
    }    
    
    /**
     * Gets the height.
     * 
     * @return the height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Sets the height.
     * 
     * @param height the new height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Gets the script.
     * 
     * @return the script
     */
    public String getScript() {
        return this.script;
    }

    /**
     * Sets the script.
     * 
     * @param script the new script
     */
    public void setScript(String script) {
        this.script = script;
    }   

    /**
     * Gets the language.
     * 
     * @return the language
     */
    public String getLanguage() {
        return this.language;
    }     

    /**
     * Sets the language.
     * 
     * @param language the new language
     */
    public void setLanguage(String language) {
        this.language = language;
    }    
}
