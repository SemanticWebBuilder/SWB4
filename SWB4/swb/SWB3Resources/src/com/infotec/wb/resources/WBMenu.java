/**  
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
**/ 
 


package com.infotec.wb.resources;

import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
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
    public void setResourceBase(Resource base)
    {
        try 
        {
            super.setResourceBase(base);
            webWorkPath = (String) SWBPortal.getWebWorkPath() +  base.getWorkPath();
        }
        catch(Exception e) { log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);  }
        if(!"".equals(base.getAttribute("template","").trim()))
        {
            try 
            { 
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getFileFromWorkPath(base.getWorkPath() +"/"+ base.getAttribute("template").trim()));
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
        Resource base=getResourceBase();
        try
        {   
            boolean onlychilds=new Boolean(paramRequest.getArgument("onlychilds","false")).booleanValue();
            WebSite tm = paramRequest.getWebPage().getWebSite();
            User user=paramRequest.getUser();
            String lang=user.getLanguage();
            WebPage tpid = null;
            if (paramRequest.getArguments().get("topic") != null) 
            {
                tpid = tm.getWebPage((String) paramRequest.getArguments().get("topic"));
                if (tpid == null) {
                    return null;
                }
            } 
            else {
                tpid = paramRequest.getWebPage();
            }
            
            Document  dom = SWBUtils.XML.getNewDocument();
            Element el = dom.createElement("menu");
            el.setAttribute("path", path);
            dom.appendChild(el);
            Element topicCurrent = dom.createElement("currenttopic");
            topicCurrent.setAttribute("id", tpid.getUrl());
            topicCurrent.setAttribute("name", tpid.getTitle(lang));
            topicCurrent.setAttribute("path", tpid.getUrl());
            el.appendChild(topicCurrent);
            WebPage padre=padre = tpid.getParent();
            if(!onlychilds && padre!=null && padre.isVisible() && user.haveAccess(padre))
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
                Iterator <WebPage>itehermanos=padre.listChilds(lang,true,false,false,null);
                while(itehermanos.hasNext())
                {
                    WebPage tphermano=itehermanos.next();
                    if(user.haveAccess(tphermano)) 
                    {
                        Element ehermano = dom.createElement("brother");
                        ehermano.setAttribute("id", tphermano.getId());
                        ehermano.setAttribute("name", tphermano.getTitle(lang));
                        ehermano.setAttribute("path", tphermano.getUrl());
                        if (tphermano.equals(tpid)) 
                        {
                            ehermano.setAttribute("current", "1");
                            ehermanos.appendChild(ehermano);

                            Iterator <WebPage> hijos = tpid.listChilds(lang,true,false,false,null);
                            while (hijos.hasNext()) 
                            {
                                WebPage hijo =  hijos.next();
                                if(user.haveAccess(hijo))
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

                Iterator <WebPage> hijos = tpid.listChilds(lang,true,false,false,null);
                while (hijos.hasNext()) 
                {
                    WebPage hijo = hijos.next();
                    if(user.haveAccess(hijo))
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
        catch (Exception e) { log.error("Error while generating DOM in resource "+ base.getResourceType().getResourceClassName() +" with identifier " + base.getId() + " - " + base.getTitle(), e); }
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
