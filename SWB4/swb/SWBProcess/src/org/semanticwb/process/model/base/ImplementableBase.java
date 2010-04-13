package org.semanticwb.process.model.base;

public interface ImplementableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swp_implementation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#implementation");
    public static final org.semanticwb.platform.SemanticClass swp_Implementable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Implementable");

    public String getImplementation();

    public void setImplementation(String value);
}
