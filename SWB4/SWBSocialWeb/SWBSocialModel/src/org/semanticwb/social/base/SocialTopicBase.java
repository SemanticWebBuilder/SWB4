package org.semanticwb.social.base;


   /**
   * Catalogo de temas de un modelo (Marca) 
   */
public abstract class SocialTopicBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.social.Childrenable
{
   /**
   * Catalogo de temas de un modelo (Marca)
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialTopic=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialTopic");
    public static final org.semanticwb.platform.SemanticProperty social_hasChildSocialTopicInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasChildSocialTopicInv");
    public static final org.semanticwb.platform.SemanticClass social_PublishFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PublishFlow");
    public static final org.semanticwb.platform.SemanticProperty social_topicPublishFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#topicPublishFlow");
    public static final org.semanticwb.platform.SemanticProperty social_parentSocialTopic=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#parentSocialTopic");
   /**
   * Clase a Cambiar despues por "Relacional", esta y todas sus hijas. Clase que comprende todos los tipos de Post que pueden ir siendo creados en la herramienta..
   */
    public static final org.semanticwb.platform.SemanticClass social_Post=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Post");
    public static final org.semanticwb.platform.SemanticProperty social_hasPostInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasPostInv");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialTopic");

    public static class ClassMgr
    {
       /**
       * Returns a list of SocialTopic for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.SocialTopic
       */

        public static java.util.Iterator<org.semanticwb.social.SocialTopic> listSocialTopics(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialTopic>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.SocialTopic for all models
       * @return Iterator of org.semanticwb.social.SocialTopic
       */

        public static java.util.Iterator<org.semanticwb.social.SocialTopic> listSocialTopics()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialTopic>(it, true);
        }

        public static org.semanticwb.social.SocialTopic createSocialTopic(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.SocialTopic.ClassMgr.createSocialTopic(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.SocialTopic
       * @param id Identifier for org.semanticwb.social.SocialTopic
       * @param model Model of the org.semanticwb.social.SocialTopic
       * @return A org.semanticwb.social.SocialTopic
       */
        public static org.semanticwb.social.SocialTopic getSocialTopic(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialTopic)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.SocialTopic
       * @param id Identifier for org.semanticwb.social.SocialTopic
       * @param model Model of the org.semanticwb.social.SocialTopic
       * @return A org.semanticwb.social.SocialTopic
       */
        public static org.semanticwb.social.SocialTopic createSocialTopic(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialTopic)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.SocialTopic
       * @param id Identifier for org.semanticwb.social.SocialTopic
       * @param model Model of the org.semanticwb.social.SocialTopic
       */
        public static void removeSocialTopic(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.SocialTopic
       * @param id Identifier for org.semanticwb.social.SocialTopic
       * @param model Model of the org.semanticwb.social.SocialTopic
       * @return true if the org.semanticwb.social.SocialTopic exists, false otherwise
       */

        public static boolean hasSocialTopic(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSocialTopic(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.SocialTopic with a determined ChildSocialTopicInv
       * @param value ChildSocialTopicInv of the type org.semanticwb.social.SocialTopic
       * @param model Model of the org.semanticwb.social.SocialTopic
       * @return Iterator with all the org.semanticwb.social.SocialTopic
       */

        public static java.util.Iterator<org.semanticwb.social.SocialTopic> listSocialTopicByChildSocialTopicInv(org.semanticwb.social.SocialTopic value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialTopic> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasChildSocialTopicInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialTopic with a determined ChildSocialTopicInv
       * @param value ChildSocialTopicInv of the type org.semanticwb.social.SocialTopic
       * @return Iterator with all the org.semanticwb.social.SocialTopic
       */

        public static java.util.Iterator<org.semanticwb.social.SocialTopic> listSocialTopicByChildSocialTopicInv(org.semanticwb.social.SocialTopic value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialTopic> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasChildSocialTopicInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialTopic with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SocialTopic
       * @return Iterator with all the org.semanticwb.social.SocialTopic
       */

        public static java.util.Iterator<org.semanticwb.social.SocialTopic> listSocialTopicByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialTopic> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialTopic with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SocialTopic
       */

        public static java.util.Iterator<org.semanticwb.social.SocialTopic> listSocialTopicByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialTopic> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialTopic with a determined PublishFlow
       * @param value PublishFlow of the type org.semanticwb.social.PublishFlow
       * @param model Model of the org.semanticwb.social.SocialTopic
       * @return Iterator with all the org.semanticwb.social.SocialTopic
       */

        public static java.util.Iterator<org.semanticwb.social.SocialTopic> listSocialTopicByPublishFlow(org.semanticwb.social.PublishFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialTopic> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_topicPublishFlow, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialTopic with a determined PublishFlow
       * @param value PublishFlow of the type org.semanticwb.social.PublishFlow
       * @return Iterator with all the org.semanticwb.social.SocialTopic
       */

        public static java.util.Iterator<org.semanticwb.social.SocialTopic> listSocialTopicByPublishFlow(org.semanticwb.social.PublishFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialTopic> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_topicPublishFlow,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialTopic with a determined ParentSocialTopic
       * @param value ParentSocialTopic of the type org.semanticwb.social.SocialTopic
       * @param model Model of the org.semanticwb.social.SocialTopic
       * @return Iterator with all the org.semanticwb.social.SocialTopic
       */

        public static java.util.Iterator<org.semanticwb.social.SocialTopic> listSocialTopicByParentSocialTopic(org.semanticwb.social.SocialTopic value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialTopic> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_parentSocialTopic, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialTopic with a determined ParentSocialTopic
       * @param value ParentSocialTopic of the type org.semanticwb.social.SocialTopic
       * @return Iterator with all the org.semanticwb.social.SocialTopic
       */

        public static java.util.Iterator<org.semanticwb.social.SocialTopic> listSocialTopicByParentSocialTopic(org.semanticwb.social.SocialTopic value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialTopic> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_parentSocialTopic,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialTopic with a determined ParentObj
       * @param value ParentObj of the type org.semanticwb.social.Childrenable
       * @param model Model of the org.semanticwb.social.SocialTopic
       * @return Iterator with all the org.semanticwb.social.SocialTopic
       */

        public static java.util.Iterator<org.semanticwb.social.SocialTopic> listSocialTopicByParentObj(org.semanticwb.social.Childrenable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialTopic> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_parentObj, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialTopic with a determined ParentObj
       * @param value ParentObj of the type org.semanticwb.social.Childrenable
       * @return Iterator with all the org.semanticwb.social.SocialTopic
       */

        public static java.util.Iterator<org.semanticwb.social.SocialTopic> listSocialTopicByParentObj(org.semanticwb.social.Childrenable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialTopic> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_parentObj,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialTopic with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SocialTopic
       * @return Iterator with all the org.semanticwb.social.SocialTopic
       */

        public static java.util.Iterator<org.semanticwb.social.SocialTopic> listSocialTopicByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialTopic> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialTopic with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SocialTopic
       */

        public static java.util.Iterator<org.semanticwb.social.SocialTopic> listSocialTopicByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialTopic> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialTopic with a determined ChildrenObjInv
       * @param value ChildrenObjInv of the type org.semanticwb.social.Childrenable
       * @param model Model of the org.semanticwb.social.SocialTopic
       * @return Iterator with all the org.semanticwb.social.SocialTopic
       */

        public static java.util.Iterator<org.semanticwb.social.SocialTopic> listSocialTopicByChildrenObjInv(org.semanticwb.social.Childrenable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialTopic> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasChildrenObjInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialTopic with a determined ChildrenObjInv
       * @param value ChildrenObjInv of the type org.semanticwb.social.Childrenable
       * @return Iterator with all the org.semanticwb.social.SocialTopic
       */

        public static java.util.Iterator<org.semanticwb.social.SocialTopic> listSocialTopicByChildrenObjInv(org.semanticwb.social.Childrenable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialTopic> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasChildrenObjInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialTopic with a determined Post
       * @param value Post of the type org.semanticwb.social.Post
       * @param model Model of the org.semanticwb.social.SocialTopic
       * @return Iterator with all the org.semanticwb.social.SocialTopic
       */

        public static java.util.Iterator<org.semanticwb.social.SocialTopic> listSocialTopicByPost(org.semanticwb.social.Post value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialTopic> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialTopic with a determined Post
       * @param value Post of the type org.semanticwb.social.Post
       * @return Iterator with all the org.semanticwb.social.SocialTopic
       */

        public static java.util.Iterator<org.semanticwb.social.SocialTopic> listSocialTopicByPost(org.semanticwb.social.Post value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialTopic> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SocialTopicBase.ClassMgr getSocialTopicClassMgr()
    {
        return new SocialTopicBase.ClassMgr();
    }

   /**
   * Constructs a SocialTopicBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SocialTopic
   */
    public SocialTopicBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.social.SocialTopic
   * @return A GenericIterator with all the org.semanticwb.social.SocialTopic
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialTopic> listChildSocialTopicInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialTopic>(getSemanticObject().listObjectProperties(social_hasChildSocialTopicInv));
    }

   /**
   * Gets true if has a ChildSocialTopicInv
   * @param value org.semanticwb.social.SocialTopic to verify
   * @return true if the org.semanticwb.social.SocialTopic exists, false otherwise
   */
    public boolean hasChildSocialTopicInv(org.semanticwb.social.SocialTopic value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasChildSocialTopicInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the ChildSocialTopicInv
   * @return a org.semanticwb.social.SocialTopic
   */
    public org.semanticwb.social.SocialTopic getChildSocialTopicInv()
    {
         org.semanticwb.social.SocialTopic ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasChildSocialTopicInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialTopic)obj.createGenericInstance();
         }
         return ret;
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
   * Sets the value for the property PublishFlow
   * @param value PublishFlow to set
   */

    public void setPublishFlow(org.semanticwb.social.PublishFlow value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_topicPublishFlow, value.getSemanticObject());
        }else
        {
            removePublishFlow();
        }
    }
   /**
   * Remove the value for PublishFlow property
   */

    public void removePublishFlow()
    {
        getSemanticObject().removeProperty(social_topicPublishFlow);
    }

   /**
   * Gets the PublishFlow
   * @return a org.semanticwb.social.PublishFlow
   */
    public org.semanticwb.social.PublishFlow getPublishFlow()
    {
         org.semanticwb.social.PublishFlow ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_topicPublishFlow);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PublishFlow)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property ParentSocialTopic
   * @param value ParentSocialTopic to set
   */

    public void setParentSocialTopic(org.semanticwb.social.SocialTopic value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_parentSocialTopic, value.getSemanticObject());
        }else
        {
            removeParentSocialTopic();
        }
    }
   /**
   * Remove the value for ParentSocialTopic property
   */

    public void removeParentSocialTopic()
    {
        getSemanticObject().removeProperty(social_parentSocialTopic);
    }

   /**
   * Gets the ParentSocialTopic
   * @return a org.semanticwb.social.SocialTopic
   */
    public org.semanticwb.social.SocialTopic getParentSocialTopic()
    {
         org.semanticwb.social.SocialTopic ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_parentSocialTopic);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialTopic)obj.createGenericInstance();
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
   * Sets the value for the property ParentObj
   * @param value ParentObj to set
   */

    public void setParentObj(org.semanticwb.social.Childrenable value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_parentObj, value.getSemanticObject());
        }else
        {
            removeParentObj();
        }
    }
   /**
   * Remove the value for ParentObj property
   */

    public void removeParentObj()
    {
        getSemanticObject().removeProperty(social_parentObj);
    }

   /**
   * Gets the ParentObj
   * @return a org.semanticwb.social.Childrenable
   */
    public org.semanticwb.social.Childrenable getParentObj()
    {
         org.semanticwb.social.Childrenable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_parentObj);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.Childrenable)obj.createGenericInstance();
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
   * Gets all the org.semanticwb.social.Childrenable
   * @return A GenericIterator with all the org.semanticwb.social.Childrenable
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.Childrenable> listChildrenObjInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Childrenable>(getSemanticObject().listObjectProperties(social_hasChildrenObjInv));
    }

   /**
   * Gets true if has a ChildrenObjInv
   * @param value org.semanticwb.social.Childrenable to verify
   * @return true if the org.semanticwb.social.Childrenable exists, false otherwise
   */
    public boolean hasChildrenObjInv(org.semanticwb.social.Childrenable value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasChildrenObjInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the ChildrenObjInv
   * @return a org.semanticwb.social.Childrenable
   */
    public org.semanticwb.social.Childrenable getChildrenObjInv()
    {
         org.semanticwb.social.Childrenable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasChildrenObjInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.Childrenable)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.social.Post
   * @return A GenericIterator with all the org.semanticwb.social.Post
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.Post> listPosts()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Post>(getSemanticObject().listObjectProperties(social_hasPostInv));
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
           ret=getSemanticObject().hasObjectProperty(social_hasPostInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the Post
   * @return a org.semanticwb.social.Post
   */
    public org.semanticwb.social.Post getPost()
    {
         org.semanticwb.social.Post ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasPostInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.Post)obj.createGenericInstance();
         }
         return ret;
    }
}
