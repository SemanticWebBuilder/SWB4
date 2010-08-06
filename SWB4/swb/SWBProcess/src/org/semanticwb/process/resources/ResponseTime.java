
package org.semanticwb.process.resources;


import java.io.File;
import java.io.IOException;

import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.ProcessSite;
import org.semanticwb.process.model.SWBProcessMgr;
import org.semanticwb.process.model.ProcessWebPage;
import org.semanticwb.process.kpi.ResponseTimeStages;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBPortal;
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
        doGraph(request, response, paramRequest);
    }

    public void doGraph(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
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
                        ChartUtilities.saveChartAsJPEG(new File(pathFile + process.getTitle() + "_stage.jpg"), chart, 500, 300);
                        response.getWriter().println("<div style=\"background-image: url(" + pathFile + process.getTitle() + "_stage.jpg); height: 300px; width: 500px; border: 0px solid black;\"> </div>");
                    }catch (Exception e) {
                        log.error(e);
                    }
                }
            }
        }
    }
}