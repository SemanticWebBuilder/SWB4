/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.resources.strategicMap;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
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
 * Patrón de diseño: Decorador (Decorador Concreto). Añade responsabilidades al
 * componente.
 *
 * @author Martha Elvia Jim&eacute;nez Salgado
 * @version %I%, %G%
 * @since 1.0
 */
public class CausalArrows extends Decorator {

    private static Logger log = SWBUtils.getLogger(CausalArrows.class);
    private final int divTitle = 5;
    private final int divContainerCols = 92;
    private final int divSizeColumn = 95;
    private double x1 = 0;
    private double x2 = 0;
    private double y1 = 0;
    private double y2 = 0;
    private int countForRelation = 1;
    private int countForPerspective = 1;
    private BigDecimal divTitl = new BigDecimal(divTitle);
    private BigDecimal divContainerCo = new BigDecimal(divContainerCols);
    private BigDecimal divSizeColum = new BigDecimal(divSizeColumn);
    private BigDecimal tam20 = new BigDecimal(20);
    private BigDecimal tam120 = new BigDecimal(120);
    private BigDecimal tam18 = new BigDecimal(18);
    private BigDecimal tam1 = new BigDecimal(1);
    private BigDecimal tam2 = new BigDecimal(2);
    private BigDecimal tam100 = new BigDecimal(100);
    private BigDecimal tam04 = new BigDecimal("0.4");

    /**
     * Construye una instancia de tipo {@code CausalArrows}
     *
     * @param _componente
     */
    public CausalArrows(ComponentMap _componente) {
        super(_componente);
    }

    /**
     * Permite agregar m&aacute;s funcionalidades al Mapa Estrat&eacute;gico
     *
     * @param bsc Elemento de tipo BalanScoreCard, se refiere al BSC
     * seleccionado actualmente
     * @param period Elemento de tipo Period, se refiere al periodo seleccionado
     * actualmente en el sitio BSC
     * @param base Elemento de tipo Resource, utilizado para obtener las
     * configuraciones del recurso
     * @return El elemento base con una funcionalidad extra: Las flechas causa -
     * efecto
     */
    @Override
    public StringBuilder draw(BSC bsc, Period period, Resource base) {
        StringBuilder sb = new StringBuilder();
        sb.append(super.draw(bsc, period, base));
        sb.append(paintArrows(getStructureDataArrows(base, bsc, period), base, bsc, period));
        return sb;
    }

    /**
     * Obtiene la estructura de la informaci&oacute;n de un balanscorecard en
     * especif&iacute;co.
     *
     * @param base Recurso de SWB utilizado para mostrarlo dentro de la
     * plataforma SWBPortal
     * @param bsc Elemento de tipo BalanScoreCard, se refiere al BSC
     * seleccionado actualmente
     * @return
     */
    private JSONObject getStructureDataArrows(Resource base, BSC bsc, Period period) {
        Iterator itPers = bsc.listPerspectives();
        JSONObject dataArrows = new JSONObject();
        if (itPers.hasNext()) {
            itPers = BSCUtils.sortObjSortable(itPers).listIterator();
            int count = 1;
            while (itPers.hasNext()) {
                Perspective perspective = (Perspective) itPers.next();
                try {
                    JSONObject perspectiveObj = new JSONObject();
                    boolean config = base.getData("perspective" + base.getId() + perspective.getId())
                            == null ? false : true;
                    boolean useMargin = base.getData("margins") == null ? false : true;
                    int maxObjectForPerspective = getMaxObjectiveForTheme(perspective, period);
                    int heightDiffer = getHeightDifferentiator(perspective, base);
                    perspectiveObj.put("index", perspective.getIndex());
                    perspectiveObj.put("title", perspective.getTitle());
                    perspectiveObj.put("height", getHeightPerspective(perspective, base,
                            maxObjectForPerspective));

                    perspectiveObj.put("isHorizontal", config);
                    perspectiveObj.put("maxObjectives", maxObjectForPerspective);
                    perspectiveObj.put("arrayTheme", getArrayTheme(perspective));
                    perspectiveObj.put("heightDiffe", heightDiffer);
                    perspectiveObj.put("margins", useMargin);
                    if (maxObjectForPerspective > 0 || heightDiffer > 0) {
                        dataArrows.put(count + "", perspectiveObj);
                        count++;
                    }
                } catch (JSONException ex) {
                    log.error("Exception getStructureDataArrows: " + ex);
                }
            }
        }
        return dataArrows;
    }

    /**
     * Ejecuta una serie de acciones para poder obtener las flechas causa -
     * efecto
     *
     * @param dataStructure objeto de tipo {@code JSONObject} que contiene la
     * estructura de datos de un balancescorecard
     * @param base Recurso de SWB utilizado para mostrarlo dentro de la
     * plataforma SWBPortal
     * @param bsc Elemento de tipo BalanScoreCard, se refiere al objeto
     * {@code BSC} seleccionado actualmente
     * @param period Elemento de tipo {@code Period], se refiere al periodo seleccionado
     * actualmente en el sitio BSC
     * @return un objeto de tipo {@code StringBuilder} con el c&oacute;digo HTML de las
     * flechas causa - efecto
     */
    private StringBuilder paintArrows(JSONObject dataStructure, Resource base, BSC bsc, Period period) {
        StringBuilder sb = new StringBuilder();
        Iterator itPers = bsc.listPerspectives();
        if (itPers.hasNext()) {
            itPers = BSCUtils.sortObjSortable(itPers).listIterator();
        }

        int countLinesAttributes = 1;
        StringBuilder sbAttributeLines = new StringBuilder();
        StringBuilder sbStatementLines = new StringBuilder();
        Iterator<Entry> itMap;

        while (itPers.hasNext()) {
            Perspective perspective = (Perspective) itPers.next();
            Iterator listThemes = perspective.listThemes();
            if (listThemes.hasNext()) {
                listThemes = BSCUtils.listValidParent(listThemes, null).iterator();
                listThemes = BSCUtils.sortObjSortable(listThemes).listIterator();
            }
            while (listThemes.hasNext()) {
                Theme theme = (Theme) listThemes.next();
                Iterator itCausalTheme = theme.listCausalThemes();
                itCausalTheme = BSCUtils.listValidParent(itCausalTheme, null).iterator();
                itCausalTheme = BSCUtils.sortObjSortable(itCausalTheme).listIterator();

                int startPerspectiveIndex = findIndexPerspective(perspective, dataStructure);
                int startThemeIndex = findIndexTheme(startPerspectiveIndex, dataStructure, theme);
                if (itCausalTheme.hasNext()) {
                    HashMap map = paintThemeTheme(dataStructure, startPerspectiveIndex, startThemeIndex,
                            base, itCausalTheme, countLinesAttributes, period, "#triangle-end1");
                    itMap = map.entrySet().iterator();
                    while (itMap.hasNext()) {
                        Entry entry = itMap.next();
                        countLinesAttributes = Integer.parseInt(entry.getKey().toString());
                        StringBuilder sb1 = (StringBuilder) entry.getValue();
                        sbAttributeLines.append(sb1);
                    }
                }
                Iterator itCausalobjective = theme.listCausalObjectives();
                itCausalobjective = BSCUtils.listValidParent(itCausalobjective, theme).iterator();
                itCausalobjective = BSCUtils.sortObjSortable(itCausalobjective).listIterator();
                if (itCausalobjective.hasNext()) {
                    HashMap map = paintThemeObjective(dataStructure, startPerspectiveIndex, startThemeIndex,
                            0, itCausalobjective, base, period, countLinesAttributes, "#triangle-end2");
                    itMap = map.entrySet().iterator();
                    while (itMap.hasNext()) {
                        Entry entry = itMap.next();
                        countLinesAttributes = Integer.parseInt(entry.getKey().toString());
                        StringBuilder sb1 = (StringBuilder) entry.getValue();
                        sbAttributeLines.append(sb1);
                    }
                }
                Iterator itObjectives = theme.listObjectives();
                itObjectives = BSCUtils.listValidParent(itObjectives, null).iterator();
                itObjectives = BSCUtils.sortObjSortable(itObjectives).listIterator();
                while (itObjectives.hasNext()) {
                    Objective objective = (Objective) itObjectives.next();
                    itCausalTheme = objective.listCausalThemes();
                    itCausalTheme = BSCUtils.listValidParent(itCausalTheme, null).iterator();
                    itCausalTheme = BSCUtils.sortObjSortable(itCausalTheme).listIterator();
                    HashMap map = new HashMap();
                    int startObjective = 0;
                    if (itCausalTheme.hasNext()) {
                        startObjective = findIndexObjective(startPerspectiveIndex, dataStructure,
                                startThemeIndex, objective);
                        map = paintObjectiveTheme(dataStructure, startPerspectiveIndex, startThemeIndex,
                                startObjective, itCausalTheme, base, period, countLinesAttributes,
                                objective.getTheme().getPerspective(), "#triangle-end3");
                        itMap = map.entrySet().iterator();
                        while (itMap.hasNext()) {
                            Entry entry = itMap.next();
                            countLinesAttributes = Integer.parseInt(entry.getKey().toString());
                            StringBuilder sb1 = (StringBuilder) entry.getValue();
                            sbAttributeLines.append(sb1);
                        }
                    }
                    itCausalobjective = objective.listCausalObjectives();
                    itCausalobjective = BSCUtils.listValidParent(itCausalobjective, objective.getTheme())
                            .iterator();
                    itCausalobjective = BSCUtils.sortObjSortable(itCausalobjective).listIterator();
                    if (itCausalobjective.hasNext()) {
                        startObjective = findIndexObjective(startPerspectiveIndex, dataStructure,
                                startThemeIndex, objective);
                        map = paintObjectiveObjective(dataStructure, startPerspectiveIndex,
                                startThemeIndex, startObjective, base, period, itCausalobjective,
                                objective, countLinesAttributes, "#triangle-end4");
                        itMap = map.entrySet().iterator();
                        while (itMap.hasNext()) {
                            Entry entry = itMap.next();
                            countLinesAttributes = Integer.parseInt(entry.getKey().toString());
                            StringBuilder sb1 = (StringBuilder) entry.getValue();
                            sbAttributeLines.append(sb1);
                        }
                    }
                }
            }

            Iterator itPerspe = perspective.listCausalPerspectives();
            itPerspe = BSCUtils.sortObjSortable(itPerspe).listIterator();
            if (itPerspe.hasNext()) {
                //aqui continuar
                Perspective perspeFinal = (Perspective)itPerspe.next();
                String classLine = ((base.getData("colorRelPP") == null)
                        || (base.getData("colorRelPP").trim().length() == 0))
                        ? "arrow" : base.getData("colorRelPP");
                int startPerspectiveIndex = findIndexPerspective(perspective, dataStructure);
                int finalPerspectiveIndex = findIndexPerspective(perspeFinal, dataStructure);
                HashMap map = paintPerspPersp(startPerspectiveIndex, finalPerspectiveIndex, //dataStructure.length(),
                        countLinesAttributes, classLine, dataStructure, "#triangle-end5");
                itMap = map.entrySet().iterator();
                while (itMap.hasNext()) {
                    Entry entry = itMap.next();
                    countLinesAttributes = Integer.parseInt(entry.getKey().toString());
                    StringBuilder sb1 = (StringBuilder) entry.getValue();
                    sbAttributeLines.append(sb1);
                }
            }
        }
        sbStatementLines.append(createLinesSVG(countLinesAttributes));
        sb.append(getJavascript(sbStatementLines, sbAttributeLines));
        sb.append(loadJavascript(dataStructure));
        return sb;
    }

    /**
     * V&aacute;lida que el tema tenga objetivos con en el periodo proporcionado
     *
     * @param theme Objeto de tipo Tema del cual seran obtenidos objetos de tipo
     * Objective, para ser evaluados como v&aacute;lidos
     * @param period Elemento de tipo Period, se refiere al periodo seleccionado
     * actualmente en el sitio BSC
     * @return un true o false indicando si el tema tiene objetivos con el
     * periodo proporcionado
     */
    private boolean isValidTheme(Theme theme, Period period) {
        boolean valid = false;
        Iterator<Objective> itObjectives = theme.listObjectives();
        while (itObjectives.hasNext()) {
            Objective objective = itObjectives.next();
            if (objective.hasPeriod(period) && objective.isActive()
                    && objective.isValid()) {
                valid = true;
                break;
            }
        }
        return valid;
    }

    /**
     * Crea l&iacute;neas de tipo SVG
     *
     * @param count n&uacute;mero de l&iacute;neas a crear
     * @return un objeto de tipo {@code StringBuilder} que contiene las
     * intrucciones para crear l&iacute;neas SVG
     */
    private StringBuilder createLinesSVG(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= count; i++) {
            sb.append("line");
            sb.append(i);
            sb.append(" = svg.appendChild(create(\"line\"));");
        }
        return sb;
    }

    /**
     * Pinta la relaci&oacute;n Causa - Efecto entre temas
     *
     * @param jsonArrows objeto de tipo {@code JSONObject} que contiene los
     * datos de un balanscorecard a partir del cual buscar&aacute; la
     * perspectiva
     * @param startPerspectiveIndex &Iacute;ndice de la perspectiva (Causa) que
     * contiene la relación causa - efecto
     * @param startThemeIndex &Iacute;ndice del tema (Causa) que contiene la
     * relación causa - efecto
     * @param base Recurso de SWB utilizado para mostrarlo dentro de la
     * plataforma SWBPortal
     * @param itCausalTheme Iterador con los temas asociados a la causa - efecto
     * de objetivos/ temas
     * @param countLine n&uacute;mero de l&iacute;nea a partir de la cual
     * deber&aacute;n crearse las siguientes l&iacute;neas
     * @param period Elemento de tipo Period, se refiere al periodo seleccionado
     * actualmente en el sitio BSC
     * @return objeto de tipo {@code HashMap} con las l&iacute;neas creadas y el
     * contador de l&iacute;neas utilizados
     */
    private HashMap paintThemeTheme(JSONObject jsonArrows, int startPerspectiveIndex,
            int startThemeIndex, Resource base, Iterator itCausalTheme, int countLine,
            Period period, String triangleEnd) {
        HashMap map1 = new HashMap();
        Iterator<Entry> itMap;
        StringBuilder sb = new StringBuilder();
        String classLine = ((base.getData("colorRelTT") == null)
                || (base.getData("colorRelTT").trim().length() == 0))
                ? "arrow" : base.getData("colorRelTT");
        while (itCausalTheme.hasNext()) {
            Theme theme = (Theme) itCausalTheme.next();
            if (isValidTheme(theme, period)) {
                HashMap map = new HashMap();
                Perspective perspective = theme.getPerspective();
                int finalPerspectiveIndex = findIndexPerspective(perspective, jsonArrows);
                int finalThemeIndex = findIndexTheme(finalPerspectiveIndex, jsonArrows, theme);
                getHeightDifferentiator(perspective, base);
                if (startPerspectiveIndex != finalPerspectiveIndex) {
                    map = paintLinesThemeTheme(startPerspectiveIndex, startThemeIndex,
                            finalPerspectiveIndex, finalThemeIndex, countLine, classLine, jsonArrows,
                            countForRelation, triangleEnd);
                } else {
                    map = paintLinesSameThemeTheme(startPerspectiveIndex, startThemeIndex,
                            finalThemeIndex, countLine, classLine, jsonArrows, triangleEnd);
                }
                itMap = map.entrySet().iterator();
                while (itMap.hasNext()) {
                    Entry entry = itMap.next();
                    countLine = Integer.parseInt(entry.getKey().toString());
                    StringBuilder sb1 = (StringBuilder) entry.getValue();
                    sb.append(sb1);
                }
                countForRelation++;
            }
        }
        map1.put(countLine, sb);
        return map1;
    }

    private HashMap paintPerspPersp(int initPerspective, int finalizePerspective, //int elements,
            int countLine, String classLine, JSONObject jsonArrows, String triangleEnd) {
        HashMap map = new HashMap();
        StringBuilder sb = new StringBuilder();
        try {
            JSONObject startPers = (JSONObject) jsonArrows.get(initPerspective + "");
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalizePerspective + "");
            //BigDecimal elem = tam1.divide(new BigDecimal(elements), MathContext.DECIMAL128);
            //BigDecimal x1A = new BigDecimal(countForPerspective).multiply(elem);
            BigDecimal x1A = new BigDecimal("4.5");
            x1 = x1A.doubleValue();
            x2 = x1;
            double sizePers = 0;
            JSONObject obj;
            for (int i = 1; i <= (initPerspective - 1); i++) {
                obj = (JSONObject) jsonArrows.get(i + "");
                sizePers = sizePers + obj.getInt("height");
                if(obj.getInt("heightDiffe") > 0) {
                    sizePers = sizePers + 40 + 2;
                }
                sizePers = sizePers + 42; //titulo del tema y espacio entre temas
            }
            sizePers = sizePers + (1 * ((startPers.getInt("height") + 22)/3)); //considera titulo del tema
            
            sizePers = sizePers + 142;//espacio para mision y vision
            
            y1 = sizePers;
            sizePers = 0;
            for (int i = 1; i <= (finalizePerspective - 1); i++) {
                obj = (JSONObject) jsonArrows.get(i + "");
                sizePers = sizePers + obj.getInt("height");
                if(obj.getInt("heightDiffe") > 0) {
                    sizePers = sizePers + 40 + 2;
                }                
                sizePers = sizePers + 42; //titulo del tema y espacio entre temas
            }
            sizePers = sizePers + (2 * ((finalPers.getInt("height") + 22)/3)); //considera titulo del tema
            sizePers = sizePers + 142;
            
            y2 = sizePers;
            if (initPerspective > finalizePerspective) {
                sb.append(paintLines(countLine, x2, x1, y1, y2, classLine));
                //sb.append(paintLineTriangle(countLine, triangleEnd));
            } else {
                sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            }
            sb.append(paintLineTriangle(countLine, triangleEnd));
            countLine++;
        } catch (JSONException ex) {
            log.error("Error in " + ex);
        }
        map.put(countLine, sb);
        return map;
    }

    /**
     * Pinta la relaci&oacute;n Causa - Efecto entre objetivos horizontales de
     * la misma perspectiva
     *
     * @param initPerspective &Iacute;ndice de la perspectiva (Causa) que
     * contiene la relaci&oacute;n causa - efecto
     * @param initTheme &Iacute;ndice del tema (Causa) que contiene la
     * relaci&oacute;n causa - efecto
     * @param initObjective &Iacute;ndice del objetivo (Causa) que contiene la
     * relaci&oacute;n causa - efecto
     * @param finalizeTheme &Iacute;ndice del tema (Efecto) que contiene la
     * relaci&oacute;n causa - efecto
     * @param finalizeObjective &Iacute;ndice del objetivo (Efecto) que contiene
     * la relaci&oacute;n causa - efecto
     * @param countLine n&uacute;mero de l&iacute;nea a partir de la cual
     * deber&aacute;n crearse las siguientes l&iacute;neas
     * @param classLine Clase CSS
     * @param jsonArrows objeto de tipo {@code JSONObject} que contiene los
     * datos de un balanscorecard a partir del cual buscar&aacute; la
     * perspectiva
     * @return objeto de tipo {@code HashMap} con las l&iacute;neas creadas y el
     * contador de l&iacute;neas utilizados
     */
    private HashMap paintOOHSameP(int initPerspective, int initTheme,
            int initObjective, int finalizeTheme, int finalizeObjective,
            int countLine, String classLine, JSONObject jsonArrows, String triangleEnd) {
        HashMap map1 = new HashMap();
        StringBuilder sb = new StringBuilder();
        try {
            JSONObject startPers = (JSONObject) jsonArrows.get(initPerspective + "");
            JSONObject arrayTheme = (JSONObject) startPers.get("arrayTheme");
            JSONObject themePer = (JSONObject) arrayTheme.get(initTheme + "");
            JSONObject arraysObjects = (JSONObject) themePer.get("arrayObjective");

            JSONObject arrayThemeFinal = (JSONObject) startPers.get("arrayTheme");
            JSONObject themePerFinal = (JSONObject) arrayThemeFinal.get(finalizeTheme + "");
            JSONObject arraysObjectsFinal = (JSONObject) themePerFinal.get("arrayObjective");

            boolean useMargins = startPers.getBoolean("margins");
            int[] heightPer = getHeigthPerspectives(initPerspective, jsonArrows);
            BigDecimal divContainerColsPer1 = divContainerCo.divide(new BigDecimal(100),
                    MathContext.DECIMAL128);
            BigDecimal divSizeColumnsPer1 = divSizeColum.divide(new BigDecimal(100),
                    MathContext.DECIMAL128);
            BigDecimal incrementTam = new BigDecimal(1);
            if (countForRelation < 15) {
                incrementTam = new BigDecimal(countForRelation).multiply(tam04).
                        multiply(divContainerColsPer1).multiply(divSizeColumnsPer1);
            }
            int[] columns = BSCUtils.getSizeColumnsTheme(arrayTheme.length());
            double restCols = 0;
            for (int i = 0; i < (initTheme - 1); i++) {
                restCols = restCols + columns[i];
            }
            //primer linea
            BigDecimal xA1 = new BigDecimal(restCols).multiply(divContainerColsPer1);
            int[] columnsObjs = BSCUtils.getSizeColumnsObjective(arraysObjects.length(),
                    columns[initTheme - 1]);
            double restColsObj = 0;
            for (int i = 0; i < (initObjective - 1); i++) {
                restColsObj = restColsObj + columnsObjs[i];
            }
            BigDecimal xA3 = new BigDecimal(restColsObj).multiply(divContainerColsPer1).
                    multiply(divSizeColumnsPer1);
            BigDecimal xA4 = new BigDecimal(columnsObjs[initObjective - 1]).
                    divide(tam2, MathContext.DECIMAL128);
            BigDecimal xA = divTitl.add(xA1).add(xA3).add(xA4);

            x1 = xA.doubleValue();
            x2 = x1;
            BigDecimal yA1 = new BigDecimal(heightPer[0]);
            BigDecimal yA3 = new BigDecimal(initPerspective);
            BigDecimal yA4 = yA3.multiply(tam2);

            BigDecimal yA5 = yA3.multiply(tam20).multiply(tam2);
            BigDecimal baseYdiff = getGroupDiffOnly(jsonArrows, initPerspective).multiply(tam20);
            BigDecimal baseYdiff2 = yA5.subtract(baseYdiff);

            BigDecimal yA = tam120.add(yA1).add(baseYdiff2);
            if (useMargins) {
                yA.add(yA4).add(yA3);
            }
            y1 = yA.doubleValue();
            BigDecimal xB1 = tam18.subtract(incrementTam);
            BigDecimal yB = yA.add(xB1);
            y2 = yB.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //segunda linea
            columns = BSCUtils.getSizeColumnsTheme(arrayThemeFinal.length());
            restCols = 0;
            for (int i = 0; i < (finalizeTheme - 1); i++) {
                restCols = restCols + columns[i];
            }
            BigDecimal xC1 = new BigDecimal(restCols).multiply(divContainerColsPer1);
            int[] columnsObjs1 = BSCUtils.getSizeColumnsObjective(arraysObjectsFinal.length(),
                    columns[finalizeTheme - 1]);
            restColsObj = 0;
            for (int i = 0; i < (finalizeObjective - 1); i++) {
                restColsObj = restColsObj + columnsObjs1[i];
            }
            BigDecimal xC3 = new BigDecimal(restColsObj).multiply(divContainerColsPer1).
                    multiply(divSizeColumnsPer1);
            BigDecimal xC4 = new BigDecimal(columnsObjs1[finalizeObjective - 1]).
                    divide(tam2, MathContext.DECIMAL128);
            BigDecimal xC = divTitl.add(xC1).add(xC3).add(xC4);
            x1 = x2;
            x2 = xC.doubleValue();
            y1 = y2;
            BigDecimal yD1 = new BigDecimal(heightPer[0]);
            BigDecimal yD4 = yA3.multiply(tam2);

            BigDecimal yD6 = tam18.subtract(incrementTam);
            BigDecimal yD = tam120.add(yD1).add(baseYdiff2).add(yD6);
            if (useMargins) {
                yD.add(yD4).add(yA3);
            }
            y2 = yD.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //tercera linea
            x1 = x2;
            x2 = x1;
            y1 = y2;
            BigDecimal yE = tam120.add(yD1).add(baseYdiff2);
            if (useMargins) {
                yE.add(yD4).add(yA3);
            }
            y2 = yE.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            sb.append(paintLineTriangle(countLine, triangleEnd));
            countLine++;

        } catch (JSONException ex) {
            log.error("Exception in: " + ex);
        }
        map1.put(countLine, sb);
        return map1;
    }

    /**
     * Pinta la relaci&oacute;n Causa - Efecto entre objetivos verticales de la
     * misma perspectiva
     *
     * @param initPerspective &Iacute;ndice de la perspectiva (Causa) que
     * contiene la relaci&oacute;n causa - efecto
     * @param initTheme &Iacute;ndice del tema (Causa) que contiene la
     * relaci&oacute;n causa - efecto
     * @param initObjective &Iacute;ndice del objetivo (Causa) que contiene la
     * relaci&oacute;n causa - efecto
     * @param finalizeTheme &Iacute;ndice del tema (Efecto) que contiene la
     * relaci&oacute;n causa - efecto
     * @param finalizeObjective &Iacute;ndice del objetivo (Efecto) que contiene
     * la relaci&oacute;n causa - efecto
     * @param countLine n&uacute;mero de l&iacute;nea a partir de la cual
     * deber&aacute;n crearse las siguientes l&iacute;neas
     * @param classLine Clase CSS
     * @param jsonArrows objeto de tipo {@code JSONObject} que contiene los
     * datos de un balanscorecard a partir del cual buscar&aacute; la
     * perspectiva
     * @return objeto de tipo {@code HashMap} con las l&iacute;neas creadas y el
     * contador de l&iacute;neas utilizados
     */
    private HashMap paintOOVSameP(int initPerspective, int initTheme,
            int initObjective, int finalizeTheme, int finalizeObjective,
            int countLine, String classLine, JSONObject jsonArrows, String triangleEnd) {
        HashMap map1 = new HashMap();
        StringBuilder sb = new StringBuilder();
        try {
            JSONObject startPers = (JSONObject) jsonArrows.get(initPerspective + "");
            JSONObject arrayTheme = (JSONObject) startPers.get("arrayTheme");
            JSONObject themePer = (JSONObject) arrayTheme.get(initTheme + "");
            JSONObject arraysObjects = (JSONObject) themePer.get("arrayObjective");

            JSONObject arrayThemeFinal = (JSONObject) startPers.get("arrayTheme");
            JSONObject themePerFinal = (JSONObject) arrayThemeFinal.get(finalizeTheme + "");
            JSONObject arraysObjectsFinal = (JSONObject) themePerFinal.get("arrayObjective");
            int[] heightPer = getHeigthPerspectives(initPerspective - 1, jsonArrows);
            boolean useMargins = startPers.getBoolean("margins");
            double restCols = 0;
            int[] columns = BSCUtils.getSizeColumnsTheme(arrayTheme.length());
            for (int i = 0; i < (initTheme - 1); i++) {
                restCols = restCols + columns[i];
            }
            BigDecimal divContainerColsPer1 = divContainerCo.divide(new BigDecimal(100),
                    MathContext.DECIMAL128);
            BigDecimal divSizeColumnsPer1 = divSizeColum.divide(new BigDecimal(100),
                    MathContext.DECIMAL128);
            BigDecimal incrementTam = new BigDecimal(1);
            if (countForRelation < 15) {
                incrementTam = new BigDecimal(countForRelation).multiply(tam04).
                        multiply(divContainerColsPer1);
            }
            if (arraysObjects.length() == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }

            BigDecimal restCols1 = new BigDecimal(restCols).multiply(divContainerColsPer1);
            BigDecimal columnsInit = new BigDecimal((columns[initTheme - 1]));
            BigDecimal result = columnsInit.multiply(divContainerColsPer1).multiply(divSizeColumnsPer1).
                    add(restCols1).add(divTitl);

            if (arrayTheme.length() == initTheme) {
                result = divTitl.add(divContainerCo);
            }

            //primer linea
            x1 = result.doubleValue();
            BigDecimal resultX2 = result.add(incrementTam);
            x2 = resultX2.doubleValue();
            BigDecimal baseY1a = new BigDecimal(initPerspective).subtract(tam1).
                    multiply(tam2).add(new BigDecimal(heightPer[1]));
            BigDecimal heightObjective1a = new BigDecimal(startPers.getInt("height")).
                    subtract(getHeightDifferentiator(jsonArrows, initPerspective)).
                    divide(new BigDecimal(arraysObjects.length()), MathContext.DECIMAL128);
            BigDecimal baseY1a1 = new BigDecimal(initObjective).multiply(tam1);

            BigDecimal baseY1a2 = new BigDecimal(initPerspective).multiply(tam20).multiply(tam2);
            BigDecimal baseYdiff = getGroupDiffOnly(jsonArrows, initPerspective).multiply(tam20);
            BigDecimal baseYdiff2 = baseY1a2.subtract(baseYdiff);

            BigDecimal baseY1a3 = new BigDecimal(initObjective).subtract(tam1);
            BigDecimal baseY1a31 = baseY1a3.multiply(heightObjective1a);
            BigDecimal baseY1a4 = heightObjective1a.divide(tam2, MathContext.DECIMAL128);
            BigDecimal baseY1a5 = new BigDecimal(heightPer[0]).add(getHeightDifferentiator(jsonArrows,
                    initPerspective));
            BigDecimal baseY1 = baseY1a5.add(tam120).add(incrementTam).
                    add(baseYdiff2).add(baseY1a31).add(baseY1a4);
            if (useMargins) {
                baseY1.add(baseY1a).add(baseY1a1);
            }

            y1 = baseY1.doubleValue();
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //segunda linea
            heightPer = getHeigthPerspectives(initPerspective, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            BigDecimal y1a = new BigDecimal(heightPer[0]);
            BigDecimal y1b = new BigDecimal(heightPer[1]);
            BigDecimal y1c = new BigDecimal(initPerspective);

            BigDecimal y1e = tam18.subtract(incrementTam);
            BigDecimal y1f = y1c.subtract(tam1).multiply(tam2).add(y1b);
            BigDecimal y1g = y1a.add(baseYdiff2).add(tam120).add(y1e);
            if (useMargins) {
                y1g.add(y1f).add(y1c);
            }
            y2 = y1g.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //tercer linea
            columns = BSCUtils.getSizeColumnsTheme(arrayThemeFinal.length());
            restCols = 0;
            for (int i = 0; i < (finalizeTheme - 1); i++) {
                restCols = restCols + columns[i];
            }
            BigDecimal xb = divContainerColsPer1.multiply(new BigDecimal(restCols));
            BigDecimal xb1 = divContainerColsPer1.multiply(divSizeColumnsPer1).
                    multiply(new BigDecimal(columns[finalizeTheme - 1]));
            BigDecimal resultX2b = divTitl.add(incrementTam).add(xb).add(xb1);
            if (arrayThemeFinal.length() == finalizeTheme) {
                resultX2b = divTitl.add(divContainerCo).add(incrementTam);
            }
            x2 = resultX2b.doubleValue();
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //cuarta linea
            heightPer = getHeigthPerspectives(initPerspective - 1, jsonArrows);
            if (arraysObjectsFinal.length() == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            x1 = x2;
            x2 = x1;
            y1 = y2;
            heightObjective1a = new BigDecimal(startPers.getInt("height")).
                    subtract(getHeightDifferentiator(jsonArrows, initPerspective)).
                    divide(new BigDecimal(arraysObjectsFinal.length()),
                    MathContext.DECIMAL128);
            BigDecimal yc = new BigDecimal(heightPer[0]).add(getHeightDifferentiator(jsonArrows,
                    initPerspective));
            BigDecimal yca = y1c.subtract(tam1).multiply(tam2).add(new BigDecimal(heightPer[1]));
            BigDecimal ycb0 = new BigDecimal(finalizeObjective);
            BigDecimal ycd = ycb0.subtract(tam1).multiply(heightObjective1a);
            BigDecimal yce = heightObjective1a.divide(tam2, MathContext.DECIMAL128);
            BigDecimal resultY2c = yc.add(baseYdiff2).add(ycd).add(yce).add(tam120).
                    add(incrementTam);
            if (useMargins) {
                resultY2c.add(yca).add(ycb0);
            }
            y2 = resultY2c.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //quinta linea
            x1 = x2;
            BigDecimal xc = new BigDecimal(columns[finalizeTheme - 1]).multiply(divContainerColsPer1).
                    multiply(divSizeColumnsPer1);
            BigDecimal resultX2c = divTitl.add(xb).add(xc);
            if (arrayThemeFinal.length() == finalizeTheme) {
                resultX2c = divTitl.add(divContainerCo);
            }
            x2 = resultX2c.doubleValue();
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            sb.append(paintLineTriangle(countLine, triangleEnd));
            countLine++;

        } catch (JSONException ex) {
            log.error("Exception in: " + ex);
        }
        map1.put(countLine, sb);
        return map1;
    }

    /**
     * Pinta la relaci&oacute;n Causa - Efecto entre temas de misma perspectiva
     *
     * @param startPerspectiveIndex &Iacute;ndice de la perspectiva (Causa) que
     * contiene la relaci&oacute;n causa - efecto
     * @param startThemeIndex &Iacute;ndice del tema (Causa) que contiene la
     * relaci&oacute;n causa - efecto
     * @param finalPerspectiveIndex &Iacute;ndice de la perspectiva (Efecto) que
     * contiene la relaci&oacute;n causa - efecto
     * @param finalThemeIndex &Iacute;ndice del tema (Efecto) que contiene la
     * relaci&oacute;n causa - efecto
     * @param countLine n&uacute;mero de l&iacute;nea a partir de la cual
     * deber&aacute;n crearse las siguientes l&iacute;neas
     * @param classLine Clase CSS
     * @param jsonArrows objeto de tipo {@code JSONObject} que contiene los
     * datos de un balanscorecard a partir del cual buscar&aacute; la
     * perspectiva
     * @return objeto de tipo {@code HashMap} con las l&iacute;neas creadas y el
     * contador de l&iacute;neas utilizados
     */
    private HashMap paintLinesSameThemeTheme(int startPerspectiveIndex, int startThemeIndex,
            int finalThemeIndex, int countLine, String classLine, JSONObject jsonArrows,
            String triangleEnd) {
        HashMap map = new HashMap();
        StringBuilder sb = new StringBuilder();
        try {
            JSONObject startPers = (JSONObject) jsonArrows.get((startPerspectiveIndex) + "");
            JSONObject arrayTheme = (JSONObject) startPers.get("arrayTheme");

            boolean useMargins = startPers.getBoolean("margins");
            double restCols = 0;
            BigDecimal divContaiPer = divContainerCo.divide(tam100, MathContext.DECIMAL128);
            BigDecimal divSizColPer = divSizeColum.divide(tam100, MathContext.DECIMAL128);
            //primer linea
            int[] columns = BSCUtils.getSizeColumnsTheme(arrayTheme.length());
            for (int i = 0; i < (startThemeIndex - 1); i++) {
                restCols = restCols + columns[i];
            }
            int[] heightPer = getHeigthPerspectives(startPerspectiveIndex - 1, jsonArrows);
            BigDecimal incrementTam = new BigDecimal(1);
            if (countForRelation < 15) {
                incrementTam = new BigDecimal(countForRelation).multiply(tam04).
                        add(new BigDecimal("0.4"));
            }
            BigDecimal xA1 = new BigDecimal(restCols).multiply(divContaiPer);
            BigDecimal xA2 = new BigDecimal(columns[startThemeIndex - 1]);

            BigDecimal xA2b = xA2.multiply(divContaiPer).multiply(divSizColPer);
            BigDecimal xA3 = xA2b.divide(tam2, MathContext.DECIMAL128);
            BigDecimal xA = divTitl.add(xA1).add(xA3);
            x1 = xA.doubleValue();
            x2 = x1;

            BigDecimal yA1 = new BigDecimal(heightPer[0]).
                    add(getHeightDifferentiator(jsonArrows, startPerspectiveIndex));
            BigDecimal yA0 = new BigDecimal(startPerspectiveIndex);
            BigDecimal yA2 = new BigDecimal(heightPer[1]);
            BigDecimal yA3 = yA0.subtract(tam1).multiply(tam2).add(tam1);

            BigDecimal yA4 = yA0.subtract(tam1).multiply(tam20).multiply(tam2);
            BigDecimal baseYdiff = getGroupDiffOnly(jsonArrows, startPerspectiveIndex).multiply(tam20);
            BigDecimal baseYdiff2 = yA4.subtract(baseYdiff);

            BigDecimal yA = yA1.add(tam120).add(baseYdiff2).add(tam20);
            if (useMargins) {
                yA.add(yA2).add(yA3);
            }
            y1 = yA.doubleValue();
            BigDecimal yB1 = tam18.subtract(incrementTam);
            BigDecimal yB = yA.subtract(yB1);
            y2 = yB.doubleValue();

            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //segunda linea
            x1 = x2;
            y1 = y2;
            y2 = y1;
            columns = BSCUtils.getSizeColumnsTheme(arrayTheme.length());
            restCols = 0;
            for (int i = 0; i < (finalThemeIndex - 1); i++) {
                restCols = restCols + columns[i];
            }
            BigDecimal xB1 = new BigDecimal(restCols).multiply(divContaiPer);
            BigDecimal xB2 = new BigDecimal(columns[finalThemeIndex - 1]);

            BigDecimal xB2b = xB2.multiply(divContaiPer).multiply(divSizColPer);
            BigDecimal xB3 = xB2b.divide(tam2, MathContext.DECIMAL128);
            BigDecimal xB = divTitl.add(xB1).add(xB3);
            x2 = xB.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //tercer linea
            x1 = x2;
            x2 = x1;
            y1 = y2;
            BigDecimal yD1 = new BigDecimal(heightPer[0]).add(getHeightDifferentiator(jsonArrows,
                    startPerspectiveIndex));
            BigDecimal yD0 = new BigDecimal(startPerspectiveIndex);
            BigDecimal yD2 = new BigDecimal(heightPer[1]);
            BigDecimal yD3 = yD0.subtract(tam1).multiply(tam2).add(tam1);

            BigDecimal yD4 = yD0.subtract(tam1).multiply(tam20).multiply(tam2);
            baseYdiff = getGroupDiffOnly(jsonArrows, startPerspectiveIndex).multiply(tam20);
            baseYdiff2 = yD4.subtract(baseYdiff);

            BigDecimal yD = yD1.add(tam120).add(baseYdiff2).add(tam20);
            if (useMargins) {
                yD.add(yD2).add(yD3);
            }

            y2 = yD.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            sb.append(paintLineTriangle(countLine, triangleEnd));
            countLine++;
        } catch (JSONException ex) {
            log.error("Exception in getLinesSameThemeTheme: " + ex);
        } catch (NumberFormatException ex) {
            log.error("Exception in NumberFormatException, getLinesSameThemeTheme: " + ex);
        }
        map.put(countLine, sb);
        return map;
    }

    /**
     * Pinta la relaci&oacute;n Causa - Efecto entre temas de distintas
     * perspectivas
     *
     * @param startPerspectiveIndex &Iacute;ndice de la perspectiva (Causa) que
     * contiene la relación causa - efecto
     * @param startThemeIndex &Iacute;ndice del tema (Causa) que contiene la
     * relación causa - efecto
     * @param finalPerspectiveIndex &Iacute;ndice de la perspectiva (Efecto) que
     * contiene la relación causa - efecto
     * @param finalThemeIndex &Iacute;ndice del tema (Efecto) que contiene la
     * relación causa - efecto
     * @param countLine n&uacute;mero de l&iacute;nea a partir de la cual
     * deber&aacute;n crearse las siguientes l&iacute;neas
     * @param classLine Clase CSS
     * @param jsonArrows objeto de tipo {@code JSONObject} que contiene los
     * datos de un balanscorecard a partir del cual buscar&aacute; la
     * perspectiva
     * @return objeto de tipo {@code HashMap} con las l&iacute;neas creadas y el
     * contador de l&iacute;neas utilizados
     */
    private HashMap paintLinesThemeTheme(int startPerspectiveIndex, int startThemeIndex,
            int finalPerspectiveIndex, int finalThemeIndex, int countLine,
            String classLine, JSONObject jsonArrows, int countForRelation, String triangleEnd) {
        HashMap map = new HashMap();
        StringBuilder sb = new StringBuilder();
        try {
            JSONObject startPers = (JSONObject) jsonArrows.get(startPerspectiveIndex + "");

            JSONObject arrayTheme = (JSONObject) startPers.get("arrayTheme");
            boolean useMargins = startPers.getBoolean("margins");
            double restCols = 0;
            BigDecimal divContaiPer = divContainerCo.divide(tam100, MathContext.DECIMAL128);
            BigDecimal divSizColPer = divSizeColum.divide(tam100, MathContext.DECIMAL128);
            //primer linea
            int[] columns = BSCUtils.getSizeColumnsTheme(arrayTheme.length());
            for (int i = 0; i < (startThemeIndex - 1); i++) {
                restCols = restCols + columns[i];
            }
            int[] heightPer = getHeigthPerspectives(startPerspectiveIndex - 1, jsonArrows);
            BigDecimal incrementTam = new BigDecimal(1);
            if (countForRelation < 15) {
                incrementTam = new BigDecimal(countForRelation).multiply(tam04).
                        multiply(divContaiPer).multiply(divSizColPer);
            }
            BigDecimal xA1 = new BigDecimal(restCols).multiply(divContaiPer);
            BigDecimal xA2 = new BigDecimal(columns[startThemeIndex - 1]);

            BigDecimal xA2b = xA2.multiply(divContaiPer).multiply(divSizColPer);
            BigDecimal xA3 = xA2b.divide(tam2, MathContext.DECIMAL128);
            BigDecimal xA = divTitl.add(xA1).add(xA3);
            x1 = xA.doubleValue();
            x2 = x1;

            BigDecimal yA1 = new BigDecimal(heightPer[0]).
                    add(getHeightDifferentiator(jsonArrows, startPerspectiveIndex));
            BigDecimal yA0 = new BigDecimal(startPerspectiveIndex);
            BigDecimal yA2 = new BigDecimal(heightPer[1]);
            BigDecimal yA3 = yA0.subtract(tam1).multiply(tam2).add(tam1);

            BigDecimal yA4 = yA0.subtract(tam1).multiply(tam20).multiply(tam2);
            BigDecimal baseYdiff = getGroupDiffOnly(jsonArrows, startPerspectiveIndex).multiply(tam20);
            BigDecimal baseYdiff2 = yA4.subtract(baseYdiff);

            BigDecimal yA = yA1.add(tam120).add(baseYdiff2).add(tam20);
            if (useMargins) {
                yA.add(yA2).add(yA3);
            }
            y1 = yA.doubleValue();
            BigDecimal yB1 = tam18.subtract(incrementTam);
            BigDecimal yB = yA.subtract(yB1);
            y2 = yB.doubleValue();

            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //segunda linea
            x1 = x2;
            BigDecimal xB = divContainerCo.add(divTitl).add(incrementTam);
            x2 = xB.doubleValue();
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //tercer linea
            x1 = x2;
            x2 = x1;
            y1 = y2;
            /*Calculo Y alto de perspectivas*/
            heightPer = getHeigthPerspectives((finalPerspectiveIndex - 1), jsonArrows);
            BigDecimal yC1 = new BigDecimal(heightPer[0]).add(getHeightDifferentiator(jsonArrows,
                    finalPerspectiveIndex));
            BigDecimal yC2 = new BigDecimal(heightPer[1]);
            BigDecimal yC3 = new BigDecimal(finalPerspectiveIndex);
            BigDecimal yC4 = yC3.subtract(tam1).multiply(tam2).add(tam1);

            BigDecimal yC5 = yC3.subtract(tam1).multiply(tam20).multiply(tam2);
            baseYdiff = getGroupDiffOnly(jsonArrows, finalPerspectiveIndex - 1).multiply(tam20);
            baseYdiff2 = yC5.subtract(baseYdiff);

            BigDecimal yC = yC1.add(tam120).add(baseYdiff2).add(incrementTam);
            if (useMargins) {
                yC.add(yC2).add(yC4);
            }
            y2 = yC.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalPerspectiveIndex + "");
            arrayTheme = (JSONObject) finalPers.get("arrayTheme");
            columns = BSCUtils.getSizeColumnsTheme(arrayTheme.length());
            restCols = 0;
            for (int i = 0; i < (finalThemeIndex - 1); i++) {
                restCols = restCols + columns[i];
            }
            //cuarta linea
            x1 = x2;
            BigDecimal xD1 = new BigDecimal(restCols).multiply(divContaiPer);
            BigDecimal xD2 = new BigDecimal(columns[finalThemeIndex - 1]).multiply(divContaiPer).
                    multiply(divSizColPer);
            BigDecimal xD3 = xD2.divide(tam2, MathContext.DECIMAL128);
            BigDecimal xD = divTitl.add(xD1).add(xD3);
            x2 = xD.doubleValue();
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //quinta linea
            heightPer = getHeigthPerspectives((finalPerspectiveIndex - 1), jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            BigDecimal yD1 = new BigDecimal(heightPer[0]).add(getHeightDifferentiator(jsonArrows,
                    finalPerspectiveIndex));
            BigDecimal yD0 = new BigDecimal(finalPerspectiveIndex);
            BigDecimal yD2 = new BigDecimal(heightPer[1]);
            BigDecimal yD3 = yD0.subtract(tam1).multiply(tam2).add(tam1);

            BigDecimal yD4 = yD0.subtract(tam1).multiply(tam20).multiply(tam2);
            baseYdiff = getGroupDiffOnly(jsonArrows, finalPerspectiveIndex).multiply(tam20);
            baseYdiff2 = yD4.subtract(baseYdiff);

            BigDecimal yD = yD1.add(tam120).add(baseYdiff2).add(tam20);
            if (useMargins) {
                yD.add(yD2).add(yD3);
            }

            y2 = yD.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            sb.append(paintLineTriangle(countLine, triangleEnd));
            countLine++;

        } catch (JSONException ex) {
            log.error("Exception get lines (Theme Theme): " + ex);
        } catch (NumberFormatException ex) {
            log.error("Exception NumberFormatException get lines (Theme Theme): " + ex);
        }
        map.put(countLine, sb);
        return map;
    }

    /**
     * Obtiene el alto de las perspectivas acumuladas a partir de una
     * perspectiva dada
     *
     * @param perspectiveIndex &Iacute;ndice de la perspectiva de la que se
     * requiere obtener el alto
     * @param jsonArrows objeto de tipo {@code JSONObject} que contiene los
     * datos de un balanscorecard a partir del cual buscar&aacute; la
     * perspectiva
     * @return arreglo de enteros que contiene la sumatoria de los altos de las
     * perspectivas y el n&uacute;mero de objetivos m&aacute;ximo por
     * perspectiva
     */
    private int[] getHeigthPerspectives(int perspectiveIndex, JSONObject jsonArrows) {
        int[] heightPerspectives = new int[3];
        int addArrowY = 0;
        int filaAgregadasSuma = 0;
        for (int i = 1; i <= perspectiveIndex; i++) {
            try {
                JSONObject pers = (JSONObject) jsonArrows.get("" + i);
                addArrowY = addArrowY + pers.getInt("height");
                if (!pers.getBoolean("isHorizontal")) {
                    filaAgregadasSuma = filaAgregadasSuma + pers.getInt("maxObjectives");
                } else {
                    filaAgregadasSuma = filaAgregadasSuma + 1;
                }
            } catch (JSONException ex) {
                log.error("Exception getHeigthPerspectives: " + ex);
            }
        }
        if (perspectiveIndex > 1) {
            filaAgregadasSuma = filaAgregadasSuma * 1; // usado para los margenes
        }
        heightPerspectives[0] = addArrowY;
        heightPerspectives[1] = filaAgregadasSuma;
        return heightPerspectives;
    }

    /**
     * Obtiene de un objeto {
     *
     * @JSONObject} el alto de un grupo de diferenciadores partiendo de una
     * perspectiva
     * @param jsonArrows objeto de tipo {@code JSONObject} que contiene los
     * datos de un balanscorecard
     * @param perspectiveIndex &Iacute;ndice de la perspectiva a partir de la
     * cual buscar&aacute; el alto del grupo de diferenciadores
     * @return el alto de un grupo de diferenciadores
     */
    private BigDecimal getHeightDifferentiator(JSONObject jsonArrows, int perspectiveIndex) {
        int heightDiff = 0;
        try {
            JSONObject pers = (JSONObject) jsonArrows.get("" + perspectiveIndex);
            heightDiff = pers.getInt("heightDiffe");
        } catch (JSONException ex) {
            log.error("Exception in getHeightDifferentiator: " + ex);
        }
        return new BigDecimal(heightDiff);
    }

    /**
     * Encuentra el &iacute;ndice del tema
     *
     * @param indexPerspective &iacute;ndice de la perspectiva contenedora del
     * tema
     * @param jsonArrows objeto de tipo {@code JSONObject} que contiene los
     * datos de un balanscorecard a partir del cual buscar&aacute; la
     * perspectiva
     * @param theme objeto de tipo {@code Theme} que ser&aacute; ubicado
     * @return &iacute;ndice del tema en el objeto de tipo {@code JSONObject}
     */
    private int findIndexTheme(int indexPerspective, JSONObject jsonArrows, Theme theme) {
        int indexTheme = 0;
        try {
            if (jsonArrows.has("" + indexPerspective)) {
                JSONObject perspective = (JSONObject) jsonArrows.get("" + indexPerspective);
                if (perspective.has("arrayTheme")) {
                    JSONObject arrayTheme = (JSONObject) perspective.get("arrayTheme");
                    boolean find = false;
                    int i = 1;
                    while ((i <= arrayTheme.length()) && (!find)) {
                        JSONObject obj = (JSONObject) arrayTheme.get(i + "");
                        int index = obj.getInt("index");
                        if (theme.getIndex() == index) {
                            indexTheme = i;
                            find = true;
                        }
                        i++;
                    }
                }
            }
        } catch (JSONException ex) {
            log.error("Exception findIndexTheme: " + ex);
        }
        return indexTheme;
    }

    /**
     * Encuentra el &iacute;ndice del Objetivo
     *
     * @param indexPerspective &iacute;ndice de la perspectiva contenedora del
     * objetivo
     * @param jsonArrows objeto de tipo {@code JSONObject} que contiene los
     * datos de un balanscorecard a partir del cual buscar&aacute; la
     * perspectiva
     * @param indexTheme &iacute;ndice del tema contenedor del objetivo
     * @param objective objeto de tipo {@code Objective} que ser&aacute; ubicado
     * @return &iacute;ndice del objetivo en el objeto de tipo
     * {@code JSONObject}
     */
    private int findIndexObjective(int indexPerspective, JSONObject jsonArrows, int indexTheme, Objective objective) {
        int indexObjective = 0;
        try {
            if (jsonArrows.has("" + indexPerspective)) {
                JSONObject perspective = (JSONObject) jsonArrows.get("" + indexPerspective);
                if (perspective.has("arrayTheme")) {
                    JSONObject arrayTheme = (JSONObject) perspective.get("arrayTheme");
                    if (arrayTheme.has(indexTheme + "")) {
                        JSONObject themeData = (JSONObject) arrayTheme.get(indexTheme + "");
                        if (themeData.has("arrayObjective")) {
                            JSONObject arrayObjective = (JSONObject) themeData.get("arrayObjective");
                            if (themeData.has("arrayObjective")) {
                                boolean find = false;
                                int i = 1;
                                while ((i <= arrayObjective.length()) && (!find)) {
                                    JSONObject obj = (JSONObject) arrayObjective.get(i + "");
                                    int index = obj.getInt("index");
                                    if (objective.getIndex() == index) {
                                        indexObjective = i;
                                        find = true;
                                    }
                                    i++;
                                }
                            }
                        }
                    }
                }
            }
        } catch (JSONException ex) {
            log.error("Exception findIndexObjective: " + ex);
        }
        return indexObjective;
    }

    /**
     * Encuentra el &iacute;ndice de la perspectiva
     *
     * @param perspective objeto de tipo {@code Perspective} que ser&aacute;
     * ubicado
     * @param jsonArrows objeto de tipo {@code JSONObject} que contiene los
     * datos de un balanscorecard a partir del cual buscar&aacute; la
     * perspectiva
     * @return &iacute;ndice de la perspectiva en el objeto de tipo
     * {@code JSONObject}
     */
    private int findIndexPerspective(Perspective perspective, JSONObject jsonArrows) {
        int indexPerspective = 0;
        boolean find = false;
        int i = 1;
        while ((i <= jsonArrows.length()) && (!find)) {
            try {
                JSONObject obj = (JSONObject) jsonArrows.get(i + "");
                int index = obj.getInt("index");
                if (perspective.getIndex() == index) {
                    indexPerspective = i;
                    find = true;
                }
            } catch (JSONException ex) {
                log.error("Error get findIndexPerspective: " + ex);
            }
            i++;
        }
        return indexPerspective;
    }

    /**
     * Permite cargar la funci&oacute;n javascript que ejecutar&aacute; la
     * creaci&oacute;n de flechas causa - efecto
     *
     * @return objeto de tipo {@code StringBuilder} con la funci&oacute;n
     * javascript que ejecutar&aacute; la creaci&oacute;n de flechas causa -
     * efecto
     */
    private StringBuilder loadJavascript(JSONObject dataArrows) {
        StringBuilder sb = new StringBuilder();
        int[] height = getHeigthPerspectives(dataArrows.length(), dataArrows);
        int heightAll = height[0] + height[1] + (dataArrows.length() * 40) + 120
                + (dataArrows.length() * 2) + 20 + 10;
        sb.append("\n <div style=\"position:absolute;width:100%;height:");
        sb.append(heightAll);
        sb.append("px;float:left;z-index:1\" id=\"arrowLayer\" name=\"arrowLayer\">");
        sb.append("\n </div>");
        sb.append("\n <script type=\"text/javascript\">");
        sb.append("\n         dojo.addOnLoad( function(){");
        sb.append("\n             calculateDivs();}");
        sb.append("\n         );");
        sb.append("\n </script>");
        return sb;
    }

    /**
     * Funci&oacute;n javascript que contiene la creaci&oacute;n din&aacute;mica
     * de flechas causa - efecto
     *
     * @param allLines objeto de tipo {@code StringBuilder} que contiene la
     * creaci&oacute;n de l&iacute;neas SVG
     * @param allAttributeLines objeto de tipo {@code StringBuilder} que
     * contiene la definici&oacute;n de atributos para l&iacute;neas definidas
     * en SVG
     * @return funci&oacute;n javascript con la creaci&oacute;n din&aacute;mica
     * de flechas causa - efecto.
     */
    private StringBuilder getJavascript(StringBuilder allLines, StringBuilder allAttributeLines) {
        StringBuilder sb = new StringBuilder();
        //sb.append("\n <link href=\"/swbadmin/css/mapaEstrategico.css\" rel=\"stylesheet\" type=\"text/css\" />");
        sb.append("\n<script type=\"text/javascript\">");
        sb.append("\n	function calculateDivs() {");
        sb.append("\n         var div1 = document.getElementById(\"arrowLayer\"),");
        sb.append("\n         svg = div1.appendChild(create(\"svg\")),");
        sb.append("\n         defs1 = svg.appendChild(create(\"defs\"));");
        sb.append(allLines);
        sb.append("\n         svg.setAttribute(\"width\", \"100%\");");
        sb.append("\n         svg.setAttribute(\"height\", \"100%\");");
        for (int i = 1; i <= 5; i++) {
            sb.append(getDownArrow(i));
        }
        sb.append(allAttributeLines);
        sb.append("\n     }");
        sb.append("\n     function create(type) {");
        sb.append("\n		return document.createElementNS(\"http://www.w3.org/2000/svg\", type);");
        sb.append("\n	 }");
        sb.append("     \n</script>");
        return sb;
    }

    /**
     * Genera las flechas causa - efecto de un mapa estrat&eacute;gico
     *
     * @return objeto tipo {@code StringBuilder} con el contenedor de las
     * flechas causa - efecto
     */
    private StringBuilder getDownArrow(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n         var defs = document.createElementNS(\"http://www.w3.org/2000/svg\", \"defs\");");
        sb.append("\n         var marker = document.createElementNS(\"http://www.w3.org/2000/svg\", \"marker\");");
        sb.append("\n         marker.setAttribute(\"id\", 'triangle-end");
        sb.append(i);
        sb.append("');");
        sb.append("\n         marker.setAttribute(\"viewBox\", '0 0 10 10');");
        sb.append("\n         marker.setAttribute(\"refX\", 10);");
        sb.append("\n         marker.setAttribute(\"refY\", 5);");
        sb.append("\n         marker.setAttribute(\"markerWidth\", 6);");
        sb.append("\n         marker.setAttribute(\"markerHeight\", 6);");
        sb.append("\n         marker.setAttribute(\"orient\", \"auto\");");
        sb.append("\n         marker.setAttribute(\"class\", \"arrow");
        sb.append(i);
        sb.append("\");");
        sb.append("\n         var path = document.createElementNS(\"http://www.w3.org/2000/svg\", \"path\");");
        sb.append("\n         marker.appendChild(path);");
        sb.append("\n         path.setAttribute(\"d\", \"M 0 0 L 10 5 L 0 10 z\");");
        sb.append("\n         path.setAttribute(\"class\",\"arrow");
        sb.append(i);
        sb.append("\");");
        sb.append("\n         svg.appendChild(defs);");
        sb.append("\n         defs.appendChild(marker);");
        return sb;
    }

    /**
     * Crea los atributos de las l&iacute;neas SVG
     *
     * @param countLine n&uacute;mero de l&iacute;nea a partir de la cual
     * deber&aacute;n crearse las siguientes l&iacute;neas
     * @param x1 Coordenda inicial x
     * @param x2 Coordenda final x
     * @param y1 Coordenda inicial y
     * @param y2 Coordenda final y
     * @param classLine Clase CSS
     * @return objeto de tipo {@code StringBuilder}
     */
    private StringBuilder paintLines(int countLine, double x1, double x2, double y1,
            double y2, String classLine) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n         line");
        sb.append(countLine);
        sb.append(".setAttribute(\"x1\", \"");
        sb.append(x1);
        sb.append("%\");");
        sb.append("\n         line");
        sb.append(countLine);
        sb.append(".setAttribute(\"x2\", \"");
        sb.append(x2);
        sb.append("%\");");
        sb.append("\n         line");
        sb.append(countLine);
        sb.append(".setAttribute(\"y1\", \"");
        sb.append(y1);
        sb.append("\");");
        sb.append("\n         line");
        sb.append(countLine);
        sb.append(".setAttribute(\"y2\", \"");
        sb.append(y2);
        sb.append("\");");
        sb.append("\n         line");
        sb.append(countLine);
        sb.append(".setAttribute(\"class\",\"");
        sb.append(classLine);
        sb.append("\");");
        return sb;
    }

    /**
     * Crea la flecha que indica el efecto
     *
     * @param countLine n&uacute;mero de l&iacute;nea a partir de la cual
     * deber&aacute;n crearse las siguientes l&iacute;neas
     * @param valueTriangle nombre del elemento creado previamente para
     * mostrarse
     * @return objeto de tipo {@code StringBuilder} con la flecha que indica el
     * efecto en un mapa estrat&eacute;gico
     */
    private StringBuilder paintLineTriangle(int countLine, String valueTriangle) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n         line");
        sb.append(countLine);
        sb.append(".setAttribute(\"marker-end\",");
        sb.append("\"url(");
        sb.append(valueTriangle);
        sb.append(")\");");
        return sb;
    }

    /**
     * Obtiene true/false si el tema coincide con el tema final de una
     * perspectiva
     *
     * @param themeIndex &Iacute;ndice del tema que buscar&aacute; si es final
     * de la perspectiva
     * @param jsonArrows objeto de tipo {@code JSONObject} que contiene los
     * datos de un balanscorecard a partir del cual buscar&aacute; la
     * perspectiva
     * @param perspectiveIndex &Iacute;ndice de la perspectiva contenedora del
     * tema
     * @return la validaci&oacute;n si el tema esta al final de la perspectiva
     */
    private boolean getIndexFinalTheme(int themeIndex, JSONObject jsonArrows, int perspectiveIndex) {
        boolean isFinalTheme = false;
        try {
            JSONObject perspe = (JSONObject) jsonArrows.get("" + perspectiveIndex);
            JSONObject themesA = (JSONObject) perspe.get("arrayTheme");
            if (themeIndex == themesA.length()) {
                isFinalTheme = true;
            }
        } catch (JSONException ex) {
            log.error("Exception in getIndexFinalTheme: " + ex);
        }
        return isFinalTheme;
    }

    /**
     * Pinta la relaci&oacute;n Causa - Efecto entre tema - objetivo
     *
     * @param jsonArrows objeto de tipo {@code JSONObject} que contiene los
     * datos de un balanscorecard a partir del cual buscar&aacute; la
     * perspectiva
     * @param startPerspectiveIndex &Iacute;ndice de la perspectiva (Causa) que
     * contiene la relación causa - efecto
     * @param startThemeIndex &Iacute;ndice del tema (Causa) que contiene la
     * relación causa - efecto
     * @param startObjetiveIndex &Iacute;ndice del Objetivo (Causa) que contiene
     * la relación causa - efecto
     * @param itCausalobjective Iterador con los objetivos asociados a la causa
     * - efecto de objetivos/ temas
     * @param base Recurso de SWB utilizado para mostrarlo dentro de la
     * plataforma SWBPortal
     * @param period Elemento de tipo Period, se refiere al periodo seleccionado
     * actualmente en el sitio BSC
     * @param countLine n&uacute;mero de l&iacute;nea a partir de la cual
     * deber&aacute;n crearse las siguientes l&iacute;neas
     * @return objeto de tipo {@code HashMap} con las l&iacute;neas creadas y el
     * contador de l&iacute;neas utilizados
     */
    private HashMap paintThemeObjective(JSONObject jsonArrows, int startPerspectiveIndex,
            int startThemeIndex, int startObjetiveIndex, Iterator itCausalobjective,
            Resource base, Period period, int countLine, String triangleEnd) {
        HashMap map1 = new HashMap();
        Iterator<Entry> itMap;
        StringBuilder sb = new StringBuilder();
        String classLine = ((base.getData("colorRelTO") == null)
                || (base.getData("colorRelTO").trim().length() == 0))
                ? "arrow" : base.getData("colorRelTO");
        while (itCausalobjective.hasNext()) {
            Objective objective = (Objective) itCausalobjective.next();
            if (objective.hasPeriod(period)) {
                HashMap map = new HashMap();
                Perspective perspective = objective.getTheme().getPerspective();
                int finalPerspectiveIndex = findIndexPerspective(perspective, jsonArrows);
                int finalThemeIndex = findIndexTheme(finalPerspectiveIndex, jsonArrows,
                        objective.getTheme());
                int finalObjetiveIndex = findIndexObjective(finalPerspectiveIndex, jsonArrows,
                        finalThemeIndex, objective);
                boolean isFinalTheme = getIndexFinalTheme(finalThemeIndex, jsonArrows,
                        finalPerspectiveIndex);
                boolean hasDifferentiator = (getHeightDifferentiator(jsonArrows, startPerspectiveIndex)
                        .intValue() > 0) ? true : false;
                if (base.getData("perspective" + base.getId() + perspective.getId()) == null) {
                    if (((finalPerspectiveIndex - startPerspectiveIndex) == -1) && (!isFinalTheme)) {
                        if (hasDifferentiator) {
                            map = paintOTH(finalPerspectiveIndex, finalThemeIndex, finalObjetiveIndex,
                                    startPerspectiveIndex, startThemeIndex, jsonArrows, countLine,
                                    classLine, countForRelation, true, triangleEnd);
                        } else {
                            map = paintOTVIndexContinuos(startPerspectiveIndex, startThemeIndex,
                                    startObjetiveIndex, finalPerspectiveIndex, finalThemeIndex, finalObjetiveIndex,
                                    jsonArrows, countLine, classLine, countForRelation, triangleEnd);
                        }
                    } else if (startPerspectiveIndex == finalPerspectiveIndex) {
                        map = paintOTH(finalPerspectiveIndex, finalThemeIndex, finalObjetiveIndex,
                                startPerspectiveIndex, startThemeIndex, jsonArrows, countLine,
                                classLine, countForRelation, true, triangleEnd);
                    } else if (isFinalTheme) {
                        map = paintOTIndexFinalP(finalPerspectiveIndex, finalThemeIndex,
                                finalObjetiveIndex, startPerspectiveIndex, startThemeIndex,
                                jsonArrows, countLine, classLine, countForRelation, false, triangleEnd);
                    } else {
                        map = paintOTH(finalPerspectiveIndex, finalThemeIndex, finalObjetiveIndex,
                                startPerspectiveIndex, startThemeIndex, jsonArrows, countLine,
                                classLine, countForRelation, true, triangleEnd);
                    }
                } else {
                    if (((finalPerspectiveIndex - startPerspectiveIndex) == -1) && (!hasDifferentiator)) {
                        map = paintOTVHIndexContinuos(finalPerspectiveIndex, finalThemeIndex,
                                finalObjetiveIndex, startPerspectiveIndex, startThemeIndex,
                                jsonArrows, countLine, classLine, countForRelation, false, triangleEnd);
                    } else if (startPerspectiveIndex == finalPerspectiveIndex) {
                        map = paintOTV(finalPerspectiveIndex, finalThemeIndex, finalObjetiveIndex,
                                startPerspectiveIndex, startThemeIndex, jsonArrows, countLine,
                                classLine, countForRelation, false, triangleEnd);
                    } else {
                        map = paintOTV(finalPerspectiveIndex, finalThemeIndex, finalObjetiveIndex,
                                startPerspectiveIndex, startThemeIndex, jsonArrows, countLine,
                                classLine, countForRelation, false, triangleEnd);
                    }
                }
                itMap = map.entrySet().iterator();
                while (itMap.hasNext()) {
                    Entry entry = itMap.next();
                    countLine = Integer.parseInt(entry.getKey().toString());
                    StringBuilder sb1 = (StringBuilder) entry.getValue();
                    sb.append(sb1);
                }
                countForRelation++;
            }
        }
        map1.put(countLine, sb);
        return map1;
    }

    /**
     * Pinta la relaci&oacute;n Causa - Efecto entre objetivo - tema
     *
     * @param jsonArrows objeto de tipo {@code JSONObject} que contiene los
     * datos de un balanscorecard a partir del cual buscar&aacute; la
     * perspectiva
     * @param startPerspectiveIndex &Iacute;ndice de la perspectiva (Causa) que
     * contiene la relaci&oacute;n causa - efecto
     * @param startThemeIndex &Iacute;ndice del tema (Causa) que contiene la
     * relaci&oacute;n causa - efecto
     * @param startObjetiveIndex &Iacute;ndice del objetivo (Causa) que contiene
     * la relaci&oacute;n causa - efecto
     * @param itCausalTheme Iterador con los temas asociados a la causa - efecto
     * de objetivos/ temas
     * @param base Recurso de SWB utilizado para mostrarlo dentro de la
     * plataforma SWBPortal
     * @param period Elemento de tipo Period, se refiere al periodo seleccionado
     * actualmente en el sitio BSC
     * @param countLine n&uacute;mero de l&iacute;nea a partir de la cual
     * deber&aacute;n crearse las siguientes l&iacute;neas
     * @return objeto de tipo {@code HashMap} con las l&iacute;neas creadas y el
     * contador de l&iacute;neas utilizados
     */
    private HashMap paintObjectiveTheme(JSONObject jsonArrows, int startPerspectiveIndex,
            int startThemeIndex, int startObjetiveIndex, Iterator itCausalTheme,
            Resource base, Period period, int countLine, Perspective iniPerspe, String triangleEnd) {
        HashMap map1 = new HashMap();
        Iterator<Entry> itMap;
        StringBuilder sb = new StringBuilder();
        String classLine = ((base.getData("colorRelOT") == null)
                || (base.getData("colorRelOT").trim().length() == 0))
                ? "arrow" : base.getData("colorRelOT");
        while (itCausalTheme.hasNext()) {
            Theme theme = (Theme) itCausalTheme.next();
            if (isValidTheme(theme, period)) {
                HashMap map = new HashMap();
                Perspective perspective = theme.getPerspective();
                int finalPerspectiveIndex = findIndexPerspective(perspective, jsonArrows);
                int finalThemeIndex = findIndexTheme(finalPerspectiveIndex, jsonArrows, theme);
                int finalObjetiveIndex = 0;
                boolean isFinalTheme = getIndexFinalTheme(startThemeIndex, jsonArrows,
                        startPerspectiveIndex);
                boolean hasDifferentiator = (getHeightDifferentiator(jsonArrows, finalPerspectiveIndex)
                        .intValue() > 0) ? true : false;

                if (base.getData("perspective" + base.getId() + iniPerspe.getId()) == null) {
                    if (((startPerspectiveIndex - finalPerspectiveIndex) == - 1) && (!isFinalTheme)) {
                        if (hasDifferentiator) {
                            map = paintOTH(startPerspectiveIndex, startThemeIndex, startObjetiveIndex,
                                    finalPerspectiveIndex, finalThemeIndex, jsonArrows, countLine,
                                    classLine, countForRelation, false, triangleEnd);
                        } else {
                            map = paintOTVIndexContinuos(startPerspectiveIndex, startThemeIndex,
                                    startObjetiveIndex, finalPerspectiveIndex, finalThemeIndex, finalObjetiveIndex,
                                    jsonArrows, countLine, classLine, countForRelation, triangleEnd);
                        }
                    } else if (startPerspectiveIndex == finalPerspectiveIndex) {
                        map = paintOTH(startPerspectiveIndex, startThemeIndex, startObjetiveIndex,
                                finalPerspectiveIndex, finalThemeIndex, jsonArrows, countLine,
                                classLine, countForRelation, false, triangleEnd);
                    } else if (isFinalTheme) {
                        map = paintOTIndexFinalP(startPerspectiveIndex, startThemeIndex,
                                startObjetiveIndex, finalPerspectiveIndex, finalThemeIndex,
                                jsonArrows, countLine, classLine, countForRelation, true, triangleEnd);
                    } else {
                        map = paintOTH(startPerspectiveIndex, startThemeIndex, startObjetiveIndex,
                                finalPerspectiveIndex, finalThemeIndex, jsonArrows, countLine,
                                classLine, countForRelation, false, triangleEnd);
                    }
                } else {
                    if (((startPerspectiveIndex - finalPerspectiveIndex) == -1) && (!hasDifferentiator)) {
                        map = paintOTVHIndexContinuos(startPerspectiveIndex, startThemeIndex,
                                startObjetiveIndex, finalPerspectiveIndex, finalThemeIndex,
                                jsonArrows, countLine, classLine, countForRelation, true, triangleEnd);
                    } else if (startPerspectiveIndex == finalPerspectiveIndex) {
                        map = paintOTV(startPerspectiveIndex, startThemeIndex, startObjetiveIndex,
                                finalPerspectiveIndex, finalThemeIndex, jsonArrows, countLine,
                                classLine, countForRelation, true, triangleEnd);
                    } else {
                        map = paintOTV(startPerspectiveIndex, startThemeIndex, startObjetiveIndex,
                                finalPerspectiveIndex, finalThemeIndex, jsonArrows,
                                countLine, classLine, countForRelation, true, triangleEnd);
                    }
                }
                itMap = map.entrySet().iterator();
                while (itMap.hasNext()) {
                    Entry entry = itMap.next();
                    countLine = Integer.parseInt(entry.getKey().toString());
                    StringBuilder sb1 = (StringBuilder) entry.getValue();
                    sb.append(sb1);
                }
                countForRelation++;
            }
        }

        map1.put(countLine, sb);
        return map1;
    }

    /**
     * Pinta la relaci&oacute;n Causa - Efecto entre objetivo - tema, forma
     * Vertical
     *
     * @param startPerspectiveIndex &Iacute;ndice de la perspectiva (Causa) que
     * contiene la relaci&oacute;n causa - efecto
     * @param startThemeIndex &Iacute;ndice del tema (Causa) que contiene la
     * relaci&oacute;n causa - efecto
     * @param startObjetiveIndex &Iacute;ndice del objetivo (Causa) que contiene
     * la relaci&oacute;n causa - efecto
     * @param finalPerspectiveIndex &Iacute;ndice de la perspectiva (Causa) que
     * contiene la relaci&oacute;n causa - efecto
     * @param finalThemeIndex &Iacute;ndice del tema (Efecto) que contiene la
     * relaci&oacute;n causa - efecto
     * @param finalObjetiveIndex &Iacute;ndice del objetivo (Efecto) que
     * contiene la relaci&oacute;n causa - efecto
     * @param jsonArrows objeto de tipo {@code JSONObject} que contiene los
     * datos de un balanscorecard a partir del cual buscar&aacute; la
     * perspectiva
     * @param countLine n&uacute;mero de l&iacute;nea a partir de la cual
     * deber&aacute;n crearse las siguientes l&iacute;neas
     * @param classLine Clase CSS
     * @return objeto de tipo {@code HashMap} con las l&iacute;neas creadas y el
     * contador de l&iacute;neas utilizados
     */
    private HashMap paintOTV(int startPerspectiveIndex, int startThemeIndex, int startObjetiveIndex,
            int finalPerspectiveIndex, int finalThemeIndex, JSONObject jsonArrows, int countLine,
            String classLine, int countForRelation, boolean isValid, String triangleEnd) {
        HashMap map = new HashMap();
        StringBuilder sb = new StringBuilder();
        int initPerspective = startPerspectiveIndex;
        int initTheme = startThemeIndex;
        int initObjective = startObjetiveIndex;
        int finalizePerspective = finalPerspectiveIndex;
        try {
            JSONObject startPers = (JSONObject) jsonArrows.get(initPerspective + "");
            JSONObject arrayTheme = (JSONObject) startPers.get("arrayTheme");
            JSONObject themePer = (JSONObject) arrayTheme.get(initTheme + "");
            JSONObject arraysObjects = (JSONObject) themePer.get("arrayObjective");
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalizePerspective + "");
            JSONObject arrayThemeFinal = (JSONObject) finalPers.get("arrayTheme");
            int[] heightPer = getHeigthPerspectives(initPerspective, jsonArrows);
            boolean useMargins = startPers.getBoolean("margins");
            BigDecimal divContainerColsPer1 = divContainerCo.divide(new BigDecimal(100),
                    MathContext.DECIMAL128);
            BigDecimal divSizeColumnsPer1 = divSizeColum.divide(new BigDecimal(100),
                    MathContext.DECIMAL128);
            BigDecimal incrementTam = new BigDecimal(1);
            if (countForRelation < 15) {
                incrementTam = new BigDecimal(countForRelation).multiply(tam04).
                        multiply(divContainerColsPer1).multiply(divSizeColumnsPer1);
            }

            int[] columns = BSCUtils.getSizeColumnsTheme(arrayTheme.length());
            double restCols = 0;
            for (int i = 0; i < (initTheme - 1); i++) {
                restCols = restCols + columns[i];
            }
            BigDecimal xC1 = new BigDecimal(restCols).multiply(divContainerColsPer1);
            int[] columnsObjs1 = BSCUtils.getSizeColumnsObjective(arraysObjects.length(),
                    columns[initTheme - 1]);
            double restColsObj = 0;
            for (int i = 0; i < (initObjective - 1); i++) {
                restColsObj = restColsObj + columnsObjs1[i];
            }
            BigDecimal xC3 = new BigDecimal(restColsObj).multiply(divContainerColsPer1).
                    multiply(divSizeColumnsPer1);
            BigDecimal xC4 = new BigDecimal(columnsObjs1[initObjective - 1]).divide(tam2,
                    MathContext.DECIMAL128);
            BigDecimal xC = divTitl.add(xC1).add(xC3).add(xC4);
            x1 = xC.doubleValue();
            x2 = x1;
            BigDecimal yA1 = new BigDecimal(heightPer[0]);
            BigDecimal yA3 = new BigDecimal(initPerspective);
            BigDecimal yA4 = yA3.multiply(tam2);

            BigDecimal yA5 = yA3.multiply(tam20).multiply(tam2);
            BigDecimal baseYdiff = getGroupDiffOnly(jsonArrows, initPerspective).multiply(tam20);
            BigDecimal baseYdiff2 = yA5.subtract(baseYdiff);

            BigDecimal yA = tam120.add(yA1).add(baseYdiff2);
            if (useMargins) {
                yA.add(yA4).add(yA3);
            }
            y1 = yA.doubleValue();
            BigDecimal xB1 = tam18.subtract(incrementTam);
            BigDecimal yB = yA.add(xB1);
            y2 = yB.doubleValue();

            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (!isValid) {
                sb.append(paintLines(countLine, x1, x2, y2, y1, classLine));
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
            //segunda linea
            x1 = x2;
            BigDecimal xB = divContainerCo.add(divTitl).add(incrementTam);
            x2 = xB.doubleValue();
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //tercer linea
            x1 = x2;
            x2 = x1;
            y1 = y2;
            /*Calculo Y alto de perspectivas*/
            heightPer = getHeigthPerspectives((finalizePerspective - 1), jsonArrows);
            BigDecimal yC1 = new BigDecimal(heightPer[0]).add(getHeightDifferentiator(jsonArrows,
                    finalizePerspective));
            BigDecimal yC2 = new BigDecimal(heightPer[1]);
            BigDecimal yC3 = new BigDecimal(finalPerspectiveIndex);
            BigDecimal yC4 = yC3.subtract(tam1).multiply(tam2).add(tam1);

            BigDecimal yC5 = yC3.subtract(tam1).multiply(tam20).multiply(tam2);
            baseYdiff = getGroupDiffOnly(jsonArrows, finalPerspectiveIndex).multiply(tam20);
            baseYdiff2 = yC5.subtract(baseYdiff);

            BigDecimal yC = yC1.add(tam120).add(baseYdiff2).add(incrementTam);
            if (useMargins) {
                yC.add(yC2).add(yC4);
            }
            y2 = yC.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //cuarta linea
            x1 = x2;
            columns = BSCUtils.getSizeColumnsTheme(arrayThemeFinal.length());
            restCols = 0;
            for (int i = 0; i < (finalThemeIndex - 1); i++) {
                restCols = restCols + columns[i];
            }
            BigDecimal xD1 = new BigDecimal(restCols).multiply(divContainerColsPer1);
            BigDecimal xD2 = new BigDecimal(columns[finalThemeIndex - 1]).multiply(divContainerColsPer1).
                    multiply(divSizeColumnsPer1);
            BigDecimal xD3 = xD2.divide(tam2, MathContext.DECIMAL128);
            BigDecimal xD = divTitl.add(xD1).add(xD3);
            x2 = xD.doubleValue();
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //quinta linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            BigDecimal yD1 = new BigDecimal(heightPer[0]).add(getHeightDifferentiator(jsonArrows,
                    finalizePerspective));
            BigDecimal yD2 = new BigDecimal(heightPer[1]);
            BigDecimal yD3 = yC3.subtract(tam1).multiply(tam2).add(tam1);

            BigDecimal yD4 = yC3.subtract(tam1).multiply(tam20).multiply(tam2);
            baseYdiff = getGroupDiffOnly(jsonArrows, finalizePerspective).multiply(tam20);
            baseYdiff2 = yD4.subtract(baseYdiff);

            BigDecimal yD = yD1.add(tam120).add(baseYdiff2).add(tam20);
            if (useMargins) {
                yD.add(yD2).add(yD3);
            }
            y2 = yD.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (isValid) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
        } catch (JSONException ex) {
            log.error("Exception in paintOTV: " + ex);
        }
        map.put(countLine, sb);
        return map;
    }

    /**
     * Pinta la relaci&oacute;n Causa - Efecto entre objetivo - tema(s), aplica
     * para forma Vertical / Horizontal.
     *
     * @param startPerspectiveIndex &Iacute;ndice de la perspectiva (Causa) que
     * contiene la relaci&oacute;n causa - efecto
     * @param startThemeIndex &Iacute;ndice del tema (Causa) que contiene la
     * relaci&oacute;n causa - efecto
     * @param startObjetiveIndex &Iacute;ndice del objetivo (Causa) que contiene
     * la relaci&oacute;n causa - efecto
     * @param finalPerspectiveIndex &Iacute;ndice de la perspectiva (Efecto) que
     * contiene la relaci&oacute;n causa - efecto
     * @param finalThemeIndex &Iacute;ndice del tema (Efecto) que contiene la
     * relaci&oacute;n causa - efecto
     * @param finalObjetiveIndex &Iacute;ndice del objetivo (Efecto) que
     * contiene la relaci&oacute;n causa - efecto
     * @param jsonArrows objeto de tipo {@code JSONObject} que contiene los
     * datos de un balanscorecard a partir del cual buscar&aacute; la
     * perspectiva
     * @param countLine n&uacute;mero de l&iacute;nea a partir de la cual
     * deber&aacute;n crearse las siguientes l&iacute;neas
     * @param classLine Clase CSS
     * @return objeto de tipo {@code HashMap} con las l&iacute;neas creadas y el
     * contador de l&iacute;neas utilizados
     */
    private HashMap paintOTVHIndexContinuos(int startPerspectiveIndex, int startThemeIndex,
            int startObjetiveIndex, int finalPerspectiveIndex, int finalThemeIndex,
            JSONObject jsonArrows, int countLine, String classLine,
            int countForRelation, boolean isValid, String triangleEnd) {
        HashMap map = new HashMap();
        StringBuilder sb = new StringBuilder();
        int initPerspective = startPerspectiveIndex;
        int initTheme = startThemeIndex;
        int initObjective = startObjetiveIndex;
        int finalizePerspective = finalPerspectiveIndex;
        try {
            JSONObject startPers = (JSONObject) jsonArrows.get(initPerspective + "");
            JSONObject arrayTheme = (JSONObject) startPers.get("arrayTheme");
            JSONObject themePer = (JSONObject) arrayTheme.get(initTheme + "");
            JSONObject arraysObjects = (JSONObject) themePer.get("arrayObjective");
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalizePerspective + "");
            JSONObject arrayThemeFinal = (JSONObject) finalPers.get("arrayTheme");
            int[] heightPer = getHeigthPerspectives(initPerspective, jsonArrows);
            boolean useMargins = startPers.getBoolean("margins");
            BigDecimal divContainerColsPer1 = divContainerCo.divide(new BigDecimal(100),
                    MathContext.DECIMAL128);
            BigDecimal divSizeColumnsPer1 = divSizeColum.divide(new BigDecimal(100),
                    MathContext.DECIMAL128);
            BigDecimal incrementTam = new BigDecimal(1);
            if (countForRelation < 15) {
                incrementTam = new BigDecimal(countForRelation).multiply(tam04).
                        multiply(divContainerColsPer1).multiply(divSizeColumnsPer1);
            }

            int[] columns = BSCUtils.getSizeColumnsTheme(arrayTheme.length());
            double restCols = 0;
            for (int i = 0; i < (initTheme - 1); i++) {
                restCols = restCols + columns[i];
            }
            //primer linea
            BigDecimal xC1 = new BigDecimal(restCols).multiply(divContainerColsPer1);
            int[] columnsObjs1 = BSCUtils.getSizeColumnsObjective(arraysObjects.length(),
                    columns[initTheme - 1]);
            double restColsObj = 0;
            for (int i = 0; i < (initObjective - 1); i++) {
                restColsObj = restColsObj + columnsObjs1[i];
            }
            BigDecimal xC3 = new BigDecimal(restColsObj).multiply(divContainerColsPer1).
                    multiply(divSizeColumnsPer1);
            BigDecimal xC4 = new BigDecimal(columnsObjs1[initObjective - 1]).divide(tam2,
                    MathContext.DECIMAL128);
            BigDecimal xC = divTitl.add(xC1).add(xC3).add(xC4);
            x1 = xC.doubleValue();
            x2 = x1;
            BigDecimal yA1 = new BigDecimal(heightPer[0]);
            BigDecimal yA3 = new BigDecimal(initPerspective);
            BigDecimal yA4 = yA3.multiply(tam2);

            BigDecimal yA5 = yA3.multiply(tam20).multiply(tam2);
            BigDecimal baseYdiff = getGroupDiffOnly(jsonArrows, initPerspective).multiply(tam20);
            BigDecimal baseYdiff2 = yA5.subtract(baseYdiff);

            BigDecimal yA = tam120.add(yA1).add(baseYdiff2);
            if (useMargins) {
                yA.add(yA4).add(yA3);
            }
            y1 = yA.doubleValue();
            BigDecimal xB1 = tam18.subtract(incrementTam);
            BigDecimal yB = yA.add(xB1);
            y2 = yB.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (!isValid) {
                sb.append(paintLines(countLine, x1, x2, y2, y1, classLine));
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
            //segunda linea
            x1 = x2;
            columns = BSCUtils.getSizeColumnsTheme(arrayThemeFinal.length());
            restCols = 0;
            for (int i = 0; i < (finalThemeIndex - 1); i++) {
                restCols = restCols + columns[i];
            }
            BigDecimal xD1 = new BigDecimal(restCols).multiply(divContainerColsPer1);
            BigDecimal xD2 = new BigDecimal(columns[finalThemeIndex - 1]).multiply(divContainerColsPer1).
                    multiply(divSizeColumnsPer1);
            BigDecimal xD3 = xD2.divide(tam2, MathContext.DECIMAL128);
            BigDecimal xD = divTitl.add(xD1).add(xD3);
            x2 = xD.doubleValue();
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //tercer linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            BigDecimal yD0 = new BigDecimal(finalPerspectiveIndex);
            BigDecimal yD1 = new BigDecimal(heightPer[0]);
            BigDecimal yD2 = new BigDecimal(heightPer[1]);
            BigDecimal yD3 = yD0.subtract(tam1).multiply(tam2).add(tam1);

            BigDecimal yD4 = yD0.subtract(tam1).multiply(tam20).multiply(tam2);
            baseYdiff = getGroupDiffOnly(jsonArrows, finalPerspectiveIndex).multiply(tam20);
            baseYdiff2 = yD4.subtract(baseYdiff);

            BigDecimal yD = yD1.add(tam120).add(baseYdiff2).add(tam20);
            if (useMargins) {
                yD.add(yD2).add(yD3);
            }
            y2 = yD.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (isValid) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
        } catch (JSONException ex) {
            log.error("Exception in paintOTVHIndexContinuos: " + ex);
        }
        map.put(countLine, sb);
        return map;
    }

    /**
     * Pinta la relaci&oacute;n Causa - Efecto entre objetivo - tema cuando el
     * objetivo esta al final de la perspectiva.
     *
     * @param startPerspectiveIndex &Iacute;ndice de la perspectiva (Causa) que
     * contiene la relaci&oacute;n causa - efecto
     * @param startThemeIndex &Iacute;ndice del tema (Causa) que contiene la
     * relaci&oacute;n causa - efecto
     * @param startObjetiveIndex &Iacute;ndice del obejtivo (Causa) que contiene
     * la relaci&oacute;n causa - efecto
     * @param finalPerspectiveIndex &Iacute;ndice de la perspectiva (Efecto) que
     * contiene la relaci&oacute;n causa - efecto
     * @param finalThemeIndex &Iacute;ndice del tema (Efecto) que contiene la
     * relaci&oacute;n causa - efecto
     * @param finalObjetiveIndex &Iacute;ndice del obejtivo (Efecto) que
     * contiene la relaci&oacute;n causa - efecto
     * @param jsonArrows objeto de tipo {@code JSONObject} que contiene los
     * datos de un balanscorecard a partir del cual buscar&aacute; la
     * perspectiva
     * @param countLine n&uacute;mero de l&iacute;nea a partir de la cual
     * deber&aacute;n crearse las siguientes l&iacute;neas
     * @param classLine Clase CSS
     * @return objeto de tipo {@code HashMap} con las l&iacute;neas creadas y el
     * contador de l&iacute;neas utilizados
     */
    private HashMap paintOTIndexFinalP(int initPerspective, int initTheme,
            int initObjective, int finalizePerspective, int finalThemeIndex,
            JSONObject jsonArrows, int countLine, String classLine,
            int countForRelation, boolean relationObjective, String triangleEnd) {
        HashMap map = new HashMap();
        StringBuilder sb = new StringBuilder();
        try {
            JSONObject startPers = (JSONObject) jsonArrows.get(initPerspective + "");
            JSONObject arrayTheme = (JSONObject) startPers.get("arrayTheme");
            JSONObject themePer = (JSONObject) arrayTheme.get(initTheme + "");
            JSONObject arraysObjects = (JSONObject) themePer.get("arrayObjective");
            JSONObject arrayObjective = (JSONObject) arraysObjects.get(initObjective + "");
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalizePerspective + "");
            JSONObject arrayThemeFinal = (JSONObject) finalPers.get("arrayTheme");
            int[] heightPer = getHeigthPerspectives(initPerspective - 1, jsonArrows);
            boolean useMargins = startPers.getBoolean("margins");
            int noCols = arrayTheme.length();
            if (noCols == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            if (arrayObjective.length() == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            double restCols = 0;
            BigDecimal divContainerColsPer1 = divContainerCo.divide(new BigDecimal(100),
                    MathContext.DECIMAL128);
            BigDecimal divSizeColumnsPer1 = divSizeColum.divide(new BigDecimal(100),
                    MathContext.DECIMAL128);
            BigDecimal incrementTam = new BigDecimal(1);
            if (countForRelation < 15) {
                incrementTam = new BigDecimal(countForRelation).multiply(tam04).
                        multiply(divContainerColsPer1);
            }
            int[] columns = BSCUtils.getSizeColumnsTheme(arrayTheme.length());
            for (int i = 0; i < (initTheme - 1); i++) {
                restCols = restCols + columns[i];
            }
            //primer linea
            BigDecimal xA = divTitl.add(divContainerCo);
            x1 = xA.doubleValue();
            BigDecimal xB = xA.add(incrementTam);
            x2 = xB.doubleValue();
            BigDecimal yA1 = new BigDecimal(heightPer[0]);
            BigDecimal yA2 = new BigDecimal(heightPer[1]);
            BigDecimal yA3 = new BigDecimal(initPerspective);
            BigDecimal yA3a = new BigDecimal(initObjective);
            BigDecimal yA4 = new BigDecimal(startPers.getInt("height")).
                    subtract(getHeightDifferentiator(jsonArrows, initPerspective))
                    .divide(new BigDecimal(arraysObjects.length()), MathContext.DECIMAL128);
            BigDecimal yA5 = yA3.subtract(tam1).multiply(tam2).add(yA2);

            BigDecimal yA6 = yA3.multiply(tam20).multiply(tam2);
            BigDecimal baseYdiff = getGroupDiffOnly(jsonArrows, initPerspective).multiply(tam20);
            BigDecimal baseYdiff2 = yA6.subtract(baseYdiff);

            BigDecimal yA7 = yA3a.subtract(tam1).multiply(yA4);
            BigDecimal yA8 = yA4.divide(tam2, MathContext.DECIMAL128);
            BigDecimal yA = yA1.add(baseYdiff2).add(yA7).add(yA8).add(tam120).
                    add(incrementTam);
            if (useMargins) {
                yA.add(yA5).add(yA3a);
            }
            y1 = yA.doubleValue();
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (!relationObjective) {
                sb.append(paintLines(countLine, x2, x1, y1, y2, classLine));
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
            //segunda linea
            x1 = x2;
            x2 = x1;
            y1 = y2;
            heightPer = getHeigthPerspectives((finalizePerspective - 1), jsonArrows);
            BigDecimal yB1 = new BigDecimal(heightPer[0]).add(getHeightDifferentiator(jsonArrows,
                    finalizePerspective));
            BigDecimal yB2 = new BigDecimal(heightPer[1]);
            BigDecimal yB3 = new BigDecimal(finalizePerspective);
            BigDecimal yB4 = yB3.subtract(tam1).multiply(tam2).add(tam1);

            BigDecimal yB5 = yB3.subtract(tam1).multiply(tam20).multiply(tam2);
            baseYdiff = getGroupDiffOnly(jsonArrows, finalizePerspective).multiply(tam20);
            baseYdiff2 = yB5.subtract(baseYdiff);

            BigDecimal yB = yB1.add(tam120).add(baseYdiff2).add(incrementTam);
            if (useMargins) {
                yB.add(yB2).add(yB4);
            }
            y2 = yB.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //tercer linea
            columns = BSCUtils.getSizeColumnsTheme(arrayThemeFinal.length());
            restCols = 0;
            for (int i = 0; i < (finalThemeIndex - 1); i++) {
                restCols = restCols + columns[i];
            }
            x1 = x2;
            BigDecimal xD1 = new BigDecimal(restCols).multiply(divContainerColsPer1);
            BigDecimal xD2 = new BigDecimal(columns[finalThemeIndex - 1]).multiply(divContainerColsPer1).
                    multiply(divSizeColumnsPer1);
            BigDecimal xD3 = xD2.divide(tam2, MathContext.DECIMAL128);
            BigDecimal xD = divTitl.add(xD1).add(xD3);
            x2 = xD.doubleValue();
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //cuarta linea
            x1 = x2;
            x2 = x1;
            y1 = y2;
            BigDecimal yC1 = new BigDecimal(heightPer[0]).add(getHeightDifferentiator(jsonArrows,
                    finalizePerspective));
            BigDecimal yC0 = new BigDecimal(finalizePerspective);
            BigDecimal yC2 = new BigDecimal(heightPer[1]);
            BigDecimal yC3 = yC0.subtract(tam1).multiply(tam2).add(tam1);

            BigDecimal yC4 = yC0.subtract(tam1).multiply(tam20).multiply(tam2);
            baseYdiff = getGroupDiffOnly(jsonArrows, finalizePerspective).multiply(tam20);
            baseYdiff2 = yC4.subtract(baseYdiff);

            BigDecimal yC = yC1.add(tam120).add(baseYdiff2).add(tam20);
            if (useMargins) {
                yC.add(yC2).add(yC3);
            }
            y2 = yC.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (relationObjective) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
        } catch (JSONException ex) {
            log.error("Exception in paintOTIndexFinalP: " + ex);
        }
        map.put(countLine, sb);
        return map;
    }

    /**
     * Pinta la relaci&oacute;n Causa - Efecto entre objetivo - tema, forma
     * vertical con &icute;ndice de perspectivas continuas
     *
     * @param startPerspectiveIndex &Iacute;ndice de la perspectiva (Causa) que
     * contiene la relaci&oacute;n causa - efecto
     * @param startThemeIndex &Iacute;ndice del tema (Causa) que contiene la
     * relaci&oacute;n causa - efecto
     * @param startObjetiveIndex &Iacute;ndice del objetivo (Causa) que contiene
     * la relaci&oacute;n causa - efecto
     * @param finalPerspectiveIndex &Iacute;ndice de la perspectiva (Efecto) que
     * contiene la relaci&oacute;n causa - efecto
     * @param finalThemeIndex &Iacute;ndice del tema (Efecto) que contiene la
     * relaci&oacute;n causa - efecto
     * @param finalObjetiveIndex &Iacute;ndice del objetivo (Efecto) que
     * contiene la relaci&oacute;n causa - efecto
     * @param jsonArrows objeto de tipo {@code JSONObject} que contiene los
     * datos de un balanscorecard a partir del cual buscar&aacute; la
     * perspectiva
     * @param countLine n&uacute;mero de l&iacute;nea a partir de la cual
     * deber&aacute;n crearse las siguientes l&iacute;neas
     * @param classLine Clase CSS
     * @return objeto de tipo {@code HashMap} con las l&iacute;neas creadas y el
     * contador de l&iacute;neas utilizados
     */
    private HashMap paintOTVIndexContinuos(int startPerspectiveIndex, int startThemeIndex,
            int startObjetiveIndex, int finalPerspectiveIndex, int finalThemeIndex,
            int finalObjetiveIndex, JSONObject jsonArrows, int countLine, String classLine,
            int countForRelation, String triangleEnd) {
        HashMap map = new HashMap();
        StringBuilder sb = new StringBuilder();
        int initPerspective = startPerspectiveIndex;
        int initTheme = startThemeIndex;
        int initObjective = startObjetiveIndex;
        int finalizePerspective = finalPerspectiveIndex;
        int finalizeTheme = finalThemeIndex;
        if ((finalPerspectiveIndex < startPerspectiveIndex) || (startObjetiveIndex == 0)) {
            initPerspective = finalPerspectiveIndex;
            initTheme = finalThemeIndex;
            initObjective = finalObjetiveIndex;
            finalizePerspective = startPerspectiveIndex;
            finalizeTheme = startThemeIndex;
        }
        try {
            JSONObject startPers = (JSONObject) jsonArrows.get(initPerspective + "");
            JSONObject arrayTheme = (JSONObject) startPers.get("arrayTheme");
            JSONObject themePer = (JSONObject) arrayTheme.get(initTheme + "");
            JSONObject arraysObjects = (JSONObject) themePer.get("arrayObjective");
            JSONObject arrayObjective = (JSONObject) arraysObjects.get(initObjective + "");
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalizePerspective + "");
            JSONObject arrayThemeFinal = (JSONObject) finalPers.get("arrayTheme");
            BigDecimal divContainerColsPer1 = divContainerCo.divide(new BigDecimal(100),
                    MathContext.DECIMAL128);
            BigDecimal divSizeColumnsPer1 = divSizeColum.divide(new BigDecimal(100),
                    MathContext.DECIMAL128);
            BigDecimal incrementTam = new BigDecimal(1);
            boolean useMargins = startPers.getBoolean("margins");
            if (countForRelation < 15) {
                incrementTam = new BigDecimal(countForRelation).multiply(tam04).
                        multiply(divContainerColsPer1);
            }
            int[] heightPer = getHeigthPerspectives(initPerspective - 1, jsonArrows);
            if (arrayObjective.length() == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            int[] columns = BSCUtils.getSizeColumnsTheme(arrayTheme.length());
            double restCols = 0;
            for (int i = 0; i < (initTheme - 1); i++) {
                restCols = restCols + columns[i];
            }

            BigDecimal heightObjective1 = new BigDecimal(startPers.getInt("height")).
                    subtract(getHeightDifferentiator(jsonArrows, initPerspective)).
                    divide(new BigDecimal(arraysObjects.length()), MathContext.DECIMAL128);
            //primer linea
            BigDecimal xA2 = new BigDecimal(restCols).multiply(divContainerColsPer1);
            BigDecimal xA4 = new BigDecimal(columns[initTheme - 1]).multiply(divContainerColsPer1).
                    multiply(divSizeColumnsPer1);
            BigDecimal xA = divTitl.add(xA2).add(xA4);
            x1 = xA.doubleValue();
            BigDecimal xB = xA.add(incrementTam);
            x2 = xB.doubleValue();
            BigDecimal yA1 = new BigDecimal(heightPer[0]).add(getHeightDifferentiator(jsonArrows,
                    initPerspective));
            BigDecimal yA2 = new BigDecimal(heightPer[1]);
            BigDecimal yA3 = new BigDecimal(initPerspective);
            BigDecimal yA4 = yA3.subtract(tam1).multiply(tam2).add(yA2);

            BigDecimal yA5 = yA3.multiply(tam20).multiply(tam2);
            BigDecimal baseYdiff = getGroupDiffOnly(jsonArrows, initPerspective).multiply(tam20);
            BigDecimal baseYdiff2 = yA5.subtract(baseYdiff);

            BigDecimal yA6 = new BigDecimal(initObjective);
            BigDecimal yA7 = yA6.subtract(tam1).multiply(heightObjective1);
            BigDecimal yA8 = heightObjective1.divide(tam2, MathContext.DECIMAL128);
            BigDecimal yA = tam120.add(yA1).add(baseYdiff2).
                    add(incrementTam).add(yA7).add(yA8);
            if (useMargins) {
                yA.add(yA4).add(yA3);
            }
            y1 = yA.doubleValue();
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex < startPerspectiveIndex) {
                sb.append(paintLines(countLine, x2, x1, y1, y2, classLine));
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
            //segunda linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            BigDecimal yB1 = new BigDecimal(heightPer[0]);
            BigDecimal yB2 = new BigDecimal(heightPer[1]);
            BigDecimal yB3 = new BigDecimal(initPerspective);
            BigDecimal yB4 = yB3.subtract(tam1).multiply(tam2).add(yB2);

            BigDecimal yB5 = yB3.multiply(tam20).multiply(tam2);
            baseYdiff = getGroupDiffOnly(jsonArrows, finalizePerspective - 1).multiply(tam20);
            baseYdiff2 = yB5.subtract(baseYdiff);

            BigDecimal yB = tam120.add(yB1).add(baseYdiff2).add(incrementTam);
            if (useMargins) {
                yB.add(yB4).add(yB3);
            }
            y2 = yB.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //tercer linea
            x1 = x2;
            columns = BSCUtils.getSizeColumnsTheme(arrayThemeFinal.length());
            restCols = 0;
            for (int i = 0; i < (finalizeTheme - 1); i++) {
                restCols = restCols + columns[i];
            }
            BigDecimal xC1 = new BigDecimal(restCols).multiply(divContainerColsPer1);
            BigDecimal xC2 = new BigDecimal(columns[finalizeTheme - 1]).divide(tam2,
                    MathContext.DECIMAL128).multiply(divContainerColsPer1).multiply(divSizeColumnsPer1);
            BigDecimal xC = divTitl.add(xC1).add(xC2).add(incrementTam);
            x2 = xC.doubleValue();
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //cuarta linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            BigDecimal yC1 = new BigDecimal(heightPer[0]);
            BigDecimal yC0 = new BigDecimal(finalizePerspective);
            BigDecimal yC2 = new BigDecimal(heightPer[1]);
            BigDecimal yC3 = yC0.subtract(tam1).multiply(tam2).add(tam1);

            BigDecimal yC4 = yC0.subtract(tam1).multiply(tam20).multiply(tam2);
            baseYdiff = getGroupDiffOnly(jsonArrows, finalizePerspective).multiply(tam20);
            baseYdiff2 = yC4.subtract(baseYdiff);

            BigDecimal yC = yC1.add(tam120).add(baseYdiff2).add(tam20);
            if (useMargins) {
                yC.add(yC2).add(yC3);
            }
            y2 = yC.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex > startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
        } catch (JSONException ex) {
            log.error("Exception in paintOTVIndexContinuos: " + ex);
        }
        map.put(countLine, sb);
        return map;
    }

    /**
     * Pinta la relaci&oacute;n Causa - Efecto entre objetivo - tema, en forma
     * Horizontal
     *
     * @param startPerspectiveIndex &Iacute;ndice de la perspectiva (Causa) que
     * contiene la relaci&oacute;n causa - efecto
     * @param startThemeIndex &Iacute;ndice del tema (Causa) que contiene la
     * relaci&oacute;n causa - efecto
     * @param startObjetiveIndex &Iacute;ndice del objetivo (Causa) que contiene
     * la relaci&oacute;n causa - efecto
     * @param finalPerspectiveIndex &Iacute;ndice de la perspectiva (Efecto) que
     * contiene la relaci&oacute;n causa - efecto
     * @param finalThemeIndex &Iacute;ndice del tema (Efecto) que contiene la
     * relaci&oacute;n causa - efecto
     * @param finalObjetiveIndex &Iacute;ndice del objetivo (Efecto) que
     * contiene la relaci&oacute;n causa - efecto
     * @param jsonArrows objeto de tipo {@code JSONObject} que contiene los
     * datos de un balanscorecard a partir del cual buscar&aacute; la
     * perspectiva
     * @param countLine n&uacute;mero de l&iacute;nea a partir de la cual
     * deber&aacute;n crearse las siguientes l&iacute;neas
     * @param classLine Clase CSS
     * @return objeto de tipo {@code HashMap} con las l&iacute;neas creadas y el
     * contador de l&iacute;neas utilizados
     */
    private HashMap paintOTH(int initPerspective, int initTheme, int initObjective,
            int finalizePerspective, int finalThemeIndex, JSONObject jsonArrows, int countLine,
            String classLine, int countForRelation, boolean isTheme, String triangleEnd) {
        HashMap map = new HashMap();
        StringBuilder sb = new StringBuilder();
        try {
            JSONObject startPers = (JSONObject) jsonArrows.get(initPerspective + "");
            JSONObject arrayTheme = (JSONObject) startPers.get("arrayTheme");
            JSONObject themePer = (JSONObject) arrayTheme.get(initTheme + "");
            JSONObject arraysObjects = (JSONObject) themePer.get("arrayObjective");
            JSONObject arrayObjective = (JSONObject) arraysObjects.get(initObjective + "");
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalizePerspective + "");
            JSONObject arrayThemeFinal = (JSONObject) finalPers.get("arrayTheme");
            int[] heightPer = getHeigthPerspectives(initPerspective - 1, jsonArrows);
            boolean useMargins = startPers.getBoolean("margins");
            BigDecimal divContainerColsPer1 = divContainerCo.divide(new BigDecimal(100),
                    MathContext.DECIMAL128);
            BigDecimal divSizeColumnsPer1 = divSizeColum.divide(new BigDecimal(100),
                    MathContext.DECIMAL128);
            BigDecimal heightObjective1 = new BigDecimal(startPers.getInt("height")).
                    subtract(getHeightDifferentiator(jsonArrows, initPerspective)).
                    divide(new BigDecimal(arraysObjects.length()), MathContext.DECIMAL128);
            BigDecimal incrementTam = new BigDecimal(1);
            if (countForRelation < 15) {
                incrementTam = new BigDecimal(countForRelation).multiply(tam04).
                        multiply(divContainerColsPer1);
            }
            int[] columns = BSCUtils.getSizeColumnsTheme(arrayTheme.length());
            double restCols = 0;
            for (int i = 0; i < (initTheme - 1); i++) {
                restCols = restCols + columns[i];
            }
            if (arrayObjective.length() == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            //primer linea
            BigDecimal xA2 = new BigDecimal(restCols).multiply(divContainerColsPer1);
            BigDecimal xA4 = new BigDecimal(columns[initTheme - 1]).multiply(divContainerColsPer1).
                    multiply(divSizeColumnsPer1);
            BigDecimal xA = divTitl.add(xA2).add(xA4);
            x1 = xA.doubleValue();
            BigDecimal xB = xA.add(incrementTam);
            x2 = xB.doubleValue();
            BigDecimal yA1 = new BigDecimal(heightPer[0]).add(getHeightDifferentiator(jsonArrows,
                    initPerspective));
            BigDecimal yA2 = new BigDecimal(heightPer[1]);
            BigDecimal yA3 = new BigDecimal(initPerspective);
            BigDecimal yA4 = yA3.subtract(tam1).multiply(tam2).add(yA2);

            BigDecimal yA5 = yA3.multiply(tam20).multiply(tam2);
            BigDecimal baseYdiff = getGroupDiffOnly(jsonArrows, initPerspective).multiply(tam20);
            BigDecimal baseYdiff2 = yA5.subtract(baseYdiff);

            BigDecimal yA6 = new BigDecimal(initObjective);
            BigDecimal yA7 = yA6.subtract(tam1).multiply(heightObjective1);
            BigDecimal yA8 = heightObjective1.divide(tam2, MathContext.DECIMAL128);
            BigDecimal yA = tam120.add(yA1).add(baseYdiff2).add(incrementTam).add(yA7).add(yA8);
            if (useMargins) {
                yA.add(yA4).add(yA3);
            }
            y1 = yA.doubleValue();
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (isTheme) {
                sb.append(paintLines(countLine, x2, x1, y1, y2, classLine));
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
            //segunda linea
            heightPer = getHeigthPerspectives(initPerspective, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            BigDecimal yB1 = new BigDecimal(heightPer[0]);
            BigDecimal yB2 = new BigDecimal(heightPer[1]);
            BigDecimal yB3 = new BigDecimal(initPerspective);
            BigDecimal yB4 = yB3.subtract(tam1).multiply(tam2).add(yB2);

            BigDecimal yB5 = yB3.multiply(tam20).multiply(tam2);
            baseYdiff = getGroupDiffOnly(jsonArrows, initPerspective).multiply(tam20);
            baseYdiff2 = yB5.subtract(baseYdiff);

            BigDecimal yB6 = tam18.subtract(incrementTam);
            BigDecimal yB = tam120.add(yB1).add(baseYdiff2).add(yB6);
            if (useMargins) {
                yB.add(yB4).add(yB3);
            }
            y2 = yB.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //tercer linea
            x1 = x2;
            BigDecimal yC1 = divTitl.add(divContainerCo).add(incrementTam);
            x2 = yC1.doubleValue();
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //cuarta linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            BigDecimal yD1 = new BigDecimal(heightPer[0]).add(getHeightDifferentiator(jsonArrows,
                    finalizePerspective));
            BigDecimal yD2 = new BigDecimal(heightPer[1]);
            BigDecimal yD3 = new BigDecimal(finalizePerspective);
            BigDecimal yD4 = yD3.subtract(tam1).multiply(tam2).add(tam1);

            BigDecimal yD5 = yD3.subtract(tam1).multiply(tam20).multiply(tam2);
            baseYdiff = getGroupDiffOnly(jsonArrows, finalizePerspective).multiply(tam20);
            baseYdiff2 = yD5.subtract(baseYdiff);

            BigDecimal yD = yD1.add(tam120).add(baseYdiff2).add(incrementTam);
            if (useMargins) {
                yD.add(yD2).add(yD4);
            }
            y2 = yD.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //quinta linea
            x1 = x2;
            columns = BSCUtils.getSizeColumnsTheme(arrayThemeFinal.length());
            restCols = 0;
            for (int i = 0; i < (finalThemeIndex - 1); i++) {
                restCols = restCols + columns[i];
            }
            BigDecimal xD1 = new BigDecimal(restCols).multiply(divContainerColsPer1);
            BigDecimal xD2 = new BigDecimal(columns[finalThemeIndex - 1]).multiply(divContainerColsPer1).
                    multiply(divSizeColumnsPer1);
            BigDecimal xD3 = xD2.divide(tam2, MathContext.DECIMAL128);
            BigDecimal xD = divTitl.add(xD1).add(xD3).add(incrementTam);
            x2 = xD.doubleValue();
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //sexta linea
            x1 = x2;
            x2 = x1;
            y1 = y2;
            BigDecimal yE1 = new BigDecimal(heightPer[0]).add(getHeightDifferentiator(jsonArrows,
                    finalizePerspective));
            BigDecimal yE0 = new BigDecimal(finalizePerspective);
            BigDecimal yE2 = new BigDecimal(heightPer[1]);
            BigDecimal yE3 = yE0.subtract(tam1).multiply(tam2).add(tam1);

            BigDecimal yE4 = yE0.subtract(tam1).multiply(tam20).multiply(tam2);
            baseYdiff = getGroupDiffOnly(jsonArrows, finalizePerspective).multiply(tam20);
            baseYdiff2 = yE4.subtract(baseYdiff);

            BigDecimal yE = yE1.add(tam120).add(baseYdiff2).add(tam20);
            if (useMargins) {
                yE.add(yE2).add(yE3);
            }
            y2 = yE.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (!isTheme) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
        } catch (JSONException ex) {
            log.error("Exception in paintOTH:" + ex);
        }
        map.put(countLine, sb);
        return map;
    }

    /**
     * Pinta la relaci&oacute;n Causa - Efecto entre objetivos
     *
     * @param jsonArrows objeto de tipo {@code JSONObject} que contiene los
     * datos de un balanscorecard a partir del cual buscar&aacute; la
     * perspectiva
     * @param startPerspectiveIndex &Iacute;ndice de la perspectiva (Causa) que
     * contiene la relaci&oacute;n causa - efecto
     * @param startThemeIndex &Iacute;ndice del tema (Causa) que contiene la
     * relaci&oacute;n causa - efecto
     * @param startObjetiveIndex &Iacute;ndice del objetivo (Causa) que contiene
     * la relaci&oacute;n causa - efecto
     * @param base Recurso de SWB utilizado para mostrarlo dentro de la
     * plataforma SWBPortal
     * @param period Elemento de tipo Period, se refiere al periodo seleccionado
     * actualmente en el sitio BSC
     * @param itCausalobjective Iterador con los objetivos asociados a la causa
     * - efecto de objetivos/ temas
     * @param objStart objeto de tipo {@code Objective} definido como el
     * objetivo Causa.
     * @param countLine n&uacute;mero de l&iacute;nea a partir de la cual
     * deber&aacute;n crearse las siguientes l&iacute;neas
     * @return objeto de tipo {@code HashMap} con las l&iacute;neas creadas y el
     * contador de l&iacute;neas utilizados
     */
    private HashMap paintObjectiveObjective(JSONObject jsonArrows, int startPerspectiveIndex,
            int startThemeIndex, int startObjetiveIndex, Resource base, Period period,
            Iterator itCausalobjective, Objective objStart, int countLine, String triangleEnd) {
        HashMap map1 = new HashMap();
        StringBuilder sb = new StringBuilder();
        Perspective persStart = objStart.getTheme().getPerspective();
        String classLine = ((base.getData("colorRelTO") == null)
                || (base.getData("colorRelTO").trim().length() == 0))
                ? "arrow" : base.getData("colorRelTO");
        while (itCausalobjective.hasNext()) {
            Objective objective = (Objective) itCausalobjective.next();
            if (objective.hasPeriod(period)) {
                HashMap map = new HashMap();
                Perspective perspective = objective.getTheme().getPerspective();
                int finalPerspectiveIndex = findIndexPerspective(perspective, jsonArrows);
                int finalThemeIndex = findIndexTheme(finalPerspectiveIndex, jsonArrows,
                        objective.getTheme());
                int finalObjetiveIndex = findIndexObjective(finalPerspectiveIndex, jsonArrows,
                        finalThemeIndex, objective);
                boolean isFinalTheme = getIndexFinalTheme(finalThemeIndex, jsonArrows,
                        finalPerspectiveIndex);
                boolean isStartTheme = getIndexFinalTheme(startThemeIndex, jsonArrows,
                        startPerspectiveIndex);
                if ((base.getData("perspective" + base.getId() + perspective.getId()) == null)
                        && (base.getData("perspective" + base.getId() + persStart.getId()) == null)) {
                    if (startPerspectiveIndex == finalPerspectiveIndex) {
                        map = paintOOVSameP(finalPerspectiveIndex, startThemeIndex, startObjetiveIndex,
                                finalThemeIndex, finalObjetiveIndex, countLine, classLine, jsonArrows, triangleEnd);
                    } else if (isFinalTheme && isStartTheme) {
                        map = paintOOIndexContinuos(startPerspectiveIndex, startThemeIndex,
                                startObjetiveIndex, finalPerspectiveIndex, finalThemeIndex,
                                finalObjetiveIndex, jsonArrows, countLine, classLine, countForRelation,
                                triangleEnd);
                    } else if ((isFinalTheme) || (isStartTheme)) {
                        map = paintOOIndexFinalP(startPerspectiveIndex, startThemeIndex,
                                startObjetiveIndex, finalPerspectiveIndex, finalThemeIndex,
                                finalObjetiveIndex, isFinalTheme, jsonArrows, countLine, classLine,
                                countForRelation, triangleEnd);
                    } else {
                        map = paintOO(startPerspectiveIndex, startThemeIndex, startObjetiveIndex,
                                finalPerspectiveIndex, finalThemeIndex, finalObjetiveIndex,
                                jsonArrows, countLine, classLine, countForRelation, triangleEnd);
                    }
                } else if ((base.getData("perspective" + base.getId() + perspective.getId()) != null)
                        && (base.getData("perspective" + base.getId() + persStart.getId()) != null)) {
                    if (startPerspectiveIndex == finalPerspectiveIndex) {
                        map = paintOOHSameP(startPerspectiveIndex, startThemeIndex, startObjetiveIndex,
                                finalThemeIndex, finalObjetiveIndex, countLine, classLine, jsonArrows, triangleEnd);
                    } else {
                        map = paintOOHH(startPerspectiveIndex, startThemeIndex, startObjetiveIndex,
                                finalPerspectiveIndex, finalThemeIndex, finalObjetiveIndex, jsonArrows,
                                countLine, classLine, countForRelation, triangleEnd);
                    }
                } else {
                    boolean perspecIni = base.getData("perspective" + base.getId() + persStart.getId())
                            == null ? true : false;
                    boolean perspecFin = base.getData("perspective" + base.getId() + perspective.getId())
                            == null ? true : false;
                    if ((isFinalTheme && perspecFin) || (isStartTheme && perspecIni)) {
                        boolean isValid = (isStartTheme && perspecIni) ? false : true;
                        map = paintOOH_VSame(startPerspectiveIndex, startThemeIndex, startObjetiveIndex,
                                finalPerspectiveIndex, finalThemeIndex, finalObjetiveIndex, jsonArrows,
                                countLine, classLine, countForRelation, isValid, triangleEnd);
                    } else {
                        boolean isValid = (perspecIni) ? false : true;
                        map = paintOOH_V(startPerspectiveIndex, startThemeIndex, startObjetiveIndex,
                                finalPerspectiveIndex, finalThemeIndex, finalObjetiveIndex, jsonArrows,
                                countLine, classLine, countForRelation, isValid, triangleEnd);
                    }
                }
                Iterator<Entry> itMap = map.entrySet().iterator();
                while (itMap.hasNext()) {
                    Entry entry = itMap.next();
                    countLine = Integer.parseInt(entry.getKey().toString());
                    StringBuilder sb1 = (StringBuilder) entry.getValue();
                    sb.append(sb1);
                }
                countForRelation++;
            }
        }
        map1.put(countLine, sb);
        return map1;
    }

    /**
     * Pinta la relaci&oacute;n Causa - Efecto entre objetivos, forma Horizontal
     * / Vertical misma
     *
     * @param startPerspectiveIndex &Iacute;ndice de la perspectiva (Causa) que
     * contiene la relaci&oacute;n causa - efecto
     * @param startThemeIndex &Iacute;ndice del tema (Causa) que contiene la
     * relaci&oacute;n causa - efecto
     * @param startObjetiveIndex &Iacute;ndice del objetivo (Causa) que contiene
     * la relaci&oacute;n causa - efecto
     * @param finalPerspectiveIndex &Iacute;ndice de la perspectiva (Efecto) que
     * contiene la relaci&oacute;n causa - efecto
     * @param finalThemeIndex &Iacute;ndice del tema (Efecto) que contiene la
     * relaci&oacute;n causa - efecto
     * @param finalObjetiveIndex &Iacute;ndice del tema (Efecto) que contiene la
     * relaci&oacute;n causa - efecto
     * @param jsonArrows objeto de tipo {@code JSONObject} que contiene los
     * datos de un balanscorecard a partir del cual buscar&aacute; la
     * perspectiva
     * @param countLine n&uacute;mero de l&iacute;nea a partir de la cual
     * deber&aacute;n crearse las siguientes l&iacute;neas
     * @param classLine Clase CSS
     * @return objeto de tipo {@code HashMap} con las l&iacute;neas creadas y el
     * contador de l&iacute;neas utilizados
     */
    private HashMap paintOOH_VSame(int startPerspectiveIndex, int startThemeIndex,
            int startObjetiveIndex, int finalPerspectiveIndex, int finalThemeIndex,
            int finalObjetiveIndex, JSONObject jsonArrows, int countLine, String classLine,
            int countForRelation, boolean isValid, String triangleEnd) {
        HashMap map = new HashMap();
        StringBuilder sb = new StringBuilder();
        int initPerspective = finalPerspectiveIndex;
        int initTheme = finalThemeIndex;
        int initObjective = finalObjetiveIndex;
        int finalizePerspective = startPerspectiveIndex;
        int finalizeTheme = startThemeIndex;
        int finalizeObjective = startObjetiveIndex;
        if (!isValid) {
            initPerspective = startPerspectiveIndex;
            initTheme = startThemeIndex;
            initObjective = startObjetiveIndex;
            finalizePerspective = finalPerspectiveIndex;
            finalizeTheme = finalThemeIndex;
            finalizeObjective = finalObjetiveIndex;
        }
        try {
            JSONObject startPers = (JSONObject) jsonArrows.get(initPerspective + "");
            JSONObject arrayTheme = (JSONObject) startPers.get("arrayTheme");
            JSONObject themePer = (JSONObject) arrayTheme.get(initTheme + "");
            JSONObject arraysObjects = (JSONObject) themePer.get("arrayObjective");
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalizePerspective + "");
            JSONObject arrayThemeFinal = (JSONObject) finalPers.get("arrayTheme");
            JSONObject themePerFinal = (JSONObject) arrayThemeFinal.get(finalizeTheme + "");
            JSONObject arraysObjectsFinal = (JSONObject) themePerFinal.get("arrayObjective");
            boolean useMargins = startPers.getBoolean("margins");
            int[] heightPer = getHeigthPerspectives((initPerspective - 1), jsonArrows);
            if (arraysObjects.length() == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }

            BigDecimal divContainerColsPer1 = divContainerCo.divide(new BigDecimal(100),
                    MathContext.DECIMAL128);
            BigDecimal divSizeColumnsPer1 = divSizeColum.divide(new BigDecimal(100),
                    MathContext.DECIMAL128);
            BigDecimal incrementTam = new BigDecimal(1);
            if (countForRelation < 15) {
                incrementTam = new BigDecimal(countForRelation).multiply(tam04).
                        multiply(divContainerColsPer1).multiply(divSizeColumnsPer1);
            }
            BigDecimal xA = divTitl.add(divContainerCo);
            x1 = xA.doubleValue();
            BigDecimal xB = xA.add(incrementTam);
            x2 = xB.doubleValue();
            BigDecimal yA1 = new BigDecimal(heightPer[0]).add(getHeightDifferentiator(jsonArrows,
                    initPerspective));
            BigDecimal yA2 = new BigDecimal(heightPer[1]);
            BigDecimal yA3 = new BigDecimal(initPerspective);
            BigDecimal yA3a = new BigDecimal(initObjective);
            BigDecimal yA4 = new BigDecimal(startPers.getInt("height")).
                    subtract(getHeightDifferentiator(jsonArrows, initPerspective)).
                    divide(new BigDecimal(arraysObjects.length()), MathContext.DECIMAL128);

            BigDecimal yA5 = yA3.subtract(tam1).multiply(tam2).add(yA2);

            BigDecimal yA6 = yA3.subtract(tam1).multiply(tam20).multiply(tam2);
            BigDecimal baseYdiff = getGroupDiffOnly(jsonArrows, initPerspective).multiply(tam20);
            BigDecimal baseYdiff2 = yA6.subtract(baseYdiff);

            BigDecimal yA7 = yA3a.subtract(tam1).multiply(yA4);
            BigDecimal yA8 = yA4.divide(tam2, MathContext.DECIMAL128);
            BigDecimal yA = yA1.add(baseYdiff2).add(tam120).add(yA7).add(yA8).
                    add(tam20).add(incrementTam);
            if (useMargins) {
                yA.add(yA5).add(yA3a);
            }
            y1 = yA.doubleValue();
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (isValid) {
                sb.append(paintLines(countLine, x2, x1, y1, y2, classLine));
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
            //segunda linea
            heightPer = getHeigthPerspectives(finalizePerspective, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            BigDecimal yB1 = new BigDecimal(heightPer[0]);
            BigDecimal yB3 = new BigDecimal(finalizePerspective);
            BigDecimal yB4 = yB3.multiply(tam2);

            BigDecimal yB5 = yB3.multiply(tam20).multiply(tam2);
            baseYdiff = getGroupDiffOnly(jsonArrows, finalizePerspective).multiply(tam20);
            baseYdiff2 = yB5.subtract(baseYdiff);

            BigDecimal yB6 = tam18.subtract(incrementTam);
            BigDecimal yB = yB1.add(tam120).add(baseYdiff2).add(yB6);
            if (useMargins) {
                yB.add(yB4).add(yB3);
            }
            y2 = yB.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //tercer linea
            int[] columns = BSCUtils.getSizeColumnsTheme(arrayThemeFinal.length());
            double restCols = 0;
            for (int i = 0; i < (finalizeTheme - 1); i++) {
                restCols = restCols + columns[i];
            }
            BigDecimal xC1 = new BigDecimal(restCols).multiply(divContainerColsPer1);
            int[] columnsObjs1 = BSCUtils.getSizeColumnsObjective(arraysObjectsFinal.length(),
                    columns[finalizeTheme - 1]);
            double restColsObj = 0;
            for (int i = 0; i < (finalizeObjective - 1); i++) {
                restColsObj = restColsObj + columnsObjs1[i];
            }
            BigDecimal xC3 = new BigDecimal(restColsObj).multiply(divContainerColsPer1).
                    multiply(divSizeColumnsPer1);
            BigDecimal xC4 = new BigDecimal(columnsObjs1[finalizeObjective - 1]).
                    divide(tam2, MathContext.DECIMAL128);
            BigDecimal xC = divTitl.add(xC1).add(xC3).add(xC4);
            x1 = x2;
            x2 = xC.doubleValue();
            y1 = y2;
            BigDecimal yD1 = new BigDecimal(heightPer[0]);
            BigDecimal yD3 = new BigDecimal(finalizePerspective);
            BigDecimal yD4 = yD3.multiply(tam2);

            BigDecimal yD5 = yD3.multiply(tam20).multiply(tam2);
            baseYdiff = getGroupDiffOnly(jsonArrows, finalizePerspective).multiply(tam20);
            baseYdiff2 = yD5.subtract(baseYdiff);

            BigDecimal yD6 = tam18.subtract(incrementTam);
            BigDecimal yD = tam120.add(yD1).add(baseYdiff2).add(yD6);
            if (useMargins) {
                yD.add(yD4).add(yD3);
            }
            y2 = yD.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //cuarta linea
            x1 = x2;
            x2 = x1;
            y1 = y2;
            BigDecimal yE = tam120.add(yD1).add(baseYdiff2);
            if (useMargins) {
                yE.add(yD4).add(yD3);
            }
            y2 = yE.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (!isValid) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
        } catch (JSONException ex) {
            log.error("Exception in paintOOH_VSame: " + ex);
        }
        map.put(countLine, sb);
        return map;
    }

    /**
     * Pinta la relaci&oacute;n Causa - Efecto entre objetivos, forma Horizontal
     * / Vertical
     *
     * @param startPerspectiveIndex &Iacute;ndice de la perspectiva (Causa) que
     * contiene la relaci&oacute;n causa - efecto
     * @param startThemeIndex &Iacute;ndice del tema (Causa) que contiene la
     * relaci&oacute;n causa - efecto
     * @param startObjetiveIndex &Iacute;ndice del objetivo (Causa) que contiene
     * la relaci&oacute;n causa - efecto
     * @param finalPerspectiveIndex &Iacute;ndice de la perspectiva (Efecto) que
     * contiene la relaci&oacute;n causa - efecto
     * @param finalThemeIndex &Iacute;ndice del tema (Efecto) que contiene la
     * relaci&oacute;n causa - efecto
     * @param finalObjetiveIndex &Iacute;ndice del objetivo (Causa) que contiene
     * la relaci&oacute;n causa - efecto
     * @param jsonArrows objeto de tipo {@code JSONObject} que contiene los
     * datos de un balanscorecard a partir del cual buscar&aacute; la
     * perspectiva
     * @param countLine n&uacute;mero de l&iacute;nea a partir de la cual
     * deber&aacute;n crearse las siguientes l&iacute;neas
     * @param classLine Clase CSS
     * @return objeto de tipo {@code HashMap} con las l&iacute;neas creadas y el
     * contador de l&iacute;neas utilizados
     */
    private HashMap paintOOH_V(int startPerspectiveIndex, int startThemeIndex, int startObjetiveIndex,
            int finalPerspectiveIndex, int finalThemeIndex, int finalObjetiveIndex, JSONObject jsonArrows,
            int countLine, String classLine, int countForRelation, boolean isValid, String triangleEnd) {
        HashMap map = new HashMap();
        StringBuilder sb = new StringBuilder();
        int initPerspective = startPerspectiveIndex;
        int initTheme = startThemeIndex;
        int initObjective = startObjetiveIndex;
        int finalizePerspective = finalPerspectiveIndex;
        int finalizeTheme = finalThemeIndex;
        int finalizeObjective = finalObjetiveIndex;
        if (isValid) {
            initPerspective = finalPerspectiveIndex;
            initTheme = finalThemeIndex;
            initObjective = finalObjetiveIndex;
            finalizePerspective = startPerspectiveIndex;
            finalizeTheme = startThemeIndex;
            finalizeObjective = startObjetiveIndex;
        }
        try {
            JSONObject startPers = (JSONObject) jsonArrows.get(initPerspective + "");
            JSONObject arrayTheme = (JSONObject) startPers.get("arrayTheme");
            JSONObject themePer = (JSONObject) arrayTheme.get(initTheme + "");
            JSONObject arraysObjects = (JSONObject) themePer.get("arrayObjective");
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalizePerspective + "");
            JSONObject arrayThemeFinal = (JSONObject) finalPers.get("arrayTheme");
            JSONObject themePerFinal = (JSONObject) arrayThemeFinal.get(finalizeTheme + "");
            JSONObject arraysObjectsFinal = (JSONObject) themePerFinal.get("arrayObjective");
            boolean useMargins = startPers.getBoolean("margins");
            int[] heightPer = getHeigthPerspectives(initPerspective - 1, jsonArrows);
            double restCols = 0;
            int[] columns = BSCUtils.getSizeColumnsTheme(arrayTheme.length());
            for (int i = 0; i < (initTheme - 1); i++) {
                restCols = restCols + columns[i];
            }
            BigDecimal divContainerColsPer1 = divContainerCo.divide(new BigDecimal(100),
                    MathContext.DECIMAL128);
            BigDecimal divSizeColumnsPer1 = divSizeColum.divide(new BigDecimal(100),
                    MathContext.DECIMAL128);

            BigDecimal incrementTam = new BigDecimal(1);
            if (countForRelation < 15) {
                incrementTam = new BigDecimal(countForRelation).multiply(tam04).
                        multiply(divContainerColsPer1).multiply(divSizeColumnsPer1);
            }
            if (arraysObjects.length() == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            BigDecimal restCols1 = new BigDecimal(restCols).multiply(divContainerColsPer1);
            BigDecimal columnsInit = new BigDecimal((columns[initTheme - 1]));
            BigDecimal result = columnsInit.multiply(divContainerColsPer1).multiply(divSizeColumnsPer1).
                    add(restCols1).add(divTitl);
            //primer linea
            x1 = result.doubleValue();
            BigDecimal resultX2 = result.add(incrementTam);
            x2 = resultX2.doubleValue();
            BigDecimal baseY1a = new BigDecimal(initPerspective).subtract(tam1).
                    multiply(tam2).add(new BigDecimal(heightPer[1]));
            BigDecimal heightObjective1a = new BigDecimal(startPers.getInt("height")).
                    subtract(getHeightDifferentiator(jsonArrows, initPerspective)).
                    divide(new BigDecimal(arraysObjects.length()), MathContext.DECIMAL128);
            BigDecimal baseY1a1 = new BigDecimal(initObjective).multiply(tam1);

            BigDecimal baseY1a2 = new BigDecimal(initPerspective).multiply(tam20).multiply(tam2);
            BigDecimal baseYdiff = getGroupDiffOnly(jsonArrows, initPerspective).multiply(tam20);
            BigDecimal baseYdiff2 = baseY1a2.subtract(baseYdiff);

            BigDecimal baseY1a3 = new BigDecimal(initObjective).subtract(tam1);
            BigDecimal baseY1a31 = baseY1a3.multiply(heightObjective1a);
            BigDecimal baseY1a4 = heightObjective1a.divide(tam2, MathContext.DECIMAL128);
            BigDecimal baseY1a5 = new BigDecimal(heightPer[0]).add(getHeightDifferentiator(jsonArrows,
                    initPerspective));
            BigDecimal baseY1 = baseY1a5.add(tam120).add(baseYdiff2).
                    add(baseY1a31).add(baseY1a4);
            if (useMargins) {
                baseY1.add(baseY1a).add(baseY1a1);
            }
            y1 = baseY1.doubleValue();
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (isValid) {
                sb.append(paintLines(countLine, x2, x1, y1, y2, classLine));
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
            //segunda linea
            heightPer = getHeigthPerspectives(initPerspective, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            BigDecimal y2a = new BigDecimal(heightPer[0]);
            BigDecimal y2a1 = baseY1a3.multiply(tam2).add(new BigDecimal(heightPer[1]));
            BigDecimal y2a2 = new BigDecimal(initPerspective).multiply(tam1);
            BigDecimal xB1 = tam18.subtract(incrementTam);
            BigDecimal resultY2 = y2a.add(baseYdiff2).add(tam120).add(xB1);
            if (useMargins) {
                resultY2.add(y2a1).add(y2a2);
            }
            y2 = resultY2.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //tercer linea
            x1 = x2;
            BigDecimal resultX2a = divTitl.add(divContainerCo).add(incrementTam);
            x2 = resultX2a.doubleValue();
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            x2 = resultX2a.doubleValue();
            //cuarta linea
            heightPer = getHeigthPerspectives(finalizePerspective, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            BigDecimal yC1 = new BigDecimal(heightPer[0]);
            BigDecimal yC3 = new BigDecimal(finalizePerspective);
            BigDecimal yC4 = yC3.multiply(tam2);

            BigDecimal yC5 = yC3.multiply(tam20).multiply(tam2);
            baseYdiff = getGroupDiffOnly(jsonArrows, finalizePerspective).multiply(tam20);
            baseYdiff2 = yC5.subtract(baseYdiff);

            BigDecimal yC6 = tam18.subtract(incrementTam);
            BigDecimal yC = tam120.add(yC1).add(baseYdiff2).add(yC6);
            if (useMargins) {
                yC.add(yC4).add(yC3);
            }
            y2 = yC.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //quinta linea
            columns = BSCUtils.getSizeColumnsTheme(arrayThemeFinal.length());
            restCols = 0;
            for (int i = 0; i < (finalizeTheme - 1); i++) {
                restCols = restCols + columns[i];
            }
            BigDecimal xC1 = new BigDecimal(restCols).multiply(divContainerColsPer1);
            int[] columnsObjs1 = BSCUtils.getSizeColumnsObjective(arraysObjectsFinal.length(),
                    columns[finalizeTheme - 1]);
            double restColsObj = 0;
            for (int i = 0; i < (finalizeObjective - 1); i++) {
                restColsObj = restColsObj + columnsObjs1[i];
            }
            BigDecimal xC3 = new BigDecimal(restColsObj).multiply(divContainerColsPer1).
                    multiply(divSizeColumnsPer1);
            BigDecimal xC4 = new BigDecimal(columnsObjs1[finalizeObjective - 1]).divide(tam2,
                    MathContext.DECIMAL128);
            BigDecimal xC = divTitl.add(xC1).add(xC3).add(xC4);
            x1 = x2;
            x2 = xC.doubleValue();
            y1 = y2;
            BigDecimal yD1 = new BigDecimal(heightPer[0]);
            BigDecimal yD3 = new BigDecimal(finalizePerspective);
            BigDecimal yD4 = yD3.multiply(tam2);

            BigDecimal yD5 = yD3.multiply(tam20).multiply(tam2);
            baseYdiff = getGroupDiffOnly(jsonArrows, finalizePerspective).multiply(tam20);
            baseYdiff2 = yD5.subtract(baseYdiff);

            BigDecimal yD6 = tam18.subtract(incrementTam);
            BigDecimal yD = tam120.add(yD1).add(baseYdiff2).add(yD6);
            if (useMargins) {
                yD.add(yD4).add(yD3);
            }
            y2 = yD.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //sexta linea
            x1 = x2;
            x2 = x1;
            y1 = y2;
            BigDecimal yE = tam120.add(yD1).add(baseYdiff2);
            if (useMargins) {
                yE.add(yD4).add(yD3);
            }
            y2 = yE.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (!isValid) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
        } catch (JSONException ex) {
            log.error("Exception in paintOOH_V: " + ex);
        }
        map.put(countLine, sb);
        return map;
    }

    /**
     * Pinta la relaci&oacute;n Causa - Efecto entre objetivos forma Horizontal
     * / Horizontal
     *
     * @param startPerspectiveIndex &Iacute;ndice de la perspectiva (Causa) que
     * contiene la relaci&oacute;n causa - efecto
     * @param startThemeIndex &Iacute;ndice del tema (Causa) que contiene la
     * relaci&oacute;n causa - efecto
     * @param startObjetiveIndex &Iacute;ndice del objetivo (Causa) que contiene
     * la relaci&oacute;n causa - efecto
     * @param finalPerspectiveIndex &Iacute;ndice de la perspectiva (Efecto) que
     * contiene la relaci&oacute;n causa - efecto
     * @param finalThemeIndex &Iacute;ndice del tema (Efecto) que contiene la
     * relaci&oacute;n causa - efecto
     * @param finalObjetiveIndex &Iacute;ndice del objetivo (Efecto) que
     * contiene la relaci&oacute;n causa - efecto
     * @param jsonArrows objeto de tipo {@code JSONObject} que contiene los
     * datos de un balanscorecard a partir del cual buscar&aacute; la
     * perspectiva
     * @param countLine n&uacute;mero de l&iacute;nea a partir de la cual
     * deber&aacute;n crearse las siguientes l&iacute;neas
     * @param classLine Clase CSS
     * @return objeto de tipo {@code HashMap} con las l&iacute;neas creadas y el
     * contador de l&iacute;neas utilizados
     */
    private HashMap paintOOHH(int startPerspectiveIndex, int startThemeIndex, int startObjetiveIndex,
            int finalPerspectiveIndex, int finalThemeIndex, int finalObjetiveIndex,
            JSONObject jsonArrows, int countLine, String classLine, int countForRelation, String triangleEnd) {
        HashMap map = new HashMap();
        StringBuilder sb = new StringBuilder();
        int initPerspective = startPerspectiveIndex;
        int initTheme = startThemeIndex;
        int initObjective = startObjetiveIndex;
        int finalizePerspective = finalPerspectiveIndex;
        int finalizeTheme = finalThemeIndex;
        int finalizeObjective = finalObjetiveIndex;
        if (finalPerspectiveIndex < startPerspectiveIndex) {
            initPerspective = finalPerspectiveIndex;
            initTheme = finalThemeIndex;
            initObjective = finalObjetiveIndex;
            finalizePerspective = startPerspectiveIndex;
            finalizeTheme = startThemeIndex;
            finalizeObjective = startObjetiveIndex;
        }
        try {
            JSONObject startPers = (JSONObject) jsonArrows.get(initPerspective + "");
            JSONObject arrayTheme = (JSONObject) startPers.get("arrayTheme");
            JSONObject themePer = (JSONObject) arrayTheme.get(initTheme + "");
            JSONObject arraysObjects = (JSONObject) themePer.get("arrayObjective");
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalizePerspective + "");
            JSONObject arrayThemeFinal = (JSONObject) finalPers.get("arrayTheme");
            JSONObject themePerFinal = (JSONObject) arrayThemeFinal.get(finalizeTheme + "");
            JSONObject arraysObjectsFinal = (JSONObject) themePerFinal.get("arrayObjective");
            boolean useMargins = startPers.getBoolean("margins");
            int[] heightPer = getHeigthPerspectives(initPerspective, jsonArrows);
            BigDecimal divContainerColsPer1 = divContainerCo.divide(new BigDecimal(100),
                    MathContext.DECIMAL128);
            BigDecimal divSizeColumnsPer1 = divSizeColum.divide(new BigDecimal(100),
                    MathContext.DECIMAL128);

            BigDecimal incrementTam = new BigDecimal(1);
            if (countForRelation < 15) {
                incrementTam = new BigDecimal(countForRelation).multiply(tam04).
                        multiply(divContainerColsPer1).multiply(divSizeColumnsPer1);
            }
            int[] columns = BSCUtils.getSizeColumnsTheme(arrayTheme.length());
            double restCols = 0;
            for (int i = 0; i < (initTheme - 1); i++) {
                restCols = restCols + columns[i];
            }
            //primer linea
            BigDecimal xA1 = new BigDecimal(restCols).multiply(divContainerColsPer1);
            int[] columnsObjs = BSCUtils.getSizeColumnsObjective(arraysObjects.length(),
                    columns[initTheme - 1]);
            double restColsObj = 0;
            for (int i = 0; i < (initObjective - 1); i++) {
                restColsObj = restColsObj + columnsObjs[i];
            }
            BigDecimal xA3 = new BigDecimal(restColsObj).multiply(divContainerColsPer1).
                    multiply(divSizeColumnsPer1);
            BigDecimal xA4 = new BigDecimal(columnsObjs[initObjective - 1]).divide(tam2,
                    MathContext.DECIMAL128);
            BigDecimal xA = divTitl.add(xA1).add(xA3).add(xA4);
            x1 = xA.doubleValue();
            x2 = x1;
            BigDecimal yA1 = new BigDecimal(heightPer[0]);
            BigDecimal yA3 = new BigDecimal(initPerspective);
            BigDecimal yA4 = yA3.multiply(tam2);

            BigDecimal yA5 = yA3.multiply(tam20).multiply(tam2);
            BigDecimal baseYdiff = getGroupDiffOnly(jsonArrows, initPerspective).multiply(tam20);
            BigDecimal baseYdiff2 = yA5.subtract(baseYdiff);

            BigDecimal yA = tam120.add(yA1).add(baseYdiff2);
            if (useMargins) {
                yA.add(yA4).add(yA3);
            }
            y1 = yA.doubleValue();
            BigDecimal xB1 = tam18.subtract(incrementTam);
            BigDecimal yB = yA.add(xB1);
            y2 = yB.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex < startPerspectiveIndex) {
                sb.append(paintLines(countLine, x1, x2, y2, y1, classLine));
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
            //segunda linea
            x1 = x2;
            BigDecimal xB = divContainerCo.add(divTitl).add(incrementTam);
            x2 = xB.doubleValue();
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //tercer linea
            heightPer = getHeigthPerspectives(finalizePerspective, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            BigDecimal yC1 = new BigDecimal(heightPer[0]);
            BigDecimal yC3 = new BigDecimal(finalizePerspective);
            BigDecimal yC4 = yC3.multiply(tam2);

            BigDecimal yC5 = yC3.multiply(tam20).multiply(tam2);
            baseYdiff = getGroupDiffOnly(jsonArrows, finalizePerspective).multiply(tam20);
            baseYdiff2 = yC5.subtract(baseYdiff);

            BigDecimal yC6 = tam18.subtract(incrementTam);
            BigDecimal yC = tam120.add(yC1).add(baseYdiff2).add(yC6);
            if (useMargins) {
                yC.add(yC4).add(yC3);
            }
            y2 = yC.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //cuarta linea
            columns = BSCUtils.getSizeColumnsTheme(arrayThemeFinal.length());
            restCols = 0;
            for (int i = 0; i < (finalizeTheme - 1); i++) {
                restCols = restCols + columns[i];
            }
            BigDecimal xC1 = new BigDecimal(restCols).multiply(divContainerColsPer1);
            int[] columnsObjs1 = BSCUtils.getSizeColumnsObjective(arraysObjectsFinal.length(),
                    columns[finalizeTheme - 1]);
            restColsObj = 0;
            for (int i = 0; i < (finalizeObjective - 1); i++) {
                restColsObj = restColsObj + columnsObjs1[i];
            }
            BigDecimal xC3 = new BigDecimal(restColsObj).multiply(divContainerColsPer1).
                    multiply(divSizeColumnsPer1);
            BigDecimal xC4 = new BigDecimal(columnsObjs1[finalizeObjective - 1]).
                    divide(tam2, MathContext.DECIMAL128);
            BigDecimal xC = divTitl.add(xC1).add(xC3).add(xC4);
            x1 = x2;
            x2 = xC.doubleValue();
            y1 = y2;
            BigDecimal yD1 = new BigDecimal(heightPer[0]);
            BigDecimal yD3 = new BigDecimal(finalizePerspective);
            BigDecimal yD4 = yD3.multiply(tam2);

            BigDecimal yD5 = yD3.multiply(tam20).multiply(tam2);
            baseYdiff = getGroupDiffOnly(jsonArrows, finalizePerspective).multiply(tam20);
            baseYdiff2 = yD5.subtract(baseYdiff);

            BigDecimal yD6 = tam18.subtract(incrementTam);
            BigDecimal yD = tam120.add(yD1).add(baseYdiff2).add(yD6);
            if (useMargins) {
                yD.add(yD4).add(yD3);
            }
            y2 = yD.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //quinta linea
            x1 = x2;
            x2 = x1;
            y1 = y2;
            BigDecimal yE = tam120.add(yD1).add(baseYdiff2);
            if (useMargins) {
                yE.add(yD4).add(yD3);
            }
            y2 = yE.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex > startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
        } catch (JSONException ex) {
            log.error("Exception in paintOOHH: " + ex);
        }
        map.put(countLine, sb);
        return map;
    }

    /**
     * Pinta la relaci&oacute;n Causa - Efecto entre objetivos con indices
     * continuos
     *
     * @param startPerspectiveIndex &Iacute;ndice de la perspectiva (Causa) que
     * contiene la relaci&oacute;n causa - efecto
     * @param startThemeIndex &Iacute;ndice del tema (Causa) que contiene la
     * relaci&oacute;n causa - efecto
     * @param startObjetiveIndex &Iacute;ndice del objetivo (Causa) que contiene
     * la relaci&oacute;n causa - efecto
     * @param finalPerspectiveIndex &Iacute;ndice de la perspectiva (Efecto) que
     * contiene la relaci&oacute;n causa - efecto
     * @param finalThemeIndex &Iacute;ndice del tema (Efecto) que contiene la
     * relaci&oacute;n causa - efecto
     * @param finalObjetiveIndex &Iacute;ndice del objetivo (Efecto) que
     * contiene la relaci&oacute;n causa - efecto
     * @param jsonArrows objeto de tipo {@code JSONObject} que contiene los
     * datos de un balanscorecard a partir del cual buscar&aacute; la
     * perspectiva
     * @param countLine n&uacute;mero de l&iacute;nea a partir de la cual
     * deber&aacute;n crearse las siguientes l&iacute;neas
     * @param classLine Clase CSS
     * @return objeto de tipo {@code HashMap} con las l&iacute;neas creadas y el
     * contador de l&iacute;neas utilizados
     */
    private HashMap paintOOIndexContinuos(int startPerspectiveIndex, int startThemeIndex,
            int startObjetiveIndex, int finalPerspectiveIndex, int finalThemeIndex,
            int finalObjetiveIndex, JSONObject jsonArrows, int countLine, String classLine,
            int countForRelation, String triangleEnd) {
        HashMap map = new HashMap();
        StringBuilder sb = new StringBuilder();
        int initPerspective = startPerspectiveIndex;
        int initTheme = startThemeIndex;
        int initObjective = startObjetiveIndex;
        int finalizePerspective = finalPerspectiveIndex;
        int finalizeTheme = finalThemeIndex;
        int finalizeObjective = finalObjetiveIndex;
        if (finalPerspectiveIndex < startPerspectiveIndex) {
            initPerspective = finalPerspectiveIndex;
            initTheme = finalThemeIndex;
            initObjective = finalObjetiveIndex;
            finalizePerspective = startPerspectiveIndex;
            finalizeTheme = startThemeIndex;
            finalizeObjective = startObjetiveIndex;
        }
        try {
            JSONObject startPers = (JSONObject) jsonArrows.get(initPerspective + "");
            JSONObject arrayTheme = (JSONObject) startPers.get("arrayTheme");
            JSONObject themePer = (JSONObject) arrayTheme.get(initTheme + "");
            JSONObject arraysObjects = (JSONObject) themePer.get("arrayObjective");
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalizePerspective + "");
            JSONObject arrayThemeFinal = (JSONObject) finalPers.get("arrayTheme");
            JSONObject themePerFinal = (JSONObject) arrayThemeFinal.get(finalizeTheme + "");
            JSONObject arraysObjectsFinal = (JSONObject) themePerFinal.get("arrayObjective");
            boolean useMargins = startPers.getBoolean("margins");
            int[] heightPer = getHeigthPerspectives(initPerspective - 1, jsonArrows);
            if (arraysObjects.length() == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            BigDecimal divContainerColsPer1 = divContainerCo.divide(new BigDecimal(100),
                    MathContext.DECIMAL128);
            BigDecimal incrementTam = new BigDecimal(1);
            if (countForRelation < 15) {
                incrementTam = new BigDecimal(countForRelation).multiply(tam04).
                        multiply(divContainerColsPer1);
            }
            //primer linea
            BigDecimal xA = divTitl.add(divContainerCo);
            x1 = xA.doubleValue();
            BigDecimal xA1 = xA.add(incrementTam);
            x2 = xA1.doubleValue();
            BigDecimal yA1 = new BigDecimal(heightPer[0]);
            BigDecimal yA2 = new BigDecimal(heightPer[1]);
            BigDecimal yA3 = new BigDecimal(initPerspective);
            BigDecimal yA4 = new BigDecimal(initObjective);
            BigDecimal yA5 = yA3.subtract(tam1).multiply(tam2).add(yA2);
            BigDecimal yA6 = new BigDecimal(startPers.getInt("height")).
                    subtract(getHeightDifferentiator(jsonArrows, initPerspective)).
                    divide(new BigDecimal(arraysObjects.length()), MathContext.DECIMAL128);
            BigDecimal yA7 = yA4.subtract(tam1).multiply(yA6);

            BigDecimal yA8 = yA3.multiply(tam20).multiply(tam2);
            BigDecimal baseYdiff = getGroupDiffOnly(jsonArrows, initPerspective).multiply(tam20);
            BigDecimal baseYdiff2 = yA8.subtract(baseYdiff);

            BigDecimal yA9 = yA6.divide(tam2, MathContext.DECIMAL128);
            BigDecimal yACom = yA1.add(yA7).add(baseYdiff2).add(yA9).add(tam120).
                    add(incrementTam);
            if (useMargins) {
                yACom.add(yA5).add(yA4);
            }
            y1 = yACom.doubleValue();
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex < startPerspectiveIndex) {
                sb.append(paintLines(countLine, x2, x1, y2, y1, classLine));
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
            //Segunda linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            if (arraysObjectsFinal.length() == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            x1 = x2;
            x2 = x1;
            y1 = y2;
            BigDecimal yB1 = new BigDecimal(heightPer[0]).add(getHeightDifferentiator(jsonArrows,
                    finalizePerspective));
            BigDecimal yB2 = new BigDecimal(heightPer[1]);
            BigDecimal yB3 = new BigDecimal(finalizeObjective);
            BigDecimal yB4 = new BigDecimal(finalPers.getInt("height")).
                    subtract(getHeightDifferentiator(jsonArrows, finalizePerspective)).
                    divide(new BigDecimal(arraysObjectsFinal.length()), MathContext.DECIMAL128);
            BigDecimal yB5 = new BigDecimal(finalizePerspective);
            BigDecimal yB6 = yB5.subtract(tam1).multiply(tam2);
            BigDecimal yB7 = yB3.subtract(tam1).multiply(yB4);
            BigDecimal yB8 = yB4.divide(tam2, MathContext.DECIMAL128);

            BigDecimal yB9 = yB5.multiply(tam20).multiply(tam2);
            baseYdiff = getGroupDiffOnly(jsonArrows, finalizePerspective).multiply(tam20);
            baseYdiff2 = yB9.subtract(baseYdiff);

            BigDecimal yB = yB1.add(yB7).add(yB8).add(baseYdiff2).add(tam120)
                    .add(incrementTam);
            if (useMargins) {
                yB.add(yB2).add(yB6).add(yB3);
            }
            y2 = yB.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //Tercer linea
            x1 = x2;
            BigDecimal cX = divTitl.add(divContainerCo);
            x2 = cX.floatValue();
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex > startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
        } catch (JSONException ex) {
            log.error("Exception in paintOOIndexContinuos: " + ex);
        }
        map.put(countLine, sb);
        return map;
    }

    /**
     * Pinta la relaci&oacute;n Causa - Efecto entre objetivos
     *
     * @param startPerspectiveIndex &Iacute;ndice de la perspectiva (Causa) que
     * contiene la relaci&oacute;n causa - efecto
     * @param startThemeIndex &Iacute;ndice del tema (Causa) que contiene la
     * relaci&oacute;n causa - efecto
     * @param startObjetiveIndex &Iacute;ndice del objetivo (Causa) que contiene
     * la relaci&oacute;n causa - efecto
     * @param finalPerspectiveIndex &Iacute;ndice de la perspectiva (Efecto) que
     * contiene la relaci&oacute;n causa - efecto
     * @param finalThemeIndex &Iacute;ndice del tema (Efecto) que contiene la
     * relaci&oacute;n causa - efecto
     * @param finalObjetiveIndex &Iacute;ndice del objetivo (Efecto) que
     * contiene la relaci&oacute;n causa - efecto
     * @param jsonArrows objeto de tipo {@code JSONObject} que contiene los
     * datos de un balanscorecard a partir del cual buscar&aacute; la
     * perspectiva
     * @param countLine n&uacute;mero de l&iacute;nea a partir de la cual
     * deber&aacute;n crearse las siguientes l&iacute;neas
     * @param classLine Clase CSS
     * @return objeto de tipo {@code HashMap} con las l&iacute;neas creadas y el
     * contador de l&iacute;neas utilizados
     */
    private HashMap paintOO(int startPerspectiveIndex, int startThemeIndex, int startObjetiveIndex,
            int finalPerspectiveIndex, int finalThemeIndex, int finalObjetiveIndex,
            JSONObject jsonArrows, int countLine, String classLine, int countForRelation, String triangleEnd) {
        HashMap map = new HashMap();
        StringBuilder sb = new StringBuilder();
        int initPerspective = startPerspectiveIndex;
        int initTheme = startThemeIndex;
        int initObjective = startObjetiveIndex;
        int finalizePerspective = finalPerspectiveIndex;
        int finalizeTheme = finalThemeIndex;
        int finalizeObjective = finalObjetiveIndex;
        if (finalPerspectiveIndex < startPerspectiveIndex) {
            initPerspective = finalPerspectiveIndex;
            initTheme = finalThemeIndex;
            initObjective = finalObjetiveIndex;
            finalizePerspective = startPerspectiveIndex;
            finalizeTheme = startThemeIndex;
            finalizeObjective = startObjetiveIndex;
        }
        try {
            JSONObject startPers = (JSONObject) jsonArrows.get(initPerspective + "");
            JSONObject arrayTheme = (JSONObject) startPers.get("arrayTheme");
            JSONObject themePer = (JSONObject) arrayTheme.get(initTheme + "");
            JSONObject arraysObjects = (JSONObject) themePer.get("arrayObjective");
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalizePerspective + "");
            JSONObject arrayThemeFinal = (JSONObject) finalPers.get("arrayTheme");
            JSONObject themePerFinal = (JSONObject) arrayThemeFinal.get(finalizeTheme + "");
            JSONObject arraysObjectsFinal = (JSONObject) themePerFinal.get("arrayObjective");
            int[] heightPer = getHeigthPerspectives(initPerspective - 1, jsonArrows);
            boolean useMargins = startPers.getBoolean("margins");
            double restCols = 0;
            int[] columns = BSCUtils.getSizeColumnsTheme(arrayTheme.length());
            for (int i = 0; i < (initTheme - 1); i++) {
                restCols = restCols + columns[i];
            }

            BigDecimal divContainerColsPer1 = divContainerCo.divide(new BigDecimal(100),
                    MathContext.DECIMAL128);
            BigDecimal divSizeColumnsPer1 = divSizeColum.divide(new BigDecimal(100),
                    MathContext.DECIMAL128);
            BigDecimal incrementTam = new BigDecimal(1);
            if (countForRelation < 15) {
                incrementTam = new BigDecimal(countForRelation).multiply(tam04).
                        multiply(divContainerColsPer1);
            }
            if (arraysObjects.length() == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }

            BigDecimal restCols1 = new BigDecimal(restCols).multiply(divContainerColsPer1);
            BigDecimal columnsInit = new BigDecimal((columns[initTheme - 1]));
            BigDecimal result = columnsInit.multiply(divContainerColsPer1).multiply(divSizeColumnsPer1).
                    add(restCols1).add(divTitl);
            //primer linea
            x1 = result.doubleValue();
            BigDecimal resultX2 = result.add(incrementTam);
            x2 = resultX2.doubleValue();
            BigDecimal baseY1a = new BigDecimal(initPerspective).subtract(tam1).
                    multiply(tam2).add(new BigDecimal(heightPer[1]));
            BigDecimal heightObjective1a = new BigDecimal(startPers.getInt("height")).
                    subtract(getHeightDifferentiator(jsonArrows, initPerspective)).
                    divide(new BigDecimal(arraysObjects.length()), MathContext.DECIMAL128);
            BigDecimal baseY1a1 = new BigDecimal(initObjective).multiply(tam1);

            BigDecimal baseY1a2 = new BigDecimal(initPerspective).multiply(tam20).multiply(tam2);
            BigDecimal baseYdiff = getGroupDiffOnly(jsonArrows, initPerspective).multiply(tam20);
            BigDecimal baseYdiff2 = baseY1a2.subtract(baseYdiff);

            BigDecimal baseY1a3 = new BigDecimal(initObjective).subtract(tam1);
            BigDecimal baseY1a31 = baseY1a3.multiply(heightObjective1a);
            BigDecimal baseY1a4 = heightObjective1a.divide(tam2, MathContext.DECIMAL128);
            BigDecimal baseY1a5 = new BigDecimal(heightPer[0]).add(getHeightDifferentiator(jsonArrows,
                    initPerspective));
            BigDecimal baseY1 = baseY1a5.add(tam120).add(incrementTam).
                    add(baseYdiff2).add(baseY1a31).add(baseY1a4);
            if (useMargins) {
                baseY1.add(baseY1a).add(baseY1a1);
            }
            y1 = baseY1.doubleValue();
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex < startPerspectiveIndex) {
                sb.append(paintLines(countLine, x2, x1, y1, y2, classLine));
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
            //segunda linea
            heightPer = getHeigthPerspectives(initPerspective, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            BigDecimal y1a = new BigDecimal(heightPer[0]);
            BigDecimal y1b = new BigDecimal(heightPer[1]);
            BigDecimal y1c = new BigDecimal(initPerspective);

            BigDecimal y1d = y1c.multiply(tam20).multiply(tam2);
            baseYdiff = getGroupDiffOnly(jsonArrows, initPerspective).multiply(tam20);
            baseYdiff2 = y1d.subtract(baseYdiff);

            BigDecimal y1e = tam18.subtract(incrementTam);
            BigDecimal y1f = y1c.subtract(tam1).multiply(tam2).add(y1b);
            BigDecimal y1g = y1a.add(baseYdiff2).add(tam120).add(y1e);
            if (useMargins) {
                y1g.add(y1f).add(y1c);
            }
            y2 = y1g.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //tercera linea
            x1 = x2;
            BigDecimal resultX2a = divTitl.add(divContainerCo).add(incrementTam);
            x2 = resultX2a.doubleValue();
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //cuarta linea
            heightPer = getHeigthPerspectives(finalizePerspective, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            BigDecimal yb = new BigDecimal(heightPer[0]);
            BigDecimal yb0 = new BigDecimal(heightPer[1]);
            BigDecimal yb1 = new BigDecimal(finalizePerspective);

            BigDecimal yb2 = yb1.multiply(tam20).multiply(tam2);
            baseYdiff = getGroupDiffOnly(jsonArrows, finalizePerspective).multiply(tam20);
            baseYdiff2 = yb2.subtract(baseYdiff);

            BigDecimal yb3 = yb1.multiply(tam1);
            BigDecimal yb4 = yb1.multiply(tam2).add(yb0);
            BigDecimal resultY2b = yb.add(baseYdiff2).add(tam120).add(incrementTam);
            if (useMargins) {
                resultY2b.add(yb4).add(yb3);
            }
            y2 = resultY2b.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //quinta linea
            columns = BSCUtils.getSizeColumnsTheme(arrayThemeFinal.length());
            restCols = 0;
            for (int i = 0; i < (finalizeTheme - 1); i++) {
                restCols = restCols + columns[i];
            }
            BigDecimal xb = divContainerColsPer1.multiply(new BigDecimal(restCols));
            BigDecimal xb1 = divContainerColsPer1.multiply(divSizeColumnsPer1).
                    multiply(new BigDecimal(columns[finalizeTheme - 1]));
            BigDecimal resultX2b = divTitl.add(incrementTam).add(xb).add(xb1);
            x2 = resultX2b.doubleValue();
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //sexta linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            if (arraysObjectsFinal.length() == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            x1 = x2;
            x2 = x1;
            y1 = y2;
            heightObjective1a = new BigDecimal(finalPers.getInt("height")).
                    subtract(getHeightDifferentiator(jsonArrows, finalizePerspective)).
                    divide(new BigDecimal(arraysObjectsFinal.length()),
                    MathContext.DECIMAL128);
            BigDecimal yc = new BigDecimal(heightPer[0]).add(getHeightDifferentiator(jsonArrows,
                    finalizePerspective));
            BigDecimal yca = yb1.subtract(tam1).multiply(tam2).add(new BigDecimal(heightPer[1]));

            BigDecimal ycb = yb1.multiply(tam20).multiply(tam2);
            baseYdiff = getGroupDiffOnly(jsonArrows, finalizePerspective).multiply(tam20);
            baseYdiff2 = ycb.subtract(baseYdiff);

            BigDecimal ycb0 = new BigDecimal(finalizeObjective);
            BigDecimal ycd = ycb0.subtract(tam1).multiply(heightObjective1a);
            BigDecimal yce = heightObjective1a.divide(tam2, MathContext.DECIMAL128);
            BigDecimal resultY2c = yc.add(baseYdiff2).add(ycd).add(yce).add(tam120).
                    add(incrementTam);
            if (useMargins) {
                resultY2c.add(yca).add(ycb0);
            }
            y2 = resultY2c.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //septima linea
            x1 = x2;
            BigDecimal xc = new BigDecimal(columns[finalizeTheme - 1]).multiply(divContainerColsPer1).
                    multiply(divSizeColumnsPer1);
            BigDecimal resultX2c = divTitl.add(xb).add(xc);
            x2 = resultX2c.doubleValue();
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex > startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
        } catch (JSONException ex) {
            log.error("Exception in paintOO: " + ex);
        }
        map.put(countLine, sb);
        return map;
    }

    /**
     * Pinta la relaci&oacute;n Causa - Efecto entre objetivos, con
     * &iacute;ndice final en la perspectiva
     *
     * @param startPerspectiveIndex &Iacute;ndice de la perspectiva (Causa) que
     * contiene la relaci&oacute;n causa - efecto
     * @param startThemeIndex &Iacute;ndice del tema (Causa) que contiene la
     * relaci&oacute;n causa - efecto
     * @param startObjetiveIndex &Iacute;ndice del objetivo (Causa) que contiene
     * la relaci&oacute;n causa - efecto
     * @param finalPerspectiveIndex &Iacute;ndice de la perspectiva (Efecto) que
     * contiene la relaci&oacute;n causa - efecto
     * @param finalThemeIndex &Iacute;ndice del tema (Efecto) que contiene la
     * relaci&oacute;n causa - efecto
     * @param finalObjetiveIndex &Iacute;ndice del objetivo (Efecto) que
     * contiene la relaci&oacute;n causa - efecto
     * @param isFinalTheme la validaci&oacute;on si el tema esta al final de la
     * perspectiva
     * @param jsonArrows objeto de tipo {@code JSONObject} que contiene los
     * datos de un balanscorecard a partir del cual buscar&aacute; la
     * perspectiva
     * @param countLine n&uacute;mero de l&iacute;nea a partir de la cual
     * deber&aacute;n crearse las siguientes l&iacute;neas
     * @param classLine Clase CSS
     * @return objeto de tipo {@code HashMap} con las l&iacute;neas creadas y el
     * contador de l&iacute;neas utilizados
     */
    private HashMap paintOOIndexFinalP(int startPerspectiveIndex, int startThemeIndex,
            int startObjetiveIndex, int finalPerspectiveIndex, int finalThemeIndex,
            int finalObjetiveIndex, boolean isFinalTheme, JSONObject jsonArrows,
            int countLine, String classLine, int countForRelation, String triangleEnd) {
        HashMap map = new HashMap();
        StringBuilder sb = new StringBuilder();
        int initPerspective = startPerspectiveIndex;
        int initTheme = startThemeIndex;
        int initObjective = startObjetiveIndex;
        int finalizePerspective = finalPerspectiveIndex;
        int finalizeTheme = finalThemeIndex;
        int finalizeObjective = finalObjetiveIndex;
        if (isFinalTheme) {
            initPerspective = finalPerspectiveIndex;
            initTheme = finalThemeIndex;
            initObjective = finalObjetiveIndex;
            finalizePerspective = startPerspectiveIndex;
            finalizeTheme = startThemeIndex;
            finalizeObjective = startObjetiveIndex;
        }
        try {
            JSONObject startPers = (JSONObject) jsonArrows.get(initPerspective + "");
            JSONObject arrayTheme = (JSONObject) startPers.get("arrayTheme");
            JSONObject themePer = (JSONObject) arrayTheme.get(initTheme + "");
            JSONObject arraysObjects = (JSONObject) themePer.get("arrayObjective");
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalizePerspective + "");
            JSONObject arrayThemeFinal = (JSONObject) finalPers.get("arrayTheme");
            JSONObject themePerFinal = (JSONObject) arrayThemeFinal.get(finalizeTheme + "");
            JSONObject arraysObjectsFinal = (JSONObject) themePerFinal.get("arrayObjective");
            boolean useMargins = startPers.getBoolean("margins");
            int[] heightPer = getHeigthPerspectives((initPerspective - 1), jsonArrows);
            if (arraysObjects.length() == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            BigDecimal divContainerColsPer1 = divContainerCo.divide(new BigDecimal(100),
                    MathContext.DECIMAL128);
            BigDecimal incrementTam = new BigDecimal(1);
            if (countForRelation < 15) {
                incrementTam = new BigDecimal(countForRelation).multiply(tam04).
                        multiply(divContainerColsPer1);
            }
            //primer linea
            BigDecimal xA = divTitl.add(divContainerCo);
            x1 = xA.doubleValue();
            BigDecimal xB = xA.add(incrementTam);
            x2 = xB.doubleValue();
            BigDecimal yA1 = new BigDecimal(heightPer[0]).add(getHeightDifferentiator(jsonArrows,
                    initPerspective));
            BigDecimal yA2 = new BigDecimal(heightPer[1]);
            BigDecimal yA3 = new BigDecimal(initPerspective);
            BigDecimal yA3a = new BigDecimal(initObjective);
            BigDecimal yA4 = new BigDecimal(startPers.getInt("height")).
                    subtract(getHeightDifferentiator(jsonArrows, initPerspective)).
                    divide(new BigDecimal(arraysObjects.length()), MathContext.DECIMAL128);
            BigDecimal yA5 = yA3.subtract(tam1).multiply(tam2).add(yA2);
            BigDecimal yA6 = yA3.multiply(tam20).multiply(tam2);
            BigDecimal yA7 = yA3a.subtract(tam1).multiply(yA4);
            BigDecimal yA8 = yA4.divide(tam2, MathContext.DECIMAL128);
            BigDecimal yA = yA1.add(tam120).add(yA6).add(incrementTam).add(yA8).add(yA7);
            if (useMargins) {
                yA.add(yA5).add(yA3a);
            }
            y1 = yA.doubleValue();
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (isFinalTheme) {
                sb.append(paintLines(countLine, x2, x1, y2, y1, classLine));
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
            //segunda linea
            heightPer = getHeigthPerspectives(finalizePerspective, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            BigDecimal yB1 = new BigDecimal(heightPer[0]);
            BigDecimal yB2 = new BigDecimal(heightPer[1]);
            BigDecimal yB3 = new BigDecimal(finalizePerspective);
            BigDecimal yB4 = yB3.multiply(tam2).add(yB2);

            BigDecimal yB5 = yB3.multiply(tam20).multiply(tam2);
            BigDecimal baseYdiff = getGroupDiffOnly(jsonArrows, finalizePerspective).multiply(tam20);
            BigDecimal baseYdiff2 = yB5.subtract(baseYdiff);

            BigDecimal yB = yB1.add(tam120).add(incrementTam).add(baseYdiff2);
            if (useMargins) {
                yB.add(yB4);
            }
            y2 = yB.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //tercer linea
            int noCols = arrayThemeFinal.length();
            if (noCols == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            double restCols = 0;
            int[] columns = BSCUtils.getSizeColumnsTheme(arrayThemeFinal.length());
            for (int i = 0; i < (finalizeTheme - 1); i++) {
                restCols = restCols + columns[i];
            }
            BigDecimal xC1 = new BigDecimal(restCols).divide(tam100, MathContext.DECIMAL128).
                    multiply(divContainerCo);
            BigDecimal xC2 = new BigDecimal(columns[finalizeTheme - 1]);
            BigDecimal xC3 = xC2.divide(tam100, MathContext.DECIMAL128);
            BigDecimal xC4 = divSizeColum.divide(tam100, MathContext.DECIMAL128);
            BigDecimal xC5 = xC3.multiply(divContainerCo).multiply(xC4);
            BigDecimal xC = divTitl.add(xC1).add(xC5).add(incrementTam);
            x1 = x2;
            x2 = xC.doubleValue();
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //cuarta linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            if (arraysObjectsFinal.length() == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            x1 = x2;
            x2 = x1;
            y1 = y2;
            BigDecimal yC1 = new BigDecimal(heightPer[0]).add(getHeightDifferentiator(jsonArrows,
                    finalizePerspective));
            BigDecimal yC2 = new BigDecimal(heightPer[1]);
            BigDecimal yC3 = new BigDecimal(finalPers.getInt("height")).
                    subtract(getHeightDifferentiator(jsonArrows, finalizePerspective)).
                    divide(new BigDecimal(arraysObjectsFinal.length()), MathContext.DECIMAL128);
            BigDecimal yC4 = yB3.subtract(tam1).multiply(tam2).add(yC2);

            BigDecimal yC5 = yB3.multiply(tam20).multiply(tam2);
            baseYdiff = getGroupDiffOnly(jsonArrows, finalizePerspective).multiply(tam20);
            baseYdiff2 = yC5.subtract(baseYdiff);

            BigDecimal yC6 = new BigDecimal(finalizeObjective);
            BigDecimal yC7 = yC6.subtract(tam1).multiply(yC3);
            BigDecimal yC8 = yC3.divide(tam2, MathContext.DECIMAL128);
            BigDecimal yC = yC1.add(baseYdiff2).add(yC7).add(yC8).add(tam120).
                    add(incrementTam);
            if (useMargins) {
                yC.add(yC4).add(yC6);
            }
            y2 = yC.doubleValue();
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //quinta linea
            x1 = x2;
            BigDecimal xD1 = new BigDecimal(restCols).divide(tam100, MathContext.DECIMAL128).
                    multiply(divContainerCo);
            BigDecimal xD2 = new BigDecimal(columns[finalizeTheme - 1]);
            BigDecimal xD3 = xD2.divide(tam100, MathContext.DECIMAL128);
            BigDecimal xD4 = divSizeColum.divide(tam100, MathContext.DECIMAL128);
            BigDecimal xD5 = xD3.multiply(divContainerCo).multiply(xD4);
            BigDecimal xD = divTitl.add(xD1).add(xD5);
            x2 = xD.doubleValue();
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (!isFinalTheme) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
        } catch (JSONException ex) {
            log.error("Exception en paintOOIndexSame: " + ex);
        }
        map.put(countLine, sb);
        return map;
    }

    /**
     * Obtiene un JSONObject con los datos de los temas en una perspectiva
     *
     * @param perspective objeto de tipo {@code Perspective} de la perspectiva
     * contenedora deñ tema
     * @return objeto de tipo {@code JSONObject} devuelve la estructura de datos
     * de los temas
     */
    private JSONObject getArrayTheme(Perspective perspective) {
        JSONObject arrayTheme = new JSONObject();
        Iterator itTheme = perspective.listThemes();
        int count = 1;
        if (itTheme.hasNext()) {
            itTheme = BSCUtils.sortObjSortable(itTheme).listIterator();
            while (itTheme.hasNext()) {
                Theme theme = (Theme) itTheme.next();
                try {
                    JSONObject dataTheme = new JSONObject();
                    dataTheme.put("title", theme.getTitle());
                    JSONObject arrayObjs = getArrayObjective(theme);
                    dataTheme.put("arrayObjective", arrayObjs);
                    dataTheme.put("index", theme.getIndex());
                    if (arrayObjs.length() > 0) {
                        arrayTheme.put(count + "", dataTheme);
                        count++;
                    }
                } catch (JSONException ex) {
                    log.error("Exception getArrayTheme: " + ex);
                }
            }
        }
        return arrayTheme;
    }

    /**
     * Obtiene un JSONObject con los objetivos de un tema
     *
     * @param theme objeto de tipo {@code Theme} que contiene los objetivos que
     * seran almacenados en el objeto de tipo {@code JSONObject}
     * @return objeto de tipo {@code JSONObject} que contiene los objetivos de
     * un tema
     */
    private JSONObject getArrayObjective(Theme theme) {
        JSONObject arrayObjective = new JSONObject();
        Iterator itObjective = theme.listObjectives();
        int count = 1;
        if (itObjective.hasNext()) {
            itObjective = BSCUtils.sortObjSortable(itObjective).listIterator();
            while (itObjective.hasNext()) {
                Objective objective = (Objective) itObjective.next();
                try {
                    JSONObject dataObjective = new JSONObject();
                    dataObjective.put("title", objective.getTitle());
                    dataObjective.put("index", objective.getIndex());
                    arrayObjective.put(count + "", dataObjective);
                    count++;
                } catch (JSONException ex) {
                    log.error("Error getArrayObjectives: " + ex);
                }
            }
        }
        return arrayObjective;
    }

    /**
     * Obtiene el alto de una perspectiva
     *
     * @param perspective objeto de tipo {@code Perspective}
     * @param base Recurso de SWB utilizado para mostrarlo dentro de la
     * plataforma SWBPortal
     * @param maxObject n&uacute;mero de objetivos m&aacute;ximo
     * @return regresa el alto de una perspectiva
     */
    private int getHeightPerspective(Perspective perspective, Resource base, int maxObject) {
        int height = 0;//120
        if (maxObject > 0) {
            if (base.getData("perspective" + base.getId() + perspective.getId()) == null) {
                if ((base.getData("widthVerticalObjective") != null)
                        && (base.getData("widthVerticalObjective").trim().length() != 0)) {
                    height = Integer.parseInt(base.getData("widthVerticalObjective"));
                }
                if (maxObject > 0) {
                    height = height * maxObject;
                }
            } else if ((base.getData("perspective" + base.getId() + perspective.getId())) != null
                    && (base.getData("widthHorizontalObjective") != null)
                    && (base.getData("widthHorizontalObjective").trim().length() != 0)) {
                height = Integer.parseInt(base.getData("widthHorizontalObjective"));
            }
        }
        height = height + getHeightDifferentiator(perspective, base);

        return height;
    }

    private int getHeightDifferentiator(Perspective perspective, Resource base) {
        int height = 0;//Cabecera del tema
        Iterator itGroupPers = perspective.listDifferentiatorGroups();
        boolean isValid = false;
        if (itGroupPers.hasNext()) {
            while (itGroupPers.hasNext()) {
                DifferentiatorGroup diffeGroup = (DifferentiatorGroup) itGroupPers.next();
                if (diffeGroup.isActive()) {
                    Iterator<Differentiator> itDiff = diffeGroup.listDifferentiators();
                    if (itDiff.hasNext()) {
                        while (itDiff.hasNext()) {
                            Differentiator differentiator = itDiff.next();
                            if (differentiator.isActive() && differentiator.isValid()) {
                                isValid = true;
                                break;
                            }
                        }
                        if (isValid) {
                            int heigthDiff = 120;
                            if ((base.getData("widthHorizontalDifferentiator") != null)
                                    && (base.getData("widthHorizontalDifferentiator").trim().length() != 0)) {
                                heigthDiff = Integer.parseInt(base.getData("widthHorizontalDifferentiator"));
                            }
                            height = height + heigthDiff + 40 + 2; //cabecera del grupo de diferenciadores
                        }
                    }
                }
            }
        }
        return height;
    }

    private BigDecimal getGroupDiffOnly(JSONObject jsonArrow, int indexPerspectives) {
        int count = 0;
        try {
            for (int i = 1; i <= indexPerspectives; i++) {
                JSONObject obj = (JSONObject) jsonArrow.get(i + "");
                int maxObj = obj.getInt("maxObjectives");
                int heightDiff = obj.getInt("heightDiffe");
                if (maxObj == 0 && heightDiff > 0) {
                    count++;
                }
            }
        } catch (JSONException ex) {
            log.error("Exception in getGroupDiffOnly: " + ex);
        }
        return new BigDecimal(count);
    }

    /**
     * Obtiene el n&uacute;mero m&aacute;ximo de objetivos por tema
     *
     * @param perspective objeto de tipo {@code Perspective}
     * @return un n&uacute;mero con los objetivos del tema que contenga
     * m&aacute;s objetivos
     */
    private int getMaxObjectiveForTheme(Perspective perspective, Period period) {
        Iterator it = perspective.listThemes();
        int maxObjecForTheme = 0;
        List list = new ArrayList();
        it = BSCUtils.sortObjSortable(it).iterator();
        while (it.hasNext()) {
            Theme theme = (Theme) it.next();
            int countObjective = 0;
            Iterator itObjective = theme.listObjectives();
            itObjective = BSCUtils.sortObjSortable(itObjective).iterator();
            while (itObjective.hasNext()) {
                Objective objective = (Objective) itObjective.next();
                if ((objective.isActive()) && (objective.isValid())
                        && (objective.hasPeriod(period))) {
                    countObjective++;
                }
            }
            list.add(countObjective);
        }
        Collections.sort(list);
        if (!list.isEmpty()) {
            maxObjecForTheme = Integer.parseInt(list.get(list.size() - 1).toString());
        }
        return maxObjecForTheme;
    }
}
