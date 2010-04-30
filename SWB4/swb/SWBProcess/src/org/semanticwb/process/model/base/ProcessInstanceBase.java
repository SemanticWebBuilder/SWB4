package org.semanticwb.process.model.base;


public abstract class ProcessInstanceBase extends org.semanticwb.process.model.BPMNInstance 
{
    public static final org.semanticwb.platform.SemanticProperty swp_processState=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#processState");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ProcessInstance");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ProcessInstance");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInstance> listProcessInstances(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInstance>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessInstance> listProcessInstances()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInstance>(it, true);
        }

        public static org.semanticwb.process.model.ProcessInstance getProcessInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessInstance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.ProcessInstance createProcessInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessInstance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeProcessInstance(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasProcessInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (getProcessInstance(id, model)!=null);
        }
    }

    public ProcessInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getProcessState()
    {
        return getSemanticObject().getProperty(swp_processState);
    }

    public void setProcessState(String value)
    {
        getSemanticObject().setProperty(swp_processState, value);
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
