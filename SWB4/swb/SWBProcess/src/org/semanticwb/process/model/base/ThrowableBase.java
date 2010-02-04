package org.semanticwb.process.model.base;

public interface ThrowableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_EventDetail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#EventDetail");
    public static final org.semanticwb.platform.SemanticProperty swp_hasResult=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasResult");
    public static final org.semanticwb.platform.SemanticClass swp_Throwable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Throwable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventDetail> listResults();
    public boolean hasResult(org.semanticwb.process.model.EventDetail eventdetail);

    public void addResult(org.semanticwb.process.model.EventDetail eventdetail);

    public void removeAllResult();

    public void removeResult(org.semanticwb.process.model.EventDetail eventdetail);

    public org.semanticwb.process.model.EventDetail getResult();
}
