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
 

/*
 * GenericResource.java
 *
 * Created on 1 de junio de 2004, 07:14 PM
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
    private Resource base;
    
    /** Creates a new instance of GenericResource */
    public GenericResource()
    {
    }
    
    public void destroy()
    {
    }
    
    /**
     * @return
     */    
    public Resource getResourceBase()
    {
        return base;
    }
    
    /**
     * @throws SWBResourceException
     */    
    public void init() throws SWBResourceException
    {
    }
    
    /**
     * @param recobj
     * @throws SWBResourceException
     */    
    public void install(ResourceType recobj) throws SWBResourceException
    {
    }
    
    /**
     * @param request
     * @param response
     * @throws SWBResourceException
     * @throws IOException
     */    
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        throw new SWBResourceException("method not implemented");
    }
    
    /**
     * @param request
     * @param response
     * @param paramRequest
     * @throws SWBResourceException
     * @throws IOException
     */    
    public void render(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        if(!paramRequest.WinState_MINIMIZED.equals(paramRequest.getWindowState()))
        {
            processRequest(request, response, paramRequest);
        }
    }
    
    /**
     * @param request
     * @param response
     * @param paramRequest
     * @throws SWBResourceException
     * @throws IOException
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
     * @param request
     * @param response
     * @param paramRequest
     * @throws SWBResourceException
     * @throws IOException
     */    
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        throw new SWBResourceException("method not implemented - doView");
    }

    /**
     * @param request
     * @param response
     * @param paramRequest
     * @throws SWBResourceException
     * @throws IOException
     */    
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        throw new SWBResourceException("method not implemented - doEdit");
    }

    /**
     * @param request
     * @param response
     * @param paramRequest
     * @throws SWBResourceException
     * @throws IOException
     */    
    public void doHelp(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        throw new SWBResourceException("method not implemented - doHelp");
    }
    
    /**
     * @param request
     * @param response
     * @param paramRequest
     * @throws SWBResourceException
     * @throws IOException
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
     * @param request
     * @param response
     * @param paramRequest
     * @throws SWBResourceException
     * @throws IOException
     */    
    public void doAdminHlp(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        throw new SWBResourceException("method not implemented - doAdminHlp");
    }
    
    /**
     * @param request
     * @param response
     * @param paramRequest
     * @throws SWBResourceException
     * @throws IOException
     */    
    public void doIndex(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        doView(request, response, paramRequest);
    }
    
    /**
     * @param request
     * @param response
     * @param paramRequest
     * @throws SWBResourceException
     * @throws IOException
     */    
    public void doXML(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        throw new SWBResourceException("method not implemented - doXML");
    }

    /**
     * @param base
     * @throws SWBResourceException
     */    
    public void setResourceBase(Resource base) throws SWBResourceException
    {
        this.base=base;
    }
    
    /**
     * @param recobj
     * @throws SWBResourceException
     */    
    public void uninstall(ResourceType recobj) throws SWBResourceException
    {
    }
    
    /**
     * by default this method will return null when the request have paramaters
     */

    public String getResourceCacheID(HttpServletRequest request, SWBParamRequest paramRequest) throws SWBResourceException
    {
        if (request.getParameterNames().hasMoreElements())
        {
            return null;
        }else
        {
            Resource base=paramRequest.getResourceBase();
            return SWBResourceCachedMgr.getKey(base);
        }
    }
   
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
    
    public String getTitle(HttpServletRequest request, SWBParamRequest paramRequest) throws SWBResourceException, java.io.IOException
    {
        return paramRequest.getWindowTitle();
    }
    
    public String[] getWindowStates(HttpServletRequest request, SWBParamRequest paramRequest) throws SWBResourceException, java.io.IOException
    {
        return new String[]{paramRequest.WinState_MINIMIZED, paramRequest.WinState_NORMAL, paramRequest.WinState_MAXIMIZED};
    }
    
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
