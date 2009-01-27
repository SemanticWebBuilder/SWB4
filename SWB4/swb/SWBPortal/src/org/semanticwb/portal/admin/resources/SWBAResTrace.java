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
 * SWBAResTrace.java
 *
 * Created on 27 de enero de 2005, 01:00 PM
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
//import com.infotec.wb.core.db.RecResourceType;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;
import org.semanticwb.model.PortletType;



/** Muestra el pool de conexiones a la base de datos seleccionada por el usuario
 * administrador.
 *
 * Shows pool of connections of the data base selected by the administrator
 * user.
 * @author Javier Solis Gonzalez
 */
public class SWBAResTrace extends GenericResource {
    
    /** Creates a new instance of WBADBQuery */
    public SWBAResTrace() {
    }
    
    /**
     * @throws SWBResourceException
     */    
    @Override
    public void init() throws SWBResourceException {
    }
    
    /**
     * @param request
     * @param response
     * @throws SWBResourceException
     * @throws IOException
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

        SWBResourceTraceMgr restracer = SWBPortal.getResourceMgr().getResourceTraceMgr();
        long actual = System.currentTimeMillis();
        
        out.println("<p class=\"box\">");
        out.println("<TABLE width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
        out.println("<TR><TD width=\"30%\" align=\"right\" class=\"tabla\">");
        out.println(paramsRequest.getLocaleString("active")+":");
        out.println("</TD><TD class=\"valores\" align=\"left\">");
        if (restracer.isResourceTrace()) {
            out.println(paramsRequest.getLocaleString("yes"));
        } else {
            out.println(paramsRequest.getLocaleString("no"));
        }
        out.println("</TD></TR>");
        out.println("<TR><TD width=\"30%\" align=\"right\" class=\"tabla\">");
        out.println(paramsRequest.getLocaleString("restypes")+":");
        out.println("</TD><TD class=\"valores\" align=\"left\">");
        out.println(restracer.getTypes().size());
        out.println("</TD></TR>");
        out.println("</TD></TR>");
        out.println("<TR><TD width=\"30%\" align=\"right\" class=\"tabla\">");
        out.println(paramsRequest.getLocaleString("totalres")+":");
        out.println("</TD><TD class=\"valores\" align=\"left\">");
        out.println(restracer.getResourceTracedSize());
        out.println("</TD></TR>");
        out.println("<TR><TD colspan=2 align=\"right\">");
        out.println("<HR size=1 noshade>");
        out.println("    <form action=\""+paramsRequest.getActionUrl()+"\" method=\"post\">");
        out.println("        <input type=submit class=\"boton\" name=\"start\" value=\""+paramsRequest.getLocaleString("start")+"\">");
        out.println("        <input type=submit class=\"boton\" name=\"stop\" value=\""+paramsRequest.getLocaleString("stop")+"\">");
        out.println("        <input type=submit class=\"boton\" name=\"clear\" value=\""+paramsRequest.getLocaleString("clear")+"\">");
        out.println("        <input type=submit class=\"boton\" name=\"update\" value=\""+paramsRequest.getLocaleString("update")+"\">");
        out.println("  </form>");
        out.println("</TD></TR>");
        out.println("</TABLE>");
        out.println("</p>");
        
        Hashtable types = restracer.getTypes();
        int s = types.size();
        
        if (s > 0) {
            out.println("<p class=\"box\">");
        }
        
        Enumeration en = types.keys();
        while (en.hasMoreElements()) {
            String stype = (String) en.nextElement();
            Hashtable type = (Hashtable) types.get(stype);
            if (type.size() > 0) {
                out.println("  <table width=\"100%\" border=0>");
                out.println("    <tr><td colspan=4 class=\"tabla\"><B>"+stype+" "+type.size()+"</B></td></tr>");
                out.println("    <tr><td colspan=4>");
                out.println("      <table width=\"100%\" border=0>");
                out.println("        <tr>");
                out.println("          <td class=\"tabla\">"+paramsRequest.getLocaleString("id")+"</td>");
                out.println("          <td class=\"tabla\">"+paramsRequest.getLocaleString("resid")+"</td>");
                out.println("          <td class=\"tabla\">"+paramsRequest.getLocaleString("description")+"</td>");
                out.println("          <td class=\"tabla\">"+paramsRequest.getLocaleString("site")+"</td>");
                out.println("          <td class=\"tabla\">"+paramsRequest.getLocaleString("topic")+"</td>");
                out.println("          <td class=\"tabla\">"+paramsRequest.getLocaleString("user")+"</td>");
                out.println("          <td class=\"tabla\">"+paramsRequest.getLocaleString("thread")+"</td>");
                out.println("          <td class=\"tabla\">"+paramsRequest.getLocaleString("time")+"</td>");
                out.println("          <td class=\"tabla\"></td>");
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
                    out.println("          <td class=\"valores\">" + t.getId() + "</td>");
                    out.println("          <td class=\"valores\">" + t.getResource().getId() + "</td>");
                    out.println("          <td class=\"valores\">" + t.getDescription() + "</td>");
                    //out.println("          <td class=\"valores\">"+t.getWBParamRequest().getTopic().getMap().getDbdata().getTitle()+"</td>");
                    out.println("          <td class=\"valores\">"
                            + t.getWBParamRequest().getTopic().getWebSite().getTitle() + "</td>");
                    out.println("          <td class=\"valores\">"
                            + t.getWBParamRequest().getTopic().getDisplayName() + "</td>");
                    out.println("          <td class=\"valores\">"
                            + t.getWBParamRequest().getUser().getId() + "</td>");
                    out.println("          <td class=\"valores\">"
                            + t.getThread() + "</td>");
                    out.println("          <td class=\"valores\">"
                            + (actual - t.getTime()) + "ms</td>");
                    SWBResourceURL url = paramsRequest.getActionUrl();
                    url.setParameter("kill", "" + t.getId());
                    url.setParameter("stype", "" + stype);
                    out.println("          <td class=\"valores\"><a href=\""
                            + url + "\"><img src=\"" + SWBPlatform.getContextPath()
                            + "/swbadmin/images/eliminar.gif\" border=0></a></td>");
                    out.println("        </tr>");
                }
                out.println("      </table>");
                out.println("    </td></tr>");
                out.println("  </table>");
            }
            
        }        

        if (s > 0) {
            out.println("</p>");
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
            out.println("<p class=\"box\">");
        
            out.println("  <table width=\"100%\" border=0>");
            out.println("    <tr><td colspan=4 class=\"tabla\"><B>Resource Trace</B></td></tr>");
            out.println("    <tr><td colspan=4>");
            out.println("      <table width=\"100%\" border=0>");
            out.println("        <tr>");
            out.println("          <td class=\"tabla\">"+paramsRequest.getLocaleString("id")+"</td>");
            out.println("          <td class=\"tabla\">"+paramsRequest.getLocaleString("site")+"</td>");
            out.println("          <td class=\"tabla\">"+paramsRequest.getLocaleString("description")+"</td>");
            out.println("          <td class=\"tabla\">"+paramsRequest.getLocaleString("min")+"</td>");
            out.println("          <td class=\"tabla\">"+paramsRequest.getLocaleString("max")+"</td>");
            out.println("          <td class=\"tabla\">"+paramsRequest.getLocaleString("avg")+"</td>");
            out.println("          <td class=\"tabla\">"+paramsRequest.getLocaleString("hits")+"</td>");
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
                    out.println("          <td class=\"valores\">"+t.getId()+"</td>");
                    out.println("          <td class=\"valores\">"+t.getTypeMap()+"</td>");
                    out.println("          <td class=\"valores\">"+t.getName()+"</td>");
                    out.println("          <td class=\"valores\">"+t.getMinTime()+"</td>");
                    out.println("          <td class=\"valores\">"+t.getMaxTime()+"</td>");
                    out.println("          <td class=\"valores\">"+t.getAvgTime()+"</td>");
                    out.println("          <td class=\"valores\">"+t.getCount()+"</td>");
                    out.println("        </tr>");
                }
            }        

            out.println("      </table>");
            out.println("    </td></tr>");
            out.println("  </table>");

            out.println("</p>");
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
    public void render(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramsRequest)
            throws SWBResourceException, IOException {
        
        super.render(request, response, paramsRequest);
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
        
        super.processRequest(request, response, paramsRequest);
    }
    
    /**
     * @param recobj
     * @throws SWBResourceException
     */    
    @Override
    public void install(PortletType recobj) throws SWBResourceException {
    }
    
    /**
     * @param recobj
     * @throws SWBResourceException
     */    
    @Override
    public void uninstall(PortletType recobj) throws SWBResourceException {
    }
    
}
