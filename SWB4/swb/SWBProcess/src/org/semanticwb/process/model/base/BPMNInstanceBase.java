package org.semanticwb.process.model.base;


public abstract class BPMNInstanceBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass swp_BPMNInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNInstance");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNInstance");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.BPMNInstance> listBPMNInstances(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNInstance>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNInstance> listBPMNInstances()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNInstance>(it, true);
        }

        public static org.semanticwb.process.model.BPMNInstance getBPMNInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.BPMNInstance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.BPMNInstance createBPMNInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.BPMNInstance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeBPMNInstance(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasBPMNInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (getBPMNInstance(id, model)!=null);
        }
    }

    public BPMNInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
