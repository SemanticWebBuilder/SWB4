package org.semanticwb.resources.sem.forumcat.base;


public abstract class CategorySubscriptionBase extends org.semanticwb.model.SWBClass 
{
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso.
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty forumCat_categoryUser=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#categoryUser");
   /**
   * Una Página Web es el elemento de SemanticWebBuilder a través del cual se estructura la información del portal.
   */
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty forumCat_categoryWebpage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#categoryWebpage");
    public static final org.semanticwb.platform.SemanticClass forumCat_CategorySubscription=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#CategorySubscription");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/SWBForumCategory#CategorySubscription");

    public static class ClassMgr
    {
       /**
       * Returns a list of CategorySubscription for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.resources.sem.forumcat.CategorySubscription
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.CategorySubscription> listCategorySubscriptions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.CategorySubscription>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.resources.sem.forumcat.CategorySubscription for all models
       * @return Iterator of org.semanticwb.resources.sem.forumcat.CategorySubscription
       */

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
       /**
       * Gets a org.semanticwb.resources.sem.forumcat.CategorySubscription
       * @param id Identifier for org.semanticwb.resources.sem.forumcat.CategorySubscription
       * @param model Model of the org.semanticwb.resources.sem.forumcat.CategorySubscription
       * @return A org.semanticwb.resources.sem.forumcat.CategorySubscription
       */
        public static org.semanticwb.resources.sem.forumcat.CategorySubscription getCategorySubscription(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.resources.sem.forumcat.CategorySubscription)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.resources.sem.forumcat.CategorySubscription
       * @param id Identifier for org.semanticwb.resources.sem.forumcat.CategorySubscription
       * @param model Model of the org.semanticwb.resources.sem.forumcat.CategorySubscription
       * @return A org.semanticwb.resources.sem.forumcat.CategorySubscription
       */
        public static org.semanticwb.resources.sem.forumcat.CategorySubscription createCategorySubscription(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.resources.sem.forumcat.CategorySubscription)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.resources.sem.forumcat.CategorySubscription
       * @param id Identifier for org.semanticwb.resources.sem.forumcat.CategorySubscription
       * @param model Model of the org.semanticwb.resources.sem.forumcat.CategorySubscription
       */
        public static void removeCategorySubscription(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.resources.sem.forumcat.CategorySubscription
       * @param id Identifier for org.semanticwb.resources.sem.forumcat.CategorySubscription
       * @param model Model of the org.semanticwb.resources.sem.forumcat.CategorySubscription
       * @return true if the org.semanticwb.resources.sem.forumcat.CategorySubscription exists, false otherwise
       */

        public static boolean hasCategorySubscription(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCategorySubscription(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.CategorySubscription with a determined CategoryUser
       * @param value CategoryUser of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.resources.sem.forumcat.CategorySubscription
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.CategorySubscription
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.CategorySubscription> listCategorySubscriptionByCategoryUser(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.CategorySubscription> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(forumCat_categoryUser, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.CategorySubscription with a determined CategoryUser
       * @param value CategoryUser of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.CategorySubscription
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.CategorySubscription> listCategorySubscriptionByCategoryUser(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.CategorySubscription> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(forumCat_categoryUser,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.CategorySubscription with a determined CategoryWebpage
       * @param value CategoryWebpage of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.resources.sem.forumcat.CategorySubscription
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.CategorySubscription
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.CategorySubscription> listCategorySubscriptionByCategoryWebpage(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.CategorySubscription> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(forumCat_categoryWebpage, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.resources.sem.forumcat.CategorySubscription with a determined CategoryWebpage
       * @param value CategoryWebpage of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.resources.sem.forumcat.CategorySubscription
       */

        public static java.util.Iterator<org.semanticwb.resources.sem.forumcat.CategorySubscription> listCategorySubscriptionByCategoryWebpage(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.resources.sem.forumcat.CategorySubscription> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(forumCat_categoryWebpage,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static CategorySubscriptionBase.ClassMgr getCategorySubscriptionClassMgr()
    {
        return new CategorySubscriptionBase.ClassMgr();
    }

   /**
   * Constructs a CategorySubscriptionBase with a SemanticObject
   * @param base The SemanticObject with the properties for the CategorySubscription
   */
    public CategorySubscriptionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property CategoryUser
   * @param value CategoryUser to set
   */

    public void setCategoryUser(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(forumCat_categoryUser, value.getSemanticObject());
        }else
        {
            removeCategoryUser();
        }
    }
   /**
   * Remove the value for CategoryUser property
   */

    public void removeCategoryUser()
    {
        getSemanticObject().removeProperty(forumCat_categoryUser);
    }

   /**
   * Gets the CategoryUser
   * @return a org.semanticwb.model.User
   */
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
   /**
   * Sets the value for the property CategoryWebpage
   * @param value CategoryWebpage to set
   */

    public void setCategoryWebpage(org.semanticwb.model.WebPage value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(forumCat_categoryWebpage, value.getSemanticObject());
        }else
        {
            removeCategoryWebpage();
        }
    }
   /**
   * Remove the value for CategoryWebpage property
   */

    public void removeCategoryWebpage()
    {
        getSemanticObject().removeProperty(forumCat_categoryWebpage);
    }

   /**
   * Gets the CategoryWebpage
   * @return a org.semanticwb.model.WebPage
   */
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
}
