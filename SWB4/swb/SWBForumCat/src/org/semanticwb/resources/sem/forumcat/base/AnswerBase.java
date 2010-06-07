package org.semanticwb.resources.sem.forumcat.base;


public abstract class AnswerBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty forumCat_ansStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#ansStatus");
    public static final org.semanticwb.platform.SemanticProperty forumCat_ansInappropriate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#ansInappropriate");
    public static final org.semanticwb.platform.SemanticProperty forumCat_answer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#answer");
    public static final org.semanticwb.platform.SemanticClass forumCat_Question=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#Question");
    public static final org.semanticwb.platform.SemanticProperty forumCat_ansQuestion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#ansQuestion");
    public static final org.semanticwb.platform.SemanticProperty forumCat_ansIsAppropiate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#ansIsAppropiate");
    public static final org.semanticwb.platform.SemanticProperty forumCat_references=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#references");
    public static final org.semanticwb.platform.SemanticProperty forumCat_attachements=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#attachements");
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

        public static org.semanticwb.resources.sem.forumcat.Answer createAnswer(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.resources.sem.forumcat.Answer.ClassMgr.createAnswer(String.valueOf(id), model);
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

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Answer> listAnswerByAnsQuestion(org.semanticwb.resources.sem.forumcat.Question value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Answer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(forumCat_ansQuestion, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Answer> listAnswerByAnsQuestion(org.semanticwb.resources.sem.forumcat.Question value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Answer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(forumCat_ansQuestion,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Answer> listAnswerByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Answer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Answer> listAnswerByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Answer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Answer> listAnswerByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Answer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Answer> listAnswerByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Answer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public AnswerBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public int getAnsStatus()
    {
        return getSemanticObject().getIntProperty(forumCat_ansStatus);
    }

    public void setAnsStatus(int value)
    {
        getSemanticObject().setIntProperty(forumCat_ansStatus, value);
    }

    public int getAnsInappropriate()
    {
        return getSemanticObject().getIntProperty(forumCat_ansInappropriate);
    }

    public void setAnsInappropriate(int value)
    {
        getSemanticObject().setIntProperty(forumCat_ansInappropriate, value);
    }

    public String getAnswer()
    {
        return getSemanticObject().getProperty(forumCat_answer);
    }

    public void setAnswer(String value)
    {
        getSemanticObject().setProperty(forumCat_answer, value);
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

    public boolean isAnsIsAppropiate()
    {
        return getSemanticObject().getBooleanProperty(forumCat_ansIsAppropiate);
    }

    public void setAnsIsAppropiate(boolean value)
    {
        getSemanticObject().setBooleanProperty(forumCat_ansIsAppropiate, value);
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

    public String getReferences()
    {
        return getSemanticObject().getProperty(forumCat_references);
    }

    public void setReferences(String value)
    {
        getSemanticObject().setProperty(forumCat_references, value);
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

    public String getAttachements()
    {
        return getSemanticObject().getProperty(forumCat_attachements);
    }

    public void setAttachements(String value)
    {
        getSemanticObject().setProperty(forumCat_attachements, value);
    }
}
