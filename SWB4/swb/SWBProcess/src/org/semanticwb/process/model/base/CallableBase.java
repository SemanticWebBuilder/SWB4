package org.semanticwb.process.model.base;

public interface CallableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swp_callable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#callable");
    public static final org.semanticwb.platform.SemanticClass swp_Callable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Callable");

    public boolean isCallable();

    public void setCallable(boolean value);
}
