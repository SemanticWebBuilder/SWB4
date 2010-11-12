package org.semanticwb.resources.sem.forumcat.base;


public abstract class SWBForumCatResourceBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticProperty forumCat_questionClosable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#questionClosable");
    public static final org.semanticwb.platform.SemanticProperty forumCat_markInnapropiateAnswers=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#markInnapropiateAnswers");
    public static final org.semanticwb.platform.SemanticProperty forumCat_isQuestionSubscription=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#isQuestionSubscription");
    public static final org.semanticwb.platform.SemanticProperty forumCat_markInnapropiateQuestions=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#markInnapropiateQuestions");
    public static final org.semanticwb.platform.SemanticProperty forumCat_isCategorySubscription=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#isCategorySubscription");
    public static final org.semanticwb.platform.SemanticProperty forumCat_isModerate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#isModerate");
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_semanticResourceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#semanticResourceInv");
    public static final org.semanticwb.platform.SemanticProperty forumCat_acceptAttachements=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#acceptAttachements");
    public static final org.semanticwb.platform.SemanticProperty forumCat_acceptGuessComments=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#acceptGuessComments");
    public static final org.semanticwb.platform.SemanticClass forumCat_Question=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#Question");
    public static final org.semanticwb.platform.SemanticProperty forumCat_hasQuestionInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#hasQuestionInv");
    public static final org.semanticwb.platform.SemanticProperty forumCat_maxInnapropiateCount=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#maxInnapropiateCount");
    public static final org.semanticwb.platform.SemanticProperty forumCat_attachFilesTypes=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#attachFilesTypes");
    public static final org.semanticwb.platform.SemanticProperty forumCat_isAnswerVotable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#isAnswerVotable");
    public static final org.semanticwb.platform.SemanticProperty forumCat_selectCategory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#selectCategory");
    public static final org.semanticwb.platform.SemanticProperty forumCat_attachFilesSize=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#attachFilesSize");
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
