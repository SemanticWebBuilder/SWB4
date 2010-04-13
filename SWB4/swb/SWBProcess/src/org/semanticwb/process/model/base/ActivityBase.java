package org.semanticwb.process.model.base;


public abstract class ActivityBase extends org.semanticwb.process.model.FlowNode implements org.semanticwb.process.model.Auditable,org.semanticwb.process.model.DefaultFlowable,org.semanticwb.process.model.DataOutputAssociable,org.semanticwb.process.model.Monitorable,org.semanticwb.process.model.Documentable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Propertyable,org.semanticwb.process.model.Messageable,org.semanticwb.process.model.DataInputAssociable,org.semanticwb.process.model.IOSpecificable
{
    public static final org.semanticwb.platform.SemanticProperty swp_startQuantity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#startQuantity");
    public static final org.semanticwb.platform.SemanticClass swp_ActivityResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ActivityResource");
    public static final org.semanticwb.platform.SemanticProperty swp_hasResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasResource");
    public static final org.semanticwb.platform.SemanticClass swp_BoundaryEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BoundaryEvent");
    public static final org.semanticwb.platform.SemanticProperty swp_hasBoundaryEventRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasBoundaryEventRef");
    public static final org.semanticwb.platform.SemanticProperty swp_compensation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#compensation");
    public static final org.semanticwb.platform.SemanticClass swp_LoopCharacteristics=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#LoopCharacteristics");
    public static final org.semanticwb.platform.SemanticProperty swp_loopCharacteristics=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#loopCharacteristics");
    public static final org.semanticwb.platform.SemanticProperty swp_completionQuantity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#completionQuantity");
    public static final org.semanticwb.platform.SemanticClass swp_Activity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Activity");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Activity");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivities(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivities()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity>(it, true);
        }

        public static org.semanticwb.process.model.Activity createActivity(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.Activity.ClassMgr.createActivity(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.Activity getActivity(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Activity)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.Activity createActivity(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Activity)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeActivity(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasActivity(String id, org.semanticwb.model.SWBModel model)
        {
            return (getActivity(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByIoSpecification(org.semanticwb.process.model.InputOutputSpecification value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_ioSpecification, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByIoSpecification(org.semanticwb.process.model.InputOutputSpecification value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_ioSpecification,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByAuditing(org.semanticwb.process.model.Auditing value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_auditing, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByAuditing(org.semanticwb.process.model.Auditing value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_auditing,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByResource(org.semanticwb.process.model.ActivityResource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasResource, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByResource(org.semanticwb.process.model.ActivityResource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasResource,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByBoundaryEventRef(org.semanticwb.process.model.BoundaryEvent value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasBoundaryEventRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByBoundaryEventRef(org.semanticwb.process.model.BoundaryEvent value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasBoundaryEventRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByProperty(org.semanticwb.process.model.Property value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasProperty, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByProperty(org.semanticwb.process.model.Property value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasProperty,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByIncomingSequenceFlow(org.semanticwb.process.model.SequenceFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasIncomingSequenceFlow, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByIncomingSequenceFlow(org.semanticwb.process.model.SequenceFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasIncomingSequenceFlow,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByOutgoingSequenceFlow(org.semanticwb.process.model.SequenceFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutgoingSequenceFlow, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByOutgoingSequenceFlow(org.semanticwb.process.model.SequenceFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutgoingSequenceFlow,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByLoopCharacteristics(org.semanticwb.process.model.LoopCharacteristics value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_loopCharacteristics, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByLoopCharacteristics(org.semanticwb.process.model.LoopCharacteristics value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_loopCharacteristics,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByDataOutputAssociation(org.semanticwb.process.model.DataOutputAssociation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataOutputAssociation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByDataOutputAssociation(org.semanticwb.process.model.DataOutputAssociation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataOutputAssociation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByDefaultSequenceFlow(org.semanticwb.process.model.SequenceFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_defaultSequenceFlow, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByDefaultSequenceFlow(org.semanticwb.process.model.SequenceFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_defaultSequenceFlow,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByMessageRef(org.semanticwb.process.model.Message value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_messageRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByMessageRef(org.semanticwb.process.model.Message value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_messageRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByDataInputAssociation(org.semanticwb.process.model.DataInputAssociation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataInputAssociation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByDataInputAssociation(org.semanticwb.process.model.DataInputAssociation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataInputAssociation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByMonitoring(org.semanticwb.process.model.Monitoring value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByMonitoring(org.semanticwb.process.model.Monitoring value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ActivityBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public int getStartQuantity()
    {
        return getSemanticObject().getIntProperty(swp_startQuantity);
    }

    public void setStartQuantity(int value)
    {
        getSemanticObject().setIntProperty(swp_startQuantity, value);
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

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ActivityResource> listResources()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ActivityResource>(getSemanticObject().listObjectProperties(swp_hasResource));
    }

    public boolean hasResource(org.semanticwb.process.model.ActivityResource value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasResource,value.getSemanticObject());
        }
        return ret;
    }

    public void addResource(org.semanticwb.process.model.ActivityResource value)
    {
        getSemanticObject().addObjectProperty(swp_hasResource, value.getSemanticObject());
    }

    public void removeAllResource()
    {
        getSemanticObject().removeProperty(swp_hasResource);
    }

    public void removeResource(org.semanticwb.process.model.ActivityResource value)
    {
        getSemanticObject().removeObjectProperty(swp_hasResource,value.getSemanticObject());
    }

    public org.semanticwb.process.model.ActivityResource getResource()
    {
         org.semanticwb.process.model.ActivityResource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasResource);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ActivityResource)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BoundaryEvent> listBoundaryEventRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BoundaryEvent>(getSemanticObject().listObjectProperties(swp_hasBoundaryEventRef));
    }

    public boolean hasBoundaryEventRef(org.semanticwb.process.model.BoundaryEvent value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasBoundaryEventRef,value.getSemanticObject());
        }
        return ret;
    }

    public void addBoundaryEventRef(org.semanticwb.process.model.BoundaryEvent value)
    {
        getSemanticObject().addObjectProperty(swp_hasBoundaryEventRef, value.getSemanticObject());
    }

    public void removeAllBoundaryEventRef()
    {
        getSemanticObject().removeProperty(swp_hasBoundaryEventRef);
    }

    public void removeBoundaryEventRef(org.semanticwb.process.model.BoundaryEvent value)
    {
        getSemanticObject().removeObjectProperty(swp_hasBoundaryEventRef,value.getSemanticObject());
    }

    public org.semanticwb.process.model.BoundaryEvent getBoundaryEventRef()
    {
         org.semanticwb.process.model.BoundaryEvent ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasBoundaryEventRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.BoundaryEvent)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Property> listProperties()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Property>(getSemanticObject().listObjectProperties(swp_hasProperty));
    }

    public boolean hasProperty(org.semanticwb.process.model.Property value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasProperty,value.getSemanticObject());
        }
        return ret;
    }

    public void addProperty(org.semanticwb.process.model.Property value)
    {
        getSemanticObject().addObjectProperty(swp_hasProperty, value.getSemanticObject());
    }

    public void removeAllProperty()
    {
        getSemanticObject().removeProperty(swp_hasProperty);
    }

    public void removeProperty(org.semanticwb.process.model.Property value)
    {
        getSemanticObject().removeObjectProperty(swp_hasProperty,value.getSemanticObject());
    }

    public org.semanticwb.process.model.Property getProperty()
    {
         org.semanticwb.process.model.Property ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasProperty);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Property)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isCompensation()
    {
        return getSemanticObject().getBooleanProperty(swp_compensation);
    }

    public void setCompensation(boolean value)
    {
        getSemanticObject().setBooleanProperty(swp_compensation, value);
    }

    public void setLoopCharacteristics(org.semanticwb.process.model.LoopCharacteristics value)
    {
        getSemanticObject().setObjectProperty(swp_loopCharacteristics, value.getSemanticObject());
    }

    public void removeLoopCharacteristics()
    {
        getSemanticObject().removeProperty(swp_loopCharacteristics);
    }

    public org.semanticwb.process.model.LoopCharacteristics getLoopCharacteristics()
    {
         org.semanticwb.process.model.LoopCharacteristics ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_loopCharacteristics);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.LoopCharacteristics)obj.createGenericInstance();
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

    public void setDefaultSequenceFlow(org.semanticwb.process.model.SequenceFlow value)
    {
        getSemanticObject().setObjectProperty(swp_defaultSequenceFlow, value.getSemanticObject());
    }

    public void removeDefaultSequenceFlow()
    {
        getSemanticObject().removeProperty(swp_defaultSequenceFlow);
    }

    public org.semanticwb.process.model.SequenceFlow getDefaultSequenceFlow()
    {
         org.semanticwb.process.model.SequenceFlow ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_defaultSequenceFlow);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.SequenceFlow)obj.createGenericInstance();
         }
         return ret;
    }

    public int getCompletionQuantity()
    {
        return getSemanticObject().getIntProperty(swp_completionQuantity);
    }

    public void setCompletionQuantity(int value)
    {
        getSemanticObject().setIntProperty(swp_completionQuantity, value);
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
}
