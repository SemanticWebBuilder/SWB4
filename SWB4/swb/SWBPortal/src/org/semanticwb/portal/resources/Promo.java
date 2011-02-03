/**
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
*
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (\u2018open source\u2019),
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

package org.semanticwb.portal.resources;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Templates;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

// TODO: Auto-generated Javadoc
/**
 * Promo se encarga de desplegar y administrar un texto promocional con una
 * imagen opcional bajo ciertos criterios(configuraci?n de recurso). Es un recurso
 * que viene de la versi?n 2 de WebBuilder y puede ser instalado como recurso de
 * estrategia o recurso de contenido.
 *
 * Promo displays and administrate a promocional text with an optional image
 * under criteria like configuration. This resource comes from WebBuilder 2 and can
 * be installed as content resource or a strategy resource.
 *
 * @Autor:Jorge Jiménez
 */

public class Promo extends GenericResource {
    /** The log. */
    private static Logger log = SWBUtils.getLogger(Promo.class);

    /** The tpl. */
    private Templates tpl;

    /** The work path. */
    private String workPath;

    /** The web work path. */
    private String webWorkPath;

    /** The path. */
    private String path = SWBPlatform.getContextPath() +"/swbadmin/xsl/Poll/";

    /** The restype. */
    private static String restype;

    private static final String Action_UPDATE = "update";

    /**
     * Sets the resource base.
     *
     * @param base the new resource base
     */
    @Override
    public void setResourceBase(Resource base) {
        try {
            super.setResourceBase(base);
            workPath    = SWBPortal.getWorkPath()+base.getWorkPath()+"/";
            webWorkPath = SWBPortal.getWebWorkPath()+base.getWorkPath()+"/";
            restype = base.getResourceType().getResourceClassName();
        }catch(Exception e) {
            log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);
        }
        if(!"".equals(base.getAttribute("template","").trim())) {
            try {
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getFileFromWorkPath(base.getWorkPath() +"/"+ base.getAttribute("template").trim()));
                path = webWorkPath;
            }catch(Exception e) {
                log.error("Error while loading resource template: "+base.getId(), e);
            }
        }
        if( tpl==null || Boolean.parseBoolean(base.getAttribute("deftmp"))) {
            try {
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/Promo/PromoRightAligned.xsl"));
            }catch(Exception e) {
                log.error("Error while loading default resource template: "+base.getId(), e);
            }
        }
    }

    /**
     * Gets the dom.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @return the dom
     * @throws SWBResourceException the sWB resource exception
     */
    public Document getDom(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException {
        Resource base=paramRequest.getResourceBase();

        Document  dom = SWBUtils.XML.getNewDocument();
        Element root = dom.createElement("promo");
        root.setAttribute("id", "promo_"+base.getId());
        root.setAttribute("width", base.getAttribute("width",""));
        root.setAttribute("height", base.getAttribute("height",""));
        dom.appendChild(root);

        Element e;
        e = dom.createElement("title");
        e.appendChild(dom.createTextNode( base.getAttribute("title","") ));
        root.appendChild(e);

        e = dom.createElement("subtitle");
        e.appendChild(dom.createTextNode( base.getAttribute("subtitle","") ));
        root.appendChild(e);

        Element img = dom.createElement("image");
        img.setAttribute("width", base.getAttribute("imgWidth","90"));
        img.setAttribute("height", base.getAttribute("imgHeight","140"));
        img.setAttribute("src", webWorkPath+base.getAttribute("imgfile"));
        img.setAttribute("alt", base.getAttribute("caption",base.getAttribute("title","image promo")));
        img.setAttribute("url", base.getAttribute("url",""));
        root.appendChild(img);

        e = dom.createElement("imageFoot");
        e.appendChild(dom.createTextNode( base.getAttribute("caption","") ));
        img.appendChild(e);

        e = dom.createElement("content");
        e.setAttribute("url", base.getAttribute("url",""));
        e.appendChild(dom.createTextNode( base.getAttribute("text","") ));
        root.appendChild(e);

        e = dom.createElement("more");
        e.setAttribute("url", base.getAttribute("url",""));
        e.appendChild(dom.createTextNode( base.getAttribute("more","Ver más") ));
        root.appendChild(e);

        return dom;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doXML(HttpServletRequest, HttpServletResponse, SWBParamRequest)
     */
    @Override
    public void doXML(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/xml; charset=ISO-8859-1");
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Pragma","no-cache");

        Resource base=getResourceBase();
        PrintWriter out = response.getWriter();
        try {
            Document dom = getDom(request, response, paramRequest);
            out.println(SWBUtils.XML.domToXml(dom));
        }catch(Exception e) {
            log.error("Error in doXML method while rendering the XML script: "+base.getId()+"-"+base.getTitle(), e);
            out.println("Error in doXML method while rendering the XML script");
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericAdmResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        Resource base = paramRequest.getResourceBase();

        if(base.getAttribute("template")!=null || Boolean.parseBoolean(base.getAttribute("deftmp"))) {
            PrintWriter out = response.getWriter();
             try {
                Document dom = getDom(request, response, paramRequest);
                String html = SWBUtils.XML.transformDom(tpl, dom);
                out.println(html);
            }
            catch(Exception e) {
                log.error("Error in doView method while rendering the resource base: "+base.getId() +"-"+ base.getTitle(), e);
                e.printStackTrace(System.out);
            }
        }else {
            String out;
            String cssClass = base.getAttribute("cssClass");
            System.out.println("\n\n\n cssClass="+cssClass);
            if(cssClass == null)
                out = renderWithStyle();
            else
                out = render();
            response.getWriter().println(out);
        }

    }

    /**
     * Render with style.
     *
     * @return the string
     */
    private String renderWithStyle() {
        System.out.println(" renderWithStyle..............");
        StringBuilder out = new StringBuilder();
        Resource base=getResourceBase();

        int width;
        try {
            width = Integer.parseInt(base.getAttribute("width","0"));
        }catch(NumberFormatException nfe) {
            width = 0;
        }
        int height;
        try {
            height = Integer.parseInt(base.getAttribute("height","0"));
        }catch(NumberFormatException nfe) {
            height = 0;
        }

        String textcolor = base.getAttribute("textcolor");

        String title = base.getAttribute("title");
        String titleStyle = base.getAttribute("titleStyle");

        String subtitle = base.getAttribute("subtitle");
        String subtitleStyle = base.getAttribute("subtitleStyle");

        String imgfile = base.getAttribute("imgfile");
        String caption = base.getAttribute("caption");
        String captionStyle = base.getAttribute("captionStyle");

        String imgWidth="";
        if(base.getAttribute("imgWidth")!=null){
            imgWidth = "width=\"" + base.getAttribute("imgWidth")+"\"";
        }

        String imgHeight="";
        if(base.getAttribute("imgHeight")!=null){
            imgHeight = "height=\"" + base.getAttribute("imgHeight")+"\"";
        }

        String text = base.getAttribute("text");
        String textStyle = base.getAttribute("textStyle");

        String more = base.getAttribute("more");
        String moreStyle = base.getAttribute("moreStyle");
        String url = base.getAttribute("url");
        String uline = base.getAttribute("uline", "0");

        int imgPos;
        try {
            imgPos = Integer.parseInt(base.getAttribute("imgPos","1"));
        }catch(NumberFormatException nfe) {
            imgPos = 1;
        }

        try {
            //marco
            out.append("<div class=\"swb-promo\"");
            if(textcolor!=null || width>0 || height>0) {
                out.append(" style=\""+(textcolor==null?"":"color:"+textcolor+";")+(width>0?"width:"+width+"px;":"")+(height>0?"height:"+height+"px;":"")+"\"");
            }
            out.append(">");

            //title
            if(title != null) {
                out.append("<h2"+(titleStyle==null?"":" style=\""+titleStyle+"\"")+"><span> \n");
                out.append(title);
                out.append("</span></h2> \n");
            }

            //image
            String margin = "";
            StringBuilder img = new StringBuilder("");
            if(imgfile != null) {
                if(imgPos == 3) {
                    img.append("<div style=\"text-align:center; float:right; margin:10px\"> \n");
                    img.append("<span><img src=\""+webWorkPath+"/"+imgfile+"\" " +imgWidth+ " "+ imgHeight +" /></span> \n");
                    if(caption != null) {
                        img.append("<h6"+(captionStyle==null?"":" style=\""+captionStyle+"\"")+"><span>"+caption+"</span></h6> \n");
                    }
                    img.append("</div> \n");
                    margin = "margin-right:"+(imgWidth+20)+"px;";
                    out.append(img);
                }else if(imgPos == 4) {
                    img.append("<div style=\"text-align:center; float:left; margin:10px\"> \n");
                    img.append("<span><img src=\""+webWorkPath+"/"+imgfile+"\" " +imgWidth+ " "+ imgHeight +" /></span> \n");
                    if(caption != null) {
                        img.append("<h6"+(captionStyle==null?"":" style=\""+captionStyle+"\"")+"><span>"+caption+"</span></h6> \n");
                    }
                    img.append("</div> \n");
                    margin = "margin-left:"+(imgWidth+20)+"px;";
                    out.append(img);
                }else if(imgPos == 2) {
                    img.append("<div style=\"text-align:center; float:right; margin:10px;\"> \n");
                    img.append("<span><img src=\""+webWorkPath+"/"+imgfile+"\" " +imgWidth+ " "+ imgHeight +" /></span> \n");
                    if(caption != null) {
                        img.append("<h6"+(captionStyle==null?"":" style=\""+captionStyle+"\"")+"><span>"+caption+"</span></h6> \n");
                    }
                    img.append("</div> \n");
                    out.append(img);
                }else if(imgPos == 1) {
                    img.append("<div style=\"text-align:center; float:left; margin:10px;\"> \n");
                    img.append("<span><img src=\""+webWorkPath+"/"+imgfile+"\" " +imgWidth+ " "+ imgHeight +" /></span> \n");
                    if(caption != null) {
                        img.append("<h6"+(captionStyle==null?"":" style=\""+captionStyle+"\"")+"><span>"+caption+"</span></h6> \n");
                    }
                    img.append("</div> \n");
                    out.append(img);
                }else if(imgPos == 5) {
                    img.append("<div style=\"text-align:center\"> \n");
                    img.append("<span><img src=\""+webWorkPath+"/"+imgfile+"\" " +imgWidth+ " "+ imgHeight +" /></span> \n");
                    if(caption != null) {
                        img.append("<h6"+(captionStyle==null?"":" style=\""+captionStyle+"\"")+"><span>"+caption+"</span></h6> \n");
                    }
                    img.append("</div> \n");
                    out.append(img);
                }else {
                    imgPos = 6;
                    img.append("<div style=\"text-align:center\"> \n");
                    img.append("<span><img src=\""+webWorkPath+"/"+imgfile+"\" " +imgWidth+ " "+ imgHeight +" /></span> \n");
                    if(caption != null) {
                        img.append("<h6"+(captionStyle==null?"":" style=\""+captionStyle+"\"")+"><span>"+caption+"</span></h6> \n");
                    }
                    img.append("</div> \n");
                }
            }

            //subtitle
            if(subtitle != null) {
                out.append("<h2"+(subtitleStyle==null?"":" style=\""+subtitleStyle+"\"")+"><span>"+subtitle+"</span></h2> \n");
            }

            if( base.getAttribute("more")==null ) {
                //texto
                out.append("<p style=\"text-align:justify;"+margin+"\"> \n");
                if(url != null) {
                    out.append("<a href=\""+url+"\"");
                    if(textStyle!=null || "0".equals(uline)) {
                        out.append(" style=\""+(textStyle==null?"":textStyle+";")+("0".equals(uline)?"text-decoration:none;":"")+"\"");
                    }
                    if ("1".equalsIgnoreCase(base.getAttribute("target", "0").trim())) {
                        out.append(" target=\"_blank\"");
                    }
                    out.append("> \n");
                    out.append(text);
                    out.append("\n</a> \n");
                }else {
                    out.append("<span "+(textStyle==null?"":"style=\""+textStyle+"\"")+"> \n");
                    out.append(text);
                    out.append("</span> \n");
                }
                out.append("</p> \n");
            }else {
                out.append("<p style=\"text-align:justify;"+margin+"\"> \n");
                out.append("<span "+(textStyle==null?"":"style=\""+textStyle+"\"")+"> \n");
                out.append(text);
                out.append("</span> \n");
                out.append("</p> \n");
                //más...
                if( url!=null) {
                    out.append("<ul style=\"list-style:none; margin:7px; padding:0px\"><li> \n");
                    out.append("<a href=\""+url+"\"");
                    if(moreStyle!=null || "0".equals(uline)) {
                        out.append(" style=\""+(moreStyle==null?"":moreStyle+";")+("0".equals(uline)?"text-decoration:none;":"")+"\"");
                    }
                    if ("1".equalsIgnoreCase(base.getAttribute("target", "0").trim())) {
                        out.append(" target=\"_blank\"");
                    }
                    out.append("> \n");
                    out.append(more);
                    out.append("\n</a> \n");
                    out.append("</li></ul> \n");
                }
            }

            if( imgfile!=null && imgPos==6 ) {
                out.append(img);
            }

            //marco
            out.append("</div>");
        }catch (Exception e) {
            log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);
        }
        return out.toString();
    }

    /**
     * Render.
     *
     * @return the string
     */
    private String render() {
        System.out.println("render..............");
        StringBuilder out = new StringBuilder();
        Resource base=getResourceBase();

        String cssClass = base.getAttribute("cssClass","");
        String title = base.getAttribute("title");
        String subtitle = base.getAttribute("subtitle");
        String imgfile = base.getAttribute("imgfile");
        String caption = base.getAttribute("caption");


         String imgWidth="";
        if(base.getAttribute("imgWidth")!=null){
            imgWidth = "width=\"" + base.getAttribute("imgWidth")+"\"";
        }

        String imgHeight="";
        if(base.getAttribute("imgHeight")!=null){
            imgHeight = "height=\"" + base.getAttribute("imgHeight")+"\"";
        }


        String text = base.getAttribute("text");
        String more = base.getAttribute("more");
        String url = base.getAttribute("url");

        int imgPos;
        try {
            imgPos = Integer.parseInt(base.getAttribute("imgPos","1"));
        }catch(NumberFormatException nfe) {
            imgPos = 1;
        }

        try {
            //marco
            out.append("<div class=\""+cssClass+"\"> \n");

            //title
            if(title != null) {
                out.append("<h1><span> \n");
                out.append(title);
                out.append("</span></h1> \n");
            }

            //image
            StringBuilder img = new StringBuilder("");
            if(imgfile != null) {
                if(imgPos != 6) {
                    img.append("<div> \n");
                    img.append("<span><img src=\""+webWorkPath+"/"+imgfile+"\" " +imgWidth+ " "+ imgHeight +" /></span> \n");
                    if(caption != null) {
                        img.append("<h6><span>"+caption+"</span></h6> \n");
                    }
                    img.append("</div> \n");
                    out.append(img);
                }else {
                    imgPos = 6;
                    img.append("<div> \n");
                    img.append("<span><img src=\""+webWorkPath+"/"+imgfile+"\" " +imgWidth+ " "+ imgHeight +" /></span> \n");
                    if(caption != null) {
                        img.append("<h6><span>"+caption+"</span></h6> \n");
                    }
                    img.append("</div> \n");
                }
            }

            //subtitle
            if(subtitle != null) {
                out.append("<h2><span>"+subtitle+"</span></h2> \n");
            }

            if( base.getAttribute("more")==null ) {
                //texto
                out.append("<p> \n");
                if(url != null) {
                    out.append("<a href=\""+url+"\">\n");
                    out.append(text);
                    out.append("\n</a> \n");
                }else {
                    out.append("<span> \n");
                    out.append(text);
                    out.append("</span> \n");
                }
                out.append("</p> \n");
            }else {
                out.append("<p> \n");
                out.append("<span> \n");
                out.append(text);
                out.append("</span> \n");
                out.append("</p> \n");
                //más...
                if( url!=null) {
                    out.append("<ul style=\"list-style:none; margin:7px; padding:0px\"><li> \n");
                    out.append("<a href=\""+url+"\">\n");
                    out.append(more);
                    out.append("\n</a> \n");
                    out.append("</li></ul> \n");
                }
            }

            if( imgfile!=null && imgPos==6 ) {
                out.append(img);
            }

            //marco
            out.append("</div>");
        }catch (Exception e) {
            log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);
        }
        return out.toString();
    }

    /**
     * Obtiene las ligas de redireccionamiento del promocional.
     *
     * @param paramRequest the param request
     * @param base the base
     * @return the url html
     */
    private String getUrlHtml(SWBParamRequest paramRequest, Resource base) {
        StringBuffer ret = new StringBuffer("");
        SWBResourceURL wburl = paramRequest.getActionUrl();

        ret.append("<a href=\"" + wburl.toString() + "\"");
        if("0".equals(base.getAttribute("uline", "0").trim())) {
            ret.append(" style=\"text-decoration:none;\"");
        }
        if ("1".equals(base.getAttribute("target", "0").trim())) {
            ret.append(" target=\"_newprm\"");
        }
        ret.append("> \n");
        return ret.toString();
    }

    /**
     * Obtiene la imagen del promocional asi como su posicionamiento (en caso de
     * existir).
     *
     * @param reqParams the req params
     * @param base the base
     * @return the img promo
     */
    private String getImgPromo(SWBParamRequest reqParams, Resource base) {
        StringBuilder ret = new StringBuilder("");
        String position = base.getAttribute("pos", "3").trim();
        String img=base.getAttribute("img", "").trim();
        String url=base.getAttribute("url", "").trim();

        if("1".equals(position)) {
            ret.append(getImgHtml(reqParams, base));
            if(!img.endsWith(".swf")) {
                ret.append(" align=\"left\" vspace=\"1\" hspace=\"5\" /> \n");
            }
            ret.append(getTextHtml(base));
        }else if("2".equals(position)) {
            ret.append(getTextHtml(base));
            if(!"".equals(url)) {
                ret.append("</a> \n");
            }
            ret.append("</td> \n");
            ret.append("<td> \n");
            if(!"".equals(url)) {
                ret.append(getUrlHtml(reqParams, base));
            }
            ret.append(getImgHtml(reqParams, base));
            if(!img.endsWith(".swf")) {
                ret.append(" align=\"left\" vspace=\"1\" hspace=\"5\" /> \n");
            }
        }else if("3".equals(position)) {
            ret.append("<center> \n");
            ret.append(getImgHtml(reqParams, base));
            if(!img.endsWith(".swf")) {
                ret.append(" align=\"left\" /> \n");
            }
            ret.append("</center> \n");
            if(!"".equals(url)) {
                ret.append("</a> \n");
            }
            ret.append("</td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td> \n");
            if(!"".equals(url)) {
                ret.append(getUrlHtml(reqParams, base));
            }
            ret.append(getTextHtml(base));
        }else if ("4".equals(position)) {
            ret.append(getImgHtml(reqParams, base));
            if(!img.endsWith(".swf")) {
                ret.append(" align=\"right\" vspace=\"1\" hspace=\"10\" /> \n");
            }
            ret.append(getTextHtml(base));
        }else if("5".equals(position)) {
            ret.append(getImgHtml(reqParams, base));
            if(!img.endsWith(".swf")) {
                ret.append(" /> \n");
            }
            if(!"".equals(url)) {
                ret.append("</a> \n");
            }
            ret.append("</td> \n");
            ret.append("<td> \n");
            if(!"".equals(url)) {
                ret.append(getUrlHtml(reqParams, base));
            }
            ret.append(getTextHtml(base));
        }else if("6".equals(position)) {
            ret.append(getTextHtml(base));
            if(!"".equals(url)) {
                ret.append("</a> \n");
            }
            ret.append("</td> \n");
            ret.append("</tr> \n");
            ret.append("<tr> \n");
            ret.append("<td> \n");
            if(!"".equals(url)) {
                ret.append(getUrlHtml(reqParams, base));
            }
            ret.append(getImgHtml(reqParams, base));
            if(!img.endsWith(".swf")) {
                ret.append(" /> \n");
            }
        }else if("7".equals(position)) {
            ret.append(getTextHtml(base));
            ret.append(getImgHtml(reqParams, base));
            if(!img.endsWith(".swf")) {
                ret.append(" align=\"left\" vspace=\"1\" hspace=\"5\" /> \n");
            }
        }else if("8".equals(position)) {
            ret.append(getTextHtml(base));
            ret.append(getImgHtml(reqParams, base));
            if(!img.endsWith(".swf")) {
                ret.append(" align=\"right\" vspace=\"1\" hspace=\"10\" /> \n");
            }
        }
        return ret.toString();
    }

    /**
     * Obtiene el html de la imagen.
     *
     * @param paramRequest the param request
     * @param base the base
     * @return the img html
     */
    private String getImgHtml(SWBParamRequest paramRequest, Resource base) {
        StringBuilder ret = new StringBuilder("");
        String width=base.getAttribute("width", "");
        String height=base.getAttribute("height", "");

        if(base.getAttribute("img", "").trim().endsWith(".swf")) {
            ret.append("<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0\"");
            if(!"".equals(width)) {
                ret.append(" width=\"" + width + "\"");
            }
            if(!"".equals(height)) {
                ret.append(" height=\"" + height + "\"");
            }
            ret.append("> \n");
            ret.append("<param name=movie value=\""+ webWorkPath +"/"+ base.getAttribute("img").trim() +"\"> \n");
            ret.append("<param name=quality value=high> \n");
            ret.append("<embed src=\""+ webWorkPath +"/"+ base.getAttribute("img").trim());
            ret.append("\" quality=high pluginspage=\"http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash\" type=\"application/x-shockwave-flash\"");
            if (!"".equals(width)) ret.append(" width=\"" + width + "\"");
            if (!"".equals(height)) ret.append(" height=\"" + height + "\"");
            ret.append("> \n");
            ret.append("</embed> \n");
            ret.append("</object> \n");
        }else {
            String border = (String)paramRequest.getArguments().get("border");
            ////ret.append("<div>");
            ret.append("<img alt=\"\" src=\""+ webWorkPath +"/"+ base.getAttribute("img").trim() +"\"");
            if(border != null && !"".equals(border.trim())) {
                ret.append(" border=\""+ border +"\"");
            }else {
                ret.append(" border=\"0\"");
            }
            if(!"".equals(width)) {
                ret.append(" width=\""+ width +"\"");
            }
            if(!"".equals(height)) {
                ret.append(" height=\""+ height +"\"");
            }
       }
        return ret.toString();
    }

    /**
     * Obtiene el texto del promocional ya armado.
     *
     * @param base the base
     * @return the text html
     */
    private String getTextHtml(Resource base) {
        StringBuilder ret = new StringBuilder("");
        if(!"".equals(base.getAttribute("text", ""))) {
            if(!"".equals(base.getAttribute("textcolor", ""))) {
                ret.append("<font color=\""+base.getAttribute("textcolor")+"\"> \n");
            }
            ////ret.append("<h2 style=\"text-align:justify\">" + base.getAttribute("text").trim() + "</h2>");
            ret.append(base.getAttribute("text")+"\n");
            if(!"".equals(base.getAttribute("textcolor", ""))) {
                ret.append("</font> \n");
            }
        }
        return ret.toString();
    }

    /**
     * Process action.
     *
     * @param request the request
     * @param response the response
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        Resource base=getResourceBase();
        String action = response.getAction();
        if(response.Action_EDIT.equals(action)) {
            try {
                edit(request, response);
                if( Boolean.parseBoolean(base.getAttribute("wbNoFile_imgfile")) ) {
                    File file = new File(SWBPortal.getWorkPath()+base.getWorkPath()+"/"+base.getAttribute("imgfile"));
                    if(file.exists() && file.delete()) {
                        base.removeAttribute("imgfile");
                        base.removeAttribute("wbNoFile_imgfile");
                    }
                }
                if( Boolean.parseBoolean(base.getAttribute("wbNoTmp_template")) ) {
                    File file = new File(SWBPortal.getWorkPath()+base.getWorkPath()+"/"+base.getAttribute("template"));
                    if(file.exists() && file.delete()) {
                        base.removeAttribute("template");
                        base.removeAttribute("wbNoFile_template");
                    }
                }
                base.updateAttributesToDB();
                response.setAction(Action_UPDATE);
            }catch(Exception e) {
                e.printStackTrace(System.out);
            }
        }
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base=getResourceBase();
        PrintWriter out = response.getWriter();

        String action = null != request.getParameter("act") && !"".equals(request.getParameter("act").trim()) ? request.getParameter("act").trim() : paramRequest.getAction();
        if(paramRequest.Action_ADD.equals(action) || paramRequest.Action_EDIT.equals(action)) {
            out.println(getForm(request, paramRequest));
        }else if(Action_UPDATE.equals(action)) {
            out.println("<script type=\"text/javascript\" language=\"JavaScript\">");
            out.println("   alert('Se actualizó exitosamente el recurso con identificador "+base.getId()+"');");
            out.println("   window.location.href='"+paramRequest.getRenderUrl().setAction("edit")+"';");
            out.println("</script>");
        }
    }

    @Override
    public void doAdminHlp(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base=getResourceBase();
        PrintWriter out = response.getWriter();
        
        String action = null != request.getParameter("act") && !"".equals(request.getParameter("act").trim()) ? request.getParameter("act").trim() : paramRequest.getAction();
        if(paramRequest.Action_ADD.equals(action) || paramRequest.Action_EDIT.equals(action)) {
            out.println(getForm(request, paramRequest));
        }else if(Action_UPDATE.equals(action)) {
            out.println("<script type=\"text/javascript\" language=\"JavaScript\">");
            out.println("   alert('Se actualizó exitosamente el recurso con identificador "+base.getId()+"');");
            out.println("   window.location.href='"+paramRequest.getRenderUrl().setAction("edit")+"';");
            out.println("</script>");
        }
    }

    private String getForm(javax.servlet.http.HttpServletRequest request, SWBParamRequest paramRequest) {
        Resource base=getResourceBase();
        StringBuilder htm = new StringBuilder();
        final String path = SWBPortal.getWebWorkPath()+base.getWorkPath()+"/";

        SWBResourceURL url = paramRequest.getActionUrl().setAction(paramRequest.Action_EDIT);
        htm.append("<script type=\"text/javascript\">\n");
        htm.append("<!--\n");
        htm.append("  dojo.require(\"dijit.layout.ContentPane\");\n");
        htm.append("  dojo.require(\"dijit.form.Form\");\n");
        htm.append("  dojo.require(\"dijit.form.ValidationTextBox\");\n");
        htm.append("  dojo.require(\"dijit.form.RadioButton\");\n");
        htm.append("  dojo.require(\"dijit.form.SimpleTextarea\");\n");
        htm.append("  dojo.require(\"dijit.form.Button\");\n");
        htm.append("-->\n");
        htm.append("</script>\n");
        htm.append("<div class=\"swbform\">\n");
        htm.append("<form id=\"frmPromo\" dojoType=\"dijit.form.Form\" method=\"post\" enctype=\"multipart/form-data\" action=\""+url+"\">\n");
        htm.append("<fieldset>\n");
        htm.append("    <legend>Datos</legend>\n");
//        htm.append("    <input type=\"hidden\" id=\"conname\" name=\"conname\" value=\"true\"/>\n");
//        htm.append("    <input type=\"hidden\" id=\"conname\" name=\"conname\" value=\"true\"/>\n");
        htm.append("    <ul class=\"swbform-ul\">\n");
        htm.append("        <li class=\"swbform-li\">\n");
        htm.append("          <label for=\"title\" class=\"swbform-label\">Título</label>\n");
        htm.append("          <input type=\"text\" id=\"title\" name=\"title\" dojoType=\"dijit.form.ValidationTextBox\" value=\""+base.getAttribute("title","")+"\" maxlength =\"50\"/>\n");
        htm.append("        </li>\n");
        htm.append("        <li class=\"swbform-li\">\n");
        htm.append("          <label for=\"subtitle\" class=\"swbform-label\">Subtítulo</label>\n");
        htm.append("          <input type=\"text\" id=\"subtitle\" name=\"subtitle\" dojoType=\"dijit.form.ValidationTextBox\" value=\""+base.getAttribute("subtitle","")+"\" maxlength=\"60\"/>\n");
        htm.append("        </li>\n");
        htm.append("        <li class=\"swbform-li\">\n");
        htm.append("          <label for=\"text\" class=\"swbform-label\">* Texto</label>\n");
        htm.append("          <textarea id=\"text\" name=\"text\" dojoType=\"dijit.form.SimpleTextarea\" cols=\"50\" rows=\"5\">"+base.getAttribute("text","")+"</textarea>\n");
        htm.append("        </li>\n");
        htm.append("        <li class=\"swbform-li\">\n");
        htm.append("          <label for=\"imgfile\" class=\"swbform-label\">Imagen (gif, jpg, jpeg, png)</label>\n");
        htm.append("          <input type=\"file\" id=\"imgfile\" name=\"imgfile\" />\n");
        htm.append("        </li>\n");
        htm.append("        <li class=\"swbform-li\">\n");
        htm.append("          <label class=\"swbform-label\"></label>\n");
        if(base.getAttribute("imgfile")!=null)
          htm.append("        <img src=\""+path+base.getAttribute("imgfile")+"\" alt=\"\" hspace=\"5\" />\n");
        //htm.append("          <input type=\"hidden\" id=\"wbfile_imgfile\" name=\"wbfile_imgfile\" value=\""+base.getAttribute("","")+"\"/>\n");
        htm.append("        </li>\n");
        if(base.getAttribute("imgfile")!=null) {
            htm.append("    <li class=\"swbform-li\">\n");
            htm.append("      <label for=\"wbNoFile_imgfile\" class=\"swbform-label\">Quitar imagen</label>\n");
            htm.append("      <input type=\"checkbox\" id=\"wbNoFile_imgfile\" name=\"wbNoFile_imgfile\" value=\"true\"/>\n");
            htm.append("    </li>\n");
        }
        htm.append("        <li class=\"swbform-li\">\n");
        htm.append("          <label for=\"caption\" class=\"swbform-label\">Pie de imagen</label>\n");
        htm.append("          <input type=\"text\" id=\"caption\" name=\"caption\" dojoType=\"dijit.form.ValidationTextBox\" value=\""+base.getAttribute("caption","")+"\" maxlength=\"60\"/>\n");
        htm.append("        </li>\n");
        htm.append("        <li class=\"swbform-li\">\n");
        htm.append("            <label for=\"caption\" class=\"swbform-label\">Texto para liga</label>\n");
        htm.append("            <input type=\"text\" id=\"more\" name=\"more\" dojoType=\"dijit.form.ValidationTextBox\" value=\""+base.getAttribute("more","")+"\" maxlength=\"60\"/>\n");
        htm.append("        </li>\n");
        htm.append("        <li class=\"swbform-li\">\n");
        htm.append("            <label for=\"url\" class=\"swbform-label\">Liga</label>\n");
        htm.append("            <input type=\"text\" id=\"url\" name=\"url\" dojoType=\"dijit.form.ValidationTextBox\" value=\""+base.getAttribute("url","")+"\" maxlength=\"60\"/>\n");
        htm.append("        </li>\n");
        htm.append("        <li>Mostrar en una nueva ventana</li>\n");
        htm.append("        <li class=\"swbform-li\">\n");
        htm.append("          <label for=\"target_si\" class=\"swbform-label\">Sí</label>\n");
        htm.append("          <input type=\"radio\" id=\"target_si\" name=\"target\" dojoType=\"dijit.form.RadioButton\" value=\"true\" checked=\"checked\" />\n");
        htm.append("        </li>\n");
        htm.append("        <li class=\"swbform-li\">\n");
        htm.append("          <label for=\"target_no\" class=\"swbform-label\">No</label>\n");
        htm.append("          <input type=\"radio\" id=\"target_no\" name=\"target\" dojoType=\"dijit.form.RadioButton\" value=\"false\"/>\n");
        htm.append("        </li>\n");
        htm.append("    </ul>\n");
        htm.append("</fieldset>\n");

        htm.append("<div title=\"Configuración del estilo\" open=\"true\" dojoType=\"dijit.TitlePane\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">\n");
        htm.append("<fieldset>\n");
        htm.append("    <legend>Estilo</legend>\n");
        htm.append("    <ul class=\"swbform-ul\">\n");
        htm.append("        <li class=\"swbform-li\">\n");
        htm.append("          <label for=\"cssClass\" class=\"swbform-label\">Clase CSS</label>\n");
        htm.append("          <input type=\"text\" id=\"cssClass\" name=\"cssClass\" dojoType=\"dijit.form.ValidationTextBox\" value=\""+base.getAttribute("cssClass","")+"\" maxlength=\"56\"/>\n");
        htm.append("        </li>\n");
        htm.append("        <li class=\"swbform-li\">\n");
        htm.append("          <label for=\"width\" class=\"swbform-label\">Ancho de la viñeta</label>\n");
        htm.append("          <input type=\"text\" id=\"width\" name=\"width\" regExp=\"\\d+\" dojoType=\"dijit.form.ValidationTextBox\" value=\""+base.getAttribute("width","")+"\" maxlength=\"4\" />\n");
        htm.append("        </li>\n");
        htm.append("        <li class=\"swbform-li\">\n");
        htm.append("          <label for=\"height\" class=\"swbform-label\">Alto de la viñeta</label>\n");
        htm.append("          <input type=\"text\" id=\"height\" name=\"height\" regExp=\"\\d+\" dojoType=\"dijit.form.ValidationTextBox\" value=\""+base.getAttribute("height","")+"\" maxlength=\"4\" />\n");
        htm.append("        </li>\n");
        htm.append("        <li class=\"swbform-li\">\n");
        htm.append("          <label for=\"titleStyle\" class=\"swbform-label\">Estilo del título</label>\n");
        htm.append("          <input type=\"text\" id=\"titleStyle\" name=\"titleStyle\" dojoType=\"dijit.form.ValidationTextBox\" value=\""+base.getAttribute("titleStyle","")+"\" maxlength=\"80\"/>\n");
        htm.append("        </li>\n");
        htm.append("        <li class=\"swbform-li\">\n");
        htm.append("          <label for=\"subtitleStyle\" class=\"swbform-label\">Estilo del subtítulo</label>\n");
        htm.append("          <input type=\"text\" id=\"subtitleStyle\" name=\"subtitleStyle\" dojoType=\"dijit.form.ValidationTextBox\" value=\""+base.getAttribute("subtitleStyle","")+"\" maxlength=\"80\"/>\n");
        htm.append("        </li>\n");
        htm.append("        <li class=\"swbform-li\">\n");
        htm.append("          <label for=\"textStyle\" class=\"swbform-label\">Estilo del texto</label>\n");
        htm.append("          <input type=\"text\" id=\"textStyle\" name=\"textStyle\" dojoType=\"dijit.form.ValidationTextBox\" value=\""+base.getAttribute("textStyle","")+"\" maxlength=\"80\"/>\n");
        htm.append("        </li>\n");
        htm.append("        <li class=\"swbform-li\">\n");
        htm.append("          <label for=\"captionStyle\" class=\"swbform-label\">Estilo del pie</label>\n");
        htm.append("          <input type=\"text\" id=\"captionStyle\" name=\"captionStyle\" dojoType=\"dijit.form.ValidationTextBox\" value=\""+base.getAttribute("captionStyle","")+"\" maxlength=\"80\"/>\n");
        htm.append("        </li>\n");
        htm.append("        <li class=\"swbform-li\">\n");
        htm.append("          <label for=\"moreStyle\" class=\"swbform-label\">Estilo de la liga</label>\n");
        htm.append("          <input type=\"text\" id=\"moreStyle\" name=\"moreStyle\" dojoType=\"dijit.form.ValidationTextBox\" value=\""+base.getAttribute("moreStyle","")+"\" maxlength=\"80\"/>\n");
        htm.append("        </li>\n");
        htm.append("        <li>Subrayar el texto de la liga</li>\n");
        htm.append("        <li class=\"swbform-li\">\n");
        htm.append("          <label for=\"uline_si\" class=\"swbform-label\">Sí</label>\n");
        htm.append("          <input type=\"radio\" id=\"uline_si\" name=\"uline\" dojoType=\"dijit.form.RadioButton\" value=\"true\"/>\n");
        htm.append("        </li>\n");
        htm.append("        <li class=\"swbform-li\">\n");
        htm.append("          <label for=\"uline_no\" class=\"swbform-label\">No</label>\n");
        htm.append("          <input type=\"radio\" id=\"uline_no\" name=\"uline\" dojoType=\"dijit.form.RadioButton\" value=\"false\" checked=\"checked\" />\n");
        htm.append("        </li>\n");
        htm.append("        <li class=\"swbform-li\">\n");
        htm.append("          <label for=\"imgWidth\" class=\"swbform-label\">Ancho de la imagen (pixeles)</label>\n");
        htm.append("          <input type=\"text\" id=\"imgWidth\" name=\"imgWidth\" regExp=\"\\d+\" dojoType=\"dijit.form.ValidationTextBox\" value=\""+base.getAttribute("imgWidth","")+"\" maxlength=\"4\" />\n");
        htm.append("        </li>\n");
        htm.append("        <li class=\"swbform-li\">\n");
        htm.append("          <label for=\"imgHeight\" class=\"swbform-label\">Alto de la imagen (pixeles)</label>\n");
        htm.append("          <input type=\"text\" id=\"imgHeight\" name=\"imgHeight\" regExp=\"\\d+\" dojoType=\"dijit.form.ValidationTextBox\" value=\""+base.getAttribute("imgHeight","")+"\" maxlength=\"4\" />\n");
        htm.append("        </li>\n");
        htm.append("        <li class=\"swbform-li\">\n");
        htm.append("          <label for=\"imgPos\" class=\"swbform-label\">Posición de la imagen con respecto al texto</label>\n");
        htm.append("          <input type=\"text\" id=\"imgPos\" name=\"imgPos\" regExp=\"\\d+\" dojoType=\"dijit.form.ValidationTextBox\" value=\""+base.getAttribute("imgPos","2")+"\" maxlength=\"1\" />\n");
        htm.append("        </li>\n");
        htm.append("        <li class=\"swbform-li\">\n");
        htm.append("          <label class=\"swbform-label\">Cambiar la posición de la imagen</label>\n");
        htm.append("          <img src=\"/swbadmin/images/posicion.gif\" alt=\"\" usemap=\"#positionmap\" />\n");
        htm.append("          <map id=\"positionmap\" name=\"positionmap\">\n");
        htm.append("              <area title=\"1\" shape=\"rect\" coords=\"1,1,20,17\" alt=\"1.- Superior izquierda\" onclick=\"dojo.byId('imgPos').value='1'\"/>\n");
        htm.append("              <area title=\"3\" shape=\"rect\" coords=\"24,3,44,18\" alt=\"3.- Derecha\" onclick=\"dojo.byId('imgPos').value='3'\"/>\n");
        htm.append("              <area title=\"5\" shape=\"rect\" coords=\"48,1,68,18\" alt=\"5.- Arriba\" onclick=\"dojo.byId('imgPos').value='5'\"/>\n");
        htm.append("              <area title=\"2\" shape=\"rect\" coords=\"0,22,20,40\" alt=\"2.- Superior derecha\" onclick=\"dojo.byId('imgPos').value='2'\"/>\n");
        htm.append("              <area title=\"4\" shape=\"rect\" coords=\"24,22,44,40\" alt=\"4.- Izquierda\" onclick=\"dojo.byId('imgPos').value='4'\"/>\n");
        htm.append("              <area title=\"6\" shape=\"rect\" coords=\"48,21,68,40\" alt=\"6.- Abajo\" onclick=\"dojo.byId('imgPos').value='6'\"/>\n");
        htm.append("          </map>\n");
        htm.append("        </li>\n");
        htm.append("        <li>\n");
        htm.append("          <table border=\"0\" width=\"100%\">\n");
        htm.append("          <tr>\n");
        htm.append("             <td class=\"datos\" width=\"200px\" align=\"right\">\n");
        htm.append("                Color del texto(hexadecimal)\n");
        htm.append("             </td>\n");
        htm.append("             <td class=\"valores\">\n");
        htm.append("                <input type=\"text\" id=\"textcolor\" name=\"textcolor\" value=\""+base.getAttribute("textcolor","#000000")+"\" maxlength=\"7\" />\n");
        htm.append("                <span style=\"background-color:"+base.getAttribute("textcolor","#000000")+";\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>\n");
        htm.append("             </td>\n");
        htm.append("          </tr>\n");
        htm.append("          </table>\n");
        htm.append("        </li>\n");
        htm.append("        <li>\n");
        htm.append("          <script type=\"text/javascript\">\n");
        htm.append("          <!--\n");
        htm.append("          dojo.require(\"dijit.ColorPalette\");\n");
        htm.append("          dojo.addOnLoad(function(){\n");
        htm.append("            var myPalette = new dijit.ColorPalette( {palette:\"7x10\", onChange: function(val){ dojo.byId('textcolor').value=val; dojo.byId('pselcolor').style.color=val;\n");
        htm.append("            dojo.byId('pselcolor').innerHTML=val;}}, \"cptextcolor\" );\n");
        htm.append("          });\n");
        htm.append("          -->\n");
        htm.append("          </script>\n");
        htm.append("          <table border=\"0\" width=\"100%\">\n");
        htm.append("            <tr>\n");
        htm.append("                <td class=\"datos\" width=\"200\" align=\"right\">\n");
        htm.append("                    Otro color de texto\n");
        htm.append("                    <span id=\"pselcolor\"></span>\n");
        htm.append("                </td>\n");
        htm.append("                <td class=\"valores\">\n");
        htm.append("                    <div id=\"cptextcolor\"></div>\n");
        htm.append("                </td>\n");
        htm.append("             </tr>\n");
        htm.append("          </table>\n");
        htm.append("        </li>\n");
        htm.append("   </ul>\n");
        htm.append("</fieldset>\n");
        htm.append("</div>\n");

        htm.append("<div title=\"Configuración Avanzada\" open=\"false\" dojoType=\"dijit.TitlePane\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">\n");
        htm.append("  <fieldset>\n");
        htm.append("    <legend>Plantilla</legend>\n");
        htm.append("    <ul class=\"swbform-ul\">\n");
        htm.append("      <li class=\"swbform-li\">\n");
        htm.append("        <label for=\"deftmp\" class=\"swbform-label\">Usar plantilla por defecto: <a href=\"#\">PromoRightAligned.xsl</a></label>\n");
        htm.append("        <input type=\"checkbox\" id=\"deftmp\" name=\"deftmp\" value=\"true\" ");
        if(Boolean.parseBoolean(base.getAttribute("deftmp")))
            htm.append(" checked=\"checked\" ");
        htm.append("/>\n");
        htm.append("      </li>\n");
        htm.append("      <li class=\"swbform-li\">\n");
        htm.append("        <label for=\"template\" class=\"swbform-label\">Usar otra plantilla (xsl, xslt)</label>\n");
        htm.append("        <input type=\"file\" id=\"template\" name=\"template\" />\n");
        htm.append("      </li>\n");
        htm.append("      <li class=\"swbform-li\">\n");
        htm.append("        <label class=\"swbform-label\"></label>\n");
        if(base.getAttribute("template")!=null)
          htm.append("      <a href=\"#\">"+base.getAttribute("template")+"</a>\n");
        htm.append("      </li>\n");
        if(base.getAttribute("template")!=null) {
            htm.append("  <li class=\"swbform-li\">\n");
            htm.append("    <label for=\"wbNoTmp_template\" class=\"swbform-label\">Quitar plantilla</label>\n");
            htm.append("    <input type=\"checkbox\" id=\"wbNoTmp_template\" name=\"wbNoTmp_template\" value=\"true\"/>\n");
            htm.append("  </li>\n");
        }
        htm.append("    </ul>\n");
        htm.append("  </fieldset>\n");
        htm.append("</div>\n");

        htm.append("<fieldset>\n");
        htm.append("   <legend></legend>\n");
        htm.append("   <ul class=\"swbform-ul\">\n");
        htm.append("      <li>\n");
        htm.append("         <button type=\"submit\" dojoType=\"dijit.form.Button\">Guardar</button>\n");
        htm.append("         <button type=\"reset\" dojoType=\"dijit.form.Button\">Reestablecer</button>\n");
        htm.append("      </li>\n");
        htm.append("   </ul>\n");
        htm.append("</fieldset>\n");
        htm.append("</form>\n");
        htm.append("</div>\n");
        
        return htm.toString();
    }

    private void edit(HttpServletRequest request, SWBActionResponse response) throws Exception {
        Resource base = getResourceBase();

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            File file = new File(SWBPortal.getWorkPath()+base.getWorkPath()+"/");
            if(!file.exists()) {
                file.mkdirs();
            }
            Iterator<FileItem> iter = SWBUtils.IO.fileUpload(request, null);
            while(iter.hasNext()) {
                FileItem item = iter.next();
                if(item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString().trim();
                    base.setAttribute(name, value);
                }else {
                    String filename = item.getName().replaceAll(" ", "_").trim();
                    if(item.getFieldName().equals("imgfile") && filename.equals("") && base.getAttribute("imgfile")==null)
                        throw new Exception(item.getFieldName()+" es requerido");
                    else if(!filename.equals("")) {
                        file = new File(SWBPortal.getWorkPath()+base.getWorkPath()+"/"+filename);
                        item.write(file);
                        //params.put(item.getFieldName(), filename);
                        base.setAttribute(item.getFieldName(), filename);
                    }
                }
            }
        }
    }
}
