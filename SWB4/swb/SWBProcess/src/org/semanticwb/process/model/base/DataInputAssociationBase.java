package org.semanticwb.process.model.base;


public abstract class DataInputAssociationBase extends org.semanticwb.process.model.DataAssociation implements org.semanticwb.process.model.Documentable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Auditable,org.semanticwb.process.model.Monitorable
{
    public static final org.semanticwb.platform.SemanticClass swp_DataInputAssociation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataInputAssociation");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataInputAssociation");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.DataInputAssociation> listDataInputAssociations(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInputAssociation>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInputAssociation> listDataInputAssociations()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInputAssociation>(it, true);
        }

        public static org.semanticwb.process.model.DataInputAssociation createDataInputAssociation(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.DataInputAssociation.ClassMgr.createDataInputAssociation(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.DataInputAssociation getDataInputAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataInputAssociation)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.DataInputAssociation createDataInputAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataInputAssociation)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeDataInputAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasDataInputAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDataInputAssociation(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInputAssociation> listDataInputAssociationByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInputAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInputAssociation> listDataInputAssociationByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInputAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInputAssociation> listDataInputAssociationByDataTargetRef(org.semanticwb.process.model.InformationAssociable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInputAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_dataTargetRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInputAssociation> listDataInputAssociationByDataTargetRef(org.semanticwb.process.model.InformationAssociable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInputAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_dataTargetRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInputAssociation> listDataInputAssociationByAuditing(org.semanticwb.process.model.Auditing value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInputAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_auditing, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInputAssociation> listDataInputAssociationByAuditing(org.semanticwb.process.model.Auditing value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInputAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_auditing,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInputAssociation> listDataInputAssociationByAssignment(org.semanticwb.process.model.Assignment value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInputAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasAssignment, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInputAssociation> listDataInputAssociationByAssignment(org.semanticwb.process.model.Assignment value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInputAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasAssignment,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInputAssociation> listDataInputAssociationByTransformation(org.semanticwb.process.model.Expression value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInputAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_transformation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInputAssociation> listDataInputAssociationByTransformation(org.semanticwb.process.model.Expression value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInputAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_transformation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInputAssociation> listDataInputAssociationByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInputAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInputAssociation> listDataInputAssociationByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInputAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInputAssociation> listDataInputAssociationByDataSourceRef(org.semanticwb.process.model.InformationAssociable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInputAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_dataSourceRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInputAssociation> listDataInputAssociationByDataSourceRef(org.semanticwb.process.model.InformationAssociable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInputAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_dataSourceRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInputAssociation> listDataInputAssociationByMonitoring(org.semanticwb.process.model.Monitoring value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInputAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInputAssociation> listDataInputAssociationByMonitoring(org.semanticwb.process.model.Monitoring value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInputAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInputAssociation> listDataInputAssociationByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInputAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInputAssociation> listDataInputAssociationByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInputAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public DataInputAssociationBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
