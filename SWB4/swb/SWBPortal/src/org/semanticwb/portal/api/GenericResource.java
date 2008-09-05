
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
import org.semanticwb.model.PortletType;
import org.semanticwb.model.Portlet;
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
    private Portlet base;
    
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
    public Portlet getResourceBase()
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
    public void install(PortletType recobj) throws SWBResourceException
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
        out.println("<div class=\"box\">");
        out.println("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\">");
        out.println("<tr>");
        out.println("<td class=datos>");
        out.println(SWBUtils.TEXT.getLocaleString("org.semanticwb.portal.resources.GenericResource", "msgNotAdmin",new Locale(paramRequest.getUser().getLanguage().getId())));
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
    public void setResourceBase(Portlet base) throws SWBResourceException
    {
        this.base=base;
    }
    
    /**
     * @param recobj
     * @throws SWBResourceException
     */    
    public void uninstall(PortletType recobj) throws SWBResourceException
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
            Portlet base=paramRequest.getResourceBase();
            return SWBResourceCachedMgr.getKey(base);
        }
    }
   
    public String[] getModes(HttpServletRequest request, SWBParamRequest paramRequest) throws SWBResourceException, java.io.IOException
    {
        WebSite tm=SWBContext.getAdminWebSite();
        if(tm!=null)
        {
            //TODO:implementar
//            if(paramRequest.getUser().havePermission(tm.getTopic("WBAd_per_Administrator")))
//            {
//                return new String[]{paramRequest.Mode_VIEW,paramRequest.Mode_ADMIN};
//            }
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
        //System.out.println("----> "+paramRequest.getResourceBase().getId()+" windowSupport:"+paramRequest.getResourceBase().getConfAttribute("portletWindow","0"));
        if(!paramRequest.getAdminTopic().getWebSiteId().equals(SWBContext.WEBSITE_ADMIN))
        {
//            TopicMap tm=TopicMgr.getInstance().getTopicMap(TopicMgr.TM_ADMIN);
//            if(tm!=null)
//            {
//                if(paramRequest.getUser().havePermission(tm.getTopic("WBAd_per_Administrator")))
//                {
//                    return true;
//                }
//            }
            if(paramRequest.getResourceBase().isPortletWindow())
            {
                return true;
            }
        }
        return false;
    }
    
}
