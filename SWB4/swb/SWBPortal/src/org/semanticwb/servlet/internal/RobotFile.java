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

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Dns;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;

// TODO: Auto-generated Javadoc
/**
 * The Class GoogleSiteMap.
 * 
 * @author serch
 */
public class RobotFile implements InternalServlet {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(RobotFile.class);

    /* (non-Javadoc)
     * @see org.semanticwb.servlet.internal.InternalServlet#init(javax.servlet.ServletContext)
     */
    public void init(ServletContext config) throws ServletException
    {
        log.event("Initializing InternalServlet robot.txt...");
    }

    /* (non-Javadoc)
     * @see org.semanticwb.servlet.internal.InternalServlet#doProcess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.servlet.internal.DistributorParams)
     */
    public void doProcess(HttpServletRequest req, HttpServletResponse resp, DistributorParams dparams) throws IOException, ServletException
    {
        String modelid=dparams.getModelId();
        String ret=SWBUtils.IO.getFileFromPath(SWBPortal.getWorkPath()+"/models/"+modelid+"/config/robots.txt");
        if(ret==null)
        {
            ret=SWBUtils.IO.getFileFromPath(SWBPortal.getWorkPath()+"/config/robots.txt");
        }
        if(ret!=null)
        {
            resp.setContentType("text/plain");
            resp.getWriter().print(ret);
        }else
        {
            resp.sendError(404);
        }
    }

}
