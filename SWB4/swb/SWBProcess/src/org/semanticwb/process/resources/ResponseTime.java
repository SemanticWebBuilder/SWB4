
package org.semanticwb.process.resources;

import java.util.Iterator;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.process.model.ProcessSite;
import org.semanticwb.process.model.SWBProcessMgr;
import org.semanticwb.process.model.ProcessWebPage;
import org.semanticwb.process.kpi.ResponseTimeStages;

import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author Sergio Téllez
 */
public class ResponseTime extends GenericResource {

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
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
        response.getWriter().print("</div>\n");
    }
}