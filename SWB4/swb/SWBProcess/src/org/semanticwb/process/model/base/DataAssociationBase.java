package org.semanticwb.process.model.base;


public abstract class DataAssociationBase extends org.semanticwb.process.model.FlowElement implements org.semanticwb.process.model.Documentable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Auditable,org.semanticwb.process.model.Monitorable
{
    public static final org.semanticwb.platform.SemanticClass swp_InformationAssociable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#InformationAssociable");
    public static final org.semanticwb.platform.SemanticProperty swp_dataTargetRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#dataTargetRef");
    public static final org.semanticwb.platform.SemanticClass swp_Assignment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Assignment");
    public static final org.semanticwb.platform.SemanticProperty swp_hasAssignment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasAssignment");
    public static final org.semanticwb.platform.SemanticClass swp_Expression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Expression");
    public static final org.semanticwb.platform.SemanticProperty swp_transformation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#transformation");
    public static final org.semanticwb.platform.SemanticProperty swp_dataSourceRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#dataSourceRef");
    public static final org.semanticwb.platform.SemanticClass swp_DataAssociation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataAssociation");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataAssociation");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.DataAssociation> listDataAssociations(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataAssociation>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataAssociation> listDataAssociations()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataAssociation>(it, true);
        }

        public static org.semanticwb.process.model.DataAssociation createDataAssociation(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.DataAssociation.ClassMgr.createDataAssociation(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.DataAssociation getDataAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataAssociation)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.DataAssociation createDataAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataAssociation)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeDataAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasDataAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDataAssociation(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataAssociation> listDataAssociationByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataAssociation> listDataAssociationByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataAssociation> listDataAssociationByDataTargetRef(org.semanticwb.process.model.InformationAssociable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_dataTargetRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataAssociation> listDataAssociationByDataTargetRef(org.semanticwb.process.model.InformationAssociable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_dataTargetRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataAssociation> listDataAssociationByAuditing(org.semanticwb.process.model.Auditing value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_auditing, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataAssociation> listDataAssociationByAuditing(org.semanticwb.process.model.Auditing value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_auditing,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataAssociation> listDataAssociationByAssignment(org.semanticwb.process.model.Assignment value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasAssignment, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataAssociation> listDataAssociationByAssignment(org.semanticwb.process.model.Assignment value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasAssignment,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataAssociation> listDataAssociationByTransformation(org.semanticwb.process.model.Expression value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_transformation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataAssociation> listDataAssociationByTransformation(org.semanticwb.process.model.Expression value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_transformation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataAssociation> listDataAssociationByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataAssociation> listDataAssociationByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataAssociation> listDataAssociationByDataSourceRef(org.semanticwb.process.model.InformationAssociable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_dataSourceRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataAssociation> listDataAssociationByDataSourceRef(org.semanticwb.process.model.InformationAssociable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_dataSourceRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataAssociation> listDataAssociationByMonitoring(org.semanticwb.process.model.Monitoring value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataAssociation> listDataAssociationByMonitoring(org.semanticwb.process.model.Monitoring value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataAssociation> listDataAssociationByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataAssociation> listDataAssociationByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public DataAssociationBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setDataTargetRef(org.semanticwb.process.model.InformationAssociable value)
    {
        getSemanticObject().setObjectProperty(swp_dataTargetRef, value.getSemanticObject());
    }

    public void removeDataTargetRef()
    {
        getSemanticObject().removeProperty(swp_dataTargetRef);
    }

    public org.semanticwb.process.model.InformationAssociable getDataTargetRef()
    {
         org.semanticwb.process.model.InformationAssociable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_dataTargetRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.InformationAssociable)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Assignment> listAssignments()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Assignment>(getSemanticObject().listObjectProperties(swp_hasAssignment));
    }

    public boolean hasAssignment(org.semanticwb.process.model.Assignment value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasAssignment,value.getSemanticObject());
        }
        return ret;
    }

    public void addAssignment(org.semanticwb.process.model.Assignment value)
    {
        getSemanticObject().addObjectProperty(swp_hasAssignment, value.getSemanticObject());
    }

    public void removeAllAssignment()
    {
        getSemanticObject().removeProperty(swp_hasAssignment);
    }

    public void removeAssignment(org.semanticwb.process.model.Assignment value)
    {
        getSemanticObject().removeObjectProperty(swp_hasAssignment,value.getSemanticObject());
    }

    public org.semanticwb.process.model.Assignment getAssignment()
    {
         org.semanticwb.process.model.Assignment ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasAssignment);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Assignment)obj.createGenericInstance();
         }
         return ret;
    }

    public void setTransformation(org.semanticwb.process.model.Expression value)
    {
        getSemanticObject().setObjectProperty(swp_transformation, value.getSemanticObject());
    }

    public void removeTransformation()
    {
        getSemanticObject().removeProperty(swp_transformation);
    }

    public org.semanticwb.process.model.Expression getTransformation()
    {
         org.semanticwb.process.model.Expression ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_transformation);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Expression)obj.createGenericInstance();
         }
         return ret;
    }

    public void setDataSourceRef(org.semanticwb.process.model.InformationAssociable value)
    {
        getSemanticObject().setObjectProperty(swp_dataSourceRef, value.getSemanticObject());
    }

    public void removeDataSourceRef()
    {
        getSemanticObject().removeProperty(swp_dataSourceRef);
    }

    public org.semanticwb.process.model.InformationAssociable getDataSourceRef()
    {
         org.semanticwb.process.model.InformationAssociable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_dataSourceRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.InformationAssociable)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
