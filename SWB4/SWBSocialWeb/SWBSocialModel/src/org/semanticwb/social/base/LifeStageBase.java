package org.semanticwb.social.base;


   /**
   * Define las diferentes etapas de la vida de una persona. 
   */
public abstract class LifeStageBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
   /**
   * Edad Maxima considerada para la etapa de la vida especificada
   */
    public static final org.semanticwb.platform.SemanticProperty social_ls_maxAge=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#ls_maxAge");
   /**
   * Minima edad que debe de cumplir un usuario para estar en una determinada etapa de la vida
   */
    public static final org.semanticwb.platform.SemanticProperty social_ls_minAge=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#ls_minAge");
   /**
   * Define las diferentes etapas de la vida de una persona.
   */
    public static final org.semanticwb.platform.SemanticClass social_LifeStage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#LifeStage");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#LifeStage");

    public static class ClassMgr
    {
       /**
       * Returns a list of LifeStage for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.LifeStage
       */

        public static java.util.Iterator<org.semanticwb.social.LifeStage> listLifeStages(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.LifeStage>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.LifeStage for all models
       * @return Iterator of org.semanticwb.social.LifeStage
       */

        public static java.util.Iterator<org.semanticwb.social.LifeStage> listLifeStages()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.LifeStage>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.LifeStage
       * @param id Identifier for org.semanticwb.social.LifeStage
       * @param model Model of the org.semanticwb.social.LifeStage
       * @return A org.semanticwb.social.LifeStage
       */
        public static org.semanticwb.social.LifeStage getLifeStage(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.LifeStage)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.LifeStage
       * @param id Identifier for org.semanticwb.social.LifeStage
       * @param model Model of the org.semanticwb.social.LifeStage
       * @return A org.semanticwb.social.LifeStage
       */
        public static org.semanticwb.social.LifeStage createLifeStage(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.LifeStage)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.LifeStage
       * @param id Identifier for org.semanticwb.social.LifeStage
       * @param model Model of the org.semanticwb.social.LifeStage
       */
        public static void removeLifeStage(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.LifeStage
       * @param id Identifier for org.semanticwb.social.LifeStage
       * @param model Model of the org.semanticwb.social.LifeStage
       * @return true if the org.semanticwb.social.LifeStage exists, false otherwise
       */

        public static boolean hasLifeStage(String id, org.semanticwb.model.SWBModel model)
        {
            return (getLifeStage(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.LifeStage with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.LifeStage
       * @return Iterator with all the org.semanticwb.social.LifeStage
       */

        public static java.util.Iterator<org.semanticwb.social.LifeStage> listLifeStageByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.LifeStage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.LifeStage with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.LifeStage
       */

        public static java.util.Iterator<org.semanticwb.social.LifeStage> listLifeStageByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.LifeStage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.LifeStage with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.LifeStage
       * @return Iterator with all the org.semanticwb.social.LifeStage
       */

        public static java.util.Iterator<org.semanticwb.social.LifeStage> listLifeStageByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.LifeStage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.LifeStage with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.LifeStage
       */

        public static java.util.Iterator<org.semanticwb.social.LifeStage> listLifeStageByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.LifeStage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static LifeStageBase.ClassMgr getLifeStageClassMgr()
    {
        return new LifeStageBase.ClassMgr();
    }

   /**
   * Constructs a LifeStageBase with a SemanticObject
   * @param base The SemanticObject with the properties for the LifeStage
   */
    public LifeStageBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the Ls_maxAge property
* @return int with the Ls_maxAge
*/
    public int getLs_maxAge()
    {
        return getSemanticObject().getIntProperty(social_ls_maxAge);
    }

/**
* Sets the Ls_maxAge property
* @param value long with the Ls_maxAge
*/
    public void setLs_maxAge(int value)
    {
        getSemanticObject().setIntProperty(social_ls_maxAge, value);
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
* Gets the Ls_minAge property
* @return int with the Ls_minAge
*/
    public int getLs_minAge()
    {
        return getSemanticObject().getIntProperty(social_ls_minAge);
    }

/**
* Sets the Ls_minAge property
* @param value long with the Ls_minAge
*/
    public void setLs_minAge(int value)
    {
        getSemanticObject().setIntProperty(social_ls_minAge, value);
    }
}
