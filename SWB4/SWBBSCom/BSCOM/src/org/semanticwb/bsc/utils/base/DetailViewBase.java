package org.semanticwb.bsc.utils.base;


   /**
   * Contiene la estructura de datos necesaria para representar una vista detalle de cualquiera de los elementos del BSC (objetivos, indicadores o iniciativas) 
   */
public abstract class DetailViewBase extends org.semanticwb.model.SWBClass implements org.semanticwb.bsc.utils.ViewTypeConfigurable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
   /**
   * Representa la ruta en filesystem del archivo en que se almacena la configuración de la vista detalle.
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_viewFilePath=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#viewFilePath");
   /**
   * Contiene la estructura de datos necesaria para representar una vista detalle de cualquiera de los elementos del BSC (objetivos, indicadores o iniciativas)
   */
    public static final org.semanticwb.platform.SemanticClass bsc_DetailView=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#DetailView");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#DetailView");

    public static class ClassMgr
    {
       /**
       * Returns a list of DetailView for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.utils.DetailView
       */

        public static java.util.Iterator<org.semanticwb.bsc.utils.DetailView> listDetailViews(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.utils.DetailView>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.utils.DetailView for all models
       * @return Iterator of org.semanticwb.bsc.utils.DetailView
       */

        public static java.util.Iterator<org.semanticwb.bsc.utils.DetailView> listDetailViews()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.utils.DetailView>(it, true);
        }

        public static org.semanticwb.bsc.utils.DetailView createDetailView(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.utils.DetailView.ClassMgr.createDetailView(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.utils.DetailView
       * @param id Identifier for org.semanticwb.bsc.utils.DetailView
       * @param model Model of the org.semanticwb.bsc.utils.DetailView
       * @return A org.semanticwb.bsc.utils.DetailView
       */
        public static org.semanticwb.bsc.utils.DetailView getDetailView(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.utils.DetailView)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.utils.DetailView
       * @param id Identifier for org.semanticwb.bsc.utils.DetailView
       * @param model Model of the org.semanticwb.bsc.utils.DetailView
       * @return A org.semanticwb.bsc.utils.DetailView
       */
        public static org.semanticwb.bsc.utils.DetailView createDetailView(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.utils.DetailView)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.utils.DetailView
       * @param id Identifier for org.semanticwb.bsc.utils.DetailView
       * @param model Model of the org.semanticwb.bsc.utils.DetailView
       */
        public static void removeDetailView(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.utils.DetailView
       * @param id Identifier for org.semanticwb.bsc.utils.DetailView
       * @param model Model of the org.semanticwb.bsc.utils.DetailView
       * @return true if the org.semanticwb.bsc.utils.DetailView exists, false otherwise
       */

        public static boolean hasDetailView(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDetailView(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.utils.DetailView with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.utils.DetailView
       * @return Iterator with all the org.semanticwb.bsc.utils.DetailView
       */

        public static java.util.Iterator<org.semanticwb.bsc.utils.DetailView> listDetailViewByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.utils.DetailView> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.utils.DetailView with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.utils.DetailView
       */

        public static java.util.Iterator<org.semanticwb.bsc.utils.DetailView> listDetailViewByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.utils.DetailView> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.utils.DetailView with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.utils.DetailView
       * @return Iterator with all the org.semanticwb.bsc.utils.DetailView
       */

        public static java.util.Iterator<org.semanticwb.bsc.utils.DetailView> listDetailViewByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.utils.DetailView> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.utils.DetailView with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.utils.DetailView
       */

        public static java.util.Iterator<org.semanticwb.bsc.utils.DetailView> listDetailViewByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.utils.DetailView> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DetailViewBase.ClassMgr getDetailViewClassMgr()
    {
        return new DetailViewBase.ClassMgr();
    }

   /**
   * Constructs a DetailViewBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DetailView
   */
    public DetailViewBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the ViewFilePath property
* @return String with the ViewFilePath
*/
    public String getViewFilePath()
    {
        return getSemanticObject().getProperty(bsc_viewFilePath);
    }

/**
* Sets the ViewFilePath property
* @param value long with the ViewFilePath
*/
    public void setViewFilePath(String value)
    {
        getSemanticObject().setProperty(bsc_viewFilePath, value);
    }
}
