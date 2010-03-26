package org.semanticwb.resources.sem.forumcat.base;


public abstract class SWBForumCategoryResourceBase extends org.semanticwb.model.base.GenericObjectBase 
{
       public static final org.semanticwb.platform.SemanticClass forumCat_Forum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#Forum");
       public static final org.semanticwb.platform.SemanticProperty forumCat_forum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#forum");
       public static final org.semanticwb.platform.SemanticClass forumCat_SWBForumCategoryResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#SWBForumCategoryResource");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#SWBForumCategoryResource");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.SWBForumCategoryResource> listSWBForumCategoryResources(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.SWBForumCategoryResource>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.SWBForumCategoryResource> listSWBForumCategoryResources()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.SWBForumCategoryResource>(it, true);
       }

       public static org.semanticwb.resources.sem.forumcat.SWBForumCategoryResource getSWBForumCategoryResource(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.resources.sem.forumcat.SWBForumCategoryResource)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.resources.sem.forumcat.SWBForumCategoryResource createSWBForumCategoryResource(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.resources.sem.forumcat.SWBForumCategoryResource)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeSWBForumCategoryResource(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasSWBForumCategoryResource(String id, org.semanticwb.model.SWBModel model)
       {
           return (getSWBForumCategoryResource(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.SWBForumCategoryResource> listSWBForumCategoryResourceByForum(org.semanticwb.resources.sem.forumcat.Forum forum,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.SWBForumCategoryResource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(forumCat_forum, forum.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.SWBForumCategoryResource> listSWBForumCategoryResourceByForum(org.semanticwb.resources.sem.forumcat.Forum forum)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.SWBForumCategoryResource> it=new org.semanticwb.model.GenericIterator(forum.getSemanticObject().getModel().listSubjects(forumCat_forum,forum.getSemanticObject()));
       return it;
   }
    }

    public SWBForumCategoryResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setForum(org.semanticwb.resources.sem.forumcat.Forum value)
    {
        getSemanticObject().setObjectProperty(forumCat_forum, value.getSemanticObject());
    }

    public void removeForum()
    {
        getSemanticObject().removeProperty(forumCat_forum);
    }


    public org.semanticwb.resources.sem.forumcat.Forum getForum()
    {
         org.semanticwb.resources.sem.forumcat.Forum ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(forumCat_forum);
         if(obj!=null)
         {
             ret=(org.semanticwb.resources.sem.forumcat.Forum)obj.createGenericInstance();
         }
         return ret;
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);
    }
}
