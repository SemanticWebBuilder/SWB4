package org.semanticwb.resources.sem.forumcat.base;


public abstract class AnswerVoteBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
{
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso.
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty forumCat_ansUserVote=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#ansUserVote");
    public static final org.semanticwb.platform.SemanticProperty forumCat_likeAnswer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#likeAnswer");
    public static final org.semanticwb.platform.SemanticProperty forumCat_ansCommentVote=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#ansCommentVote");
    public static final org.semanticwb.platform.SemanticProperty forumCat_irrelevantVote=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#irrelevantVote");
    public static final org.semanticwb.platform.SemanticClass forumCat_Answer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#Answer");
    public static final org.semanticwb.platform.SemanticProperty forumCat_answerVote=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#answerVote");
    public static final org.semanticwb.platform.SemanticClass forumCat_AnswerVote=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#AnswerVote");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#AnswerVote");

    public static class ClassMgr
    {
       /**
       * Returns a list of AnswerVote for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.resources.sem.forumcat.AnswerVote
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.AnswerVote> listAnswerVotes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.AnswerVote>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.resources.sem.forumcat.AnswerVote for all models
       * @return Iterator of org.semanticwb.resources.sem.forumcat.AnswerVote
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.AnswerVote> listAnswerVotes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.AnswerVote>(it, true);
        }

        public static org.semanticwb.resources.sem.forumcat.AnswerVote createAnswerVote(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.resources.sem.forumcat.AnswerVote.ClassMgr.createAnswerVote(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.resources.sem.forumcat.AnswerVote
       * @param id Identifier for org.semanticwb.resources.sem.forumcat.AnswerVote
       * @param model Model of the org.semanticwb.resources.sem.forumcat.AnswerVote
       * @return A org.semanticwb.resources.sem.forumcat.AnswerVote
       */
        public static org.semanticwb.resources.sem.forumcat.AnswerVote getAnswerVote(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.resources.sem.forumcat.AnswerVote)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.resources.sem.forumcat.AnswerVote
       * @param id Identifier for org.semanticwb.resources.sem.forumcat.AnswerVote
       * @param model Model of the org.semanticwb.resources.sem.forumcat.AnswerVote
       * @return A org.semanticwb.resources.sem.forumcat.AnswerVote
       */
        public static org.semanticwb.resources.sem.forumcat.AnswerVote createAnswerVote(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.resources.sem.forumcat.AnswerVote)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.resources.sem.forumcat.AnswerVote
       * @param id Identifier for org.semanticwb.resources.sem.forumcat.AnswerVote
       * @param model Model of the org.semanticwb.resources.sem.forumcat.AnswerVote
       */
        public static void removeAnswerVote(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.resources.sem.forumcat.AnswerVote
       * @param id Identifier for org.semanticwb.resources.sem.forumcat.AnswerVote
       * @param model Model of the org.semanticwb.resources.sem.forumcat.AnswerVote
       * @return true if the org.semanticwb.resources.sem.forumcat.AnswerVote exists, false otherwise
       */

        public static boolean hasAnswerVote(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAnswerVote(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.AnswerVote with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.resources.sem.forumcat.AnswerVote
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.AnswerVote
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.AnswerVote> listAnswerVoteByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.AnswerVote> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.AnswerVote with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.AnswerVote
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.AnswerVote> listAnswerVoteByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.AnswerVote> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.AnswerVote with a determined AnsUserVote
       * @param value AnsUserVote of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.resources.sem.forumcat.AnswerVote
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.AnswerVote
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.AnswerVote> listAnswerVoteByAnsUserVote(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.AnswerVote> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(forumCat_ansUserVote, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.AnswerVote with a determined AnsUserVote
       * @param value AnsUserVote of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.AnswerVote
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.AnswerVote> listAnswerVoteByAnsUserVote(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.AnswerVote> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(forumCat_ansUserVote,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.AnswerVote with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.resources.sem.forumcat.AnswerVote
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.AnswerVote
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.AnswerVote> listAnswerVoteByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.AnswerVote> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.AnswerVote with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.AnswerVote
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.AnswerVote> listAnswerVoteByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.AnswerVote> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.AnswerVote with a determined AnswerVote
       * @param value AnswerVote of the type org.semanticwb.resources.sem.forumcat.Answer
       * @param model Model of the org.semanticwb.resources.sem.forumcat.AnswerVote
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.AnswerVote
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.AnswerVote> listAnswerVoteByAnswerVote(org.semanticwb.resources.sem.forumcat.Answer value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.AnswerVote> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(forumCat_answerVote, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.AnswerVote with a determined AnswerVote
       * @param value AnswerVote of the type org.semanticwb.resources.sem.forumcat.Answer
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.AnswerVote
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.AnswerVote> listAnswerVoteByAnswerVote(org.semanticwb.resources.sem.forumcat.Answer value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.AnswerVote> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(forumCat_answerVote,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static AnswerVoteBase.ClassMgr getAnswerVoteClassMgr()
    {
        return new AnswerVoteBase.ClassMgr();
    }

   /**
   * Constructs a AnswerVoteBase with a SemanticObject
   * @param base The SemanticObject with the properties for the AnswerVote
   */
    public AnswerVoteBase(org.semanticwb.platform.SemanticObject base)
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
   * Sets the value for the property AnsUserVote
   * @param value AnsUserVote to set
   */

    public void setAnsUserVote(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(forumCat_ansUserVote, value.getSemanticObject());
        }else
        {
            removeAnsUserVote();
        }
    }
   /**
   * Remove the value for AnsUserVote property
   */

    public void removeAnsUserVote()
    {
        getSemanticObject().removeProperty(forumCat_ansUserVote);
    }

   /**
   * Gets the AnsUserVote
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getAnsUserVote()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(forumCat_ansUserVote);
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
* Gets the LikeAnswer property
* @return boolean with the LikeAnswer
*/
    public boolean isLikeAnswer()
    {
        return getSemanticObject().getBooleanProperty(forumCat_likeAnswer);
    }

/**
* Sets the LikeAnswer property
* @param value long with the LikeAnswer
*/
    public void setLikeAnswer(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_likeAnswer, value);
    }

/**
* Gets the AnsCommentVote property
* @return String with the AnsCommentVote
*/
    public String getAnsCommentVote()
    {
        return getSemanticObject().getProperty(forumCat_ansCommentVote);
    }

/**
* Sets the AnsCommentVote property
* @param value long with the AnsCommentVote
*/
    public void setAnsCommentVote(String value)
    {
        getSemanticObject().setProperty(forumCat_ansCommentVote, value);
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
* Gets the IrrelevantVote property
* @return boolean with the IrrelevantVote
*/
    public boolean isIrrelevantVote()
    {
        return getSemanticObject().getBooleanProperty(forumCat_irrelevantVote);
    }

/**
* Sets the IrrelevantVote property
* @param value long with the IrrelevantVote
*/
    public void setIrrelevantVote(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_irrelevantVote, value);
    }
   /**
   * Sets the value for the property AnswerVote
   * @param value AnswerVote to set
   */

    public void setAnswerVote(org.semanticwb.resources.sem.forumcat.Answer value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(forumCat_answerVote, value.getSemanticObject());
        }else
        {
            removeAnswerVote();
        }
    }
   /**
   * Remove the value for AnswerVote property
   */

    public void removeAnswerVote()
    {
        getSemanticObject().removeProperty(forumCat_answerVote);
    }

   /**
   * Gets the AnswerVote
   * @return a org.semanticwb.resources.sem.forumcat.Answer
   */
    public org.semanticwb.resources.sem.forumcat.Answer getAnswerVote()
    {
         org.semanticwb.resources.sem.forumcat.Answer ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(forumCat_answerVote);
         if(obj!=null)
         {
             ret=(org.semanticwb.resources.sem.forumcat.Answer)obj.createGenericInstance();
         }
         return ret;
    }
}
