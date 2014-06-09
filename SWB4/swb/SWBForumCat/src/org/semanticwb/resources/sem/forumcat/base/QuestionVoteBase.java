package org.semanticwb.resources.sem.forumcat.base;


public abstract class QuestionVoteBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty forumCat_commentVote=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#commentVote");
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso.
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty forumCat_userVote=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#userVote");
    public static final org.semanticwb.platform.SemanticProperty forumCat_likeVote=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#likeVote");
    public static final org.semanticwb.platform.SemanticClass forumCat_Question=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#Question");
    public static final org.semanticwb.platform.SemanticProperty forumCat_questionVote=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#questionVote");
    public static final org.semanticwb.platform.SemanticClass forumCat_QuestionVote=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#QuestionVote");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#QuestionVote");

    public static class ClassMgr
    {
       /**
       * Returns a list of QuestionVote for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.resources.sem.forumcat.QuestionVote
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.QuestionVote> listQuestionVotes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.QuestionVote>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.resources.sem.forumcat.QuestionVote for all models
       * @return Iterator of org.semanticwb.resources.sem.forumcat.QuestionVote
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.QuestionVote> listQuestionVotes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.QuestionVote>(it, true);
        }

        public static org.semanticwb.resources.sem.forumcat.QuestionVote createQuestionVote(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.resources.sem.forumcat.QuestionVote.ClassMgr.createQuestionVote(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.resources.sem.forumcat.QuestionVote
       * @param id Identifier for org.semanticwb.resources.sem.forumcat.QuestionVote
       * @param model Model of the org.semanticwb.resources.sem.forumcat.QuestionVote
       * @return A org.semanticwb.resources.sem.forumcat.QuestionVote
       */
        public static org.semanticwb.resources.sem.forumcat.QuestionVote getQuestionVote(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.resources.sem.forumcat.QuestionVote)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.resources.sem.forumcat.QuestionVote
       * @param id Identifier for org.semanticwb.resources.sem.forumcat.QuestionVote
       * @param model Model of the org.semanticwb.resources.sem.forumcat.QuestionVote
       * @return A org.semanticwb.resources.sem.forumcat.QuestionVote
       */
        public static org.semanticwb.resources.sem.forumcat.QuestionVote createQuestionVote(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.resources.sem.forumcat.QuestionVote)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.resources.sem.forumcat.QuestionVote
       * @param id Identifier for org.semanticwb.resources.sem.forumcat.QuestionVote
       * @param model Model of the org.semanticwb.resources.sem.forumcat.QuestionVote
       */
        public static void removeQuestionVote(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.resources.sem.forumcat.QuestionVote
       * @param id Identifier for org.semanticwb.resources.sem.forumcat.QuestionVote
       * @param model Model of the org.semanticwb.resources.sem.forumcat.QuestionVote
       * @return true if the org.semanticwb.resources.sem.forumcat.QuestionVote exists, false otherwise
       */

        public static boolean hasQuestionVote(String id, org.semanticwb.model.SWBModel model)
        {
            return (getQuestionVote(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.QuestionVote with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.resources.sem.forumcat.QuestionVote
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.QuestionVote
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.QuestionVote> listQuestionVoteByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.QuestionVote> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.QuestionVote with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.QuestionVote
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.QuestionVote> listQuestionVoteByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.QuestionVote> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.QuestionVote with a determined UserVote
       * @param value UserVote of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.resources.sem.forumcat.QuestionVote
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.QuestionVote
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.QuestionVote> listQuestionVoteByUserVote(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.QuestionVote> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(forumCat_userVote, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.QuestionVote with a determined UserVote
       * @param value UserVote of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.QuestionVote
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.QuestionVote> listQuestionVoteByUserVote(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.QuestionVote> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(forumCat_userVote,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.QuestionVote with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.resources.sem.forumcat.QuestionVote
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.QuestionVote
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.QuestionVote> listQuestionVoteByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.QuestionVote> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.QuestionVote with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.QuestionVote
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.QuestionVote> listQuestionVoteByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.QuestionVote> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.QuestionVote with a determined QuestionVote
       * @param value QuestionVote of the type org.semanticwb.resources.sem.forumcat.Question
       * @param model Model of the org.semanticwb.resources.sem.forumcat.QuestionVote
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.QuestionVote
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.QuestionVote> listQuestionVoteByQuestionVote(org.semanticwb.resources.sem.forumcat.Question value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.QuestionVote> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(forumCat_questionVote, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.QuestionVote with a determined QuestionVote
       * @param value QuestionVote of the type org.semanticwb.resources.sem.forumcat.Question
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.QuestionVote
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.QuestionVote> listQuestionVoteByQuestionVote(org.semanticwb.resources.sem.forumcat.Question value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.QuestionVote> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(forumCat_questionVote,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static QuestionVoteBase.ClassMgr getQuestionVoteClassMgr()
    {
        return new QuestionVoteBase.ClassMgr();
    }

   /**
   * Constructs a QuestionVoteBase with a SemanticObject
   * @param base The SemanticObject with the properties for the QuestionVote
   */
    public QuestionVoteBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the CommentVote property
* @return String with the CommentVote
*/
    public String getCommentVote()
    {
        return getSemanticObject().getProperty(forumCat_commentVote);
    }

/**
* Sets the CommentVote property
* @param value long with the CommentVote
*/
    public void setCommentVote(String value)
    {
        getSemanticObject().setProperty(forumCat_commentVote, value);
    }
   /**
   * Sets the value for the property UserVote
   * @param value UserVote to set
   */

    public void setUserVote(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(forumCat_userVote, value.getSemanticObject());
        }else
        {
            removeUserVote();
        }
    }
   /**
   * Remove the value for UserVote property
   */

    public void removeUserVote()
    {
        getSemanticObject().removeProperty(forumCat_userVote);
    }

   /**
   * Gets the UserVote
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getUserVote()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(forumCat_userVote);
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
* Gets the LikeVote property
* @return boolean with the LikeVote
*/
    public boolean isLikeVote()
    {
        return getSemanticObject().getBooleanProperty(forumCat_likeVote);
    }

/**
* Sets the LikeVote property
* @param value long with the LikeVote
*/
    public void setLikeVote(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_likeVote, value);
    }
   /**
   * Sets the value for the property QuestionVote
   * @param value QuestionVote to set
   */

    public void setQuestionVote(org.semanticwb.resources.sem.forumcat.Question value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(forumCat_questionVote, value.getSemanticObject());
        }else
        {
            removeQuestionVote();
        }
    }
   /**
   * Remove the value for QuestionVote property
   */

    public void removeQuestionVote()
    {
        getSemanticObject().removeProperty(forumCat_questionVote);
    }

   /**
   * Gets the QuestionVote
   * @return a org.semanticwb.resources.sem.forumcat.Question
   */
    public org.semanticwb.resources.sem.forumcat.Question getQuestionVote()
    {
         org.semanticwb.resources.sem.forumcat.Question ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(forumCat_questionVote);
         if(obj!=null)
         {
             ret=(org.semanticwb.resources.sem.forumcat.Question)obj.createGenericInstance();
         }
         return ret;
    }
}
