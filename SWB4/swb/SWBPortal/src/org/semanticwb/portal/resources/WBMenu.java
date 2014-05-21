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


// TODO: Auto-generated Javadoc
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
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(WBMenu.class);
    
    /** The tpl. */
    javax.xml.transform.Templates tpl; 
    
    /** The web work path. */
    String webWorkPath = "/work";  
    
    /** The path. */
    String path = SWBPlatform.getContextPath() +"/swbadmin/xsl/WBMenu/";
    
    /**
     * Creates a new instance of WBMenu.
     */
    public WBMenu() {
    }
    
    /**
     * Asigna la informaci�n de la base de datos al recurso.
     * 
     * @param base the new resource base
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
     * Obtiene el resultado final del recurso en formato dom.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @return the dom
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    public org.w3c.dom.Document getDom(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        User user=paramRequest.getUser();
        Resource base=paramRequest.getResourceBase();
        try
        {   
            boolean justsons = Boolean.parseBoolean( paramRequest.getArgument("justsons", base.getAttribute("justsons","false")) );
            WebSite tm = paramRequest.getWebPage().getWebSite();
            String lang=paramRequest.getUser().getLanguage();
            WebPage tpid = null;
            if( paramRequest.getArguments().get("topic")!=null ) {
                tpid = tm.getWebPage((String) paramRequest.getArguments().get("topic"));
                if (tpid == null)
                    return null;
            }else {
                tpid = paramRequest.getWebPage();
            }
            
            Document  dom = SWBUtils.XML.getNewDocument();
            Element emenu = dom.createElement("menu");
            emenu.setAttribute("path", path);
            dom.appendChild(emenu);

            Element ecurSection = dom.createElement("currenttopic");
            ecurSection.setAttribute("id", tpid.getUrl(lang,false));
            ecurSection.setAttribute("name", tpid.getDisplayName(lang));
            ecurSection.setAttribute("path", tpid.getUrl(lang,false));
            emenu.appendChild(ecurSection);

            WebPage padre = tpid.getParent();
            if(!justsons && padre!=null && padre.isVisible() && user.haveAccess(padre)) {
                Element epadre = dom.createElement("parent");
                epadre.setAttribute("id", padre.getId());
                epadre.setAttribute("name", padre.getDisplayName(lang));
                epadre.setAttribute("path", padre.getUrl(lang,false));
                if (padre.getTarget() != null && !"".equalsIgnoreCase(padre.getTarget())) {
                    epadre.setAttribute("target", padre.getTarget());
                }
                emenu.appendChild(epadre);
            }
            
            Element esiblings = dom.createElement("brothers");
            if(!justsons && padre!=null)
            {
                Iterator <WebPage>itehermanos=padre.listVisibleChilds(lang);
                while(itehermanos.hasNext())
                {
                    WebPage tphermano=itehermanos.next();
                    if(user.haveAccess(tphermano))
                    {
                        Element esibling = dom.createElement("brother");
                        esibling.setAttribute("id", tphermano.getId());
                        esibling.setAttribute("name", tphermano.getDisplayName(lang));
                        esibling.setAttribute("path", tphermano.getUrl(lang,false));
                        if (tphermano.getTarget() != null && !"".equalsIgnoreCase(tphermano.getTarget())) {
                            esibling.setAttribute("target", tphermano.getTarget());
                        }
                        if (tphermano.equals(tpid)) 
                        {
                            esibling.setAttribute("current", "1");
                            esiblings.appendChild(esibling);

                            Iterator <WebPage> hijos = tpid.listVisibleChilds(lang);
                            while (hijos.hasNext()) 
                            {
                                WebPage hijo =  hijos.next();
                                if(user.haveAccess(hijo))
                                {
                                    Element ehijo = dom.createElement("child");
                                    ehijo.setAttribute("id", hijo.getId());
                                    ehijo.setAttribute("name", hijo.getDisplayName(lang));
                                    ehijo.setAttribute("path", hijo.getUrl(lang,false));
                                    if (hijo.getTarget() != null && !"".equalsIgnoreCase(hijo.getTarget())) {
                                        ehijo.setAttribute("target", hijo.getTarget());
                                    }
                                    esibling.appendChild(ehijo);
                                }
                            }
                        }
                        else 
                        {
                            
                            esibling.setAttribute("current", "0");
                            esiblings.appendChild(esibling);
                        }
                    }
                }
            }
            else
            {
                Element esibling = dom.createElement("brother");
                esibling.setAttribute("id", tpid.getId());
                esibling.setAttribute("name", tpid.getDisplayName(lang));
                esibling.setAttribute("path", tpid.getUrl(lang,false));
                esibling.setAttribute("current", "1");
                if (tpid.getTarget() != null && !"".equalsIgnoreCase(tpid.getTarget())) {
                    esibling.setAttribute("target", tpid.getTarget());
                }
                esiblings.appendChild(esibling);

                Iterator <WebPage> hijos = tpid.listVisibleChilds(lang);
                while (hijos.hasNext()) 
                {
                    WebPage hijo = hijos.next();
                    if(user.haveAccess(hijo))
                    {
                        Element ehijo = dom.createElement("child");
                        ehijo.setAttribute("id", hijo.getId());
                        ehijo.setAttribute("name", hijo.getDisplayName(lang));
                        ehijo.setAttribute("path", hijo.getUrl(lang,false));
                        if (hijo.getTarget() != null && !"".equalsIgnoreCase(hijo.getTarget())) {
                            ehijo.setAttribute("target", hijo.getTarget());
                        }
                        esibling.appendChild(ehijo);
                    }
                }
            }
            emenu.appendChild(esiblings);
            return dom;
        }
        catch (Exception e) {
            log.error("Error while generating DOM in resource "+ base.getResourceType().getResourceClassName() +" with identifier " + base.getId() + " - " + base.getTitle(), e);
        }
        return null;
    }
    
    /**
     * Obtiene el resultado final del recurso en formato xml.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
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
     * Obtiene el formato final del recurso en formato html.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        try
        {
            Document dom =getDom(request, response, paramRequest);
            if(dom != null)  {
                //System.out.println("\n\n menu=\n"+SWBUtils.XML.domToXml(dom));
                response.getWriter().println(SWBUtils.XML.transformDom(tpl, dom));
            }
        }
        catch(Exception e) { log.error(e); }
    }    
}
