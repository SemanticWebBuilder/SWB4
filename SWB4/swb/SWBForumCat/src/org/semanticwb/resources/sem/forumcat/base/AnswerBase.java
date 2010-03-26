package org.semanticwb.resources.sem.forumcat.base;


public abstract class AnswerBase extends org.semanticwb.model.SWBClass 
{
       public static final org.semanticwb.platform.SemanticProperty forumCat_ansInappropriate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#ansInappropriate");
       public static final org.semanticwb.platform.SemanticClass forumCat_Question=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#Question");
       public static final org.semanticwb.platform.SemanticProperty forumCat_ansQuestion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#ansQuestion");
       public static final org.semanticwb.platform.SemanticProperty forumCat_answer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#answer");
       public static final org.semanticwb.platform.SemanticClass forumCat_Answer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#Answer");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#Answer");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Answer> listAnswers(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Answer>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Answer> listAnswers()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Answer>(it, true);
       }

       public static org.semanticwb.resources.sem.forumcat.Answer getAnswer(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.resources.sem.forumcat.Answer)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.resources.sem.forumcat.Answer createAnswer(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.resources.sem.forumcat.Answer)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeAnswer(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasAnswer(String id, org.semanticwb.model.SWBModel model)
       {
           return (getAnswer(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Answer> listAnswerByAnsQuestion(org.semanticwb.resources.sem.forumcat.Question ansquestion,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Answer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(forumCat_ansQuestion, ansquestion.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Answer> listAnswerByAnsQuestion(org.semanticwb.resources.sem.forumcat.Question ansquestion)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Answer> it=new org.semanticwb.model.GenericIterator(ansquestion.getSemanticObject().getModel().listSubjects(forumCat_ansQuestion,ansquestion.getSemanticObject()));
       return it;
   }
    }

    public AnswerBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public boolean isAnsInappropriate()
    {
        return getSemanticObject().getBooleanProperty(forumCat_ansInappropriate);
    }

    public void setAnsInappropriate(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_ansInappropriate, value);
    }

    public void setAnsQuestion(org.semanticwb.resources.sem.forumcat.Question value)
    {
        getSemanticObject().setObjectProperty(forumCat_ansQuestion, value.getSemanticObject());
    }

    public void removeAnsQuestion()
    {
        getSemanticObject().removeProperty(forumCat_ansQuestion);
    }


    public org.semanticwb.resources.sem.forumcat.Question getAnsQuestion()
    {
         org.semanticwb.resources.sem.forumcat.Question ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(forumCat_ansQuestion);
         if(obj!=null)
         {
             ret=(org.semanticwb.resources.sem.forumcat.Question)obj.createGenericInstance();
         }
         return ret;
    }

    public String getAnswer()
    {
        return getSemanticObject().getProperty(forumCat_answer);
    }

    public void setAnswer(String value)
    {
        getSemanticObject().setProperty(forumCat_answer, value);
    }
}
