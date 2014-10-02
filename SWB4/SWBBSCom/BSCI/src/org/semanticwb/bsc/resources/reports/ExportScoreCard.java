package org.semanticwb.bsc.resources.reports;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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
import org.semanticwb.bsc.Perspective;
import org.semanticwb.bsc.Theme;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.element.Indicator;
import org.semanticwb.bsc.element.Initiative;
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
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    public void doGetPDFDocument(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("application/pdf");
        WebSite ws = paramRequest.getWebPage().getWebSite();
        String lang = paramRequest.getUser().getLanguage();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + ws.getId() + ".pdf\"");

        if (ws instanceof BSC) {
            String titleDoc = paramRequest.getLocaleString("titleDoc");
            String perspectiveHeader= paramRequest.getLocaleString("perspectiveHeader");
            String themeHeader= paramRequest.getLocaleString("themeHeader");
            String objHeader= paramRequest.getLocaleString("objHeader");
            String indicHeader= paramRequest.getLocaleString("indicHeader");
            String initHeader= paramRequest.getLocaleString("initHeader");
            String objectiveTitle = "";
            String themeTitle = "";
            String perspectiveTitle = "";
            String indicatorTitle = "";
            String scoreCard = "";
            String periodTitle = "";
            String initiativeTitle = "";
            String msg = "";
            boolean hasIndicator = false;
            boolean hasInitiative = false;

            LinkedList<ParamsScoreCard> lista = new LinkedList();
            Period period = getPeriod(request);
            periodTitle = period.getTitle();
            scoreCard = ws.getTitle();
            JasperTemplates jasperLogo = JasperTemplates.LOGO;
            int exp = 0;

            Iterator<Perspective> itPersp = Perspective.ClassMgr.listPerspectives(ws);
            while (itPersp.hasNext()) {
                Perspective perspective = itPersp.next();
                //si la perspectiva es activa, trae sus temas
                if (perspective.isActive()) {
                    exp = 1;
                    perspectiveTitle = perspective.getTitle();
                    List<Theme> themes = perspective.listValidThemes();
                    if (!themes.isEmpty()) {
                        Collections.sort(themes);
                        for (Theme theme : themes) {
                            themeTitle = theme.getTitle();
                            //El tema trae sus objetivos
                            List<Objective> objectives = theme.listValidObjectives();
                            Collections.sort(objectives);
                            for (Objective obj : objectives) { //Recorre los objetivos asignados
                                if (obj.hasPeriod(period)) {
                                    objectiveTitle = obj.getTitle();
                                    List<Indicator> indicators = obj.listValidIndicators(); //Trae la lista de indicadores
                                    if (!indicators.isEmpty()) {
                                        int count = 0;
                                        Iterator<Indicator> itIndicator = indicators.iterator();
                                        while (itIndicator.hasNext()) { //Recorre cada indicador
                                            hasIndicator = false;
                                            hasInitiative = false;
                                            Indicator indicator = itIndicator.next();
                                            if (indicator.hasPeriod(period)) { //Valida que pertenezcan al mismo periodo
                                                hasIndicator = true;
                                                indicatorTitle = indicator.getTitle();
                                                Iterator<Initiative> itInit = indicator.listInitiatives(); //El indicador trae sus iniciativas
                                                int countInit = 0;
                                                while (itInit.hasNext()) {
                                                    Initiative initiative = itInit.next();
                                                    if (initiative.isActive() && initiative.hasPeriod(period)) {
                                                        hasInitiative = true;
                                                        initiativeTitle = initiative.getTitle();
                                                        if (countInit == 0) {
                                                            lista.add(new ParamsScoreCard(titleDoc,perspectiveHeader,themeHeader,objHeader,indicHeader,initHeader,perspectiveTitle, themeTitle, objectiveTitle, indicatorTitle, initiativeTitle, scoreCard, periodTitle, msg, this.getClass().getResourceAsStream(jasperLogo.getTemplatePath())));
                                                        } else {
                                                            lista.add(new ParamsScoreCard(titleDoc,perspectiveHeader,themeHeader,objHeader,indicHeader,initHeader,"", "", "", "", initiativeTitle, scoreCard, periodTitle, msg, this.getClass().getResourceAsStream(jasperLogo.getTemplatePath())));

                                                        }
                                                        countInit++;
                                                    }
                                                }
                                                if (hasInitiative == false) {
                                                    if (count == 0) {
                                                        lista.add(new ParamsScoreCard(titleDoc,perspectiveHeader,themeHeader,objHeader,indicHeader,initHeader,perspectiveTitle, themeTitle, objectiveTitle, indicatorTitle, "", scoreCard, periodTitle, msg, this.getClass().getResourceAsStream(jasperLogo.getTemplatePath())));
                                                    } else {
                                                        lista.add(new ParamsScoreCard(titleDoc,perspectiveHeader,themeHeader,objHeader,indicHeader,initHeader,"", "", "", indicatorTitle, "", scoreCard, periodTitle, msg, this.getClass().getResourceAsStream(jasperLogo.getTemplatePath())));
                                                    }
                                                    count++;
                                                }
                                            }

                                        }
                                        if (hasIndicator == false) {
                                            lista.add(new ParamsScoreCard(titleDoc,perspectiveHeader,themeHeader,objHeader,indicHeader,initHeader,perspectiveTitle, themeTitle, objectiveTitle, "", "", scoreCard, periodTitle, msg, this.getClass().getResourceAsStream(jasperLogo.getTemplatePath())));
                                        }
                                    } // si no tiene indicadores, manda la lista hasta los objetivos
                                    else {
                                        lista.add(new ParamsScoreCard(titleDoc,perspectiveHeader,themeHeader,objHeader,indicHeader,initHeader,perspectiveTitle, themeTitle, objectiveTitle, "", "", scoreCard, periodTitle, msg, this.getClass().getResourceAsStream(jasperLogo.getTemplatePath())));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (exp == 0) {
                msg = paramRequest.getLocaleString("msg");
                lista.add(new ParamsScoreCard(titleDoc,perspectiveHeader,themeHeader,objHeader,indicHeader,initHeader,"", "", "", "", "", scoreCard, periodTitle, msg, this.getClass().getResourceAsStream(jasperLogo.getTemplatePath())));

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
            } catch (IOException e) {
                log.error(e);
            } catch (JRException e) {
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
        SWBResourceURL url = paramRequest.getRenderUrl();
        url.setCallMethod(SWBResourceURL.Call_DIRECT);
        url.setMode(Mode_StreamPDF);
        out.print("<button type=\"button\" class=\"btn btn-default\" onclick=\"location.href='" + url + "'\"><span class=\"glyphicon glyphicon-th-large\"></span></button>");
    }
}
