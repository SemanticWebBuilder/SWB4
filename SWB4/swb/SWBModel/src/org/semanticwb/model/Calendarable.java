package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface Calendarable extends GenericObject
{

    public GenericIterator<org.semanticwb.model.Calendar> listCalendars();

    public void addCalendar(org.semanticwb.model.Calendar calendar);

    public void removeAllCalendar();

    public void removeCalendar(org.semanticwb.model.Calendar calendar);

    public Calendar getCalendar();
}
