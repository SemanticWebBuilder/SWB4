
package org.semanticwb.portal.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBActionResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/** Esta clase se encarga de desplegar los lenguajes disponibles en WebBuilder bajo ciertos
 * criterios (configuraci�n de recurso) para actulizar el lenguaje del usuario final. Puede ser un
 * recurso de estrategia o de contenido.
 *
 * This class displays all available languages on WebBuilder under criteria like
 * resource configuration. In addition can update language for user. this resource
 * could be installed as a content resource or a strategy resource.
 *
 * @author : Vanessa Arredondo N��ez
 * @version 1.0
 */
public class Language extends GenericAdmResource
{
    private static Logger log = SWBUtils.getLogger(Language.class);
    
    javax.xml.transform.Templates tpl; 
    String path = SWBPlatform.getContextPath() +"swbadmin/xsl/Language/";

    /** 
     * Creates a new instance of Language 
     */      
    public Language(){
    }

    /**
     * @param base
     */    
    @Override
    public void setResourceBase(Resource base)
    {
        try { super.setResourceBase(base); }
        catch(Exception e) { log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);  }
        if(!"".equals(base.getAttribute("template","").trim()))
        {
            try 
            {
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPlatform.getFileFromWorkPath(base.getWorkPath() +"/"+ base.getAttribute("template").trim()));
                path=SWBPlatform.getWebWorkPath() +  base.getWorkPath() + "/";
            }
            catch(Exception e) { log.error("Error while loading resource template: "+base.getId(), e); }
        }
        if(tpl==null)
        {
            try {
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/Language/Language.xslt"));
            }catch(Exception e) { log.error("Error while loading default resource template: "+base.getId(), e); }
        }
    }


    /**
     * @param request
     * @param response
     * @param reqParams
     * @return <b>Document</b>
     * @throws AFException
     * @throws IOException
     */
    public Document getDom(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        try
        {
            SWBResourceURL url = paramRequest.getActionUrl().setMode(paramRequest.Mode_VIEW);                        
            Document  dom = SWBUtils.XML.getNewDocument();
            Element root = dom.createElement("resource");
            root.setAttribute("currentlang", paramRequest.getUser().getLanguage());
            root.setAttribute("path", path);
            dom.appendChild(root);

            Iterator<org.semanticwb.model.Language> itLang = paramRequest.getWebPage().getWebSite().listLanguages();
            while(itLang.hasNext())
            {
                org.semanticwb.model.Language lang = itLang.next();
                Element elang = dom.createElement("language");
                elang.setAttribute("id", String.valueOf(lang.getId()));
                elang.setAttribute("idtm", lang.getWebSite().getId());
                elang.setAttribute("lang", lang.getDisplayTitle(paramRequest.getUser().getLanguage()));//
                elang.setAttribute("title", lang.getDisplayTitle(paramRequest.getUser().getLanguage()));
                url.setParameter("language", lang.getId());//
                elang.setAttribute("ref", url.toString());
                root.appendChild(elang);
            }
            return dom;
        }
        catch (Exception e) { log.error("Error while generating DOM in resource "+ getResourceBase().getResourceType().getResourceClassName() +" with identifier " + getResourceBase().getId() + " - " + getResourceBase().getTitle(), e); }
        return null;            
    }
    
    
    public static ArrayList getAppLanguages() {
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
    }
    
    
    
    /**
     * @param request
     * @param response
     * @param reqParams
     * @throws AFException
     * @throws IOException
     */    
    public void doXML(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        try
        {
            org.w3c.dom.Document dom=getDom(request, response, paramRequest);
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
        String action = null != request.getParameter("lng_act") && !"".equals(request.getParameter("lng_act").trim()) ? request.getParameter("lng_act").trim() : "lng_step1";
        
        /*if("lng_step2".equals(action)) {
            paramRequest.getUser().icooklanguage(request,response,request.getParameter("language"));
        }*/
        try
        {
            Document dom =getDom(request, response, paramRequest);
            if(dom != null)  {
                response.getWriter().print(SWBUtils.XML.transformDom(tpl, dom));
            }
        }
        catch(Exception e) { log.error(e); }
    }
    
    /**
     * @param request
     * @param response
     * @throws AFException
     * @throws IOException
     */      
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        String language = null != request.getParameter("language") && !"".equals(request.getParameter("language").trim()) ? request.getParameter("language").trim() : "";
        if(!"".equals(language))
        {
            User user=response.getUser();
            user.setLanguage(language);
            request.getSession().removeAttribute("WBCookieMgrUI"); //remove User Intert flag (remoteWebApps)
            /** TODO: VER 4.0
            if(user.isRegistered()) user.getRecUser().update(); 
            else 
            {
                response.setRenderParameter("lng_act","lng_step2");
                response.setRenderParameter("language",language);
            }**/
            response.setRenderParameter("lng_act","lng_step2");
            response.setRenderParameter("language",language);
        }
    }    
}
