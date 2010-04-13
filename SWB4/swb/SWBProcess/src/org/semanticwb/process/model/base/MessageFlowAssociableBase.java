package org.semanticwb.process.model.base;

public interface MessageFlowAssociableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_MessageFlowAssociation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#MessageFlowAssociation");
    public static final org.semanticwb.platform.SemanticProperty swp_hasMessageFlowAssocaition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasMessageFlowAssocaition");
    public static final org.semanticwb.platform.SemanticClass swp_MessageFlowAssociable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#MessageFlowAssociable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlowAssociation> listMessageFlowAssocaitions();
    public boolean hasMessageFlowAssocaition(org.semanticwb.process.model.MessageFlowAssociation value);

    public void addMessageFlowAssocaition(org.semanticwb.process.model.MessageFlowAssociation value);

    public void removeAllMessageFlowAssocaition();

    public void removeMessageFlowAssocaition(org.semanticwb.process.model.MessageFlowAssociation value);

    public org.semanticwb.process.model.MessageFlowAssociation getMessageFlowAssocaition();
}
