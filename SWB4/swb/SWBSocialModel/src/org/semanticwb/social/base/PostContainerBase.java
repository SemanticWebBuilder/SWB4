package org.semanticwb.social.base;


public abstract class PostContainerBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty social_year=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.owl-ontologies.com/socialNet#year");
    public static final org.semanticwb.platform.SemanticProperty social_month=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.owl-ontologies.com/socialNet#month");
    public static final org.semanticwb.platform.SemanticClass social_Post=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.owl-ontologies.com/socialNet#Post");
    public static final org.semanticwb.platform.SemanticProperty social_hasPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.owl-ontologies.com/socialNet#hasPost");
    public static final org.semanticwb.platform.SemanticClass social_PostContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.owl-ontologies.com/socialNet#PostContainer");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.owl-ontologies.com/socialNet#PostContainer");

    public static class ClassMgr
    {
       /**
       * Returns a list of PostContainer for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.PostContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostContainer> listPostContainers(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostContainer>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.PostContainer for all models
       * @return Iterator of org.semanticwb.social.PostContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostContainer> listPostContainers()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostContainer>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.PostContainer
       * @param id Identifier for org.semanticwb.social.PostContainer
       * @param model Model of the org.semanticwb.social.PostContainer
       * @return A org.semanticwb.social.PostContainer
       */
        public static org.semanticwb.social.PostContainer getPostContainer(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostContainer)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.PostContainer
       * @param id Identifier for org.semanticwb.social.PostContainer
       * @param model Model of the org.semanticwb.social.PostContainer
       * @return A org.semanticwb.social.PostContainer
       */
        public static org.semanticwb.social.PostContainer createPostContainer(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostContainer)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.PostContainer
       * @param id Identifier for org.semanticwb.social.PostContainer
       * @param model Model of the org.semanticwb.social.PostContainer
       */
        public static void removePostContainer(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.PostContainer
       * @param id Identifier for org.semanticwb.social.PostContainer
       * @param model Model of the org.semanticwb.social.PostContainer
       * @return true if the org.semanticwb.social.PostContainer exists, false otherwise
       */

        public static boolean hasPostContainer(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPostContainer(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.PostContainer with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PostContainer
       * @return Iterator with all the org.semanticwb.social.PostContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostContainer> listPostContainerByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostContainer with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PostContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostContainer> listPostContainerByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostContainer with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PostContainer
       * @return Iterator with all the org.semanticwb.social.PostContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostContainer> listPostContainerByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostContainer with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PostContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostContainer> listPostContainerByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostContainer with a determined Post
       * @param value Post of the type org.semanticwb.social.Post
       * @param model Model of the org.semanticwb.social.PostContainer
       * @return Iterator with all the org.semanticwb.social.PostContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostContainer> listPostContainerByPost(org.semanticwb.social.Post value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPost, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostContainer with a determined Post
       * @param value Post of the type org.semanticwb.social.Post
       * @return Iterator with all the org.semanticwb.social.PostContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostContainer> listPostContainerByPost(org.semanticwb.social.Post value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPost,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a PostContainerBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PostContainer
   */
    public PostContainerBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the Year property
* @return int with the Year
*/
    public int getYear()
    {
        return getSemanticObject().getIntProperty(social_year);
    }

/**
* Sets the Year property
* @param value long with the Year
*/
    public void setYear(int value)
    {
        getSemanticObject().setIntProperty(social_year, value);
    }

/**
* Gets the Month property
* @return int with the Month
*/
    public int getMonth()
    {
        return getSemanticObject().getIntProperty(social_month);
    }

/**
* Sets the Month property
* @param value long with the Month
*/
    public void setMonth(int value)
    {
        getSemanticObject().setIntProperty(social_month, value);
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
