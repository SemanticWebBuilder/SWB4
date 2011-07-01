package org.semanticwb.model;

import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


   /**
   * TreeSelect 
   */
public class TreeSelect extends org.semanticwb.model.base.TreeSelectBase 
{
    protected static final String pathImages = SWBPlatform.getContextPath() + "/swbadmin/icons/";
    private static Logger log = SWBUtils.getLogger(TreeSelect.class);

    public TreeSelect(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName) {
        if (prop.getDisplayProperty() == null) {
            return;
        }
        String value = request.getParameter("section" + "_" + obj.getShortURI() + "_" + propName);
        String delPages = request.getParameter("deletePages" + "_" + obj.getShortURI() + "_" + propName);
        if ((value != null) && (value.length() > 0)) {
            try {
                obj.setProperty(prop, value);
            } catch (Exception e) {
                log.error("Can't save the value's property in TreeSelect, " + e);
            }
        }
        if(delPages != null) {
            obj.removeProperty(prop);
        }
    }

    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type, String mode, String lang) {
        StringBuffer ret = new StringBuffer(256);
        String style = "";
        ArrayList listData = new ArrayList();
        StringBuilder params = new StringBuilder("");

        String li = obj.getProperty(prop);
        WebSite site = SWBContext.getWebSite(getModel().getName());
        WebPage home = site.getHomePage();
        String url = SWBPlatform.getContextPath() + "/treeSelect/" + obj.getModel().getModelObject().getId();
        String dataProp = obj.getShortURI() + "_" + propName;

        //Si la propiedad tiene un valor almacenado, obtener los padres para abrir las carpetas
        if(li != null && li.length() > 0) {
            WebPage lip = site.getWebPage(li);
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

        //Carpeta Abierta o Cerrada
        boolean toggleopen = Boolean.parseBoolean(request.getParameter(home.getId()) == null ? "false" : ((String)request.getParameter(home.getId())).equals("1") ? "true" : "false");
        ret.append("<input type=\"checkbox\" name=\"deletePages" + "_" + obj.getShortURI() + "_" + propName + "\" value=\"deletePages\">Deseleccionar páginas web");
        ret.append("<div id=\"slave\">");
        ret.append("<div class=\"sitesectiontree\" id=\"tree_" + site.getId() + "_" + obj.getShortURI() + "_" + propName + "\">");
        ret.append("<ul class=\"treeres\">");
        ret.append("<li>");


        if(site.isActive()) { //Icono Activo/Inactivo para el sitio
            ret.append("<img src=\"" + pathImages + "/icon_siteb.gif\" alt=\"seleccionar sitio\" />");
        } else {
            ret.append("<img src=\"" + pathImages + "/icon_sitein.png\" alt=\"seleccionar sitio\" />");
        }

        try { //Titulo del Sitio
            ret.append("<span style=\"padding-left:5px\">" + SWBUtils.TEXT.encode(site.getDisplayTitle(lang), SWBUtils.TEXT.CHARSET_UTF8) + "</span>");
        } catch(Exception e) {
            log.error("Can´t convert text to code UTF8" + e);
        }
        ret.append("<ul class=\"treeres\">");
        ret.append("<li>");

        if(listData.isEmpty()) {
            style = "";
            if(toggleopen) {  //Si la página esta abierta
                params.append("&" + home.getId() + "=1");
                ret.append("<img src=\"" + pathImages + "/minus.gif\" alt=\"cerrar nodo\"  onclick=\"getHtml('" + url + "?site=" + home.getWebSiteId() + "&reptp=" + home.getId() + params + "&dataProp=" + dataProp + "&li=" + li + "&lang=" + lang + "','tree_'+'" + site.getId() + "_" + obj.getShortURI() + "_" + propName+"')\" />");
            }else { //Si la página no esta abierta
                params.append("&" + home.getId() + "=0");
                ret.append("<img src=\"" + pathImages + "/plus.gif\" alt=\"abrir nodo\"  onclick=\"getHtml('" + url + "?site=" + home.getWebSiteId() + "&reptp=" + home.getId() + params + "&dataProp=" + dataProp + "&li=" + li + "&lang=" + lang + "','tree_'+'" + site.getId() + "_" + obj.getShortURI() + "_" + propName + "')\" />");
            }
        } else {
            params.append("&" + home.getId() + "=1");
            ret.append("<img src=\"" + pathImages + "/minus.gif\" alt=\"cerrar nodo\"  onclick=\"getHtml('" + url + "?site=" + home.getWebSiteId() + "&reptp=" + home.getId() + params + "&dataProp=" + dataProp + "&li=" + li + "&lang=" + lang + "','tree_'+'" + site.getId() + "_" + obj.getShortURI() + "_" + propName + "')\" />");
        }
        if((li != null && li.length() > 0) && li.equalsIgnoreCase(home.getId())) {
            style = " style=\"font-weight:bold; background-color:#6699FF\"";
        }

        ret.append("<a class=\"treeres\" onclick=\"getHtml('" + url + "?site=" + home.getWebSiteId() + "&reptp=" + home.getId() + params + "&dataProp=" + dataProp + "&li=" + li + "&lang=" + lang + "','tree_'+'" + site.getId() + "_" + obj.getShortURI() + "_" + propName + "')\" " + style + ">");
        if(home.isActive()) { //mostrar icono del home(Activo/Desactivo)
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

        //Pintando los hijos si esta abierta la carpeta
        if(home != null && toggleopen) {
            ret.append(addChild(listData, request, site, home, url , params, lang, li, dataProp));//url1...true,obj, propName
        }
        if(!listData.isEmpty()) {
            ret.append(addChild(listData, request, site, home, url , params, lang, li, dataProp));//url1...false,obj, propName
        }
        ret.append("</li>");
        ret.append("</ul>");
        ret.append("</li>");
        ret.append("</ul>");
        ret.append("</div>");
        ret.append("</div>");
        return ret.toString();
    }
    
    protected String addChild(ArrayList list, HttpServletRequest request, WebSite tmit, WebPage pageroot, String url, StringBuilder params, String lang, String dataCurrent, String dataProp) { //boolean isAjax,SemanticObject obj, String propName
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
                    html.append("<li>");
                    style = "";
                    if(toggleopen) {
                        params.append("&" + webpage.getId() + "=1");
                        html.append("<img src=\"" + pathImages + "/minus.gif\" alt=\"cerrar nodo\" onclick=\"getHtml('" + url + "?site=" + webpage.getWebSiteId() + "&reptp=" + webpage.getId() + params + "&dataProp=" + dataProp + "&li=" + dataCurrent +"&lang=" + lang + "','tree_'+'" + tmit.getId() + "_" + dataProp + "')\" />");//obj.getShortURI() + "_" + propName
                    } else {
                        params.append("&" + webpage.getId() + "=0");
                        html.append("<img src=\"" + pathImages + "/plus.gif\" alt=\"abrir nodo\" onclick=\"getHtml('" + url + "?site=" + webpage.getWebSiteId() + "&reptp=" + webpage.getId() + params + "&dataProp=" + dataProp + "&li=" + dataCurrent + "&lang=" + lang + "','tree_'+'" + tmit.getId() + "_" + dataProp + "')\" />"); //obj.getShortURI() + "_" + propName
                    }
                    if((dataCurrent != null && dataCurrent.length() > 0) && dataCurrent.equalsIgnoreCase(webpage.getId())) {
                        style = " style=\"font-weight:bold; background-color:#6699FF\"";
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
                        style = " style=\"font-weight:bold; background-color:#6699FF\"";
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
    }

}
