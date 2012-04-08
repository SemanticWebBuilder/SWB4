package org.semanticwb.social.base;


public abstract class SocialNetWorkBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
   /**
   * Password de Conexión de la Red Social
   */
    public static final org.semanticwb.platform.SemanticProperty social_password=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.owl-ontologies.com/socialNet#password");
   /**
   * Usuario de la Red Social
   */
    public static final org.semanticwb.platform.SemanticProperty social_login=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.owl-ontologies.com/socialNet#login");
    public static final org.semanticwb.platform.SemanticClass social_Post=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.owl-ontologies.com/socialNet#Post");
    public static final org.semanticwb.platform.SemanticProperty social_hasPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.owl-ontologies.com/socialNet#hasPost");
    public static final org.semanticwb.platform.SemanticClass social_SocialNetWork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.owl-ontologies.com/socialNet#SocialNetWork");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.owl-ontologies.com/socialNet#SocialNetWork");

    public static class ClassMgr
    {
       /**
       * Returns a list of SocialNetWork for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.SocialNetWork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetWork> listSocialNetWorks(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetWork>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.SocialNetWork for all models
       * @return Iterator of org.semanticwb.social.SocialNetWork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetWork> listSocialNetWorks()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetWork>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.SocialNetWork
       * @param id Identifier for org.semanticwb.social.SocialNetWork
       * @param model Model of the org.semanticwb.social.SocialNetWork
       * @return A org.semanticwb.social.SocialNetWork
       */
        public static org.semanticwb.social.SocialNetWork getSocialNetWork(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialNetWork)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.SocialNetWork
       * @param id Identifier for org.semanticwb.social.SocialNetWork
       * @param model Model of the org.semanticwb.social.SocialNetWork
       * @return A org.semanticwb.social.SocialNetWork
       */
        public static org.semanticwb.social.SocialNetWork createSocialNetWork(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialNetWork)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.SocialNetWork
       * @param id Identifier for org.semanticwb.social.SocialNetWork
       * @param model Model of the org.semanticwb.social.SocialNetWork
       */
        public static void removeSocialNetWork(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.SocialNetWork
       * @param id Identifier for org.semanticwb.social.SocialNetWork
       * @param model Model of the org.semanticwb.social.SocialNetWork
       * @return true if the org.semanticwb.social.SocialNetWork exists, false otherwise
       */

        public static boolean hasSocialNetWork(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSocialNetWork(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.SocialNetWork with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SocialNetWork
       * @return Iterator with all the org.semanticwb.social.SocialNetWork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetWork> listSocialNetWorkByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetWork> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetWork with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SocialNetWork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetWork> listSocialNetWorkByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetWork> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetWork with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SocialNetWork
       * @return Iterator with all the org.semanticwb.social.SocialNetWork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetWork> listSocialNetWorkByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetWork> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetWork with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SocialNetWork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetWork> listSocialNetWorkByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetWork> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetWork with a determined Post
       * @param value Post of the type org.semanticwb.social.Post
       * @param model Model of the org.semanticwb.social.SocialNetWork
       * @return Iterator with all the org.semanticwb.social.SocialNetWork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetWork> listSocialNetWorkByPost(org.semanticwb.social.Post value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetWork> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPost, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetWork with a determined Post
       * @param value Post of the type org.semanticwb.social.Post
       * @return Iterator with all the org.semanticwb.social.SocialNetWork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetWork> listSocialNetWorkByPost(org.semanticwb.social.Post value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetWork> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPost,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a SocialNetWorkBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SocialNetWork
   */
    public SocialNetWorkBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the Password property
* @return String with the Password
*/
    public String getPassword()
    {
        return getSemanticObject().getProperty(social_password);
    }

/**
* Sets the Password property
* @param value long with the Password
*/
    public void setPassword(String value)
    {
        getSemanticObject().setProperty(social_password, value);
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
* Gets the Login property
* @return String with the Login
*/
    public String getLogin()
    {
        return getSemanticObject().getProperty(social_login);
    }

/**
* Sets the Login property
* @param value long with the Login
*/
    public void setLogin(String value)
    {
        getSemanticObject().setProperty(social_login, value);
    }
   /**
   * Gets all the org.semanticwb.social.Post
   * @return A GenericIterator with all the org.semanticwb.social.Post
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.Post> listPosts()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Post>(getSemanticObject().listObjectProperties(social_hasPost));
    }

   /**
   * Gets true if has a Post
   * @param value org.semanticwb.social.Post to verify
   * @return true if the org.semanticwb.social.Post exists, false otherwise
   */
    public boolean hasPost(org.semanticwb.social.Post value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasPost,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Post
   * @param value org.semanticwb.social.Post to add
   */

    public void addPost(org.semanticwb.social.Post value)
    {
        getSemanticObject().addObjectProperty(social_hasPost, value.getSemanticObject());
    }
   /**
   * Removes all the Post
   */

    public void removeAllPost()
    {
        getSemanticObject().removeProperty(social_hasPost);
    }
   /**
   * Removes a Post
   * @param value org.semanticwb.social.Post to remove
   */

    public void removePost(org.semanticwb.social.Post value)
    {
        getSemanticObject().removeObjectProperty(social_hasPost,value.getSemanticObject());
    }

   /**
   * Gets the Post
   * @return a org.semanticwb.social.Post
   */
    public org.semanticwb.social.Post getPost()
    {
         org.semanticwb.social.Post ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasPost);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.Post)obj.createGenericInstance();
         }
         return ret;
    }
}
