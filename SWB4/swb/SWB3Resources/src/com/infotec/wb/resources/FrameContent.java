/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * FrameContent.java
 *
 * Created on 20 de junio de 2002, 16:38
 */

package com.infotec.wb.resources;

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
import org.semanticwb.model.Portlet;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

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
    
    private static Logger log = SWBUtils.getLogger(FrameContent.class);
    
    /** Creates a new instance of FrameContent */    
    public FrameContent() {
    }

    /**
     * @param request
     * @param response
     * @param reqParams
     * @throws AFException
     * @throws IOException
     */    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        Portlet base=getResourceBase();
        if("".equals(base.getAttribute("url","").trim())) 
        { 
            //response.getWriter().print(""); 
            response.getWriter().println("<br><a href=\"" + paramRequest.getRenderUrl().setMode(paramRequest.Mode_ADMIN) + "\">admin</a>");
            return; 
        }

        StringBuffer ret = new StringBuffer("");        
        String ind = request.getParameter("WBIndexer");
        if (!"indexing".equals(ind))
        {
            try
            {
                if (paramRequest.getArguments().get("adm") == null)
                {
                    ret.append("<FRAME SRC=\"" + base.getAttribute("url").trim());
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
                    ret.append("\" NAME=\"bottom\" SCROLLING=\"Auto\" noresize BORDER=0 frameborder=\"NO\">");
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
        out.println("<br><a href=\"" + paramRequest.getRenderUrl().setMode(paramRequest.Mode_ADMIN) + "\">admin</a>");
    }
}