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

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.admin.admresources.lib.WBAdmResourceAbs;
import org.w3c.dom.*;


/**
 * Objeto que administra un elemento de tipo button en una forma de html.
 * <p>
 * Object that administers an html button element in a form.
 * @author  Jorge Alberto Jim�nez
 */

public class ButtonFE extends WBAdmResourceAbs
{
    private static Logger log = SWBUtils.getLogger(ButtonFE.class);
    
    protected String accesskey;
    protected String align;
    protected boolean isdisabled;
    protected int width;
    protected int height;
    protected String value;
    protected Node tag;
    protected String onClick;

    /**
     * Inicializa la clase
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
     * Inicializa la clase
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
     * Inicializa la clase
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

    public void setValue(String value)
    {
        this.value = value;
    }

    private void setAccessKey(String accesskey)
    {
        this.accesskey = accesskey;
    }

    public void setAlign(String align)
    {
        this.align = align;
    }

    private void setDisabled(boolean isdisabled)
    {
        this.isdisabled = isdisabled;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public String getValue()
    {
        return value;
    }

    private String getAccessKey()
    {
        return accesskey;
    }

    public String getAlign()
    {
        return align;
    }

    private boolean getDisabled()
    {
        return isdisabled;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
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
                }
                if(root!=null) dom.appendChild(root);
                Element child=dom.createElement("input");
                child.setAttribute("type", "button");
                if(name!=null) child.setAttribute("name",name);
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
                    if(attrName.equalsIgnoreCase("name"))
                    {
                        name = attrValue;
                        continue;
                    }
                    if(attrName.equalsIgnoreCase("style"))
                    {
                        style = attrValue;
                        continue;
                    }
                    if(attrName.equalsIgnoreCase("class"))
                    {
                        styleclass = attrValue;
                        continue;
                    }
                    if(attrName.equalsIgnoreCase("moreattr"))
                    {
                        moreattr = attrValue;
                        continue;
                    }
                    if(attrName.equalsIgnoreCase("onclick"))
                    {
                        onClick = attrValue;
                        continue;
                    }
                    if(attrName.equalsIgnoreCase("accesskey"))
                    {
                        accesskey = attrValue;
                        continue;
                    }
                    if(attrName.equalsIgnoreCase("align"))
                    {
                        align = attrValue;
                        continue;
                    }
                    if(attrName.equalsIgnoreCase("disabled"))
                    {
                        isdisabled = Boolean.valueOf(attrValue).booleanValue();
                        continue;
                    }
                    if(attrName.equalsIgnoreCase("width"))
                    {
                        width = Integer.parseInt(attrValue);
                        continue;
                    }
                    if(attrName.equalsIgnoreCase("height"))
                    {
                        height = Integer.parseInt(attrValue);
                        continue;
                    }
                    if(attrName.equalsIgnoreCase("value"))
                        value = attrValue;
                }

            }
        }
    }
}