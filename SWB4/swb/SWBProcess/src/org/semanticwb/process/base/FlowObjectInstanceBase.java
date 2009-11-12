package org.semanticwb.process.base;


public class FlowObjectInstanceBase extends org.semanticwb.model.SWBClass implements org.semanticwb.process.ProcessTraceable,org.semanticwb.model.Traceable
{
       public static final org.semanticwb.platform.SemanticClass swbps_FlowObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowObject");
       public static final org.semanticwb.platform.SemanticProperty swbps_flowObjectType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#flowObjectType");
       public static final org.semanticwb.platform.SemanticProperty swbps_status=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#status");
       public static final org.semanticwb.platform.SemanticClass swbps_ProcessInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessInstance");
       public static final org.semanticwb.platform.SemanticProperty swbps_parentProcessInstanceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#parentProcessInstanceInv");
       public static final org.semanticwb.platform.SemanticClass swbps_FlowObjectInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowObjectInstance");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowObjectInstance");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.FlowObjectInstance> listFlowObjectInstances(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.FlowObjectInstance>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.FlowObjectInstance> listFlowObjectInstances()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.FlowObjectInstance>(it, true);
       }

       public static org.semanticwb.process.FlowObjectInstance createFlowObjectInstance(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.FlowObjectInstance.ClassMgr.createFlowObjectInstance(String.valueOf(id), model);
       }

       public static org.semanticwb.process.FlowObjectInstance getFlowObjectInstance(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.FlowObjectInstance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.FlowObjectInstance createFlowObjectInstance(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.FlowObjectInstance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeFlowObjectInstance(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasFlowObjectInstance(String id, org.semanticwb.model.SWBModel model)
       {
           return (getFlowObjectInstance(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.FlowObjectInstance> listFlowObjectInstanceByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.FlowObjectInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.FlowObjectInstance> listFlowObjectInstanceByModifiedBy(org.semanticwb.model.User modifiedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.FlowObjectInstance> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.FlowObjectInstance> listFlowObjectInstanceByFlowObjectType(org.semanticwb.process.FlowObject flowobjecttype,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.FlowObjectInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_flowObjectType, flowobjecttype.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.FlowObjectInstance> listFlowObjectInstanceByFlowObjectType(org.semanticwb.process.FlowObject flowobjecttype)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.FlowObjectInstance> it=new org.semanticwb.model.GenericIterator(flowobjecttype.getSemanticObject().getModel().listSubjects(swbps_flowObjectType,flowobjecttype.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.FlowObjectInstance> listFlowObjectInstanceByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.FlowObjectInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.FlowObjectInstance> listFlowObjectInstanceByCreator(org.semanticwb.model.User creator)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.FlowObjectInstance> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.FlowObjectInstance> listFlowObjectInstanceByParentProcessInstance(org.semanticwb.process.ProcessInstance parentprocessinstanceinv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.FlowObjectInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_parentProcessInstanceInv, parentprocessinstanceinv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.FlowObjectInstance> listFlowObjectInstanceByParentProcessInstance(org.semanticwb.process.ProcessInstance parentprocessinstanceinv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.FlowObjectInstance> it=new org.semanticwb.model.GenericIterator(parentprocessinstanceinv.getSemanticObject().getModel().listSubjects(swbps_parentProcessInstanceInv,parentprocessinstanceinv.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.FlowObjectInstance> listFlowObjectInstanceByEndedby(org.semanticwb.model.User endedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.FlowObjectInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_endedby, endedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.FlowObjectInstance> listFlowObjectInstanceByEndedby(org.semanticwb.model.User endedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.FlowObjectInstance> it=new org.semanticwb.model.GenericIterator(endedby.getSemanticObject().getModel().listSubjects(swbps_endedby,endedby.getSemanticObject()));
       return it;
   }
    }

    public FlowObjectInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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

    public void setFlowObjectType(org.semanticwb.process.FlowObject value)
    {
        getSemanticObject().setObjectProperty(swbps_flowObjectType, value.getSemanticObject());
    }

    public void removeFlowObjectType()
    {
        getSemanticObject().removeProperty(swbps_flowObjectType);
    }


    public org.semanticwb.process.FlowObject getFlowObjectType()
    {
         org.semanticwb.process.FlowObject ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbps_flowObjectType);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.FlowObject)obj.createGenericInstance();
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

    public int getStatus()
    {
        return getSemanticObject().getIntProperty(swbps_status);
    }

    public void setStatus(int value)
    {
        getSemanticObject().setIntProperty(swbps_status, value);
    }

    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
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

    public void setParentProcessInstance(org.semanticwb.process.ProcessInstance value)
    {
        getSemanticObject().setObjectProperty(swbps_parentProcessInstanceInv, value.getSemanticObject());
    }

    public void removeParentProcessInstance()
    {
        getSemanticObject().removeProperty(swbps_parentProcessInstanceInv);
    }


    public org.semanticwb.process.ProcessInstance getParentProcessInstance()
    {
         org.semanticwb.process.ProcessInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbps_parentProcessInstanceInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.ProcessInstance)obj.createGenericInstance();
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
