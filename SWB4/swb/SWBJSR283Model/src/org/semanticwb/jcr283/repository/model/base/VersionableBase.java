package org.semanticwb.jcr283.repository.model.base;

public interface VersionableBase extends org.semanticwb.jcr283.repository.model.Referenceable,org.semanticwb.jcr283.repository.model.SimpleVersionable
{
    public static final org.semanticwb.platform.SemanticClass mix_Versionable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/mix/1.0#versionable");
}
