package org.semanticwb.model.base;

public interface CalendarRefableBase extends org.semanticwb.model.Referensable
{
    public static final org.semanticwb.platform.SemanticClass swb_CalendarRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#CalendarRef");
    public static final org.semanticwb.platform.SemanticProperty swb_hasCalendarRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasCalendarRef");
    public static final org.semanticwb.platform.SemanticClass swb_CalendarRefable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#CalendarRefable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef> listCalendarRefs();
    public boolean hasCalendarRef(org.semanticwb.model.CalendarRef value);

    public void addCalendarRef(org.semanticwb.model.CalendarRef value);

    public void removeAllCalendarRef();

    public void removeCalendarRef(org.semanticwb.model.CalendarRef value);

    public org.semanticwb.model.CalendarRef getCalendarRef();
}
