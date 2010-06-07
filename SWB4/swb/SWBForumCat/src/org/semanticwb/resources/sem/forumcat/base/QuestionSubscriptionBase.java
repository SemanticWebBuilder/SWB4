package org.semanticwb.resources.sem.forumcat.base;


public abstract class QuestionSubscriptionBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty forumCat_userObj=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#userObj");
    public static final org.semanticwb.platform.SemanticClass forumCat_Question=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#Question");
    public static final org.semanticwb.platform.SemanticProperty forumCat_questionObj=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#questionObj");
    public static final org.semanticwb.platform.SemanticClass forumCat_QuestionSubscription=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#QuestionSubscription");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#QuestionSubscription");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.QuestionSubscription> listQuestionSubscriptions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.QuestionSubscription>(it, true);
        }

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

        public static org.semanticwb.resources.sem.forumcat.QuestionSubscription getQuestionSubscription(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.resources.sem.forumcat.QuestionSubscription)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.resources.sem.forumcat.QuestionSubscription createQuestionSubscription(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.resources.sem.forumcat.QuestionSubscription)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeQuestionSubscription(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasQuestionSubscription(String id, org.semanticwb.model.SWBModel model)
        {
            return (getQuestionSubscription(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.QuestionSubscription> listQuestionSubscriptionByUserObj(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.QuestionSubscription> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(forumCat_userObj, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.QuestionSubscription> listQuestionSubscriptionByUserObj(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.QuestionSubscription> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(forumCat_userObj,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.QuestionSubscription> listQuestionSubscriptionByQuestionObj(org.semanticwb.resources.sem.forumcat.Question value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.QuestionSubscription> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(forumCat_questionObj, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.QuestionSubscription> listQuestionSubscriptionByQuestionObj(org.semanticwb.resources.sem.forumcat.Question value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.QuestionSubscription> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(forumCat_questionObj,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public QuestionSubscriptionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setUserObj(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(forumCat_userObj, value.getSemanticObject());
    }

    public void removeUserObj()
    {
        getSemanticObject().removeProperty(forumCat_userObj);
    }

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

    public void setQuestionObj(org.semanticwb.resources.sem.forumcat.Question value)
    {
        getSemanticObject().setObjectProperty(forumCat_questionObj, value.getSemanticObject());
    }

    public void removeQuestionObj()
    {
        getSemanticObject().removeProperty(forumCat_questionObj);
    }

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
