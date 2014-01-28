package org.semanticwb.process.model.base;


public abstract class ItemAwareBase extends org.semanticwb.process.model.GraphicalElement implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Sortable
{
    public static final org.semanticwb.platform.SemanticClass swb_Class=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Class");
    public static final org.semanticwb.platform.SemanticProperty swp_dataObjectClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#dataObjectClass");
    public static final org.semanticwb.platform.SemanticProperty swp_name=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#name");
    public static final org.semanticwb.platform.SemanticClass swp_ItemAware=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ItemAware");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ItemAware");

    public static class ClassMgr
    {
       /**
       * Returns a list of ItemAware for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ItemAware
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAware> listItemAwares(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAware>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ItemAware for all models
       * @return Iterator of org.semanticwb.process.model.ItemAware
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAware> listItemAwares()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAware>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.ItemAware
       * @param id Identifier for org.semanticwb.process.model.ItemAware
       * @param model Model of the org.semanticwb.process.model.ItemAware
       * @return A org.semanticwb.process.model.ItemAware
       */
        public static org.semanticwb.process.model.ItemAware getItemAware(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ItemAware)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.ItemAware
       * @param id Identifier for org.semanticwb.process.model.ItemAware
       * @param model Model of the org.semanticwb.process.model.ItemAware
       * @return A org.semanticwb.process.model.ItemAware
       */
        public static org.semanticwb.process.model.ItemAware createItemAware(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ItemAware)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ItemAware
       * @param id Identifier for org.semanticwb.process.model.ItemAware
       * @param model Model of the org.semanticwb.process.model.ItemAware
       */
        public static void removeItemAware(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ItemAware
       * @param id Identifier for org.semanticwb.process.model.ItemAware
       * @param model Model of the org.semanticwb.process.model.ItemAware
       * @return true if the org.semanticwb.process.model.ItemAware exists, false otherwise
       */

        public static boolean hasItemAware(String id, org.semanticwb.model.SWBModel model)
        {
            return (getItemAware(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.ItemAware with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ItemAware
       * @return Iterator with all the org.semanticwb.process.model.ItemAware
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAware> listItemAwareByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAware> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ItemAware with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ItemAware
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAware> listItemAwareByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAware> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ItemAware with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.ItemAware
       * @return Iterator with all the org.semanticwb.process.model.ItemAware
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAware> listItemAwareByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAware> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ItemAware with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.ItemAware
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAware> listItemAwareByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAware> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ItemAware with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.ItemAware
       * @return Iterator with all the org.semanticwb.process.model.ItemAware
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAware> listItemAwareByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAware> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ItemAware with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.ItemAware
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAware> listItemAwareByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAware> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ItemAware with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.ItemAware
       * @return Iterator with all the org.semanticwb.process.model.ItemAware
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAware> listItemAwareByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAware> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ItemAware with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.ItemAware
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAware> listItemAwareByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAware> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ItemAware with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.ItemAware
       * @return Iterator with all the org.semanticwb.process.model.ItemAware
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAware> listItemAwareByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAware> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ItemAware with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.ItemAware
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAware> listItemAwareByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAware> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ItemAware with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.ItemAware
       * @return Iterator with all the org.semanticwb.process.model.ItemAware
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAware> listItemAwareByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAware> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ItemAware with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.ItemAware
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAware> listItemAwareByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAware> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ItemAware with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ItemAware
       * @return Iterator with all the org.semanticwb.process.model.ItemAware
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAware> listItemAwareByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAware> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ItemAware with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ItemAware
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAware> listItemAwareByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAware> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ItemAware with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.ItemAware
       * @return Iterator with all the org.semanticwb.process.model.ItemAware
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAware> listItemAwareByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAware> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ItemAware with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.ItemAware
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAware> listItemAwareByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAware> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ItemAwareBase.ClassMgr getItemAwareClassMgr()
    {
        return new ItemAwareBase.ClassMgr();
    }

   /**
   * Constructs a ItemAwareBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ItemAware
   */
    public ItemAwareBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setDataObjectClass(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().setObjectProperty(swp_dataObjectClass, value);
    }

    public void removeDataObjectClass()
    {
        getSemanticObject().removeProperty(swp_dataObjectClass);
    }

/**
* Gets the DataObjectClass property
* @return the value for the property as org.semanticwb.platform.SemanticObject
*/
    public org.semanticwb.platform.SemanticObject getDataObjectClass()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swp_dataObjectClass);
         return ret;
    }

/**
* Gets the Name property
* @return String with the Name
*/
    public String getName()
    {
        return getSemanticObject().getProperty(swp_name);
    }

/**
* Sets the Name property
* @param value long with the Name
*/
    public void setName(String value)
    {
        getSemanticObject().setProperty(swp_name, value);
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
