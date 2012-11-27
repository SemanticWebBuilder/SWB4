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


        if(request.getParameter("edit") != null && request.getParameter("edit").equals("true") && request.getParameter("site") != null && li.length() > 1){
            /** Muestra la página web que esta almacenada dentro del árbol **/
            ret.append("<ul class=\"treeres\">");
            WebSite site = WebSite.ClassMgr.getWebSite(request.getParameter("site"));
            WebPage home = site.getHomePage();
            WebPage lip = null;
            String url = SWBPlatform.getContextPath() + "/treeSelect/" + home.getSemanticObject().getModel().getModelObject().getId();
            if(li != null && li.length() > 0) {
                lip = site.getWebPage(li);
                WebPage parent = lip;
                if(lip != null) {
                    boolean isparent = true;
                    do {
                        parent = parent.getParent();
                        if(parent == null){
                            isparent = false;
                        } else {
                            listData.add(parent);
                        }
                    } while(isparent) ;
                }
            }
            if(lip != null && lip.equals(home)) {
                ret.append("<li >");//style=\"border-color:#6699FF; max-width:400px; min-width:200px;border-style:solid;border-width:2px;margin: 3px;\"
                params.append("&" + home.getId() + "=0");
                style = " style=\"font-weight:bold; background-color:#BBD4D9; color:#3368B4\";";
                ret.append("<img src=\"" + pathImages + "/plus.gif\" alt=\"abrir nodo\"  onclick=\"getHtml('" + url + "?site=" + home.getWebSiteId() + "&reptp=" + home.getId() + params + "&dataProp=" + dataProp + "&li=" + li + "&lang=" + lang + "','tree_'+'" + site.getId() + "_" + dataProp + "')\" />");
                ret.append("<a class=\"treeres\" onclick=\"getHtml('" + url + "?site=" + home.getWebSiteId() + "&reptp=" + home.getId() + params + "&dataProp=" + dataProp + "&li=" + li + "&lang=" + lang + "','tree_'+'" + site.getId() + "_" + dataProp + "')\" " + style + ">");
                if(home.isActive()) { 
                    ret.append("<img src=\"" + pathImages + "/icon_homeac.png\" alt=\"seleccionar inicio\" />");
                } else {
                    ret.append("<img src=\"" + pathImages + "/icon_homein.png\" alt=\"seleccionar inicio\" />");
                }

                try { 
                    ret.append("<span style=\"padding-left:5px\">" + SWBUtils.TEXT.encode(home.getDisplayTitle(lang), SWBUtils.TEXT.CHARSET_UTF8) + "</span>");
                } catch(Exception e) {
                    log.error("Can't save the value's property in TreeSelect, " + e);
                }
                ret.append("</a>");
                ret.append("</li>");

            } else if (!listData.isEmpty()) {
                ret.append("<li>");
                params.append("&" + home.getId() + "=1");
                ret.append("<img src=\"" + pathImages + "/minus.gif\" alt=\"cerrar nodo\"  onclick=\"getHtml('" + url + "?site=" + home.getWebSiteId() + "&reptp=" + home.getId() + params + "&dataProp=" + dataProp + "&li=" + li + "&lang=" + lang + "','tree_'+'" + site.getId() + "_" + dataProp + "')\" />");
                ret.append("<a class=\"treeres\" onclick=\"getHtml('" + url + "?site=" + home.getWebSiteId() + "&reptp=" + home.getId() + params + "&dataProp=" + dataProp + "&li=" + li + "&lang=" + lang + "','tree_'+'" + site.getId() + "_" + dataProp + "')\" " + style + ">");
                if(home.isActive()) { 
                    ret.append("<img src=\"" + pathImages + "/icon_homeac.png\" alt=\"seleccionar inicio\" />");
                } else {
                    ret.append("<img src=\"" + pathImages + "/icon_homein.png\" alt=\"seleccionar inicio\" />");
                }

                try { 
                    ret.append("<span style=\"padding-left:5px\">" + SWBUtils.TEXT.encode(home.getDisplayTitle(lang), SWBUtils.TEXT.CHARSET_UTF8) + "</span>");
                } catch(Exception e) {
                    log.error("Can't save the value's property in TreeSelect, " + e);
                }
                ret.append("</a>");

                ret.append(addChild(listData, request, site, home,tpid, url , params, lang, li, dataProp));
                /*Este debe ser cambiado para ver si utilizas solo un solo metodo ret.append(addChild(listData, request, site, home, url , params, lang, li, dataProp));*/
                ret.append("</li>");
            }
            ret.append("</ul>");

        } else if(request.getParameter("site") != null) {
            ret.append("<ul class=\"treeres\">");
            
            WebSite site = WebSite.ClassMgr.getWebSite(request.getParameter("site"));
            WebPage home = site.getHomePage();
            String url = SWBPlatform.getContextPath() + "/treeSelect/" + home.getSemanticObject().getModel().getModelObject().getId();

            if(li != null && li.length() > 0 && tpid == null) { 
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

            if((li != null && li.length() > 0) && li.equalsIgnoreCase(home.getId())) {
                ret.append("<li>");
            } else {
                ret.append("<li>");
            }
            if(request.getParameter("reptp") != null) { 
                tpid = site.getWebPage((String)request.getParameter("reptp"));
                ret.append("<input type=\"hidden\" name=\"section" + "_" + dataProp + "\" id=\"section" + "_" + dataProp + "\" value=\"" + tpid.getId() + "\" />");
            }
            boolean toggleopen = Boolean.parseBoolean(request.getParameter(home.getId()) == null ? "false" : ((String)request.getParameter(home.getId())).equals("1") ? "true" : "false");
            if(tpid != null){
                listData = new ArrayList();
            }

            if(tpid != null && tpid.getId().equalsIgnoreCase(home.getId())) { //Selecciono la página web home
                style = " style=\"color:#336699; font-weight:bold; background-color:#b3b3b3\"";
                if(toggleopen) { //Si esta abierta
                    params.append("&" + home.getId() + "=0");
                    ret.append("<img src=\"" + pathImages + "/plus.gif\" alt=\"abrir nodo\" onclick=\"getHtml('" + url + "?site=" + home.getWebSiteId() + "&reptp=" + home.getId() + params + "&dataProp=" + dataProp + "&li=" + li + "&lang=" + lang + "','tree_'+'" + site.getId() + "_" + dataProp + "')\" />");
                    toggleopen = false;
                } else { //Si la página esta cerrada
                    params.append("&" + home.getId() + "=1");
                    ret.append("<img src=\"" + pathImages + "/minus.gif\" alt=\"cerrar nodo\" onclick=\"getHtml('" + url + "?site=" + home.getWebSiteId() + "&reptp=" + home.getId() + params + "&dataProp=" + dataProp + "&li=" + li + "&lang=" + lang + "','tree_'+'" + site.getId() + "_" + dataProp + "')\" />");
                    toggleopen = true;
                }
            } else { //Se selecciono cualquier hijo de home
                if(listData.isEmpty()) { //Si el elemento tiene un valor almacenado
                    style = "";
                    if(toggleopen) {
                        params.append("&" + home.getId() + "=1");
                        ret.append("<img src=\"" + pathImages + "/minus.gif\" alt=\"cerrar nodo\"  onclick=\"getHtml('" + url + "?site=" + home.getWebSiteId() + "&reptp=" + home.getId() + params + "&dataProp=" + dataProp + "&li=" + li + "&lang=" + lang + "','tree_'+'" + site.getId() + "_" + dataProp + "')\" />");
                    } else { 
                        params.append("&" + home.getId() + "=0");
                        ret.append("<img src=\"" + pathImages + "/plus.gif\" alt=\"abrir nodo\"  onclick=\"getHtml('" + url + "?site=" + home.getWebSiteId() + "&reptp=" + home.getId() + params + "&dataProp=" + dataProp + "&li=" + li + "&lang=" + lang + "','tree_'+'" + site.getId() + "_" + dataProp + "')\" />");
                    }
                } else {
                    params.append("&" + home.getId() + "=1");
                    ret.append("<img src=\"" + pathImages + "/minus.gif\" alt=\"cerrar nodo\"  onclick=\"getHtml('" + url + "?site=" + home.getWebSiteId() + "&reptp=" + home.getId() + params + "&dataProp=" + dataProp + "&li=" + li + "&lang=" + lang + "','tree_'+'" + site.getId() + "_" + dataProp + "')\" />");
                }
            }
            if((li != null && li.length() > 0) && li.equalsIgnoreCase(home.getId())) {
                style = " style=\"font-weight:bold; background-color:#BBD4D9; color:#3368B4\";";
            }

            ret.append("<a class=\"treeres\" onclick=\"getHtml('" + url + "?site=" + home.getWebSiteId() + "&reptp=" + home.getId() + params + "&dataProp=" + dataProp + "&li=" + li + "&lang=" + lang + "','tree_'+'" + site.getId() + "_" + dataProp + "')\" " + style + ">");
            if(home.isActive()) {
                ret.append("<img src=\"" + pathImages + "/icon_homeac.png\" alt=\"seleccionar inicio\" />");
            } else {
                ret.append("<img src=\"" + pathImages + "/icon_homein.png\" alt=\"seleccionar inicio\" />");
            }

            try { 
                ret.append("<span style=\"padding-left:5px\">" + SWBUtils.TEXT.encode(home.getDisplayTitle(lang), SWBUtils.TEXT.CHARSET_UTF8) + "</span>");
            } catch(Exception e) {
                log.error("Can't save the value's property in TreeSelect, " + e);
            }
            ret.append("</a>");

            if(home != null && toggleopen) { 
                ret.append(addChild(listData, request, site, home, tpid, url, params, lang, li, dataProp));
            }

            if(!listData.isEmpty() && tpid == null) {
                ret.append(addChild(listData, request, site, home, tpid, url, params, lang, li, dataProp));
            }

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
            if(webpage.getId() != null && !webpage.isDeleted()) {
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
                    
                    if(tpid != null && tpid.getId().equalsIgnoreCase(webpage.getId())) {
                        if((dataCurrent != null && dataCurrent.length() > 0) && dataCurrent.equalsIgnoreCase(webpage.getId())) {
                            html.append("<li>");
                            style = " style=\"font-weight:bold; background-color:#BBD4D9; color:#3368B4\";";
                        } else {
                            html.append("<li>");
                            style = " style=\"color:#336699; font-weight:bold; background-color:#b3b3b3\"";
                        }
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
                        if((dataCurrent != null && dataCurrent.length() > 0) && dataCurrent.equalsIgnoreCase(webpage.getId())) {
                            html.append("<li>");
                            style = " style=\"font-weight:bold; background-color:#BBD4D9; color:#3368B4\";";
                        } else {                        
                            html.append("<li>");
                            style = "";//style = " style=\"color:#336699; font-weight:bold; background-color:#b3b3b3\"";
                        }
                        /*html.append("<li>");
                        style = "";*/
                        if(toggleopen) {
                            params.append("&" + webpage.getId() + "=1");
                            html.append("<img src=\"" + pathImages + "/minus.gif\" alt=\"cerrar nodo\" onclick=\"getHtml('" + url + "?site=" + webpage.getWebSiteId() + "&reptp=" + webpage.getId() + params + "&dataProp=" + dataProp + "&li=" + dataCurrent + "&lang=" + lang + "','tree_'+'" + tmit.getId() + "_" + dataProp + "')\" />");
                        } else {
                            params.append("&" + webpage.getId() + "=0");
                            html.append("<img src=\"" + pathImages + "/plus.gif\" alt=\"abrir nodo\" onclick=\"getHtml('" + url + "?site=" + webpage.getWebSiteId() + "&reptp=" + webpage.getId() + params + "&dataProp=" + dataProp + "&li=" + dataCurrent + "&lang=" + lang + "','tree_'+'" + tmit.getId() + "_" + dataProp + "')\" />");
                        }
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
                } else {
                    if(tpid != null && tpid.getId().equalsIgnoreCase(webpage.getId())) {
                        style = " style=\"color:#336699; font-weight:bold; background-color:#b3b3b3\"";
                    } else {
                        style = "";
                    }
                    if((dataCurrent != null && dataCurrent.length() > 0) && dataCurrent.equalsIgnoreCase(webpage.getId())) {
                        style = " style=\"font-weight:bold; background-color:#BBD4D9; color:#3368B4\";";
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

  /*  protected String addChild(ArrayList list, HttpServletRequest request, WebSite tmit, WebPage pageroot, String url, StringBuilder params, String lang, String dataCurrent, String dataProp) { //boolean isAjax,SemanticObject obj, String propName
        String style;
        boolean toggleopen;
        StringBuilder html = new StringBuilder("<ul class=\"treeres ch\">");
        Iterator<WebPage> childs=pageroot.listChilds();
        while(childs.hasNext()) {
            WebPage webpage = childs.next();
            if(webpage.getId() != null && !webpage.isDeleted()) {
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
                    style = "";
                    if((dataCurrent != null && dataCurrent.length() > 0) && dataCurrent.equalsIgnoreCase(webpage.getId())) {
                        style = " style=\"font-weight:bold; background-color:#BBD4D9; color:#3368B4\";";
                    }
                    html.append("<li>");
                    if(toggleopen) {
                        params.append("&" + webpage.getId() + "=1");
                        html.append("<img src=\"" + pathImages + "/minus.gif\" alt=\"cerrar nodo\" onclick=\"getHtml('" + url + "?site=" + webpage.getWebSiteId() + "&reptp=" + webpage.getId() + params + "&dataProp=" + dataProp + "&li=" + dataCurrent +"&lang=" + lang + "','tree_'+'" + tmit.getId() + "_" + dataProp + "')\" />");//obj.getShortURI() + "_" + propName
                    } else {
                        params.append("&" + webpage.getId() + "=0");
                        html.append("<img src=\"" + pathImages + "/plus.gif\" alt=\"abrir nodo\" onclick=\"getHtml('" + url + "?site=" + webpage.getWebSiteId() + "&reptp=" + webpage.getId() + params + "&dataProp=" + dataProp + "&li=" + dataCurrent + "&lang=" + lang + "','tree_'+'" + tmit.getId() + "_" + dataProp + "')\" />"); //obj.getShortURI() + "_" + propName
                    }


                    html.append("<a class=\"treeres\" onclick=\"getHtml('" + url + "?site=" + webpage.getWebSiteId() + "&reptp=" + webpage.getId() + params + "&dataProp=" + dataProp + "&li=" + dataCurrent + "&lang=" + lang + "','tree_'+'" + tmit.getId() +"_" + dataProp + "')\" " + style + ">");//obj.getShortURI() + "_" + propName
                    if(webpage.isActive()) {
                        html.append("<img src=\"" + pathImages + "/icon_secac.png\" alt=\"seleccionar sección\" />");
                    } else {
                        html.append("<img src=\"" + pathImages + "/icon_secin.png\" alt=\"seleccionar sección\" />");
                    }
                    html.append("<span style=\"padding-left:5px\">" + webpage.getDisplayTitle(lang) + "</span>");
                    html.append("</a>");
                    if(toggleopen) {
                        html.append(addChild(list, request, tmit, webpage, url, params, lang, dataCurrent, dataProp));//url1...isAjax,obj, propName
                    }
                    html.append("</li>");
                }else {
                    style = "";
                    if((dataCurrent != null && dataCurrent.length() > 0) && dataCurrent.equalsIgnoreCase(webpage.getId())) {
                        style = " style=\"font-weight:bold; background-color:#BBD4D9; color:#3368B4\";";
                    }
                    html.append("<li>");
                    html.append("<img src=\"" + pathImages + "/trans.gif\" />");
                    html.append("<a class=\"treeres\" onclick=\"getHtml('" + url + "?site=" + webpage.getWebSiteId() + "&reptp=" + webpage.getId() + params + "&dataProp=" + dataProp + "&li=" + dataCurrent + "&lang=" + lang + "','tree_'+'" + tmit.getId() + "_" + dataProp + "')\" " + style + ">");//obj.getShortURI() + "_" + propName
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
    }*/
}
