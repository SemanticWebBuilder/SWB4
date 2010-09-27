
package org.semanticwb.process.resources;


import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;

import java.util.Iterator;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.ProcessSite;
import org.semanticwb.process.model.ProcessInstance;
import org.semanticwb.process.model.FlowNodeInstance;
import org.semanticwb.process.model.SubProcessInstance;

import org.semanticwb.process.utils.Ajax;
import org.semanticwb.process.kpi.ResponseTimeStages;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBException;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBResourceException;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.general.DatasetUtilities;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.plot.PlotOrientation;

import org.jfree.data.function.Function2D;
import org.jfree.data.function.NormalDistributionFunction2D;


/**
 *
 * @author Sergio Téllez
 */
public class ResponseTime extends GenericResource {

    private static Logger log = SWBUtils.getLogger(ResponseTime.class);

    String opacity = "0.4";
    String colour = "#3090C7";
    String[] colours = {"#3090C7", "#1589FF", "#0760F9", "#157DEC", "#6698FF", "", "#87AFC7", "#659EC7", "#8BB381", "#348781"};
    String[] highColours = {"#EB8EBF", "#AB91BC", "#637CB0", "#92C2DF", "#BDDDE4", "#69BF8E", "#B0D990", "#F7FA7B", "#F9DF82", "#E46F6A"};

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if ("add".equals(paramRequest.getAction()) || "edit".equals(paramRequest.getAction()))
            doAdminCase(request, response, paramRequest);
        else
            doAdminResume(request, response, paramRequest);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        /*PrintWriter out = response.getWriter();
        Iterator isites = ProcessSite.ClassMgr.listProcessSites();
        response.getWriter().print("<div class=\"swbform\">\n");
        response.getWriter().print("  <fieldset>\n");
        while (isites.hasNext()) {
            ProcessSite site = (ProcessSite)isites.next();
            Iterator <org.semanticwb.process.model.ProcessWebPage>itProcessWebPages = ProcessWebPage.ClassMgr.listProcessWebPages(site);
            while (itProcessWebPages.hasNext()) {
                ProcessWebPage pwp = (ProcessWebPage)itProcessWebPages.next();
                org.semanticwb.process.model.Process process = SWBProcessMgr.getProcess(pwp);
                if ("Soporte Técnico".equalsIgnoreCase(process.getTitle())) {
                    out.println("<b>Soporte Técnico</b><ul>");
                    out.println("<li>Tiempo promedio de ejecución del escenario Inicio-Solución: " + ResponseTimeStages.getAverageTimeStages(process,"Start Event","Solucion") + " milisegundos</li>");
                    out.println("<li>Tiempo mínimo de ejecución del escenario Inicio-Solución: " + ResponseTimeStages.getMinimumTimeStages(process,"Start Event","Solucion") + " milisegundos</li>");
                    out.println("<li>Tiempo máximo de ejecución del escenario Inicio-Solución: " + ResponseTimeStages.getMaximumTimeStages(process,"Start Event","Solucion") + " milisegundos</li>");
                    out.println("<li>Tiempo promedio de ejecución del escenario Inicio-Fin: " + ResponseTimeStages.getAverageTimeStages(process,"Start Event","End Event") + " milisegundos</li>");
                    out.println("<li>Tiempo mínimo de ejecución del escenario Solución-Verifica: " + ResponseTimeStages.getMinimumTimeStages(process,"Solucion","Verifica") + " milisegundos</li>");
                    out.println("<li>Tiempo máximo de ejecución del escenario Solución-Fin: " + ResponseTimeStages.getMaximumTimeStages(process,"Solucion","End Event") + " milisegundos</li>");
                    out.println("</ul>");
                }
            }
        }
        response.getWriter().print("  </fieldset>\n");
        response.getWriter().print("</div>\n");*/
        response.getWriter().println("<div class=\"swbform\">\n");
        response.getWriter().print("  <fieldset>\n");
        doGraph(request, response, paramRequest);
        response.getWriter().print("  </fieldset>\n");
        response.getWriter().println("</div>\n");
    }

    public void doGraph(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Iterator isites = ProcessSite.ClassMgr.listProcessSites();
        while (isites.hasNext()) {
            ProcessSite site = (ProcessSite)isites.next();
            Iterator<Process> it = site.listProcesses();
            while (it.hasNext()) {
                Process process = it.next();
                if (process.getId().equals(getResourceBase().getAttribute("process",""))) {
                    if ("1".equalsIgnoreCase(getResourceBase().getAttribute("plot","")))
                        doBars(request, response, paramRequest, process);
                    else if ("3".equalsIgnoreCase(getResourceBase().getAttribute("plot","")))
                        doArea(request, response, paramRequest, process);
                    else
                        doPie(request, response, paramRequest, process);
                }
            }
        }
    }

    public void doAdminCase(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL url = paramRequest.getRenderUrl();
        url.setMode(paramRequest.Mode_ADMIN);
        url.setAction("edit");
        updateAttributes(request);
        out.print("<script>\n");
        out.print(" function stage(form) {\n");
        out.print("     form.action=\"" + url.toString() + "\";\n");
        out.print("     form.submit();\n");
        out.print(" }\n");
        out.print("</script>\n");
        url.setAction("resume");
        out.print("<div class=\"swbform\">\n");
        out.print("  <fieldset>\n");
        out.print(paramRequest.getLocaleString("TIME_STAGE"));
        out.print("  </fieldset>\n");
        out.print("  <form id=\"case\" name=\"case\" action=" + url.toString() + " method=\"post\">\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("process") + "</legend>\n");
        out.print("          <table border=\"0\"  width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td>\n");
        out.print("                      <select id=\"process\" name=\"process\" onchange=\"stage(form);\">\n");
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
        out.print("                      </select>\n");
        out.print("                  </td>");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("INIT_STAGE") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td>\n");
        out.print("                      <select id=\"init_stage\" name=\"init_stage\" onchange=\"stage(form);\">\n");
        ProcessInstance pinst = getProcessInstance(getResourceBase().getAttribute("process",""));
        if (null != pinst) {
            Iterator<FlowNodeInstance> flowbis = pinst.listFlowNodeInstances();
            while(flowbis.hasNext()) {
                FlowNodeInstance obj = flowbis.next();
                selectInitStage(obj, out, paramRequest);
            }
        }
        out.print("                      </select>\n");
        out.print("                  </td>");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("FINAL_STAGE") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td>\n");
        out.print("                      <select id=\"final_stage\" name=\"final_stage\">\n");
        if (null != pinst) {
            Iterator<FlowNodeInstance> flowbis = pinst.listFlowNodeInstances();
            while(flowbis.hasNext()) {
                FlowNodeInstance obj = flowbis.next();
                selectFinalStage(obj, out, paramRequest);
            }
        }
        out.print("                      </select>\n");
        out.print("                  </td>");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("PLOT_TYPE") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot\" type=\"radio\" name=\"plot\" value=\"1\"" + ("1".equalsIgnoreCase(getResourceBase().getAttribute("plot","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("bars") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot\" type=\"radio\" name=\"plot\" value=\"2\"" + ("2".equalsIgnoreCase(getResourceBase().getAttribute("plot","")) ? " checked" : "") + ("".equalsIgnoreCase(getResourceBase().getAttribute("plot","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("pie") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot\" type=\"radio\" name=\"plot\" value=\"3\"" + ("3".equalsIgnoreCase(getResourceBase().getAttribute("plot","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("area") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("PLOT_THEME") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot_theme\" type=\"radio\" name=\"plot_theme\" value=\"1\"" + ("1".equalsIgnoreCase(getResourceBase().getAttribute("plot_theme","")) ? " checked" : "") + ("".equalsIgnoreCase(getResourceBase().getAttribute("plot_theme","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("blue") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot_theme\" type=\"radio\" name=\"plot_theme\" value=\"2\"" + ("2".equalsIgnoreCase(getResourceBase().getAttribute("plot_theme","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("green") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"plot_theme\" type=\"radio\" name=\"plot_theme\" value=\"3\"" + ("3".equalsIgnoreCase(getResourceBase().getAttribute("plot_theme","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("red") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("TIME_UNIT") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"time_unit\" type=\"radio\" name=\"time_unit\" value=\"1\"" + ("1".equalsIgnoreCase(getResourceBase().getAttribute("time_unit","")) ? " checked" : "") + ("".equalsIgnoreCase(getResourceBase().getAttribute("time_unit","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("seconds") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"time_unit\" type=\"radio\" name=\"time_unit\" value=\"2\"" + ("2".equalsIgnoreCase(getResourceBase().getAttribute("time_unit","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("minutes") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"time_unit\" type=\"radio\" name=\"time_unit\" value=\"3\"" + ("3".equalsIgnoreCase(getResourceBase().getAttribute("time_unit","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("hours") + "</td>\n");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"time_unit\" type=\"radio\" name=\"time_unit\" value=\"4\"" + ("4".equalsIgnoreCase(getResourceBase().getAttribute("time_unit","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("days") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("labels") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td><input id=\"display_totals\" type=\"checkbox\" name=\"display_totals\" value=\"1\"" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? " checked" : "") + "> " + paramRequest.getLocaleString("DISPLAY_TOTALS") + "</td>\n");
        out.print("              </tr>\n");
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("     <fieldset>\n");
        out.print("         <button  dojoType=\"dijit.form.Button\" type=\"submit\" >"+paramRequest.getLocaleString("apply")+"</button>");
        out.print("     </fieldset>\n");
        out.print(" </form>\n");
        out.print("</div>\n");
    }

    private void doAdminResume(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        updateAttributes(request);
        out.print("<div class=\"swbform\">\n");
        out.print("  <fieldset>\n");
        out.print(paramRequest.getLocaleString("TIME_STAGE"));
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
        out.print("     <legend>" + paramRequest.getLocaleString("INIT_STAGE") + "</legend>\n");
        out.print("     <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("         <tr>\n");
        out.print("             <td>" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("init_stage","")) ? getStageTitle(getResourceBase().getAttribute("process"), getResourceBase().getAttribute("init_stage"), paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "</td>\n");
        out.print("         </tr>\n");
        out.print("     </table>\n");
        out.print("  </fieldset>\n");
        out.print("  <fieldset>\n");
        out.print("     <legend>" + paramRequest.getLocaleString("FINAL_STAGE") + "</legend>\n");
        out.print("     <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("         <tr>\n");
        out.print("             <td>" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("final_stage","")) ? getStageTitle(getResourceBase().getAttribute("process"), getResourceBase().getAttribute("final_stage"), paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "</td>\n");
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
        out.print("</div>\n");
    }

    private void updateAttributes(HttpServletRequest request) {
        try {
            if (null != request.getParameter("process")) {
                getResourceBase().setAttribute("process", request.getParameter("process"));
                getResourceBase().setAttribute("init_stage", request.getParameter("init_stage"));
                getResourceBase().setAttribute("final_stage", request.getParameter("final_stage"));
                getResourceBase().setAttribute("plot", request.getParameter("plot"));
                getResourceBase().setAttribute("plot_theme", request.getParameter("plot_theme"));
                getResourceBase().setAttribute("time_unit", request.getParameter("time_unit"));
                getResourceBase().setAttribute("display_totals", request.getParameter("display_totals"));
                getResourceBase().updateAttributesToDB();
            }
        }catch (SWBException swbe) {
            swbe.printStackTrace();
        }
    }

    private ProcessInstance getProcessInstance(String processId) {
        Iterator isites = ProcessSite.ClassMgr.listProcessSites();
        while (isites.hasNext()) {
            ProcessSite site = (ProcessSite)isites.next();
            Iterator<Process> itprocess = site.listProcesses();
            while (itprocess.hasNext()) {
                Process process = itprocess.next();
                if (processId.equalsIgnoreCase(process.getId()))
                    return org.semanticwb.process.kpi.CaseProcessInstance.pop(process);
            }
        }
        return null;
    }

    private void selectInitStage(FlowNodeInstance ai, PrintWriter out, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (!"".equals(getTitle(ai,paramRequest)) && !"org.semanticwb.process.model.EndEvent".equalsIgnoreCase(ai.getFlowNodeType().getSemanticObject().getSemanticClass().getClassName()))
            out.print("                  <option value=\"" +  ai.getFlowNodeType().hashCode() + "\"" + (getResourceBase().getAttribute("init_stage","").equalsIgnoreCase(("" + ai.getFlowNodeType().hashCode())) ? " selected" : "") + " >" + getTitle(ai,paramRequest) +  "</option>\n");
        if (ai instanceof SubProcessInstance) {
            SubProcessInstance spi = (SubProcessInstance)ai;
            Iterator<FlowNodeInstance> acit = spi.listFlowNodeInstances();
            if (acit.hasNext()) {
                while(acit.hasNext()) {
                    FlowNodeInstance actinst =  acit.next();
                    selectInitStage(actinst,out,paramRequest);
                }
            }
        }
    }

    private GenericIterator<FlowNodeInstance> getTargetStage(FlowNodeInstance ai) {
        GenericIterator<FlowNodeInstance> targetInstances = null;
        if (("" + ai.getFlowNodeType().hashCode()).equals(getResourceBase().getAttribute("init_stage","")))
            targetInstances = ai.listTargetInstances();
        else if (ai instanceof SubProcessInstance) {
            SubProcessInstance spi = (SubProcessInstance)ai;
            Iterator<FlowNodeInstance> acit = spi.listFlowNodeInstances();
            if (acit.hasNext()) {
                while(acit.hasNext()) {
                    FlowNodeInstance actinst =  acit.next();
                    getTargetStage(actinst);
                }
            }
        }
        return targetInstances;
    }

    private void selectFinalStage(FlowNodeInstance ai, PrintWriter out, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        ArrayList<FlowNodeInstance> stage = new ArrayList();
        setReachableStage(getTargetStage(ai), stage);
        Iterator<FlowNodeInstance> targetStage = stage.listIterator();
        while (targetStage.hasNext()) {
            FlowNodeInstance fni =  targetStage.next();
            if (!"".equals(getTitle(fni, paramRequest)))
                out.print("                  <option value=\"" +  fni.getFlowNodeType().hashCode() + "\"" + (getResourceBase().getAttribute("final_stage","").equalsIgnoreCase(("" + fni.getFlowNodeType().hashCode())) ? " selected" : "") + " >" + getTitle(fni, paramRequest) +  "</option>\n");
        }
    }

    private void setReachableStage(GenericIterator<FlowNodeInstance> targetInstances, ArrayList<FlowNodeInstance> stage) {
        if (null != targetInstances) {
            while (targetInstances.hasNext()) {
                FlowNodeInstance fni = targetInstances.next();
                stage.add(fni);
                setReachableStage(fni.listTargetInstances(), stage);
            }
        }
    }

    private String getStageTitle(FlowNodeInstance ai, String stageId, SWBParamRequest paramRequest) {
        String title = null;
        if (stageId.equalsIgnoreCase("" + ai.getFlowNodeType().hashCode()))
            title = getTitle(ai, paramRequest);
        if (ai instanceof SubProcessInstance) {
            SubProcessInstance spi = (SubProcessInstance)ai;
            Iterator<FlowNodeInstance> acit = spi.listFlowNodeInstances();
            if (acit.hasNext()) {
                while(acit.hasNext()) {
                    FlowNodeInstance actinst =  acit.next();
                    getStageTitle(actinst,stageId, paramRequest);
                }
            }
        }
        return title;
    }

    private String getStageTitle(String processId, String stageId, SWBParamRequest paramRequest) {
        String title = null;
        ProcessInstance pinst = getProcessInstance(processId);
        if (null != pinst) {
            Iterator<FlowNodeInstance> flowbis = pinst.listFlowNodeInstances();
            while(flowbis.hasNext()) {
                FlowNodeInstance obj = flowbis.next();
                if (null != getStageTitle(obj, stageId, paramRequest))
                    title = getStageTitle(obj, stageId, paramRequest);
            }
        }
        return title;
    }
    
    private Process getProcess(String processId) {
        Iterator isites = ProcessSite.ClassMgr.listProcessSites();
        while (isites.hasNext()) {
            ProcessSite site = (ProcessSite)isites.next();
            Iterator<Process> itprocess = site.listProcesses();
            while (itprocess.hasNext()) {
                Process process = itprocess.next();
                if (processId.equalsIgnoreCase(process.getId()))
                    return process;
            }
        }
        return null;
    }

    private String getProcessTitle(String processId) {
        Process process = getProcess(processId);
        if (null != process)
            return process.getTitle();
        else
            return "";
    }

    private String getTitle(FlowNodeInstance fni, SWBParamRequest paramRequest) {
        if (null != fni) {
            if (null != fni.getFlowNodeType().getTitle(paramRequest.getUser().getLanguage()))
                return fni.getFlowNodeType().getTitle(paramRequest.getUser().getLanguage());
            else
                return fni.getFlowNodeType().getTitle();
        }else
            return "";
    }

    private void setPlotTheme() {
        String[] blueTheme = {"#3090C7", "#1589FF", "#0760F9", "#157DEC", "#6698FF", "#5CB3FF", "#87AFC7", "#659EC7", "#8BB381", "#348781"};
        String[] redTheme = {"#FF0000","#E42217","#E41B17","#F62817","#F62217","#E42217","#F80000","#C80000","#B80000","#900000"};
        String[] greenTheme = {"#4CC417","#57E964","#59E817","#4CC552","#4AA02C","#52D017","#41A317","#3EA99F","#348781","#387C44"};
        if ("1".equalsIgnoreCase(getResourceBase().getAttribute("plot_theme",""))) {
            opacity = "0.4";
            colour = "#3090C7";
            colours = blueTheme;
        }else if ("2".equalsIgnoreCase(getResourceBase().getAttribute("plot_theme",""))) {
            opacity = "0.4";
            colour = "#4CC552";
            colours = greenTheme;
        }else if ("3".equalsIgnoreCase(getResourceBase().getAttribute("plot_theme",""))) {
            opacity = "0.7";
            colour = "#FF0000";
            colours = redTheme;
        }
    }

    public void doPie(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, Process process) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        setPlotTheme();
        out.println("<script type=\"text/javascript\">");
        out.println("   dojo.require(\"dojox.charting.Chart2D\");");
        out.println("   dojo.require(\"dojox.charting.themes.PlotKit.blue\");");
        out.println("   dojo.require(\"dojox.charting.action2d.MoveSlice\");");
        out.println("   dojo.require(\"dojox.charting.action2d.Tooltip\");");
        out.println("   dojo.require(\"dojox.charting.action2d.Highlight\");");
        out.println("   makeObjects = function(){");
        out.println("       var chart = new dojox.charting.Chart2D(\"instances\");");
        out.println("       chart.setTheme(dojox.charting.themes.PlotKit.blue);");
        out.println("       chart.addPlot(\"default\", {");
        out.println("           type: \"Pie\",");
        out.println("           font: \"normal normal bold 10pt Tahoma\",");
        out.println("           fontColor: \"white\",");
        out.println("           labelOffset: 40,");
        out.println("           radius: 120");
        out.println("       });");
        out.println("       chart.addSeries(\"ResponseTime\", [");
        out.println("           " + getDataPie(process, paramRequest));
        out.println("       ]);");
        out.println("       var a = new dojox.charting.action2d.MoveSlice(chart, \"default\")");
        out.println("       var b = new dojox.charting.action2d.Highlight(chart, \"default\", {highlight: \"#6698FF\"});");
        out.println("       var c = new dojox.charting.action2d.Tooltip(chart, \"default\");");
        out.println("       chart.render();");
        out.println("   };");
        out.println("   dojo.addOnLoad(makeObjects);");
        out.println("</script>");
        out.println("<div id=\"title\" style=\"width:400px; height:25px; text-align:center;\"><label>" + process.getTitle() + "</label></div>\n");
        out.println("<div id=\"instances\" style=\"width: 400px; height: 300px;\"></div>");
        out.println("<div id=\"stage\" style=\"width:400px; height:50px; text-align:center;\"><label>" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("init_stage","")) ? getStageTitle(getResourceBase().getAttribute("process"), getResourceBase().getAttribute("init_stage"), paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "-" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("final_stage","")) ? getStageTitle(getResourceBase().getAttribute("process"), getResourceBase().getAttribute("final_stage"), paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) +"</label></div>\n");
    }

    public void doArea(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, Process process) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        setPlotTheme();
        out.println(Ajax.getChartScript());
        out.println("<div id=\"title\" style=\"width:400px; height:25px; text-align:center;\"><label>" + process.getTitle() + "</label></div>\n");
        out.println("<div id='instances' style='width:400px; height:300px;'></div>\n");
        out.println("<script type=\"text/javascript\">\n");
        out.println("    var areagraph = new Grafico.AreaGraph($('instances'), { workload: " + getData(process) + " },");
        out.println("        {");
        out.println("           grid :                false,");
        out.println("           area_opacity :        " + opacity + ",");
        out.println("           plot_padding :        10,");
        out.println("           font_size :           10,");
        out.println("           colors :              { workload: '" + colour + "' },");
        //out.println("           background_color :	  '#EFF5FB',");
        out.println("           label_color :         \"#348781\",");
        if (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")))
            out.println("           markers :             \"value\",");
        out.println("           meanline :            false,");
        out.println("           draw_axis :           false,\n");
        out.println("           labels :			  " + getTitles(paramRequest) + ",\n");
        out.println("           datalabels :          {workload: '" + paramRequest.getLocaleString("time") + "'}\n");
        out.println("        }\n");
        out.println("    );\n");
        out.println("</script>\n");
        out.println("<div id=\"stage\" style=\"width:400px; height:50px; text-align:center;\"><label>" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("init_stage","")) ? getStageTitle(getResourceBase().getAttribute("process"), getResourceBase().getAttribute("init_stage"), paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "-" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("final_stage","")) ? getStageTitle(getResourceBase().getAttribute("process"), getResourceBase().getAttribute("final_stage"), paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) +"</label></div>\n");
    }

    public void doBars(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, Process process) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        setPlotTheme();
        out.println(Ajax.getChartScript());
        out.println("<div id=\"title\" style=\"width:400px; height:25px; text-align:center;\"><label>" + process.getTitle() + "</label></div>\n");
        out.println("<div id='instances' style='width:400px; height:300px;'></div>\n");
        out.println("<script type=\"text/javascript\">\n");
        out.println("    var bargraph = new Grafico.BarGraph($('instances'), " + getData(process) + ",\n");
        out.println("        {\n");
        out.println("           labels :			  " + getTitles(paramRequest) + ",\n");
        out.println("           color :				  '" + colour + "',\n");
        //out.println("           background_color :	  '#EFF5FB',\n");
        out.println("           meanline :		      false,\n");
        out.println("           grid :                false,\n");
        out.println("           draw_axis :           false,\n");
        out.println("           label_rotation :	  -30,\n");
        //out.println("           vertical_label_unit : \"%\",\n");
        //out.println("           bargraph_lastcolor :  \"#666666\",\n");
        out.println("           label_color :         \"#348781\",\n");
        out.println("           hover_color :         \"#6698FF\",\n");
        out.println("           datalabels :          {one: " + getLabels(process, paramRequest) + "}\n");
        out.println("        }\n");
        out.println("    );\n");
        out.println("</script>\n");
        out.println("<div id=\"stage\" style=\"width:400px; height:50px; text-align:center;\"><label>" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("init_stage","")) ? getStageTitle(getResourceBase().getAttribute("process"), getResourceBase().getAttribute("init_stage"), paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) + "-" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("final_stage","")) ? getStageTitle(getResourceBase().getAttribute("process"), getResourceBase().getAttribute("final_stage"), paramRequest) : paramRequest.getLocaleString("DEFAULT_VIEW")) +"</label></div>\n");
    }

    private String getTitles(SWBParamRequest paramRequest) throws SWBResourceException {
        StringBuilder labels = new StringBuilder();
        labels.append("[");
        labels.append("'" + paramRequest.getLocaleString("minimum") + "', '" + paramRequest.getLocaleString("average") + "','" + paramRequest.getLocaleString("maximum") + "'");
        labels.append("]");
        return labels.toString();
    }

    private String getData(Process process) {
        long minimum = 0;
        long average = 0;
        long maximum = 0;
        StringBuilder data = new StringBuilder();
        if (!"".equalsIgnoreCase(getResourceBase().getAttribute("init_stage", "")) && !"".equalsIgnoreCase(getResourceBase().getAttribute("final_stage", ""))) {
            minimum = ResponseTimeStages.getMinimumTimeStages(process, getResourceBase().getAttribute("init_stage"), getResourceBase().getAttribute("final_stage"));
            average = ResponseTimeStages.getAverageTimeStages(process, getResourceBase().getAttribute("init_stage"), getResourceBase().getAttribute("final_stage"));
            maximum = ResponseTimeStages.getMaximumTimeStages(process, getResourceBase().getAttribute("init_stage"), getResourceBase().getAttribute("final_stage"));
        }
        data.append("[");
        if ("1".equalsIgnoreCase(getResourceBase().getAttribute("time_unit","")))
            data.append(minimum/1000 + "," + average/1000 + "," + maximum/1000);
        else if ("2".equalsIgnoreCase(getResourceBase().getAttribute("time_unit","")))
            data.append(minimum/60000 + "," + average/60000 + "," + maximum/60000);
        else if ("3".equalsIgnoreCase(getResourceBase().getAttribute("time_unit","")))
            data.append(minimum/3600000 + "," + average/3600000 + "," + maximum/3600000);
        else
            data.append(minimum/86400000 + "," + average/86400000 + "," + maximum/86400000);
        data.append("]");
        return data.toString();
    }

    private String getDataPie(Process process, SWBParamRequest paramRequest) throws SWBResourceException {
        long minimum = 0;
        long average = 0;
        long maximum = 0;
        StringBuilder data = new StringBuilder();
        if (!"".equalsIgnoreCase(getResourceBase().getAttribute("init_stage", "")) && !"".equalsIgnoreCase(getResourceBase().getAttribute("final_stage", ""))) {
            minimum = ResponseTimeStages.getMinimumTimeStages(process, getResourceBase().getAttribute("init_stage"), getResourceBase().getAttribute("final_stage"));
            average = ResponseTimeStages.getAverageTimeStages(process, getResourceBase().getAttribute("init_stage"), getResourceBase().getAttribute("final_stage"));
            maximum = ResponseTimeStages.getMaximumTimeStages(process, getResourceBase().getAttribute("init_stage"), getResourceBase().getAttribute("final_stage"));
        }
        data.append("{y: ");
        if ("1".equalsIgnoreCase(getResourceBase().getAttribute("time_unit","")))
            data.append(minimum/1000);
        else if ("2".equalsIgnoreCase(getResourceBase().getAttribute("time_unit","")))
            data.append(minimum/60000);
        else if ("3".equalsIgnoreCase(getResourceBase().getAttribute("time_unit","")))
            data.append(minimum/3600000);
        else
            data.append(minimum/86400000);
        data.append(", text: \"" + paramRequest.getLocaleString("minimum") + "\", color: \"" + colours[0] + "\"" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? ", tooltip: " + getToolTips(paramRequest, getResourceBase().getAttribute("time_unit",""), "minimum", minimum) : "") + "},");
        data.append("{y: ");
        if ("1".equalsIgnoreCase(getResourceBase().getAttribute("time_unit","")))
            data.append(average/1000);
        else if ("2".equalsIgnoreCase(getResourceBase().getAttribute("time_unit","")))
            data.append(average/60000);
        else if ("3".equalsIgnoreCase(getResourceBase().getAttribute("time_unit","")))
            data.append(average/3600000);
        else
            data.append(average/86400000);
        data.append(", text: \"" + paramRequest.getLocaleString("average") + "\", color: \"" + colours[1] + "\"" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? ", tooltip: " + getToolTips(paramRequest, getResourceBase().getAttribute("time_unit",""), "average", average) : "") + "},");
        data.append("{y: ");
        if ("1".equalsIgnoreCase(getResourceBase().getAttribute("time_unit","")))
            data.append(maximum/1000);
        else if ("2".equalsIgnoreCase(getResourceBase().getAttribute("time_unit","")))
            data.append(maximum/60000);
        else if ("3".equalsIgnoreCase(getResourceBase().getAttribute("time_unit","")))
            data.append(maximum/3600000);
        else
            data.append(maximum/86400000);
        data.append(", text: \"" + paramRequest.getLocaleString("maximum") + "\", color: \"" + colours[2] + "\"" + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? ", tooltip: " + getToolTips(paramRequest, getResourceBase().getAttribute("time_unit",""), "maximum", maximum) : "") + "}");
        return data.toString();
    }

    private String getLabels(Process process, SWBParamRequest paramRequest) throws SWBResourceException {
        long min = 0;
        long avg = 0;
        long max = 0;
        StringBuilder labels = new StringBuilder();
        StringBuilder minimum = new StringBuilder();
        StringBuilder average = new StringBuilder();
        StringBuilder maximum = new StringBuilder();
        if (!"".equalsIgnoreCase(getResourceBase().getAttribute("init_stage", "")) && !"".equalsIgnoreCase(getResourceBase().getAttribute("final_stage", ""))) {
            min = ResponseTimeStages.getMinimumTimeStages(process, getResourceBase().getAttribute("init_stage"), getResourceBase().getAttribute("final_stage"));
            avg = ResponseTimeStages.getAverageTimeStages(process, getResourceBase().getAttribute("init_stage"), getResourceBase().getAttribute("final_stage"));
            max = ResponseTimeStages.getMaximumTimeStages(process, getResourceBase().getAttribute("init_stage"), getResourceBase().getAttribute("final_stage"));
        }
        if ("1".equalsIgnoreCase(getResourceBase().getAttribute("time_unit",""))) {
            minimum.append("" + min/1000 + " " + paramRequest.getLocaleString("seconds"));
            average.append("" + avg/1000 + " " + paramRequest.getLocaleString("seconds"));
            maximum.append("" + max/1000 + " " + paramRequest.getLocaleString("seconds"));
        }else if ("2".equalsIgnoreCase(getResourceBase().getAttribute("time_unit",""))) {
            minimum.append("" + min/60000 + " " + paramRequest.getLocaleString("minutes"));
            average.append("" + avg/60000 + " " + paramRequest.getLocaleString("minutes"));
            maximum.append("" + max/60000 + " " + paramRequest.getLocaleString("minutes"));
        }else if ("3".equalsIgnoreCase(getResourceBase().getAttribute("time_unit",""))) {
            minimum.append("" + min/3600000 + " " + paramRequest.getLocaleString("hours"));
            average.append("" + avg/3600000 + " " + paramRequest.getLocaleString("hours"));
            maximum.append("" + max/3600000 + " " + paramRequest.getLocaleString("hours"));
        }else {
            minimum.append("" + min/86400000 + " " + paramRequest.getLocaleString("days"));
            average.append("" + avg/86400000 + " " + paramRequest.getLocaleString("days"));
            maximum.append("" + max/86400000 + " " + paramRequest.getLocaleString("days"));
        }
        labels.append("[");
        labels.append("'" + paramRequest.getLocaleString("minimum") + " " + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? minimum : "") + "', '" + paramRequest.getLocaleString("average") + " " + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? average : "") + "','" + paramRequest.getLocaleString("maximum") + " " + (!"".equalsIgnoreCase(getResourceBase().getAttribute("display_totals","")) ? maximum : "") + "'");
        labels.append("]");
        return labels.toString();
    }

    private String getToolTips(SWBParamRequest paramRequest, String timeUnit, String function, long time) throws SWBResourceException {
        StringBuilder labels = new StringBuilder();
        StringBuilder total = new StringBuilder();
        if ("1".equalsIgnoreCase(timeUnit)) {
            total.append(" " + time/1000);
            total.append(" " + paramRequest.getLocaleString("seconds"));
        }else if ("2".equalsIgnoreCase(getResourceBase().getAttribute("time_unit",""))) {
            total.append(" " + time/60000);
            total.append(" " + paramRequest.getLocaleString("minutes"));
        }else if ("3".equalsIgnoreCase(getResourceBase().getAttribute("time_unit",""))) {
            total.append(" " + time/3600000);
            total.append(" " + paramRequest.getLocaleString("hours"));
        }else {
            total.append(" " + time/86400000);
            total.append(" " + paramRequest.getLocaleString("days"));
        }
        labels.append("\"" + paramRequest.getLocaleString("time") + " " + paramRequest.getLocaleString(function) + total.toString() + "\"");
        return labels.toString();
    }

    public void doChart(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Iterator isites = ProcessSite.ClassMgr.listProcessSites();
        String pathFile = SWBPortal.getWorkPath() + getResourceBase().getWorkPath() + "/images";
        File filex = new File(pathFile);
        if (!filex.exists())
            filex.mkdirs();
        while (isites.hasNext()) {
            ProcessSite site = (ProcessSite)isites.next();
            Iterator<Process> it = site.listProcesses();
            while (it.hasNext()) {
                Process process = it.next();
                if ("Soporte Técnico".equalsIgnoreCase(process.getTitle()) && ResponseTimeStages.getMinimumTimeStages(process,"Start Event","Solucion") < ResponseTimeStages.getMaximumTimeStages(process,"Start Event","Solucion")) {
                    Function2D normal = new NormalDistributionFunction2D(ResponseTimeStages.getAverageTimeStages(process,"Start Event","Solucion")/1000, 1.0);
                    XYDataset dataset = DatasetUtilities.sampleFunction2D(normal, ResponseTimeStages.getMinimumTimeStages(process,"Start Event","Solucion")/1000, ResponseTimeStages.getMaximumTimeStages(process,"Start Event","Solucion")/1000, 100, "Escenario Inicio-Solución");
                    JFreeChart chart = ChartFactory.createXYLineChart(process.getTitle(), paramRequest.getLocaleString("seconds"), paramRequest.getLocaleString("NORMAL_DISTRIBUTION"), dataset, PlotOrientation.VERTICAL, true, true, false);
                    try {
                        ChartUtilities.saveChartAsPNG(new File(pathFile + "/" + process.getId() + "_stage.png"), chart, 500, 300);
                        response.getWriter().println("<div style=\"background-image: url(" + SWBPortal.getWebWorkPath() + getResourceBase().getWorkPath() + "/images/" + process.getId() + "_stage.png); height: 300px; width: 500px; border: 0px solid black;\"> </div>");
                    }catch (Exception e) {
                        log.error(e);
                    }
                }
            }
        }
    }
}