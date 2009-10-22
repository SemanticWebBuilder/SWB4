package org.semanticwb.process.base;


public class TaskInstanceBase extends org.semanticwb.process.ActivityInstance implements org.semanticwb.process.ProcessTraceable,org.semanticwb.model.Traceable
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
       public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
       public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
       public static final org.semanticwb.platform.SemanticClass swbps_Task=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Task");
       public static final org.semanticwb.platform.SemanticProperty swbps_taskType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#taskType");
       public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
       public static final org.semanticwb.platform.SemanticProperty swbps_ended=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#ended");
       public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
       public static final org.semanticwb.platform.SemanticProperty swbps_status=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#status");
       public static final org.semanticwb.platform.SemanticClass swbps_ProcessInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessInstance");
       public static final org.semanticwb.platform.SemanticProperty swbps_processInstanceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processInstanceInv");
       public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
       public static final org.semanticwb.platform.SemanticProperty swbps_endedby=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#endedby");
       public static final org.semanticwb.platform.SemanticClass swbps_TaskInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#TaskInstance");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#TaskInstance");

       public static java.util.Iterator<org.semanticwb.process.TaskInstance> listTaskInstances(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.TaskInstance>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.TaskInstance> listTaskInstances()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.TaskInstance>(it, true);
       }

       public static org.semanticwb.process.TaskInstance createTaskInstance(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.TaskInstance.ClassMgr.createTaskInstance(String.valueOf(id), model);
       }

       public static org.semanticwb.process.TaskInstance getTaskInstance(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.TaskInstance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.TaskInstance createTaskInstance(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.TaskInstance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeTaskInstance(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasTaskInstance(String id, org.semanticwb.model.SWBModel model)
       {
           return (getTaskInstance(id, model)!=null);
       }
    }

    public TaskInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setTaskType(org.semanticwb.process.Task value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swbps_taskType, value.getSemanticObject());
    }

    public void removeTaskType()
    {
        getSemanticObject().removeProperty(ClassMgr.swbps_taskType);
    }

   public static java.util.Iterator<org.semanticwb.process.TaskInstance> listTaskInstanceByTaskType(org.semanticwb.process.Task tasktype,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.TaskInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_taskType, tasktype.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.TaskInstance> listTaskInstanceByTaskType(org.semanticwb.process.Task tasktype)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.TaskInstance> it=new org.semanticwb.model.GenericIterator(tasktype.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_taskType,tasktype.getSemanticObject()));
       return it;
   }

    public org.semanticwb.process.Task getTaskType()
    {
         org.semanticwb.process.Task ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbps_taskType);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.Task)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.process.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
