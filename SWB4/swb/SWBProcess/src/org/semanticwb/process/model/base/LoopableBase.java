package org.semanticwb.process.model.base;

public interface LoopableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swp_loopCounter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#loopCounter");
    public static final org.semanticwb.platform.SemanticClass swp_Loopable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Loopable");

    public int getLoopCounter();

    public void setLoopCounter(int value);
}
