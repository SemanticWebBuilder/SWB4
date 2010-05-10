package org.semanticwb.process.model.base;


public abstract class FlowObjectInstanceBase extends org.semanticwb.model.SWBClass implements org.semanticwb.process.model.ProcessTraceable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swp_FlowNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowNode");
    public static final org.semanticwb.platform.SemanticProperty swp_flowObjectType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#flowObjectType");
    public static final org.semanticwb.platform.SemanticProperty swp_action=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#action");
    public static final org.semanticwb.platform.SemanticProperty swp_status=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#status");
    public static final org.semanticwb.platform.SemanticProperty swp_iteration=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#iteration");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessInstance");
    public static final org.semanticwb.platform.SemanticProperty swp_parentProcessInstanceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#parentProcessInstanceInv");
    public static final org.semanticwb.platform.SemanticProperty swp_execution=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#execution");
    public static final org.semanticwb.platform.SemanticClass swp_FlowObjectInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowObjectInstance");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowObjectInstance");

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

        public static java.util.Iterator<org.semanticwb.process.model.FlowObjectInstance> listFlowObjectInstanceByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowObjectInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowObjectInstance> listFlowObjectInstanceByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowObjectInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowObjectInstance> listFlowObjectInstanceByFlowObjectType(org.semanticwb.process.model.FlowNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowObjectInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_flowObjectType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowObjectInstance> listFlowObjectInstanceByFlowObjectType(org.semanticwb.process.model.FlowNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowObjectInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_flowObjectType,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowObjectInstance> listFlowObjectInstanceByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowObjectInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowObjectInstance> listFlowObjectInstanceByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowObjectInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowObjectInstance> listFlowObjectInstanceByParentProcessInstance(org.semanticwb.process.model.ProcessInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowObjectInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parentProcessInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowObjectInstance> listFlowObjectInstanceByParentProcessInstance(org.semanticwb.process.model.ProcessInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowObjectInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parentProcessInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowObjectInstance> listFlowObjectInstanceByEndedby(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowObjectInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_endedby, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowObjectInstance> listFlowObjectInstanceByEndedby(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowObjectInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_endedby,value.getSemanticObject(),sclass));
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

    public void setFlowObjectType(org.semanticwb.process.model.FlowNode value)
    {
        getSemanticObject().setObjectProperty(swp_flowObjectType, value.getSemanticObject());
    }

    public void removeFlowObjectType()
    {
        getSemanticObject().removeProperty(swp_flowObjectType);
    }

    public org.semanticwb.process.model.FlowNode getFlowObjectType()
    {
         org.semanticwb.process.model.FlowNode ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_flowObjectType);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FlowNode)obj.createGenericInstance();
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

    public String getAction()
    {
        return getSemanticObject().getProperty(swp_action);
    }

    public void setAction(String value)
    {
        getSemanticObject().setProperty(swp_action, value);
    }

    public int getStatus()
    {
        return getSemanticObject().getIntProperty(swp_status);
    }

    public void setStatus(int value)
    {
        getSemanticObject().setIntProperty(swp_status, value);
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

    public void setParentProcessInstance(org.semanticwb.process.model.ProcessInstance value)
    {
        getSemanticObject().setObjectProperty(swp_parentProcessInstanceInv, value.getSemanticObject());
    }

    public void removeParentProcessInstance()
    {
        getSemanticObject().removeProperty(swp_parentProcessInstanceInv);
    }

    public org.semanticwb.process.model.ProcessInstance getParentProcessInstance()
    {
         org.semanticwb.process.model.ProcessInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_parentProcessInstanceInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ProcessInstance)obj.createGenericInstance();
         }
         return ret;
    }

    public void setEndedby(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swp_endedby, value.getSemanticObject());
    }

    public void removeEndedby()
    {
        getSemanticObject().removeProperty(swp_endedby);
    }

    public org.semanticwb.model.User getEndedby()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_endedby);
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

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
