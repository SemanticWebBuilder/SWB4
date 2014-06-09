package org.semanticwb.resources.sem.forumcat.base;


public abstract class AnswerBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Searchable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty forumCat_hasAttachements=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#hasAttachements");
    public static final org.semanticwb.platform.SemanticProperty forumCat_ansInappropriate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#ansInappropriate");
    public static final org.semanticwb.platform.SemanticProperty forumCat_ansStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#ansStatus");
   /**
   * Sirve para poner ligas de referencias a la respuesta que se esta dando
   */
    public static final org.semanticwb.platform.SemanticProperty forumCat_references=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#references");
    public static final org.semanticwb.platform.SemanticProperty forumCat_ansIsAppropiate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#ansIsAppropiate");
    public static final org.semanticwb.platform.SemanticProperty forumCat_answer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#answer");
    public static final org.semanticwb.platform.SemanticClass forumCat_Question=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#Question");
    public static final org.semanticwb.platform.SemanticProperty forumCat_ansQuestion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#ansQuestion");
   /**
   * Indica si la respuesta ha sido marcada como la mejor.
   */
    public static final org.semanticwb.platform.SemanticProperty forumCat_bestAnswer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#bestAnswer");
    public static final org.semanticwb.platform.SemanticProperty forumCat_ansIrrelevant=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#ansIrrelevant");
    public static final org.semanticwb.platform.SemanticClass forumCat_Answer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#Answer");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#Answer");

    public static class ClassMgr
    {
       /**
       * Returns a list of Answer for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.resources.sem.forumcat.Answer
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Answer> listAnswers(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Answer>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.resources.sem.forumcat.Answer for all models
       * @return Iterator of org.semanticwb.resources.sem.forumcat.Answer
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Answer> listAnswers()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Answer>(it, true);
        }

        public static org.semanticwb.resources.sem.forumcat.Answer createAnswer(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.resources.sem.forumcat.Answer.ClassMgr.createAnswer(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.resources.sem.forumcat.Answer
       * @param id Identifier for org.semanticwb.resources.sem.forumcat.Answer
       * @param model Model of the org.semanticwb.resources.sem.forumcat.Answer
       * @return A org.semanticwb.resources.sem.forumcat.Answer
       */
        public static org.semanticwb.resources.sem.forumcat.Answer getAnswer(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.resources.sem.forumcat.Answer)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.resources.sem.forumcat.Answer
       * @param id Identifier for org.semanticwb.resources.sem.forumcat.Answer
       * @param model Model of the org.semanticwb.resources.sem.forumcat.Answer
       * @return A org.semanticwb.resources.sem.forumcat.Answer
       */
        public static org.semanticwb.resources.sem.forumcat.Answer createAnswer(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.resources.sem.forumcat.Answer)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.resources.sem.forumcat.Answer
       * @param id Identifier for org.semanticwb.resources.sem.forumcat.Answer
       * @param model Model of the org.semanticwb.resources.sem.forumcat.Answer
       */
        public static void removeAnswer(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.resources.sem.forumcat.Answer
       * @param id Identifier for org.semanticwb.resources.sem.forumcat.Answer
       * @param model Model of the org.semanticwb.resources.sem.forumcat.Answer
       * @return true if the org.semanticwb.resources.sem.forumcat.Answer exists, false otherwise
       */

        public static boolean hasAnswer(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAnswer(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.Answer with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.resources.sem.forumcat.Answer
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.Answer
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Answer> listAnswerByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Answer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.Answer with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.Answer
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Answer> listAnswerByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Answer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.Answer with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.resources.sem.forumcat.Answer
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.Answer
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Answer> listAnswerByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Answer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.Answer with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.Answer
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Answer> listAnswerByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Answer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.Answer with a determined AnsQuestion
       * @param value AnsQuestion of the type org.semanticwb.resources.sem.forumcat.Question
       * @param model Model of the org.semanticwb.resources.sem.forumcat.Answer
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.Answer
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Answer> listAnswerByAnsQuestion(org.semanticwb.resources.sem.forumcat.Question value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Answer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(forumCat_ansQuestion, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.Answer with a determined AnsQuestion
       * @param value AnsQuestion of the type org.semanticwb.resources.sem.forumcat.Question
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.Answer
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Answer> listAnswerByAnsQuestion(org.semanticwb.resources.sem.forumcat.Question value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Answer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(forumCat_ansQuestion,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static AnswerBase.ClassMgr getAnswerClassMgr()
    {
        return new AnswerBase.ClassMgr();
    }

   /**
   * Constructs a AnswerBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Answer
   */
    public AnswerBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public java.util.Iterator<String> listAttachementses()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(forumCat_hasAttachements);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addAttachements(String value)
    {
        getSemanticObject().addLiteralProperty(forumCat_hasAttachements, new org.semanticwb.platform.SemanticLiteral(value));
    }

    public void removeAllAttachements()
    {
        getSemanticObject().removeProperty(forumCat_hasAttachements);
    }

    public void removeAttachements(String value)
    {
        getSemanticObject().removeLiteralProperty(forumCat_hasAttachements,new org.semanticwb.platform.SemanticLiteral(value));
    }
   /**
   * Sets the value for the property ModifiedBy
   * @param value ModifiedBy to set
   */

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
        }else
        {
            removeModifiedBy();
        }
    }
   /**
   * Remove the value for ModifiedBy property
   */

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

   /**
   * Gets the ModifiedBy
   * @return a org.semanticwb.model.User
   */
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

/**
* Gets the AnsInappropriate property
* @return int with the AnsInappropriate
*/
    public int getAnsInappropriate()
    {
        return getSemanticObject().getIntProperty(forumCat_ansInappropriate);
    }

/**
* Sets the AnsInappropriate property
* @param value long with the AnsInappropriate
*/
    public void setAnsInappropriate(int value)
    {
        getSemanticObject().setIntProperty(forumCat_ansInappropriate, value);
    }

/**
* Gets the AnsStatus property
* @return int with the AnsStatus
*/
    public int getAnsStatus()
    {
        return getSemanticObject().getIntProperty(forumCat_ansStatus);
    }

/**
* Sets the AnsStatus property
* @param value long with the AnsStatus
*/
    public void setAnsStatus(int value)
    {
        getSemanticObject().setIntProperty(forumCat_ansStatus, value);
    }

/**
* Gets the References property
* @return String with the References
*/
    public String getReferences()
    {
        return getSemanticObject().getProperty(forumCat_references);
    }

/**
* Sets the References property
* @param value long with the References
*/
    public void setReferences(String value)
    {
        getSemanticObject().setProperty(forumCat_references, value);
    }

/**
* Gets the Updated property
* @return java.util.Date with the Updated
*/
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

/**
* Sets the Updated property
* @param value long with the Updated
*/
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

/**
* Gets the Created property
* @return java.util.Date with the Created
*/
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

/**
* Sets the Created property
* @param value long with the Created
*/
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

/**
* Gets the AnsIsAppropiate property
* @return boolean with the AnsIsAppropiate
*/
    public boolean isAnsIsAppropiate()
    {
        return getSemanticObject().getBooleanProperty(forumCat_ansIsAppropiate);
    }

/**
* Sets the AnsIsAppropiate property
* @param value long with the AnsIsAppropiate
*/
    public void setAnsIsAppropiate(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_ansIsAppropiate, value);
    }

/**
* Gets the Answer property
* @return String with the Answer
*/
    public String getAnswer()
    {
        return getSemanticObject().getProperty(forumCat_answer);
    }

/**
* Sets the Answer property
* @param value long with the Answer
*/
    public void setAnswer(String value)
    {
        getSemanticObject().setProperty(forumCat_answer, value);
    }
   /**
   * Sets the value for the property Creator
   * @param value Creator to set
   */

    public void setCreator(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
        }else
        {
            removeCreator();
        }
    }
   /**
   * Remove the value for Creator property
   */

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

   /**
   * Gets the Creator
   * @return a org.semanticwb.model.User
   */
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
   /**
   * Sets the value for the property AnsQuestion
   * @param value AnsQuestion to set
   */

    public void setAnsQuestion(org.semanticwb.resources.sem.forumcat.Question value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(forumCat_ansQuestion, value.getSemanticObject());
        }else
        {
            removeAnsQuestion();
        }
    }
   /**
   * Remove the value for AnsQuestion property
   */

    public void removeAnsQuestion()
    {
        getSemanticObject().removeProperty(forumCat_ansQuestion);
    }

   /**
   * Gets the AnsQuestion
   * @return a org.semanticwb.resources.sem.forumcat.Question
   */
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

/**
* Gets the BestAnswer property
* @return boolean with the BestAnswer
*/
    public boolean isBestAnswer()
    {
        return getSemanticObject().getBooleanProperty(forumCat_bestAnswer);
    }

/**
* Sets the BestAnswer property
* @param value long with the BestAnswer
*/
    public void setBestAnswer(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_bestAnswer, value);
    }

/**
* Gets the AnsIrrelevant property
* @return int with the AnsIrrelevant
*/
    public int getAnsIrrelevant()
    {
        return getSemanticObject().getIntProperty(forumCat_ansIrrelevant);
    }

/**
* Sets the AnsIrrelevant property
* @param value long with the AnsIrrelevant
*/
    public void setAnsIrrelevant(int value)
    {
        getSemanticObject().setIntProperty(forumCat_ansIrrelevant, value);
    }
}
