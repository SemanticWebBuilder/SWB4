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
 * SWBAMemory.java
 *
 * Created on 9 de noviembre de 2004, 04:39 PM
 */

package org.semanticwb.portal.admin.resources;


import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.SWBMonitor;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import javax.servlet.http.*;
import java.util.*;
import java.io.*;


/** Recurso de administraci�n de WebBuilder que monitorea el uso de memoria de la
 * aplicaci�n, su memoria m�xima, la memoria total, la memoria utilizada y la
 * memoria disponible. Permite tambi�n llamar al colector de basura.
 *
 * WebBuilder administration resource,it shows the memory used, the maximum
 * memory, the total memory, and the memory available, also allows to call the garbage
 * collector.
 * @author Javier Solis Gonzalez
 */
public class SWBAMMemory extends GenericResource {

    
    /** Creates a new instance of WBAMemory */
    public SWBAMMemory() {
    }
    
    
    /**
     * @param request
     * @param response
     * @throws SWBResourceException
     * @throws IOException
     */    
    @Override
    public void processAction(HttpServletRequest request,
            SWBActionResponse response)
            throws SWBResourceException, IOException {
        
        String gc = request.getParameter("gc");
        if (gc != null) {
            System.gc();
        }
    }
    
   /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        String act = request.getParameter("act");
        //out.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"10; URL="+paramsRequest.getTopic().getUrl()+"/\">");        
        out.println("<div class=\"swbform\">");
        
        if (SWBPlatform.getEnv("wb/systemMonitor", "false").equals("true")) {
            out.println("<fieldset>");
            out.println("<table width=\"98%\" cellpadding=10 cellspacing=0 border=0>");
            out.println("<tr><td>");

            out.println("<div class=\"applet\">");
            out.println("<APPLET code=\"applets.graph.WBGraph.class\" archive=\""
                    + SWBPlatform.getContextPath()
                    + "/swbadmin/lib/WBGraph.jar\" width=\"100%\" height=\"200\">");
            SWBResourceURL url = paramsRequest.getRenderUrl();
            url.setCallMethod(url.Call_DIRECT);
            url.setMode("getData");
            out.println("<param name=\"cgi\" value=\"" + url + "\">");
            out.println("<param name=\"reload\" value=\"5\">");
            out.println("</APPLET>");
            out.println("</div>");
            out.println("</td></tr>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("<fieldset>");
            SWBResourceURL urlS = paramsRequest.getActionUrl();
            out.println("  <form id=\""+getResourceBase().getId()+"/MMemory\" action=\"" + paramsRequest.getActionUrl() + "\" method=\"POST\">");
            out.println("  <button dojoType=\"dijit.form.Button\" name=\"gc\" onclick=\"submitForm('"+getResourceBase().getId()+"/MMemory'); return false;\" name=\"gc\">" + paramsRequest.getLocaleString("gc") + "</button>");
            //out.print("      <input type=\"submit\" name=\"gc\" value=\""+paramsRequest.getLocaleString("gc")+"\">&nbsp;&nbsp;");
            out.println("</form>");
            out.println("</fieldset>");
        } else {
            out.println(paramsRequest.getLocaleString("msgIsNotActive"));
        }
    }
    
    public void getData(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramsRequest)
            throws SWBResourceException, IOException {
        
        PrintWriter out = response.getWriter();

        int max = 100;
        int ratio = ((SWBPortal.getMonitor().getMonitorRecords().size() - 1) / max) + 1;
        Vector data = SWBPortal.getMonitor().getAverageMonitorRecords(ratio);
        int labc = data.size() / 10;
        if (labc == 0) {
            labc = 1;
        }
        
        out.println("GraphType=Lines");
        out.println("ncdata=3");
        out.println("percent=false");
        out.println("BrakeLabels=false");
        out.println("Title=" + paramsRequest.getLocaleString("memoryMonitor") + "");
        out.println("SubTitle=" + paramsRequest.getLocaleString("max") + ": "
                + (Runtime.getRuntime().maxMemory() / 1000000) + " MB");
        Enumeration en = data.elements();
        int x = -1;

        while (en.hasMoreElements()) {
            SWBMonitor.MonitorRecord mr = (SWBMonitor.MonitorRecord) en.nextElement();
            //long mmax=mr.getMaxMemory();
            long total = mr.getTotalMemory() / 1000000;
            long free = mr.getFreeMemory() / 1000000;
            long used = mr.getUsedMemory() / 1000000;
            if (x >= 0) {
                java.util.Date dt=mr.getDate();
                String date = "" + dt.getHours() + ":" + dt.getMinutes() + ":" + dt.getSeconds();
                out.println("label" + x + "=_" + date + "");
                out.println("data" + x + "=" + total + "|" + free + "|" + used + "");
            }
            x++;
        }
        out.println("ndata=" + x + "");
        out.println("color0=237,100,100");
        out.println("color1=229,243,50");
        out.println("color2=150,150,150");
        out.println("barname0=" + paramsRequest.getLocaleString("total") + "");
        out.println("barname1=" + paramsRequest.getLocaleString("free") + "");
        out.println("barname2=" + paramsRequest.getLocaleString("used") + "");
        out.println("zoom=true");
    }

    
    
    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */    
    @Override
    public void processRequest(HttpServletRequest request,
            HttpServletResponse response, SWBParamRequest paramsRequest)
            throws SWBResourceException, IOException {
        
        if (paramsRequest.getMode().equals("getData")) {
            getData(request, response, paramsRequest);
        } else {
            super.processRequest(request, response, paramsRequest);
        }
    }    
}