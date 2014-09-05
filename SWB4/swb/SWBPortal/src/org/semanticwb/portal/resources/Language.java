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
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.User;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBResourceException;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

// TODO: Auto-generated Javadoc
/**
 * Language se encarga de desplegar los lenguajes disponibles en WebBuilder bajo
 * ciertos criterios (configuraci�n de recurso) para actulizar el lenguaje del
 * usuario final. Puede ser un recurso de estrategia o de contenido.
 *
 * Language displays all available languages on WebBuilder under criteria like
 * resource configuration. In addition can update language for user. this
 * resource could be installed as a content resource or a strategy resource.
 *
 * @author : Vanessa Arredondo Nunez
 */
public class Language extends GenericAdmResource
{

    /**
     * The log.
     */
    private static Logger log = SWBUtils.getLogger(Language.class);

    /**
     * The tpl.
     */
    javax.xml.transform.Templates tpl;

    /**
     * The path.
     */
    String path = SWBPlatform.getContextPath() + "swbadmin/xsl/Language/";

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericAdmResource#setResourceBase(org.semanticwb.model.Resource)
     */
    @Override
    public void setResourceBase(Resource base) throws SWBResourceException
    {
        super.setResourceBase(base);

        if (!"".equals(base.getAttribute("template", "").trim()))
        {
            try
            {
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getFileFromWorkPath(base.getWorkPath() + "/" + base.getAttribute("template").trim()));
                //path=SWBPlatform.getWebWorkPath() +  base.getWorkPath() + "/";
                path = SWBPlatform.getContextPath() + "swbadmin/xsl/Language/";
            }
            catch (Exception e)
            {
                log.error("Error while loading resource template: " + base.getId(), e);
            }
        }
        if (tpl == null)
        {
            try
            {
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/Language/Language.xslt"));
            }
            catch (Exception e)
            {
                log.error("Error while loading default resource template: " + base.getId(), e);
            }
        }
    }

    /**
     * Gets the dom.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @return the dom
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public Document getDom(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        Document dom = null;
        try
        {
            //SWBResourceURL url = paramRequest.getActionUrl().setMode(paramRequest.Mode_VIEW);                        
            dom = SWBUtils.XML.getNewDocument();
            Element root = dom.createElement("resource");
            root.setAttribute("currentlang", paramRequest.getUser().getLanguage());

            //System.out.println("\n\npath="+path);
            root.setAttribute("path", path);
            dom.appendChild(root);

            Iterator<org.semanticwb.model.Language> itLang = paramRequest.getWebPage().getWebSite().listLanguages();
            while (itLang.hasNext())
            {
                org.semanticwb.model.Language lang = itLang.next();
                Element elang = dom.createElement("language");
                elang.setAttribute("id", String.valueOf(lang.getId()));
                elang.setAttribute("idtm", lang.getWebSite().getId());
                elang.setAttribute("lang", lang.getDisplayTitle(paramRequest.getUser().getLanguage()));//
                elang.setAttribute("title", lang.getDisplayTitle(paramRequest.getUser().getLanguage()));
                String url = request.getRequestURI();

                StringBuilder urlToLanguage = new StringBuilder();
                String context = SWBPortal.getContextPath();
                url = url.substring(context.length()); // le quita el context
                String[] values = url.split("/");
                if (url.isEmpty())
                {
                    url = "/";
                }
                if (url.equals("/"))
                {
                    url += lang.getId() + "/" + paramRequest.getWebPage().getWebSiteId() + "/" + paramRequest.getWebPage().getId();
                    urlToLanguage.append(url);
                }
                else
                {
                    int i = 0;
                    for (String value : values)
                    {
                        if (!value.isEmpty())
                        {
                            i++;
                            if (i != 1)
                            {
                                urlToLanguage.append("/");
                                urlToLanguage.append(value);
                            }
                            else
                            {
                                urlToLanguage.append("/");
                                urlToLanguage.append(lang.getId());
                            }
                        }
                    }
                }
                //url.setParameter("language", lang.getId());//
                String urlToDisplay = context + urlToLanguage.toString();
                urlToDisplay = urlToDisplay.replace("//", "/");
                if (request.getQueryString() != null)
                {
                    urlToDisplay += "?" + request.getQueryString();
                }

                elang.setAttribute("ref", urlToDisplay);
                root.appendChild(elang);
            }
        }
        catch (Exception e)
        {
            log.error("Error while generating DOM in resource " + getResourceBase().getResourceType().getResourceClassName() + " with identifier " + getResourceBase().getId() + " - " + getResourceBase().getTitle(), e);
        }
        return dom;
    }

    /*public static ArrayList getAppLanguages() {
     ArrayList languages = new ArrayList();
     Iterator<WebSite> it = SWBContext.listWebSites();
     while (it.hasNext()) {
     WebSite site = it.next();
     Iterator<org.semanticwb.model.Language > itLang = site.listLanguages();
     while (itLang.hasNext()) {
     org.semanticwb.model.Language lang = itLang.next();
     if (!languages.contains(lang.getId())) {
     languages.add(lang.getId());
     }
     }
     }
     return languages;
     }*/
    /**
     * Do xml.
     *
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    public void doXML(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        try
        {
            org.w3c.dom.Document dom = getDom(request, response, paramRequest);
            if (dom != null)
            {
                response.getWriter().println(SWBUtils.XML.domToXml(dom));
            }
        }
        catch (Exception e)
        {
            log.error(e);
        }
    }

    /**
     * Do view.
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
        String action = null != request.getParameter("lng_act") && !"".equals(request.getParameter("lng_act").trim()) ? request.getParameter("lng_act").trim() : "lng_step1";

        /*if("lng_step2".equals(action)) {
         paramRequest.getUser().icooklanguage(request,response,request.getParameter("language"));
         }*/
        try
        {
            Document dom = getDom(request, response, paramRequest);
            if (dom != null)
            {
                response.getWriter().print(SWBUtils.XML.transformDom(tpl, dom));
            }
        }
        catch (Exception e)
        {
            log.error(e);
        }
    }

    /**
     * Process action.
     *
     * @param request the request
     * @param response the response
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        String language = null != request.getParameter("language") && !"".equals(request.getParameter("language").trim()) ? request.getParameter("language").trim() : "";
        if (!"".equals(language))
        {
            User user = response.getUser();
            user.setLanguage(language);
            request.getSession().removeAttribute("WBCookieMgrUI"); //remove User Intert flag (remoteWebApps)
            /**
             * TODO: VER 4.0 if(user.isRegistered()) user.getRecUser().update();
             * else { response.setRenderParameter("lng_act","lng_step2");
             * response.setRenderParameter("language",language); }*
             */
            response.setRenderParameter("lng_act", "lng_step2");
            response.setRenderParameter("language", language);
        }
    }
}
