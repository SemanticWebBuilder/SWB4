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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.ProcessSite;
import org.semanticwb.process.model.SWBProcessMgr;
import org.semanticwb.process.model.ProcessWebPage;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBPortal;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.process.kpi.CaseResponseTime;
import org.semanticwb.portal.api.SWBResourceException;

import java.io.File;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;


/**
 *
 * @author Sergio Téllez
 */
public class ResponseCase extends GenericResource {

    private static Logger log = SWBUtils.getLogger(ResponseCase.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        /*CaseResponseTime crt = new CaseResponseTime();
        Iterator isites = ProcessSite.ClassMgr.listProcessSites();
        response.getWriter().print("<div class=\"swbform\">\n");
        response.getWriter().print("  <fieldset>\n");
        while (isites.hasNext()) {
            ProcessSite site = (ProcessSite)isites.next();
            Iterator <org.semanticwb.process.model.ProcessWebPage>itProcessWebPages = ProcessWebPage.ClassMgr.listProcessWebPages(site);
            while(itProcessWebPages.hasNext()) {
                ProcessWebPage pwp = (ProcessWebPage) itProcessWebPages.next();
                org.semanticwb.process.model.Process process = SWBProcessMgr.getProcess(pwp);
                response.getWriter().println("<ul><li>Tiempo promedio de ejecución de todas las instancias del proceso " + process.getTitle() + ": " + crt.getAverageProcessInstances(process) + " milisegundos</li>");
                response.getWriter().println("<li>Tiempo mímimo de ejecución de todas las instancias del proceso " + process.getTitle() + ": " + crt.getMinimumProcessInstance(process) + " milisegundos</li>");
                response.getWriter().println("<li>Tiempo máximo de ejecución de todas las instancias del proceso " + process.getTitle() + ": " + crt.getMaximumProcessInstance(process) + " milisegundos</li></ul>");
            }
        }
        response.getWriter().print("  </fieldset>\n");
        response.getWriter().print("</div>\n");*/
        doGraph(request, response, paramRequest);
    }

    public void doGraph(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        CaseResponseTime crt = new CaseResponseTime();
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
                if (crt.getMaximumProcessInstance(process) > 0) {
                    DefaultPieDataset dataCase = new DefaultPieDataset();
                    dataCase.setValue(paramRequest.getLocaleString("minimum"), new Integer((int)crt.getMinimumProcessInstance(process)/1000));
                    dataCase.setValue(paramRequest.getLocaleString("average"), new Integer((int)crt.getAverageProcessInstances(process)/1000));
                    dataCase.setValue(paramRequest.getLocaleString("maximum"), new Integer((int)crt.getMaximumProcessInstance(process)/1000));
                    JFreeChart chart = ChartFactory.createPieChart(process.getTitle(), dataCase, true, true, false);
                    try {
                        ChartUtilities.saveChartAsJPEG(new File(pathFile + process.getTitle() + "_response.jpg"), chart, 500, 300);
                        response.getWriter().println("<div style=\"background-image: url(" + pathFile + process.getTitle() + "_response.jpg); height: 300px; width: 500px; border: 0px solid black;\"> </div>");
                    }catch (Exception e) {
                        log.error(e);
                    }
                }
            }
        }
    }
}