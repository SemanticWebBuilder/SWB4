package org.semanticwb.process.model.base;

public interface MultipleCorrelationKeyReferensableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_CorrelationKey=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CorrelationKey");
    public static final org.semanticwb.platform.SemanticProperty swp_hasCorrelationKey=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasCorrelationKey");
    public static final org.semanticwb.platform.SemanticClass swp_MultipleCorrelationKeyReferensable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#MultipleCorrelationKeyReferensable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationKey> listCorrelationKeys();
    public boolean hasCorrelationKey(org.semanticwb.process.model.CorrelationKey value);

    public void addCorrelationKey(org.semanticwb.process.model.CorrelationKey value);

    public void removeAllCorrelationKey();

    public void removeCorrelationKey(org.semanticwb.process.model.CorrelationKey value);

    public org.semanticwb.process.model.CorrelationKey getCorrelationKey();
}
