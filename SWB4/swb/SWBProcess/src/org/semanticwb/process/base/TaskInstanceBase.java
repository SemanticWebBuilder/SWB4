package org.semanticwb.process.base;


public class TaskInstanceBase extends org.semanticwb.model.SWBClass implements org.semanticwb.process.ProcessTraceable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swbps_Task=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Task");
    public static final org.semanticwb.platform.SemanticProperty swbps_taskType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#taskType");
    public static final org.semanticwb.platform.SemanticClass swbps_ActivityInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ActivityInstance");
    public static final org.semanticwb.platform.SemanticProperty swbps_activityInstanceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#activityInstanceInv");
    public static final org.semanticwb.platform.SemanticProperty swbps_taskStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#taskStatus");
    public static final org.semanticwb.platform.SemanticClass swbps_TaskInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#TaskInstance");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#TaskInstance");

    public TaskInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

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
        return org.semanticwb.process.TaskInstance.createTaskInstance(String.valueOf(id), model);
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

    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

   public static java.util.Iterator<org.semanticwb.process.TaskInstance> listTaskInstanceByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.TaskInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.TaskInstance> listTaskInstanceByModifiedBy(org.semanticwb.model.User modifiedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.TaskInstance> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public void setTaskType(org.semanticwb.process.Task value)
    {
        getSemanticObject().setObjectProperty(swbps_taskType, value.getSemanticObject());
    }

    public void removeTaskType()
    {
        getSemanticObject().removeProperty(swbps_taskType);
    }

   public static java.util.Iterator<org.semanticwb.process.TaskInstance> listTaskInstanceByTaskType(org.semanticwb.process.Task tasktype,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.TaskInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_taskType, tasktype.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.TaskInstance> listTaskInstanceByTaskType(org.semanticwb.process.Task tasktype)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.TaskInstance> it=new org.semanticwb.model.GenericIterator(tasktype.getSemanticObject().getModel().listSubjects(swbps_taskType,tasktype.getSemanticObject()));
       return it;
   }

    public org.semanticwb.process.Task getTaskType()
    {
         org.semanticwb.process.Task ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbps_taskType);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.Task)obj.createGenericInstance();
         }
         return ret;
    }

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

    public java.util.Date getEnded()
    {
        return getSemanticObject().getDateProperty(swbps_ended);
    }

    public void setEnded(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swbps_ended, value);
    }

    public void setActivityInstance(org.semanticwb.process.ActivityInstance value)
    {
        getSemanticObject().setObjectProperty(swbps_activityInstanceInv, value.getSemanticObject());
    }

    public void removeActivityInstance()
    {
        getSemanticObject().removeProperty(swbps_activityInstanceInv);
    }

   public static java.util.Iterator<org.semanticwb.process.TaskInstance> listTaskInstanceByActivityInstance(org.semanticwb.process.ActivityInstance activityinstanceinv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.TaskInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_activityInstanceInv, activityinstanceinv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.TaskInstance> listTaskInstanceByActivityInstance(org.semanticwb.process.ActivityInstance activityinstanceinv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.TaskInstance> it=new org.semanticwb.model.GenericIterator(activityinstanceinv.getSemanticObject().getModel().listSubjects(swbps_activityInstanceInv,activityinstanceinv.getSemanticObject()));
       return it;
   }

    public org.semanticwb.process.ActivityInstance getActivityInstance()
    {
         org.semanticwb.process.ActivityInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbps_activityInstanceInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.ActivityInstance)obj.createGenericInstance();
         }
         return ret;
    }

    public int getStatus()
    {
        return getSemanticObject().getIntProperty(swbps_taskStatus);
    }

    public void setStatus(int value)
    {
        getSemanticObject().setIntProperty(swbps_taskStatus, value);
    }

    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

   public static java.util.Iterator<org.semanticwb.process.TaskInstance> listTaskInstanceByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.TaskInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.TaskInstance> listTaskInstanceByCreator(org.semanticwb.model.User creator)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.TaskInstance> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public void setEndedby(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swbps_endedby, value.getSemanticObject());
    }

    public void removeEndedby()
    {
        getSemanticObject().removeProperty(swbps_endedby);
    }

   public static java.util.Iterator<org.semanticwb.process.TaskInstance> listTaskInstanceByEndedby(org.semanticwb.model.User endedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.TaskInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_endedby, endedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.TaskInstance> listTaskInstanceByEndedby(org.semanticwb.model.User endedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.TaskInstance> it=new org.semanticwb.model.GenericIterator(endedby.getSemanticObject().getModel().listSubjects(swbps_endedby,endedby.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.User getEndedby()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbps_endedby);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.process.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
