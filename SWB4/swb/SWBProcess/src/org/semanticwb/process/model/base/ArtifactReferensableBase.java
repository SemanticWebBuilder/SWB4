package org.semanticwb.process.model.base;

public interface ArtifactReferensableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_Artifact=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Artifact");
    public static final org.semanticwb.platform.SemanticProperty swp_artifactRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#artifactRef");
    public static final org.semanticwb.platform.SemanticClass swp_ArtifactReferensable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ArtifactReferensable");

    public void setArtifactRef(org.semanticwb.process.model.Artifact artifact);

    public void removeArtifactRef();

    public org.semanticwb.process.model.Artifact getArtifactRef();
}
