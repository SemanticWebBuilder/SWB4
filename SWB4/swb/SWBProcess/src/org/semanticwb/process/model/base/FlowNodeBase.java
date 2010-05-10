package org.semanticwb.process.model.base;


public abstract class FlowNodeBase extends org.semanticwb.process.model.GraphicalElement implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swp_ConnectionObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ConnectionObject");
    public static final org.semanticwb.platform.SemanticProperty swp_hasFromConnectionObjectInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasFromConnectionObjectInv");
    public static final org.semanticwb.platform.SemanticClass swp_FlowObjectInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowObjectInstance");
    public static final org.semanticwb.platform.SemanticProperty swp_hasFlowObjectInstansInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasFlowObjectInstansInv");
    public static final org.semanticwb.platform.SemanticProperty swp_hasToConnectionObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasToConnectionObject");
    public static final org.semanticwb.platform.SemanticClass swp_SubProcess=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SubProcess");
    public static final org.semanticwb.platform.SemanticProperty swp_parentProcessInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#parentProcessInv");
    public static final org.semanticwb.platform.SemanticClass swp_FlowNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowNode");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowNode");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode>(it, true);
        }

        public static org.semanticwb.process.model.FlowNode getFlowNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.FlowNode)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.FlowNode createFlowNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.FlowNode)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeFlowNode(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasFlowNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFlowNode(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByFromConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFromConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByFromConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFromConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByFlowObjectInstance(org.semanticwb.process.model.FlowObjectInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowObjectInstansInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByFlowObjectInstance(org.semanticwb.process.model.FlowObjectInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowObjectInstansInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByToConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasToConnectionObject, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByToConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasToConnectionObject,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByParentProcess(org.semanticwb.process.model.SubProcess value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parentProcessInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByParentProcess(org.semanticwb.process.model.SubProcess value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parentProcessInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public FlowNodeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject> listFromConnectionObjects()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject>(getSemanticObject().listObjectProperties(swp_hasFromConnectionObjectInv));
    }

    public boolean hasFromConnectionObject(org.semanticwb.process.model.ConnectionObject value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasFromConnectionObjectInv,value.getSemanticObject());
        }
        return ret;
    }

    public org.semanticwb.process.model.ConnectionObject getFromConnectionObject()
    {
         org.semanticwb.process.model.ConnectionObject ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasFromConnectionObjectInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ConnectionObject)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowObjectInstance> listFlowObjectInstances()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowObjectInstance>(getSemanticObject().listObjectProperties(swp_hasFlowObjectInstansInv));
    }

    public boolean hasFlowObjectInstance(org.semanticwb.process.model.FlowObjectInstance value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasFlowObjectInstansInv,value.getSemanticObject());
        }
        return ret;
    }

    public org.semanticwb.process.model.FlowObjectInstance getFlowObjectInstance()
    {
         org.semanticwb.process.model.FlowObjectInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasFlowObjectInstansInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FlowObjectInstance)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject> listToConnectionObjects()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject>(getSemanticObject().listObjectProperties(swp_hasToConnectionObject));
    }

    public boolean hasToConnectionObject(org.semanticwb.process.model.ConnectionObject value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasToConnectionObject,value.getSemanticObject());
        }
        return ret;
    }

    public void addToConnectionObject(org.semanticwb.process.model.ConnectionObject value)
    {
        getSemanticObject().addObjectProperty(swp_hasToConnectionObject, value.getSemanticObject());
    }

    public void removeAllToConnectionObject()
    {
        getSemanticObject().removeProperty(swp_hasToConnectionObject);
    }

    public void removeToConnectionObject(org.semanticwb.process.model.ConnectionObject value)
    {
        getSemanticObject().removeObjectProperty(swp_hasToConnectionObject,value.getSemanticObject());
    }

    public org.semanticwb.process.model.ConnectionObject getToConnectionObject()
    {
         org.semanticwb.process.model.ConnectionObject ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasToConnectionObject);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ConnectionObject)obj.createGenericInstance();
         }
         return ret;
    }

    public void setParentProcess(org.semanticwb.process.model.SubProcess value)
    {
        getSemanticObject().setObjectProperty(swp_parentProcessInv, value.getSemanticObject());
    }

    public void removeParentProcess()
    {
        getSemanticObject().removeProperty(swp_parentProcessInv);
    }

    public org.semanticwb.process.model.SubProcess getParentProcess()
    {
         org.semanticwb.process.model.SubProcess ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_parentProcessInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.SubProcess)obj.createGenericInstance();
         }
         return ret;
    }
}
