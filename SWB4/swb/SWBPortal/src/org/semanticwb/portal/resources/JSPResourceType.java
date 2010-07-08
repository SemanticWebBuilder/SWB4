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
 * JSPResourceType.java
 *
 * Created on 11 de diciembre de 2007, 09:26 PM
 *
 */

package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.platform.SemanticClass;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class JSPResourceType extends GenericResource
{
    //Subcalse de ResourceType
    public static SemanticClass classType =  org.semanticwb.model.JSPResourceType.sclass;

    private static Logger log = SWBUtils.getLogger(JSPResourceType.class);
    /**
     * Creates a new instance of JSPResourceType
     */
    public JSPResourceType()
    {
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        String path=((org.semanticwb.model.JSPResourceType)getResourceBase().getResourceType()).getJspPath();
        try
        {
            request.setAttribute("paramRequest", paramsRequest);
            RequestDispatcher dispatcher = request.getRequestDispatcher(path);
            if(dispatcher!=null)
            {
                if(getResourceBase().getAttribute("forward")!=null)
                {
                    dispatcher.forward(request, response);
                }else
                {
                    dispatcher.include(request, response);
                }
            }
        } 
        catch(Exception e)
        { 
            log.error(paramsRequest.getLocaleLogString("Process_Error") + "..." + path, e);
        }
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        String path=((org.semanticwb.model.JSPResourceType)getResourceBase().getResourceType()).getJspPath();
        out.println("<div class=\"box\" align=\"left\">");
        out.println("<b>Actual JSP</b>:"+path+"<br>");
        out.println("<b>Use ResourceType for configure JSP Path.</b><br>");
        out.println("<b>Example:</b><br><pre>");
        out.println("/jsp/menu.jsp");
        out.println("</pre></div>");
    }
       
    
}
