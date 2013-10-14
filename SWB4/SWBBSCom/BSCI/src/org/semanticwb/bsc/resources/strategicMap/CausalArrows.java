/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.resources.strategicMap;

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
    private String triangleEnd = "#triangle-end";
    private JSONObject dataArrows = new JSONObject();

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
        if (itPers.hasNext()) {
            itPers = BSCUtils.sortObjSortable(itPers).listIterator();
            int count = 1;
            while (itPers.hasNext()) {
                try {
                    JSONObject perspectiveObj = new JSONObject();
                    Perspective perspective = (Perspective) itPers.next();
                    boolean config = base.getData("perspective" + base.getId() + perspective.getId())
                            == null ? false : true;
                    int maxObjectForPerspective = getMaxObjectiveForTheme(perspective, period);
                    perspectiveObj.put("index", perspective.getIndex());
                    perspectiveObj.put("title", perspective.getTitle());
                    perspectiveObj.put("height", getHeightPerspective(perspective, base,
                            maxObjectForPerspective));
                    perspectiveObj.put("isHorizontal", config);
                    perspectiveObj.put("maxObjectives", maxObjectForPerspective);
                    perspectiveObj.put("arrayTheme", getArrayTheme(perspective));
                    if (maxObjectForPerspective > 0) {
                        dataArrows.put(count + "", perspectiveObj);
                    }
                    count++;
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
     * flechas causa - efecti
     */
    private StringBuilder paintArrows(JSONObject dataStructure, Resource base, BSC bsc, Period period) {
        StringBuilder sb = new StringBuilder();
        Iterator<Perspective> itPers = bsc.listPerspectives();
        int countLinesAttributes = 1;
        StringBuilder sbAttributeLines = new StringBuilder();
        StringBuilder sbStatementLines = new StringBuilder();

        Iterator<Entry> itMap;
        while (itPers.hasNext()) {
            Perspective perspective = itPers.next();
            Iterator<Theme> listThemes = perspective.listThemes();
            while (listThemes.hasNext()) {
                Theme theme = listThemes.next();
                Iterator<Theme> itCausalTheme = theme.listCausalThemes();
                int startPerspectiveIndex = findIndexPerspective(perspective, dataStructure);
                int startThemeIndex = findIndexTheme(startPerspectiveIndex, dataStructure, theme);
                if (itCausalTheme.hasNext()) {
                    HashMap map = paintThemeTheme(dataStructure, startPerspectiveIndex, startThemeIndex,
                            base, itCausalTheme, countLinesAttributes, period);
                    itMap = map.entrySet().iterator();
                    while (itMap.hasNext()) {
                        Entry entry = itMap.next();
                        countLinesAttributes = Integer.parseInt(entry.getKey().toString());
                        StringBuilder sb1 = (StringBuilder) entry.getValue();
                        sbAttributeLines.append(sb1);
                    }
                }
                Iterator<Objective> itCausalobjective = theme.listCausalObjectives();
                if (itCausalobjective.hasNext()) {
                    HashMap map = paintThemeObjective(dataStructure, startPerspectiveIndex, startThemeIndex,
                            0, itCausalobjective, base, period, countLinesAttributes);
                    itMap = map.entrySet().iterator();
                    while (itMap.hasNext()) {
                        Entry entry = itMap.next();
                        countLinesAttributes = Integer.parseInt(entry.getKey().toString());
                        StringBuilder sb1 = (StringBuilder) entry.getValue();
                        sbAttributeLines.append(sb1);
                    }
                }
                Iterator<Objective> itObjectives = theme.listObjectives();
                while (itObjectives.hasNext()) {
                    Objective objective = itObjectives.next();
                    itCausalTheme = objective.listCausalThemes();
                    int startObjective = findIndexObjective(startPerspectiveIndex, dataStructure,
                            startThemeIndex, objective);
                    HashMap map = paintObjectiveTheme(dataStructure, startPerspectiveIndex, startThemeIndex,
                            startObjective, itCausalTheme, base, period, countLinesAttributes);
                    itMap = map.entrySet().iterator();
                    while (itMap.hasNext()) {
                        Entry entry = itMap.next();
                        countLinesAttributes = Integer.parseInt(entry.getKey().toString());
                        StringBuilder sb1 = (StringBuilder) entry.getValue();
                        sbAttributeLines.append(sb1);
                    }
                    itCausalobjective = objective.listCausalObjectives();
                    map = paintObjectiveObjective(dataStructure, startPerspectiveIndex, startThemeIndex,
                            startObjective, base, period, itCausalobjective, objective, countLinesAttributes);
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
        sbStatementLines.append(createLinesSVG(countLinesAttributes));
        sb.append(getJavascript(sbStatementLines, sbAttributeLines));
        sb.append(loadJavascript());
        return sb;
    }

    /**
     * Valida que el tema tenga objetivos con en el periodo proporcionado
     *
     * @param theme
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
            if (objective.hasPeriod(period)) {
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
            sb.append("line" + i + " = svg.appendChild(create(\"line\"));");
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
            int startThemeIndex, Resource base, Iterator<Theme> itCausalTheme, int countLine,
            Period period) {
        HashMap map1 = new HashMap();
        Iterator<Entry> itMap;
        StringBuilder sb = new StringBuilder();
        String classLine = ((base.getData("colorRelTT") == null) ||
                (base.getData("colorRelTT").trim().length() == 0))
                ? "arrow" : base.getData("colorRelTT");
        while (itCausalTheme.hasNext()) {
            Theme theme = itCausalTheme.next();
            if (isValidTheme(theme, period)) {
                HashMap map = new HashMap();
                Perspective perspective = theme.getPerspective();
                int finalPerspectiveIndex = findIndexPerspective(perspective, jsonArrows);
                int finalThemeIndex = findIndexTheme(finalPerspectiveIndex, jsonArrows, theme);
                if (startPerspectiveIndex != finalPerspectiveIndex) {
                    map = paintLinesThemeTheme(startPerspectiveIndex, startThemeIndex,
                            finalPerspectiveIndex, finalThemeIndex, countLine, classLine, jsonArrows);
                } else {
                    map = paintLinesSameThemeTheme(startPerspectiveIndex, startThemeIndex,
                            finalThemeIndex, countLine, classLine, jsonArrows);
                }
                itMap = map.entrySet().iterator();
                while (itMap.hasNext()) {
                    Entry entry = itMap.next();
                    countLine = Integer.parseInt(entry.getKey().toString());
                    StringBuilder sb1 = (StringBuilder) entry.getValue();
                    sb.append(sb1);
                }
            }
        }
        map1.put(countLine, sb);
        return map1;
    }

    /**
     * Pinta la relaci&oacute;n Causa - Efecto entre temas de misma perspectiva
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
    private HashMap paintLinesSameThemeTheme(int startPerspectiveIndex, int startThemeIndex,
            int finalThemeIndex, int countLine, String classLine, JSONObject jsonArrows) {
        HashMap map = new HashMap();
        StringBuilder sb = new StringBuilder();
        try {
            JSONObject startPers = (JSONObject) jsonArrows.get((startPerspectiveIndex - 1) + "");
            JSONObject arrayTheme = (JSONObject) startPers.get("arrayTheme");
            int noCols = arrayTheme.length();//total de columnas
            if (noCols == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            int restCols = (int) divContainerCols / noCols;//valor por columna
            int[] heightPer = getHeigthPerspectives(startPerspectiveIndex, jsonArrows);
            //primer linea
            x1 = (divTitle) + ((startThemeIndex * (restCols)) - (restCols / 2));
            x2 = x1;
            y1 = heightPer[0] + heightPer[1] + 20;//+ 120
            y2 = heightPer[0] + heightPer[1] + (2 * countLine);//+ 120
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //segunda linea
            x1 = x2;
            y1 = y2;
            y2 = y1;
            x2 = (divTitle) + ((finalThemeIndex * (restCols)) - (restCols / 2));
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //tercer linea
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + (2 * countLine);//+ 120
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
            int finalPerspectiveIndex, int finalThemeIndex,
            int countLine, String classLine, JSONObject jsonArrows) {
        HashMap map = new HashMap();
        StringBuilder sb = new StringBuilder();
        String triangleEnd = "#triangle-end";
        try {
            JSONObject startPers = (JSONObject) jsonArrows.get(startPerspectiveIndex + "");
            JSONObject arrayTheme = (JSONObject) startPers.get("arrayTheme");
            int noCols = arrayTheme.length();//total de columnas
            if (noCols == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            int restCols = (int) divContainerCols / noCols;//valor por columna
            //primer linea
            int[] heightPer = getHeigthPerspectives(startPerspectiveIndex - 1, jsonArrows);
            x1 = (divTitle) + ((startThemeIndex * (restCols)) - (restCols / 2));
            x2 = x1;
            y1 = heightPer[0] + heightPer[1] + 20 + 120; //cabecera
            y2 = heightPer[0] + heightPer[1] + (2 * countLine) + 120;
            
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //segunda linea
            int countCol = 1;
            if(countLine < 5) {
                countCol = countLine; 
            }
            x1 = x2;
            x2 = 92 + 5 + countCol;
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
            int[] aux = getHeigthPerspectives((finalPerspectiveIndex), jsonArrows);
            int margin = heightPer[1] + aux[1];
            y2 = heightPer[0] + margin + 120 + (20 * (finalPerspectiveIndex - 1));//
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalPerspectiveIndex + "");
            arrayTheme = (JSONObject) finalPers.get("arrayTheme");
            noCols = arrayTheme.length();//total de columnas
            if (noCols == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            restCols = (int) divContainerCols / noCols;//valor por columna            
            //cuarta linea
            x1 = x2;
            x2 = (divTitle) + ((finalThemeIndex * (restCols)) - (restCols / 2));
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //quinta linea
            heightPer = getHeigthPerspectives((finalPerspectiveIndex - 1), jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;

            aux = getHeigthPerspectives((finalPerspectiveIndex), jsonArrows);
            margin = heightPer[1] + aux[1];
//            y2 = heightPer[0] +  margin + 120 + (20 * (finalPerspectiveIndex - 1));//
            y2 = heightPer[0] + margin + 20 + (20 * (finalPerspectiveIndex - 1)) + 120;// 
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
        int[] heightPerspectives = new int[2];
        int addArrowY = 0;
        int filaAgregadasSuma = 0;
        for (int i = 1; i <= perspectiveIndex; i++) {
            try {
                JSONObject pers = (JSONObject) jsonArrows.get("" + i);
                addArrowY = addArrowY + pers.getInt("height");

                filaAgregadasSuma = filaAgregadasSuma + pers.getInt("maxObjectives");
            } catch (JSONException ex) {
                log.error("Exception getHeigthPerspectives: " + ex);
            }
        }
        if (perspectiveIndex > 1) {
            filaAgregadasSuma = filaAgregadasSuma * 1; // usado para los margenes
            //filaAgregadasSuma = filaAgregadasSuma * 20; // usado para los margenes
        }
        heightPerspectives[0] = addArrowY;
        heightPerspectives[1] = filaAgregadasSuma;
        return heightPerspectives;
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
            JSONObject perspective = (JSONObject) jsonArrows.get("" + indexPerspective);
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
            JSONObject perspective = (JSONObject) jsonArrows.get("" + indexPerspective);
            JSONObject arrayTheme = (JSONObject) perspective.get("arrayTheme");
            JSONObject themeData = (JSONObject) arrayTheme.get(indexTheme + "");
            JSONObject arrayObjective = (JSONObject) themeData.get("arrayObjective");
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
    private StringBuilder loadJavascript() {
        StringBuilder sb = new StringBuilder();
        int[] height = getHeigthPerspectives(dataArrows.length(), dataArrows);
        int heightAll = height[0] + height[1] + (dataArrows.length() * 20) + 120 + 40;
        sb.append("\n <div style=\"position:absolute; width:100%;height:" + heightAll);
        sb.append("px; float:left;  z-index:1 \" id=\"arrowLayer\" name=\"arrowLayer\">");
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
        sb.append("\n <link href=\"/swbadmin/css/mapaEstrategico.css\" rel=\"stylesheet\" type=\"text/css\" />");
        sb.append("\n<script type=\"text/javascript\">");
        sb.append("\n	function calculateDivs() {");
        sb.append("\n         var div1 = document.getElementById(\"arrowLayer\"),");
        sb.append("\n         svg = div1.appendChild(create(\"svg\")),");

        sb.append("\n         defs1 = svg.appendChild(create(\"defs\"));");
        sb.append(allLines);
        sb.append("\n         svg.setAttribute(\"width\", \"100%\");");
        sb.append("\n         svg.setAttribute(\"height\", \"100%\");");
        sb.append(getDownArrow());
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
    private StringBuilder getDownArrow() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n         var defs = document.createElementNS(\"http://www.w3.org/2000/svg\", \"defs\");");
        sb.append("\n         var marker = document.createElementNS(\"http://www.w3.org/2000/svg\", \"marker\");");
        sb.append("\n         marker.setAttribute(\"id\", 'triangle-end');");
        sb.append("\n         marker.setAttribute(\"viewBox\", '0 0 10 10');");
        sb.append("\n         marker.setAttribute(\"refX\", 10);");
        sb.append("\n         marker.setAttribute(\"refY\", 5);");
        sb.append("\n         marker.setAttribute(\"markerWidth\", 6);");
        sb.append("\n         marker.setAttribute(\"markerHeight\", 6);");
        sb.append("\n         marker.setAttribute(\"orient\", \"auto\");");
        sb.append("\n         marker.setAttribute(\"class\", \"arrow\");");
        sb.append("\n         var path = document.createElementNS(\"http://www.w3.org/2000/svg\", \"path\");");
        sb.append("\n         marker.appendChild(path);");
        sb.append("\n         path.setAttribute(\"d\", \"M 0 0 L 10 5 L 0 10 z\");");
        sb.append("\n         path.setAttribute(\"class\",\"arrow\");");
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
        sb.append("\n         line" + countLine + ".setAttribute(\"x1\", \"" + x1 + "%\");");
        sb.append("\n         line" + countLine + ".setAttribute(\"x2\", \"" + x2 + "%\");");
        sb.append("\n         line" + countLine + ".setAttribute(\"y1\", \"" + y1 + "\");");
        sb.append("\n         line" + countLine + ".setAttribute(\"y2\", \"" + y2 + "\");");
        sb.append("\n         line" + countLine + ".setAttribute(\"class\",\"" + classLine + "\");");
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
        sb.append("\n         line" + countLine + ".setAttribute(\"marker-end\",");
        sb.append("\"url(" + valueTriangle + ")\");");//#triangle-end
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
            JSONObject themes = (JSONObject) themesA.get("" + themeIndex);
            if (themeIndex == themes.length()) {
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
            int startThemeIndex, int startObjetiveIndex, Iterator<Objective> itCausalobjective,
            Resource base, Period period, int countLine) {
        HashMap map1 = new HashMap();
        Iterator<Entry> itMap;
        StringBuilder sb = new StringBuilder();
        String classLine = ((base.getData("colorRelTO") == null) ||
                (base.getData("colorRelTO").trim().length() == 0))
                ? "arrow" : base.getData("colorRelTO");
        while (itCausalobjective.hasNext()) {
            Objective objective = itCausalobjective.next();
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
                if (base.getData("perspective" + base.getId() + perspective.getId()) == null) {
                    if (isFinalTheme) {
                       // System.out.println("first option");
                        map = paintOTIndexFinalP(startPerspectiveIndex, startThemeIndex,
                                startObjetiveIndex, finalPerspectiveIndex, finalThemeIndex,
                                finalObjetiveIndex, jsonArrows, countLine, classLine);
                    } else if ((startObjetiveIndex > 0) && 
                            ((startPerspectiveIndex - finalPerspectiveIndex) == -1)) {
                        //objStart > 1 && (perspecstart - perspecFinal == -1)
                            //    else if (((startPerspectiveIndex - finalPerspectiveIndex) == 1)
                            //|| ((startPerspectiveIndex - finalPerspectiveIndex) == -1)) {
                      //  System.out.println("second option");
                        map = paintOTVIndexContinuos(startPerspectiveIndex, startThemeIndex, 
                                startObjetiveIndex, finalPerspectiveIndex, finalThemeIndex,
                                finalObjetiveIndex, jsonArrows, countLine, classLine);
                    } else {
                     //   System.out.println("third option");
                        
                        map = paintOTH(startPerspectiveIndex, startThemeIndex, startObjetiveIndex, 
                                finalPerspectiveIndex, finalThemeIndex, finalObjetiveIndex, 
                                jsonArrows, countLine, classLine);
                    }
                } else {
                    if (((startPerspectiveIndex - finalPerspectiveIndex) == 1)
                            || ((startPerspectiveIndex - finalPerspectiveIndex) == -1)) {
                        //System.out.println("four option");
                        
                        map = paintOTVHIndexContinuos(startPerspectiveIndex, startThemeIndex, 
                                startObjetiveIndex, finalPerspectiveIndex, finalThemeIndex,
                                finalObjetiveIndex, jsonArrows, countLine, classLine);
                    } else {
                      //  System.out.println("five option");
                        
                        map = paintOTV(startPerspectiveIndex, startThemeIndex, startObjetiveIndex, 
                                finalPerspectiveIndex, finalThemeIndex, finalObjetiveIndex,
                                jsonArrows, countLine, classLine);
                    }
                }
                itMap = map.entrySet().iterator();
                while (itMap.hasNext()) {
                    Entry entry = itMap.next();
                    countLine = Integer.parseInt(entry.getKey().toString());
                    StringBuilder sb1 = (StringBuilder) entry.getValue();
                    sb.append(sb1);
                }
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
            int startThemeIndex, int startObjetiveIndex, Iterator<Theme> itCausalTheme,
            Resource base, Period period, int countLine) {
        
        HashMap map1 = new HashMap();
        Iterator<Entry> itMap;
        StringBuilder sb = new StringBuilder();
       
        String classLine = ((base.getData("colorRelOT") == null) ||
                (base.getData("colorRelOT").trim().length() == 0))
                ? "arrow" : base.getData("colorRelOT");
        while (itCausalTheme.hasNext()) {
            Theme theme = itCausalTheme.next();
            if (isValidTheme(theme, period)) {
                HashMap map = new HashMap();
                Perspective perspective = theme.getPerspective();
                int finalPerspectiveIndex = findIndexPerspective(perspective, jsonArrows);
                int finalThemeIndex = findIndexTheme(finalPerspectiveIndex, jsonArrows, theme);
                int finalObjetiveIndex = 0;
                boolean isFinalTheme = getIndexFinalTheme(startThemeIndex, jsonArrows, 
                        startPerspectiveIndex);
                if (base.getData("perspective" + base.getId() + perspective.getId()) == null) {
                    if (isFinalTheme) {
                        map = paintOTIndexFinalP(startPerspectiveIndex, startThemeIndex, 
                                startObjetiveIndex, finalPerspectiveIndex, finalThemeIndex,
                                finalObjetiveIndex, jsonArrows, countLine, classLine);
                    } else if (((startPerspectiveIndex - finalPerspectiveIndex) == 1)
                            || ((startPerspectiveIndex - finalPerspectiveIndex) == -1)) {
                        map = paintOTVIndexContinuos(startPerspectiveIndex, startThemeIndex, 
                                startObjetiveIndex, finalPerspectiveIndex, finalThemeIndex,
                                finalObjetiveIndex, jsonArrows, countLine, classLine);
                    } else {
                        map = paintOTH(startPerspectiveIndex, startThemeIndex, startObjetiveIndex, 
                                finalPerspectiveIndex, finalThemeIndex, finalObjetiveIndex, 
                                jsonArrows, countLine, classLine);
                    }
                } else {
                    if (((startPerspectiveIndex - finalPerspectiveIndex) == 1)
                            || ((startPerspectiveIndex - finalPerspectiveIndex) == -1)) {
                        map = paintOTVHIndexContinuos(startPerspectiveIndex, startThemeIndex, 
                                startObjetiveIndex, finalPerspectiveIndex, finalThemeIndex,
                                finalObjetiveIndex, jsonArrows, countLine, classLine);
                    } else {
                        map = paintOTV(startPerspectiveIndex, startThemeIndex, startObjetiveIndex, 
                                finalPerspectiveIndex, finalThemeIndex, finalObjetiveIndex,
                                jsonArrows, countLine, classLine);
                    }
                }
                itMap = map.entrySet().iterator();
                while (itMap.hasNext()) {
                    Entry entry = itMap.next();
                    countLine = Integer.parseInt(entry.getKey().toString());
                    StringBuilder sb1 = (StringBuilder) entry.getValue();
                    sb.append(sb1);
                }
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
            int finalPerspectiveIndex, int finalThemeIndex, int finalObjetiveIndex,
            JSONObject jsonArrows, int countLine, String classLine) {
        HashMap map = new HashMap();
        StringBuilder sb = new StringBuilder();
        int initPerspective = startPerspectiveIndex;
        int initTheme = startThemeIndex;
        int initObjective = startObjetiveIndex;
        int finalizePerspective = finalPerspectiveIndex;
        int finalizeTheme = finalThemeIndex;
        if (finalPerspectiveIndex > startPerspectiveIndex) {
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
            int[] heightPer = getHeigthPerspectives(initPerspective, jsonArrows);
            int noCols = arrayTheme.length();//total de columnas
            if (noCols == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            if (arrayObjective.length() == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            int restCols = (int) divContainerCols / noCols;//valor por columna
            x1 = (divTitle) + (restCols * (initTheme - 1)) + ((divSizeColumn / arrayObjective.length())
                    * (initObjective - 1)) + (countLine * 2);//((startThemeIndex * (restCols)) - (restCols / 2));
            x2 = x1;
            y1 = heightPer[0] + heightPer[1] + 120;//
            y2 = y1 + (countLine * 2);//+ 120
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex < startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
            //segunda linea
            x1 = x2;
            x2 = divTitle + divContainerCols + (countLine * 2);
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //tercer linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + (countLine * 2);//+ 120
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //cuarta linea
            noCols = arrayThemeFinal.length();//total de columnas
            if (noCols == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            restCols = (int) divContainerCols / noCols;//valor por columna            
            x1 = x2;
            x2 = divTitle + (restCols * (finalizeTheme - 1)) + (restCols / 2) + (countLine * 2);
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //quinta linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + 20;//+ 120
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex > startPerspectiveIndex) {
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
            int finalObjetiveIndex, JSONObject jsonArrows, int countLine, String classLine) {
        HashMap map = new HashMap();
        StringBuilder sb = new StringBuilder();
        int initPerspective = startPerspectiveIndex;
        int initTheme = startThemeIndex;
        int initObjective = startObjetiveIndex;
        int finalizePerspective = finalPerspectiveIndex;
        int finalizeTheme = finalThemeIndex;
        if (finalPerspectiveIndex > startPerspectiveIndex) {
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
            int[] heightPer = getHeigthPerspectives(initPerspective, jsonArrows);
            int noCols = arrayTheme.length();//total de columnas
            if (noCols == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            if (arrayObjective.length() == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            int restCols = (int) divContainerCols / noCols;//valor por columna
            //primer linea
            x1 = (divTitle) + (restCols * (initTheme - 1)) + ((divSizeColumn / arrayObjective.length())
                    * (initObjective - 1)) + (countLine * 2);//((startThemeIndex * (restCols)) - (restCols / 2));
            x2 = x1;
            y1 = heightPer[0] + heightPer[1] + 120;// 
            y2 = y1 + (countLine * 2);// + 120
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex < startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
            //segunda linea
            noCols = arrayThemeFinal.length();//total de columnas
            if (noCols == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            restCols = (int) divContainerCols / noCols;//valor por columna            
            x1 = x2;
            x2 = divTitle + (restCols * (finalizeTheme - 1)) + (restCols / 2) + (countLine * 2);
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //tercer linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + 20;//+ 120
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex > startPerspectiveIndex) {
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
    private HashMap paintOTIndexFinalP(int startPerspectiveIndex, int startThemeIndex,
            int startObjetiveIndex, int finalPerspectiveIndex, int finalThemeIndex,
            int finalObjetiveIndex, JSONObject jsonArrows, int countLine, String classLine) {
        HashMap map = new HashMap();
        StringBuilder sb = new StringBuilder();
        int initPerspective = startPerspectiveIndex;
        int initTheme = startThemeIndex;
        int initObjective = startObjetiveIndex;
        int finalizePerspective = finalPerspectiveIndex;
        int finalizeTheme = finalThemeIndex;
        if (finalPerspectiveIndex > startPerspectiveIndex) {
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
            int[] heightPer = getHeigthPerspectives(initPerspective - 1, jsonArrows);
            int noCols = arrayTheme.length();//total de columnas
            if (noCols == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            if (arrayObjective.length() == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            int restCols = (int) divContainerCols / noCols;//valor por columna
            int heightObjective = (startPers.getInt("height") - 20) / arrayObjective.length();
            //primer linea
            x1 = (divTitle) + (restCols * (initTheme - 1)) + (restCols * (divSizeColumn / 100));//((startThemeIndex * (restCols)) - (restCols / 2));
            x2 = x1 + (2 * countLine);
            y1 = heightPer[0] + heightPer[1] + 20 + (heightObjective * (initObjective - 1))
                    + (countLine * 2) + 120;//
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex < startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
            //segunda linea
            heightPer = getHeigthPerspectives(initPerspective, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + (countLine * 2);//+ 120
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //tercer linea
            noCols = arrayThemeFinal.length();//total de columnas
            if (noCols == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            restCols = (int) divContainerCols / noCols;//valor por columna
            x1 = x2;
            x2 = divTitle + ((restCols) * (finalizeTheme - 1)) + (restCols / 2) + (countLine * 2);
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //cuarta linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + 20;//+ 120
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex > startPerspectiveIndex) {
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
            int finalObjetiveIndex, JSONObject jsonArrows, int countLine, String classLine) {
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
            int[] heightPer = getHeigthPerspectives(initPerspective - 1, jsonArrows);
            int noCols = arrayTheme.length();//total de columnas
            if (noCols == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            if (arrayObjective.length() == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            int restCols = (int) divContainerCols / noCols;//valor por columna
            int heightObjective = (startPers.getInt("height") - 20) / arrayObjective.length();
            //primer linea
            x1 = (divTitle) + (restCols * (initTheme - 1)) + (restCols * (divSizeColumn / 100));//((startThemeIndex * (restCols)) - (restCols / 2));
            x2 = x1 + (2 * countLine);
            y1 = heightPer[0] + heightPer[1] + 20 + (heightObjective * (initObjective - 1))
                    + (countLine * 2) + 120;//
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex < startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
            //segunda linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + (countLine * 2);//+ 120
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //tercer linea
            noCols = arrayThemeFinal.length();//total de columnas
            if (noCols == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            restCols = (int) divContainerCols / noCols;//valor por columna
            x1 = x2;
            x2 = divTitle + ((restCols) * (finalizeTheme - 1)) + (restCols / 2) + (countLine * 2);
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //cuarta linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + 20;//+ 120
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
    private HashMap paintOTH(int startPerspectiveIndex, int startThemeIndex, int startObjetiveIndex,
            int finalPerspectiveIndex, int finalThemeIndex, int finalObjetiveIndex,
            JSONObject jsonArrows, int countLine, String classLine) {
        HashMap map = new HashMap();

        StringBuilder sb = new StringBuilder();
        int initPerspective = startPerspectiveIndex;
        int initTheme = startThemeIndex;
        int initObjective = startObjetiveIndex;
        int finalizePerspective = finalPerspectiveIndex;
        int finalizeObjective = finalObjetiveIndex;
        if (finalPerspectiveIndex > startPerspectiveIndex) {
            initPerspective = finalPerspectiveIndex;
            initTheme = finalThemeIndex;
            initObjective = finalObjetiveIndex;
            finalizePerspective = startPerspectiveIndex;
            finalizeObjective = startObjetiveIndex;
        }
        try {
            JSONObject startPers = (JSONObject) jsonArrows.get(initPerspective + "");
            JSONObject arrayTheme = (JSONObject) startPers.get("arrayTheme");
            JSONObject themePer = (JSONObject) arrayTheme.get(initTheme + "");
            JSONObject arraysObjects = (JSONObject) themePer.get("arrayObjective");
            JSONObject arrayObjective = (JSONObject) arraysObjects.get(initObjective + "");
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalizePerspective + "");
            JSONObject arrayThemeFinal = (JSONObject) finalPers.get("arrayTheme");
            int[] heightPer = getHeigthPerspectives(initPerspective - 1, jsonArrows);
            int noCols = arrayTheme.length();//total de columnas
            if (noCols == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            if (arrayObjective.length() == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            int restCols = (int) divContainerCols / noCols;//valor por columna
            int heightObjective = (startPers.getInt("height") - 20) / arrayObjective.length();
            //primer linea
            x1 = (divTitle) + (restCols * (initTheme - 1)) + (restCols * (divSizeColumn / 100));//((startThemeIndex * (restCols)) - (restCols / 2));
            x2 = x1 + (2 * countLine);
            y1 = heightPer[0] + heightPer[1] + 20 + (heightObjective * (initObjective - 1))
                    + (countLine * 2);//+ 120
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex < startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
            //segunda linea
            heightPer = getHeigthPerspectives(initPerspective, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + (countLine * 2);//+ 120
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //tercer linea
            x1 = x2;
            x2 = divTitle + divContainerCols + (countLine * 2);
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //cuarta linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + (countLine * 2);//+ 120
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //quinta linea
            noCols = arrayThemeFinal.length();//total de columnas
            if (noCols == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            restCols = (int) divContainerCols / noCols;//valor por columna            
            x1 = x2;
            x2 = (restCols * (finalizeObjective - 1)) + (restCols / 2) + (countLine * 2);
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //sexta linea
            heightPer = getHeigthPerspectives(finalizePerspective, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + 20;//+ 120
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex > startPerspectiveIndex) {
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
            Iterator<Objective> itCausalobjective, Objective objStart, int countLine) {
        HashMap map1 = new HashMap();
        StringBuilder sb = new StringBuilder();

        Perspective persStart = objStart.getTheme().getPerspective();
        String classLine = ((base.getData("colorRelTO") == null) ||
                (base.getData("colorRelTO").trim().length() == 0)) ? 
                "arrow" : base.getData("colorRelTO");
        while (itCausalobjective.hasNext()) {
            Objective objective = itCausalobjective.next();
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
                    if ((isFinalTheme) || (isStartTheme)) {
                       // System.out.println("1");
                        map = paintOOIndexFinalP(startPerspectiveIndex, startThemeIndex,
                                startObjetiveIndex, finalPerspectiveIndex, finalThemeIndex,
                                finalObjetiveIndex, isFinalTheme, jsonArrows, countLine, classLine);
                    } else if ((((startPerspectiveIndex - finalPerspectiveIndex) == 1)
                            && (finalThemeIndex == startThemeIndex))
                            || ((startPerspectiveIndex - finalPerspectiveIndex == -1)
                            && (finalThemeIndex == startThemeIndex))) {
                       // System.out.println("2");
                        map = paintOOIndexContinuos(startPerspectiveIndex, startThemeIndex,
                                startObjetiveIndex, finalPerspectiveIndex, finalThemeIndex,
                                finalObjetiveIndex, jsonArrows, countLine, classLine);
                    } else {
                       // System.out.println("3");
                        map = paintOO(startPerspectiveIndex, startThemeIndex, startObjetiveIndex,
                                finalPerspectiveIndex, finalThemeIndex, finalObjetiveIndex,
                                jsonArrows, countLine, classLine);
                    }
                } else if ((base.getData("perspective" + base.getId() + perspective.getId()) != null)
                        && (base.getData("perspective" + base.getId() + persStart.getId()) != null)) {
                    //System.out.println("4");
                    map = paintOOHH(startPerspectiveIndex, startThemeIndex, startObjetiveIndex,
                            finalPerspectiveIndex, finalThemeIndex, finalObjetiveIndex, jsonArrows,
                            countLine, classLine);
                } else {
                    if ((isFinalTheme) || (isStartTheme)) {
                      //  System.out.println("5");
                        map = paintOOH_VSame(startPerspectiveIndex, startThemeIndex, startObjetiveIndex,
                                finalPerspectiveIndex, finalThemeIndex, finalObjetiveIndex, jsonArrows,
                                countLine, classLine);
                    } else {
                       // System.out.println("6");
                        map = paintOOH_V(startPerspectiveIndex, startThemeIndex, startObjetiveIndex,
                                finalPerspectiveIndex, finalThemeIndex, finalObjetiveIndex, jsonArrows,
                                countLine, classLine);
                    }

                }
                Iterator<Entry> itMap = map.entrySet().iterator();
                while (itMap.hasNext()) {
                    Entry entry = itMap.next();
                    countLine = Integer.parseInt(entry.getKey().toString());
                    StringBuilder sb1 = (StringBuilder) entry.getValue();
                    sb.append(sb1);
                }
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
            int finalObjetiveIndex, JSONObject jsonArrows, int countLine, String classLine) {
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
            JSONObject arrayObjective = (JSONObject) arraysObjects.get(initObjective + "");
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalizePerspective + "");
            JSONObject arrayThemeFinal = (JSONObject) finalPers.get("arrayTheme");
            JSONObject themePerFinal = (JSONObject) arrayThemeFinal.get(finalizeTheme + "");
            JSONObject arraysObjectsFinal = (JSONObject) themePerFinal.get("arrayObjective");
            JSONObject arrayObjectiveFinal = (JSONObject) arraysObjectsFinal.get(finalizeObjective + "");
            int[] heightPer = getHeigthPerspectives(initPerspective, jsonArrows);
            int noCols = arrayTheme.length();//total de columnas
            if (noCols == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            if (arrayObjective.length() == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            int restCols = (int) divContainerCols / noCols;//valor por columna
            int heigthPers = finalPers.getInt("height");
            //primer linea
            x1 = (divTitle) + (restCols * (initTheme - 1)) + ((divSizeColumn / arrayObjective.length()) * (initObjective - 1))
                    + ((divSizeColumn / arrayObjective.length()) / 2) + (countLine * 2);
            x2 = x1;
            y1 = heightPer[0] + heightPer[1];
            y2 = y1 + (2 * countLine);
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex > startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
            //segunda linea
            x1 = x2;
            x2 = divTitle + divContainerCols + (countLine * 2);
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //tercer linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            if (arrayObjectiveFinal.length() == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + 20 + (((heigthPers - 20) / arrayObjectiveFinal.length())
                    * (finalizeObjective - 1)) + (((heigthPers - 20) / arrayObjectiveFinal.length())
                    / 2) + (countLine * 2);
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //cuarta linea
            noCols = arrayThemeFinal.length();//total de columnas
            if (noCols == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            restCols = (int) divContainerCols / noCols;
            x1 = x2;
            x2 = (restCols * (finalizeTheme - 1)) + (restCols * (divSizeColumn / 100));
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex < startPerspectiveIndex) {
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
            int finalPerspectiveIndex, int finalThemeIndex, int finalObjetiveIndex,
            JSONObject jsonArrows, int countLine, String classLine) {
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
            JSONObject arrayObjective = (JSONObject) arraysObjects.get(initObjective + "");
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalizePerspective + "");
            JSONObject arrayThemeFinal = (JSONObject) finalPers.get("arrayTheme");
            JSONObject themePerFinal = (JSONObject) arrayThemeFinal.get(finalizeTheme + "");
            JSONObject arraysObjectsFinal = (JSONObject) themePerFinal.get("arrayObjective");
            JSONObject arrayObjectiveFinal = (JSONObject) arraysObjectsFinal.get(finalizeObjective + "");
            int[] heightPer = getHeigthPerspectives(initPerspective - 1, jsonArrows);
            int noCols = arrayTheme.length();//total de columnas
            if (noCols == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            int restCols = (int) divContainerCols / noCols;//valor por columna
            int heigthPers = startPers.getInt("height");
            //primer linea
            x1 = (divTitle) + (restCols * (initTheme - 1)) + (restCols * divSizeColumn);
            x2 = x1 + (countLine * 2);
            if (arrayObjective.length() == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            y1 = heightPer[0] + heightPer[1] + 20 + ((heigthPers - 20) / arrayObjective.length())
                    * (initObjective - 1) + (((heigthPers - 20) / arrayTheme.length()) / 2)
                    + (countLine * 2);
            y2 = y1 + (2 * countLine);
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex > startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
            //segunda linea
            heightPer = getHeigthPerspectives(initPerspective, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + (countLine * 2);
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //tercer linea
            x1 = x2;
            x2 = divTitle + divContainerCols + (countLine * 2);
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //cuarta linea
            heightPer = getHeigthPerspectives(finalizePerspective, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + (countLine * 2);
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //quinta linea
            noCols = arrayThemeFinal.length();//total de columnas
            if (noCols == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            if (arrayObjectiveFinal.length() == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            restCols = (int) divContainerCols / noCols;//valor por columna
            heigthPers = finalPers.getInt("height");
            x1 = x2;
            x2 = (restCols * (finalizeTheme - 1)) + ((divSizeColumn / arrayObjectiveFinal.length())
                    * (finalizeObjective - 1)) + (countLine * 2);
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //sexta linea
            heightPer = getHeigthPerspectives(finalizePerspective, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1];
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex < startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
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
            JSONObject jsonArrows, int countLine, String classLine) {
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
            JSONObject arrayObjective = (JSONObject) arraysObjects.get(initObjective + "");
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalizePerspective + "");
            JSONObject arrayThemeFinal = (JSONObject) finalPers.get("arrayTheme");
            JSONObject themePerFinal = (JSONObject) arrayThemeFinal.get(finalizeTheme + "");
            JSONObject arraysObjectsFinal = (JSONObject) themePerFinal.get("arrayObjective");
            JSONObject arrayObjectiveFinal = (JSONObject) arraysObjectsFinal.get(finalizeObjective + "");
            int[] heightPer = getHeigthPerspectives(initPerspective, jsonArrows);
            int noCols = arrayTheme.length();//total de columnas
            if (noCols == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            if (arrayObjective.length() == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            int restCols = (int) divContainerCols / noCols;//valor por columna
            //primer linea
            x1 = (divTitle) + (restCols * (initTheme - 1))
                    + ((divSizeColumn / arrayObjective.length()) * (initObjective - 1))
                    + (countLine * 2);//(restCols * (divSizeColumn/100));//((startThemeIndex * (restCols)) - (restCols / 2));
            x2 = x1;
            y1 = heightPer[0] + heightPer[1];
            y2 = y1 + (2 * countLine);
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex > startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
            //segunda linea
            x1 = x2;
            x2 = divTitle + divContainerCols + (countLine * 2);
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //tercer linea
            heightPer = getHeigthPerspectives(finalizePerspective, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + (countLine * 2);
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //cuarta linea
            noCols = arrayThemeFinal.length();//total de columnas
            if (noCols == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            if (arrayObjectiveFinal.length() == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            restCols = (int) divContainerCols / noCols;
            x1 = x2;
            x2 = (divTitle) + (restCols * (initTheme - 1))
                    + ((divSizeColumn / arrayObjectiveFinal.length()) * (finalizeObjective - 1))
                    + (countLine * 2);
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + (countLine * 2);
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //quinta linea
            heightPer = getHeigthPerspectives(finalizePerspective, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1];
            if (finalPerspectiveIndex < startPerspectiveIndex) {
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
            int finalObjetiveIndex, JSONObject jsonArrows, int countLine, String classLine) {
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
            JSONObject arrayObjective = (JSONObject) arraysObjects.get(initObjective + "");
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalizePerspective + "");
            JSONObject arrayThemeFinal = (JSONObject) finalPers.get("arrayTheme");
            JSONObject themePerFinal = (JSONObject) arrayThemeFinal.get(finalizeTheme + "");
            JSONObject arraysObjectsFinal = (JSONObject) themePerFinal.get("arrayObjective");
            JSONObject arrayObjectiveFinal = (JSONObject) arraysObjectsFinal.get(finalizeObjective + "");
            int[] heightPer = getHeigthPerspectives(initPerspective - 1, jsonArrows);
            int noCols = arrayTheme.length();//total de columnas
            if (noCols == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            if (arrayObjective.length() == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            int restCols = (int) divContainerCols / noCols;//valor por columna
            int heightObjective = (startPers.getInt("height") - 20) / arrayObjective.length();
            //primer linea
            x1 = (divTitle) + (restCols * (initTheme - 1)) + (restCols * (divSizeColumn / 100));//((startThemeIndex * (restCols)) - (restCols / 2));
            x2 = x1 + (2 * countLine);
            y1 = heightPer[0] + heightPer[1] + (heightObjective * initObjective) + 20;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex > startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
            //Segunda linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            if (arrayObjectiveFinal.length() == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            heightObjective = (finalPers.getInt("height") - 20) / arrayObjectiveFinal.length();
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + 20 + (heightObjective * finalizeObjective);
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //Tercer linea
            noCols = arrayThemeFinal.length();
            if (noCols == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            restCols = (int) divContainerCols / noCols;
            x1 = x2;
            x2 = (divTitle) + (restCols * (finalizeTheme - 1)) + (restCols * (divSizeColumn / 100));
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex < startPerspectiveIndex) {
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
            JSONObject jsonArrows, int countLine, String classLine) {
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
            JSONObject arrayObjective = (JSONObject) arraysObjects.get(initObjective + "");
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalizePerspective + "");
            JSONObject arrayThemeFinal = (JSONObject) finalPers.get("arrayTheme");
            JSONObject themePerFinal = (JSONObject) arrayThemeFinal.get(finalizeTheme + "");
            JSONObject arraysObjectsFinal = (JSONObject) themePerFinal.get("arrayObjective");
            JSONObject arrayObjectiveFinal = (JSONObject) arraysObjectsFinal.get(finalizeObjective + "");
            int[] heightPer = getHeigthPerspectives(initPerspective - 1, jsonArrows);
            int noCols = arrayTheme.length();//total de columnas
            if (noCols == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            int restCols = (int) divContainerCols / noCols;//valor por columna
            if (arrayObjective.length() == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            int heightObjective = (startPers.getInt("height") - 20) / arrayObjective.length();
            //primer linea
            x1 = (divTitle) + (restCols * (initTheme - 1)) + (restCols * (0.95));//((startThemeIndex * (restCols)) - (restCols / 2));
            x2 = x1 + 1;//(2 * countLine)
            y1 = heightPer[0] + heightPer[1] + (heightObjective * initObjective) + 20;//+ 120
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex < startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            countLine++;
            //segunda linea
            heightPer = getHeigthPerspectives(initPerspective, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + (20 * initPerspective) + 120;//
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //tercera linea
            x1 = x2;
            x2 = divTitle + divContainerCols + (1);//(divTitle) + (restCols * (finalizeTheme - 1)) + (restCols * 0.95) + (countLine * 2);
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //cuarta linea
            heightPer = getHeigthPerspectives(finalizePerspective, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + (20 * finalizePerspective) + 120;//+ 120
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //quinta linea
            noCols = arrayThemeFinal.length();//total de columnas
            if (noCols == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            restCols = (int) divContainerCols / noCols;//valor por columna
            x1 = x2;
            int margin = 1;
            if ((divContainerCols % noCols) != 0) {
                margin = divContainerCols % noCols + margin;
            }
            x2 = divTitle + (restCols * (finalizeTheme - 1)) + (restCols * (0.95)) + margin;
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //sexta linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            if (arrayObjectiveFinal.length() == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            heightObjective = (finalPers.getInt("height") - 20) / arrayObjectiveFinal.length();
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + (countLine * 2) + (heightObjective * initObjective);//+ 120
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //septima linea
            noCols = arrayThemeFinal.length();//total de columnas
            if (noCols == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            restCols = (int) divContainerCols / noCols;
            x1 = x2;
            x2 = (divTitle) + (restCols * (finalizeTheme - 1)) + (restCols * (0.95));;
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
            int countLine, String classLine) {
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
            JSONObject arrayObjective = (JSONObject) arraysObjects.get(initObjective + "");
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalizePerspective + "");
            JSONObject arrayThemeFinal = (JSONObject) finalPers.get("arrayTheme");
            JSONObject themePerFinal = (JSONObject) arrayThemeFinal.get(finalizeTheme + "");
            JSONObject arraysObjectsFinal = (JSONObject) themePerFinal.get("arrayObjective");
            JSONObject arrayObjectiveFinal = (JSONObject) arraysObjectsFinal.get(finalizeObjective + "");
            int[] heightPer = getHeigthPerspectives(initPerspective - 1, jsonArrows);
            int noCols = arrayTheme.length();//total de columnas
            if (noCols == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            if (arrayObjective.length() == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            int restCols = (int) divContainerCols / noCols;//valor por columna
            int heightObjective = (startPers.getInt("height") - 20) / arrayObjective.length();
            //primer linea
            x1 = (divTitle) + divContainerCols ;//((startThemeIndex * (restCols)) - (restCols / 2));
//            x1 = (divTitle) + (restCols * (initTheme)) + (restCols * (divSizeColumn / 100));//((startThemeIndex * (restCols)) - (restCols / 2));
            int margE = 2;
            if(countLine < 5) {
                margE = countLine;
            }
            x2 = x1  + margE;
            y1 = heightPer[0] + heightPer[1] + ((heightObjective * initObjective) / 2) + 20 + 120;
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
            y2 = heightPer[0] + heightPer[1] + 120 + (20 * finalizePerspective);//+ 120 (countLine * 2)
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //tercer linea
            noCols = arrayThemeFinal.length();//total de columnas
            if (noCols == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            restCols = (int) divContainerCols / noCols;
            x1 = x2;
            int margin = 1;
            if ((divContainerCols % noCols) != 0) {
                margin = (divContainerCols % noCols) + margin;
            }
            x2 = (divTitle) + (restCols * (finalizeTheme -1)) + (restCols * (0.95))
                    + margin;
            //System.out.println("restCols: " + restCols + ", x2= " + x2 + "div: " + divSizeColumn);
            //System.out.println("mult: " + (divContainerCols%noCols));
                    //((restCols * (finalizeTheme)) - 2);// + (restCols * (divSizeColumn/100))
                    //+ (countLine * 2);
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //cuarta linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            if (arrayObjectiveFinal.length() == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            heightObjective = (finalPers.getInt("height") - 20) / arrayObjectiveFinal.length();
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + 20 + (heightObjective * finalizeObjective);//+ 120
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            countLine++;
            //quinta linea
            noCols = arrayThemeFinal.length();//total de columnas
            if (noCols == 0) {
                throw new java.lang.ArithmeticException("/ by zero");
            }
            restCols = (int) divContainerCols / noCols;
            x1 = x2;
            margin = 0;
            if ((divContainerCols % noCols) != 0) {
                margin = 1;
            }
            x2 = (divTitle) + (restCols * (finalizeTheme - 1)) +(restCols * (0.95)) + margin;
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
        Iterator<Theme> itTheme = perspective.listThemes();
        int count = 1;
        while (itTheme.hasNext()) {
            try {
                Theme theme = itTheme.next();
                JSONObject dataTheme = new JSONObject();
                dataTheme.put("title", theme.getTitle());
                dataTheme.put("arrayObjective", getArrayObjective(theme));
                dataTheme.put("index", theme.getIndex());
                arrayTheme.put(count + "", dataTheme);
                count++;
            } catch (JSONException ex) {
                log.error("Exception getArrayTheme: " + ex);
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
        Iterator<Objective> itObjective = theme.listObjectives();
        int count = 1;
        while (itObjective.hasNext()) {
            try {
                Objective objective = itObjective.next();
                JSONObject dataObjective = new JSONObject();
                dataObjective.put("title", objective.getTitle());
                dataObjective.put("index", objective.getIndex());
                arrayObjective.put(count + "", dataObjective);
                count++;
            } catch (JSONException ex) {
                log.error("Error getArrayObjectives: " + ex);
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
        int height = 120;
        if (base.getData("perspective" + base.getId() + perspective.getId()) == null) {
            if ((base.getData("widthVerticalObjective") != null) &&
                    (base.getData("widthVerticalObjective").trim().length() != 0)) {
                height = Integer.parseInt(base.getData("widthVerticalObjective"));
            }
            if (maxObject > 0) {
                height = height * maxObject;
            }
        } else if ((base.getData("perspective" + base.getId() + perspective.getId())) != null
                && (base.getData("widthHorizontalObjective") != null) &&
                (base.getData("widthHorizontalObjective").trim().length() != 0)) {
            height = Integer.parseInt(base.getData("widthHorizontalObjective"));
        }
        height = height + 20;//Cabecera del tema
        Iterator itGroupPers = perspective.listDifferentiatorGroups();
        if (itGroupPers.hasNext()) {
            while (itGroupPers.hasNext()) {
                DifferentiatorGroup diffeGroup = (DifferentiatorGroup) itGroupPers.next();
                Iterator<Differentiator> itDiff = diffeGroup.listDifferentiators();
                if (itDiff.hasNext()) {
                    int heigthDiff = 120;
                    if ((base.getData("widthHorizontalDifferentiator") != null) &&
                            (base.getData("widthHorizontalDifferentiator").trim().length() != 0)) {
                        heigthDiff = Integer.parseInt(base.getData("widthHorizontalDifferentiator"));
                    }
                    height = height + heigthDiff + 20; //cabecera del grupo de diferenciadores
                }
            }
        }
        return height;
    }

    /**
     * Obtiene el n&uacute;mero m&aacute;ximo de objetivos por tema
     *
     * @param perspective objeto de tipo {@code Perspective}
     * @return un n&uacute;mero con los objetivos del tema que contenga
     * m&aacute;s objetivos
     */
    private int getMaxObjectiveForTheme(Perspective perspective, Period period) {
        Iterator<Theme> it = perspective.listThemes();
        int maxObjecForTheme = 0;
        List list = new ArrayList();
        while (it.hasNext()) {
            Theme theme = it.next();
            int countObjective = 0;
            Iterator<Objective> itObjective = theme.listObjectives();

            while (itObjective.hasNext()) {
                Objective objective = itObjective.next();
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
