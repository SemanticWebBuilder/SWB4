package org.semanticwb.process.model.base;

public interface CatchableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_EventDetail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#EventDetail");
    public static final org.semanticwb.platform.SemanticProperty swp_hasTrigger=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasTrigger");
    public static final org.semanticwb.platform.SemanticClass swp_Catchable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Catchable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventDetail> listTriggers();
    public boolean hasTrigger(org.semanticwb.process.model.EventDetail eventdetail);

    public void addTrigger(org.semanticwb.process.model.EventDetail eventdetail);

    public void removeAllTrigger();

    public void removeTrigger(org.semanticwb.process.model.EventDetail eventdetail);

    public org.semanticwb.process.model.EventDetail getTrigger();
}
