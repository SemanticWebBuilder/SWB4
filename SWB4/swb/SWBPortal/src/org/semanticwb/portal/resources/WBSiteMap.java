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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * WBSiteMap muestra el mapa del sitio de acuerdo a un determinado TopicMap.
 *
 * WBSiteMap displays a map site according with TopicMap.
 *
 * @author : Carlos Ramos Inchaustegui
 */
public class WBSiteMap extends GenericAdmResource
{
    /** The log. */
    private static Logger log = SWBUtils.getLogger(WBSiteMap.class);
    
    /** The web work path. */
    String webWorkPath;  
    
    /** The max level. */
    int maxLevel;
    
    /** The path. */
    String path;

    /** The tpl. */
    private javax.xml.transform.Templates tpl;

    /**
     * Instantiates a new wB site map.
     */
    public WBSiteMap() {
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericAdmResource#setResourceBase(org.semanticwb.model.Resource)
     */
    @Override
    public void setResourceBase(Resource base) {
        try {
            super.setResourceBase(base);
            webWorkPath = (String) SWBPortal.getWebWorkPath()+ base.getWorkPath()+"/";
        }catch(Exception e) {
            log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);
        }

        if( base.getAttribute("template")!=null ) {
            try {
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getFileFromWorkPath(base.getWorkPath() +"/"+ base.getAttribute("template").trim()));
                path = webWorkPath;
            }catch(Exception e) {
                log.error("Error while loading resource template: "+base.getId(), e);
            }
        }
        if( tpl==null ) {
            try {
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/WBSiteMap/WBSiteMap.xsl"));
                path = SWBPlatform.getContextPath() +"/swbadmin/xsl/WBSiteMap/";
            }catch(Exception e) {
                log.error("Error while loading default resource template: "+base.getId(), e);
            }
        }
    }
     
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
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

    /**
     * Obtiene el resultado final del recurso en formato dom.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @return the dom
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    public Document getDom(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base=getResourceBase();
        SWBResourceURL url=paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setMode("bind");
//        url.setCallMethod(url.Call_DIRECT);
//        url.setMode("bind");
        int level;
        try {
            level = Integer.parseInt(base.getAttribute("level"),10);
        }catch(NumberFormatException e) {
            level = 0;
        }
        SelectTree tree = new SelectTree(base, url.toString(), false, level, base.getAttribute("title"), paramRequest.getUser());
        HashMap<String,String> params = new HashMap();
        Enumeration<String> names = request.getParameterNames();
        while(names.hasMoreElements()) {
            String name = names.nextElement();
            params.put(name, request.getParameter(name));
        }
        return tree.renderXHTMLFirstTime(params);
    }

    /**
     * Do bind.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doBind(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        Resource base=getResourceBase();

        SWBResourceURL url = paramRequest.getRenderUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setMode("bind");
        url.setCallMethod(url.Call_DIRECT);
        url.setMode("bind");

        HashMap params = new HashMap();
        Enumeration<String> names = request.getParameterNames();
        while(names.hasMoreElements()) {
            String name = names.nextElement();
            params.put(name, request.getParameter(name));
        }
        //SelectTree tree = new SelectTree(paramRequest.getWebPage().getWebSite().getId(), url.toString(true), false, base.getAttribute("title"), paramRequest.getUser());
        SelectTree tree = new SelectTree(getResourceBase(), url.toString(true), false, base.getAttribute("title"), paramRequest.getUser());
        
        Document dom = tree.renderXHTML(params);
        if(dom != null)  {
            try {
                //System.out.println("\n\nsitemap=\n"+SWBUtils.XML.domToXml(dom));
                out.print(SWBUtils.XML.transformDom(tpl, dom));
            }catch(TransformerException te) {
                log.error("doBind Method. Error while building site map: "+base.getId() +"-"+ base.getTitle(), te);
            }
        }
        out.flush();
    }
        
    /**
     * Do childs.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doChilds(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String lang = paramRequest.getUser().getLanguage();
        
Resource base = getResourceBase();
WebPage home;
//try {
    home = base.getWebSite().getWebPage(base.getAttribute("home"));
//}catch(Exception e) {
//    home = base.getWebSite().getHomePage();
//}
if(home == null) {
    home = base.getWebSite().getHomePage();
}


//        WebPage home = paramRequest.getWebPage().getWebSite().getHomePage();

        StringBuilder json = new StringBuilder();
        try {
            json.append("{");
            json.append("identifier:'id',");
            json.append("label:'name',");
            json.append("items:[");

            json.append("{");
            json.append("name:'"+home.getDisplayTitle(lang)+"',");
            json.append("id:'"+home.getId()+"',");
            json.append("purl:'"+home.getUrl()+"',");
            json.append("type:'home'");
            if(home.listChilds(lang, true, false, false, true).hasNext()) {
                json.append(",children:[");
                json.append(addReferences(home, lang));
                json.append("]");
            }
            json.append("}");

            if(home.listChilds(lang, true, false, false, true).hasNext()) {
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

    /**
     * Adds the references.
     * 
     * @param node the node
     * @param lang the lang
     * @return the string
     */
    private String addReferences(WebPage node, String lang) {
        StringBuilder json = new StringBuilder();

        Iterator<WebPage> itwps = node.listChilds(lang, true, false, false, true);
        while(itwps.hasNext()) {
            WebPage wp = itwps.next();
            json.append("{_reference:'"+wp.getDisplayTitle(lang)+"'}");
            if(itwps.hasNext()) {
                json.append(",");
            }
        }
        return json.toString();
    }

    /**
     * Adds the childs.
     * 
     * @param node the node
     * @param lang the lang
     * @return the string
     */
    private String addChilds(WebPage node, String lang) {
        StringBuilder json = new StringBuilder();

        Iterator<WebPage> itwps = node.listChilds(lang, true, false, false, true);
        while(itwps.hasNext()) {
            WebPage wp = itwps.next();
            json.append("{");
            json.append("name:'"+wp.getDisplayTitle(lang)+"',");
            json.append("id:'"+wp.getId()+"',");
            json.append("purl:'"+wp.getUrl()+"'");
            if(wp.listChilds(lang, true, false, false, true).hasNext()) {
                json.append(",type:'chanel'");
                json.append(",children:[");
                json.append(addReferences(wp, lang));
                json.append("]");
            }
            json.append("}");

            if(wp.listChilds(lang, true, false, false, true).hasNext()) {
                json.append(",");
                json.append(addChilds(wp, lang));
            }

            if(itwps.hasNext()) {
                json.append(",");
            }
        }
        return json.toString();
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doXML(HttpServletRequest, HttpServletResponse, SWBParamRequest)
     */
    @Override
    public void doXML(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Document dom=getDom(request, response, paramRequest);
        if( dom!=null )
            response.getWriter().println(SWBUtils.XML.domToXml(dom));
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericAdmResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        Resource base=getResourceBase();
        
        if(paramRequest.getCallMethod()==paramRequest.Call_STRATEGY) {
            String surl = paramRequest.getWebPage().getUrl();
            Iterator<Resourceable> res = base.listResourceables();
            while(res.hasNext()) {
                Resourceable re = res.next();
                if( re instanceof WebPage ) {
                    surl = ((WebPage)re).getUrl();
                    break;
                }
            }

            if( base.getAttribute("lnktexto")!=null ) {
                out.println("<a href=\""+surl+"\">"+base.getAttribute("lnktexto")+"</a>");
            }else if( base.getAttribute("btntexto")!=null ) {
                out.println("<form method=\"post\" action=\""+surl+"\">");
                out.println("<input type=\"submit\" value=\""+base.getAttribute("btntexto")+"\" />");
                out.println("</form>");
            }else if( base.getAttribute("img")!=null ) {
                out.println("<a href=\""+surl+"\">");
                out.println("<img src=\""+webWorkPath+base.getAttribute("img")+"\" alt=\""+base.getAttribute("alt",paramRequest.getLocaleString("msgSiteMap"))+"\" />");
                out.println("</a>");
            }else {
                out.println("<div class=\"swb-sitemap\">");
                out.println("<a href=\""+surl+"\">"+paramRequest.getLocaleString("msgSiteMap")+"</a>");
                out.println("</div>");
            }
        }else {
            // Mapa de sitio
            Document dom = getDom(request, response, paramRequest);
            if(dom != null)  {
                try {
                    //System.out.println("\n\nsitemap=\n"+SWBUtils.XML.domToXml(dom));
                    out.print(SWBUtils.XML.transformDom(tpl, dom));
                }catch(TransformerException te) {
                    log.error("doView Method. Error while building site map: "+base.getId() +"-"+ base.getTitle(), te);
                }
            }
        }
        out.flush();
    }
    
    /**
     * The Class SelectTree.
     */
    private class SelectTree {
        
        /** The path images. */
        private final String pathImages = SWBPlatform.getContextPath() + "/swbadmin/icons";
        
        /** The website. */
        //private String website;
        private Resource base;
        
        /** The url. */
        private String url;
        
        /** The open on click. */
        private boolean openOnClick;
        
        /** The level. */
        private int level;
        
        /** The title. */
        private String title;

        /** The height. */
        private String width, height;

        /** The user. */
        private User user;

        /**
         * Instantiates a new select tree.
         * 
         * @param website the website
         * @param url the url
         * @param openOnClick the open on click
         * @param title the title
         * @param user the user
         */
        //public SelectTree(String website, String url, boolean openOnClick, String title, User user) {
        public SelectTree(Resource base, String url, boolean openOnClick, String title, User user) {
            //this(website, url, openOnClick, 0, title, user);
            this(base, url, openOnClick, 0, title, user);
        }

        /**
         * Instantiates a new select tree.
         * 
         * @param website the website
         * @param url the url
         * @param openOnClick the open on click
         * @param level the level
         * @param title the title
         * @param user the user
         */
        //public SelectTree(String website, String url, boolean openOnClick, int level, String title, User user) {
        public SelectTree(Resource base, String url, boolean openOnClick, int level, String title, User user) {
            //this.website = website;
            this.base = base;
            this.url = url;
            this.openOnClick = openOnClick;
            if(level>0) {
                this.level = level;
            }
            this.title = title;
            this.user = user;
        }

        /**
         * Render xhtml first time.
         * 
         * @param request the request
         * @return the string
         * @throws SWBResourceException the sWB resource exception
         * @throws IOException Signals that an I/O exception has occurred.
         */
        private Document renderXHTMLFirstTime(HashMap request) throws SWBResourceException, IOException {
            Document  dom = SWBUtils.XML.getNewDocument();
            Element smE = dom.createElement("sitemap");

            StringBuilder params = new StringBuilder("&site="+base.getWebSiteId());
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

//                if(level>0 && tpsite==null) {
//                    tpsite=SWBContext.getWebSite(website).getHomePage();
//                }
                //WebSite tmit = SWBContext.getWebSite(website);
                WebSite tmit = base.getWebSite();
                WebPage tmhome=tmit.getHomePage();


//try {
    tmhome = base.getWebSite().getWebPage(base.getAttribute("home"));
//}catch(Exception e) {
//    tmhome = base.getWebSite().getHomePage();
//}
if(tmhome == null) {
    tmhome = base.getWebSite().getHomePage();
}


                boolean opened = Boolean.parseBoolean(request.get(tmhome.getId())==null?"false":((String)request.get(tmhome.getId())).equals("1")?"true":"false");
                if(level>0) {
                    opened=true;
                }
                smE.setAttribute("leaf", "0");
                smE.setAttribute("id", "tree_"+base.getWebSiteId());
                if(title!=null)
                    smE.setAttribute("title", title);
                dom.appendChild(smE);
                
                Element node = dom.createElement("node");
                smE.appendChild(node);

                if(!user.haveAccess(tmhome))
                    return dom;

                if(opened) {
                    params.append("&"+tmhome.getId()+"=1");
                    node.setAttribute("leaf", "0");
                    node.setAttribute("href", "javascript:getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"','tree_'+'"+base.getWebSiteId()+"');return false;");
                    node.setAttribute("key", "-");
                }else {
                    params.append("&"+tmhome.getId()+"=0");
                    node.setAttribute("leaf", "0");
                    node.setAttribute("href", "javascript:getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"','tree_'+'"+base.getWebSiteId()+"');return false;");
                    node.setAttribute("key", "+");
                }

                if(openOnClick) {
//                    html.append("<a onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+whoOpen+params+"','tree_'+'"+website+"')\" "+style+">");
//                    html.append(tmhome.getDisplayTitle(this.language));
//                    html.append("</a>");
                }else {
                    node.setAttribute("title", tmhome.getDisplayTitle(user.getLanguage()));
                    node.setAttribute("url", tmhome.getUrl(user.getLanguage(),false));
                }

                if(opened) {
                    addChild(request, tmit, tmhome, tpid, params, 1, node);
                }
            }catch(Exception e) {
                log.error("Error on method WebSiteSectionTree.renderXHTMLFirstTime()", e);
            }
            return dom;
        }

        /**
         * Render xhtml.
         * 
         * @param request the request
         * @return the string
         * @throws SWBResourceException the sWB resource exception
         * @throws IOException Signals that an I/O exception has occurred.
         */
        private Document renderXHTML(HashMap request) throws SWBResourceException, IOException {
            Document  dom = SWBUtils.XML.getNewDocument();
            Element smE = dom.createElement("sitemap");

            StringBuilder params = new StringBuilder("&site="+base.getWebSiteId());
            WebSite tm = null;

            try {
                WebPage tpsite=null, tpid=null;
                if(request.containsKey("reptm") && request.get("reptm")!=null) {
                    //tm=SWBContext.getWebSite((String)request.get("reptm"));
                    tm = base.getWebSite();
                    if(tm!=null) {
                        tpsite=tm.getHomePage();
//try {
    tpsite = base.getWebSite().getWebPage(base.getAttribute("home"));
//}catch(Exception e) {
//    tpsite = base.getWebSite().getHomePage();
//}
if(tpsite == null) {
    tpsite = base.getWebSite().getHomePage();
}

                        if(request.containsKey("reptp") && request.get("reptp")!=null && !((String)request.get("reptp")).trim().equals("")) {
                            if(tm.getWebPage((String)request.get("reptp"))!=null) {
                                tpid=tm.getWebPage((String)request.get("reptp"));
                            }
                        }
                    }
                }

//                if(level>0 && tpsite==null) {
//System.out.println("tpsite es nulo");
//                    tpsite = base.getWebSite().getHomePage();
//                }

                WebSite tmit = SWBContext.getWebSite(base.getWebSiteId());
                //WebPage tmhome=tmit.getHomePage();
                WebPage tmhome = base.getWebSite().getWebPage(base.getAttribute("home"));
                if(tmhome == null)
                    tmhome = base.getWebSite().getHomePage();

                boolean opened = Boolean.parseBoolean(request.get(tmhome.getId())==null?"false":((String)request.get(tmhome.getId())).equals("1")?"true":"false");
                if(level>0) {
                    opened=true;
                }
                smE.setAttribute("leaf", "0");
                smE.setAttribute("id", "tree_"+base.getWebSiteId());
                if(title!=null)
                    smE.setAttribute("title", title);
                dom.appendChild(smE);

                Element node = dom.createElement("node");
                smE.appendChild(node);

                if(!user.haveAccess(tmhome))
                    return dom;

                if(tpid!=null && tpid.getId().equalsIgnoreCase(tmhome.getId())) {
                    if(opened) {
                        params.append("&"+tmhome.getId()+"=0");
                        node.setAttribute("leaf", "0");
                        node.setAttribute("href", "javascript:getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"','tree_'+'"+base.getWebSiteId()+"');return false;");
                        node.setAttribute("key", "+");
                        if(level==0)
                            opened = false;
                    }else {
                        params.append("&"+tmhome.getId()+"=1");
                        node.setAttribute("leaf", "0");
                        node.setAttribute("href", "javascript:getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"','tree_'+'"+base.getWebSiteId()+"');return false;");
                        node.setAttribute("key", "-");
                        opened = true;
                    }
                }else {
                    if(opened) {
                        params.append("&"+tmhome.getId()+"=1");
                        node.setAttribute("leaf", "0");
                        node.setAttribute("href", "javascript:getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"','tree_'+'"+base.getWebSiteId()+"');return false;");
                        node.setAttribute("key", "-");
                    }else {
                        params.append("&"+tmhome.getId()+"=0");
                        node.setAttribute("leaf", "0");
                        node.setAttribute("href", "javascript:getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+params+"','tree_'+'"+base.getWebSiteId()+"');return false;");
                        node.setAttribute("key", "+");
                    }
                }

                if(openOnClick) {
//                    html.append("<a onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp=" + tmhome.getId()+whoOpen+params+"','tree_'+'"+website+"')\">");
//                    html.append(tmhome.getDisplayTitle(this.language));
//                    html.append("</a>");
                }else {
                    node.setAttribute("title", tmhome.getDisplayTitle(user.getLanguage()));
                    node.setAttribute("url", tmhome.getUrl(user.getLanguage(),false));
                }

                if(tpsite!=null && opened) {                
                    addChild(request, tmit, tmhome, tpid, params, node);
                }
            }
            catch(Exception e) {
                log.error("Error on method WebSiteSectionTree.render()", e);
            }
            return dom;
        }

        /**
         * Adds the child.
         * 
         * @param request the request
         * @param tmit the tmit
         * @param pageroot the pageroot
         * @param tpid the tpid
         * @param params the params
         * @param level the level
         * @param node the node
         * @return the string
         */
        private void addChild(HashMap request, WebSite tmit, WebPage pageroot, WebPage tpid, StringBuilder params, int level, Element node) {
            boolean opened;

            Document dom = node.getOwnerDocument();
            Element branch = dom.createElement("branch");
            node.appendChild(branch);

            Iterator<WebPage> childs=pageroot.listChilds(user.getLanguage(), true, false, false, true);
            while(childs.hasNext()) {
                WebPage webpage = childs.next();
                //if(webpage.getId()!=null && webpage instanceof WebPage ) {
                if( user.haveAccess(webpage) ) {
                    opened = Boolean.parseBoolean(request.get(webpage.getId())==null?"false":((String)request.get(webpage.getId())).equals("1")?"true":"false");
                    if( this.level>level )
                        opened=true;
                    else
                        opened=false;

                    Element child = dom.createElement("node");
                    branch.appendChild(child);
                    if(webpage.listChilds(user.getLanguage(), true, false, false, true).hasNext()) {
                        if(tpid!=null && tpid.getId().equalsIgnoreCase(webpage.getId())) {
                            if(opened) {
                                params.append("&"+webpage.getId()+"=0");
                                child.setAttribute("leaf", "0");
                                child.setAttribute("href", "javascript:getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"');return false;");
                                child.setAttribute("key", "+");
                                if(this.level==level)
                                    opened = false;
                            }else {
                                params.append("&"+webpage.getId()+"=1");
                                child.setAttribute("leaf", "0");
                                child.setAttribute("href", "javascript:getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"');return false;");
                                child.setAttribute("key", "-");
                                opened = true;
                            }
                        }else {
                            if(opened) {
                                params.append("&"+webpage.getId()+"=1");
                                child.setAttribute("leaf", "0");
                                child.setAttribute("href", "javascript:getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"');return false;");
                                child.setAttribute("key", "-");
                            }else {
                                params.append("&"+webpage.getId()+"=0");
                                child.setAttribute("leaf", "0");
                                child.setAttribute("href", "javascript:getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"');return false;");
                                child.setAttribute("key", "+");
                            }
                        }

                        if(openOnClick) {
//                            html.append("<a onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"')\">");
//                            html.append(webpage.getDisplayTitle(this.language));
//                            html.append("</a>");
                        }else {
                            child.setAttribute("title", webpage.getDisplayTitle(user.getLanguage()));
                            child.setAttribute("url", webpage.getUrl(user.getLanguage(),false));
                        }

                        if(opened) {
                            addChild(request, tmit, webpage, tpid, params, level+1, child);
                        }
                    }else {
                        child.setAttribute("leaf", "1");
                        if(openOnClick) {
//                            html.append("<a onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"')\">");
//                            html.append(webpage.getDisplayTitle(this.language));
//                            html.append("</a>");
                        }else {
                            child.setAttribute("title", webpage.getDisplayTitle(user.getLanguage()));
                            child.setAttribute("url", webpage.getUrl(user.getLanguage(),false));
                        }
                    }
                }
            }
        }

        /**
         * Adds the child.
         * 
         * @param request the request
         * @param tmit the tmit
         * @param pageroot the pageroot
         * @param tpid the tpid
         * @param params the params
         * @param node the node
         * @return the string
         */
        private void addChild(HashMap request, WebSite tmit, WebPage pageroot, WebPage tpid, StringBuilder params, Element node) {
            boolean opened;

            Document dom = node.getOwnerDocument();
            Element branch = dom.createElement("branch");
            node.appendChild(branch);

            Iterator<WebPage> childs=pageroot.listChilds(user.getLanguage(), true, false, false, true);
            while(childs.hasNext()) {
                WebPage webpage = childs.next();
                //if(webpage.getId()!=null) {
                if( user.haveAccess(webpage) ) {
                    opened = Boolean.parseBoolean(request.get(webpage.getId())==null?"false":((String)request.get(webpage.getId())).equals("1")?"true":"false");

                    Element child = dom.createElement("node");
                    branch.appendChild(child);
                    if(webpage.listChilds(user.getLanguage(), true, false, false, true).hasNext()) {
                        if(tpid!=null && tpid.getId().equalsIgnoreCase(webpage.getId())) {
                            if(opened) {
                                params.append("&"+webpage.getId()+"=0");
                                child.setAttribute("leaf", "0");
                                child.setAttribute("href", "javascript:getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"');return false;");
                                child.setAttribute("key", "+");
                                opened = false;
                            }else {
                                params.append("&"+webpage.getId()+"=1");
                                child.setAttribute("leaf", "0");
                                child.setAttribute("href", "javascript:getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"');return false;");
                                child.setAttribute("key", "-");
                                opened = true;
                            }
                        }else {
                            if(opened) {
                                params.append("&"+webpage.getId()+"=1");
                                child.setAttribute("leaf", "0");
                                child.setAttribute("href", "javascript:getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"');return false;");
                                child.setAttribute("key", "-");
                            }else {
                                params.append("&"+webpage.getId()+"=0");
                                child.setAttribute("leaf", "0");
                                child.setAttribute("href", "javascript:getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"');return false;");
                                child.setAttribute("key", "+");
                            }
                        }

                        if(openOnClick) {
//                            html.append("<a onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"')\">");
//                            html.append(webpage.getDisplayTitle(this.language));
//                            html.append("</a>");
                        }else {
                            child.setAttribute("title", webpage.getDisplayTitle(user.getLanguage()));
                            child.setAttribute("url", webpage.getUrl(user.getLanguage(),false));
                        }

                        if(opened) {
                            addChild(request, tmit, webpage, tpid, params, child);
                        }
                    }else {
                        child.setAttribute("leaf", "1");
                        if(openOnClick) {                            
//                            html.append("<a onclick=\"getHtml('"+url+"?reptm="+tmit.getId()+"&reptp="+webpage.getId()+params+"','tree_'+'"+tmit.getId()+"')\">");
//                            html.append(webpage.getDisplayTitle(this.language));
//                            html.append("</a>");
                        }else {
                            child.setAttribute("title", webpage.getDisplayTitle(user.getLanguage()));
                            child.setAttribute("url", webpage.getUrl(user.getLanguage(),false));
                        }
                    }
                }
            }
        }

        /**
         * Gets the width.
         * 
         * @return the width
         */
        public String getWidth() {
            return width;
        }

        /**
         * Sets the width.
         * 
         * @param width the width to set
         */
        public void setWidth(String width) {
            this.width = width;
        }

        /**
         * Gets the height.
         * 
         * @return the height
         */
        public String getHeight() {
            return height;
        }

        /**
         * Sets the height.
         * 
         * @param height the height to set
         */
        public void setHeight(String height) {
            this.height = height;
        }
    }
}












