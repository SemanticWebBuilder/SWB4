package org.semanticwb.process.model.base;


public abstract class DataOutputAssociationBase extends org.semanticwb.process.model.DataAssociation implements org.semanticwb.process.model.Auditable,org.semanticwb.process.model.Monitorable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Documentable
{
    public static final org.semanticwb.platform.SemanticClass swp_DataOutputAssociation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataOutputAssociation");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataOutputAssociation");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.DataOutputAssociation> listDataOutputAssociations(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutputAssociation>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutputAssociation> listDataOutputAssociations()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutputAssociation>(it, true);
        }

        public static org.semanticwb.process.model.DataOutputAssociation createDataOutputAssociation(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.DataOutputAssociation.ClassMgr.createDataOutputAssociation(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.DataOutputAssociation getDataOutputAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataOutputAssociation)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.DataOutputAssociation createDataOutputAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataOutputAssociation)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeDataOutputAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasDataOutputAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDataOutputAssociation(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutputAssociation> listDataOutputAssociationByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutputAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutputAssociation> listDataOutputAssociationByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutputAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutputAssociation> listDataOutputAssociationByDataTargetRef(org.semanticwb.process.model.InformationAssociable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutputAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_dataTargetRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutputAssociation> listDataOutputAssociationByDataTargetRef(org.semanticwb.process.model.InformationAssociable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutputAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_dataTargetRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutputAssociation> listDataOutputAssociationByAuditing(org.semanticwb.process.model.Auditing value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutputAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_auditing, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutputAssociation> listDataOutputAssociationByAuditing(org.semanticwb.process.model.Auditing value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutputAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_auditing,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutputAssociation> listDataOutputAssociationByAssignment(org.semanticwb.process.model.Assignment value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutputAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasAssignment, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutputAssociation> listDataOutputAssociationByAssignment(org.semanticwb.process.model.Assignment value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutputAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasAssignment,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutputAssociation> listDataOutputAssociationByTransformation(org.semanticwb.process.model.Expression value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutputAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_transformation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutputAssociation> listDataOutputAssociationByTransformation(org.semanticwb.process.model.Expression value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutputAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_transformation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutputAssociation> listDataOutputAssociationByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutputAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutputAssociation> listDataOutputAssociationByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutputAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutputAssociation> listDataOutputAssociationByDataSourceRef(org.semanticwb.process.model.InformationAssociable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutputAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_dataSourceRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutputAssociation> listDataOutputAssociationByDataSourceRef(org.semanticwb.process.model.InformationAssociable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutputAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_dataSourceRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutputAssociation> listDataOutputAssociationByMonitoring(org.semanticwb.process.model.Monitoring value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutputAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutputAssociation> listDataOutputAssociationByMonitoring(org.semanticwb.process.model.Monitoring value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutputAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutputAssociation> listDataOutputAssociationByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutputAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutputAssociation> listDataOutputAssociationByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutputAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public DataOutputAssociationBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
