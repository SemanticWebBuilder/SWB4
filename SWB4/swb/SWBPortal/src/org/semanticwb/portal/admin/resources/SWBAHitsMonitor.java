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
 * SWBAHitsMonitor.java
 *
 * Created on 5 de enero de 2005, 10:02 AM
 */

package org.semanticwb.portal.admin.resources;

import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.SWBMonitor;
//import org.semanticwb.SWBUtils;
//import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
//import java.sql.*;


/** Muestra una gráfica para monitorear los hits en unperiodo de tiempo.
 *
 * Shows a graph to monitoring the hits in a period of time.
 * @author INFOTEC
 */
public class SWBAHitsMonitor extends GenericResource {
    
    //log utilizado en los metodos comentados:
    //private static Logger log = SWBUtils.getLogger(SWBAHitsMonitor.class);

    
    /** Creates a new instance of WBAHitsMonitor */
    public SWBAHitsMonitor() {
    }
    
    /** User view of the SWBAHitsMonitor resource
     * @param request input parameters
     * @param response an answer to the request
     * @param paramsRequest a list of objects (topic, user, action, ...)
     * @throws SWBResourceException an AF Exception
     * @throws IOException an IO Exception
     */    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramsRequest) 
            throws SWBResourceException, IOException {
        
        PrintWriter out = response.getWriter();
        String act = request.getParameter("act");
        
        out.println("<div class=\"swbform\">");
        out.println("<fieldset>");
        if (SWBPlatform.getEnv("wb/systemMonitor","false").equals("true")) {
            out.println("<table width=100% cellpadding=10 cellspacing=0 border=0>");
            out.println("<tr><td>");
            
            out.println("<APPLET code=\"applets.graph.WBGraph.class\" archive=\""
                    + SWBPlatform.getContextPath()
                    + "/swbadmin/lib/WBGraph.jar\" width=\"100%\" height=\"200\">");
            SWBResourceURL url = paramsRequest.getRenderUrl();
            url.setCallMethod(url.Call_DIRECT);
            url.setMode("hitsMonitor");
            out.println("<param name=\"cgi\" value=\"" + url + "\">");
            out.println("<param name=\"reload\" value=\"5\">");
            out.println("</APPLET>");

            out.println("</td></tr>");
            out.println("<tr><td>");

            out.println("<APPLET code=\"applets.graph.WBGraph.class\" archive=\""
                    + SWBPlatform.getContextPath()
                    + "/swbadmin/lib/WBGraph.jar\" width=\"100%\" height=\"200\">");
            url = paramsRequest.getRenderUrl();
            url.setCallMethod(url.Call_DIRECT);
            url.setMode("hitsTime");
            out.println("<param name=\"cgi\" value=\"" + url + "\">");
            out.println("<param name=\"reload\" value=\"5\">");
            out.println("</APPLET>");

            out.println("</td></tr>");
            out.println("<tr><td>");

            out.println("<APPLET code=\"applets.graph.WBGraph.class\" archive=\""
                    + SWBPlatform.getContextPath()
                    + "/swbadmin/lib/WBGraph.jar\" width=\"100%\" height=\"200\">");            
            url = paramsRequest.getRenderUrl();
            url.setCallMethod(url.Call_DIRECT);
            url.setMode("hitsXSec");
            out.println("<param name=\"cgi\" value=\"" + url + "\">");            
            out.println("<param name=\"reload\" value=\"5\">");
            out.println("</APPLET>");

            out.println("</td></tr>");            
            out.println("</table>");
        } else {
            out.println(paramsRequest.getLocaleString("msgIsNotActive"));
        }
        out.println("</fieldset>");
        out.println("</div>");
    }
    
    /* * Generate a graph of the hits
     * @param request input parameters
     * @param response an answer to the request
     * @param paramsRequest a list of objects (topic, user, action, ...)
     * @throws SWBResourceException an AF Exception
     * @throws IOException an IO Exception
     */
    /*
    public void doGraph(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramsRequest) 
            throws SWBResourceException, IOException {
        
        try {
            int maxx = SWBPortal.getMonitor().getMaxRecords();
            int maxy = 140;
            BufferedImage buffer = new BufferedImage(maxx, maxy,
                    BufferedImage.TYPE_INT_BGR);
            Graphics2D pad = buffer.createGraphics();
            pad.setColor(Color.BLACK);
            pad.fillRect(0, 0, maxx, maxy);

            pad.setColor(new Color(0, 150, 0));
            for (int x = 0; x < maxx; x += 20) {
                pad.drawLine(x, 0, x, maxy);
            }

            for (int y = 0; y < maxy; y += 20) {
                pad.drawLine(0, y, maxx, y);
            }

            //escala maxima en base a totalMemory
            Enumeration en = SWBPortal.getMonitor().getMonitorRecords().elements();
            long mx = 0;
            while (en.hasMoreElements()) {
                SWBMonitor.MonitorRecord mr = (SWBMonitor.MonitorRecord) en.nextElement();
                if (mr.getHits() > mx)
                    mx = mr.getHits();
            }

            if (mx < 100)
                mx = 100;

            int x = 0;
            int y = 0;
            long maxv = (long) (mx * 1.1);
            en = SWBPortal.getMonitor().getMonitorRecords().elements();
            while (en.hasMoreElements()) {
                x++;
                SWBMonitor.MonitorRecord mr = (SWBMonitor.MonitorRecord) en.nextElement();
                long usedm = mr.getHits();
                pad.setColor(Color.green);
                y = maxy - (int) ((usedm * maxy) / maxv);
                pad.drawLine(x, y, x, y);
            }

            try {
                GIFEncoder gifEncoder = new GIFEncoder(buffer);
                response.setContentType("image/gif");
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Cache-Control", "no-cache");
                ServletOutputStream out = response.getOutputStream();
                gifEncoder.Write(out);
            } catch (AWTException e) {
                log.error(e);
            }
        } catch (Throwable  e) {
            log.error(e);
        }
    }
    */
   /* * Generate a graph of the hits
     * @param request input parameters
     * @param response an answer to the request
     * @param paramsRequest a list of objects (topic, user, action, ...)
     * @throws SWBResourceException an AF Exception
     * @throws IOException an IO Exception
     */
    /*
    public void doGraph2(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramsRequest)
            throws SWBResourceException, IOException {
        
        String dif = request.getParameter("dif");
        try {
            SWBMonitor monitor = new SWBMonitor();
            int maxx = monitor.getMaxRecords();
            int maxy = 140;
            BufferedImage buffer = new BufferedImage(maxx, maxy,
                    BufferedImage.TYPE_INT_BGR);
            Graphics2D pad = buffer.createGraphics();
            pad.setColor(Color.BLACK);
            pad.fillRect(0, 0, maxx, maxy);

            pad.setColor(new Color(0, 150, 0));
            for (int x = 0; x < maxx; x += 20) {
                pad.drawLine(x, 0, x, maxy);
            }

            for (int y = 0; y < maxy; y += 20) {
                pad.drawLine(0, y, maxx, y);
            }

            //escala maxima en base a totalMemory
            Enumeration en = monitor.getMonitorRecords().elements();
            long mx = 140;

            int x = 0;
            int y = 0;
            long maxv = (long) (mx * 1);
            en = monitor.getMonitorRecords().elements();
            long old = 0;
            while (en.hasMoreElements()) {
                x++;
                SWBMonitor.MonitorRecord mr = (SWBMonitor.MonitorRecord) en.nextElement();
                long usedm = (mr.getHits() - old) / monitor.getDelay();
                old = mr.getHits();
                pad.setColor(Color.green);
                y = maxy - (int) ((usedm * maxy) / maxv);
                pad.drawLine(x, y, x, y);
            }

            try {
                GIFEncoder gifEncoder = new GIFEncoder(buffer);
                response.setContentType("image/gif");
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Cache-Control", "no-cache");
                ServletOutputStream out = response.getOutputStream();
                gifEncoder.Write(out);
            } catch (AWTException e) {
                log.error(e);
            }
        } catch (Throwable e) {
            log.error(e);
        }
    }        
    */
    /* * Process the requested action of the WBAHitsMonitor resource
     * @param request input parameters
     * @param response an answer to the request and a list of objects (topic, user, action, ...)
     * @throws SWBResourceException an AF Exception
     * @throws IOException an IO Exception
     */
    /*
    @Override
    public void processAction(HttpServletRequest request,
            SWBActionResponse response) throws SWBResourceException, IOException {
        String act = "";
        if (request.getParameter("act") != null)
            act = request.getParameter("act");
        if (act.equals("close")) {
            String rep = request.getParameter("rep");
            String sessionid = request.getParameter("sessionid");
            //TODO preguntar si es correcto instanciar la clase porque no existe .getInstance 
            WBUserMgr.getInstance().removeUser(sessionid, rep);
            response.setRenderParameter("act","");
        }
    }
    */
   
   
    /**  Process the user request
     * @param request input parameters
     * @param response an answer to the request
     * @param paramsRequest a list of objects (topic, user, action, ...)
     * @throws SWBResourceException an AF Exception
     * @throws IOException an IO Exception
     */    
    @Override
    public void processRequest(HttpServletRequest request,
            HttpServletResponse response, SWBParamRequest paramsRequest)
            throws SWBResourceException, IOException {
        /*
        if (paramsRequest.getMode().equals("graph")) {
            doGraph(request, response, paramsRequest);
        } else if (paramsRequest.getMode().equals("graph2")) {
            doGraph2(request, response, paramsRequest);
        } else */
        if (paramsRequest.getMode().equals("hitsMonitor")) {
            hitsMonitor(request, response, paramsRequest);
        } else if (paramsRequest.getMode().equals("hitsTime")) {
            hitsTime(request, response, paramsRequest);
        } else if (paramsRequest.getMode().equals("hitsXSec")) {
            hitsXSec(request, response, paramsRequest);
        } else {
            super.processRequest(request, response, paramsRequest);
        }
    }       
    
    public void hitsMonitor(HttpServletRequest request, 
            HttpServletResponse response, SWBParamRequest paramsRequest)
            throws SWBResourceException, IOException {
        
        PrintWriter out = response.getWriter();
        Vector data = SWBPortal.getMonitor().getMonitorRecords();
        out.println("GraphType=Lines");
        out.println("ncdata=1");
        out.println("percent=false");
        out.println("BrakeLabels=false");
        out.println("Title=" + paramsRequest.getLocaleString("msgHitsMonitor") + "");
        out.println("SubTitle=" + paramsRequest.getLocaleString("msgTotalHits")
                + ": TODO: Obtener Hits");
                // TODO: + ":" + WBAccessLog.getInstance().getInstanceHits() + "");
        Enumeration en = data.elements();
        int x = 0;
        int y = 0;
        int z = ((data.size() - 1) / 100) + 1;
        long prom = 0;
        while (en.hasMoreElements()) {
            y++;
            SWBMonitor.MonitorRecord mr = (SWBMonitor.MonitorRecord) en.nextElement();
            long val = mr.getHits();
            prom += val;
            if ((y % z) == 0) {
                prom = prom / z;
                String date = "" + mr.getDate().getHours() + ":" 
                        + mr.getDate().getMinutes() + ":"
                        + mr.getDate().getSeconds();
                out.println("label" + x + "=_" + date + "");
                out.println("data" + x + "=" + prom + "");
                x++;
                prom = 0;
            }
        }
        out.println("ndata=" + x + "");
        out.println("color0=66,138,212");
        out.println("color1=237,237,235");
        out.println("color2=229,243,253");
        out.println("barname0=" + paramsRequest.getLocaleString("msgHits") + "");
        out.println("zoom=true");
        out.println("vzoom=true");
    }
    
    public void hitsTime(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramsRequest)
            throws SWBResourceException, IOException {
        
        PrintWriter out = response.getWriter();
        Vector data = SWBPortal.getMonitor().getMonitorRecords();
        out.println("GraphType=Lines");
        out.println("ncdata=1");
        out.println("percent=false");
        out.println("BrakeLabels=false");
        Enumeration en = data.elements();
        int x = 0;
        int y = 0;
        int z = ((data.size() - 1) / 100) + 1;
        long prom = 0;
        while (en.hasMoreElements()) {
            y++;
            SWBMonitor.MonitorRecord mr = (SWBMonitor.MonitorRecord) en.nextElement();
            long val = mr.getHitsTime();
            prom += val;
            if ((y % z) == 0) {
                prom = prom / z;
                String date = "" + mr.getDate().getHours() + ":"
                        + mr.getDate().getMinutes() + ":" 
                        + mr.getDate().getSeconds();
                out.println("label" + x + "=_" + date + "");
                out.println("data" + x + "=" + prom + "");
                x++;
                prom = 0;
            }
        }
        out.println("ndata=" + x + "");
        out.println("color0=66,138,212");
        out.println("color1=237,237,235");
        out.println("color2=229,243,253");
        out.println("barname0=" + paramsRequest.getLocaleString("hitsTime") + "");
        out.println("zoom=true");
    }
    
    public void hitsXSec(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramsRequest) 
            throws SWBResourceException, IOException {
        
        PrintWriter out = response.getWriter();
        Vector data = SWBPortal.getMonitor().getMonitorRecords();
        out.println("GraphType=Lines");
        out.println("ncdata=1");
        out.println("percent=false");
        out.println("BrakeLabels=false");
        Enumeration en = data.elements();
        int x = 0;
        int y = 0;
        int z = ((data.size() - 1) / 100) + 1;
        long prom = 0;
        long old = 0;
        while (en.hasMoreElements()) {
            y++;
            SWBMonitor.MonitorRecord mr = (SWBMonitor.MonitorRecord) en.nextElement();
            long val = mr.getHits();
            if (y == 1) {
                old = val;
            }
            prom += val;
            if ((y % z) == 0) {
                prom = prom / z;
                String date = "" + mr.getDate().getHours() + ":"
                        + mr.getDate().getMinutes() + ":" + mr.getDate().getSeconds();
                out.println("label" + x + "=_" + date + "");
                out.println("data" + x + "="
                        + (prom - old) / (SWBPortal.getMonitor().getDelay() * z)
                        + "");
                x++;
                old = prom;
                prom = 0;
            }
        }
        out.println("ndata=" + x + "");
        out.println("color0=66,138,212");
        out.println("color1=237,237,235");
        out.println("color2=229,243,253");
        out.println("barname0=" + paramsRequest.getLocaleString("msgHitsXseg") + "");
        out.println("zoom=true");    
    }
    
}
