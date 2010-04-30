package org.semanticwb.process.model.base;


public abstract class ThrowEventBase extends org.semanticwb.process.model.Event implements org.semanticwb.process.model.Auditable,org.semanticwb.process.model.Documentable,org.semanticwb.process.model.EventDefinible,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.DataInputReferensable,org.semanticwb.process.model.DataInputAssociable,org.semanticwb.process.model.Monitorable,org.semanticwb.process.model.Messageable
{
    public static final org.semanticwb.platform.SemanticClass swp_InputSet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#InputSet");
    public static final org.semanticwb.platform.SemanticProperty swp_inputSet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#inputSet");
    public static final org.semanticwb.platform.SemanticClass swp_ThrowEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ThrowEvent");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ThrowEvent");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.ThrowEvent> listThrowEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ThrowEvent>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ThrowEvent> listThrowEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ThrowEvent>(it, true);
        }

        public static org.semanticwb.process.model.ThrowEvent createThrowEvent(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ThrowEvent.ClassMgr.createThrowEvent(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.ThrowEvent getThrowEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ThrowEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.ThrowEvent createThrowEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ThrowEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeThrowEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasThrowEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getThrowEvent(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ThrowEvent> listThrowEventByEventDefinitionRef(org.semanticwb.process.model.EventDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasEventDefinitionRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ThrowEvent> listThrowEventByEventDefinitionRef(org.semanticwb.process.model.EventDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasEventDefinitionRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ThrowEvent> listThrowEventByAuditing(org.semanticwb.process.model.Auditing value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_auditing, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ThrowEvent> listThrowEventByAuditing(org.semanticwb.process.model.Auditing value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_auditing,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ThrowEvent> listThrowEventByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ThrowEvent> listThrowEventByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ThrowEvent> listThrowEventByInputSet(org.semanticwb.process.model.InputSet value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_inputSet, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ThrowEvent> listThrowEventByInputSet(org.semanticwb.process.model.InputSet value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_inputSet,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ThrowEvent> listThrowEventByIncomingSequenceFlow(org.semanticwb.process.model.SequenceFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasIncomingSequenceFlow, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ThrowEvent> listThrowEventByIncomingSequenceFlow(org.semanticwb.process.model.SequenceFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasIncomingSequenceFlow,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ThrowEvent> listThrowEventByOutgoingSequenceFlow(org.semanticwb.process.model.SequenceFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutgoingSequenceFlow, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ThrowEvent> listThrowEventByOutgoingSequenceFlow(org.semanticwb.process.model.SequenceFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutgoingSequenceFlow,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ThrowEvent> listThrowEventByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ThrowEvent> listThrowEventByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ThrowEvent> listThrowEventByEventDefinition(org.semanticwb.process.model.EventDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasEventDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ThrowEvent> listThrowEventByEventDefinition(org.semanticwb.process.model.EventDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasEventDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ThrowEvent> listThrowEventByMessageRef(org.semanticwb.process.model.Message value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_messageRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ThrowEvent> listThrowEventByMessageRef(org.semanticwb.process.model.Message value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_messageRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ThrowEvent> listThrowEventByDataInputRef(org.semanticwb.process.model.DataInput value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataInputRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ThrowEvent> listThrowEventByDataInputRef(org.semanticwb.process.model.DataInput value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataInputRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ThrowEvent> listThrowEventByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ThrowEvent> listThrowEventByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ThrowEvent> listThrowEventByDataInputAssociation(org.semanticwb.process.model.DataInputAssociation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataInputAssociation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ThrowEvent> listThrowEventByDataInputAssociation(org.semanticwb.process.model.DataInputAssociation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataInputAssociation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ThrowEvent> listThrowEventByMonitoring(org.semanticwb.process.model.Monitoring value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ThrowEvent> listThrowEventByMonitoring(org.semanticwb.process.model.Monitoring value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ThrowEventBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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

    public void setInputSet(org.semanticwb.process.model.InputSet value)
    {
        getSemanticObject().setObjectProperty(swp_inputSet, value.getSemanticObject());
    }

    public void removeInputSet()
    {
        getSemanticObject().removeProperty(swp_inputSet);
    }

    public org.semanticwb.process.model.InputSet getInputSet()
    {
         org.semanticwb.process.model.InputSet ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_inputSet);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.InputSet)obj.createGenericInstance();
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

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> listDataInputRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput>(getSemanticObject().listObjectProperties(swp_hasDataInputRef));
    }

    public boolean hasDataInputRef(org.semanticwb.process.model.DataInput value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasDataInputRef,value.getSemanticObject());
        }
        return ret;
    }

    public void addDataInputRef(org.semanticwb.process.model.DataInput value)
    {
        getSemanticObject().addObjectProperty(swp_hasDataInputRef, value.getSemanticObject());
    }

    public void removeAllDataInputRef()
    {
        getSemanticObject().removeProperty(swp_hasDataInputRef);
    }

    public void removeDataInputRef(org.semanticwb.process.model.DataInput value)
    {
        getSemanticObject().removeObjectProperty(swp_hasDataInputRef,value.getSemanticObject());
    }

    public org.semanticwb.process.model.DataInput getDataInputRef()
    {
         org.semanticwb.process.model.DataInput ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasDataInputRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.DataInput)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInputAssociation> listDataInputAssociations()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInputAssociation>(getSemanticObject().listObjectProperties(swp_hasDataInputAssociation));
    }

    public boolean hasDataInputAssociation(org.semanticwb.process.model.DataInputAssociation value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasDataInputAssociation,value.getSemanticObject());
        }
        return ret;
    }

    public void addDataInputAssociation(org.semanticwb.process.model.DataInputAssociation value)
    {
        getSemanticObject().addObjectProperty(swp_hasDataInputAssociation, value.getSemanticObject());
    }

    public void removeAllDataInputAssociation()
    {
        getSemanticObject().removeProperty(swp_hasDataInputAssociation);
    }

    public void removeDataInputAssociation(org.semanticwb.process.model.DataInputAssociation value)
    {
        getSemanticObject().removeObjectProperty(swp_hasDataInputAssociation,value.getSemanticObject());
    }

    public org.semanticwb.process.model.DataInputAssociation getDataInputAssociation()
    {
         org.semanticwb.process.model.DataInputAssociation ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasDataInputAssociation);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.DataInputAssociation)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
