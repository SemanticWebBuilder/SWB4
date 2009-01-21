package org.semanticwb.repository.base;


public class VersionLabelsBase extends org.semanticwb.repository.BaseNode 
{
    public static final org.semanticwb.platform.SemanticClass nt_VersionLabels=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#versionLabels");


    public static org.semanticwb.repository.VersionLabels createVersionLabels(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.repository.VersionLabels)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, nt_VersionLabels), nt_VersionLabels);
    }

    public VersionLabelsBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
