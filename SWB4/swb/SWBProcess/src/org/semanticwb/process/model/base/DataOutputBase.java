package org.semanticwb.process.model.base;


public abstract class DataOutputBase extends org.semanticwb.process.model.ItemAware implements org.semanticwb.process.model.Collectionable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Sortable
{
    public static final org.semanticwb.platform.SemanticClass swp_DataOutput=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#DataOutput");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#DataOutput");

    public static class ClassMgr
    {
       /**
       * Returns a list of DataOutput for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.DataOutput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.DataOutput for all models
       * @return Iterator of org.semanticwb.process.model.DataOutput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput>(it, true);
        }

        public static org.semanticwb.process.model.DataOutput createDataOutput(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.DataOutput.ClassMgr.createDataOutput(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.DataOutput
       * @param id Identifier for org.semanticwb.process.model.DataOutput
       * @param model Model of the org.semanticwb.process.model.DataOutput
       * @return A org.semanticwb.process.model.DataOutput
       */
        public static org.semanticwb.process.model.DataOutput getDataOutput(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataOutput)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.DataOutput
       * @param id Identifier for org.semanticwb.process.model.DataOutput
       * @param model Model of the org.semanticwb.process.model.DataOutput
       * @return A org.semanticwb.process.model.DataOutput
       */
        public static org.semanticwb.process.model.DataOutput createDataOutput(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataOutput)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.DataOutput
       * @param id Identifier for org.semanticwb.process.model.DataOutput
       * @param model Model of the org.semanticwb.process.model.DataOutput
       */
        public static void removeDataOutput(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.DataOutput
       * @param id Identifier for org.semanticwb.process.model.DataOutput
       * @param model Model of the org.semanticwb.process.model.DataOutput
       * @return true if the org.semanticwb.process.model.DataOutput exists, false otherwise
       */

        public static boolean hasDataOutput(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDataOutput(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.DataOutput with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.DataOutput
       * @return Iterator with all the org.semanticwb.process.model.DataOutput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataOutput with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.DataOutput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataOutput with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.DataOutput
       * @return Iterator with all the org.semanticwb.process.model.DataOutput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataOutput with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.DataOutput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataOutput with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.DataOutput
       * @return Iterator with all the org.semanticwb.process.model.DataOutput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataOutput with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.DataOutput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataOutput with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.DataOutput
       * @return Iterator with all the org.semanticwb.process.model.DataOutput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataOutput with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.DataOutput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataOutput with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.DataOutput
       * @return Iterator with all the org.semanticwb.process.model.DataOutput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataOutput with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.DataOutput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataOutput with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.DataOutput
       * @return Iterator with all the org.semanticwb.process.model.DataOutput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataOutput with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.DataOutput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataOutput with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.DataOutput
       * @return Iterator with all the org.semanticwb.process.model.DataOutput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataOutput with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.DataOutput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataOutput with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.DataOutput
       * @return Iterator with all the org.semanticwb.process.model.DataOutput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DataOutput with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.DataOutput
       */

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DataOutputBase.ClassMgr getDataOutputClassMgr()
    {
        return new DataOutputBase.ClassMgr();
    }

   /**
   * Constructs a DataOutputBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DataOutput
   */
    public DataOutputBase(org.semanticwb.platform.SemanticObject base)
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
