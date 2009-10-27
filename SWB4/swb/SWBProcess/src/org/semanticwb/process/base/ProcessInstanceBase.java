package org.semanticwb.process.base;


public class ProcessInstanceBase extends org.semanticwb.process.ActivityInstance implements org.semanticwb.process.ProcessTraceable,org.semanticwb.model.Traceable
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
       public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
       public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
       public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
       public static final org.semanticwb.platform.SemanticClass swbps_ActivityInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ActivityInstance");
       public static final org.semanticwb.platform.SemanticProperty swbps_hasActivityInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasActivityInstance");
       public static final org.semanticwb.platform.SemanticProperty swbps_ended=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#ended");
       public static final org.semanticwb.platform.SemanticClass swbps_Process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Process");
       public static final org.semanticwb.platform.SemanticProperty swbps_processType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processType");
       public static final org.semanticwb.platform.SemanticClass swbps_ProcessObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessObject");
       public static final org.semanticwb.platform.SemanticProperty swbps_hasProcessObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasProcessObject");
       public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
       public static final org.semanticwb.platform.SemanticProperty swbps_status=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#status");
       public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
       public static final org.semanticwb.platform.SemanticClass swbps_ProcessInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessInstance");
       public static final org.semanticwb.platform.SemanticProperty swbps_parentProcessInstanceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#parentProcessInstanceInv");
       public static final org.semanticwb.platform.SemanticProperty swbps_endedby=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#endedby");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessInstance");

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
           return org.semanticwb.process.ProcessInstance.ClassMgr.createProcessInstance(String.valueOf(id), model);
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
    }

    public ProcessInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.ActivityInstance> listActivityInstances()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ActivityInstance>(getSemanticObject().listObjectProperties(ClassMgr.swbps_hasActivityInstance));
    }

    public boolean hasActivityInstance(org.semanticwb.process.ActivityInstance activityinstance)
    {
        if(activityinstance==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swbps_hasActivityInstance,activityinstance.getSemanticObject());
    }

    public void addActivityInstance(org.semanticwb.process.ActivityInstance value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swbps_hasActivityInstance, value.getSemanticObject());
    }

    public void removeAllActivityInstance()
    {
        getSemanticObject().removeProperty(ClassMgr.swbps_hasActivityInstance);
    }

    public void removeActivityInstance(org.semanticwb.process.ActivityInstance activityinstance)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swbps_hasActivityInstance,activityinstance.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.process.ProcessInstance> listProcessInstanceByActivityInstance(org.semanticwb.process.ActivityInstance hasactivityinstance,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_hasActivityInstance, hasactivityinstance.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.ProcessInstance> listProcessInstanceByActivityInstance(org.semanticwb.process.ActivityInstance hasactivityinstance)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessInstance> it=new org.semanticwb.model.GenericIterator(hasactivityinstance.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_hasActivityInstance,hasactivityinstance.getSemanticObject()));
       return it;
   }

    public org.semanticwb.process.ActivityInstance getActivityInstance()
    {
         org.semanticwb.process.ActivityInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbps_hasActivityInstance);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.ActivityInstance)obj.createGenericInstance();
         }
         return ret;
    }

    public void setProcessType(org.semanticwb.process.Process value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swbps_processType, value.getSemanticObject());
    }

    public void removeProcessType()
    {
        getSemanticObject().removeProperty(ClassMgr.swbps_processType);
    }

   public static java.util.Iterator<org.semanticwb.process.ProcessInstance> listProcessInstanceByProcessType(org.semanticwb.process.Process processtype,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_processType, processtype.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.ProcessInstance> listProcessInstanceByProcessType(org.semanticwb.process.Process processtype)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessInstance> it=new org.semanticwb.model.GenericIterator(processtype.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_processType,processtype.getSemanticObject()));
       return it;
   }

    public org.semanticwb.process.Process getProcessType()
    {
         org.semanticwb.process.Process ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbps_processType);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.Process)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessObject> listProcessObjects()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessObject>(getSemanticObject().listObjectProperties(ClassMgr.swbps_hasProcessObject));
    }

    public boolean hasProcessObject(org.semanticwb.process.ProcessObject processobject)
    {
        if(processobject==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swbps_hasProcessObject,processobject.getSemanticObject());
    }

    public void addProcessObject(org.semanticwb.process.ProcessObject value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swbps_hasProcessObject, value.getSemanticObject());
    }

    public void removeAllProcessObject()
    {
        getSemanticObject().removeProperty(ClassMgr.swbps_hasProcessObject);
    }

    public void removeProcessObject(org.semanticwb.process.ProcessObject processobject)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swbps_hasProcessObject,processobject.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.process.ProcessInstance> listProcessInstanceByProcessObject(org.semanticwb.process.ProcessObject hasprocessobject,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_hasProcessObject, hasprocessobject.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.ProcessInstance> listProcessInstanceByProcessObject(org.semanticwb.process.ProcessObject hasprocessobject)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessInstance> it=new org.semanticwb.model.GenericIterator(hasprocessobject.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_hasProcessObject,hasprocessobject.getSemanticObject()));
       return it;
   }

    public org.semanticwb.process.ProcessObject getProcessObject()
    {
         org.semanticwb.process.ProcessObject ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbps_hasProcessObject);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.ProcessObject)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.process.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
