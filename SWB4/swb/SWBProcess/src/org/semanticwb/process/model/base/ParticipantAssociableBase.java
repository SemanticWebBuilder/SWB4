package org.semanticwb.process.model.base;

public interface ParticipantAssociableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_ParticipantAssociation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ParticipantAssociation");
    public static final org.semanticwb.platform.SemanticProperty swp_hasParticipantAssociation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasParticipantAssociation");
    public static final org.semanticwb.platform.SemanticClass swp_ParticipantAssociable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ParticipantAssociable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantAssociation> listParticipantAssociations();
    public boolean hasParticipantAssociation(org.semanticwb.process.model.ParticipantAssociation value);

    public void addParticipantAssociation(org.semanticwb.process.model.ParticipantAssociation value);

    public void removeAllParticipantAssociation();

    public void removeParticipantAssociation(org.semanticwb.process.model.ParticipantAssociation value);

    public org.semanticwb.process.model.ParticipantAssociation getParticipantAssociation();
}
