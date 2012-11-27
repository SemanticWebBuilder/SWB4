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
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Dns;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;

// TODO: Auto-generated Javadoc
/**
 * The Class GoogleSiteMap.
 * 
 * @author serch
 */
public class GoogleSiteMap implements InternalServlet {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(GoogleSiteMap.class);

    /* (non-Javadoc)
     * @see org.semanticwb.servlet.internal.InternalServlet#init(javax.servlet.ServletContext)
     */
    public void init(ServletContext config) throws ServletException
    {
        log.event("Initializing InternalServlet GoogleSiteMap...");
    }

    /* (non-Javadoc)
     * @see org.semanticwb.servlet.internal.InternalServlet#doProcess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.servlet.internal.DistributorParams)
     */
    public void doProcess(HttpServletRequest req, HttpServletResponse resp, DistributorParams dparams) throws IOException, ServletException
    {
        String lang = dparams.getUser().getLanguage();
//        String country = "mx";
        String host = "http://"+req.getServerName();
        if (req.getServerPort()!=80) host += ":"+req.getServerPort();
        //resp.setHeader("Content-Encoding", "gzip");
        resp.setContentType("text/xml");
        resp.setCharacterEncoding("UTF-8");
        //java.util.zip.GZIPOutputStream garr = new java.util.zip.GZIPOutputStream(resp.getOutputStream());
        StringBuffer ret=new StringBuffer();
        ret.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        //ret.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">");
        ret.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd\">");

        String modelid=dparams.getModelId();

        //Iterator<WebSite> maps = SWBContext.listWebSites();;
        //while (maps.hasNext())
        {
            //WebSite map = maps.next();
            WebSite map = SWBContext.getWebSite(modelid);

//            if(map.getLanguage()!=null)
//            {
//                lang=map.getLanguage().getId();
//            }
//            if(map.getCountry()!=null)
//            {
//                country=map.getCountry().getId();
//            }
//            if (SWBContext.WEBSITE_GLOBAL.equals(map.getId()))
//            {
//                continue;
//            }

//            Iterator<Dns> ht =  map.listDnses();
            String hn = host;
//            if (ht.hasNext()) {
//                Dns dns = ht.next();
//                String tmp = dns.getDns();
//                if (!"localhost".equals(tmp)){
//                    if (req.getServerPort()!=80) tmp += ":"+req.getServerPort();
//                    if (!tmp.startsWith("http:")) hn = "http://"+tmp;
//                }
//            }
            WebPage topicH = map.getHomePage();
            processWebPage(ret, hn, topicH, 95, lang);

//            if (null!= topicH.getWebSite().getLanguage()) lang = topicH.getWebSite().getLanguage().getId(); else lang = "es";
//            ret.append("<url><loc>" + hn +topicH.getRealUrl() + "</loc>");
//            if (!"".equals(topicH.getContentsLastUpdate(lang, "yyyy-mm-dd")))
//                    ret.append("<lastmod>"+topicH.getContentsLastUpdate(lang, "yyyy-mm-dd")+"</lastmod>");
//            ret.append("<priority>0.8</priority></url>");
//            Iterator<WebPage> chanels =topicH.listVisibleChilds(lang);
//            while (chanels.hasNext())
//            {
//                WebPage chanel = chanels.next();
//                if (null!= chanel.getWebSite().getLanguage()) lang = chanel.getWebSite().getLanguage().getId(); else lang = "es";
//                ret.append("<url><loc>" + hn + chanel.getRealUrl() + "</loc>");
//            if (!"".equals(chanel.getContentsLastUpdate(lang, "yyyy-mm-dd")))
//                    ret.append("<lastmod>"+chanel.getContentsLastUpdate(lang, "yyyy-mm-dd")+"</lastmod>");
//            ret.append("<priority>0.5</priority></url>");
//                Iterator sections = chanel.listVisibleChilds(lang);
//                while (sections.hasNext())
//                {
//                    WebPage section = (WebPage) sections.next();
//                    if (null!= section.getWebSite().getLanguage()) lang = section.getWebSite().getLanguage().getId(); else lang = "es";
//                    ret.append("<url><loc>" + hn + section.getRealUrl() + "</loc>");
//            if (!"".equals(section.getContentsLastUpdate(lang, "yyyy-mm-dd")))
//                    ret.append("<lastmod>"+section.getContentsLastUpdate(lang, "yyyy-mm-dd")+"</lastmod>");
//            ret.append("<priority>0.3</priority></url>");
//                }
//            }
        }
        ret.append("</urlset>");
        resp.getWriter().print(SWBUtils.TEXT.encode(ret.toString(), SWBUtils.TEXT.CHARSET_UTF8));
    }

    void processWebPage(StringBuffer ret, String hn, WebPage page, int score, String lang)
    {
        int tscore = score-5;
        String scoregap = score==5?"05":""+score;
        if (page.isVisible())
        {
            String urlcnt;
            urlcnt = page.getUrl();
//            if (null!=lang && null!=country){
//                urlcnt = page.getRealUrl(lang, country);
//            } else if (null!=lang) {
//                urlcnt = page.getRealUrl(lang);
//            } else {
//                urlcnt = page.getRealUrl();
//            }
            if(page.getWebPageURL()==null||!page.getWebPageURL().startsWith("http:"))
            {
                ret.append("<url><loc>" + hn +urlcnt + "</loc>");
                if (!"".equals(page.getContentsLastUpdate(lang, "yyyy-mm-dd")))
                    ret.append("<lastmod>"+page.getContentsLastUpdate(lang, "yyyy-mm-dd")+"</lastmod>");
                ret.append("<priority>0."+scoregap+"</priority></url>");
            }            
            
        }
        Iterator<WebPage> childs =page.listChilds(lang, true, false, null, true);
        if (score==5) { tscore = score;}
        while (childs.hasNext())
        {
            processWebPage(ret, hn, childs.next(), tscore, lang);
        }
    }

}
