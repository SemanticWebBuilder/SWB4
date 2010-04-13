package org.semanticwb.process.model.base;


public abstract class CompensationFlowConnectorBase extends org.semanticwb.process.model.BPMNConnector 
{
    public static final org.semanticwb.platform.SemanticClass swp_CompensationFlowConnector=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CompensationFlowConnector");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CompensationFlowConnector");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.CompensationFlowConnector> listCompensationFlowConnectors(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationFlowConnector>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.CompensationFlowConnector> listCompensationFlowConnectors()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationFlowConnector>(it, true);
        }

        public static org.semanticwb.process.model.CompensationFlowConnector getCompensationFlowConnector(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CompensationFlowConnector)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.CompensationFlowConnector createCompensationFlowConnector(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CompensationFlowConnector)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeCompensationFlowConnector(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasCompensationFlowConnector(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCompensationFlowConnector(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.CompensationFlowConnector> listCompensationFlowConnectorByConnectorLabel(org.semanticwb.process.model.BPMNLabel value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationFlowConnector> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_connectorLabel, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CompensationFlowConnector> listCompensationFlowConnectorByConnectorLabel(org.semanticwb.process.model.BPMNLabel value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationFlowConnector> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_connectorLabel,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CompensationFlowConnector> listCompensationFlowConnectorByContextType(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationFlowConnector> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_contextType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CompensationFlowConnector> listCompensationFlowConnectorByContextType(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationFlowConnector> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_contextType,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CompensationFlowConnector> listCompensationFlowConnectorByConnectorSource(org.semanticwb.process.model.BPMNNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationFlowConnector> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_connectorSource, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CompensationFlowConnector> listCompensationFlowConnectorByConnectorSource(org.semanticwb.process.model.BPMNNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationFlowConnector> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_connectorSource,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CompensationFlowConnector> listCompensationFlowConnectorByConnectorTarget(org.semanticwb.process.model.BPMNNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationFlowConnector> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_connectorTarget, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CompensationFlowConnector> listCompensationFlowConnectorByConnectorTarget(org.semanticwb.process.model.BPMNNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationFlowConnector> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_connectorTarget,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public CompensationFlowConnectorBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
