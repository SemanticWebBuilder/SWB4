package org.semanticwb.process.base;


public class TaskBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.UserGroupable,org.semanticwb.model.Roleable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty swbps_taskExecutions=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#taskExecutions");
    public static final org.semanticwb.platform.SemanticProperty swbps_keepOpen=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#keepOpen");
    public static final org.semanticwb.platform.SemanticClass swbps_Task=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Task");
    public static final org.semanticwb.platform.SemanticProperty swbps_hasTaskDependence=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasTaskDependence");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Task");

    public TaskBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.process.Task> listTasks(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.Task>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.process.Task> listTasks()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.Task>(it, true);
    }

    public static org.semanticwb.process.Task createTask(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.process.Task.createTask(String.valueOf(id), model);
    }

    public static org.semanticwb.process.Task getTask(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.process.Task)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.process.Task createTask(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.process.Task)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeTask(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasTask(String id, org.semanticwb.model.SWBModel model)
    {
        return (getTask(id, model)!=null);
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

   public static java.util.Iterator<org.semanticwb.process.Task> listTaskByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Task> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.Task> listTaskByModifiedBy(org.semanticwb.model.User modifiedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Task> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
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

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup> listUserGroups()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup>(getSemanticObject().listObjectProperties(swb_hasUserGroup));
    }

    public boolean hasUserGroup(org.semanticwb.model.UserGroup usergroup)
    {
        if(usergroup==null)return false;
        return getSemanticObject().hasObjectProperty(swb_hasUserGroup,usergroup.getSemanticObject());
    }

    public void addUserGroup(org.semanticwb.model.UserGroup value)
    {
        getSemanticObject().addObjectProperty(swb_hasUserGroup, value.getSemanticObject());
    }

    public void removeAllUserGroup()
    {
        getSemanticObject().removeProperty(swb_hasUserGroup);
    }

    public void removeUserGroup(org.semanticwb.model.UserGroup usergroup)
    {
        getSemanticObject().removeObjectProperty(swb_hasUserGroup,usergroup.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.process.Task> listTaskByUserGroup(org.semanticwb.model.UserGroup hasusergroup,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Task> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasUserGroup, hasusergroup.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.Task> listTaskByUserGroup(org.semanticwb.model.UserGroup hasusergroup)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Task> it=new org.semanticwb.model.GenericIterator(hasusergroup.getSemanticObject().getModel().listSubjects(swb_hasUserGroup,hasusergroup.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.UserGroup getUserGroup()
    {
         org.semanticwb.model.UserGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasUserGroup);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserGroup)obj.createGenericInstance();
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

    public int getExecutions()
    {
        return getSemanticObject().getIntProperty(swbps_taskExecutions);
    }

    public void setExecutions(int value)
    {
        getSemanticObject().setIntProperty(swbps_taskExecutions, value);
    }

    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

   public static java.util.Iterator<org.semanticwb.process.Task> listTaskByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Task> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.Task> listTaskByCreator(org.semanticwb.model.User creator)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Task> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
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

    public boolean isKeepOpen()
    {
        return getSemanticObject().getBooleanProperty(swbps_keepOpen);
    }

    public void setKeepOpen(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbps_keepOpen, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Role> listRoles()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Role>(getSemanticObject().listObjectProperties(swb_hasRole));
    }

    public boolean hasRole(org.semanticwb.model.Role role)
    {
        if(role==null)return false;
        return getSemanticObject().hasObjectProperty(swb_hasRole,role.getSemanticObject());
    }

    public void addRole(org.semanticwb.model.Role value)
    {
        getSemanticObject().addObjectProperty(swb_hasRole, value.getSemanticObject());
    }

    public void removeAllRole()
    {
        getSemanticObject().removeProperty(swb_hasRole);
    }

    public void removeRole(org.semanticwb.model.Role role)
    {
        getSemanticObject().removeObjectProperty(swb_hasRole,role.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.process.Task> listTaskByRole(org.semanticwb.model.Role hasrole,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Task> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasRole, hasrole.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.Task> listTaskByRole(org.semanticwb.model.Role hasrole)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Task> it=new org.semanticwb.model.GenericIterator(hasrole.getSemanticObject().getModel().listSubjects(swb_hasRole,hasrole.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.Role getRole()
    {
         org.semanticwb.model.Role ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasRole);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Role)obj.createGenericInstance();
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

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.Task> listDependences()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.Task>(getSemanticObject().listObjectProperties(swbps_hasTaskDependence));
    }

    public boolean hasDependence(org.semanticwb.process.Task task)
    {
        if(task==null)return false;
        return getSemanticObject().hasObjectProperty(swbps_hasTaskDependence,task.getSemanticObject());
    }

    public void addDependence(org.semanticwb.process.Task value)
    {
        getSemanticObject().addObjectProperty(swbps_hasTaskDependence, value.getSemanticObject());
    }

    public void removeAllDependence()
    {
        getSemanticObject().removeProperty(swbps_hasTaskDependence);
    }

    public void removeDependence(org.semanticwb.process.Task task)
    {
        getSemanticObject().removeObjectProperty(swbps_hasTaskDependence,task.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.process.Task> listTaskByDependence(org.semanticwb.process.Task hastaskdependence,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Task> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_hasTaskDependence, hastaskdependence.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.Task> listTaskByDependence(org.semanticwb.process.Task hastaskdependence)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Task> it=new org.semanticwb.model.GenericIterator(hastaskdependence.getSemanticObject().getModel().listSubjects(swbps_hasTaskDependence,hastaskdependence.getSemanticObject()));
       return it;
   }

    public org.semanticwb.process.Task getDependence()
    {
         org.semanticwb.process.Task ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbps_hasTaskDependence);
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
