/*
 * Esta clase contiene utilerias para el tratamiento de cadenas:
 * métricas de similitud, normalización de entidades, etc.
 */
package org.semanticwb.nlp.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author vieyra samuel.vieyra@infotec.com.mx
 */
public class string {

    //**** Metrics for distance of strings ****//
    public static double JaroWinklerDistance(String string1, String string2) {
        return JaroWinklerDistance(string1, string2, 0.1);
    }

    public static double JaroWinklerDistance(String string1, String string2, int ElementsC) {
        double jwd = JaroWinklerDistance(string1, string2);
        double lengthElement = (double) ElementsC;
        return jwd * (lengthElement / (lengthElement + 1.0));
    }

    public static double JaroWinklerDistance(String string1, String string2, double p) {
        double JD = JaroDistance(string1, string2);
        return JD + (getIndexofCommonPrefix(string1, string2) * p * (1 - JD));
    }

    public static double JaroDistance(String string1, String string2) {
        int len1 = string1.length();
        int len2 = string2.length();
        if (len1 == 0) {
            return len2 == 0 ? 1.0 : 0.0;
        }

        int searchRange = Math.max(0, Math.max(len1, len2) / 2 - 1);

        boolean[] matched1 = new boolean[len1];
        Arrays.fill(matched1, false);
        boolean[] matched2 = new boolean[len2];
        Arrays.fill(matched2, false);

        int numCommon = 0;
        for (int i = 0; i < len1; ++i) {
            int start = Math.max(0, i - searchRange);
            int end = Math.min(i + searchRange + 1, len2);
            for (int j = start; j < end; ++j) {
                if (matched2[j]) {
                    continue;
                }
                if (string1.charAt(i) != string2.charAt(j)) {
                    continue;
                }
                matched1[i] = true;
                matched2[j] = true;
                ++numCommon;
                break;
            }
        }
        if (numCommon == 0) {
            return 0.0;
        }

        int numHalfTransposed = 0;
        int j = 0;
        for (int i = 0; i < len1; ++i) {
            if (!matched1[i]) {
                continue;
            }
            while (!matched2[j]) {
                ++j;
            }
            if (string1.charAt(i) != string2.charAt(j)) {
                ++numHalfTransposed;
            }
            ++j;
        }
        int numTransposed = numHalfTransposed / 2;

        double numCommonD = numCommon;
        return (numCommonD / len1
                + numCommonD / len2
                + (numCommon - numTransposed) / numCommonD) / 3.0;
    }

    public static float LevenshteinDistance(String string1, String string2) {
        int[][] matriz = new int[string1.length() + 1][string2.length() + 1];
        for (int i = 0; i < string1.length() + 1; i++) {
            matriz[i][0] = i;
        }
        for (int j = 0; j < string1.length() + 1; j++) {
            matriz[0][j] = j;
        }
        for (int i = 1; i < string1.length() + 1; i++) {
            for (int j = 1; j < string1.length() + 1; j++) {
                if (string1.charAt(i - 1) == string2.charAt(i - 1)) {
                    matriz[i][j] = matriz[i - 1][j - 1];
                } else {
                    matriz[i][j] = Math.min(matriz[i][j - 1] + 1, matriz[i - 1][j] + 1);
                    matriz[i][j] = Math.min(matriz[i][j], matriz[i - 1][j - 1] + 1);
                }
            }
        }
        return matriz[string1.length()][string2.length()];
    }

    private static int getIndexofCommonPrefix(String string1, String string2) {
        int n = ((string1.length() > string2.length()) ? (string2.length()) : string1.length()) > 4 ? 4
                : (string1.length() > string2.length()) ? (string2.length()) : string1.length();
        for (int i = 0; i < n; i++) {
            if (string1.charAt(i) != string2.charAt(i)) {
                return i;
            }
        }
        return n;
    }

    public static String printDate(String date) {
        String[] parsedDate = parseDate(date);
        return "[" + parsedDate[0] + ":" + parsedDate[1] + "/" + parsedDate[2] + "/" + parsedDate[3] + ":" + parsedDate[4] + ":" + parsedDate[5] + "]";
    }
    
   
    public static int getIntensity(String Intensity){
        return getIntensity(Intensity, 16);
    }
    
    public static int getIntensity(String Intensity, int MaxRange){
        if (Intensity != null) {
            Pattern regex = Pattern.compile("([0-9]+)/([0-9]+)$");
            Matcher regexMatcher = regex.matcher(Intensity);
            while (regexMatcher.find()) {
                return (int) (Double.parseDouble(regexMatcher.group(1)) * MaxRange / Double.parseDouble(regexMatcher.group(2)));
            }
        }
       return -1;
    }
    
    //Recibe una fecha y hora en formato [??:??/??/??:9.00:pm] y devuelve un arraglo {dia_semana,dia,mes,año,hora<0-12:0:59>,{am-pm}}
    public static Calendar getCalendar(String date) {
        if(date==null)return null;
//        System.out.println(date);
        Pattern regex = Pattern.compile("\\[([^:]+):([^/]+)/([^/]+)/([^:]+):([^:]+):([^\\]]+)\\]");
        Matcher regexMatcher = regex.matcher(date);
        Calendar cal = Calendar.getInstance();

        while (regexMatcher.find()) {
            if (!regexMatcher.group(1).equals("??") && !regexMatcher.group(1).equals("null")) {
                cal.set(Calendar.DAY_OF_WEEK, getDayOfTheWeek(regexMatcher.group(1)));
            }
            if (!regexMatcher.group(2).equals("??") && !regexMatcher.group(2).equals("null")) {
                cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(regexMatcher.group(2)));
            }
            if (!regexMatcher.group(3).equals("??") && !regexMatcher.group(3).equals("null")) {
                int Month = getMonth(Integer.parseInt(regexMatcher.group(3)));
                cal.set(Calendar.MONTH, Month);
            }
            if (!regexMatcher.group(4).equals("??") && !regexMatcher.group(4).equals("null") ) {
                int YEAR = Integer.parseInt(regexMatcher.group(4));
                cal.set(Calendar.YEAR, YEAR);
            } 
            if (!regexMatcher.group(5).equals("??.??") && !regexMatcher.group(5).equals("null")) {
                
                if(regexMatcher.group(5).split("\\.").length == 2){
                    int Hour = Integer.parseInt(regexMatcher.group(5).split("\\.")[0]);
                    int Minutes = Integer.parseInt(regexMatcher.group(5).split("\\.")[1]);
                    if (regexMatcher.group(6).equals("pm")) {
                        Hour += 12;
                    }
                    cal.set(Calendar.HOUR_OF_DAY, Hour);
                    cal.set(Calendar.MINUTE, Minutes);
                }
            }
        }
//        
//        System.out.println("Day of the week: " + cal.get(Calendar.DAY_OF_WEEK));
//        System.out.println("Day: " + cal.get(Calendar.DAY_OF_MONTH));
//        System.out.println("Month: " + cal.get(Calendar.MONTH));
//        System.out.println("Year: " + cal.get(Calendar.YEAR));
//        System.out.println("Hours: " + cal.get(Calendar.HOUR_OF_DAY));
//        System.out.println("Minutes: " + cal.get(Calendar.MINUTE));
//        
        return cal;
    }

    private static int getMonth(int Month) {
        switch(Month){
            case  1: return Calendar.JANUARY;
            case  2: return Calendar.FEBRUARY;
            case  3: return Calendar.MARCH;
            case  4: return Calendar.APRIL;
            case  5: return Calendar.MAY;
            case  6: return Calendar.JUNE;
            case  7: return Calendar.JULY;
            case  8: return Calendar.AUGUST;
            case  9: return Calendar.SEPTEMBER;
            case  10: return Calendar.OCTOBER;
            case  11: return Calendar.NOVEMBER;
            case  12: return Calendar.DECEMBER;
            default : return -1;
        }
    }
    
    private static int getDayOfTheWeek(String day) {
        if (day.equals("G")) {
            return Calendar.SUNDAY;
        }
        if (day.equals("L")) {
            return Calendar.MONDAY;
        }
        if (day.equals("M")) {
            return Calendar.TUESDAY;
        }
        if (day.equals("X")) {
            return Calendar.WEDNESDAY;
        }
        if (day.equals("J")) {
            return Calendar.THURSDAY;
        }
        if (day.equals("V")) {
            return Calendar.FRIDAY;
        }
        if (day.equals("S")) {
            return Calendar.SATURDAY;
        }
        return -1;
    }

    public static String[] parseDate(String date) {
        String[] parseddate = new String[6];
        Pattern regex = Pattern.compile("\\[([^:]+):([^/]+)/([^/]+)/([^:]+):([^:]+):([^\\]]+)\\]");
        Matcher regexMatcher = regex.matcher(date);
        while (regexMatcher.find()) {
            if (!regexMatcher.group(1).equals("??")) {
                if (!regexMatcher.group(1).equals("X")) {
                    parseddate[0] = regexMatcher.group(1);
                } else {
                    parseddate[0] = "MI";
                }
            }
            if (!regexMatcher.group(2).equals("??")) {
                parseddate[1] = regexMatcher.group(2);
            }
            if (!regexMatcher.group(3).equals("??")) {
                parseddate[2] = regexMatcher.group(3);
            }
            if (!regexMatcher.group(4).equals("??")) {
                parseddate[3] = regexMatcher.group(4);
            }
            if (!regexMatcher.group(5).equals("??")) {
                parseddate[4] = regexMatcher.group(5);
            }
            if (!regexMatcher.group(6).equals("??")) {
                parseddate[5] = regexMatcher.group(6);
            }
        }
        return parseddate;
    }

    public static String normalizeElementName(String ElementName) {
        //If the element name contains one or more numbers replace for its description
        if (ElementName.matches(".*[0-9]+.*")) {
            Pattern regex = Pattern.compile("([0-9]+)");
            Matcher regexMatcher = regex.matcher(ElementName);

            while (regexMatcher.find()) {
                ElementName = ElementName.replace(regexMatcher.group(), convertNumbetToString(Integer.parseInt(regexMatcher.group())));
            }
        }

        ElementName = ElementName.replace(" ", "_");
        return ElementName.replaceFirst("[a-z]", (ElementName.charAt(0) + "").toUpperCase());
    }

    public static String normalizeEntityName(String EntityName) {
        String normalizedEntityName = "";

        EntityName = EntityName.replace("-", " ");
        EntityName = EntityName.replace("_", " ");

        while (EntityName.contains("  ")) {
            EntityName = EntityName.replace("  ", " ");
        }

        if (EntityName.startsWith(" ")) {
            EntityName = EntityName.substring(1);
        }
        if (EntityName.endsWith(" ")) {
            EntityName = EntityName.substring(0, EntityName.length() - 1);
        }

        if (EntityName.matches("[A-Z][a-z0-9 ]+([A-Z][a-z0-9 ]+)+")) {
            String[] splitedEntityName = EntityName.split("[A-Z ]");
            for (int i = 0; i < splitedEntityName.length; i++) {
                String splited = splitedEntityName[i];
                if (!splited.equals("")) {
                    String toAppend = "";
                    try {
                        toAppend = EntityName.substring(EntityName.indexOf(splited) - 1, (EntityName.indexOf(splited) + splited.length()));
                    } catch (Exception ex) {
                    }
                    normalizedEntityName += toAppend + ((i < splitedEntityName.length - 1) ? " " : "");
                }
            }
            EntityName = normalizedEntityName;
        }

        return EntityName.toLowerCase();
    }

    public static String[] getEntityNameSegments(String EntityName) {
        ArrayList<String> Segments = new ArrayList<String>();

        if (EntityName.matches("[A-Z][a-z0-9]+([A-Z][a-z0-9]+)+")) {
            String[] splitedEntityName = EntityName.split("[A-Z]");
            for (String splited : splitedEntityName) {
                if (!splited.equals("")) {
                    String toAppend;
                    try {
                        toAppend = EntityName.substring(EntityName.indexOf(splited) - 1, (EntityName.indexOf(splited) + splited.length())).toLowerCase();
                    } catch (Exception ex) {
                        break;
                    }
                    Segments.add(toAppend);
                    EntityName = EntityName.replace(toAppend, "");
                }
            }
        }
        if (EntityName.matches("[A-Za-z0-9]+([ _ -][A-Za-z0-9]+)+")) {
            return EntityName.split("[ _-]");
        }
        if (Segments.isEmpty()) {
            Segments.add(EntityName);
        }
        return Segments.toArray(new String[]{});
    }

    //Código basado en http://explotandocodigo.blogspot.mx/2008/12/convertir-un-numero-su-descripcion-en.html
    public static String convertNumbetToString(Integer number) {
        if (number > 2000000) {
            return "";
        }
        switch (number) {
            case 0:
                return " cero";
            case 1:
                return "uno";
            case 2:
                return "dos";
            case 3:
                return "tres";
            case 4:
                return "cuatro";
            case 5:
                return "cinco";
            case 6:
                return "seis";
            case 7:
                return "siete";
            case 8:
                return "ocho";
            case 9:
                return "nueve";
            case 10:
                return "diez";
            case 11:
                return "once";
            case 12:
                return "doce";
            case 13:
                return "trece";
            case 14:
                return "catorce";
            case 15:
                return "quince";
            case 20:
                return "veinte";
            case 30:
                return "treinta";
            case 40:
                return "cuarenta";
            case 50:
                return "cincuenta";
            case 60:
                return "sesenta";
            case 70:
                return "setenta";
            case 80:
                return "ochenta";
            case 90:
                return "noventa";
            case 100:
                return "cien";
            case 200:
                return "doscientos";
            case 300:
                return "trescientos";
            case 400:
                return "cuatrocientos";
            case 500:
                return "quinientos";
            case 600:
                return "seiscientos";
            case 700:
                return "setecientos";
            case 800:
                return "ochocientos";
            case 900:
                return "novecientos";
            case 1000:
                return "mil";
            case 1000000:
                return "un millon";
            case 2000000:
                return "dos millones";
        }
        if (number < 20) {
            return "dieci" + convertNumbetToString(number - 10);
        }
        if (number < 30) {
            return "veinti" + convertNumbetToString(number - 20);
        }
        if (number < 100) {
            return convertNumbetToString((int) (number / 10) * 10) + " y " + convertNumbetToString(number % 10);
        }
        if (number < 200) {
            return "ciento " + convertNumbetToString(number - 100);
        }
        if (number < 1000) {
            return convertNumbetToString((int) (number / 100) * 100) + " " + convertNumbetToString(number % 100);
        }
        if (number < 2000) {
            return "mil " + convertNumbetToString(number % 1000);
        }
        if (number < 1000000) {
            String var = convertNumbetToString((int) (number / 1000)) + " mil";
            if (number % 1000 != 0) {
                var += " " + convertNumbetToString(number % 1000);
            }
            return var;
        }
        if (number < 2000000) {
            return "un millon " + convertNumbetToString(number % 1000000);
        }
        return "";
    }
}
