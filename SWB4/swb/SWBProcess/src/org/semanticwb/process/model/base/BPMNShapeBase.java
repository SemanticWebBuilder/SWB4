package org.semanticwb.process.model.base;


public abstract class BPMNShapeBase extends org.semanticwb.process.model.BPMNNode 
{
    public static final org.semanticwb.platform.SemanticClass swp_BPMNShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNShape");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNShape");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.BPMNShape> listBPMNShapes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNShape>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNShape> listBPMNShapes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNShape>(it, true);
        }

        public static org.semanticwb.process.model.BPMNShape createBPMNShape(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.BPMNShape.ClassMgr.createBPMNShape(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.BPMNShape getBPMNShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.BPMNShape)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.BPMNShape createBPMNShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.BPMNShape)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeBPMNShape(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasBPMNShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (getBPMNShape(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNShape> listBPMNShapeByContextType(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNShape> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_contextType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNShape> listBPMNShapeByContextType(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNShape> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_contextType,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public BPMNShapeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
