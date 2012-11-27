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
package org.semanticwb.portal.admin.resources;

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
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.resources.RSSResource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBNews.
 * 
 * @author Jorge Jiménez
 */
public class SWBNews extends GenericAdmResource
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(RSSResource.class);
    
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
    public org.w3c.dom.Document getDom(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramReq) throws SWBResourceException, IOException
    {
        Resource base = getResourceBase();
        try
        {
            URL url = new URL(base.getAttribute("url","").trim());
            URLConnection urlconn = url.openConnection();
            InputStream is = urlconn.getInputStream();
            String rss=SWBUtils.IO.readInputStream(is);
            Document dom = SWBUtils.XML.xmlToDom(SWBUtils.TEXT.encode(rss,"utf-8"));
            return dom;
        }
        catch (Exception e) {
            //log.error("Error while generating DOM in resource: "+base.getId() +"-"+ base.getTitle(), e);
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
        response.setContentType("text/html; charset=iso-8859-1");
        ArrayList swbNews=new ArrayList();
        PrintWriter out = response.getWriter();
        try
        {
            Document dom = getDom(request, response, paramReq);
            if(dom != null) {
                NodeList nListItems=dom.getElementsByTagName("item");
                for(int i=0;i<nListItems.getLength();i++){
                    ComunityNews comNews=new ComunityNews();
                    comNews.setNode(nListItems.item(i));
                    swbNews.add(comNews);
                }
            }
            out.println("<ul>");
  	        Iterator <ComunityNews> itNews=swbNews.iterator();
            while(itNews.hasNext()){
                ComunityNews comNew=itNews.next();
                out.println("<li><a target=\"_new\" href=\""+comNew.getLink()+"\">");
                out.println("   <span>"+comNew.getPubDate()+"</span><br>");
                out.println(    comNew.getTitle() );
                out.println("</a></li>");
            }
            out.println("</ul>");
        }
        catch (Exception e) {
            //log.error("Error while processing RSS for: "+getResourceBase().getAttribute("url"), e);
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
