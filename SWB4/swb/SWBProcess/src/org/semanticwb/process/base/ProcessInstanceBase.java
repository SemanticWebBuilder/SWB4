package org.semanticwb.process.base;


public class ProcessInstanceBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable,org.semanticwb.process.ProcessTraceable
{
    public static final org.semanticwb.platform.SemanticClass swbps_ActivityInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ActivityInstance");
    public static final org.semanticwb.platform.SemanticProperty swbps_hasActivityInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasActivityInstance");
    public static final org.semanticwb.platform.SemanticClass swbps_Process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Process");
    public static final org.semanticwb.platform.SemanticProperty swbps_processType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processType");
    public static final org.semanticwb.platform.SemanticClass swbps_ProcessObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessObject");
    public static final org.semanticwb.platform.SemanticProperty swbps_hasProcessObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasProcessObject");
    public static final org.semanticwb.platform.SemanticProperty swbps_processStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processStatus");
    public static final org.semanticwb.platform.SemanticClass swbps_ProcessInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessInstance");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessInstance");

    public ProcessInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.process.ProcessInstance> listProcessInstances(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessInstance>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.process.ProcessInstance> listProcessInstances()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessInstance>(it, true);
    }

    public static org.semanticwb.process.ProcessInstance createProcessInstance(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.process.ProcessInstance.createProcessInstance(String.valueOf(id), model);
    }

    public static org.semanticwb.process.ProcessInstance getProcessInstance(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.process.ProcessInstance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.process.ProcessInstance createProcessInstance(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.process.ProcessInstance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeProcessInstance(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasProcessInstance(String id, org.semanticwb.model.SWBModel model)
    {
        return (getProcessInstance(id, model)!=null);
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

   public static java.util.Iterator<org.semanticwb.process.ProcessInstance> listProcessInstanceByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.ProcessInstance> listProcessInstanceByModifiedBy(org.semanticwb.model.User modifiedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessInstance> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
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

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.ActivityInstance> listActivityInstances()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ActivityInstance>(getSemanticObject().listObjectProperties(swbps_hasActivityInstance));
    }

    public boolean hasActivityInstance(org.semanticwb.process.ActivityInstance activityinstance)
    {
        if(activityinstance==null)return false;
        return getSemanticObject().hasObjectProperty(swbps_hasActivityInstance,activityinstance.getSemanticObject());
    }

    public void addActivityInstance(org.semanticwb.process.ActivityInstance value)
    {
        getSemanticObject().addObjectProperty(swbps_hasActivityInstance, value.getSemanticObject());
    }

    public void removeAllActivityInstance()
    {
        getSemanticObject().removeProperty(swbps_hasActivityInstance);
    }

    public void removeActivityInstance(org.semanticwb.process.ActivityInstance activityinstance)
    {
        getSemanticObject().removeObjectProperty(swbps_hasActivityInstance,activityinstance.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.process.ProcessInstance> listProcessInstanceByActivityInstance(org.semanticwb.process.ActivityInstance hasactivityinstance,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_hasActivityInstance, hasactivityinstance.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.ProcessInstance> listProcessInstanceByActivityInstance(org.semanticwb.process.ActivityInstance hasactivityinstance)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessInstance> it=new org.semanticwb.model.GenericIterator(hasactivityinstance.getSemanticObject().getModel().listSubjects(swbps_hasActivityInstance,hasactivityinstance.getSemanticObject()));
       return it;
   }

    public org.semanticwb.process.ActivityInstance getActivityInstance()
    {
         org.semanticwb.process.ActivityInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbps_hasActivityInstance);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.ActivityInstance)obj.createGenericInstance();
         }
         return ret;
    }

    public java.util.Date getEnded()
    {
        return getSemanticObject().getDateProperty(swbps_ended);
    }

    public void setEnded(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swbps_ended, value);
    }

    public void setProcessType(org.semanticwb.process.Process value)
    {
        getSemanticObject().setObjectProperty(swbps_processType, value.getSemanticObject());
    }

    public void removeProcessType()
    {
        getSemanticObject().removeProperty(swbps_processType);
    }

   public static java.util.Iterator<org.semanticwb.process.ProcessInstance> listProcessInstanceByProcessType(org.semanticwb.process.Process processtype,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_processType, processtype.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.ProcessInstance> listProcessInstanceByProcessType(org.semanticwb.process.Process processtype)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessInstance> it=new org.semanticwb.model.GenericIterator(processtype.getSemanticObject().getModel().listSubjects(swbps_processType,processtype.getSemanticObject()));
       return it;
   }

    public org.semanticwb.process.Process getProcessType()
    {
         org.semanticwb.process.Process ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbps_processType);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.Process)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessObject> listProcessObjects()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessObject>(getSemanticObject().listObjectProperties(swbps_hasProcessObject));
    }

    public boolean hasProcessObject(org.semanticwb.process.ProcessObject processobject)
    {
        if(processobject==null)return false;
        return getSemanticObject().hasObjectProperty(swbps_hasProcessObject,processobject.getSemanticObject());
    }

    public void addProcessObject(org.semanticwb.process.ProcessObject value)
    {
        getSemanticObject().addObjectProperty(swbps_hasProcessObject, value.getSemanticObject());
    }

    public void removeAllProcessObject()
    {
        getSemanticObject().removeProperty(swbps_hasProcessObject);
    }

    public void removeProcessObject(org.semanticwb.process.ProcessObject processobject)
    {
        getSemanticObject().removeObjectProperty(swbps_hasProcessObject,processobject.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.process.ProcessInstance> listProcessInstanceByProcessObject(org.semanticwb.process.ProcessObject hasprocessobject,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_hasProcessObject, hasprocessobject.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.ProcessInstance> listProcessInstanceByProcessObject(org.semanticwb.process.ProcessObject hasprocessobject)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessInstance> it=new org.semanticwb.model.GenericIterator(hasprocessobject.getSemanticObject().getModel().listSubjects(swbps_hasProcessObject,hasprocessobject.getSemanticObject()));
       return it;
   }

    public org.semanticwb.process.ProcessObject getProcessObject()
    {
         org.semanticwb.process.ProcessObject ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbps_hasProcessObject);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.ProcessObject)obj.createGenericInstance();
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

   public static java.util.Iterator<org.semanticwb.process.ProcessInstance> listProcessInstanceByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.ProcessInstance> listProcessInstanceByCreator(org.semanticwb.model.User creator)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessInstance> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
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

    public int getStatus()
    {
        return getSemanticObject().getIntProperty(swbps_processStatus);
    }

    public void setStatus(int value)
    {
        getSemanticObject().setIntProperty(swbps_processStatus, value);
    }

    public void setEndedby(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swbps_endedby, value.getSemanticObject());
    }

    public void removeEndedby()
    {
        getSemanticObject().removeProperty(swbps_endedby);
    }

   public static java.util.Iterator<org.semanticwb.process.ProcessInstance> listProcessInstanceByEndedby(org.semanticwb.model.User endedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_endedby, endedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.ProcessInstance> listProcessInstanceByEndedby(org.semanticwb.model.User endedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessInstance> it=new org.semanticwb.model.GenericIterator(endedby.getSemanticObject().getModel().listSubjects(swbps_endedby,endedby.getSemanticObject()));
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
