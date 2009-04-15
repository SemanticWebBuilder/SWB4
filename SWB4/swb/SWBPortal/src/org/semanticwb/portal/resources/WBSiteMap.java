/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci?n e integraci?n para Internet,
 * la cual, es una creaci?n original del Fondo de Informaci?n y Documentaci?n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P?blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi?n 1; No. 03-2003-012112473900 para la versi?n 2, y No. 03-2006-012012004000-01
 * para la versi?n 3, respectivamente.
 *
 * INFOTEC pone a su disposici?n la herramienta INFOTEC WebBuilder a trav?s de su licenciamiento abierto al p?blico (?open source?),
 * en virtud del cual, usted podr? usarlo en las mismas condiciones con que INFOTEC lo ha dise?ado y puesto a su disposici?n;
 * aprender de ?l; distribuirlo a terceros; acceder a su c?digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t?rminos y condiciones de la LICENCIA ABIERTA AL P?BLICO que otorga INFOTEC para la utilizaci?n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant?a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl?cita ni expl?cita,
 * siendo usted completamente responsable de la utilizaci?n que le d? y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici?n la siguiente
 * direcci?n electr?nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.*;
        

/** Esta clase muestra el mapa del sitio de acuerdo a un determinado TopicMap.
 *
 * This class displays a map site according with TopicMap.
 * @since : October 23th 2002, 19:11
 * @author : Infotec
 */
public class WBSiteMap extends GenericAdmResource
{
    private static Logger log = SWBUtils.getLogger(WBSiteMap.class);
    
    String webWorkPath;  
    int maxLevel;
    String path = SWBPlatform.getContextPath() +"/swbadmin/xsl/WBSiteMap/";

    public WBSiteMap() {
    }

    /**
     * @param base
     */    
    @Override
    public void setResourceBase(Resource base)
    {
        try {
            super.setResourceBase(base);
            webWorkPath = (String) SWBPlatform.getWebWorkPath() +  base.getWorkPath();            
        }catch(Exception e) {
            log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);
        }
    }
    
    /**
     * @param request
     * @param response
     * @param paramRequest
     * @throws SWBResourceException
     * @throws IOException
     */    
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        if(paramRequest.getMode().equalsIgnoreCase("Json")) {
            doChilds(request, response, paramRequest);
        }else if(paramRequest.getMode().equalsIgnoreCase("bind")) {
            doBind(request, response, paramRequest);
        }else {
            super.processRequest(request, response, paramRequest);
        }
    }

    public void doBind(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        Resource base=getResourceBase();

        SWBResourceURL url=paramRequest.getRenderUrl();
        url.setCallMethod(url.Call_DIRECT);
        url.setMode("bind");

        HashMap params = new HashMap();
        Enumeration<String> names = request.getParameterNames();
        while(names.hasMoreElements()) {
            String name = names.nextElement();
            params.put(name, request.getParameter(name));
        }
        SelectTree tree = new SelectTree(paramRequest.getTopic().getWebSite().getTitle(), url.toString(), false, base.getAttribute("title"));
        String x = tree.renderXHTML(params);
        out.println(x);
        out.flush();
    }
        
    public void doChilds(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/json;charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        String lang = paramRequest.getUser().getLanguage();
        WebPage home = paramRequest.getTopic().getWebSite().getHomePage();

        StringBuilder json = new StringBuilder();
        try {
            json.append("{");
            json.append("identifier:'id',");
            json.append("label:'name',");
            json.append("items:[");

            json.append("{");
            json.append("name:'"+home.getDisplayName(lang)+"',");
            json.append("id:'"+home.getId()+"',");
            json.append("purl:'"+home.getUrl()+"',");
            json.append("type:'home'");
            if(home.listVisibleChilds(lang).hasNext()) {
                json.append(",children:[");
                json.append(addReferences(home, lang));
                json.append("]");
            }
            json.append("}");

            if(home.listVisibleChilds(lang).hasNext()) {
                json.append(",");
                json.append(addChilds(home, lang));
            }

            json.append("]");
            json.append("}");
        }catch(Exception e) {
            json.append(e);
        }
        out.print(json.toString());
        out.flush();
    }

    private String addReferences(WebPage node, String lang) {
        StringBuilder json = new StringBuilder();

        Iterator<WebPage> itwps = node.listVisibleChilds(lang);
        while(itwps.hasNext()) {
            WebPage wp = itwps.next();
            json.append("{_reference:'"+wp.getDisplayName(lang)+"'}");
            if(itwps.hasNext()) {
                json.append(",");
            }
        }
        return json.toString();
    }

    private String addChilds(WebPage node, String lang) {
        StringBuilder json = new StringBuilder();

        Iterator<WebPage> itwps = node.listVisibleChilds(lang);
        while(itwps.hasNext()) {
            WebPage wp = itwps.next();
            json.append("{");
            json.append("name:'"+wp.getDisplayName(lang)+"',");
            json.append("id:'"+wp.getId()+"',");
            json.append("purl:'"+wp.getUrl()+"'");
            if(wp.listVisibleChilds(lang).hasNext()) {
                json.append(",type:'chanel'");
                json.append(",children:[");
                json.append(addReferences(wp, lang));
                json.append("]");
            }
            json.append("}");

            if(wp.listVisibleChilds(lang).hasNext()) {
                json.append(",");
                json.append(addChilds(wp, lang));
            }

            if(itwps.hasNext()) {
                json.append(",");
            }
        }
        return json.toString();
    }
        
    /**
     * @param request
     * @param response
     * @param reqParams
     * @throws AFException
     * @throws IOException
     */    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        Resource base=getResourceBase();
        
        if(paramRequest.getCallMethod()==paramRequest.Call_STRATEGY) {        
            String surl="";

            if (!"".equals(base.getAttribute("url", "").trim())) {
                surl=base.getAttribute("url").trim();
            }else {
                surl=paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW).toString();
            }

            if (!"".equals(base.getAttribute("img", "").trim())) {
                out.println("<a href=\"" + surl +"\">");
                out.println("<img src=\""+ webWorkPath +"/"+ base.getAttribute("img").trim() +"\"");
                if (!"".equals(base.getAttribute("alt", "").trim())) {
                    out.println(" alt=\"" + base.getAttribute("alt").trim() + "\"");
                }
                out.println(" border=0></a>");
            }else if (!"".equals(base.getAttribute("btntexto", "").trim())) {
                out.println("<form name=frmWBSiteMap method=POST action=\"" + surl + "\">");
                out.println("<input type=submit name=btnWBSiteMap value=");
                out.println("\"" + base.getAttribute("btntexto").trim().replaceAll("\"","&#34;") + "\"");
                if (!"".equals(base.getAttribute("blnstyle", "").trim())) {
                    out.println(" style=\"" + base.getAttribute("blnstyle").trim().replaceAll("\"","&#34;") + "\"");
                }
                out.println("></form>");
            }else {
                out.println("<a href=\"" + surl +"\"");
                if (!"".equals(base.getAttribute("blnstyle", "").trim())) {
                    out.println(" style=\"" + base.getAttribute("blnstyle").trim().replaceAll("\"","&#34;") + "\"");
                }
                out.println(">");
                if (!"".equals(base.getAttribute("lnktexto", "").trim())) {
                    out.println(base.getAttribute("lnktexto").trim());
                }
                else out.println(paramRequest.getLocaleString("msgSiteMap"));
                out.println("</a>");
            }
        }else {
            // Mapa de sitio
            try {
                SWBResourceURL url=paramRequest.getRenderUrl();
                url.setCallMethod(url.Call_DIRECT);
                url.setMode("bind");
                int level;
                try {
                    level = Integer.parseInt(base.getAttribute("level"),10);
                }catch(NumberFormatException e) {
                    level = 0;
                }
                SelectTree tree = new SelectTree(paramRequest.getTopic().getWebSite().getTitle(), url.toString(), false, level, base.getAttribute("title"));
                HashMap params = new HashMap();
                Enumeration<String> names = request.getParameterNames();
                while(names.hasMoreElements()) {
                    String name = names.nextElement();
                    params.put(name, request.getParameter(name));
                }
                String x = tree.renderXHTMLFirstTime(params);
                out.print(x);
            }
            catch(Exception e) {
                log.error(e);
            }
        }
        out.flush();
    }

    private class SelectTree {
        private final String pathImages = SWBPlatform.getContextPath() + "/swbadmin/icons";
        private String website;
        private String url;
        private boolean openOnClick;
        private int level;
        private String title;

        public SelectTree(String website, String url, boolean openOnClick, String title) {
            this.website = website;
            this.url = url;
            this.openOnClick = openOnClick;
            this.title = title;
        }

        public SelectTree(String website, String url, boolean openOnClick, int level, String title) {
            this.website = website;
            this.url = url;
            this.openOnClick = openOnClick;
            if(level>0) {
                this.level = level;
            }
            this.title = title;
        }

        public String renderXHTMLFirstTime(HashMap request) throws SWBResourceException, IOException {
            StringBuilder html = new StringBuilder();
            StringBuilder params = new StringBuilder("&site="+website);
            String whoOpen = "";
            WebSite tm = null;

            try {
                WebPage tpsite=null, tpid=null;
                String style="";

                if(request.containsKey("reptm") && request.get("reptm")!=null) {
                    tm=SWBContext.getWebSite((String)request.get("reptm"));

                    if(tm!=null) {
                        tpsite=tm.getHomePage();

                        if(request.containsKey("reptp") && request.get("reptp")!=null && !((String)request.get("reptp")).trim().equals("")) {
                            if(tm.getWebPage((String)request.get("reptp"))!=null) {
                                tpid=tm.getWebPage((String)request.get("reptp"));
                            }
                        }
                    }
                }

                if(level>0 && tpsite==null) tpsite=SWBContext.getWebSite(website).getHomePage();

                WebSite tmit = SWBContext.getWebSite(website);
                WebPage tmhome=tmit.getHomePage();

                boolean opened = Boolean.parseBoolean(request.get(tmhome.getId())==null?"false":((String)request.get(tmhome.getId())).equals("1")?"true":"false");
                if(level>0)opened=true;

                html.append("<div class=\"swb-mapa\" id=\"tree_"+website+"\">");
                if(title!=null) {
                    html.append("<h1>mapa de sitio</h1>");
                }
                html.append("<ul>");
                html.append("<li>");

                if(tpid!=null && tpid.getId().equalsIgnoreCase(tmhome.getId())) {
                    if(opened) {
                        params.append("&"+tmhome.getId()+"=0");
                        html.append("<a class=\"icomap\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"','tree_'+'"+website+"')\"><span>+<span></a>");
                        if(level==0)
                            opened = false;
                    }else {
                        params.append("&"+tmhome.getId()+"=1");
                        html.append("<a class=\"icomap\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"','tree_'+'"+website+"')\"><span>-<span></a>");
                        opened = true;
                    }
                }else {
                    if(opened) {
                        params.append("&"+tmhome.getId()+"=1");
                        html.append("<a class=\"icomap\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"','tree_'+'"+website+"')\"><span>-<span></a>");
                    }else {
                        params.append("&"+tmhome.getId()+"=0");
                        html.append("<a class=\"icomap\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"','tree_'+'"+website+"')\"><span>+<span></a>");
                    }
                }

                if(openOnClick) {
                    html.append("<a onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+whoOpen+params+"','tree_'+'"+website+"')\" "+style+">");
                    html.append(tmhome.getDisplayName());
                    html.append("</a>");
                }else {
                    html.append("<a onclick=\"window.location='"+tmhome.getUrl()+"'\">");
                    html.append(tmhome.getDisplayName());
                    html.append("</a>");
                }

                //if(tpsite!=null && this.level>0 && opened) {
                if(opened) {
                    html.append(addChild(request, tmit, tmhome, tpid, params, 1));
                }
                html.append("</li>");
                html.append("</ul>");
                html.append("</div>");


            }
            catch(Exception e) {
                log.error("Error on method WebSiteSectionTree.render()", e);
            }
            return html.toString();
        }

        public String renderXHTML(HashMap request) throws SWBResourceException, IOException {
            StringBuilder html = new StringBuilder();
            StringBuilder params = new StringBuilder("&site="+website);
            String whoOpen = "";
            WebSite tm = null;

            try {
                WebPage tpsite=null, tpid=null;
                if(request.containsKey("reptm") && request.get("reptm")!=null) {
                    tm=SWBContext.getWebSite((String)request.get("reptm"));

                    if(tm!=null) {
                        tpsite=tm.getHomePage();

                        if(request.containsKey("reptp") && request.get("reptp")!=null && !((String)request.get("reptp")).trim().equals("")) {
                            if(tm.getWebPage((String)request.get("reptp"))!=null) {
                                tpid=tm.getWebPage((String)request.get("reptp"));
                            }
                        }
                    }
                }

                if(level>0 && tpsite==null) tpsite=SWBContext.getWebSite(website).getHomePage();

                WebSite tmit = SWBContext.getWebSite(website);
                WebPage tmhome=tmit.getHomePage();

                boolean opened = Boolean.parseBoolean(request.get(tmhome.getId())==null?"false":((String)request.get(tmhome.getId())).equals("1")?"true":"false");
                if(level>0)opened=true;

                html.append("<div class=\"swb-mapa\" id=\"tree_"+website+"\">");
                if(title!=null) {
                    html.append("<h1>mapa de sitio</h1>");
                }
                html.append("<ul>");
                html.append("<li>");

                if(tpid!=null && tpid.getId().equalsIgnoreCase(tmhome.getId())) {                    
                    if(opened) {
                        params.append("&"+tmhome.getId()+"=0");
                        html.append("<a class=\"icomap\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"','tree_'+'"+website+"')\"><span>+<span></a>");
                        if(level==0)
                            opened = false;
                    }else {
                        params.append("&"+tmhome.getId()+"=1");
                        html.append("<a class=\"icomap\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"','tree_'+'"+website+"')\"><span>-<span></a>");
                        opened = true;
                    }
                }else {
                    if(opened) {
                        params.append("&"+tmhome.getId()+"=1");
                        html.append("<a class=\"icomap\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"','tree_'+'"+website+"')\"><span>-<span></a>");
                    }else {
                        params.append("&"+tmhome.getId()+"=0");
                        html.append("<a class=\"icomap\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"','tree_'+'"+website+"')\"><span>+<span></a>");
                    }
                }

                if(openOnClick) {
                    html.append("<a onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+whoOpen+params+"','tree_'+'"+website+"')\">");
                    html.append(tmhome.getDisplayName());
                    html.append("</a>");
                }else {
                    html.append("<a onclick=\"window.location='"+tmhome.getUrl()+"'\">");
                    html.append(tmhome.getDisplayName());
                    html.append("</a>");
                }

                if(tpsite!=null && opened) {                
                    html.append(addChild(request, tmit, tmhome, tpid, params));
                }
                html.append("</li>");
                html.append("</ul>");
                html.append("</div>");


            }
            catch(Exception e) {
                log.error("Error on method WebSiteSectionTree.render()", e);
            }
            return html.toString();
        }

        private String addChild(HashMap request, WebSite tmit, WebPage pageroot, WebPage tpid, StringBuilder params, int level) {
            boolean opened;

            StringBuilder html = new StringBuilder("<ul>");
            Iterator<WebPage> childs=pageroot.listChilds();
            while(childs.hasNext()) {
                WebPage webpage = childs.next();
                if(webpage.getId()!=null) {

                    opened = Boolean.parseBoolean(request.get(webpage.getId())==null?"false":((String)request.get(webpage.getId())).equals("1")?"true":"false");
                    if(this.level>level)opened=true;else opened=false;

                    if(webpage.listChilds().hasNext()) {
                        html.append("<li>");
                        if(tpid!=null && tpid.getId().equalsIgnoreCase(webpage.getId())) {
                            if(opened) {
                                params.append("&"+webpage.getId()+"=0");
                                html.append("<a class=\"icomap\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"')\"><span>+<span></a>");
                                if(this.level==level)
                                    opened = false;
                            }else {
                                params.append("&"+webpage.getId()+"=1");
                                html.append("<a class=\"icomap\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"')\"><span>-<span></a>");
                                opened = true;
                            }
                        }else {
                            if(opened) {
                                params.append("&"+webpage.getId()+"=1");
                                html.append("<a class=\"icomap\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"')\"><span>-<span></a>");
                            }else {
                                params.append("&"+webpage.getId()+"=0");
                                html.append("<a class=\"icomap\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"')\"><span>+<span></a>");
                            }
                        }

                        if(openOnClick) {
                            html.append("<a onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"')\">");
                            html.append(webpage.getDisplayName());
                            html.append("</a>");
                        }else {
                            html.append("<a onclick=\"window.location='"+webpage.getUrl()+"'\">");
                            html.append(webpage.getDisplayName());
                            html.append("</a>");
                        }

                        if(opened) {
                            html.append(addChild(request, tmit, webpage, tpid, params, level+1));
                        }

                        html.append("</li>");
                    }else {
                        html.append("<li>");
                        //html.append("<img src=\""+pathImages+"/trans.gif\" />");
                        if(openOnClick) {
                            html.append("<a onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"')\">");
                            html.append(webpage.getDisplayName());
                            html.append("</a>");
                        }else {
                            html.append("<a onclick=\"window.location='"+webpage.getUrl()+"'\">");
                            html.append(webpage.getDisplayName());
                            html.append("</a>");
                        }
                        html.append("</li>");
                    }
                }
            }
            html.append("</ul>");

            return html.toString();
        }

        private String addChild(HashMap request, WebSite tmit, WebPage pageroot, WebPage tpid, StringBuilder params) {
            boolean opened;

            StringBuilder html = new StringBuilder("<ul>");
            Iterator<WebPage> childs=pageroot.listChilds();
            while(childs.hasNext()) {
                WebPage webpage = childs.next();
                if(webpage.getId()!=null) {

                    opened = Boolean.parseBoolean(request.get(webpage.getId())==null?"false":((String)request.get(webpage.getId())).equals("1")?"true":"false");

                    if(webpage.listChilds().hasNext()) {
                        html.append("<li>");
                        if(tpid!=null && tpid.getId().equalsIgnoreCase(webpage.getId())) {
                            if(opened) {
                                params.append("&"+webpage.getId()+"=0");
                                html.append("<a class=\"icomap\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"')\"><span>+<span></a>");
                                opened = false;
                            }else {
                                params.append("&"+webpage.getId()+"=1");
                                html.append("<a class=\"icomap\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"')\"><span>-<span></a>");
                                opened = true;
                            }
                        }else {
                            if(opened) {
                                params.append("&"+webpage.getId()+"=1");
                                html.append("<a class=\"icomap\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"')\"><span>-<span></a>");
                            }else {
                                params.append("&"+webpage.getId()+"=0");
                                html.append("<a class=\"icomap\" onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"')\"><span>+<span></a>");
                            }
                        }

                        if(openOnClick) {
                            html.append("<a onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"')\">");
                            html.append(webpage.getDisplayName());
                            html.append("</a>");
                        }else {
                            html.append("<a onclick=\"window.location='"+webpage.getUrl()+"'\">");
                            html.append(webpage.getDisplayName());
                            html.append("</a>");
                        }

                        if(opened) {
                            html.append(addChild(request, tmit, webpage, tpid, params));
                        }

                        html.append("</li>");
                    }else {
                        html.append("<li>");
                        //html.append("<img src=\""+pathImages+"/trans.gif\" />");
                        if(openOnClick) {
                            html.append("<a onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"')\">");
                            html.append(webpage.getDisplayName());
                            html.append("</a>");
                        }else {
                            html.append("<a onclick=\"window.location='"+webpage.getUrl()+"'\">");
                            html.append(webpage.getDisplayName());
                            html.append("</a>");
                        }
                        html.append("</li>");
                    }
                }
            }
            html.append("</ul>");

            return html.toString();
        }
    }
}












