package org.semanticwb.process.model.base;


public abstract class ParticipantBase extends org.semanticwb.process.model.BPMNBaseElement implements org.semanticwb.process.model.Messageable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Documentable,org.semanticwb.process.model.ProcessReferensable
{
    public static final org.semanticwb.platform.SemanticClass swp_EndPoint=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#EndPoint");
    public static final org.semanticwb.platform.SemanticProperty swp_hasEndPointRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasEndPointRef");
    public static final org.semanticwb.platform.SemanticClass swp_ParticipantMultiplicity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ParticipantMultiplicity");
    public static final org.semanticwb.platform.SemanticProperty swp_participantMultiplicity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#participantMultiplicity");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessInterface=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ProcessInterface");
    public static final org.semanticwb.platform.SemanticProperty swp_hasInterfaceRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasInterfaceRef");
    public static final org.semanticwb.platform.SemanticClass swp_Participant=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Participant");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Participant");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.Participant> listParticipants(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Participant>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.Participant> listParticipants()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Participant>(it, true);
        }

        public static org.semanticwb.process.model.Participant createParticipant(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.Participant.ClassMgr.createParticipant(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.Participant getParticipant(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Participant)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.Participant createParticipant(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Participant)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeParticipant(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasParticipant(String id, org.semanticwb.model.SWBModel model)
        {
            return (getParticipant(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.Participant> listParticipantByEndPointRef(org.semanticwb.process.model.EndPoint value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Participant> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasEndPointRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Participant> listParticipantByEndPointRef(org.semanticwb.process.model.EndPoint value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Participant> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasEndPointRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Participant> listParticipantByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Participant> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Participant> listParticipantByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Participant> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Participant> listParticipantByProcessRef(org.semanticwb.process.model.Process value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Participant> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_processRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Participant> listParticipantByProcessRef(org.semanticwb.process.model.Process value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Participant> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_processRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Participant> listParticipantByMessageRef(org.semanticwb.process.model.Message value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Participant> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_messageRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Participant> listParticipantByMessageRef(org.semanticwb.process.model.Message value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Participant> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_messageRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Participant> listParticipantByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Participant> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Participant> listParticipantByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Participant> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Participant> listParticipantByParticipantMultiplicity(org.semanticwb.process.model.ParticipantMultiplicity value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Participant> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_participantMultiplicity, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Participant> listParticipantByParticipantMultiplicity(org.semanticwb.process.model.ParticipantMultiplicity value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Participant> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_participantMultiplicity,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Participant> listParticipantByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Participant> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Participant> listParticipantByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Participant> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Participant> listParticipantByInterfaceRef(org.semanticwb.process.model.ProcessInterface value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Participant> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInterfaceRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Participant> listParticipantByInterfaceRef(org.semanticwb.process.model.ProcessInterface value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Participant> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInterfaceRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ParticipantBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EndPoint> listEndPointRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EndPoint>(getSemanticObject().listObjectProperties(swp_hasEndPointRef));
    }

    public boolean hasEndPointRef(org.semanticwb.process.model.EndPoint value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasEndPointRef,value.getSemanticObject());
        }
        return ret;
    }

    public void addEndPointRef(org.semanticwb.process.model.EndPoint value)
    {
        getSemanticObject().addObjectProperty(swp_hasEndPointRef, value.getSemanticObject());
    }

    public void removeAllEndPointRef()
    {
        getSemanticObject().removeProperty(swp_hasEndPointRef);
    }

    public void removeEndPointRef(org.semanticwb.process.model.EndPoint value)
    {
        getSemanticObject().removeObjectProperty(swp_hasEndPointRef,value.getSemanticObject());
    }

    public org.semanticwb.process.model.EndPoint getEndPointRef()
    {
         org.semanticwb.process.model.EndPoint ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasEndPointRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.EndPoint)obj.createGenericInstance();
         }
         return ret;
    }

    public void setProcessRef(org.semanticwb.process.model.Process value)
    {
        getSemanticObject().setObjectProperty(swp_processRef, value.getSemanticObject());
    }

    public void removeProcessRef()
    {
        getSemanticObject().removeProperty(swp_processRef);
    }

    public org.semanticwb.process.model.Process getProcessRef()
    {
         org.semanticwb.process.model.Process ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_processRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Process)obj.createGenericInstance();
         }
         return ret;
    }

    public void setMessageRef(org.semanticwb.process.model.Message value)
    {
        getSemanticObject().setObjectProperty(swp_messageRef, value.getSemanticObject());
    }

    public void removeMessageRef()
    {
        getSemanticObject().removeProperty(swp_messageRef);
    }

    public org.semanticwb.process.model.Message getMessageRef()
    {
         org.semanticwb.process.model.Message ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_messageRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Message)obj.createGenericInstance();
         }
         return ret;
    }

    public void setParticipantMultiplicity(org.semanticwb.process.model.ParticipantMultiplicity value)
    {
        getSemanticObject().setObjectProperty(swp_participantMultiplicity, value.getSemanticObject());
    }

    public void removeParticipantMultiplicity()
    {
        getSemanticObject().removeProperty(swp_participantMultiplicity);
    }

    public org.semanticwb.process.model.ParticipantMultiplicity getParticipantMultiplicity()
    {
         org.semanticwb.process.model.ParticipantMultiplicity ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_participantMultiplicity);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ParticipantMultiplicity)obj.createGenericInstance();
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

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInterface> listInterfaceRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInterface>(getSemanticObject().listObjectProperties(swp_hasInterfaceRef));
    }

    public boolean hasInterfaceRef(org.semanticwb.process.model.ProcessInterface value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasInterfaceRef,value.getSemanticObject());
        }
        return ret;
    }

    public void addInterfaceRef(org.semanticwb.process.model.ProcessInterface value)
    {
        getSemanticObject().addObjectProperty(swp_hasInterfaceRef, value.getSemanticObject());
    }

    public void removeAllInterfaceRef()
    {
        getSemanticObject().removeProperty(swp_hasInterfaceRef);
    }

    public void removeInterfaceRef(org.semanticwb.process.model.ProcessInterface value)
    {
        getSemanticObject().removeObjectProperty(swp_hasInterfaceRef,value.getSemanticObject());
    }

    public org.semanticwb.process.model.ProcessInterface getInterfaceRef()
    {
         org.semanticwb.process.model.ProcessInterface ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasInterfaceRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ProcessInterface)obj.createGenericInstance();
         }
         return ret;
    }
}
