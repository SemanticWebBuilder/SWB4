/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
        String lang = "es";
        String host = "http://"+req.getServerName();
        if (req.getServerPort()!=80) host += ":"+req.getServerPort();
        //resp.setHeader("Content-Encoding", "gzip");
        resp.setContentType("text/xml");
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
            if(map.getLanguage()!=null)
            {
                lang=map.getLanguage().getId();
            }
//            if (SWBContext.WEBSITE_GLOBAL.equals(map.getId()))
//            {
//                continue;
//            }

            Iterator<Dns> ht =  map.listDnses();
            String hn = host;
            if (ht.hasNext()) {
                Dns dns = ht.next();
                String tmp = dns.getDns();
                if (!"localhost".equals(tmp)){
                    if (req.getServerPort()!=80) tmp += ":"+req.getServerPort();
                    if (!tmp.startsWith("http:")) hn = "http://"+tmp;
                }
            }
            WebPage topicH = map.getHomePage();
            if (null!= topicH.getWebSite().getLanguage()) lang = topicH.getWebSite().getLanguage().getId(); else lang = "es";
            ret.append("<url><loc>" + hn +topicH.getRealUrl() + "</loc>");
            if (!"".equals(topicH.getContentsLastUpdate(lang, "yyyy-mm-dd")))
                    ret.append("<lastmod>"+topicH.getContentsLastUpdate(lang, "yyyy-mm-dd")+"</lastmod>");
            ret.append("<priority>0.8</priority></url>");
            Iterator<WebPage> chanels =topicH.listVisibleChilds(lang);
            while (chanels.hasNext())
            {
                WebPage chanel = chanels.next();
                if (null!= chanel.getWebSite().getLanguage()) lang = chanel.getWebSite().getLanguage().getId(); else lang = "es";
                ret.append("<url><loc>" + hn + chanel.getRealUrl() + "</loc>");
            if (!"".equals(chanel.getContentsLastUpdate(lang, "yyyy-mm-dd")))
                    ret.append("<lastmod>"+chanel.getContentsLastUpdate(lang, "yyyy-mm-dd")+"</lastmod>");
            ret.append("<priority>0.5</priority></url>");
                Iterator sections = chanel.listVisibleChilds(lang);
                while (sections.hasNext())
                {
                    WebPage section = (WebPage) sections.next();
                    if (null!= section.getWebSite().getLanguage()) lang = section.getWebSite().getLanguage().getId(); else lang = "es";
                    ret.append("<url><loc>" + hn + section.getRealUrl() + "</loc>");
            if (!"".equals(section.getContentsLastUpdate(lang, "yyyy-mm-dd")))
                    ret.append("<lastmod>"+section.getContentsLastUpdate(lang, "yyyy-mm-dd")+"</lastmod>");
            ret.append("<priority>0.3</priority></url>");
                }
            }
        }
        ret.append("</urlset>");
        resp.getWriter().print(SWBUtils.TEXT.encode(ret.toString(), SWBUtils.TEXT.CHARSET_UTF8));
    }

}
