package org.semanticwb.bsc.base;

public interface BlockableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty bsc_blocked=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#blocked");
    public static final org.semanticwb.platform.SemanticClass bsc_Blockable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Blockable");

    public boolean isBlocked();

    public void setBlocked(boolean value);
}
