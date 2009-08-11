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

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.admin.admresources.lib.WBAdmResourceAbs;
import org.w3c.dom.*;

/**
 * Objeto que administra un elemento de tipo Image en una forma de html.
 * <p>
 * Object that administers an html image element in a form
 * @author  Jorge Alberto Jim�nez
 */

public class ImageFE extends WBAdmResourceAbs
{
    private static Logger log = SWBUtils.getLogger(ImageFE.class);
    
    protected String accesskey;
    protected String align;
    protected String alt;
    protected int border;
    protected boolean isdisabled;
    protected int hspace;
    protected String src;
    protected String usemap;
    protected String value;
    protected int vspace;
    protected int width;
    protected int height;
    protected String xmltag;
    protected Node tag;
    
    public ImageFE()
    {
        accesskey = null;
        align = null;
        alt = null;
        border = -1;
        isdisabled = false;
        hspace = -1;
        src = null;
        usemap = null;
        value = null;
        vspace = -1;
        width = -1;
        height = -1;
        xmltag = null;
        tag = null;
    }

    public ImageFE(String name, String src)
    {
        accesskey = null;
        align = null;
        alt = null;
        border = -1;
        isdisabled = false;
        hspace = -1;
        this.src = null;
        usemap = null;
        value = null;
        vspace = -1;
        width = -1;
        height = -1;
        xmltag = null;
        tag = null;
        this.name = name;
        this.src = src;
    }

    public ImageFE(Node tag)
    {
        accesskey = null;
        align = null;
        alt = null;
        border = -1;
        isdisabled = false;
        hspace = -1;
        src = null;
        usemap = null;
        value = null;
        vspace = -1;
        width = -1;
        height = -1;
        xmltag = null;
        this.tag = null;
        this.tag = tag;
        setAttributes();
    }

    private void setAccessKey(String accesskey)
    {
        this.accesskey = accesskey;
    }

    public void setAlign(String align)
    {
        this.align = align;
    }

    public void setAlt(String alt)
    {
        this.alt = alt;
    }

    public void setBorder(int border)
    {
        this.border = border;
    }

    private void setDisabled(boolean isdisabled)
    {
        this.isdisabled = isdisabled;
    }

    public void setHspace(int hspace)
    {
        this.hspace = hspace;
    }

    public void setSrc(String src)
    {
        this.src = src;
    }

    public void setUseMap(String usemap)
    {
        this.usemap = usemap;
    }

    public void setVspace(int vspace)
    {
        this.vspace = vspace;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public void setXmlTag(String xmltag)
    {
        this.xmltag = xmltag;
    }

    public String getAccessKey()
    {
        return accesskey;
    }

    public String getAlign()
    {
        return align;
    }

    public String getAlt()
    {
        return alt;
    }

    public int getBorder()
    {
        return border;
    }

    private boolean getDisabled()
    {
        return isdisabled;
    }

    public int getHspace(int hspace)
    {
        return hspace;
    }

    public String getSrc()
    {
        return src;
    }

    public String getUseMap()
    {
        return usemap;
    }

    public int getVspace()
    {
        return vspace;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public String getValue()
    {
        return value;
    }

    public String getXmlTag()
    {
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
                }
                if(root!=null) dom.appendChild(root);
                Element child=dom.createElement("input");
                child.setAttribute("type", "image");
                if(id!=null) child.setAttribute("id",id);
                if(name!=null) {
                    child.setAttribute("name",name);
                    if(id==null) child.setAttribute("id",name);
                }
                if(src!=null) child.setAttribute("src",src);
                if(usemap!=null) child.setAttribute("usemap",usemap);
                if(border!=-1) child.setAttribute("border",String.valueOf(border));
                if(alt!=null) child.setAttribute("alt",alt);
                if(align!=null) child.setAttribute("align",align);
                if(isdisabled) child.setAttribute("disabled","true");
                if(width!=-1) child.setAttribute("width",String.valueOf(width));
                if(height!=-1) child.setAttribute("height",String.valueOf(height));
                if(vspace!=-1) child.setAttribute("vspace",String.valueOf(vspace));
                if(hspace!=-1) child.setAttribute("hspace",String.valueOf(hspace));
                if(accesskey!=null) child.setAttribute("accesskey",accesskey);
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
                    } else if(attrName.equalsIgnoreCase("id")) id=attrValue;
                    else if(attrName.equalsIgnoreCase("label"))
                    {
                        label = attrValue;
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
                    else if(attrName.equalsIgnoreCase("moreattr"))
                    {
                        moreattr = attrValue;
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
                    else if(attrName.equalsIgnoreCase("disabled"))
                    {
                        isdisabled = Boolean.valueOf(attrValue).booleanValue();
                        continue;
                    }
                    else if(attrName.equalsIgnoreCase("hspace"))
                    {
                        hspace = Integer.parseInt(attrValue);
                        continue;
                    }
                    else if(attrName.equalsIgnoreCase("src"))
                    {
                        src = attrValue;
                        if(src.toLowerCase().startsWith("{@webpath}"))
                            src=SWBPlatform.getContextPath() + src.substring(10);
                        continue;
                    }
                    else if(attrName.equalsIgnoreCase("usemap"))
                    {
                        usemap = attrValue;
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
                    else if(attrName.equalsIgnoreCase("value"))
                        value = attrValue;
                }

            }
        }
    }
}
