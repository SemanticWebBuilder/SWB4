package org.semanticwb.process.base;


public class AutoTaskBase extends org.semanticwb.model.base.GenericObjectBase implements org.semanticwb.process.FlowObject,org.semanticwb.process.Activity,org.semanticwb.model.Traceable,org.semanticwb.process.Task,org.semanticwb.model.Descriptiveable
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
       public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
       public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
       public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
       public static final org.semanticwb.platform.SemanticClass swbps_ConnectionObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ConnectionObject");
       public static final org.semanticwb.platform.SemanticProperty swbps_hasFromConnectionObjectInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasFromConnectionObjectInv");
       public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
       public static final org.semanticwb.platform.SemanticClass swbps_FlowObjectInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowObjectInstance");
       public static final org.semanticwb.platform.SemanticProperty swbps_hasFlowObjectInstansInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasFlowObjectInstansInv");
       public static final org.semanticwb.platform.SemanticProperty swbps_hasToConnectionObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasToConnectionObject");
       public static final org.semanticwb.platform.SemanticClass swbps_Process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Process");
       public static final org.semanticwb.platform.SemanticProperty swbps_parentProcessInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#parentProcessInv");
       public static final org.semanticwb.platform.SemanticProperty swbps_executions=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#executions");
       public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
       public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
       public static final org.semanticwb.platform.SemanticProperty swbps_keepOpen=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#keepOpen");
       public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
       public static final org.semanticwb.platform.SemanticClass swbps_AutoTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#AutoTask");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#AutoTask");

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
           return org.semanticwb.process.AutoTask.ClassMgr.createAutoTask(String.valueOf(id), model);
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

    public AutoTaskBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(ClassMgr.swb_created);
    }

    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(ClassMgr.swb_created, value);
    }

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swb_modifiedBy, value.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_modifiedBy);
    }

   public static java.util.Iterator<org.semanticwb.process.AutoTask> listAutoTaskByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.AutoTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_modifiedBy, modifiedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.AutoTask> listAutoTaskByModifiedBy(org.semanticwb.model.User modifiedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.AutoTask> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(ClassMgr.swb_modifiedBy,modifiedby.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(ClassMgr.swb_title);
    }

    public void setTitle(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(ClassMgr.swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(ClassMgr.swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(ClassMgr.swb_title, title, lang);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.ConnectionObject> listFromConnectionObjects()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ConnectionObject>(getSemanticObject().listObjectProperties(ClassMgr.swbps_hasFromConnectionObjectInv));
    }

    public boolean hasFromConnectionObject(org.semanticwb.process.ConnectionObject connectionobject)
    {
        if(connectionobject==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swbps_hasFromConnectionObjectInv,connectionobject.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.process.AutoTask> listAutoTaskByFromConnectionObject(org.semanticwb.process.ConnectionObject hasfromconnectionobjectinv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.AutoTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_hasFromConnectionObjectInv, hasfromconnectionobjectinv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.AutoTask> listAutoTaskByFromConnectionObject(org.semanticwb.process.ConnectionObject hasfromconnectionobjectinv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.AutoTask> it=new org.semanticwb.model.GenericIterator(hasfromconnectionobjectinv.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_hasFromConnectionObjectInv,hasfromconnectionobjectinv.getSemanticObject()));
       return it;
   }

    public org.semanticwb.process.ConnectionObject getFromConnectionObject()
    {
         org.semanticwb.process.ConnectionObject ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbps_hasFromConnectionObjectInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.ConnectionObject)obj.createGenericInstance();
         }
         return ret;
    }

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(ClassMgr.swb_updated);
    }

    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(ClassMgr.swb_updated, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.FlowObjectInstance> listFlowObjectInstances()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.FlowObjectInstance>(getSemanticObject().listObjectProperties(ClassMgr.swbps_hasFlowObjectInstansInv));
    }

    public boolean hasFlowObjectInstance(org.semanticwb.process.FlowObjectInstance flowobjectinstance)
    {
        if(flowobjectinstance==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swbps_hasFlowObjectInstansInv,flowobjectinstance.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.process.AutoTask> listAutoTaskByFlowObjectInstance(org.semanticwb.process.FlowObjectInstance hasflowobjectinstansinv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.AutoTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_hasFlowObjectInstansInv, hasflowobjectinstansinv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.AutoTask> listAutoTaskByFlowObjectInstance(org.semanticwb.process.FlowObjectInstance hasflowobjectinstansinv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.AutoTask> it=new org.semanticwb.model.GenericIterator(hasflowobjectinstansinv.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_hasFlowObjectInstansInv,hasflowobjectinstansinv.getSemanticObject()));
       return it;
   }

    public org.semanticwb.process.FlowObjectInstance getFlowObjectInstance()
    {
         org.semanticwb.process.FlowObjectInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbps_hasFlowObjectInstansInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.FlowObjectInstance)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.ConnectionObject> listToConnectionObjects()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ConnectionObject>(getSemanticObject().listObjectProperties(ClassMgr.swbps_hasToConnectionObject));
    }

    public boolean hasToConnectionObject(org.semanticwb.process.ConnectionObject connectionobject)
    {
        if(connectionobject==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swbps_hasToConnectionObject,connectionobject.getSemanticObject());
    }

    public void addToConnectionObject(org.semanticwb.process.ConnectionObject value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swbps_hasToConnectionObject, value.getSemanticObject());
    }

    public void removeAllToConnectionObject()
    {
        getSemanticObject().removeProperty(ClassMgr.swbps_hasToConnectionObject);
    }

    public void removeToConnectionObject(org.semanticwb.process.ConnectionObject connectionobject)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swbps_hasToConnectionObject,connectionobject.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.process.AutoTask> listAutoTaskByToConnectionObject(org.semanticwb.process.ConnectionObject hastoconnectionobject,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.AutoTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_hasToConnectionObject, hastoconnectionobject.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.AutoTask> listAutoTaskByToConnectionObject(org.semanticwb.process.ConnectionObject hastoconnectionobject)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.AutoTask> it=new org.semanticwb.model.GenericIterator(hastoconnectionobject.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_hasToConnectionObject,hastoconnectionobject.getSemanticObject()));
       return it;
   }

    public org.semanticwb.process.ConnectionObject getToConnectionObject()
    {
         org.semanticwb.process.ConnectionObject ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbps_hasToConnectionObject);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.ConnectionObject)obj.createGenericInstance();
         }
         return ret;
    }

    public void setParentProcess(org.semanticwb.process.Process value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swbps_parentProcessInv, value.getSemanticObject());
    }

    public void removeParentProcess()
    {
        getSemanticObject().removeProperty(ClassMgr.swbps_parentProcessInv);
    }

   public static java.util.Iterator<org.semanticwb.process.AutoTask> listAutoTaskByParentProcess(org.semanticwb.process.Process parentprocessinv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.AutoTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_parentProcessInv, parentprocessinv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.AutoTask> listAutoTaskByParentProcess(org.semanticwb.process.Process parentprocessinv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.AutoTask> it=new org.semanticwb.model.GenericIterator(parentprocessinv.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_parentProcessInv,parentprocessinv.getSemanticObject()));
       return it;
   }

    public org.semanticwb.process.Process getParentProcess()
    {
         org.semanticwb.process.Process ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbps_parentProcessInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.Process)obj.createGenericInstance();
         }
         return ret;
    }

    public int getExecutions()
    {
        return getSemanticObject().getIntProperty(ClassMgr.swbps_executions);
    }

    public void setExecutions(int value)
    {
        getSemanticObject().setIntProperty(ClassMgr.swbps_executions, value);
    }

    public boolean isValid()
    {
        //Override this method in AutoTask object
        return getSemanticObject().getBooleanProperty(ClassMgr.swb_valid,false);
    }

    public void setValid(boolean value)
    {
        //Override this method in AutoTask object
        getSemanticObject().setBooleanProperty(ClassMgr.swb_valid, value,false);
    }

    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swb_creator, value.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_creator);
    }

   public static java.util.Iterator<org.semanticwb.process.AutoTask> listAutoTaskByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.AutoTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_creator, creator.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.AutoTask> listAutoTaskByCreator(org.semanticwb.model.User creator)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.AutoTask> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(ClassMgr.swb_creator,creator.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isKeepOpen()
    {
        return getSemanticObject().getBooleanProperty(ClassMgr.swbps_keepOpen);
    }

    public void setKeepOpen(boolean value)
    {
        getSemanticObject().setBooleanProperty(ClassMgr.swbps_keepOpen, value);
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(ClassMgr.swb_description);
    }

    public void setDescription(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(ClassMgr.swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(ClassMgr.swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(ClassMgr.swb_description, description, lang);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);
    }

    public org.semanticwb.process.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
