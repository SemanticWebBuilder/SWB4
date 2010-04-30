package org.semanticwb.process.model.base;


public abstract class ChoreographyBase extends org.semanticwb.process.model.BPMNBaseElement implements org.semanticwb.process.model.Artifactable,org.semanticwb.process.model.ParticipantAssociable,org.semanticwb.process.model.FlowElementsContainerable,org.semanticwb.process.model.Interactable,org.semanticwb.process.model.Conversable,org.semanticwb.process.model.ParticipantReferensable,org.semanticwb.process.model.Callable,org.semanticwb.process.model.IOSpecificable,org.semanticwb.process.model.ConversationAssociable,org.semanticwb.process.model.Collapsable,org.semanticwb.process.model.Documentable,org.semanticwb.process.model.MessageFlowAssociable,org.semanticwb.process.model.MessageFlowable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Rootable
{
    public static final org.semanticwb.platform.SemanticClass swp_Choreography=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Choreography");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Choreography");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographies(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographies()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography>(it, true);
        }

        public static org.semanticwb.process.model.Choreography createChoreography(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.Choreography.ClassMgr.createChoreography(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.Choreography getChoreography(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Choreography)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.Choreography createChoreography(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Choreography)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeChoreography(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasChoreography(String id, org.semanticwb.model.SWBModel model)
        {
            return (getChoreography(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographyByParticipant(org.semanticwb.process.model.Participant value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasParticipant, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographyByParticipant(org.semanticwb.process.model.Participant value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasParticipant,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographyByParticipantAssociation(org.semanticwb.process.model.ParticipantAssociation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasParticipantAssociation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographyByParticipantAssociation(org.semanticwb.process.model.ParticipantAssociation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasParticipantAssociation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographyByIOBinding(org.semanticwb.process.model.InputOutputBinding value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasIOBinding, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographyByIOBinding(org.semanticwb.process.model.InputOutputBinding value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasIOBinding,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographyBySupportedInterfaceRef(org.semanticwb.process.model.ProcessInterface value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasSupportedInterfaceRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographyBySupportedInterfaceRef(org.semanticwb.process.model.ProcessInterface value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasSupportedInterfaceRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographyByIoSpecification(org.semanticwb.process.model.InputOutputSpecification value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_ioSpecification, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographyByIoSpecification(org.semanticwb.process.model.InputOutputSpecification value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_ioSpecification,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographyByMessageFlow(org.semanticwb.process.model.MessageFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasMessageFlow, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographyByMessageFlow(org.semanticwb.process.model.MessageFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasMessageFlow,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographyByArtifact(org.semanticwb.process.model.Artifact value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasArtifact, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographyByArtifact(org.semanticwb.process.model.Artifact value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasArtifact,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographyByConversationAssociation(org.semanticwb.process.model.ConversationAssociation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasConversationAssociation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographyByConversationAssociation(org.semanticwb.process.model.ConversationAssociation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasConversationAssociation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographyByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographyByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographyByMessageFlowAssocaition(org.semanticwb.process.model.MessageFlowAssociation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasMessageFlowAssocaition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographyByMessageFlowAssocaition(org.semanticwb.process.model.MessageFlowAssociation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasMessageFlowAssocaition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographyByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographyByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographyByCalledElement(org.semanticwb.process.model.Callable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_calledElement, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographyByCalledElement(org.semanticwb.process.model.Callable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_calledElement,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographyByFlowElement(org.semanticwb.process.model.FlowElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowElement, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographyByFlowElement(org.semanticwb.process.model.FlowElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowElement,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographyByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographyByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographyByConversation(org.semanticwb.process.model.Conversation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasConversation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographyByConversation(org.semanticwb.process.model.Conversation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Choreography> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasConversation,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ChoreographyBase(org.semanticwb.platform.SemanticObject base)
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

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantAssociation> listParticipantAssociations()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantAssociation>(getSemanticObject().listObjectProperties(swp_hasParticipantAssociation));
    }

    public boolean hasParticipantAssociation(org.semanticwb.process.model.ParticipantAssociation value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasParticipantAssociation,value.getSemanticObject());
        }
        return ret;
    }

    public void addParticipantAssociation(org.semanticwb.process.model.ParticipantAssociation value)
    {
        getSemanticObject().addObjectProperty(swp_hasParticipantAssociation, value.getSemanticObject());
    }

    public void removeAllParticipantAssociation()
    {
        getSemanticObject().removeProperty(swp_hasParticipantAssociation);
    }

    public void removeParticipantAssociation(org.semanticwb.process.model.ParticipantAssociation value)
    {
        getSemanticObject().removeObjectProperty(swp_hasParticipantAssociation,value.getSemanticObject());
    }

    public org.semanticwb.process.model.ParticipantAssociation getParticipantAssociation()
    {
         org.semanticwb.process.model.ParticipantAssociation ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasParticipantAssociation);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ParticipantAssociation)obj.createGenericInstance();
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

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConversationAssociation> listConversationAssociations()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConversationAssociation>(getSemanticObject().listObjectProperties(swp_hasConversationAssociation));
    }

    public boolean hasConversationAssociation(org.semanticwb.process.model.ConversationAssociation value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasConversationAssociation,value.getSemanticObject());
        }
        return ret;
    }

    public void addConversationAssociation(org.semanticwb.process.model.ConversationAssociation value)
    {
        getSemanticObject().addObjectProperty(swp_hasConversationAssociation, value.getSemanticObject());
    }

    public void removeAllConversationAssociation()
    {
        getSemanticObject().removeProperty(swp_hasConversationAssociation);
    }

    public void removeConversationAssociation(org.semanticwb.process.model.ConversationAssociation value)
    {
        getSemanticObject().removeObjectProperty(swp_hasConversationAssociation,value.getSemanticObject());
    }

    public org.semanticwb.process.model.ConversationAssociation getConversationAssociation()
    {
         org.semanticwb.process.model.ConversationAssociation ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasConversationAssociation);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ConversationAssociation)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlowAssociation> listMessageFlowAssocaitions()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlowAssociation>(getSemanticObject().listObjectProperties(swp_hasMessageFlowAssocaition));
    }

    public boolean hasMessageFlowAssocaition(org.semanticwb.process.model.MessageFlowAssociation value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasMessageFlowAssocaition,value.getSemanticObject());
        }
        return ret;
    }

    public void addMessageFlowAssocaition(org.semanticwb.process.model.MessageFlowAssociation value)
    {
        getSemanticObject().addObjectProperty(swp_hasMessageFlowAssocaition, value.getSemanticObject());
    }

    public void removeAllMessageFlowAssocaition()
    {
        getSemanticObject().removeProperty(swp_hasMessageFlowAssocaition);
    }

    public void removeMessageFlowAssocaition(org.semanticwb.process.model.MessageFlowAssociation value)
    {
        getSemanticObject().removeObjectProperty(swp_hasMessageFlowAssocaition,value.getSemanticObject());
    }

    public org.semanticwb.process.model.MessageFlowAssociation getMessageFlowAssocaition()
    {
         org.semanticwb.process.model.MessageFlowAssociation ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasMessageFlowAssocaition);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.MessageFlowAssociation)obj.createGenericInstance();
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

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowElement> listFlowElements()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowElement>(getSemanticObject().listObjectProperties(swp_hasFlowElement));
    }

    public boolean hasFlowElement(org.semanticwb.process.model.FlowElement value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasFlowElement,value.getSemanticObject());
        }
        return ret;
    }

    public void addFlowElement(org.semanticwb.process.model.FlowElement value)
    {
        getSemanticObject().addObjectProperty(swp_hasFlowElement, value.getSemanticObject());
    }

    public void removeAllFlowElement()
    {
        getSemanticObject().removeProperty(swp_hasFlowElement);
    }

    public void removeFlowElement(org.semanticwb.process.model.FlowElement value)
    {
        getSemanticObject().removeObjectProperty(swp_hasFlowElement,value.getSemanticObject());
    }

    public org.semanticwb.process.model.FlowElement getFlowElement()
    {
         org.semanticwb.process.model.FlowElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasFlowElement);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FlowElement)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation> listConversations()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Conversation>(getSemanticObject().listObjectProperties(swp_hasConversation));
    }

    public boolean hasConversation(org.semanticwb.process.model.Conversation value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasConversation,value.getSemanticObject());
        }
        return ret;
    }

    public void addConversation(org.semanticwb.process.model.Conversation value)
    {
        getSemanticObject().addObjectProperty(swp_hasConversation, value.getSemanticObject());
    }

    public void removeAllConversation()
    {
        getSemanticObject().removeProperty(swp_hasConversation);
    }

    public void removeConversation(org.semanticwb.process.model.Conversation value)
    {
        getSemanticObject().removeObjectProperty(swp_hasConversation,value.getSemanticObject());
    }

    public org.semanticwb.process.model.Conversation getConversation()
    {
         org.semanticwb.process.model.Conversation ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasConversation);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Conversation)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isCollapsed()
    {
        return getSemanticObject().getBooleanProperty(swp_collapsed);
    }

    public void setCollapsed(boolean value)
    {
        getSemanticObject().setBooleanProperty(swp_collapsed, value);
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

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
