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
import java.util.Iterator;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.*;

public class SemBanner extends org.semanticwb.portal.resources.sem.base.SemBannerBase 
{
    private static Logger log = SWBUtils.getLogger(SemBanner.class);

    public SemBanner()
    {
    }

   /**
   * Constructs a SemBanner with a SemanticObject
   * @param base The SemanticObject with the properties for the SemBanner
   */
    public SemBanner(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        SemBanner banner= (SemBanner) getSemanticObject().createGenericInstance();
        PrintWriter out = response.getWriter();
        String lang=paramRequest.getUser().getLanguage();
        String pathWeb = SWBPortal.getWebWorkPath() + "/models/" + banner.getSemanticObject().getModel().getName() + "/" + banner.getSemanticClass().getClassGroupId() + "/" + getId();

        try {
            String code =banner.getBanrCode();
            if(code==null) {
                String img = banner.getBanrImage() == null ? "" : banner.getBanrImage();
                String longdesc = banner.getBanrLongDescr();
                String url = banner.getBanrExternalUrl();
                if(url == null) {
                    if(banner.getBanrInternalUrl() != null) {
                        WebPage wpInternalUrl = paramRequest.getWebPage().getWebSite().getWebPage(banner.getBanrInternalUrl());
                        if(wpInternalUrl != null && !wpInternalUrl.isDeleted() && !wpInternalUrl.isHidden()) {
                            url = wpInternalUrl.getUrl(lang) == null ? (wpInternalUrl.getUrl() == null ? "" : wpInternalUrl.getUrl()) : wpInternalUrl.getUrl(lang);
                        }
                    }
                }

                String width = "";
                if(banner.getBanrImgWidth() > 0) {
                    width = banner.getBanrImgWidth() + "";
                }
                try {
                    Integer.parseInt(width.replaceAll("\\D", ""));
                } catch(Exception e) {
                    width = null;
                }
                String height = "";
                if(banner.getBanrImgHeight() > 0) {
                    height = banner.getBanrImgHeight() + "";
                }
                try {
                    Integer.parseInt(height.replaceAll("\\D", ""));
                } catch(Exception e) {
                    height = null;
                }

                String wburl = null;
                if(url != null && url.toLowerCase().startsWith("mailto:")) {
                    wburl = url.replaceAll("\"", "&#34;");
                } else if (url != null) {
                    wburl = paramRequest.getActionUrl().toString();
                }
                if(img.endsWith(".swf")) {
                    out.print("<object ");
                    out.print(" classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" ");
                    out.print(" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab\"");
                    if(width != null) {
                        out.print(" width=\"" + width + "\"");
                    }
                    if(height != null) {
                        out.print(" height=\"" + height + "\"");
                    }
                    out.println(">");
                    out.println("<param name=\"movie\" value=\"" + pathWeb + "/" + banner.swbres_banrImage.getName() + "_" + banner.getId() + "_" + img + "\"/>");
                    if(wburl != null) {
                        out.println("<param name=\"flashvars\" value=\"liga=" + wburl + "\"/>\n");
                    }
                    out.println("<param name=\"quality\" value=\"high\"/> <param name=\"wmode\" value=\"transparent\"/> <param name=\"play\" value=\"true\"/> <param name=\"loop\" value=\"true\"/>");

                    out.print("<embed pluginspage=\"http://get.adobe.com/flashplayer/\" type=\"application/x-shockwave-flash\" quality=\"high\" wmode=\"transparent\" play=\"true\" loop=\"true\" ");
                    out.print(" src=\"" + pathWeb + "/" + banner.swbres_banrImage.getName() + "_" + banner.getId() + "_" + img + "\"");
                    if(wburl != null) {
                        out.print(" flashvars=\"liga=" + wburl + "\"");
                    }
                    if(width != null) {
                        out.print(" width=\"" + width + "\"");
                    }
                    if(height != null) {
                        out.print(" height=\"" + height + "\"");
                    }
                    out.println(">");
                    out.println("</embed></object>");
                }else {
                    if(url != null) {
                        out.print("<a class=\"swb-banner\"");
                        out.print(" href=\"" + url + "\"");
                        if(banner.isBanrOpenNewWindow()) {
                            out.print(" target=\"_blank\"");
                        } else {
                            out.print(" onclick=\"window.location.href='" + url + "';return true;\"");//paramRequest.getActionUrl()
                        }
                        String title = banner.getTitle(request, paramRequest) == null ? "" : banner.getTitle(request, paramRequest);
                        out.println(" title=\"" + title + "\">");
                    }
                    out.print("<img src=\"");
                    out.print(pathWeb + "/" + banner.swbres_banrImage.getName() + "_" + banner.getId() + "_" + img + "\"");
                    String alt = banner.getBanrAlterText() == null ? paramRequest.getLocaleString("goto") + " " + (banner.getResourceBase().getDisplayTitle(lang) == null ? "" : banner.getResourceBase().getDisplayTitle(lang)) : banner.getBanrAlterText();
                    out.print(" alt=\"" + alt + "\"");

                    if(width != null) {
                        out.print(" width=\"" + width + "\"");
                    }
                    if(height != null) {
                        out.print(" height=\"" + height + "\"");
                    }
                    if( longdesc != null ) {
                        //out.print(" longdesc=\"" + paramRequest.getRenderUrl().setMode(paramRequest.Mode_HELP).toString() + "\"");
                    }
                    String action = banner.getBanrAction();
                    if(action != null) {
                        action = action.replaceAll("\"", "'");
                        out.print(" onclick=\"" + action + "\"");
                    }
                    out.println("/>");

                    if(url != null) {
                        out.print("</a>");
                    }
                }

                if(longdesc != null) {
                    //out.println("<a class=\"swb-banner-hlp\" href=\"" + paramRequest.getRenderUrl().setMode(paramRequest.Mode_HELP).toString() + "\">" + paramRequest.getLocaleString("longDesc") + "</a>");
                }
            } else {
                String img = banner.getBanrImage() == null ? "" : banner.getBanrImage();
                //publicidad externa
                code = SWBUtils.TEXT.replaceAll(code, "{title}", banner.getTitle(request, paramRequest));
                code = SWBUtils.TEXT.replaceAll(code, "{description}", getResourceBase().getDisplayDescription(lang));
                code = SWBUtils.TEXT.replaceAll(code, "{image}", pathWeb + "/" + banner.swbres_banrImage.getName() + "_" + banner.getId() + "_" + img);
                out.println(code);
            }
        }catch (Exception e) {
            log.error("Error in resource Banner while bringing HTML", e);
        }
        out.flush();
        out.close();
    }

    @Override
    public void doHelp(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        SemBanner banner= (SemBanner) getSemanticObject().createGenericInstance();
        PrintWriter out = response.getWriter();

        out.println("<div class=\"swb-banner-ld\">");
        out.println(banner.getBanrLongDescr() == null ? "" : banner.getBanrLongDescr());
        out.println("  <hr size=\"1\" noshade=\"noshade\"/>");
        out.println("  <a href=\"" + paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW).toString() + "\" rel=\"" + paramRequest.getLocaleString("back") + "\" title=\"" + paramRequest.getLocaleString("back") + "\">" + paramRequest.getLocaleString("back") + "</a>");
        out.println("</div>");
        out.flush();
        out.close();
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        SemBanner base= (SemBanner) getSemanticObject().createGenericInstance();
        base.getResourceBase().addHit(request, response.getUser(), response.getWebPage());
        //        String url = base.getAttribute("url");
        //        if( url!=null ) {
        //            url = replaceTags(url, request, response.getUser(), response.getWebPage());
        //            response.sendRedirect(url);
        //        }
    }
    public String replaceTags(String str, HttpServletRequest request, User user,WebPage webpage) {
        if(str == null || str.trim().length() == 0) {
            return null;
        }
        str=str.trim();

        Iterator it=SWBUtils.TEXT.findInterStr(str, "{request.getParameter(\"", "\")}");
        while(it.hasNext()) {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{request.getParameter(\"" + s + "\")}", request.getParameter(replaceTags(s,request,user,webpage)));
        }

        it=SWBUtils.TEXT.findInterStr(str, "{session.getAttribute(\"", "\")}");
        while(it.hasNext()) {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{session.getAttribute(\"" + s + "\")}", (String)request.getSession().getAttribute(replaceTags(s,request,user,webpage)));
        }

        /*it=SWBUtils.TEXT.findInterStr(str, "{template.getArgument(\"", "\")}");
        while(it.hasNext())
        {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{template.getArgument(\""+s+"\")}", (String)response.getArgument(replaceTags(s,request,user,webpage)));
        }*/

        it=SWBUtils.TEXT.findInterStr(str, "{getEnv(\"", "\")}");
        while(it.hasNext()) {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{getEnv(\"" + s + "\")}", SWBPlatform.getEnv(replaceTags(s,request,user,webpage)));
        }

        str=SWBUtils.TEXT.replaceAll(str, "{user.login}", user.getLogin());
        str=SWBUtils.TEXT.replaceAll(str, "{user.email}", user.getEmail());
        str=SWBUtils.TEXT.replaceAll(str, "{user.language}", user.getLanguage());
        str=SWBUtils.TEXT.replaceAll(str, "{webpath}", SWBPortal.getContextPath());
        str=SWBUtils.TEXT.replaceAll(str, "{distpath}", SWBPortal.getDistributorPath());
        str=SWBUtils.TEXT.replaceAll(str, "{webworkpath}", SWBPortal.getWebWorkPath());
        str=SWBUtils.TEXT.replaceAll(str, "{workpath}", SWBPortal.getWorkPath());
        str=SWBUtils.TEXT.replaceAll(str, "{websiteid}", webpage.getWebSiteId());
        return str;
    }


}
