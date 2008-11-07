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


package org.semanticwb.portal.resources;

import java.io.IOException;
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
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/** Esta clase muestra el men� con las secciones de un sitio (padres e hijos).
 * Utiliza un archivo XSL. Este recurso viene de la versi�n 2 de WebBuilder
 *
 * This class shows a menu with site's sections (father and son). Uses an XSL
 * file. This resource comes from WebBuilder 2.
 * @author : Jorge Alberto Jim�nez Sandoval (JAJS)
 * @since : December 9th 2004, 12:55
 */
public class WBMenu extends GenericAdmResource 
{
    
    private static Logger log = SWBUtils.getLogger(WBMenu.class);
    
    javax.xml.transform.Templates tpl; 
    String webWorkPath = "/work";  
    String path = SWBPlatform.getContextPath() +"swbadmin/xsl/WBMenu/";
    
    /** Creates a new instance of WBMenu */
    public WBMenu() {
    }
    
    /**
     * Asigna la informaci�n de la base de datos al recurso.
     *
     * @param     base  La informaci�n del recurso en memoria.
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
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPlatform.getFileFromWorkPath(base.getWorkPath() +"/"+ base.getAttribute("template").trim())); 
                path=webWorkPath + "/";
            }
            catch(Exception e) { log.error("Error while loading resource template: "+base.getId(), e); }
        }
        if(tpl==null)
        {
            try { tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/WBMenu/WBMenu.xslt")); } 
            catch(Exception e) { log.error("Error while loading default resource template: "+base.getId(), e); }
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
        try
        {   
            boolean onlychilds=new Boolean(paramRequest.getArgument("onlychilds","false")).booleanValue();
            WebSite tm = paramRequest.getTopic().getWebSite();
            String lang=paramRequest.getUser().getLanguage();
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
            Element topicCurrent = dom.createElement("currenttopic");
            topicCurrent.setAttribute("id", tpid.getUrl());
            //topicCurrent.setAttribute("name", tpid.getTitle(lang)); //TODO: AUMENTAR EL LANGUAJE, AHORA NO SE MUESTRA
            topicCurrent.setAttribute("name", tpid.getTitle(lang));
            topicCurrent.setAttribute("path", tpid.getUrl());
            el.appendChild(topicCurrent);
            WebPage padre=padre = tpid.getParent();
            //if(!onlychilds && padre!=null && padre.isVisible() && user.haveAccess(padre)) //TODO VER 4
            if(!onlychilds && padre!=null && padre.isVisible())
            {
                Element epadre = dom.createElement("parent");
                epadre.setAttribute("id", padre.getId());
                epadre.setAttribute("name", padre.getTitle(lang));
                epadre.setAttribute("path", padre.getUrl());
                el.appendChild(epadre);
            }
            Element ehermanos = dom.createElement("brothers");
            if(!onlychilds && padre!=null)
            {
                Iterator <WebPage>itehermanos=padre.listChilds();
                while(itehermanos.hasNext())
                {
                    WebPage tphermano=itehermanos.next();
                    //if(user.haveAccess(tphermano))    //TODO
                    {
                        Element ehermano = dom.createElement("brother");
                        ehermano.setAttribute("id", tphermano.getId());
                        ehermano.setAttribute("name", tphermano.getTitle(lang));
                        ehermano.setAttribute("path", tphermano.getUrl());
                        if (tphermano.equals(tpid)) 
                        {
                            ehermano.setAttribute("current", "1");
                            ehermanos.appendChild(ehermano);

                            Iterator <WebPage> hijos = tpid.listChilds();
                            while (hijos.hasNext()) 
                            {
                                WebPage hijo =  hijos.next();
                                //if(user.haveAccess(hijo))
                                {
                                    Element ehijo = dom.createElement("child");
                                    ehijo.setAttribute("id", hijo.getId());
                                    ehijo.setAttribute("name", hijo.getTitle(lang));
                                    ehijo.setAttribute("path", hijo.getUrl());
                                    ehermano.appendChild(ehijo);
                                }
                            }
                        }
                        else 
                        {
                            
                            ehermano.setAttribute("current", "0");
                            ehermanos.appendChild(ehermano);
                        }
                    }
                }
            }
            else
            {
                Element ehermano = dom.createElement("brother");
                ehermano.setAttribute("id", tpid.getId());
                ehermano.setAttribute("name", tpid.getTitle(lang));
                ehermano.setAttribute("path", tpid.getUrl());
                ehermano.setAttribute("current", "1");
                ehermanos.appendChild(ehermano);

                Iterator <WebPage> hijos = tpid.listChilds();
                while (hijos.hasNext()) 
                {
                    WebPage hijo = hijos.next();
                    //if(user.haveAccess(hijo)) TODO: VER 4
                    {
                        Element ehijo = dom.createElement("child");
                        ehijo.setAttribute("id", hijo.getId());
                        ehijo.setAttribute("name", hijo.getTitle(lang));
                        ehijo.setAttribute("path", hijo.getUrl());
                        ehermano.appendChild(ehijo);
                    }
                }
            }
            el.appendChild(ehermanos);            
            return dom;
        }
        catch (Exception e) { log.error("Error while generating DOM in resource "+ base.getPortletType().getPortletClassName() +" with identifier " + base.getId() + " - " + base.getTitle(), e); }
        return null;
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
        catch(Exception e){ log.error(e); }        
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
                response.getWriter().print(SWBUtils.XML.transformDom(tpl, dom));
            }
        }
        catch(Exception e) { log.error(e); }
    }    
}
