package org.semanticwb.process.model.base;


public abstract class SubProcessInstanceBase extends org.semanticwb.process.model.FlowObjectInstance implements org.semanticwb.model.Traceable,org.semanticwb.process.model.ProcessTraceable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_FlowObjectInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#FlowObjectInstance");
       public static final org.semanticwb.platform.SemanticProperty swp_hasFlowObjectInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasFlowObjectInstance");
       public static final org.semanticwb.platform.SemanticClass swp_SubProcessInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#SubProcessInstance");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#SubProcessInstance");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstances(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstances()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance>(it, true);
       }

       public static org.semanticwb.process.model.SubProcessInstance createSubProcessInstance(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.SubProcessInstance.ClassMgr.createSubProcessInstance(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.SubProcessInstance getSubProcessInstance(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.SubProcessInstance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.SubProcessInstance createSubProcessInstance(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.SubProcessInstance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeSubProcessInstance(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasSubProcessInstance(String id, org.semanticwb.model.SWBModel model)
       {
           return (getSubProcessInstance(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceByModifiedBy(org.semanticwb.model.User modifiedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceByFlowObjectInstance(org.semanticwb.process.model.FlowObjectInstance hasflowobjectinstance,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasFlowObjectInstance, hasflowobjectinstance.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceByFlowObjectInstance(org.semanticwb.process.model.FlowObjectInstance hasflowobjectinstance)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(hasflowobjectinstance.getSemanticObject().getModel().listSubjects(swp_hasFlowObjectInstance,hasflowobjectinstance.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceByFlowObjectType(org.semanticwb.process.model.FlowObject flowobjecttype,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_flowObjectType, flowobjecttype.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceByFlowObjectType(org.semanticwb.process.model.FlowObject flowobjecttype)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(flowobjecttype.getSemanticObject().getModel().listSubjects(swp_flowObjectType,flowobjecttype.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceByCreator(org.semanticwb.model.User creator)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceByEndedBy(org.semanticwb.model.User endedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_endedBy, endedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstanceByEndedBy(org.semanticwb.model.User endedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcessInstance> it=new org.semanticwb.model.GenericIterator(endedby.getSemanticObject().getModel().listSubjects(swp_endedBy,endedby.getSemanticObject()));
       return it;
   }
    }

    public SubProcessInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowObjectInstance> listFlowObjectInstances()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowObjectInstance>(getSemanticObject().listObjectProperties(swp_hasFlowObjectInstance));
    }

    public boolean hasFlowObjectInstance(org.semanticwb.process.model.FlowObjectInstance flowobjectinstance)
    {
        if(flowobjectinstance==null)return false;
        return getSemanticObject().hasObjectProperty(swp_hasFlowObjectInstance,flowobjectinstance.getSemanticObject());
    }

    public void addFlowObjectInstance(org.semanticwb.process.model.FlowObjectInstance value)
    {
        getSemanticObject().addObjectProperty(swp_hasFlowObjectInstance, value.getSemanticObject());
    }

    public void removeAllFlowObjectInstance()
    {
        getSemanticObject().removeProperty(swp_hasFlowObjectInstance);
    }

    public void removeFlowObjectInstance(org.semanticwb.process.model.FlowObjectInstance flowobjectinstance)
    {
        getSemanticObject().removeObjectProperty(swp_hasFlowObjectInstance,flowobjectinstance.getSemanticObject());
    }


    public org.semanticwb.process.model.FlowObjectInstance getFlowObjectInstance()
    {
         org.semanticwb.process.model.FlowObjectInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasFlowObjectInstance);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FlowObjectInstance)obj.createGenericInstance();
         }
         return ret;
    }
}
