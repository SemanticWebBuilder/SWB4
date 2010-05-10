package org.semanticwb.process.model.base;


public abstract class AnnotationArtifactBase extends org.semanticwb.process.model.Artifact implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swp_AnnotationArtifact=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#AnnotationArtifact");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#AnnotationArtifact");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.AnnotationArtifact> listAnnotationArtifacts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AnnotationArtifact>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.AnnotationArtifact> listAnnotationArtifacts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AnnotationArtifact>(it, true);
        }

        public static org.semanticwb.process.model.AnnotationArtifact createAnnotationArtifact(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.AnnotationArtifact.ClassMgr.createAnnotationArtifact(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.AnnotationArtifact getAnnotationArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.AnnotationArtifact)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.AnnotationArtifact createAnnotationArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.AnnotationArtifact)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeAnnotationArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasAnnotationArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAnnotationArtifact(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.AnnotationArtifact> listAnnotationArtifactByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AnnotationArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AnnotationArtifact> listAnnotationArtifactByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AnnotationArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AnnotationArtifact> listAnnotationArtifactByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AnnotationArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AnnotationArtifact> listAnnotationArtifactByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AnnotationArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public AnnotationArtifactBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
