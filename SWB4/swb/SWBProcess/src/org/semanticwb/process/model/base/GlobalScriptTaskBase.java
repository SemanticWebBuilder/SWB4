package org.semanticwb.process.model.base;


public abstract class GlobalScriptTaskBase extends org.semanticwb.process.model.GlobalTask implements org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Documentable,org.semanticwb.process.model.Rootable,org.semanticwb.process.model.IOSpecificable,org.semanticwb.process.model.Callable
{
    public static final org.semanticwb.platform.SemanticClass swp_GlobalScriptTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#GlobalScriptTask");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#GlobalScriptTask");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.GlobalScriptTask> listGlobalScriptTasks(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GlobalScriptTask>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.GlobalScriptTask> listGlobalScriptTasks()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GlobalScriptTask>(it, true);
        }

        public static org.semanticwb.process.model.GlobalScriptTask createGlobalScriptTask(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.GlobalScriptTask.ClassMgr.createGlobalScriptTask(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.GlobalScriptTask getGlobalScriptTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.GlobalScriptTask)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.GlobalScriptTask createGlobalScriptTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.GlobalScriptTask)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeGlobalScriptTask(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasGlobalScriptTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (getGlobalScriptTask(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.GlobalScriptTask> listGlobalScriptTaskByIOBinding(org.semanticwb.process.model.InputOutputBinding value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GlobalScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasIOBinding, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.GlobalScriptTask> listGlobalScriptTaskByIOBinding(org.semanticwb.process.model.InputOutputBinding value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GlobalScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasIOBinding,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.GlobalScriptTask> listGlobalScriptTaskBySupportedInterfaceRef(org.semanticwb.process.model.ProcessInterface value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GlobalScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasSupportedInterfaceRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.GlobalScriptTask> listGlobalScriptTaskBySupportedInterfaceRef(org.semanticwb.process.model.ProcessInterface value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GlobalScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasSupportedInterfaceRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.GlobalScriptTask> listGlobalScriptTaskByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GlobalScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.GlobalScriptTask> listGlobalScriptTaskByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GlobalScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.GlobalScriptTask> listGlobalScriptTaskByCalledElement(org.semanticwb.process.model.Callable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GlobalScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_calledElement, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.GlobalScriptTask> listGlobalScriptTaskByCalledElement(org.semanticwb.process.model.Callable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GlobalScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_calledElement,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.GlobalScriptTask> listGlobalScriptTaskByIoSpecification(org.semanticwb.process.model.InputOutputSpecification value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GlobalScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_ioSpecification, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.GlobalScriptTask> listGlobalScriptTaskByIoSpecification(org.semanticwb.process.model.InputOutputSpecification value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GlobalScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_ioSpecification,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.GlobalScriptTask> listGlobalScriptTaskByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GlobalScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.GlobalScriptTask> listGlobalScriptTaskByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GlobalScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.GlobalScriptTask> listGlobalScriptTaskByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GlobalScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.GlobalScriptTask> listGlobalScriptTaskByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GlobalScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public GlobalScriptTaskBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
