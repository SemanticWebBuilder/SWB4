package org.semanticwb.process.model.base;

public interface ConversableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_Conversation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Conversation");
    public static final org.semanticwb.platform.SemanticProperty swp_hasConversation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasConversation");
    public static final org.semanticwb.platform.SemanticClass swp_Conversable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Conversable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation> listConversations();
    public boolean hasConversation(org.semanticwb.process.model.Conversation value);

    public void addConversation(org.semanticwb.process.model.Conversation value);

    public void removeAllConversation();

    public void removeConversation(org.semanticwb.process.model.Conversation value);

    public org.semanticwb.process.model.Conversation getConversation();
}
