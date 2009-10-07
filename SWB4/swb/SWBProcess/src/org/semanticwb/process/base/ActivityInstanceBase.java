package org.semanticwb.process.base;


public class ActivityInstanceBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable,org.semanticwb.process.ProcessTraceable
{
    public static final org.semanticwb.platform.SemanticClass swbps_TaskInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#TaskInstance");
    public static final org.semanticwb.platform.SemanticProperty swbps_hasTaskinstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasTaskinstance");
    public static final org.semanticwb.platform.SemanticClass swbps_Activity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Activity");
    public static final org.semanticwb.platform.SemanticProperty swbps_activityType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#activityType");
    public static final org.semanticwb.platform.SemanticProperty swbps_activityStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#activityStatus");
    public static final org.semanticwb.platform.SemanticClass swbps_ProcessInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessInstance");
    public static final org.semanticwb.platform.SemanticProperty swbps_processInstanceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processInstanceInv");
    public static final org.semanticwb.platform.SemanticClass swbps_ActivityInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ActivityInstance");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ActivityInstance");

    public ActivityInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.process.ActivityInstance> listActivityInstances(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ActivityInstance>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.process.ActivityInstance> listActivityInstances()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ActivityInstance>(it, true);
    }

    public static org.semanticwb.process.ActivityInstance createActivityInstance(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.process.ActivityInstance.createActivityInstance(String.valueOf(id), model);
    }

    public static org.semanticwb.process.ActivityInstance getActivityInstance(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.process.ActivityInstance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.process.ActivityInstance createActivityInstance(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.process.ActivityInstance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeActivityInstance(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasActivityInstance(String id, org.semanticwb.model.SWBModel model)
    {
        return (getActivityInstance(id, model)!=null);
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

   public static java.util.Iterator<org.semanticwb.process.ActivityInstance> listActivityInstanceByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ActivityInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.ActivityInstance> listActivityInstanceByModifiedBy(org.semanticwb.model.User modifiedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ActivityInstance> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
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

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.TaskInstance> listTaskinstances()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.TaskInstance>(getSemanticObject().listObjectProperties(swbps_hasTaskinstance));
    }

    public boolean hasTaskinstance(org.semanticwb.process.TaskInstance taskinstance)
    {
        if(taskinstance==null)return false;
        return getSemanticObject().hasObjectProperty(swbps_hasTaskinstance,taskinstance.getSemanticObject());
    }

    public void addTaskinstance(org.semanticwb.process.TaskInstance value)
    {
        getSemanticObject().addObjectProperty(swbps_hasTaskinstance, value.getSemanticObject());
    }

    public void removeAllTaskinstance()
    {
        getSemanticObject().removeProperty(swbps_hasTaskinstance);
    }

    public void removeTaskinstance(org.semanticwb.process.TaskInstance taskinstance)
    {
        getSemanticObject().removeObjectProperty(swbps_hasTaskinstance,taskinstance.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.process.ActivityInstance> listActivityInstanceByTaskinstance(org.semanticwb.process.TaskInstance hastaskinstance,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ActivityInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_hasTaskinstance, hastaskinstance.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.ActivityInstance> listActivityInstanceByTaskinstance(org.semanticwb.process.TaskInstance hastaskinstance)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ActivityInstance> it=new org.semanticwb.model.GenericIterator(hastaskinstance.getSemanticObject().getModel().listSubjects(swbps_hasTaskinstance,hastaskinstance.getSemanticObject()));
       return it;
   }

    public org.semanticwb.process.TaskInstance getTaskinstance()
    {
         org.semanticwb.process.TaskInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbps_hasTaskinstance);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.TaskInstance)obj.createGenericInstance();
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

    public void setActivityType(org.semanticwb.process.Activity value)
    {
        getSemanticObject().setObjectProperty(swbps_activityType, value.getSemanticObject());
    }

    public void removeActivityType()
    {
        getSemanticObject().removeProperty(swbps_activityType);
    }

   public static java.util.Iterator<org.semanticwb.process.ActivityInstance> listActivityInstanceByActivityType(org.semanticwb.process.Activity activitytype,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ActivityInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_activityType, activitytype.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.ActivityInstance> listActivityInstanceByActivityType(org.semanticwb.process.Activity activitytype)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ActivityInstance> it=new org.semanticwb.model.GenericIterator(activitytype.getSemanticObject().getModel().listSubjects(swbps_activityType,activitytype.getSemanticObject()));
       return it;
   }

    public org.semanticwb.process.Activity getActivityType()
    {
         org.semanticwb.process.Activity ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbps_activityType);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.Activity)obj.createGenericInstance();
         }
         return ret;
    }

    public int getStatus()
    {
        return getSemanticObject().getIntProperty(swbps_activityStatus);
    }

    public void setStatus(int value)
    {
        getSemanticObject().setIntProperty(swbps_activityStatus, value);
    }

    public java.util.Date getEnded()
    {
        return getSemanticObject().getDateProperty(swbps_ended);
    }

    public void setEnded(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swbps_ended, value);
    }

    public void setProcessInstance(org.semanticwb.process.ProcessInstance value)
    {
        getSemanticObject().setObjectProperty(swbps_processInstanceInv, value.getSemanticObject());
    }

    public void removeProcessInstance()
    {
        getSemanticObject().removeProperty(swbps_processInstanceInv);
    }

   public static java.util.Iterator<org.semanticwb.process.ActivityInstance> listActivityInstanceByProcessInstance(org.semanticwb.process.ProcessInstance processinstanceinv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ActivityInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_processInstanceInv, processinstanceinv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.ActivityInstance> listActivityInstanceByProcessInstance(org.semanticwb.process.ProcessInstance processinstanceinv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ActivityInstance> it=new org.semanticwb.model.GenericIterator(processinstanceinv.getSemanticObject().getModel().listSubjects(swbps_processInstanceInv,processinstanceinv.getSemanticObject()));
       return it;
   }

    public org.semanticwb.process.ProcessInstance getProcessInstance()
    {
         org.semanticwb.process.ProcessInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbps_processInstanceInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.ProcessInstance)obj.createGenericInstance();
         }
         return ret;
    }

    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

   public static java.util.Iterator<org.semanticwb.process.ActivityInstance> listActivityInstanceByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ActivityInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.ActivityInstance> listActivityInstanceByCreator(org.semanticwb.model.User creator)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ActivityInstance> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
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

   public static java.util.Iterator<org.semanticwb.process.ActivityInstance> listActivityInstanceByEndedby(org.semanticwb.model.User endedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ActivityInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_endedby, endedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.ActivityInstance> listActivityInstanceByEndedby(org.semanticwb.model.User endedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ActivityInstance> it=new org.semanticwb.model.GenericIterator(endedby.getSemanticObject().getModel().listSubjects(swbps_endedby,endedby.getSemanticObject()));
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
