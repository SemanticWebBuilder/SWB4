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
package org.semanticwb.portal.community;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 * Resource to manage user events.
 * <p>
 * Recurso para administrar los eventos de un usuario.
 * 
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class MyEvents extends GenericAdmResource {
    private static Logger log = SWBUtils.getLogger(MyEvents.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        //String mode = paramRequest.getArgument("mode");
        String action = request.getParameter("act");
        String mode = request.getParameter("mode");
        String url = "";
        Resourceable rsa = paramRequest.getResourceBase().getResourceable();
        if (rsa != null && rsa instanceof WebPage) {
                url = ((WebPage) rsa).getUrl();
        }
        String path = "/swbadmin/jsp/microsite/perfil/myEvents.jsp";
        if (action == null) action = "view";
        
        if (paramRequest.getCallMethod() == paramRequest.Call_STRATEGY) {
            path = "/swbadmin/jsp/microsite/perfil/myEventsCalendar.jsp";
        } else if (paramRequest.getCallMethod() == paramRequest.Call_CONTENT) {
            if (mode != null && mode.equals("calendar")) {
                path = "/swbadmin/jsp/microsite/perfil/myEventsCalendar.jsp";
            }
        }
        
        try {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("admurl", url);
            RequestDispatcher rd = request.getRequestDispatcher(path);
            rd.include(request, response);
        } catch (Exception e) {
            log.error("MyEvents say " + e);
        }        
    }
}
