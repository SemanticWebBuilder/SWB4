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
package org.semanticwb.portal.resources.sem;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import javax.xml.transform.Templates;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SemPromo extends org.semanticwb.portal.resources.sem.base.SemPromoBase 
{
        /** The log. */
    private static Logger log = SWBUtils.getLogger(SemPromo.class);

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

    public SemPromo()
    {
    }

   /**
   * Constructs a SemPromo with a SemanticObject
   * @param base The SemanticObject with the properties for the SemPromo
   */
    public SemPromo(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void setResourceBase(Resource base) throws SWBResourceException {
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


    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        response.setContentType("text/html; charset=ISO-8859-1");
        SemPromo promo = (SemPromo) getSemanticObject().createGenericInstance();
        PrintWriter out = response.getWriter();
        String pathWeb = SWBPortal.getWebWorkPath() + "/models/" + promo.getSemanticObject().getModel().getName() + "/" + promo.getSemanticClass().getClassGroupId() + "/" + getId();

        boolean template = false;
        if(promo.getPromChangeTemplate() != null) {
            if(promo.getPromChangeTemplate().indexOf("xsl") > 0 || promo.getPromChangeTemplate().indexOf("xslt") > 0) {
                template = true ;
            }
        }
        if(template || promo.isPromDefaultTemplate()) {
            Templates curtpl;
            try {
                curtpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getFileFromWorkPath(pathWeb + "/" + promo.swbres_promChangeTemplate.getName() + "_" + promo.getId() + "_" + promo.getPromChangeTemplate()));
                path = webWorkPath;
            } catch(Exception e) {
                curtpl = tpl;
            }
            try {
                Document dom = getDom(request, response, paramRequest);
                String html = SWBUtils.XML.transformDom(curtpl, dom);
                out.println(html);
            } catch(SWBResourceException swbe) {
                out.println(swbe);
            } catch(Exception e) {
                log.error("Error in doView method while rendering the resource base: " + promo.getId() + "-" + promo.getResourceBase().getTitle(), e);
                e.printStackTrace(System.out);
            }
        } else {
            try {
                if(promo.getPromCssClass() == null) {
                    out.println(renderWithStyle(paramRequest));
                }  else {
                    out.println(render(paramRequest));
                }
            } catch(SWBResourceException swbe) {
                out.println(swbe);
            }
        }
    }

    public Document getDom(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException {
        SemPromo promo = (SemPromo) getSemanticObject().createGenericInstance();
        String pathWeb = SWBPortal.getWebWorkPath() + "/models/" + promo.getSemanticObject().getModel().getName() + "/" + promo.getSemanticClass().getClassGroupId() + "/" + getId();

        if(promo.getPromImage() == null) {
            throw new SWBResourceException(paramRequest.getLocaleString("msgErrResource"));
        }

        Document  dom = SWBUtils.XML.getNewDocument();
        Element root = dom.createElement("promo");
        root.setAttribute("id", "promo_" + promo.getId());
        root.setAttribute("width", promo.getPromBulletWidth() + "");
        root.setAttribute("height", promo.getPromBulletHeight() + "");
        dom.appendChild(root);

        Element e;
        e = dom.createElement("title");
        String title = promo.getPromTitle() == null ? "" : promo.getPromTitle();
        e.appendChild(dom.createTextNode(title));
        root.appendChild(e);

        e = dom.createElement("subtitle");
        String subtitle = promo.getPromSubtitle() == null ? "" : promo.getPromSubtitle();
        e.appendChild(dom.createTextNode(subtitle));
        root.appendChild(e);

        Element img = dom.createElement("image");
        int widthImg = promo.getPromImgWidth() <= 0 ? 90 : promo.getPromImgWidth();
        img.setAttribute("width", widthImg + "");
        int heightImg = promo.getPromImgHeight() <= 0 ? 140 : promo.getPromImgHeight();
        img.setAttribute("height", heightImg + "");
        img.setAttribute("src", pathWeb + "/" + promo.swbres_promImage.getName() + "_" + promo.getId() + "_" + promo.getPromImage());
        String footer = promo.getPromFooter() == null ? (promo.getPromTitle() == null ? "image promo" : promo.getPromTitle()) : promo.getPromFooter();
        img.setAttribute("alt", footer);
        String internalLink = null;
        if (promo.getPromInternalUrl() != null) {
            WebPage wp = paramRequest.getWebPage().getWebSite().getWebPage(promo.getPromInternalUrl());
            if (wp != null && !wp.isDeleted() && !wp.isHidden()) {
                String lang = paramRequest.getUser().getLanguage();
                internalLink = wp.getUrl(lang) == null ? (wp.getUrl() == null ? "" : wp.getUrl()) : wp.getUrl(lang);
            }
        }
        String link = promo.getPromLink() == null ? (internalLink == null ? "" : internalLink) : promo.getPromLink();
        img.setAttribute("url", link);
        root.appendChild(img);

        e = dom.createElement("imageFoot");
        footer = promo.getPromFooter() == null ? "" : promo.getPromFooter();
        e.appendChild(dom.createTextNode(footer));
        img.appendChild(e);

        e = dom.createElement("content");

        e.setAttribute("url", link);
        String text = promo.getPromText() == null ? "" : promo.getPromText();
        e.appendChild(dom.createTextNode(text));
        root.appendChild(e);

        e = dom.createElement("more");
        e.setAttribute("url", link);
        e.setAttribute("target", promo.isPromOpenNewWindow() + "");
        String linkText = promo.getPromLinkText() == null ? "" : promo.getPromLinkText();
        e.appendChild(dom.createTextNode(linkText));
        root.appendChild(e);

        return dom;
    }

    private String renderWithStyle(SWBParamRequest paramRequest) throws SWBResourceException {
        StringBuilder out = new StringBuilder();
        SemPromo promo = (SemPromo) getSemanticObject().createGenericInstance();
        String pathWeb = SWBPortal.getWebWorkPath() + "/models/" + promo.getSemanticObject().getModel().getName() + "/" + promo.getSemanticClass().getClassGroupId() + "/" + getId();
        int width;
        try {
            width = promo.getPromBulletWidth();
        } catch(NumberFormatException nfe) {
            width = 0;
        }
        int height;
        try {
            height = promo.getPromBulletHeight();
        } catch(NumberFormatException nfe) {
            height = 0;
        }

        String textcolor = promo.getPromTextColor();
        String title = promo.getPromTitle();
        String titleStyle = promo.getPromTitleStyle();
        String subtitle = promo.getPromSubtitle();
        String subtitleStyle = promo.getPromSubtitleStyle();

        if(promo.getPromImage() == null) {
            throw new SWBResourceException(paramRequest.getLocaleString("msgErrResource"));
        }

        String imgfile = promo.getPromImage();
        String caption = promo.getPromFooter();
        String captionStyle = promo.getPromFooterStyle();

        String imgWidth = "";
        if(promo.getPromImgWidth() > 0) {
            imgWidth = "width=\"" + promo.getPromImgWidth() + "\"";
        }

        String imgHeight = "";
        if(promo.getPromImgHeight() > 0){
            imgHeight = "height=\"" + promo.getPromImgHeight() + "\"";
        }

        String text = promo.getPromText() == null ? "" : promo.getPromText();
        String textStyle = promo.getPromTextStyle();
        String more = promo.getPromLinkText() == null ? "" : promo.getPromLinkText();
        String moreStyle = promo.getPromLinkStyle();
        String internalLink = null;
        if (promo.getPromInternalUrl() != null) {
            WebPage wp = paramRequest.getWebPage().getWebSite().getWebPage(promo.getPromInternalUrl());
            if (wp != null && !wp.isDeleted() && !wp.isHidden()) {
                String lang = paramRequest.getUser().getLanguage();
                internalLink = wp.getUrl(lang) == null ? (wp.getUrl() == null ? "" : wp.getUrl()) : wp.getUrl(lang);
            }
        }

        String url = promo.getPromLink() == null ? (internalLink == null ? "" : internalLink) : promo.getPromLink();
        String uline = promo.isPromUnderlineLink() ? "1" : "0";
        boolean target = promo.isPromOpenNewWindow();

        int imgPos;
        try {
            String imgPo = promo.getPromImgPosition() == null ? "1" : promo.getPromImgPosition();
            imgPos = Integer.parseInt(imgPo);
        } catch(NumberFormatException nfe) {
            imgPos = 1;
        }

        try {
            //marco
            out.append("<div class=\"swb-promo\"");
            if(textcolor != null || width > 0 || height > 0) {
                out.append(" style=\"" + (textcolor == null ? "" : "color:" + textcolor + ";") + (width > 0 ? "width:" + width + "px;" : "") + (height > 0 ? "height:" + height + "px;" : "") + "\"");
            }
            out.append(">");

            //title
            if(title != null) {
                out.append("<h2" + (titleStyle == null ? "" : " style=\"" + titleStyle + "\"") + "><span> \n");
                out.append(title);
                out.append("</span></h2> \n");
            }

            //image
            String margin = "";
            StringBuilder img = new StringBuilder("");
            if(imgfile != null) {
                boolean hasLink = promo.getPromLinkText() == null && url != null;
                if(imgPos == 3) {
                    img.append("<div style=\"text-align:center; float:right; margin:10px\">");
                    img.append("<span>");
                    if(hasLink) {
                        img.append("<a href=\"" + url + "\"");
                        if(textStyle != null || "0".equals(uline)) {
                            img.append(" style=\"" + (textStyle == null ? "" : textStyle + ";") + ("0".equals(uline) ? "text-decoration:none;" : "") + "\"");
                        }
                        if(target) {
                            img.append(" target=\"_blank\"");
                        }
                        img.append(">");
                    }
                    img.append("<img src=\"" + pathWeb + "/" + promo.swbres_promImage.getName() + "_" + promo.getId() + "_" + imgfile + "\" " + imgWidth + " " + imgHeight + "/>");
                    if(hasLink) {
                        img.append("</a>");
                    }
                    img.append("</span>\n");
                    if(caption != null) {
                        img.append("<h6" + (captionStyle == null ? "" : " style=\"" + captionStyle + "\"") + "><span>" + caption + "</span></h6> \n");
                    }
                    img.append("</div>\n");
                    margin = "margin-right:" + (imgWidth + 20) + "px;";
                    out.append(img);
                } else if(imgPos == 4) {
                    img.append("<div style=\"text-align:center; float:left; margin:10px\">");
                    img.append("<span>");
                    if(hasLink) {
                        img.append("<a href=\"" + url + "\"");
                        if(textStyle != null || "0".equals(uline)) {
                            img.append(" style=\"" + (textStyle == null ? "" : textStyle + ";") + ("0".equals(uline) ? "text-decoration:none;" : "") + "\"");
                        }
                        if(target) {
                            img.append(" target=\"_blank\"");
                        }
                        img.append(">");
                    }
                    img.append("<img src=\"" + pathWeb + "/" + promo.swbres_promImage.getName() + "_" + promo.getId() + "_" +  imgfile + "\" " + imgWidth + " " + imgHeight + "/>");
                    if(hasLink) {
                        img.append("</a>");
                    }
                    img.append("</span>\n");
                    if(caption != null) {
                        img.append("<h6" + (captionStyle == null ? "" : " style=\"" + captionStyle + "\"") + "><span>" + caption + "</span></h6> \n");
                    }
                    img.append("</div>\n");
                    margin = "margin-left:" + (imgWidth + 20) + "px;";
                    out.append(img);
                } else if(imgPos == 2) {
                    img.append("<div style=\"text-align:center; float:right; margin:10px;\">");
                    img.append("<span>");
                    if(hasLink) {
                        img.append("<a href=\"" + url + "\"");
                        if(textStyle != null || "0".equals(uline)) {
                            img.append(" style=\"" + (textStyle == null ? "" : textStyle + ";") + ("0".equals(uline) ? "text-decoration:none;" : "") + "\"");
                        }
                        if(target) {
                            img.append(" target=\"_blank\"");
                        }
                        img.append(">");
                    }
                    img.append("<img src=\"" + pathWeb  + "/" + promo.swbres_promImage.getName() + "_" + promo.getId() + "_" + imgfile+"\" " + imgWidth + " " + imgHeight +"/>");
                    if(hasLink) {
                        img.append("</a>");
                    }
                    img.append("</span>\n");
                    if(caption != null) {
                        img.append("<h6" + (captionStyle == null ? "" : " style=\"" + captionStyle + "\"") + "><span>" + caption + "</span></h6> \n");
                    }
                    img.append("</div>\n");
                    out.append(img);
                } else if(imgPos == 1) {
                    img.append("<div style=\"text-align:center; float:left; margin:10px;\">");
                    img.append("<span>");
                    if(hasLink) {
                        img.append("<a href=\"" + url + "\"");
                        if(textStyle != null || "0".equals(uline)) {
                            img.append(" style=\"" + (textStyle == null ? "" : textStyle + ";") + ("0".equals(uline) ? "text-decoration:none;" : "") + "\"");
                        }
                        if(target) {
                            img.append(" target=\"_blank\"");
                        }
                        img.append(">");
                    }
                    img.append("<img src=\"" + pathWeb + "/" + promo.swbres_promImage.getName() + "_" + promo.getId() + "_" + imgfile+"\" " + imgWidth + " " + imgHeight +"/>");

                    if(hasLink) {
                        img.append("</a>");
                    }
                    img.append("</span>\n");
                    if(caption != null) {
                        img.append("<h6" + (captionStyle == null ? "" : " style=\"" + captionStyle + "\"") + "><span>" + caption + "</span></h6> \n");
                    }
                    img.append("</div>\n");
                    out.append(img);
                } else if(imgPos == 5) {
                    img.append("<div style=\"text-align:center\"> \n");
                    img.append("<span>");
                    if(hasLink) {
                        img.append("<a href=\"" + url + "\"");
                        if(textStyle != null || "0".equals(uline)) {
                            img.append(" style=\"" + (textStyle == null ? "" : textStyle + ";") + ("0".equals(uline) ? "text-decoration:none;" : "") + "\"");
                        }
                        if(target) {
                            img.append(" target=\"_blank\"");
                        }
                        img.append(">");
                    }
                    img.append("<img src=\"" + pathWeb + "/" + promo.swbres_promImage.getName() + "_" + promo.getId() + "_" + imgfile + "\" " + imgWidth + " " + imgHeight + "/>");
                    if(hasLink) {
                        img.append("</a>");
                    }
                    img.append("</span>\n");
                    if(caption != null) {
                        img.append("<h6" + (captionStyle == null ? "" : " style=\"" + captionStyle + "\"") + "><span>" + caption + "</span></h6> \n");
                    }
                    img.append("</div> \n");
                    out.append(img);
                } else {
                    imgPos = 6;
                    img.append("<div style=\"text-align:center\">");
                    img.append("<span>");
                    if(hasLink) {
                        img.append("<a href=\"" + url + "\"");
                        if(textStyle != null || "0".equals(uline)) {
                            img.append(" style=\"" + (textStyle == null ? "" : textStyle + ";") + ("0".equals(uline) ? "text-decoration:none;" : "") + "\"");
                        }
                        if(target) {
                            img.append(" target=\"_blank\"");
                        }
                        img.append(">");
                    }
                    img.append("<img src=\"" + pathWeb + "/" + promo.swbres_promImage.getName() + "_" + promo.getId() + "_" + imgfile + "\" " + imgWidth + " " + imgHeight + "/>");
                    if(hasLink) {
                        img.append("</a>");
                    }
                    img.append("</span>\n");
                    if(caption != null) {
                        img.append("<h6" + (captionStyle == null ? "" : " style=\"" + captionStyle + "\"") + "><span>" + caption + "</span></h6> \n");
                    }
                    img.append("</div>\n");
                }
            }

            //subtitle
            if(subtitle != null) {
                out.append("<h2" + (subtitleStyle == null ? "" : " style=\"" + subtitleStyle + "\"") + "><span>" + subtitle + "</span></h2>\n");
            }

            if(promo.getPromLinkText() == null) {
                //texto
                out.append("<p style=\"text-align:justify;" + margin + "\">");
                if(url != null) {
                    out.append("<a href=\"" + url + "\"");
                    if(textStyle != null || "0".equals(uline)) {
                        out.append(" style=\"" + (textStyle == null ? "" : textStyle + ";") + ("0".equals(uline) ? "text-decoration:none;" : "") + "\"");
                    }
                    if(target) {
                        out.append(" target=\"_blank\"");
                    }
                    out.append(">");
                    out.append(text);
                    out.append("</a>\n");
                } else {
                    out.append("<span " + (textStyle == null ? "" : "style=\"" + textStyle + "\"") + ">");
                    out.append(text);
                    out.append("</span>\n");
                }
                out.append("</p>\n");
            } else {
                out.append("<p style=\"text-align:justify;" + margin + "\">");
                out.append("<span " + (textStyle == null ? "" : "style=\"" + textStyle + "\"") + ">");
                out.append(text);
                out.append("</span>");
                out.append("</p>\n");
                //más...
                if(url != null) {
                    out.append("<ul style=\"list-style:none; margin:7px; padding:0px\"><li>");
                    out.append("<a href=\"" + url + "\"");
                    if(moreStyle != null || "0".equals(uline)) {
                        out.append(" style=\"" + (moreStyle == null ? "" : moreStyle + ";") + ("0".equals(uline) ? "text-decoration:none;" : "") + "\"");
                    }
                    if(target) {
                        out.append(" target=\"_blank\"");
                    }
                    out.append(">");
                    out.append(more);
                    out.append("</a>");
                    out.append("</li></ul>\n");
                }
            }

            if(imgfile != null && imgPos == 6) {
                out.append(img);
            }
            //marco
            out.append("</div>");
        }catch (Exception e) {
            log.error("Error while setting resource base: " + promo.getId() + "-" + promo.getResourceBase().getTitle(), e);
        }
        return out.toString();
    }

    private String render(SWBParamRequest paramRequest) throws SWBResourceException {
        StringBuilder out = new StringBuilder();
        SemPromo promo = (SemPromo) getSemanticObject().createGenericInstance();
        String pathWeb = SWBPortal.getWebWorkPath() + "/models/" + promo.getSemanticObject().getModel().getName() + "/" + promo.getSemanticClass().getClassGroupId() + "/" + getId();

        int width;
        try {
            width = promo.getPromBulletWidth();
        } catch(NumberFormatException nfe) {
            width = 0;
        }
        int height;
        try {
            height = promo.getPromBulletHeight();
        } catch(NumberFormatException nfe) {
            height = 0;
        }

        String title = promo.getPromTitle() == null ? "" : promo.getPromTitle();
        String subtitle = promo.getPromSubtitle();
        if(promo.getPromImage() == null) {
            throw new SWBResourceException(paramRequest.getLocaleString("msgErrResource"));
        }
        String imgfile = promo.getPromImage();
        String caption = promo.getPromFooter();
        String imgWidth = "";
        if(promo.getPromImgWidth() > 0){
            imgWidth = "width=\"" + promo.getPromImgWidth() + "\"";
        }

        String imgHeight = "";
        if(promo.getPromImgHeight() > 0){
            imgHeight = "height=\"" + promo.getPromImgHeight() + "\"";
        }
        String text = promo.getPromText() == null ? "" : promo.getPromText();
        String more = promo.getPromLinkText() == null ? "" : promo.getPromLinkText();
        String internalLink = null;
        if (promo.getPromInternalUrl() != null) {
            WebPage wp = paramRequest.getWebPage().getWebSite().getWebPage(promo.getPromInternalUrl());
            if (wp != null && !wp.isDeleted() && !wp.isHidden()) {
                String lang = paramRequest.getUser().getLanguage();
                internalLink = wp.getUrl(lang) == null ? (wp.getUrl() == null ? "" : wp.getUrl()) : wp.getUrl(lang);
            }
        }
        String url = promo.getPromLink() == null ? (internalLink == null ? "" : internalLink) : promo.getPromLink();
        String uline = promo.isPromUnderlineLink() ? "1" : "0";
        boolean target = promo.isPromOpenNewWindow();
        String cssClass = promo.getPromCssClass() == null ? "" : promo.getPromCssClass();

        int imgPos;
        try {
            String imgPo = promo.getPromImgPosition() == null ? "1" : promo.getPromImgPosition();
            imgPos = Integer.parseInt(imgPo);
        }catch(NumberFormatException nfe) {
            imgPos = 1;
        }

        try {
            //marco
            out.append("<div class=\"" + cssClass + "\">");

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
                boolean hasLink = promo.getPromLinkText() == null && url != null;
                if(imgPos == 3) {
                    img.append("<div style=\"text-align:center; float:right; margin:10px\">");
                    img.append("<span>");
                    if(hasLink) {
                        img.append("<a href=\"" + url + "\"");
                        if("0".equals(uline)) {
                            img.append(" style=\"text-decoration:none;\" ");
                        }
                        if(target) {
                            img.append(" target=\"_blank\"");
                        }
                        img.append(">");
                    }
                    img.append("<img src=\"" + pathWeb + "/" + promo.swbres_promImage.getName() + "_" + promo.getId() + "_" + imgfile + "\" " + imgWidth + " " + imgHeight + "/>");
                    if(hasLink) {
                        img.append("</a>");
                    }
                    img.append("</span>\n");
                    if(caption != null) {
                        img.append("<h6><span>" + caption + "</span></h6> \n");
                    }
                    img.append("</div>\n");
                    margin = "margin-right:" + (imgWidth + 20) + "px;";
                    out.append(img);
                } else if(imgPos == 4) {
                    img.append("<div style=\"text-align:center; float:left; margin:10px\">");
                    img.append("<span>");
                    if(hasLink) {
                        img.append("<a href=\"" + url + "\"");
                        if("0".equals(uline)) {
                            img.append(" style=\"text-decoration:none;\" ");
                        }
                        if(target) {
                            img.append(" target=\"_blank\"");
                        }
                        img.append(">");
                    }
                    img.append("<img src=\"" + pathWeb + "/" + promo.swbres_promImage.getName() + "_" + promo.getId() + "_" + imgfile + "\" " + imgWidth + " " + imgHeight + "/>");
                    if(hasLink) {
                        img.append("</a>");
                    }
                    img.append("</span>\n");
                    if(caption != null) {
                        img.append("<h6><span>" + caption + "</span></h6> \n");
                    }
                    img.append("</div>\n");
                    margin = "margin-left:" + (imgWidth + 20) + "px;";
                    out.append(img);
                } else if(imgPos == 2) {
                    img.append("<div style=\"text-align:center; float:right; margin:10px;\">");
                    img.append("<span>");
                    if(hasLink) {
                        img.append("<a href=\"" + url + "\"");
                        if("0".equals(uline)) {
                            img.append(" style=\"text-decoration:none;\" ");
                        }
                        if(target) {
                            img.append(" target=\"_blank\"");
                        }
                        img.append(">");
                    }
                    img.append("<img src=\"" + pathWeb + "/" + promo.swbres_promImage.getName() + "_" + promo.getId() + "_" + imgfile + "\" " + imgWidth + " " + imgHeight + "/>");
                    if(hasLink) {
                        img.append("</a>");
                    }
                    img.append("</span>\n");
                    if(caption != null) {
                        img.append("<h6><span>" + caption + "</span></h6> \n");
                    }
                    img.append("</div>\n");
                    out.append(img);
                } else if(imgPos == 1) {
                    img.append("<div style=\"text-align:center; float:left; margin:10px;\">");
                    img.append("<span>");
                    if(hasLink) {
                        img.append("<a href=\"" + url + "\"");
                        if("0".equals(uline)) {
                            img.append(" style=\"text-decoration:none;\" ");
                        }
                        if(target) {
                            img.append(" target=\"_blank\"");
                        }
                        img.append(">");
                    }
                    img.append("<img src=\"" + pathWeb + "/" + promo.swbres_promImage.getName() + "_" + promo.getId() + "_" + imgfile + "\" " + imgWidth + " " + imgHeight + "/>");
                    if(hasLink) {
                        img.append("</a>");
                    }
                    img.append("</span>\n");
                    if(caption != null) {
                        img.append("<h6><span>" + caption + "</span></h6> \n");
                    }
                    img.append("</div>\n");
                    out.append(img);
                } else if(imgPos == 5) {
                    img.append("<div style=\"text-align:center\"> \n");
                    img.append("<span>");
                    if(hasLink) {
                        img.append("<a href=\"" + url + "\"");
                        if("0".equals(uline)) {
                            img.append(" style=\"text-decoration:none;\" ");
                        }
                        if(target) {
                            img.append(" target=\"_blank\"");
                        }
                        img.append(">");
                    }
                    img.append("<img src=\"" + pathWeb + "/" + promo.swbres_promImage.getName() + "_" + promo.getId() + "_" + imgfile + "\" " + imgWidth + " "+ imgHeight + "/>");
                    if(hasLink) {
                        img.append("</a>");
                    }
                    img.append("</span>\n");
                    if(caption != null) {
                        img.append("<h6><span>" + caption + "</span></h6> \n");
                    }
                    img.append("</div> \n");
                    out.append(img);
                } else {
                    imgPos = 6;
                    img.append("<div style=\"text-align:center\">");
                    img.append("<span>");
                    if(hasLink) {
                        img.append("<a href=\"" + url + "\"");
                        if("0".equals(uline)) {
                            img.append(" style=\"text-decoration:none;\" ");
                        }
                        if(target) {
                            img.append(" target=\"_blank\"");
                        }
                        img.append(">");
                    }
                    img.append("<img src=\"" + pathWeb + "/" + promo.swbres_promImage.getName() + "_" + promo.getId() + "_" + imgfile + "\" " + imgWidth + " " + imgHeight + "/>");
                    if(hasLink) {
                        img.append("</a>");
                    }
                    img.append("</span>\n");
                    if(caption != null) {
                        img.append("<h6><span>" + caption + "</span></h6> \n");
                    }
                    img.append("</div>\n");
                }
            }

            //subtitle
            if(subtitle != null) {
                out.append("<h2><span>" + subtitle + "</span></h2>\n");
            }

            if(promo.getPromLinkText() == null) {
                //texto
                out.append("<p>");
                if(url != null) {
                    out.append("<a href=\"" + url + "\"");
                    if("0".equals(uline)) {
                        out.append(" style=\"text-decoration:none;\" ");
                    }
                    if(target) {
                        out.append(" target=\"_blank\"");
                    }
                    out.append(">");
                    out.append(text);
                    out.append("</a>\n");
                } else {
                    out.append("<span>");
                    out.append(text);
                    out.append("</span>\n");
                }
                out.append("</p>\n");
            } else {
                out.append("<p>");
                out.append("<span>");
                out.append(text);
                out.append("</span>");
                out.append("</p>\n");
                //más...
                if(url != null) {
                    out.append("<ul style=\"list-style:none; margin:7px; padding:0px\"><li>");
                    out.append("<a href=\"" + url + "\"");
                    if("0".equals(uline)) {
                        out.append(" style=\"text-decoration:none;\" ");
                    }
                    if(target) {
                        out.append(" target=\"_blank\"");
                    }
                    out.append(">");
                    out.append(more);
                    out.append("</a>");
                    out.append("</li></ul>\n");
                }
            }

            if(imgfile != null && imgPos == 6) {
                out.append(img);
            }
            //marco
            out.append("</div>");
        } catch (Exception e) {
            log.error("Error while setting resource base: " + promo.getId() + "-" + promo.getResourceBase().getTitle(), e);
        }
        return out.toString();
    }
}
