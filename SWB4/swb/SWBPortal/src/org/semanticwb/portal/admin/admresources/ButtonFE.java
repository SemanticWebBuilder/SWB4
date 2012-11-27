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
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.admin.admresources.lib.WBAdmResourceAbs;
import org.w3c.dom.*;


// TODO: Auto-generated Javadoc
/**
 * Objeto que administra un elemento de tipo button en una forma de html.
 * <p>
 * Object that administers an html button element in a form.
 * @author  Jorge Alberto Jim�nez
 */

public class ButtonFE extends WBAdmResourceAbs
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(ButtonFE.class);
    
    /** The accesskey. */
    protected String accesskey;
    
    /** The align. */
    protected String align;
    
    /** The isdisabled. */
    protected boolean isdisabled;
    
    /** The width. */
    protected int width;
    
    /** The height. */
    protected int height;
    
    /** The value. */
    protected String value;
    
    /** The tag. */
    protected Node tag;
    
    /** The on click. */
    protected String onClick;

    /** The prompt message. */
    protected String promptMessage=null;
    
    /** The invalid message. */
    protected String invalidMessage=null;

    /**
     * Inicializa la clase.
     */    
    public ButtonFE()
    {
        accesskey = null;
        align = null;
        isdisabled = false;
        width = -1;
        height = -1;
        value = null;
        tag = null;
        onClick=null;
    }

    /**
     * Inicializa la clase.
     * 
     * @param name Nombre del boton
     * @param value Valor del boton
     */    
    public ButtonFE(String name, String value)
    {
        accesskey = null;
        align = null;
        isdisabled = false;
        width = -1;
        height = -1;
        this.value = null;
        tag = null;
        this.name = name;
        this.value = value;
        this.onClick=null;
    }

    /**
     * Inicializa la clase.
     * 
     * @param tag Nodo de elemento boton que se encontro en la definici�n de xml de la
     * admministraci�n
     */    
    public ButtonFE(Node tag)
    {
        accesskey = null;
        align = null;
        isdisabled = false;
        width = -1;
        height = -1;
        value = null;
        this.tag = null;
        this.tag = tag;
        this.onClick=null;
        setAttributes();
    }

    /**
     * Sets the value.
     * 
     * @param value the new value
     */
    public void setValue(String value)
    {
        this.value = value;
    }

    /**
     * Sets the access key.
     * 
     * @param accesskey the new access key
     */
    private void setAccessKey(String accesskey)
    {
        this.accesskey = accesskey;
    }

    /**
     * Sets the align.
     * 
     * @param align the new align
     */
    public void setAlign(String align)
    {
        this.align = align;
    }

    /**
     * Sets the disabled.
     * 
     * @param isdisabled the new disabled
     */
    private void setDisabled(boolean isdisabled)
    {
        this.isdisabled = isdisabled;
    }

    /**
     * Sets the width.
     * 
     * @param width the new width
     */
    public void setWidth(int width)
    {
        this.width = width;
    }

    /**
     * Sets the height.
     * 
     * @param height the new height
     */
    public void setHeight(int height)
    {
        this.height = height;
    }

    /**
     * Gets the value.
     * 
     * @return the value
     */
    public String getValue()
    {
        return value;
    }

    /**
     * Gets the access key.
     * 
     * @return the access key
     */
    private String getAccessKey()
    {
        return accesskey;
    }

    /**
     * Gets the align.
     * 
     * @return the align
     */
    public String getAlign()
    {
        return align;
    }

    /**
     * Gets the disabled.
     * 
     * @return the disabled
     */
    private boolean getDisabled()
    {
        return isdisabled;
    }

    /**
     * Gets the width.
     * 
     * @return the width
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Gets the height.
     * 
     * @return the height
     */
    public int getHeight()
    {
        return height;
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
                Element child=dom.createElement("input");
                child.setAttribute("type", "button");
                if(id!=null) child.setAttribute("id",id);
                if(name!=null) {
                    child.setAttribute("name",name);
                    if(id==null) child.setAttribute("id",name);
                }
                if(value!=null) child.setAttribute("value",value);
                if(align!=null) child.setAttribute("align",align);
                if(isdisabled) child.setAttribute("disabled","true");
                if(width!=-1) child.setAttribute("width",String.valueOf(width));
                if(height!=-1) child.setAttribute("height",String.valueOf(height));
                if(accesskey!=null) child.setAttribute("accesskey",accesskey);                
                if(style!=null) child.setAttribute("style",style);
                if(styleclass!=null) child.setAttribute("class",styleclass);
                if(onClick != null) child.setAttribute("onclick",onClick);
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
        catch(Exception e) { log.error(e); }
        return xml;         
    }
 
  
    /**
     * Agrega los atributos del elemento boton del xml de la definici�n de la
     * administraci�n a la clase.
     * Set attributes to class according with the xml tag element
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
                    String attrName = nnodemap.item(i).getNodeName();
                    String attrValue = nnodemap.item(i).getNodeValue();
                    if(attrValue == null || attrValue.equals(""))
                        continue;
                    else if(attrName.equalsIgnoreCase("name"))
                    {
                        name = attrValue;
                        continue;
                    }else if(attrName.equalsIgnoreCase("id")) id=attrValue;
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
                    else if(attrName.equalsIgnoreCase("moreattr"))
                    {
                        moreattr = attrValue;
                        continue;
                    }
                    else if(attrName.equalsIgnoreCase("onclick"))
                    {
                        onClick = attrValue;
                        continue;
                    }
                    else if(attrName.equalsIgnoreCase("accesskey"))
                    {
                        accesskey = attrValue;
                        continue;
                    }
                    else if(attrName.equalsIgnoreCase("align"))
                    {
                        align = attrValue;
                        continue;
                    }
                    else if(attrName.equalsIgnoreCase("disabled"))
                    {
                        isdisabled = Boolean.valueOf(attrValue).booleanValue();
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
                    else if(attrName.equalsIgnoreCase("value")) value = attrValue;
                    else if(attrName.equalsIgnoreCase("promptMessage")) promptMessage=attrValue;
                    else if(attrName.equalsIgnoreCase("invalidMessage")) invalidMessage=attrValue;
                }

            }
        }
    }
     
     /**
      * Manejo de Frameworks de JavaScript.
      * 
      * @param child the new js framework attributes
      */
    protected void setJsFrameworkAttributes(Element child){
            String jsFramework=getFormFE().getJsFrameWork();
            if(jsFramework!=null){
                if(jsFramework.equalsIgnoreCase("dojo")){
                    child.setAttribute("dojoType","dijit.form.Button");
                    if(promptMessage!=null){
                        child.setAttribute("promptMessage",promptMessage);
                    }
                    if(invalidMessage!=null){
                        child.setAttribute("invalidMessage",invalidMessage);
                    }
                    if(value!=null){
                        child.appendChild(child.getOwnerDocument().createTextNode(value.trim()));
                    }
                }
            }
    }

}
