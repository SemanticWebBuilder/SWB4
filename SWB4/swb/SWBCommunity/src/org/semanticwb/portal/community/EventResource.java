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
package org.semanticwb.portal.community;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.*;

 /** @author Hasdai Pacheco {haxdai@gmail.com} */
public class EventResource extends org.semanticwb.portal.community.base.EventResourceBase {

    private static Logger log = SWBUtils.getLogger(EventResource.class);

    public EventResource() {
    }

    public EventResource(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = request.getParameter("act");
        WebPage page = response.getWebPage();
        Member mem = Member.getMember(response.getUser(), response.getWebPage());
        
        if (!mem.canView()) {
            return;                                       //si el usuario no pertenece a la red sale;
        }
        
        //System.out.println("act:" + action);
        if (action.equals("add") && mem.canAdd()) {
            String title = request.getParameter("event_title");
            String desc = request.getParameter("event_description");
            String place = request.getParameter("event_place");

            EventElement rec = EventElement.createEventElement(getResourceBase().getWebSite());
            rec.setTitle(title);
            rec.setDescription(desc);
            rec.setEventWebPage(page);
            rec.setPlace(place);

            //addVideoElement(rec);
            try {
                response.setRenderParameter("act", "edit");
                response.setRenderParameter("uri", rec.getURI());
            } catch (Exception e) {
                log.error(e);
                response.setRenderParameter("act", "add");               //regresa a agregar codigo
                response.setRenderParameter("err", "true");              //envia parametro de error
            }
        } else if (action.equals("edit")) {
            String uri = request.getParameter("uri");
            EventElement rec = (EventElement) SemanticObject.createSemanticObject(uri).createGenericInstance();
            
            if (rec != null && rec.canModify(mem)) {
                rec.setTitle(request.getParameter("event_title"));
                rec.setDescription(request.getParameter("event_description"));
                rec.setPlace(request.getParameter("event_place"));
                rec.setVisibility(Integer.parseInt(request.getParameter("level")));   //hace convercion a int en automatico

                if (page instanceof MicroSiteWebPageUtil) {
                    ((MicroSiteWebPageUtil) page).sendNotification(rec);
                }
            }
        } else if (action.equals("remove")) {
            String uri = request.getParameter("uri");
            EventElement rec = (EventElement) SemanticObject.createSemanticObject(uri).createGenericInstance();

            if (rec != null && rec.canModify(mem)) {
                rec.remove();                                       //elimina el registro
            }
        } else {
            super.processAction(request, response);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String action = request.getParameter("act");
        action = (action == null ? "view" : action);

        String path = "/swbadmin/jsp/microsite/EventResource/eventView.jsp";
        if (action.equals("add")) {
            path = "/swbadmin/jsp/microsite/EventResource/eventAdd.jsp";
        } else if (action.equals("edit")) {
            path = "/swbadmin/jsp/microsite/EventResource/eventEdit.jsp";
        } else if (action.equals("detail")) {
            path = "/swbadmin/jsp/microsite/EventResource/eventDetail.jsp";
        }

        RequestDispatcher dis = request.getRequestDispatcher(path);
        try {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e) {
            log.error(e);
        }
    }
}
