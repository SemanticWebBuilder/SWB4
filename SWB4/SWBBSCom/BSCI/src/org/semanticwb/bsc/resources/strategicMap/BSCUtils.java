/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.resources.strategicMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.bsc.Sortable;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.accessory.State;
import org.semanticwb.bsc.element.Objective;
import org.semanticwb.bsc.tracing.MeasurementFrequency;
import org.semanticwb.bsc.tracing.PeriodStatus;
import org.semanticwb.model.Activeable;
import org.semanticwb.model.SWBClass;
import org.semanticwb.model.User;

/**
 * Utiler&iacute;as exclusivas para el mapa estrat&eacute;gico. 
 * @author Martha Elvia Jim&eacute;nez Salgado
 * @version %I%, %G%
 * @since 1.0
 */
public class BSCUtils {

    /**
     * Se encarga de obtener las iniciales de un usuario de tipo Sponsor
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
     * @param period Elemento de tipo Period, se refiere al periodo seleccionado
     * actualmente en el sitio BSC
     * @param objective  objeto de tipo {@code Objective} utilizado para obtener
     * el icono de un objetivo
     * @return objeto de tipo {@code String} con la ruta del icono
     */
    public static String getIconPeriodStatus(Period period, Objective objective) {
        String icon = "";
        Iterator<PeriodStatus> listPeriodSta = objective.listPeriodStatuses();
        while (listPeriodSta.hasNext()) {
            PeriodStatus periodStatus = listPeriodSta.next();
            if (periodStatus.getPeriod().equals(period)) {
                State state = periodStatus.getStatus();
                if (state != null) {
                    icon = state.getIcon() == null ? "" : state.getIcon();
                }
            }
        }
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
     * @param itSortable Contiene el iterador a ordenar
     * @return objeto de tipo {@code List<Sortable>} que contiene una lista ordenada
     * de elementos de tipo Sortable
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
     * @param itSortable contiene una lista de elementos
     * @return objeto de tipo {@code List<Sortable>} que contiene una lista ordenada
     * de elementos de tipo Sortable
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
}
