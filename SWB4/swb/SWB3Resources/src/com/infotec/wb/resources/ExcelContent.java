/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * ExcelContent.java
 *
 * Created on 20 de junio de 2002, 16:38
 */

package com.infotec.wb.resources;

import java.io.*;
import javax.servlet.http.*;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Portlet;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.SWBResourceMgr;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.w3c.dom.*;


/** Objeto que despliega y administra un contenido de tipo Excel bajo ciertas
 * configuraciones.
 *
 * Object that unfolds and administers a content of Excel type under certain
 * configurations.
 * @author :Jorge Alberto Jim�nez(JAJS)
 * @version 1.0
 * @see com.infotec.wb.core.db.RecResource
 * @see com.infotec.wb.core.Resource
 */

public class ExcelContent extends GenericResource 
{
    
    private static Logger log = SWBUtils.getLogger(ExcelContent.class);
    
    WebSite topicmap;
    SWBResourceMgr RecM;
    
    /** Obtiene la versi�n html del recurso.
     *
     * @param paramsRequest
     * @param request El servlet container crea un objeto HttpServletRequest y
     *                      se pasa como argumento al m�todo del servlet.
     * @param response El servlet container crea un objeto HttpServletResponse y
     *                      se pasa como argumento al m�todo del servlet.
     * @throws IOException
     * @exception com.infotec.appfw.exception.AFException Si se origina cualquier error en el recurso al traer el html.
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {       
        
        if(paramRequest.getMode().equals("getcontent"))
        {
            doGetContent(request,response,paramRequest);
        }       
        else {
            super.processRequest(request,response,paramRequest);
        }                
    }
    
    public void doGetContent(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        OutputStream outStream = response.getOutputStream();
        Portlet base=getResourceBase();
        
        Document dom=SWBUtils.XML.xmlToDom(getResourceBase().getXml());
        if(dom==null){
            throw new SWBResourceException("Dom nulo");
        }
        try 
        {
            NodeList url=null;
            String surl=null;
            int lastVer = base.getLastVersion().getVersionNumber();
            int actualVersion = base.getActualVersion().getVersionNumber();
            if(paramRequest.getArguments().get("adm")!=null || request.getParameter("wblastversion")!=null || request.getAttribute("adm")!=null) 
            {
                url=dom.getElementsByTagName("url"+lastVer);
                surl=""+lastVer;
            }
            else if(request.getAttribute("wbresversion")!=null)
            {
                url=dom.getElementsByTagName("url"+request.getAttribute("wbresversion"));
                surl=(String)request.getAttribute("wbresversion");
            }
            else 
            {
                url=dom.getElementsByTagName("url"+actualVersion);
                surl=""+actualVersion;
            }
            if(url.getLength()==0) 
            {
                url=dom.getElementsByTagName("url");
            }
            if(url.getLength()>0) 
            {
                surl=surl+"/"+url.item(0).getChildNodes().item(0).getNodeValue();                
            }            
            surl= getResourceBase().getWorkPath()+"/"+surl;
            surl=surl.replaceAll(".html", ".xls");
            surl=surl.replaceAll(".htm", ".xls");            
            //surl=surl.replaceAll(WBUtils.getInstance().getWebPath()+"work","");    
            InputStream in=SWBPlatform.getFileFromWorkPath(surl);
            byte[] bcont=new byte[51200];
            response.setContentType("application/vnd.ms-excel");
            int retBytes=in.read(bcont);
            while(retBytes!=-1)
            {                
                outStream.write(bcont,0,retBytes);
                retBytes=in.read(bcont);
            }
            in.close();
            outStream.close();
        }
        catch(Exception e)
        {
            try
            {
                PrintWriter out = response.getWriter();
                out.println(SWBUtils.TEXT.getLocaleString("locale_wb2_resources","usrmsg_Content_getHtml_msgContenidoFueraServicio")  );
                out.println("<nocache/>");
                log.error(SWBUtils.TEXT.getLocaleString("locale_wb2_resources","errormsg_Content_getHtml_msgErrorRecursoContent")  +e.getMessage(), e);
            }
            catch(Exception ue)
            {
                
            }
        }
    }
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        Portlet base=getResourceBase();
        PrintWriter out = response.getWriter();
        Document dom=SWBUtils.XML.xmlToDom(base.getXml());
        if(dom==null){
            throw new SWBResourceException("Dom nulo");
        }
        out.println("<!-- <content id=\""+base.getId()+"\"/> -->");
        try {
            NodeList url=null;
            String surl=null;
            int lastVer = base.getLastVersion().getVersionNumber();
            int actualVersion = base.getActualVersion().getVersionNumber();
            
            if(paramRequest.getArguments().get("adm")!=null || request.getParameter("wblastversion")!=null || request.getAttribute("adm")!=null) {
                url=dom.getElementsByTagName("url"+lastVer);
                surl=""+lastVer;
            }else if(request.getAttribute("wbresversion")!=null){
                url=dom.getElementsByTagName("url"+request.getAttribute("wbresversion"));
                surl=(String)request.getAttribute("wbresversion");
            }
            else {
                url=dom.getElementsByTagName("url"+actualVersion);
                surl=""+actualVersion;
            }
            if(url.getLength()==0) {
                url=dom.getElementsByTagName("url");
            }
            if(url.getLength()>0) {
                surl=surl+"/"+url.item(0).getChildNodes().item(0).getNodeValue();
                
                InputStream istream = SWBPlatform.getFileFromWorkPath(getResourceBase().getWorkPath() + "/" + surl);
                String scontent = null;
                if (istream != null) {
                    scontent = SWBUtils.IO.readInputStream(istream);
                }
                String userAgent=request.getHeader("User-Agent");                               
                if(base.getAttribute("mode","web").equals("web") || userAgent.toLowerCase().indexOf("msie")==-1)
                {
                    out.println(scontent);                    
                }
                else
                {
                    SWBResourceURL urlGetContent= paramRequest.getRenderUrl();
                    urlGetContent.setMode("getcontent");                    
                    urlGetContent.setCallMethod(SWBResourceURL.Call_DIRECT);
                    out.print("<IFRAME ID=\"WBIFrame_"+base.getId()+"\" SRC=\""+ urlGetContent.toString() + "\"");
                    out.print(" width=\""+base.getAttribute("width", "100%").trim() +"\"");
                    out.print(" height=\""+base.getAttribute("height", "500").trim() +"\"");
                    out.print(" marginwidth=\""+base.getAttribute("marginwidth", "0").trim() +"\"");
                    out.print(" marginheight=\""+base.getAttribute("marginheight", "0").trim() +"\"");                    
                    out.print(" scrolling=\""+base.getAttribute("scrollbars", "auto").trim() +"\"");
                    out.print(" frameborder=\""+base.getAttribute("frameborder", "0").trim() +"\"");                    
                    out.println(">");                    
                    out.println("</IFRAME>");  
                }
            }
        }catch(Exception e) {
            out.println(SWBUtils.TEXT.getLocaleString("locale_wb2_resources","usrmsg_Content_getHtml_msgContenidoFueraServicio")  );
            out.println("<nocache/>");
            log.error(SWBUtils.TEXT.getLocaleString("locale_wb2_resources","errormsg_Content_getHtml_msgErrorRecursoContent")  +e.getMessage(), e);
        }
    }
    
    
    @Override
    public void doIndex(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        response.setContentType("text/html");
        Portlet base=getResourceBase();
        Document dom=SWBUtils.XML.xmlToDom(getResourceBase().getXml());
        if(dom==null){
            throw new SWBResourceException("Dom nulo");
        }
        try {
            NodeList url=null;
            String surl=null;
            int lastVer = base.getLastVersion().getVersionNumber();
            int actualVersion = base.getActualVersion().getVersionNumber();
            
            url=dom.getElementsByTagName("url"+actualVersion);
            surl=""+actualVersion;
            
            if(url.getLength()==0) {
                url=dom.getElementsByTagName("url");
            }
            if(url.getLength()>0) {
                surl=surl+"/"+url.item(0).getChildNodes().item(0).getNodeValue();
                /* TODO: VER 4.0
                com.infotec.wb.core.indexer.WBIndexer wbIndexer=WBIndexMgr.getInstance().getTopicMapIndexer(paramsRequest.getResourceBase().getTopicMap().getId());
                if(wbIndexer!=null)
                {
                    File file=new File(WBUtils.getInstance().getWorkPath()+paramsRequest.getResourceBase().getResourceWorkPath()+"/"+surl);
                    if(file!=null){
                        wbIndexer.indexFile(file,paramsRequest.getTopic().getUrl(),paramsRequest.getTopic(),paramsRequest.getResourceBase().getId());
                    }
                }
                 * */
            }
        }catch(Exception e) {
            log.error("Error while indexing content with id:"+base.getId()+",in Content/doIndex()", e);
        }
        super.doIndex(request, response, paramRequest);        
    }
    
    
    
    /**
     * Obtiene la versi�n html de la administraci�n del recurso.
     * @param paramsRequest
     * @param request El servlet container crea un objeto HttpServletRequest y
     *                      se pasa como argumento al m�todo del servlet.
     * @param response El servlet container crea un objeto HttpServletResponse y
     *                      se pasa como argumento al m�todo del servlet.
     * @throws IOException
     * @exception com.infotec.appfw.exception.AFException Si se origina cualquier error en el recurso al traer el html de la administraci�n.
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        out.println("Este recurso no es administrable..");
    }
    
}