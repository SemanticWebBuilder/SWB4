/*
 * WBSearch.java
 *
 * Created on 28 de junio de 2006, 02:05 PM
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
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.indexer.SWBIndexObj;
import org.semanticwb.portal.indexer.SWBIndexObjList;
import org.semanticwb.portal.indexer.SWBIndexer;


/**
 *
 * @author Javier Solis Gonzalez,
 * @modified by Jorge A. Jim�nez
 */
public class WBSearch extends GenericAdmResource
{
    private static Logger log=SWBUtils.getLogger(WBSearch.class);
    
    javax.xml.transform.Templates tpl;
    String path = SWBPlatform.getContextPath() +"/swbadmin/xsl/WBSearch/";
    private boolean client = false;
    
    /** Creates a new instance of FullTextSearch */
    public WBSearch() 
    {
        client=SWBPlatform.isClient();
    }
    
    
    /**
     * Asigna la informaci�n de la base de datos al recurso.
     *
     * @param     base  La información del recurso en memoria.
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
                    tpl = SWBUtils.XML.loadTemplateXSLT(SWBPlatform.getFileFromWorkPath(base.getWorkPath() +"/"+ base.getAttribute("template").trim()));
                    path= SWBPlatform.getWebWorkPath() +  base.getWorkPath() + "/";
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
     * @param request
     * @param response
     * @param reqParams
     * @throws AFException
     * @throws IOException
     */
    public org.w3c.dom.Document getDom(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, SWBParamRequest reqParams) throws SWBResourceException, IOException
    {
        //System.out.println("getDom");
        try {
            Document doc = SWBUtils.XML.getNewDocument();
            WebSite tm = reqParams.getWebPage().getWebSite();
            
            String lang=reqParams.getUser().getLanguage();

            HashMap args = new HashMap();
            args.put("separator", " | ");
            args.put("links", "false");
            args.put("language", lang);
            
            Element search = doc.createElement("SEARCH");
            doc.appendChild(search);
            search.setAttribute("path", path);
            
            
            int ipage=0,ipindex=0;
            String q = request.getParameter("q");
            if(request.getParameter("wbSpage")!=null) ipage=Integer.parseInt(request.getParameter("wbSpage"));
            if(request.getParameter("wbSIpage")!=null) ipindex=Integer.parseInt(request.getParameter("wbSIpage"));
            ArrayList aPages=new ArrayList();
            
            int max=0;
            int i = 0;        //contador del segmento
            int ipageLength=0;
            if (q != null && q.trim().length()>0) {
                search.setAttribute("words", q);
                search.setAttribute("wordsEnc", java.net.URLEncoder.encode(q));
                search.setAttribute("work", SWBPlatform.getWebWorkPath());
                search.setAttribute("url", reqParams.getWebPage().getUrl());
                
                String smap=reqParams.getResourceBase().getAttribute("amaps","0");
                //System.out.println("amaps:"+reqParams.getResourceBase().getAttribute("amaps","0"));
                SWBIndexObj wbIndexObj=null;
                if(!smap.trim().equals("1")){
                    String stpini=null;
                    wbIndexObj=new SWBIndexObj();
                    wbIndexObj.setTopicMapID(reqParams.getResourceBase().getWebSiteId());
                    stpini=reqParams.getResourceBase().getAttribute("tpini","");
                    if(stpini.trim().length()>0){
                        wbIndexObj.setCategory(stpini);
                    }
                }
                
                SWBIndexer indexer=SWBPortal.getIndexMgr().getTopicMapIndexer(reqParams.getWebPage().getWebSiteId());
                //System.out.println("indexer:"+indexer);

                if(indexer!=null)
                {
                    SWBIndexObjList list=indexer.search(q,wbIndexObj,reqParams.getUser(),ipage,ipindex);
                    max=list.getHits();
                    ipageLength=list.getPageLength();
                    search.setAttribute("size", ""+max);
                
                    String st = "";
                    HashMap arg = new HashMap();
                    arg.put("separator", " | ");
                    arg.put("links", "false");
                    arg.put("language", reqParams.getUser().getLanguage());

                    int apage=-1;
                    Iterator it=list.iterator();
                    while(it.hasNext()) {
                        SWBIndexObj obj=(SWBIndexObj)it.next();
                        //System.out.println("WBIndexObj:"+obj.getPage()+" "+obj.getPageIndex());
                        if(apage!=obj.getPage()){
                            apage=obj.getPage();
                            aPages.add(obj);
                        }
                        if(ipage==obj.getPage()){
                            i++;
                            Element eobj=doc.createElement("Object");
                            search.appendChild(eobj);
                            addElem(doc, eobj, "objTitle", obj.getTitle());
                            addElem(doc, eobj, "objId", obj.getId());
                            addElem(doc, eobj, "objType", obj.getType());
                            addElem(doc, eobj, "objCategory", obj.getCategory());
                            addElem(doc, eobj, "objTopicid", obj.getTopicID());
                            addElem(doc, eobj, "objSummary", obj.getSummary());
                            addElem(doc, eobj, "objUrl", obj.getUrl());
                            addElem(doc, eobj, "objScore", (int)(obj.getScore()*100) + "%");
                            if(obj.getTopicMapID()!=null && obj.getTopicID()!=null){
                                WebSite objTM=SWBContext.getWebSite(obj.getTopicMapID());
                                WebPage tp=objTM.getWebPage(obj.getTopicID());
                                if(tp!=null){
                                    st=tp.getPath(arg);
                                    addElem(doc, eobj, "navPath", st);
                                }
                            }
                        }
                    }
                }
            }
            
            search.setAttribute("seg", "" + ((ipage * ipageLength) + i));
            if (max == 0) search.setAttribute("off", "0");
            else {
                if(ipage>0 && i<ipageLength){
                    search.setAttribute("off", "" + ((ipage * ipageLength) + 1));
                }else{
                    search.setAttribute("off", "" + ((ipage * i) + 1));
                }
            }
            
            if(aPages.size()>1){
                search.setAttribute("bPagination", "1");
            }
            boolean bnext=false,bback=false;
            Element epagination=doc.createElement("pagination");
            search.appendChild(epagination);
            Iterator itPages=aPages.iterator();
            while(itPages.hasNext()){
                SWBIndexObj obj=(SWBIndexObj)itPages.next();
                Element epage=doc.createElement("page");
                epagination.appendChild(epage);
                if(ipage==obj.getPage()) {
                    addElem(doc, epage, "actualPage", "1");
                }else{
                    addElem(doc, epage, "actualPage", "0");
                }
                if(obj.getPage()>ipage && !bnext){
                    search.setAttribute("nextPage", ""+obj.getPage());
                    search.setAttribute("nextPIndex", ""+obj.getPageIndex());
                    bnext=true;
                }
                if(obj.getPage()+1==ipage && !bback){
                    search.setAttribute("backPage", ""+obj.getPage());
                    search.setAttribute("backPIndex", ""+obj.getPageIndex());
                    bback=true;
                }
                String spageview=""+(obj.getPage()+1);
                addElem(doc, epage, "ipage", ""+obj.getPage());
                addElem(doc, epage, "spageview", spageview);
                addElem(doc, epage, "ipindex", ""+obj.getPageIndex());
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
     * @param request
     * @param response
     * @param reqParams
     * @throws AFException
     * @throws IOException
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
     * @param request
     * @param response
     * @param reqParams
     * @throws AFException
     * @throws IOException
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
     * @param doc
     * @param parent
     * @param elemName
     * @param elemValue
     */
    private void addElem(Document doc, Element parent, String elemName, String elemValue) {
        Element elem = doc.createElement(elemName);
        if(elemValue!=null){
            elem.appendChild(doc.createTextNode(elemValue));
        }
        parent.appendChild(elem);
    }
    
}