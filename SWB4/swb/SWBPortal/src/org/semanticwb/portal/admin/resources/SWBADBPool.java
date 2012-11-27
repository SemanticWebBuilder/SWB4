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


import org.semanticwb.base.db.DBConnectionPool;
import org.semanticwb.base.db.PoolConnectionTimeLock;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.SWBMonitor;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;
import java.sql.*;
import java.util.concurrent.ConcurrentHashMap;



// TODO: Auto-generated Javadoc
/** Muestra el pool de conexiones a la base de datos seleccionada por el usuario
 * administrador.
 *
 * Shows the conection pool of the data base selected by the administrator
 * user.
 * @author Javier Solis Gonzalez
 */
public class SWBADBPool extends GenericResource {
    
    
    /**
     * Creates a new instance of SWBADBPool.
     */
    public SWBADBPool() {
    }
        
    /**
     * Process the user request action.
     * 
     * @param request the request
     * @param response the response
     * @throws SWBResourceException, a Resource Exception
     * @throws IOException, an In Out Exception
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */    
    @Override
    public void processAction(HttpServletRequest request,
            SWBActionResponse response) throws SWBResourceException, IOException {
        
        String clear = request.getParameter("clear");
        String dbcon = request.getParameter("dbcon");
        
        if (clear != null) {
            //TODO: Revisar que esta conversion sea correcta
//            DBConnectionPool pool = (DBConnectionPool) DBConnectionManager.getInstance().getPools().get(dbcon);
//            pool.release();
            for (Enumeration<DBConnectionPool> en = SWBUtils.DB.getPools(); en.hasMoreElements();) {
                DBConnectionPool dbPool = en.nextElement();
                if (dbPool.getName().equals(dbcon)) {
                    dbPool.release();
                }
            }
        }
        response.setRenderParameter("dbcon", dbcon);
    }
    
    /**
     * Show the user view of the connection pool.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException, a Resource Exception
     * @throws IOException, an In Out Exception
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramsRequest)
            throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        String dbcon = request.getParameter("dbcon");
        DBConnectionPool selectedPool = null;
        
        if (dbcon == null) {
            dbcon = SWBPlatform.getEnv("wb/db/nameconn", "swb");
        }
        
        out.println("<div class=\"swbform\">");
        out.println("<fieldset>");
        out.println("<table border=\"0\" cellspacing=\"2\" cellpadding=\"0\" width=\"98%\">");
        out.println("<tr><td width=\"200\">");
        out.println(paramsRequest.getLocaleString("connPool"));
        out.println("</td><td>");
        SWBResourceURL urlOC = paramsRequest.getRenderUrl();
        out.println("<form id=\""+getResourceBase().getId()+"/ocDB\" action=\"" + urlOC + "\" method=\"post\">");
        out.println("<select name=\"dbcon\" onChange=\"submitForm('"+getResourceBase().getId()+"/ocDB');return false;\">");
        //Enumeration en = DBConnectionManager.getInstance().getPools().elements();
        Enumeration<DBConnectionPool> en = SWBUtils.DB.getPools();
        while (en.hasMoreElements()) {
            DBConnectionPool pool = (DBConnectionPool) en.nextElement();
            String name = pool.getName();
            out.print("<option value=\""+name+"\"");
            if (name.equals(dbcon)) {
                out.print(" selected");
                selectedPool = pool;
            }
            out.println(">" + name + "</option>");
        }
        out.println("</select>");
        out.println("</form>");
        out.println("</td></tr>");
        out.println("</table>");
        out.println("</fieldset>");
        out.println("</div>");
        
        PoolConnectionTimeLock timelock = SWBUtils.DB.getTimeLock();
        
        if (selectedPool != null) {
            String dbname = "-";
            String dbversion = "-";
            String drivername = "-";
            String driverversion = "-";
            String encpasswd = selectedPool.getPassword();
            try {
                Connection con = SWBUtils.DB.getConnection(selectedPool.getName(), "WBADBPool");
                java.sql.DatabaseMetaData md = con.getMetaData();
                dbname = md.getDatabaseProductName();
                dbversion = md.getDatabaseProductVersion();
                drivername = md.getDriverName();
                driverversion = md.getDriverVersion();
                con.close();
            } catch (Exception e) {}
            
            int tot = SWBUtils.DB.getConnections(selectedPool.getName());
            int free=+SWBUtils.DB.getFreeConnections(selectedPool.getName());
            out.println("<div class=\"swbform\">");
            out.println("<fieldset>");
            out.println("  <table width=\"98%\" border=0>");
            out.println("    <tr><td>DBName:</td><td>" + dbname + " (" + dbversion + ")</td>");
            out.println("        <td>" + paramsRequest.getLocaleString("total") + ":</td><td>" + tot + "</td></tr>");
            out.println("    <tr><td>Driver:</td><td>" + drivername + " (" + driverversion + ")</td>");
            out.println("       <td>" + paramsRequest.getLocaleString("used") + ":</td><td>" + (tot - free) + "</td></tr>");
            out.println("    <tr><td>URL:</td><td>" + selectedPool.getURL() + "</td>");
            out.println("        <td>" + paramsRequest.getLocaleString("free") + ":</td><td>" + free + "</td></tr>");
            out.println("    <tr><td>" + paramsRequest.getLocaleString("user") + ":</td><td>" + selectedPool.getUser() + "</td>");
            out.println("        <td>" + paramsRequest.getLocaleString("max") + ":</td><td>" + selectedPool.getMaxConn() + "</td></tr>");
            out.println("    <tr><td>" + paramsRequest.getLocaleString("password") + ":</td><td>" + selectedPool.getPassword() + "</td>");
            out.println("        <td>Hits:</td><td>" + selectedPool.getHits() + "</td></tr>");
            out.println("  </table>");
            out.println("</fieldset>");
            out.println("<fieldset>");

            SWBResourceURL url1 =  paramsRequest.getActionUrl();
            url1.setParameter("dbcon", dbcon);
            url1.setParameter("clear", "clear");

            out.println("        <button dojoType=\"dijit.form.Button\" name=\"clear\" onclick=\"submitUrl('"+url1+"', this.domNode); return false;\" >" + paramsRequest.getLocaleString("clear") +" " + selectedPool.getName() + "</button>");
            //out.println("        <input type=submit name=\"clear\" value=\"" + paramsRequest.getLocaleString("clear") + " " + selectedPool.getName() + "\">");

            SWBResourceURL url2 =  paramsRequest.getRenderUrl();
            url2.setParameter("dbcon", dbcon);
            url2.setParameter("reload", "reload");

            out.println("  <button dojoType=\"dijit.form.Button\" name=\"reload\" onclick=\"submitUrl('"+url2+"',this.domNode); return false;\" name=\"gc\">" + paramsRequest.getLocaleString("update") + "</button>");
            //out.println("        <input type=submit name=\"reload\" value=\"" + paramsRequest.getLocaleString("update") + "\">");
            
            out.println("</fieldset>");
            out.println("</div>");
            
            if (SWBPlatform.getEnv("wb/systemMonitor", "false").equals("true")) {
                out.println("<div class=\"swbform\">");
                out.println("<fieldset>");
                out.println("<table width=98% cellpadding=10 cellspacing=0 border=0>");
                out.println("<tr><td>");
                out.println("<div class=\"applet\">");
                out.println("<APPLET code=\"applets.graph.WBGraph.class\" archive=\""
                        + SWBPlatform.getContextPath()
                        + "/swbadmin/lib/SWBAplGraph.jar\" width=\"100%\" height=\"200\">");
                SWBResourceURL url = paramsRequest.getRenderUrl();
                url.setCallMethod(url.Call_DIRECT);
                url.setParameter("dbcon", dbcon);
                url.setMode("getData");
                out.println("<param name=\"jsess\" value=\""+request.getSession().getId()+"\">");
                out.println("<param name=\"cgi\" value=\"" + url + "\">");
                out.println("<param name=\"reload\" value=\"5\">");
                out.println("</APPLET>");
                out.println("</div>");
                out.println("</td></tr>");

                out.println("<tr><td>");
                //out.println("<img src=\""+paramsRequest.getRenderUrl().setMode("graph").setCallMethod(WBResourceURL.Call_DIRECT)+"\">");
                out.println("<div class=\"applet\">");
                out.println("<APPLET code=\"applets.graph.WBGraph.class\" archive=\""
                        + SWBPlatform.getContextPath()
                        + "/swbadmin/lib/SWBAplGraph.jar\" width=\"100%\" height=\"200\">");
                url = paramsRequest.getRenderUrl();
                url.setCallMethod(url.Call_DIRECT);
                url.setParameter("dbcon", dbcon);
                url.setMode("hitsTime");
                out.println("<param name=\"jsess\" value=\""+request.getSession().getId()+"\">");
                out.println("<param name=\"cgi\" value=\"" + url + "\">");
                out.println("<param name=\"reload\" value=\"5\">");
                out.println("</APPLET>");
                out.println("</div>");
                out.println("</td></tr>");

                out.println("<tr><td>");
                out.println("<div class=\"applet\">");
                out.println("<APPLET code=\"applets.graph.WBGraph.class\" archive=\""
                        + SWBPlatform.getContextPath()
                        + "/swbadmin/lib/SWBAplGraph.jar\" width=\"100%\" height=\"200\">");
                url = paramsRequest.getRenderUrl();
                url.setCallMethod(url.Call_DIRECT);
                url.setParameter("dbcon", dbcon);
                url.setMode("getData2");
                out.println("<param name=\"jsess\" value=\""+request.getSession().getId()+"\">");
                out.println("<param name=\"cgi\" value=\"" + url + "\">");
                out.println("<param name=\"reload\" value=\"5\">");
                out.println("</APPLET>");
                out.println("</div>");
                out.println("</td></tr>");
                
                out.println("</table>");
                out.println("</fieldset>");
                out.println("</div>");
            }

            ConcurrentHashMap timepool = (ConcurrentHashMap) timelock.getPools().get(selectedPool.getName());
            if (timepool != null) {
                HashMap pool2 = new HashMap(timepool);
                int ps = pool2.size();
                if (ps > 0) {
                    out.println("<div class=\"swbform\">");
                    out.println("<fieldset>");
                    out.println("  <table width=\"100%\" border=0>");
                    out.println("    <tr><td colspan=4><B>"
                            + paramsRequest.getLocaleString("connDetails")
                            + "</B></td></tr>");
                    out.println("    <tr><td colspan=4>");
                    out.println("      <table width=\"100%\" border=0>");
                    out.println("        <tr><td>ID</td><td>"
                            + paramsRequest.getLocaleString("description")
                            + "</td><td>"
                            + paramsRequest.getLocaleString("time")
                            + "</td></tr>");

                    long actual = System.currentTimeMillis();
                    Iterator it2 = pool2.keySet().iterator();
                    while (it2.hasNext()) {
                        Long time = (Long) it2.next();
                        String desc = (String) pool2.get(time);
                        long seg = (actual - time.longValue()) / 1000;
                        out.println("        <tr ");
                        if ((time.longValue() + 300000L) < actual) {
                            out.print("bgcolor=\"#FFA0A0\"");
                        }
                        out.println("><td>" + time
                                + "</td><td>" + desc
                                + "</td><td>" + seg
                                + "s</td></tr>");
                    }
                    out.println("      </table>");
                    out.println("    </td></tr>");
                    out.println("  </table>");
                    out.println("</fieldset>");
                    out.println("</div>");
                }        
            }
            //out.println("</form>");
        } else {
            //TODO:internacionalizar texto
            out.println(paramsRequest.getLocaleString("nopool"));
        }
    }
    

    /**
     * Gets the data used into the chart.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @return the data
     * @throws SWBResourceException, a Resource Exception
     * @throws IOException, an In Out Exception
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void getData(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramsRequest)
            throws SWBResourceException, IOException {
        
        PrintWriter out = response.getWriter();
        String dbcon = request.getParameter("dbcon");
        if (dbcon == null) {
            dbcon = SWBPlatform.getEnv("wb/db/nameconn", "swb");
        }

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
        out.println("Title=" + paramsRequest.getLocaleString("connPool") + "");
        out.println("SubTitle=" + dbcon);
        Enumeration en = data.elements();
        int x = -1;

        while (en.hasMoreElements()) {
            SWBMonitor.MonitorRecord mr = (SWBMonitor.MonitorRecord) en.nextElement();
            SWBMonitor.PoolRecord pr = (SWBMonitor.PoolRecord) mr.getPools().get(dbcon);
            long total = pr.getTotalConnections();
            long free = pr.getFreeConnections();
            long used = pr.getUsedConnections();
            if (x >= 0) {
                java.util.Date dt = mr.getDate();
                String date = "" + dt.getHours() + ":" + dt.getMinutes() + ":" + dt.getSeconds();
                out.println("label" + x + "=_" + date + "");
                out.println("data" + x + "=" + total + "|" + free + "|" + used + "");
                //System.out.println("data"+x+"="+total+"|"+free+"|"+used+"");
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
     * Gets the data used into the chart.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @return the data2
     * @throws SWBResourceException, a Resource Exception
     * @throws IOException, an In Out Exception
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void getData2(HttpServletRequest request,
            HttpServletResponse response, SWBParamRequest paramsRequest)
            throws SWBResourceException, IOException {
        
        PrintWriter out = response.getWriter();
        String dbcon = request.getParameter("dbcon");
        if (dbcon == null) {
            dbcon = SWBPlatform.getEnv("wb/db/nameconn", "swb");
        }

        int max = 100;
        int ratio = ((SWBPortal.getMonitor().getMonitorRecords().size() - 1) / max) + 1;
        Vector data = SWBPortal.getMonitor().getAverageMonitorRecords(ratio);
        int labc = data.size() / 10;
        if (labc == 0) {
            labc = 1;
        }
        
        out.println("GraphType=Lines");
        out.println("ncdata=1");
        out.println("percent=false");
        out.println("BrakeLabels=false");
        //out.println("Title="+paramsRequest.getLocaleString("connPool")+"");
        //out.println("SubTitle="+dbcon);
        Enumeration en = data.elements();
        int x = -1;

        while (en.hasMoreElements()) {
            SWBMonitor.MonitorRecord mr = (SWBMonitor.MonitorRecord) en.nextElement();
            SWBMonitor.PoolRecord pr = (SWBMonitor.PoolRecord) mr.getPools().get(dbcon);
            long hits = pr.getHits();
            if (x >= 0) {
                java.util.Date dt = mr.getDate();
                String date = "" + dt.getHours() + ":" + dt.getMinutes() + ":" + dt.getSeconds();
                out.println("label" + x + "=_" + date + "");
                out.println("data" + x + "=" + hits);
            }
            x++;
        }
        out.println("ndata=" + x + "");
        out.println("color0=237,100,100");
        out.println("color1=229,243,50");
        out.println("color2=150,150,150");
        out.println("barname0=" + paramsRequest.getLocaleString("hits") + "");
        out.println("zoom=true");
    }    
    
    /**
     * Gets the data used into the chart.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException, a Resource Exception
     * @throws IOException, an In Out Exception
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void hitsTime(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramsRequest)
            throws SWBResourceException, IOException {
        
        PrintWriter out = response.getWriter();
        String dbcon = request.getParameter("dbcon");
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
            SWBMonitor.PoolRecord pr = (SWBMonitor.PoolRecord) mr.getPools().get(dbcon);
            long val = pr.getHitsTime();
            prom += val;
            if ((y % z) == 0) {
                prom = prom / z;
                String date = "" + mr.getDate().getHours() + ":"
                        + mr.getDate().getMinutes() + ":" + mr.getDate().getSeconds();
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
    
    
    /**
     * Process the user request mode to show.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException, a Resource Exception
     * @throws IOException, an In Out Exception
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */    
    @Override
    public void processRequest(HttpServletRequest request,
            HttpServletResponse response, SWBParamRequest paramsRequest)
            throws SWBResourceException, IOException {
        
        if (paramsRequest.getMode().equals("getData")) {
            getData(request, response, paramsRequest);
        } else if (paramsRequest.getMode().equals("getData2")) {
            getData2(request, response, paramsRequest);
        } else if (paramsRequest.getMode().equals("hitsTime")) {
            hitsTime(request, response, paramsRequest);
        } else {
            super.processRequest(request, response, paramsRequest);
        }
    }    
    
}
