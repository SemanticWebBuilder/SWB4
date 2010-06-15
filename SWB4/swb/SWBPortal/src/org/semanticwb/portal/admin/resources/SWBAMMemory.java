/**  
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
**/ 
 


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
import org.semanticwb.portal.monitor.SWBSummary;
import org.semanticwb.servlet.internal.Monitor;


// TODO: Auto-generated Javadoc
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
    private SWBSummary swbSummary = null;
    
    /**
     * Creates a new instance of WBAMemory.
     */
    public SWBAMMemory() {
        swbSummary = new SWBSummary();
    }
    
    
    /**
     * Process action.
     * 
     * @param request the request
     * @param response the response
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
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
    * Do view.
    * 
    * @param request the request
    * @param response the response
    * @param paramsRequest the params request
    * @throws SWBResourceException the sWB resource exception
    * @throws IOException Signals that an I/O exception has occurred.
    */    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        String act = request.getParameter("act");
        //out.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"10; URL="+paramsRequest.getWebPage().getUrl()+"/\">");
        out.println("<div class=\"swbform\">");
        
        if (SWBPlatform.getEnv("wb/systemMonitor", "false").equals("true")) {
            out.println("<fieldset>");
            out.println("<table width=\"98%\" cellpadding=10 cellspacing=0 border=0>");
            out.println("<tr><td>");

            out.println("<div class=\"applet\">");
            out.println("<applet code=\"applets.graph.WBGraph.class\" archive=\""
                    + SWBPlatform.getContextPath()
                    + "/swbadmin/lib/SWBAplGraph.jar\" width=\"100%\" height=\"200\">");
            SWBResourceURL url = paramsRequest.getRenderUrl();
            url.setCallMethod(url.Call_DIRECT);
            url.setMode("getData");
            out.println("<param name=\"jsess\" value=\""+request.getSession().getId()+"\">");
            out.println("<param name=\"cgi\" value=\"" + url + "\">");
            out.println("<param name=\"reload\" value=\"5\">");
            out.println("</applet>");
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
            out.println("<div class=\"swbform\">");
            out.println(swbSummary.getSample().GetSumaryHTML());
            out.println("</div>");
        } else {
            out.println(paramsRequest.getLocaleString("msgIsNotActive"));
        }
        out.println("</div>");
    }
    
    /**
     * Gets the data.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @return the data
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
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
     * Process request.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
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
