package org.semanticwb.transparencia.model.base;


public abstract class ActividadBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass trans_Actividad=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.transparencia.org/ontology#Actividad");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.transparencia.org/ontology#Actividad");

    public static class ClassMgr
    {
       /**
       * Returns a list of Actividad for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.transparencia.model.Actividad
       */

        public static java.util.Iterator<org.semanticwb.transparencia.model.Actividad> listActividads(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.transparencia.model.Actividad>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.transparencia.model.Actividad for all models
       * @return Iterator of org.semanticwb.transparencia.model.Actividad
       */

        public static java.util.Iterator<org.semanticwb.transparencia.model.Actividad> listActividads()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.transparencia.model.Actividad>(it, true);
        }
       /**
       * Gets a org.semanticwb.transparencia.model.Actividad
       * @param id Identifier for org.semanticwb.transparencia.model.Actividad
       * @param model Model of the org.semanticwb.transparencia.model.Actividad
       * @return A org.semanticwb.transparencia.model.Actividad
       */
        public static org.semanticwb.transparencia.model.Actividad getActividad(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.transparencia.model.Actividad)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.transparencia.model.Actividad
       * @param id Identifier for org.semanticwb.transparencia.model.Actividad
       * @param model Model of the org.semanticwb.transparencia.model.Actividad
       * @return A org.semanticwb.transparencia.model.Actividad
       */
        public static org.semanticwb.transparencia.model.Actividad createActividad(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.transparencia.model.Actividad)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.transparencia.model.Actividad
       * @param id Identifier for org.semanticwb.transparencia.model.Actividad
       * @param model Model of the org.semanticwb.transparencia.model.Actividad
       */
        public static void removeActividad(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.transparencia.model.Actividad
       * @param id Identifier for org.semanticwb.transparencia.model.Actividad
       * @param model Model of the org.semanticwb.transparencia.model.Actividad
       * @return true if the org.semanticwb.transparencia.model.Actividad exists, false otherwise
       */

        public static boolean hasActividad(String id, org.semanticwb.model.SWBModel model)
        {
            return (getActividad(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.transparencia.model.Actividad with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.transparencia.model.Actividad
       * @return Iterator with all the org.semanticwb.transparencia.model.Actividad
       */

        public static java.util.Iterator<org.semanticwb.transparencia.model.Actividad> listActividadByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.transparencia.model.Actividad> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.transparencia.model.Actividad with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.transparencia.model.Actividad
       */

        public static java.util.Iterator<org.semanticwb.transparencia.model.Actividad> listActividadByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.transparencia.model.Actividad> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.transparencia.model.Actividad with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.transparencia.model.Actividad
       * @return Iterator with all the org.semanticwb.transparencia.model.Actividad
       */

        public static java.util.Iterator<org.semanticwb.transparencia.model.Actividad> listActividadByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.transparencia.model.Actividad> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.transparencia.model.Actividad with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.transparencia.model.Actividad
       */

        public static java.util.Iterator<org.semanticwb.transparencia.model.Actividad> listActividadByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.transparencia.model.Actividad> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a ActividadBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Actividad
   */
    public ActividadBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
}
