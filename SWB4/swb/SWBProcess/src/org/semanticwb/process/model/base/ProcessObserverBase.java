package org.semanticwb.process.model.base;


public abstract class ProcessObserverBase extends org.semanticwb.process.model.ProcessElement implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swp_FlowNodeInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowNodeInstance");
    public static final org.semanticwb.platform.SemanticProperty swp_hasRuleObserverInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasRuleObserverInstance");
    public static final org.semanticwb.platform.SemanticClass swp_StartEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#StartEvent");
    public static final org.semanticwb.platform.SemanticProperty swp_hasSignalObserverNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasSignalObserverNode");
    public static final org.semanticwb.platform.SemanticProperty swp_hasSignalObserverInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasSignalObserverInstance");
    public static final org.semanticwb.platform.SemanticProperty swp_hasMessageObserverNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasMessageObserverNode");
    public static final org.semanticwb.platform.SemanticProperty swp_hasTimeObserverInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasTimeObserverInstance");
    public static final org.semanticwb.platform.SemanticProperty swp_hasRuleObserverNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasRuleObserverNode");
    public static final org.semanticwb.platform.SemanticProperty swp_hasTimeObserverNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasTimeObserverNode");
    public static final org.semanticwb.platform.SemanticProperty swp_hasMessageObserverInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasMessageObserverInstance");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessObserver=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessObserver");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessObserver");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObservers(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObservers()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver>(it, true);
        }

        public static org.semanticwb.process.model.ProcessObserver getProcessObserver(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessObserver)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.ProcessObserver createProcessObserver(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessObserver)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeProcessObserver(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasProcessObserver(String id, org.semanticwb.model.SWBModel model)
        {
            return (getProcessObserver(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByRuleObserverInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasRuleObserverInstance, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByRuleObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasRuleObserverInstance,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverBySignalObserverNode(org.semanticwb.process.model.StartEvent value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasSignalObserverNode, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverBySignalObserverNode(org.semanticwb.process.model.StartEvent value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasSignalObserverNode,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverBySignalObserverInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasSignalObserverInstance, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverBySignalObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasSignalObserverInstance,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByMessageObserverNode(org.semanticwb.process.model.StartEvent value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasMessageObserverNode, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByMessageObserverNode(org.semanticwb.process.model.StartEvent value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasMessageObserverNode,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByTimeObserverInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasTimeObserverInstance, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByTimeObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasTimeObserverInstance,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByRuleObserverNode(org.semanticwb.process.model.StartEvent value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasRuleObserverNode, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByRuleObserverNode(org.semanticwb.process.model.StartEvent value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasRuleObserverNode,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByTimeObserverNode(org.semanticwb.process.model.StartEvent value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasTimeObserverNode, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByTimeObserverNode(org.semanticwb.process.model.StartEvent value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasTimeObserverNode,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByMessageObserverInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasMessageObserverInstance, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByMessageObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasMessageObserverInstance,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ProcessObserverBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> listRuleObserverInstances()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance>(getSemanticObject().listObjectProperties(swp_hasRuleObserverInstance));
    }

    public boolean hasRuleObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasRuleObserverInstance,value.getSemanticObject());
        }
        return ret;
    }

    public void addRuleObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
    {
        getSemanticObject().addObjectProperty(swp_hasRuleObserverInstance, value.getSemanticObject());
    }

    public void removeAllRuleObserverInstance()
    {
        getSemanticObject().removeProperty(swp_hasRuleObserverInstance);
    }

    public void removeRuleObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
    {
        getSemanticObject().removeObjectProperty(swp_hasRuleObserverInstance,value.getSemanticObject());
    }

    public org.semanticwb.process.model.FlowNodeInstance getRuleObserverInstance()
    {
         org.semanticwb.process.model.FlowNodeInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasRuleObserverInstance);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FlowNodeInstance)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEvent> listSignalObserverNodes()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEvent>(getSemanticObject().listObjectProperties(swp_hasSignalObserverNode));
    }

    public boolean hasSignalObserverNode(org.semanticwb.process.model.StartEvent value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasSignalObserverNode,value.getSemanticObject());
        }
        return ret;
    }

    public void addSignalObserverNode(org.semanticwb.process.model.StartEvent value)
    {
        getSemanticObject().addObjectProperty(swp_hasSignalObserverNode, value.getSemanticObject());
    }

    public void removeAllSignalObserverNode()
    {
        getSemanticObject().removeProperty(swp_hasSignalObserverNode);
    }

    public void removeSignalObserverNode(org.semanticwb.process.model.StartEvent value)
    {
        getSemanticObject().removeObjectProperty(swp_hasSignalObserverNode,value.getSemanticObject());
    }

    public org.semanticwb.process.model.StartEvent getSignalObserverNode()
    {
         org.semanticwb.process.model.StartEvent ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasSignalObserverNode);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.StartEvent)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> listSignalObserverInstances()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance>(getSemanticObject().listObjectProperties(swp_hasSignalObserverInstance));
    }

    public boolean hasSignalObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasSignalObserverInstance,value.getSemanticObject());
        }
        return ret;
    }

    public void addSignalObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
    {
        getSemanticObject().addObjectProperty(swp_hasSignalObserverInstance, value.getSemanticObject());
    }

    public void removeAllSignalObserverInstance()
    {
        getSemanticObject().removeProperty(swp_hasSignalObserverInstance);
    }

    public void removeSignalObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
    {
        getSemanticObject().removeObjectProperty(swp_hasSignalObserverInstance,value.getSemanticObject());
    }

    public org.semanticwb.process.model.FlowNodeInstance getSignalObserverInstance()
    {
         org.semanticwb.process.model.FlowNodeInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasSignalObserverInstance);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FlowNodeInstance)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEvent> listMessageObserverNodes()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEvent>(getSemanticObject().listObjectProperties(swp_hasMessageObserverNode));
    }

    public boolean hasMessageObserverNode(org.semanticwb.process.model.StartEvent value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasMessageObserverNode,value.getSemanticObject());
        }
        return ret;
    }

    public void addMessageObserverNode(org.semanticwb.process.model.StartEvent value)
    {
        getSemanticObject().addObjectProperty(swp_hasMessageObserverNode, value.getSemanticObject());
    }

    public void removeAllMessageObserverNode()
    {
        getSemanticObject().removeProperty(swp_hasMessageObserverNode);
    }

    public void removeMessageObserverNode(org.semanticwb.process.model.StartEvent value)
    {
        getSemanticObject().removeObjectProperty(swp_hasMessageObserverNode,value.getSemanticObject());
    }

    public org.semanticwb.process.model.StartEvent getMessageObserverNode()
    {
         org.semanticwb.process.model.StartEvent ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasMessageObserverNode);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.StartEvent)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> listTimeObserverInstances()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance>(getSemanticObject().listObjectProperties(swp_hasTimeObserverInstance));
    }

    public boolean hasTimeObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasTimeObserverInstance,value.getSemanticObject());
        }
        return ret;
    }

    public void addTimeObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
    {
        getSemanticObject().addObjectProperty(swp_hasTimeObserverInstance, value.getSemanticObject());
    }

    public void removeAllTimeObserverInstance()
    {
        getSemanticObject().removeProperty(swp_hasTimeObserverInstance);
    }

    public void removeTimeObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
    {
        getSemanticObject().removeObjectProperty(swp_hasTimeObserverInstance,value.getSemanticObject());
    }

    public org.semanticwb.process.model.FlowNodeInstance getTimeObserverInstance()
    {
         org.semanticwb.process.model.FlowNodeInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasTimeObserverInstance);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FlowNodeInstance)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEvent> listRuleObserverNodes()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEvent>(getSemanticObject().listObjectProperties(swp_hasRuleObserverNode));
    }

    public boolean hasRuleObserverNode(org.semanticwb.process.model.StartEvent value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasRuleObserverNode,value.getSemanticObject());
        }
        return ret;
    }

    public void addRuleObserverNode(org.semanticwb.process.model.StartEvent value)
    {
        getSemanticObject().addObjectProperty(swp_hasRuleObserverNode, value.getSemanticObject());
    }

    public void removeAllRuleObserverNode()
    {
        getSemanticObject().removeProperty(swp_hasRuleObserverNode);
    }

    public void removeRuleObserverNode(org.semanticwb.process.model.StartEvent value)
    {
        getSemanticObject().removeObjectProperty(swp_hasRuleObserverNode,value.getSemanticObject());
    }

    public org.semanticwb.process.model.StartEvent getRuleObserverNode()
    {
         org.semanticwb.process.model.StartEvent ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasRuleObserverNode);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.StartEvent)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEvent> listTimeObserverNodes()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEvent>(getSemanticObject().listObjectProperties(swp_hasTimeObserverNode));
    }

    public boolean hasTimeObserverNode(org.semanticwb.process.model.StartEvent value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasTimeObserverNode,value.getSemanticObject());
        }
        return ret;
    }

    public void addTimeObserverNode(org.semanticwb.process.model.StartEvent value)
    {
        getSemanticObject().addObjectProperty(swp_hasTimeObserverNode, value.getSemanticObject());
    }

    public void removeAllTimeObserverNode()
    {
        getSemanticObject().removeProperty(swp_hasTimeObserverNode);
    }

    public void removeTimeObserverNode(org.semanticwb.process.model.StartEvent value)
    {
        getSemanticObject().removeObjectProperty(swp_hasTimeObserverNode,value.getSemanticObject());
    }

    public org.semanticwb.process.model.StartEvent getTimeObserverNode()
    {
         org.semanticwb.process.model.StartEvent ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasTimeObserverNode);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.StartEvent)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> listMessageObserverInstances()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance>(getSemanticObject().listObjectProperties(swp_hasMessageObserverInstance));
    }

    public boolean hasMessageObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasMessageObserverInstance,value.getSemanticObject());
        }
        return ret;
    }

    public void addMessageObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
    {
        getSemanticObject().addObjectProperty(swp_hasMessageObserverInstance, value.getSemanticObject());
    }

    public void removeAllMessageObserverInstance()
    {
        getSemanticObject().removeProperty(swp_hasMessageObserverInstance);
    }

    public void removeMessageObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
    {
        getSemanticObject().removeObjectProperty(swp_hasMessageObserverInstance,value.getSemanticObject());
    }

    public org.semanticwb.process.model.FlowNodeInstance getMessageObserverInstance()
    {
         org.semanticwb.process.model.FlowNodeInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasMessageObserverInstance);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FlowNodeInstance)obj.createGenericInstance();
         }
         return ret;
    }
}
