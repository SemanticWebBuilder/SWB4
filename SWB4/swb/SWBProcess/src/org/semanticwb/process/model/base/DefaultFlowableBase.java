package org.semanticwb.process.model.base;

public interface DefaultFlowableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_SequenceFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#SequenceFlow");
    public static final org.semanticwb.platform.SemanticProperty swp_defaultSequenceFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#defaultSequenceFlow");
    public static final org.semanticwb.platform.SemanticClass swp_DefaultFlowable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DefaultFlowable");

    public void setDefaultSequenceFlow(org.semanticwb.process.model.SequenceFlow sequenceflow);

    public void removeDefaultSequenceFlow();

    public org.semanticwb.process.model.SequenceFlow getDefaultSequenceFlow();
}
