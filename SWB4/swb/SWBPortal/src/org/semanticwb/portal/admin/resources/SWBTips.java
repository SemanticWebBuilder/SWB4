/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.resources.RSSResource;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author Jorge Jiménez
 */
public class SWBTips extends GenericAdmResource
{
    private static Logger log = SWBUtils.getLogger(RSSResource.class);
    private ArrayList swbNews=new ArrayList();
    /**
     * @param request
     * @param response
     * @param reqParams
     * @throws AFException
     * @throws IOException
     */
    public org.w3c.dom.Document getDom(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramReq) throws SWBResourceException, IOException
    {
        Resource base = getResourceBase();
        try
        {
            URL url = new URL(base.getAttribute("url","").trim());
            URLConnection urlconn = url.openConnection();
            InputStream is = urlconn.getInputStream();
            String rss=SWBUtils.IO.readInputStream(is);
            Document dom = SWBUtils.XML.xmlToDom(rss);
            return dom;
        }
        catch (Exception e) {
            log.error("Error while generating DOM in resource: "+base.getId() +"-"+ base.getTitle(), e);
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
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramReq) throws SWBResourceException, java.io.IOException
    {
        response.setContentType("text/html; charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        try
        {
            Document dom = getDom(request, response, paramReq);
            if(dom != null) {
                NodeList nListItems=dom.getElementsByTagName("item");
                for(int i=0;i<nListItems.getLength();i++){
                    NodeList itemChilds=nListItems.item(i).getChildNodes();
                    for(int y=0;y<itemChilds.getLength();y++){
                        if(itemChilds.item(y).getNodeName().equals("description")){
                            swbNews.add(itemChilds.item(y).getFirstChild().getNodeValue());
                        }
                    }
                }
            }
           if(swbNews.size()>0){
            int randomNumber=(int)(Math.random()*swbNews.size());
                out.println(swbNews.get(randomNumber));
           }else{
                out.println(paramReq.getLocaleString("default"));
           }
        }
        catch (Exception e) {
            log.error("Error while processing RSS for: "+getResourceBase().getAttribute("url"), e);
        }
    }
}
