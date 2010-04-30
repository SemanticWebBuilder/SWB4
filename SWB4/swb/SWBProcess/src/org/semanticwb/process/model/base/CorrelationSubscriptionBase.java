package org.semanticwb.process.model.base;


public abstract class CorrelationSubscriptionBase extends org.semanticwb.process.model.BPMNBaseElement implements org.semanticwb.process.model.Documentable,org.semanticwb.process.model.CorrelationKeyReferensable
{
    public static final org.semanticwb.platform.SemanticClass swp_CorrelationPropertyBinding=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CorrelationPropertyBinding");
    public static final org.semanticwb.platform.SemanticProperty swp_hasCorrelationPropertyBinding=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasCorrelationPropertyBinding");
    public static final org.semanticwb.platform.SemanticClass swp_Process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Process");
    public static final org.semanticwb.platform.SemanticProperty swp_process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#process");
    public static final org.semanticwb.platform.SemanticClass swp_CorrelationSubscription=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CorrelationSubscription");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CorrelationSubscription");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationSubscription> listCorrelationSubscriptions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationSubscription>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationSubscription> listCorrelationSubscriptions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationSubscription>(it, true);
        }

        public static org.semanticwb.process.model.CorrelationSubscription createCorrelationSubscription(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.CorrelationSubscription.ClassMgr.createCorrelationSubscription(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.CorrelationSubscription getCorrelationSubscription(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CorrelationSubscription)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.CorrelationSubscription createCorrelationSubscription(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CorrelationSubscription)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeCorrelationSubscription(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasCorrelationSubscription(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCorrelationSubscription(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationSubscription> listCorrelationSubscriptionByCorrelationPropertyBinding(org.semanticwb.process.model.CorrelationPropertyBinding value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationSubscription> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasCorrelationPropertyBinding, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationSubscription> listCorrelationSubscriptionByCorrelationPropertyBinding(org.semanticwb.process.model.CorrelationPropertyBinding value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationSubscription> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasCorrelationPropertyBinding,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationSubscription> listCorrelationSubscriptionByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationSubscription> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationSubscription> listCorrelationSubscriptionByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationSubscription> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationSubscription> listCorrelationSubscriptionByProcess(org.semanticwb.process.model.Process value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationSubscription> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_process, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationSubscription> listCorrelationSubscriptionByProcess(org.semanticwb.process.model.Process value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationSubscription> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_process,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationSubscription> listCorrelationSubscriptionByCorrelationKeyRef(org.semanticwb.process.model.CorrelationKey value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationSubscription> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_correlationKeyRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationSubscription> listCorrelationSubscriptionByCorrelationKeyRef(org.semanticwb.process.model.CorrelationKey value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationSubscription> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_correlationKeyRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationSubscription> listCorrelationSubscriptionByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationSubscription> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationSubscription> listCorrelationSubscriptionByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationSubscription> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationSubscription> listCorrelationSubscriptionByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationSubscription> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationSubscription> listCorrelationSubscriptionByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationSubscription> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public CorrelationSubscriptionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationPropertyBinding> listCorrelationPropertyBindings()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationPropertyBinding>(getSemanticObject().listObjectProperties(swp_hasCorrelationPropertyBinding));
    }

    public boolean hasCorrelationPropertyBinding(org.semanticwb.process.model.CorrelationPropertyBinding value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasCorrelationPropertyBinding,value.getSemanticObject());
        }
        return ret;
    }

    public void addCorrelationPropertyBinding(org.semanticwb.process.model.CorrelationPropertyBinding value)
    {
        getSemanticObject().addObjectProperty(swp_hasCorrelationPropertyBinding, value.getSemanticObject());
    }

    public void removeAllCorrelationPropertyBinding()
    {
        getSemanticObject().removeProperty(swp_hasCorrelationPropertyBinding);
    }

    public void removeCorrelationPropertyBinding(org.semanticwb.process.model.CorrelationPropertyBinding value)
    {
        getSemanticObject().removeObjectProperty(swp_hasCorrelationPropertyBinding,value.getSemanticObject());
    }

    public org.semanticwb.process.model.CorrelationPropertyBinding getCorrelationPropertyBinding()
    {
         org.semanticwb.process.model.CorrelationPropertyBinding ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasCorrelationPropertyBinding);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.CorrelationPropertyBinding)obj.createGenericInstance();
         }
         return ret;
    }

    public void setProcess(org.semanticwb.process.model.Process value)
    {
        getSemanticObject().setObjectProperty(swp_process, value.getSemanticObject());
    }

    public void removeProcess()
    {
        getSemanticObject().removeProperty(swp_process);
    }

    public org.semanticwb.process.model.Process getProcess()
    {
         org.semanticwb.process.model.Process ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_process);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Process)obj.createGenericInstance();
         }
         return ret;
    }

    public void setCorrelationKeyRef(org.semanticwb.process.model.CorrelationKey value)
    {
        getSemanticObject().setObjectProperty(swp_correlationKeyRef, value.getSemanticObject());
    }

    public void removeCorrelationKeyRef()
    {
        getSemanticObject().removeProperty(swp_correlationKeyRef);
    }

    public org.semanticwb.process.model.CorrelationKey getCorrelationKeyRef()
    {
         org.semanticwb.process.model.CorrelationKey ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_correlationKeyRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.CorrelationKey)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
