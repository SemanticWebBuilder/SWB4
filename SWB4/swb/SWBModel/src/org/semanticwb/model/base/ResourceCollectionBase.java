package org.semanticwb.model.base;


   /**
   * Define una agrupacion de objetos de tipo resource 
   */
public abstract class ResourceCollectionBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Filterable,org.semanticwb.model.Traceable,org.semanticwb.model.Referensable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Descriptiveable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Resourceable
{
   /**
   * Define una categoria dentro de una colección de recursos
   */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceCollectionCategory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceCollectionCategory");
    public static final org.semanticwb.platform.SemanticProperty swb_hasResourceCollectionCategoryInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasResourceCollectionCategoryInv");
   /**
   * Una Página Web es el elemento de SemanticWebBuilder a través del cual se estructura la información del portal.
   */
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty swb_displayWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#displayWebPage");
   /**
   * Objeto por medio del cual se define un tipo de componente o recurso
   */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceType");
    public static final org.semanticwb.platform.SemanticProperty swb_resourceCollectionType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceCollectionType");
   /**
   * Define una agrupacion de objetos de tipo resource
   */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceCollection=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceCollection");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceCollection");

    public static class ClassMgr
    {
       /**
       * Returns a list of ResourceCollection for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.ResourceCollection
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceCollection> listResourceCollections(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceCollection>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.ResourceCollection for all models
       * @return Iterator of org.semanticwb.model.ResourceCollection
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceCollection> listResourceCollections()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceCollection>(it, true);
        }

        public static org.semanticwb.model.ResourceCollection createResourceCollection(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.ResourceCollection.ClassMgr.createResourceCollection(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.model.ResourceCollection
       * @param id Identifier for org.semanticwb.model.ResourceCollection
       * @param model Model of the org.semanticwb.model.ResourceCollection
       * @return A org.semanticwb.model.ResourceCollection
       */
        public static org.semanticwb.model.ResourceCollection getResourceCollection(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.ResourceCollection)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.ResourceCollection
       * @param id Identifier for org.semanticwb.model.ResourceCollection
       * @param model Model of the org.semanticwb.model.ResourceCollection
       * @return A org.semanticwb.model.ResourceCollection
       */
        public static org.semanticwb.model.ResourceCollection createResourceCollection(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.ResourceCollection)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.ResourceCollection
       * @param id Identifier for org.semanticwb.model.ResourceCollection
       * @param model Model of the org.semanticwb.model.ResourceCollection
       */
        public static void removeResourceCollection(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.ResourceCollection
       * @param id Identifier for org.semanticwb.model.ResourceCollection
       * @param model Model of the org.semanticwb.model.ResourceCollection
       * @return true if the org.semanticwb.model.ResourceCollection exists, false otherwise
       */

        public static boolean hasResourceCollection(String id, org.semanticwb.model.SWBModel model)
        {
            return (getResourceCollection(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.ResourceCollection with a determined Category
       * @param value Category of the type org.semanticwb.model.ResourceCollectionCategory
       * @param model Model of the org.semanticwb.model.ResourceCollection
       * @return Iterator with all the org.semanticwb.model.ResourceCollection
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceCollection> listResourceCollectionByCategory(org.semanticwb.model.ResourceCollectionCategory value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceCollection> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResourceCollectionCategoryInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ResourceCollection with a determined Category
       * @param value Category of the type org.semanticwb.model.ResourceCollectionCategory
       * @return Iterator with all the org.semanticwb.model.ResourceCollection
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceCollection> listResourceCollectionByCategory(org.semanticwb.model.ResourceCollectionCategory value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceCollection> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResourceCollectionCategoryInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ResourceCollection with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.ResourceCollection
       * @return Iterator with all the org.semanticwb.model.ResourceCollection
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceCollection> listResourceCollectionByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceCollection> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ResourceCollection with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.ResourceCollection
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceCollection> listResourceCollectionByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceCollection> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ResourceCollection with a determined DisplayWebPage
       * @param value DisplayWebPage of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.model.ResourceCollection
       * @return Iterator with all the org.semanticwb.model.ResourceCollection
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceCollection> listResourceCollectionByDisplayWebPage(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceCollection> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_displayWebPage, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ResourceCollection with a determined DisplayWebPage
       * @param value DisplayWebPage of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.ResourceCollection
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceCollection> listResourceCollectionByDisplayWebPage(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceCollection> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_displayWebPage,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ResourceCollection with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.ResourceCollection
       * @return Iterator with all the org.semanticwb.model.ResourceCollection
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceCollection> listResourceCollectionByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceCollection> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ResourceCollection with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.ResourceCollection
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceCollection> listResourceCollectionByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceCollection> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ResourceCollection with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @param model Model of the org.semanticwb.model.ResourceCollection
       * @return Iterator with all the org.semanticwb.model.ResourceCollection
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceCollection> listResourceCollectionByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceCollection> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ResourceCollection with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.model.ResourceCollection
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceCollection> listResourceCollectionByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceCollection> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ResourceCollection with a determined ResourceCollectionType
       * @param value ResourceCollectionType of the type org.semanticwb.model.ResourceType
       * @param model Model of the org.semanticwb.model.ResourceCollection
       * @return Iterator with all the org.semanticwb.model.ResourceCollection
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceCollection> listResourceCollectionByResourceCollectionType(org.semanticwb.model.ResourceType value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceCollection> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_resourceCollectionType, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ResourceCollection with a determined ResourceCollectionType
       * @param value ResourceCollectionType of the type org.semanticwb.model.ResourceType
       * @return Iterator with all the org.semanticwb.model.ResourceCollection
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceCollection> listResourceCollectionByResourceCollectionType(org.semanticwb.model.ResourceType value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceCollection> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_resourceCollectionType,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ResourceCollection with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @param model Model of the org.semanticwb.model.ResourceCollection
       * @return Iterator with all the org.semanticwb.model.ResourceCollection
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceCollection> listResourceCollectionByPFlowRef(org.semanticwb.model.PFlowRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceCollection> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ResourceCollection with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @return Iterator with all the org.semanticwb.model.ResourceCollection
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceCollection> listResourceCollectionByPFlowRef(org.semanticwb.model.PFlowRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceCollection> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ResourceCollectionBase.ClassMgr getResourceCollectionClassMgr()
    {
        return new ResourceCollectionBase.ClassMgr();
    }

   /**
   * Constructs a ResourceCollectionBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ResourceCollection
   */
    public ResourceCollectionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.model.ResourceCollectionCategory
   * @return A GenericIterator with all the org.semanticwb.model.ResourceCollectionCategory
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceCollectionCategory> listCategories()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceCollectionCategory>(getSemanticObject().listObjectProperties(swb_hasResourceCollectionCategoryInv));
    }

   /**
   * Gets true if has a Category
   * @param value org.semanticwb.model.ResourceCollectionCategory to verify
   * @return true if the org.semanticwb.model.ResourceCollectionCategory exists, false otherwise
   */
    public boolean hasCategory(org.semanticwb.model.ResourceCollectionCategory value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasResourceCollectionCategoryInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the Category
   * @return a org.semanticwb.model.ResourceCollectionCategory
   */
    public org.semanticwb.model.ResourceCollectionCategory getCategory()
    {
         org.semanticwb.model.ResourceCollectionCategory ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasResourceCollectionCategoryInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.ResourceCollectionCategory)obj.createGenericInstance();
         }
         return ret;
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
* Gets the NotInheritPFlowRef property
* @return boolean with the NotInheritPFlowRef
*/
    public boolean isNotInheritPFlowRef()
    {
        return getSemanticObject().getBooleanProperty(swb_notInheritPFlowRef);
    }

/**
* Sets the NotInheritPFlowRef property
* @param value long with the NotInheritPFlowRef
*/
    public void setNotInheritPFlowRef(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_notInheritPFlowRef, value);
    }
   /**
   * Sets the value for the property DisplayWebPage
   * @param value DisplayWebPage to set
   */

    public void setDisplayWebPage(org.semanticwb.model.WebPage value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_displayWebPage, value.getSemanticObject());
        }else
        {
            removeDisplayWebPage();
        }
    }
   /**
   * Remove the value for DisplayWebPage property
   */

    public void removeDisplayWebPage()
    {
        getSemanticObject().removeProperty(swb_displayWebPage);
    }

   /**
   * Gets the DisplayWebPage
   * @return a org.semanticwb.model.WebPage
   */
    public org.semanticwb.model.WebPage getDisplayWebPage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_displayWebPage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
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
   /**
   * Sets the value for the property ResourceCollectionType
   * @param value ResourceCollectionType to set
   */

    public void setResourceCollectionType(org.semanticwb.model.ResourceType value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_resourceCollectionType, value.getSemanticObject());
        }else
        {
            removeResourceCollectionType();
        }
    }
   /**
   * Remove the value for ResourceCollectionType property
   */

    public void removeResourceCollectionType()
    {
        getSemanticObject().removeProperty(swb_resourceCollectionType);
    }

   /**
   * Gets the ResourceCollectionType
   * @return a org.semanticwb.model.ResourceType
   */
    public org.semanticwb.model.ResourceType getResourceCollectionType()
    {
         org.semanticwb.model.ResourceType ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_resourceCollectionType);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.ResourceType)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.model.PFlowRef
   * @return A GenericIterator with all the org.semanticwb.model.PFlowRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef> listPFlowRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef>(getSemanticObject().listObjectProperties(swb_hasPFlowRef));
    }

   /**
   * Gets true if has a PFlowRef
   * @param value org.semanticwb.model.PFlowRef to verify
   * @return true if the org.semanticwb.model.PFlowRef exists, false otherwise
   */
    public boolean hasPFlowRef(org.semanticwb.model.PFlowRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasPFlowRef,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets all the PFlowRefs inherits
   * @return A GenericIterator with all the org.semanticwb.model.PFlowRef
   */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef> listInheritPFlowRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef>(getSemanticObject().listInheritProperties(swb_hasPFlowRef));
    }
   /**
   * Adds a PFlowRef
   * @param value org.semanticwb.model.PFlowRef to add
   */

    public void addPFlowRef(org.semanticwb.model.PFlowRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasPFlowRef, value.getSemanticObject());
    }
   /**
   * Removes all the PFlowRef
   */

    public void removeAllPFlowRef()
    {
        getSemanticObject().removeProperty(swb_hasPFlowRef);
    }
   /**
   * Removes a PFlowRef
   * @param value org.semanticwb.model.PFlowRef to remove
   */

    public void removePFlowRef(org.semanticwb.model.PFlowRef value)
    {
        getSemanticObject().removeObjectProperty(swb_hasPFlowRef,value.getSemanticObject());
    }

   /**
   * Gets the PFlowRef
   * @return a org.semanticwb.model.PFlowRef
   */
    public org.semanticwb.model.PFlowRef getPFlowRef()
    {
         org.semanticwb.model.PFlowRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasPFlowRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.PFlowRef)obj.createGenericInstance();
         }
         return ret;
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
