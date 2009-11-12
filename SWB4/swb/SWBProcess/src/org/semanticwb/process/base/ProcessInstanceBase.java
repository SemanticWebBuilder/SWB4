package org.semanticwb.process.base;


public class ProcessInstanceBase extends org.semanticwb.process.FlowObjectInstance implements org.semanticwb.process.ProcessTraceable,org.semanticwb.model.Traceable
{
       public static final org.semanticwb.platform.SemanticClass swbps_ProcessObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessObject");
       public static final org.semanticwb.platform.SemanticProperty swbps_hasProcessObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasProcessObject");
       public static final org.semanticwb.platform.SemanticClass swbps_FlowObjectInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowObjectInstance");
       public static final org.semanticwb.platform.SemanticProperty swbps_hasFlowObjectInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasFlowObjectInstance");
       public static final org.semanticwb.platform.SemanticClass swbps_ProcessInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessInstance");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessInstance");
    public static class ClassMgr
    {

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
   public static java.util.Iterator<org.semanticwb.process.ProcessInstance> listProcessInstanceByFlowObjectType(org.semanticwb.process.FlowObject flowobjecttype,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_flowObjectType, flowobjecttype.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.ProcessInstance> listProcessInstanceByFlowObjectType(org.semanticwb.process.FlowObject flowobjecttype)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessInstance> it=new org.semanticwb.model.GenericIterator(flowobjecttype.getSemanticObject().getModel().listSubjects(swbps_flowObjectType,flowobjecttype.getSemanticObject()));
       return it;
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
   public static java.util.Iterator<org.semanticwb.process.ProcessInstance> listProcessInstanceByParentProcessInstance(org.semanticwb.process.ProcessInstance parentprocessinstanceinv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_parentProcessInstanceInv, parentprocessinstanceinv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.ProcessInstance> listProcessInstanceByParentProcessInstance(org.semanticwb.process.ProcessInstance parentprocessinstanceinv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessInstance> it=new org.semanticwb.model.GenericIterator(parentprocessinstanceinv.getSemanticObject().getModel().listSubjects(swbps_parentProcessInstanceInv,parentprocessinstanceinv.getSemanticObject()));
       return it;
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
   public static java.util.Iterator<org.semanticwb.process.ProcessInstance> listProcessInstanceByFlowObjectInstance(org.semanticwb.process.FlowObjectInstance hasflowobjectinstance,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_hasFlowObjectInstance, hasflowobjectinstance.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.ProcessInstance> listProcessInstanceByFlowObjectInstance(org.semanticwb.process.FlowObjectInstance hasflowobjectinstance)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessInstance> it=new org.semanticwb.model.GenericIterator(hasflowobjectinstance.getSemanticObject().getModel().listSubjects(swbps_hasFlowObjectInstance,hasflowobjectinstance.getSemanticObject()));
       return it;
   }
    }

    public ProcessInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.FlowObjectInstance> listFlowObjectInstances()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.FlowObjectInstance>(getSemanticObject().listObjectProperties(swbps_hasFlowObjectInstance));
    }

    public boolean hasFlowObjectInstance(org.semanticwb.process.FlowObjectInstance flowobjectinstance)
    {
        if(flowobjectinstance==null)return false;
        return getSemanticObject().hasObjectProperty(swbps_hasFlowObjectInstance,flowobjectinstance.getSemanticObject());
    }

    public void addFlowObjectInstance(org.semanticwb.process.FlowObjectInstance value)
    {
        getSemanticObject().addObjectProperty(swbps_hasFlowObjectInstance, value.getSemanticObject());
    }

    public void removeAllFlowObjectInstance()
    {
        getSemanticObject().removeProperty(swbps_hasFlowObjectInstance);
    }

    public void removeFlowObjectInstance(org.semanticwb.process.FlowObjectInstance flowobjectinstance)
    {
        getSemanticObject().removeObjectProperty(swbps_hasFlowObjectInstance,flowobjectinstance.getSemanticObject());
    }


    public org.semanticwb.process.FlowObjectInstance getFlowObjectInstance()
    {
         org.semanticwb.process.FlowObjectInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbps_hasFlowObjectInstance);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.FlowObjectInstance)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.process.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
