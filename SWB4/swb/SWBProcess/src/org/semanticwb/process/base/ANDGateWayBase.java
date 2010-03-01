package org.semanticwb.process.base;


public abstract class ANDGateWayBase extends org.semanticwb.process.GateWay implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.process.FlowObject
{
    public static final org.semanticwb.platform.SemanticClass swbps_ANDGateWay=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ANDGateWay");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ANDGateWay");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.ANDGateWay> listANDGateWays(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ANDGateWay>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.ANDGateWay> listANDGateWays()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ANDGateWay>(it, true);
        }

        public static org.semanticwb.process.ANDGateWay createANDGateWay(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.ANDGateWay.ClassMgr.createANDGateWay(String.valueOf(id), model);
        }

        public static org.semanticwb.process.ANDGateWay getANDGateWay(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.ANDGateWay)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.ANDGateWay createANDGateWay(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.ANDGateWay)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeANDGateWay(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasANDGateWay(String id, org.semanticwb.model.SWBModel model)
        {
            return (getANDGateWay(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.ANDGateWay> listANDGateWayByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.ANDGateWay> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.ANDGateWay> listANDGateWayByModifiedBy(org.semanticwb.model.User modifiedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.ANDGateWay> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.ANDGateWay> listANDGateWayByFromConnectionObject(org.semanticwb.process.ConnectionObject hasfromconnectionobjectinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.ANDGateWay> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_hasFromConnectionObjectInv, hasfromconnectionobjectinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.ANDGateWay> listANDGateWayByFromConnectionObject(org.semanticwb.process.ConnectionObject hasfromconnectionobjectinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.ANDGateWay> it=new org.semanticwb.model.GenericIterator(hasfromconnectionobjectinv.getSemanticObject().getModel().listSubjects(swbps_hasFromConnectionObjectInv,hasfromconnectionobjectinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.ANDGateWay> listANDGateWayByFlowObjectInstance(org.semanticwb.process.FlowObjectInstance hasflowobjectinstansinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.ANDGateWay> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_hasFlowObjectInstansInv, hasflowobjectinstansinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.ANDGateWay> listANDGateWayByFlowObjectInstance(org.semanticwb.process.FlowObjectInstance hasflowobjectinstansinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.ANDGateWay> it=new org.semanticwb.model.GenericIterator(hasflowobjectinstansinv.getSemanticObject().getModel().listSubjects(swbps_hasFlowObjectInstansInv,hasflowobjectinstansinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.ANDGateWay> listANDGateWayByToConnectionObject(org.semanticwb.process.ConnectionObject hastoconnectionobject,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.ANDGateWay> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_hasToConnectionObject, hastoconnectionobject.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.ANDGateWay> listANDGateWayByToConnectionObject(org.semanticwb.process.ConnectionObject hastoconnectionobject)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.ANDGateWay> it=new org.semanticwb.model.GenericIterator(hastoconnectionobject.getSemanticObject().getModel().listSubjects(swbps_hasToConnectionObject,hastoconnectionobject.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.ANDGateWay> listANDGateWayByParentProcess(org.semanticwb.process.Process parentprocessinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.ANDGateWay> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_parentProcessInv, parentprocessinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.ANDGateWay> listANDGateWayByParentProcess(org.semanticwb.process.Process parentprocessinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.ANDGateWay> it=new org.semanticwb.model.GenericIterator(parentprocessinv.getSemanticObject().getModel().listSubjects(swbps_parentProcessInv,parentprocessinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.ANDGateWay> listANDGateWayByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.ANDGateWay> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.ANDGateWay> listANDGateWayByCreator(org.semanticwb.model.User creator)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.ANDGateWay> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
            return it;
        }
    }

    public ANDGateWayBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.process.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
