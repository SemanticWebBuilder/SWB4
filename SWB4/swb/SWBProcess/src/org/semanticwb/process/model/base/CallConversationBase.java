package org.semanticwb.process.model.base;


public abstract class CallConversationBase extends org.semanticwb.process.model.ConversationNode implements org.semanticwb.process.model.ParticipantAssociable,org.semanticwb.process.model.Documentable,org.semanticwb.process.model.ParticipantReferensable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.IOSpecificable,org.semanticwb.process.model.Callable,org.semanticwb.process.model.Rootable
{
    public static final org.semanticwb.platform.SemanticClass swp_CallConversation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CallConversation");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CallConversation");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.CallConversation> listCallConversations(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallConversation>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.CallConversation> listCallConversations()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallConversation>(it, true);
        }

        public static org.semanticwb.process.model.CallConversation createCallConversation(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.CallConversation.ClassMgr.createCallConversation(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.CallConversation getCallConversation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CallConversation)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.CallConversation createCallConversation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CallConversation)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeCallConversation(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasCallConversation(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCallConversation(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.CallConversation> listCallConversationByParticipant(org.semanticwb.process.model.Participant value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallConversation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasParticipant, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CallConversation> listCallConversationByParticipant(org.semanticwb.process.model.Participant value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallConversation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasParticipant,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CallConversation> listCallConversationByParticipantAssociation(org.semanticwb.process.model.ParticipantAssociation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallConversation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasParticipantAssociation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CallConversation> listCallConversationByParticipantAssociation(org.semanticwb.process.model.ParticipantAssociation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallConversation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasParticipantAssociation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CallConversation> listCallConversationByIOBinding(org.semanticwb.process.model.InputOutputBinding value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallConversation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasIOBinding, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CallConversation> listCallConversationByIOBinding(org.semanticwb.process.model.InputOutputBinding value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallConversation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasIOBinding,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CallConversation> listCallConversationBySupportedInterfaceRef(org.semanticwb.process.model.ProcessInterface value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallConversation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasSupportedInterfaceRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CallConversation> listCallConversationBySupportedInterfaceRef(org.semanticwb.process.model.ProcessInterface value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallConversation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasSupportedInterfaceRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CallConversation> listCallConversationByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallConversation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CallConversation> listCallConversationByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallConversation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CallConversation> listCallConversationByCalledElement(org.semanticwb.process.model.Callable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallConversation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_calledElement, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CallConversation> listCallConversationByCalledElement(org.semanticwb.process.model.Callable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallConversation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_calledElement,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CallConversation> listCallConversationByIoSpecification(org.semanticwb.process.model.InputOutputSpecification value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallConversation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_ioSpecification, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CallConversation> listCallConversationByIoSpecification(org.semanticwb.process.model.InputOutputSpecification value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallConversation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_ioSpecification,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CallConversation> listCallConversationByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallConversation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CallConversation> listCallConversationByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallConversation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CallConversation> listCallConversationByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallConversation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CallConversation> listCallConversationByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallConversation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public CallConversationBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
