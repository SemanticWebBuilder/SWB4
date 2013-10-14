/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.resources.strategicMap;

import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.model.Resource;

/**
 * Patrón de diseño: Decorador (Componente concreto). Define un objeto al cual
 * se le pueden agregar responsabilidades adicionales.
 *
 * @author Martha Elvia Jim&eacute;nez Salgado
 * @version %I%, %G%
 * @since 1.0
 */
public class CausalMap extends ComponentMap {

    private static Logger log = SWBUtils.getLogger(CausalMap.class);

    /**
     * Se encarga de pintar la base del mapa estrat&eacute;gico
     *
     * @param bsc BalanceScoreCard
     * @param period Periodo seleccionado
     * @param base Recurso de SWB utilizado para mostrarlo dentro de la
     * plataforma SWBPortal
     * @return
     */
    @Override
    public StringBuilder draw(BSC bsc, Period period, Resource base) {
        StringBuilder sb = new StringBuilder();
        DataBuilder data = new DataBuilder();
        sb.append(paintBaseMap(base, data.getData(base, period, bsc)));
        return sb;
    }

    /**
     * Pinta la estructura de datos de un BalanceScoreCard
     *
     * @param base Recurso de SWB utilizado para mostrarlo dentro de la
     * plataforma SWBPortal
     * @param data JSON que contiene los datos a pintar
     * @return un objeto {@code StringBuilder} que contiene el codigo HTML para
     * pinta un mapa
     */
    private StringBuilder paintBaseMap(Resource base, JSONArray data) {
        StringBuilder sb = new StringBuilder();
        try {
            JSONObject headers = (JSONObject) data.get(0);//visibility:visible
            sb.append("<div id=\"container\" name=\"container\" style=\" z-index:5; position:absolute;");
            sb.append(" width:100%;height:100%; float:left; visibility:visible \"\">");
            sb.append(paintDivHeader(headers).toString());
            JSONArray perspectives = (JSONArray) data.get(1);
            for (int i = 0; i < perspectives.length(); i++) {
                JSONObject perspective = (JSONObject) perspectives.get(i);
                sb.append(paintDivSeparation());
                sb.append(paintDivPerspective(base, perspective));
            }
            sb.append("</div>");
        } catch (JSONException ex) {
            log.error("Error read data in paintBaseMap " + ex);
        }
        return sb;
    }

    /**
     * Se encarga de armar el contenedor de la cabecera de un mapa
     *
     * @param header Objeto de tipo {@code JSONObject} que contiene los datos
     * para pintar la cabecera de un mapa
     * @return
     */
    private StringBuilder paintDivHeader(JSONObject header) {
        StringBuilder sb = new StringBuilder();
        try {
            JSONObject headerData = (JSONObject) header.get("headers");
            sb.append("<div style=\"clear:both; width:100%;float:left;height:120px\">");
            sb.append("     \n<div style=\"clear:both; width:33%;float:left;height:120px;margin-top:10px;\">");
            sb.append(headerData.get("mision"));
            sb.append("     \n</div>");
            sb.append("     \n<div style=\"clear:both; width:34%;float:left;height:\"120px\";margin-top:10px;\">");
            sb.append("      \n<img src=\"" + headerData.get("logo") + "\">");
            sb.append("     \n</div>");
            sb.append("     \n<div style=\"clear:both; width:33%;float:left;height:\"120px\";\">");
            sb.append(headerData.get("vision"));
            sb.append("     \n</div>");
            sb.append("\n</div>");
        } catch (JSONException ex) {
            log.error("Exception to paint headers: " + ex);
        }
        return sb;
    }

    /**
     * Pinta un div de separaci&oacute;n entre perspectivas.
     *
     * @return objeto tipo {@code StringBuilder} con un div de separaci&oacute;n
     */
    private StringBuilder paintDivSeparation() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n<div style=\"width:100%; float:left;height:20px;\">");//height:10%;
        sb.append("\n</div>");
        return sb;
    }

    /**
     * Obtiene el contenedor de una perpectiva
     *
     * @param base Recurso de SWB utilizado para mostrarlo dentro de la
     * plataforma SWBPortal
     * @param perspective objeto de tipo {@code Perspective}
     * @return objeto tipo {@code StringBuilder} con el contenedor de
     * perspectivas
     */
    private StringBuilder paintDivPerspective(Resource base, JSONObject perspective) {
        StringBuilder sb = new StringBuilder();
        try {
            int countTheme = Integer.parseInt(perspective.get("countTheme").toString());
            int countDifferentiator = Integer.parseInt(perspective.get("countDiffGroup").toString());
            if ((countTheme > 0) || (countDifferentiator > 0)) {
                String titlePers = perspective.get("title").toString();
                sb.append("\n<div style=\"clear:both; width:5%;float:left;\">");
                if (perspective.getBoolean("titleHorizontal")) {
                    sb.append("\n<div style=\"clear:both; width:100%;float:left;text-align:center;color:\"");
                    sb.append(perspective.get("colorText") + "\";\">");
                    sb.append(titlePers);
                    sb.append("\n</div>");
                } else {
                    for (int i = 0; i < titlePers.length(); i++) {
                        sb.append("         <div style=\"width:100%; text-align:center;color:\"");
                        sb.append(perspective.get("colorText") + "\";\">" + titlePers.charAt(i) + "</div>");
                    }
                }
                sb.append("\n</div>");
                sb.append("     \n<div style=\"width:92%;float:left;\">");
                sb.append("     \n<div style=\"width:100%;float:left;\">");
                JSONArray arrayThemes = (JSONArray) perspective.get("arrayThemes");
                if ((countTheme > 0) && (perspective.getBoolean("showHorizontal"))) {
                    sb.append(paintDivHorizontalView(base, arrayThemes, countTheme));
                } else if (countTheme > 0) {
                    sb.append(paintDivVerticalView(arrayThemes, countTheme, base));
                }
                sb.append("     \n</div>");
                if (countDifferentiator > 0) {
                    JSONArray arrayDifferentiatorGroup = (JSONArray) perspective.get("arrayDifferentiatorGroup");
                    sb.append(paintDivGroupDifferentiator(arrayDifferentiatorGroup, countDifferentiator, base));
                }
                sb.append("     \n</div>");
                sb.append("     <div style=\"clear:both; width:3%;float:left;\"></div>");
            }
        } catch (JSONException ex) {
            log.error("Error to paint perspective, paintDivPerspective: " + ex);
        }
        return sb;
    }

    /**
     * Encargado de pintar la vista de objetivos horizontal
     *
     * @param base Recurso de SWB utilizado para mostrarlo dentro de la
     * plataforma SWBPortal
     * @param arrayThemes Objeto de tipo {@code JSONArray}
     * @param countTheme n&uacute;mero de temas
     * @return Objeto de tipo {@code StringBuilder} con los datos HTML para
     * pintar la vista horizontal del mapa estrat&eacute;gico
     */
    private StringBuilder paintDivHorizontalView(Resource base, JSONArray arrayThemes, int countTheme) {
        StringBuilder sb = new StringBuilder();
        //Array que tendra la cantidad de objetivos por tema
        int[] objsForTheme = getObjectivesForTheme(arrayThemes, countTheme);//new int[countTheme]; 
        //Array con el tamanio de temas a lo ancho
        int[] arraySizeColThemes = getSizeColumnsTheme(countTheme);

        for (int i = 0; i < arraySizeColThemes.length; i++) {
            //Si el tema no es el final asigna 95% sino asigna 100%
            int widthColTheme = (arraySizeColThemes.length - 1) == i ? 100 : 95;
            int[] configuration = configurationMargin(objsForTheme[i]);
            try {
                JSONObject objObject = arrayThemes.getJSONObject(i);
                sb.append("         <div style=\"width:" + arraySizeColThemes[i] + "%;float:left;\">");
                sb.append("             <div style=\"float:left; width:" + widthColTheme + "%;\">");

                String backgroundColor = (String) objObject.get("bgcolor");
                backgroundColor = ((backgroundColor == null) || (backgroundColor.trim().length() < 1)
                        || (objObject.getBoolean("isHidden"))) ? "white" : backgroundColor;
                String fontColor = (String) objObject.get("colorText");
                fontColor = ((fontColor == null) || (fontColor.trim().length() < 1)
                        || (objObject.getBoolean("isHidden"))) ? "black" : fontColor;
                sb.append("                 ");
                sb.append(" <div style=\"background-color:" + backgroundColor + ";color:" + fontColor);
                sb.append(";clear:both;text-align:center;width:" + configuration[0] + "%;");
                sb.append("border:1px solid black;border-left:" + configuration[1] + "px solid");
                //if (!objObject.getBoolean("isHidden")) {
                    sb.append(" black;");
                //} else {
                //    sb.append(" white;");
                //}
                //sb.append(" border-right:" + configuration[2] + "px solid");
                //if (!objObject.getBoolean("isHidden")) {
                 //   sb.append(" black;");
                //} else {
                //    sb.append(" white;");
                //}
               // sb.append(" border-bottom-style:none\"");
                sb.append(">");
                //if (objObject.getBoolean("isHidden")) {
                    sb.append("                 " + objObject.get("title"));
                //}
                sb.append("                 </div>");
                JSONArray arrayObjecs = (JSONArray) objObject.get("arrayObjectives");
                //if (arraySizeColThemes[i] > 0) {
                sb.append(paintDivObjective(arrayObjecs, base, objsForTheme[i], configuration[0], true));
                //}
                //System.out.println("" + paintDivObjective(arrayObjecs, base, objsForTheme[i], configuration[0], true));
                sb.append("             </div>");
                sb.append("         </div>");
            } catch (JSONException ex) {
                log.error("Exception paint horizontal view " + ex);
            }
        }
        return sb;
    }

    /**
     * Encargado de pintar la vista vertical del balanscorecard
     *
     * @param arrayThemes Objeto de tipo {@code JSONArray}
     * @param countTheme n&uacute;mero de temas
     * @param base Recurso de SWB utilizado para mostrarlo dentro de la
     * plataforma SWBPortal
     * @return objeto tipo {@code StringBuilder} con con los datos HTML para
     * pintar la vista vertical del mapa estrat&eacute;gico
     */
    private StringBuilder paintDivVerticalView(JSONArray arrayThemes, int countTheme, Resource base) {
        StringBuilder sb = new StringBuilder();

        int[] objsForTheme = getObjectivesForTheme(arrayThemes, countTheme);
        //Tema con mayor objetivos
        int elemHigher = getHigherTheme(objsForTheme);
        int[] arraySizeColThemes = getSizeColumnsTheme(countTheme);

        int heigthConfig = 120;
        if ((base.getData("widthVerticalObjective") != null)
                && (base.getData("widthVerticalObjective").trim().length() != 0)) {
            heigthConfig = Integer.parseInt(base.getData("widthVerticalObjective"));
        }
        for (int i = 0; i < arraySizeColThemes.length; i++) {
            int widthColTheme = (arraySizeColThemes.length - 1) == i ? 100 : 95;
            try {
                JSONObject objObject = arrayThemes.getJSONObject(i);

                JSONArray arrayObjecs = (JSONArray) objObject.get("arrayObjectives");
                String backgroundColor = (String) objObject.get("bgcolor");
                backgroundColor = ((backgroundColor == null) || (backgroundColor.trim().length() < 1)
                        || (objObject.getBoolean("isHidden"))) ? "white" : backgroundColor;
                String fontColor = (String) objObject.get("colorText");
                fontColor = ((fontColor == null) || (fontColor.trim().length() < 1)
                        || (objObject.getBoolean("isHidden"))) ? "black" : fontColor;

                sb.append("         <div style=\"width:" + arraySizeColThemes[i] + "%;float:left;\">");

                sb.append("             <div style=\"background-color:" + backgroundColor + ";color:" + fontColor);
                sb.append("; clear:both;text-align:center;width:" + widthColTheme + "%;border:1px solid");
                if (!objObject.getBoolean("isHidden")) {
                    sb.append(" black;\">");
                } else {
                    sb.append(" white;\">");
                }
                if (!objObject.getBoolean("isHidden")) {
                    sb.append("                 " + objObject.get("title"));
                }
                sb.append("             </div>");

                sb.append(paintDivObjective(arrayObjecs, objsForTheme[i], elemHigher, widthColTheme, heigthConfig));
                sb.append("         </div>");
            } catch (JSONException ex) {
                log.error("Exception in paintDivVerticalView: " + ex);
            }
        }
        return sb;
    }

    /**
     * Pinta el contenedor de grupos de diferenciadores
     *
     * @param arrayDifferentiatorGroup objeto JSON que contiene la
     * informaci&oacute;n de cada grupo de diferenciadores
     * @param countDiffGroup Contador del n&uacute;mero de grupo de
     * diferenciadores
     * @param base Recurso de SWB utilizado para mostrarlo dentro de la
     * plataforma SWBPortal
     * @return un objeto {@code StringBuilder} con los contenedores de los
     * grupos de diferenciadores
     */
    private StringBuilder paintDivGroupDifferentiator(JSONArray arrayDifferentiatorGroup,
            int countDiffGroup, Resource base) {
        StringBuilder sb = new StringBuilder();
        int[] differenForGroupDifferentiator = getDifferentiatorForDifferentiatorGroup(arrayDifferentiatorGroup, countDiffGroup);

        for (int i = 0; i < differenForGroupDifferentiator.length; i++) {
            sb.append("     <div style=\"width:100%;float:left; margin-top:10px;\">");

            int[] arraySizeColDifferentiator = getSizeColumnsTheme(differenForGroupDifferentiator[i]);
            try {
                JSONObject objObjectDistinctive = arrayDifferentiatorGroup.getJSONObject(i);
                JSONArray arrayObjecs = (JSONArray) objObjectDistinctive.get("arrayDifferentiator");
                String backgroundColor = (String) objObjectDistinctive.get("bgcolor");
                backgroundColor = ((backgroundColor == null) || (backgroundColor.trim().length() < 1)) ? "white"
                        : backgroundColor;
                String fontColor = (String) objObjectDistinctive.get("colorText");
                fontColor = ((fontColor == null) || (fontColor.trim().length() < 1)) ? "black" : fontColor;
                sb.append("     <div style=\"background-color:" + backgroundColor + ";color:" + fontColor);
                sb.append(";clear:both;text-align:center;");
                sb.append("width:100%;border:1px solid black;border-bottom-style:none\">");
                sb.append("     " + objObjectDistinctive.get("title") + "");
                sb.append("     </div>");
                sb.append(paintDivDifferentiator(arraySizeColDifferentiator, arrayObjecs, base));

            } catch (JSONException ex) {
                log.error("Exception in paintDivGroupDifferentiator: " + ex);
            }
            sb.append("     </div>");
        }
        return sb;
    }

    /**
     * Se encarga de obtener el tema con el alto &aacute;ms grande
     *
     * @param objsForTheme Arreglo con lo altos de los temas
     * @return el tema con el alto mayor
     */
    private int getHigherTheme(int[] objsForTheme) {
        int elemHigher = 0;
        int[] elemThemesCopy = Arrays.copyOfRange(objsForTheme, 0, objsForTheme.length);
        Arrays.sort(elemThemesCopy);
        try {
            elemHigher = elemThemesCopy[objsForTheme.length - 1];
        } catch (ArrayIndexOutOfBoundsException ex) {
            log.error("Error try get data getHigherTheme: " + ex);
        }
        return elemHigher;
    }

    /**
     * Pinta con el contenedor de un diferenciador.
     *
     * @param arraySizeColDifferentiator array que contiene el ancho de los
     * diferenciadores
     * @param arrayDifferentiator objeto JSON que contiene la informaci&oacute;n
     * de cada diferenciador
     * @param base Recurso de SWB utilizado para mostrarlo dentro de la
     * plataforma SWBPortal
     * @return un objeto {@code StringBuilder} con los contenedores de los
     * diferenciadores
     */
    private StringBuilder paintDivDifferentiator(int[] arraySizeColDifferentiator,
            JSONArray arrayDifferentiator, Resource base) {
        StringBuilder sb = new StringBuilder();
        String height = "120";
        if (base.getData("widthDifferentiator") != null) {
            height = base.getData("widthDifferentiator");
        }
        for (int j = 0; j < arraySizeColDifferentiator.length; j++) {
            int widthColTheme = 100;//(arraySizeColDistinctive.length - 1) == j ? 100 : 95;
            try {
                JSONObject distObj = (JSONObject) arrayDifferentiator.get(j);
                String title = (String) distObj.get("title");
                sb.append("         <div style=\"width:" + arraySizeColDifferentiator[j] + "%;float:left;\">");
                sb.append("             <div style=\"background-color:white;clear:both;text-align:center;height:\"" + height + "\";width:"
                        + widthColTheme + "%;border:1px solid black;\">");
                sb.append("                 " + title);
                sb.append("             </div>");
                sb.append("         </div>");
            } catch (JSONException ex) {
                log.error("Exception get differentiator: " + ex);
            }
        }
        return sb;
    }

    /**
     * Pinta un objetivo en forma horizontal
     *
     * @param objectives Objeto tipo {@code JSONArray} el cual contiene los
     * objetivos de un tema
     * @param base Recurso de SWB utilizado para mostrarlo dentro de la
     * plataforma SWBPortal
     * @param amountObjective cantidad de objetivos del tema
     * @param sizeColumn tama&ntilde;o de la columna
     * @param isHorizontal propiedad que indica si la perspectiva se visualiza
     * en forma horizontal
     * @return objeto tipo {@code StringBuilder} con el contenedor de objetivos
     */
    private StringBuilder paintDivObjective(JSONArray objectives, Resource base, int amountObjective,
            int sizeColumn, boolean isHorizontal) {
        StringBuilder sb = new StringBuilder();
        int[] arraySizeColObjectives = getSizeColumnsObjective(amountObjective, sizeColumn);
        //System.out.println("---------------------");
        String height = "120";
        if ((isHorizontal) && (base.getData("widthHorizontalObjective") != null)
                && (base.getData("widthHorizontalObjective").trim().length() != 0)) {
            height = base.getData("widthHorizontalObjective");
        }
        int heigthColDefault = Integer.parseInt(height);
        for (int j = 0; j < arraySizeColObjectives.length; j++) {
           // System.out.println("arraySi: " + arraySizeColObjectives[j]);
            try {
                JSONObject objetive = (JSONObject) objectives.get(j);
                String[] dataObjective = new String[6];
                dataObjective[0] = (String) objetive.get("title");
                dataObjective[1] = (String) objetive.get("icon");
                dataObjective[2] = (String) objetive.get("prefix");
                dataObjective[3] = (String) objetive.get("periodicity");
                dataObjective[4] = (String) objetive.get("sponsor");
                dataObjective[5] = (String) objetive.get("url");

                String styleColObjetive = (amountObjective - 1) == j ? "" : "border-right-style:none";
                String borderPaint = "border:1px solid black; " + styleColObjetive;

                sb.append(paintDataObjective(dataObjective, arraySizeColObjectives[j], heigthColDefault,
                        borderPaint));
                //sb.append("             </div>");
            } catch (JSONException ex) {
                log.error("Exception try get Objective, paintDivObjective: " + ex);
            }
        }
        return sb;
    }

    /**
     * Pinta un objetivo en forma vertical
     *
     * @param arrayObjecs Objeto tipo {@code JSONArray} el cual contiene los
     * objetivos de un tema
     * @param countObjtheme cantidad de objetivos por tema
     * @param elemHigher numero mayor de objetivos para la perspectiva
     * @param widthColTheme ancho de la columna del tema
     * @param heigthConfig alto del objetivo, configurado en el
     * administraci&oacute;n del recurso
     * @return objeto tipo {@code StringBuilder} con el contenedor de objetivos
     */
    private StringBuilder paintDivObjective(JSONArray arrayObjecs, int countObjtheme, int elemHigher,
            int widthColTheme, int heigthConfig) {
        StringBuilder sb = new StringBuilder();
        if (countObjtheme == 0) {
            throw new java.lang.ArithmeticException("/ by zero");
        }
        int heigthCol = (heigthConfig * elemHigher) / countObjtheme;

        //Cálculo de los bordes
        int border = 1;
        int borderLastIni = 0;
        int borderLastFin = 0;
        int borderChange = 0;
        if (elemHigher > countObjtheme) {
            borderChange = elemHigher - countObjtheme;
            borderLastIni = 1 + (int) borderChange / 2;
            borderLastFin = borderLastIni;
            if ((borderChange % 2) != 0) {
                borderLastFin = borderLastFin + 1;
            }
        }

        try {
            for (int j = 0; j < countObjtheme; j++) {
                JSONObject objetive = (JSONObject) arrayObjecs.get(j);
                String[] dataObje = new String[6];
                dataObje[0] = (String) objetive.get("title");
                dataObje[1] = (String) objetive.get("icon");
                dataObje[2] = (String) objetive.get("prefix");
                dataObje[3] = (String) objetive.get("periodicity");
                dataObje[4] = (String) objetive.get("sponsor");
                dataObje[5] = (String) objetive.get("url");

                String styleNone = (j < (countObjtheme - 1)) ? " border-bottom-style:none;" : "";
                String styleNone2 = "";
                if ((j == 0) && (borderLastIni > 1)) {
                    styleNone2 = " border-bottom-width:" + borderLastIni + "px; ";
                }
                if ((j == (countObjtheme - 1)) && (borderLastFin > 1)) {
                    styleNone2 = " border-bottom-width:" + borderLastFin + "px; ";
                }
                String borderPaint = " border:" + border + "px solid black;" + styleNone + styleNone2;
                sb.append(paintDataObjective(dataObje, widthColTheme, heigthCol, borderPaint));
            }
        } catch (JSONException ex) {
            log.error("Exception get data Objective, paintDivObjective: " + ex);
        }
        return sb;
    }

    /**
     * Pinta el contenido de un objetivo en el mapa
     *
     * @param dataObjective Datos del objetivo a pintar
     * @param widthCol ancho de la columna del objetivo
     * @param heigthCol alto de la columna del objetivo
     * @param borderPaint borde de las columnas
     * @return un contenedor con los datos del objetivo a pintar
     */
    private StringBuilder paintDataObjective(String[] dataObjective, int widthCol, int heigthCol,
            String borderPaint) {
        StringBuilder sb = new StringBuilder();
        sb.append("             <div style=\"background-color:white;text-align:center;width:" + widthCol + "%;height:");
        sb.append(heigthCol + "px;float:left; " + borderPaint + "\">");
        try {
            sb.append("             <a href=\"" + dataObjective[5] + "\">" + dataObjective[0] + "</a>");
            sb.append("                 <p>");
            sb.append("                     <span style=\"padding:10px;\">");
            if (dataObjective[1].length() > 1) {
                sb.append("<img src=\"" + dataObjective[1] + "\"/>");
            }
            sb.append("</span>");
            sb.append("                     <span style=\"padding:10px;\">" + dataObjective[2] + "</span>");
            sb.append("                     <span style=\"padding:10px;\">" + dataObjective[3] + "</span>");
            sb.append("                     <span style=\"padding:10px;\">" + dataObjective[4] + "</span>");
            sb.append("                 </p>");
        } catch (ArrayIndexOutOfBoundsException ex) {
            log.error("Exception try get ArrayIndex,  paintDataObjective: " + ex);
        }
        sb.append("             </div>");
        return sb;

    }

    /**
     * Obtiene el tama&ntilde;o de las columnas por tema
     *
     * @param amountObjective Cantidad de objetivos por tema
     * @param sizeColumn tama&ntilde;o de la columna
     * @return arreglo con el tama&ntilde;o de las columnas inicial, final y las
     * interiores
     */
    private int[] getSizeColumnsObjective(int amountObjective, int sizeColumn) {
        int[] arraySizeColObjectives = new int[amountObjective];

        if (amountObjective == 0) {
            throw new java.lang.ArithmeticException("/ by zero");
        }
        int sizeColsObjectives = (sizeColumn / amountObjective);
        int increColsIniObject = 0;
        int increColsFinObject = 0;
        if ((sizeColumn % amountObjective) != 0) {
            increColsIniObject = increColsFinObject = (sizeColumn % amountObjective) / 2;
            if (((sizeColumn % amountObjective) % 2) != 0) {
                increColsFinObject = increColsFinObject + 1;
            }
        }

        for (int k = 0; k < amountObjective; k++) {
            if (k == 0) {
                arraySizeColObjectives[k] = sizeColsObjectives + increColsIniObject;
            } else if (k == (amountObjective - 1)) {
                arraySizeColObjectives[k] = sizeColsObjectives + increColsFinObject;
            } else {
                arraySizeColObjectives[k] = sizeColsObjectives;
            }
        }

        return arraySizeColObjectives;
    }

    /**
     * Obtiene un arreglo con el n&uacute;mero de objetivos existentes por
     * perspectiva.
     *
     * @param countTheme Cantidad de temas por perspectiva
     * @return arreglo con el tama&ntilde;o de las columnas inicial, final y las
     * interiores
     */
    private int[] getSizeColumnsTheme(int countTheme) {
        int[] arraySizeColThemes = new int[countTheme];
        if (countTheme == 0) {
            throw new java.lang.ArithmeticException("/ by zero");
        }
        int sizeCols = (int) (100 / countTheme); //Divide el espacio entre el número de temas existentes
        int divSizeCols = 100 % countTheme; //Obtiene el residuo de la división del número de temas existentes
        int increColsIni = 0;
        int increColsFin = 0;

        increColsFin = (int) divSizeCols / 2; //Si tiene datos el residuo de la división lo divide entre 2 para
        //repartirlo entre las columnas de los temas finales e iniciales
        if ((divSizeCols % 2) != 0) {
            increColsIni = increColsFin + 1;
        }
        //Asigna los tamanios para cada columna inicial, final y las de enmedio
        for (int i = 0; i < countTheme; i++) {
            if (i == 0) {
                arraySizeColThemes[i] = sizeCols + increColsIni;
            } else if (i == (countTheme - 1)) {
                arraySizeColThemes[i] = sizeCols + increColsFin;
            } else {
                arraySizeColThemes[i] = sizeCols;
            }
        }
        return arraySizeColThemes;
    }

    /**
     * Obtiene un arreglo con el n&uacute;mero de objetivos existentes por tema.
     *
     * @param arrayThemes Objeto json con objetos "Temas"
     * @param countTheme número de temas
     * @return un arreglo de tama&ntilde;o del numero de tema existentes y cada
     * instancia del arreglo contiene el n&uacute;mero de onjetivos existentes
     * por tema
     */
    private int[] getObjectivesForTheme(JSONArray arrayThemes, int countTheme) {
        int[] objsForTheme = new int[countTheme]; //Array que tendra los objetivos por tema
        for (int theme = 0; theme < arrayThemes.length(); theme++) {
            try {
                JSONObject arrayObjectives = (JSONObject) arrayThemes.get(theme);
                int idObj = Integer.parseInt(arrayObjectives.get("countObjectives").toString());
                objsForTheme[theme] = idObj;
            } catch (JSONException ex) {
                log.error("Exception try get countObjectives: " + ex);
            } catch (NumberFormatException ex) {
                log.error("Exception try convert number getObjectivesForTheme: " + ex);
            }
        }
        return objsForTheme;
    }

    /**
     * Obtiene un arreglo con el n&uacute;mero de diferenciadores existentes por
     * grupo de diferenciadores.
     *
     * @param arrayDifferentiator Objeto json con objetos "Grupos de
     * diferenciadores"
     * @param countDifferentiator N&uacute;mero de grupo de diferenciadores
     * @return un arreglo de tama&ntilde;o del numero de grupos de
     * diferenciadores existentes y cada instancia del arreglo contiene el
     * n&uacute;mero de diferenciadores existentes por grupo
     */
    private int[] getDifferentiatorForDifferentiatorGroup(JSONArray arrayDifferentiator,
            int countDifferentiator) {
        int[] diffForGroupDifferentiator = new int[countDifferentiator];
        for (int differentiator = 0; differentiator < arrayDifferentiator.length(); differentiator++) {
            try {
                JSONObject arrayDiff = (JSONObject) arrayDifferentiator.get(differentiator);
                int idDistint = Integer.parseInt(arrayDiff.get("countDifferentiator").toString());
                diffForGroupDifferentiator[differentiator] = idDistint;
            } catch (JSONException ex) {
                log.error("Exception try get countObjectives: " + ex);
            } catch (NumberFormatException ex1) {
                log.error("Exception try convert number: " + ex1);
            }
        }
        return diffForGroupDifferentiator;
    }

    /**
     * Permite calcular los margenes que deben tener los objetivos de un tema
     * horizontal.
     *
     * @param objForTheme N&uacute;mero de objetivos por tema
     * @return Arreglo con datos del margen inicial, final y por default de los
     * objetivos dentro de un tema
     */
    private int[] configurationMargin(int objForTheme) {
        int[] configuration = new int[3];
        //Determina el margen que deberá ser restado dependiendo del numero de objetivos por tema
        int lowerMargin = (int) (objForTheme * 0.5);
        if (((objForTheme * 0.5) % 2) != 0) {
            lowerMargin = lowerMargin + 1;
        }
        int sizedColumn = objForTheme > 1 ? 100 - lowerMargin : 100;
        configuration[0] = sizedColumn;
        int divSizeMarginObjective = (objForTheme + 1);
        //Determina los bordes izquierdo, derecho del tema
        int increMarginObjIni = 0;
        int increMarginObjFin = 0;
        if ((divSizeMarginObjective % 2) != 0) {
            increMarginObjIni = ((int) divSizeMarginObjective / 2) + 1;
            increMarginObjFin = (int) divSizeMarginObjective / 2;
        } else {
            increMarginObjIni = increMarginObjFin = divSizeMarginObjective / 2;
        }
        configuration[1] = increMarginObjIni;
        configuration[2] = increMarginObjFin;

        return configuration;
    }
}
