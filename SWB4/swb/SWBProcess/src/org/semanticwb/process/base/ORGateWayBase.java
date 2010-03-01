package org.semanticwb.process.base;


public abstract class ORGateWayBase extends org.semanticwb.process.GateWay implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.process.FlowObject
{
    public static final org.semanticwb.platform.SemanticClass swbps_ORGateWay=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ORGateWay");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ORGateWay");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.ORGateWay> listORGateWays(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ORGateWay>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.ORGateWay> listORGateWays()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ORGateWay>(it, true);
        }

        public static org.semanticwb.process.ORGateWay createORGateWay(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.ORGateWay.ClassMgr.createORGateWay(String.valueOf(id), model);
        }

        public static org.semanticwb.process.ORGateWay getORGateWay(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.ORGateWay)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.ORGateWay createORGateWay(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.ORGateWay)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeORGateWay(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasORGateWay(String id, org.semanticwb.model.SWBModel model)
        {
            return (getORGateWay(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.ORGateWay> listORGateWayByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.ORGateWay> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.ORGateWay> listORGateWayByModifiedBy(org.semanticwb.model.User modifiedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.ORGateWay> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.ORGateWay> listORGateWayByFromConnectionObject(org.semanticwb.process.ConnectionObject hasfromconnectionobjectinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.ORGateWay> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_hasFromConnectionObjectInv, hasfromconnectionobjectinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.ORGateWay> listORGateWayByFromConnectionObject(org.semanticwb.process.ConnectionObject hasfromconnectionobjectinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.ORGateWay> it=new org.semanticwb.model.GenericIterator(hasfromconnectionobjectinv.getSemanticObject().getModel().listSubjects(swbps_hasFromConnectionObjectInv,hasfromconnectionobjectinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.ORGateWay> listORGateWayByFlowObjectInstance(org.semanticwb.process.FlowObjectInstance hasflowobjectinstansinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.ORGateWay> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_hasFlowObjectInstansInv, hasflowobjectinstansinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.ORGateWay> listORGateWayByFlowObjectInstance(org.semanticwb.process.FlowObjectInstance hasflowobjectinstansinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.ORGateWay> it=new org.semanticwb.model.GenericIterator(hasflowobjectinstansinv.getSemanticObject().getModel().listSubjects(swbps_hasFlowObjectInstansInv,hasflowobjectinstansinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.ORGateWay> listORGateWayByToConnectionObject(org.semanticwb.process.ConnectionObject hastoconnectionobject,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.ORGateWay> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_hasToConnectionObject, hastoconnectionobject.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.ORGateWay> listORGateWayByToConnectionObject(org.semanticwb.process.ConnectionObject hastoconnectionobject)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.ORGateWay> it=new org.semanticwb.model.GenericIterator(hastoconnectionobject.getSemanticObject().getModel().listSubjects(swbps_hasToConnectionObject,hastoconnectionobject.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.ORGateWay> listORGateWayByParentProcess(org.semanticwb.process.Process parentprocessinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.ORGateWay> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_parentProcessInv, parentprocessinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.ORGateWay> listORGateWayByParentProcess(org.semanticwb.process.Process parentprocessinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.ORGateWay> it=new org.semanticwb.model.GenericIterator(parentprocessinv.getSemanticObject().getModel().listSubjects(swbps_parentProcessInv,parentprocessinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.ORGateWay> listORGateWayByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.ORGateWay> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.ORGateWay> listORGateWayByCreator(org.semanticwb.model.User creator)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.ORGateWay> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
            return it;
        }
    }

    public ORGateWayBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.process.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
