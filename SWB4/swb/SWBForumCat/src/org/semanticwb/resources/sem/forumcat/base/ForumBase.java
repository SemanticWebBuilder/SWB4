package org.semanticwb.resources.sem.forumcat.base;


public abstract class ForumBase extends org.semanticwb.model.SWBClass 
{
       public static final org.semanticwb.platform.SemanticClass forumCat_SWBForumCategoryResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#SWBForumCategoryResource");
       public static final org.semanticwb.platform.SemanticProperty forumCat_ForumResourceinv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#ForumResourceinv");
       public static final org.semanticwb.platform.SemanticClass forumCat_SWBForumCatResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#SWBForumCatResource");
       public static final org.semanticwb.platform.SemanticProperty forumCat_forumResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#forumResource");
       public static final org.semanticwb.platform.SemanticClass forumCat_Question=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#Question");
       public static final org.semanticwb.platform.SemanticProperty forumCat_hasQuestionInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#hasQuestionInv");
       public static final org.semanticwb.platform.SemanticClass forumCat_Forum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#Forum");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#Forum");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Forum> listForums(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Forum>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Forum> listForums()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Forum>(it, true);
       }

       public static org.semanticwb.resources.sem.forumcat.Forum createForum(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.resources.sem.forumcat.Forum.ClassMgr.createForum(String.valueOf(id), model);
       }

       public static org.semanticwb.resources.sem.forumcat.Forum getForum(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.resources.sem.forumcat.Forum)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.resources.sem.forumcat.Forum createForum(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.resources.sem.forumcat.Forum)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeForum(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasForum(String id, org.semanticwb.model.SWBModel model)
       {
           return (getForum(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Forum> listForumByForumResourceinv(org.semanticwb.resources.sem.forumcat.SWBForumCategoryResource forumresourceinv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Forum> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(forumCat_ForumResourceinv, forumresourceinv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Forum> listForumByForumResourceinv(org.semanticwb.resources.sem.forumcat.SWBForumCategoryResource forumresourceinv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Forum> it=new org.semanticwb.model.GenericIterator(forumresourceinv.getSemanticObject().getModel().listSubjects(forumCat_ForumResourceinv,forumresourceinv.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Forum> listForumByForumResource(org.semanticwb.resources.sem.forumcat.SWBForumCatResource forumresource,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Forum> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(forumCat_forumResource, forumresource.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Forum> listForumByForumResource(org.semanticwb.resources.sem.forumcat.SWBForumCatResource forumresource)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Forum> it=new org.semanticwb.model.GenericIterator(forumresource.getSemanticObject().getModel().listSubjects(forumCat_forumResource,forumresource.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Forum> listForumByQuestionInv(org.semanticwb.resources.sem.forumcat.Question hasquestioninv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Forum> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(forumCat_hasQuestionInv, hasquestioninv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.Forum> listForumByQuestionInv(org.semanticwb.resources.sem.forumcat.Question hasquestioninv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Forum> it=new org.semanticwb.model.GenericIterator(hasquestioninv.getSemanticObject().getModel().listSubjects(forumCat_hasQuestionInv,hasquestioninv.getSemanticObject()));
       return it;
   }
    }

    public ForumBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setForumResourceinv(org.semanticwb.resources.sem.forumcat.SWBForumCategoryResource value)
    {
        getSemanticObject().setObjectProperty(forumCat_ForumResourceinv, value.getSemanticObject());
    }

    public void removeForumResourceinv()
    {
        getSemanticObject().removeProperty(forumCat_ForumResourceinv);
    }


    public org.semanticwb.resources.sem.forumcat.SWBForumCategoryResource getForumResourceinv()
    {
         org.semanticwb.resources.sem.forumcat.SWBForumCategoryResource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(forumCat_ForumResourceinv);
         if(obj!=null)
         {
             ret=(org.semanticwb.resources.sem.forumcat.SWBForumCategoryResource)obj.createGenericInstance();
         }
         return ret;
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

    public org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question> listQuestionInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.Question>(getSemanticObject().listObjectProperties(forumCat_hasQuestionInv));
    }

    public boolean hasQuestionInv(org.semanticwb.resources.sem.forumcat.Question question)
    {
        if(question==null)return false;
        return getSemanticObject().hasObjectProperty(forumCat_hasQuestionInv,question.getSemanticObject());
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
