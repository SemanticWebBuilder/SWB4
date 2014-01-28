package org.semanticwb.process.model.base;


public abstract class DataStoreBase extends org.semanticwb.process.model.ItemAware implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Sortable
{
    public static final org.semanticwb.platform.SemanticProperty swp_dataObjectInitializationCode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#dataObjectInitializationCode");
    public static final org.semanticwb.platform.SemanticProperty swp_dataObjectId=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#dataObjectId");
    public static final org.semanticwb.platform.SemanticClass swp_DataStore=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#DataStore");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#DataStore");

    public static class ClassMgr
    {
       /**
       * Returns a list of DataStore for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.DataStore
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataStore> listDataStores(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStore>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.DataStore for all models
       * @return Iterator of org.semanticwb.process.model.DataStore
       */

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
       /**
       * Gets a org.semanticwb.process.model.DataStore
       * @param id Identifier for org.semanticwb.process.model.DataStore
       * @param model Model of the org.semanticwb.process.model.DataStore
       * @return A org.semanticwb.process.model.DataStore
       */
        public static org.semanticwb.process.model.DataStore getDataStore(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataStore)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.DataStore
       * @param id Identifier for org.semanticwb.process.model.DataStore
       * @param model Model of the org.semanticwb.process.model.DataStore
       * @return A org.semanticwb.process.model.DataStore
       */
        public static org.semanticwb.process.model.DataStore createDataStore(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataStore)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.DataStore
       * @param id Identifier for org.semanticwb.process.model.DataStore
       * @param model Model of the org.semanticwb.process.model.DataStore
       */
        public static void removeDataStore(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.DataStore
       * @param id Identifier for org.semanticwb.process.model.DataStore
       * @param model Model of the org.semanticwb.process.model.DataStore
       * @return true if the org.semanticwb.process.model.DataStore exists, false otherwise
       */

        public static boolean hasDataStore(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDataStore(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.DataStore with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.DataStore
       * @return Iterator with all the org.semanticwb.process.model.DataStore
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataStore> listDataStoreByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStore> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataStore with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.DataStore
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataStore> listDataStoreByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStore> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataStore with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.DataStore
       * @return Iterator with all the org.semanticwb.process.model.DataStore
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataStore> listDataStoreByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStore> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataStore with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.DataStore
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataStore> listDataStoreByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStore> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataStore with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.DataStore
       * @return Iterator with all the org.semanticwb.process.model.DataStore
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataStore> listDataStoreByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStore> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataStore with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.DataStore
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataStore> listDataStoreByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStore> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataStore with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.DataStore
       * @return Iterator with all the org.semanticwb.process.model.DataStore
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataStore> listDataStoreByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStore> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataStore with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.DataStore
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataStore> listDataStoreByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStore> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataStore with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.DataStore
       * @return Iterator with all the org.semanticwb.process.model.DataStore
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataStore> listDataStoreByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStore> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataStore with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.DataStore
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataStore> listDataStoreByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStore> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataStore with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.DataStore
       * @return Iterator with all the org.semanticwb.process.model.DataStore
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataStore> listDataStoreByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStore> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataStore with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.DataStore
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataStore> listDataStoreByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStore> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataStore with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.DataStore
       * @return Iterator with all the org.semanticwb.process.model.DataStore
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataStore> listDataStoreByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStore> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataStore with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.DataStore
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataStore> listDataStoreByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStore> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataStore with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.DataStore
       * @return Iterator with all the org.semanticwb.process.model.DataStore
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataStore> listDataStoreByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStore> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataStore with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.DataStore
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataStore> listDataStoreByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStore> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DataStoreBase.ClassMgr getDataStoreClassMgr()
    {
        return new DataStoreBase.ClassMgr();
    }

   /**
   * Constructs a DataStoreBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DataStore
   */
    public DataStoreBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the InitializationCode property
* @return String with the InitializationCode
*/
    public String getInitializationCode()
    {
        return getSemanticObject().getProperty(swp_dataObjectInitializationCode);
    }

/**
* Sets the InitializationCode property
* @param value long with the InitializationCode
*/
    public void setInitializationCode(String value)
    {
        getSemanticObject().setProperty(swp_dataObjectInitializationCode, value);
    }

/**
* Gets the DataObjectId property
* @return String with the DataObjectId
*/
    public String getDataObjectId()
    {
        return getSemanticObject().getProperty(swp_dataObjectId);
    }

/**
* Sets the DataObjectId property
* @param value long with the DataObjectId
*/
    public void setDataObjectId(String value)
    {
        getSemanticObject().setProperty(swp_dataObjectId, value);
    }

   /**
   * Gets the ProcessSite
   * @return a instance of org.semanticwb.process.model.ProcessSite
   */
    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
