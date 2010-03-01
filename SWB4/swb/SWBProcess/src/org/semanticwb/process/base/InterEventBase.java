package org.semanticwb.process.base;


public abstract class InterEventBase extends org.semanticwb.process.Event implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.process.FlowObject
{
    public static final org.semanticwb.platform.SemanticClass swbps_InterEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#InterEvent");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#InterEvent");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.InterEvent> listInterEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.InterEvent>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.InterEvent> listInterEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.InterEvent>(it, true);
        }

        public static org.semanticwb.process.InterEvent createInterEvent(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.InterEvent.ClassMgr.createInterEvent(String.valueOf(id), model);
        }

        public static org.semanticwb.process.InterEvent getInterEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.InterEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.InterEvent createInterEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.InterEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeInterEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasInterEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getInterEvent(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.InterEvent> listInterEventByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.InterEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.InterEvent> listInterEventByModifiedBy(org.semanticwb.model.User modifiedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.InterEvent> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.InterEvent> listInterEventByFromConnectionObject(org.semanticwb.process.ConnectionObject hasfromconnectionobjectinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.InterEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_hasFromConnectionObjectInv, hasfromconnectionobjectinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.InterEvent> listInterEventByFromConnectionObject(org.semanticwb.process.ConnectionObject hasfromconnectionobjectinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.InterEvent> it=new org.semanticwb.model.GenericIterator(hasfromconnectionobjectinv.getSemanticObject().getModel().listSubjects(swbps_hasFromConnectionObjectInv,hasfromconnectionobjectinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.InterEvent> listInterEventByFlowObjectInstance(org.semanticwb.process.FlowObjectInstance hasflowobjectinstansinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.InterEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_hasFlowObjectInstansInv, hasflowobjectinstansinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.InterEvent> listInterEventByFlowObjectInstance(org.semanticwb.process.FlowObjectInstance hasflowobjectinstansinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.InterEvent> it=new org.semanticwb.model.GenericIterator(hasflowobjectinstansinv.getSemanticObject().getModel().listSubjects(swbps_hasFlowObjectInstansInv,hasflowobjectinstansinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.InterEvent> listInterEventByToConnectionObject(org.semanticwb.process.ConnectionObject hastoconnectionobject,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.InterEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_hasToConnectionObject, hastoconnectionobject.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.InterEvent> listInterEventByToConnectionObject(org.semanticwb.process.ConnectionObject hastoconnectionobject)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.InterEvent> it=new org.semanticwb.model.GenericIterator(hastoconnectionobject.getSemanticObject().getModel().listSubjects(swbps_hasToConnectionObject,hastoconnectionobject.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.InterEvent> listInterEventByParentProcess(org.semanticwb.process.Process parentprocessinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.InterEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_parentProcessInv, parentprocessinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.InterEvent> listInterEventByParentProcess(org.semanticwb.process.Process parentprocessinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.InterEvent> it=new org.semanticwb.model.GenericIterator(parentprocessinv.getSemanticObject().getModel().listSubjects(swbps_parentProcessInv,parentprocessinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.InterEvent> listInterEventByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.InterEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.InterEvent> listInterEventByCreator(org.semanticwb.model.User creator)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.InterEvent> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
            return it;
        }
    }

    public InterEventBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.process.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
