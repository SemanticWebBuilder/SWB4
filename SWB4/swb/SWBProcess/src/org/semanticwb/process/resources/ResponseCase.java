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
package org.semanticwb.process.resources;

import java.io.PrintWriter;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.process.model.Process;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBException;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.GenericResource;

//import org.semanticwb.process.utils.Ajax;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.process.model.ProcessInstance;

/**
 *
 * @author Sergio Téllez
 */
public class ResponseCase extends GenericResource {
    private static Logger log = SWBUtils.getLogger(ResponseCase.class);
    public static final String TIMEUNIT_SECONDS="1";
    public static final String TIMEUNIT_MINUTES="2";
    public static final String TIMEUNIT_HOURS="3";
    
    String theme = "dojox.charting.themes.PlotKit.blue";

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (SWBParamRequest.Action_EDIT.equals(paramRequest.getAction())) {
//            System.out.println("action: " + paramRequest.getAction() + "LLendose a admin");
            doAdminCase(request, response, paramRequest);
        } else {
//          System.out.println("action: " + paramRequest.getAction() + "LLendose a adminResume");
            doAdminResume(request, response, paramRequest);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        SWBResourceURL edit = paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_EDIT);
        edit.setParameter("suri",suri );
//        Enumeration enuparam = request.getParameterNames();
//        while (enuparam.hasMoreElements()) {
//            String sparam = (String)enuparam.nextElement();
//            System.out.println("param: "+sparam+" ("+request.getParameter(sparam)+")");
//        }
        out.println("<div id=\"properties\" class=\"swbform\">");
        out.println("  <fieldset>");
        out.println("    <legend>" + paramRequest.getLocaleString("title") + "</legend>");
        if (null != suri)
            doGraph(request, response, paramRequest, suri);
        out.println("  </fieldset>");
        out.println("  <fieldset>\n");
        out.println("    <button  dojoType=\"dijit.form.Button\" onClick=\"submitUrl('"+edit.toString()+"',this.domNode);return false;\">" + paramRequest.getLocaleString("config") + "</button>");
        out.println("  </fieldset>");
        out.println("</div>");
    }

    private void doAdminCase(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        Process process = getProcess(suri);
        String pid = process.getId();
        SWBResourceURL url = paramRequest.getRenderUrl();
        url.setMode(SWBResourceURL.Mode_EDIT);
        url.setAction("properties");
        url.setParameter("suri", suri);
        out.print("<div class=\"swbform\">\n");
        out.print("  <fieldset>\n");
        out.print(paramRequest.getLocaleString("title"));
        out.print("  </fieldset>\n");
        long fid = System.currentTimeMillis();
        out.print("  <form id=\""+fid+"/Rescase\" name=\"Rescase\" action=" + url.toString() + " method=\"post\" onSubmit=\"submitForm('"+fid+"/Rescase');return false;\">\n");
        /*out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("process") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td>\n");
        out.print("                      <select id=\"process\" name=\"process\">\n");
        //out.print("                          <option value=\"\">&nbsp;</option>\n");
        Iterator isites = ProcessSite.ClassMgr.listProcessSites();
        while (isites.hasNext()) {
            ProcessSite site = (ProcessSite)isites.next();
            Iterator<Process> itprocess = site.listProcesses();
            while (itprocess.hasNext()) {
                Process process = itprocess.next();
                out.print("                  <option value=\"" +  process.getId() + "\"" + (getResourceBase().getAttribute("process","").equalsIgnoreCase(process.getId()) ? " selected" : "") + " >" + process.getTitle() +  "</option>\n");
            }
        }
        out.print("                  </td>");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");*/
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("PLOT_TYPE") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot\" type=\"radio\" name=\"plot\" value=\"1\"" + ("1".equalsIgnoreCase(getAttribute(pid, "plot")) ? " checked" : "") + "> " + paramRequest.getLocaleString("bars") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot\" type=\"radio\" name=\"plot\" value=\"2\"" + ("2".equalsIgnoreCase(getAttribute(pid, "plot")) ? " checked" : "") + "> " + paramRequest.getLocaleString("pie") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot\" type=\"radio\" name=\"plot\" value=\"3\"" + ("3".equalsIgnoreCase(getAttribute(pid, "plot")) ? " checked" : "") + "> " + paramRequest.getLocaleString("area") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("PLOT_THEME") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot_theme\" type=\"radio\" name=\"plot_theme\" value=\"1\"" + ("1".equalsIgnoreCase(getAttribute(pid,"plot_theme")) ? " checked" : "") + "> " + paramRequest.getLocaleString("blue") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot_theme\" type=\"radio\" name=\"plot_theme\" value=\"2\"" + ("2".equalsIgnoreCase(getAttribute(pid,"plot_theme")) ? " checked" : "") + "> " + paramRequest.getLocaleString("green") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot_theme\" type=\"radio\" name=\"plot_theme\" value=\"3\"" + ("3".equalsIgnoreCase(getAttribute(pid,"plot_theme")) ? " checked" : "") + "> " + paramRequest.getLocaleString("red") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("TIME_UNIT") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"time_unit\" type=\"radio\" name=\"time_unit\" value=\"1\"" + ("1".equalsIgnoreCase(getAttribute(pid,"time_unit")) ? " checked" : "") + "> " + paramRequest.getLocaleString("seconds") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"time_unit\" type=\"radio\" name=\"time_unit\" value=\"2\"" + ("2".equalsIgnoreCase(getAttribute(pid,"time_unit")) ? " checked" : "") + "> " + paramRequest.getLocaleString("minutes") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"time_unit\" type=\"radio\" name=\"time_unit\" value=\"3\"" + ("3".equalsIgnoreCase(getAttribute(pid,"time_unit")) ? " checked" : "") + "> " + paramRequest.getLocaleString("hours") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"time_unit\" type=\"radio\" name=\"time_unit\" value=\"4\"" + ("4".equalsIgnoreCase(getAttribute(pid,"time_unit")) ? " checked" : "") + "> " + paramRequest.getLocaleString("days") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("      </fieldset>\n");
//        out.print("      <fieldset>\n");
//        out.print("          <legend>" + paramRequest.getLocaleString("labels") + "</legend>\n");
//        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
//        out.print("              <tr>\n");
//        out.print("                  <td><input id=\"display_totals\" type=\"checkbox\" name=\"display_totals\" value=\"1\"" + ("1".equalsIgnoreCase(getAttribute(pid,"display_totals")) ? " checked" : "") + "> " + paramRequest.getLocaleString("DISPLAY_TOTALS") + "</td>\n");
//        out.print("              </tr>\n");
//        out.print("         </table>\n");
//        out.print("     </fieldset>\n");
        
//        out.print("      <fieldset>\n");
//        out.print("          <legend>" + paramRequest.getLocaleString("LOG") + "</legend>\n");
//        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
//        out.print("              <tr>\n");
//        out.print("                  <td><input id=\"show_log\" type=\"checkbox\" name=\"show_log\" value=\"1\"" + ("1".equalsIgnoreCase(getAttribute(pid,"show_log")) ? " checked" : "") + "> " + paramRequest.getLocaleString("showLog") + "</td>\n");
//        out.print("              </tr>\n");
//        out.print("              <tr>\n");
//        out.print("                  <td><input id=\"last_date\" type=\"radio\" name=\"last_date\" value=\"1\"" + ("1".equalsIgnoreCase(getAttribute(pid,"last_date")) ? " checked" : "") + "> " + paramRequest.getLocaleString("lastWeek") + "</td>\n");
//        out.print("              </tr>\n");
//        out.print("              <tr>\n");
//        out.print("                  <td><input id=\"last_date\" type=\"radio\" name=\"last_date\" value=\"2\"" + ("2".equalsIgnoreCase(getAttribute(pid,"last_date")) ? " checked" : "") + "> " + paramRequest.getLocaleString("lastMonth") + "</td>\n");
//        out.print("              </tr>\n");
//        out.print("              <tr>\n");
//        out.print("                  <td><input id=\"last_date\" type=\"radio\" name=\"last_date\" value=\"3\"" + ("3".equalsIgnoreCase(getAttribute(pid,"last_date")) ? " checked" : "") + "> " + paramRequest.getLocaleString("lastYear") + "</td>\n");
//        out.print("              </tr>\n");
//        out.print("         </table>\n");
//        out.print("     </fieldset>\n");
        
        out.print("     <fieldset>\n");
        out.print("         <button  dojoType=\"dijit.form.Button\" type=\"submit\" >"+paramRequest.getLocaleString("apply")+"</button>");
        url = paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW);
        url.setParameter("suri", suri);
        out.print("         <button dojoType=\"dijit.form.Button\" onClick=\"submitUrl('" + url + "',this.domNode); return false;\">"+paramRequest.getLocaleString("return")+"</button>");
        out.print("     </fieldset>\n");
        out.print(" </form>\n");
        out.print("</div>\n");
    }

    private void doAdminResume(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        SWBResourceURL url = paramRequest.getRenderUrl();
        url.setMode(paramRequest.Mode_EDIT);
        url.setParameter("suri", suri);
        Process process = getProcess(suri);
        updateAttributes(request);
        out.print("<div class=\"swbform\">\n");
        out.print("  <fieldset>\n");
        out.print(paramRequest.getLocaleString("title"));
        out.print("  </fieldset>\n");
        out.print("  <fieldset>\n");
        out.print("     <legend>" + paramRequest.getLocaleString("process") + "</legend>\n");
        out.print("     <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("         <tr>\n");
        out.print("             <td>" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("process_"+process.getId(),"")) ? getProcessTitle(suri) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "</td>\n");
        out.print("         </tr>\n");
        out.print("     </table>\n");
        out.print("  </fieldset>\n");
        out.print("  <fieldset>\n");
        out.print("     <legend>" + paramRequest.getLocaleString("PLOT_TYPE") + "</legend>\n");
        out.print("     <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("         <tr>\n");
        out.print("             <td>" + ("1".equalsIgnoreCase(getAttribute(suri, "plot")) ? paramRequest.getLocaleString("bars") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("2".equalsIgnoreCase(getAttribute(suri, "plot")) ? paramRequest.getLocaleString("pie") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("3".equalsIgnoreCase(getAttribute(suri, "plot")) ? paramRequest.getLocaleString("area") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("PLOT_THEME") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("1".equalsIgnoreCase(getAttribute(suri,"plot_theme")) ? paramRequest.getLocaleString("blue") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("2".equalsIgnoreCase(getAttribute(suri,"plot_theme")) ? paramRequest.getLocaleString("green") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("3".equalsIgnoreCase(getAttribute(suri,"plot_theme")) ? paramRequest.getLocaleString("red") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("TIME_UNIT") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("1".equalsIgnoreCase(getAttribute(suri,"time_unit")) ? paramRequest.getLocaleString("seconds") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("2".equalsIgnoreCase(getAttribute(suri,"time_unit")) ? paramRequest.getLocaleString("minutes") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("3".equalsIgnoreCase(getAttribute(suri,"time_unit")) ? paramRequest.getLocaleString("hours") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("4".equalsIgnoreCase(getAttribute(suri,"time_unit")) ? paramRequest.getLocaleString("days") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
//        out.print("      <fieldset>\n");
//        out.print("          <legend>" + paramRequest.getLocaleString("labels") + "</legend>\n");
//        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
//        out.print("              <tr>\n");
//        if ("1".equalsIgnoreCase(getAttribute(suri,"display_totals")))
//            out.print("                  <td>" + paramRequest.getLocaleString("DISPLAY_TOTALS") + "</td>\n");
//        else
//            out.print("                  <td>" + paramRequest.getLocaleString("DEFAULT_VIEW") + "</td>\n");
//        out.print("              </tr>\n");
//        out.print("         </table>\n");
//        out.print("     </fieldset>\n");
        
//        out.print("      <fieldset>\n");
//        out.print("          <legend>" + paramRequest.getLocaleString("LOG") + "</legend>\n");
//        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
//        out.print("              <tr>\n");
//        out.print("                  <td>" + ("1".equalsIgnoreCase(getAttribute(suri,"last_date")) ? paramRequest.getLocaleString("lastWeek") : "") + "</td>\n");
//        out.print("              </tr>\n");
//        out.print("              <tr>\n");
//        out.print("                  <td>" + ("2".equalsIgnoreCase(getAttribute(suri,"last_date")) ? paramRequest.getLocaleString("lastMonth") : "") + "</td>\n");
//        out.print("              </tr>\n");
//        out.print("              <tr>\n");
//        out.print("                  <td>" + ("3".equalsIgnoreCase(getAttribute(suri,"last_date")) ? paramRequest.getLocaleString("lastYear") : "") + "</td>\n");
//        out.print("              </tr>\n");
//        out.print("         </table>\n");
//        out.print("     </fieldset>\n");
        
        out.print("     <fieldset>\n");
        out.print("         <button dojoType=\"dijit.form.Button\" onClick=\"submitUrl('" + url + "',this.domNode); return false;\">"+paramRequest.getLocaleString("config")+"</button>");
        url = paramRequest.getRenderUrl();
        url.setMode(SWBResourceURL.Mode_VIEW);
        url.setParameter("suri", suri);
        out.print("         <button dojoType=\"dijit.form.Button\" onClick=\"submitUrl('" + url + "',this.domNode); return false;\">"+paramRequest.getLocaleString("return")+"</button>");
        out.print("     </fieldset>\n");
        out.print("</div>\n");
    }

    /*private void doAdminResume(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL url = paramRequest.getRenderUrl().setMode(paramRequest.Mode_EDIT);
        updateAttributes(request);
        out.print("<div class=\"swbform\">\n");
        out.print("  <fieldset>\n");
        out.print(paramRequest.getLocaleString("title"));
        out.print("  </fieldset>\n");
        out.print("  <fieldset>\n");
        out.print("     <legend>" + paramRequest.getLocaleString("process") + "</legend>\n");
        out.print("     <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("         <tr>\n");
        out.print("             <td>" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("process","")) ? getProcessTitle(getResourceBase().getAttribute("process")) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "</td>\n");
        out.print("         </tr>\n");
        out.print("     </table>\n");
        out.print("  </fieldset>\n");
        out.print("  <fieldset>\n");
        out.print("     <legend>" + paramRequest.getLocaleString("PLOT_TYPE") + "</legend>\n");
        out.print("     <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("         <tr>\n");
        out.print("             <td>" + ("1".equalsIgnoreCase(getResourceBase().getAttribute("plot","")) ? paramRequest.getLocaleString("bars") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("2".equalsIgnoreCase(getResourceBase().getAttribute("plot","")) ? paramRequest.getLocaleString("pie") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("3".equalsIgnoreCase(getResourceBase().getAttribute("plot","")) ? paramRequest.getLocaleString("area") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("PLOT_THEME") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("1".equalsIgnoreCase(getResourceBase().getAttribute("plot_theme","")) ? paramRequest.getLocaleString("blue") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("2".equalsIgnoreCase(getResourceBase().getAttribute("plot_theme","")) ? paramRequest.getLocaleString("green") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("3".equalsIgnoreCase(getResourceBase().getAttribute("plot_theme","")) ? paramRequest.getLocaleString("red") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("TIME_UNIT") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("1".equalsIgnoreCase(getResourceBase().getAttribute("time_unit","")) ? paramRequest.getLocaleString("seconds") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("2".equalsIgnoreCase(getResourceBase().getAttribute("time_unit","")) ? paramRequest.getLocaleString("minutes") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("3".equalsIgnoreCase(getResourceBase().getAttribute("time_unit","")) ? paramRequest.getLocaleString("hours") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + ("4".equalsIgnoreCase(getResourceBase().getAttribute("time_unit","")) ? paramRequest.getLocaleString("days") : "") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("labels") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        if (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")))
            out.print("                  <td>" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? paramRequest.getLocaleString("DISPLAY_TOTALS") : "") + "</td>\n");
        else
            out.print("                  <td>" + paramRequest.getLocaleString("DEFAULT_VIEW") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("     <fieldset>\n");
        out.print("         <button dojoType=\"dijit.form.Button\" onClick=location='" + url + "'>"+paramRequest.getLocaleString("config")+"</button>");
        url = paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW);
        out.print("         <button dojoType=\"dijit.form.Button\" onClick=location='" + url + "'>"+paramRequest.getLocaleString("return")+"</button>");
        out.print("     </fieldset>\n");
        out.print("</div>\n");
    }*/

    private void updateAttributes(HttpServletRequest request) {
        String suri = request.getParameter("suri");
        Process process = getProcess(suri);
        try {
            getResourceBase().setAttribute("process_"+process.getId(),getConfig(request.getParameter("plot"), request.getParameter("plot_theme"), request.getParameter("time_unit"), request.getParameter("show_log"), request.getParameter("last_date")));
            /*getResourceBase().setAttribute("process", request.getParameter("process"));
            getResourceBase().setAttribute("plot", request.getParameter("plot"));
            getResourceBase().setAttribute("plot_theme", request.getParameter("plot_theme"));
            getResourceBase().setAttribute("time_unit", request.getParameter("time_unit"));
            getResourceBase().setAttribute("display_totals", request.getParameter("display_totals"));*/
            getResourceBase().updateAttributesToDB();
        }catch (SWBException swbe) {
            log.error(swbe);
        }
    }

    public void doGraph(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, String suri) throws SWBResourceException, IOException {
        Process process = getProcess(suri);
        if (process != null) {
            String pid = process.getId();
            String sconf = getAttribute(pid,"plot");
            if ("1".equalsIgnoreCase(sconf))
                doBars(request, response, paramRequest, process);
            else if ("3".equalsIgnoreCase(sconf))
                doArea(request, response, paramRequest, process);
            else
                doPie(request, response, paramRequest, process);
        }
    }

    public void doPie(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, Process process) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        setPlotTheme(getAttribute(process.getId(), "plot_theme"));
        long lid = System.currentTimeMillis();
        
        out.println("<script type=\"text/javascript\">");
        out.println("   dojo.require(\"dojox.charting.Chart2D\");");
        out.println("   dojo.require(\"" + theme + "\");");
        out.println("   dojo.require(\"dojox.charting.action2d.MoveSlice\");");
        out.println("   dojo.require(\"dojox.charting.action2d.Tooltip\");");
        out.println("   dojo.require(\"dojox.charting.action2d.Highlight\");");
        out.println("   makeObjects = function(){");
        out.println("       var chart = new dojox.charting.Chart2D(\""+lid+"_instances\");");
        out.println("       chart.setTheme(" + theme + ");");
        out.println("       chart.addPlot(\"default\", {");
        out.println("           type: \"Pie\",");
        out.println("           labelOffset: 40,");
        out.println("           fontColor: \"white\",");
        out.println("           radius: 120");
        out.println("       });");
        out.println("       chart.addSeries(\"CaseResponseTime\", [");
        out.println("           " + getDataPie(process, paramRequest));
        out.println("       ]);");
        out.println("       var a = new dojox.charting.action2d.MoveSlice(chart, \"default\");");
        //out.println("       var b = new dojox.charting.action2d.Highlight(chart, \"default\", {highlight: \"#6698FF\"});");
        out.println("       var c = new dojox.charting.action2d.Tooltip(chart, \"default\");");
        out.println("       chart.render();");
        out.println("   };");
        out.println("   dojo.addOnLoad(makeObjects);");
        out.println("</script>");
        out.println("<div id=\""+lid+"_instances\" style=\"width: 400px; height: 300px;\"></div>");
        out.println("<div id=\""+lid+"_title\" style=\"width:400px; height:50px; text-align:center;\"><label>" + process.getTitle() + "</label></div>\n");
    }
    
    public void doBars(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, Process process) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        long lid = System.currentTimeMillis();
        setPlotTheme(getAttribute(process.getId(), "plot_theme"));
        String time_unit = getAttribute(process.getId(), "time_unit");
        String unit = paramRequest.getLocaleString("days");
        if (time_unit.equalsIgnoreCase("1")) unit = paramRequest.getLocaleString("seconds");
        if (time_unit.equalsIgnoreCase("2")) unit = paramRequest.getLocaleString("minutes");
        if (time_unit.equalsIgnoreCase("3")) unit = paramRequest.getLocaleString("hours");
        
        Long [] _data = getResponseTimeData(process, time_unit);
        if (_data != null) {
            out.println("<script type=\"text/javascript\">");
            out.println("   dojo.require(\"dojox.charting.Chart2D\");");
            out.println("   dojo.require(\"" + theme + "\");");
            out.println("   dojo.require(\"dojox.charting.action2d.Tooltip\");");
            out.println("   makeObjects = function(){");
            out.println("       var chartData = [" + _data[0] +"," + _data[1] + "," + _data[2] + "];");
            out.println("       var chart = new dojox.charting.Chart2D(\""+lid+"_instances\");");
            out.println("       chart.setTheme(" + theme + ");");
            out.println("       chart.addPlot(\"default\", {");
            out.println("           type: \"Columns\",");
            out.println("           markers: true,");
            out.println("           gap: 5");
            out.println("       });");
            out.println("       chart.addAxis(\"x\", {");
            out.println("           labels:[");
            out.println("               {value:1, text:\""+ paramRequest.getLocaleString("minimum") +"\"},");
            out.println("               {value:2, text:\"" + paramRequest.getLocaleString("average") + "\"},");
            out.println("               {value:3, text:\"" + paramRequest.getLocaleString("maximum") + "\"}");
            out.println("           ],");
            out.println("           majorTicks: true,");
            out.println("           majorLabels: true,");
            out.println("           minorTicks: true,");
            out.println("           minorLabels: true,");
            out.println("       });");
            out.println("       chart.addAxis(\"y\", {vertical:true, min:0});");
            out.println("       chart.addSeries(\"CaseResponseTime\", chartData);");
            out.println("       var c = new dojox.charting.action2d.Tooltip(chart, \"default\");");
            out.println("       chart.render();");
            out.println("   };");
            out.println("   dojo.addOnLoad(makeObjects);");
            out.println("</script>");
            out.println("<div id=\""+lid+"_instances\" style=\"width: 700px; height: 300px;\"></div>");
            out.println("<div id=\"stage\" style=\"width:700px; height:50px; text-align:center;\"><label>" + paramRequest.getLocaleString("title") + "("+ unit +")</label></div>\n");
        }
    }

    public void doArea(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, Process process) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        long lid = System.currentTimeMillis();
        setPlotTheme(getAttribute(process.getId(), "plot_theme"));
        String time_unit = getAttribute(process.getId(), "time_unit");
        String unit = paramRequest.getLocaleString("days");
        if (time_unit.equalsIgnoreCase("1")) unit = paramRequest.getLocaleString("seconds");
        if (time_unit.equalsIgnoreCase("2")) unit = paramRequest.getLocaleString("minutes");
        if (time_unit.equalsIgnoreCase("3")) unit = paramRequest.getLocaleString("hours");
        
        Long [] _data = getResponseTimeData(process, time_unit);
        if (_data != null) {
            out.println("<script type=\"text/javascript\">");
            out.println("   dojo.require(\"dojox.charting.Chart2D\");");
            out.println("   dojo.require(\"" + theme + "\");");
            out.println("   dojo.require(\"dojox.charting.action2d.Tooltip\");");
            out.println("   makeObjects = function(){");
            out.println("       var chartData = [" + _data[0] +"," + _data[1] + "," + _data[2] + "];");
            out.println("       var chart = new dojox.charting.Chart2D(\""+lid+"_instances\");");
            out.println("       chart.setTheme(" + theme + ");");
            out.println("       chart.addPlot(\"default\", {");
            out.println("           type: \"StackedAreas\",");
            out.println("           markers: true,");
            out.println("       });");
            out.println("       chart.addAxis(\"x\", {");
            out.println("           labels:[");
            out.println("               {value:1, text:\""+ paramRequest.getLocaleString("minimum") +"\"},");
            out.println("               {value:2, text:\"" + paramRequest.getLocaleString("average") + "\"},");
            out.println("               {value:3, text:\"" + paramRequest.getLocaleString("maximum") + "\"}");
            out.println("           ],");
            out.println("           majorTicks: true,");
            out.println("           majorLabels: true,");
            out.println("           minorTicks: true,");
            out.println("           minorLabels: true,");
            out.println("       });");
            out.println("       chart.addAxis(\"y\", {vertical:true, min:0, max:" + _data[2] + "});");
            out.println("       chart.addSeries(\"CaseResponseTime\", chartData);");
            out.println("       var c = new dojox.charting.action2d.Tooltip(chart, \"default\");");
            out.println("       chart.render();");
            out.println("   };");
            out.println("   dojo.addOnLoad(makeObjects);");
            out.println("</script>");
            out.println("<div id=\""+lid+"_instances\" style=\"width: 700px; height: 300px;\"></div>");
            out.println("<div id=\"stage\" style=\"width:700px; height:50px; text-align:center;\"><label>" + paramRequest.getLocaleString("title") + "(" + unit + ")</label></div>\n");
        }
    }

    private void setPlotTheme(String plot_theme) {
        if ("1".equalsIgnoreCase(plot_theme)) {
            theme = "dojox.charting.themes.PlotKit.blue";
        }else if ("2".equalsIgnoreCase(plot_theme)) {
            theme = "dojox.charting.themes.PlotKit.green";
        }else if ("3".equalsIgnoreCase(plot_theme)) {
            theme = "dojox.charting.themes.PlotKit.red";
        }
    }

    private Long[] getResponseTimeDataByStatus(Process p, int status, String timeunit) {
        Long[] ret = null;
        int divisor = 86400000;
        long max_time = 0;
        long min_time = 0;
        long sum_time = 0;
        
        Iterator<ProcessInstance> instances = p.listProcessInstances();
        if (instances.hasNext()) {
            int c_instances = 0;
            while(instances.hasNext()) {
                ProcessInstance instance = instances.next();
                if (instance.getStatus() == status) {
                    c_instances++;
                    long instanceTime = instance.getEnded().getTime() - instance.getCreated().getTime();
                    sum_time += instanceTime;
                    if (instanceTime > max_time) max_time = instanceTime;
                    if (c_instances == 1 || instanceTime < min_time) min_time = instanceTime;
                }
            }
            if (c_instances != 0) {
                if (timeunit.equals(TIMEUNIT_SECONDS)) divisor = 1000;
                if (timeunit.equals(TIMEUNIT_MINUTES)) divisor = 60000;
                if (timeunit.equals(TIMEUNIT_HOURS)) divisor = 3600000;
                sum_time = (sum_time / c_instances);
            }
        }
        ret = new Long[3];
        ret[0] = min_time / divisor;
        ret[1] = sum_time / divisor;
        ret[2] = max_time / divisor;
        return ret;
    }
    
    private Long[] getResponseTimeData(Process p, String timeunit) {
        return getResponseTimeDataByStatus(p, ProcessInstance.STATUS_CLOSED, timeunit);
    }

    private String getDataPie(Process process, SWBParamRequest paramRequest) throws SWBResourceException {
        StringBuilder data = new StringBuilder();
        String time_unit = getAttribute(process.getId(), "time_unit");
        Long [] _data = getResponseTimeData(process, time_unit);
        
        if (_data != null) {
            String unit = paramRequest.getLocaleString("days");
            if (time_unit.equalsIgnoreCase("1")) unit = paramRequest.getLocaleString("seconds");
            if (time_unit.equalsIgnoreCase("2")) unit = paramRequest.getLocaleString("minutes");
            if (time_unit.equalsIgnoreCase("3")) unit = paramRequest.getLocaleString("hours");
            data.append("{y: "+_data[0]);
            data.append(", text: \"" + paramRequest.getLocaleString("minimum") + "\", tooltip: \"" + paramRequest.getLocaleString("minTime") + " " + _data[0] + " " + unit + "\"},");
            data.append("{y: "+_data[1]);
            data.append(", text: \"" + paramRequest.getLocaleString("average") + "\", tooltip: \""+ paramRequest.getLocaleString("avgTime") + " " + _data[1] + " " + unit + "\"},");
            data.append("{y: "+_data[2]);
            data.append(", text: \"" + paramRequest.getLocaleString("maximum") + "\", tooltip: \""+ paramRequest.getLocaleString("maxTime") + " " + _data[2] + " " +unit + "\"}");
        }
        return data.toString();
    }
    
    private String getProcessTitle(String suri) {
        String title = "";
        if (null != suri) {
            Process process = getProcess(suri);
            if (null != process)
                title = process.getTitle();
        }
        return title;
    }

    /***
     * Recupera un proceso a partir de la URI proporcionada.
     * @param suri URI del proceso a recuperar.
     * @return Proceso al que corresponde la URI o null.
     */
    private Process getProcess(String suri) {
        Process ret = null;
        SemanticObject sobj = SemanticObject.createSemanticObject(suri);
        if (sobj != null && sobj.instanceOf(Process.sclass)) {
            ret = (Process)sobj.createGenericInstance();
        }
        return ret;
    }

    private String getConfig(String plot, String colours, String time, String log, String lowerDate) {
        StringBuilder config = new StringBuilder();
        config.append(plot).append(colours).append(time);
        if ("1".equals(log)) config.append(log); else config.append("0");
        config.append(lowerDate);
        return config.toString();
    }

    private String getAttribute(String pid, String title) {
        String attribute = "";

        String config = getResourceBase().getAttribute("process_"+pid, "");
        if ("plot".equals(title)) {
            if (config.length() > 0) attribute = config.substring(0, 1); else attribute = "2";
        }else if ("plot_theme".equals(title)) {
            if (config.length() > 1) attribute = config.substring(1, 2); else attribute = "1";
        }else if ("time_unit".equals(title)) {
            if (config.length() > 2) attribute = config.substring(2, 3); else attribute = "1";
        }
//        else if ("show_log".equals(title)) {
//            if (config.length() > 4) attribute = config.substring(3, 4); else attribute = "0";
//        } else if ("last_date".equals(title)) {
//            if (config.length() > 5) attribute = config.substring(4, 5); else attribute = "1";
//        }
        return attribute;
    }
}