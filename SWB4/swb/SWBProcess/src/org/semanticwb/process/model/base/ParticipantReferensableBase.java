package org.semanticwb.process.model.base;

public interface ParticipantReferensableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_Participant=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Participant");
    public static final org.semanticwb.platform.SemanticProperty swp_hasParticipant=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasParticipant");
    public static final org.semanticwb.platform.SemanticClass swp_ParticipantReferensable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ParticipantReferensable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Participant> listParticipants();
    public boolean hasParticipant(org.semanticwb.process.model.Participant value);

    public void addParticipant(org.semanticwb.process.model.Participant value);

    public void removeAllParticipant();

    public void removeParticipant(org.semanticwb.process.model.Participant value);

    public org.semanticwb.process.model.Participant getParticipant();
}
