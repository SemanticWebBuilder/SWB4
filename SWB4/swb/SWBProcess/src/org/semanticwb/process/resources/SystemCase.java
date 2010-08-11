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

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.SWBPortal;
import org.semanticwb.process.model.Instance;
import org.semanticwb.process.kpi.CaseCountSys;
import org.semanticwb.process.utils.Restriction;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBResourceException;

import java.io.File;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.GradientPaint;

import java.awt.Color;
import java.awt.BasicStroke;
import org.jfree.data.Range;
import org.jfree.chart.plot.MeterPlot;
import org.jfree.chart.plot.MeterInterval;
import org.jfree.data.general.DefaultValueDataset;

/**
 *
 * @author Sergio Téllez
 */
public class SystemCase extends GenericResource {

    private static Logger log = SWBUtils.getLogger(SystemCase.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        /*CaseCountSys sys = new CaseCountSys();
         *
         *
         * 
        response.getWriter().println("<div class=\"swbform\">\n");
        response.getWriter().println("  <fieldset>\n");
        response.getWriter().println("      <ul><li>Número total de instancias de procesos: " + sys.totalProcessInstance()+"</li>");
        sys.addRestriction(new Restriction(CaseCountSys.STATUS,String.valueOf(Instance.STATUS_PROCESSING),null));
        response.getWriter().println("      <li>Número total de instancias de procesos en ejecución: " + sys.totalProcessInstance()+"</li>");
        sys.clear();
        sys.addRestriction(new Restriction(CaseCountSys.USER,"admin",null));
        response.getWriter().println("     <li>Número total de instancias de procesos del usuario admin: " + sys.totalProcessInstance()+"</li></ul>");
        response.getWriter().println("  </fieldset>\n");
        response.getWriter().println("</div>\n");*/
        doMeter(request, response, paramRequest);
    }

    public void doGraph(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        /*int total, processing, closed, others = 0;
        CaseCountSys sys = new CaseCountSys();
        DefaultPieDataset dataCase = new DefaultPieDataset();
        total = sys.totalProcessInstance();
        sys.addRestriction(new Restriction(CaseCountSys.STATUS,String.valueOf(Instance.STATUS_PROCESSING),null));
        processing = sys.totalProcessInstance();
        dataCase.setValue(paramRequest.getLocaleString("STATUS_PROCESSING"), new Integer(processing));
        sys.clear();
        sys.addRestriction(new Restriction(CaseCountSys.STATUS,String.valueOf(Instance.STATUS_CLOSED),null));
        closed = sys.totalProcessInstance();
        dataCase.setValue(paramRequest.getLocaleString("STATUS_CLOSED"), new Integer(closed));
        others = total - processing - closed;
        dataCase.setValue(paramRequest.getLocaleString("STATUS_ABORTED"), new Integer(others));
        JFreeChart chart = ChartFactory.createPieChart(paramRequest.getLocaleString("title"), dataCase, true, true, false);
        try {
            String pathFile = SWBPortal.getWorkPath() + getResourceBase().getWorkPath() + "/images";
            File filex = new File(pathFile);
            if (!filex.exists())
                filex.mkdirs();
            ChartUtilities.saveChartAsJPEG(new File(pathFile + "/systemcase.jpg"), chart, 500, 300);
            //ChartUtilities.saveChartAsPNG(new File(pathFile + "/systemcase.png"), chart, 500, 300);
            response.getWriter().println("<div style=\"background-image: url(" + pathFile + "/systemcase.jpg); height: 300px; width: 500px; border: 0px solid black;\"> </div>");
        } catch (Exception e) {
            log.error(e);
        }*/
    }

    public void doMeter(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        int total, processing, closed, aborted = 0;
        CaseCountSys sys = new CaseCountSys();
        total = sys.totalProcessInstance();
        sys.addRestriction(new Restriction(CaseCountSys.STATUS,String.valueOf(Instance.STATUS_PROCESSING),null));
        processing = sys.totalProcessInstance();
        sys.clear();
        sys.addRestriction(new Restriction(CaseCountSys.STATUS,String.valueOf(Instance.STATUS_CLOSED),null));
        closed = sys.totalProcessInstance();
        aborted = total - (closed + processing);
        DefaultValueDataset dataset = new DefaultValueDataset(total);
        MeterPlot plot = new MeterPlot(dataset);
        plot.setUnits(paramRequest.getLocaleString("title"));
        plot.setRange(new Range(0, total));
        plot.addInterval(new MeterInterval(paramRequest.getLocaleString("STATUS_CLOSED"), new Range(0, closed), Color.lightGray, new BasicStroke(2.0f), new Color(72, 72, 255, 128)));
        plot.addInterval(new MeterInterval(paramRequest.getLocaleString("STATUS_PROCESSING"), new Range(closed + aborted, total), Color.lightGray, new BasicStroke(2.0f), new Color(255, 0, 0, 128)));
        plot.addInterval(new MeterInterval(paramRequest.getLocaleString("STATUS_ABORTED"), new Range(closed, closed + aborted), Color.lightGray, new BasicStroke(2.0f), new Color(64, 255, 64, 128)));
        plot.setDialOutlinePaint(Color.white);
        plot.setDialBackgroundPaint(new Color(172, 188, 244, 128));
        JFreeChart chart = new JFreeChart(paramRequest.getLocaleString("title"), JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        chart.setBackgroundPaint(new GradientPaint(0, 0, Color.white, 0, 1000, Color.blue));
        try {
            String pathFile = SWBPortal.getWorkPath() + getResourceBase().getWorkPath() + "/images";
            File filex = new File(pathFile);
            if (!filex.exists())
                filex.mkdirs();
            ChartUtilities.saveChartAsPNG(new File(pathFile + "/systemcase.png"), chart, 500, 300);
            response.getWriter().println("<div style=\"background-image: url(" + pathFile + "/systemcase.png); height: 300px; width: 500px; border: 0px solid black;\"> </div>");
            response.getWriter().println("<div style=\"background-image: url(/swbadmin/images/systemft.jpg); height: 27px; width: 500px; border: 0px solid black;\"> </div>");
        } catch (Exception e) {
            log.error(e);
        }
    }
}