package org.semanticwb.process.model.base;


public abstract class NormalFlowBase extends org.semanticwb.process.model.SequenceFlow implements org.semanticwb.process.model.Documentable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Auditable,org.semanticwb.process.model.Monitorable
{
    public static final org.semanticwb.platform.SemanticClass swp_NormalFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#NormalFlow");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#NormalFlow");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.NormalFlow> listNormalFlows(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalFlow>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.NormalFlow> listNormalFlows()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalFlow>(it, true);
        }

        public static org.semanticwb.process.model.NormalFlow createNormalFlow(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.NormalFlow.ClassMgr.createNormalFlow(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.NormalFlow getNormalFlow(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.NormalFlow)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.NormalFlow createNormalFlow(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.NormalFlow)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeNormalFlow(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasNormalFlow(String id, org.semanticwb.model.SWBModel model)
        {
            return (getNormalFlow(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.NormalFlow> listNormalFlowBySourceRef(org.semanticwb.process.model.FlowNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_sourceRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.NormalFlow> listNormalFlowBySourceRef(org.semanticwb.process.model.FlowNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_sourceRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.NormalFlow> listNormalFlowByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.NormalFlow> listNormalFlowByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.NormalFlow> listNormalFlowByAuditing(org.semanticwb.process.model.Auditing value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_auditing, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.NormalFlow> listNormalFlowByAuditing(org.semanticwb.process.model.Auditing value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_auditing,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.NormalFlow> listNormalFlowByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.NormalFlow> listNormalFlowByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.NormalFlow> listNormalFlowByMonitoring(org.semanticwb.process.model.Monitoring value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.NormalFlow> listNormalFlowByMonitoring(org.semanticwb.process.model.Monitoring value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.NormalFlow> listNormalFlowByTargetRef(org.semanticwb.process.model.FlowNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_targetRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.NormalFlow> listNormalFlowByTargetRef(org.semanticwb.process.model.FlowNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_targetRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.NormalFlow> listNormalFlowByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.NormalFlow> listNormalFlowByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public NormalFlowBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
