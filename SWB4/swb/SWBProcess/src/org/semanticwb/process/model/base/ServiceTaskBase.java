package org.semanticwb.process.model.base;


public abstract class ServiceTaskBase extends org.semanticwb.process.model.Task implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swp_ServiceTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ServiceTask");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ServiceTask");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTasks(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTasks()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask>(it, true);
        }

        public static org.semanticwb.process.model.ServiceTask createServiceTask(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ServiceTask.ClassMgr.createServiceTask(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.ServiceTask getServiceTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ServiceTask)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.ServiceTask createServiceTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ServiceTask)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeServiceTask(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasServiceTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (getServiceTask(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstansInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstansInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTaskByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ServiceTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ServiceTaskBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
