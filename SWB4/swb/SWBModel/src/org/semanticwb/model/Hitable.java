package org.semanticwb.model;

public interface Hitable extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swb_hits=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hits");
    public static final org.semanticwb.platform.SemanticProperty swb_maxHits=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#maxHits");
    public static final org.semanticwb.platform.SemanticClass swb_Hitable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Hitable");
    public long getHits();
    public void setHits(long hits);
    public long getMaxHits();
    public void setMaxHits(long maxHits);
}
