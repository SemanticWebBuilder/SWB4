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
 * SWBACacheMonitor.java
 *
 * Created on 5 de enero de 2005, 10:02 AM
 */

package org.semanticwb.portal.admin.resources;

import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.SWBMonitor;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
//import org.semanticwb.Logger;


/** Muestra una gráfica de los recursosen memoria utilizados en la  
 * aplicación en un periodo de tiempo.
 *
 * Shows a chart of the resouces in memory into the application
 * in a time period.
 * @author Juan Antonio Fernández Arias
 */
public class SWBACacheMonitor extends GenericResource {
    
    /** Creates a new instance of WBACacheMonitor */
    public SWBACacheMonitor() {
    }
    
    /** User view of the WBACacheMonitor resource
     * @param request
     * @param response
     * @param paramsRequest
     * @throws SWBResourceException
     * @throws IOException
     */    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramsRequest) 
            throws SWBResourceException, IOException {
        
        PrintWriter out = response.getWriter();
        //String act = request.getParameter("act");
        
        out.println("<div class=\"swbform\">");
        
        if (SWBPlatform.getEnv("wb/systemMonitor", "false").equals("true")) {
            out.println("<fieldset>");
            out.println("<table width=98% cellpadding=10 cellspacing=0 border=0>");
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
        } else {
            out.println(paramsRequest.getLocaleString("msgIsNotActive"));
        }
        out.println("</div>");
    }
    
    public void getData(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramsRequest)
            throws SWBResourceException, IOException {
        
        PrintWriter out = response.getWriter();

        int max=100;
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
        out.println("Title=" + paramsRequest.getLocaleString("msgCacheMonitor") + "");
        Enumeration en = data.elements();
        int x = -1;

        long oldreshits = 0;
        long oldrescache = 0;
        long oldloadrescache = 0;

        while (en.hasMoreElements()) {
            SWBMonitor.MonitorRecord mr = (SWBMonitor.MonitorRecord) en.nextElement();
            long reshits = mr.getResourceHits();
            long rescache = mr.getCacheResHits();
            long loadrescache = mr.getCacheResLoadHits();
            if (x >= 0) {
                java.util.Date dt = mr.getDate();
                String date = "" + dt.getHours() + ":" + dt.getMinutes()
                              + ":" + dt.getSeconds();
                //if(x%labc==0)
                //    out.println("label"+x+"="+date+"");
                //else
                    out.println("label" + x + "=_" + date + "");

                long difreshits = (reshits - oldreshits)
                        / (SWBPortal.getMonitor().getDelay() * ratio);
                long difrescache = (rescache - oldrescache)
                        /(SWBPortal.getMonitor().getDelay() * ratio);
                long difloadrescache = (loadrescache - oldloadrescache)
                        / (SWBPortal.getMonitor().getDelay() * ratio);

                out.println("data" + x + "=" + difreshits + "|" + difrescache
                        + "|" + difloadrescache + "");
            }
            oldreshits = reshits;
            oldrescache = rescache;
            oldloadrescache = loadrescache;
            x++;
        }
        out.println("ndata=" + x + "");
        out.println("color0=66,138,212");
        out.println("color1=237,237,100");
        out.println("color2=229,100,100");
        out.println("barname0=" + paramsRequest.getLocaleString("msgResXseg") + "");
        out.println("barname1=" + paramsRequest.getLocaleString("msgResCachedXSec") + "");
        out.println("barname2=" + paramsRequest.getLocaleString("msgResCacheLoaded") + "");
        out.println("zoom=true");
    }
    
    /**  Process the user request
     * @param request input parameters
     * @param response an answer to the request
     * @param paramsRequest a list of objects (topic, user, action, ...)
     * @throws SWBResourceException an SWBResource Exception
     * @throws IOException an IO Exception
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