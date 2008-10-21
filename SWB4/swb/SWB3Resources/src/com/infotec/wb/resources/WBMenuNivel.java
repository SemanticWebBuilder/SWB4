/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.infotec.wb.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Portlet;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericAdmResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author jorge.jimenez
 */
public class WBMenuNivel extends GenericAdmResource
{
    private static Logger log = SWBUtils.getLogger(WBMenuNivel.class);
    
    javax.xml.transform.Templates tpl;
    String webWorkPath = "/work";
    String path = SWBPlatform.getContextPath() +"swbadmin/xsl/WBMenuNivel/";
    private int ancho=10;
    
    /** Creates a new instance of WBMenu */
    public WBMenuNivel()
    {
    }
    
    /**
     * Asigna la información de la base de datos al recurso.
     *
     * @param     base  La información del recurso en memoria.
     */
    @Override
    public void setResourceBase(Portlet base)
    {
        try
        {
            super.setResourceBase(base);
            webWorkPath = (String) SWBPlatform.getWebWorkPath() +  base.getWorkPath();
        }
        catch(Exception e)
        { log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);  }
        if(!"".equals(base.getAttribute("template","").trim()))
        {
            try
            {
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPlatform.getFileFromWorkPath(base.getWorkPath() +"/"+ base.getAttribute("template").trim()));
                path=webWorkPath + "/";
            }
            catch(Exception e)
            { log.error("Error while loading resource template: "+base.getId(), e); }
        }
        if(tpl==null)
        {
            try
            { tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/WBMenuNivel/WBMenuNivel.xslt")); }
            catch(Exception e)
            { log.error("Error while loading default resource template: "+base.getId(), e); }
        }
    }
    
    
    /**
     * Obtiene el resultado final del recurso en formato dom
     * @param request
     * @param response
     * @param reqParams
     * @throws AFException
     * @throws IOException
     */
    public org.w3c.dom.Document getDom(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        Portlet base=getResourceBase();
        int niveles = Integer.parseInt(base.getAttribute("niveles","2"));
        String basetopic = base.getAttribute("basetopic","_home");
        WebPage basetp = paramRequest.getTopic().getWebSite().getHomePage();
        try
        {
            if(!"_home".equals(basetopic))
            {
                basetp = paramRequest.getTopic().getWebSite().getWebPage(basetopic);
            }
        }
        catch(Exception e)
        {
            log.error("Error. Tópico no encontrado: "+basetopic+". WBMenuNivel.getDom()", e);
            basetp = paramRequest.getTopic().getWebSite().getHomePage();
        }
        User user=paramRequest.getUser();
        try
        {
            WebSite tm = paramRequest.getTopic().getWebSite();
            org.semanticwb.model.Language lang=tm.getLanguage(user.getLanguage());            
            WebPage tpid = null;
            
            if (paramRequest.getArguments().get("topic") != null)
            {
                tpid = tm.getWebPage((String) paramRequest.getArguments().get("topic"));
                if (tpid == null) {
                    return null;
                }
            }
            else {
                tpid = paramRequest.getTopic();
            }
            
            Document  dom = SWBUtils.XML.getNewDocument();
            Element el = dom.createElement("menu");
            el.setAttribute("path", path);
            dom.appendChild(el);
            
            Element baseele = dom.createElement("basetopic");
            baseele.setAttribute("id", basetp.getId());
            baseele.setAttribute("name", basetp.getTitle(lang.getId()));
            baseele.setAttribute("path", basetp.getUrl());
            baseele.setAttribute("level",Integer.toString(basetp.getLevel()));
            if(basetp.equals(tpid)) {
                baseele.setAttribute("current", "1");
            }
            else {
                baseele.setAttribute("current", "0");
            }
            el.appendChild(baseele);
            
            Element topicCurrent = dom.createElement("currenttopic");
            topicCurrent.setAttribute("id", tpid.getId());
            topicCurrent.setAttribute("name", tpid.getTitle(lang.getId()));
            topicCurrent.setAttribute("path", tpid.getUrl());
            topicCurrent.setAttribute("level",Integer.toString(tpid.getLevel()));
            el.appendChild(topicCurrent);
            
            WebPage padre = tpid.getParent();//basetp.getType();
            if(padre!=null)
            {
                Element epadre = dom.createElement("parent");
                epadre.setAttribute("id", padre.getId());
                epadre.setAttribute("name", padre.getTitle(lang.getId()));
                epadre.setAttribute("path", padre.getUrl());
                epadre.setAttribute("level",Integer.toString(padre.getLevel()));
                el.appendChild(epadre);
            }
            
            Element ehermanos = dom.createElement("brothers");
            
            WebPage tmpTopic =  basetp;
            //if(padre!=null) tmpTopic = padre;
            
            int nivel = 1;
            Iterator <WebPage> itehermanos=tmpTopic.listChilds();
            while(itehermanos.hasNext())
            {
                WebPage tphermano=itehermanos.next();
               //if(user.haveAccess(tphermano))
                {
                    Element ehermano = dom.createElement("brother");
                    ehermano.setAttribute("id", tphermano.getId());
                    ehermano.setAttribute("name", tphermano.getTitle(lang.getId()));
                    ehermano.setAttribute("path", tphermano.getUrl());
                    ehermano.setAttribute("level", Integer.toString(tphermano.getLevel()));
                    ehermano.setAttribute("nivel", Integer.toString(nivel));                    
                    ehermano.setAttribute("caracter", Integer.toString(ancho*nivel));

                    if (tphermano.equals(tpid)||(tpid.getParent()!=null&&tpid.getParent().equals(tphermano))||tphermano.isParentof(tpid)) {
                        ehermano.setAttribute("current", "1");
                    }
                    else {
                        ehermano.setAttribute("current", "0");
                    }
                    
                    ehermanos.appendChild(ehermano);
                    
                    Iterator <WebPage> hijos = tphermano.listChilds();
                    while (hijos.hasNext())
                    {
                        WebPage hijo = hijos.next();
                        //TODO VER 4:if(user.haveAccess(hijo))
                        {
                            Element ehijo = dom.createElement("child");
                            ehijo.setAttribute("id", hijo.getId());
                            ehijo.setAttribute("name", hijo.getTitle(lang.getId()));
                            ehijo.setAttribute("path", hijo.getUrl());
                            ehijo.setAttribute("level", Integer.toString(hijo.getLevel()));
                            ehijo.setAttribute("nivel", Integer.toString(nivel+1));
                            if (hijo.equals(tpid)||hijo.isParentof(tpid)) {
                                ehijo.setAttribute("current", "1");
                            }
                            else {
                                ehijo.setAttribute("current", "0");
                            }
                            ehijo.setAttribute("caracter", Integer.toString(ancho*(nivel+1)));
                            ehermano.appendChild(ehijo);
                            if((nivel+1)<=niveles){
                                addChild(dom, ehijo, hijo, nivel+2,niveles, user, lang, tpid);
                            }
                        }
                    }
                    
                }
            }
            el.appendChild(ehermanos);
            return dom;
        }
        catch (Exception e)
        { log.error("Error while generating DOM in resource "+ base.getPortletType().getPortletClassName() +" with identifier " + base.getId() + " - " + base.getTitle(), e); }
        return null;
    }
    
    public void addChild(Document dom, Element nodo, WebPage topic, int actual, int nivel, User user, org.semanticwb.model.Language lang, WebPage currenttopic)
    {
        if(nivel>=actual)
        {
            Iterator <WebPage> hijos = topic.listChilds();
            while (hijos.hasNext())
            {
                WebPage hijo = hijos.next();
                //TODO VER 4:if(user.haveAccess(hijo))
                {
                    Element ehijo = dom.createElement("child");
                    ehijo.setAttribute("id", hijo.getId());
                    ehijo.setAttribute("name", hijo.getTitle(lang.getTitle(lang.getId())));
                    ehijo.setAttribute("path", hijo.getUrl());
                    ehijo.setAttribute("level", Integer.toString(hijo.getLevel()));
                    ehijo.setAttribute("nivel", Integer.toString(actual));
                    ehijo.setAttribute("caracter",Integer.toString(ancho*(actual+1)));
                    if (hijo.equals(currenttopic)||hijo.isParentof(currenttopic)) {
                        ehijo.setAttribute("current", "1");
                    }
                    else {
                        ehijo.setAttribute("current", "0");
                    }
                    nodo.appendChild(ehijo);
                    addChild(dom, ehijo, hijo, actual+1, nivel, user, lang, currenttopic);
                }
            }
        }
    }
    
    
    /**
     * Obtiene el resultado final del recurso en formato xml
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
        catch(Exception e)
        { log.error(e); }
    }
    
    /**
     * Obtiene el formato final del recurso en formato html
     * @param request
     * @param response
     * @param reqParams
     * @throws AFException
     * @throws IOException
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        try
        {
            Document dom =getDom(request, response, paramRequest);
            if(dom != null)  {
                PrintWriter out = response.getWriter();
                //response.getWriter().print(SWBUtils.XML.transformDom(tpl, dom));
                out.print(SWBUtils.XML.transformDom(tpl, dom));
                out.println("<br><a href=\"" + paramRequest.getRenderUrl().setMode(paramRequest.Mode_ADMIN) + "\">admin</a>");
            }
        }
        catch(Exception e)
        { log.error(e); }
    }
}
