/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.social.Rss;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author jorge.jimenez
 */
public class RssReview extends GenericResource{
   
    private Logger log = SWBUtils.getLogger(StreamInBox.class);
    
    /**
     * Gets the dom.
     * 
     * @param request the request
     * @param response the response
     * @param paramReq the param req
     * @return the dom
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    public org.w3c.dom.Document getDom(Rss rss) throws SWBResourceException, IOException
    {
        try
        {
            URL url = new URL(rss.getRss_URL().trim());
            URLConnection urlconn = url.openConnection();
            InputStream is = urlconn.getInputStream();
            String rssStr=SWBUtils.IO.readInputStream(is);
            Document dom = SWBUtils.XML.xmlToDom(rssStr);
            return dom;
        }
        catch (Exception e) {
            log.error("Error while generating DOM in resource: "+rss, e);
        }
        return null;
    }
    
    /**
     * Do view.
     * 
     * @param request the request
     * @param response the response
     * @param paramReq the param req
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramReq) throws SWBResourceException, java.io.IOException
    {
        String suri=request.getParameter("suri");
        if(suri==null) return;
        
        SemanticObject semObj=SemanticObject.getSemanticObject(suri);
        
        Rss rss=null;
        try{
            rss=(Rss)semObj.getGenericInstance();
        }catch(Exception ignored)
        {
            return;
        }
        if(rss==null || rss.getRss_URL()==null || rss.getTags()==null || rss.getTags().trim().length()==0) return;
        
        ArrayList<String> amsgWords=new ArrayList();
        String[] msgWords=rss.getTags().split(",");
        for(int i=0;i<msgWords.length;i++)
        {
            String msgWord=msgWords[i];
            if(msgWord!=null && msgWord.length()>0)
            {
                amsgWords.add(msgWord.toLowerCase());
            }
        }
        
        response.setContentType("text/html; charset=iso-8859-1");
        ArrayList swbNews=new ArrayList();
        PrintWriter out = response.getWriter();
        try
        {
            Document dom = getDom(rss);
            if(dom != null) 
            {
                NodeList nListItems=dom.getElementsByTagName("item");
                for(int i=0;i<nListItems.getLength();i++){
                    NodeList nListItemChilds=nListItems.item(i).getChildNodes();
                    for(int j=0;j<nListItemChilds.getLength();j++)
                    {
                        Node node=nListItemChilds.item(j);
                        if(node.getNodeName().equalsIgnoreCase("title") || node.getNodeName().equalsIgnoreCase("description"))
                        {
                            for(int k=0;k<amsgWords.size();k++)
                            {
                                String word=amsgWords.get(k);
                                if(node.getFirstChild().getNodeValue().toLowerCase().indexOf(word)>-1)
                                {
                                    ComunityNews comNews=new ComunityNews();
                                    comNews.setNode(nListItems.item(i));
                                    swbNews.add(comNews);
                                }
                            }
                        }
                    }
                }
                out.println("<div class=\"rssContent\">");
                out.println("<p class=\"rssResult\"><em>Resultados en:</em>"+rss.getRss_URL()+"</p>");
                out.println("<p class=\"rssWords\"><em>Con las palabras:</em><strong>"+rss.getTags()+"</strong></p>");
                out.println("<ul>");    
                Iterator <ComunityNews> itNews=swbNews.iterator();
                if(!itNews.hasNext())
                {
                    out.println("<br><br><br>NO SE ENCONTRARON RESULTADOS");
                }
                while(itNews.hasNext()){
                    ComunityNews comNew=itNews.next();
                    out.println("<li>");
                    String pubDate="";
                    if(comNew.getPubDate()!=null && comNew.getPubDate().trim().length()>0) pubDate=comNew.getPubDate();
                    if(comNew.getTitle()!=null) out.println("<span class=\"rssTitle\"><em>"+pubDate+"</em> "+SWBUtils.TEXT.encode(comNew.getTitle(), "iso8859-1")+"</span>");
                    if(comNew.getDescription()!=null) out.println("<span class=\"rssDescr\">"+SWBUtils.TEXT.encode(comNew.getDescription(), "iso8859-1")+"</span>");
                    if(comNew.getLink()!=null) out.println("<span class=\"rssLink\"><a target=\"_new\" href=\""+comNew.getLink()+"\">"+comNew.getLink()+"</a></span>");
                    if(comNew.getGuid()!=null) out.println("<span class=\"rssGuid\"><a target=\"_new\" href=\""+comNew.getGuid()+"\">"+comNew.getGuid()+"</a></span>");
                    out.println("</li>");
                }
                out.println("</ul>");
                out.println("</div>");
            }
        }
        catch (Exception e) {
            log.error("Error while processing RSS for: "+rss, e);
        }
    }
    
}

  class ComunityNews {

    String title;
    String description;
    String link;
    String guid;
    String pubDate;

    ComunityNews(){
        title=null;
        description=null;
        link=null;
        guid=null;
        pubDate=null;
    }

    public void setNode(Node itemNode){
        NodeList nChilds=itemNode.getChildNodes();
        for(int i=0;i<=nChilds.getLength();i++){
            if(nChilds.item(i)!=null)
            {
                if(nChilds.item(i).getNodeName().equalsIgnoreCase("title")){
                    setTitle(nChilds.item(i).getFirstChild().getNodeValue());
                }else if(nChilds.item(i).getNodeName().equalsIgnoreCase("description")){
                    setDescription(nChilds.item(i).getFirstChild().getNodeValue());
                }else if(nChilds.item(i).getNodeName().equalsIgnoreCase("link")){
                    setLink(nChilds.item(i).getFirstChild().getNodeValue());
                }else if(nChilds.item(i).getNodeName().equalsIgnoreCase("guid")){
                    setGuid(nChilds.item(i).getFirstChild().getNodeValue());
                }else if(nChilds.item(i).getNodeName().equalsIgnoreCase("pubDate")){
                    setPubDate(nChilds.item(i).getFirstChild().getNodeValue());
                }
            }
        }
    }

    public void setTitle(String title){
        this.title=title;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public void setLink(String link){
        this.link=link;
    }
    public void setGuid(String guid){
        this.guid=guid;
    }
    public void setPubDate(String pubDate){
        this.pubDate=pubDate;
    }

    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public String getLink(){
        return link;
    }
    public String getGuid(){
        return guid;
    }
    public String getPubDate(){
        return pubDate;
    }
}
