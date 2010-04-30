package org.semanticwb.process.model.base;


public abstract class FlowNodeBase extends org.semanticwb.process.model.FlowElement implements org.semanticwb.process.model.Documentable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Auditable,org.semanticwb.process.model.Monitorable
{
    public static final org.semanticwb.platform.SemanticClass swp_SequenceFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#SequenceFlow");
    public static final org.semanticwb.platform.SemanticProperty swp_hasIncomingSequenceFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasIncomingSequenceFlow");
    public static final org.semanticwb.platform.SemanticProperty swp_hasOutgoingSequenceFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasOutgoingSequenceFlow");
    public static final org.semanticwb.platform.SemanticClass swp_FlowNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#FlowNode");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#FlowNode");

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

        public static org.semanticwb.process.model.FlowNode createFlowNode(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.FlowNode.ClassMgr.createFlowNode(String.valueOf(id), model);
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

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByIncomingSequenceFlow(org.semanticwb.process.model.SequenceFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasIncomingSequenceFlow, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByIncomingSequenceFlow(org.semanticwb.process.model.SequenceFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasIncomingSequenceFlow,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByOutgoingSequenceFlow(org.semanticwb.process.model.SequenceFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutgoingSequenceFlow, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByOutgoingSequenceFlow(org.semanticwb.process.model.SequenceFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutgoingSequenceFlow,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByAuditing(org.semanticwb.process.model.Auditing value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_auditing, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByAuditing(org.semanticwb.process.model.Auditing value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_auditing,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByMonitoring(org.semanticwb.process.model.Monitoring value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByMonitoring(org.semanticwb.process.model.Monitoring value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodeByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public FlowNodeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> listIncomingSequenceFlows()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow>(getSemanticObject().listObjectProperties(swp_hasIncomingSequenceFlow));
    }

    public boolean hasIncomingSequenceFlow(org.semanticwb.process.model.SequenceFlow value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasIncomingSequenceFlow,value.getSemanticObject());
        }
        return ret;
    }

    public void addIncomingSequenceFlow(org.semanticwb.process.model.SequenceFlow value)
    {
        getSemanticObject().addObjectProperty(swp_hasIncomingSequenceFlow, value.getSemanticObject());
    }

    public void removeAllIncomingSequenceFlow()
    {
        getSemanticObject().removeProperty(swp_hasIncomingSequenceFlow);
    }

    public void removeIncomingSequenceFlow(org.semanticwb.process.model.SequenceFlow value)
    {
        getSemanticObject().removeObjectProperty(swp_hasIncomingSequenceFlow,value.getSemanticObject());
    }

    public org.semanticwb.process.model.SequenceFlow getIncomingSequenceFlow()
    {
         org.semanticwb.process.model.SequenceFlow ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasIncomingSequenceFlow);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.SequenceFlow)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> listOutgoingSequenceFlows()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow>(getSemanticObject().listObjectProperties(swp_hasOutgoingSequenceFlow));
    }

    public boolean hasOutgoingSequenceFlow(org.semanticwb.process.model.SequenceFlow value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasOutgoingSequenceFlow,value.getSemanticObject());
        }
        return ret;
    }

    public void addOutgoingSequenceFlow(org.semanticwb.process.model.SequenceFlow value)
    {
        getSemanticObject().addObjectProperty(swp_hasOutgoingSequenceFlow, value.getSemanticObject());
    }

    public void removeAllOutgoingSequenceFlow()
    {
        getSemanticObject().removeProperty(swp_hasOutgoingSequenceFlow);
    }

    public void removeOutgoingSequenceFlow(org.semanticwb.process.model.SequenceFlow value)
    {
        getSemanticObject().removeObjectProperty(swp_hasOutgoingSequenceFlow,value.getSemanticObject());
    }

    public org.semanticwb.process.model.SequenceFlow getOutgoingSequenceFlow()
    {
         org.semanticwb.process.model.SequenceFlow ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasOutgoingSequenceFlow);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.SequenceFlow)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
