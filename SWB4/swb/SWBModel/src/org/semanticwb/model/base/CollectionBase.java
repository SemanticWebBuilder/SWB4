package org.semanticwb.model.base;


   /**
   * Define una Collección de objetos de una clase especificada con la propiedad "collectionClass" 
   */
public abstract class CollectionBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.FilterableNode,org.semanticwb.model.Descriptiveable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Filterable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty swb_hasCollectionSearchProperties=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasCollectionSearchProperties");
   /**
   * Superclase de todos los tipos de Modelos de SemanticWebBuilder
   */
    public static final org.semanticwb.platform.SemanticClass swb_SWBModel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBModel");
    public static final org.semanticwb.platform.SemanticProperty swb_collectionModel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#collectionModel");
    public static final org.semanticwb.platform.SemanticClass swb_Class=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Class");
    public static final org.semanticwb.platform.SemanticProperty swb_collectionClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#collectionClass");
    public static final org.semanticwb.platform.SemanticProperty swb_hasCollectionListProperties=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasCollectionListProperties");
   /**
   * Define una Collección de objetos de una clase especificada con la propiedad "collectionClass"
   */
    public static final org.semanticwb.platform.SemanticClass swb_Collection=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Collection");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Collection");

    public static class ClassMgr
    {
       /**
       * Returns a list of Collection for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.Collection
       */

        public static java.util.Iterator<org.semanticwb.model.Collection> listCollections(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Collection>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.Collection for all models
       * @return Iterator of org.semanticwb.model.Collection
       */

        public static java.util.Iterator<org.semanticwb.model.Collection> listCollections()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Collection>(it, true);
        }

        public static org.semanticwb.model.Collection createCollection(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.Collection.ClassMgr.createCollection(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.model.Collection
       * @param id Identifier for org.semanticwb.model.Collection
       * @param model Model of the org.semanticwb.model.Collection
       * @return A org.semanticwb.model.Collection
       */
        public static org.semanticwb.model.Collection getCollection(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Collection)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.Collection
       * @param id Identifier for org.semanticwb.model.Collection
       * @param model Model of the org.semanticwb.model.Collection
       * @return A org.semanticwb.model.Collection
       */
        public static org.semanticwb.model.Collection createCollection(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Collection)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.Collection
       * @param id Identifier for org.semanticwb.model.Collection
       * @param model Model of the org.semanticwb.model.Collection
       */
        public static void removeCollection(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.Collection
       * @param id Identifier for org.semanticwb.model.Collection
       * @param model Model of the org.semanticwb.model.Collection
       * @return true if the org.semanticwb.model.Collection exists, false otherwise
       */

        public static boolean hasCollection(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCollection(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.Collection with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.Collection
       * @return Iterator with all the org.semanticwb.model.Collection
       */

        public static java.util.Iterator<org.semanticwb.model.Collection> listCollectionByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Collection> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Collection with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.Collection
       */

        public static java.util.Iterator<org.semanticwb.model.Collection> listCollectionByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Collection> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Collection with a determined CollectionModel
       * @param value CollectionModel of the type org.semanticwb.model.SWBModel
       * @param model Model of the org.semanticwb.model.Collection
       * @return Iterator with all the org.semanticwb.model.Collection
       */

        public static java.util.Iterator<org.semanticwb.model.Collection> listCollectionByCollectionModel(org.semanticwb.model.SWBModel value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Collection> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_collectionModel, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Collection with a determined CollectionModel
       * @param value CollectionModel of the type org.semanticwb.model.SWBModel
       * @return Iterator with all the org.semanticwb.model.Collection
       */

        public static java.util.Iterator<org.semanticwb.model.Collection> listCollectionByCollectionModel(org.semanticwb.model.SWBModel value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Collection> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_collectionModel,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Collection with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.Collection
       * @return Iterator with all the org.semanticwb.model.Collection
       */

        public static java.util.Iterator<org.semanticwb.model.Collection> listCollectionByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Collection> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Collection with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.Collection
       */

        public static java.util.Iterator<org.semanticwb.model.Collection> listCollectionByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Collection> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static CollectionBase.ClassMgr getCollectionClassMgr()
    {
        return new CollectionBase.ClassMgr();
    }

   /**
   * Constructs a CollectionBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Collection
   */
    public CollectionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public java.util.Iterator<String> listSearchPropertieses()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(swb_hasCollectionSearchProperties);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addSearchProperties(String value)
    {
        getSemanticObject().addLiteralProperty(swb_hasCollectionSearchProperties, new org.semanticwb.platform.SemanticLiteral(value));
    }

    public void removeAllSearchProperties()
    {
        getSemanticObject().removeProperty(swb_hasCollectionSearchProperties);
    }

    public void removeSearchProperties(String value)
    {
        getSemanticObject().removeLiteralProperty(swb_hasCollectionSearchProperties,new org.semanticwb.platform.SemanticLiteral(value));
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
   * Sets the value for the property CollectionModel
   * @param value CollectionModel to set
   */

    public void setCollectionModel(org.semanticwb.model.SWBModel value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_collectionModel, value.getSemanticObject());
        }else
        {
            removeCollectionModel();
        }
    }
   /**
   * Remove the value for CollectionModel property
   */

    public void removeCollectionModel()
    {
        getSemanticObject().removeProperty(swb_collectionModel);
    }

   /**
   * Gets the CollectionModel
   * @return a org.semanticwb.model.SWBModel
   */
    public org.semanticwb.model.SWBModel getCollectionModel()
    {
         org.semanticwb.model.SWBModel ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_collectionModel);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.SWBModel)obj.createGenericInstance();
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

    public void setCollectionClass(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().setObjectProperty(swb_collectionClass, value);
    }

    public void removeCollectionClass()
    {
        getSemanticObject().removeProperty(swb_collectionClass);
    }

/**
* Gets the CollectionClass property
* @return the value for the property as org.semanticwb.platform.SemanticObject
*/
    public org.semanticwb.platform.SemanticObject getCollectionClass()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swb_collectionClass);
         return ret;
    }

    public java.util.Iterator<String> listListPropertieses()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(swb_hasCollectionListProperties);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addListProperties(String value)
    {
        getSemanticObject().addLiteralProperty(swb_hasCollectionListProperties, new org.semanticwb.platform.SemanticLiteral(value));
    }

    public void removeAllListProperties()
    {
        getSemanticObject().removeProperty(swb_hasCollectionListProperties);
    }

    public void removeListProperties(String value)
    {
        getSemanticObject().removeLiteralProperty(swb_hasCollectionListProperties,new org.semanticwb.platform.SemanticLiteral(value));
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
