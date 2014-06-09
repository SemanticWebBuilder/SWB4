package org.semanticwb.resources.sem.forumcat.base;


public abstract class QuestionSubscriptionBase extends org.semanticwb.model.SWBClass 
{
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso.
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty forumCat_userObj=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#userObj");
    public static final org.semanticwb.platform.SemanticClass forumCat_Question=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#Question");
    public static final org.semanticwb.platform.SemanticProperty forumCat_questionObj=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#questionObj");
    public static final org.semanticwb.platform.SemanticClass forumCat_QuestionSubscription=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#QuestionSubscription");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#QuestionSubscription");

    public static class ClassMgr
    {
       /**
       * Returns a list of QuestionSubscription for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.resources.sem.forumcat.QuestionSubscription
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.QuestionSubscription> listQuestionSubscriptions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.QuestionSubscription>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.resources.sem.forumcat.QuestionSubscription for all models
       * @return Iterator of org.semanticwb.resources.sem.forumcat.QuestionSubscription
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.QuestionSubscription> listQuestionSubscriptions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.QuestionSubscription>(it, true);
        }

        public static org.semanticwb.resources.sem.forumcat.QuestionSubscription createQuestionSubscription(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.resources.sem.forumcat.QuestionSubscription.ClassMgr.createQuestionSubscription(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.resources.sem.forumcat.QuestionSubscription
       * @param id Identifier for org.semanticwb.resources.sem.forumcat.QuestionSubscription
       * @param model Model of the org.semanticwb.resources.sem.forumcat.QuestionSubscription
       * @return A org.semanticwb.resources.sem.forumcat.QuestionSubscription
       */
        public static org.semanticwb.resources.sem.forumcat.QuestionSubscription getQuestionSubscription(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.resources.sem.forumcat.QuestionSubscription)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.resources.sem.forumcat.QuestionSubscription
       * @param id Identifier for org.semanticwb.resources.sem.forumcat.QuestionSubscription
       * @param model Model of the org.semanticwb.resources.sem.forumcat.QuestionSubscription
       * @return A org.semanticwb.resources.sem.forumcat.QuestionSubscription
       */
        public static org.semanticwb.resources.sem.forumcat.QuestionSubscription createQuestionSubscription(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.resources.sem.forumcat.QuestionSubscription)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.resources.sem.forumcat.QuestionSubscription
       * @param id Identifier for org.semanticwb.resources.sem.forumcat.QuestionSubscription
       * @param model Model of the org.semanticwb.resources.sem.forumcat.QuestionSubscription
       */
        public static void removeQuestionSubscription(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.resources.sem.forumcat.QuestionSubscription
       * @param id Identifier for org.semanticwb.resources.sem.forumcat.QuestionSubscription
       * @param model Model of the org.semanticwb.resources.sem.forumcat.QuestionSubscription
       * @return true if the org.semanticwb.resources.sem.forumcat.QuestionSubscription exists, false otherwise
       */

        public static boolean hasQuestionSubscription(String id, org.semanticwb.model.SWBModel model)
        {
            return (getQuestionSubscription(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.QuestionSubscription with a determined UserObj
       * @param value UserObj of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.resources.sem.forumcat.QuestionSubscription
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.QuestionSubscription
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.QuestionSubscription> listQuestionSubscriptionByUserObj(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.QuestionSubscription> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(forumCat_userObj, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.QuestionSubscription with a determined UserObj
       * @param value UserObj of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.QuestionSubscription
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.QuestionSubscription> listQuestionSubscriptionByUserObj(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.QuestionSubscription> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(forumCat_userObj,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.QuestionSubscription with a determined QuestionObj
       * @param value QuestionObj of the type org.semanticwb.resources.sem.forumcat.Question
       * @param model Model of the org.semanticwb.resources.sem.forumcat.QuestionSubscription
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.QuestionSubscription
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.QuestionSubscription> listQuestionSubscriptionByQuestionObj(org.semanticwb.resources.sem.forumcat.Question value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.QuestionSubscription> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(forumCat_questionObj, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.QuestionSubscription with a determined QuestionObj
       * @param value QuestionObj of the type org.semanticwb.resources.sem.forumcat.Question
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.QuestionSubscription
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.QuestionSubscription> listQuestionSubscriptionByQuestionObj(org.semanticwb.resources.sem.forumcat.Question value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.QuestionSubscription> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(forumCat_questionObj,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static QuestionSubscriptionBase.ClassMgr getQuestionSubscriptionClassMgr()
    {
        return new QuestionSubscriptionBase.ClassMgr();
    }

   /**
   * Constructs a QuestionSubscriptionBase with a SemanticObject
   * @param base The SemanticObject with the properties for the QuestionSubscription
   */
    public QuestionSubscriptionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property UserObj
   * @param value UserObj to set
   */

    public void setUserObj(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(forumCat_userObj, value.getSemanticObject());
        }else
        {
            removeUserObj();
        }
    }
   /**
   * Remove the value for UserObj property
   */

    public void removeUserObj()
    {
        getSemanticObject().removeProperty(forumCat_userObj);
    }

   /**
   * Gets the UserObj
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getUserObj()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(forumCat_userObj);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property QuestionObj
   * @param value QuestionObj to set
   */

    public void setQuestionObj(org.semanticwb.resources.sem.forumcat.Question value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(forumCat_questionObj, value.getSemanticObject());
        }else
        {
            removeQuestionObj();
        }
    }
   /**
   * Remove the value for QuestionObj property
   */

    public void removeQuestionObj()
    {
        getSemanticObject().removeProperty(forumCat_questionObj);
    }

   /**
   * Gets the QuestionObj
   * @return a org.semanticwb.resources.sem.forumcat.Question
   */
    public org.semanticwb.resources.sem.forumcat.Question getQuestionObj()
    {
         org.semanticwb.resources.sem.forumcat.Question ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(forumCat_questionObj);
         if(obj!=null)
         {
             ret=(org.semanticwb.resources.sem.forumcat.Question)obj.createGenericInstance();
         }
         return ret;
    }
}
