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
import javax.servlet.http.*;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.*;

public class ChatResource extends org.semanticwb.portal.community.base.ChatResourceBase
{

    public ChatResource()
    {
    }

    public ChatResource(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        String commId = getCommId(paramRequest.getWebPage());
        if (null==commId) commId = paramRequest.getWebPage().getId();
        out.println("<div align=\"center\">\n");
        out.println("        <APPLET CODE=\"class_AppletChat.class\"  codebase=\"/swbadmin\" archive=\"appletChat.jar\" WIDTH=\"508\" HEIGHT=\"285\" id=\"Applet1\" VIEWASTEXT>\n");
        out.println("                <PARAM name=\"anchoApp\" value=\"508\">\n");
        out.println("                <PARAM name=\"altoApp\" value=\"245\">\n");
        out.println("                <PARAM name=\"puerto\" value=\"9494\">\n");
        out.println("                <PARAM name=\"moderador\" value=\"1\">\n");
        out.println("                <PARAM NAME=\"locale\" VALUE=\"es\">");
        out.println("                <PARAM name=\"idUsuario\" value=\"" + paramRequest.getUser().getLogin() + "\">\n");
        out.println("                <PARAM name=\"idComunidad\" value=\"" + commId + "\">\n");
        out.println("        </APPLET>   \n");
        out.println("</div>\n");
    }

    private String getCommId(WebPage current)
    {
        String ret = null;
        if (current instanceof MicroSite)
        {
            ret = current.getId();
        } else if (current instanceof MicroSiteWebPageUtil)
        {
            ret = getCommId(((MicroSiteWebPageUtil) current).getMicroSite());
        }
        return ret;

    }
}



