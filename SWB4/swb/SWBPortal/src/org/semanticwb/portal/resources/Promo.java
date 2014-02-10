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
package org.semanticwb.portal.resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Templates;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.semanticwb.Logger;
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
 * @Autor:Jorge Jiménez,Carlos Ramos
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
    private String path = SWBPlatform.getContextPath() +"/swbadmin/xsl/Promo/";

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
//        if(!"".equals(base.getAttribute("template","").trim())) {
//            try {
//                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getFileFromWorkPath(base.getWorkPath() +"/"+ base.getAttribute("template").trim()));
//                path = webWorkPath;
//            }catch(Exception e) {
//                log.error("Error while loading resource template: "+base.getId(), e);
//            }
//        }
//        if( tpl==null || Boolean.parseBoolean(base.getAttribute("deftmp"))) {
            try {
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/Promo/PromoRightAligned.xsl"));
            }catch(Exception e) {
                log.error("Error while loading default resource template: "+base.getId(), e);
            }
//        }
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
        
        if(base.getAttribute("imgfile")==null)
            throw new SWBResourceException(paramRequest.getLocaleString("msgErrResource"));

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
        e.appendChild(dom.createTextNode(base.getAttribute("text","")));
        root.appendChild(e);

        e = dom.createElement("more");
        e.setAttribute("url", base.getAttribute("url",""));
        e.setAttribute("target", base.getAttribute("target","true"));
        e.appendChild(dom.createTextNode(base.getAttribute("more","")));
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
        PrintWriter out = response.getWriter();
        
        if(base.getAttribute("template")!=null || Boolean.parseBoolean(base.getAttribute("deftmp"))) {
            Templates curtpl;
            try {
                curtpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getFileFromWorkPath(base.getWorkPath() +"/"+ base.getAttribute("template").trim()));
                path = webWorkPath;
            }catch(Exception e) {
                curtpl = tpl;
            }
            try {
                Document dom = getDom(request, response, paramRequest);
                String html = SWBUtils.XML.transformDom(curtpl, dom);
                out.println(html);
            }catch(SWBResourceException swbe) {
                out.println(swbe.getMessage());
            }catch(Exception e) {
                log.error("Error in doView method while rendering the resource base: "+base.getId() +"-"+ base.getTitle(), e);
                e.printStackTrace(System.out);
            }
        }else {
            try {
                if(base.getAttribute("cssClass")==null) {
                    out.println(renderWithStyle(paramRequest));
                }else {
                    out.println(render(paramRequest));
                }
            }catch(SWBResourceException swbe) {
                out.println(swbe.getMessage());
            }
        }
    }

    /**
     * Render with style.
     *
     * @return the string
     */
    private String renderWithStyle(SWBParamRequest paramRequest) throws SWBResourceException {
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
        
        if(base.getAttribute("imgfile")==null)
            throw new SWBResourceException(paramRequest.getLocaleString("msgErrResource"));
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

        String text = base.getAttribute("text","");
        String textStyle = base.getAttribute("textStyle");

        String more = base.getAttribute("more","");
        String moreStyle = base.getAttribute("moreStyle");
        String url = base.getAttribute("url");
        String uline = base.getAttribute("uline", "0");
        boolean target = Boolean.parseBoolean(base.getAttribute("target"));

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
            
            boolean hasLink = base.getAttribute("more")!=null && url!=null;

            //image
            String margin = "";
            StringBuilder img = new StringBuilder("");
            if(imgfile != null) {
                if(imgPos == 3) {
                    img.append("<div style=\"text-align:center; float:right; margin:10px\">");
                    img.append("<span>");
                    if(hasLink) {
                        img.append("<a href=\""+url+"\"");
                        if(textStyle!=null || "0".equals(uline)) {
                            img.append(" style=\""+(textStyle==null?"":textStyle+";")+("0".equals(uline)?"text-decoration:none;":"")+"\"");
                        }
                        if(target) {
                            img.append(" onclick=\"window.open('"+paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD)+"');return false;\" target=\"_blank\"");
                        }else {
                            img.append(" onclick=\"window.location.href='"+paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD)+"';return false;\"");
                        }                        
                        img.append(">");
                    }
                    img.append("<img src=\""+webWorkPath+imgfile+"\" " +imgWidth+ " "+ imgHeight +"/>");
                    if(hasLink) {
                        img.append("</a>");
                    }
                    img.append("</span>\n");
                    if(caption != null) {
                        img.append("<h6"+(captionStyle==null?"":" style=\""+captionStyle+"\"")+"><span>"+caption+"</span></h6> \n");
                    }
                    img.append("</div>\n");
                    margin = "margin-right:"+(imgWidth+20)+"px;";
                    out.append(img);
                }else if(imgPos == 4) {
                    img.append("<div style=\"text-align:center; float:left; margin:10px\">");
                    img.append("<span>");
                    if(hasLink) {
                        img.append("<a href=\""+url+"\"");
                        if(textStyle!=null || "0".equals(uline)) {
                            img.append(" style=\""+(textStyle==null?"":textStyle+";")+("0".equals(uline)?"text-decoration:none;":"")+"\"");
                        }
                        if(target) {
                            img.append(" onclick=\"window.open('"+paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD)+"');return false;\" target=\"_blank\"");
                        }else {
                            img.append(" onclick=\"window.location.href='"+paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD)+"';return false;\"");
                        }
                        img.append(">");
                    }
                    img.append("<img src=\""+webWorkPath+"/"+imgfile+"\" " +imgWidth+ " "+ imgHeight +"/>");
                    if(hasLink) {
                        img.append("</a>");
                    }
                    img.append("</span>\n");
                    if(caption != null) {
                        img.append("<h6"+(captionStyle==null?"":" style=\""+captionStyle+"\"")+"><span>"+caption+"</span></h6> \n");
                    }
                    img.append("</div>\n");
                    margin = "margin-left:"+(imgWidth+20)+"px;";
                    out.append(img);
                }else if(imgPos == 2) {
                    img.append("<div style=\"text-align:center; float:right; margin:10px;\">");
                    img.append("<span>");
                    if(hasLink) {
                        img.append("<a href=\""+url+"\"");
                        if(textStyle!=null || "0".equals(uline)) {
                            img.append(" style=\""+(textStyle==null?"":textStyle+";")+("0".equals(uline)?"text-decoration:none;":"")+"\"");
                        }
                        if(target) {
                            img.append(" onclick=\"window.open('"+paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD)+"');return false;\" target=\"_blank\"");
                        }else {
                            img.append(" onclick=\"window.location.href='"+paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD)+"';return false;\"");
                        }
                        img.append(">");
                    }
                    img.append("<img src=\""+webWorkPath+"/"+imgfile+"\" " +imgWidth+ " "+ imgHeight +"/>");
                    if(hasLink) {
                        img.append("</a>");
                    }
                    img.append("</span>\n");
                    if(caption != null) {
                        img.append("<h6"+(captionStyle==null?"":" style=\""+captionStyle+"\"")+"><span>"+caption+"</span></h6> \n");
                    }
                    img.append("</div>\n");
                    out.append(img);
                }else if(imgPos == 1) {
                    img.append("<div style=\"text-align:center; float:left; margin:10px;\">");
                    img.append("<span>");
                    if(hasLink) {
                        img.append("<a href=\""+url+"\"");
                        if(textStyle!=null || "0".equals(uline)) {
                            img.append(" style=\""+(textStyle==null?"":textStyle+";")+("0".equals(uline)?"text-decoration:none;":"")+"\"");
                        }
                        if(target) {
                            img.append(" onclick=\"window.open('"+paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD)+"');return false;\" target=\"_blank\"");
                        }else {
                            img.append(" onclick=\"window.location.href='"+paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD)+"';return false;\"");
                        }
                        img.append(">");
                    }
                    img.append("<img src=\""+webWorkPath+"/"+imgfile+"\" " +imgWidth+ " "+ imgHeight +"/>");
                    if(hasLink) {
                        img.append("</a>");
                    }
                    img.append("</span>\n");
                    if(caption != null) {
                        img.append("<h6"+(captionStyle==null?"":" style=\""+captionStyle+"\"")+"><span>"+caption+"</span></h6> \n");
                    }
                    img.append("</div>\n");
                    out.append(img);
                }else if(imgPos == 5) {
                    img.append("<div style=\"text-align:center\"> \n");
                    img.append("<span>");
                    if(hasLink) {
                        img.append("<a href=\""+url+"\"");
                        if(textStyle!=null || "0".equals(uline)) {
                            img.append(" style=\""+(textStyle==null?"":textStyle+";")+("0".equals(uline)?"text-decoration:none;":"")+"\"");
                        }
                        if(target) {
                            img.append(" onclick=\"window.open('"+paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD)+"');return false;\" target=\"_blank\"");
                        }else {
                            img.append(" onclick=\"window.location.href='"+paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD)+"';return false;\"");
                        }
                        img.append(">");
                    }
                    img.append("<img src=\""+webWorkPath+"/"+imgfile+"\" " +imgWidth+ " "+ imgHeight +"/>");
                    if(hasLink) {
                        img.append("</a>");
                    }
                    img.append("</span>\n");
                    if(caption != null) {
                        img.append("<h6"+(captionStyle==null?"":" style=\""+captionStyle+"\"")+"><span>"+caption+"</span></h6> \n");
                    }
                    img.append("</div> \n");
                    out.append(img);
                }else {
                    imgPos = 6;
                    img.append("<div style=\"text-align:center\">");
                    img.append("<span>");
                    if(hasLink) {
                        img.append("<a href=\""+url+"\"");
                        if(textStyle!=null || "0".equals(uline)) {
                            img.append(" style=\""+(textStyle==null?"":textStyle+";")+("0".equals(uline)?"text-decoration:none;":"")+"\"");
                        }
                        if(target) {
                            img.append(" onclick=\"window.open('"+paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD)+"');return false;\" target=\"_blank\"");
                        }else {
                            img.append(" onclick=\"window.location.href='"+paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD)+"';return false;\"");
                        }
                        img.append(">");
                    }
                    img.append("<img src=\""+webWorkPath+"/"+imgfile+"\" " +imgWidth+ " "+ imgHeight +"/>");
                    if(hasLink) {
                        img.append("</a>");
                    }
                    img.append("</span>\n");
                    if(caption != null) {
                        img.append("<h6"+(captionStyle==null?"":" style=\""+captionStyle+"\"")+"><span>"+caption+"</span></h6> \n");
                    }
                    img.append("</div>\n");
                }
            }

            //subtitle
            if(subtitle != null) {
                out.append("<h2"+(subtitleStyle==null?"":" style=\""+subtitleStyle+"\"")+"><span>"+subtitle+"</span></h2>\n");
            }
            
            if(hasLink) {
                out.append("<p style=\"text-align:justify;"+margin+"\">");
                out.append("<span "+(textStyle==null?"":"style=\""+textStyle+"\"")+">");
                out.append(text);
                out.append("</span>");
                out.append("</p>\n");
                out.append("<ul style=\"list-style:none; margin:7px; padding:0px\"><li>");
                out.append("<a href=\""+url+"\"");
                if(moreStyle!=null || "0".equals(uline)) {
                    out.append(" style=\""+(moreStyle==null?"":moreStyle+";")+("0".equals(uline)?"text-decoration:none;":"")+"\"");
                }
                if(target) {
                    out.append(" onclick=\"window.open('"+paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD)+"');return false;\" target=\"_blank\"");
                }else {
                    out.append(" onclick=\"window.location.href='"+paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD)+"';return false;\"");
                }
                out.append(">");
                out.append(more);
                out.append("</a>");
                out.append("</li></ul>\n");
            }else {
                out.append("<p style=\"text-align:justify;"+margin+"\">");
                if(url != null) {
                    out.append("<a href=\""+url+"\"");
                    if(textStyle!=null || "0".equals(uline)) {
                        out.append(" style=\""+(textStyle==null?"":textStyle+";")+("0".equals(uline)?"text-decoration:none;":"")+"\"");
                    }
                    if(target) {
                        img.append(" onclick=\"window.open('"+paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD)+"');return false;\" target=\"_blank\"");
                    }else {
                        img.append(" onclick=\"window.location.href='"+paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD)+"';return false;\"");
                    }
                    out.append(">");
                    out.append(text);
                    out.append("</a>\n");
                }else {
                    out.append("<span "+(textStyle==null?"":"style=\""+textStyle+"\"")+">");
                    out.append(text);
                    out.append("</span>\n");
                }
                out.append("</p>\n");
            }

            if( imgfile!=null && imgPos==6 ) {
                out.append(img);
            }
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
    private String render(SWBParamRequest paramRequest) throws SWBResourceException {
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

        String title = base.getAttribute("title");

        String subtitle = base.getAttribute("subtitle");

        if(base.getAttribute("imgfile")==null)
            throw new SWBResourceException(paramRequest.getLocaleString("msgErrResource"));
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

        String text = base.getAttribute("text","");

        String more = base.getAttribute("more","");
        String url = base.getAttribute("url");
        String uline = base.getAttribute("uline", "0");
        boolean target = Boolean.parseBoolean(base.getAttribute("target"));
        String cssClass = base.getAttribute("cssClass","");

        int imgPos;
        try {
            imgPos = Integer.parseInt(base.getAttribute("imgPos","1"));
        }catch(NumberFormatException nfe) {
            imgPos = 1;
        }

        try {
            //marco
            out.append("<div class=\""+cssClass+"\">");

            //title
            if(title != null) {
                out.append("<h2><span> \n");
                out.append(title);
                out.append("</span></h2> \n");
            }

            //image
            String margin = "";
            StringBuilder img = new StringBuilder("");
            if(imgfile != null) {
                boolean hasLink = base.getAttribute("more")==null && url!=null;
                if(imgPos == 3) {
                    img.append("<div style=\"text-align:center; float:right; margin:10px\">");
                    img.append("<span>");
                    if(hasLink) {
                        img.append("<a href=\""+url+"\"");
                        if("0".equals(uline))
                            img.append(" style=\"text-decoration:none;\" ");
                        if(target)
                            img.append(" target=\"_blank\"");
                        img.append(">");
                    }
                    img.append("<img src=\""+webWorkPath+"/"+imgfile+"\" " +imgWidth+ " "+ imgHeight +"/>");
                    if(hasLink)
                        img.append("</a>");
                    img.append("</span>\n");
                    if(caption != null)
                        img.append("<h6><span>"+caption+"</span></h6> \n");
                    img.append("</div>\n");
                    margin = "margin-right:"+(imgWidth+20)+"px;";
                    out.append(img);
                }else if(imgPos == 4) {
                    img.append("<div style=\"text-align:center; float:left; margin:10px\">");
                    img.append("<span>");
                    if(hasLink){
                        img.append("<a href=\""+url+"\"");
                        if("0".equals(uline))
                            img.append(" style=\"text-decoration:none;\" ");
                        if(target)
                            img.append(" target=\"_blank\"");
                        img.append(">");
                    }
                    img.append("<img src=\""+webWorkPath+"/"+imgfile+"\" " +imgWidth+ " "+ imgHeight +"/>");
                    if(hasLink)
                        img.append("</a>");
                    img.append("</span>\n");
                    if(caption != null)
                        img.append("<h6><span>"+caption+"</span></h6> \n");
                    img.append("</div>\n");
                    margin = "margin-left:"+(imgWidth+20)+"px;";
                    out.append(img);
                }else if(imgPos == 2) {
                    img.append("<div style=\"text-align:center; float:right; margin:10px;\">");
                    img.append("<span>");
                    if(hasLink) {
                        img.append("<a href=\""+url+"\"");
                        if("0".equals(uline))
                            img.append(" style=\"text-decoration:none;\" ");
                        if(target)
                            img.append(" target=\"_blank\"");
                        img.append(">");
                    }
                    img.append("<img src=\""+webWorkPath+"/"+imgfile+"\" " +imgWidth+ " "+ imgHeight +"/>");
                    if(hasLink)
                        img.append("</a>");
                    img.append("</span>\n");
                    if(caption != null)
                        img.append("<h6><span>"+caption+"</span></h6> \n");
                    img.append("</div>\n");
                    out.append(img);
                }else if(imgPos == 1) {
                    img.append("<div style=\"text-align:center; float:left; margin:10px;\">");
                    img.append("<span>");
                    if(hasLink) {
                        img.append("<a href=\""+url+"\"");
                        if("0".equals(uline))
                            img.append(" style=\"text-decoration:none;\" ");
                        if(target)
                            img.append(" target=\"_blank\"");
                        img.append(">");
                    }
                    img.append("<img src=\""+webWorkPath+"/"+imgfile+"\" " +imgWidth+ " "+ imgHeight +"/>");
                    if(hasLink)
                        img.append("</a>");
                    img.append("</span>\n");
                    if(caption != null)
                        img.append("<h6><span>"+caption+"</span></h6> \n");
                    img.append("</div>\n");
                    out.append(img);
                }else if(imgPos == 5) {
                    img.append("<div style=\"text-align:center\"> \n");
                    img.append("<span>");
                    if(hasLink) {
                        img.append("<a href=\""+url+"\"");
                        if("0".equals(uline))
                            img.append(" style=\"text-decoration:none;\" ");
                        if(target)
                            img.append(" target=\"_blank\"");
                        img.append(">");
                    }
                    img.append("<img src=\""+webWorkPath+"/"+imgfile+"\" " +imgWidth+ " "+ imgHeight +"/>");
                    if(hasLink)
                        img.append("</a>");
                    img.append("</span>\n");
                    if(caption != null)
                        img.append("<h6><span>"+caption+"</span></h6> \n");
                    img.append("</div> \n");
                    out.append(img);
                }else {
                    imgPos = 6;
                    img.append("<div style=\"text-align:center\">");
                    img.append("<span>");
                    if(hasLink) {
                        img.append("<a href=\""+url+"\"");
                        if("0".equals(uline))
                            img.append(" style=\"text-decoration:none;\" ");
                        if(target)
                            img.append(" target=\"_blank\"");
                        img.append(">");
                    }
                    img.append("<img src=\""+webWorkPath+"/"+imgfile+"\" " +imgWidth+ " "+ imgHeight +"/>");
                    if(hasLink)
                        img.append("</a>");
                    img.append("</span>\n");
                    if(caption != null)
                        img.append("<h6><span>"+caption+"</span></h6> \n");
                    img.append("</div>\n");
                }
            }

            //subtitle
            if(subtitle != null) {
                out.append("<h2><span>"+subtitle+"</span></h2>\n");
            }

            if( base.getAttribute("more")==null ) {
                //texto
                out.append("<p>");
                if(url != null) {
                    out.append("<a href=\""+url+"\"");
                    if("0".equals(uline))
                        out.append(" style=\"text-decoration:none;\" ");
                    if(target)
                        out.append(" target=\"_blank\"");
                    out.append(">");
                    out.append(text);
                    out.append("</a>\n");
                }else {
                    out.append("<span>");
                    out.append(text);
                    out.append("</span>\n");
                }
                out.append("</p>\n");
            }else {
                out.append("<p>");
                out.append("<span>");
                out.append(text);
                out.append("</span>");
                out.append("</p>\n");
                //más...
                if(url!=null) {
                    out.append("<ul style=\"list-style:none; margin:7px; padding:0px\"><li>");
                    out.append("<a href=\""+url+"\"");
                    if("0".equals(uline))
                        out.append(" style=\"text-decoration:none;\" ");
                    if(target)
                        out.append(" target=\"_blank\"");
                    out.append(">");
                    out.append(more);
                    out.append("</a>");
                    out.append("</li></ul>\n");
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
//    private String getUrlHtml(SWBParamRequest paramRequest, Resource base) {
//        StringBuffer ret = new StringBuffer("");
//        SWBResourceURL wburl = paramRequest.getActionUrl();
//
//        ret.append("<a href=\"" + wburl.toString() + "\"");
//        if("0".equals(base.getAttribute("uline", "0").trim())) {
//            ret.append(" style=\"text-decoration:none;\"");
//        }
//        if(Boolean.parseBoolean(base.getAttribute("target","true"))) {
//            ret.append(" target=\"_blank\"");
//        }
//        ret.append("> \n");
//        return ret.toString();
//    }

    /**
     * Obtiene la imagen del promocional asi como su posicionamiento (en caso de
     * existir).
     *
     * @param reqParams the req params
     * @param base the base
     * @return the img promo
     */
//    private String getImgPromo(SWBParamRequest reqParams, Resource base) {
//        StringBuilder ret = new StringBuilder("");
//        String position = base.getAttribute("pos", "3").trim();
//        String img=base.getAttribute("img", "").trim();
//        String url=base.getAttribute("url", "").trim();
//
//        if("1".equals(position)) {
//            ret.append(getImgHtml(reqParams, base));
//            if(!img.endsWith(".swf")) {
//                ret.append(" align=\"left\" vspace=\"1\" hspace=\"5\" /> \n");
//            }
//            ret.append(getTextHtml(base));
//        }else if("2".equals(position)) {
//            ret.append(getTextHtml(base));
//            if(!"".equals(url)) {
//                ret.append("</a> \n");
//            }
//            ret.append("</td> \n");
//            ret.append("<td> \n");
//            if(!"".equals(url)) {
//                ret.append(getUrlHtml(reqParams, base));
//            }
//            ret.append(getImgHtml(reqParams, base));
//            if(!img.endsWith(".swf")) {
//                ret.append(" align=\"left\" vspace=\"1\" hspace=\"5\" /> \n");
//            }
//        }else if("3".equals(position)) {
//            ret.append("<center> \n");
//            ret.append(getImgHtml(reqParams, base));
//            if(!img.endsWith(".swf")) {
//                ret.append(" align=\"left\" /> \n");
//            }
//            ret.append("</center> \n");
//            if(!"".equals(url)) {
//                ret.append("</a> \n");
//            }
//            ret.append("</td> \n");
//            ret.append("</tr> \n");
//            ret.append("<tr> \n");
//            ret.append("<td> \n");
//            if(!"".equals(url)) {
//                ret.append(getUrlHtml(reqParams, base));
//            }
//            ret.append(getTextHtml(base));
//        }else if ("4".equals(position)) {
//            ret.append(getImgHtml(reqParams, base));
//            if(!img.endsWith(".swf")) {
//                ret.append(" align=\"right\" vspace=\"1\" hspace=\"10\" /> \n");
//            }
//            ret.append(getTextHtml(base));
//        }else if("5".equals(position)) {
//            ret.append(getImgHtml(reqParams, base));
//            if(!img.endsWith(".swf")) {
//                ret.append(" /> \n");
//            }
//            if(!"".equals(url)) {
//                ret.append("</a> \n");
//            }
//            ret.append("</td> \n");
//            ret.append("<td> \n");
//            if(!"".equals(url)) {
//                ret.append(getUrlHtml(reqParams, base));
//            }
//            ret.append(getTextHtml(base));
//        }else if("6".equals(position)) {
//            ret.append(getTextHtml(base));
//            if(!"".equals(url)) {
//                ret.append("</a> \n");
//            }
//            ret.append("</td> \n");
//            ret.append("</tr> \n");
//            ret.append("<tr> \n");
//            ret.append("<td> \n");
//            if(!"".equals(url)) {
//                ret.append(getUrlHtml(reqParams, base));
//            }
//            ret.append(getImgHtml(reqParams, base));
//            if(!img.endsWith(".swf")) {
//                ret.append(" /> \n");
//            }
//        }else if("7".equals(position)) {
//            ret.append(getTextHtml(base));
//            ret.append(getImgHtml(reqParams, base));
//            if(!img.endsWith(".swf")) {
//                ret.append(" align=\"left\" vspace=\"1\" hspace=\"5\" /> \n");
//            }
//        }else if("8".equals(position)) {
//            ret.append(getTextHtml(base));
//            ret.append(getImgHtml(reqParams, base));
//            if(!img.endsWith(".swf")) {
//                ret.append(" align=\"right\" vspace=\"1\" hspace=\"10\" /> \n");
//            }
//        }
//        return ret.toString();
//    }

    /**
     * Obtiene el html de la imagen.
     *
     * @param paramRequest the param request
     * @param base the base
     * @return the img html
     */
//    private String getImgHtml(SWBParamRequest paramRequest, Resource base) {
//        StringBuilder ret = new StringBuilder("");
//        String width=base.getAttribute("width", "");
//        String height=base.getAttribute("height", "");
//
//        if(base.getAttribute("img", "").trim().endsWith(".swf")) {
//            ret.append("<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0\"");
//            if(!"".equals(width)) {
//                ret.append(" width=\"" + width + "\"");
//            }
//            if(!"".equals(height)) {
//                ret.append(" height=\"" + height + "\"");
//            }
//            ret.append("> \n");
//            ret.append("<param name=movie value=\""+ webWorkPath +"/"+ base.getAttribute("img").trim() +"\"> \n");
//            ret.append("<param name=quality value=high> \n");
//            ret.append("<embed src=\""+ webWorkPath +"/"+ base.getAttribute("img").trim());
//            ret.append("\" quality=high pluginspage=\"http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash\" type=\"application/x-shockwave-flash\"");
//            if (!"".equals(width)) ret.append(" width=\"" + width + "\"");
//            if (!"".equals(height)) ret.append(" height=\"" + height + "\"");
//            ret.append("> \n");
//            ret.append("</embed> \n");
//            ret.append("</object> \n");
//        }else {
//            String border = (String)paramRequest.getArguments().get("border");
//            ////ret.append("<div>");
//            ret.append("<img alt=\"\" src=\""+ webWorkPath +"/"+ base.getAttribute("img").trim() +"\"");
//            if(border != null && !"".equals(border.trim())) {
//                ret.append(" border=\""+ border +"\"");
//            }else {
//                ret.append(" border=\"0\"");
//            }
//            if(!"".equals(width)) {
//                ret.append(" width=\""+ width +"\"");
//            }
//            if(!"".equals(height)) {
//                ret.append(" height=\""+ height +"\"");
//            }
//       }
//        return ret.toString();
//    }

    /**
     * Obtiene el texto del promocional ya armado.
     *
     * @param base the base
     * @return the text html
     */
//    private String getTextHtml(Resource base) {
//        StringBuilder ret = new StringBuilder("");
//        if(!"".equals(base.getAttribute("text", ""))) {
//            if(!"".equals(base.getAttribute("textcolor", ""))) {
//                ret.append("<font color=\""+base.getAttribute("textcolor")+"\"> \n");
//            }
//            ////ret.append("<h2 style=\"text-align:justify\">" + base.getAttribute("text").trim() + "</h2>");
//            ret.append(base.getAttribute("text")+"\n");
//            if(!"".equals(base.getAttribute("textcolor", ""))) {
//                ret.append("</font> \n");
//            }
//        }
//        return ret.toString();
//    }


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

        if(SWBActionResponse.Action_ADD.equals(action)) {
            getResourceBase().addHit(request, response.getUser(), response.getWebPage());
            String url = base.getAttribute("url");
            if( url!=null ) {
                response.sendRedirect(url);
            }
        }else if(SWBActionResponse.Action_EDIT.equals(action)) {
            try {
                edit(request, response);
                if( Boolean.parseBoolean(base.getAttribute("wbNoFile_imgfile")) ) {
                    File file = new File(SWBPortal.getWorkPath()+base.getWorkPath()+"/"+base.getAttribute("imgfile"));
                    if(file.exists() && file.delete()) {
                        base.removeAttribute("imgfile");
                        base.removeAttribute("wbNoFile_imgfile");
                    }
                }
                base.updateAttributesToDB();
                response.setAction(Action_UPDATE);
            }catch(FileNotFoundException fne) {
                log.info(fne.getMessage());
            }catch(Exception e) {
                log.error(e);
            }
        }
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base=getResourceBase();
        PrintWriter out = response.getWriter();

        String action = null != request.getParameter("act") && !"".equals(request.getParameter("act").trim()) ? request.getParameter("act").trim() : paramRequest.getAction();
        if(SWBParamRequest.Action_ADD.equals(action) || SWBParamRequest.Action_EDIT.equals(action)) {
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
        if(SWBParamRequest.Action_ADD.equals(action) || SWBParamRequest.Action_EDIT.equals(action)) {
            out.println(getForm(request, paramRequest));
        }else if(Action_UPDATE.equals(action)) {
            out.println("<script type=\"text/javascript\" language=\"JavaScript\">");
            out.println("   alert('Se actualizó exitosamente el recurso con identificador "+base.getId()+"');");
            out.println("   window.location.href='"+paramRequest.getRenderUrl().setAction("edit")+"';");
            out.println("</script>");
        }
    }

    private String getForm(javax.servlet.http.HttpServletRequest request, SWBParamRequest paramRequest) throws SWBResourceException {
        Resource base=getResourceBase();
        StringBuilder htm = new StringBuilder();
        final String path = SWBPortal.getWebWorkPath()+base.getWorkPath()+"/";

        SWBResourceURL url = paramRequest.getActionUrl().setAction(SWBParamRequest.Action_EDIT);
        htm.append("<script type=\"text/javascript\">\n");
        htm.append("<!--\n");
        htm.append("  dojo.require('dijit.layout.ContentPane');");
        htm.append("  dojo.require('dijit.form.Form');");
        htm.append("  dojo.require('dijit.form.ValidationTextBox');");
        htm.append("  dojo.require('dijit.form.RadioButton');");
        htm.append("  dojo.require('dijit.form.SimpleTextarea');");
        htm.append("  dojo.require('dijit.form.Button');");
        
        htm.append("  function isValid() {");
        if(base.getAttribute("imgfile")==null) {
            htm.append("    var imgfile = dojo.byId('imgfile');");
            htm.append("    if(isEmpty(imgfile.value)&&(!dojo.byId('wbNoFile_imgfile')||dojo.byId('wbNoFile_imgfile')&& !dojo.byId('wbNoFile_imgfile').checked)) {");
            htm.append("      alert('"+paramRequest.getLocaleString("msgWrnNoImage")+"');");
            htm.append("      return false;");
            htm.append("    }");
        }
        htm.append("    return valida_frmAdmRes();");
        htm.append("  }\n");

        htm.append("function valida_frmAdmRes() {");
        htm.append("   pCaracter = dojo.byId('imgfile').value;");
        htm.append("   var pExt='gif|jpg|jpeg|png';");
        htm.append("   if(pCaracter.length > 0) {");
        htm.append("       var swFormat=pExt + '|';");
        htm.append("       sExt=pCaracter.substring(pCaracter.indexOf('.')).toLowerCase();");
        htm.append("       var sType='';");
        htm.append("       var flag=false;");
        htm.append("       while(swFormat.length > 0 ) {");
        htm.append("           sType= swFormat.substring(0, swFormat.indexOf('|'));");
        htm.append("           if(sExt.indexOf(sType)!=-1)");
        htm.append("               flag=true;");
        htm.append("           swFormat=swFormat.substring(swFormat.indexOf('|')+1);");
        htm.append("       }");
        htm.append("       if(!flag) {");
        htm.append("           while(pExt.indexOf('|')!=-1)");
        htm.append("               pExt=pExt.replace('|',',');");
        htm.append("           alert('El archivo no corresponde a ninguna de las extensiones validas:' + pExt.replace('|',','));");
        htm.append("           return false;");
        htm.append("       }");
        htm.append("   }");
        htm.append("   return true;");
        htm.append("}\n");
        htm.append("-->\n");
        htm.append("</script>\n");

        htm.append("<div class=\"swbform\">\n");
        htm.append("<form id=\"frmPromo\" dojoType=\"dijit.form.Form\" method=\"post\" enctype=\"multipart/form-data\" action=\""+url+"\">\n");
        htm.append("<fieldset>\n");
        htm.append("    <legend>Datos</legend>\n");
        htm.append("    <ul class=\"swbform-ul\">\n");
        htm.append("        <li class=\"swbform-li\">\n");
        htm.append("          <label for=\"title\" class=\"swbform-label\">Título</label>\n");
        htm.append("          <input type=\"text\" id=\"title\" name=\"title\" dojoType=\"dijit.form.ValidationTextBox\" value=\""+base.getAttribute("title","")+"\" />\n");
        htm.append("        </li>\n");
        htm.append("        <li class=\"swbform-li\">\n");
        htm.append("          <label for=\"subtitle\" class=\"swbform-label\">Subtítulo</label>\n");
        htm.append("          <input type=\"text\" id=\"subtitle\" name=\"subtitle\" dojoType=\"dijit.form.ValidationTextBox\" value=\""+base.getAttribute("subtitle","")+"\" />\n");
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
        htm.append("            <input type=\"text\" id=\"url\" name=\"url\" dojoType=\"dijit.form.ValidationTextBox\" value=\""+base.getAttribute("url","")+"\"/>\n");
        htm.append("        </li>\n");
        htm.append("        <li>Mostrar en una nueva ventana</li>\n");
        htm.append("        <li class=\"swbform-li\">\n");
        htm.append("          <label for=\"target_si\" class=\"swbform-label\">Sí</label>\n");
        htm.append("          <input type=\"radio\" id=\"target_si\" name=\"target\" dojoType=\"dijit.form.RadioButton\" value=\"true\" ");
        boolean target = Boolean.parseBoolean(base.getAttribute("target"));
        if(target)
            htm.append(" checked=\"checked\" ");
        htm.append("/>\n");
        htm.append("        </li>\n");

        htm.append("        <li class=\"swbform-li\">\n");
        htm.append("          <label for=\"target_no\" class=\"swbform-label\">No</label>\n");
        htm.append("          <input type=\"radio\" id=\"target_no\" name=\"target\" dojoType=\"dijit.form.RadioButton\" value=\"false\" ");
        if(!target)
            htm.append(" checked=\"checked\" ");
        htm.append("/>\n");
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
//        htm.append("        <li>\n");
//        htm.append("          <table border=\"0\" width=\"100%\">\n");
//        htm.append("          <tr>\n");
//        htm.append("             <td class=\"datos\" width=\"200px\" align=\"right\">\n");
//        htm.append("                Color del texto(hexadecimal)\n");
//        htm.append("             </td>\n");
//        htm.append("             <td class=\"valores\">\n");
//        htm.append("                <input type=\"text\" id=\"textcolor\" name=\"textcolor\" value=\""+base.getAttribute("textcolor","#000000")+"\" maxlength=\"7\" />\n");
//        htm.append("                <span style=\"background-color:"+base.getAttribute("textcolor","#000000")+";\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>\n");
//        htm.append("             </td>\n");
//        htm.append("          </tr>\n");
//        htm.append("          </table>\n");
//        htm.append("        </li>\n");
//        htm.append("        <li>\n");
//        htm.append("          <script type=\"text/javascript\">\n");
//        htm.append("          <!--\n");
//        htm.append("          dojo.require(\"dijit.ColorPalette\");\n");
//        htm.append("          dojo.addOnLoad(function(){\n");
//        htm.append("            var myPalette = new dijit.ColorPalette( {palette:\"7x10\", onChange: function(val){ dojo.byId('textcolor').value=val; dojo.byId('pselcolor').style.color=val;\n");
//        htm.append("            dojo.byId('pselcolor').innerHTML=val;}}, \"cptextcolor\" );\n");
//        htm.append("          });\n");
//        htm.append("          -->\n");
//        htm.append("          </script>\n");
//        htm.append("          <table border=\"0\" width=\"100%\">\n");
//        htm.append("            <tr>\n");
//        htm.append("                <td class=\"datos\" width=\"200\" align=\"right\">\n");
//        htm.append("                    Otro color de texto\n");
//        htm.append("                    <span id=\"pselcolor\"></span>\n");
//        htm.append("                </td>\n");
//        htm.append("                <td class=\"valores\">\n");
//        htm.append("                    <div id=\"cptextcolor\"></div>\n");
//        htm.append("                </td>\n");
//        htm.append("             </tr>\n");
//        htm.append("          </table>\n");
//        htm.append("        </li>\n");
        
        htm.append("        <li class=\"swbform-li\">\n");
        htm.append("            <script type=\"text/javascript\">\n");
        htm.append("                function setColor(color)\n");
        htm.append("                {\n");
        htm.append("                    var col=dojo.byId('textcolor');\n");
        htm.append("                    col.value=color;\n");
        htm.append("                    col.style.backgroundColor=color;\n");
        htm.append("                    var num=parseInt(\"FFFFFF\", 16)-parseInt(color.substring(1), 16);\n");
        htm.append("                    var hex=num.toString(16);\n");
        htm.append("                    while (hex.length < 6) {\n");
        htm.append("                        hex = \"0\" + hex;\n");
        htm.append("                    }                    \n");
        htm.append("                    col.style.color=\"#\"+hex;\n");
        htm.append("                    //alert(hex);\n");
        htm.append("                }\n");
        htm.append("                dojo.addOnLoad(function(){\n");
        htm.append("                    setColor(\""+base.getAttribute("textcolor","#000000")+"\");\n");
        htm.append("                });\n");
        htm.append("            </script>  \n");
        htm.append("          <label for=\"textcolor\" class=\"swbform-label\">Color del texto(hexadecimal)</label>\n");
        htm.append("          <input class=\"color\" type=\"text\" id=\"textcolor\" name=\"textcolor\" value=\""+base.getAttribute("textcolor","#000000")+"\" onblur=\"setColor(this.value);\" maxlength=\"7\" />\n");
        htm.append("        </li>\n");
        htm.append("        <li class=\"swbform-li\">\n");
        htm.append("          <label class=\"swbform-label\"></label>\n");
        htm.append("          <img src=\"/swbadmin/images/colorgrid.gif\" width=\"292\" height=\"196\" border=\"0\" alt=\"RGB color mixer\" usemap=\"#gridmap\" ismap=\"ismap\">\n");
        htm.append("          <map name=\"gridmap\">\n");
        htm.append("<!--- Row 1 --->\n");
        htm.append("<area coords=\"2,2,18,18\" onclick=\"setColor('#330000')\">\n");
        htm.append("<area coords=\"18,2,34,18\" onclick=\"setColor('#333300')\">\n");
        htm.append("<area coords=\"34,2,50,18\" onclick=\"setColor('#336600')\">\n");
        htm.append("<area coords=\"50,2,66,18\" onclick=\"setColor('#339900')\">\n");
        htm.append("<area coords=\"66,2,82,18\" onclick=\"setColor('#33CC00')\">\n");
        htm.append("<area coords=\"82,2,98,18\" onclick=\"setColor('#33FF00')\">\n");
        htm.append("<area coords=\"98,2,114,18\" onclick=\"setColor('#66FF00')\">\n");
        htm.append("<area coords=\"114,2,130,18\" onclick=\"setColor('#66CC00')\">\n");
        htm.append("<area coords=\"130,2,146,18\" onclick=\"setColor('#669900')\">\n");
        htm.append("<area coords=\"146,2,162,18\" onclick=\"setColor('#666600')\">\n");
        htm.append("<area coords=\"162,2,178,18\" onclick=\"setColor('#663300')\">\n");
        htm.append("<area coords=\"178,2,194,18\" onclick=\"setColor('#660000')\">\n");
        htm.append("<area coords=\"194,2,210,18\" onclick=\"setColor('#FF0000')\">\n");
        htm.append("<area coords=\"210,2,226,18\" onclick=\"setColor('#FF3300')\">\n");
        htm.append("<area coords=\"226,2,242,18\" onclick=\"setColor('#FF6600')\">\n");
        htm.append("<area coords=\"242,2,258,18\" onclick=\"setColor('#FF9900')\">\n");
        htm.append("<area coords=\"258,2,274,18\" onclick=\"setColor('#FFCC00')\">\n");
        htm.append("<area coords=\"274,2,290,18\" onclick=\"setColor('#FFFF00')\">\n");
        htm.append("<!--- Row 2 --->\n");
        htm.append("<area coords=\"2,18,18,34\" onclick=\"setColor('#330033')\">\n");
        htm.append("<area coords=\"18,18,34,34\" onclick=\"setColor('#333333')\">\n");
        htm.append("<area coords=\"34,18,50,34\" onclick=\"setColor('#336633')\">\n");
        htm.append("<area coords=\"50,18,66,34\" onclick=\"setColor('#339933')\">\n");
        htm.append("<area coords=\"66,18,82,34\" onclick=\"setColor('#33CC33')\">\n");
        htm.append("<area coords=\"82,18,98,34\" onclick=\"setColor('#33FF33')\">\n");
        htm.append("<area coords=\"98,18,114,34\" onclick=\"setColor('#66FF33')\">\n");
        htm.append("<area coords=\"114,18,130,34\" onclick=\"setColor('#66CC33')\">\n");
        htm.append("<area coords=\"130,18,146,34\" onclick=\"setColor('#669933')\">\n");
        htm.append("<area coords=\"146,18,162,34\" onclick=\"setColor('#666633')\">\n");
        htm.append("<area coords=\"162,18,178,34\" onclick=\"setColor('#663333')\">\n");
        htm.append("<area coords=\"178,18,194,34\" onclick=\"setColor('#660033')\">\n");
        htm.append("<area coords=\"194,18,210,34\" onclick=\"setColor('#FF0033')\">\n");
        htm.append("<area coords=\"210,18,226,34\" onclick=\"setColor('#FF3333')\">\n");
        htm.append("<area coords=\"226,18,242,34\" onclick=\"setColor('#FF6633')\">\n");
        htm.append("<area coords=\"242,18,258,34\" onclick=\"setColor('#FF9933')\">\n");
        htm.append("<area coords=\"258,18,274,34\" onclick=\"setColor('#FFCC33')\">\n");
        htm.append("<area coords=\"274,18,290,34\" onclick=\"setColor('#FFFF33')\">\n");
        htm.append("<!--- Row 3 --->\n");
        htm.append("<area coords=\"2,34,18,50\" onclick=\"setColor('#330066')\">\n");
        htm.append("<area coords=\"18,34,34,50\" onclick=\"setColor('#333366')\">\n");
        htm.append("<area coords=\"34,34,50,50\" onclick=\"setColor('#336666')\">\n");
        htm.append("<area coords=\"50,34,66,50\" onclick=\"setColor('#339966')\">\n");
        htm.append("<area coords=\"66,34,82,50\" onclick=\"setColor('#33CC66')\">\n");
        htm.append("<area coords=\"82,34,98,50\" onclick=\"setColor('#33FF66')\">\n");
        htm.append("<area coords=\"98,34,114,50\" onclick=\"setColor('#66FF66')\">\n");
        htm.append("<area coords=\"114,34,130,50\" onclick=\"setColor('#66CC66')\">\n");
        htm.append("<area coords=\"130,34,146,50\" onclick=\"setColor('#669966')\">\n");
        htm.append("<area coords=\"146,34,162,50\" onclick=\"setColor('#666666')\">\n");
        htm.append("<area coords=\"162,34,178,50\" onclick=\"setColor('#663366')\">\n");
        htm.append("<area coords=\"178,34,194,50\" onclick=\"setColor('#660066')\">\n");
        htm.append("<area coords=\"194,34,210,50\" onclick=\"setColor('#FF0066')\">\n");
        htm.append("<area coords=\"210,34,226,50\" onclick=\"setColor('#FF3366')\">\n");
        htm.append("<area coords=\"226,34,242,50\" onclick=\"setColor('#FF6666')\">\n");
        htm.append("<area coords=\"242,34,258,50\" onclick=\"setColor('#FF9966')\">\n");
        htm.append("<area coords=\"258,34,274,50\" onclick=\"setColor('#FFCC66')\">\n");
        htm.append("<area coords=\"274,34,290,50\" onclick=\"setColor('#FFFF66')\">\n");
        htm.append("<!--- Row 4 --->\n");
        htm.append("<area coords=\"2,50,18,66\" onclick=\"setColor('#330099')\">\n");
        htm.append("<area coords=\"18,50,34,66\" onclick=\"setColor('#333399')\">\n");
        htm.append("<area coords=\"34,50,50,66\" onclick=\"setColor('#336699')\">\n");
        htm.append("<area coords=\"50,50,66,66\" onclick=\"setColor('#339999')\">\n");
        htm.append("<area coords=\"66,50,82,66\" onclick=\"setColor('#33CC99')\">\n");
        htm.append("<area coords=\"82,50,98,66\" onclick=\"setColor('#33FF99')\">\n");
        htm.append("<area coords=\"98,50,114,66\" onclick=\"setColor('#66FF99')\">\n");
        htm.append("<area coords=\"114,50,130,66\" onclick=\"setColor('#66CC99')\">\n");
        htm.append("<area coords=\"130,50,146,66\" onclick=\"setColor('#669999')\">\n");
        htm.append("<area coords=\"146,50,162,66\" onclick=\"setColor('#666699')\">\n");
        htm.append("<area coords=\"162,50,178,66\" onclick=\"setColor('#663399')\">\n");
        htm.append("<area coords=\"178,50,194,66\" onclick=\"setColor('#660099')\">\n");
        htm.append("<area coords=\"194,50,210,66\" onclick=\"setColor('#FF0099')\">\n");
        htm.append("<area coords=\"210,50,226,66\" onclick=\"setColor('#FF3399')\">\n");
        htm.append("<area coords=\"226,50,242,66\" onclick=\"setColor('#FF6699')\">\n");
        htm.append("<area coords=\"242,50,258,66\" onclick=\"setColor('#FF9999')\">\n");
        htm.append("<area coords=\"258,50,274,66\" onclick=\"setColor('#FFCC99')\">\n");
        htm.append("<area coords=\"274,50,290,66\" onclick=\"setColor('#FFFF99')\">\n");
        htm.append("<!--- Row 5 --->\n");
        htm.append("<area coords=\"2,66,18,82\" onclick=\"setColor('#3300CC')\">\n");
        htm.append("<area coords=\"18,66,34,82\" onclick=\"setColor('#3333CC')\">\n");
        htm.append("<area coords=\"34,66,50,82\" onclick=\"setColor('#3366CC')\">\n");
        htm.append("<area coords=\"50,66,66,82\" onclick=\"setColor('#3399CC')\">\n");
        htm.append("<area coords=\"66,66,82,82\" onclick=\"setColor('#33CCCC')\">\n");
        htm.append("<area coords=\"82,66,98,82\" onclick=\"setColor('#33FFCC')\">\n");
        htm.append("<area coords=\"98,66,114,82\" onclick=\"setColor('#66FFCC')\">\n");
        htm.append("<area coords=\"114,66,130,82\" onclick=\"setColor('#66CCCC')\">\n");
        htm.append("<area coords=\"130,66,146,82\" onclick=\"setColor('#6699CC')\">\n");
        htm.append("<area coords=\"146,66,162,82\" onclick=\"setColor('#6666CC')\">\n");
        htm.append("<area coords=\"162,66,178,82\" onclick=\"setColor('#6633CC')\">\n");
        htm.append("<area coords=\"178,66,194,82\" onclick=\"setColor('#6600CC')\">\n");
        htm.append("<area coords=\"194,66,210,82\" onclick=\"setColor('#FF00CC')\">\n");
        htm.append("<area coords=\"210,66,226,82\" onclick=\"setColor('#FF33CC')\">\n");
        htm.append("<area coords=\"226,66,242,82\" onclick=\"setColor('#FF66CC')\">\n");
        htm.append("<area coords=\"242,66,258,82\" onclick=\"setColor('#FF99CC')\">\n");
        htm.append("<area coords=\"258,66,274,82\" onclick=\"setColor('#FFCCCC')\">\n");
        htm.append("<area coords=\"274,66,290,82\" onclick=\"setColor('#FFFFCC')\">\n");
        htm.append("<!--- Row 6 --->\n");
        htm.append("<area coords=\"2,82,18,98\" onclick=\"setColor('#3300FF')\">\n");
        htm.append("<area coords=\"18,82,34,98\" onclick=\"setColor('#3333FF')\">\n");
        htm.append("<area coords=\"34,82,50,98\" onclick=\"setColor('#3366FF')\">\n");
        htm.append("<area coords=\"50,82,66,98\" onclick=\"setColor('#3399FF')\">\n");
        htm.append("<area coords=\"66,82,82,98\" onclick=\"setColor('#33CCFF')\">\n");
        htm.append("<area coords=\"82,82,98,98\" onclick=\"setColor('#33FFFF')\">\n");
        htm.append("<area coords=\"98,82,114,98\" onclick=\"setColor('#66FFFF')\">\n");
        htm.append("<area coords=\"114,82,130,98\" onclick=\"setColor('#66CCFF')\">\n");
        htm.append("<area coords=\"130,82,146,98\" onclick=\"setColor('#6699FF')\">\n");
        htm.append("<area coords=\"146,82,162,98\" onclick=\"setColor('#6666FF')\">\n");
        htm.append("<area coords=\"162,82,178,98\" onclick=\"setColor('#6633FF')\">\n");
        htm.append("<area coords=\"178,82,194,98\" onclick=\"setColor('#6600FF')\">\n");
        htm.append("<area coords=\"194,82,210,98\" onclick=\"setColor('#FF00FF')\">\n");
        htm.append("<area coords=\"210,82,226,98\" onclick=\"setColor('#FF33FF')\">\n");
        htm.append("<area coords=\"226,82,242,98\" onclick=\"setColor('#FF66FF')\">\n");
        htm.append("<area coords=\"242,82,258,98\" onclick=\"setColor('#FF99FF')\">\n");
        htm.append("<area coords=\"258,82,274,98\" onclick=\"setColor('#FFCCFF')\">\n");
        htm.append("<area coords=\"274,82,290,98\" onclick=\"setColor('#FFFFFF')\">\n");
        htm.append("<!--- Row 7 --->\n");
        htm.append("<area coords=\"2,98,18,114\" onclick=\"setColor('#0000FF')\">\n");
        htm.append("<area coords=\"18,98,34,114\" onclick=\"setColor('#0033FF')\">\n");
        htm.append("<area coords=\"34,98,50,114\" onclick=\"setColor('#0066FF')\">\n");
        htm.append("<area coords=\"50,98,66,114\" onclick=\"setColor('#0099FF')\">\n");
        htm.append("<area coords=\"66,98,82,114\" onclick=\"setColor('#00CCFF')\">\n");
        htm.append("<area coords=\"82,98,98,114\" onclick=\"setColor('#00FFFF')\">\n");
        htm.append("<area coords=\"98,98,114,114\" onclick=\"setColor('#99FFFF')\">\n");
        htm.append("<area coords=\"114,98,130,114\" onclick=\"setColor('#99CCFF')\">\n");
        htm.append("<area coords=\"130,98,146,114\" onclick=\"setColor('#9999FF')\">\n");
        htm.append("<area coords=\"146,98,162,114\" onclick=\"setColor('#9966FF')\">\n");
        htm.append("<area coords=\"162,98,178,114\" onclick=\"setColor('#9933FF')\">\n");
        htm.append("<area coords=\"178,98,194,114\" onclick=\"setColor('#9900FF')\">\n");
        htm.append("<area coords=\"194,98,210,114\" onclick=\"setColor('#CC00FF')\">\n");
        htm.append("<area coords=\"210,98,226,114\" onclick=\"setColor('#CC33FF')\">\n");
        htm.append("<area coords=\"226,98,242,114\" onclick=\"setColor('#CC66FF')\">\n");
        htm.append("<area coords=\"242,98,258,114\" onclick=\"setColor('#CC99FF')\">\n");
        htm.append("<area coords=\"258,98,274,114\" onclick=\"setColor('#CCCCFF')\">\n");
        htm.append("<area coords=\"274,98,290,114\" onclick=\"setColor('#CCFFFF')\">\n");
        htm.append("<!--- Row 8 --->\n");
        htm.append("<area coords=\"2,114,18,130\" onclick=\"setColor('#0000CC')\">\n");
        htm.append("<area coords=\"18,114,34,130\" onclick=\"setColor('#0033CC')\">\n");
        htm.append("<area coords=\"34,114,50,130\" onclick=\"setColor('#0066CC')\">\n");
        htm.append("<area coords=\"50,114,66,130\" onclick=\"setColor('#0099CC')\">\n");
        htm.append("<area coords=\"66,114,82,130\" onclick=\"setColor('#00CCCC')\">\n");
        htm.append("<area coords=\"82,114,98,130\" onclick=\"setColor('#00FFCC')\">\n");
        htm.append("<area coords=\"98,114,114,130\" onclick=\"setColor('#99FFCC')\">\n");
        htm.append("<area coords=\"114,114,130,130\" onclick=\"setColor('#99CCCC')\">\n");
        htm.append("<area coords=\"130,114,146,130\" onclick=\"setColor('#9999CC')\">\n");
        htm.append("<area coords=\"146,114,162,130\" onclick=\"setColor('#9966CC')\">\n");
        htm.append("<area coords=\"162,114,178,130\" onclick=\"setColor('#9933CC')\">\n");
        htm.append("<area coords=\"178,114,194,130\" onclick=\"setColor('#9900CC')\">\n");
        htm.append("<area coords=\"194,114,210,130\" onclick=\"setColor('#CC00CC')\">\n");
        htm.append("<area coords=\"210,114,226,130\" onclick=\"setColor('#CC33CC')\">\n");
        htm.append("<area coords=\"226,114,242,130\" onclick=\"setColor('#CC66CC')\">\n");
        htm.append("<area coords=\"242,114,258,130\" onclick=\"setColor('#CC99CC')\">\n");
        htm.append("<area coords=\"258,114,274,130\" onclick=\"setColor('#CCCCCC')\">\n");
        htm.append("<area coords=\"274,114,290,130\" onclick=\"setColor('#CCFFCC')\">\n");
        htm.append("<!--- Row 9 --->\n");
        htm.append("<area coords=\"2,130,18,146\" onclick=\"setColor('#000099')\">\n");
        htm.append("<area coords=\"18,130,34,146\" onclick=\"setColor('#003399')\">\n");
        htm.append("<area coords=\"34,130,50,146\" onclick=\"setColor('#006699')\">\n");
        htm.append("<area coords=\"50,130,66,146\" onclick=\"setColor('#009999')\">\n");
        htm.append("<area coords=\"66,130,82,146\" onclick=\"setColor('#00CC99')\">\n");
        htm.append("<area coords=\"82,130,98,146\" onclick=\"setColor('#00FF99')\">\n");
        htm.append("<area coords=\"98,130,114,146\" onclick=\"setColor('#99FF99')\">\n");
        htm.append("<area coords=\"114,130,130,146\" onclick=\"setColor('#99CC99')\">\n");
        htm.append("<area coords=\"130,130,146,146\" onclick=\"setColor('#999999')\">\n");
        htm.append("<area coords=\"146,130,162,146\" onclick=\"setColor('#996699')\">\n");
        htm.append("<area coords=\"162,130,178,146\" onclick=\"setColor('#993399')\">\n");
        htm.append("<area coords=\"178,130,194,146\" onclick=\"setColor('#990099')\">\n");
        htm.append("<area coords=\"194,130,210,146\" onclick=\"setColor('#CC0099')\">\n");
        htm.append("<area coords=\"210,130,226,146\" onclick=\"setColor('#CC3399')\">\n");
        htm.append("<area coords=\"226,130,242,146\" onclick=\"setColor('#CC6699')\">\n");
        htm.append("<area coords=\"242,130,258,146\" onclick=\"setColor('#CC9999')\">\n");
        htm.append("<area coords=\"258,130,274,146\" onclick=\"setColor('#CCCC99')\">\n");
        htm.append("<area coords=\"274,130,290,146\" onclick=\"setColor('#CCFF99')\">\n");
        htm.append("<!--- Row 10 --->\n");
        htm.append("<area coords=\"2,146,18,162\" onclick=\"setColor('#000066')\">\n");
        htm.append("<area coords=\"18,146,34,162\" onclick=\"setColor('#003366')\">\n");
        htm.append("<area coords=\"34,146,50,162\" onclick=\"setColor('#006666')\">\n");
        htm.append("<area coords=\"50,146,66,162\" onclick=\"setColor('#009966')\">\n");
        htm.append("<area coords=\"66,146,82,162\" onclick=\"setColor('#00CC66')\">\n");
        htm.append("<area coords=\"82,146,98,162\" onclick=\"setColor('#00FF66')\">\n");
        htm.append("<area coords=\"98,146,114,162\" onclick=\"setColor('#99FF66')\">\n");
        htm.append("<area coords=\"114,146,130,162\" onclick=\"setColor('#99CC66')\">\n");
        htm.append("<area coords=\"130,146,146,162\" onclick=\"setColor('#999966')\">\n");
        htm.append("<area coords=\"146,146,162,162\" onclick=\"setColor('#996666')\">\n");
        htm.append("<area coords=\"162,146,178,162\" onclick=\"setColor('#993366')\">\n");
        htm.append("<area coords=\"178,146,194,162\" onclick=\"setColor('#990066')\">\n");
        htm.append("<area coords=\"194,146,210,162\" onclick=\"setColor('#CC0066')\">\n");
        htm.append("<area coords=\"210,146,226,162\" onclick=\"setColor('#CC3366')\">\n");
        htm.append("<area coords=\"226,146,242,162\" onclick=\"setColor('#CC6666')\">\n");
        htm.append("<area coords=\"242,146,258,162\" onclick=\"setColor('#CC9966')\">\n");
        htm.append("<area coords=\"258,146,274,162\" onclick=\"setColor('#CCCC66')\">\n");
        htm.append("<area coords=\"274,146,290,162\" onclick=\"setColor('#CCFF66')\">\n");
        htm.append("<!--- Row 11 --->\n");
        htm.append("<area coords=\"2,162,18,178\" onclick=\"setColor('#000033')\">\n");
        htm.append("<area coords=\"18,162,34,178\" onclick=\"setColor('#003333')\">\n");
        htm.append("<area coords=\"34,162,50,178\" onclick=\"setColor('#006633')\">\n");
        htm.append("<area coords=\"50,162,66,178\" onclick=\"setColor('#009933')\">\n");
        htm.append("<area coords=\"66,162,82,178\" onclick=\"setColor('#00CC33')\">\n");
        htm.append("<area coords=\"82,162,98,178\" onclick=\"setColor('#00FF33')\">\n");
        htm.append("<area coords=\"98,162,114,178\" onclick=\"setColor('#99FF33')\">\n");
        htm.append("<area coords=\"114,162,130,178\" onclick=\"setColor('#99CC33')\">\n");
        htm.append("<area coords=\"130,162,146,178\" onclick=\"setColor('#999933')\">\n");
        htm.append("<area coords=\"146,162,162,178\" onclick=\"setColor('#996633')\">\n");
        htm.append("<area coords=\"162,162,178,178\" onclick=\"setColor('#993333')\">\n");
        htm.append("<area coords=\"178,162,194,178\" onclick=\"setColor('#990033')\">\n");
        htm.append("<area coords=\"194,162,210,178\" onclick=\"setColor('#CC0033')\">\n");
        htm.append("<area coords=\"210,162,226,178\" onclick=\"setColor('#CC3333')\">\n");
        htm.append("<area coords=\"226,162,242,178\" onclick=\"setColor('#CC6633')\">\n");
        htm.append("<area coords=\"242,162,258,178\" onclick=\"setColor('#CC9933')\">\n");
        htm.append("<area coords=\"258,162,274,178\" onclick=\"setColor('#CCCC33')\">\n");
        htm.append("<area coords=\"274,162,290,178\" onclick=\"setColor('#CCFF33')\">\n");
        htm.append("<!--- Row 12 --->\n");
        htm.append("<area coords=\"2,178,18,194\" onclick=\"setColor('#000000')\">\n");
        htm.append("<area coords=\"18,178,34,194\" onclick=\"setColor('#003300')\">\n");
        htm.append("<area coords=\"34,178,50,194\" onclick=\"setColor('#006600')\">\n");
        htm.append("<area coords=\"50,178,66,194\" onclick=\"setColor('#009900')\">\n");
        htm.append("<area coords=\"66,178,82,194\" onclick=\"setColor('#00CC00')\">\n");
        htm.append("<area coords=\"82,178,98,194\" onclick=\"setColor('#00FF00')\">\n");
        htm.append("<area coords=\"98,178,114,194\" onclick=\"setColor('#99FF00')\">\n");
        htm.append("<area coords=\"114,178,130,194\" onclick=\"setColor('#99CC00')\">\n");
        htm.append("<area coords=\"130,178,146,194\" onclick=\"setColor('#999900')\">\n");
        htm.append("<area coords=\"146,178,162,194\" onclick=\"setColor('#996600')\">\n");
        htm.append("<area coords=\"162,178,178,194\" onclick=\"setColor('#993300')\">\n");
        htm.append("<area coords=\"178,178,194,194\" onclick=\"setColor('#990000')\">\n");
        htm.append("<area coords=\"194,178,210,194\" onclick=\"setColor('#CC0000')\">\n");
        htm.append("<area coords=\"210,178,226,194\" onclick=\"setColor('#CC3300')\">\n");
        htm.append("<area coords=\"226,178,242,194\" onclick=\"setColor('#CC6600')\">\n");
        htm.append("<area coords=\"242,178,258,194\" onclick=\"setColor('#CC9900')\">\n");
        htm.append("<area coords=\"258,178,274,194\" onclick=\"setColor('#CCCC00')\">\n");
        htm.append("<area coords=\"274,178,290,194\" onclick=\"setColor('#CCFF00')\">\n");
        htm.append("</map>\n");
        htm.append("        </li>        \n");
        
        
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
            htm.append("    <label for=\"wbNoFile_template\" class=\"swbform-label\">Quitar plantilla</label>\n");
            htm.append("    <input type=\"checkbox\" id=\"wbNoFile_template\" name=\"wbNoFile_template\" value=\"true\"/>\n");
            htm.append("  </li>\n");
        }
        htm.append("    </ul>\n");
        htm.append("  </fieldset>\n");
        htm.append("</div>\n");

        htm.append("<fieldset>\n");
        htm.append("   <legend></legend>\n");
        htm.append("   <ul class=\"swbform-ul\">\n");
        htm.append("      <li>\n");
        htm.append("         <button type=\"submit\" dojoType=\"dijit.form.Button\" onclick=\"return isValid()\">Guardar</button>\n");
        htm.append("         <button type=\"reset\" dojoType=\"dijit.form.Button\">Reestablecer</button>\n");
        htm.append("      </li>\n");
        htm.append("   </ul>\n");
        htm.append("</fieldset>\n");
        htm.append("</form>\n");
        htm.append("</div>\n");
        
        return htm.toString();
    }

    private void edit(HttpServletRequest request, SWBActionResponse response) throws FileNotFoundException, Exception {
        Resource base = getResourceBase();

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            File file = new File(SWBPortal.getWorkPath()+base.getWorkPath()+"/");
            if(!file.exists()) {
                file.mkdirs();
            }
            base.setAttribute("deftmp", "false");
            Iterator<FileItem> iter = SWBUtils.IO.fileUpload(request, null);
            while(iter.hasNext()) {
                FileItem item = iter.next();
                if(item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString().trim();
                    base.setAttribute(name, value);
                }else {
                    String filename = item.getName().replaceAll(" ", "_").trim();
                    filename = filename.substring(filename.lastIndexOf('\\')+1);
                    if(item.getFieldName().equals("imgfile") && filename.equals("") && base.getAttribute("imgfile")==null)
                    {
                        throw new FileNotFoundException(item.getFieldName()+" es requerido");
                    }
                    else if(!filename.equals("")) {
                        file = new File(SWBPortal.getWorkPath()+base.getWorkPath()+"/"+filename);
                        item.write(file);
                        base.setAttribute(item.getFieldName(), filename);
                        base.setAttribute("wbNoFile_"+item.getFieldName(), "false");
                    }
                    else
                    {
                    }
                }
            }
        }
    }
}
