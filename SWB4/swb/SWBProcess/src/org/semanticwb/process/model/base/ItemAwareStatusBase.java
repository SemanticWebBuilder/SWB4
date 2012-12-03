package org.semanticwb.process.model.base;


public abstract class ItemAwareStatusBase extends org.semanticwb.process.model.BaseElement implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swp_ItemAwareStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ItemAwareStatus");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ItemAwareStatus");

    public static class ClassMgr
    {
       /**
       * Returns a list of ItemAwareStatus for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ItemAwareStatus
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAwareStatus> listItemAwareStatuses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAwareStatus>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ItemAwareStatus for all models
       * @return Iterator of org.semanticwb.process.model.ItemAwareStatus
       */

        public static java.util.Iterator<org.semanticwb.process.model.ItemAwareStatus> listItemAwareStatuses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAwareStatus>(it, true);
        }

        public static org.semanticwb.process.model.ItemAwareStatus createItemAwareStatus(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ItemAwareStatus.ClassMgr.createItemAwareStatus(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.ItemAwareStatus
       * @param id Identifier for org.semanticwb.process.model.ItemAwareStatus
       * @param model Model of the org.semanticwb.process.model.ItemAwareStatus
       * @return A org.semanticwb.process.model.ItemAwareStatus
       */
        public static org.semanticwb.process.model.ItemAwareStatus getItemAwareStatus(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ItemAwareStatus)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.ItemAwareStatus
       * @param id Identifier for org.semanticwb.process.model.ItemAwareStatus
       * @param model Model of the org.semanticwb.process.model.ItemAwareStatus
       * @return A org.semanticwb.process.model.ItemAwareStatus
       */
        public static org.semanticwb.process.model.ItemAwareStatus createItemAwareStatus(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ItemAwareStatus)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ItemAwareStatus
       * @param id Identifier for org.semanticwb.process.model.ItemAwareStatus
       * @param model Model of the org.semanticwb.process.model.ItemAwareStatus
       */
        public static void removeItemAwareStatus(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ItemAwareStatus
       * @param id Identifier for org.semanticwb.process.model.ItemAwareStatus
       * @param model Model of the org.semanticwb.process.model.ItemAwareStatus
       * @return true if the org.semanticwb.process.model.ItemAwareStatus exists, false otherwise
       */

        public static boolean hasItemAwareStatus(String id, org.semanticwb.model.SWBModel model)
        {
            return (getItemAwareStatus(id, model)!=null);
        }
    }

    public static ItemAwareStatusBase.ClassMgr getItemAwareStatusClassMgr()
    {
        return new ItemAwareStatusBase.ClassMgr();
    }

   /**
   * Constructs a ItemAwareStatusBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ItemAwareStatus
   */
    public ItemAwareStatusBase(org.semanticwb.platform.SemanticObject base)
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
