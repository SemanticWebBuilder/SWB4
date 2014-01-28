package org.semanticwb.process.model.base;


public abstract class DataObjectBase extends org.semanticwb.process.model.ItemAware implements org.semanticwb.process.model.Collectionable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Sortable
{
    public static final org.semanticwb.platform.SemanticClass swp_DataObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#DataObject");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#DataObject");

    public static class ClassMgr
    {
       /**
       * Returns a list of DataObject for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.DataObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataObject> listDataObjects(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataObject>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.DataObject for all models
       * @return Iterator of org.semanticwb.process.model.DataObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataObject> listDataObjects()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataObject>(it, true);
        }

        public static org.semanticwb.process.model.DataObject createDataObject(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.DataObject.ClassMgr.createDataObject(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.DataObject
       * @param id Identifier for org.semanticwb.process.model.DataObject
       * @param model Model of the org.semanticwb.process.model.DataObject
       * @return A org.semanticwb.process.model.DataObject
       */
        public static org.semanticwb.process.model.DataObject getDataObject(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataObject)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.DataObject
       * @param id Identifier for org.semanticwb.process.model.DataObject
       * @param model Model of the org.semanticwb.process.model.DataObject
       * @return A org.semanticwb.process.model.DataObject
       */
        public static org.semanticwb.process.model.DataObject createDataObject(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataObject)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.DataObject
       * @param id Identifier for org.semanticwb.process.model.DataObject
       * @param model Model of the org.semanticwb.process.model.DataObject
       */
        public static void removeDataObject(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.DataObject
       * @param id Identifier for org.semanticwb.process.model.DataObject
       * @param model Model of the org.semanticwb.process.model.DataObject
       * @return true if the org.semanticwb.process.model.DataObject exists, false otherwise
       */

        public static boolean hasDataObject(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDataObject(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.DataObject with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.DataObject
       * @return Iterator with all the org.semanticwb.process.model.DataObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataObject> listDataObjectByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataObject with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.DataObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataObject> listDataObjectByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataObject> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataObject with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.DataObject
       * @return Iterator with all the org.semanticwb.process.model.DataObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataObject> listDataObjectByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataObject with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.DataObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataObject> listDataObjectByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataObject> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataObject with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.DataObject
       * @return Iterator with all the org.semanticwb.process.model.DataObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataObject> listDataObjectByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataObject with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.DataObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataObject> listDataObjectByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataObject> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataObject with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.DataObject
       * @return Iterator with all the org.semanticwb.process.model.DataObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataObject> listDataObjectByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataObject with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.DataObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataObject> listDataObjectByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataObject> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataObject with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.DataObject
       * @return Iterator with all the org.semanticwb.process.model.DataObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataObject> listDataObjectByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataObject with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.DataObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataObject> listDataObjectByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataObject> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataObject with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.DataObject
       * @return Iterator with all the org.semanticwb.process.model.DataObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataObject> listDataObjectByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataObject with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.DataObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataObject> listDataObjectByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataObject> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataObject with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.DataObject
       * @return Iterator with all the org.semanticwb.process.model.DataObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataObject> listDataObjectByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataObject with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.DataObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataObject> listDataObjectByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataObject> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataObject with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.DataObject
       * @return Iterator with all the org.semanticwb.process.model.DataObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataObject> listDataObjectByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataObject with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.DataObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataObject> listDataObjectByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataObject> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DataObjectBase.ClassMgr getDataObjectClassMgr()
    {
        return new DataObjectBase.ClassMgr();
    }

   /**
   * Constructs a DataObjectBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DataObject
   */
    public DataObjectBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Collection property
* @return boolean with the Collection
*/
    public boolean isCollection()
    {
        return getSemanticObject().getBooleanProperty(swp_collection);
    }

/**
* Sets the Collection property
* @param value long with the Collection
*/
    public void setCollection(boolean value)
    {
        getSemanticObject().setBooleanProperty(swp_collection, value);
    }
}
