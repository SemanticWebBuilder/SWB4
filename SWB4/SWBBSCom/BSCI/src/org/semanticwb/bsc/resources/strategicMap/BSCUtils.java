/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.resources.strategicMap;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.bsc.Sortable;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.accessory.State;
import org.semanticwb.bsc.element.Objective;
import org.semanticwb.bsc.element.Theme;
import org.semanticwb.bsc.tracing.MeasurementFrequency;
import org.semanticwb.bsc.tracing.PeriodStatus;
import org.semanticwb.model.Activeable;
import org.semanticwb.model.SWBClass;
import org.semanticwb.model.User;

/**
 * Utiler&iacute;as exclusivas para el mapa estrat&eacute;gico.
 *
 * @author Martha Elvia Jim&eacute;nez Salgado
 * @version %I%, %G%
 * @since 1.0
 */
public class BSCUtils {

    /**
     * Se encarga de obtener las iniciales de un usuario de tipo Sponsor
     *
     * @param sponsor objeto de tipo {@code User} que representa un sponsor
     * @return objeto de tipo {@code String} con las iniciales de un Sponsor
     */
    public static String getSponsor(User sponsor) {
        String initials = "";
        if (sponsor != null) {
            String fullname = sponsor.getFullName() == null ? "" : sponsor.getFullName();
            String[] dataName = fullname.split(" ");
            for (int i = 0; i < dataName.length; i++) {
                initials = initials + dataName[i].substring(0, 1);
            }
        }
        return initials;
    }

    /**
     *
     * Obtiene el icon para un objetivo durante un periodo
     *
     * @param period Elemento de tipo Period, se refiere al periodo seleccionado
     * actualmente en el sitio BSC
     * @param objective objeto de tipo {@code Objective} utilizado para obtener
     * el icono de un objetivo
     * @return objeto de tipo {@code String} con la ruta del icono
     */
    public static String getIconPeriodStatus(Period period, Objective objective) {
        String icon = "indefinido";
        
//        Iterator<PeriodStatus> listPeriodSta = objective.listPeriodStatuses();
//        while (listPeriodSta.hasNext()) {
//            PeriodStatus periodStatus = listPeriodSta.next();
//            if (periodStatus.getPeriod().equals(period)) {
//                State state = periodStatus.getStatus();
                State state = objective.getState(period);
                if (state != null) {
                    icon = state.getIconClass() == null ? "indefinido" : state.getIconClass();
                }
//            }
//        }
        return icon;
    }

    /**
     *
     * @param mesure objeto de tipo {@code MeasurementFrequency} que contiene el
     * t&iacute;tulo de la frecuencia de medici&oacute;n
     * @return string con el t&iacute;tulo de la medida de frecuencia
     */
    public static String getFrequencyObj(MeasurementFrequency mesure) {
        String mesureFreq = "";
        if (mesure != null) {
            mesureFreq = mesure.getTitle();
        }
        return mesureFreq;
    }

    /**
     * Se encarga de ordenar un conjunto de datos proporcionado
     *
     * @param itSortable Contiene el iterador a ordenar
     * @return objeto de tipo {@code List<Sortable>} que contiene una lista
     * ordenada de elementos de tipo Sortable
     */
    public static List<Sortable> sortObjSortable(Iterator<Sortable> itSortable) {
        List sortable = listValidSortable(itSortable);
        Collections.sort(sortable, new Comparator() {
            public int compare(Object o1, Object o2) {
                Sortable p1 = (Sortable) o1;
                Sortable p2 = (Sortable) o2;
                return p1.getIndex() >= p2.getIndex() ? 1 : -1;
            }
        });
        return sortable;
    }

    /**
     * Se encarga de validar la lista proporcionada.
     *
     * @param itSortable contiene una lista de elementos
     * @return objeto de tipo {@code List<Sortable>} que contiene una lista
     * ordenada de elementos de tipo Sortable
     */
    private static List<Sortable> listValidSortable(Iterator<Sortable> itSortable) {
        List<Sortable> validSorteable = new ArrayList<Sortable>();
        while (itSortable.hasNext()) {
            org.semanticwb.bsc.Sortable sort = itSortable.next();

            if ((sort instanceof Activeable) && (sort instanceof SWBClass)) {
                Activeable activeable = (Activeable) sort;
                SWBClass classSort = (SWBClass) sort;
                if ((activeable.isActive()) && (classSort.isValid())) {
                    validSorteable.add(sort);
                }
            }
        }
        return validSorteable;
    }

    /**
     * Valida que la lista proporcionada y los padres de los objetos sean v&aacute;lidos y 
     * activos, valida que el tema del objeto Objective no sea igual al tema proporcionado
     * @param itSortable Lista de objetos a comparar
     * @param themeCurrent Tema que ser&aacute; utilizado para evaluar objetivos 
     * v&uacute;lidos
     * @return  Lista con objetos v&uacute;alidos
     */
    public static List<Sortable> listValidParent(Iterator<Sortable> itSortable,
            Theme themeCurrent) {
        List<Sortable> validSorteable = new ArrayList<Sortable>();
        while (itSortable.hasNext()) {
            org.semanticwb.bsc.Sortable sort = itSortable.next();
            if (sort instanceof Theme) {
                Theme theme = (Theme) sort;
                if ((theme.getPerspective().isActive()) && (theme.getPerspective().isValid())) {
                    validSorteable.add(sort);
                }
            } else if (sort instanceof Objective) {
                Objective objective = (Objective) sort;
                if ((objective.getTheme().isActive() && objective.getTheme().isValid())
                        && (objective.getTheme().getPerspective().isActive()
                        && objective.getTheme().getPerspective().isValid())) {
                    if ((themeCurrent == null) || (!objective.getTheme().equals(themeCurrent))) {
                        validSorteable.add(sort);
                    }
                }
            }
        }
        return validSorteable;
    }

    /**
     * Obtiene un arreglo con el n&uacute;mero de objetivos existentes por
     * perspectiva.
     *
     * @param countTheme Cantidad de temas por perspectiva
     * @return arreglo con el tama&ntilde;o de las columnas inicial, final y las
     * interiores
     */
    public static int[] getSizeColumnsTheme(int countTheme) {
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
     * Obtiene el tama&ntilde;o del ancho para cada diferenciador
     * @param countDiff n&uacute;mero de diferenciadores
     * @return tama&ntilde;o para cada diferenciador
     */
    public static double getSizeDifferentiator(int countDiff) {
        double size = 0;
        if (countDiff == 0) {
            throw new java.lang.ArithmeticException("/ by zero");
        }
        BigDecimal sizeCols = new BigDecimal(100).divide(new BigDecimal(countDiff),
                MathContext.DECIMAL128);

        size = sizeCols.round(MathContext.DECIMAL32).doubleValue();
        return size;
    }

    /**
     * Obtiene el tama&ntilde;o de las columnas por tema
     *
     * @param amountObjective Cantidad de objetivos por tema
     * @param sizeColumn tama&ntilde;o de la columna
     * @return arreglo con el tama&ntilde;o de las columnas inicial, final y las
     * interiores
     */
    public static int[] getSizeColumnsObjective(int amountObjective, int sizeColumn) {
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
}
