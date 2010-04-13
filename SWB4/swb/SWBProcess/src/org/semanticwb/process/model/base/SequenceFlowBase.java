package org.semanticwb.process.model.base;


public abstract class SequenceFlowBase extends org.semanticwb.process.model.FlowElement implements org.semanticwb.process.model.Auditable,org.semanticwb.process.model.Monitorable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Documentable
{
    public static final org.semanticwb.platform.SemanticClass swp_FlowNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#FlowNode");
    public static final org.semanticwb.platform.SemanticProperty swp_sourceRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#sourceRef");
    public static final org.semanticwb.platform.SemanticProperty swp_inmediate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#inmediate");
    public static final org.semanticwb.platform.SemanticProperty swp_targetRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#targetRef");
    public static final org.semanticwb.platform.SemanticClass swp_SequenceFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#SequenceFlow");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#SequenceFlow");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlows(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlows()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow>(it, true);
        }

        public static org.semanticwb.process.model.SequenceFlow createSequenceFlow(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.SequenceFlow.ClassMgr.createSequenceFlow(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.SequenceFlow getSequenceFlow(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.SequenceFlow)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.SequenceFlow createSequenceFlow(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.SequenceFlow)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeSequenceFlow(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasSequenceFlow(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSequenceFlow(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowBySourceRef(org.semanticwb.process.model.FlowNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_sourceRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowBySourceRef(org.semanticwb.process.model.FlowNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_sourceRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowByAuditing(org.semanticwb.process.model.Auditing value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_auditing, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowByAuditing(org.semanticwb.process.model.Auditing value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_auditing,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowByMonitoring(org.semanticwb.process.model.Monitoring value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowByMonitoring(org.semanticwb.process.model.Monitoring value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowByTargetRef(org.semanticwb.process.model.FlowNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_targetRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowByTargetRef(org.semanticwb.process.model.FlowNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_targetRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public SequenceFlowBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setSourceRef(org.semanticwb.process.model.FlowNode value)
    {
        getSemanticObject().setObjectProperty(swp_sourceRef, value.getSemanticObject());
    }

    public void removeSourceRef()
    {
        getSemanticObject().removeProperty(swp_sourceRef);
    }

    public org.semanticwb.process.model.FlowNode getSourceRef()
    {
         org.semanticwb.process.model.FlowNode ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_sourceRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FlowNode)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isInmediate()
    {
        return getSemanticObject().getBooleanProperty(swp_inmediate);
    }

    public void setInmediate(boolean value)
    {
        getSemanticObject().setBooleanProperty(swp_inmediate, value);
    }

    public void setTargetRef(org.semanticwb.process.model.FlowNode value)
    {
        getSemanticObject().setObjectProperty(swp_targetRef, value.getSemanticObject());
    }

    public void removeTargetRef()
    {
        getSemanticObject().removeProperty(swp_targetRef);
    }

    public org.semanticwb.process.model.FlowNode getTargetRef()
    {
         org.semanticwb.process.model.FlowNode ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_targetRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FlowNode)obj.createGenericInstance();
         }
         return ret;
    }
}
