package org.semanticwb.model.base;

   /**
   * Interfaz que define propiedades para elementos que pueden referencia a calendarios 
   */
public interface CalendarRefableBase extends org.semanticwb.model.Referensable
{
    public static final org.semanticwb.platform.SemanticProperty swb_notInheritCalendarRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#notInheritCalendarRef");
   /**
   * Referencia a un objeto de tipo Calendar 
   */
    public static final org.semanticwb.platform.SemanticClass swb_CalendarRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#CalendarRef");
    public static final org.semanticwb.platform.SemanticProperty swb_hasCalendarRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasCalendarRef");
   /**
   * Interfaz que define propiedades para elementos que pueden referencia a calendarios 
   */
    public static final org.semanticwb.platform.SemanticClass swb_CalendarRefable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#CalendarRefable");

    public boolean isNotInheritCalendarRef();

    public void setNotInheritCalendarRef(boolean value);

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef> listCalendarRefs();
    public boolean hasCalendarRef(org.semanticwb.model.CalendarRef value);

   /**
   * Adds the CalendarRef
   * @param value An instance of org.semanticwb.model.CalendarRef
   */
    public void addCalendarRef(org.semanticwb.model.CalendarRef value);

   /**
   * Remove all the values for the property CalendarRef
   */
    public void removeAllCalendarRef();

   /**
   * Remove a value from the property CalendarRef
   * @param value An instance of org.semanticwb.model.CalendarRef
   */
    public void removeCalendarRef(org.semanticwb.model.CalendarRef value);

/**
* Gets the CalendarRef
* @return a instance of org.semanticwb.model.CalendarRef
*/
    public org.semanticwb.model.CalendarRef getCalendarRef();
}
