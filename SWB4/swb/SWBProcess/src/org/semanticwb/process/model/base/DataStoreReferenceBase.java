package org.semanticwb.process.model.base;


public abstract class DataStoreReferenceBase extends org.semanticwb.process.model.FlowElement implements org.semanticwb.process.model.Auditable,org.semanticwb.process.model.InformationAssociable,org.semanticwb.process.model.Monitorable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Documentable
{
    public static final org.semanticwb.platform.SemanticClass swp_DataStore=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataStore");
    public static final org.semanticwb.platform.SemanticProperty swp_dataStoreRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#dataStoreRef");
    public static final org.semanticwb.platform.SemanticClass swp_DataStoreReference=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataStoreReference");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataStoreReference");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.DataStoreReference> listDataStoreReferences(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStoreReference>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStoreReference> listDataStoreReferences()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStoreReference>(it, true);
        }

        public static org.semanticwb.process.model.DataStoreReference createDataStoreReference(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.DataStoreReference.ClassMgr.createDataStoreReference(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.DataStoreReference getDataStoreReference(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataStoreReference)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.DataStoreReference createDataStoreReference(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataStoreReference)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeDataStoreReference(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasDataStoreReference(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDataStoreReference(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStoreReference> listDataStoreReferenceByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStoreReference> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStoreReference> listDataStoreReferenceByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStoreReference> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStoreReference> listDataStoreReferenceByAuditing(org.semanticwb.process.model.Auditing value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStoreReference> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_auditing, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStoreReference> listDataStoreReferenceByAuditing(org.semanticwb.process.model.Auditing value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStoreReference> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_auditing,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStoreReference> listDataStoreReferenceByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStoreReference> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStoreReference> listDataStoreReferenceByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStoreReference> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStoreReference> listDataStoreReferenceByDataStoreRef(org.semanticwb.process.model.DataStore value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStoreReference> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_dataStoreRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStoreReference> listDataStoreReferenceByDataStoreRef(org.semanticwb.process.model.DataStore value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStoreReference> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_dataStoreRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStoreReference> listDataStoreReferenceByMonitoring(org.semanticwb.process.model.Monitoring value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStoreReference> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStoreReference> listDataStoreReferenceByMonitoring(org.semanticwb.process.model.Monitoring value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStoreReference> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStoreReference> listDataStoreReferenceByItemSubjectRef(org.semanticwb.process.model.ItemDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStoreReference> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_itemSubjectRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStoreReference> listDataStoreReferenceByItemSubjectRef(org.semanticwb.process.model.ItemDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStoreReference> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_itemSubjectRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStoreReference> listDataStoreReferenceByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStoreReference> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStoreReference> listDataStoreReferenceByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStoreReference> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public DataStoreReferenceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getDataState()
    {
        return getSemanticObject().getProperty(swp_dataState);
    }

    public void setDataState(String value)
    {
        getSemanticObject().setProperty(swp_dataState, value);
    }

    public void setDataStoreRef(org.semanticwb.process.model.DataStore value)
    {
        getSemanticObject().setObjectProperty(swp_dataStoreRef, value.getSemanticObject());
    }

    public void removeDataStoreRef()
    {
        getSemanticObject().removeProperty(swp_dataStoreRef);
    }

    public org.semanticwb.process.model.DataStore getDataStoreRef()
    {
         org.semanticwb.process.model.DataStore ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_dataStoreRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.DataStore)obj.createGenericInstance();
         }
         return ret;
    }

    public void setItemSubjectRef(org.semanticwb.process.model.ItemDefinition value)
    {
        getSemanticObject().setObjectProperty(swp_itemSubjectRef, value.getSemanticObject());
    }

    public void removeItemSubjectRef()
    {
        getSemanticObject().removeProperty(swp_itemSubjectRef);
    }

    public org.semanticwb.process.model.ItemDefinition getItemSubjectRef()
    {
         org.semanticwb.process.model.ItemDefinition ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_itemSubjectRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ItemDefinition)obj.createGenericInstance();
         }
         return ret;
    }
}
