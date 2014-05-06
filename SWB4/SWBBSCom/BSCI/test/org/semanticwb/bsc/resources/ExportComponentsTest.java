/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.resources;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.SWBPlatform;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.accessory.State;
import org.semanticwb.bsc.element.Indicator;
import org.semanticwb.bsc.tracing.Series;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;

/**
 *
 * @author martha.jimenez
 */
public class ExportComponentsTest {

    final private String modelId = "InfotecPEMP2";
    final private String userGroupId = "Actualizador";
    final private String suri = "http://www.InfotecPEMP2.swb#bsc_Indicator:58";
    final private String serie1 = "http://www.InfotecPEMP2.swb#bsc_Series:190";
    final private String serie2 = "http://www.InfotecPEMP2.swb#bsc_Series:191";
    final private String serie3 = "http://www.InfotecPEMP2.swb#bsc_Series:192";
    final private String serie4 = "http://www.InfotecPEMP2.swb#bsc_Series:197";
    final private String period1 = "http://www.InfotecPEMP2.swb#bsc_Period:12";
    final private String period2 = "http://www.InfotecPEMP2.swb#bsc_Period:14";
    User user;

    @BeforeClass
    public static void setUpClass() throws Exception {
        SWBPlatform plat = SWBPlatform.createInstance();
        plat.setPersistenceType(SWBPlatform.PRESIST_TYPE_SWBTRIPLESTORE);
        SWBPlatform.getSemanticMgr().initializeDB();

        SWBPlatform.getSemanticMgr().addBaseOntology("C:/Desarrollos5/SWB4/SWBBSCom/build/web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology("C:/Desarrollos5/SWB4/SWBBSCom/build/web/WEB-INF/owl/swb_rep.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology("C:/Desarrollos5/SWB4/SWBBSCom/build/web/WEB-INF/owl/ext/bscom.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        SWBPlatform.getSemanticMgr().loadDBModels();
        SWBPlatform.getSemanticMgr().getOntology().rebind();
    }

    public void setUpSessionUser(BSC model) {
        Iterator it = model.getUserRepository().listUsers();
        UserGroup userGroup = UserGroup.ClassMgr.getUserGroup(userGroupId, model);
        while (it.hasNext()) {
            user = (User) it.next();
            if (user.hasUserGroup(userGroup)) {
                SWBContext.setSessionUser(user);
                break;
            }
        }
    }

    @Test
    public void getComponentGraphGeneration() {
        StringBuilder sb = new StringBuilder();
        WebSite ws = WebSite.ClassMgr.getWebSite(modelId);
        setUpSessionUser((BSC) ws);
        if (suri == null) {
            sb.append("No se detect&oacute ning&uacute;n objeto sem&aacute;ntico!");
        }
        final String lang = "es";
        SemanticObject sobj = SemanticObject.getSemanticObject(suri);
        if (sobj != null && (sobj.getGenericInstance() instanceof Indicator)) {
            Indicator indicator = (Indicator) sobj.getGenericInstance();
            Series star = indicator.getStar();
            if (star != null) {
                Iterator<Series> serieses = getListSeries();
                if (serieses != null) {

                    Iterator periods = listPeriods();
                    sb.append("<table class=\"detail data-table\">");
                    sb.append("<thead>");
                    sb.append("<tr>");
                    sb.append("<th>Periodo</th>");
                    sb.append("<th>Estado</th>");
                    while (serieses.hasNext()) {
                        Series series = serieses.next();
                        sb.append("<th>");

                        sb.append((series.getTitle(lang) == null ? series.getTitle()
                                : series.getTitle(lang)));
                        sb.append("</th>");
                    }
                    sb.append("</tr>");
                    sb.append("</thead>");
                    sb.append("<tbody>");
                    while (periods.hasNext()) {
                        Period period = (Period) periods.next();
                        sb.append("<tr>");
                        sb.append("<td>");
                        sb.append(period.getTitle());
                        sb.append("</td>");
                        sb.append("<td>");
                        if (star.getMeasure(period) != null) {
                            State state = star.getMeasure(period).getEvaluation().
                                    getStatus();
                            if (state == null) {
                                state = indicator.getMinimumState();
                                star.getMeasure(period).getEvaluation().setStatus(state);
                            }
                            String title = "Meta No alcanzada";//state.getTitle(lang) == null ? state.getTitle()
                                    //: state.getTitle(lang);
                            sb.append("<span class=\"");
                            sb.append("state-undefined");
                            //sb.append((state.getIconClass() == null ? "state-undefined"
                            //        : state.getIconClass()));
                            sb.append("\">");
                            sb.append(title);
                            sb.append("</span>");
                        } else {
                            sb.append("--");
                        }
                        sb.append("</td>");
                        while (serieses.hasNext()) {
                            Series series = serieses.next();
                            sb.append("<td>");
                            String value = series.getMeasure(period) == null ? "--"
                                    : series.getFormatter().format(series.getMeasure(period).
                                    getValue());
                            sb.append(value);
                            sb.append("</td>");
                        }
                        sb.append("</tr>");
                    }
                    sb.append("</tbody>");
                    sb.append("</table>");
                }
            } else {
                sb.append("No hay STAR");
            }
        } else {
            sb.append("No hay indicador");
        }
        System.out.println(sb.toString());
    }

    @Test
    public void getComponentDataTableResource() {
        StringBuilder sb = new StringBuilder();
        try {
            String destpath = SWBPlatform.getContextPath() + "/work/models/"
                    + modelId
                    + "/graphics.jpg";

            sb.append("<p><img src=\"");
            sb.append(destpath);
            sb.append("\" alt=\"graphics\"/></p>");
        } catch (Exception ex) {
            System.out.println("Error create image: " + ex);
        }
        System.out.println(sb.toString());

    }

    private Iterator getListSeries() {
        List listSeries = new ArrayList();
        SemanticObject so = SemanticObject.getSemanticObject(serie1);
        Series serie = (Series) so.createGenericInstance();
        listSeries.add(serie);
        so = SemanticObject.getSemanticObject(serie2);
        serie = (Series) so.createGenericInstance();
        listSeries.add(serie);
        so = SemanticObject.getSemanticObject(serie3);
        serie = (Series) so.createGenericInstance();
        listSeries.add(serie);
        so = SemanticObject.getSemanticObject(serie4);
        serie = (Series) so.createGenericInstance();
        listSeries.add(serie);
        return listSeries.iterator();
    }
    
    private Iterator listPeriods() {
        List listPeriodIt = new ArrayList();
        SemanticObject so = SemanticObject.getSemanticObject(period1);
        Period period = (Period) so.createGenericInstance();
        listPeriodIt.add(period);
        so = SemanticObject.getSemanticObject(period2);
        period = (Period) so.createGenericInstance();
        listPeriodIt.add(period);
        return listPeriodIt.iterator();
    }
}
