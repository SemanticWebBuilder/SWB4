package org.semanticwb.process.model.base;


public abstract class CollaborationBase extends org.semanticwb.process.model.BPMNBaseElement implements org.semanticwb.process.model.Artifactable,org.semanticwb.process.model.ParticipantAssociable,org.semanticwb.process.model.Interactable,org.semanticwb.process.model.Conversable,org.semanticwb.process.model.ParticipantReferensable,org.semanticwb.process.model.ConversationAssociable,org.semanticwb.process.model.Collapsable,org.semanticwb.process.model.Documentable,org.semanticwb.process.model.MessageFlowAssociable,org.semanticwb.process.model.MessageFlowable,org.semanticwb.process.model.Rootable
{
    public static final org.semanticwb.platform.SemanticClass swp_Choreography=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Choreography");
    public static final org.semanticwb.platform.SemanticProperty swp_choreographyRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#choreographyRef");
    public static final org.semanticwb.platform.SemanticClass swp_Collaboration=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Collaboration");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Collaboration");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.Collaboration> listCollaborations(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Collaboration>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.Collaboration> listCollaborations()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Collaboration>(it, true);
        }

        public static org.semanticwb.process.model.Collaboration createCollaboration(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.Collaboration.ClassMgr.createCollaboration(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.Collaboration getCollaboration(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Collaboration)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.Collaboration createCollaboration(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Collaboration)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeCollaboration(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasCollaboration(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCollaboration(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.Collaboration> listCollaborationByParticipant(org.semanticwb.process.model.Participant value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Collaboration> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasParticipant, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Collaboration> listCollaborationByParticipant(org.semanticwb.process.model.Participant value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Collaboration> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasParticipant,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Collaboration> listCollaborationByParticipantAssociation(org.semanticwb.process.model.ParticipantAssociation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Collaboration> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasParticipantAssociation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Collaboration> listCollaborationByParticipantAssociation(org.semanticwb.process.model.ParticipantAssociation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Collaboration> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasParticipantAssociation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Collaboration> listCollaborationByMessageFlowAssocaition(org.semanticwb.process.model.MessageFlowAssociation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Collaboration> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasMessageFlowAssocaition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Collaboration> listCollaborationByMessageFlowAssocaition(org.semanticwb.process.model.MessageFlowAssociation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Collaboration> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasMessageFlowAssocaition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Collaboration> listCollaborationByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Collaboration> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Collaboration> listCollaborationByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Collaboration> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Collaboration> listCollaborationByMessageFlow(org.semanticwb.process.model.MessageFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Collaboration> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasMessageFlow, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Collaboration> listCollaborationByMessageFlow(org.semanticwb.process.model.MessageFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Collaboration> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasMessageFlow,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Collaboration> listCollaborationByArtifact(org.semanticwb.process.model.Artifact value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Collaboration> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasArtifact, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Collaboration> listCollaborationByArtifact(org.semanticwb.process.model.Artifact value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Collaboration> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasArtifact,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Collaboration> listCollaborationByChoreographyRef(org.semanticwb.process.model.Choreography value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Collaboration> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_choreographyRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Collaboration> listCollaborationByChoreographyRef(org.semanticwb.process.model.Choreography value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Collaboration> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_choreographyRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Collaboration> listCollaborationByConversationAssociation(org.semanticwb.process.model.ConversationAssociation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Collaboration> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasConversationAssociation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Collaboration> listCollaborationByConversationAssociation(org.semanticwb.process.model.ConversationAssociation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Collaboration> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasConversationAssociation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Collaboration> listCollaborationByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Collaboration> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Collaboration> listCollaborationByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Collaboration> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Collaboration> listCollaborationByConversation(org.semanticwb.process.model.Conversation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Collaboration> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasConversation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Collaboration> listCollaborationByConversation(org.semanticwb.process.model.Conversation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Collaboration> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasConversation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Collaboration> listCollaborationByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Collaboration> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Collaboration> listCollaborationByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Collaboration> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public CollaborationBase(org.semanticwb.platform.SemanticObject base)
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

    public void setChoreographyRef(org.semanticwb.process.model.Choreography value)
    {
        getSemanticObject().setObjectProperty(swp_choreographyRef, value.getSemanticObject());
    }

    public void removeChoreographyRef()
    {
        getSemanticObject().removeProperty(swp_choreographyRef);
    }

    public org.semanticwb.process.model.Choreography getChoreographyRef()
    {
         org.semanticwb.process.model.Choreography ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_choreographyRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Choreography)obj.createGenericInstance();
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

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
