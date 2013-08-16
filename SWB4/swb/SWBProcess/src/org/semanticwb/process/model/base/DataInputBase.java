package org.semanticwb.process.model.base;


public abstract class DataInputBase extends org.semanticwb.process.model.ItemAware implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.process.model.Collectionable
{
    public static final org.semanticwb.platform.SemanticClass swp_DataInput=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#DataInput");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#DataInput");

    public static class ClassMgr
    {
       /**
       * Returns a list of DataInput for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.DataInput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.DataInput for all models
       * @return Iterator of org.semanticwb.process.model.DataInput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput>(it, true);
        }

        public static org.semanticwb.process.model.DataInput createDataInput(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.DataInput.ClassMgr.createDataInput(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.DataInput
       * @param id Identifier for org.semanticwb.process.model.DataInput
       * @param model Model of the org.semanticwb.process.model.DataInput
       * @return A org.semanticwb.process.model.DataInput
       */
        public static org.semanticwb.process.model.DataInput getDataInput(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataInput)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.DataInput
       * @param id Identifier for org.semanticwb.process.model.DataInput
       * @param model Model of the org.semanticwb.process.model.DataInput
       * @return A org.semanticwb.process.model.DataInput
       */
        public static org.semanticwb.process.model.DataInput createDataInput(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataInput)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.DataInput
       * @param id Identifier for org.semanticwb.process.model.DataInput
       * @param model Model of the org.semanticwb.process.model.DataInput
       */
        public static void removeDataInput(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.DataInput
       * @param id Identifier for org.semanticwb.process.model.DataInput
       * @param model Model of the org.semanticwb.process.model.DataInput
       * @return true if the org.semanticwb.process.model.DataInput exists, false otherwise
       */

        public static boolean hasDataInput(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDataInput(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.DataInput with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.DataInput
       * @return Iterator with all the org.semanticwb.process.model.DataInput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataInput with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.DataInput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataInput with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.DataInput
       * @return Iterator with all the org.semanticwb.process.model.DataInput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataInput with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.DataInput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataInput with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.DataInput
       * @return Iterator with all the org.semanticwb.process.model.DataInput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataInput with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.DataInput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataInput with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.DataInput
       * @return Iterator with all the org.semanticwb.process.model.DataInput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataInput with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.DataInput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataInput with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.DataInput
       * @return Iterator with all the org.semanticwb.process.model.DataInput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataInput with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.DataInput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataInput with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.DataInput
       * @return Iterator with all the org.semanticwb.process.model.DataInput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataInput with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.DataInput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataInput with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.DataInput
       * @return Iterator with all the org.semanticwb.process.model.DataInput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataInput with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.DataInput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataInput with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.DataInput
       * @return Iterator with all the org.semanticwb.process.model.DataInput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataInput with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.DataInput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DataInputBase.ClassMgr getDataInputClassMgr()
    {
        return new DataInputBase.ClassMgr();
    }

   /**
   * Constructs a DataInputBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DataInput
   */
    public DataInputBase(org.semanticwb.platform.SemanticObject base)
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
