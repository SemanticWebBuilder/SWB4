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

import javax.servlet.http.*;
import java.io.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceType;
import org.semanticwb.servlet.SWBHttpServletResponseWrapper;


// TODO: Auto-generated Javadoc
/**
 * Objeto: Se encarga de cachar el resultado de la consulta de un recurso.
 *
 * Object: Its in charge of catch the result of a resource query.
 *
 * @author Javier Solis Gonzalez
 */
public class SWBResourceCached implements SWBResource, SWBResourceWindow
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(SWBResourceCached.class);
    
    /** The resource. */
    SWBResource resource;
    
    /** The cache. */
    volatile String cache = null;
    
    /** The cachetime. */
    int cachetime = 0;
    
    /** The lasttime. */
    volatile long lasttime = 0;
    
    volatile boolean caching=false;
    
    /** The cache mgr. */
    SWBResourceCachedMgr cacheMgr;

    /**
     * Creates a new instance of WBResourceCached.
     * 
     * @param resource the resource
     */
    public SWBResourceCached(SWBResource resource)
    {
        this.resource = resource;
        try
        {
            cachetime = resource.getResourceBase().getResourceType().getResourceCache() * 1000;
        } catch (Exception e)
        {
            log.error(e);
        }
        cacheMgr=SWBPortal.getResourceMgr().getResourceCacheMgr();
    }

    /**
     * Inits the.
     * 
     * @throws SWBResourceException the sWB resource exception
     */    
    public void init() throws SWBResourceException
    {
        resource.init();
    }
    
    /**
     * regresa la informacion de base de datos del recurso.
     * 
     * @return the resource base
     * @return
     */
    public Resource getResourceBase()
    {
        return resource.getResourceBase();
    }
    
    /**
     * Process action.
     * 
     * @param request the request
     * @param response the response
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */    
    public void processAction(HttpServletRequest request, SWBActionResponse response)
        throws SWBResourceException, java.io.IOException
    {
        resource.processAction(request, response);
    }
    
    /**
     * Render.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */    
    public void render(final HttpServletRequest request, final HttpServletResponse response, final SWBParamRequest paramsRequest)
        throws SWBResourceException, java.io.IOException
    {
        if(paramsRequest.getMode().equals(paramsRequest.Mode_VIEW))
        {        
            String ret = null;
            if (cachetime == 0)
            {
                resource.render(request, response, paramsRequest);
            } else
            {
                cacheMgr.incCacheHits();
                {
                    if (!caching && (cache == null || (System.currentTimeMillis() - lasttime > cachetime)))
                    {
                        synchronized(this)
                        {
                            if(!caching && (cache == null || (System.currentTimeMillis() - lasttime > cachetime)))
                            {
                                caching=true;
                                cacheMgr.incCacheLoadHits();
                                lasttime = System.currentTimeMillis();
                                SWBHttpServletResponseWrapper res=new SWBHttpServletResponseWrapper(response);
                                try
                                {
                                    resource.render(request, res, paramsRequest);
                                    String tmp = res.toString();
                                    if(tmp!=null && tmp.length() > 0)
                                    {
                                        cache=tmp;
                                    }
                                }catch(Exception e)
                                {
                                    log.error(e);
                                }
                                caching=false;
                                //System.out.println("cache carcado:"+(cache!=null));
                            } 
                        }
                    }
                    ret = cache;
                    if(ret!=null)response.getWriter().print(ret);
                }
            }
            //System.out.println("resource:"+getResourceBase().getId()+" cached");
        }else
        {
            resource.render(request, response, paramsRequest);            
        }
    }
    
    
    /**
     * asigna la informacion de base de datos al recurso.
     * 
     * @param base the new resource base
     * @throws SWBResourceException the sWB resource exception
     */
    public void setResourceBase(Resource base) throws SWBResourceException
    {
        resource.setResourceBase(base);
        cache = null;
        try
        {
            cachetime = resource.getResourceBase().getResourceType().getResourceCache() * 1000;
        } catch (Exception e)
        {
            log.error(e);
        }
    }

    /**
     * Metodo que es llamado al momento de instalar el recurso en webbuilder.
     * 
     * @param recobj informaci�n de base de datos de la definici�n del Recurso
     * @throws SWBResourceException the sWB resource exception
     */
    public void install(ResourceType recobj) throws SWBResourceException
    {
        resource.install(recobj);
    }

    /**
     * Metodo que es llamado al momento de desinstalar el recurso en webbuilder.
     * 
     * @param recobj informaci�n de base de datos de la definici�n del Recurso
     * @throws SWBResourceException the sWB resource exception
     */
    public void uninstall(ResourceType recobj) throws SWBResourceException
    {
        resource.uninstall(recobj);
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBResource#destroy()
     */
    public void destroy()
    {
        resource.destroy();
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBResourceWindow#getModes(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBParamRequest)
     */
    public String[] getModes(HttpServletRequest request, SWBParamRequest paramRequest) throws SWBResourceException, java.io.IOException
    {
        if(resource instanceof SWBResourceWindow)
        {
            return ((SWBResourceWindow)resource).getModes(request, paramRequest);
        }
        return new String[]{paramRequest.Mode_VIEW};
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBResourceWindow#getTitle(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBParamRequest)
     */
    public String getTitle(HttpServletRequest request, SWBParamRequest paramRequest) throws SWBResourceException, java.io.IOException
    {
        if(resource instanceof SWBResourceWindow)
        {
            return ((SWBResourceWindow)resource).getTitle(request, paramRequest);
        }        
        return paramRequest.getWindowTitle();
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBResourceWindow#getWindowStates(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBParamRequest)
     */
    public String[] getWindowStates(HttpServletRequest request, SWBParamRequest paramRequest) throws SWBResourceException, java.io.IOException
    {
        if(resource instanceof SWBResourceWindow)
        {
            return ((SWBResourceWindow)resource).getWindowStates(request, paramRequest);
        }     
        return new String[]{paramRequest.WinState_MINIMIZED, paramRequest.WinState_NORMAL, paramRequest.WinState_MAXIMIZED};
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBResourceWindow#windowSupport(javax.servlet.http.HttpServletRequest, org.semanticwb.portal.api.SWBParamRequest)
     */
    public boolean windowSupport(HttpServletRequest request, SWBParamRequest paramRequest) throws SWBResourceException, java.io.IOException
    {
        if(resource instanceof SWBResourceWindow)
        {
            return ((SWBResourceWindow)resource).windowSupport(request, paramRequest);
        }          
        return false;
    }
    
}
