package org.semanticwb.process.model.base;

public interface InstantiableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swp_instantiate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#instantiate");
    public static final org.semanticwb.platform.SemanticClass swp_Instantiable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Instantiable");

    public boolean isInstantiate();

    public void setInstantiate(boolean value);
}
