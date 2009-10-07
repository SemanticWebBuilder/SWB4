package org.semanticwb.process.base;


public class ActivityBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swbps_Activity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Activity");
    public static final org.semanticwb.platform.SemanticProperty swbps_hasActivityDependence=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasActivityDependence");
    public static final org.semanticwb.platform.SemanticProperty swbps_activityExecutions=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#activityExecutions");
    public static final org.semanticwb.platform.SemanticClass swbps_Task=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Task");
    public static final org.semanticwb.platform.SemanticProperty swbps_hasTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasTask");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Activity");

    public ActivityBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.process.Activity> listActivities(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.Activity>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.process.Activity> listActivities()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.Activity>(it, true);
    }

    public static org.semanticwb.process.Activity createActivity(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.process.Activity.createActivity(String.valueOf(id), model);
    }

    public static org.semanticwb.process.Activity getActivity(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.process.Activity)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.process.Activity createActivity(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.process.Activity)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeActivity(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasActivity(String id, org.semanticwb.model.SWBModel model)
    {
        return (getActivity(id, model)!=null);
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

   public static java.util.Iterator<org.semanticwb.process.Activity> listActivityByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.Activity> listActivityByModifiedBy(org.semanticwb.model.User modifiedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Activity> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
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

    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.Activity> listDependences()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.Activity>(getSemanticObject().listObjectProperties(swbps_hasActivityDependence));
    }

    public boolean hasDependence(org.semanticwb.process.Activity activity)
    {
        if(activity==null)return false;
        return getSemanticObject().hasObjectProperty(swbps_hasActivityDependence,activity.getSemanticObject());
    }

    public void addDependence(org.semanticwb.process.Activity value)
    {
        getSemanticObject().addObjectProperty(swbps_hasActivityDependence, value.getSemanticObject());
    }

    public void removeAllDependence()
    {
        getSemanticObject().removeProperty(swbps_hasActivityDependence);
    }

    public void removeDependence(org.semanticwb.process.Activity activity)
    {
        getSemanticObject().removeObjectProperty(swbps_hasActivityDependence,activity.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.process.Activity> listActivityByDependence(org.semanticwb.process.Activity hasactivitydependence,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_hasActivityDependence, hasactivitydependence.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.Activity> listActivityByDependence(org.semanticwb.process.Activity hasactivitydependence)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Activity> it=new org.semanticwb.model.GenericIterator(hasactivitydependence.getSemanticObject().getModel().listSubjects(swbps_hasActivityDependence,hasactivitydependence.getSemanticObject()));
       return it;
   }

    public org.semanticwb.process.Activity getDependence()
    {
         org.semanticwb.process.Activity ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbps_hasActivityDependence);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.Activity)obj.createGenericInstance();
         }
         return ret;
    }

    public int getExecutions()
    {
        return getSemanticObject().getIntProperty(swbps_activityExecutions);
    }

    public void setExecutions(int value)
    {
        getSemanticObject().setIntProperty(swbps_activityExecutions, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.Task> listTasks()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.Task>(getSemanticObject().listObjectProperties(swbps_hasTask));
    }

    public boolean hasTask(org.semanticwb.process.Task task)
    {
        if(task==null)return false;
        return getSemanticObject().hasObjectProperty(swbps_hasTask,task.getSemanticObject());
    }

    public void addTask(org.semanticwb.process.Task value)
    {
        getSemanticObject().addObjectProperty(swbps_hasTask, value.getSemanticObject());
    }

    public void removeAllTask()
    {
        getSemanticObject().removeProperty(swbps_hasTask);
    }

    public void removeTask(org.semanticwb.process.Task task)
    {
        getSemanticObject().removeObjectProperty(swbps_hasTask,task.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.process.Activity> listActivityByTask(org.semanticwb.process.Task hastask,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_hasTask, hastask.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.Activity> listActivityByTask(org.semanticwb.process.Task hastask)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Activity> it=new org.semanticwb.model.GenericIterator(hastask.getSemanticObject().getModel().listSubjects(swbps_hasTask,hastask.getSemanticObject()));
       return it;
   }

    public org.semanticwb.process.Task getTask()
    {
         org.semanticwb.process.Task ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbps_hasTask);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.Task)obj.createGenericInstance();
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

   public static java.util.Iterator<org.semanticwb.process.Activity> listActivityByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.Activity> listActivityByCreator(org.semanticwb.model.User creator)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Activity> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
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

    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

    public org.semanticwb.process.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
