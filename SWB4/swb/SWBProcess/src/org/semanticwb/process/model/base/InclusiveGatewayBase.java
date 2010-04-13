package org.semanticwb.process.model.base;


public abstract class InclusiveGatewayBase extends org.semanticwb.process.model.Gateway implements org.semanticwb.process.model.Auditable,org.semanticwb.process.model.DefaultFlowable,org.semanticwb.process.model.Monitorable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Documentable
{
    public static final org.semanticwb.platform.SemanticClass swp_InclusiveGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#InclusiveGateway");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#InclusiveGateway");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGateways(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGateways()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway>(it, true);
        }

        public static org.semanticwb.process.model.InclusiveGateway createInclusiveGateway(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.InclusiveGateway.ClassMgr.createInclusiveGateway(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.InclusiveGateway getInclusiveGateway(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.InclusiveGateway)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.InclusiveGateway createInclusiveGateway(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.InclusiveGateway)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeInclusiveGateway(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasInclusiveGateway(String id, org.semanticwb.model.SWBModel model)
        {
            return (getInclusiveGateway(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByIncomingSequenceFlow(org.semanticwb.process.model.SequenceFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasIncomingSequenceFlow, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByIncomingSequenceFlow(org.semanticwb.process.model.SequenceFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasIncomingSequenceFlow,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByOutgoingSequenceFlow(org.semanticwb.process.model.SequenceFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutgoingSequenceFlow, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByOutgoingSequenceFlow(org.semanticwb.process.model.SequenceFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutgoingSequenceFlow,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByAuditing(org.semanticwb.process.model.Auditing value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_auditing, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByAuditing(org.semanticwb.process.model.Auditing value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_auditing,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByDefaultSequenceFlow(org.semanticwb.process.model.SequenceFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_defaultSequenceFlow, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByDefaultSequenceFlow(org.semanticwb.process.model.SequenceFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_defaultSequenceFlow,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByMonitoring(org.semanticwb.process.model.Monitoring value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByMonitoring(org.semanticwb.process.model.Monitoring value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGatewayByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InclusiveGateway> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public InclusiveGatewayBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setDefaultSequenceFlow(org.semanticwb.process.model.SequenceFlow value)
    {
        getSemanticObject().setObjectProperty(swp_defaultSequenceFlow, value.getSemanticObject());
    }

    public void removeDefaultSequenceFlow()
    {
        getSemanticObject().removeProperty(swp_defaultSequenceFlow);
    }

    public org.semanticwb.process.model.SequenceFlow getDefaultSequenceFlow()
    {
         org.semanticwb.process.model.SequenceFlow ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_defaultSequenceFlow);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.SequenceFlow)obj.createGenericInstance();
         }
         return ret;
    }
}
