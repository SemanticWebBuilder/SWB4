package org.semanticwb.process.model.base;


public abstract class LoopActivityInstanceBase extends org.semanticwb.process.model.BPMNInstance implements org.semanticwb.process.model.Loopable
{
    public static final org.semanticwb.platform.SemanticClass swp_LoopActivityInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#LoopActivityInstance");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#LoopActivityInstance");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.LoopActivityInstance> listLoopActivityInstances(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LoopActivityInstance>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.LoopActivityInstance> listLoopActivityInstances()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LoopActivityInstance>(it, true);
        }

        public static org.semanticwb.process.model.LoopActivityInstance getLoopActivityInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.LoopActivityInstance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.LoopActivityInstance createLoopActivityInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.LoopActivityInstance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeLoopActivityInstance(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasLoopActivityInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (getLoopActivityInstance(id, model)!=null);
        }
    }

    public LoopActivityInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public int getLoopCounter()
    {
        return getSemanticObject().getIntProperty(swp_loopCounter);
    }

    public void setLoopCounter(int value)
    {
        getSemanticObject().setIntProperty(swp_loopCounter, value);
    }
}
