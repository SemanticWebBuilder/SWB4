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
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.community.utilresources.CommunityActivity;
import org.semanticwb.portal.community.utilresources.CommunityActivityUtil;

/**
 *
 * @author juan.fernandez
 */
public class CommunityActivitiesResource extends GenericAdmResource {

    private static Logger log = SWBUtils.getLogger(CommunityActivitiesResource.class);
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        Resource base = getResourceBase();
        int numrec = Integer.parseInt(base.getAttribute("numrec","10"));        
        WebPage wp = paramRequest.getWebPage();
        if(wp instanceof MicroSite)
        {
            MicroSite ms = (MicroSite)wp;
            CommunityActivityUtil cau = new CommunityActivityUtil();
            Iterator<CommunityActivity> activities = cau.getCommunityActivities(ms);
            String path = "/swbadmin/jsp/microsite/CommunityActivitiesResource/CommunityActivitiesResource.jsp";
            RequestDispatcher dis = request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("numrec", numrec);
            request.setAttribute("activities", activities);
            dis.include(request, response);
        }
        catch (Exception e)
        {
            log.error(e);
        }
            /*out.println("<div id=\"contactos\">");
            out.println("<h2>Actividades</h2>");

            out.println("<ul>");
            CommunityActivity ca = null;
            User user = null;
            MicroSiteElement mse = null;
            if(itca.hasNext())
            {
                int num = 0;
                while(itca.hasNext())
                {
                    num++;
                    if (num > numrec) {
                        break;
                    }

                    ca = itca.next();
                    user = ca.getUser();
                    mse = ca.getElement();

                    if(mse!=null&&user!=null&&ms!=null)
                    {
                        out.println("<li><a href=\""+mse.getURL()+"\">" + mse.getDisplayTitle(user.getLanguage()) );
                        out.println("("+mse.getSemanticObject().getSemanticClass().getDisplayName(user.getLanguage())+")</a>, ");
                        out.println("" + user.getFullName() + ", ");
                        out.println("<a class=\"contactos_nombre\" href=\"#\">"+SWBUtils.TEXT.getTimeAgo(mse.getUpdated(),user.getLanguage()) + "</a></li>");
                    }
                }
            }
            else
                out.println("<li>No hay actividades.</li>");
            out.println("</ul>");
            out.println("</div>");*/
        }

    }

}
