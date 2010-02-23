package org.semanticwb.repository.base;

public interface VersionableBase extends org.semanticwb.repository.Referenceable
{
    public static final org.semanticwb.platform.SemanticProperty jcr_isCheckedOut=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#isCheckedOut");
    public static final org.semanticwb.platform.SemanticClass nt_VersionHistory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#versionHistory");
    public static final org.semanticwb.platform.SemanticProperty jcr_versionHistory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#versionHistory");
    public static final org.semanticwb.platform.SemanticClass nt_Version=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#version");
    public static final org.semanticwb.platform.SemanticProperty jcr_baseVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#baseVersion");
    public static final org.semanticwb.platform.SemanticProperty jcr_mergeFailed=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#mergeFailed");
    public static final org.semanticwb.platform.SemanticClass mix_Versionable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/mix/1.0#versionable");

    public boolean isIsCheckedOut();

    public void setIsCheckedOut(boolean value);

    public void setVersionHistory(org.semanticwb.repository.VersionHistory versionhistory);

    public void removeVersionHistory();

    public org.semanticwb.repository.VersionHistory getVersionHistory();

    public void setBaseVersion(org.semanticwb.repository.Version version);

    public void removeBaseVersion();

    public org.semanticwb.repository.Version getBaseVersion();

    public void setMergeFailed(org.semanticwb.repository.Version version);

    public void removeMergeFailed();

    public org.semanticwb.repository.Version getMergeFailed();
}
