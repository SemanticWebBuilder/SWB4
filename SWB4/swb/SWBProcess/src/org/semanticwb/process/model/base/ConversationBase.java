package org.semanticwb.process.model.base;


public abstract class ConversationBase extends org.semanticwb.model.base.GenericObjectBase implements org.semanticwb.process.model.Artifactable,org.semanticwb.process.model.MessageFlowable,org.semanticwb.process.model.Documentable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Callable,org.semanticwb.process.model.Interactable,org.semanticwb.process.model.MultipleCorrelationKeyReferensable,org.semanticwb.process.model.ParticipantReferensable,org.semanticwb.process.model.Conversationable,org.semanticwb.process.model.Rootable,org.semanticwb.process.model.MessageFlowReferensable,org.semanticwb.process.model.IOSpecificable
{
    public static final org.semanticwb.platform.SemanticClass swp_Conversation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Conversation");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Conversation");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.Conversation> listConversations(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.Conversation> listConversations()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation>(it, true);
        }

        public static org.semanticwb.process.model.Conversation createConversation(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.Conversation.ClassMgr.createConversation(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.Conversation getConversation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Conversation)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.Conversation createConversation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Conversation)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeConversation(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasConversation(String id, org.semanticwb.model.SWBModel model)
        {
            return (getConversation(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.Conversation> listConversationByParticipant(org.semanticwb.process.model.Participant value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasParticipant, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Conversation> listConversationByParticipant(org.semanticwb.process.model.Participant value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasParticipant,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Conversation> listConversationByIOBinding(org.semanticwb.process.model.InputOutputBinding value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasIOBinding, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Conversation> listConversationByIOBinding(org.semanticwb.process.model.InputOutputBinding value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasIOBinding,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Conversation> listConversationBySupportedInterfaceRef(org.semanticwb.process.model.ProcessInterface value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasSupportedInterfaceRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Conversation> listConversationBySupportedInterfaceRef(org.semanticwb.process.model.ProcessInterface value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasSupportedInterfaceRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Conversation> listConversationByConversationNode(org.semanticwb.process.model.ConversationNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasConversationNode, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Conversation> listConversationByConversationNode(org.semanticwb.process.model.ConversationNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasConversationNode,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Conversation> listConversationByMessageFlowRef(org.semanticwb.process.model.MessageFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasMessageFlowRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Conversation> listConversationByMessageFlowRef(org.semanticwb.process.model.MessageFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasMessageFlowRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Conversation> listConversationByIoSpecification(org.semanticwb.process.model.InputOutputSpecification value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_ioSpecification, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Conversation> listConversationByIoSpecification(org.semanticwb.process.model.InputOutputSpecification value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_ioSpecification,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Conversation> listConversationByMessageFlow(org.semanticwb.process.model.MessageFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasMessageFlow, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Conversation> listConversationByMessageFlow(org.semanticwb.process.model.MessageFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasMessageFlow,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Conversation> listConversationByArtifact(org.semanticwb.process.model.Artifact value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasArtifact, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Conversation> listConversationByArtifact(org.semanticwb.process.model.Artifact value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasArtifact,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Conversation> listConversationByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Conversation> listConversationByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Conversation> listConversationByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Conversation> listConversationByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Conversation> listConversationByCalledElement(org.semanticwb.process.model.Callable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_calledElement, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Conversation> listConversationByCalledElement(org.semanticwb.process.model.Callable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_calledElement,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Conversation> listConversationByCorrelationKey(org.semanticwb.process.model.CorrelationKey value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasCorrelationKey, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Conversation> listConversationByCorrelationKey(org.semanticwb.process.model.CorrelationKey value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasCorrelationKey,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Conversation> listConversationByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Conversation> listConversationByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ConversationBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Participant> listParticipants()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Participant>(getSemanticObject().listObjectProperties(swp_hasParticipant));
    }

    public boolean hasParticipant(org.semanticwb.process.model.Participant value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasParticipant,value.getSemanticObject());
        }
        return ret;
    }

    public void addParticipant(org.semanticwb.process.model.Participant value)
    {
        getSemanticObject().addObjectProperty(swp_hasParticipant, value.getSemanticObject());
    }

    public void removeAllParticipant()
    {
        getSemanticObject().removeProperty(swp_hasParticipant);
    }

    public void removeParticipant(org.semanticwb.process.model.Participant value)
    {
        getSemanticObject().removeObjectProperty(swp_hasParticipant,value.getSemanticObject());
    }

    public org.semanticwb.process.model.Participant getParticipant()
    {
         org.semanticwb.process.model.Participant ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasParticipant);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Participant)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputOutputBinding> listIOBindings()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputOutputBinding>(getSemanticObject().listObjectProperties(swp_hasIOBinding));
    }

    public boolean hasIOBinding(org.semanticwb.process.model.InputOutputBinding value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasIOBinding,value.getSemanticObject());
        }
        return ret;
    }

    public void addIOBinding(org.semanticwb.process.model.InputOutputBinding value)
    {
        getSemanticObject().addObjectProperty(swp_hasIOBinding, value.getSemanticObject());
    }

    public void removeAllIOBinding()
    {
        getSemanticObject().removeProperty(swp_hasIOBinding);
    }

    public void removeIOBinding(org.semanticwb.process.model.InputOutputBinding value)
    {
        getSemanticObject().removeObjectProperty(swp_hasIOBinding,value.getSemanticObject());
    }

    public org.semanticwb.process.model.InputOutputBinding getIOBinding()
    {
         org.semanticwb.process.model.InputOutputBinding ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasIOBinding);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.InputOutputBinding)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInterface> listSupportedInterfaceRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInterface>(getSemanticObject().listObjectProperties(swp_hasSupportedInterfaceRef));
    }

    public boolean hasSupportedInterfaceRef(org.semanticwb.process.model.ProcessInterface value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasSupportedInterfaceRef,value.getSemanticObject());
        }
        return ret;
    }

    public void addSupportedInterfaceRef(org.semanticwb.process.model.ProcessInterface value)
    {
        getSemanticObject().addObjectProperty(swp_hasSupportedInterfaceRef, value.getSemanticObject());
    }

    public void removeAllSupportedInterfaceRef()
    {
        getSemanticObject().removeProperty(swp_hasSupportedInterfaceRef);
    }

    public void removeSupportedInterfaceRef(org.semanticwb.process.model.ProcessInterface value)
    {
        getSemanticObject().removeObjectProperty(swp_hasSupportedInterfaceRef,value.getSemanticObject());
    }

    public org.semanticwb.process.model.ProcessInterface getSupportedInterfaceRef()
    {
         org.semanticwb.process.model.ProcessInterface ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasSupportedInterfaceRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ProcessInterface)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConversationNode> listConversationNodes()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConversationNode>(getSemanticObject().listObjectProperties(swp_hasConversationNode));
    }

    public boolean hasConversationNode(org.semanticwb.process.model.ConversationNode value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasConversationNode,value.getSemanticObject());
        }
        return ret;
    }

    public void addConversationNode(org.semanticwb.process.model.ConversationNode value)
    {
        getSemanticObject().addObjectProperty(swp_hasConversationNode, value.getSemanticObject());
    }

    public void removeAllConversationNode()
    {
        getSemanticObject().removeProperty(swp_hasConversationNode);
    }

    public void removeConversationNode(org.semanticwb.process.model.ConversationNode value)
    {
        getSemanticObject().removeObjectProperty(swp_hasConversationNode,value.getSemanticObject());
    }

    public org.semanticwb.process.model.ConversationNode getConversationNode()
    {
         org.semanticwb.process.model.ConversationNode ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasConversationNode);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ConversationNode)obj.createGenericInstance();
         }
         return ret;
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

    public void setIoSpecification(org.semanticwb.process.model.InputOutputSpecification value)
    {
        getSemanticObject().setObjectProperty(swp_ioSpecification, value.getSemanticObject());
    }

    public void removeIoSpecification()
    {
        getSemanticObject().removeProperty(swp_ioSpecification);
    }

    public org.semanticwb.process.model.InputOutputSpecification getIoSpecification()
    {
         org.semanticwb.process.model.InputOutputSpecification ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_ioSpecification);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.InputOutputSpecification)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlow> listMessageFlows()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlow>(getSemanticObject().listObjectProperties(swp_hasMessageFlow));
    }

    public boolean hasMessageFlow(org.semanticwb.process.model.MessageFlow value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasMessageFlow,value.getSemanticObject());
        }
        return ret;
    }

    public void addMessageFlow(org.semanticwb.process.model.MessageFlow value)
    {
        getSemanticObject().addObjectProperty(swp_hasMessageFlow, value.getSemanticObject());
    }

    public void removeAllMessageFlow()
    {
        getSemanticObject().removeProperty(swp_hasMessageFlow);
    }

    public void removeMessageFlow(org.semanticwb.process.model.MessageFlow value)
    {
        getSemanticObject().removeObjectProperty(swp_hasMessageFlow,value.getSemanticObject());
    }

    public org.semanticwb.process.model.MessageFlow getMessageFlow()
    {
         org.semanticwb.process.model.MessageFlow ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasMessageFlow);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.MessageFlow)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Artifact> listArtifacts()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Artifact>(getSemanticObject().listObjectProperties(swp_hasArtifact));
    }

    public boolean hasArtifact(org.semanticwb.process.model.Artifact value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasArtifact,value.getSemanticObject());
        }
        return ret;
    }

    public void addArtifact(org.semanticwb.process.model.Artifact value)
    {
        getSemanticObject().addObjectProperty(swp_hasArtifact, value.getSemanticObject());
    }

    public void removeAllArtifact()
    {
        getSemanticObject().removeProperty(swp_hasArtifact);
    }

    public void removeArtifact(org.semanticwb.process.model.Artifact value)
    {
        getSemanticObject().removeObjectProperty(swp_hasArtifact,value.getSemanticObject());
    }

    public org.semanticwb.process.model.Artifact getArtifact()
    {
         org.semanticwb.process.model.Artifact ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasArtifact);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Artifact)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isValid()
    {
        //Override this method in Conversation object
        return getSemanticObject().getBooleanProperty(swb_valid,false);
    }

    public void setValid(boolean value)
    {
        //Override this method in Conversation object
        getSemanticObject().setBooleanProperty(swb_valid, value,false);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExtensionAttributeValue> listExtensionValues()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExtensionAttributeValue>(getSemanticObject().listObjectProperties(swp_hasExtensionValue));
    }

    public boolean hasExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasExtensionValue,value.getSemanticObject());
        }
        return ret;
    }

    public void addExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
    {
        getSemanticObject().addObjectProperty(swp_hasExtensionValue, value.getSemanticObject());
    }

    public void removeAllExtensionValue()
    {
        getSemanticObject().removeProperty(swp_hasExtensionValue);
    }

    public void removeExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
    {
        getSemanticObject().removeObjectProperty(swp_hasExtensionValue,value.getSemanticObject());
    }

    public org.semanticwb.process.model.ExtensionAttributeValue getExtensionValue()
    {
         org.semanticwb.process.model.ExtensionAttributeValue ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasExtensionValue);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ExtensionAttributeValue)obj.createGenericInstance();
         }
         return ret;
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Documentation> listDocumentations()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Documentation>(getSemanticObject().listObjectProperties(swp_hasDocumentation));
    }

    public boolean hasDocumentation(org.semanticwb.process.model.Documentation value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasDocumentation,value.getSemanticObject());
        }
        return ret;
    }

    public void addDocumentation(org.semanticwb.process.model.Documentation value)
    {
        getSemanticObject().addObjectProperty(swp_hasDocumentation, value.getSemanticObject());
    }

    public void removeAllDocumentation()
    {
        getSemanticObject().removeProperty(swp_hasDocumentation);
    }

    public void removeDocumentation(org.semanticwb.process.model.Documentation value)
    {
        getSemanticObject().removeObjectProperty(swp_hasDocumentation,value.getSemanticObject());
    }

    public org.semanticwb.process.model.Documentation getDocumentation()
    {
         org.semanticwb.process.model.Documentation ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasDocumentation);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Documentation)obj.createGenericInstance();
         }
         return ret;
    }

    public void setCalledElement(org.semanticwb.process.model.Callable value)
    {
        getSemanticObject().setObjectProperty(swp_calledElement, value.getSemanticObject());
    }

    public void removeCalledElement()
    {
        getSemanticObject().removeProperty(swp_calledElement);
    }

    public org.semanticwb.process.model.Callable getCalledElement()
    {
         org.semanticwb.process.model.Callable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_calledElement);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Callable)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationKey> listCorrelationKeys()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationKey>(getSemanticObject().listObjectProperties(swp_hasCorrelationKey));
    }

    public boolean hasCorrelationKey(org.semanticwb.process.model.CorrelationKey value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasCorrelationKey,value.getSemanticObject());
        }
        return ret;
    }

    public void addCorrelationKey(org.semanticwb.process.model.CorrelationKey value)
    {
        getSemanticObject().addObjectProperty(swp_hasCorrelationKey, value.getSemanticObject());
    }

    public void removeAllCorrelationKey()
    {
        getSemanticObject().removeProperty(swp_hasCorrelationKey);
    }

    public void removeCorrelationKey(org.semanticwb.process.model.CorrelationKey value)
    {
        getSemanticObject().removeObjectProperty(swp_hasCorrelationKey,value.getSemanticObject());
    }

    public org.semanticwb.process.model.CorrelationKey getCorrelationKey()
    {
         org.semanticwb.process.model.CorrelationKey ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasCorrelationKey);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.CorrelationKey)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExtensionDefinition> listExtensionDefinitions()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExtensionDefinition>(getSemanticObject().listObjectProperties(swp_hasExtensionDefinition));
    }

    public boolean hasExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasExtensionDefinition,value.getSemanticObject());
        }
        return ret;
    }

    public void addExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
    {
        getSemanticObject().addObjectProperty(swp_hasExtensionDefinition, value.getSemanticObject());
    }

    public void removeAllExtensionDefinition()
    {
        getSemanticObject().removeProperty(swp_hasExtensionDefinition);
    }

    public void removeExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
    {
        getSemanticObject().removeObjectProperty(swp_hasExtensionDefinition,value.getSemanticObject());
    }

    public org.semanticwb.process.model.ExtensionDefinition getExtensionDefinition()
    {
         org.semanticwb.process.model.ExtensionDefinition ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasExtensionDefinition);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ExtensionDefinition)obj.createGenericInstance();
         }
         return ret;
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);
    }
}
