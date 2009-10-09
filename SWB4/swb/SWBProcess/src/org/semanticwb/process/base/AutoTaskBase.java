package org.semanticwb.process.base;


public class AutoTaskBase extends org.semanticwb.process.Task implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.UserGroupable,org.semanticwb.model.Traceable,org.semanticwb.model.Roleable
{
    public static final org.semanticwb.platform.SemanticClass swbps_AutoTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#AutoTask");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#AutoTask");

    public AutoTaskBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.process.AutoTask> listAutoTasks(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.AutoTask>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.process.AutoTask> listAutoTasks()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.AutoTask>(it, true);
    }

    public static org.semanticwb.process.AutoTask createAutoTask(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.process.AutoTask.createAutoTask(String.valueOf(id), model);
    }

    public static org.semanticwb.process.AutoTask getAutoTask(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.process.AutoTask)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.process.AutoTask createAutoTask(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.process.AutoTask)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeAutoTask(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasAutoTask(String id, org.semanticwb.model.SWBModel model)
    {
        return (getAutoTask(id, model)!=null);
    }
}
