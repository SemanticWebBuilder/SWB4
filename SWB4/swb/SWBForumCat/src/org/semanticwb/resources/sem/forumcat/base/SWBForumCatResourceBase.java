package org.semanticwb.resources.sem.forumcat.base;


public abstract class SWBForumCatResourceBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticProperty forumCat_questionClosable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#questionClosable");
    public static final org.semanticwb.platform.SemanticProperty forumCat_markIrrelevantAnswers=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#markIrrelevantAnswers");
    public static final org.semanticwb.platform.SemanticProperty forumCat_markInnapropiateAnswers=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#markInnapropiateAnswers");
    public static final org.semanticwb.platform.SemanticProperty forumCat_isQuestionSubscription=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#isQuestionSubscription");
    public static final org.semanticwb.platform.SemanticProperty forumCat_markInnapropiateQuestions=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#markInnapropiateQuestions");
    public static final org.semanticwb.platform.SemanticProperty forumCat_pointsVoteAnswer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#pointsVoteAnswer");
    public static final org.semanticwb.platform.SemanticProperty forumCat_isCategorySubscription=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#isCategorySubscription");
    public static final org.semanticwb.platform.SemanticProperty forumCat_pointsIrrelevantAnswer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#pointsIrrelevantAnswer");
    public static final org.semanticwb.platform.SemanticProperty forumCat_pointsPublishQuestion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#pointsPublishQuestion");
    public static final org.semanticwb.platform.SemanticProperty forumCat_isModerate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#isModerate");
    public static final org.semanticwb.platform.SemanticProperty forumCat_viewJSP=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#viewJSP");
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_semanticResourceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#semanticResourceInv");
    public static final org.semanticwb.platform.SemanticProperty forumCat_acceptAttachements=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#acceptAttachements");
    public static final org.semanticwb.platform.SemanticProperty forumCat_markBestAnswer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#markBestAnswer");
    public static final org.semanticwb.platform.SemanticProperty forumCat_acceptGuessComments=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#acceptGuessComments");
    public static final org.semanticwb.platform.SemanticProperty forumCat_pointsMarkBestAnswer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#pointsMarkBestAnswer");
    public static final org.semanticwb.platform.SemanticClass forumCat_Question=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#Question");
    public static final org.semanticwb.platform.SemanticProperty forumCat_hasQuestionInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#hasQuestionInv");
    public static final org.semanticwb.platform.SemanticProperty forumCat_maxInnapropiateCount=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#maxInnapropiateCount");
    public static final org.semanticwb.platform.SemanticProperty forumCat_pointsAnswer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#pointsAnswer");
    public static final org.semanticwb.platform.SemanticProperty forumCat_attachFilesTypes=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#attachFilesTypes");
    public static final org.semanticwb.platform.SemanticProperty forumCat_isAnswerVotable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#isAnswerVotable");
    public static final org.semanticwb.platform.SemanticProperty forumCat_idCatPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#idCatPage");
    public static final org.semanticwb.platform.SemanticProperty forumCat_selectCategory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#selectCategory");
    public static final org.semanticwb.platform.SemanticProperty forumCat_attachFilesSize=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#attachFilesSize");
    public static final org.semanticwb.platform.SemanticProperty forumCat_pointsLikeAnswer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#pointsLikeAnswer");
    public static final org.semanticwb.platform.SemanticProperty forumCat_pointsBestAnswer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#pointsBestAnswer");
    public static final org.semanticwb.platform.SemanticProperty forumCat_useScoreSystem=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#useScoreSystem");
    public static final org.semanticwb.platform.SemanticProperty forumCat_pointsDontLikeAnswer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#pointsDontLikeAnswer");
    public static final org.semanticwb.platform.SemanticProperty forumCat_isQuestionVotable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#isQuestionVotable");
    public static final org.semanticwb.platform.SemanticClass forumCat_SWBForumCatResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#SWBForumCatResource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#SWBForumCatResource");

    public SWBForumCatResourceBase()
    {
    }

   /**
   * Constructs a SWBForumCatResourceBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SWBForumCatResource
   */
    public SWBForumCatResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() 
    {
        return getSemanticObject().hashCode();
    }

    /*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) 
    {
        if(obj==null)return false;
        return hashCode()==obj.hashCode();
    }

/**
* Gets the QuestionClosable property
* @return boolean with the QuestionClosable
*/
    public boolean isQuestionClosable()
    {
        return getSemanticObject().getBooleanProperty(forumCat_questionClosable);
    }

/**
* Sets the QuestionClosable property
* @param value long with the QuestionClosable
*/
    public void setQuestionClosable(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_questionClosable, value);
    }

/**
* Gets the MarkIrrelevantAnswers property
* @return boolean with the MarkIrrelevantAnswers
*/
    public boolean isMarkIrrelevantAnswers()
    {
        return getSemanticObject().getBooleanProperty(forumCat_markIrrelevantAnswers);
    }

/**
* Sets the MarkIrrelevantAnswers property
* @param value long with the MarkIrrelevantAnswers
*/
    public void setMarkIrrelevantAnswers(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_markIrrelevantAnswers, value);
    }

/**
* Gets the MarkInnapropiateAnswers property
* @return boolean with the MarkInnapropiateAnswers
*/
    public boolean isMarkInnapropiateAnswers()
    {
        return getSemanticObject().getBooleanProperty(forumCat_markInnapropiateAnswers);
    }

/**
* Sets the MarkInnapropiateAnswers property
* @param value long with the MarkInnapropiateAnswers
*/
    public void setMarkInnapropiateAnswers(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_markInnapropiateAnswers, value);
    }

/**
* Gets the IsQuestionSubscription property
* @return boolean with the IsQuestionSubscription
*/
    public boolean isIsQuestionSubscription()
    {
        return getSemanticObject().getBooleanProperty(forumCat_isQuestionSubscription);
    }

/**
* Sets the IsQuestionSubscription property
* @param value long with the IsQuestionSubscription
*/
    public void setIsQuestionSubscription(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_isQuestionSubscription, value);
    }

/**
* Gets the MarkInnapropiateQuestions property
* @return boolean with the MarkInnapropiateQuestions
*/
    public boolean isMarkInnapropiateQuestions()
    {
        return getSemanticObject().getBooleanProperty(forumCat_markInnapropiateQuestions);
    }

/**
* Sets the MarkInnapropiateQuestions property
* @param value long with the MarkInnapropiateQuestions
*/
    public void setMarkInnapropiateQuestions(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_markInnapropiateQuestions, value);
    }

/**
* Gets the PointsVoteAnswer property
* @return int with the PointsVoteAnswer
*/
    public int getPointsVoteAnswer()
    {
        return getSemanticObject().getIntProperty(forumCat_pointsVoteAnswer);
    }

/**
* Sets the PointsVoteAnswer property
* @param value long with the PointsVoteAnswer
*/
    public void setPointsVoteAnswer(int value)
    {
        getSemanticObject().setIntProperty(forumCat_pointsVoteAnswer, value);
    }

/**
* Gets the IsCategorySubscription property
* @return boolean with the IsCategorySubscription
*/
    public boolean isIsCategorySubscription()
    {
        return getSemanticObject().getBooleanProperty(forumCat_isCategorySubscription);
    }

/**
* Sets the IsCategorySubscription property
* @param value long with the IsCategorySubscription
*/
    public void setIsCategorySubscription(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_isCategorySubscription, value);
    }

/**
* Gets the PointsIrrelevantAnswer property
* @return int with the PointsIrrelevantAnswer
*/
    public int getPointsIrrelevantAnswer()
    {
        return getSemanticObject().getIntProperty(forumCat_pointsIrrelevantAnswer);
    }

/**
* Sets the PointsIrrelevantAnswer property
* @param value long with the PointsIrrelevantAnswer
*/
    public void setPointsIrrelevantAnswer(int value)
    {
        getSemanticObject().setIntProperty(forumCat_pointsIrrelevantAnswer, value);
    }

/**
* Gets the PointsPublishQuestion property
* @return int with the PointsPublishQuestion
*/
    public int getPointsPublishQuestion()
    {
        return getSemanticObject().getIntProperty(forumCat_pointsPublishQuestion);
    }

/**
* Sets the PointsPublishQuestion property
* @param value long with the PointsPublishQuestion
*/
    public void setPointsPublishQuestion(int value)
    {
        getSemanticObject().setIntProperty(forumCat_pointsPublishQuestion, value);
    }

/**
* Gets the IsModerate property
* @return boolean with the IsModerate
*/
    public boolean isIsModerate()
    {
        return getSemanticObject().getBooleanProperty(forumCat_isModerate);
    }

/**
* Sets the IsModerate property
* @param value long with the IsModerate
*/
    public void setIsModerate(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_isModerate, value);
    }

/**
* Gets the ViewJSP property
* @return String with the ViewJSP
*/
    public String getViewJSP()
    {
        return getSemanticObject().getProperty(forumCat_viewJSP);
    }

/**
* Sets the ViewJSP property
* @param value long with the ViewJSP
*/
    public void setViewJSP(String value)
    {
        getSemanticObject().setProperty(forumCat_viewJSP, value);
    }
   /**
   * Sets the value for the property Resource
   * @param value Resource to set
   */

    public void setResource(org.semanticwb.model.Resource value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_semanticResourceInv, value.getSemanticObject());
        }else
        {
            removeResource();
        }
    }
   /**
   * Remove the value for Resource property
   */

    public void removeResource()
    {
        getSemanticObject().removeProperty(swb_semanticResourceInv);
    }

   /**
   * Gets the Resource
   * @return a org.semanticwb.model.Resource
   */
    public org.semanticwb.model.Resource getResource()
    {
         org.semanticwb.model.Resource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_semanticResourceInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resource)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the AcceptAttachements property
* @return boolean with the AcceptAttachements
*/
    public boolean isAcceptAttachements()
    {
        return getSemanticObject().getBooleanProperty(forumCat_acceptAttachements);
    }

/**
* Sets the AcceptAttachements property
* @param value long with the AcceptAttachements
*/
    public void setAcceptAttachements(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_acceptAttachements, value);
    }

/**
* Gets the MarkBestAnswer property
* @return boolean with the MarkBestAnswer
*/
    public boolean isMarkBestAnswer()
    {
        return getSemanticObject().getBooleanProperty(forumCat_markBestAnswer);
    }

/**
* Sets the MarkBestAnswer property
* @param value long with the MarkBestAnswer
*/
    public void setMarkBestAnswer(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_markBestAnswer, value);
    }

/**
* Gets the AcceptGuessComments property
* @return boolean with the AcceptGuessComments
*/
    public boolean isAcceptGuessComments()
    {
        return getSemanticObject().getBooleanProperty(forumCat_acceptGuessComments);
    }

/**
* Sets the AcceptGuessComments property
* @param value long with the AcceptGuessComments
*/
    public void setAcceptGuessComments(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_acceptGuessComments, value);
    }

/**
* Gets the PointsMarkBestAnswer property
* @return int with the PointsMarkBestAnswer
*/
    public int getPointsMarkBestAnswer()
    {
        return getSemanticObject().getIntProperty(forumCat_pointsMarkBestAnswer);
    }

/**
* Sets the PointsMarkBestAnswer property
* @param value long with the PointsMarkBestAnswer
*/
    public void setPointsMarkBestAnswer(int value)
    {
        getSemanticObject().setIntProperty(forumCat_pointsMarkBestAnswer, value);
    }
   /**
   * Gets all the org.semanticwb.resources.sem.forumcat.Question
   * @return A GenericIterator with all the org.semanticwb.resources.sem.forumcat.Question
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question>(getSemanticObject().listObjectProperties(forumCat_hasQuestionInv));
    }

   /**
   * Gets true if has a QuestionInv
   * @param value org.semanticwb.resources.sem.forumcat.Question to verify
   * @return true if the org.semanticwb.resources.sem.forumcat.Question exists, false otherwise
   */
    public boolean hasQuestionInv(org.semanticwb.resources.sem.forumcat.Question value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(forumCat_hasQuestionInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the QuestionInv
   * @return a org.semanticwb.resources.sem.forumcat.Question
   */
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

/**
* Gets the MaxInnapropiateCount property
* @return int with the MaxInnapropiateCount
*/
    public int getMaxInnapropiateCount()
    {
        return getSemanticObject().getIntProperty(forumCat_maxInnapropiateCount);
    }

/**
* Sets the MaxInnapropiateCount property
* @param value long with the MaxInnapropiateCount
*/
    public void setMaxInnapropiateCount(int value)
    {
        getSemanticObject().setIntProperty(forumCat_maxInnapropiateCount, value);
    }

/**
* Gets the PointsAnswer property
* @return int with the PointsAnswer
*/
    public int getPointsAnswer()
    {
        return getSemanticObject().getIntProperty(forumCat_pointsAnswer);
    }

/**
* Sets the PointsAnswer property
* @param value long with the PointsAnswer
*/
    public void setPointsAnswer(int value)
    {
        getSemanticObject().setIntProperty(forumCat_pointsAnswer, value);
    }

/**
* Gets the AttachFilesTypes property
* @return String with the AttachFilesTypes
*/
    public String getAttachFilesTypes()
    {
        return getSemanticObject().getProperty(forumCat_attachFilesTypes);
    }

/**
* Sets the AttachFilesTypes property
* @param value long with the AttachFilesTypes
*/
    public void setAttachFilesTypes(String value)
    {
        getSemanticObject().setProperty(forumCat_attachFilesTypes, value);
    }

/**
* Gets the IsAnswerVotable property
* @return boolean with the IsAnswerVotable
*/
    public boolean isIsAnswerVotable()
    {
        return getSemanticObject().getBooleanProperty(forumCat_isAnswerVotable);
    }

/**
* Sets the IsAnswerVotable property
* @param value long with the IsAnswerVotable
*/
    public void setIsAnswerVotable(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_isAnswerVotable, value);
    }

/**
* Gets the IdCatPage property
* @return String with the IdCatPage
*/
    public String getIdCatPage()
    {
        return getSemanticObject().getProperty(forumCat_idCatPage);
    }

/**
* Sets the IdCatPage property
* @param value long with the IdCatPage
*/
    public void setIdCatPage(String value)
    {
        getSemanticObject().setProperty(forumCat_idCatPage, value);
    }

/**
* Gets the SelectCategory property
* @return boolean with the SelectCategory
*/
    public boolean isSelectCategory()
    {
        return getSemanticObject().getBooleanProperty(forumCat_selectCategory);
    }

/**
* Sets the SelectCategory property
* @param value long with the SelectCategory
*/
    public void setSelectCategory(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_selectCategory, value);
    }

/**
* Gets the AttachFilesSize property
* @return int with the AttachFilesSize
*/
    public int getAttachFilesSize()
    {
        return getSemanticObject().getIntProperty(forumCat_attachFilesSize);
    }

/**
* Sets the AttachFilesSize property
* @param value long with the AttachFilesSize
*/
    public void setAttachFilesSize(int value)
    {
        getSemanticObject().setIntProperty(forumCat_attachFilesSize, value);
    }

/**
* Gets the PointsLikeAnswer property
* @return int with the PointsLikeAnswer
*/
    public int getPointsLikeAnswer()
    {
        return getSemanticObject().getIntProperty(forumCat_pointsLikeAnswer);
    }

/**
* Sets the PointsLikeAnswer property
* @param value long with the PointsLikeAnswer
*/
    public void setPointsLikeAnswer(int value)
    {
        getSemanticObject().setIntProperty(forumCat_pointsLikeAnswer, value);
    }

/**
* Gets the PointsBestAnswer property
* @return int with the PointsBestAnswer
*/
    public int getPointsBestAnswer()
    {
        return getSemanticObject().getIntProperty(forumCat_pointsBestAnswer);
    }

/**
* Sets the PointsBestAnswer property
* @param value long with the PointsBestAnswer
*/
    public void setPointsBestAnswer(int value)
    {
        getSemanticObject().setIntProperty(forumCat_pointsBestAnswer, value);
    }

/**
* Gets the UseScoreSystem property
* @return boolean with the UseScoreSystem
*/
    public boolean isUseScoreSystem()
    {
        return getSemanticObject().getBooleanProperty(forumCat_useScoreSystem);
    }

/**
* Sets the UseScoreSystem property
* @param value long with the UseScoreSystem
*/
    public void setUseScoreSystem(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_useScoreSystem, value);
    }

/**
* Gets the PointsDontLikeAnswer property
* @return int with the PointsDontLikeAnswer
*/
    public int getPointsDontLikeAnswer()
    {
        return getSemanticObject().getIntProperty(forumCat_pointsDontLikeAnswer);
    }

/**
* Sets the PointsDontLikeAnswer property
* @param value long with the PointsDontLikeAnswer
*/
    public void setPointsDontLikeAnswer(int value)
    {
        getSemanticObject().setIntProperty(forumCat_pointsDontLikeAnswer, value);
    }

/**
* Gets the IsQuestionVotable property
* @return boolean with the IsQuestionVotable
*/
    public boolean isIsQuestionVotable()
    {
        return getSemanticObject().getBooleanProperty(forumCat_isQuestionVotable);
    }

/**
* Sets the IsQuestionVotable property
* @param value long with the IsQuestionVotable
*/
    public void setIsQuestionVotable(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_isQuestionVotable, value);
    }
}
