package org.semanticwb.bsc.resources.reports;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.BSC;
import static org.semanticwb.bsc.PDFExportable.Mode_StreamPDF;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.element.Indicator;
import org.semanticwb.bsc.element.Objective;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author ana.garcias
 */
public class ExportScoreCard extends GenericResource {
    private static Logger log = SWBUtils.getLogger(ExportScoreCard.class);
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        doViewStrategy(request, response, paramRequest);
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (Mode_StreamPDF.equals(paramRequest.getMode())) {
            doGetPDFDocument(request, response, paramRequest);
        }else {
            super.processRequest(request, response, paramRequest);
        }
    }
    
    public void doGetPDFDocument(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        response.setContentType("application/pdf");
        WebSite ws = paramRequest.getWebPage().getWebSite();
        response.setHeader("Content-Disposition", "attachment; filename=\""+ws.getId()+".pdf\"");

        if (ws instanceof BSC) {
            String objective = "";
            String theme = "";
            String perspective = "";
            String indicator = "";
            String scoreCard = "";

            LinkedList<ParamsScoreCard> lista = new LinkedList<>();
            Period period = getPeriod(request);
            scoreCard = ws.getTitle();
            JasperTemplates jasperLogo = JasperTemplates.LOGO;
            Iterator<Objective> itObje = Objective.ClassMgr.listObjectiveByPeriod(period, ws);
            int exp = 0;
            while (itObje.hasNext()) {
                Objective ob = itObje.next();
                if (ob.isActive()) {
                    exp = 1;
                    boolean hasIndicator = false;
                    objective = ob.getTitle();
                    theme = ob.getTheme().getTitle();
                    perspective = ob.getTheme().getPerspective().getTitle();
                    //Saca los indicadores del periodo y evalua si pertenecen al mismo objetivo
                    Iterator<Indicator> itIndPeriod = Indicator.ClassMgr.listIndicatorByPeriod(period, ws);
                    int count = 0;

                    while (itIndPeriod.hasNext()) {
                        Indicator indic = itIndPeriod.next();
                        if (indic.getObjective().equals(ob) && indic.isActive()) {
                            hasIndicator = true;
                            indicator = indic.getTitle();
                            if (count == 0) {
                                lista.add(new ParamsScoreCard(perspective, theme, objective, indicator, scoreCard, "", this.getClass().getResourceAsStream(jasperLogo.getTemplatePath())));
                            } else {
                                lista.add(new ParamsScoreCard("", "", "", indicator, scoreCard, "", this.getClass().getResourceAsStream(jasperLogo.getTemplatePath())));
                            }
                            count++;
                        }
                    }
                    if (hasIndicator == false) {
                        lista.add(new ParamsScoreCard(perspective, theme, objective, "", scoreCard, "", this.getClass().getResourceAsStream(jasperLogo.getTemplatePath())));
                    }
                }
            }

            if (exp == 0) {
                lista.add(new ParamsScoreCard("", "", "", "", scoreCard, "No existen objetivos válidos para el periodo seleccionado", this.getClass().getResourceAsStream(jasperLogo.getTemplatePath())));
            }
            try {
                JasperTemplates jasperTemplate = JasperTemplates.SCORECARD;
                InputStream is = getClass().getResourceAsStream(jasperTemplate.getTemplatePath());
                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(is);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, new JRBeanCollectionDataSource(lista));
                JRExporter exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "iso-8859-1");
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
                exporter.exportReport();
            } catch (IOException | JRException e) {
                log.error(e);
            }

        }
    }

    private Period getPeriod(HttpServletRequest request) {
        WebSite ws = getResourceBase().getWebSite();
        Period period = null;
        String pid = (String) request.getSession(true).getAttribute(ws.getId());
        if (Period.ClassMgr.hasPeriod(pid, ws)) {
            period = Period.ClassMgr.getPeriod(pid, ws);
        }
        return period;
    }

    /**
     * Genera el despliegue de la liga que redireccionar&aacute; al recurso que 
     * exporta un Scorecard.
     * 
     * @param request Proporciona informaci&oacute;n de petici&oacute;n HTTP
     * @param response Proporciona funcionalidad especifica HTTP para
     * envi&oacute; en la respuesta
     * @param paramRequest Objeto con el cual se acceden a los objetos de SWB
     * @throws SWBResourceException SWBResourceException Excepti&oacute;n
     * utilizada para recursos de SWB
     * @throws IOException Excepti&oacute;n de IO
     */
    public void doViewStrategy(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("application/pdf; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        
        PrintWriter out = response.getWriter();
        
        String title = paramRequest.getLocaleString("msgPrintPDFDocument");
        SWBResourceURL url = paramRequest.getRenderUrl();
        url.setCallMethod(SWBResourceURL.Call_DIRECT);
        url.setMode(Mode_StreamPDF);
        out.print("<a href=\"" + url + "\" class=\"swb-toolbar-stgy\" title=\""+title+"\">");
        out.print(title);
        out.println("</a>");
    }
}
