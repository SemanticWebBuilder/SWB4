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
package org.semanticwb.portal.api;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;

// TODO: Auto-generated Javadoc
/** Recurso gen�rico utilizado como plantilla para la generaci�n de nuevos recursos para
 * WebBuilder.
 *
 * Used generic resource used as it template for the generation of new resources
 * for WebBuilder.
 *
 * @author Javier Solis Gonzalez
 */
public class GenericResource implements SWBResource, SWBResourceCache, SWBResourceWindow
{
    
    /** The base. */
    private String baseUri;
    
    /**
     * Creates a new instance of GenericResource.
     */
    public GenericResource()
    {
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBResource#destroy()
     */
    public void destroy()
    {
    }
    
    /**
     * Gets the resource base.
     * 
     * @return the resource base
     * @return
     */    
    public Resource getResourceBase()
    {
        Resource ret=null;
        SemanticObject obj=SemanticObject.createSemanticObject(baseUri);
        if(obj!=null)ret=(Resource)obj.createGenericInstance();
        return ret;
    }
    
    /**
     * Inits the.
     * 
     * @throws SWBResourceException the sWB resource exception
     */    
    public void init() throws SWBResourceException
    {
    }
    
    /**
     * Install.
     * 
     * @param recobj the recobj
     * @throws SWBResourceException the sWB resource exception
     */    
    public void install(ResourceType recobj) throws SWBResourceException
    {
    }
    
    /**
     * Process action.
     * 
     * @param request the request
     * @param response the response
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */    
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        throw new SWBResourceException("method not implemented");
    }
    
    /**
     * Render.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */    
    public void render(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        if(!paramRequest.WinState_MINIMIZED.equals(paramRequest.getWindowState()))
        {
            processRequest(request, response, paramRequest);
        }
    }
    
    /**
     * Process request.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */    
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        if(paramRequest.getMode().equals(paramRequest.Mode_VIEW))
        {
            doView(request,response,paramRequest);
        }else if(paramRequest.getMode().equals(paramRequest.Mode_EDIT))
        {
            doEdit(request,response,paramRequest);
        }else if(paramRequest.getMode().equals(paramRequest.Mode_HELP))
        {
            doHelp(request,response,paramRequest);
        }else if(paramRequest.getMode().equals(paramRequest.Mode_ADMIN))
        {
            doAdmin(request,response,paramRequest);
        }else if(paramRequest.getMode().equals(paramRequest.Mode_ADMHLP))
        {
            doAdminHlp(request,response,paramRequest);
        }else if(paramRequest.getMode().equals(paramRequest.Mode_INDEX))
        {
            doIndex(request,response,paramRequest);
        }else if(paramRequest.getMode().equals(paramRequest.Mode_XML))
        {
            doXML(request,response,paramRequest);
        }else 
        {
            throw new SWBResourceException("method not implemented - Mode:("+paramRequest.getMode()+"), URL:("+request.getRequestURL()+"), Ref:("+request.getHeader("referer")+")");
        }
    }
    
    /**
     * Do view.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */    
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        throw new SWBResourceException("method not implemented - doView");
    }

    /**
     * Do edit.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */    
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        throw new SWBResourceException("method not implemented - doEdit");
    }

    /**
     * Do help.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */    
    public void doHelp(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        throw new SWBResourceException("method not implemented - doHelp");
    }
    
    /**
     * Do admin.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */    
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        java.io.PrintWriter out = response.getWriter();
        out.println("<div class=\"swbform\">");
        out.println("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\">");
        out.println("<tr>");
        out.println("<td class=datos>");
        out.println(SWBUtils.TEXT.getLocaleString("org.semanticwb.portal.api.GenericResource", "msgNotAdmin",new Locale(paramRequest.getUser().getLanguage())));
        out.println("</td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("</div>");
    }
    
    /**
     * Do admin hlp.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */    
    public void doAdminHlp(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        throw new SWBResourceException("method not implemented - doAdminHlp");
    }
    
    /**
     * Do index.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */    
    public void doIndex(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        doView(request, response, paramRequest);
    }
    
    /**
     * Do xml.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */    
    public void doXML(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        throw new SWBResourceException("method not implemented - doXML");
    }

    /**
     * Sets the resource base.
     * 
     * @param base the new resource base
     * @throws SWBResourceException the sWB resource exception
     */    
    public void setResourceBase(Resource base) throws SWBResourceException
    {
        baseUri=base.getURI();
    }
    
    /**
     * Uninstall.
     * 
     * @param recobj the recobj
     * @throws SWBResourceException the sWB resource exception
     */    
    public void uninstall(ResourceType recobj) throws SWBResourceException
    {
    }
    
    /**
     * by default this method will return null when the request have paramaters.
     * 
     * @param request the request
     * @param paramRequest the param request
     * @return the resource cache id
     * @throws SWBResourceException the sWB resource exception
     */

    public String getResourceCacheID(HttpServletRequest request, SWBParamRequest paramRequest) throws SWBResourceException
    {
        Resource base=paramRequest.getResourceBase();
        if (!base.getResourceType().isResourceCacheIgnoreQueryParams() && request.getParameterNames().hasMoreElements())
        {
            return null;
        }else
        {
            //resource
            //webpage
            //user
            //language
            //user_webpage
                                    
            String key = SWBResourceCachedMgr.getKey(base);
            if("webpage".equals(base.getResourceType().getResourceCacheType()))
            {
                key=paramRequest.getWebPage().getId()+"_"+key;
            }else if("user".equals(base.getResourceType().getResourceCacheType()))
            {
                key=paramRequest.getUser().getId()+"_"+key;
            }else if("language".equals(base.getResourceType().getResourceCacheType()))
            {
                key=paramRequest.getUser().getLanguage()+"_"+key;
            }else if("user_webpage".equals(base.getResourceType().getResourceCacheType()))
            {
                key=paramRequest.getUser().getId()+"_"+paramRequest.getWebPage().getId()+"_"+key;
            }
            //System.out.println(base.getResourceType().getResourceCacheType() +" "+ key);
            return key;
        }
    }
   
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBResourceWindow#getModes(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBParamRequest)
     */
    public String[] getModes(HttpServletRequest request, SWBParamRequest paramRequest) throws SWBResourceException, java.io.IOException
    {
        WebSite tm=SWBContext.getAdminWebSite();
        if(tm!=null)
        {
            if(paramRequest.getUser().hasUserGroup(SWBContext.getAdminRepository().getUserGroup("admin")))
            {
                return new String[]{paramRequest.Mode_VIEW,paramRequest.Mode_ADMIN};
            }
        }
        return new String[]{paramRequest.Mode_VIEW};
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBResourceWindow#getTitle(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBParamRequest)
     */
    public String getTitle(HttpServletRequest request, SWBParamRequest paramRequest) throws SWBResourceException, java.io.IOException
    {
        return paramRequest.getWindowTitle();
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBResourceWindow#getWindowStates(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBParamRequest)
     */
    public String[] getWindowStates(HttpServletRequest request, SWBParamRequest paramRequest) throws SWBResourceException, java.io.IOException
    {
        return new String[]{paramRequest.WinState_MINIMIZED, paramRequest.WinState_NORMAL, paramRequest.WinState_MAXIMIZED};
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBResourceWindow#windowSupport(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBParamRequest)
     */
    public boolean windowSupport(HttpServletRequest request, SWBParamRequest paramRequest) throws SWBResourceException, java.io.IOException
    {
        //System.out.println("----> "+paramRequest.getResourceBase().getId()+" windowSupport:"+paramRequest.getResourceBase().getConfAttribute("resourceWindow","0"));
        if(!paramRequest.getAdminTopic().getWebSiteId().equals(SWBContext.WEBSITE_ADMIN))
        {
//            TopicMap tm=TopicMgr.getInstance().getTopicMap(TopicMgr.TM_ADMIN);
//            if(tm!=null)
//            {
//                if(paramRequest.getUser().havePermission(tm.getWebPage("WBAd_per_Administrator")))
//                {
//                    return true;
//                }
//            }
            if(paramRequest.getResourceBase().isResourceWindow())
            {
                return true;
            }
        }
        return false;
    }
    
}
