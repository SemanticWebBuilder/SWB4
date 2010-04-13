package org.semanticwb.process.model.base;

public interface CorrelationKeyReferensableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_CorrelationKey=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CorrelationKey");
    public static final org.semanticwb.platform.SemanticProperty swp_correlationKeyRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#correlationKeyRef");
    public static final org.semanticwb.platform.SemanticClass swp_CorrelationKeyReferensable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CorrelationKeyReferensable");

    public void setCorrelationKeyRef(org.semanticwb.process.model.CorrelationKey correlationkey);

    public void removeCorrelationKeyRef();

    public org.semanticwb.process.model.CorrelationKey getCorrelationKeyRef();
}
