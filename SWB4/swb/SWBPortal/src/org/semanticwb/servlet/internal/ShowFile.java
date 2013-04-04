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
package org.semanticwb.servlet.internal;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;

/**
 * The Class ShowFile.
 * 
 * @author carlos.ramos
 */
public class ShowFile implements InternalServlet {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(ShowFile.class);

    /* (non-Javadoc)
     * @see org.semanticwb.servlet.internal.InternalServlet#doProcess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.servlet.internal.DistributorParams)
     */
    @Override
    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException, ServletException {
        try {
            String path = request.getParameter("file");
            String pathType = request.getParameter("pathType");
            String content = "";
            if (pathType.equals("def")) {
                content = SWBUtils.IO.readInputStream(SWBPortal.getAdminFileStream(path));
            } else if (pathType.equals("res")) {
                content = SWBUtils.IO.readInputStream(SWBPortal.getFileFromWorkPath(path));
            } else {
                FileInputStream fileInput = new FileInputStream(path);
                content = SWBUtils.IO.readInputStream(fileInput);
            }
            PrintWriter out = response.getWriter();

            out.println("<html>");
            out.println("<head>");
            out.println(" <meta http-equiv='Content-Type' content='text/html; charset=utf-8'>");
            out.println(" <title>EditArea Test</title>");
            //out.println(" <script language=\"javascript\" type=\"text/javascript\" src=\"/swb/swbadmin/js/editarea/edit_area/edit_area_full.js\"></script>");
            out.println("</head>");
            out.println("<body>");
            out.println(" <applet alt=\"editar xsl\" codebase=\"" + SWBPlatform.getContextPath() + "/\" code=\"applets.edit.XSLEditorApplet\" archive=\"swbadmin/lib/SWBAplXSLEditor.jar, swbadmin/lib/rsyntaxtextarea.jar\" width=\"100%\" height=\"100%\">");
            out.println("  <param name=\"content\" value=\"" + URLEncoder.encode(content, "UTF-8") + "\" />");
            out.println("  <param name=\"isDefaultTemplate\" value=\"true\" />");
            out.println(" </applet>");
            out.println("</body> \n");
            out.println("</html> \n");

        } catch (Exception e) {
            e.printStackTrace(System.out);
            log.debug(e);
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.servlet.internal.InternalServlet#init(javax.servlet.ServletContext)
     */
    @Override
    public void init(ServletContext config) throws ServletException {
        log.event("Initializing InternalServlet ShowFile...");
    }
}