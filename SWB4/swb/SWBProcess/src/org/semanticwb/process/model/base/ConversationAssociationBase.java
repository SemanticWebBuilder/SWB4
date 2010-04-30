package org.semanticwb.process.model.base;


public abstract class ConversationAssociationBase extends org.semanticwb.process.model.BPMNBaseElement implements org.semanticwb.process.model.MessageFlowReferensable,org.semanticwb.process.model.Documentable,org.semanticwb.process.model.CorrelationKeyReferensable
{
    public static final org.semanticwb.platform.SemanticClass swp_Conversation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Conversation");
    public static final org.semanticwb.platform.SemanticProperty swp_conversationRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#conversationRef");
    public static final org.semanticwb.platform.SemanticClass swp_ConversationAssociation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ConversationAssociation");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ConversationAssociation");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.ConversationAssociation> listConversationAssociations(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConversationAssociation>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConversationAssociation> listConversationAssociations()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConversationAssociation>(it, true);
        }

        public static org.semanticwb.process.model.ConversationAssociation createConversationAssociation(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ConversationAssociation.ClassMgr.createConversationAssociation(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.ConversationAssociation getConversationAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ConversationAssociation)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.ConversationAssociation createConversationAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ConversationAssociation)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeConversationAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasConversationAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (getConversationAssociation(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConversationAssociation> listConversationAssociationByMessageFlowRef(org.semanticwb.process.model.MessageFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConversationAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasMessageFlowRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConversationAssociation> listConversationAssociationByMessageFlowRef(org.semanticwb.process.model.MessageFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConversationAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasMessageFlowRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConversationAssociation> listConversationAssociationByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConversationAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConversationAssociation> listConversationAssociationByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConversationAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConversationAssociation> listConversationAssociationByConversationRef(org.semanticwb.process.model.Conversation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConversationAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_conversationRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConversationAssociation> listConversationAssociationByConversationRef(org.semanticwb.process.model.Conversation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConversationAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_conversationRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConversationAssociation> listConversationAssociationByCorrelationKeyRef(org.semanticwb.process.model.CorrelationKey value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConversationAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_correlationKeyRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConversationAssociation> listConversationAssociationByCorrelationKeyRef(org.semanticwb.process.model.CorrelationKey value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConversationAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_correlationKeyRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConversationAssociation> listConversationAssociationByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConversationAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConversationAssociation> listConversationAssociationByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConversationAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConversationAssociation> listConversationAssociationByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConversationAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConversationAssociation> listConversationAssociationByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConversationAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ConversationAssociationBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlow> listMessageFlowRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlow>(getSemanticObject().listObjectProperties(swp_hasMessageFlowRef));
    }

    public boolean hasMessageFlowRef(org.semanticwb.process.model.MessageFlow value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasMessageFlowRef,value.getSemanticObject());
        }
        return ret;
    }

    public void addMessageFlowRef(org.semanticwb.process.model.MessageFlow value)
    {
        getSemanticObject().addObjectProperty(swp_hasMessageFlowRef, value.getSemanticObject());
    }

    public void removeAllMessageFlowRef()
    {
        getSemanticObject().removeProperty(swp_hasMessageFlowRef);
    }

    public void removeMessageFlowRef(org.semanticwb.process.model.MessageFlow value)
    {
        getSemanticObject().removeObjectProperty(swp_hasMessageFlowRef,value.getSemanticObject());
    }

    public org.semanticwb.process.model.MessageFlow getMessageFlowRef()
    {
         org.semanticwb.process.model.MessageFlow ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasMessageFlowRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.MessageFlow)obj.createGenericInstance();
         }
         return ret;
    }

    public void setConversationRef(org.semanticwb.process.model.Conversation value)
    {
        getSemanticObject().setObjectProperty(swp_conversationRef, value.getSemanticObject());
    }

    public void removeConversationRef()
    {
        getSemanticObject().removeProperty(swp_conversationRef);
    }

    public org.semanticwb.process.model.Conversation getConversationRef()
    {
         org.semanticwb.process.model.Conversation ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_conversationRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Conversation)obj.createGenericInstance();
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
