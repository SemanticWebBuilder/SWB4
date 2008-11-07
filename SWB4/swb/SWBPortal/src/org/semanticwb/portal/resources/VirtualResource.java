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


package org.semanticwb.portal.resources;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

import org.semanticwb.SWBPortal;
import org.semanticwb.model.Portlet;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBParamRequestImp;
import org.semanticwb.portal.api.SWBResource;
import org.semanticwb.portal.api.SWBResourceException;

/** Esta clase muestra el contenido de cualquier recurso de WebBuilder, recibe el
 * identificador del recurso a desplegar.
 *
 * This class displays the content of any WebBuilder resource, receives the id
 * resource to show.
 * @author Javier Solis Gonzalez
 * @since : January 24th 2005, 19:27
 */
public class VirtualResource extends GenericAdmResource
{ 
    
    /** Creates a new instance of VirtualResource */
    public VirtualResource() {
    }
   
    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws AFException
     * @throws IOException
     */    
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        
        
        Portlet base=getResourceBase();
        String tmid=base.getAttribute("tmid","0");
        String id=base.getAttribute("resid","0");
        //TODO quitar la siguiente línea
        SWBResource res=SWBPortal.getResourceMgr().getResource( paramRequest.getTopic().getWebSiteId(), id);
        //TODO descomentar la siguiente línea
        //SWBResource res=SWBPortal.getResourceMgr().getResource(tmid, id);
                
        if(res!=null) {
            //base=res.getResourceBase();
            // TODO agregar validación base.haveAccess(paramsRequest.getUser()) al siguiente if
            // TODO agregar validación SWBIntervalEvaluation.eval(new java.util.Date(),base) al siguiente if
            if(base.isActive() && !base.isDeleted() ) {
                ((SWBParamRequestImp)paramRequest).setResourceBase(base);
                ((SWBParamRequestImp)paramRequest).setVirtualResource(base);                
                res.render(request, response, paramRequest);                
            }
        }else {
            out.println(paramRequest.getLocaleString("notfound") + "...");
        }       
        out.println("<br><a href=\"" + paramRequest.getRenderUrl().setMode(paramRequest.Mode_ADMIN) + "\">admin virtual resource</a>");
    }
}
