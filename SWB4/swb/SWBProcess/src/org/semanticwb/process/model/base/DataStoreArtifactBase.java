package org.semanticwb.process.model.base;


public abstract class DataStoreArtifactBase extends org.semanticwb.process.model.Artifact implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swp_DataStoreArtifact=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#DataStoreArtifact");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#DataStoreArtifact");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.DataStoreArtifact> listDataStoreArtifacts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStoreArtifact>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStoreArtifact> listDataStoreArtifacts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStoreArtifact>(it, true);
        }

        public static org.semanticwb.process.model.DataStoreArtifact createDataStoreArtifact(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.DataStoreArtifact.ClassMgr.createDataStoreArtifact(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.DataStoreArtifact getDataStoreArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataStoreArtifact)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.DataStoreArtifact createDataStoreArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataStoreArtifact)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeDataStoreArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasDataStoreArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDataStoreArtifact(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStoreArtifact> listDataStoreArtifactByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStoreArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStoreArtifact> listDataStoreArtifactByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStoreArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStoreArtifact> listDataStoreArtifactByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStoreArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStoreArtifact> listDataStoreArtifactByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStoreArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public DataStoreArtifactBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
