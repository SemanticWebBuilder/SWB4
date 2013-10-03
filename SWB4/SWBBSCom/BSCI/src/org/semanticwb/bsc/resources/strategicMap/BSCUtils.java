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
 *
 * @author martha.jimenez
 */
public class BSCUtils {

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

    public static String getFrequencyObj(MeasurementFrequency mesure) {
        String mesureFreq = "";
        if (mesure != null) {
            mesureFreq = mesure.getTitle();
        }
        return mesureFreq;
    }

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

    private static List<Sortable> listValidSortable(Iterator<Sortable> itSortable) {
        List<Sortable> validSorteable = new ArrayList<Sortable>();
        while (itSortable.hasNext()) {
            org.semanticwb.bsc.Sortable sort = itSortable.next();

            if (sort instanceof Activeable && sort instanceof SWBClass) {
                Activeable activeable = (Activeable) sort;
                SWBClass classSort = (SWBClass) sort;
                if (activeable.isActive() && classSort.isValid()) {
                    validSorteable.add(sort);
                }
            }
        }
        return validSorteable;
    }
}
