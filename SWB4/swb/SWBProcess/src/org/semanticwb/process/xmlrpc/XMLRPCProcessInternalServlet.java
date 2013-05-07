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
package org.semanticwb.process.xmlrpc;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.servlet.internal.DistributorParams;
import org.semanticwb.servlet.internal.InternalServlet;

/**
 * Implementa un servlet interno de XMLRPC para manejar las llamadas al API de servicios de SWBProcess.
 * @author victor.lorenzana
 */
public class XMLRPCProcessInternalServlet implements InternalServlet
{
    /**Logger. Objeto para logueo. */
    static Logger log = SWBUtils.getLogger(XMLRPCProcessInternalServlet.class);
    /**Contexto. Servlet que maneja las peticiones de SWBProcess. */
    ProcessServlet servletContext = new ProcessServlet();
    
    /**
     * Inicializa el servlet interno.
     * @param config contexto.
     * @throws ServletException 
     */
    @Override
    public void init(ServletContext config) throws ServletException
    {
        log.event("Initializing XMLRPCProcessServlet...");
        servletContext.init("org.semanticwb.process.xmlrpc.");
        ProcessServlet.addMappingType("RPCProcess", RPCProcessImp.class);
    }

    /**
     * Procesa las peticiones hechas al servlet interno.
     * @param request request.
     * @param response response.
     * @param dparams parámetros del distribuidor.
     * @throws IOException
     * @throws ServletException 
     */
    @Override
    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException, ServletException
    {
        if (request.getMethod().toLowerCase().equals("post"))
        {
            servletContext.doPost(request, response);
        }
        else
        {
            if (request.getParameter("wsdl") != null)
            {
                servletContext.doWDSL(request, response);

                return;
            }
        }
    }
}
