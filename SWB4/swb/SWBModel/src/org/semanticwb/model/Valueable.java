package org.semanticwb.model;

public interface Valueable extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swb_value=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#value");
    public static final org.semanticwb.platform.SemanticClass swb_Valueable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Valueable");
    public String getValue();
    public void setValue(String value);
}
