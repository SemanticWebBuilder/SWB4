package org.semanticwb.model;

public interface Viewable extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swb_views=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#views");
    public static final org.semanticwb.platform.SemanticProperty swb_hits=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hits");
    public static final org.semanticwb.platform.SemanticClass swb_Viewable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Viewable");
    public int getViews();
    public void setViews(int views);
    public int getHits();
    public void setHits(int hits);
}
