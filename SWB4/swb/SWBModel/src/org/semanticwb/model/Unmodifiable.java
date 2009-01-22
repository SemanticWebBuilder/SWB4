package org.semanticwb.model;

public interface Unmodifiable extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swb_readOnly=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#readOnly");
    public static final org.semanticwb.platform.SemanticClass swb_Unmodifiable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Unmodifiable");
    public boolean isReadOnly();
    public void setReadOnly(boolean readOnly);
}
