package org.semanticwb.process.model.base;


public abstract class FlowObjectInstanceBase extends org.semanticwb.model.SWBClass implements org.semanticwb.process.model.Observable,org.semanticwb.model.Traceable,org.semanticwb.process.model.ProcessTraceable
{
       public static final org.semanticwb.platform.SemanticClass swp_FlowObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#FlowObject");
       public static final org.semanticwb.platform.SemanticProperty swp_flowObjectType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#flowObjectType");
       public static final org.semanticwb.platform.SemanticProperty swp_action=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#action");
       public static final org.semanticwb.platform.SemanticProperty swp_iteration=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#iteration");
       public static final org.semanticwb.platform.SemanticProperty swp_execution=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#execution");
       public static final org.semanticwb.platform.SemanticClass swp_FlowObjectInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#FlowObjectInstance");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#FlowObjectInstance");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.FlowObjectInstance> listFlowObjectInstances(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowObjectInstance>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.FlowObjectInstance> listFlowObjectInstances()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowObjectInstance>(it, true);
       }

       public static org.semanticwb.process.model.FlowObjectInstance createFlowObjectInstance(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.FlowObjectInstance.ClassMgr.createFlowObjectInstance(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.FlowObjectInstance getFlowObjectInstance(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.FlowObjectInstance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.FlowObjectInstance createFlowObjectInstance(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.FlowObjectInstance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeFlowObjectInstance(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasFlowObjectInstance(String id, org.semanticwb.model.SWBModel model)
       {
           return (getFlowObjectInstance(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.FlowObjectInstance> listFlowObjectInstanceByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowObjectInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.FlowObjectInstance> listFlowObjectInstanceByModifiedBy(org.semanticwb.model.User modifiedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowObjectInstance> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.FlowObjectInstance> listFlowObjectInstanceByFlowObjectType(org.semanticwb.process.model.FlowObject flowobjecttype,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowObjectInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_flowObjectType, flowobjecttype.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.FlowObjectInstance> listFlowObjectInstanceByFlowObjectType(org.semanticwb.process.model.FlowObject flowobjecttype)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowObjectInstance> it=new org.semanticwb.model.GenericIterator(flowobjecttype.getSemanticObject().getModel().listSubjects(swp_flowObjectType,flowobjecttype.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.FlowObjectInstance> listFlowObjectInstanceByEndedBy(org.semanticwb.model.User endedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowObjectInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_endedBy, endedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.FlowObjectInstance> listFlowObjectInstanceByEndedBy(org.semanticwb.model.User endedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowObjectInstance> it=new org.semanticwb.model.GenericIterator(endedby.getSemanticObject().getModel().listSubjects(swp_endedBy,endedby.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.FlowObjectInstance> listFlowObjectInstanceByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowObjectInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.FlowObjectInstance> listFlowObjectInstanceByCreator(org.semanticwb.model.User creator)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowObjectInstance> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
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

    public void setFlowObjectType(org.semanticwb.process.model.FlowObject value)
    {
        getSemanticObject().setObjectProperty(swp_flowObjectType, value.getSemanticObject());
    }

    public void removeFlowObjectType()
    {
        getSemanticObject().removeProperty(swp_flowObjectType);
    }


    public org.semanticwb.process.model.FlowObject getFlowObjectType()
    {
         org.semanticwb.process.model.FlowObject ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_flowObjectType);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FlowObject)obj.createGenericInstance();
         }
         return ret;
    }

    public java.util.Date getEnded()
    {
        return getSemanticObject().getDateProperty(swp_ended);
    }

    public void setEnded(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swp_ended, value);
    }

    public void setEndedBy(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swp_endedBy, value.getSemanticObject());
    }

    public void removeEndedBy()
    {
        getSemanticObject().removeProperty(swp_endedBy);
    }


    public org.semanticwb.model.User getEndedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_endedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public String getAction()
    {
        return getSemanticObject().getProperty(swp_action);
    }

    public void setAction(String value)
    {
        getSemanticObject().setProperty(swp_action, value);
    }

    public String getStatus()
    {
        return getSemanticObject().getProperty(swp_status);
    }

    public void setStatus(String value)
    {
        getSemanticObject().setProperty(swp_status, value);
    }

    public int getIteration()
    {
        return getSemanticObject().getIntProperty(swp_iteration);
    }

    public void setIteration(int value)
    {
        getSemanticObject().setIntProperty(swp_iteration, value);
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

    public int getExecution()
    {
        return getSemanticObject().getIntProperty(swp_execution);
    }

    public void setExecution(int value)
    {
        getSemanticObject().setIntProperty(swp_execution, value);
    }
}
