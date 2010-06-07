package org.semanticwb.resources.sem.forumcat.base;


public abstract class SWBForumCatResourceBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticProperty forumCat_attachFilesSize=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#attachFilesSize");
    public static final org.semanticwb.platform.SemanticProperty forumCat_markInnapropiateQuestions=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#markInnapropiateQuestions");
    public static final org.semanticwb.platform.SemanticProperty forumCat_isQuestionVotable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#isQuestionVotable");
    public static final org.semanticwb.platform.SemanticProperty forumCat_isAnswerVotable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#isAnswerVotable");
    public static final org.semanticwb.platform.SemanticProperty forumCat_acceptGuessComments=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#acceptGuessComments");
    public static final org.semanticwb.platform.SemanticProperty forumCat_isModerate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#isModerate");
    public static final org.semanticwb.platform.SemanticProperty forumCat_attachFilesTypes=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#attachFilesTypes");
    public static final org.semanticwb.platform.SemanticProperty forumCat_markInnapropiateAnswers=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#markInnapropiateAnswers");
    public static final org.semanticwb.platform.SemanticProperty forumCat_isQuestionSubscription=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#isQuestionSubscription");
    public static final org.semanticwb.platform.SemanticProperty forumCat_maxInnapropiateCount=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#maxInnapropiateCount");
    public static final org.semanticwb.platform.SemanticProperty forumCat_isCategorySubscription=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#isCategorySubscription");
    public static final org.semanticwb.platform.SemanticProperty forumCat_acceptAttachements=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#acceptAttachements");
    public static final org.semanticwb.platform.SemanticClass forumCat_Question=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#Question");
    public static final org.semanticwb.platform.SemanticProperty forumCat_hasQuestionInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#hasQuestionInv");
    public static final org.semanticwb.platform.SemanticClass forumCat_SWBForumCatResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#SWBForumCatResource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#SWBForumCatResource");

    public SWBForumCatResourceBase()
    {
    }

    public SWBForumCatResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public int getAttachFilesSize()
    {
        return getSemanticObject().getIntProperty(forumCat_attachFilesSize);
    }

    public void setAttachFilesSize(int value)
    {
        getSemanticObject().setIntProperty(forumCat_attachFilesSize, value);
    }

    public boolean isMarkInnapropiateQuestions()
    {
        return getSemanticObject().getBooleanProperty(forumCat_markInnapropiateQuestions);
    }

    public void setMarkInnapropiateQuestions(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_markInnapropiateQuestions, value);
    }

    public boolean isIsQuestionVotable()
    {
        return getSemanticObject().getBooleanProperty(forumCat_isQuestionVotable);
    }

    public void setIsQuestionVotable(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_isQuestionVotable, value);
    }

    public boolean isIsAnswerVotable()
    {
        return getSemanticObject().getBooleanProperty(forumCat_isAnswerVotable);
    }

    public void setIsAnswerVotable(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_isAnswerVotable, value);
    }

    public boolean isAcceptGuessComments()
    {
        return getSemanticObject().getBooleanProperty(forumCat_acceptGuessComments);
    }

    public void setAcceptGuessComments(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_acceptGuessComments, value);
    }

    public boolean isIsModerate()
    {
        return getSemanticObject().getBooleanProperty(forumCat_isModerate);
    }

    public void setIsModerate(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_isModerate, value);
    }

    public String getAttachFilesTypes()
    {
        return getSemanticObject().getProperty(forumCat_attachFilesTypes);
    }

    public void setAttachFilesTypes(String value)
    {
        getSemanticObject().setProperty(forumCat_attachFilesTypes, value);
    }

    public boolean isMarkInnapropiateAnswers()
    {
        return getSemanticObject().getBooleanProperty(forumCat_markInnapropiateAnswers);
    }

    public void setMarkInnapropiateAnswers(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_markInnapropiateAnswers, value);
    }

    public boolean isIsQuestionSubscription()
    {
        return getSemanticObject().getBooleanProperty(forumCat_isQuestionSubscription);
    }

    public void setIsQuestionSubscription(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_isQuestionSubscription, value);
    }

    public int getMaxInnapropiateCount()
    {
        return getSemanticObject().getIntProperty(forumCat_maxInnapropiateCount);
    }

    public void setMaxInnapropiateCount(int value)
    {
        getSemanticObject().setIntProperty(forumCat_maxInnapropiateCount, value);
    }

    public boolean isIsCategorySubscription()
    {
        return getSemanticObject().getBooleanProperty(forumCat_isCategorySubscription);
    }

    public void setIsCategorySubscription(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_isCategorySubscription, value);
    }

    public boolean isAcceptAttachements()
    {
        return getSemanticObject().getBooleanProperty(forumCat_acceptAttachements);
    }

    public void setAcceptAttachements(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_acceptAttachements, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question>(getSemanticObject().listObjectProperties(forumCat_hasQuestionInv));
    }

    public boolean hasQuestionInv(org.semanticwb.resources.sem.forumcat.Question value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(forumCat_hasQuestionInv,value.getSemanticObject());
        }
        return ret;
    }

    public org.semanticwb.resources.sem.forumcat.Question getQuestionInv()
    {
         org.semanticwb.resources.sem.forumcat.Question ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(forumCat_hasQuestionInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.resources.sem.forumcat.Question)obj.createGenericInstance();
         }
         return ret;
    }
}
