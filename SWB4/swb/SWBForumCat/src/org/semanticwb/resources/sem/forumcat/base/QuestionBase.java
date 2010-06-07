package org.semanticwb.resources.sem.forumcat.base;


public abstract class QuestionBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty forumCat_queInappropriate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#queInappropriate");
    public static final org.semanticwb.platform.SemanticProperty forumCat_queIsApropiate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#queIsApropiate");
    public static final org.semanticwb.platform.SemanticProperty forumCat_queStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#queStatus");
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty forumCat_webpage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#webpage");
    public static final org.semanticwb.platform.SemanticClass forumCat_QuestionSubscription=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#QuestionSubscription");
    public static final org.semanticwb.platform.SemanticProperty forumCat_questionObjInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#questionObjInv");
    public static final org.semanticwb.platform.SemanticClass forumCat_Answer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#Answer");
    public static final org.semanticwb.platform.SemanticProperty forumCat_hasAnswerInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#hasAnswerInv");
    public static final org.semanticwb.platform.SemanticProperty forumCat_specifications=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#specifications");
    public static final org.semanticwb.platform.SemanticClass forumCat_SWBForumCatResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#SWBForumCatResource");
    public static final org.semanticwb.platform.SemanticProperty forumCat_forumResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#forumResource");
    public static final org.semanticwb.platform.SemanticProperty forumCat_question=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#question");
    public static final org.semanticwb.platform.SemanticClass forumCat_Question=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#Question");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#Question");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question>(it, true);
        }

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

        public static org.semanticwb.resources.sem.forumcat.Question getQuestion(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.resources.sem.forumcat.Question)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.resources.sem.forumcat.Question createQuestion(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.resources.sem.forumcat.Question)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeQuestion(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasQuestion(String id, org.semanticwb.model.SWBModel model)
        {
            return (getQuestion(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionByWebpage(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(forumCat_webpage, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionByWebpage(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(forumCat_webpage,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionByQuestionObjInv(org.semanticwb.resources.sem.forumcat.QuestionSubscription value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(forumCat_questionObjInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionByQuestionObjInv(org.semanticwb.resources.sem.forumcat.QuestionSubscription value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(forumCat_questionObjInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionByAnswerInv(org.semanticwb.resources.sem.forumcat.Answer value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(forumCat_hasAnswerInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionByAnswerInv(org.semanticwb.resources.sem.forumcat.Answer value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(forumCat_hasAnswerInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionByForumResource(org.semanticwb.resources.sem.forumcat.SWBForumCatResource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(forumCat_forumResource, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionByForumResource(org.semanticwb.resources.sem.forumcat.SWBForumCatResource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(forumCat_forumResource,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public QuestionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public int getQueInappropriate()
    {
        return getSemanticObject().getIntProperty(forumCat_queInappropriate);
    }

    public void setQueInappropriate(int value)
    {
        getSemanticObject().setIntProperty(forumCat_queInappropriate, value);
    }

    public boolean isQueIsApropiate()
    {
        return getSemanticObject().getBooleanProperty(forumCat_queIsApropiate);
    }

    public void setQueIsApropiate(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_queIsApropiate, value);
    }

    public int getQueStatus()
    {
        return getSemanticObject().getIntProperty(forumCat_queStatus);
    }

    public void setQueStatus(int value)
    {
        getSemanticObject().setIntProperty(forumCat_queStatus, value);
    }

    public void setWebpage(org.semanticwb.model.WebPage value)
    {
        getSemanticObject().setObjectProperty(forumCat_webpage, value.getSemanticObject());
    }

    public void removeWebpage()
    {
        getSemanticObject().removeProperty(forumCat_webpage);
    }

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

    public void setQuestionObjInv(org.semanticwb.resources.sem.forumcat.QuestionSubscription value)
    {
        getSemanticObject().setObjectProperty(forumCat_questionObjInv, value.getSemanticObject());
    }

    public void removeQuestionObjInv()
    {
        getSemanticObject().removeProperty(forumCat_questionObjInv);
    }

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

    public org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Answer> listAnswerInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Answer>(getSemanticObject().listObjectProperties(forumCat_hasAnswerInv));
    }

    public boolean hasAnswerInv(org.semanticwb.resources.sem.forumcat.Answer value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(forumCat_hasAnswerInv,value.getSemanticObject());
        }
        return ret;
    }

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

    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

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

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

    public String getSpecifications()
    {
        return getSemanticObject().getProperty(forumCat_specifications);
    }

    public void setSpecifications(String value)
    {
        getSemanticObject().setProperty(forumCat_specifications, value);
    }

    public void setForumResource(org.semanticwb.resources.sem.forumcat.SWBForumCatResource value)
    {
        getSemanticObject().setObjectProperty(forumCat_forumResource, value.getSemanticObject());
    }

    public void removeForumResource()
    {
        getSemanticObject().removeProperty(forumCat_forumResource);
    }

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

    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

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

    public String getQuestion()
    {
        return getSemanticObject().getProperty(forumCat_question);
    }

    public void setQuestion(String value)
    {
        getSemanticObject().setProperty(forumCat_question, value);
    }
}
