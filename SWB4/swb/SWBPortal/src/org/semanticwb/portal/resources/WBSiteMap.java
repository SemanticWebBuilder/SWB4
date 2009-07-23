
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
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
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
        SelectTree tree = new SelectTree(paramRequest.getWebPage().getWebSite().getId(), url.toString(), false, base.getAttribute("title"), paramRequest.getUser().getLanguage());
        String x = tree.renderXHTML(params);
        out.println(x);
        out.flush();
    }
        
    public void doChilds(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/json;charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        String lang = paramRequest.getUser().getLanguage();
        WebPage home = paramRequest.getWebPage().getWebSite().getHomePage();

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
            if(home.listChilds(lang, true, false, false, false).hasNext()) {
                json.append(",children:[");
                json.append(addReferences(home, lang));
                json.append("]");
            }
            json.append("}");

            if(home.listChilds(lang, true, false, false, false).hasNext()) {
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

        Iterator<WebPage> itwps = node.listChilds(lang, true, false, false, false);
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

        Iterator<WebPage> itwps = node.listChilds(lang, true, false, false, false);
        while(itwps.hasNext()) {
            WebPage wp = itwps.next();
            json.append("{");
            json.append("name:'"+wp.getDisplayName(lang)+"',");
            json.append("id:'"+wp.getId()+"',");
            json.append("purl:'"+wp.getUrl()+"'");
            if(wp.listChilds(lang, true, false, false, false).hasNext()) {
                json.append(",type:'chanel'");
                json.append(",children:[");
                json.append(addReferences(wp, lang));
                json.append("]");
            }
            json.append("}");

            if(wp.listChilds(lang, true, false, false, false).hasNext()) {
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

            if(!"".equals(base.getAttribute("url", "").trim())) {
                surl=base.getAttribute("url").trim();
            }else {
                surl=paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW).toString();
            }
            
            if( base.getAttribute("img")!=null ) {
                out.println("<a href=\"" + surl +"\">");
                out.println("<img src=\""+ webWorkPath +"/"+ base.getAttribute("img").trim() +"\"");
                if(!"".equals(base.getAttribute("alt", "").trim())) {
                    out.println(" alt=\"" + base.getAttribute("alt").trim() + "\"");
                }
                out.println(" border=0></a>");
            }else if( base.getAttribute("btntexto")!=null ) {
                out.println("<form name=frmWBSiteMap method=POST action=\"" + surl + "\">");
                out.println("<input type=submit name=btnWBSiteMap value=");
                out.println("\"" + base.getAttribute("btntexto").trim().replaceAll("\"","&#34;") + "\"");
                if( base.getAttribute("blnstyle")!=null ) {
                    out.println(" style=\"" + base.getAttribute("blnstyle").trim().replaceAll("\"","&#34;") + "\"");
                }
                out.println("></form>");
            }else {
                out.println("<a href=\""+surl+"\"");
                if( base.getAttribute("blnstyle")!=null ) {
                    out.println(" style=\"" + base.getAttribute("blnstyle").trim().replaceAll("\"","&#34;") + "\"");
                }
                out.println(">");
                if( base.getAttribute("lnktexto")!=null ) {
                    out.println(base.getAttribute("lnktexto").trim());
                }else {
                    out.println(paramRequest.getLocaleString("msgSiteMap"));
                }
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
                SelectTree tree = new SelectTree(paramRequest.getWebPage().getWebSite().getId(), url.toString(), false, level, base.getAttribute("title"), paramRequest.getUser().getLanguage());
                HashMap params = new HashMap();
                Enumeration<String> names = request.getParameterNames();
                while(names.hasMoreElements()) {
                    String name = names.nextElement();
                    params.put(name, request.getParameter(name));
                }

                String width = base.getAttribute("width");
                String height = base.getAttribute("height");
                if(width != null) {
                    if(!width.endsWith("%")) {
                        width += "px";
                    }
                    tree.setWidth(base.getAttribute("width"));
                }
                if(height != null) {
                    if(!height.endsWith("%")) {
                        height += "px";
                    }
                    tree.setHeight(base.getAttribute("height"));
                }
                
                out.println(tree.renderXHTMLFirstTime(params));
            }catch(Exception e) {
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
        private String language;

        private String width, height;

        public SelectTree(String website, String url, boolean openOnClick, String title, String language) {
            this.website = website;
            this.url = url;
            this.openOnClick = openOnClick;
            this.title = title;
            this.language = language;
        }

        public SelectTree(String website, String url, boolean openOnClick, int level, String title, String language) {
            this.website = website;
            this.url = url;
            this.openOnClick = openOnClick;
            if(level>0) {
                this.level = level;
            }
            this.title = title;
            this.language = language;
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

                if(level>0 && tpsite==null) {
                    tpsite=SWBContext.getWebSite(website).getHomePage();
                }

                WebSite tmit = SWBContext.getWebSite(website);
                WebPage tmhome=tmit.getHomePage();

                boolean opened = Boolean.parseBoolean(request.get(tmhome.getId())==null?"false":((String)request.get(tmhome.getId())).equals("1")?"true":"false");
                if(level>0) {
                    opened=true;
                }
                html.append("<div class=\"swb-mapa\" id=\"tree_"+website+"\" style=\"");
                if(width!=null) {
                    html.append("width:"+width+";");
                }
                if(height!=null) {
                    html.append("height:"+height+";");
                }
                html.append("\" >");
                if(title!=null) {
                    html.append("<h1>"+title+"</h1>");
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
                    html.append(addChild(request, tmit, tmhome, tpid, params, 1, language));
                }
                html.append("</li>");
                html.append("</ul>");
                html.append("</div>");


            }
            catch(Exception e) {
                log.error("Error on method WebSiteSectionTree.render()", e);
                html.append("\n\nError:"+e);
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
                if(level>0) {
                    opened=true;
                }

                /*html.append("<div class=\"swb-mapa\" id=\"tree_"+website+"\" style=\"");
                if(width!=null) {
                    html.append("width:"+width+";");
                }
                if(height!=null) {
                    html.append("height:"+height+";");
                }
                html.append("\" >");*/
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
                    html.append(addChild(request, tmit, tmhome, tpid, params, language));
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

        private String addChild(HashMap request, WebSite tmit, WebPage pageroot, WebPage tpid, StringBuilder params, int level, String language) {
            boolean opened;

            StringBuilder html = new StringBuilder("<ul>");
            Iterator<WebPage> childs=pageroot.listChilds(language, true, false, false, false);
            while(childs.hasNext()) {
                WebPage webpage = childs.next();
                if(webpage.getId()!=null && webpage instanceof WebPage ) {

                    opened = Boolean.parseBoolean(request.get(webpage.getId())==null?"false":((String)request.get(webpage.getId())).equals("1")?"true":"false");
                    if(this.level>level)opened=true;else opened=false;

                    if(webpage.listChilds(language, true, false, false, false).hasNext()) {
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
                            html.append(addChild(request, tmit, webpage, tpid, params, level+1, language));
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

        private String addChild(HashMap request, WebSite tmit, WebPage pageroot, WebPage tpid, StringBuilder params, String language) {
            boolean opened;

            StringBuilder html = new StringBuilder("<ul>");
            Iterator<WebPage> childs=pageroot.listChilds(language, true, false, false, false);
            while(childs.hasNext()) {
                WebPage webpage = childs.next();
                if(webpage.getId()!=null) {

                    opened = Boolean.parseBoolean(request.get(webpage.getId())==null?"false":((String)request.get(webpage.getId())).equals("1")?"true":"false");

                    if(webpage.listChilds(language, true, false, false, false).hasNext()) {
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
                            html.append(addChild(request, tmit, webpage, tpid, params, language));
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

        /**
         * @return the width
         */
        public String getWidth() {
            return width;
        }

        /**
         * @param width the width to set
         */
        public void setWidth(String width) {
            this.width = width;
        }

        /**
         * @return the height
         */
        public String getHeight() {
            return height;
        }

        /**
         * @param height the height to set
         */
        public void setHeight(String height) {
            this.height = height;
        }
    }
}












