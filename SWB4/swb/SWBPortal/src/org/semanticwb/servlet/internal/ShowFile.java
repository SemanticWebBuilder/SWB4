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
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
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
            String pathType=request.getParameter("pathType");
            String content = "";
            if(pathType.equals("def")) {
                    content=SWBUtils.IO.readInputStream(SWBPortal.getAdminFileStream(path));
            }else if(pathType.equals("res")) {
                    content=SWBUtils.IO.readInputStream(SWBPortal.getFileFromWorkPath(path));
            }else {
                FileInputStream fileInput=new FileInputStream(path);
                content=SWBUtils.IO.readInputStream(fileInput);
            }

            StringBuilder htm = new StringBuilder();
            htm.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\"\n");
            htm.append("\"http://www.w3.org/TR/html4/strict.dtd\">\n");
            htm.append("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"es\" lang=\"es\">\n");
            htm.append("    <META HTTP-EQUIV=\"CONTENT-TYPE\" CONTENT=\"text/html; charset=ISO-8859-1\">\n");
            htm.append("    <META HTTP-EQUIV=\"CACHE-CONTROL\" CONTENT=\"NO-CACHE\">\n");
            htm.append("    <META HTTP-EQUIV=\"PRAGMA\" CONTENT=\"NO-CACHE\">\n");
            htm.append("<head>\n");
            htm.append("<title>SemanticWebBuilder - Show file</title>\n");
            htm.append("</head>\n");
            htm.append("<body class=\"soria\">\n");
            htm.append("  <p><input type=\"button\" onclick=\"history.go(-1);\" value=\"Regresar\"/></p>");
            htm.append("  <p><textarea cols=\"100\" rows=\"45\">");
            htm.append(content);
            htm.append("  </textarea></p>\n");
            htm.append("  <p><input type=\"button\" onclick=\"history.go(-1);\" value=\"Regresar\"/></p>\n");
            htm.append("</body>\n");
            htm.append("</html>");
            PrintWriter out = response.getWriter();
            out.println(htm.toString());
        } catch (Exception e) {
            e.printStackTrace(System.out);
            log.error(e);
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