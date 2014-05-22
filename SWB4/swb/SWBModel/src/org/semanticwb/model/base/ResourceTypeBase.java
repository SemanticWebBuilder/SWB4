package org.semanticwb.model.base;


   /**
   * Objeto por medio del cual se define un tipo de componente o recurso 
   */
public abstract class ResourceTypeBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable,org.semanticwb.model.Filterable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Trashable,org.semanticwb.model.FilterableNode
{
    public static final org.semanticwb.platform.SemanticProperty swb_resourceCache=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceCache");
   /**
   * Un recurso es un componente en una Página Web con el cual el usuario tiene interacción
   */
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_hasPTResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasPTResource");
    public static final org.semanticwb.platform.SemanticProperty swb_resourceClassName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceClassName");
   /**
   * Define una agrupacion de objetos de tipo resource
   */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceCollection=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceCollection");
    public static final org.semanticwb.platform.SemanticProperty swb_resourceCollectionInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceCollectionInv");
    public static final org.semanticwb.platform.SemanticProperty swb_resourceCacheType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceCacheType");
   /**
   * Objeto que define una agripacion de componentes o recursos de un mismo tipo
   */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceSubType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceSubType");
    public static final org.semanticwb.platform.SemanticProperty swb_hasPTSubType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasPTSubType");
    public static final org.semanticwb.platform.SemanticProperty swb_resourceMode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceMode");
    public static final org.semanticwb.platform.SemanticProperty swb_resourceBundle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceBundle");
    public static final org.semanticwb.platform.SemanticProperty swb_resourceOWL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceOWL");
    public static final org.semanticwb.platform.SemanticProperty swb_resourceCacheIgnoreQueryParams=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceCacheIgnoreQueryParams");
   /**
   * Objeto por medio del cual se define un tipo de componente o recurso
   */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceType");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceType");

    public static class ClassMgr
    {
       /**
       * Returns a list of ResourceType for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.ResourceType
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.ResourceType for all models
       * @return Iterator of org.semanticwb.model.ResourceType
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.ResourceType
       * @param id Identifier for org.semanticwb.model.ResourceType
       * @param model Model of the org.semanticwb.model.ResourceType
       * @return A org.semanticwb.model.ResourceType
       */
        public static org.semanticwb.model.ResourceType getResourceType(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.ResourceType)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.ResourceType
       * @param id Identifier for org.semanticwb.model.ResourceType
       * @param model Model of the org.semanticwb.model.ResourceType
       * @return A org.semanticwb.model.ResourceType
       */
        public static org.semanticwb.model.ResourceType createResourceType(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.ResourceType)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.ResourceType
       * @param id Identifier for org.semanticwb.model.ResourceType
       * @param model Model of the org.semanticwb.model.ResourceType
       */
        public static void removeResourceType(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.ResourceType
       * @param id Identifier for org.semanticwb.model.ResourceType
       * @param model Model of the org.semanticwb.model.ResourceType
       * @return true if the org.semanticwb.model.ResourceType exists, false otherwise
       */

        public static boolean hasResourceType(String id, org.semanticwb.model.SWBModel model)
        {
            return (getResourceType(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.ResourceType with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.ResourceType
       * @return Iterator with all the org.semanticwb.model.ResourceType
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ResourceType with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.ResourceType
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ResourceType with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @param model Model of the org.semanticwb.model.ResourceType
       * @return Iterator with all the org.semanticwb.model.ResourceType
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPTResource, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ResourceType with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.model.ResourceType
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasPTResource,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ResourceType with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.ResourceType
       * @return Iterator with all the org.semanticwb.model.ResourceType
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ResourceType with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.ResourceType
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ResourceType with a determined ResourceCollection
       * @param value ResourceCollection of the type org.semanticwb.model.ResourceCollection
       * @param model Model of the org.semanticwb.model.ResourceType
       * @return Iterator with all the org.semanticwb.model.ResourceType
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeByResourceCollection(org.semanticwb.model.ResourceCollection value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_resourceCollectionInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ResourceType with a determined ResourceCollection
       * @param value ResourceCollection of the type org.semanticwb.model.ResourceCollection
       * @return Iterator with all the org.semanticwb.model.ResourceType
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeByResourceCollection(org.semanticwb.model.ResourceCollection value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_resourceCollectionInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ResourceType with a determined SubType
       * @param value SubType of the type org.semanticwb.model.ResourceSubType
       * @param model Model of the org.semanticwb.model.ResourceType
       * @return Iterator with all the org.semanticwb.model.ResourceType
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeBySubType(org.semanticwb.model.ResourceSubType value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPTSubType, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ResourceType with a determined SubType
       * @param value SubType of the type org.semanticwb.model.ResourceSubType
       * @return Iterator with all the org.semanticwb.model.ResourceType
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeBySubType(org.semanticwb.model.ResourceSubType value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasPTSubType,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ResourceTypeBase.ClassMgr getResourceTypeClassMgr()
    {
        return new ResourceTypeBase.ClassMgr();
    }

   /**
   * Constructs a ResourceTypeBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ResourceType
   */
    public ResourceTypeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the ResourceCache property
* @return int with the ResourceCache
*/
    public int getResourceCache()
    {
        return getSemanticObject().getIntProperty(swb_resourceCache);
    }

/**
* Sets the ResourceCache property
* @param value long with the ResourceCache
*/
    public void setResourceCache(int value)
    {
        getSemanticObject().setIntProperty(swb_resourceCache, value);
    }
   /**
   * Sets the value for the property ModifiedBy
   * @param value ModifiedBy to set
   */

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
        }else
        {
            removeModifiedBy();
        }
    }
   /**
   * Remove the value for ModifiedBy property
   */

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

   /**
   * Gets the ModifiedBy
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Updated property
* @return java.util.Date with the Updated
*/
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

/**
* Sets the Updated property
* @param value long with the Updated
*/
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

/**
* Gets the Created property
* @return java.util.Date with the Created
*/
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

/**
* Sets the Created property
* @param value long with the Created
*/
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

/**
* Gets the Description property
* @return String with the Description
*/
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

/**
* Sets the Description property
* @param value long with the Description
*/
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

/**
* Gets the Deleted property
* @return boolean with the Deleted
*/
    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(swb_deleted);
    }

/**
* Sets the Deleted property
* @param value long with the Deleted
*/
    public void setDeleted(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_deleted, value);
    }
   /**
   * Gets all the org.semanticwb.model.Resource
   * @return A GenericIterator with all the org.semanticwb.model.Resource
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> listResources()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource>(getSemanticObject().listObjectProperties(swb_hasPTResource));
    }

   /**
   * Gets true if has a Resource
   * @param value org.semanticwb.model.Resource to verify
   * @return true if the org.semanticwb.model.Resource exists, false otherwise
   */
    public boolean hasResource(org.semanticwb.model.Resource value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasPTResource,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the Resource
   * @return a org.semanticwb.model.Resource
   */
    public org.semanticwb.model.Resource getResource()
    {
         org.semanticwb.model.Resource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasPTResource);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resource)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the ResourceClassName property
* @return String with the ResourceClassName
*/
    public String getResourceClassName()
    {
        return getSemanticObject().getProperty(swb_resourceClassName);
    }

/**
* Sets the ResourceClassName property
* @param value long with the ResourceClassName
*/
    public void setResourceClassName(String value)
    {
        getSemanticObject().setProperty(swb_resourceClassName, value);
    }
   /**
   * Sets the value for the property Creator
   * @param value Creator to set
   */

    public void setCreator(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
        }else
        {
            removeCreator();
        }
    }
   /**
   * Remove the value for Creator property
   */

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

   /**
   * Gets the Creator
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Title property
* @return String with the Title
*/
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

/**
* Sets the Title property
* @param value long with the Title
*/
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }
   /**
   * Sets the value for the property ResourceCollection
   * @param value ResourceCollection to set
   */

    public void setResourceCollection(org.semanticwb.model.ResourceCollection value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_resourceCollectionInv, value.getSemanticObject());
        }else
        {
            removeResourceCollection();
        }
    }
   /**
   * Remove the value for ResourceCollection property
   */

    public void removeResourceCollection()
    {
        getSemanticObject().removeProperty(swb_resourceCollectionInv);
    }

   /**
   * Gets the ResourceCollection
   * @return a org.semanticwb.model.ResourceCollection
   */
    public org.semanticwb.model.ResourceCollection getResourceCollection()
    {
         org.semanticwb.model.ResourceCollection ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_resourceCollectionInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.ResourceCollection)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the ResourceCacheType property
* @return String with the ResourceCacheType
*/
    public String getResourceCacheType()
    {
        return getSemanticObject().getProperty(swb_resourceCacheType);
    }

/**
* Sets the ResourceCacheType property
* @param value long with the ResourceCacheType
*/
    public void setResourceCacheType(String value)
    {
        getSemanticObject().setProperty(swb_resourceCacheType, value);
    }
   /**
   * Gets all the org.semanticwb.model.ResourceSubType
   * @return A GenericIterator with all the org.semanticwb.model.ResourceSubType
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceSubType> listSubTypes()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceSubType>(getSemanticObject().listObjectProperties(swb_hasPTSubType));
    }

   /**
   * Gets true if has a SubType
   * @param value org.semanticwb.model.ResourceSubType to verify
   * @return true if the org.semanticwb.model.ResourceSubType exists, false otherwise
   */
    public boolean hasSubType(org.semanticwb.model.ResourceSubType value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasPTSubType,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the SubType
   * @return a org.semanticwb.model.ResourceSubType
   */
    public org.semanticwb.model.ResourceSubType getSubType()
    {
         org.semanticwb.model.ResourceSubType ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasPTSubType);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.ResourceSubType)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the ResourceMode property
* @return int with the ResourceMode
*/
    public int getResourceMode()
    {
        return getSemanticObject().getIntProperty(swb_resourceMode);
    }

/**
* Sets the ResourceMode property
* @param value long with the ResourceMode
*/
    public void setResourceMode(int value)
    {
        getSemanticObject().setIntProperty(swb_resourceMode, value);
    }

/**
* Gets the ResourceBundle property
* @return String with the ResourceBundle
*/
    public String getResourceBundle()
    {
        return getSemanticObject().getProperty(swb_resourceBundle);
    }

/**
* Sets the ResourceBundle property
* @param value long with the ResourceBundle
*/
    public void setResourceBundle(String value)
    {
        getSemanticObject().setProperty(swb_resourceBundle, value);
    }

/**
* Gets the ResourceOWL property
* @return String with the ResourceOWL
*/
    public String getResourceOWL()
    {
        return getSemanticObject().getProperty(swb_resourceOWL);
    }

/**
* Sets the ResourceOWL property
* @param value long with the ResourceOWL
*/
    public void setResourceOWL(String value)
    {
        getSemanticObject().setProperty(swb_resourceOWL, value);
    }

/**
* Gets the ResourceCacheIgnoreQueryParams property
* @return boolean with the ResourceCacheIgnoreQueryParams
*/
    public boolean isResourceCacheIgnoreQueryParams()
    {
        return getSemanticObject().getBooleanProperty(swb_resourceCacheIgnoreQueryParams);
    }

/**
* Sets the ResourceCacheIgnoreQueryParams property
* @param value long with the ResourceCacheIgnoreQueryParams
*/
    public void setResourceCacheIgnoreQueryParams(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_resourceCacheIgnoreQueryParams, value);
    }

   /**
   * Gets the WebSite
   * @return a instance of org.semanticwb.model.WebSite
   */
    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
