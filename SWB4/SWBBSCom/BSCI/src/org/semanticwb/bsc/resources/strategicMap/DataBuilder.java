/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.resources.strategicMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.accessory.Differentiator;
import org.semanticwb.bsc.accessory.DifferentiatorGroup;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.element.Objective;
import org.semanticwb.bsc.element.Perspective;
import org.semanticwb.bsc.element.Theme;
import org.semanticwb.model.Resource;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;

/**
 * Clase que permite obtener los datos de un mapa estrat&eacute;gico. Obtiene la
 * información y la entrega en un objeto de tipo JSON
 *
 * @author Martha Elvia Jim&eacute;nez Salgado
 * @version %I%, %G%
 * @since 1.0
 *
 */
public class DataBuilder {

    private static Logger log = SWBUtils.getLogger(DataBuilder.class);

    /**
     * Se encarga de obtener la estructura de datos de un BalanceScoreCard
     *
     * @param base Elemento de tipo Resource, utilizado para obtener las
     * configuraciones del recurso
     * @param period Elemento de tipo Period, se refiere al periodo seleccionado
     * actualmente en el sitio BSC
     * @param bsc Elemento de tipo BalanScoreCard, se refiere al BSC
     * seleccionado actualmente
     * @return objeto de tipo {
     * @JSONArray} que contiene la estructura de datos de una BalanceScoreCard
     */
    public JSONArray getData(Resource base, Period period, BSC bsc) {
        JSONArray mapContainer = new JSONArray();
        JSONArray allPerspectives = new JSONArray();
        JSONObject headers = new JSONObject();
        try {
            headers.put("headers", getDataHeaders(bsc, base));
        } catch (JSONException ex) {
            log.error("Error getHeaders: " + ex);
        }
        Iterator itPersp = bsc.listPerspectives();
        if (itPersp.hasNext()) {
            itPersp = BSCUtils.sortObjSortable(itPersp).listIterator();
            while (itPersp.hasNext()) {
                Perspective perspective = (Perspective) itPersp.next();
                allPerspectives.put(getDataPerspective(period, base, perspective));
            }
        }
        mapContainer.put(headers);
        mapContainer.put(allPerspectives);
        return mapContainer;
    }

    /**
     * Obtiene la cabecera de un BSC, es decir obtiene la visi&oacute;n,
     * misi&acute;n y logotipo
     *
     * @param bsc Elemento de tipo BalanScoreCard, se refiere al BSC
     * seleccionado actualmente
     * @return objeto de tipo {@code JSONObject} con los datos de la cabera de
     * un BSC mision, vision y logo
     */
    private JSONObject getDataHeaders(BSC bsc, Resource base) {
        JSONObject headers = new JSONObject();
        try {
            String logo = "";
            if ((bsc.getLogo() != null) && (bsc.getLogo().trim().length() > 0)) {
                logo = SWBPortal.getWebWorkPath() + bsc.getWorkPath() + "/" + bsc.getLogo();
            }
            String vision = bsc.getVision() == null ? "" : bsc.getVision();
            String mision = bsc.getMission() == null ? "" : bsc.getMission();
            String bg_mision = ((base.getData("bg_mision") == null) || 
                    (base.getData("bg_mision").equals(""))) ? "white" : 
                    base.getData("bg_mision");
            String ty_mision = ((base.getData("ty_mision") == null) || 
                    (base.getData("ty_mision").equals(""))) ? "#666" : 
                    base.getData("ty_mision");
            String ty_vision = ((base.getData("ty_vision") == null) || 
                    (base.getData("ty_vision").equals(""))) ? "#666" : 
                    base.getData("ty_vision");
            String bg_vision = (((base.getData("bg_vision") == null) || (
                    base.getData("bg_vision").equals("")))) ? "white" : 
                    base.getData("bg_vision");
            headers.put("logo", logo);
            headers.put("vision", vision);
            headers.put("mision", mision);
            headers.put("bg_mision", bg_mision);
            headers.put("ty_mision", ty_mision);
            headers.put("ty_vision", ty_vision);
            headers.put("bg_vision", bg_vision);
        } catch (JSONException ex) {
            log.error("Failed to get data from bsc " + ex);
        }
        return headers;
    }

    /**
     * Obtiene los datos de una perspectiva de un BSC
     *
     * @param period Elemento de tipo Period, se refiere al periodo seleccionado
     * actualmente en el sitio BSC
     * @param base Elemento de tipo Resource, utilizado para obtener las
     * configuraciones del recurso
     * @param perspective objeto de tipo {@code Perspective} utilizado para
     * obtener la informaci&oacute;n de una perspectiva
     * @return objeto de tipo {@code JSONObject} con la estructura de los datos
     * de una perspectiva
     */
    private JSONObject getDataPerspective(Period period, Resource base, Perspective perspective) {
        JSONObject dataPerspective = new JSONObject();
        JSONArray arrayThemes = new JSONArray();
        JSONArray arrayDifferentiatorGroup = new JSONArray();
        int countTheme = 0;
        int countDiffGroup = 0;
        String index = perspective.getIndex() + "";
        String idResour = base.getId();
        boolean showHorizontal = (base.getData("perspective" + idResour + perspective.getId()) == null)
                ? false : true;
        String title = perspective.getTitle();
        int sizeTitle = ((base.getData("amountPerspective") == null)
                || (base.getData("amountPerspective").trim().length() == 0)
                || (base.getData("amountPerspective").equals("")))
                ? 200 : Integer.parseInt(base.getData("amountPerspective"));
        title = SWBUtils.TEXT.cropText(title, sizeTitle);
        String colorText = (base.getData("ty_perspective" + perspective.getId())
                == null) ? "#666" : base.getData("ty_perspective" + perspective.getId());
        Iterator itTheme = perspective.listThemes();

        if (itTheme.hasNext()) {
            itTheme = BSCUtils.sortObjSortable(itTheme).listIterator();
            while (itTheme.hasNext()) {
                Theme theme = (Theme) itTheme.next();
                JSONObject dataTheme = getDataTheme(period, base, theme);
                try {
                    if (dataTheme.has("countObjectives") && dataTheme.getInt("countObjectives") > 0) {
                        arrayThemes.put(dataTheme);
                        countTheme++;
                    }
                } catch (JSONException ex) {
                    log.error("Exception try get countObjectives (DataBuilder): " + ex);
                }
            }
        }

        Iterator<DifferentiatorGroup> itDifferGroup = perspective.listDifferentiatorGroups();
        if (itDifferGroup.hasNext()) {
            while (itDifferGroup.hasNext()) {
                DifferentiatorGroup differentiatorGroup = itDifferGroup.next();
                if (differentiatorGroup.isActive()) {
                    JSONObject dataDifferGroup = getDataDifferentiatorGroup(base, differentiatorGroup);
                    try {
                        if (dataDifferGroup.getInt("countDifferentiator") > 0) {
                            arrayDifferentiatorGroup.put(dataDifferGroup);
                            countDiffGroup++;
                        }
                    } catch (JSONException ex) {
                        log.error("Exception try get countDifferentiator: " + ex);
                    }
                }
            }
        }
        try {
            dataPerspective.put("index", index);
            dataPerspective.put("id", perspective.getId());
            dataPerspective.put("showHorizontal", showHorizontal);
            dataPerspective.put("title", title);
            dataPerspective.put("colorText", colorText);
            dataPerspective.put("arrayThemes", arrayThemes);
            dataPerspective.put("countTheme", countTheme);
            dataPerspective.put("arrayDifferentiatorGroup", arrayDifferentiatorGroup);
            dataPerspective.put("countDiffGroup", countDiffGroup);
        } catch (JSONException ex) {
            log.error("Exception to try data theme: " + ex);
        }
        return dataPerspective;
    }

    /**
     * Obtiene los datos de un tema para construir una estructura de
     * informaci&oacute;n
     *
     * @param period Elemento de tipo Period, se refiere al periodo seleccionado
     * actualmente en el sitio BSC
     * @param base Elemento de tipo Resource, utilizado para obtener las
     * configuraciones del recurso
     * @param theme objeto de tipo {@code Theme} utilizado para obtener la
     * informaci&oacute;n de un tema
     * @return objeto de tipo {@code JSONObject} con la estructura de los datos
     * de un tema
     */
    private JSONObject getDataTheme(Period period, Resource base, Theme theme) {
        JSONObject dataTheme = new JSONObject();
        JSONArray arrayObjectives = new JSONArray();
        Iterator itObjs = theme.listObjectives();
        itObjs = getObjetivesByPeriod(itObjs, period);
        List itObjectives = null;
        if (itObjs.hasNext()) {
            itObjectives = BSCUtils.sortObjSortable(itObjs);
            itObjs = itObjectives.listIterator();
            int countObjectives = 0;
            while (itObjs.hasNext()) {
                Objective objective = (Objective) itObjs.next();
                arrayObjectives.put(getDataObjective(base, objective, period));
                countObjectives++;
            }
            String colorText = (base.getData("ty_theme_" + theme.getId())
                    == null) ? "black" : base.getData("ty_theme_"
                    + theme.getId());
            String bgcolor = (base.getData("bg_theme_" + theme.getId()) == null)
                    ? "white" : base.getData("bg_theme_" + theme.getId());
            String title = theme.getTitle();
            int sizeTitle = ((base.getData("amountTheme") == null)
                    || (base.getData("amountTheme").trim().length() == 0))
                    ? 200 : Integer.parseInt(base.getData("amountTheme"));
            title = SWBUtils.TEXT.cropText(title, sizeTitle);
            try {
                dataTheme.put("isHidden", theme.isHidden());
                dataTheme.put("colorText", colorText);
                dataTheme.put("bgcolor", bgcolor);
                dataTheme.put("title", title);
                dataTheme.put("index", theme.getIndex() + "");
                dataTheme.put("arrayObjectives", arrayObjectives);
                dataTheme.put("countObjectives", countObjectives);
            } catch (JSONException ex) {
                log.error("Exception try get data theme: " + ex);
            }
        }
        return dataTheme;
    }

    /**
     * Obtiene los datos de un grupo de diferenciadores para construir una
     * estructura de informaci&oacute;n
     *
     * @param base Recurso de SWB utilizado para mostrarlo dentro de la
     * plataforma SWBPortal
     * @param differentiatorGroup objeto de tipo {@code DifferentiatorGroup}
     * utilizado para obtener la informaci&oacute;n de un grupo de
     * diferenciadores
     * @return objeto de tipo {@code JSONObject} con la estructura de los datos
     * de un grupo de diferenciadores
     */
    private JSONObject getDataDifferentiatorGroup(Resource base,
            DifferentiatorGroup differentiatorGroup) {
        JSONObject dataDiffeGroup = new JSONObject();
        JSONArray arrayDifferentiator = new JSONArray();
        Iterator itDiffe = differentiatorGroup.listDifferentiators();
        List itDifferentiator = null;
        if (itDiffe.hasNext()) {
            itDifferentiator = BSCUtils.sortObjSortable(itDiffe);
            itDiffe = itDifferentiator.iterator();
            int countDifferentiator = 0;
            while (itDiffe.hasNext()) {
                Differentiator differentiator = (Differentiator)itDiffe.next();
                if (differentiator.isActive()) {
                    arrayDifferentiator.put(getDataDifferentiator(base, differentiator));
                    countDifferentiator++;
                }
            }
            String colorText = (base.getData("ty_diffG_" + differentiatorGroup.getId())) == null
                     ? "black" : (base.getData("ty_diffG_" + differentiatorGroup.getId()));
            String bgcolor = (base.getData("bg_diffG_" + differentiatorGroup.getId()) == null) 
                    ? "white" : (base.getData("bg_diffG_" + differentiatorGroup.getId()));
            String title = differentiatorGroup.getTitle();
            int sizeTitle = ((base.getData("amountDifferentiator") == null)
                    || ((base.getData("amountDifferentiator") != null)
                    && (base.getData("amountDifferentiator").trim().length() == 0)))
                    ? 200 : Integer.parseInt(base.getData("amountDifferentiator"));
            title = SWBUtils.TEXT.cropText(title, sizeTitle);
            try {
                dataDiffeGroup.put("colorText", colorText);
                dataDiffeGroup.put("bgcolor", bgcolor);
                dataDiffeGroup.put("title", title);
                dataDiffeGroup.put("arrayDifferentiator", arrayDifferentiator);
                dataDiffeGroup.put("countDifferentiator", countDifferentiator);
            } catch (JSONException ex) {
                log.error("Error try to get data DifferentiatorGroup: " + ex);
            }
        }
        return dataDiffeGroup;
    }

    /**
     * Obtiene los datos de un objetivo para construir una estructura de
     * informaci&oacute;n
     *
     * @param base Elemento de tipo Resource, utilizado para obtener las
     * configuraciones del recurso
     * @param objective objeto de tipo {@code Objective} utilizado para obtener
     * la informaci&oacute;n de un objetivo
     * @param period Elemento de tipo Period, se refiere al periodo seleccionado
     * actualmente en el sitio BSC
     * @return objeto de tipo {@code JSONObject} con la estructura de los datos
     * de un objetivo
     */
    private JSONObject getDataObjective(Resource base, Objective objective, Period period) {
        JSONObject dataObjective = new JSONObject();
        String title = objective.getTitle();
        int sizeTitle = ((base.getData("amountObjective") == null)
                || (base.getData("amountObjective").trim().length() == 0))
                ? 200 : Integer.parseInt(base.getData("amountObjective"));
        title = SWBUtils.TEXT.cropText(title, sizeTitle);
        String prefix = (objective.getPrefix() == null) ? "" : objective.getPrefix();
        WebSite ws = base.getWebSite();
        WebPage wp = ws.getWebPage(Objective.class.getSimpleName());
        String url = "#";
        if(wp != null) {
            url = wp.getUrl() + "?suri=" + objective.getEncodedURI();
        }
        try {
            dataObjective.put("title", title);
            dataObjective.put("sponsor", BSCUtils.getSponsor(objective.getSponsor()));
            dataObjective.put("icon", BSCUtils.getIconPeriodStatus(period, objective));
            dataObjective.put("periodicity", BSCUtils.getFrequencyObj(objective.getPeriodicity()));
            dataObjective.put("prefix", prefix);
            dataObjective.put("url", url);
            dataObjective.put("index", objective.getIndex() + "");
            dataObjective.put("id", objective.getId());
        } catch (JSONException ex) {
            log.error("Error try get data Objective: " + ex);
        }
        return dataObjective;
    }

    /**
     * Obtiene los datos de un diferenciaador para construir una estructura de
     * informaci&oacute;n
     *
     * @param base Elemento de tipo Resource, utilizado para obtener las
     * configuraciones del recurso
     * @param differentiator objeto de tipo {@code Differentiator} utilizado
     * para obtener la informaci&oacute;n de un diferenciador
     * @return objeto de tipo {@code JSONObject} con la estructura de los datos
     * de un diferenciador
     */
    private JSONObject getDataDifferentiator(Resource base, Differentiator differentiator) {
        JSONObject dataDifferentiator = new JSONObject();
        String title = differentiator.getTitle();
        int sizeTitle = ((base.getData("amountDifferentiator") == null)
                || (base.getData("amountDifferentiator").trim().length() == 0))
                ? 200 : Integer.parseInt(base.getData("amountDifferentiator"));
        title = SWBUtils.TEXT.cropText(title, sizeTitle);
        try {
            dataDifferentiator.put("title", title);
            dataDifferentiator.put("index", differentiator.getIndex());
        } catch (JSONException ex) {
            log.error("Error to get data Differentiator: " + ex);
        }
        return dataDifferentiator;
    }

    /**
     * Se encarga de validar que los objetivos tengan asociado el periodo actual
     *
     * @param itObjectives Iterador con los objetivos a validar
     * @param period Elemento de tipo Period, se refiere al periodo seleccionado
     * actualmente en el sitio BSC
     * @return Objeto de tipo {
     * @codeIterator} con los objetivos validados para el periodo
     */
    private Iterator getObjetivesByPeriod(Iterator itObjectives, Period period) {
        ArrayList list = new ArrayList();
        while (itObjectives.hasNext()) {
            Objective objective = (Objective) itObjectives.next();
            if (objective.hasPeriod(period)) {
                list.add(objective);
            }
        }
        return list.iterator();
    }
}
