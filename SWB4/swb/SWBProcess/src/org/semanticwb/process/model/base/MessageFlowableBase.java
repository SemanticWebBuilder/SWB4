package org.semanticwb.process.model.base;

public interface MessageFlowableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_MessageFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#MessageFlow");
    public static final org.semanticwb.platform.SemanticProperty swp_hasMessageFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasMessageFlow");
    public static final org.semanticwb.platform.SemanticClass swp_MessageFlowable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#MessageFlowable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlow> listMessageFlows();
    public boolean hasMessageFlow(org.semanticwb.process.model.MessageFlow value);

    public void addMessageFlow(org.semanticwb.process.model.MessageFlow value);

    public void removeAllMessageFlow();

    public void removeMessageFlow(org.semanticwb.process.model.MessageFlow value);

    public org.semanticwb.process.model.MessageFlow getMessageFlow();
}
