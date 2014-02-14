package org.semanticwb.bsc.catalogs.base;


public abstract class CatalogBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.FilterableNode,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Filterable
{
    public static final org.semanticwb.platform.SemanticClass bsc_Catalog=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Catalog");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Catalog");

    public static class ClassMgr
    {
       /**
       * Returns a list of Catalog for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.catalogs.Catalog
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Catalog> listCatalogs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Catalog>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.catalogs.Catalog for all models
       * @return Iterator of org.semanticwb.bsc.catalogs.Catalog
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Catalog> listCatalogs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Catalog>(it, true);
        }

        public static org.semanticwb.bsc.catalogs.Catalog createCatalog(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.catalogs.Catalog.ClassMgr.createCatalog(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.catalogs.Catalog
       * @param id Identifier for org.semanticwb.bsc.catalogs.Catalog
       * @param model Model of the org.semanticwb.bsc.catalogs.Catalog
       * @return A org.semanticwb.bsc.catalogs.Catalog
       */
        public static org.semanticwb.bsc.catalogs.Catalog getCatalog(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.catalogs.Catalog)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.catalogs.Catalog
       * @param id Identifier for org.semanticwb.bsc.catalogs.Catalog
       * @param model Model of the org.semanticwb.bsc.catalogs.Catalog
       * @return A org.semanticwb.bsc.catalogs.Catalog
       */
        public static org.semanticwb.bsc.catalogs.Catalog createCatalog(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.catalogs.Catalog)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.catalogs.Catalog
       * @param id Identifier for org.semanticwb.bsc.catalogs.Catalog
       * @param model Model of the org.semanticwb.bsc.catalogs.Catalog
       */
        public static void removeCatalog(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.catalogs.Catalog
       * @param id Identifier for org.semanticwb.bsc.catalogs.Catalog
       * @param model Model of the org.semanticwb.bsc.catalogs.Catalog
       * @return true if the org.semanticwb.bsc.catalogs.Catalog exists, false otherwise
       */

        public static boolean hasCatalog(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCatalog(id, model)!=null);
        }
    }

    public static CatalogBase.ClassMgr getCatalogClassMgr()
    {
        return new CatalogBase.ClassMgr();
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
}
