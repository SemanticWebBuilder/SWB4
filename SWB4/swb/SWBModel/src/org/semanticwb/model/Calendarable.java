package org.semanticwb.model;

import org.semanticwb.platform.SemanticIterator;
import java.util.Date;
public interface Calendarable 
{
    public SemanticIterator<org.semanticwb.model.Calendar> listCalendar();
    public void addCalendar(org.semanticwb.model.Calendar calendar);
    public void removeAllCalendar();
    public Calendar getCalendar();
}
