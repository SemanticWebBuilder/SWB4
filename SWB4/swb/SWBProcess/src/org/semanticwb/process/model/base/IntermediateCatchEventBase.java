package org.semanticwb.process.model.base;


public abstract class IntermediateCatchEventBase extends org.semanticwb.process.model.CatchEvent implements org.semanticwb.process.model.DataOutputReferensable,org.semanticwb.process.model.Auditable,org.semanticwb.process.model.EventDefinible,org.semanticwb.process.model.Documentable,org.semanticwb.process.model.DataOutputAssociable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Monitorable,org.semanticwb.process.model.Messageable
{
    public static final org.semanticwb.platform.SemanticClass swp_IntermediateCatchEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#IntermediateCatchEvent");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#IntermediateCatchEvent");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.IntermediateCatchEvent> listIntermediateCatchEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateCatchEvent>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.IntermediateCatchEvent> listIntermediateCatchEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateCatchEvent>(it, true);
        }

        public static org.semanticwb.process.model.IntermediateCatchEvent createIntermediateCatchEvent(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.IntermediateCatchEvent.ClassMgr.createIntermediateCatchEvent(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.IntermediateCatchEvent getIntermediateCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.IntermediateCatchEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.IntermediateCatchEvent createIntermediateCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.IntermediateCatchEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeIntermediateCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasIntermediateCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getIntermediateCatchEvent(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.IntermediateCatchEvent> listIntermediateCatchEventByDataOutputRef(org.semanticwb.process.model.DataOutput value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataOutputRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.IntermediateCatchEvent> listIntermediateCatchEventByDataOutputRef(org.semanticwb.process.model.DataOutput value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataOutputRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.IntermediateCatchEvent> listIntermediateCatchEventByEventDefinitionRef(org.semanticwb.process.model.EventDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasEventDefinitionRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.IntermediateCatchEvent> listIntermediateCatchEventByEventDefinitionRef(org.semanticwb.process.model.EventDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasEventDefinitionRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.IntermediateCatchEvent> listIntermediateCatchEventByAuditing(org.semanticwb.process.model.Auditing value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_auditing, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.IntermediateCatchEvent> listIntermediateCatchEventByAuditing(org.semanticwb.process.model.Auditing value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_auditing,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.IntermediateCatchEvent> listIntermediateCatchEventByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.IntermediateCatchEvent> listIntermediateCatchEventByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.IntermediateCatchEvent> listIntermediateCatchEventByIncomingSequenceFlow(org.semanticwb.process.model.SequenceFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasIncomingSequenceFlow, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.IntermediateCatchEvent> listIntermediateCatchEventByIncomingSequenceFlow(org.semanticwb.process.model.SequenceFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasIncomingSequenceFlow,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.IntermediateCatchEvent> listIntermediateCatchEventByOutgoingSequenceFlow(org.semanticwb.process.model.SequenceFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutgoingSequenceFlow, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.IntermediateCatchEvent> listIntermediateCatchEventByOutgoingSequenceFlow(org.semanticwb.process.model.SequenceFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutgoingSequenceFlow,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.IntermediateCatchEvent> listIntermediateCatchEventByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.IntermediateCatchEvent> listIntermediateCatchEventByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.IntermediateCatchEvent> listIntermediateCatchEventByEventDefinition(org.semanticwb.process.model.EventDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasEventDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.IntermediateCatchEvent> listIntermediateCatchEventByEventDefinition(org.semanticwb.process.model.EventDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasEventDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.IntermediateCatchEvent> listIntermediateCatchEventByDataOutputAssociation(org.semanticwb.process.model.DataOutputAssociation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataOutputAssociation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.IntermediateCatchEvent> listIntermediateCatchEventByDataOutputAssociation(org.semanticwb.process.model.DataOutputAssociation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataOutputAssociation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.IntermediateCatchEvent> listIntermediateCatchEventByMessageRef(org.semanticwb.process.model.Message value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_messageRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.IntermediateCatchEvent> listIntermediateCatchEventByMessageRef(org.semanticwb.process.model.Message value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_messageRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.IntermediateCatchEvent> listIntermediateCatchEventByOutputSet(org.semanticwb.process.model.OutputSet value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_outputSet, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.IntermediateCatchEvent> listIntermediateCatchEventByOutputSet(org.semanticwb.process.model.OutputSet value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_outputSet,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.IntermediateCatchEvent> listIntermediateCatchEventByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.IntermediateCatchEvent> listIntermediateCatchEventByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.IntermediateCatchEvent> listIntermediateCatchEventByMonitoring(org.semanticwb.process.model.Monitoring value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.IntermediateCatchEvent> listIntermediateCatchEventByMonitoring(org.semanticwb.process.model.Monitoring value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.IntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public IntermediateCatchEventBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
