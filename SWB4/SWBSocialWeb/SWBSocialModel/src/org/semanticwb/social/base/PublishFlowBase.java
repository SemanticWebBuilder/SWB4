package org.semanticwb.social.base;


public abstract class PublishFlowBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
   /**
   * Catalogo de temas de un modelo (Marca)
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialTopic=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialTopic");
   /**
   * Inversa con la que obtenemos los temas a los que esta asociados un determinado flujo
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasSocialTopicInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasSocialTopicInv");
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso.
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
   /**
   * Un flujo de publicación puede estar asignado a mas de un usuario. Esto es para que los usuarios sigan uno o mas flujos.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasUser=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasUser");
    public static final org.semanticwb.platform.SemanticClass social_PublishFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PublishFlow");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PublishFlow");

    public static class ClassMgr
    {
       /**
       * Returns a list of PublishFlow for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.PublishFlow
       */

        public static java.util.Iterator<org.semanticwb.social.PublishFlow> listPublishFlows(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PublishFlow>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.PublishFlow for all models
       * @return Iterator of org.semanticwb.social.PublishFlow
       */

        public static java.util.Iterator<org.semanticwb.social.PublishFlow> listPublishFlows()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PublishFlow>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.PublishFlow
       * @param id Identifier for org.semanticwb.social.PublishFlow
       * @param model Model of the org.semanticwb.social.PublishFlow
       * @return A org.semanticwb.social.PublishFlow
       */
        public static org.semanticwb.social.PublishFlow getPublishFlow(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PublishFlow)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.PublishFlow
       * @param id Identifier for org.semanticwb.social.PublishFlow
       * @param model Model of the org.semanticwb.social.PublishFlow
       * @return A org.semanticwb.social.PublishFlow
       */
        public static org.semanticwb.social.PublishFlow createPublishFlow(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PublishFlow)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.PublishFlow
       * @param id Identifier for org.semanticwb.social.PublishFlow
       * @param model Model of the org.semanticwb.social.PublishFlow
       */
        public static void removePublishFlow(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.PublishFlow
       * @param id Identifier for org.semanticwb.social.PublishFlow
       * @param model Model of the org.semanticwb.social.PublishFlow
       * @return true if the org.semanticwb.social.PublishFlow exists, false otherwise
       */

        public static boolean hasPublishFlow(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPublishFlow(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.PublishFlow with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PublishFlow
       * @return Iterator with all the org.semanticwb.social.PublishFlow
       */

        public static java.util.Iterator<org.semanticwb.social.PublishFlow> listPublishFlowByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PublishFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PublishFlow with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PublishFlow
       */

        public static java.util.Iterator<org.semanticwb.social.PublishFlow> listPublishFlowByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PublishFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PublishFlow with a determined SocialTopic
       * @param value SocialTopic of the type org.semanticwb.social.SocialTopic
       * @param model Model of the org.semanticwb.social.PublishFlow
       * @return Iterator with all the org.semanticwb.social.PublishFlow
       */

        public static java.util.Iterator<org.semanticwb.social.PublishFlow> listPublishFlowBySocialTopic(org.semanticwb.social.SocialTopic value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PublishFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialTopicInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PublishFlow with a determined SocialTopic
       * @param value SocialTopic of the type org.semanticwb.social.SocialTopic
       * @return Iterator with all the org.semanticwb.social.PublishFlow
       */

        public static java.util.Iterator<org.semanticwb.social.PublishFlow> listPublishFlowBySocialTopic(org.semanticwb.social.SocialTopic value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PublishFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialTopicInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PublishFlow with a determined User
       * @param value User of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PublishFlow
       * @return Iterator with all the org.semanticwb.social.PublishFlow
       */

        public static java.util.Iterator<org.semanticwb.social.PublishFlow> listPublishFlowByUser(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PublishFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasUser, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PublishFlow with a determined User
       * @param value User of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PublishFlow
       */

        public static java.util.Iterator<org.semanticwb.social.PublishFlow> listPublishFlowByUser(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PublishFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasUser,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PublishFlow with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PublishFlow
       * @return Iterator with all the org.semanticwb.social.PublishFlow
       */

        public static java.util.Iterator<org.semanticwb.social.PublishFlow> listPublishFlowByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PublishFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PublishFlow with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PublishFlow
       */

        public static java.util.Iterator<org.semanticwb.social.PublishFlow> listPublishFlowByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PublishFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static PublishFlowBase.ClassMgr getPublishFlowClassMgr()
    {
        return new PublishFlowBase.ClassMgr();
    }

   /**
   * Constructs a PublishFlowBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PublishFlow
   */
    public PublishFlowBase(org.semanticwb.platform.SemanticObject base)
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
   * Gets all the org.semanticwb.social.SocialTopic
   * @return A GenericIterator with all the org.semanticwb.social.SocialTopic
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialTopic> listSocialTopics()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialTopic>(getSemanticObject().listObjectProperties(social_hasSocialTopicInv));
    }

   /**
   * Gets true if has a SocialTopic
   * @param value org.semanticwb.social.SocialTopic to verify
   * @return true if the org.semanticwb.social.SocialTopic exists, false otherwise
   */
    public boolean hasSocialTopic(org.semanticwb.social.SocialTopic value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasSocialTopicInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the SocialTopic
   * @return a org.semanticwb.social.SocialTopic
   */
    public org.semanticwb.social.SocialTopic getSocialTopic()
    {
         org.semanticwb.social.SocialTopic ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasSocialTopicInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialTopic)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.model.User
   * @return A GenericIterator with all the org.semanticwb.model.User
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.User> listUsers()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.User>(getSemanticObject().listObjectProperties(social_hasUser));
    }

   /**
   * Gets true if has a User
   * @param value org.semanticwb.model.User to verify
   * @return true if the org.semanticwb.model.User exists, false otherwise
   */
    public boolean hasUser(org.semanticwb.model.User value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasUser,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a User
   * @param value org.semanticwb.model.User to add
   */

    public void addUser(org.semanticwb.model.User value)
    {
        getSemanticObject().addObjectProperty(social_hasUser, value.getSemanticObject());
    }
   /**
   * Removes all the User
   */

    public void removeAllUser()
    {
        getSemanticObject().removeProperty(social_hasUser);
    }
   /**
   * Removes a User
   * @param value org.semanticwb.model.User to remove
   */

    public void removeUser(org.semanticwb.model.User value)
    {
        getSemanticObject().removeObjectProperty(social_hasUser,value.getSemanticObject());
    }

   /**
   * Gets the User
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getUser()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasUser);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
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
   * Gets the SocialSite
   * @return a instance of org.semanticwb.social.SocialSite
   */
    public org.semanticwb.social.SocialSite getSocialSite()
    {
        return (org.semanticwb.social.SocialSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
