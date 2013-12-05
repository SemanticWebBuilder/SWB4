package org.semanticwb.model.base;


   /**
   * Define una categoria dentro de una colección de recursos 
   */
public abstract class ResourceCollectionCategoryBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Resourceable,org.semanticwb.model.Traceable
{
   /**
   * Define una agrupacion de objetos de tipo resource
   */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceCollection=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceCollection");
    public static final org.semanticwb.platform.SemanticProperty swb_resourceCollection=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceCollection");
   /**
   * Define una categoria dentro de una colección de recursos
   */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceCollectionCategory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceCollectionCategory");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceCollectionCategory");

    public static class ClassMgr
    {
       /**
       * Returns a list of ResourceCollectionCategory for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.ResourceCollectionCategory
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceCollectionCategory> listResourceCollectionCategories(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceCollectionCategory>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.ResourceCollectionCategory for all models
       * @return Iterator of org.semanticwb.model.ResourceCollectionCategory
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceCollectionCategory> listResourceCollectionCategories()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceCollectionCategory>(it, true);
        }

        public static org.semanticwb.model.ResourceCollectionCategory createResourceCollectionCategory(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.ResourceCollectionCategory.ClassMgr.createResourceCollectionCategory(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.model.ResourceCollectionCategory
       * @param id Identifier for org.semanticwb.model.ResourceCollectionCategory
       * @param model Model of the org.semanticwb.model.ResourceCollectionCategory
       * @return A org.semanticwb.model.ResourceCollectionCategory
       */
        public static org.semanticwb.model.ResourceCollectionCategory getResourceCollectionCategory(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.ResourceCollectionCategory)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.ResourceCollectionCategory
       * @param id Identifier for org.semanticwb.model.ResourceCollectionCategory
       * @param model Model of the org.semanticwb.model.ResourceCollectionCategory
       * @return A org.semanticwb.model.ResourceCollectionCategory
       */
        public static org.semanticwb.model.ResourceCollectionCategory createResourceCollectionCategory(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.ResourceCollectionCategory)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.ResourceCollectionCategory
       * @param id Identifier for org.semanticwb.model.ResourceCollectionCategory
       * @param model Model of the org.semanticwb.model.ResourceCollectionCategory
       */
        public static void removeResourceCollectionCategory(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.ResourceCollectionCategory
       * @param id Identifier for org.semanticwb.model.ResourceCollectionCategory
       * @param model Model of the org.semanticwb.model.ResourceCollectionCategory
       * @return true if the org.semanticwb.model.ResourceCollectionCategory exists, false otherwise
       */

        public static boolean hasResourceCollectionCategory(String id, org.semanticwb.model.SWBModel model)
        {
            return (getResourceCollectionCategory(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.ResourceCollectionCategory with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.ResourceCollectionCategory
       * @return Iterator with all the org.semanticwb.model.ResourceCollectionCategory
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceCollectionCategory> listResourceCollectionCategoryByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceCollectionCategory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ResourceCollectionCategory with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.ResourceCollectionCategory
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceCollectionCategory> listResourceCollectionCategoryByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceCollectionCategory> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ResourceCollectionCategory with a determined ResourceCollection
       * @param value ResourceCollection of the type org.semanticwb.model.ResourceCollection
       * @param model Model of the org.semanticwb.model.ResourceCollectionCategory
       * @return Iterator with all the org.semanticwb.model.ResourceCollectionCategory
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceCollectionCategory> listResourceCollectionCategoryByResourceCollection(org.semanticwb.model.ResourceCollection value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceCollectionCategory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_resourceCollection, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ResourceCollectionCategory with a determined ResourceCollection
       * @param value ResourceCollection of the type org.semanticwb.model.ResourceCollection
       * @return Iterator with all the org.semanticwb.model.ResourceCollectionCategory
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceCollectionCategory> listResourceCollectionCategoryByResourceCollection(org.semanticwb.model.ResourceCollection value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceCollectionCategory> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_resourceCollection,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ResourceCollectionCategory with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.ResourceCollectionCategory
       * @return Iterator with all the org.semanticwb.model.ResourceCollectionCategory
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceCollectionCategory> listResourceCollectionCategoryByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceCollectionCategory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ResourceCollectionCategory with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.ResourceCollectionCategory
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceCollectionCategory> listResourceCollectionCategoryByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceCollectionCategory> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ResourceCollectionCategory with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @param model Model of the org.semanticwb.model.ResourceCollectionCategory
       * @return Iterator with all the org.semanticwb.model.ResourceCollectionCategory
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceCollectionCategory> listResourceCollectionCategoryByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceCollectionCategory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ResourceCollectionCategory with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.model.ResourceCollectionCategory
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceCollectionCategory> listResourceCollectionCategoryByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceCollectionCategory> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ResourceCollectionCategoryBase.ClassMgr getResourceCollectionCategoryClassMgr()
    {
        return new ResourceCollectionCategoryBase.ClassMgr();
    }

   /**
   * Constructs a ResourceCollectionCategoryBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ResourceCollectionCategory
   */
    public ResourceCollectionCategoryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
   * Sets the value for the property ResourceCollection
   * @param value ResourceCollection to set
   */

    public void setResourceCollection(org.semanticwb.model.ResourceCollection value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_resourceCollection, value.getSemanticObject());
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
        getSemanticObject().removeProperty(swb_resourceCollection);
    }

   /**
   * Gets the ResourceCollection
   * @return a org.semanticwb.model.ResourceCollection
   */
    public org.semanticwb.model.ResourceCollection getResourceCollection()
    {
         org.semanticwb.model.ResourceCollection ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_resourceCollection);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.ResourceCollection)obj.createGenericInstance();
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
   * Gets all the org.semanticwb.model.Resource
   * @return A GenericIterator with all the org.semanticwb.model.Resource
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> listResources()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource>(getSemanticObject().listObjectProperties(swb_hasResource));
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
           ret=getSemanticObject().hasObjectProperty(swb_hasResource,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Resource
   * @param value org.semanticwb.model.Resource to add
   */

    public void addResource(org.semanticwb.model.Resource value)
    {
        getSemanticObject().addObjectProperty(swb_hasResource, value.getSemanticObject());
    }
   /**
   * Removes all the Resource
   */

    public void removeAllResource()
    {
        getSemanticObject().removeProperty(swb_hasResource);
    }
   /**
   * Removes a Resource
   * @param value org.semanticwb.model.Resource to remove
   */

    public void removeResource(org.semanticwb.model.Resource value)
    {
        getSemanticObject().removeObjectProperty(swb_hasResource,value.getSemanticObject());
    }

   /**
   * Gets the Resource
   * @return a org.semanticwb.model.Resource
   */
    public org.semanticwb.model.Resource getResource()
    {
         org.semanticwb.model.Resource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasResource);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resource)obj.createGenericInstance();
         }
         return ret;
    }
}
