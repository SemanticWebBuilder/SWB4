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

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBPortal;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.Instance;
import org.semanticwb.process.model.ProcessSite;
import org.semanticwb.process.utils.Restriction;
import org.semanticwb.process.kpi.CaseCountSys;
import org.semanticwb.process.kpi.ProcessCaseCount;

import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.GenericResource;
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
public class ProcessCase extends GenericResource {

    private static Logger log = SWBUtils.getLogger(ProcessCase.class);
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        /*Iterator isites = ProcessSite.ClassMgr.listProcessSites();
        response.getWriter().print("<div class=\"swbform\">\n");
        response.getWriter().print("  <fieldset>\n");
        while (isites.hasNext()) {
            ProcessSite site = (ProcessSite)isites.next();
            Iterator<Process> it = site.listProcesses();
            while (it.hasNext()) {
                Process process = it.next();
                ProcessCaseCount pcc = new ProcessCaseCount(process.getURI());
                response.getWriter().println("<ul>");
                response.getWriter().println("  <li>Número total de instancias del proceso " + process.getTitle() + ": " + pcc.totalProcessInstance() + "</li>");
                pcc.addRestriction(new Restriction(CaseCountSys.STATUS,String.valueOf(Instance.STATUS_PROCESSING),null));
                response.getWriter().println("  <li>Número total de instancias del proceso " + process.getTitle() + " en ejecución: " + pcc.totalProcessInstance()+"</li>");
                new ProcessCaseCount(process.getURI());
                pcc.addRestriction(new Restriction(CaseCountSys.USER,"admin",null));
                response.getWriter().println("  <li>Número total de instancias del proceso " + process.getTitle() + " del usuario admin: " + pcc.totalProcessInstance()+"</li>");
                response.getWriter().println("</ul>");
            }
        }
        response.getWriter().print("  </fieldset>\n");
        response.getWriter().print("</div>\n");*/
        doGraph(request, response, paramRequest);
    }

    public void doGraph(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        int total = 0;
        DefaultPieDataset dataCase = new DefaultPieDataset();
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
                ProcessCaseCount pcc = new ProcessCaseCount(process.getURI());
                total = pcc.totalProcessInstance();
                if (total > 0)
                    dataCase.setValue(process.getTitle(), new Integer(total));
            }
        }
        JFreeChart chart = ChartFactory.createPieChart(paramRequest.getLocaleString("title"), dataCase, true, true, false);
        try {
            ChartUtilities.saveChartAsJPEG(new File(pathFile + "processcase.jpg"), chart, 600, 400);
            response.getWriter().println("<div style=\"background-image: url(" + pathFile + "processcase.jpg); height: 400px; width: 600px; border: 0px solid black;\"> </div>");
        }catch (Exception e) {
            log.error(e);
        }
    }
}