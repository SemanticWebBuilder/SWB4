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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.util.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.URLEncoder;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Searchable;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.indexer.SWBIndexer;
import org.semanticwb.portal.indexer.parser.GenericParser;
import org.semanticwb.portal.indexer.searcher.SearchDocument;
import org.semanticwb.portal.indexer.searcher.SearchQuery;
import org.semanticwb.portal.indexer.searcher.SearchResults;
import org.semanticwb.portal.indexer.searcher.SearchTerm;


// TODO: Auto-generated Javadoc
/**
 * The Class WBSearch.
 * 
 * @author Javier Solis Gonzalez,
 * @modified by Jorge A. Jiménez
 * @modified by Hasdai Pacheco {haxdai@gmail.com}
 */
public class WBSearch extends GenericAdmResource
{
    
    /** The log. */
    private static Logger log=SWBUtils.getLogger(WBSearch.class);
    
    /** The tpl. */
    javax.xml.transform.Templates tpl;
    
    /** The path. */
    String path = SWBPlatform.getContextPath() +"/swbadmin/xsl/WBSearch/";
    
    /** The client. */
    private boolean client = false;
    
    /**
     * Creates a new instance of FullTextSearch.
     */
    public WBSearch() 
    {
        client=SWBPortal.isClient();
    }
    
    
    /**
     * Asigna la información de la base de datos al recurso.
     * 
     * @param base the new resource base
     */
    @Override
    public void setResourceBase(Resource base) {
        try { 
            super.setResourceBase(base);
        }
        catch(Exception e)
        {
            log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(),e);
        }
        if (!client) {
            if(!"".equals(base.getAttribute("template","").trim())) {
                try {
                    tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getFileFromWorkPath(base.getWorkPath() +"/"+ base.getAttribute("template").trim()));
                    path= SWBPortal.getWebWorkPath() +  base.getWorkPath() + "/";
                }
                catch(Exception e)
                {
                    log.error("Error while loading resource template: "+base.getId(),e);
                }
            }
            if(tpl==null) {
                try
                {
                    tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/WBSearch/WBSearch.xsl"));
                }
                catch(Exception e)
                {
                    log.error("Error while loading default resource template: "+base.getId(),e);
                }
            }
        }
    }
    
    /**
     * Gets the dom.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @return the dom
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    public org.w3c.dom.Document getDom(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        //System.out.println("getDom");
        try {
            Document doc = SWBUtils.XML.getNewDocument();
            WebSite tm = paramRequest.getWebPage().getWebSite();
            User user=paramRequest.getUser();
            
            String lang=paramRequest.getUser().getLanguage();

            HashMap args = new HashMap();
            args.put("separator", " | ");
            args.put("links", "false");
            args.put("language", lang);
            
            Element search = doc.createElement("SEARCH");
            doc.appendChild(search);
            search.setAttribute("path", path);

            int ipage=0;
            String q = request.getParameter("q");
            String sort = request.getParameter("sort");
            if(request.getParameter("wbSpage")!=null) ipage=Integer.parseInt(request.getParameter("wbSpage"));
            
            int max=0;
            int i = 0;        //contador del segmento
            int ipageLength=0;
            if (q != null && q.trim().length()>0) {
                search.setAttribute("words", q);
                search.setAttribute("wordsEnc", java.net.URLEncoder.encode(q));
                search.setAttribute("work", SWBPortal.getWebWorkPath());
                search.setAttribute("url", paramRequest.getWebPage().getUrl());

                
                String smap=paramRequest.getResourceBase().getAttribute("amaps","0");
                //System.out.println("amaps:"+reqParams.getResourceBase().getAttribute("amaps","0"));
                SearchQuery query=new SearchQuery();
                SearchQuery tquery=new SearchQuery(SearchQuery.OPER_AND);
                query.addQuery(tquery);
                tquery.addTerm(new SearchTerm(SWBIndexer.ATT_TITLE, q, SearchTerm.OPER_OR));
                tquery.addTerm(new SearchTerm(SWBIndexer.ATT_DESCRIPTION, q, SearchTerm.OPER_OR));
                tquery.addTerm(new SearchTerm(SWBIndexer.ATT_TAGS, q, SearchTerm.OPER_OR));
                tquery.addTerm(new SearchTerm(SWBIndexer.ATT_DATA, q, SearchTerm.OPER_OR));
                if(!smap.trim().equals("1"))
                {
                    query.addTerm(new SearchTerm(SWBIndexer.ATT_MODEL, tm.getId(), SearchTerm.OPER_AND));
                }

                String stpini=paramRequest.getResourceBase().getAttribute("tpini",null);
                if(stpini==null)stpini=request.getParameter("cat");
                if(stpini!=null)
                {
                    query.addTerm(new SearchTerm(SWBIndexer.ATT_CATEGORY, stpini, SearchTerm.OPER_AND));
                }

                String cls=paramRequest.getResourceBase().getAttribute("cls",null);
                if(cls==null)stpini=request.getParameter("cls");
                if(cls!=null)
                {
                    if (cls.contains(" ")) {
                        SearchQuery sq = new SearchQuery(SearchQuery.OPER_AND);
                        String classes [] = cls.split(" ");
                        for (int j = 0; j < classes.length; j++) {
                            String clsname = classes[j];
                            SearchTerm st = new SearchTerm(SWBIndexer.ATT_CLASS, clsname, SearchTerm.OPER_OR);
                            sq.addTerm(st);
                        }
                        query.addQuery(sq);
                    } else {
                        query.addTerm(new SearchTerm(SWBIndexer.ATT_CLASS, cls, SearchTerm.OPER_AND));
                    }
                }

                SWBIndexer indexer=SWBPortal.getIndexMgr().getModelIndexer(paramRequest.getWebPage().getWebSite());
                //System.out.println("indexer:"+indexer);

                if(indexer!=null)
                {
                    String params = null;
                    if (sort != null) {
                        search.setAttribute("sort", sort);
                        params="&sort=" + sort;
                    }
                    
                    if(params != null) {
                        search.setAttribute("params", params);
                    }

                    SearchResults results = null;
                    if(sort == null) {
                        results = indexer.search(query, user);
                    } else {
                        String[] sortList = {SWBIndexer.ATT_INV + SWBIndexer.ATT_UPDATED};
                        results = indexer.search(query, user, sortList);
                    }
                    
                    max=results.size();
                    int pageSize=10;
                    try
                    {
                        pageSize=Integer.parseInt(getResourceBase().getAttribute("pageSize","10"));
                    }catch(Exception e){log.error(e);}
                    results.setPageLength(pageSize);
                    ipageLength=results.getPageLength();
                    search.setAttribute("size", ""+max);
                
                    Iterator<SearchDocument>it=results.listDocuments(ipage);
                    while(it.hasNext())
                    {
                        SearchDocument obj=it.next();
                        Searchable srch=obj.getSearchable();
                        GenericParser parser=indexer.getParser(srch);

                        Element eobj=doc.createElement("Object");
                        search.appendChild(eobj);
                        addElem(doc, eobj, "objTitle", parser.getTitle(srch, lang));
                        addElem(doc, eobj, "objId", srch.getURI());
                        addElem(doc, eobj, "objType", parser.getType(srch));
                        addElem(doc, eobj, "objClassName", srch.getClass().getName());
                        //addElem(doc, eobj, "objCategory", obj.getCategory());
                        //addElem(doc, eobj, "objTopicid", obj.getTopicID());
                        String summary=obj.getSummary();
                        if(summary==null)summary=parser.getSummary(srch, user.getLanguage());
                        addElem(doc, eobj, "objSummary", summary);
                        addElem(doc, eobj, "objUrl", parser.getUrl(srch));
                        addElem(doc, eobj, "objScore", (int)(obj.getScore()*100) + "%");
                        addElem(doc, eobj, "objUpdated", parser.getUpdated(srch));
                        addElem(doc, eobj, "navPath", parser.getPath(srch,lang));
                        if(parser.getImage(srch) != null && !parser.getImage(srch).equals("")) {
                            addElem(doc, eobj, "objImg", parser.getImage(srch));
                        }
                        addElem(doc, eobj, "objTypeLbl", parser.getTypeDisplayLabel(srch));
                    }
                }
            }

            int off=0;
            int seg=0;
            if(max>0)
            {
                off=(ipage * ipageLength)+1;
                seg=(ipage+1) * ipageLength;
                if(seg>max)seg=max;
            }
            search.setAttribute("off", "" + off);
            search.setAttribute("seg", "" + seg);

            if(off!= 0 || seg!=max)
            {
                search.setAttribute("bPagination", "true");
            }
            Element epagination=doc.createElement("pagination");
            search.appendChild(epagination);

            int pm=0;
            if(ipageLength>0)pm=(max-1)/ipageLength;
            int pi=ipage-5;if(pi<0)pi=0;
            int pf=ipage+5;if(pf>pm)pf=pm;

            if(ipage+1<=pf){
                search.setAttribute("nextPage", ""+(ipage+1));
            }
            if(ipage-1>=pi){
                search.setAttribute("backPage", ""+(ipage-1));
            }

            for(int x=pi;x<=pf;x++)
            {
                Element epage=doc.createElement("page");
                epagination.appendChild(epage);
                if(ipage==x) {
                    addElem(doc, epage, "actualPage", "1");
                }else{
                    addElem(doc, epage, "actualPage", "0");
                }
                addElem(doc, epage, "ipage", ""+x);
                addElem(doc, epage, "spageview", ""+(x+1));
            }

 
            return doc;
        }
        catch (Exception e)
        {
            log.error("Error while generating DOM in resource "+ getResourceBase().getResourceType().getId() +" with identifier " + getResourceBase().getId() + " - " + getResourceBase().getTitle(), e);
        }
        return null;
    }
    
    /**
     * Do xml.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void doXML(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, java.io.IOException
    {
        try {
            org.w3c.dom.Document dom=getDom(request, response, paramsRequest);
            if(dom!=null) response.getWriter().println(SWBUtils.XML.domToXml(dom));
        }
        catch(Exception e){ log.error(e); }
    }
    
    /**
     * Do view.
     * 
     * @param request the request
     * @param response the response
     * @param reqParams the req params
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    public void doView(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, SWBParamRequest reqParams) throws SWBResourceException, java.io.IOException
    {
        Resource base=getResourceBase();
        PrintWriter out = response.getWriter();
        if (client) {
            String queryString = request.getParameter("q");
            String wbSpage = request.getParameter("wbSpage");
            String wbSIpage = request.getParameter("wbSIpage");
            String map = request.getParameter("m");
            String q = "";
            if (queryString != null)
                q += "&q=" + URLEncoder.encode(queryString);
            if (wbSpage != null)
                q += "&wbSpage=" + wbSpage;
            if (wbSIpage != null)
                q += "&wbSIpage=" + wbSIpage;
            if (map != null)
                q += "&m=" + map;
            if (q.length() > 0)
                q = "?" + q.substring(1);
            try {
                String server = (String) SWBPlatform.getEnv("swb/serverURL");
                String uri=reqParams.getRenderUrl().setCallMethod(reqParams.Call_DIRECT).setMode(reqParams.Mode_VIEW).toString();
                log.debug(server + uri + q);
                java.net.URL url = new java.net.URL(server + uri + q);
                java.net.URLConnection con = url.openConnection();
                String content = SWBUtils.IO.readInputStream(con.getInputStream());
                out.print(content);
            }
            catch (Exception e)
            {
                log.error("Error while connecting to the server.", e);
            }
        }
        else {
            try {
                Document dom =getDom(request, response, reqParams);
                //System.out.println(AFUtils.getInstance().DomtoXml(dom));
                if(dom != null){
                    response.getWriter().print(SWBUtils.XML.transformDom(tpl, dom));
                }
            }
            catch(Exception e) { log.error(e); }
        }
    }
    
    
    /**
     * Adds the elem.
     * 
     * @param doc the doc
     * @param parent the parent
     * @param elemName the elem name
     * @param elemValue the elem value
     */
    private void addElem(Document doc, Element parent, String elemName, String elemValue) {
        if(elemValue!=null)
        {
            Element elem = doc.createElement(elemName);
            if(elemValue!=null){
                elem.appendChild(doc.createTextNode(elemValue));
            }
            parent.appendChild(elem);
        }
    }
}