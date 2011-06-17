/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.servlet.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;

/**
 *
 * @author martha.jimenez
 */
public class TreeSelectFormElement implements InternalServlet {

    private static Logger log = SWBUtils.getLogger(TreeSelectFormElement.class);

    protected static final String pathImages = SWBPlatform.getContextPath() + "/swbadmin/icons/";

    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException, ServletException {
        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        StringBuffer ret = new StringBuffer(256);
        String style= "";
        WebPage tpid = null;
        ArrayList listData = new ArrayList();
        StringBuilder params = new StringBuilder("");
        String dataProp = request.getParameter("dataProp");
        String li = request.getParameter("li") != null ? (request.getParameter("li").equals("null") ? "" : request.getParameter("li")) : "";
        String lang = request.getParameter("lang") != null ? request.getParameter("lang") : "es";

        if(request.getParameter("site") != null) {
            WebSite site = WebSite.ClassMgr.getWebSite(request.getParameter("site"));
            WebPage home = site.getHomePage();
            String url = SWBPlatform.getContextPath() + "/treeSelect/" + home.getSemanticObject().getModel().getModelObject().getId();

            if(li != null && li.length() > 0 && tpid == null) { //Si la propiedad tiene un valor almacenado, obtener los padres para abrir las carpetas
                tpid = site.getWebPage(li);
                WebPage lip = site.getWebPage(li);
                WebPage parent = lip;
                if(lip != null) {
                    boolean isparent = true;
                    do {
                        parent = parent.getParent();
                        if(parent == null) {
                            isparent = false;
                        } else {
                            listData.add(parent);
                        }
                    } while(isparent) ;
                }
                tpid = null;
             }

            if(request.getParameter("reptp") != null) { //Obteniendo página web seleccionada
                tpid = site.getWebPage((String)request.getParameter("reptp"));
                ret.append("<input type=\"hidden\" name=\"section" + "_" + dataProp + "\" id=\"section" + "_" + dataProp + "\" value=\"" + tpid.getId() + "\" />");
            }

            boolean toggleopen = Boolean.parseBoolean(request.getParameter(home.getId()) == null ? "false" : ((String)request.getParameter(home.getId())).equals("1") ? "true" : "false");
            if(tpid != null){
                listData = new ArrayList();
            }
            ret.append("<li>");

            if(site.isActive()) { //Icono del sitio Activo/Inactivo
                ret.append("<img src=\"" + pathImages + "/icon_siteb.gif\" alt=\"seleccionar sitio\" />");
            } else {
                ret.append("<img src=\"" + pathImages + "/icon_sitein.png\" alt=\"seleccionar sitio\" />");
            }

            try {  //Titulo del Sitio
                ret.append("<span style=\"padding-left:5px\">" + SWBUtils.TEXT.encode(site.getDisplayTitle(lang), SWBUtils.TEXT.CHARSET_UTF8) + "</span>");
            } catch(Exception e) {
                log.error("Can´t convert text to code UTF8" + e);
            }
            ret.append("<ul class=\"treeres\">");
            ret.append("<li>");

            if(tpid != null && tpid.getId().equalsIgnoreCase(home.getId())) { //Si es la página actual, obteniendo las imagenes
                style = " style=\"color:#FF6600; font-weight:bold; background-color:#000\"";
                if(toggleopen) { //Si esta abierta
                    params.append("&" + home.getId() + "=0");
                    ret.append("<img src=\"" + pathImages + "/plus.gif\" alt=\"abrir nodo\" onclick=\"getHtml('" + url + "?site=" + home.getWebSiteId() + "&reptp=" + home.getId() + params + "&dataProp=" + dataProp + "&li=" + li + "&lang=" + lang + "','tree_'+'" + site.getId() + "_" + dataProp + "')\" />");
                    toggleopen = false;
                } else { //Si la página esta cerrada
                    params.append("&" + home.getId() + "=1");
                    ret.append("<img src=\"" + pathImages + "/minus.gif\" alt=\"cerrar nodo\" onclick=\"getHtml('" + url + "?site=" + home.getWebSiteId() + "&reptp=" + home.getId() + params + "&dataProp=" + dataProp + "&li=" + li + "&lang=" + lang + "','tree_'+'" + site.getId() + "_" + dataProp + "')\" />");
                    toggleopen = true;
                }
            } else { //Si la página no es la actual
                if(listData.isEmpty()) {
                    style = "";
                    if(toggleopen) { //Si la página esta abierta
                        params.append("&" + home.getId() + "=1");
                        ret.append("<img src=\"" + pathImages + "/minus.gif\" alt=\"cerrar nodo\"  onclick=\"getHtml('" + url + "?site=" + home.getWebSiteId() + "&reptp=" + home.getId() + params + "&dataProp=" + dataProp + "&li=" + li + "&lang=" + lang + "','tree_'+'" + site.getId() + "_" + dataProp + "')\" />");
                    } else { //Si la página no esta abierta
                        params.append("&" + home.getId() + "=0");
                        ret.append("<img src=\"" + pathImages + "/plus.gif\" alt=\"abrir nodo\"  onclick=\"getHtml('" + url + "?site=" + home.getWebSiteId() + "&reptp=" + home.getId() + params + "&dataProp=" + dataProp + "&li=" + li + "&lang=" + lang + "','tree_'+'" + site.getId() + "_" + dataProp + "')\" />");
                    }
                } else {
                    params.append("&" + home.getId() + "=1");
                    ret.append("<img src=\"" + pathImages + "/minus.gif\" alt=\"cerrar nodo\"  onclick=\"getHtml('" + url + "?site=" + home.getWebSiteId() + "&reptp=" + home.getId() + params + "&dataProp=" + dataProp + "&li=" + li + "&lang=" + lang + "','tree_'+'" + site.getId() + "_" + dataProp + "')\" />");
                }
            }
            if((li != null && li.length() > 0) && li.equalsIgnoreCase(home.getId())) {
                style = " style=\"font-weight:bold; background-color:#6699FF\"";
            }
            //Obteniendo las ligas
            ret.append("<a class=\"treeres\" onclick=\"getHtml('" + url + "?site=" + home.getWebSiteId() + "&reptp=" + home.getId() + params + "&dataProp=" + dataProp + "&li=" + li + "&lang=" + lang + "','tree_'+'" + site.getId() + "_" + dataProp + "')\" " + style + ">");
            if(home.isActive()) {
                ret.append("<img src=\"" + pathImages + "/icon_homeac.png\" alt=\"seleccionar inicio\" />");
            } else {
                ret.append("<img src=\"" + pathImages + "/icon_homein.png\" alt=\"seleccionar inicio\" />");
            }

            try { //Agregar el título del nodo actual
                ret.append("<span style=\"padding-left:5px\">" + SWBUtils.TEXT.encode(home.getDisplayTitle(lang), SWBUtils.TEXT.CHARSET_UTF8) + "</span>");
            } catch(Exception e) {
                log.error("Can't save the value's property in TreeSelect, " + e);
            }
            ret.append("</a>");

            if(home != null && toggleopen) { //Pintando los hijos si esta abierta la carpeta
                ret.append(addChild(listData, request, site, home, tpid, url, params, lang, li, dataProp));
            }

            if(!listData.isEmpty() && tpid == null) {
                ret.append(addChild(listData, request, site, home, tpid, url, params, lang, li, dataProp));
            }
            ret.append("</li>");
            ret.append("</ul>");
            ret.append("</li>");
            ret.append("</ul>");
        }
        response.getWriter().print(ret);
    }

    public void init(ServletContext config) throws ServletException {
        log.event("Initializing InternalServlet TreeSelectFormElement...");
    }

    protected String addChild(ArrayList list, HttpServletRequest request, WebSite tmit, WebPage pageroot, WebPage tpid, String url, StringBuilder params, String lang, String dataCurrent, String dataProp) {
        String style;
        boolean toggleopen;
        StringBuilder html = new StringBuilder("<ul class=\"treeres ch\">");

        Iterator<WebPage> childs=pageroot.listChilds();
        while(childs.hasNext()) {
            WebPage webpage = childs.next();
            if(webpage.getId() != null) {
                if(!list.isEmpty()) {
                    if(list.contains(webpage)) {
                        toggleopen = true;
                    } else {
                        toggleopen = false;
                    }
                } else {
                    toggleopen = Boolean.parseBoolean(request.getParameter(webpage.getId()) == null ? "false" : ((String)request.getParameter(webpage.getId())).equals("1") ? "true" : "false");
                }
                if(webpage.listChilds().hasNext()) {
                    html.append("<li>");
                    if(tpid != null && tpid.getId().equalsIgnoreCase(webpage.getId())) {
                        style = " style=\"color:#FF6600; font-weight:bold; background-color:#000\"";
                        if(toggleopen) {
                            params.append("&" + webpage.getId() + "=0");
                            html.append("<img src=\"" + pathImages + "/plus.gif\" alt=\"abrir nodo\" onclick=\"getHtml('" + url + "?site=" + webpage.getWebSiteId() + "&reptp=" + webpage.getId() + params + "&dataProp=" + dataProp + "&li=" + dataCurrent + "&lang=" + lang + "','tree_'+'" + tmit.getId()+ "_" + dataProp + "')\" />");
                            toggleopen = false;
                        } else {
                            params.append("&" + webpage.getId() + "=1");
                            html.append("<img src=\"" + pathImages + "/minus.gif\" alt=\"cerrar nodo\" onclick=\"getHtml('" + url + "?site=" + webpage.getWebSiteId() + "&reptp=" + webpage.getId() + params + "&dataProp=" + dataProp + "&li=" + dataCurrent + "&lang=" + lang + "','tree_'+'" + tmit.getId() + "_" + dataProp + "')\" />");
                            toggleopen = true;
                        }
                    } else {
                        style = "";
                        if(toggleopen) {
                            params.append("&" + webpage.getId() + "=1");
                            html.append("<img src=\"" + pathImages + "/minus.gif\" alt=\"cerrar nodo\" onclick=\"getHtml('" + url + "?site=" + webpage.getWebSiteId() + "&reptp=" + webpage.getId() + params + "&dataProp=" + dataProp + "&li=" + dataCurrent + "&lang=" + lang + "','tree_'+'" + tmit.getId() + "_" + dataProp + "')\" />");
                        } else {
                            params.append("&" + webpage.getId() + "=0");
                            html.append("<img src=\"" + pathImages + "/plus.gif\" alt=\"abrir nodo\" onclick=\"getHtml('" + url + "?site=" + webpage.getWebSiteId() + "&reptp=" + webpage.getId() + params + "&dataProp=" + dataProp + "&li=" + dataCurrent + "&lang=" + lang + "','tree_'+'" + tmit.getId() + "_" + dataProp + "')\" />");
                        }
                    }
                    if((dataCurrent != null && dataCurrent.length() > 0) && dataCurrent.equalsIgnoreCase(webpage.getId())) {
                        style = " style=\"font-weight:bold; background-color:#6699FF\"";
                    }
                    html.append("<a class=\"treeres\" onclick=\"getHtml('" + url + "?site=" + webpage.getWebSiteId() + "&reptp=" + webpage.getId() + params + "&dataProp=" + dataProp + "&li=" + dataCurrent + "&lang=" + lang + "','tree_'+'" + tmit.getId() + "_" + dataProp + "')\" " + style + ">");
                    if(webpage.isActive()) {
                        html.append("<img src=\"" + pathImages + "/icon_secac.png\" alt=\"seleccionar sección\" />");
                    } else {
                        html.append("<img src=\"" + pathImages + "/icon_secin.png\" alt=\"seleccionar sección\" />");
                    }
                    html.append("<span style=\"padding-left:5px\">" + webpage.getDisplayTitle(lang) + "</span>");
                    html.append("</a>");
                    if(toggleopen) {
                        html.append(addChild(list, request, tmit, webpage, tpid, url, params, lang, dataCurrent, dataProp));
                    }
                    html.append("</li>");
                }else {
                    if(tpid != null && tpid.getId().equalsIgnoreCase(webpage.getId())) {
                        style = " style=\"color:#FF6600; font-weight:bold; background-color:#000\"";
                    } else {
                        style = "";
                    }
                    if((dataCurrent != null && dataCurrent.length() > 0) && dataCurrent.equalsIgnoreCase(webpage.getId())) {
                        style = " style=\"font-weight:bold; background-color:#6699FF\"";
                    }
                    html.append("<li>");
                    html.append("<img src=\"" + pathImages + "/trans.gif\" />");
                    html.append("<a class=\"treeres\" onclick=\"getHtml('" + url + "?site=" + webpage.getWebSiteId() + "&reptp=" + webpage.getId() + params + "&dataProp=" + dataProp + "&li=" + dataCurrent + "&lang=" + lang + "','tree_'+'" + tmit.getId() + "_" + dataProp + "')\" " + style + ">");
                    if(webpage.isActive()) {
                        html.append("<img src=\"" + pathImages + "/icon_secac.png\" alt=\"seleccionar sección\" />");
                    } else {
                        html.append("<img src=\"" + pathImages + "/icon_secin.png\" alt=\"seleccionar sección\" />");
                    }
                    html.append("<span style=\"padding-left:5px\">" + webpage.getDisplayTitle(lang) + "</span>");
                    html.append("</a>");
                    html.append("</li>");
                }
            }
        }
        html.append("</ul>");
        return html.toString();
    }


}
