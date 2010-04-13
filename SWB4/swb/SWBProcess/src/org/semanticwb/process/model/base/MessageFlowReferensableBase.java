package org.semanticwb.process.model.base;

public interface MessageFlowReferensableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_MessageFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#MessageFlow");
    public static final org.semanticwb.platform.SemanticProperty swp_hasMessageFlowRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasMessageFlowRef");
    public static final org.semanticwb.platform.SemanticClass swp_MessageFlowReferensable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#MessageFlowReferensable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlow> listMessageFlowRefs();
    public boolean hasMessageFlowRef(org.semanticwb.process.model.MessageFlow value);

    public void addMessageFlowRef(org.semanticwb.process.model.MessageFlow value);

    public void removeAllMessageFlowRef();

    public void removeMessageFlowRef(org.semanticwb.process.model.MessageFlow value);

    public org.semanticwb.process.model.MessageFlow getMessageFlowRef();
}
