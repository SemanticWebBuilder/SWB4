package org.semanticwb.model.base;

public interface CalendarRefableBase extends org.semanticwb.model.Referensable
{
   /**
   * Referencia a un objeto de tipo Calendar 
   */
    public static final org.semanticwb.platform.SemanticClass swb_CalendarRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#CalendarRef");
    public static final org.semanticwb.platform.SemanticProperty swb_hasCalendarRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasCalendarRef");
    public static final org.semanticwb.platform.SemanticClass swb_CalendarRefable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#CalendarRefable");

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
