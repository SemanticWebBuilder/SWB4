package org.semanticwb.process.model.base;


public abstract class DataAssociationConnectorBase extends org.semanticwb.process.model.BPMNConnector 
{
    public static final org.semanticwb.platform.SemanticClass swp_DataAssociationConnector=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataAssociationConnector");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataAssociationConnector");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.DataAssociationConnector> listDataAssociationConnectors(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataAssociationConnector>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataAssociationConnector> listDataAssociationConnectors()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataAssociationConnector>(it, true);
        }

        public static org.semanticwb.process.model.DataAssociationConnector getDataAssociationConnector(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataAssociationConnector)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.DataAssociationConnector createDataAssociationConnector(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataAssociationConnector)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeDataAssociationConnector(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasDataAssociationConnector(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDataAssociationConnector(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataAssociationConnector> listDataAssociationConnectorByConnectorLabel(org.semanticwb.process.model.BPMNLabel value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataAssociationConnector> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_connectorLabel, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataAssociationConnector> listDataAssociationConnectorByConnectorLabel(org.semanticwb.process.model.BPMNLabel value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataAssociationConnector> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_connectorLabel,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataAssociationConnector> listDataAssociationConnectorByContextType(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataAssociationConnector> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_contextType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataAssociationConnector> listDataAssociationConnectorByContextType(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataAssociationConnector> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_contextType,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataAssociationConnector> listDataAssociationConnectorByConnectorSource(org.semanticwb.process.model.BPMNNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataAssociationConnector> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_connectorSource, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataAssociationConnector> listDataAssociationConnectorByConnectorSource(org.semanticwb.process.model.BPMNNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataAssociationConnector> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_connectorSource,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataAssociationConnector> listDataAssociationConnectorByConnectorTarget(org.semanticwb.process.model.BPMNNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataAssociationConnector> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_connectorTarget, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataAssociationConnector> listDataAssociationConnectorByConnectorTarget(org.semanticwb.process.model.BPMNNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataAssociationConnector> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_connectorTarget,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public DataAssociationConnectorBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
