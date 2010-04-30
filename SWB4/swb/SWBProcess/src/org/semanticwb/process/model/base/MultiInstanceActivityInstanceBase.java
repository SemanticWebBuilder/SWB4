package org.semanticwb.process.model.base;


public abstract class MultiInstanceActivityInstanceBase extends org.semanticwb.process.model.BPMNInstance implements org.semanticwb.process.model.Loopable
{
    public static final org.semanticwb.platform.SemanticProperty swp_numberOfActiveInstances=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#numberOfActiveInstances");
    public static final org.semanticwb.platform.SemanticProperty swp_numberOfInstances=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#numberOfInstances");
    public static final org.semanticwb.platform.SemanticProperty swp_numberOfTerminatedInstances=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#numberOfTerminatedInstances");
    public static final org.semanticwb.platform.SemanticProperty swp_numberOfCompletedInstances=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#numberOfCompletedInstances");
    public static final org.semanticwb.platform.SemanticClass swp_MultiInstanceActivityInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#MultiInstanceActivityInstance");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#MultiInstanceActivityInstance");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceActivityInstance> listMultiInstanceActivityInstances(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceActivityInstance>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceActivityInstance> listMultiInstanceActivityInstances()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceActivityInstance>(it, true);
        }

        public static org.semanticwb.process.model.MultiInstanceActivityInstance createMultiInstanceActivityInstance(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.MultiInstanceActivityInstance.ClassMgr.createMultiInstanceActivityInstance(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.MultiInstanceActivityInstance getMultiInstanceActivityInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.MultiInstanceActivityInstance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.MultiInstanceActivityInstance createMultiInstanceActivityInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.MultiInstanceActivityInstance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeMultiInstanceActivityInstance(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasMultiInstanceActivityInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMultiInstanceActivityInstance(id, model)!=null);
        }
    }

    public MultiInstanceActivityInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public int getNumberOfActiveInstances()
    {
        return getSemanticObject().getIntProperty(swp_numberOfActiveInstances);
    }

    public void setNumberOfActiveInstances(int value)
    {
        getSemanticObject().setIntProperty(swp_numberOfActiveInstances, value);
    }

    public int getNumberOfInstances()
    {
        return getSemanticObject().getIntProperty(swp_numberOfInstances);
    }

    public void setNumberOfInstances(int value)
    {
        getSemanticObject().setIntProperty(swp_numberOfInstances, value);
    }

    public int getNumberOfTerminatedInstances()
    {
        return getSemanticObject().getIntProperty(swp_numberOfTerminatedInstances);
    }

    public void setNumberOfTerminatedInstances(int value)
    {
        getSemanticObject().setIntProperty(swp_numberOfTerminatedInstances, value);
    }

    public int getNumberOfCompletedInstances()
    {
        return getSemanticObject().getIntProperty(swp_numberOfCompletedInstances);
    }

    public void setNumberOfCompletedInstances(int value)
    {
        getSemanticObject().setIntProperty(swp_numberOfCompletedInstances, value);
    }

    public int getLoopCounter()
    {
        return getSemanticObject().getIntProperty(swp_loopCounter);
    }

    public void setLoopCounter(int value)
    {
        getSemanticObject().setIntProperty(swp_loopCounter, value);
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
