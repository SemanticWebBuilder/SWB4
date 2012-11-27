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
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceTrace;
import org.semanticwb.portal.api.SWBResourceTraceMeter;
import org.semanticwb.portal.api.SWBResourceTraceMgr;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;
import org.semanticwb.model.User;

// TODO: Auto-generated Javadoc
/** Muestra el pool de conexiones a la base de datos seleccionada por el usuario
 * administrador.
 *
 * Shows pool of connections of the data base selected by the administrator
 * user.
 * @author Javier Solis Gonzalez
 */
public class SWBAResTrace extends GenericResource {
    
    /**
     * Creates a new instance of WBADBQuery.
     */
    public SWBAResTrace() {
    }
    
    /**
     * Inits the.
     * 
     * @throws SWBResourceException the sWB resource exception
     */    
    @Override
    public void init() throws SWBResourceException {
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
            SWBActionResponse response) throws SWBResourceException, IOException {
        
        String clear = request.getParameter("clear");
        String start = request.getParameter("start");
        String stop = request.getParameter("stop");
        String kill = request.getParameter("kill");
        String stype = request.getParameter("stype");
        
        if (clear != null) {
            SWBPortal.getResourceMgr().getResourceTraceMgr().clearResourceTrace();
        }
        if (start != null) {
            SWBPortal.getResourceMgr().getResourceTraceMgr().setResourceTrace(true);
        }
        if (stop != null) {
            SWBPortal.getResourceMgr().getResourceTraceMgr().setResourceTrace(false);
        }
        if (kill != null) {
            Hashtable types = (Hashtable) SWBPortal.getResourceMgr().getResourceTraceMgr().getTypes().get(stype);
            SWBResourceTrace trace = (SWBResourceTrace) types.get(Long.valueOf(kill));
            try {
                if (trace != null) {
                    //trace.getThread().dumpStack();
                    trace.getThread().stop();
                }
            } catch (Exception e) {}
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
            SWBParamRequest paramsRequest)
            throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();

        SWBResourceTraceMgr restracer = SWBPortal.getResourceMgr().getResourceTraceMgr();
        long actual = System.currentTimeMillis();
        User user = paramsRequest.getUser();
        out.println("<div class=\"swbform\">");
        out.println("<fieldset>");
        out.println("<TABLE width=\"98%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\">");
        out.println("<TR><TD width=\"30%\" align=\"right\" >");
        out.println(paramsRequest.getLocaleString("active")+":");
        out.println("</TD><TD align=\"left\">");
        if (restracer.isResourceTrace()) {
            out.println(paramsRequest.getLocaleString("yes"));
        } else {
            out.println(paramsRequest.getLocaleString("no"));
        }
        out.println("</TD></TR>");
        out.println("<TR><TD width=\"30%\" align=\"right\" >");
        out.println(paramsRequest.getLocaleString("restypes")+":");
        out.println("</TD><TD  align=\"left\">");
        out.println(restracer.getTypes().size());
        out.println("</TD></TR>");
        out.println("</TD></TR>");
        out.println("<TR><TD width=\"30%\" align=\"right\" >");
        out.println(paramsRequest.getLocaleString("totalres")+":");
        out.println("</TD><TD align=\"left\">");
        out.println(restracer.getResourceTracedSize());
        out.println("</TD></TR>");
        out.println("</TABLE>");
        out.println("</fieldset>");

        out.println("<fieldset>");
        SWBResourceURL urlA = paramsRequest.getActionUrl();
        urlA.setParameter("start", "start");
        out.println("        <button dojoType=\"dijit.form.Button\" name=\"start\" onclick=\"submitUrl('"+urlA+"',this.domNode); return false;\" >" + paramsRequest.getLocaleString("start") + "</button>");
        SWBResourceURL urlB = paramsRequest.getActionUrl();
        urlB.setParameter("stop", "stop");
        out.println("        <button dojoType=\"dijit.form.Button\" name=\"stop\" onclick=\"submitUrl('"+urlB+"',this.domNode); return false;\" >" + paramsRequest.getLocaleString("stop") +"</button>");
        SWBResourceURL urlC = paramsRequest.getActionUrl();
        urlC.setParameter("clear", "clear");
        out.println("        <button dojoType=\"dijit.form.Button\" name=\"clear\" onclick=\"submitUrl('"+urlC+"',this.domNode); return false;\" >" + paramsRequest.getLocaleString("clear") +"</button>");
        SWBResourceURL urlU = paramsRequest.getActionUrl();
        urlU.setParameter("update", "update");
        out.println("        <button dojoType=\"dijit.form.Button\" name=\"update\" onclick=\"submitUrl('"+urlU+"',this.domNode); return false;\" >" + paramsRequest.getLocaleString("update") +"</button>");
        out.println("</fieldset>");
        out.println("</div>");
        
        Hashtable types = restracer.getTypes();
        int s = types.size();
        
        if (s > 0) {
            out.println("<div class=\"swbform\">");
            out.println("<fieldset>");
        }
        
        Enumeration en = types.keys();
        while (en.hasMoreElements()) {
            String stype = (String) en.nextElement();
            Hashtable type = (Hashtable) types.get(stype);
            if (type.size() > 0) {
                out.println("  <table width=\"98%\" border=0>");
                out.println("    <tr><th colspan=4 >"+stype+" "+type.size()+"</th></tr>");
                out.println("    <tr><td colspan=4>");
                out.println("      <table width=\"100%\" border=0>");
                out.println("        <tr>");
                out.println("          <th>"+paramsRequest.getLocaleString("id")+"</th>");
                out.println("          <th>"+paramsRequest.getLocaleString("resid")+"</th>");
                out.println("          <th>"+paramsRequest.getLocaleString("description")+"</th>");
                out.println("          <th>"+paramsRequest.getLocaleString("site")+"</th>");
                out.println("          <th>"+paramsRequest.getLocaleString("topic")+"</th>");
                out.println("          <th>"+paramsRequest.getLocaleString("user")+"</th>");
                out.println("          <th>"+paramsRequest.getLocaleString("thread")+"</th>");
                out.println("          <th>"+paramsRequest.getLocaleString("time")+"</th>");
                out.println("          <th></th>");
                out.println("        </tr>");
                
                Enumeration en2 = type.elements();
                while (en2.hasMoreElements()) {
                    SWBResourceTrace t = (SWBResourceTrace) en2.nextElement();

                    long time = t.getTime();
                    if ((time + 10000L) < actual) {
                        out.println("        <tr bgcolor=\"#f05050\">");
                    } else {
                        out.println("        <tr>");
                    }
                    out.println("          <td >" + t.getId() + "</td>");
                    out.println("          <td >" + t.getResource().getId() + "</td>");
                    out.println("          <td >" + t.getDescription() + "</td>");
                    //out.println("          <td class=\"valores\">"+t.getWBParamRequest().getWebPage().getMap().getDbdata().getTitle()+"</td>");
                    out.println("          <td >"
                            + t.getWBParamRequest().getWebPage().getWebSite().getDisplayTitle(user.getLanguage()) + "</td>");
                    out.println("          <td >"
                            + t.getWBParamRequest().getWebPage().getDisplayName() + "</td>");
                    out.println("          <td >"
                            + t.getWBParamRequest().getUser().getId() + "</td>");
                    out.println("          <td >"
                            + t.getThread() + "</td>");
                    out.println("          <td >"
                            + (actual - t.getTime()) + "ms</td>");
                    SWBResourceURL url = paramsRequest.getActionUrl();
                    url.setParameter("kill", "" + t.getId());
                    url.setParameter("stype", "" + stype);
                    out.println("          <td ><a href=\""
                            + url + "\"><img src=\"" + SWBPlatform.getContextPath()
                            + "/swbadmin/images/delete.gif\" border=0></a></td>");
                    out.println("        </tr>");
                }
                out.println("      </table>");
                out.println("    </td></tr>");
                out.println("  </table>");
            }
            
        }        

        if (s > 0) {
            out.println("</fieldset>");
            out.println("</div>");
        }
        
        //Types Meter
        Set set=restracer.getSortTypesMeter();
        
        
//        Iterator it = set.iterator();
//        while (it.hasNext())
//        {
//            WBResourceTraceMeter t = (WBResourceTraceMeter)it.next();        
//            if("WBAdmin".equals(t.getTypeMap()) && AFUtils.getEnv("wb/adminTopicMap","false").equals("false"))
//                set.remove(t);
//        }
        s=set.size();
        
        if(s>0)
        {
            out.println("<div class=\"swbform\">");
            out.println("<fieldset>");
            out.println("  <table width=\"98%\" border=0>");
            out.println("    <tr><th colspan=4 >Resource Trace</th></tr>");
            out.println("    <tr><td colspan=4>");
            out.println("      <table width=\"100%\" border=0>");
            out.println("        <tr>");
            out.println("          <th>"+paramsRequest.getLocaleString("id")+"</th>");
            out.println("          <th>"+paramsRequest.getLocaleString("site")+"</th>");
            out.println("          <th>"+paramsRequest.getLocaleString("description")+"</th>");
            out.println("          <th>"+paramsRequest.getLocaleString("min")+"</th>");
            out.println("          <th>"+paramsRequest.getLocaleString("max")+"</th>");
            out.println("          <th>"+paramsRequest.getLocaleString("avg")+"</th>");
            out.println("          <th>"+paramsRequest.getLocaleString("hits")+"</th>");
            out.println("        </tr>");

            Iterator it = set.iterator();
            boolean colf=false;
            while (it.hasNext())
            {
                SWBResourceTraceMeter t = (SWBResourceTraceMeter)it.next();
                if (!"WBAdmin".equals(t.getTypeMap())
                        || SWBPlatform.getEnv("wb/adminTopicMap", "false").equals("true")) {                
                    if (t.getAvgTime() > 10000L) {
                        out.println("        <tr bgcolor=\"#f05050\">");
                    } else {
                        if (colf) {
                            out.println("        <tr>");
                            colf = false;
                        } else {
                            out.println("        <tr bgcolor=\"#e0e0e0\">");
                            colf = true;
                        }
                    }
                    out.println("          <td >"+t.getId()+"</td>");
                    out.println("          <td >"+t.getTypeMap()+"</td>");
                    out.println("          <td >"+t.getName()+"</td>");
                    out.println("          <td >"+t.getMinTime()+"</td>");
                    out.println("          <td >"+t.getMaxTime()+"</td>");
                    out.println("          <td >"+t.getAvgTime()+"</td>");
                    out.println("          <td >"+t.getCount()+"</td>");
                    out.println("        </tr>");
                }
            }        

            out.println("      </table>");
            out.println("    </td></tr>");
            out.println("  </table>");

            out.println("</fieldset>");
            out.println("</div>");
        }        
    }
}
