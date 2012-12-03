package org.semanticwb.process.model.base;


public abstract class ItemAwareReferenceBase extends org.semanticwb.process.model.BaseElement 
{
    public static final org.semanticwb.platform.SemanticProperty swp_itemAwareTemporal=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#itemAwareTemporal");
   /**
   * Superclase de todos los objetos con persistencia en SemanticWebBuilder
   */
    public static final org.semanticwb.platform.SemanticClass swb_SWBClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBClass");
    public static final org.semanticwb.platform.SemanticProperty swp_processObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processObject");
    public static final org.semanticwb.platform.SemanticProperty swp_processObjectReused=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processObjectReused");
    public static final org.semanticwb.platform.SemanticClass swp_ItemAware=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ItemAware");
    public static final org.semanticwb.platform.SemanticProperty swp_itemAware=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#itemAware");
    public static final org.semanticwb.platform.SemanticClass swp_ItemAwareReference=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ItemAwareReference");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ItemAwareReference");

    public static class ClassMgr
    {
       /**
       * Returns a list of ItemAwareReference for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ItemAwareReference
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAwareReference> listItemAwareReferences(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAwareReference>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ItemAwareReference for all models
       * @return Iterator of org.semanticwb.process.model.ItemAwareReference
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAwareReference> listItemAwareReferences()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAwareReference>(it, true);
        }

        public static org.semanticwb.process.model.ItemAwareReference createItemAwareReference(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ItemAwareReference.ClassMgr.createItemAwareReference(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.ItemAwareReference
       * @param id Identifier for org.semanticwb.process.model.ItemAwareReference
       * @param model Model of the org.semanticwb.process.model.ItemAwareReference
       * @return A org.semanticwb.process.model.ItemAwareReference
       */
        public static org.semanticwb.process.model.ItemAwareReference getItemAwareReference(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ItemAwareReference)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.ItemAwareReference
       * @param id Identifier for org.semanticwb.process.model.ItemAwareReference
       * @param model Model of the org.semanticwb.process.model.ItemAwareReference
       * @return A org.semanticwb.process.model.ItemAwareReference
       */
        public static org.semanticwb.process.model.ItemAwareReference createItemAwareReference(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ItemAwareReference)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ItemAwareReference
       * @param id Identifier for org.semanticwb.process.model.ItemAwareReference
       * @param model Model of the org.semanticwb.process.model.ItemAwareReference
       */
        public static void removeItemAwareReference(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ItemAwareReference
       * @param id Identifier for org.semanticwb.process.model.ItemAwareReference
       * @param model Model of the org.semanticwb.process.model.ItemAwareReference
       * @return true if the org.semanticwb.process.model.ItemAwareReference exists, false otherwise
       */

        public static boolean hasItemAwareReference(String id, org.semanticwb.model.SWBModel model)
        {
            return (getItemAwareReference(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.ItemAwareReference with a determined ProcessObject
       * @param value ProcessObject of the type org.semanticwb.model.SWBClass
       * @param model Model of the org.semanticwb.process.model.ItemAwareReference
       * @return Iterator with all the org.semanticwb.process.model.ItemAwareReference
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAwareReference> listItemAwareReferenceByProcessObject(org.semanticwb.model.SWBClass value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAwareReference> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_processObject, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ItemAwareReference with a determined ProcessObject
       * @param value ProcessObject of the type org.semanticwb.model.SWBClass
       * @return Iterator with all the org.semanticwb.process.model.ItemAwareReference
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAwareReference> listItemAwareReferenceByProcessObject(org.semanticwb.model.SWBClass value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAwareReference> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_processObject,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ItemAwareReference with a determined ItemAware
       * @param value ItemAware of the type org.semanticwb.process.model.ItemAware
       * @param model Model of the org.semanticwb.process.model.ItemAwareReference
       * @return Iterator with all the org.semanticwb.process.model.ItemAwareReference
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAwareReference> listItemAwareReferenceByItemAware(org.semanticwb.process.model.ItemAware value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAwareReference> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_itemAware, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ItemAwareReference with a determined ItemAware
       * @param value ItemAware of the type org.semanticwb.process.model.ItemAware
       * @return Iterator with all the org.semanticwb.process.model.ItemAwareReference
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAwareReference> listItemAwareReferenceByItemAware(org.semanticwb.process.model.ItemAware value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAwareReference> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_itemAware,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ItemAwareReferenceBase.ClassMgr getItemAwareReferenceClassMgr()
    {
        return new ItemAwareReferenceBase.ClassMgr();
    }

   /**
   * Constructs a ItemAwareReferenceBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ItemAwareReference
   */
    public ItemAwareReferenceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the ItemAwareTemporal property
* @return boolean with the ItemAwareTemporal
*/
    public boolean isItemAwareTemporal()
    {
        return getSemanticObject().getBooleanProperty(swp_itemAwareTemporal);
    }

/**
* Sets the ItemAwareTemporal property
* @param value long with the ItemAwareTemporal
*/
    public void setItemAwareTemporal(boolean value)
    {
        getSemanticObject().setBooleanProperty(swp_itemAwareTemporal, value);
    }
   /**
   * Sets the value for the property ProcessObject
   * @param value ProcessObject to set
   */

    public void setProcessObject(org.semanticwb.model.SWBClass value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_processObject, value.getSemanticObject());
        }else
        {
            removeProcessObject();
        }
    }
   /**
   * Remove the value for ProcessObject property
   */

    public void removeProcessObject()
    {
        getSemanticObject().removeProperty(swp_processObject);
    }

   /**
   * Gets the ProcessObject
   * @return a org.semanticwb.model.SWBClass
   */
    public org.semanticwb.model.SWBClass getProcessObject()
    {
         org.semanticwb.model.SWBClass ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_processObject);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.SWBClass)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the ProcessObjectReused property
* @return boolean with the ProcessObjectReused
*/
    public boolean isProcessObjectReused()
    {
        return getSemanticObject().getBooleanProperty(swp_processObjectReused);
    }

/**
* Sets the ProcessObjectReused property
* @param value long with the ProcessObjectReused
*/
    public void setProcessObjectReused(boolean value)
    {
        getSemanticObject().setBooleanProperty(swp_processObjectReused, value);
    }
   /**
   * Sets the value for the property ItemAware
   * @param value ItemAware to set
   */

    public void setItemAware(org.semanticwb.process.model.ItemAware value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_itemAware, value.getSemanticObject());
        }else
        {
            removeItemAware();
        }
    }
   /**
   * Remove the value for ItemAware property
   */

    public void removeItemAware()
    {
        getSemanticObject().removeProperty(swp_itemAware);
    }

   /**
   * Gets the ItemAware
   * @return a org.semanticwb.process.model.ItemAware
   */
    public org.semanticwb.process.model.ItemAware getItemAware()
    {
         org.semanticwb.process.model.ItemAware ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_itemAware);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ItemAware)obj.createGenericInstance();
         }
         return ret;
    }
}
