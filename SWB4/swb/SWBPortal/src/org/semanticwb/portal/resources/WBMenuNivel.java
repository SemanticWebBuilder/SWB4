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
import java.io.PrintWriter;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

// TODO: Auto-generated Javadoc
/**
 * The Class WBMenuNivel.
 * 
 * @author jorge.jimenez
 */
public class WBMenuNivel extends GenericAdmResource
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(WBMenuNivel.class);
    
    /** The tpl. */
    javax.xml.transform.Templates tpl;
    
    /** The web work path. */
    String webWorkPath = "/work";
    
    /** The path. */
    String path = SWBPlatform.getContextPath() +"/swbadmin/xsl/WBMenuNivel/";
    
    /** The ancho. */
    private int ancho=10;
    
    /**
     * Creates a new instance of WBMenu.
     */
    public WBMenuNivel()
    {
    }
    
    /**
     * Asigna la información de la base de datos al recurso.
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
        catch(Exception e)
        { log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);  }
        if(!"".equals(base.getAttribute("template","").trim()))
        {
            try
            {
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getFileFromWorkPath(base.getWorkPath() +"/"+ base.getAttribute("template").trim()));
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
        Resource base=getResourceBase();
        int niveles = Integer.parseInt(base.getAttribute("niveles","2"));
        String basetopic = base.getAttribute("basetopic","_home");
        WebPage basetp = paramRequest.getWebPage().getWebSite().getHomePage();
        try
        {
            if(!"_home".equals(basetopic))
            {
                basetp = paramRequest.getWebPage().getWebSite().getWebPage(basetopic);
            }
        }
        catch(Exception e)
        {
            log.error("Error. Tópico no encontrado: "+basetopic+". WBMenuNivel.getDom()", e);
            basetp = paramRequest.getWebPage().getWebSite().getHomePage();
        }
        User user=paramRequest.getUser();
        try
        {
            WebSite tm = paramRequest.getWebPage().getWebSite();
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
            
            Element baseele = dom.createElement("basetopic");
            baseele.setAttribute("id", basetp.getId());
            baseele.setAttribute("name", basetp.getDisplayTitle(user.getLanguage()));
            baseele.setAttribute("path", basetp.getUrl(user.getLanguage(),false));
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
            topicCurrent.setAttribute("name", tpid.getDisplayTitle(user.getLanguage()));
            topicCurrent.setAttribute("path", tpid.getUrl(user.getLanguage(),false));
            topicCurrent.setAttribute("level",Integer.toString(tpid.getLevel()));
            el.appendChild(topicCurrent);
            
            WebPage padre = tpid.getParent();//basetp.getType();
            if(padre!=null)
            {
                Element epadre = dom.createElement("parent");
                epadre.setAttribute("id", padre.getId());
                epadre.setAttribute("name", padre.getDisplayTitle(user.getLanguage()));
                epadre.setAttribute("path", padre.getUrl(user.getLanguage(),false));
                epadre.setAttribute("level",Integer.toString(padre.getLevel()));
                el.appendChild(epadre);
            }
            
            Element ehermanos = dom.createElement("brothers");
            
            WebPage tmpTopic =  basetp;
            //if(padre!=null) tmpTopic = padre;
            
            int nivel = 1;
            Iterator <WebPage> itehermanos=tmpTopic.listVisibleChilds(paramRequest.getUser().getLanguage());
            while(itehermanos.hasNext())
            {
                WebPage tphermano=itehermanos.next();
               if(user.haveAccess(tphermano))
               {
                    Element ehermano = dom.createElement("brother");
                    ehermano.setAttribute("id", tphermano.getId());
                    ehermano.setAttribute("name", tphermano.getDisplayTitle(user.getLanguage()));
                    ehermano.setAttribute("path", tphermano.getUrl(user.getLanguage(),false));
                    ehermano.setAttribute("level", Integer.toString(tphermano.getLevel()));
                    ehermano.setAttribute("nivel", Integer.toString(nivel));                    
                    ehermano.setAttribute("caracter", Integer.toString(ancho*nivel));
                    if (tphermano.getTarget() != null && !"".equalsIgnoreCase(tphermano.getTarget())) {
                        ehermano.setAttribute("target", tphermano.getTarget());
                    }

                    if (tphermano.equals(tpid)||(tpid.getParent()!=null&&tpid.getParent().equals(tphermano))||tphermano.isParentof(tpid)) {
                        ehermano.setAttribute("current", "1");
                    }
                    else {
                        ehermano.setAttribute("current", "0");
                    }
                    
                    ehermanos.appendChild(ehermano);
                    
                    Iterator <WebPage> hijos = tphermano.listVisibleChilds(user.getLanguage());
                    while (hijos.hasNext())
                    {
                        WebPage hijo = hijos.next();
                        if(user.haveAccess(hijo))
                        {
                            Element ehijo = dom.createElement("child");
                            ehijo.setAttribute("id", hijo.getId());
                            ehijo.setAttribute("name", hijo.getDisplayTitle(user.getLanguage()));
                            ehijo.setAttribute("path", hijo.getUrl(user.getLanguage(),false));
                            ehijo.setAttribute("level", Integer.toString(hijo.getLevel()));
                            ehijo.setAttribute("nivel", Integer.toString(nivel+1));
                            if (hijo.getTarget() != null && !"".equalsIgnoreCase(hijo.getTarget())) {
                                ehijo.setAttribute("target", hijo.getTarget());
                            }
                            if (hijo.equals(tpid)||hijo.isParentof(tpid)) {
                                ehijo.setAttribute("current", "1");
                            }
                            else {
                                ehijo.setAttribute("current", "0");
                            }
                            ehijo.setAttribute("caracter", Integer.toString(ancho*(nivel+1)));
                            ehermano.appendChild(ehijo);
                            if((nivel+1)<=niveles){
                                addChild(dom, ehijo, hijo, nivel+2,niveles, user, tpid);
                            }
                        }
                    }
                    
                }
            }
            el.appendChild(ehermanos);
            return dom;
        }
        catch (Exception e)
        { log.error("Error while generating DOM in resource "+ base.getResourceType().getResourceClassName() +" with identifier " + base.getId() + " - " + base.getTitle(), e); }
        return null;
    }
    
    /**
     * Adds the child.
     * 
     * @param dom the dom
     * @param nodo the nodo
     * @param topic the topic
     * @param actual the actual
     * @param nivel the nivel
     * @param user the user
     * @param currenttopic the currenttopic
     */
    public void addChild(Document dom, Element nodo, WebPage topic, int actual, int nivel, User user, WebPage currenttopic)
    {
        if(nivel>=actual)
        {
            Iterator <WebPage> hijos = topic.listVisibleChilds(user.getLanguage());
            while (hijos.hasNext())
            {
                WebPage hijo = hijos.next();
                if(user.haveAccess(hijo))
                {
                    Element ehijo = dom.createElement("child");
                    ehijo.setAttribute("id", hijo.getId());
                    ehijo.setAttribute("name", hijo.getDisplayTitle(user.getLanguage()));
                    ehijo.setAttribute("path", hijo.getUrl(user.getLanguage(),false));
                    ehijo.setAttribute("level", Integer.toString(hijo.getLevel()));
                    ehijo.setAttribute("nivel", Integer.toString(actual));
                    ehijo.setAttribute("caracter",Integer.toString(ancho*(actual+1)));
                    if (hijo.equals(currenttopic)||hijo.isParentof(currenttopic)) {
                        ehijo.setAttribute("current", "1");
                    }
                    else {
                        ehijo.setAttribute("current", "0");
                    }
                    if (hijo.getTarget() != null && !"".equalsIgnoreCase(hijo.getTarget())) {
                        ehijo.setAttribute("target", hijo.getTarget());
                    }
                    nodo.appendChild(ehijo);
                    addChild(dom, ehijo, hijo, actual+1, nivel, user, currenttopic);
                }
            }
        }
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
        catch(Exception e)
        { log.error(e); }
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
                PrintWriter out = response.getWriter();
                //response.getWriter().print(SWBUtils.XML.transformDom(tpl, dom));
                out.print(SWBUtils.XML.transformDom(tpl, dom));
                //out.println("<br><a href=\"" + paramRequest.getRenderUrl().setMode(paramRequest.Mode_ADMIN) + "\">admin</a>");
            }
        }
        catch(Exception e)
        { log.error(e); }
    }
}
