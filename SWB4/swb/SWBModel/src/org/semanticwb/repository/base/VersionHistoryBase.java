package org.semanticwb.repository.base;


public class VersionHistoryBase extends org.semanticwb.repository.BaseNode implements org.semanticwb.repository.Referenceable
{
    public static final org.semanticwb.platform.SemanticProperty jcr_versionableUuid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#versionableUuid");
    public static final org.semanticwb.platform.SemanticProperty jcr_uuid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#uuid");
    public static final org.semanticwb.platform.SemanticClass nt_VersionHistory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#versionHistory");


    public static org.semanticwb.repository.VersionHistory createVersionHistory(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.repository.VersionHistory)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, nt_VersionHistory), nt_VersionHistory);
    }

    public VersionHistoryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getVersionableUuid()
    {
        return getSemanticObject().getProperty(jcr_versionableUuid);
    }

    public void setVersionableUuid(String versionableUuid)
    {
        getSemanticObject().setProperty(jcr_versionableUuid, versionableUuid);
    }

    public String getUuid()
    {
        return getSemanticObject().getProperty(jcr_uuid);
    }

    public void setUuid(String uuid)
    {
        getSemanticObject().setProperty(jcr_uuid, uuid);
    }
}
