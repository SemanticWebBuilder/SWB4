package org.semanticwb.bsc.base;

public interface ReadOnlyBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty bsc_readOnly=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#readOnly");
    public static final org.semanticwb.platform.SemanticClass bsc_ReadOnly=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#ReadOnly");

    public boolean isReadOnly();

    public void setReadOnly(boolean value);
}
