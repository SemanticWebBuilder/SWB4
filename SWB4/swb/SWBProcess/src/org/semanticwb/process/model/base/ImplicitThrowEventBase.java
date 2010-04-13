package org.semanticwb.process.model.base;


public abstract class ImplicitThrowEventBase extends org.semanticwb.process.model.ThrowEvent implements org.semanticwb.process.model.Auditable,org.semanticwb.process.model.Monitorable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Documentable,org.semanticwb.process.model.EventDefinible,org.semanticwb.process.model.DataInputReferensable,org.semanticwb.process.model.Messageable,org.semanticwb.process.model.DataInputAssociable
{
    public static final org.semanticwb.platform.SemanticClass swp_ImplicitThrowEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ImplicitThrowEvent");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ImplicitThrowEvent");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.ImplicitThrowEvent> listImplicitThrowEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ImplicitThrowEvent>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ImplicitThrowEvent> listImplicitThrowEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ImplicitThrowEvent>(it, true);
        }

        public static org.semanticwb.process.model.ImplicitThrowEvent createImplicitThrowEvent(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ImplicitThrowEvent.ClassMgr.createImplicitThrowEvent(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.ImplicitThrowEvent getImplicitThrowEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ImplicitThrowEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.ImplicitThrowEvent createImplicitThrowEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ImplicitThrowEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeImplicitThrowEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasImplicitThrowEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getImplicitThrowEvent(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ImplicitThrowEvent> listImplicitThrowEventByEventDefinitionRef(org.semanticwb.process.model.EventDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ImplicitThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasEventDefinitionRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ImplicitThrowEvent> listImplicitThrowEventByEventDefinitionRef(org.semanticwb.process.model.EventDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ImplicitThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasEventDefinitionRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ImplicitThrowEvent> listImplicitThrowEventByAuditing(org.semanticwb.process.model.Auditing value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ImplicitThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_auditing, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ImplicitThrowEvent> listImplicitThrowEventByAuditing(org.semanticwb.process.model.Auditing value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ImplicitThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_auditing,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ImplicitThrowEvent> listImplicitThrowEventByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ImplicitThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ImplicitThrowEvent> listImplicitThrowEventByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ImplicitThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ImplicitThrowEvent> listImplicitThrowEventByInputSet(org.semanticwb.process.model.InputSet value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ImplicitThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_inputSet, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ImplicitThrowEvent> listImplicitThrowEventByInputSet(org.semanticwb.process.model.InputSet value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ImplicitThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_inputSet,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ImplicitThrowEvent> listImplicitThrowEventByIncomingSequenceFlow(org.semanticwb.process.model.SequenceFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ImplicitThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasIncomingSequenceFlow, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ImplicitThrowEvent> listImplicitThrowEventByIncomingSequenceFlow(org.semanticwb.process.model.SequenceFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ImplicitThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasIncomingSequenceFlow,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ImplicitThrowEvent> listImplicitThrowEventByOutgoingSequenceFlow(org.semanticwb.process.model.SequenceFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ImplicitThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutgoingSequenceFlow, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ImplicitThrowEvent> listImplicitThrowEventByOutgoingSequenceFlow(org.semanticwb.process.model.SequenceFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ImplicitThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutgoingSequenceFlow,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ImplicitThrowEvent> listImplicitThrowEventByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ImplicitThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ImplicitThrowEvent> listImplicitThrowEventByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ImplicitThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ImplicitThrowEvent> listImplicitThrowEventByEventDefinition(org.semanticwb.process.model.EventDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ImplicitThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasEventDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ImplicitThrowEvent> listImplicitThrowEventByEventDefinition(org.semanticwb.process.model.EventDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ImplicitThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasEventDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ImplicitThrowEvent> listImplicitThrowEventByMessageRef(org.semanticwb.process.model.Message value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ImplicitThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_messageRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ImplicitThrowEvent> listImplicitThrowEventByMessageRef(org.semanticwb.process.model.Message value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ImplicitThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_messageRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ImplicitThrowEvent> listImplicitThrowEventByDataInputRef(org.semanticwb.process.model.DataInput value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ImplicitThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataInputRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ImplicitThrowEvent> listImplicitThrowEventByDataInputRef(org.semanticwb.process.model.DataInput value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ImplicitThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataInputRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ImplicitThrowEvent> listImplicitThrowEventByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ImplicitThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ImplicitThrowEvent> listImplicitThrowEventByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ImplicitThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ImplicitThrowEvent> listImplicitThrowEventByDataInputAssociation(org.semanticwb.process.model.DataInputAssociation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ImplicitThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataInputAssociation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ImplicitThrowEvent> listImplicitThrowEventByDataInputAssociation(org.semanticwb.process.model.DataInputAssociation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ImplicitThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataInputAssociation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ImplicitThrowEvent> listImplicitThrowEventByMonitoring(org.semanticwb.process.model.Monitoring value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ImplicitThrowEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ImplicitThrowEvent> listImplicitThrowEventByMonitoring(org.semanticwb.process.model.Monitoring value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ImplicitThrowEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ImplicitThrowEventBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
