package org.semanticwb.model;

public interface Undeleteable extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swb_undeleteable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#undeleteable");
    public static final org.semanticwb.platform.SemanticClass swb_Undeleteable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Undeleteable");
    public boolean isUndeleteable();
    public void setUndeleteable(boolean undeleteable);
}
