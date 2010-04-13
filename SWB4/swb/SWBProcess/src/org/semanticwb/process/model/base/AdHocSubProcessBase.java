package org.semanticwb.process.model.base;


public abstract class AdHocSubProcessBase extends org.semanticwb.process.model.SubProcess implements org.semanticwb.process.model.Auditable,org.semanticwb.process.model.Artifactable,org.semanticwb.process.model.DefaultFlowable,org.semanticwb.process.model.DataOutputAssociable,org.semanticwb.process.model.Monitorable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Documentable,org.semanticwb.process.model.Propertyable,org.semanticwb.process.model.DataInputAssociable,org.semanticwb.process.model.Messageable,org.semanticwb.process.model.FlowElementsContainerable,org.semanticwb.process.model.IOSpecificable
{
    public static final org.semanticwb.platform.SemanticClass swp_Expression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Expression");
    public static final org.semanticwb.platform.SemanticProperty swp_completionCondition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#completionCondition");
    public static final org.semanticwb.platform.SemanticProperty swp_ordering=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#ordering");
    public static final org.semanticwb.platform.SemanticProperty swp_cancelRemainingInstances=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#cancelRemainingInstances");
    public static final org.semanticwb.platform.SemanticClass swp_AdHocSubProcess=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#AdHocSubProcess");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#AdHocSubProcess");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcesses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcesses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess>(it, true);
        }

        public static org.semanticwb.process.model.AdHocSubProcess createAdHocSubProcess(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.AdHocSubProcess.ClassMgr.createAdHocSubProcess(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.AdHocSubProcess getAdHocSubProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.AdHocSubProcess)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.AdHocSubProcess createAdHocSubProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.AdHocSubProcess)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeAdHocSubProcess(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasAdHocSubProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAdHocSubProcess(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByIoSpecification(org.semanticwb.process.model.InputOutputSpecification value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_ioSpecification, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByIoSpecification(org.semanticwb.process.model.InputOutputSpecification value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_ioSpecification,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByArtifact(org.semanticwb.process.model.Artifact value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasArtifact, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByArtifact(org.semanticwb.process.model.Artifact value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasArtifact,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByAuditing(org.semanticwb.process.model.Auditing value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_auditing, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByAuditing(org.semanticwb.process.model.Auditing value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_auditing,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByCompletionCondition(org.semanticwb.process.model.Expression value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_completionCondition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByCompletionCondition(org.semanticwb.process.model.Expression value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_completionCondition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByResource(org.semanticwb.process.model.ActivityResource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasResource, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByResource(org.semanticwb.process.model.ActivityResource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasResource,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByBoundaryEventRef(org.semanticwb.process.model.BoundaryEvent value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasBoundaryEventRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByBoundaryEventRef(org.semanticwb.process.model.BoundaryEvent value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasBoundaryEventRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByProperty(org.semanticwb.process.model.Property value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasProperty, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByProperty(org.semanticwb.process.model.Property value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasProperty,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByIncomingSequenceFlow(org.semanticwb.process.model.SequenceFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasIncomingSequenceFlow, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByIncomingSequenceFlow(org.semanticwb.process.model.SequenceFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasIncomingSequenceFlow,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByOutgoingSequenceFlow(org.semanticwb.process.model.SequenceFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutgoingSequenceFlow, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByOutgoingSequenceFlow(org.semanticwb.process.model.SequenceFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutgoingSequenceFlow,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByLoopCharacteristics(org.semanticwb.process.model.LoopCharacteristics value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_loopCharacteristics, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByLoopCharacteristics(org.semanticwb.process.model.LoopCharacteristics value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_loopCharacteristics,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByFlowElement(org.semanticwb.process.model.FlowElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowElement, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByFlowElement(org.semanticwb.process.model.FlowElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowElement,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByDataOutputAssociation(org.semanticwb.process.model.DataOutputAssociation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataOutputAssociation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByDataOutputAssociation(org.semanticwb.process.model.DataOutputAssociation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataOutputAssociation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByDefaultSequenceFlow(org.semanticwb.process.model.SequenceFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_defaultSequenceFlow, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByDefaultSequenceFlow(org.semanticwb.process.model.SequenceFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_defaultSequenceFlow,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByMessageRef(org.semanticwb.process.model.Message value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_messageRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByMessageRef(org.semanticwb.process.model.Message value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_messageRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByMonitoring(org.semanticwb.process.model.Monitoring value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByMonitoring(org.semanticwb.process.model.Monitoring value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByDataInputAssociation(org.semanticwb.process.model.DataInputAssociation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataInputAssociation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByDataInputAssociation(org.semanticwb.process.model.DataInputAssociation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataInputAssociation,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public AdHocSubProcessBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setCompletionCondition(org.semanticwb.process.model.Expression value)
    {
        getSemanticObject().setObjectProperty(swp_completionCondition, value.getSemanticObject());
    }

    public void removeCompletionCondition()
    {
        getSemanticObject().removeProperty(swp_completionCondition);
    }

    public org.semanticwb.process.model.Expression getCompletionCondition()
    {
         org.semanticwb.process.model.Expression ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_completionCondition);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Expression)obj.createGenericInstance();
         }
         return ret;
    }

    public String getOrdering()
    {
        return getSemanticObject().getProperty(swp_ordering);
    }

    public void setOrdering(String value)
    {
        getSemanticObject().setProperty(swp_ordering, value);
    }

    public boolean isCancelRemainingInstances()
    {
        return getSemanticObject().getBooleanProperty(swp_cancelRemainingInstances);
    }

    public void setCancelRemainingInstances(boolean value)
    {
        getSemanticObject().setBooleanProperty(swp_cancelRemainingInstances, value);
    }
}
