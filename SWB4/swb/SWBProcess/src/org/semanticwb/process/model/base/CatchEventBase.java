package org.semanticwb.process.model.base;


public abstract class CatchEventBase extends org.semanticwb.process.model.Event implements org.semanticwb.process.model.DataOutputReferensable,org.semanticwb.process.model.Auditable,org.semanticwb.process.model.EventDefinible,org.semanticwb.process.model.Documentable,org.semanticwb.process.model.DataOutputAssociable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Monitorable,org.semanticwb.process.model.Messageable
{
    public static final org.semanticwb.platform.SemanticClass swp_OutputSet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#OutputSet");
    public static final org.semanticwb.platform.SemanticProperty swp_outputSet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#outputSet");
    public static final org.semanticwb.platform.SemanticClass swp_CatchEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CatchEvent");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CatchEvent");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.CatchEvent> listCatchEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CatchEvent>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.CatchEvent> listCatchEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CatchEvent>(it, true);
        }

        public static org.semanticwb.process.model.CatchEvent createCatchEvent(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.CatchEvent.ClassMgr.createCatchEvent(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.CatchEvent getCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CatchEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.CatchEvent createCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CatchEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCatchEvent(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.CatchEvent> listCatchEventByDataOutputRef(org.semanticwb.process.model.DataOutput value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataOutputRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CatchEvent> listCatchEventByDataOutputRef(org.semanticwb.process.model.DataOutput value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataOutputRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CatchEvent> listCatchEventByEventDefinitionRef(org.semanticwb.process.model.EventDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasEventDefinitionRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CatchEvent> listCatchEventByEventDefinitionRef(org.semanticwb.process.model.EventDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasEventDefinitionRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CatchEvent> listCatchEventByAuditing(org.semanticwb.process.model.Auditing value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_auditing, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CatchEvent> listCatchEventByAuditing(org.semanticwb.process.model.Auditing value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_auditing,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CatchEvent> listCatchEventByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CatchEvent> listCatchEventByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CatchEvent> listCatchEventByIncomingSequenceFlow(org.semanticwb.process.model.SequenceFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasIncomingSequenceFlow, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CatchEvent> listCatchEventByIncomingSequenceFlow(org.semanticwb.process.model.SequenceFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasIncomingSequenceFlow,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CatchEvent> listCatchEventByOutgoingSequenceFlow(org.semanticwb.process.model.SequenceFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutgoingSequenceFlow, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CatchEvent> listCatchEventByOutgoingSequenceFlow(org.semanticwb.process.model.SequenceFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutgoingSequenceFlow,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CatchEvent> listCatchEventByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CatchEvent> listCatchEventByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CatchEvent> listCatchEventByEventDefinition(org.semanticwb.process.model.EventDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasEventDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CatchEvent> listCatchEventByEventDefinition(org.semanticwb.process.model.EventDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasEventDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CatchEvent> listCatchEventByDataOutputAssociation(org.semanticwb.process.model.DataOutputAssociation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataOutputAssociation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CatchEvent> listCatchEventByDataOutputAssociation(org.semanticwb.process.model.DataOutputAssociation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataOutputAssociation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CatchEvent> listCatchEventByMessageRef(org.semanticwb.process.model.Message value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_messageRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CatchEvent> listCatchEventByMessageRef(org.semanticwb.process.model.Message value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_messageRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CatchEvent> listCatchEventByOutputSet(org.semanticwb.process.model.OutputSet value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_outputSet, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CatchEvent> listCatchEventByOutputSet(org.semanticwb.process.model.OutputSet value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_outputSet,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CatchEvent> listCatchEventByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CatchEvent> listCatchEventByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CatchEvent> listCatchEventByMonitoring(org.semanticwb.process.model.Monitoring value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CatchEvent> listCatchEventByMonitoring(org.semanticwb.process.model.Monitoring value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public CatchEventBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> listDataOutputRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput>(getSemanticObject().listObjectProperties(swp_hasDataOutputRef));
    }

    public boolean hasDataOutputRef(org.semanticwb.process.model.DataOutput value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasDataOutputRef,value.getSemanticObject());
        }
        return ret;
    }

    public void addDataOutputRef(org.semanticwb.process.model.DataOutput value)
    {
        getSemanticObject().addObjectProperty(swp_hasDataOutputRef, value.getSemanticObject());
    }

    public void removeAllDataOutputRef()
    {
        getSemanticObject().removeProperty(swp_hasDataOutputRef);
    }

    public void removeDataOutputRef(org.semanticwb.process.model.DataOutput value)
    {
        getSemanticObject().removeObjectProperty(swp_hasDataOutputRef,value.getSemanticObject());
    }

    public org.semanticwb.process.model.DataOutput getDataOutputRef()
    {
         org.semanticwb.process.model.DataOutput ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasDataOutputRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.DataOutput)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventDefinition> listEventDefinitionRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventDefinition>(getSemanticObject().listObjectProperties(swp_hasEventDefinitionRef));
    }

    public boolean hasEventDefinitionRef(org.semanticwb.process.model.EventDefinition value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasEventDefinitionRef,value.getSemanticObject());
        }
        return ret;
    }

    public void addEventDefinitionRef(org.semanticwb.process.model.EventDefinition value)
    {
        getSemanticObject().addObjectProperty(swp_hasEventDefinitionRef, value.getSemanticObject());
    }

    public void removeAllEventDefinitionRef()
    {
        getSemanticObject().removeProperty(swp_hasEventDefinitionRef);
    }

    public void removeEventDefinitionRef(org.semanticwb.process.model.EventDefinition value)
    {
        getSemanticObject().removeObjectProperty(swp_hasEventDefinitionRef,value.getSemanticObject());
    }

    public org.semanticwb.process.model.EventDefinition getEventDefinitionRef()
    {
         org.semanticwb.process.model.EventDefinition ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasEventDefinitionRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.EventDefinition)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventDefinition> listEventDefinitions()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventDefinition>(getSemanticObject().listObjectProperties(swp_hasEventDefinition));
    }

    public boolean hasEventDefinition(org.semanticwb.process.model.EventDefinition value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasEventDefinition,value.getSemanticObject());
        }
        return ret;
    }

    public void addEventDefinition(org.semanticwb.process.model.EventDefinition value)
    {
        getSemanticObject().addObjectProperty(swp_hasEventDefinition, value.getSemanticObject());
    }

    public void removeAllEventDefinition()
    {
        getSemanticObject().removeProperty(swp_hasEventDefinition);
    }

    public void removeEventDefinition(org.semanticwb.process.model.EventDefinition value)
    {
        getSemanticObject().removeObjectProperty(swp_hasEventDefinition,value.getSemanticObject());
    }

    public org.semanticwb.process.model.EventDefinition getEventDefinition()
    {
         org.semanticwb.process.model.EventDefinition ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasEventDefinition);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.EventDefinition)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutputAssociation> listDataOutputAssociations()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutputAssociation>(getSemanticObject().listObjectProperties(swp_hasDataOutputAssociation));
    }

    public boolean hasDataOutputAssociation(org.semanticwb.process.model.DataOutputAssociation value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasDataOutputAssociation,value.getSemanticObject());
        }
        return ret;
    }

    public void addDataOutputAssociation(org.semanticwb.process.model.DataOutputAssociation value)
    {
        getSemanticObject().addObjectProperty(swp_hasDataOutputAssociation, value.getSemanticObject());
    }

    public void removeAllDataOutputAssociation()
    {
        getSemanticObject().removeProperty(swp_hasDataOutputAssociation);
    }

    public void removeDataOutputAssociation(org.semanticwb.process.model.DataOutputAssociation value)
    {
        getSemanticObject().removeObjectProperty(swp_hasDataOutputAssociation,value.getSemanticObject());
    }

    public org.semanticwb.process.model.DataOutputAssociation getDataOutputAssociation()
    {
         org.semanticwb.process.model.DataOutputAssociation ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasDataOutputAssociation);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.DataOutputAssociation)obj.createGenericInstance();
         }
         return ret;
    }

    public void setOutputSet(org.semanticwb.process.model.OutputSet value)
    {
        getSemanticObject().setObjectProperty(swp_outputSet, value.getSemanticObject());
    }

    public void removeOutputSet()
    {
        getSemanticObject().removeProperty(swp_outputSet);
    }

    public org.semanticwb.process.model.OutputSet getOutputSet()
    {
         org.semanticwb.process.model.OutputSet ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_outputSet);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.OutputSet)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
