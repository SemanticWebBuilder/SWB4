package org.semanticwb.process.model.base;


public abstract class MultipleIntermediateCatchEventBase extends org.semanticwb.process.model.IntermediateCatchEvent implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swp_MultipleIntermediateCatchEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#MultipleIntermediateCatchEvent");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#MultipleIntermediateCatchEvent");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateCatchEvent> listMultipleIntermediateCatchEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateCatchEvent>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateCatchEvent> listMultipleIntermediateCatchEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateCatchEvent>(it, true);
        }

        public static org.semanticwb.process.model.MultipleIntermediateCatchEvent createMultipleIntermediateCatchEvent(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.MultipleIntermediateCatchEvent.ClassMgr.createMultipleIntermediateCatchEvent(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.MultipleIntermediateCatchEvent getMultipleIntermediateCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.MultipleIntermediateCatchEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.MultipleIntermediateCatchEvent createMultipleIntermediateCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.MultipleIntermediateCatchEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeMultipleIntermediateCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasMultipleIntermediateCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMultipleIntermediateCatchEvent(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateCatchEvent> listMultipleIntermediateCatchEventByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateCatchEvent> listMultipleIntermediateCatchEventByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateCatchEvent> listMultipleIntermediateCatchEventByFromConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFromConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateCatchEvent> listMultipleIntermediateCatchEventByFromConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFromConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateCatchEvent> listMultipleIntermediateCatchEventByFlowObjectInstance(org.semanticwb.process.model.FlowObjectInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowObjectInstansInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateCatchEvent> listMultipleIntermediateCatchEventByFlowObjectInstance(org.semanticwb.process.model.FlowObjectInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowObjectInstansInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateCatchEvent> listMultipleIntermediateCatchEventByToConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasToConnectionObject, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateCatchEvent> listMultipleIntermediateCatchEventByToConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasToConnectionObject,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateCatchEvent> listMultipleIntermediateCatchEventByParentProcess(org.semanticwb.process.model.SubProcess value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parentProcessInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateCatchEvent> listMultipleIntermediateCatchEventByParentProcess(org.semanticwb.process.model.SubProcess value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parentProcessInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateCatchEvent> listMultipleIntermediateCatchEventByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateCatchEvent> listMultipleIntermediateCatchEventByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public MultipleIntermediateCatchEventBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
