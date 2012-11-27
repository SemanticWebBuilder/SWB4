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
package org.semanticwb.portal.resources;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

// TODO: Auto-generated Javadoc
/** Objeto que se encarga de desplegar y administrar un contenido de tipo remoto en un frame independiente
 * bajo ciertos criterios (configuraci�n de recurso).
 *
 * Object that is in charge to unfold and to administer a content of remote type in
 * independent frame under certain criteria (resource configuration). 
 *
 * @author Infotec
 * @version 1.0
 */

public class FrameContent extends GenericAdmResource 
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(FrameContent.class);
    
    /**
     * Creates a new instance of FrameContent.
     */    
    public FrameContent() {
    }

    /**
     * Do view.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        Resource base=getResourceBase();

        StringBuffer ret = new StringBuffer("");        
        String ind = request.getParameter("WBIndexer");
        if (!"indexing".equals(ind))
        {
            try
            {
                if (paramRequest.getArguments().get("adm") == null)
                {
                    ret.append("<frame src=\"" + base.getAttribute("url").trim());
                     Enumeration en = request.getParameterNames();
                    for (int i=0; en.hasMoreElements(); i++)
                    {
                        String param = en.nextElement().toString();
                        if (param.equals("x") || param.equals("y")) {
                            continue;
                        }
                        if (request.getParameter(param).trim().length() > 0)
                        {
                            if ( i > 0) {
                                ret.append("&");
                            }
                            else {
                                ret.append("?");
                            }
                            ret.append(param +"=" + request.getParameter(param));
                        }
                    }
                    ret.append("\" name=\"bottom\" scrolling=\"Auto\" noresize=\"noresize\" border=\"0\" frameborder=\"no\" />");
                } 
                else if (paramRequest.getArguments().get("adm") != null )
                {
                    String line="";
                    URL page = new URL(base.getAttribute("url").trim());
                    URLConnection conn = page.openConnection();
                    DataInputStream in = new DataInputStream(conn.getInputStream());
                    do
                    {
                        line = in.readLine();
                        if (line != null) ret.append(line);
                    } while (line != null);
                    in.close();
                }
            } 
            catch (Exception e) { log.error("Error in resource FrameContent while bringing HTML.", e); }            
        }
        else 
        {
            try
            {
                URL page = new URL(base.getAttribute("url").trim());
                URLConnection conn = page.openConnection();
                InputStream in = conn.getInputStream();
                ret.append(SWBUtils.IO.readInputStream(in));
            } 
            catch (Exception e) { log.error("Error in resource FrameContent while bringing HTML.", e); }
        }
        PrintWriter out=response.getWriter();
        out.print(ret.toString());        
    }
}
