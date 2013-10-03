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
     *
     * @param bsc
     * @param period
     * @param base
     * @return
     */
    @Override
    public StringBuilder draw(BSC bsc, Period period, Resource base) {
        StringBuilder sb = new StringBuilder();
        DataBuilder data = new DataBuilder();
        sb.append(paintBaseMap(base, data.getData(base, period, bsc)));
        return sb;
    }

    private StringBuilder paintBaseMap(Resource base, JSONArray data) {
        StringBuilder sb = new StringBuilder();
        try {
            JSONObject headers = (JSONObject) data.get(0);//visibility:visible
            sb.append("<div id=\"container\" name=\"container\" style=\" z-index:2; position:absolute; width:100%;height:100%; float:left; visibility:visible \"\">");
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

    private StringBuilder paintDivHeader(JSONObject header) {
        StringBuilder sb = new StringBuilder();
        try {
            JSONObject headerData = (JSONObject) header.get("headers");
            sb.append("<div style=\"clear:both; width:100%;float:left;\">");
            sb.append("     \n<div style=\"clear:both; width:33%;float:left;height:\"120px\";margin-top:10px;\">");
            sb.append(headerData.get("mision"));
            sb.append("     \n</div>");
            sb.append("     \n<div style=\"clear:both; width:34%;float:left;height:\"120px\";margin-top:10px;\">");
            sb.append(headerData.get("logo"));
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

    private StringBuilder paintDivSeparation() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n<div style=\"width:100%;height:10%; float:left;height:20px;\">");
        sb.append("\n</div>");
        return sb;
    }

    private StringBuilder paintDivPerspective(Resource base, JSONObject perspective) {
        StringBuilder sb = new StringBuilder();
        try {
            int countTheme = Integer.parseInt(perspective.get("countTheme").toString());
            int countDifferentiator = Integer.parseInt(perspective.get("countDiffGroup").toString());
            if (countTheme > 0 || countDifferentiator > 0) {
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
                if (countTheme > 0 && perspective.getBoolean("showHorizontal")) {
                    JSONArray arrayThemes = (JSONArray) perspective.get("arrayThemes");
                    sb.append(paintDivHorizontalView(base, arrayThemes, countTheme));
                } else if (countTheme > 0) {
                    JSONArray arrayThemes = (JSONArray) perspective.get("arrayThemes");
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
            log.error("Error to paint perspective: " + ex);
        }
        return sb;
    }

    private StringBuilder paintDivHorizontalView(Resource base, JSONArray arrayThemes, int countTheme) {
        StringBuilder sb = new StringBuilder();
        //Array que tendra la cantidad de objetivos por tema
        int[] objsForTheme = getObjectivesForTheme(arrayThemes, countTheme);//new int[countTheme]; 
        //Array con el tamanio de temas a lo ancho
        int[] arraySizeColThemes = getSizeColumnsTheme(countTheme);
        //verificaar si puede cambiarse a getSizeColumnsObjective

        for (int i = 0; i < arraySizeColThemes.length; i++) {
            //Si el tema no es el final asigna 95% sino asigna 100%
            int widthColTheme = (arraySizeColThemes.length - 1) == i ? 100 : 95;
            int[] configuration = configurationMargin(objsForTheme[i]);
            try {
                JSONObject objObject = arrayThemes.getJSONObject(i);
                sb.append("         <div style=\"width:" + arraySizeColThemes[i] + "%;float:left;\">");
                sb.append("             <div style=\"float:left; width:" + widthColTheme + "%;\">");
                if (!objObject.getBoolean("isHidden")) {
                    String backgroundColor = (String) objObject.get("bgcolor");
                    backgroundColor = (backgroundColor == null || backgroundColor.trim().length() < 1) ? "white" : backgroundColor;
                    String fontColor = (String) objObject.get("colorText");
                    fontColor = (fontColor == null || fontColor.trim().length() < 1) ? "black" : fontColor;
                    sb.append("                 <div style=\"background-color:" + backgroundColor + ";color:" + fontColor);
                    sb.append(";clear:both;text-align:center;width:" + configuration[0] + "%;border:1px solid black;border-left:");
                    sb.append(configuration[1] + "px solid black;border-right:" + configuration[2] + "px solid black;border-bottom-style:none\">");
                    sb.append("                 " + objObject.get("title"));
                    sb.append("                 </div>");
                }
                JSONArray arrayObjecs = (JSONArray) objObject.get("arrayObjectives");
                if (arraySizeColThemes[i] > 0) {
                    sb.append(paintDivObjective(arrayObjecs, base, objsForTheme[i], configuration[0], true));
                }
                sb.append("             </div>");
                sb.append("         </div>");
            } catch (JSONException ex) {
                log.error("Exception paint horizontal view " + ex);
            }
        }

        return sb;
    }

    private StringBuilder paintDivVerticalView(JSONArray arrayThemes, int countTheme, Resource base) {
        StringBuilder sb = new StringBuilder();

        int[] objsForTheme = getObjectivesForTheme(arrayThemes, countTheme);
        //Tema con mayor objetivos
        int elemHigher = getHigherTheme(objsForTheme);
        int[] arraySizeColThemes = getSizeColumnsTheme(countTheme);

        int heigthConfig = 120;
        if (base.getData("widthVerticalObjective") != null) {
            heigthConfig = Integer.parseInt(base.getData("widthVerticalObjective"));
        }
        for (int i = 0; i < arraySizeColThemes.length; i++) {
            int widthColTheme = (arraySizeColThemes.length - 1) == i ? 100 : 95;
            try {
                JSONObject objObject = arrayThemes.getJSONObject(i);

                JSONArray arrayObjecs = (JSONArray) objObject.get("arrayObjectives");
                String backgroundColor = (String) objObject.get("bgcolor");
                backgroundColor = (backgroundColor == null || backgroundColor.trim().length() < 1) ? "white" : backgroundColor;
                String fontColor = (String) objObject.get("colorText");
                fontColor = (fontColor == null || fontColor.trim().length() < 1) ? "black" : fontColor;
                sb.append("         <div style=\"width:" + arraySizeColThemes[i] + "%;float:left;\">");
                if (!objObject.getBoolean("isHidden")) {
                    sb.append("             <div style=\"background-color:" + backgroundColor + ";color:" + fontColor);
                    sb.append("; clear:both;text-align:center;width:" + widthColTheme + "%;border:1px solid black;\">");
                    sb.append("                 " + objObject.get("title"));
                    sb.append("             </div>");
                }

                //Objectives
                //System.out.println("objsForTheme[i]: " + objsForTheme[i]);
                sb.append(paintDivObjective(arrayObjecs, objsForTheme[i], elemHigher, widthColTheme, heigthConfig));
                //Fin de Objectives

                sb.append("         </div>");
            } catch (JSONException ex) {
                log.error("Exception in: " + ex);
            }
        }

        return sb;
    }

    private StringBuilder paintDivGroupDifferentiator(JSONArray arrayDifferentiatorGroup, int countDiffGroup, Resource base) {
        StringBuilder sb = new StringBuilder();
        //JSONArray objArrayThemesDistinctive = null;

        int[] differenForGroupDifferentiator = getDifferentiatorForDifferentiatorGroup(arrayDifferentiatorGroup, countDiffGroup);

        //objArrayThemesDistinctive = (JSONArray) theme.get("arrayThemesDistinctive");

        for (int i = 0; i < differenForGroupDifferentiator.length; i++) {
            sb.append("     <div style=\"width:100%;float:left; margin-top:10px;\">");

            //int numDistinctive = differenForGroupDifferentiator[i];


            int[] arraySizeColDifferentiator = getSizeColumnsTheme(differenForGroupDifferentiator[i]);//new int[numDistinctive];countDiffGroup
            /*int sizeColsDistinctive = (int) (100 / numDistinctive);
             int divSizeDistinctive = 100 % numDistinctive;
             int increDistinctiveIni = 0;
             int increDistinctiveFin = 0;
             increDistinctiveFin = (int) divSizeDistinctive / 2;
             if ((divSizeDistinctive % 2) != 0) {
             increDistinctiveIni = increDistinctiveFin + 1;
             }
             for (int j = 0; j < numDistinctive; j++) {
             if (j == 0) {
             arraySizeColDistinctive[j] = sizeColsDistinctive + increDistinctiveIni;
             } else if (j == (numDistinctive - 1)) {
             arraySizeColDistinctive[j] = sizeColsDistinctive + increDistinctiveFin;
             } else {
             arraySizeColDistinctive[j] = sizeColsDistinctive;
             }
             }*/


            try {
                JSONObject objObjectDistinctive = arrayDifferentiatorGroup.getJSONObject(i);
                JSONArray arrayObjecs = (JSONArray) objObjectDistinctive.get("arrayDifferentiatorGroup");
                String backgroundColor = (String) objObjectDistinctive.get("bgcolor");
                backgroundColor = (backgroundColor == null || backgroundColor.trim().length() < 1) ? "white" : backgroundColor;
                String fontColor = (String) objObjectDistinctive.get("colorText");
                fontColor = (fontColor == null || fontColor.trim().length() < 1) ? "black" : fontColor;
                sb.append("     <div style=\"background-color:" + backgroundColor + ";color:" + fontColor + ";clear:both;text-align:center;width:100%;border:1px solid black;border-bottom-style:none\">");
                sb.append("     " + objObjectDistinctive.get("title") + "");
                sb.append("     </div>");
                sb.append(paintDivDifferentiator(arraySizeColDifferentiator, arrayObjecs, base));

            } catch (JSONException ex) {
                log.error("Exception in: " + ex);
            }
            sb.append("     </div>");
        }
        return sb;
    }

    private int getHigherTheme(int[] objsForTheme) {
        int elemHigher = 0;
        int[] elemThemesCopy = Arrays.copyOfRange(objsForTheme, 0, objsForTheme.length);
        Arrays.sort(elemThemesCopy);
        elemHigher = elemThemesCopy[objsForTheme.length - 1];
        return elemHigher;
    }

    private StringBuilder paintDivDifferentiator(int[] arraySizeColDifferentiator, JSONArray arrayDifferentiator, Resource base) {
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

    private StringBuilder paintDivObjective(JSONArray objectives, Resource base, int amountObjective, int sizeColumn, boolean isHorizontal) {
        StringBuilder sb = new StringBuilder();

        int[] arraySizeColObjectives = getSizeColumnsObjective(amountObjective, sizeColumn);

        String height = "120";
        if (isHorizontal && base.getData("widthHorizontalObjective") != null) {
            height = base.getData("widthHorizontalObjective");
        }
        int heigthColDefault = Integer.parseInt(height);
        for (int j = 0; j < amountObjective; j++) {
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

                sb.append(paintDataObjective(dataObjective, arraySizeColObjectives[j], heigthColDefault, borderPaint));

                /*sb.append("             <div style=\"background-color:white;text-align:center;width:"
                 + arraySizeColObjectives[j] + "%;height:" + heigthColDefault
                 + "px;float:left; " + borderPaint + "\">");
                 sb.append("             <a href=\"" + url + "\">" + title + "</a>");
                 sb.append("                 <p>");
                 sb.append("                     <span style=\"padding:10px;\">" + icon + "</span>");
                 sb.append("                     <span style=\"padding:10px;\">" + prefix + "</span>");
                 sb.append("                     <span style=\"padding:10px;\">" + periodicity + "</span>");
                 sb.append("                     <span style=\"padding:10px;\">" + initials + "</span>");
                 sb.append("                 </p>");*/
                sb.append("             </div>");
            } catch (JSONException ex) {
                log.error("Exception try get Objective: " + ex);
            }
        }
        return sb;
    }

    private StringBuilder paintDivObjective(JSONArray arrayObjecs, int theme, int elemHigher, int widthColTheme, int heigthConfig) {
        StringBuilder sb = new StringBuilder();
        int heigthCol = (heigthConfig * elemHigher) / theme;
        int border = 1;
        int borderLastIni = 0;
        int borderLastFin = 0;
        int borderChange = 0;
        if (elemHigher > theme) {
            borderChange = elemHigher - theme;
            borderLastIni = 1 + (int) borderChange / 2;
            borderLastFin = borderLastIni;
            if ((borderChange % 2) != 0) {
                borderLastFin = borderLastFin + 1;
            }
        }

        try {
            for (int j = 0; j < theme; j++) {
                JSONObject objetive = (JSONObject) arrayObjecs.get(j);
                String[] dataObje = new String[6];
                dataObje[0] = (String) objetive.get("title");
                dataObje[1] = (String) objetive.get("icon");
                dataObje[2] = (String) objetive.get("prefix");
                dataObje[3] = (String) objetive.get("periodicity");
                dataObje[4] = (String) objetive.get("sponsor");
                dataObje[5] = (String) objetive.get("url");

                String styleNone = (j < (theme - 1)) ? " border-bottom-style:none;" : "";
                String styleNone2 = "";
                if (j == 0 && borderLastIni > 1) {
                    styleNone2 = "border-bottom-width:" + borderLastIni + "px; ";
                }
                if (j == (theme - 1) && borderLastFin > 1) {
                    styleNone2 = "border-bottom-width:" + borderLastFin + "px; ";
                }
                String borderPaint = "border:" + border + "px solid black;" + styleNone + styleNone2;
                sb.append(paintDataObjective(dataObje, widthColTheme, heigthCol, borderPaint));
            }
        } catch (JSONException ex) {
            log.error("Exception get data Objective: " + ex);
        }
        return sb;
    }

    private StringBuilder paintDataObjective(String[] dataObjective, int widthColTheme, int heigthCol, String borderPaint) {
        StringBuilder sb = new StringBuilder();
        sb.append("             <div style=\"background-color:white;text-align:center;width:" + widthColTheme + "%;height:");
        sb.append(heigthCol + "px;float:left; " + borderPaint + "\">");
        sb.append("             <a href=\"" + dataObjective[5] + "\">" + dataObjective[0] + "</a>");
        sb.append("                 <p>");
        sb.append("                     <span style=\"padding:10px;\">" + dataObjective[1] + "</span>");
        sb.append("                     <span style=\"padding:10px;\">" + dataObjective[2] + "</span>");
        sb.append("                     <span style=\"padding:10px;\">" + dataObjective[3] + "</span>");
        sb.append("                     <span style=\"padding:10px;\">" + dataObjective[4] + "</span>");
        sb.append("                 </p>");
        sb.append("             </div>");
        return sb;

    }

    private int[] getSizeColumnsObjective(int amountObjective, int sizeColumn) {
        int sizeColsObjectives = (sizeColumn / amountObjective);
        int increColsIniObject = 0;
        int increColsFinObject = 0;
        if ((sizeColumn % amountObjective) != 0) {
            increColsIniObject = increColsFinObject = (sizeColumn % amountObjective) / 2;
            if (((sizeColumn % amountObjective) % 2) != 0) {
                increColsFinObject = increColsFinObject + 1;
            }
        }

        int[] arraySizeColObjectives = new int[amountObjective];
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

    private int[] getSizeColumnsTheme(int countTheme) {
        int[] arraySizeColThemes = new int[countTheme];
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

    private int[] getObjectivesForTheme(JSONArray arrayThemes, int countTheme) {
        int[] objsForTheme = new int[countTheme]; //Array que tendra los objetivos por tema
        for (int theme = 0; theme < arrayThemes.length(); theme++) {
            try {
                JSONObject arrayObjectives = (JSONObject) arrayThemes.get(theme);
                int idObj = Integer.parseInt(arrayObjectives.get("countObjectives").toString());
                System.out.println("idObj: " + idObj);
                objsForTheme[theme] = idObj;
            } catch (JSONException ex) {
                log.error("Exception try get countObjectives: " + ex);
            }
        }
        return objsForTheme;
    }

    private int[] getDifferentiatorForDifferentiatorGroup(JSONArray arrayDifferentiator, int countDifferentiator) {
        int[] objsForGroupDifferentiator = new int[countDifferentiator];
        for (int differentiator = 0; differentiator < arrayDifferentiator.length(); differentiator++) {
            try {
                JSONObject arrayDistintives = (JSONObject) arrayDifferentiator.get(differentiator);
                int idDistint = Integer.parseInt(arrayDistintives.get("noDistinctives").toString());
                objsForGroupDifferentiator[differentiator] = idDistint;
            } catch (JSONException ex) {
                log.error("Exception try get countObjectives: " + ex);
            }
        }
        return objsForGroupDifferentiator;
    }

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
