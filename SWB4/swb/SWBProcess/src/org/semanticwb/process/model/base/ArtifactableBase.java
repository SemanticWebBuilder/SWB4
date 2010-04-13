package org.semanticwb.process.model.base;

public interface ArtifactableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_Artifact=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Artifact");
    public static final org.semanticwb.platform.SemanticProperty swp_hasArtifact=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasArtifact");
    public static final org.semanticwb.platform.SemanticClass swp_Artifactable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Artifactable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Artifact> listArtifacts();
    public boolean hasArtifact(org.semanticwb.process.model.Artifact value);

    public void addArtifact(org.semanticwb.process.model.Artifact value);

    public void removeAllArtifact();

    public void removeArtifact(org.semanticwb.process.model.Artifact value);

    public org.semanticwb.process.model.Artifact getArtifact();
}
