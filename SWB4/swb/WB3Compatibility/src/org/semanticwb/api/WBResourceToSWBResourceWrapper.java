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
package org.semanticwb.api;

import com.infotec.appfw.exception.AFException;
import com.infotec.wb.core.db.RecResourceType;
import com.infotec.wb.lib.WBActionResponseImp;
import com.infotec.wb.lib.WBParamRequestImp;
import com.infotec.wb.lib.WBResourceCache;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceType;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBActionResponseImp;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBParamRequestImp;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author javier.solis
 */
public class WBResourceToSWBResourceWrapper extends GenericResource
{
    private com.infotec.wb.lib.WBResource res = null;

    public WBResourceToSWBResourceWrapper(com.infotec.wb.lib.WBResource wbResource)
    {
//        System.out.println("wbResource:"+wbResource);
//        Thread.currentThread().dumpStack();
        this.res = wbResource;
    }

    public com.infotec.wb.lib.WBResource getOldResource()
    {
        return res;
    }

    /**
     * @throws SWBResourceException
     */
    @Override
    public void init() throws SWBResourceException
    {
        try
        {
            res.init();
        }catch(Exception e){throw new SWBResourceException(e.getMessage(),e);}
    }

    /**
     * @param request
     * @param response
     * @throws SWBResourceException
     * @throws IOException
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        try
        {
            res.processAction(request, new WBActionResponseImp((SWBActionResponseImp)response));
        }catch(AFException e){throw new SWBResourceException(e.getMessage(),e);}
    }


  /**
     * @return
     */
    @Override
    public Resource getResourceBase()
    {
        return res.getResourceBase().getNative();
    }


    /**
     * @param recobj
     * @throws SWBResourceException
     */
    @Override
    public void install(ResourceType recobj) throws SWBResourceException
    {
        try
        {
            res.install(new RecResourceType(recobj));
        }catch(AFException e){throw new SWBResourceException(e.getMessage(),e);}
    }


    /**
     * @param request
     * @param response
     * @param paramRequest
     * @throws SWBResourceException
     * @throws IOException
     */
    @Override
    public void render(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        try
        {
            res.render(request, response, new WBParamRequestImp((SWBParamRequestImp)paramRequest));
        }catch(AFException e){throw new SWBResourceException(e.getMessage(),e);}
    }



    /**
     * @param base
     * @throws SWBResourceException
     */
    @Override
    public void setResourceBase(Resource base) throws SWBResourceException
    {
        try
        {
            res.setResourceBase(new com.infotec.wb.core.Resource(base));
        }catch(AFException e){throw new SWBResourceException(e.getMessage(),e);}
    }

    /**
     * @param recobj
     * @throws SWBResourceException
     */
    @Override
    public void uninstall(ResourceType recobj) throws SWBResourceException
    {
        try
        {
            res.uninstall(new RecResourceType(recobj));
        }catch(AFException e){throw new SWBResourceException(e.getMessage(),e);}
    }

    /**
     * by default this method will return null when the request have paramaters
     */
    @Override
    public String getResourceCacheID(HttpServletRequest request, SWBParamRequest paramRequest) throws SWBResourceException
    {
        try
        {
            return ((WBResourceCache)res).getResourceCacheID(request, new WBParamRequestImp((SWBParamRequestImp)paramRequest));
        }catch(Exception e){throw new SWBResourceException(e.getMessage(),e);}
    }


}
