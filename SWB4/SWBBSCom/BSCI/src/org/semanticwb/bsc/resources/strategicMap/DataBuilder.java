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
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.accessory.Differentiator;
import org.semanticwb.bsc.accessory.DifferentiatorGroup;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.element.Objective;
import org.semanticwb.bsc.element.Perspective;
import org.semanticwb.bsc.element.Theme;
import org.semanticwb.model.Resource;

/**
 *
 * @author martha.jimenez Pendiente checar que pasa con los elementos de alto de
 * objetivos y diferenciadores horizontal / vertical
 */
public class DataBuilder {

    private static Logger log = SWBUtils.getLogger(DataBuilder.class);

    public JSONArray getData(Resource base, Period period, BSC bsc) {
        JSONArray mapContainer = new JSONArray();
        JSONArray allPerspectives = new JSONArray();
        JSONObject headers = new JSONObject();
        try {
            headers.put("headers", getDataHeaders(bsc));
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

    private JSONObject getDataHeaders(BSC bsc) {
        JSONObject headers = new JSONObject();
        try {
            String logo = bsc.getLogo() == null ? "" : bsc.getLogo();
            String vision = bsc.getVision() == null ? "" : bsc.getVision();
            String mision = bsc.getMission() == null ? "" : bsc.getMission();
            headers.put("logo", logo);
            headers.put("vision", vision);
            headers.put("mision", mision);
        } catch (JSONException ex) {
            log.error("Failed to get data from bsc " + ex);
        }
        return headers;
    }

    private JSONObject getDataPerspective(Period period, Resource base, Perspective perspective) {
        JSONObject dataPerspective = new JSONObject();
        JSONArray arrayThemes = new JSONArray();
        JSONArray arrayDifferentiatorGroup = new JSONArray();
        int countTheme = 0;
        int countDiffGroup = 0;
        String index = perspective.getIndex() + "";
        String idResour = base.getId();
        boolean showHorizontal = base.getData("perspective" + idResour + perspective.getId()) == null
                ? false : true;
        String title = perspective.getTitle();
        int sizeTitle = (base.getData("amountPerspective") == null
                || base.getData("amountPerspective").trim().length() > 1)
                ? 200 : Integer.parseInt(base.getData("amountPerspective"));
        title = SWBUtils.TEXT.cropText(title, sizeTitle);
        boolean titleHorizontal = base.getData("show_" + perspective.getTitle() + "_"
                + perspective.getId()) == null ? false : true;
        String colorText = base.getData("ty_" + perspective.getTitle() + "_" + perspective.getId())
                == null ? "black" : base.getData("ty_" + perspective.getTitle() + "_"
                + perspective.getId());
        Iterator itTheme = perspective.listThemes();

        if (itTheme.hasNext()) {
            itTheme = BSCUtils.sortObjSortable(itTheme).listIterator();
            while (itTheme.hasNext()) {
                Theme theme = (Theme) itTheme.next();
                JSONObject dataTheme = getDataTheme(period, base, theme);
                try {
                    if (dataTheme.getInt("countObjectives") > 0) {
                        arrayThemes.put(dataTheme);
                        countTheme++;
                    }
                } catch (JSONException ex) {
                    log.error("Exception try get countObjectives: " + ex);
                }
            }
        }

        Iterator<DifferentiatorGroup> itDifferGroup = perspective.listDifferentiatorGroups();
        if (itDifferGroup.hasNext()) {
            while (itDifferGroup.hasNext()) {
                DifferentiatorGroup differentiatorGroup = itDifferGroup.next();
                JSONObject dataDifferGroup = getDataDifferentiatorGroup(period, base, differentiatorGroup);
                if (dataDifferGroup.length() > 0) {
                    arrayDifferentiatorGroup.put(dataDifferGroup);
                    countDiffGroup++;
                }
            }
        }
        try {
            dataPerspective.put("index", index);
            dataPerspective.put("showHorizontal", showHorizontal);
            dataPerspective.put("title", title);
            dataPerspective.put("titleHorizontal", titleHorizontal);
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
            String colorText = base.getData("ty_" + theme.getTitle() + "_" + theme.getId())
                    == null ? "black" : base.getData("ty_" + theme.getTitle() + "_"
                    + theme.getId());
            String bgcolor = base.getData("bg_" + theme.getTitle() + "_" + theme.getId()) == null
                    ? "white" : base.getData("bg_" + theme.getTitle() + "_" + theme.getId());
            String title = theme.getTitle();
            int sizeTitle = (base.getData("amountTheme") == null
                    || base.getData("amountTheme").trim().length() > 1)
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

    private JSONObject getDataDifferentiatorGroup(Period period, Resource base,
            DifferentiatorGroup differentiatorGroup) {
        JSONObject dataDiffeGroup = new JSONObject();
        JSONArray arrayDifferentiator = new JSONArray();
        Iterator<Differentiator> itDiffe = differentiatorGroup.listDifferentiators();
        itDiffe = getDifferentiatorByPeriod(itDiffe, period);
        if (itDiffe.hasNext()) {
            int countDifferentiator = 0;
            while (itDiffe.hasNext()) {
                Differentiator differentiator = itDiffe.next();
                arrayDifferentiator.put(getDataDifferentiator(base, differentiator));
                countDifferentiator++;
            }
            String colorText = base.getData("ty_" + differentiatorGroup.getTitle() + "_"
                    + differentiatorGroup.getId()) == null ? "black" : base.getData("ty_"
                    + differentiatorGroup.getTitle() + "_" + differentiatorGroup.getId());
            String bgcolor = base.getData("bg_" + differentiatorGroup.getTitle() + "_"
                    + differentiatorGroup.getId()) == null ? "white" : base.getData("bg_"
                    + differentiatorGroup.getTitle() + "_" + differentiatorGroup.getId());
            String title = differentiatorGroup.getTitle();
            int sizeTitle = (base.getData("amountDifferentiator") == null
                    || base.getData("amountDifferntiator").trim().length() > 1)
                    ? 200 : Integer.parseInt(base.getData("amountDifferntiator"));
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

    private JSONObject getDataObjective(Resource base, Objective objective, Period period) {
        JSONObject dataObjective = new JSONObject();
        String title = objective.getTitle();
        int sizeTitle = (base.getData("amountObjective") == null
                || base.getData("amountObjective").trim().length() > 1)
                ? 200 : Integer.parseInt(base.getData("amountObjective"));
        title = SWBUtils.TEXT.cropText(title, sizeTitle);
        String prefix = objective.getPrefix() == null ? "" : objective.getPrefix();
        try {
            dataObjective.put("title", title);
            dataObjective.put("sponsor", BSCUtils.getSponsor(objective.getSponsor()));
            dataObjective.put("icon", BSCUtils.getIconPeriodStatus(period, objective));
            dataObjective.put("periodicity", BSCUtils.getFrequencyObj(objective.getPeriodicity()));
            dataObjective.put("prefix", prefix);
            dataObjective.put("url", objective.getURI());
            dataObjective.put("index", objective.getIndex() + "");
        } catch (JSONException ex) {
            log.error("Error try get data Objective: " + ex);
        }
        return dataObjective;
    }

    private JSONObject getDataDifferentiator(Resource base, Differentiator differentiator) {
        JSONObject dataDifferentiator = new JSONObject();
        String title = differentiator.getTitle();
        int sizeTitle = (base.getData("amountDistinctive") == null
                || base.getData("amountDistinctive").trim().length() > 1)
                ? 200 : Integer.parseInt(base.getData("amountDistinctive"));
        title = SWBUtils.TEXT.cropText(title, sizeTitle);
        try {
            dataDifferentiator.put("title", title);
            dataDifferentiator.put("index", differentiator.getIndex());
        } catch (JSONException ex) {
            log.error("Error to get data Differentiator: " + ex);
        }
        return dataDifferentiator;
    }

    private Iterator getDifferentiatorByPeriod(Iterator itDifferentiator, Period period) {
        ArrayList list = new ArrayList();
        while (itDifferentiator.hasNext()) {
            Differentiator differentiator = (Differentiator) itDifferentiator.next();
            if (differentiator.hasPeriod(period)) {
                list.add(differentiator);
            }
        }
        return list.iterator();
    }

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
    /*private List<Perspective> sortPerspective(Iterator<Perspective> itPersp) {
     List perspectives = listValidPerspective(itPersp);
     Collections.sort(perspectives, new Comparator() {
     public int compare(Object o1, Object o2) {
     Perspective p1 = (Perspective) o1;
     Perspective p2 = (Perspective) o2;
     return p1.getIndex() >= p2.getIndex() ? 1 : -1;
     }
     });
     return perspectives;
     }

     public List<Perspective> listValidPerspective(Iterator<Perspective> itPersp) {
     List<Perspective> validPerspectives = null;
     while (itPersp.hasNext()) {
     Perspective perspective = itPersp.next();
     if (perspective.isValid() && perspective.isActive()) {
     validPerspectives.add(itPersp.next());
     }
     }
     return validPerspectives;
     }

     private List<Theme> sortTheme(Iterator<Theme> itThemes) {
     List themes = listValidTheme(itThemes);
     Collections.sort(themes, new Comparator() {
     public int compare(Object o1, Object o2) {
     Theme p1 = (Theme) o1;
     Theme p2 = (Theme) o2;
     return p1.getIndex() >= p2.getIndex() ? 1 : -1;
     }
     });
     return themes;
     }

     public List<Theme> listValidTheme(Iterator<Theme> itTheme) {
     List<Theme> validTheme = null;
     while (itTheme.hasNext()) {
     Theme theme = itTheme.next();
     if (theme.isValid() && theme.isActive()) {
     validTheme.add(itTheme.next());
     }
     }
     return validTheme;
     }*/

}
