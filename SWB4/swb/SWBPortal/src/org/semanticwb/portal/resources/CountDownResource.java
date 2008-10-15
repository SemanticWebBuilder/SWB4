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
 * CountDownResource.java
 *
 * Created on 25 de julio de 2005, 01:47 PM
 */

package org.semanticwb.portal.resources;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import org.semanticwb.SWBPlatform;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBParamRequest;


/**
 *
 * @author Javier Solis Gonzalez
 */
public class CountDownResource extends GenericAdmResource
{
    
    /** Creates a new instance of CountDownResource */
    public CountDownResource()
    {
    }
    
    
    
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        PrintWriter out=response.getWriter();
        String width=getResourceBase().getAttribute("width","120");
        String height=getResourceBase().getAttribute("height","25");
        String date=getResourceBase().getAttribute("endtime","12/12/2012");
        String backgrownd=getResourceBase().getAttribute("backgrownd","FFFFFF");
        Date d=new Date(date);
        
        out.println("<APPLET code=\"applets/clocks/CountDown.class\" codebase=\""+SWBPlatform.getContextPath()+"\" ARCHIVE=\"wbadmin/lib/clock.jar, wbadmin/lib/WBCommons.jar\" width="+width+" height="+height+">");
        out.println("<param name=\"actualTime\" value=\""+(new Date()).getTime()+"\">");
        out.println("<param name=\"endTime\" value=\""+d.getTime()+"\">");
        out.println("<param name=\"backgrownd\" value=\""+backgrownd+"\">");
        out.println("</APPLET>");
    }
    
}
