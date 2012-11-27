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
package org.semanticwb.process.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author jei
 */
public class ViewModeler extends Modeler
{
    @Override
    public void doApplet(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html");
        String suri = request.getParameter("suri");
        String pending = request.getParameter("pending");
        String done = request.getParameter("done");
        if (pending == null)pending="";
        if (done == null)done="";
        PrintWriter out = response.getWriter();
        SWBResourceURL urlapp = paramsRequest.getRenderUrl();
        urlapp.setMode("gateway");
        urlapp.setCallMethod(urlapp.Call_DIRECT);
        urlapp.setParameter("suri", suri);

        out.println("<html>");
        out.println("<head>");
        out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">");
        out.println("<script src=\"http://dl.javafx.com/1.3/dtfx.js\"></script>");
        out.println("</head>");
        out.println(" <body style=\"margin-top:0; margin-left:0;\">");
        out.println("  <div style=\"width: 100%; height: 100%\">");
        out.println("    <script>");
        out.println("    javafx(");
        out.println("        {");
        out.println("              archive: \"" + SWBPlatform.getContextPath() + "/swbadmin/lib/SWBAppBPMNModeler.jar," + SWBPlatform.getContextPath() + "/swbadmin/lib/json.jar," + SWBPlatform.getContextPath() + "/swbadmin/lib/SWBAplCommons.jar\",");
        out.println("              draggable: true,");
        //out.println("              width: document.body.scrollWidth,");
        //out.println("              height: document.body.scrollHeight,");
        out.println("              width: \"100%\",");
        out.println("              height: \"100%\",");
        out.println("              code: \"org.semanticwb.process.modeler.Main\",");
        out.println("              name: \"SWBAppBPMNModeler\"");
        out.println("        },");
        out.println("        {");
        out.println("              cache_archive: \"" + SWBPlatform.getContextPath() + "/swbadmin/lib/SWBAppBPMNModeler.jar," + SWBPlatform.getContextPath() + "/swbadmin/lib/json.jar," + SWBPlatform.getContextPath() + "/swbadmin/lib/SWBAplCommons.jar\",");
        out.println("              cache_version: \"1.0,1.0,1.0\",");        
        out.println("              lang: \"" + paramsRequest.getUser().getLanguage() + "\",");
        out.println("              mode: \"view\",");
        out.println("              pending: \""+pending+"\",");
        out.println("              done: \""+done+"\",");
        out.println("              jsess: \"" + request.getSession().getId() + "\",");
        out.println("              cgipath: \"" + urlapp + "\"");
        out.println("        }");
        out.println("    );");
        out.println("    </script>");
        out.println("  </div>");
        out.println(" </body>");
        out.println("</html>");
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        String current = request.getParameter("current");
        if (current==null)current="";

        SWBResourceURL urlapp = paramsRequest.getRenderUrl();
        urlapp.setMode("applet");
        urlapp.setCallMethod(urlapp.Call_DIRECT);
        urlapp.setParameter("suri", suri);
        urlapp.setParameter("current", current);

        out.println("<div class=\"applet\">");
        out.println("<iframe dojoType_=\"dijit.layout.ContentPane\" src=\"" + urlapp + "\" width=\"800px\" height=\"600px\" frameborder=\"0\" scrolling=\"no\"></iframe>");
        //String idframe = "ifr_" + getResourceBase().getId();
        //out.println("<iframe alt=\"Modeler\" scrolling=\"no\" frameborder=\"0\" name==\"" + idframe + "\" id=\"" + idframe + "\" src=\"" + urlapp + "\" width=\"100%\" onload=\"this.style.height = " + idframe + ".document.body.scrollHeight + 5\" ></iframe>"); //height=\"100%\"
        out.println("</div>");
    }



}
