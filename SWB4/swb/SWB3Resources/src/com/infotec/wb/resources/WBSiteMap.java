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


package com.infotec.wb.resources;

import java.io.IOException;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Portlet;
import org.semanticwb.model.User;
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
    
    javax.xml.transform.Templates tpl; 
    String webWorkPath = "/work";  
    Vector vTopic = new Vector();
    int intMaxLevel = 1;
    String path = SWBPlatform.getContextPath() +"swbadmin/xsl/WBSiteMap/";

    public WBSiteMap() {
    }

    /**
     * @param base
     */    
    @Override
    public void setResourceBase(Portlet base)
    {
        try 
        {
            super.setResourceBase(base);
            webWorkPath = (String) SWBPlatform.getWebWorkPath() +  base.getWorkPath();            
        }
        catch(Exception e) { log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);  }
        if(!"".equals(base.getAttribute("template","").trim()))
        {
            try 
            { 
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPlatform.getFileFromWorkPath(base.getWorkPath()+"/"+ base.getAttribute("template").trim())); 
                path=webWorkPath + "/";
            }
            catch(Exception e) { log.error("Error while loading resource template: "+base.getId(), e); }
        }
        if(tpl==null)
        {
            try { tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/WBSiteMap/WBSiteMap.xslt")); } 
            catch(Exception e) { log.error("Error while loading default resource template: "+base.getId(), e); }
        } 
    }

    /**
     * @param request
     * @param response
     * @param reqParams
     * @throws AFException
     * @throws IOException
     */
    public org.w3c.dom.Document getDom(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        StringBuffer ret = new StringBuffer("");
        User user=paramRequest.getUser();
        Portlet base=getResourceBase();
        try
        {
            WebSite tm = paramRequest.getTopic().getWebSite();
            WebPage tpsite = null;
            int intLevel = 1, intWidth = 10;
            
            HashMap list= new HashMap();
            String id="";
            if (request.getParameter("id") != null && !"".equals(request.getParameter("id").trim()))
            {
                id=request.getParameter("id").trim();
                list=setList(tm, id.split(";"));
            }
            if (!"".equals(base.getAttribute("level", "").trim()))
            {
                try { intMaxLevel = Integer.parseInt(base.getAttribute("level", "1").trim()); } 
                catch (Exception e) { intMaxLevel=1; } 
            }
            if (!"".equals(base.getAttribute("width", "").trim()))
            {
                try { intWidth = Integer.parseInt(base.getAttribute("width", "10").trim()); } 
                catch (Exception e) { intWidth=10; } 
            }
            if (!"".equals(base.getAttribute("home", "").trim())) {
                tpsite = tm.getWebPage(base.getAttribute("home").trim());
            }
            if (tpsite == null) {
                tpsite = tm.getHomePage();
            }
            
            Document dom = SWBUtils.XML.getNewDocument();
            Element emn = dom.createElement("mapa");
            if (!"".equals(base.getAttribute("title", "").trim())) {
                emn.setAttribute("title", base.getAttribute("title").trim());
            }
            emn.setAttribute("path", path);
            dom.appendChild(emn);

            //if (user.haveAccess(tpsite)) TODO: VER4
            {
                SWBResourceURL url=paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW);
                url.setParameter("smp_act", "smp_step2");
                Vector mapPath=null;
            
                String lang = user.getLanguage();
                Iterator <WebPage> it = tpsite.listChilds(lang, true, false, false, null);
                while (it.hasNext())
                {
                    intLevel = 1;
                    WebPage tp = it.next();
                    //if (tp.getId() != null && user.haveAccess(tp)) //TODO VER 4.0
                    if (tp.getId() != null)
                    {
                        emn = dom.createElement("padre");
                        emn.setAttribute("url", tp.getUrl());
                        emn.setAttribute("nombre", tp.getTitle(lang));
                        if(tp.listChilds().hasNext())
                        {
                            emn.setAttribute("mas", "1");
                            if(intLevel < intMaxLevel || list.containsKey(tp)) 
                            {
                                emn.setAttribute("mas", "2");
                                getChilds(list, id, tp, emn, intLevel + 1, intWidth, paramRequest);
                            }
                            else  
                            {
                                Iterator it2=list.keySet().iterator();
                                while(it2.hasNext())
                                {
                                    mapPath = (Vector)list.get((WebPage)it2.next());
                                    if(mapPath!=null && mapPath.contains(tp.getId())) 
                                    {
                                        emn.setAttribute("mas", "2");
                                        getChilds(list, id, tp, emn, intLevel + 1, intWidth, paramRequest);
                                        break;
                                    }
                                }
                            }
                        }
                        else {
                            emn.setAttribute("mas", "0");
                        }
                        if("2".equals(emn.getAttribute("mas"))) {
                            url.setParameter("id", subList(tp, lang, id));
                        }
                        else if("1".equals(emn.getAttribute("mas"))) 
                        {
                            if(!list.containsKey(tp)) {
                                url.setParameter("id", id+(!"".equals(id) ? ";" :"")+tp.getId());
                            }
                            else {
                                url.setParameter("id", id);
                            }
                        }
                        emn.setAttribute("urlmapa", url.toString());
                        dom.getFirstChild().appendChild(emn);                        
                    }
                }
            }
            return dom;
        }
        catch (Exception e) { log.error("Error while generating DOM in resource "+ base.getPortletType().getPortletClassName() +" with identifier " + base.getId() + " - " + base.getTitle(), e); }
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
    public void doXML(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        try
        {
            Document dom=getDom(request, response, paramRequest);
            if(dom!=null) {
                response.getWriter().println(SWBUtils.XML.domToXml(dom));
            }
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
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        StringBuffer ret = new StringBuffer("");
        Portlet base=getResourceBase();
        
        if(paramRequest.getCallMethod()==paramRequest.Call_STRATEGY)
        { // Objeto (imagen/bot�n) para invocar el mapa de sitio   
            String surl="";
            if (!"".equals(base.getAttribute("url", "").trim())) {
                surl=base.getAttribute("url").trim();
            }
            else 
            {
                SWBResourceURL url=paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW);
                url.setParameter("smp_act", "smp_step2");
                surl=url.toString();
            }
            if (!"".equals(base.getAttribute("img", "").trim()))
            {
                ret.append("\n<a href=\"" + surl +"\">");
                ret.append("<img src=\""+ webWorkPath +"/"+ base.getAttribute("img").trim() +"\"");
                if (!"".equals(base.getAttribute("alt", "").trim())) {
                    ret.append(" alt=\"" + base.getAttribute("alt").trim() + "\"");
                }
                ret.append(" border=0></a>");
            } 
            else if (!"".equals(base.getAttribute("btntexto", "").trim()))
            {
                ret.append("\n<form name=frmWBSiteMap method=POST action=\"" + surl + "\">");
                ret.append("\n<input type=submit name=btnWBSiteMap value=");
                ret.append("\"" + base.getAttribute("btntexto").trim().replaceAll("\"","&#34;") + "\"");
                if (!"".equals(base.getAttribute("blnstyle", "").trim())) {
                    ret.append(" style=\"" + base.getAttribute("blnstyle").trim().replaceAll("\"","&#34;") + "\"");
                }
                ret.append("></form>");
            } 
            else
            {
                ret.append("\n<a href=\"" + surl +"\"");
                if (!"".equals(base.getAttribute("blnstyle", "").trim())) {
                    ret.append(" style=\"" + base.getAttribute("blnstyle").trim().replaceAll("\"","&#34;") + "\"");
                }
                ret.append(">");
                if (!"".equals(base.getAttribute("lnktexto", "").trim())) {
                    ret.append(base.getAttribute("lnktexto").trim());
                }
                else ret.append(paramRequest.getLocaleString("msgSiteMap"));
                ret.append("</a>");
            }
        }
        else 
        { // Mapa de sitio
            try
            {
                Document dom =getDom(request, response, paramRequest);
                //System.out.println(AFUtils.getInstance().DomtoXml(dom));
                if(dom != null) {
                    ret.append(SWBUtils.XML.transformDom(tpl, dom));
                }
            }
            catch(Exception e){ log.error(e); }
        }
        response.getWriter().print(ret.toString());
    }    

    /**
     * Obtiene los t�picos hijo relacionados al t�pico solicitado bajo una estructura jer�rquica.
     *
     * @param     list      T�picos seleccionados:
     *                          tpid - Objeto Topic.
     *                          mapPath - El vector que almacena los t�picos padre de tpid.
     * @param     id        Lista separada por comas de los identificadores de los t�picos seleccionados.
     * @param     tpc       El t�pico hijo de acuerdo al nivel de recursividad presente en la generaci�n
     *                      de la estructura jer�rquica.
     * @param     emn       El elemento del DOM al cual se agregar�n los t�picos hijo subyacentes.
     * @param     intLevel  El nivel de recursividad presente.
     * @param     intWidth  El espacio en pixeles para definir la separaci�n entre los difentes niveles
     *                      de la estructura jer�rquica con respecto al margen izquierdo.
     * @param     reqParams
     * @see       com.infotec.topicmaps.Topic
     */
    public void getChilds(HashMap list, String id, WebPage tpc, Element emn, int intLevel, int intWidth, SWBParamRequest reqParams)
    {
        User user=reqParams.getUser();
        SWBResourceURL url=reqParams.getRenderUrl().setMode(reqParams.Mode_VIEW);
        url.setParameter("smp_act", "smp_step2");
        Vector mapPath=null;
        
        String lang = user.getLanguage();
        Iterator <WebPage> it = tpc.listChilds(lang, true, false, false, null);
        while (it.hasNext())
        {
            WebPage tpsub = it.next();
            //if (tpsub.getId() != null && user.haveAccess(tpsub)) TODO: VER 4
            if (tpsub.getId() != null)
            {
                if (vTopic.contains(tpsub)) break;
                vTopic.addElement(tpsub);

                Element hijo = emn.getOwnerDocument().createElement("hijo");
                for (int i = 0; i < intLevel - 1; i++)
                {
                    Element hijospc = emn.getOwnerDocument().createElement("hijospc");
                    hijospc.setAttribute("width", String.valueOf(intWidth));
                    hijo.appendChild(hijospc);
                }
                hijo.setAttribute("url", tpsub.getUrl());
                hijo.setAttribute("nombre", tpsub.getDisplayName(lang));
                emn.appendChild(hijo);                
                
                if(tpsub.listChilds().hasNext())
                {
                    hijo.setAttribute("mas", "1");
                    if(intLevel < intMaxLevel || list.containsKey(tpsub)) 
                    {
                        hijo.setAttribute("mas", "2");
                        getChilds(list, id, tpsub, emn, intLevel + 1, intWidth, reqParams);
                    }
                    else  
                    {
                        Iterator it2=list.keySet().iterator();
                        while(it2.hasNext())
                        {
                            mapPath = (Vector)list.get((WebPage)it2.next());
                            if(mapPath!=null && mapPath.contains(tpsub.getId())) 
                            {
                                hijo.setAttribute("mas", "2");
                                getChilds(list, id, tpsub, emn, intLevel + 1, intWidth, reqParams);
                                break;
                            }
                        }
                    }
                }
                else {
                    hijo.setAttribute("mas", "0");
                }
                if("2".equals(hijo.getAttribute("mas"))) {
                    url.setParameter("id", subList(tpsub, lang, id));
                }
                else if("1".equals(hijo.getAttribute("mas"))) 
                {
                    if(!list.containsKey(tpsub)) {
                        url.setParameter("id", id+(!"".equals(id) ? ";" :"")+tpsub.getId());
                    }
                    else {
                        url.setParameter("id", id);
                    }
                }
                hijo.setAttribute("urlmapa", url.toString());
                vTopic.removeElement(tpsub);
            }
        }
    }

    /**
     * @param tm
     * @param id
     * @return
     * @see       com.infotec.topicmaps.Topic
     */     
    private HashMap setList(WebSite tm, String[] id)
    {
        HashMap list = new HashMap();
        if(id!=null && id.length > 0)
        {
            WebPage tp=null;
            Vector mapPath=null;
            for(int i=0; i < id.length; i++)
            {
                tp = tm.getWebPage(id[i]);
                if(tp!=null)
                {
                    mapPath = getMapPath(tp);
                    list.put(tp, mapPath);
                }
            }            
        }
        return list;
    }
    
    /**
     * @param tp
     * @param lang
     * @param id
     * @return
     * @see       com.infotec.topicmaps.Topic
     */     
    private String subList(WebPage tp, String lang, String id)
    {
        String list="";
        if(tp!=null && (id!=null && !"".equals(id.trim())))
        {
            list=id.trim()+";";
            if(!list.endsWith(";")) {
                list+=";";
            }
            list=list.replaceFirst(tp.getId()+";","");
            Iterator <WebPage> it = tp.listChilds(lang, true, false, false, null);
            while (it.hasNext())
            {
                WebPage tpsub =  it.next();
                if(tpsub != null) 
                {
                    if(tpsub.listChilds().hasNext()) {
                        list=subList(tpsub, lang, list);
                    }
                    else  {
                        list=list.replaceFirst(tpsub.getId()+";","");
                    }
                }
            } 
            if(list.endsWith(";")) {
                list=list.substring(0, list.length()-1);
            }
        }
        return list;
    }

    /**
     * Calcula los t�picos padre de un t�pico.
     *
     * @param     tpid      El t�pico que solicita el recurso.
     * @return    Regresa un objeto Vector que contiene los t�picos padre del t�pico requerido.
     * @see       com.infotec.topicmaps.Topic
     */
    private Vector getMapPath(WebPage tpid)
    {
        Vector vctPath = new Vector();
        if (tpid.getWebSite().getHomePage() != tpid)
        {
            Iterator <WebPage> aux = tpid.listVirtualParents();
            while (aux.hasNext())
            {
                WebPage tp =  aux.next();
                vctPath.addElement(tp.getId());
                aux = tp.listVirtualParents();
                if (tpid.getWebSite().getHomePage() == tp) break;
            }
        }
        return vctPath;
    }
    
    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws AFException
     * @throws IOException
     */    
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        Portlet base=getResourceBase();
        String surl="";
        if(paramRequest.getTopic()!=paramRequest.getAdminTopic())
        {
            String action = null != request.getParameter("act") && !"".equals(request.getParameter("act").trim()) ? request.getParameter("act").trim() : paramRequest.getAction();
            if("add".equals(action))
            {
                System.out.println(paramRequest.getTopic().getId()+"-----------");
                
                SWBResourceURLImp url=new SWBResourceURLImp(request, base, paramRequest.getTopic(),SWBResourceURL.UrlType_RENDER);
                url.setResourceBase(base);
                url.setMode(url.Mode_VIEW);
                //url.setWindowState(url.WinState_MAXIMIZED);
                url.setParameter("smp_act","smp_step2");
                url.setTopic(paramRequest.getTopic());
                //url.setCallMethod(paramsRequest.Call_DIRECT);
                surl=url.toString();
            }
            else {
                surl=base.getAttribute("url","");
            }
        }
        super.doAdmin(request, response, paramRequest);
        if(paramRequest.getTopic()!=paramRequest.getAdminTopic() && !"".equals(surl))
        {
            base.setAttribute("url", surl);
            try{
                base.updateAttributesToDB();
            }catch(Exception e){
                throw new SWBResourceException(e.getMessage());
            }
        }
    }
}
