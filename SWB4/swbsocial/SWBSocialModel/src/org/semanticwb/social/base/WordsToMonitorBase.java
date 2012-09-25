package org.semanticwb.social.base;


   /**
   * Clase en donde se almacenan las palabras que se desean monitorear 
   */
public abstract class WordsToMonitorBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
   /**
   * Palabras a monitorear acerca de la compañia
   */
    public static final org.semanticwb.platform.SemanticProperty social_company=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#company");
   /**
   * Palabras a monitorear acerca de los productos y servicios que ofresca la compañia
   */
    public static final org.semanticwb.platform.SemanticProperty social_ProductsAndServices=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#ProductsAndServices");
   /**
   * Algunas otras palabras que se deseen monitorear
   */
    public static final org.semanticwb.platform.SemanticProperty social_OtherWords=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#OtherWords");
   /**
   * Palabras a monitoreas acerca de la competencia
   */
    public static final org.semanticwb.platform.SemanticProperty social_competition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#competition");
   /**
   * Clase en donde se almacenan las palabras que se desean monitorear
   */
    public static final org.semanticwb.platform.SemanticClass social_WordsToMonitor=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#WordsToMonitor");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#WordsToMonitor");

    public static class ClassMgr
    {
       /**
       * Returns a list of WordsToMonitor for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.WordsToMonitor
       */

        public static java.util.Iterator<org.semanticwb.social.WordsToMonitor> listWordsToMonitors(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.WordsToMonitor>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.WordsToMonitor for all models
       * @return Iterator of org.semanticwb.social.WordsToMonitor
       */

        public static java.util.Iterator<org.semanticwb.social.WordsToMonitor> listWordsToMonitors()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.WordsToMonitor>(it, true);
        }

        public static org.semanticwb.social.WordsToMonitor createWordsToMonitor(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.WordsToMonitor.ClassMgr.createWordsToMonitor(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.WordsToMonitor
       * @param id Identifier for org.semanticwb.social.WordsToMonitor
       * @param model Model of the org.semanticwb.social.WordsToMonitor
       * @return A org.semanticwb.social.WordsToMonitor
       */
        public static org.semanticwb.social.WordsToMonitor getWordsToMonitor(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.WordsToMonitor)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.WordsToMonitor
       * @param id Identifier for org.semanticwb.social.WordsToMonitor
       * @param model Model of the org.semanticwb.social.WordsToMonitor
       * @return A org.semanticwb.social.WordsToMonitor
       */
        public static org.semanticwb.social.WordsToMonitor createWordsToMonitor(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.WordsToMonitor)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.WordsToMonitor
       * @param id Identifier for org.semanticwb.social.WordsToMonitor
       * @param model Model of the org.semanticwb.social.WordsToMonitor
       */
        public static void removeWordsToMonitor(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.WordsToMonitor
       * @param id Identifier for org.semanticwb.social.WordsToMonitor
       * @param model Model of the org.semanticwb.social.WordsToMonitor
       * @return true if the org.semanticwb.social.WordsToMonitor exists, false otherwise
       */

        public static boolean hasWordsToMonitor(String id, org.semanticwb.model.SWBModel model)
        {
            return (getWordsToMonitor(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.WordsToMonitor with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.WordsToMonitor
       * @return Iterator with all the org.semanticwb.social.WordsToMonitor
       */

        public static java.util.Iterator<org.semanticwb.social.WordsToMonitor> listWordsToMonitorByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.WordsToMonitor> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.WordsToMonitor with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.WordsToMonitor
       */

        public static java.util.Iterator<org.semanticwb.social.WordsToMonitor> listWordsToMonitorByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.WordsToMonitor> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.WordsToMonitor with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.WordsToMonitor
       * @return Iterator with all the org.semanticwb.social.WordsToMonitor
       */

        public static java.util.Iterator<org.semanticwb.social.WordsToMonitor> listWordsToMonitorByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.WordsToMonitor> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.WordsToMonitor with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.WordsToMonitor
       */

        public static java.util.Iterator<org.semanticwb.social.WordsToMonitor> listWordsToMonitorByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.WordsToMonitor> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static WordsToMonitorBase.ClassMgr getWordsToMonitorClassMgr()
    {
        return new WordsToMonitorBase.ClassMgr();
    }

   /**
   * Constructs a WordsToMonitorBase with a SemanticObject
   * @param base The SemanticObject with the properties for the WordsToMonitor
   */
    public WordsToMonitorBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the Company property
* @return String with the Company
*/
    public String getCompany()
    {
        return getSemanticObject().getProperty(social_company);
    }

/**
* Sets the Company property
* @param value long with the Company
*/
    public void setCompany(String value)
    {
        getSemanticObject().setProperty(social_company, value);
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
* Gets the ProductsAndServices property
* @return String with the ProductsAndServices
*/
    public String getProductsAndServices()
    {
        return getSemanticObject().getProperty(social_ProductsAndServices);
    }

/**
* Sets the ProductsAndServices property
* @param value long with the ProductsAndServices
*/
    public void setProductsAndServices(String value)
    {
        getSemanticObject().setProperty(social_ProductsAndServices, value);
    }

/**
* Gets the OtherWords property
* @return String with the OtherWords
*/
    public String getOtherWords()
    {
        return getSemanticObject().getProperty(social_OtherWords);
    }

/**
* Sets the OtherWords property
* @param value long with the OtherWords
*/
    public void setOtherWords(String value)
    {
        getSemanticObject().setProperty(social_OtherWords, value);
    }

/**
* Gets the Competition property
* @return String with the Competition
*/
    public String getCompetition()
    {
        return getSemanticObject().getProperty(social_competition);
    }

/**
* Sets the Competition property
* @param value long with the Competition
*/
    public void setCompetition(String value)
    {
        getSemanticObject().setProperty(social_competition, value);
    }
}
