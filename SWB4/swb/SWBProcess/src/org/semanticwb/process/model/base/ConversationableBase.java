package org.semanticwb.process.model.base;

public interface ConversationableBase extends org.semanticwb.process.model.Artifactable
{
    public static final org.semanticwb.platform.SemanticClass swp_ConversationNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ConversationNode");
    public static final org.semanticwb.platform.SemanticProperty swp_hasConversationNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasConversationNode");
    public static final org.semanticwb.platform.SemanticClass swp_Conversationable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Conversationable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConversationNode> listConversationNodes();
    public boolean hasConversationNode(org.semanticwb.process.model.ConversationNode value);

    public void addConversationNode(org.semanticwb.process.model.ConversationNode value);

    public void removeAllConversationNode();

    public void removeConversationNode(org.semanticwb.process.model.ConversationNode value);

    public org.semanticwb.process.model.ConversationNode getConversationNode();
}
