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

    /**
     * Construye una instancia de tipo {@code CausalArrows}
     *
     * @param _componente
     */
    public CausalArrows(ComponentMap _componente) {
        super(_componente);
    }

    /**
     * Permite agregar m&aacute;s cosas al Mapa Estrat&eacute;gico
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
        sb.append(paintArrows(getStructureDataArrows(base, bsc), base, bsc, period));
        return sb;
    }

    private JSONObject getStructureDataArrows(Resource base, BSC bsc) {
        JSONObject dataArrows = new JSONObject();
        Iterator itPers = bsc.listPerspectives();
        if (itPers.hasNext()) {
            itPers = BSCUtils.sortObjSortable(itPers).listIterator();
            int count = 1;
            while (itPers.hasNext()) {
                try {
                    JSONObject perspectiveObj = new JSONObject();
                    Perspective perspective = (Perspective) itPers.next();
                    boolean config = base.getData("perspective" + base.getId() + perspective.getId()) == null ? false : true;
                    int maxObjectForPerspective = getMaxObjectiveForTheme(perspective);
                    perspectiveObj.put("index", perspective.getIndex());
                    perspectiveObj.put("title", perspective.getTitle());
                    perspectiveObj.put("height", getHeightPerspective(perspective, base, maxObjectForPerspective));
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

    private StringBuilder paintArrows(JSONObject dataStructure, Resource base, BSC bsc, Period period) {
        StringBuilder sb = new StringBuilder();
        Iterator<Perspective> itPers = bsc.listPerspectives();
        int countLines = 1;
        int countLinesAttributes = 1;
        StringBuilder sbAttributeLines = new StringBuilder();
        StringBuilder sbStatementLines = new StringBuilder();
        HashMap map = new HashMap();
        Iterator<Entry> itMap;
        while (itPers.hasNext()) {
            Perspective perspective = itPers.next();
            Iterator<Theme> listThemes = perspective.listThemes();
            while (listThemes.hasNext()) {
                Theme theme = listThemes.next();
                Iterator<Theme> itCausalTheme = theme.listCausalThemes();
                int startPerspectiveIndex = findIndexPerspective(perspective, dataStructure);
                    //System.out.println("startPerspectiveIndex: " + startPerspectiveIndex);
                int startThemeIndex = findIndexTheme(startPerspectiveIndex, dataStructure, theme);
                if (itCausalTheme.hasNext()) {

                    //int startPerspectiveIndex = findIndexPerspective(perspective, dataStructure);
                    //System.out.println("startPerspectiveIndex: " + startPerspectiveIndex);
                    //int startThemeIndex = findIndexTheme(startPerspectiveIndex, dataStructure, theme);
                    //System.out.println("startThemeIndex: " + startThemeIndex);
                    map = paintThemeTheme(dataStructure, startPerspectiveIndex, startThemeIndex, base, 
                            itCausalTheme, countLinesAttributes, period);
                    itMap = map.entrySet().iterator();
                    while (itMap.hasNext()) {
                        Entry entry = itMap.next();
                        countLines = Integer.parseInt(entry.getKey().toString());
                        StringBuilder sb1 = (StringBuilder) entry.getValue();
                        sbAttributeLines.append(sb1);
                    }
                   // System.out.println("countLines: " + countLines);
                    map = createLineTheme(itCausalTheme, countLines, period);
                    itMap = map.entrySet().iterator();
                    while (itMap.hasNext()) {
                        Entry entry = itMap.next();
                        countLines = Integer.parseInt(entry.getKey().toString());
                        StringBuilder sb1 = (StringBuilder) entry.getValue();
                        sbStatementLines.append(sb1);
                    }
                }
                Iterator<Objective> itCausalobjective = theme.listCausalObjectives();
                if (itCausalobjective.hasNext()) { //
                    //int startPerspectiveIndex = findIndexPerspective(perspective, dataStructure);
                    //System.out.println("startPerspectiveIndex: " + startPerspectiveIndex);
                    //int startThemeIndex = findIndexTheme(startPerspectiveIndex, dataStructure, theme);
                    sbAttributeLines.append(paintThemeObjective(dataStructure, startPerspectiveIndex, startThemeIndex, 0, 
                            itCausalobjective, base, period, countLinesAttributes));
                    map = createLineObjective(itCausalobjective, countLines, period);
                    itMap = map.entrySet().iterator();
                    while (itMap.hasNext()) {
                        Entry entry = itMap.next();
                        countLines = Integer.parseInt(entry.getKey().toString());
                        StringBuilder sb1 = (StringBuilder) entry.getValue();
                        sbStatementLines.append(sb1);
                    }
                }
                Iterator<Objective> itObjectives = theme.listObjectives();
                while (itObjectives.hasNext()) {
                    Objective objective = itObjectives.next();
                    itCausalTheme = objective.listCausalThemes();
                    int startObjective = findIndexObjective(startPerspectiveIndex, dataStructure, startThemeIndex, objective);
                    sbAttributeLines.append(paintObjectiveTheme(dataStructure, startPerspectiveIndex, startThemeIndex, startObjective, 
                            itCausalTheme, base, period, countLinesAttributes));
                    if (itCausalTheme.hasNext()) {
                        map = createLineTheme(itCausalTheme, countLines, period);
                        itMap = map.entrySet().iterator();
                        while (itMap.hasNext()) {
                            Entry entry = itMap.next();
                            countLines = Integer.parseInt(entry.getKey().toString());
                            StringBuilder sb1 = (StringBuilder) entry.getValue();
                            sbStatementLines.append(sb1);
                        }
                    }
                    itCausalobjective = objective.listCausalObjectives();//countLinesAttributes
                    sbAttributeLines.append(paintObjectiveObjective(dataStructure, startPerspectiveIndex, startThemeIndex, startObjective, base, period, 
                            itCausalobjective, objective, countLines));
                    if (itCausalobjective.hasNext()) {
                        map = createLineObjective(itCausalobjective, countLines, period);
                        itMap = map.entrySet().iterator();
                        while (itMap.hasNext()) {
                            Entry entry = itMap.next();
                            countLines = Integer.parseInt(entry.getKey().toString());
                            StringBuilder sb1 = (StringBuilder) entry.getValue();
                            sbStatementLines.append(sb1);
                        }
                    }
                }
            }
        }
        sb.append(getJavascript(sbStatementLines, sbAttributeLines));
        sb.append(loadJavascript());
        return sb;
    }

    private HashMap createLineTheme(Iterator<Theme> themes, int count, Period period) {
        HashMap map = new HashMap();
        StringBuilder sb = new StringBuilder();
        while (themes.hasNext()) {
            Theme theme = themes.next();
            if (isValidTheme(theme, period)) {
                sb.append("line" + count + " = svg.appendChild(create(\"line\"));");
            }
        }
        map.put(count, sb);
        return map;
    }

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

    private HashMap createLineObjective(Iterator<Objective> objectives, int count, Period period) {
        HashMap map = new HashMap();
        StringBuilder sb = new StringBuilder();
        while (objectives.hasNext()) {
            Objective objective = objectives.next();
            if (objective.hasPeriod(period)) {
                sb.append("line" + count + " = svg.appendChild(create(\"line\"));");
            }
        }
        map.put(count, sb);
        return map;
    }

    private HashMap paintThemeTheme(JSONObject jsonArrows, int startPerspectiveIndex, int startThemeIndex,
            Resource base, Iterator<Theme> itCausalTheme, int countLine, Period period) {
        HashMap map = new HashMap();
        StringBuilder sb = new StringBuilder();
        int finalPerspectiveIndex = 0;
        int finalThemeIndex = 0;
        String classLine = base.getData("colorRelTT") == null ? "arrow" : base.getData("colorRelTT");
        while (itCausalTheme.hasNext()) {
            Theme theme = itCausalTheme.next();
            if (isValidTheme(theme, period)) {
                Perspective perspective = theme.getPerspective();
                finalPerspectiveIndex = findIndexPerspective(perspective, jsonArrows);
                finalThemeIndex = findIndexTheme(finalPerspectiveIndex, jsonArrows, theme);
                System.out.println("--------------en paintThemeThme: " + countLine);
                if (startPerspectiveIndex != finalPerspectiveIndex) {
                    sb.append(getLinesThemeTheme(startPerspectiveIndex, startThemeIndex,
                            finalPerspectiveIndex, finalThemeIndex, countLine, classLine, jsonArrows));
                } else {
                    sb.append(getLinesSameThemeTheme(startPerspectiveIndex, startThemeIndex,
                            finalPerspectiveIndex, finalThemeIndex, countLine, classLine, jsonArrows));
                }
                countLine++;
            }
        }
        map.put(countLine, sb);
        return map;
    }

    private StringBuilder getLinesSameThemeTheme(int startPerspectiveIndex, int startThemeIndex, int finalPerspectiveIndex, int finalThemeIndex,
            int countLine, String classLine, JSONObject jsonArrows) {
        StringBuilder sb = new StringBuilder();
//        double x1 = 0;
//        double x2 = 0;
//        double y1 = 0;
//        double y2 = 0;
        try {
            JSONObject startPers = (JSONObject) jsonArrows.get(startPerspectiveIndex + "");
            JSONObject arrayTheme = (JSONObject) startPers.get("arrayTheme");
            int noCols = arrayTheme.length();//total de columnas
            int restCols = (int) divContainerCols / noCols;//valor por columna
            x1 = (divTitle) + ((startThemeIndex * (restCols)) - (restCols / 2));
            x2 = x1;
            /*Calculo Y alto de perspectivas*/
            int[] heightPer = getHeigthPerspectives(startPerspectiveIndex, jsonArrows);
            x1 = (divTitle) + ((startThemeIndex * (restCols)) - (restCols / 2));
            x2 = x1;
            y1 = heightPer[0] + heightPer[1] + 20;
            y2 = heightPer[0] + heightPer[1] + (1 * countLine);
            /*for (int i = 1; i < startPerspectiveIndex; i++) {
             JSONObject pers = (JSONObject) jsonArrows.get("" + i);
             addArrowY = addArrowY + pers.getInt("height");
             filaAgregadasSuma = filaAgregadasSuma + pers.getInt("maxObjectives");
             }
             if (startPerspectiveIndex > 1) {
             filaAgregadasSuma = filaAgregadasSuma * 20;
             }*/
            /*Calculo Y*/
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            x1 = x2;
            y1 = y2;
            y2 = y1;
            int diff = restCols * (startThemeIndex - finalThemeIndex);
            if (startPerspectiveIndex > finalPerspectiveIndex) {
                x2 = x1 - diff;
            } else {
                x2 = x1 + diff;
            }
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + (1 * countLine);
            sb.append(paintLineTriangle(countLine, triangleEnd));
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
        } catch (JSONException ex) {
            log.error("Exception in getLinesSameThemeTheme: " + ex);
        }
        return sb;
    }

    private StringBuilder getLinesThemeTheme(int startPerspectiveIndex, int startThemeIndex, int finalPerspectiveIndex, int finalThemeIndex,
            int countLine, String classLine, JSONObject jsonArrows) {
        StringBuilder sb = new StringBuilder();
        String triangleEnd = "#triangle-end";
//        double x1 = 0;
//        double x2 = 0;
//        double y1 = 0;
//        double y2 = 0;
        try {
            JSONObject startPers = (JSONObject) jsonArrows.get(startPerspectiveIndex + "");
            JSONObject arrayTheme = (JSONObject) startPers.get("arrayTheme");
            int noCols = arrayTheme.length();//total de columnas
            int restCols = (int) divContainerCols / noCols;//valor por columna
            //int colActualPerc = (countLine * 1) * startThemeIndex; // variable dependiendo cual columna se esta pintando

            int[] heightPer = getHeigthPerspectives(startPerspectiveIndex, jsonArrows);
            /*                    //Convertir en funcion
             int addArrowY = 0;
             int filaAgregadasSuma = 0;
             for (int i = 1; i < startPerspectiveIndex; i++) {
             JSONObject pers = (JSONObject) jsonArrows.get("" + i);
             addArrowY = addArrowY + pers.getInt("height");
             filaAgregadasSuma = filaAgregadasSuma + pers.getInt("maxObjectives");
             }
             if (startPerspectiveIndex > 1) {
             filaAgregadasSuma = filaAgregadasSuma * 20;
             }
             //Fin de convertir en funcion devuelve dos enteros en un int[]
             */
            x1 = (divTitle) + ((startThemeIndex * (restCols)) - (restCols / 2));
            x2 = x1;
            y1 = heightPer[0] + heightPer[1] + 20;
            y2 = heightPer[0] + heightPer[1] + (1 * countLine);

            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));

            x1 = x2;
            x2 = 92 + 5 + countLine;
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            x1 = x2;
            x2 = x1;
            y1 = y2;
            /*Calculo Y alto de perspectivas*/
            heightPer = getHeigthPerspectives(finalPerspectiveIndex, jsonArrows);
            /*filaAgregadasSuma = 0;
             for (int i = 1; i < finalPerspectiveIndex; i++) {
             JSONObject pers = (JSONObject) jsonArrows.get("" + i);
             addArrowY = addArrowY + pers.getInt("height");
             filaAgregadasSuma = filaAgregadasSuma + pers.getInt("maxObjectives");
             }
             if (startPerspectiveIndex > 1) {
             filaAgregadasSuma = filaAgregadasSuma * 20;
             }*/
            /*Calculo Y*/
            if (startPerspectiveIndex > finalPerspectiveIndex) {
                y2 = y1 - heightPer[0];
            } else {
                y2 = y1 + heightPer[0];
            }
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalPerspectiveIndex + "");
            arrayTheme = (JSONObject) finalPers.get("arrayTheme");
            noCols = arrayTheme.length();//total de columnas
            restCols = (int) divContainerCols / noCols;//valor por columna            
            x1 = x2;
            x2 = (divTitle) + ((finalThemeIndex * (restCols)) - (restCols / 2));
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            /*Calculo Y alto de perspectivas*/
            heightPer = getHeigthPerspectives(finalPerspectiveIndex, jsonArrows);
            /*for (int i = 1; i < finalPerspectiveIndex; i++) {
             JSONObject pers = (JSONObject) jsonArrows.get("" + i);
             addArrowY = addArrowY + pers.getInt("height");
             filaAgregadasSuma = filaAgregadasSuma + pers.getInt("maxObjectives");
             }
             if (finalPerspectiveIndex > 1) {
             filaAgregadasSuma = filaAgregadasSuma * 20;
             }*/
            /*Calculo Y*/
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + 20;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            sb.append(paintLineTriangle(countLine, triangleEnd));
        } catch (JSONException ex) {
            log.error("Exception get lines (Theme Theme): " + ex);
        }
        return sb;
    }

    private int[] getHeigthPerspectives(int perspectiveIndex, JSONObject jsonArrows) {
        int[] heightPerspectives = new int[2];
        int addArrowY = 0;
        int filaAgregadasSuma = 0;
        /*Calculo Y alto de perspectivas*/
        for (int i = 1; i < perspectiveIndex; i++) {
            try {
                JSONObject pers = (JSONObject) jsonArrows.get("" + i);
                addArrowY = addArrowY + pers.getInt("height");
                filaAgregadasSuma = filaAgregadasSuma + pers.getInt("maxObjectives");
            } catch (JSONException ex) {
                log.error("Exception getHeigthPerspectives: " + ex);
            }
        }
        if (perspectiveIndex > 1) {
            filaAgregadasSuma = filaAgregadasSuma * 20;
        }
        heightPerspectives[0] = addArrowY;
        heightPerspectives[1] = filaAgregadasSuma;
        /*Calculo Y*/
        return heightPerspectives;
    }

    private int findIndexTheme(int indexPerspective, JSONObject jsonArrows, Theme theme) {
        int indexTheme = 0;
        try {
            JSONObject perspective = (JSONObject) jsonArrows.get("" + indexPerspective);
            JSONObject arrayTheme = (JSONObject) perspective.get("arrayTheme");
            boolean find = false;
            int i = 1;
            while (i <= arrayTheme.length() && !find) {
                JSONObject obj = (JSONObject) arrayTheme.get(i + "");
                int index = obj.getInt("index");
                if (theme.getIndex() == index) {
                    indexTheme = i;
                    find = true;
                }
                i++;
            }
        } catch (JSONException ex) {
            log.error("Exception find indez theme: " + ex);
        }
        return indexTheme;
    }

    private int findIndexObjective(int indexPerspective, JSONObject jsonArrows, int indexTheme, Objective objective) {
        int indexObjective = 0;
        try {
            JSONObject perspective = (JSONObject) jsonArrows.get("" + indexPerspective);
            JSONObject arrayTheme = (JSONObject) perspective.get("arrayTheme");
            JSONObject themeData = (JSONObject) arrayTheme.get(indexTheme + "");
            JSONObject arrayObjective = (JSONObject) themeData.get("arrayObjective");

            boolean find = false;
            int i = 1;
            while (i <= arrayObjective.length() && !find) {
                JSONObject obj = (JSONObject) arrayObjective.get(i + "");
                int index = obj.getInt("index");
                if (objective.getIndex() == index) {
                    indexObjective = i;
                    find = true;
                }
                i++;
            }

        } catch (JSONException ex) {
            log.error("Exception find indez theme: " + ex);
        }
        return indexObjective;
    }

    private int findIndexPerspective(Perspective perspective, JSONObject jsonArrows) {
        int indexPerspective = 0;
        boolean find = false;
        int i = 1;
        while (i <= jsonArrows.length() && !find) {
            try {
                JSONObject obj = (JSONObject) jsonArrows.get(i + "");
                int index = obj.getInt("index");
                if (perspective.getIndex() == index) {
                    indexPerspective = i;
                    find = true;
                }
            } catch (JSONException ex) {
                log.error("Error get indexPerspective: " + ex);
            }
            i++;
        }
        return indexPerspective;
    }

    private StringBuilder loadJavascript() {
        //10.26 - 10.30
        StringBuilder sb = new StringBuilder();//z-index:-1,visibility:visible
        sb.append("\n <div style=\"position:absolute; width:100%;height:100%; float:left;  z-index:1 \" id=\"arrowLayer\" name=\"arrowLayer\">");
        sb.append("\n </div>");
        sb.append("\n <script type=\"text/javascript\">");
        //sb.append("     <!--");
        sb.append("\n         dojo.addOnLoad( function(){");
        sb.append("\n             calculateDivs();}");
        sb.append("\n         );");
        //sb.append("     -->");
        sb.append("\n </script>");
        return sb;
    }

    private StringBuilder getJavascript(StringBuilder allLines, StringBuilder allAttributeLines) {
        //<LINE x1="10" y1="10" x2="100" y2="100"/> 9.45 - 10.02
        StringBuilder sb = new StringBuilder();
        sb.append("\n <link href=\"/swbadmin/css/mapaEstrategico.css\" rel=\"stylesheet\" type=\"text/css\" />");
        sb.append("\n<script type=\"text/javascript\">");
        sb.append("\n<!--");
        sb.append("\n	function calculateDivs() {");
        sb.append("\n         var div1 = document.getElementById(\"arrowLayer\"),");
        sb.append("\n         svg = div1.appendChild(create(\"svg\")),");

        sb.append("\n         defs1 = svg.appendChild(create(\"defs\"));");
        sb.append(allLines);
        //llamado a todas las lineas creadas

        sb.append("\n         svg.setAttribute(\"width\", \"100%\");");
        sb.append("\n         svg.setAttribute(\"height\", \"100%\");");
        sb.append(getDownArrow());

        sb.append(allAttributeLines);
//        sb.append("\n         line2.setAttribute(\"x1\",  \"1%\");");
//        sb.append("\n         line2.setAttribute(\"x2\",  \"1%\");");
//        sb.append("\n         line2.setAttribute(\"y1\",  \"10%\");");
//        sb.append("\n         line2.setAttribute(\"y2\",  \"20%\");");
//        sb.append("\n         line2.setAttribute(\"class\",\"arrow\");");
//        sb.append("\n         line2.setAttribute(\"marker-end\", \"url(#triangle-end)\");");

        sb.append("\n     }");
        sb.append("\n     function create(type) {");
        sb.append("\n		return document.createElementNS(\"http://www.w3.org/2000/svg\", type);");
        sb.append("\n	 }");

        sb.append("     \n</script>");
        return sb;
    }

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

    private StringBuilder paintLines(int countLine, double x1, double x2, double y1, double y2, String classLine) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n         line" + countLine + ".setAttribute(\"x1\", \"" + x1 + "%\");");
        sb.append("\n         line" + countLine + ".setAttribute(\"x2\", \"" + x2 + "%\");");
        sb.append("\n         line" + countLine + ".setAttribute(\"y1\", \"" + y1 + "\");");
        sb.append("\n         line" + countLine + ".setAttribute(\"y2\", \"" + y2 + "\");");
        sb.append("\n         line" + countLine + ".setAttribute(\"class\",\"" + classLine + "\");");
        return sb;
    }

    private StringBuilder paintLineTriangle(int countLine, String valueTriangle) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n         line" + countLine + ".setAttribute(\"marker-end\", \"url(" + valueTriangle + ")\");");//#triangle-end
        return sb;
    }

    private boolean getIndexTheme(int themeIndex, JSONObject jsonArrows, int perspectiveIndex) {
        boolean isFinalTheme = false;
        try {
            JSONObject perspe = (JSONObject) jsonArrows.get("" + perspectiveIndex);
            JSONObject themes = (JSONObject) perspe.get("" + themeIndex);
            if (themeIndex == themes.length()) {
                isFinalTheme = true;
            }
        } catch (JSONException ex) {
            log.error("Exception in getIndexTheme: " + ex);
        }
        return isFinalTheme;
    }


    private HashMap paintThemeObjective(JSONObject jsonArrows, int startPerspectiveIndex, int startThemeIndex, int startObjetiveIndex,
            Iterator<Objective> itCausalobjective, Resource base, Period period, int countLine) {
        HashMap map = new HashMap();
        StringBuilder sb = new StringBuilder();
        int finalPerspectiveIndex = 0;
        int finalThemeIndex = 0;
        int finalObjetiveIndex = 0;
        String classLine = base.getData("colorRelTO") == null ? "arrow" : base.getData("colorRelTO");
        while (itCausalobjective.hasNext()) {
            Objective objective = itCausalobjective.next();
            if (objective.hasPeriod(period)) {
                Perspective perspective = objective.getTheme().getPerspective();
                finalPerspectiveIndex = findIndexPerspective(perspective, jsonArrows);
                finalThemeIndex = findIndexTheme(finalPerspectiveIndex, jsonArrows, objective.getTheme());
                finalObjetiveIndex = findIndexObjective(finalPerspectiveIndex, jsonArrows, finalThemeIndex, objective);
                boolean isFinalTheme = getIndexTheme(finalThemeIndex, jsonArrows, finalPerspectiveIndex);
                if (base.getData("perspective" + base.getId() + perspective.getId()) == null) {
                    if (isFinalTheme) {
                        //aqui traer funcion que haga lo necesario
                        sb.append(paintOTIndexFinalP(startPerspectiveIndex, startThemeIndex, startObjetiveIndex, finalPerspectiveIndex, finalThemeIndex,
                                finalObjetiveIndex, jsonArrows, countLine, classLine));

                    } else if (((startPerspectiveIndex - finalPerspectiveIndex) == 1) || 
                            ((startPerspectiveIndex - finalPerspectiveIndex) == -1)) {
                        sb.append(paintOTVIndexContinuos(startPerspectiveIndex, startThemeIndex, startObjetiveIndex, finalPerspectiveIndex, finalThemeIndex,
                                finalObjetiveIndex, jsonArrows, countLine, classLine));
                    } else {
                        sb.append(paintOTH(startPerspectiveIndex, startThemeIndex, startObjetiveIndex, finalPerspectiveIndex, finalThemeIndex,
                                finalObjetiveIndex, jsonArrows, countLine, classLine));
                    }
                } else {
                    if (((startPerspectiveIndex - finalPerspectiveIndex) == 1) || 
                            ((startPerspectiveIndex - finalPerspectiveIndex) == -1)) {
                        sb.append(paintOTVHIndexContinuos(startPerspectiveIndex, startThemeIndex, startObjetiveIndex, finalPerspectiveIndex, finalThemeIndex,
                                finalObjetiveIndex, jsonArrows, countLine, classLine));
                    } else {
                        //aqui traer funcion que haga lo necesario
                        sb.append(paintOTV(startPerspectiveIndex, startThemeIndex, startObjetiveIndex, finalPerspectiveIndex, finalThemeIndex, finalObjetiveIndex,
                                jsonArrows, countLine, classLine));
                    }
                }
                countLine++;
            }
        }
        map.put(countLine, sb);
        return map;
    }

    private HashMap paintObjectiveTheme(JSONObject jsonArrows, int startPerspectiveIndex, int startThemeIndex,
            int startObjetiveIndex, Iterator<Theme> itCausalTheme, Resource base, Period period, int countLine) {
        HashMap map = new HashMap();
        StringBuilder sb = new StringBuilder();
        int finalPerspectiveIndex = 0;
        int finalThemeIndex = 0;
        int finalObjetiveIndex = 0;
        String classLine = base.getData("colorRelOT") == null ? "arrow" : base.getData("colorRelOT");
        while (itCausalTheme.hasNext()) {
            Theme theme = itCausalTheme.next();
            if (isValidTheme(theme, period)) {
                Perspective perspective = theme.getPerspective();
                finalPerspectiveIndex = findIndexPerspective(perspective, jsonArrows);
                finalThemeIndex = findIndexTheme(finalPerspectiveIndex, jsonArrows, theme);
                boolean isFinalTheme = getIndexTheme(startThemeIndex, jsonArrows, startPerspectiveIndex);
                if (base.getData("perspective" + base.getId() + perspective.getId()) == null) {
                    if (isFinalTheme) {
                        //aqui traer funcion que haga lo necesario C
                        sb.append(paintOTIndexFinalP(startPerspectiveIndex, startThemeIndex, startObjetiveIndex, finalPerspectiveIndex, finalThemeIndex,
                                finalObjetiveIndex, jsonArrows, countLine, classLine));
                    } else if (((startPerspectiveIndex - finalPerspectiveIndex) == 1)
                            || ((startPerspectiveIndex - finalPerspectiveIndex) == -1)) {
                        //aqui traer funcion que haga lo necesario B
                        sb.append(paintOTVIndexContinuos(startPerspectiveIndex, startThemeIndex, startObjetiveIndex, finalPerspectiveIndex, finalThemeIndex,
                                finalObjetiveIndex, jsonArrows, countLine, classLine));
                    } else {
                        //aqui traer funcion que haga lo necesario A
                        sb.append(paintOTH(startPerspectiveIndex, startThemeIndex, startObjetiveIndex, finalPerspectiveIndex, finalThemeIndex,
                                finalObjetiveIndex, jsonArrows, countLine, classLine));
                    }
                } else {
                    if (((startPerspectiveIndex - finalPerspectiveIndex) == 1)
                            || ((startPerspectiveIndex - finalPerspectiveIndex) == -1)) {
                        //aqui traer funcion que haga lo necesario A
                        sb.append(paintOTVHIndexContinuos(startPerspectiveIndex, startThemeIndex, startObjetiveIndex, finalPerspectiveIndex, finalThemeIndex,
                                finalObjetiveIndex, jsonArrows, countLine, classLine));
                    } else {
                        //aqui traer funcion que haga lo necesario B
                        sb.append(paintOTV(startPerspectiveIndex, startThemeIndex, startObjetiveIndex, finalPerspectiveIndex, finalThemeIndex, finalObjetiveIndex,
                                jsonArrows, countLine, classLine));
                    }
                }
                countLine++;
            }
        }

        map.put(countLine, sb);
        return map;
    }

    private StringBuilder paintOTV(int startPerspectiveIndex, int startThemeIndex, int startObjetiveIndex,
            int finalPerspectiveIndex, int finalThemeIndex, int finalObjetiveIndex, JSONObject jsonArrows,
            int countLine, String classLine) {
        StringBuilder sb = new StringBuilder();
        int initPerspective = startPerspectiveIndex;
        int initTheme = startThemeIndex;
        int initObjective = startObjetiveIndex;
        int finalizePerspective = finalPerspectiveIndex;
        int finalizeTheme = finalThemeIndex;
        //int finalizeObjective = finalObjetiveIndex;
        if (finalPerspectiveIndex > startPerspectiveIndex) {
            initPerspective = finalPerspectiveIndex;
            initTheme = finalThemeIndex;
            initObjective = finalObjetiveIndex;
            finalizePerspective = startPerspectiveIndex;
            finalizeTheme = startThemeIndex;
            //  finalizeObjective = startObjetiveIndex;
        }
        try {
            JSONObject startPers = (JSONObject) jsonArrows.get(initPerspective + "");
            JSONObject arrayTheme = (JSONObject) startPers.get("arrayTheme");
            JSONObject themePer = (JSONObject) arrayTheme.get(initTheme + "");
            JSONObject arrayObjective = (JSONObject) themePer.get(initObjective + "");
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalizePerspective + "");
            JSONObject arrayThemeFinal = (JSONObject) finalPers.get("arrayTheme");
            //JSONObject themePerFinal = (JSONObject) arrayThemeFinal.get(finalizeTheme + "");
            //JSONObject arrayObjectiveFinal = (JSONObject) themePerFinal.get(finalizeObjective + "");
            int[] heightPer = getHeigthPerspectives(initPerspective, jsonArrows);
            int noCols = arrayTheme.length();//total de columnas
            int restCols = (int) divContainerCols / noCols;//valor por columna
            //int heightObjective = (startPers.getInt("height") - 20) / arrayObjective.length();
            //primer linea
            x1 = (divTitle) + (restCols * (initTheme - 1)) + ((divSizeColumn / arrayObjective.length()) * (initObjective - 1)) + (countLine * 2);//((startThemeIndex * (restCols)) - (restCols / 2));
            x2 = x1;
            y1 = heightPer[0] + heightPer[1];
            y2 = y1 + (countLine * 2);
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex < startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }

            //segunda linea
            x1 = x2;
            x2 = divTitle + divContainerCols + (countLine * 2);
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));

            //tercer linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + (countLine * 2);
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));

            //cuarta linea
            noCols = arrayThemeFinal.length();//total de columnas
            restCols = (int) divContainerCols / noCols;//valor por columna            
            x1 = x2;
            x2 = divTitle + (restCols * (finalizeTheme - 1)) + (restCols / 2) + (countLine * 2);
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));

            //quinta linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + 20;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex > startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
        } catch (JSONException ex) {
            log.error("Exception in paintOTVHIndexContinuos: " + ex);
        }
        return sb;
    }

    private StringBuilder paintOTVHIndexContinuos(int startPerspectiveIndex, int startThemeIndex, int startObjetiveIndex,
            int finalPerspectiveIndex, int finalThemeIndex, int finalObjetiveIndex, JSONObject jsonArrows,
            int countLine, String classLine) {
        StringBuilder sb = new StringBuilder();
        int initPerspective = startPerspectiveIndex;
        int initTheme = startThemeIndex;
        int initObjective = startObjetiveIndex;
        int finalizePerspective = finalPerspectiveIndex;
        int finalizeTheme = finalThemeIndex;
        //int finalizeObjective = finalObjetiveIndex;
        if (finalPerspectiveIndex > startPerspectiveIndex) {
            initPerspective = finalPerspectiveIndex;
            initTheme = finalThemeIndex;
            initObjective = finalObjetiveIndex;
            finalizePerspective = startPerspectiveIndex;
            finalizeTheme = startThemeIndex;
            //    finalizeObjective = startObjetiveIndex;
        }
        try {
            JSONObject startPers = (JSONObject) jsonArrows.get(initPerspective + "");
            JSONObject arrayTheme = (JSONObject) startPers.get("arrayTheme");
            JSONObject themePer = (JSONObject) arrayTheme.get(initTheme + "");
            JSONObject arrayObjective = (JSONObject) themePer.get(initObjective + "");
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalizePerspective + "");
            JSONObject arrayThemeFinal = (JSONObject) finalPers.get("arrayTheme");
            //JSONObject themePerFinal = (JSONObject) arrayThemeFinal.get(finalizeTheme + "");
            //JSONObject arrayObjectiveFinal = (JSONObject) themePerFinal.get(finalizeObjective + "");
            int[] heightPer = getHeigthPerspectives(initPerspective, jsonArrows);
            int noCols = arrayTheme.length();//total de columnas
            int restCols = (int) divContainerCols / noCols;//valor por columna
            //int heightObjective = (startPers.getInt("height") - 20) / arrayObjective.length();
            //primer linea
            x1 = (divTitle) + (restCols * (initTheme - 1)) + ((divSizeColumn / arrayObjective.length()) * (initObjective - 1)) + (countLine * 2);//((startThemeIndex * (restCols)) - (restCols / 2));
            x2 = x1;
            y1 = heightPer[0] + heightPer[1];
            y2 = y1 + (countLine * 2);
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex < startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }

            //segunda linea
            noCols = arrayThemeFinal.length();//total de columnas
            restCols = (int) divContainerCols / noCols;//valor por columna            
            x1 = x2;
            x2 = divTitle + (restCols * (finalizeTheme - 1)) + (restCols / 2) + (countLine * 2);
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));

            //tercer linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + 20;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex > startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
        } catch (JSONException ex) {
            log.error("Exception in paintOTVHIndexContinuos: " + ex);
        }
        return sb;
    }

    private StringBuilder paintOTIndexFinalP(int startPerspectiveIndex, int startThemeIndex, int startObjetiveIndex,
            int finalPerspectiveIndex, int finalThemeIndex, int finalObjetiveIndex, JSONObject jsonArrows,
            int countLine, String classLine) {
        StringBuilder sb = new StringBuilder();
        int initPerspective = startPerspectiveIndex;
        int initTheme = startThemeIndex;
        int initObjective = startObjetiveIndex;
        int finalizePerspective = finalPerspectiveIndex;
        int finalizeTheme = finalThemeIndex;
        //int finalizeObjective = finalObjetiveIndex;
        if (finalPerspectiveIndex > startPerspectiveIndex) {
            initPerspective = finalPerspectiveIndex;
            initTheme = finalThemeIndex;
            initObjective = finalObjetiveIndex;
            finalizePerspective = startPerspectiveIndex;
            finalizeTheme = startThemeIndex;
            //  finalizeObjective = startObjetiveIndex;
        }
        try {
            JSONObject startPers = (JSONObject) jsonArrows.get(initPerspective + "");
            JSONObject arrayTheme = (JSONObject) startPers.get("arrayTheme");
            JSONObject themePer = (JSONObject) arrayTheme.get(initTheme + "");
            JSONObject arrayObjective = (JSONObject) themePer.get(initObjective + "");
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalizePerspective + "");
            JSONObject arrayThemeFinal = (JSONObject) finalPers.get("arrayTheme");
            //JSONObject themePerFinal = (JSONObject) arrayThemeFinal.get(finalizeTheme + "");
            //JSONObject arrayObjectiveFinal = (JSONObject) themePerFinal.get(finalizeObjective + "");

            int[] heightPer = getHeigthPerspectives(initPerspective - 1, jsonArrows);
            int noCols = arrayTheme.length();//total de columnas
            int restCols = (int) divContainerCols / noCols;//valor por columna
            int heightObjective = (startPers.getInt("height") - 20) / arrayObjective.length();
            //primer linea
            x1 = (divTitle) + (restCols * (initTheme - 1)) + (restCols * (divSizeColumn / 100));//((startThemeIndex * (restCols)) - (restCols / 2));
            x2 = x1 + (2 * countLine);
            y1 = heightPer[0] + heightPer[1] + 20 + (heightObjective * (initObjective - 1))
                    + (countLine * 2);
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex < startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }

            //segunda linea
            heightPer = getHeigthPerspectives(initPerspective, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + (countLine * 2);
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));

            //tercer linea
            noCols = arrayThemeFinal.length();//total de columnas
            restCols = (int) divContainerCols / noCols;//valor por columna
            x1 = x2;
            x2 = divTitle + ((restCols) * (finalizeTheme - 1)) + (restCols / 2) + (countLine * 2);
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));

            //cuarta linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + 20;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex > startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }

        } catch (JSONException ex) {
            log.error("Exception in paintOTIndexFinalP: " + ex);
        }
        return sb;
    }

    private StringBuilder paintOTVIndexContinuos(int startPerspectiveIndex, int startThemeIndex, int startObjetiveIndex,
            int finalPerspectiveIndex, int finalThemeIndex, int finalObjetiveIndex, JSONObject jsonArrows,
            int countLine, String classLine) {
        StringBuilder sb = new StringBuilder();
        int initPerspective = startPerspectiveIndex;
        int initTheme = startThemeIndex;
        int initObjective = startObjetiveIndex;
        int finalizePerspective = finalPerspectiveIndex;
        int finalizeTheme = finalThemeIndex;
        //int finalizeObjective = finalObjetiveIndex;
        if (finalPerspectiveIndex > startPerspectiveIndex) {
            initPerspective = finalPerspectiveIndex;
            initTheme = finalThemeIndex;
            initObjective = finalObjetiveIndex;
            finalizePerspective = startPerspectiveIndex;
            finalizeTheme = startThemeIndex;
            //    finalizeObjective = startObjetiveIndex;
        }
        try {
            JSONObject startPers = (JSONObject) jsonArrows.get(initPerspective + "");
            JSONObject arrayTheme = (JSONObject) startPers.get("arrayTheme");
            JSONObject themePer = (JSONObject) arrayTheme.get(initTheme + "");
            JSONObject arrayObjective = (JSONObject) themePer.get(initObjective + "");
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalizePerspective + "");
            JSONObject arrayThemeFinal = (JSONObject) finalPers.get("arrayTheme");
            //JSONObject themePerFinal = (JSONObject) arrayThemeFinal.get(finalizeTheme + "");
            //JSONObject arrayObjectiveFinal = (JSONObject) themePerFinal.get(finalizeObjective + "");

            int[] heightPer = getHeigthPerspectives(initPerspective - 1, jsonArrows);
            int noCols = arrayTheme.length();//total de columnas
            int restCols = (int) divContainerCols / noCols;//valor por columna
            int heightObjective = (startPers.getInt("height") - 20) / arrayObjective.length();
            //primer linea
            x1 = (divTitle) + (restCols * (initTheme - 1)) + (restCols * (divSizeColumn / 100));//((startThemeIndex * (restCols)) - (restCols / 2));
            x2 = x1 + (2 * countLine);
            y1 = heightPer[0] + heightPer[1] + 20 + (heightObjective * (initObjective - 1))
                    + (countLine * 2);
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex < startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }

            //segunda linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + (countLine * 2);
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));

            //tercer linea
            noCols = arrayThemeFinal.length();//total de columnas
            restCols = (int) divContainerCols / noCols;//valor por columna
            x1 = x2;
            x2 = divTitle + ((restCols) * (finalizeTheme - 1)) + (restCols / 2) + (countLine * 2);
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));

            //cuarta linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + 20;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex > startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }

        } catch (JSONException ex) {
            log.error("Exception in paintOTVIndexContinuos: " + ex);
        }
        return sb;
    }

    private StringBuilder paintOTH(int startPerspectiveIndex, int startThemeIndex, int startObjetiveIndex,
            int finalPerspectiveIndex, int finalThemeIndex, int finalObjetiveIndex, JSONObject jsonArrows,
            int countLine, String classLine) {
        StringBuilder sb = new StringBuilder();
        int initPerspective = startPerspectiveIndex;
        int initTheme = startThemeIndex;
        int initObjective = startObjetiveIndex;
        int finalizePerspective = finalPerspectiveIndex;
        //int finalizeTheme = finalThemeIndex;
        int finalizeObjective = finalObjetiveIndex;
        if (finalPerspectiveIndex > startPerspectiveIndex) {
            initPerspective = finalPerspectiveIndex;
            initTheme = finalThemeIndex;
            initObjective = finalObjetiveIndex;
            finalizePerspective = startPerspectiveIndex;
            //     finalizeTheme = startThemeIndex;
            finalizeObjective = startObjetiveIndex;
        }
        try {
            JSONObject startPers = (JSONObject) jsonArrows.get(initPerspective + "");
            JSONObject arrayTheme = (JSONObject) startPers.get("arrayTheme");
            JSONObject themePer = (JSONObject) arrayTheme.get(initTheme + "");
            JSONObject arrayObjective = (JSONObject) themePer.get(initObjective + "");
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalizePerspective + "");
            JSONObject arrayThemeFinal = (JSONObject) finalPers.get("arrayTheme");
            //JSONObject themePerFinal = (JSONObject) arrayThemeFinal.get(finalizeTheme + "");
            //JSONObject arrayObjectiveFinal = (JSONObject) themePerFinal.get(finalizeObjective + "");

            int[] heightPer = getHeigthPerspectives(initPerspective - 1, jsonArrows);
            int noCols = arrayTheme.length();//total de columnas
            int restCols = (int) divContainerCols / noCols;//valor por columna
            int heightObjective = (startPers.getInt("height") - 20) / arrayObjective.length();
            //primer linea
            x1 = (divTitle) + (restCols * (initTheme - 1)) + (restCols * (divSizeColumn / 100));//((startThemeIndex * (restCols)) - (restCols / 2));
            x2 = x1 + (2 * countLine);
            y1 = heightPer[0] + heightPer[1] + 20 + (heightObjective * (initObjective - 1))
                    + (countLine * 2);
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex < startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }

            //segunda linea
            heightPer = getHeigthPerspectives(initPerspective, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + (countLine * 2);
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));

            //tercer linea
            x1 = x2;
            x2 = divTitle + divContainerCols + (countLine * 2);
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));

            //cuarta linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + (countLine * 2);
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));

            //quinta linea
            noCols = arrayThemeFinal.length();//total de columnas
            restCols = (int) divContainerCols / noCols;//valor por columna            
            x1 = x2;
            x2 = (restCols * (finalizeObjective - 1)) + (restCols / 2) + (countLine * 2);
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));

            //sexta linea
            heightPer = getHeigthPerspectives(finalizePerspective, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + 20;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex > startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }

        } catch (JSONException ex) {
            log.error("Exception in paintOTH:" + ex);
        }
        return sb;
    }

    private HashMap paintObjectiveObjective(JSONObject jsonArrows, int startPerspectiveIndex, int startThemeIndex,
            int startObjetiveIndex, Resource base, Period period, Iterator<Objective> itCausalobjective, Objective objStart,
            int countLine) {
        HashMap map = new HashMap();
        StringBuilder sb = new StringBuilder();
        int finalPerspectiveIndex = 0;
        int finalThemeIndex = 0;
        int finalObjetiveIndex = 0;
        Perspective persStart = objStart.getTheme().getPerspective();
        String classLine = base.getData("colorRelTO") == null ? "arrow" : base.getData("colorRelTO");
        while (itCausalobjective.hasNext()) {
            Objective objective = itCausalobjective.next();
            if (objective.hasPeriod(period)) {
                Perspective perspective = objective.getTheme().getPerspective();
                finalPerspectiveIndex = findIndexPerspective(perspective, jsonArrows);
                finalThemeIndex = findIndexTheme(finalPerspectiveIndex, jsonArrows, objective.getTheme());
                finalObjetiveIndex = findIndexObjective(finalPerspectiveIndex, jsonArrows, finalThemeIndex, objective);
                boolean isFinalTheme = getIndexTheme(finalThemeIndex, jsonArrows, finalPerspectiveIndex);
                boolean isStartTheme = getIndexTheme(startThemeIndex, jsonArrows, startPerspectiveIndex);

                if (base.getData("perspective" + base.getId() + perspective.getId()) == null
                        && base.getData("perspective" + base.getId() + persStart.getId()) == null) {
                    if (isFinalTheme || isStartTheme) {
                        sb.append(paintOOIndexFinalP(startPerspectiveIndex, startThemeIndex, startObjetiveIndex, finalPerspectiveIndex,
                                finalThemeIndex, finalObjetiveIndex, isFinalTheme, jsonArrows, countLine, classLine));
                    } else if ((startPerspectiveIndex - finalPerspectiveIndex == 1 && finalThemeIndex == startThemeIndex)
                            || (startPerspectiveIndex - finalPerspectiveIndex == -1 && finalThemeIndex == startThemeIndex)) {
                        sb.append(paintOOIndexContinuos(startPerspectiveIndex, startThemeIndex, startObjetiveIndex, finalPerspectiveIndex, finalThemeIndex,
                                finalObjetiveIndex, jsonArrows, countLine, classLine));
                    } else {
                        sb.append(paintOO(startPerspectiveIndex, startThemeIndex, startObjetiveIndex, finalPerspectiveIndex, finalThemeIndex, finalObjetiveIndex,
                                jsonArrows, countLine, classLine));
                    }
                } else if (base.getData("perspective" + base.getId() + perspective.getId()) != null
                        && base.getData("perspective" + base.getId() + persStart.getId()) != null) {
                    sb.append(paintOOHH(startPerspectiveIndex, startThemeIndex, startObjetiveIndex, finalPerspectiveIndex, finalThemeIndex, finalObjetiveIndex,
                            jsonArrows, countLine, classLine));
                } else /*if (base.getData("perspective" + base.getId() + perspective.getId()) == null
                 && base.getData("perspective" + base.getId() + persStart.getId()) != null) */ {
                    if (isFinalTheme || isStartTheme) {
                        paintOOH_VSame(startPerspectiveIndex, startThemeIndex, startObjetiveIndex, finalPerspectiveIndex, finalThemeIndex, finalObjetiveIndex,
                                jsonArrows, countLine, classLine);
                    } else {
                        sb.append(paintOOH_V(startPerspectiveIndex, startThemeIndex, startObjetiveIndex, finalPerspectiveIndex, finalThemeIndex, finalObjetiveIndex,
                                jsonArrows, countLine, classLine));
                    }

                } /*else if (base.getData("perspective" + base.getId() + perspective.getId()) != null
                 && base.getData("perspective" + base.getId() + persStart.getId()) == null) {
                 }*/
                countLine++;
            }
        }
        map.put(countLine, sb);
        return map;
    }

    private StringBuilder paintOOH_VSame(int startPerspectiveIndex, int startThemeIndex, int startObjetiveIndex,
            int finalPerspectiveIndex, int finalThemeIndex, int finalObjetiveIndex, JSONObject jsonArrows,
            int countLine, String classLine) {
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
            JSONObject arrayObjective = (JSONObject) themePer.get(initObjective + "");
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalizePerspective + "");
            JSONObject arrayThemeFinal = (JSONObject) finalPers.get("arrayTheme");
            JSONObject themePerFinal = (JSONObject) arrayThemeFinal.get(finalizeTheme + "");
            JSONObject arrayObjectiveFinal = (JSONObject) themePerFinal.get(finalizeObjective + "");

            int[] heightPer = getHeigthPerspectives(initPerspective, jsonArrows);
            int noCols = arrayTheme.length();//total de columnas
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

            //segunda linea
            x1 = x2;
            x2 = divTitle + divContainerCols + (countLine * 2);
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));

            //tercer linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + 20 + (((heigthPers - 20) / arrayObjectiveFinal.length()) * (finalizeObjective - 1))
                    + (((heigthPers - 20) / arrayObjectiveFinal.length()) / 2) + (countLine * 2);
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));

            //cuarta linea
            noCols = arrayThemeFinal.length();//total de columnas
            restCols = (int) divContainerCols / noCols;
            x1 = x2;
            x2 = (restCols * (finalizeTheme - 1)) + (restCols * (divSizeColumn / 100));
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex < startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }

        } catch (JSONException ex) {
            log.error("Exception in paintOOH_VSame: " + ex);
        }
        return sb;
    }

    private StringBuilder paintOOH_V(int startPerspectiveIndex, int startThemeIndex, int startObjetiveIndex,
            int finalPerspectiveIndex, int finalThemeIndex, int finalObjetiveIndex, JSONObject jsonArrows,
            int countLine, String classLine) {
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
            JSONObject arrayObjective = (JSONObject) themePer.get(initObjective + "");
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalizePerspective + "");
            JSONObject arrayThemeFinal = (JSONObject) finalPers.get("arrayTheme");
            JSONObject themePerFinal = (JSONObject) arrayThemeFinal.get(finalizeTheme + "");
            JSONObject arrayObjectiveFinal = (JSONObject) themePerFinal.get(finalizeObjective + "");
            int[] heightPer = getHeigthPerspectives(initPerspective - 1, jsonArrows);
            int noCols = arrayTheme.length();//total de columnas
            int restCols = (int) divContainerCols / noCols;//valor por columna
            int heigthPers = startPers.getInt("height");
            //primer linea
            x1 = (divTitle) + (restCols * (initTheme - 1)) + (restCols * divSizeColumn);
            x2 = x1 + (countLine * 2);
            y1 = heightPer[0] + heightPer[1] + 20 + ((heigthPers - 20) / arrayObjective.length()) * (initObjective - 1)
                    + ((heigthPers - 20) / arrayTheme.length()) / 2 + (countLine * 2);
            y2 = y1 + (2 * countLine);
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex > startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }

            //segunda linea
            heightPer = getHeigthPerspectives(initPerspective, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + (countLine * 2);
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));

            //tercer linea
            x1 = x2;
            x2 = divTitle + divContainerCols + (countLine * 2);
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));

            //cuarta linea
            heightPer = getHeigthPerspectives(finalizePerspective, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + (countLine * 2);
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));

            //quinta linea
            noCols = arrayThemeFinal.length();//total de columnas
            restCols = (int) divContainerCols / noCols;//valor por columna
            heigthPers = finalPers.getInt("height");
            x1 = x2;
            x2 = (restCols * (finalizeTheme - 1)) + ((divSizeColumn / arrayObjectiveFinal.length()) * (finalizeObjective - 1))
                    + (countLine * 2);
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
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

        } catch (JSONException ex) {
            log.error("Exception in paintOOH_V: " + ex);
        }
        return sb;
    }

    private StringBuilder paintOOHH(int startPerspectiveIndex, int startThemeIndex, int startObjetiveIndex,
            int finalPerspectiveIndex, int finalThemeIndex, int finalObjetiveIndex, JSONObject jsonArrows,
            int countLine, String classLine) {
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
            JSONObject arrayObjective = (JSONObject) themePer.get(initObjective + "");
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalizePerspective + "");
            JSONObject arrayThemeFinal = (JSONObject) finalPers.get("arrayTheme");
            JSONObject themePerFinal = (JSONObject) arrayThemeFinal.get(finalizeTheme + "");
            JSONObject arrayObjectiveFinal = (JSONObject) themePerFinal.get(finalizeObjective + "");
            int[] heightPer = getHeigthPerspectives(initPerspective, jsonArrows);
            int noCols = arrayTheme.length();//total de columnas
            int restCols = (int) divContainerCols / noCols;//valor por columna
            //primer linea
            x1 = (divTitle) + (restCols * (initTheme - 1)) + ((divSizeColumn / arrayObjective.length()) * (initObjective - 1))
                    + (countLine * 2);//(restCols * (divSizeColumn/100));//((startThemeIndex * (restCols)) - (restCols / 2));
            x2 = x1;
            y1 = heightPer[0] + heightPer[1];
            y2 = y1 + (2 * countLine);
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex > startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            //segunda linea
            x1 = x2;
            x2 = divTitle + divContainerCols + (countLine * 2);
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            //tercer linea
            heightPer = getHeigthPerspectives(finalizePerspective, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + (countLine * 2);
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            //cuarta linea
            noCols = arrayThemeFinal.length();//total de columnas
            restCols = (int) divContainerCols / noCols;
            x1 = x2;
            x2 = (divTitle) + (restCols * (initTheme - 1)) + ((divSizeColumn / arrayObjectiveFinal.length()) * (finalizeObjective - 1))
                    + (countLine * 2);
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + (countLine * 2);
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));

            heightPer = getHeigthPerspectives(finalizePerspective, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1];
            if (finalPerspectiveIndex < startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }


        } catch (JSONException ex) {
            log.error("Exception in paintOOHH: " + ex);
        }
        return sb;
    }

    private StringBuilder paintOOIndexContinuos(int startPerspectiveIndex, int startThemeIndex, int startObjetiveIndex,
            int finalPerspectiveIndex, int finalThemeIndex, int finalObjetiveIndex, JSONObject jsonArrows,
            int countLine, String classLine) {
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
            JSONObject arrayObjective = (JSONObject) themePer.get(initObjective + "");
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalizePerspective + "");
            JSONObject arrayThemeFinal = (JSONObject) finalPers.get("arrayTheme");
            JSONObject themePerFinal = (JSONObject) arrayThemeFinal.get(finalizeTheme + "");
            JSONObject arrayObjectiveFinal = (JSONObject) themePerFinal.get(finalizeObjective + "");
            int[] heightPer = getHeigthPerspectives(initPerspective - 1, jsonArrows);
            int noCols = arrayTheme.length();//total de columnas
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
            //Segunda linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            heightObjective = (finalPers.getInt("height") - 20) / arrayObjectiveFinal.length();
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + 20 + (heightObjective * finalizeObjective);

            //Tercer linea
            noCols = arrayThemeFinal.length();
            restCols = (int) divContainerCols / noCols;
            x1 = x2;
            x2 = (divTitle) + (restCols * (finalizeTheme - 1)) + (restCols * (divSizeColumn / 100));;
            y1 = y2;
            y2 = y1;
            if (finalPerspectiveIndex < startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
        } catch (JSONException ex) {
            log.error("Exception in paintOOIndexContinuos: " + ex);
        }
        return sb;
    }

    private StringBuilder paintOO(int startPerspectiveIndex, int startThemeIndex, int startObjetiveIndex,
            int finalPerspectiveIndex, int finalThemeIndex, int finalObjetiveIndex, JSONObject jsonArrows,
            int countLine, String classLine) {
        StringBuilder sb = new StringBuilder();
        int initPerspective = startPerspectiveIndex;
        int initTheme = startThemeIndex;
        int initObjective = startObjetiveIndex;
        int finalizePerspective = finalPerspectiveIndex;
        int finalizeTheme = finalThemeIndex;
        int finalizeObjective = finalObjetiveIndex;
        if (finalPerspectiveIndex > startPerspectiveIndex) {
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
            JSONObject arrayObjective = (JSONObject) themePer.get(initObjective + "");
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalizePerspective + "");
            JSONObject arrayThemeFinal = (JSONObject) finalPers.get("arrayTheme");
            JSONObject themePerFinal = (JSONObject) arrayThemeFinal.get(finalizeTheme + "");
            JSONObject arrayObjectiveFinal = (JSONObject) themePerFinal.get(finalizeObjective + "");

            int[] heightPer = getHeigthPerspectives(initPerspective - 1, jsonArrows);
            int noCols = arrayTheme.length();//total de columnas
            int restCols = (int) divContainerCols / noCols;//valor por columna
            int heightObjective = (startPers.getInt("height") - 20) / arrayObjective.length();
            //primer linea
            x1 = (divTitle) + (restCols * (initTheme - 1)) + (restCols * (divSizeColumn / 100));//((startThemeIndex * (restCols)) - (restCols / 2));
            x2 = x1 + (2 * countLine);
            y1 = heightPer[0] + heightPer[1] + (heightObjective * initObjective) + 20;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex < startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            //segunda linea
            heightPer = getHeigthPerspectives(initPerspective, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + (countLine * 2);
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            //tercera linea
            x1 = x2;
            x2 = divTitle + divContainerCols + (countLine * 2);//(divTitle) + (restCols * (finalizeTheme - 1)) + (restCols * 0.95) + (countLine * 2);
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            //cuarta linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + (countLine * 2);
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));

            //quinta linea
            noCols = arrayThemeFinal.length();//total de columnas
            restCols = (int) divContainerCols / noCols;//valor por columna
            x1 = x2;
            x2 = divTitle + (restCols * (finalizeTheme - 1)) + (restCols * (divSizeColumn / 100)) + (countLine * 2);
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));

            //sexta linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            heightObjective = (finalPers.getInt("height") - 20) / arrayObjectiveFinal.length();
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + (countLine * 2) + (heightObjective * initObjective);

            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));

            //septima linea
            noCols = arrayThemeFinal.length();//total de columnas
            restCols = (int) divContainerCols / noCols;
            x1 = x2;
            x2 = (divTitle) + (restCols * (finalizeTheme - 1)) + (restCols * (divSizeColumn / 100));;
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (finalPerspectiveIndex > startPerspectiveIndex) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
        } catch (JSONException ex) {
            log.error("Exception in paintOO: " + ex);
        }
        return sb;
    }

    private StringBuilder paintOOIndexFinalP(int startPerspectiveIndex, int startThemeIndex, int startObjetiveIndex,
            int finalPerspectiveIndex, int finalThemeIndex, int finalObjetiveIndex, boolean isFinalTheme,
            JSONObject jsonArrows, int countLine, String classLine) {
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
            JSONObject arrayObjective = (JSONObject) themePer.get(initObjective + "");
            JSONObject finalPers = (JSONObject) jsonArrows.get(finalizePerspective + "");
            JSONObject arrayThemeFinal = (JSONObject) finalPers.get("arrayTheme");
            JSONObject themePerFinal = (JSONObject) arrayThemeFinal.get(finalizeTheme + "");
            JSONObject arrayObjectiveFinal = (JSONObject) themePerFinal.get(finalizeObjective + "");
            int[] heightPer = getHeigthPerspectives(initPerspective - 1, jsonArrows);
            int noCols = arrayTheme.length();//total de columnas
            int restCols = (int) divContainerCols / noCols;//valor por columna
            int heightObjective = (startPers.getInt("height") - 20) / arrayObjective.length();
            //primer linea
            x1 = (divTitle) + (restCols * (initTheme - 1)) + (restCols * (divSizeColumn / 100));//((startThemeIndex * (restCols)) - (restCols / 2));
            x2 = x1 + (2 * countLine);
            y1 = heightPer[0] + heightPer[1] + (heightObjective * initObjective) + 20;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (isFinalTheme) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }
            //segunda linea
            heightPer = getHeigthPerspectives(finalizePerspective, jsonArrows);
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + (countLine * 2);
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));

            //tercer linea
            x1 = x2;
            x2 = (divTitle) + (restCols * (initTheme - 1)) + (restCols * (divSizeColumn / 100)) + (countLine * 2);
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));

            //cuarta linea
            heightPer = getHeigthPerspectives(finalizePerspective - 1, jsonArrows);
            heightObjective = (finalPers.getInt("height") - 20) / arrayObjectiveFinal.length();
            x1 = x2;
            x2 = x1;
            y1 = y2;
            y2 = heightPer[0] + heightPer[1] + 20 + (heightObjective * finalizeObjective);
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            //quinta linea
            noCols = arrayThemeFinal.length();//total de columnas
            restCols = (int) divContainerCols / noCols;
            x1 = x2;
            x2 = (divTitle) + (restCols * (finalizeTheme - 1)) + (restCols * (divSizeColumn / 100)) + +(countLine * 2);
            y1 = y2;
            y2 = y1;
            sb.append(paintLines(countLine, x1, x2, y1, y2, classLine));
            if (!isFinalTheme) {
                sb.append(paintLineTriangle(countLine, triangleEnd));
            }

        } catch (JSONException ex) {
            log.error("Exception en paintOOIndexSame: " + ex);
        }
        return sb;
    }

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

    private int getHeightPerspective(Perspective perspective, Resource base, int maxObject) {
        int height = 120;
        if (base.getData("perspective" + base.getId() + perspective.getId()) == null) {
            if (base.getData("widthVerticalObjective") != null) {
                height = Integer.parseInt(base.getData("widthVerticalObjective"));
                if (maxObject > 0) {
                    height = height * maxObject;
                }
            }
        } else if (base.getData("perspective" + base.getId() + perspective.getId()) != null && base.getData("widthHorizontalObjective") != null) {
            height = Integer.parseInt(base.getData("widthHorizontalObjective"));
        }
        height = height + 20;//Cabecera del tema
        return height;
    }

    private int getMaxObjectiveForTheme(Perspective perspective) {
        Iterator<Theme> it = perspective.listThemes();
        int maxObjecForTheme = 0;
        List list = new ArrayList();
        while (it.hasNext()) {
            Theme theme = it.next();
            Iterator<Objective> itObjective = theme.listObjectives();
            int countObjective = 0;
            while (itObjective.hasNext()) {
                Objective objective = itObjective.next();
                if (objective.isActive() && objective.isValid()) {
                    countObjective++;
                }
            }
            list.add(countObjective);
        }
        Collections.sort(list);
        if (!list.isEmpty()) {
            maxObjecForTheme = Integer.parseInt(list.get(0).toString());
        }
        return maxObjecForTheme;
    }
}
