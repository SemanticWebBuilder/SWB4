package org.semanticwb.resources.sem.forumcat.base;


public abstract class QuestionVoteBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass forumCat_Question=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#Question");
    public static final org.semanticwb.platform.SemanticProperty forumCat_questionVote=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#questionVote");
    public static final org.semanticwb.platform.SemanticProperty forumCat_commentVote=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#commentVote");
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty forumCat_userVote=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#userVote");
    public static final org.semanticwb.platform.SemanticProperty forumCat_likeVote=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#likeVote");
    public static final org.semanticwb.platform.SemanticClass forumCat_QuestionVote=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#QuestionVote");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#QuestionVote");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.QuestionVote> listQuestionVotes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.QuestionVote>(it, true);
        }

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

        public static org.semanticwb.resources.sem.forumcat.QuestionVote getQuestionVote(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.resources.sem.forumcat.QuestionVote)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.resources.sem.forumcat.QuestionVote createQuestionVote(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.resources.sem.forumcat.QuestionVote)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeQuestionVote(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasQuestionVote(String id, org.semanticwb.model.SWBModel model)
        {
            return (getQuestionVote(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.QuestionVote> listQuestionVoteByQuestionVote(org.semanticwb.resources.sem.forumcat.Question value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.QuestionVote> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(forumCat_questionVote, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.QuestionVote> listQuestionVoteByQuestionVote(org.semanticwb.resources.sem.forumcat.Question value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.QuestionVote> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(forumCat_questionVote,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.QuestionVote> listQuestionVoteByUserVote(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.QuestionVote> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(forumCat_userVote, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.QuestionVote> listQuestionVoteByUserVote(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.QuestionVote> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(forumCat_userVote,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public QuestionVoteBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setQuestionVote(org.semanticwb.resources.sem.forumcat.Question value)
    {
        getSemanticObject().setObjectProperty(forumCat_questionVote, value.getSemanticObject());
    }

    public void removeQuestionVote()
    {
        getSemanticObject().removeProperty(forumCat_questionVote);
    }

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

    public String getCommentVote()
    {
        return getSemanticObject().getProperty(forumCat_commentVote);
    }

    public void setCommentVote(String value)
    {
        getSemanticObject().setProperty(forumCat_commentVote, value);
    }

    public void setUserVote(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(forumCat_userVote, value.getSemanticObject());
    }

    public void removeUserVote()
    {
        getSemanticObject().removeProperty(forumCat_userVote);
    }

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

    public boolean isLikeVote()
    {
        return getSemanticObject().getBooleanProperty(forumCat_likeVote);
    }

    public void setLikeVote(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_likeVote, value);
    }
}
