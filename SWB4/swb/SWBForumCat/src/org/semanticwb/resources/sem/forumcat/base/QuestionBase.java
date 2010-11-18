package org.semanticwb.resources.sem.forumcat.base;


public abstract class QuestionBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Searchable,org.semanticwb.model.Tagable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass forumCat_SWBForumCatResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#SWBForumCatResource");
    public static final org.semanticwb.platform.SemanticProperty forumCat_forumResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#forumResource");
    public static final org.semanticwb.platform.SemanticClass forumCat_QuestionSubscription=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#QuestionSubscription");
    public static final org.semanticwb.platform.SemanticProperty forumCat_questionObjInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#questionObjInv");
   /**
   * Una Página Web es el elemento de SemanticWebBuilder a través del cual se estructura la información del portal.
   */
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty forumCat_webpage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#webpage");
    public static final org.semanticwb.platform.SemanticProperty forumCat_queStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#queStatus");
    public static final org.semanticwb.platform.SemanticClass forumCat_Answer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#Answer");
    public static final org.semanticwb.platform.SemanticProperty forumCat_hasAnswerInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#hasAnswerInv");
   /**
   * Indica si la pregunta ha sido cerrada por su creador.
   */
    public static final org.semanticwb.platform.SemanticProperty forumCat_closed=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#closed");
    public static final org.semanticwb.platform.SemanticProperty forumCat_question=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#question");
    public static final org.semanticwb.platform.SemanticProperty forumCat_queIsApropiate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#queIsApropiate");
    public static final org.semanticwb.platform.SemanticProperty forumCat_specifications=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#specifications");
    public static final org.semanticwb.platform.SemanticProperty forumCat_queInappropriate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#queInappropriate");
    public static final org.semanticwb.platform.SemanticClass forumCat_Question=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#Question");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#Question");

    public static class ClassMgr
    {
       /**
       * Returns a list of Question for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.resources.sem.forumcat.Question
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.resources.sem.forumcat.Question for all models
       * @return Iterator of org.semanticwb.resources.sem.forumcat.Question
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question>(it, true);
        }

        public static org.semanticwb.resources.sem.forumcat.Question createQuestion(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.resources.sem.forumcat.Question.ClassMgr.createQuestion(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.resources.sem.forumcat.Question
       * @param id Identifier for org.semanticwb.resources.sem.forumcat.Question
       * @param model Model of the org.semanticwb.resources.sem.forumcat.Question
       * @return A org.semanticwb.resources.sem.forumcat.Question
       */
        public static org.semanticwb.resources.sem.forumcat.Question getQuestion(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.resources.sem.forumcat.Question)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.resources.sem.forumcat.Question
       * @param id Identifier for org.semanticwb.resources.sem.forumcat.Question
       * @param model Model of the org.semanticwb.resources.sem.forumcat.Question
       * @return A org.semanticwb.resources.sem.forumcat.Question
       */
        public static org.semanticwb.resources.sem.forumcat.Question createQuestion(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.resources.sem.forumcat.Question)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.resources.sem.forumcat.Question
       * @param id Identifier for org.semanticwb.resources.sem.forumcat.Question
       * @param model Model of the org.semanticwb.resources.sem.forumcat.Question
       */
        public static void removeQuestion(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.resources.sem.forumcat.Question
       * @param id Identifier for org.semanticwb.resources.sem.forumcat.Question
       * @param model Model of the org.semanticwb.resources.sem.forumcat.Question
       * @return true if the org.semanticwb.resources.sem.forumcat.Question exists, false otherwise
       */

        public static boolean hasQuestion(String id, org.semanticwb.model.SWBModel model)
        {
            return (getQuestion(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.Question with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.resources.sem.forumcat.Question
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.Question
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.Question with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.Question
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.Question with a determined ForumResource
       * @param value ForumResource of the type org.semanticwb.resources.sem.forumcat.SWBForumCatResource
       * @param model Model of the org.semanticwb.resources.sem.forumcat.Question
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.Question
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionByForumResource(org.semanticwb.resources.sem.forumcat.SWBForumCatResource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(forumCat_forumResource, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.Question with a determined ForumResource
       * @param value ForumResource of the type org.semanticwb.resources.sem.forumcat.SWBForumCatResource
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.Question
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionByForumResource(org.semanticwb.resources.sem.forumcat.SWBForumCatResource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(forumCat_forumResource,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.Question with a determined QuestionObjInv
       * @param value QuestionObjInv of the type org.semanticwb.resources.sem.forumcat.QuestionSubscription
       * @param model Model of the org.semanticwb.resources.sem.forumcat.Question
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.Question
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionByQuestionObjInv(org.semanticwb.resources.sem.forumcat.QuestionSubscription value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(forumCat_questionObjInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.Question with a determined QuestionObjInv
       * @param value QuestionObjInv of the type org.semanticwb.resources.sem.forumcat.QuestionSubscription
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.Question
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionByQuestionObjInv(org.semanticwb.resources.sem.forumcat.QuestionSubscription value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(forumCat_questionObjInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.Question with a determined Webpage
       * @param value Webpage of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.resources.sem.forumcat.Question
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.Question
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionByWebpage(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(forumCat_webpage, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.Question with a determined Webpage
       * @param value Webpage of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.Question
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionByWebpage(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(forumCat_webpage,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.Question with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.resources.sem.forumcat.Question
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.Question
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.Question with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.Question
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.Question with a determined AnswerInv
       * @param value AnswerInv of the type org.semanticwb.resources.sem.forumcat.Answer
       * @param model Model of the org.semanticwb.resources.sem.forumcat.Question
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.Question
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionByAnswerInv(org.semanticwb.resources.sem.forumcat.Answer value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(forumCat_hasAnswerInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.Question with a determined AnswerInv
       * @param value AnswerInv of the type org.semanticwb.resources.sem.forumcat.Answer
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.Question
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionByAnswerInv(org.semanticwb.resources.sem.forumcat.Answer value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(forumCat_hasAnswerInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a QuestionBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Question
   */
    public QuestionBase(org.semanticwb.platform.SemanticObject base)
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
   * Sets the value for the property ForumResource
   * @param value ForumResource to set
   */

    public void setForumResource(org.semanticwb.resources.sem.forumcat.SWBForumCatResource value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(forumCat_forumResource, value.getSemanticObject());
        }else
        {
            removeForumResource();
        }
    }
   /**
   * Remove the value for ForumResource property
   */

    public void removeForumResource()
    {
        getSemanticObject().removeProperty(forumCat_forumResource);
    }

   /**
   * Gets the ForumResource
   * @return a org.semanticwb.resources.sem.forumcat.SWBForumCatResource
   */
    public org.semanticwb.resources.sem.forumcat.SWBForumCatResource getForumResource()
    {
         org.semanticwb.resources.sem.forumcat.SWBForumCatResource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(forumCat_forumResource);
         if(obj!=null)
         {
             ret=(org.semanticwb.resources.sem.forumcat.SWBForumCatResource)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property QuestionObjInv
   * @param value QuestionObjInv to set
   */

    public void setQuestionObjInv(org.semanticwb.resources.sem.forumcat.QuestionSubscription value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(forumCat_questionObjInv, value.getSemanticObject());
        }else
        {
            removeQuestionObjInv();
        }
    }
   /**
   * Remove the value for QuestionObjInv property
   */

    public void removeQuestionObjInv()
    {
        getSemanticObject().removeProperty(forumCat_questionObjInv);
    }

   /**
   * Gets the QuestionObjInv
   * @return a org.semanticwb.resources.sem.forumcat.QuestionSubscription
   */
    public org.semanticwb.resources.sem.forumcat.QuestionSubscription getQuestionObjInv()
    {
         org.semanticwb.resources.sem.forumcat.QuestionSubscription ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(forumCat_questionObjInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.resources.sem.forumcat.QuestionSubscription)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Webpage
   * @param value Webpage to set
   */

    public void setWebpage(org.semanticwb.model.WebPage value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(forumCat_webpage, value.getSemanticObject());
        }else
        {
            removeWebpage();
        }
    }
   /**
   * Remove the value for Webpage property
   */

    public void removeWebpage()
    {
        getSemanticObject().removeProperty(forumCat_webpage);
    }

   /**
   * Gets the Webpage
   * @return a org.semanticwb.model.WebPage
   */
    public org.semanticwb.model.WebPage getWebpage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(forumCat_webpage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the QueStatus property
* @return int with the QueStatus
*/
    public int getQueStatus()
    {
        return getSemanticObject().getIntProperty(forumCat_queStatus);
    }

/**
* Sets the QueStatus property
* @param value long with the QueStatus
*/
    public void setQueStatus(int value)
    {
        getSemanticObject().setIntProperty(forumCat_queStatus, value);
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
   * Gets all the org.semanticwb.resources.sem.forumcat.Answer
   * @return A GenericIterator with all the org.semanticwb.resources.sem.forumcat.Answer
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Answer> listAnswerInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Answer>(getSemanticObject().listObjectProperties(forumCat_hasAnswerInv));
    }

   /**
   * Gets true if has a AnswerInv
   * @param value org.semanticwb.resources.sem.forumcat.Answer to verify
   * @return true if the org.semanticwb.resources.sem.forumcat.Answer exists, false otherwise
   */
    public boolean hasAnswerInv(org.semanticwb.resources.sem.forumcat.Answer value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(forumCat_hasAnswerInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the AnswerInv
   * @return a org.semanticwb.resources.sem.forumcat.Answer
   */
    public org.semanticwb.resources.sem.forumcat.Answer getAnswerInv()
    {
         org.semanticwb.resources.sem.forumcat.Answer ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(forumCat_hasAnswerInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.resources.sem.forumcat.Answer)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Closed property
* @return boolean with the Closed
*/
    public boolean isClosed()
    {
        return getSemanticObject().getBooleanProperty(forumCat_closed);
    }

/**
* Sets the Closed property
* @param value long with the Closed
*/
    public void setClosed(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_closed, value);
    }

/**
* Gets the Question property
* @return String with the Question
*/
    public String getQuestion()
    {
        return getSemanticObject().getProperty(forumCat_question);
    }

/**
* Sets the Question property
* @param value long with the Question
*/
    public void setQuestion(String value)
    {
        getSemanticObject().setProperty(forumCat_question, value);
    }

/**
* Gets the QueIsApropiate property
* @return boolean with the QueIsApropiate
*/
    public boolean isQueIsApropiate()
    {
        return getSemanticObject().getBooleanProperty(forumCat_queIsApropiate);
    }

/**
* Sets the QueIsApropiate property
* @param value long with the QueIsApropiate
*/
    public void setQueIsApropiate(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_queIsApropiate, value);
    }

/**
* Gets the Tags property
* @return String with the Tags
*/
    public String getTags()
    {
        return getSemanticObject().getProperty(swb_tags);
    }

/**
* Sets the Tags property
* @param value long with the Tags
*/
    public void setTags(String value)
    {
        getSemanticObject().setProperty(swb_tags, value);
    }

/**
* Gets the Specifications property
* @return String with the Specifications
*/
    public String getSpecifications()
    {
        return getSemanticObject().getProperty(forumCat_specifications);
    }

/**
* Sets the Specifications property
* @param value long with the Specifications
*/
    public void setSpecifications(String value)
    {
        getSemanticObject().setProperty(forumCat_specifications, value);
    }

/**
* Gets the QueInappropriate property
* @return int with the QueInappropriate
*/
    public int getQueInappropriate()
    {
        return getSemanticObject().getIntProperty(forumCat_queInappropriate);
    }

/**
* Sets the QueInappropriate property
* @param value long with the QueInappropriate
*/
    public void setQueInappropriate(int value)
    {
        getSemanticObject().setIntProperty(forumCat_queInappropriate, value);
    }
}
