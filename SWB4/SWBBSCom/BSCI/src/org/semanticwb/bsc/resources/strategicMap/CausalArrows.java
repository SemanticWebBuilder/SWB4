/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.resources.strategicMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
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
        sb.append(datPaint());
        sb.append(super.draw(bsc, period, base));
        sb.append(datPaint2());
        //sb.append(paintArrows(getStructureDataArrows(base, bsc), base, bsc));
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
                    //perspectiveObj.put("id", count);
                    perspectiveObj.put("title", perspective.getTitle());
                    perspectiveObj.put("height", getHeightPerspective(perspective, base, maxObjectForPerspective));
                    perspectiveObj.put("isHorizontal", config);
                    perspectiveObj.put("maxObjectives", maxObjectForPerspective);
                    perspectiveObj.put("arrayTheme", getArrayTheme(perspective));
                    dataArrows.put(count + "", perspectiveObj);
                    count++;
                } catch (JSONException ex) {
                    log.error("Exception getStructureDataArrows: " + ex);
                }
            }
        }
        return dataArrows;
    }
/*
    private StringBuilder paintArrows(JSONObject dataStructure, Resource base, BSC bsc) {
        StringBuilder sb = new StringBuilder();
        Iterator<Perspective> itPers = bsc.listPerspectives();
        while (itPers.hasNext()) {
            Perspective perspective = itPers.next();
            Iterator<Theme> listThemes = perspective.listThemes();
            while (listThemes.hasNext()) {
                Theme theme = listThemes.next();
                Iterator<Theme> itCausalTheme = theme.listCausalThemes();
                sb.append(paintThemeTheme());
                Iterator<Objective> itCausalobjective = theme.listCausalObjectives();
                sb.append(paintThemeObjective());
                Iterator<Objective> itObjectives = theme.listObjectives();
                while (itObjectives.hasNext()) {
                    Objective objective = itObjectives.next();
                    itCausalTheme = objective.listCausalThemes();
                    sb.append(paintObjectiveTheme());
                    itCausalobjective = objective.listCausalObjectives();
                    sb.append(paintObjectiveObjective());
                }
            }
        }
        return sb;
    }

    private StringBuilder paintThemeTheme(JSONObject jsonArrows, int startPerspectiveIndex, int startThemeIndex,
            Resource base, Iterator<Theme> itCausalTheme) {
        StringBuilder sb = new StringBuilder();
        int countLine = 1;
        int finalPerspectiveIndex = 0;
        int finalThemeIndex = 0;
        while (itCausalTheme.hasNext()) {
            Theme theme = itCausalTheme.next();
            Perspective perspective = theme.getPerspective();
            finalPerspectiveIndex = findIndexPerspective(perspective, jsonArrows);
            finalThemeIndex = findIndexTheme(finalPerspectiveIndex, jsonArrows, theme);
            sb.append(getLinesThemeTheme(startPerspectiveIndex, startThemeIndex, finalPerspectiveIndex, finalThemeIndex, countLine));
            countLine++;
        }


        /*1. Sale a la mitad del tema desde arriba hasta la mitad de la división de perspectivas
         2. Dobla a la derecha hasta el área de flechas
         3. Sube / Baja hasta donde se encuentra la perspectiva que contiene el tema asociado (depende del índice actual - índice destino  mayor/menor para mostrar perspectivas), exactamente a la mitad de la división de perspectivas
         4. Dobla a la izquierda hasta la mitad del número de tema que ocupa el tema asociado
         5. Dobla hacia abajo hasta encontrar el tema
         6. Crea la flecha hacia abajo
         Pinta el color de flecha según la configuración, sino tiene configurado utilizar la configuración default
         */
    /*    return sb;
    }*/
    
    private StringBuilder getLinesThemeTheme(int startPerspectiveIndex, int startThemeIndex,int finalPerspectiveIndex, int finalThemeIndex, int countLine, String classLine) {
        StringBuilder sb = new StringBuilder();
        double x1 = 0;
        double x2 = 0;
        double y1 = 0;
        double y2 = 0;
        x1 = 0; 
        x2 = x1;
        y1 = 0;
        y2 = y1-10;
        sb.append(paintLines(countLine,x1, x2,y1, y2, classLine));
        x1 = x2;
        x2 = 0;
        y1 = y2;
        y2 = 0;
        sb.append(paintLines(countLine,x1, x2,y1, y2, classLine));
        x1 = x2;
        x2 = 0;
        y1 = y2;
        y2 = 0;
        sb.append(paintLines(countLine,x1, x2,y1, y2, classLine));
        x1 = x2;
        x2 = 0;
        y1 = y2;
        y2 = 0;
        sb.append(paintLines(countLine,x1, x2,y1, y2, classLine));
        x1 = x2;
        x2 = 0;
        y1 = y2;
        y2 = 0;
        sb.append(paintLines(countLine,x1, x2,y1, y2, classLine));
        return sb;
    }

    private int findIndexTheme(int indexPerspective, JSONObject jsonArrows, Theme theme) {
        int indexTheme = 0;
        try {
            JSONObject perspective = (JSONObject) jsonArrows.get("" + indexPerspective);
            JSONObject arrayTheme = (JSONObject) perspective.get("arrayTheme");
            
            boolean find = false;
            int i = 1;
            while (i <= arrayTheme.length() && !find) {
                try {
                    JSONObject obj = (JSONObject) arrayTheme.get(i + "");
                    int index = obj.getInt("index");
                    if (theme.getIndex() == index) {
                        indexTheme = i;
                        find = true;
                    }
                } catch (JSONException ex) {
                    log.error("Error get indexPerspective: " + ex);
                }
                i++;
            }

        } catch (JSONException ex) {
            log.error("Exception find indez theme: " + ex);
        }
        return indexTheme;
    }

    private int findIndexPerspective(Perspective perspective, JSONObject jsonArrows) {
        int indexPerspective = 0;
        boolean find = false;
        int i = 1;
        //for (int i = 1; i <= jsonArrows.length(); i++) {
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
    
    private StringBuilder datPaint2() {
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
        return  sb;
    }
    
    private StringBuilder datPaint() {
        //<LINE x1="10" y1="10" x2="100" y2="100"/> 9.45 - 10.02
        StringBuilder sb = new StringBuilder();
        sb.append("\n <link href=\"/swbadmin/css/mapaEstrategico.css\" rel=\"stylesheet\" type=\"text/css\" />");
        sb.append("\n<script type=\"text/javascript\">");
        sb.append("\n<!--");
        sb.append("\n	function calculateDivs() {");
        sb.append("\n         var div1 = document.getElementById(\"arrowLayer\"),");
        sb.append("\n         svg = div1.appendChild(create(\"svg\")),"); 
        
        sb.append("\n         defs1 = svg.appendChild(create(\"defs\"));");
        sb.append("\n         line2 = svg.appendChild(create(\"line\"));"); //
        //llamado a todas las lineas creadas
        
        sb.append("\n         svg.setAttribute(\"width\", \"100%\");");
        sb.append("\n         svg.setAttribute(\"height\", \"100%\");");
        

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
        

        sb.append("\n         line2.setAttribute(\"x1\",  \"1%\");");
        sb.append("\n         line2.setAttribute(\"x2\",  \"1%\");");
        sb.append("\n         line2.setAttribute(\"y1\",  \"10%\");");
        sb.append("\n         line2.setAttribute(\"y2\",  \"20%\");");
        sb.append("\n         line2.setAttribute(\"class\",\"arrow\");");

        sb.append("\n         line2.setAttribute(\"marker-end\", \"url(#triangle-end)\");");
        
        sb.append("\n     }");
        sb.append("\n     function create(type) {");
	sb.append("\n		return document.createElementNS(\"http://www.w3.org/2000/svg\", type);");
	sb.append("\n	 }");

        //sb.append("     <line x1=\"10\" y1=\"10\" x2=\"100\" y2=\"200\"/>");
        sb.append("     \n</script>");
        return sb;
    }

    private StringBuilder paintLines(double countLine, double x1, double x2, double y1, double y2, String classLine) {
        StringBuilder sb = new StringBuilder();
        sb.append("line" + countLine + ".setAttribute(\"x1\", \"" + x1 + "\"%\");");
        sb.append("line" + countLine + ".setAttribute(\"x2\", \"" + x2 + "\"%\");");
        sb.append("line" + countLine + ".setAttribute(\"y1\", \"" + y1 + "\"%\");");
        sb.append("line" + countLine + ".setAttribute(\"y2\", \"" + y2 + "\"%\");");
        sb.append("line" + countLine + ".setAttribute(\"class\",\"" + classLine + "\"%\");");
        return sb;
    }

    private StringBuilder paintThemeObjective(JSONObject jsonArrows, int startPerspectiveIndex, int startThemeIndex,
            int finalPerspectiveIndex, int finalThemeIndex, int finalObjetiveIndex, Resource base) {
        StringBuilder sb = new StringBuilder();
        /**
         * * Si es vertical: * Si está el objetivo en el área de flechas, serán
         * 4 líneas 1. Sale de la mitad del tema por arriba hasta la división de
         * perspectivas 2. Dobla a la derecha hasta el área de flechas 3. Sube o
         * baja hasta la mitad donde se encuentran el objetivo asociado (depende
         * del índice actual - índice destino mayor/menor para mostrar
         * perspectivas) 4. Dobla a la izquierda hasta encontrar el objetivo
         * asociado 5. Crea la flecha hacia la izquierda * Si la perspectiva del
         * objetivo está arriba de la perspectiva del tema, serán 5 1. Sale de
         * la mitad del tema por arriba hasta la división de perspectivas 2.
         * Dobla izquierda/derecha hasta la mitad de la división del tema
         * contenedor del objetivo asociado (depende del índice actual - índice
         * destino mayor/menor para mostrar tema) 3. Dobla hacia arriba hasta la
         * mitad del objetivo asociado 4. Dobla a la izquierda hasta encontrar
         * el objetivo asociado 5. Crea la flecha hacia la izquierda * Sino está
         * el objetivo en área de flechas, serán 6 líneas 1. Sale de la mitad
         * del tema por arriba hasta la división de perspectivas 2. Dobla a la
         * derecha hasta el área de flechas 3. Sube o baja hasta el final de la
         * perspectiva con el objetivo asociado (depende del índice actual -
         * índice destino mayor/menor para mostrar perspectivas) 4. Dobla a la
         * izquierda hasta la división de temas antes del tema con el objetivo a
         * utilizar (hasta la mitad) 5. Dobla hacia arriba hasta la mitad del
         * objetivo asociado 6. Dobla a la izquierda hasta encontrarse con la
         * mitad del objetivo asociado 7. Crea la flecha hacia la izquierda * Si
         * es horizontal: * Si la perspectiva del objetivo no está arriba de la
         * perspectiva del tema, serán 5 1. Sale a la mitad del tema desde
         * arriba 2. Dobla a la derecha hasta el área de flechas 3. Sube o baja
         * hasta el final de la perspectiva con el objetivo asociado (depende
         * del índice actual - índice destino mayor/menor para mostrar
         * perspectivas) 4. Dobla a la izquierda hasta la mitad del objetivo
         * asociado 5. Dobla hacia arriba hasta encontrar el objetivo asociado
         * 6. Crea la flecha hacia arriba * Si la perspectiva del objetivo está
         * arriba de la perspectiva del tema, serán 3 1. Sale a la mitad del
         * tema desde arriba 2. Dobla a la derecha/izquierda hasta la mitad del
         * objetivo asociado ((depende del índice actual - índice destino
         * mayor/menor para mostrar tema) 3. Dobla hacia arriba hasta encontrar
         * el objetivo asociado 4. Crea la flecha hacia arriba Pinta el color de
         * flecha según la configuración, ** Si es vertical: * Si está el
         * objetivo en el área de flechas, serán 4 líneas 1. Sale de la mitad
         * del tema por arriba hasta la división de perspectivas 2. Dobla a la
         * derecha hasta el área de flechas 3. Sube o baja hasta la mitad donde
         * se encuentran el objetivo asociado (depende del índice actual -
         * índice destino mayor/menor para mostrar perspectivas) 4. Dobla a la
         * izquierda hasta encontrar el objetivo asociado 5. Crea la flecha
         * hacia la izquierda * Si la perspectiva del objetivo está arriba de la
         * perspectiva del tema, serán 5 1. Sale de la mitad del tema por arriba
         * hasta la división de perspectivas 2. Dobla izquierda/derecha hasta la
         * mitad de la división del tema contenedor del objetivo asociado
         * (depende del índice actual - índice destino mayor/menor para mostrar
         * tema) 3. Dobla hacia arriba hasta la mitad del objetivo asociado 4.
         * Dobla a la izquierda hasta encontrar el objetivo asociado 5. Crea la
         * flecha hacia la izquierda * Sino está el objetivo en área de flechas,
         * serán 6 líneas 1. Sale de la mitad del tema por arriba hasta la
         * división de perspectivas 2. Dobla a la derecha hasta el área de
         * flechas 3. Sube o baja hasta el final de la perspectiva con el
         * objetivo asociado (depende del índice actual - índice destino
         * mayor/menor para mostrar perspectivas) 4. Dobla a la izquierda hasta
         * la división de temas antes del tema con el objetivo a utilizar (hasta
         * la mitad) 5. Dobla hacia arriba hasta la mitad del objetivo asociado
         * 6. Dobla a la izquierda hasta encontrarse con la mitad del objetivo
         * asociado 7. Crea la flecha hacia la izquierda * Si es horizontal: *
         * Si la perspectiva del objetivo no está arriba de la perspectiva del
         * tema, serán 5 1. Sale a la mitad del tema desde arriba 2. Dobla a la
         * derecha hasta el área de flechas 3. Sube o baja hasta el final de la
         * perspectiva con el objetivo asociado (depende del índice actual -
         * índice destino mayor/menor para mostrar perspectivas) 4. Dobla a la
         * izquierda hasta la mitad del objetivo asociado 5. Dobla hacia arriba
         * hasta encontrar el objetivo asociado 6. Crea la flecha hacia arriba *
         * Si la perspectiva del objetivo está arriba de la perspectiva del
         * tema, serán 3 1. Sale a la mitad del tema desde arriba 2. Dobla a la
         * derecha/izquierda hasta la mitad del objetivo asociado (depende del
         * índice actual - índice destino mayor/menor para mostrar tema) 3.
         * Dobla hacia arriba hasta encontrar el objetivo asociado 4. Crea la
         * flecha hacia arriba Pinta el color de flecha según la configuración,
         * sino tiene configurado utilizar la configuración default
         */
        return sb;
    }

    private StringBuilder paintObjectiveTheme(JSONObject jsonArrows, int startPerspectiveIndex, int startThemeIndex,
            int startObjetiveIndex, int finalPerspectiveIndex, int finalThemeIndex, Resource base) {
        StringBuilder sb = new StringBuilder();
        /**
         * * Si es vertical: * Si está el objetivo en el área de flechas, serán
         * 4 líneas 1. Sale a la mitad del objetivo del lado derecho hasta el
         * área de flechas 2. Baja o sube al inicio de la perspectiva con el
         * tema asociado (depende del índice actual - índice destino mayor/menor
         * para mostrar perspectivas) 3. Dobla a la izquierda hasta la mitad del
         * tema asociado 4. Dobla hacia abajo hasta encontrar el tema asociado
         * 5. Crea la flecha hacia abajo * Si la perspectiva del objetivo está
         * arriba de la perspectiva del tema, serán 5 1. Sale de la mitad del
         * objetivo del lado derecho hasta la mitad de división de temas, 2.
         * Dobla hacia abajo hasta la división de perspectiva 3. Dobla
         * derecha/izquierda hasta la mitad del tema asociado (depende del
         * índice actual - índice destino mayor/menor para mostrar tema) 4.
         * Dobla hacia abajo hasta encontrar el tema asociado 5. Crea la flecha
         * hacia abajo * Sino esta al final el objetivo (área de flechas) serán
         * 6 líneas 1. Sale de la mitad del objetivo del lado derecho hasta la
         * mitad de división de temas, 2. Dobla hacia abajo hasta la división de
         * perspectiva 3. Dobla a la derecha hasta el área de flechas 4. Baja o
         * sube al inicio de la perspectiva con el tema asociado (depende del
         * índice actual - índice destino mayor/menor para mostrar perspectivas)
         * 5. Dobla a la izquierda hasta la mitad del tema asociado 6. Dobla
         * hacia abajo hasta encontrar el tema asociado 7. Crea la flecha hacia
         * abajo * Si es horizontal: * Si la perspectiva del objetivo no está
         * arriba de la perspectiva del tema, serán 5 1. Sale a la mitad del
         * objetivo desde bajo hasta la mitad de la división de perspectivas 2.
         * Dobla a la derecha hasta el área de flechas 3. Sube o baja a la
         * perspectiva con el tema asociado, (depende del índice actual - índice
         * destino mayor/menor para mostrar perspectivas) 4. Dobla a la
         * izquierda hasta la mitad del tema asociado 5. Dobla hacia abajo hasta
         * encontrar el tema asociado 6. Crea la flecha hacia abajo * Si la
         * perspectiva del objetivo está arriba de la perspectiva del tema,
         * serán 3 1. Sale a la mitad del objetivo desde bajo 2. Dobla a la
         * derecha/izquierda hasta la mitad del tema asociado (depende del
         * índice actual - índice destino mayor/menor para mostrar tema) 3.
         * Dobla hacia abajo hasta encontrar el tema asociado 4. Crea la flecha
         * hacia abajo Pinta el color de flecha según la configuración, sino
         * tiene configurado utilizar la configuración default
         */
        return sb;
    }

    private StringBuilder paintObjectiveObjective(JSONObject jsonArrows, int startPerspectiveIndex, int startThemeIndex,
            int startObjetiveIndex, int finalPerspectiveIndex, int finalThemeIndex, int finalObjetiveIndex, Resource base) {
        StringBuilder sb = new StringBuilder();
        /**
         * * Si ambos son verticales: * Sino esta al final el objetivo (área de
         * flechas), serán 7 líneas 1. Sale de la mitad del objetivo por la
         * derecha hasta la división de temas (mitad) 2. Dobla hacia abajo hasta
         * la división de la perspectiva (mitad) 3. Dobla a la derecha hasta el
         * final de las perspectivas, área de flechas 4. Sube o baja al final de
         * la perspectiva con el objetivo asociado (depende del índice actual -
         * índice destino mayor/menor para mostrar perspectivas) 5. Dobla a la
         * izquierda hasta la división de temas antes del tema contenedor del
         * objetivo asociado 5. Dobla hacia arriba hasta la mitad del objetivo
         * asociado 6. Dobla a la izquierda hasta encontrarse con el objetivo
         * asociado, exactamente a la mitad 7. Crea la flecha Izquierda * Sino
         * esta al final el objetivo (área de flechas), serán 5 líneas 1. Sale
         * de la mitad del objetivo hasta la división de temas (mitad) 2. Dobla
         * hacia abajo hasta la división de la perspectiva (mitad) 3. Dobla a la
         * derecha hasta el final de las perspectivas, área de flechas 4. Sube o
         * baja a la perspectiva con el objetivo asociado (depende del índice
         * actual - índice destino mayor/menor para mostrar perspectivas),
         * exactamente hasta donde se encuentra la mitad del objetivo asociado
         * 5. Dobla a la izquierda hasta encontrarse con el objetivo asociado 6.
         * Crea la flecha izquierda * Si ambos son horizontales: 1. Sale a la
         * mitad del objetivo desde abajo hasta la división de perspectivas 2.
         * Dobla a la derecha hasta el área de flechas 3. Sube o baja hasta
         * final de la perspectiva con el objetivo asociado (depende del índice
         * actual - índice destino mayor/menor para mostrar perspectivas) 4.
         * Dobla a la izquierda hasta encontrarse a la mitad del objetivo
         * asociado (dentro del tema contenedor de objetivo) 5. Dobla hacia
         * arriba hasta encontrarse con el objetivo asociado 6. Crea la flecha
         * hacia arriba * Si va del horizontal al vertical, serán 6 líneas: 1.
         * Sale a la mitad del objetivo desde abajo hasta la división de
         * perspectivas 2. Dobla a la derecha hasta el área de flechas 3. Sube o
         * baja hasta el final de la perspectiva con el objetivo asociado
         * (depende del índice actual - índice destino mayor/menor para mostrar
         * perspectivas) 4. Dobla a la izquierda hasta división de temas, del
         * tema que contiene el objetivo asociado (por abajo) 5. Sube hasta la
         * mitad del objetivo asociado 6. Dobla a la izquierda hasta encontrarse
         * con el objetivo asociado 7. Crea la flecha derecha * Si va del
         * vertical al horizontal, serán 6 líneas: 1. Sale a la mitad del
         * objetivo por la derecha, hasta la mitad de la división del tema 2.
         * Dobla hacia abajo hasta la división de perspectiva 3. Dobla hacia la
         * derecha hasta el área de flechas 4. Sube o baja hasta el final de la
         * perspectiva con el objetivo asociado (depende del índice actual -
         * índice destino mayor/menor para mostrar perspectivas) 5. Dobla a la
         * izquierda hasta la mitad del objetivo asociado 6. Dobla hacia arriba
         * hasta encontrar el objetivo asociado 7. Crea la flecha hacia arriba
         * Pinta el color de flecha según la configuración, sino tiene
         * configurado utilizar la configuración default
         */
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
