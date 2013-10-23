package org.semanticwb.model.base;


public abstract class FriendlyURLBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Localeable,org.semanticwb.model.Traceable,org.semanticwb.model.Countryable
{
   /**
   * Una Página Web es el elemento de SemanticWebBuilder a través del cual se estructura la información del portal.
   */
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty swb_friendlyURLWebPageInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#friendlyURLWebPageInv");
    public static final org.semanticwb.platform.SemanticProperty swb_frindlyOldURL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#frindlyOldURL");
    public static final org.semanticwb.platform.SemanticProperty swb_friendlyURL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#friendlyURL");
    public static final org.semanticwb.platform.SemanticProperty swb_friendlyURLDescription=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#friendlyURLDescription");
    public static final org.semanticwb.platform.SemanticClass swb_FriendlyURL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#FriendlyURL");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#FriendlyURL");

    public static class ClassMgr
    {
       /**
       * Returns a list of FriendlyURL for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.FriendlyURL
       */

        public static java.util.Iterator<org.semanticwb.model.FriendlyURL> listFriendlyURLs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.FriendlyURL>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.FriendlyURL for all models
       * @return Iterator of org.semanticwb.model.FriendlyURL
       */

        public static java.util.Iterator<org.semanticwb.model.FriendlyURL> listFriendlyURLs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.FriendlyURL>(it, true);
        }

        public static org.semanticwb.model.FriendlyURL createFriendlyURL(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.FriendlyURL.ClassMgr.createFriendlyURL(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.model.FriendlyURL
       * @param id Identifier for org.semanticwb.model.FriendlyURL
       * @param model Model of the org.semanticwb.model.FriendlyURL
       * @return A org.semanticwb.model.FriendlyURL
       */
        public static org.semanticwb.model.FriendlyURL getFriendlyURL(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.FriendlyURL)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.FriendlyURL
       * @param id Identifier for org.semanticwb.model.FriendlyURL
       * @param model Model of the org.semanticwb.model.FriendlyURL
       * @return A org.semanticwb.model.FriendlyURL
       */
        public static org.semanticwb.model.FriendlyURL createFriendlyURL(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.FriendlyURL)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.FriendlyURL
       * @param id Identifier for org.semanticwb.model.FriendlyURL
       * @param model Model of the org.semanticwb.model.FriendlyURL
       */
        public static void removeFriendlyURL(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.FriendlyURL
       * @param id Identifier for org.semanticwb.model.FriendlyURL
       * @param model Model of the org.semanticwb.model.FriendlyURL
       * @return true if the org.semanticwb.model.FriendlyURL exists, false otherwise
       */

        public static boolean hasFriendlyURL(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFriendlyURL(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.FriendlyURL with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.FriendlyURL
       * @return Iterator with all the org.semanticwb.model.FriendlyURL
       */

        public static java.util.Iterator<org.semanticwb.model.FriendlyURL> listFriendlyURLByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.FriendlyURL> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.FriendlyURL with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.FriendlyURL
       */

        public static java.util.Iterator<org.semanticwb.model.FriendlyURL> listFriendlyURLByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.FriendlyURL> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.FriendlyURL with a determined WebPage
       * @param value WebPage of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.model.FriendlyURL
       * @return Iterator with all the org.semanticwb.model.FriendlyURL
       */

        public static java.util.Iterator<org.semanticwb.model.FriendlyURL> listFriendlyURLByWebPage(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.FriendlyURL> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_friendlyURLWebPageInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.FriendlyURL with a determined WebPage
       * @param value WebPage of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.FriendlyURL
       */

        public static java.util.Iterator<org.semanticwb.model.FriendlyURL> listFriendlyURLByWebPage(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.FriendlyURL> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_friendlyURLWebPageInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.FriendlyURL with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @param model Model of the org.semanticwb.model.FriendlyURL
       * @return Iterator with all the org.semanticwb.model.FriendlyURL
       */

        public static java.util.Iterator<org.semanticwb.model.FriendlyURL> listFriendlyURLByCountry(org.semanticwb.model.Country value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.FriendlyURL> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_country, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.FriendlyURL with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @return Iterator with all the org.semanticwb.model.FriendlyURL
       */

        public static java.util.Iterator<org.semanticwb.model.FriendlyURL> listFriendlyURLByCountry(org.semanticwb.model.Country value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.FriendlyURL> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_country,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.FriendlyURL with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.model.FriendlyURL
       * @return Iterator with all the org.semanticwb.model.FriendlyURL
       */

        public static java.util.Iterator<org.semanticwb.model.FriendlyURL> listFriendlyURLByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.FriendlyURL> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.FriendlyURL with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.model.FriendlyURL
       */

        public static java.util.Iterator<org.semanticwb.model.FriendlyURL> listFriendlyURLByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.FriendlyURL> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.FriendlyURL with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.FriendlyURL
       * @return Iterator with all the org.semanticwb.model.FriendlyURL
       */

        public static java.util.Iterator<org.semanticwb.model.FriendlyURL> listFriendlyURLByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.FriendlyURL> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.FriendlyURL with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.FriendlyURL
       */

        public static java.util.Iterator<org.semanticwb.model.FriendlyURL> listFriendlyURLByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.FriendlyURL> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static FriendlyURLBase.ClassMgr getFriendlyURLClassMgr()
    {
        return new FriendlyURLBase.ClassMgr();
    }

   /**
   * Constructs a FriendlyURLBase with a SemanticObject
   * @param base The SemanticObject with the properties for the FriendlyURL
   */
    public FriendlyURLBase(org.semanticwb.platform.SemanticObject base)
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
   * Sets the value for the property WebPage
   * @param value WebPage to set
   */

    public void setWebPage(org.semanticwb.model.WebPage value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_friendlyURLWebPageInv, value.getSemanticObject());
        }else
        {
            removeWebPage();
        }
    }
   /**
   * Remove the value for WebPage property
   */

    public void removeWebPage()
    {
        getSemanticObject().removeProperty(swb_friendlyURLWebPageInv);
    }

   /**
   * Gets the WebPage
   * @return a org.semanticwb.model.WebPage
   */
    public org.semanticwb.model.WebPage getWebPage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_friendlyURLWebPageInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the OldURL property
* @return boolean with the OldURL
*/
    public boolean isOldURL()
    {
        return getSemanticObject().getBooleanProperty(swb_frindlyOldURL);
    }

/**
* Sets the OldURL property
* @param value long with the OldURL
*/
    public void setOldURL(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_frindlyOldURL, value);
    }
   /**
   * Sets the value for the property Country
   * @param value Country to set
   */

    public void setCountry(org.semanticwb.model.Country value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_country, value.getSemanticObject());
        }else
        {
            removeCountry();
        }
    }
   /**
   * Remove the value for Country property
   */

    public void removeCountry()
    {
        getSemanticObject().removeProperty(swb_country);
    }

   /**
   * Gets the Country
   * @return a org.semanticwb.model.Country
   */
    public org.semanticwb.model.Country getCountry()
    {
         org.semanticwb.model.Country ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_country);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Country)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Language
   * @param value Language to set
   */

    public void setLanguage(org.semanticwb.model.Language value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_language, value.getSemanticObject());
        }else
        {
            removeLanguage();
        }
    }
   /**
   * Remove the value for Language property
   */

    public void removeLanguage()
    {
        getSemanticObject().removeProperty(swb_language);
    }

   /**
   * Gets the Language
   * @return a org.semanticwb.model.Language
   */
    public org.semanticwb.model.Language getLanguage()
    {
         org.semanticwb.model.Language ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_language);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Language)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the URL property
* @return String with the URL
*/
    public String getURL()
    {
        return getSemanticObject().getProperty(swb_friendlyURL);
    }

/**
* Sets the URL property
* @param value long with the URL
*/
    public void setURL(String value)
    {
        getSemanticObject().setProperty(swb_friendlyURL, value);
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
* Gets the FriendlyURLDescription property
* @return String with the FriendlyURLDescription
*/
    public String getFriendlyURLDescription()
    {
        return getSemanticObject().getProperty(swb_friendlyURLDescription);
    }

/**
* Sets the FriendlyURLDescription property
* @param value long with the FriendlyURLDescription
*/
    public void setFriendlyURLDescription(String value)
    {
        getSemanticObject().setProperty(swb_friendlyURLDescription, value);
    }
}
