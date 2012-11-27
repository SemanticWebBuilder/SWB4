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

// TODO: Auto-generated Javadoc
/**
 * The Class JSPResourceType.
 * 
 * @author Javier Solis Gonzalez
 */
public class JSPResourceType extends GenericResource
{
    //Subcalse de ResourceType
    /** The class type. */
    public static SemanticClass classType =  org.semanticwb.model.JSPResourceType.sclass;

    /** The log. */
    private static Logger log = SWBUtils.getLogger(JSPResourceType.class);
    
    /**
     * Creates a new instance of JSPResourceType.
     */
    public JSPResourceType()
    {
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(HttpServletRequest, HttpServletResponse, SWBParamRequest)
     */
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

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doAdmin(HttpServletRequest, HttpServletResponse, SWBParamRequest)
     */
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
