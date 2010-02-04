package org.semanticwb.process.model.base;

public interface ObservableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swp_status=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#status");
    public static final org.semanticwb.platform.SemanticClass swp_Observable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Observable");

    public String getStatus();

    public void setStatus(String value);
}
