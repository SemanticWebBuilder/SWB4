package org.semanticwb.repository.base;


public class VersionLabelsBase extends org.semanticwb.repository.BaseNode 
{
    public static final org.semanticwb.platform.SemanticClass nt_VersionLabels=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#versionLabels");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#versionLabels");

    public VersionLabelsBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.repository.VersionLabels getVersionLabels(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.repository.VersionLabels)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.repository.VersionLabels> listVersionLabelss(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.VersionLabels>(org.semanticwb.repository.VersionLabels.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.repository.VersionLabels> listVersionLabelss()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.VersionLabels>(org.semanticwb.repository.VersionLabels.class, it, true);
    }

    public static org.semanticwb.repository.VersionLabels createVersionLabels(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.repository.VersionLabels)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeVersionLabels(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasVersionLabels(String id, org.semanticwb.model.SWBModel model)
    {
        return (getVersionLabels(id, model)!=null);
    }
}
