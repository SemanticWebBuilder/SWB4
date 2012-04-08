package org.semanticwb.social.base;


public abstract class PostBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable,org.semanticwb.model.Expirable
{
    public static final org.semanticwb.platform.SemanticClass social_SocialNetWork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.owl-ontologies.com/socialNet#SocialNetWork");
    public static final org.semanticwb.platform.SemanticProperty social_hasSocialNetwork_PostInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.owl-ontologies.com/socialNet#hasSocialNetwork_PostInv");
    public static final org.semanticwb.platform.SemanticClass social_Post=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.owl-ontologies.com/socialNet#Post");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.owl-ontologies.com/socialNet#Post");

    public static class ClassMgr
    {
       /**
       * Returns a list of Post for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.Post
       */

        public static java.util.Iterator<org.semanticwb.social.Post> listPosts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Post>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.Post for all models
       * @return Iterator of org.semanticwb.social.Post
       */

        public static java.util.Iterator<org.semanticwb.social.Post> listPosts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Post>(it, true);
        }

        public static org.semanticwb.social.Post createPost(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.Post.ClassMgr.createPost(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.Post
       * @param id Identifier for org.semanticwb.social.Post
       * @param model Model of the org.semanticwb.social.Post
       * @return A org.semanticwb.social.Post
       */
        public static org.semanticwb.social.Post getPost(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Post)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.Post
       * @param id Identifier for org.semanticwb.social.Post
       * @param model Model of the org.semanticwb.social.Post
       * @return A org.semanticwb.social.Post
       */
        public static org.semanticwb.social.Post createPost(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Post)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.Post
       * @param id Identifier for org.semanticwb.social.Post
       * @param model Model of the org.semanticwb.social.Post
       */
        public static void removePost(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.Post
       * @param id Identifier for org.semanticwb.social.Post
       * @param model Model of the org.semanticwb.social.Post
       * @return true if the org.semanticwb.social.Post exists, false otherwise
       */

        public static boolean hasPost(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPost(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.Post with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.Post
       * @return Iterator with all the org.semanticwb.social.Post
       */

        public static java.util.Iterator<org.semanticwb.social.Post> listPostByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Post> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Post with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.Post
       */

        public static java.util.Iterator<org.semanticwb.social.Post> listPostByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Post> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Post with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.Post
       * @return Iterator with all the org.semanticwb.social.Post
       */

        public static java.util.Iterator<org.semanticwb.social.Post> listPostByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Post> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Post with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.Post
       */

        public static java.util.Iterator<org.semanticwb.social.Post> listPostByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Post> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Post with a determined SocialNetwork_PostInv
       * @param value SocialNetwork_PostInv of the type org.semanticwb.social.SocialNetWork
       * @param model Model of the org.semanticwb.social.Post
       * @return Iterator with all the org.semanticwb.social.Post
       */

        public static java.util.Iterator<org.semanticwb.social.Post> listPostBySocialNetwork_PostInv(org.semanticwb.social.SocialNetWork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Post> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetwork_PostInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Post with a determined SocialNetwork_PostInv
       * @param value SocialNetwork_PostInv of the type org.semanticwb.social.SocialNetWork
       * @return Iterator with all the org.semanticwb.social.Post
       */

        public static java.util.Iterator<org.semanticwb.social.Post> listPostBySocialNetwork_PostInv(org.semanticwb.social.SocialNetWork value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Post> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetwork_PostInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a PostBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Post
   */
    public PostBase(org.semanticwb.platform.SemanticObject base)
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
   * Gets all the org.semanticwb.social.SocialNetWork
   * @return A GenericIterator with all the org.semanticwb.social.SocialNetWork
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetWork> listSocialNetwork_PostInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetWork>(getSemanticObject().listObjectProperties(social_hasSocialNetwork_PostInv));
    }

   /**
   * Gets true if has a SocialNetwork_PostInv
   * @param value org.semanticwb.social.SocialNetWork to verify
   * @return true if the org.semanticwb.social.SocialNetWork exists, false otherwise
   */
    public boolean hasSocialNetwork_PostInv(org.semanticwb.social.SocialNetWork value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasSocialNetwork_PostInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the SocialNetwork_PostInv
   * @return a org.semanticwb.social.SocialNetWork
   */
    public org.semanticwb.social.SocialNetWork getSocialNetwork_PostInv()
    {
         org.semanticwb.social.SocialNetWork ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasSocialNetwork_PostInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialNetWork)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Expiration property
* @return java.util.Date with the Expiration
*/
    public java.util.Date getExpiration()
    {
        return getSemanticObject().getDateProperty(swb_expiration);
    }

/**
* Sets the Expiration property
* @param value long with the Expiration
*/
    public void setExpiration(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_expiration, value);
    }
}
