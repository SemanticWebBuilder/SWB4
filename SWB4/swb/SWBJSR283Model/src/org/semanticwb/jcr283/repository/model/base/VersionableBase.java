package org.semanticwb.jcr283.repository.model.base;

public interface VersionableBase extends org.semanticwb.jcr283.repository.model.SimpleVersionable,org.semanticwb.jcr283.repository.model.Referenceable
{
    public static final org.semanticwb.platform.SemanticClass nt_Activity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#activity");
    public static final org.semanticwb.platform.SemanticProperty jcr_activity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#activity");
    public static final org.semanticwb.platform.SemanticClass nt_VersionHistory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#versionHistory");
    public static final org.semanticwb.platform.SemanticProperty jcr_versionHistory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#versionHistory");
    public static final org.semanticwb.platform.SemanticClass nt_Version=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#version");
    public static final org.semanticwb.platform.SemanticProperty jcr_baseVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#baseVersion");
    public static final org.semanticwb.platform.SemanticClass mix_Versionable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/mix/1.0#versionable");

    public void setActivity(org.semanticwb.jcr283.repository.model.Activity activity);

    public void removeActivity();

    public org.semanticwb.jcr283.repository.model.Activity getActivity();

    public void setVersionHistory(org.semanticwb.jcr283.repository.model.VersionHistory versionhistory);

    public void removeVersionHistory();

    public org.semanticwb.jcr283.repository.model.VersionHistory getVersionHistory();

    public void setBaseVersion(org.semanticwb.jcr283.repository.model.Version version);

    public void removeBaseVersion();

    public org.semanticwb.jcr283.repository.model.Version getBaseVersion();
}
