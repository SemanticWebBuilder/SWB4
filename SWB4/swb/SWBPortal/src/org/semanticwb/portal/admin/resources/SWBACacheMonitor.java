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
// TODO: Auto-generated Javadoc
//import org.semanticwb.Logger;


/** Muestra una gráfica de los recursosen memoria utilizados en la  
 * aplicación en un periodo de tiempo.
 *
 * Shows a chart of the resouces in memory into the application
 * in a time period.
 * @author Juan Antonio Fernández Arias
 */
public class SWBACacheMonitor extends GenericResource {
    
    /**
     * Creates a new instance of WBACacheMonitor.
     */
    public SWBACacheMonitor() {
    }
    
    /**
     * User view of the WBACacheMonitor resource.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException, a Resource Exception
     * @throws IOException, In Out Exception
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
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
                    + "/swbadmin/lib/SWBAplGraph.jar\" width=\"100%\" height=\"200\">");
            SWBResourceURL url = paramsRequest.getRenderUrl();
            url.setCallMethod(url.Call_DIRECT);
            url.setMode("getData");
            out.println("<param name=\"jsess\" value=\""+request.getSession().getId()+"\">");
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
    
    /**
     * Get Data used in chart.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @return the data
     * @throws SWBResourceException, a Resource Exception
     * @throws IOException, In Out Exception
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
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
    
    /**
     * Process the user request.
     * 
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
