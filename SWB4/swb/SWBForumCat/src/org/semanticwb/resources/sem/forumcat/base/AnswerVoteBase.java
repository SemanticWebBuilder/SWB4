package org.semanticwb.resources.sem.forumcat.base;


public abstract class AnswerVoteBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticProperty forumCat_likeAnswer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#likeAnswer");
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty forumCat_ansUserVote=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#ansUserVote");
    public static final org.semanticwb.platform.SemanticProperty forumCat_ansCommentVote=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#ansCommentVote");
    public static final org.semanticwb.platform.SemanticClass forumCat_Answer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#Answer");
    public static final org.semanticwb.platform.SemanticProperty forumCat_answerVote=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#answerVote");
    public static final org.semanticwb.platform.SemanticClass forumCat_AnswerVote=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#AnswerVote");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#AnswerVote");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.AnswerVote> listAnswerVotes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.AnswerVote>(it, true);
        }

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

        public static org.semanticwb.resources.sem.forumcat.AnswerVote getAnswerVote(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.resources.sem.forumcat.AnswerVote)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.resources.sem.forumcat.AnswerVote createAnswerVote(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.resources.sem.forumcat.AnswerVote)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeAnswerVote(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasAnswerVote(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAnswerVote(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.AnswerVote> listAnswerVoteByAnsUserVote(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.AnswerVote> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(forumCat_ansUserVote, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.AnswerVote> listAnswerVoteByAnsUserVote(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.AnswerVote> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(forumCat_ansUserVote,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.AnswerVote> listAnswerVoteByAnswerVote(org.semanticwb.resources.sem.forumcat.Answer value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.AnswerVote> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(forumCat_answerVote, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.AnswerVote> listAnswerVoteByAnswerVote(org.semanticwb.resources.sem.forumcat.Answer value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.AnswerVote> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(forumCat_answerVote,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public AnswerVoteBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public boolean isLikeAnswer()
    {
        return getSemanticObject().getBooleanProperty(forumCat_likeAnswer);
    }

    public void setLikeAnswer(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_likeAnswer, value);
    }

    public void setAnsUserVote(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(forumCat_ansUserVote, value.getSemanticObject());
    }

    public void removeAnsUserVote()
    {
        getSemanticObject().removeProperty(forumCat_ansUserVote);
    }

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

    public String getAnsCommentVote()
    {
        return getSemanticObject().getProperty(forumCat_ansCommentVote);
    }

    public void setAnsCommentVote(String value)
    {
        getSemanticObject().setProperty(forumCat_ansCommentVote, value);
    }

    public void setAnswerVote(org.semanticwb.resources.sem.forumcat.Answer value)
    {
        getSemanticObject().setObjectProperty(forumCat_answerVote, value.getSemanticObject());
    }

    public void removeAnswerVote()
    {
        getSemanticObject().removeProperty(forumCat_answerVote);
    }

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
