package org.semanticwb.process.model.base;


public abstract class DataStoreBase extends org.semanticwb.process.model.BPMNBaseElement implements org.semanticwb.process.model.Documentable,org.semanticwb.process.model.InformationAssociable,org.semanticwb.process.model.Rootable
{
    public static final org.semanticwb.platform.SemanticProperty swp_capacity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#capacity");
    public static final org.semanticwb.platform.SemanticProperty swp_unlimited=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#unlimited");
    public static final org.semanticwb.platform.SemanticClass swp_DataStore=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataStore");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataStore");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.DataStore> listDataStores(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStore>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStore> listDataStores()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStore>(it, true);
        }

        public static org.semanticwb.process.model.DataStore createDataStore(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.DataStore.ClassMgr.createDataStore(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.DataStore getDataStore(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataStore)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.DataStore createDataStore(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataStore)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeDataStore(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasDataStore(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDataStore(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStore> listDataStoreByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStore> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStore> listDataStoreByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStore> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStore> listDataStoreByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStore> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStore> listDataStoreByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStore> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStore> listDataStoreByItemSubjectRef(org.semanticwb.process.model.ItemDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStore> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_itemSubjectRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStore> listDataStoreByItemSubjectRef(org.semanticwb.process.model.ItemDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStore> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_itemSubjectRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStore> listDataStoreByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStore> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStore> listDataStoreByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStore> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public DataStoreBase(org.semanticwb.platform.SemanticObject base)
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

    public int getCapacity()
    {
        return getSemanticObject().getIntProperty(swp_capacity);
    }

    public void setCapacity(int value)
    {
        getSemanticObject().setIntProperty(swp_capacity, value);
    }

    public boolean isUnlimited()
    {
        return getSemanticObject().getBooleanProperty(swp_unlimited);
    }

    public void setUnlimited(boolean value)
    {
        getSemanticObject().setBooleanProperty(swp_unlimited, value);
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

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
