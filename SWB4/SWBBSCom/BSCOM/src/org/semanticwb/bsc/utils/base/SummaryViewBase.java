package org.semanticwb.bsc.utils.base;


   /**
   * Define la estructura de propiedades a mostrar en las vistas resumen. 
   */
public abstract class SummaryViewBase extends org.semanticwb.model.SWBClass implements org.semanticwb.bsc.utils.ViewTypeConfigurable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
   /**
   * Define el orden en que debe aparecer una propiedad en la vista resumen correspondiente.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_PropertyListItem=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#PropertyListItem");
   /**
   * Representa el conjunto de propiedades que conforman la vista resumen.
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_hasPropertyListItem=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasPropertyListItem");
   /**
   * Define la estructura de propiedades a mostrar en las vistas resumen.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_SummaryView=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#SummaryView");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#SummaryView");

    public static class ClassMgr
    {
       /**
       * Returns a list of SummaryView for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.utils.SummaryView
       */

        public static java.util.Iterator<org.semanticwb.bsc.utils.SummaryView> listSummaryViews(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.utils.SummaryView>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.utils.SummaryView for all models
       * @return Iterator of org.semanticwb.bsc.utils.SummaryView
       */

        public static java.util.Iterator<org.semanticwb.bsc.utils.SummaryView> listSummaryViews()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.utils.SummaryView>(it, true);
        }

        public static org.semanticwb.bsc.utils.SummaryView createSummaryView(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.utils.SummaryView.ClassMgr.createSummaryView(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.utils.SummaryView
       * @param id Identifier for org.semanticwb.bsc.utils.SummaryView
       * @param model Model of the org.semanticwb.bsc.utils.SummaryView
       * @return A org.semanticwb.bsc.utils.SummaryView
       */
        public static org.semanticwb.bsc.utils.SummaryView getSummaryView(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.utils.SummaryView)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.utils.SummaryView
       * @param id Identifier for org.semanticwb.bsc.utils.SummaryView
       * @param model Model of the org.semanticwb.bsc.utils.SummaryView
       * @return A org.semanticwb.bsc.utils.SummaryView
       */
        public static org.semanticwb.bsc.utils.SummaryView createSummaryView(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.utils.SummaryView)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.utils.SummaryView
       * @param id Identifier for org.semanticwb.bsc.utils.SummaryView
       * @param model Model of the org.semanticwb.bsc.utils.SummaryView
       */
        public static void removeSummaryView(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.utils.SummaryView
       * @param id Identifier for org.semanticwb.bsc.utils.SummaryView
       * @param model Model of the org.semanticwb.bsc.utils.SummaryView
       * @return true if the org.semanticwb.bsc.utils.SummaryView exists, false otherwise
       */

        public static boolean hasSummaryView(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSummaryView(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.utils.SummaryView with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.utils.SummaryView
       * @return Iterator with all the org.semanticwb.bsc.utils.SummaryView
       */

        public static java.util.Iterator<org.semanticwb.bsc.utils.SummaryView> listSummaryViewByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.utils.SummaryView> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.utils.SummaryView with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.utils.SummaryView
       */

        public static java.util.Iterator<org.semanticwb.bsc.utils.SummaryView> listSummaryViewByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.utils.SummaryView> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.utils.SummaryView with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.utils.SummaryView
       * @return Iterator with all the org.semanticwb.bsc.utils.SummaryView
       */

        public static java.util.Iterator<org.semanticwb.bsc.utils.SummaryView> listSummaryViewByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.utils.SummaryView> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.utils.SummaryView with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.utils.SummaryView
       */

        public static java.util.Iterator<org.semanticwb.bsc.utils.SummaryView> listSummaryViewByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.utils.SummaryView> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.utils.SummaryView with a determined PropertyListItem
       * @param value PropertyListItem of the type org.semanticwb.bsc.utils.PropertyListItem
       * @param model Model of the org.semanticwb.bsc.utils.SummaryView
       * @return Iterator with all the org.semanticwb.bsc.utils.SummaryView
       */

        public static java.util.Iterator<org.semanticwb.bsc.utils.SummaryView> listSummaryViewByPropertyListItem(org.semanticwb.bsc.utils.PropertyListItem value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.utils.SummaryView> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasPropertyListItem, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.utils.SummaryView with a determined PropertyListItem
       * @param value PropertyListItem of the type org.semanticwb.bsc.utils.PropertyListItem
       * @return Iterator with all the org.semanticwb.bsc.utils.SummaryView
       */

        public static java.util.Iterator<org.semanticwb.bsc.utils.SummaryView> listSummaryViewByPropertyListItem(org.semanticwb.bsc.utils.PropertyListItem value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.utils.SummaryView> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasPropertyListItem,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SummaryViewBase.ClassMgr getSummaryViewClassMgr()
    {
        return new SummaryViewBase.ClassMgr();
    }

   /**
   * Constructs a SummaryViewBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SummaryView
   */
    public SummaryViewBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the ObjectsType property
* @return String with the ObjectsType
*/
    public String getObjectsType()
    {
        return getSemanticObject().getProperty(bsc_objectsType);
    }

/**
* Sets the ObjectsType property
* @param value long with the ObjectsType
*/
    public void setObjectsType(String value)
    {
        getSemanticObject().setProperty(bsc_objectsType, value);
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
   * Gets all the org.semanticwb.bsc.utils.PropertyListItem
   * @return A GenericIterator with all the org.semanticwb.bsc.utils.PropertyListItem
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.utils.PropertyListItem> listPropertyListItems()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.utils.PropertyListItem>(getSemanticObject().listObjectProperties(bsc_hasPropertyListItem));
    }

   /**
   * Gets true if has a PropertyListItem
   * @param value org.semanticwb.bsc.utils.PropertyListItem to verify
   * @return true if the org.semanticwb.bsc.utils.PropertyListItem exists, false otherwise
   */
    public boolean hasPropertyListItem(org.semanticwb.bsc.utils.PropertyListItem value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasPropertyListItem,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a PropertyListItem
   * @param value org.semanticwb.bsc.utils.PropertyListItem to add
   */

    public void addPropertyListItem(org.semanticwb.bsc.utils.PropertyListItem value)
    {
        getSemanticObject().addObjectProperty(bsc_hasPropertyListItem, value.getSemanticObject());
    }
   /**
   * Removes all the PropertyListItem
   */

    public void removeAllPropertyListItem()
    {
        getSemanticObject().removeProperty(bsc_hasPropertyListItem);
    }
   /**
   * Removes a PropertyListItem
   * @param value org.semanticwb.bsc.utils.PropertyListItem to remove
   */

    public void removePropertyListItem(org.semanticwb.bsc.utils.PropertyListItem value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasPropertyListItem,value.getSemanticObject());
    }

   /**
   * Gets the PropertyListItem
   * @return a org.semanticwb.bsc.utils.PropertyListItem
   */
    public org.semanticwb.bsc.utils.PropertyListItem getPropertyListItem()
    {
         org.semanticwb.bsc.utils.PropertyListItem ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasPropertyListItem);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.utils.PropertyListItem)obj.createGenericInstance();
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
}
