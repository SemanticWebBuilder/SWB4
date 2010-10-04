package org.semanticwb.model.base;

   /**
   * Interfaz que define propiedades para elementos que pueden tener calendarización 
   */
public interface CalendarableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Objeto de calendarización que permite configurar una página o un recurso para desplegarse en cierta fecha, entre un rango de fechas o incluso en periodos de tiempo definidos. 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Calendar=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Calendar");
   /**
   * Calendario donde se definen los días en que el elemento es visible 
   */
    public static final org.semanticwb.platform.SemanticProperty swb_hasCalendar=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasCalendar");
   /**
   * Interfaz que define propiedades para elementos que pueden tener calendarización 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Calendarable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Calendarable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Calendar> listCalendars();
    public boolean hasCalendar(org.semanticwb.model.Calendar value);

   /**
   * Adds the Calendar
   * @param value An instance of org.semanticwb.model.Calendar
   */
    public void addCalendar(org.semanticwb.model.Calendar value);

   /**
   * Remove all the values for the property Calendar
   */
    public void removeAllCalendar();

   /**
   * Remove a value from the property Calendar
   * @param value An instance of org.semanticwb.model.Calendar
   */
    public void removeCalendar(org.semanticwb.model.Calendar value);

/**
* Gets the Calendar
* @return a instance of org.semanticwb.model.Calendar
*/
    public org.semanticwb.model.Calendar getCalendar();
}
