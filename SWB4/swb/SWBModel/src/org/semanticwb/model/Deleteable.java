package org.semanticwb.model;

public interface Deleteable extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swb_deleted=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#deleted");
    public static final org.semanticwb.platform.SemanticClass swb_Deleteable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Deleteable");
    public boolean isDeleted();
    public void setDeleted(boolean deleted);
}
