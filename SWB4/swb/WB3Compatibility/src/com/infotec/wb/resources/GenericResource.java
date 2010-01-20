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


/*
 * GenericResource.java
 *
 * Created on 1 de junio de 2004, 07:14 PM
 */

package com.infotec.wb.resources;

import com.infotec.wb.lib.WBResource;
import com.infotec.wb.lib.WBResourceCache;
import com.infotec.wb.lib.WBActionResponse;
import com.infotec.wb.lib.WBParamRequest;
import com.infotec.wb.lib.WBResourceWindow;
import com.infotec.wb.core.Resource;
import com.infotec.appfw.exception.AFException;
import com.infotec.appfw.util.AFUtils;
import com.infotec.topicmaps.TopicMap;
import com.infotec.wb.core.db.RecResourceType;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;
import org.semanticwb.model.SWBContext;
import org.semanticwb.portal.api.SWBResourceCachedMgr;

/** Recurso gen�rico utilizado como plantilla para la generaci�n de nuevos recursos para
 * WebBuilder.
 *
 * Used generic resource used as it template for the generation of new resources
 * for WebBuilder.
 *
 * @author Javier Solis Gonzalez
 */
public class GenericResource implements WBResource, WBResourceCache, WBResourceWindow
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
     * @throws AFException
     */    
    public void init() throws AFException
    {
    }
    
    /**
     * @param recobj
     * @throws AFException
     */    
    public void install(RecResourceType recobj) throws AFException
    {
    }
    
    /**
     * @param request
     * @param response
     * @throws AFException
     * @throws IOException
     */    
    public void processAction(HttpServletRequest request, WBActionResponse response) throws AFException, IOException
    {
        throw new AFException("method not implemented","processAction");
    }
    
    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws AFException
     * @throws IOException
     */    
    public void render(HttpServletRequest request, HttpServletResponse response, WBParamRequest paramsRequest) throws AFException, IOException
    {
        if(!paramsRequest.WinState_MINIMIZED.equals(paramsRequest.getWindowState()))
        {
            processRequest(request, response, paramsRequest);
        }
    }
    
    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws AFException
     * @throws IOException
     */    
    public void processRequest(HttpServletRequest request, HttpServletResponse response, WBParamRequest paramsRequest) throws AFException, IOException
    {
        if(paramsRequest.getMode().equals(paramsRequest.Mode_VIEW))
        {
            doView(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equals(paramsRequest.Mode_EDIT))
        {
            doEdit(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equals(paramsRequest.Mode_HELP))
        {
            doHelp(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equals(paramsRequest.Mode_ADMIN))
        {
            doAdmin(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equals(paramsRequest.Mode_ADMHLP))
        {
            doAdminHlp(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equals(paramsRequest.Mode_INDEX))
        {
            doIndex(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equals(paramsRequest.Mode_XML))
        {
            doXML(request,response,paramsRequest);
        }else 
        {
            throw new AFException("method not implemented","Mode:("+paramsRequest.getMode()+"), URL:("+request.getRequestURL()+"), Ref:("+request.getHeader("referer")+")");
        }
    }
    
    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws AFException
     * @throws IOException
     */    
    public void doView(HttpServletRequest request, HttpServletResponse response, WBParamRequest paramsRequest) throws AFException, IOException
    {
        throw new AFException("method not implemented","doView");
    }

    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws AFException
     * @throws IOException
     */    
    public void doEdit(HttpServletRequest request, HttpServletResponse response, WBParamRequest paramsRequest) throws AFException, IOException
    {
        throw new AFException("method not implemented","doEdit");
    }

    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws AFException
     * @throws IOException
     */    
    public void doHelp(HttpServletRequest request, HttpServletResponse response, WBParamRequest paramsRequest) throws AFException, IOException
    {
        throw new AFException("method not implemented","doHelp");
    }
    
    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws AFException
     * @throws IOException
     */    
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, WBParamRequest paramsRequest) throws AFException, IOException
    {
        java.io.PrintWriter out = response.getWriter();
        out.println("<div class=\"box\">");
        out.println("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\">");
        out.println("<tr>");
        out.println("<td class=datos>");
        out.println(AFUtils.getLocaleString("com.infotec.wb.resources.GenericResource", "msgNotAdmin",new Locale(paramsRequest.getUser().getLanguage())));
        out.println("</td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("</div>");
    }
    
    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws AFException
     * @throws IOException
     */    
    public void doAdminHlp(HttpServletRequest request, HttpServletResponse response, WBParamRequest paramsRequest) throws AFException, IOException
    {
        throw new AFException("method not implemented","doAdminHlp");
    }
    
    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws AFException
     * @throws IOException
     */    
    public void doIndex(HttpServletRequest request, HttpServletResponse response, WBParamRequest paramsRequest) throws AFException, IOException
    {
        doView(request, response, paramsRequest);
    }
    
    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws AFException
     * @throws IOException
     */    
    public void doXML(HttpServletRequest request, HttpServletResponse response, WBParamRequest paramsRequest) throws AFException, IOException
    {
        throw new AFException("method not implemented","doXML");
    }

    /**
     * @param base
     * @throws AFException
     */    
    public void setResourceBase(Resource base) throws AFException
    {
        this.base=base;
    }
    
    /**
     * @param recobj
     * @throws AFException
     */    
    public void uninstall(RecResourceType recobj) throws AFException
    {
    }
    
    /**
     * by default this method will return null when the request have paramaters
     */

    public String getResourceCacheID(HttpServletRequest request, WBParamRequest paramsRequest) throws AFException
    {
        if (request.getParameterNames().hasMoreElements())
        {
            return null;
        }else
        {
            Resource base=paramsRequest.getResourceBase();
            return SWBResourceCachedMgr.getKey(base.getNative());
        }
    }
   
    public String[] getModes(HttpServletRequest request, WBParamRequest paramRequest) throws AFException, java.io.IOException
    {
        if(paramRequest.getUser().getNative().getUserRepository().getId().equals(SWBContext.USERREPOSITORY_ADMIN))
        {
            return new String[]{paramRequest.Mode_VIEW,paramRequest.Mode_ADMIN};
        }
        return new String[]{paramRequest.Mode_VIEW};
    }
    
    public String getTitle(HttpServletRequest request, WBParamRequest paramRequest) throws AFException, java.io.IOException
    {
        return paramRequest.getWindowTitle();
    }
    
    public String[] getWindowStates(HttpServletRequest request, WBParamRequest paramRequest) throws AFException, java.io.IOException
    {
        return new String[]{paramRequest.WinState_MINIMIZED, paramRequest.WinState_NORMAL, paramRequest.WinState_MAXIMIZED};
    }
    
    public boolean windowSupport(HttpServletRequest request, WBParamRequest paramRequest) throws AFException, java.io.IOException
    {
        //System.out.println("----> "+paramRequest.getResourceBase().getId()+" windowSupport:"+paramRequest.getResourceBase().getConfAttribute("portletWindow","0"));
        if(!paramRequest.getAdminTopic().getNative().getWebSiteId().equals(SWBContext.WEBSITE_ADMIN))
        {
            if(paramRequest.getResourceBase().getNative().isResourceWindow())
            {
                return true;
            }
        }
        return false;
    }
    
}
