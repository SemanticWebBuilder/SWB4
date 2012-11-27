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
package org.semanticwb.portal.resources.sem.genericCalendar;

import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPortal;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBActionResponseImp;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBParamRequestImp;
import org.semanticwb.portal.api.SWBResource;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author jorge.jimenez
 */
public class VirtualEventResources extends GenericResource{

    @Override
    public void render(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        WebPage page = paramRequest.getWebPage();
        Resource base = paramRequest.getResourceBase();
        Resourceable resourceAble = null;
        if(request.getParameter("id") != null){ 
            String id = request.getParameter("id");
            resourceAble = Event.ClassMgr.getEvent(id, page.getWebSite());
            if(resourceAble != null) {
                Iterator<Resource> it = SWBComparator.sortSortableObject(resourceAble.listResources());
                while(it.hasNext()) {
                    Resource res = it.next();
                    if(paramRequest.getUser().haveAccess(res)) {
                        SWBResource swbres=SWBPortal.getResourceMgr().getResource(res);
                        ((SWBParamRequestImp)paramRequest).setResourceBase(res);
                        ((SWBParamRequestImp)paramRequest).setVirtualResource(base);
                        ((SWBParamRequestImp)paramRequest).setCallMethod(SWBParamRequest.Call_CONTENT);
                        swbres.render(request, response, paramRequest);
                    }
                }
            }
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        WebPage page = response.getWebPage();
        Resource base = response.getResourceBase();
        Resourceable resourceAble = null;
        if(request.getParameter("id") != null) { 
            String id = request.getParameter("id");
            resourceAble = Event.ClassMgr.getEvent(id, page.getWebSite());
            if(resourceAble != null) {
                Iterator<Resource> it = SWBComparator.sortSortableObject(resourceAble.listResources());
                while(it.hasNext()) {
                    Resource res = it.next();
                    if(res.isValid() && response.getUser().haveAccess(res)) {
                        SWBResource swbres = SWBPortal.getResourceMgr().getResource(res);
                        //SWBParamRequestImp pr=new SWBParamRequestImp(request,res,paramRequest.getWebPage(),paramRequest.getUser());
                        ((SWBActionResponseImp)response).setResourceBase(res);
                        ((SWBActionResponseImp)response).setVirtualResource(base);
                        swbres.processAction(request, response);
                    }
                }
            }
        }
    }
}
