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
package org.semanticwb.blogger;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.servlet.internal.DistributorParams;
import org.semanticwb.servlet.internal.InternalServlet;
import org.semanticwb.xmlrpc.XMLRPCServlet;

/**
 *
 * @author victor.lorenzana
 */
public class MetaWebBlogServlet extends XMLRPCServlet implements InternalServlet
{

    private static final String title = "Gateway de Comunicación con Blogger";
    static Logger log = SWBUtils.getLogger(MetaWebBlogServlet.class);

    @Override
    public void init() throws ServletException
    {
        log.event("Adding mappingType blogger...");
        addMappingType("blogger", MetaWeblogImp.class);
        log.event("Adding mappingType metaWeblog...");
        addMappingType("metaWeblog", MetaWeblogImp.class);
    }

    public boolean isAuthenticate(String pUserName, String pPassword)
    {
        /*UserRepository ur = SWBContext.getAdminWebSite().getUserRepository();
        String context = ur.getLoginContext();
        Subject subject = new Subject();
        LoginContext lc;
        try
        {
            SWB4CallbackHandlerBlogger callbackHandler = new SWB4CallbackHandlerBlogger(pUserName, pPassword);
            lc = new LoginContext(context, subject, callbackHandler);
            lc.login();
            return true;
        }
        catch (Exception e)
        {
            log.debug("Can't log User", e);
        }
        return false;*/
        return true;
    }

    public void init(ServletContext config) throws ServletException
    {
        log.event("Initializing BloggerServlet...");
    }

    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException, ServletException
    {
        if (request.getMethod().toLowerCase().equals("post"))
        {
            super.doPost(request, response);
        }
        else
        {
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head>");
            out.println("<title>" + title + "</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>" + title + "</h1>");
            out.println("</body>");
            out.println("</html>");
            out.close();
        }
    }
}
