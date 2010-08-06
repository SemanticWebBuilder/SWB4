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

package org.semanticwb.process.resources;

import java.util.Iterator;
import java.io.IOException;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.ProcessSite;
import org.semanticwb.process.model.SWBProcessMgr;
import org.semanticwb.process.model.ProcessWebPage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBResourceException;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBPortal;
import org.semanticwb.process.utils.Ajax;
import org.semanticwb.process.kpi.CaseProcessObject;

import java.io.File;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.*;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Sergio Téllez
 */
public class CaseObject extends GenericResource {

    private static Logger log = SWBUtils.getLogger(CaseObject.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        /*Iterator isites = ProcessSite.ClassMgr.listProcessSites();
        response.getWriter().print("<div class=\"swbform\">\n");
        response.getWriter().print("  <fieldset>\n");
        response.getWriter().println("  <ul>");
        while (isites.hasNext()) {
            ProcessSite site = (ProcessSite)isites.next();
            Iterator <org.semanticwb.process.model.ProcessWebPage>itProcessWebPages = ProcessWebPage.ClassMgr.listProcessWebPages(site);
            while(itProcessWebPages.hasNext()) {
                ProcessWebPage pwp = (ProcessWebPage)itProcessWebPages.next();
                org.semanticwb.process.model.Process process = SWBProcessMgr.getProcess(pwp);
                if("Soporte Técnico".equals(process.getTitle())) {
                    response.getWriter().println("<li>Total presupuesto de incidente: " + Ajax.notNull(CaseProcessObject.sum(process, "Incidente", "budget")) + "</li>");
                    response.getWriter().println("<li>Presupuesto promedio de incidente: " + CaseProcessObject.average(process, "Incidente", "budget") + "</li>");
                    response.getWriter().println("<li>Presupuesto máximo de incidente: " + Ajax.notNull(CaseProcessObject.maximum(process, "Incidente", "budget")) + "</li>");
                    response.getWriter().println("<li>Presupuesto mínimo de incidente: " + Ajax.notNull(CaseProcessObject.minimum(process, "Incidente", "budget")) + "</li>");
                    response.getWriter().println("<li>Presupuestos distintos de incidente: " + CaseProcessObject.distincts(process, "Incidente", "budget").size() + "</li>");
                }
            }
        }
        response.getWriter().println("      </ul>");
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
                if ("Soporte Técnico".equalsIgnoreCase(process.getTitle())) {
                    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                    dataset.setValue((Float)CaseProcessObject.sum(process, "Incidente", "budget"), "Incidente", "Total");
                    dataset.setValue((Float)CaseProcessObject.maximum(process, "Incidente", "budget"), "Incidente", "Máximo");
                    dataset.setValue((Double)CaseProcessObject.average(process, "Incidente", "budget"), "Incidente", "Promedio");
                    dataset.setValue((Float)CaseProcessObject.minimum(process, "Incidente", "budget"), "Incidente", "Mínimo");
                    JFreeChart chart = ChartFactory.createBarChart(process.getTitle(), "Presupuesto", "Pesos ($)", dataset, PlotOrientation.VERTICAL, true, true, false);
                    try {
                        ChartUtilities.saveChartAsJPEG(new File(pathFile + process.getTitle() + "_object.jpg"), chart, 500, 300);
                        response.getWriter().println("<div style=\"background-image: url(" + pathFile + process.getTitle() + "_object.jpg); height: 300px; width: 500px; border: 0px solid black;\"> </div>");
                    }catch (Exception e) {
                        log.error(e);
                    }
                }
            }
        }
    }
}
