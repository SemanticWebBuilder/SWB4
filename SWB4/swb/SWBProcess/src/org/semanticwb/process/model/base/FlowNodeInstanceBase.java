package org.semanticwb.process.model.base;


public abstract class FlowNodeInstanceBase extends org.semanticwb.model.SWBClass implements org.semanticwb.process.model.ProcessTraceable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty swp_execution=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#execution");
    public static final org.semanticwb.platform.SemanticClass swp_FlowNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowNode");
    public static final org.semanticwb.platform.SemanticProperty swp_flowNodeType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#flowNodeType");
    public static final org.semanticwb.platform.SemanticProperty swp_action=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#action");
    public static final org.semanticwb.platform.SemanticProperty swp_status=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#status");
    public static final org.semanticwb.platform.SemanticProperty swp_iteration=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#iteration");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessInstance");
    public static final org.semanticwb.platform.SemanticProperty swp_parentProcessInstanceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#parentProcessInstanceInv");
    public static final org.semanticwb.platform.SemanticClass swp_FlowNodeInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowNodeInstance");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowNodeInstance");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstances(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstances()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance>(it, true);
        }

        public static org.semanticwb.process.model.FlowNodeInstance createFlowNodeInstance(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.FlowNodeInstance.ClassMgr.createFlowNodeInstance(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.FlowNodeInstance getFlowNodeInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.FlowNodeInstance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.FlowNodeInstance createFlowNodeInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.FlowNodeInstance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeFlowNodeInstance(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasFlowNodeInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFlowNodeInstance(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstanceByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstanceByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstanceByFlowNodeType(org.semanticwb.process.model.FlowNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_flowNodeType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstanceByFlowNodeType(org.semanticwb.process.model.FlowNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_flowNodeType,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstanceByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstanceByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstanceByParentProcessInstance(org.semanticwb.process.model.ProcessInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parentProcessInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstanceByParentProcessInstance(org.semanticwb.process.model.ProcessInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parentProcessInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstanceByEndedby(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_endedby, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstanceByEndedby(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_endedby,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public FlowNodeInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public java.util.Date getEnded()
    {
        return getSemanticObject().getDateProperty(swp_ended);
    }

    public void setEnded(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swp_ended, value);
    }

    public int getExecution()
    {
        return getSemanticObject().getIntProperty(swp_execution);
    }

    public void setExecution(int value)
    {
        getSemanticObject().setIntProperty(swp_execution, value);
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

    public void setFlowNodeType(org.semanticwb.process.model.FlowNode value)
    {
        getSemanticObject().setObjectProperty(swp_flowNodeType, value.getSemanticObject());
    }

    public void removeFlowNodeType()
    {
        getSemanticObject().removeProperty(swp_flowNodeType);
    }

    public org.semanticwb.process.model.FlowNode getFlowNodeType()
    {
         org.semanticwb.process.model.FlowNode ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_flowNodeType);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FlowNode)obj.createGenericInstance();
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

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
