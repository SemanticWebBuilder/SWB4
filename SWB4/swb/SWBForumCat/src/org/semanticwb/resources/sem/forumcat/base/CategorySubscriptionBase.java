package org.semanticwb.resources.sem.forumcat.base;


public abstract class CategorySubscriptionBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty forumCat_categoryWebpage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#categoryWebpage");
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty forumCat_categoryUser=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#categoryUser");
    public static final org.semanticwb.platform.SemanticClass forumCat_CategorySubscription=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#CategorySubscription");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#CategorySubscription");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.CategorySubscription> listCategorySubscriptions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.CategorySubscription>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.CategorySubscription> listCategorySubscriptions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.CategorySubscription>(it, true);
        }

        public static org.semanticwb.resources.sem.forumcat.CategorySubscription createCategorySubscription(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.resources.sem.forumcat.CategorySubscription.ClassMgr.createCategorySubscription(String.valueOf(id), model);
        }

        public static org.semanticwb.resources.sem.forumcat.CategorySubscription getCategorySubscription(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.resources.sem.forumcat.CategorySubscription)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.resources.sem.forumcat.CategorySubscription createCategorySubscription(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.resources.sem.forumcat.CategorySubscription)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeCategorySubscription(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasCategorySubscription(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCategorySubscription(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.CategorySubscription> listCategorySubscriptionByCategoryWebpage(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.CategorySubscription> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(forumCat_categoryWebpage, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.CategorySubscription> listCategorySubscriptionByCategoryWebpage(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.CategorySubscription> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(forumCat_categoryWebpage,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.CategorySubscription> listCategorySubscriptionByCategoryUser(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.CategorySubscription> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(forumCat_categoryUser, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.CategorySubscription> listCategorySubscriptionByCategoryUser(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.CategorySubscription> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(forumCat_categoryUser,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public CategorySubscriptionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setCategoryWebpage(org.semanticwb.model.WebPage value)
    {
        getSemanticObject().setObjectProperty(forumCat_categoryWebpage, value.getSemanticObject());
    }

    public void removeCategoryWebpage()
    {
        getSemanticObject().removeProperty(forumCat_categoryWebpage);
    }

    public org.semanticwb.model.WebPage getCategoryWebpage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(forumCat_categoryWebpage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    public void setCategoryUser(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(forumCat_categoryUser, value.getSemanticObject());
    }

    public void removeCategoryUser()
    {
        getSemanticObject().removeProperty(forumCat_categoryUser);
    }

    public org.semanticwb.model.User getCategoryUser()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(forumCat_categoryUser);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }
}
