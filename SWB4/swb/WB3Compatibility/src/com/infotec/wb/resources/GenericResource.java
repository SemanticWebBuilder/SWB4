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
import org.semanticwb.SWBUtils;
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
        out.println("<div class=\"swbform\">");
        out.println("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\">");
        out.println("<tr>");
        out.println("<td class=datos>");
        out.println(SWBUtils.TEXT.getLocaleString("org.semanticwb.portal.api.GenericResource", "msgNotAdmin",new Locale(paramsRequest.getUser().getLanguage())));
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
