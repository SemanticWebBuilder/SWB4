package org.semanticwb.portal.resources.sem.catalog.base;


public abstract class CatalogBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty catalog_hasCatalogDetailProperties=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sem/SWBCatalogResource#hasCatalogDetailProperties");
    public static final org.semanticwb.platform.SemanticProperty catalog_hasCatalogSearchProperties=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sem/SWBCatalogResource#hasCatalogSearchProperties");
   /**
   * Superclase de todos los objetos con persistencia en SemanticWebBuilder
   */
    public static final org.semanticwb.platform.SemanticClass swb_SWBClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBClass");
    public static final org.semanticwb.platform.SemanticProperty catalog_catalogClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sem/SWBCatalogResource#catalogClass");
    public static final org.semanticwb.platform.SemanticProperty catalog_hasCatalogListProperties=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sem/SWBCatalogResource#hasCatalogListProperties");
   /**
   * Superclase de todos los tipos de Modelos de SemanticWebBuilder
   */
    public static final org.semanticwb.platform.SemanticClass swb_SWBModel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBModel");
    public static final org.semanticwb.platform.SemanticProperty catalog_catalogModel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sem/SWBCatalogResource#catalogModel");
    public static final org.semanticwb.platform.SemanticClass catalog_Catalog=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sem/SWBCatalogResource#Catalog");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sem/SWBCatalogResource#Catalog");

    public static class ClassMgr
    {
       /**
       * Returns a list of Catalog for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.portal.resources.sem.catalog.Catalog
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.catalog.Catalog> listCatalogs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.catalog.Catalog>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.portal.resources.sem.catalog.Catalog for all models
       * @return Iterator of org.semanticwb.portal.resources.sem.catalog.Catalog
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.catalog.Catalog> listCatalogs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.catalog.Catalog>(it, true);
        }

        public static org.semanticwb.portal.resources.sem.catalog.Catalog createCatalog(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.catalog.Catalog.ClassMgr.createCatalog(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.portal.resources.sem.catalog.Catalog
       * @param id Identifier for org.semanticwb.portal.resources.sem.catalog.Catalog
       * @param model Model of the org.semanticwb.portal.resources.sem.catalog.Catalog
       * @return A org.semanticwb.portal.resources.sem.catalog.Catalog
       */
        public static org.semanticwb.portal.resources.sem.catalog.Catalog getCatalog(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.catalog.Catalog)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.portal.resources.sem.catalog.Catalog
       * @param id Identifier for org.semanticwb.portal.resources.sem.catalog.Catalog
       * @param model Model of the org.semanticwb.portal.resources.sem.catalog.Catalog
       * @return A org.semanticwb.portal.resources.sem.catalog.Catalog
       */
        public static org.semanticwb.portal.resources.sem.catalog.Catalog createCatalog(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.catalog.Catalog)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.portal.resources.sem.catalog.Catalog
       * @param id Identifier for org.semanticwb.portal.resources.sem.catalog.Catalog
       * @param model Model of the org.semanticwb.portal.resources.sem.catalog.Catalog
       */
        public static void removeCatalog(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.portal.resources.sem.catalog.Catalog
       * @param id Identifier for org.semanticwb.portal.resources.sem.catalog.Catalog
       * @param model Model of the org.semanticwb.portal.resources.sem.catalog.Catalog
       * @return true if the org.semanticwb.portal.resources.sem.catalog.Catalog exists, false otherwise
       */

        public static boolean hasCatalog(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCatalog(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.catalog.Catalog with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.portal.resources.sem.catalog.Catalog
       * @return Iterator with all the org.semanticwb.portal.resources.sem.catalog.Catalog
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.catalog.Catalog> listCatalogByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.catalog.Catalog> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.catalog.Catalog with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.portal.resources.sem.catalog.Catalog
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.catalog.Catalog> listCatalogByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.catalog.Catalog> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.catalog.Catalog with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.portal.resources.sem.catalog.Catalog
       * @return Iterator with all the org.semanticwb.portal.resources.sem.catalog.Catalog
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.catalog.Catalog> listCatalogByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.catalog.Catalog> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.catalog.Catalog with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.portal.resources.sem.catalog.Catalog
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.catalog.Catalog> listCatalogByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.catalog.Catalog> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.catalog.Catalog with a determined CatalogClass
       * @param value CatalogClass of the type org.semanticwb.model.SWBClass
       * @param model Model of the org.semanticwb.portal.resources.sem.catalog.Catalog
       * @return Iterator with all the org.semanticwb.portal.resources.sem.catalog.Catalog
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.catalog.Catalog> listCatalogByCatalogClass(org.semanticwb.model.SWBClass value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.catalog.Catalog> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(catalog_catalogClass, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.catalog.Catalog with a determined CatalogClass
       * @param value CatalogClass of the type org.semanticwb.model.SWBClass
       * @return Iterator with all the org.semanticwb.portal.resources.sem.catalog.Catalog
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.catalog.Catalog> listCatalogByCatalogClass(org.semanticwb.model.SWBClass value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.catalog.Catalog> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(catalog_catalogClass,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.catalog.Catalog with a determined CatalogModel
       * @param value CatalogModel of the type org.semanticwb.model.SWBModel
       * @param model Model of the org.semanticwb.portal.resources.sem.catalog.Catalog
       * @return Iterator with all the org.semanticwb.portal.resources.sem.catalog.Catalog
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.catalog.Catalog> listCatalogByCatalogModel(org.semanticwb.model.SWBModel value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.catalog.Catalog> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(catalog_catalogModel, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.catalog.Catalog with a determined CatalogModel
       * @param value CatalogModel of the type org.semanticwb.model.SWBModel
       * @return Iterator with all the org.semanticwb.portal.resources.sem.catalog.Catalog
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.catalog.Catalog> listCatalogByCatalogModel(org.semanticwb.model.SWBModel value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.catalog.Catalog> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(catalog_catalogModel,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a CatalogBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Catalog
   */
    public CatalogBase(org.semanticwb.platform.SemanticObject base)
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

    public java.util.Iterator<String> listDetailPropertieses()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(catalog_hasCatalogDetailProperties);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addDetailProperties(String value)
    {
        getSemanticObject().addLiteralProperty(catalog_hasCatalogDetailProperties, new org.semanticwb.platform.SemanticLiteral(value));
    }

    public void removeAllDetailProperties()
    {
        getSemanticObject().removeProperty(catalog_hasCatalogDetailProperties);
    }

    public void removeDetailProperties(String value)
    {
        getSemanticObject().removeLiteralProperty(catalog_hasCatalogDetailProperties,new org.semanticwb.platform.SemanticLiteral(value));
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

    public java.util.Iterator<String> listSearchPropertieses()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(catalog_hasCatalogSearchProperties);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addSearchProperties(String value)
    {
        getSemanticObject().addLiteralProperty(catalog_hasCatalogSearchProperties, new org.semanticwb.platform.SemanticLiteral(value));
    }

    public void removeAllSearchProperties()
    {
        getSemanticObject().removeProperty(catalog_hasCatalogSearchProperties);
    }

    public void removeSearchProperties(String value)
    {
        getSemanticObject().removeLiteralProperty(catalog_hasCatalogSearchProperties,new org.semanticwb.platform.SemanticLiteral(value));
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
   * Sets the value for the property CatalogClass
   * @param value CatalogClass to set
   */

    public void setCatalogClass(org.semanticwb.model.SWBClass value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(catalog_catalogClass, value.getSemanticObject());
        }else
        {
            removeCatalogClass();
        }
    }
   /**
   * Remove the value for CatalogClass property
   */

    public void removeCatalogClass()
    {
        getSemanticObject().removeProperty(catalog_catalogClass);
    }

   /**
   * Gets the CatalogClass
   * @return a org.semanticwb.model.SWBClass
   */
    public org.semanticwb.model.SWBClass getCatalogClass()
    {
         org.semanticwb.model.SWBClass ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(catalog_catalogClass);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.SWBClass)obj.createGenericInstance();
         }
         return ret;
    }

    public java.util.Iterator<String> listListPropertieses()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(catalog_hasCatalogListProperties);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addListProperties(String value)
    {
        getSemanticObject().addLiteralProperty(catalog_hasCatalogListProperties, new org.semanticwb.platform.SemanticLiteral(value));
    }

    public void removeAllListProperties()
    {
        getSemanticObject().removeProperty(catalog_hasCatalogListProperties);
    }

    public void removeListProperties(String value)
    {
        getSemanticObject().removeLiteralProperty(catalog_hasCatalogListProperties,new org.semanticwb.platform.SemanticLiteral(value));
    }
   /**
   * Sets the value for the property CatalogModel
   * @param value CatalogModel to set
   */

    public void setCatalogModel(org.semanticwb.model.SWBModel value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(catalog_catalogModel, value.getSemanticObject());
        }else
        {
            removeCatalogModel();
        }
    }
   /**
   * Remove the value for CatalogModel property
   */

    public void removeCatalogModel()
    {
        getSemanticObject().removeProperty(catalog_catalogModel);
    }

   /**
   * Gets the CatalogModel
   * @return a org.semanticwb.model.SWBModel
   */
    public org.semanticwb.model.SWBModel getCatalogModel()
    {
         org.semanticwb.model.SWBModel ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(catalog_catalogModel);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.SWBModel)obj.createGenericInstance();
         }
         return ret;
    }
}
