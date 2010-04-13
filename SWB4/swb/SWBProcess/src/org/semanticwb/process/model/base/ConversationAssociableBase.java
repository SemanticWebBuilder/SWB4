package org.semanticwb.process.model.base;

public interface ConversationAssociableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_ConversationAssociation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ConversationAssociation");
    public static final org.semanticwb.platform.SemanticProperty swp_hasConversationAssociation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasConversationAssociation");
    public static final org.semanticwb.platform.SemanticClass swp_ConversationAssociable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ConversationAssociable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConversationAssociation> listConversationAssociations();
    public boolean hasConversationAssociation(org.semanticwb.process.model.ConversationAssociation value);

    public void addConversationAssociation(org.semanticwb.process.model.ConversationAssociation value);

    public void removeAllConversationAssociation();

    public void removeConversationAssociation(org.semanticwb.process.model.ConversationAssociation value);

    public org.semanticwb.process.model.ConversationAssociation getConversationAssociation();
}
