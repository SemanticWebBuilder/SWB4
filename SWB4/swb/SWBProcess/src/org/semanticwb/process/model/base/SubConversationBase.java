package org.semanticwb.process.model.base;


public abstract class SubConversationBase extends org.semanticwb.process.model.ConversationNode implements org.semanticwb.process.model.Artifactable,org.semanticwb.process.model.MultipleCorrelationKeyReferensable,org.semanticwb.process.model.ParticipantReferensable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Documentable,org.semanticwb.process.model.Conversationable
{
    public static final org.semanticwb.platform.SemanticClass swp_SubConversation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#SubConversation");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#SubConversation");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.SubConversation> listSubConversations(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubConversation>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubConversation> listSubConversations()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubConversation>(it, true);
        }

        public static org.semanticwb.process.model.SubConversation createSubConversation(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.SubConversation.ClassMgr.createSubConversation(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.SubConversation getSubConversation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.SubConversation)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.SubConversation createSubConversation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.SubConversation)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeSubConversation(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasSubConversation(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSubConversation(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubConversation> listSubConversationByParticipant(org.semanticwb.process.model.Participant value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubConversation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasParticipant, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubConversation> listSubConversationByParticipant(org.semanticwb.process.model.Participant value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubConversation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasParticipant,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubConversation> listSubConversationByConversationNode(org.semanticwb.process.model.ConversationNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubConversation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasConversationNode, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubConversation> listSubConversationByConversationNode(org.semanticwb.process.model.ConversationNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubConversation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasConversationNode,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubConversation> listSubConversationByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubConversation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubConversation> listSubConversationByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubConversation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubConversation> listSubConversationByArtifact(org.semanticwb.process.model.Artifact value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubConversation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasArtifact, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubConversation> listSubConversationByArtifact(org.semanticwb.process.model.Artifact value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubConversation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasArtifact,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubConversation> listSubConversationByCorrelationKey(org.semanticwb.process.model.CorrelationKey value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubConversation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasCorrelationKey, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubConversation> listSubConversationByCorrelationKey(org.semanticwb.process.model.CorrelationKey value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubConversation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasCorrelationKey,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubConversation> listSubConversationByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubConversation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubConversation> listSubConversationByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubConversation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubConversation> listSubConversationByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubConversation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubConversation> listSubConversationByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubConversation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public SubConversationBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
}
