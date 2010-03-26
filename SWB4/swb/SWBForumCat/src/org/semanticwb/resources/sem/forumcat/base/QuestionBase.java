package org.semanticwb.resources.sem.forumcat.base;


public abstract class QuestionBase extends org.semanticwb.model.SWBClass 
{
       public static final org.semanticwb.platform.SemanticClass forumCat_Answer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#Answer");
       public static final org.semanticwb.platform.SemanticProperty forumCat_hasAnswerInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#hasAnswerInv");
       public static final org.semanticwb.platform.SemanticProperty forumCat_question=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#question");
       public static final org.semanticwb.platform.SemanticProperty forumCat_queInappropriate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#queInappropriate");
       public static final org.semanticwb.platform.SemanticClass forumCat_Forum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#Forum");
       public static final org.semanticwb.platform.SemanticProperty forumCat_queforum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#queforum");
       public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
       public static final org.semanticwb.platform.SemanticProperty forumCat_webpage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#webpage");
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
   public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionByAnswerInv(org.semanticwb.resources.sem.forumcat.Answer hasanswerinv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(forumCat_hasAnswerInv, hasanswerinv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionByAnswerInv(org.semanticwb.resources.sem.forumcat.Answer hasanswerinv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> it=new org.semanticwb.model.GenericIterator(hasanswerinv.getSemanticObject().getModel().listSubjects(forumCat_hasAnswerInv,hasanswerinv.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionByQueforum(org.semanticwb.resources.sem.forumcat.Forum queforum,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(forumCat_queforum, queforum.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionByQueforum(org.semanticwb.resources.sem.forumcat.Forum queforum)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> it=new org.semanticwb.model.GenericIterator(queforum.getSemanticObject().getModel().listSubjects(forumCat_queforum,queforum.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionByWebpage(org.semanticwb.model.WebPage webpage,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(forumCat_webpage, webpage.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionByWebpage(org.semanticwb.model.WebPage webpage)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> it=new org.semanticwb.model.GenericIterator(webpage.getSemanticObject().getModel().listSubjects(forumCat_webpage,webpage.getSemanticObject()));
       return it;
   }
    }

    public QuestionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Answer> listAnswerInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Answer>(getSemanticObject().listObjectProperties(forumCat_hasAnswerInv));
    }

    public boolean hasAnswerInv(org.semanticwb.resources.sem.forumcat.Answer answer)
    {
        if(answer==null)return false;
        return getSemanticObject().hasObjectProperty(forumCat_hasAnswerInv,answer.getSemanticObject());
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

    public String getQuestion()
    {
        return getSemanticObject().getProperty(forumCat_question);
    }

    public void setQuestion(String value)
    {
        getSemanticObject().setProperty(forumCat_question, value);
    }

    public boolean isQueInappropriate()
    {
        return getSemanticObject().getBooleanProperty(forumCat_queInappropriate);
    }

    public void setQueInappropriate(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_queInappropriate, value);
    }

    public void setQueforum(org.semanticwb.resources.sem.forumcat.Forum value)
    {
        getSemanticObject().setObjectProperty(forumCat_queforum, value.getSemanticObject());
    }

    public void removeQueforum()
    {
        getSemanticObject().removeProperty(forumCat_queforum);
    }


    public org.semanticwb.resources.sem.forumcat.Forum getQueforum()
    {
         org.semanticwb.resources.sem.forumcat.Forum ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(forumCat_queforum);
         if(obj!=null)
         {
             ret=(org.semanticwb.resources.sem.forumcat.Forum)obj.createGenericInstance();
         }
         return ret;
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
}
