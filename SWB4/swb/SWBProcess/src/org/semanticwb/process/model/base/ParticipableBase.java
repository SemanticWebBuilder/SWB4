package org.semanticwb.process.model.base;

public interface ParticipableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_Participant=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Participant");
    public static final org.semanticwb.platform.SemanticProperty swp_participantRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#participantRef");
    public static final org.semanticwb.platform.SemanticClass swp_Participable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Participable");

    public void setParticipantRef(org.semanticwb.process.model.Participant participant);

    public void removeParticipantRef();

    public org.semanticwb.process.model.Participant getParticipantRef();
}
