package org.semanticwb.model.base;

public interface CampableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swb_Camp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Camp");
    public static final org.semanticwb.platform.SemanticProperty swb_camp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#camp");
    public static final org.semanticwb.platform.SemanticClass swb_Campable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Campable");

    public void setCamp(org.semanticwb.model.Camp value);

    public void removeCamp();

    public org.semanticwb.model.Camp getCamp();
}
