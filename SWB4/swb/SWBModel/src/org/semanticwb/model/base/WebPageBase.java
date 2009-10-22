package org.semanticwb.model.base;


public class WebPageBase extends org.semanticwb.model.Topic implements org.semanticwb.model.Rankable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.Traceable,org.semanticwb.model.Indexable,org.semanticwb.model.Activeable,org.semanticwb.model.Resourceable,org.semanticwb.model.RoleRefable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.Viewable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Undeleteable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Trashable,org.semanticwb.model.Referensable,org.semanticwb.model.Hiddenable,org.semanticwb.model.Filterable,org.semanticwb.model.Expirable
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticClass swb_UserGroupRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroupRef");
       public static final org.semanticwb.platform.SemanticProperty swb_hasUserGroupRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasUserGroupRef");
       public static final org.semanticwb.platform.SemanticClass swb_AssMember=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AssMember");
       public static final org.semanticwb.platform.SemanticProperty swb_hasAssMemberInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasAssMemberInv");
       public static final org.semanticwb.platform.SemanticProperty swb_reviews=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#reviews");
       public static final org.semanticwb.platform.SemanticProperty swb_rank=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#rank");
       public static final org.semanticwb.platform.SemanticProperty swb_webPageSortName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageSortName");
       public static final org.semanticwb.platform.SemanticProperty swb_tags=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#tags");
       public static final org.semanticwb.platform.SemanticProperty swb_webPageURLType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageURLType");
       public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
       public static final org.semanticwb.platform.SemanticProperty swb_hasWebPageVirtualParent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasWebPageVirtualParent");
       public static final org.semanticwb.platform.SemanticProperty swb_deleted=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#deleted");
       public static final org.semanticwb.platform.SemanticProperty swb_active=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#active");
       public static final org.semanticwb.platform.SemanticProperty swb_webPageTarget=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageTarget");
       public static final org.semanticwb.platform.SemanticProperty swb_hasWebPageVirtualChild=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasWebPageVirtualChild");
       public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
       public static final org.semanticwb.platform.SemanticClass swb_TemplateRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateRef");
       public static final org.semanticwb.platform.SemanticProperty swb_hasTemplateRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasTemplateRef");
       public static final org.semanticwb.platform.SemanticClass swb_PFlowRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowRef");
       public static final org.semanticwb.platform.SemanticProperty swb_hasPFlowRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasPFlowRef");
       public static final org.semanticwb.platform.SemanticProperty swb_hasWebPageChild=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasWebPageChild");
       public static final org.semanticwb.platform.SemanticClass swb_CalendarRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#CalendarRef");
       public static final org.semanticwb.platform.SemanticProperty swb_hasCalendarRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasCalendarRef");
       public static final org.semanticwb.platform.SemanticProperty swb_webPageParent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageParent");
       public static final org.semanticwb.platform.SemanticProperty swb_undeleteable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#undeleteable");
       public static final org.semanticwb.platform.SemanticProperty swb_maxViews=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#maxViews");
       public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
       public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
       public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
       public static final org.semanticwb.platform.SemanticProperty swb_expiration=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#expiration");
       public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
       public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
       public static final org.semanticwb.platform.SemanticProperty swb_hasResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasResource");
       public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
       public static final org.semanticwb.platform.SemanticProperty swb_hidden=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hidden");
       public static final org.semanticwb.platform.SemanticProperty swb_indexable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#indexable");
       public static final org.semanticwb.platform.SemanticProperty swb_webPageDiskUsage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageDiskUsage");
       public static final org.semanticwb.platform.SemanticClass swb_RoleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RoleRef");
       public static final org.semanticwb.platform.SemanticProperty swb_hasRoleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasRoleRef");
       public static final org.semanticwb.platform.SemanticProperty swb_hasThisRoleAssMemberInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasThisRoleAssMemberInv");
       public static final org.semanticwb.platform.SemanticProperty swb_views=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#views");
       public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
       public static final org.semanticwb.platform.SemanticClass swb_RuleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RuleRef");
       public static final org.semanticwb.platform.SemanticProperty swb_hasRuleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasRuleRef");
       public static final org.semanticwb.platform.SemanticProperty swb_webPageURL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageURL");
       public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
       public static final org.semanticwb.platform.SemanticClass swb_Association=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Association");
       public static final org.semanticwb.platform.SemanticProperty swb_hasThisTypeAssociationInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasThisTypeAssociationInv");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");

       public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPages(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPages()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage>(it, true);
       }

       public static org.semanticwb.model.WebPage getWebPage(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.model.WebPage)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.model.WebPage createWebPage(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.model.WebPage)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeWebPage(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasWebPage(String id, org.semanticwb.model.SWBModel model)
       {
           return (getWebPage(id, model)!=null);
       }
    }

    public WebPageBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef> listUserGroupRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef>(getSemanticObject().listObjectProperties(ClassMgr.swb_hasUserGroupRef));
    }

    public boolean hasUserGroupRef(org.semanticwb.model.UserGroupRef usergroupref)
    {
        if(usergroupref==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swb_hasUserGroupRef,usergroupref.getSemanticObject());
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef> listInheritUserGroupRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef>(getSemanticObject().listInheritProperties(ClassMgr.swb_hasUserGroupRef));
    }

    public void addUserGroupRef(org.semanticwb.model.UserGroupRef value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swb_hasUserGroupRef, value.getSemanticObject());
    }

    public void removeAllUserGroupRef()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_hasUserGroupRef);
    }

    public void removeUserGroupRef(org.semanticwb.model.UserGroupRef usergroupref)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swb_hasUserGroupRef,usergroupref.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByUserGroupRef(org.semanticwb.model.UserGroupRef hasusergroupref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasUserGroupRef, hasusergroupref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByUserGroupRef(org.semanticwb.model.UserGroupRef hasusergroupref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(hasusergroupref.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasUserGroupRef,hasusergroupref.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.UserGroupRef getUserGroupRef()
    {
         org.semanticwb.model.UserGroupRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_hasUserGroupRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserGroupRef)obj.createGenericInstance();
         }
         return ret;
    }

    public long getReviews()
    {
        return getSemanticObject().getLongProperty(ClassMgr.swb_reviews);
    }

    public void setReviews(long value)
    {
        getSemanticObject().setLongProperty(ClassMgr.swb_reviews, value);
    }

    public double getRank()
    {
        return getSemanticObject().getDoubleProperty(ClassMgr.swb_rank);
    }

    public void setRank(double value)
    {
        getSemanticObject().setDoubleProperty(ClassMgr.swb_rank, value);
    }

    public String getSortName()
    {
        return getSemanticObject().getProperty(ClassMgr.swb_webPageSortName);
    }

    public void setSortName(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swb_webPageSortName, value);
    }

    public int getWebPageURLType()
    {
        return getSemanticObject().getIntProperty(ClassMgr.swb_webPageURLType);
    }

    public void setWebPageURLType(int value)
    {
        getSemanticObject().setIntProperty(ClassMgr.swb_webPageURLType, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> listVirtualParents()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage>(getSemanticObject().listObjectProperties(ClassMgr.swb_hasWebPageVirtualParent));
    }

    public boolean hasVirtualParent(org.semanticwb.model.WebPage webpage)
    {
        if(webpage==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swb_hasWebPageVirtualParent,webpage.getSemanticObject());
    }

    public void addVirtualParent(org.semanticwb.model.WebPage value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swb_hasWebPageVirtualParent, value.getSemanticObject());
    }

    public void removeAllVirtualParent()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_hasWebPageVirtualParent);
    }

    public void removeVirtualParent(org.semanticwb.model.WebPage webpage)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swb_hasWebPageVirtualParent,webpage.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByVirtualParent(org.semanticwb.model.WebPage haswebpagevirtualparent,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasWebPageVirtualParent, haswebpagevirtualparent.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByVirtualParent(org.semanticwb.model.WebPage haswebpagevirtualparent)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(haswebpagevirtualparent.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasWebPageVirtualParent,haswebpagevirtualparent.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.WebPage getVirtualParent()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_hasWebPageVirtualParent);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(ClassMgr.swb_deleted);
    }

    public void setDeleted(boolean value)
    {
        getSemanticObject().setBooleanProperty(ClassMgr.swb_deleted, value);
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(ClassMgr.swb_active);
    }

    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(ClassMgr.swb_active, value);
    }

    public String getTarget()
    {
        return getSemanticObject().getProperty(ClassMgr.swb_webPageTarget);
    }

    public void setTarget(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swb_webPageTarget, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> listWebPageVirtualChilds()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage>(getSemanticObject().listObjectProperties(ClassMgr.swb_hasWebPageVirtualChild));
    }

    public boolean hasWebPageVirtualChild(org.semanticwb.model.WebPage webpage)
    {
        if(webpage==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swb_hasWebPageVirtualChild,webpage.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByWebPageVirtualChild(org.semanticwb.model.WebPage haswebpagevirtualchild,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasWebPageVirtualChild, haswebpagevirtualchild.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByWebPageVirtualChild(org.semanticwb.model.WebPage haswebpagevirtualchild)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(haswebpagevirtualchild.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasWebPageVirtualChild,haswebpagevirtualchild.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.WebPage getWebPageVirtualChild()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_hasWebPageVirtualChild);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef> listTemplateRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef>(getSemanticObject().listObjectProperties(ClassMgr.swb_hasTemplateRef));
    }

    public boolean hasTemplateRef(org.semanticwb.model.TemplateRef templateref)
    {
        if(templateref==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swb_hasTemplateRef,templateref.getSemanticObject());
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef> listInheritTemplateRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef>(getSemanticObject().listInheritProperties(ClassMgr.swb_hasTemplateRef));
    }

    public void addTemplateRef(org.semanticwb.model.TemplateRef value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swb_hasTemplateRef, value.getSemanticObject());
    }

    public void removeAllTemplateRef()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_hasTemplateRef);
    }

    public void removeTemplateRef(org.semanticwb.model.TemplateRef templateref)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swb_hasTemplateRef,templateref.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByTemplateRef(org.semanticwb.model.TemplateRef hastemplateref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasTemplateRef, hastemplateref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByTemplateRef(org.semanticwb.model.TemplateRef hastemplateref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(hastemplateref.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasTemplateRef,hastemplateref.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.TemplateRef getTemplateRef()
    {
         org.semanticwb.model.TemplateRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_hasTemplateRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.TemplateRef)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef> listPFlowRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef>(getSemanticObject().listObjectProperties(ClassMgr.swb_hasPFlowRef));
    }

    public boolean hasPFlowRef(org.semanticwb.model.PFlowRef pflowref)
    {
        if(pflowref==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swb_hasPFlowRef,pflowref.getSemanticObject());
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef> listInheritPFlowRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef>(getSemanticObject().listInheritProperties(ClassMgr.swb_hasPFlowRef));
    }

    public void addPFlowRef(org.semanticwb.model.PFlowRef value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swb_hasPFlowRef, value.getSemanticObject());
    }

    public void removeAllPFlowRef()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_hasPFlowRef);
    }

    public void removePFlowRef(org.semanticwb.model.PFlowRef pflowref)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swb_hasPFlowRef,pflowref.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByPFlowRef(org.semanticwb.model.PFlowRef haspflowref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasPFlowRef, haspflowref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByPFlowRef(org.semanticwb.model.PFlowRef haspflowref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(haspflowref.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasPFlowRef,haspflowref.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.PFlowRef getPFlowRef()
    {
         org.semanticwb.model.PFlowRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_hasPFlowRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.PFlowRef)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> listChilds()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage>(getSemanticObject().listObjectProperties(ClassMgr.swb_hasWebPageChild));
    }

    public boolean hasChild(org.semanticwb.model.WebPage webpage)
    {
        if(webpage==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swb_hasWebPageChild,webpage.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByChild(org.semanticwb.model.WebPage haswebpagechild,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasWebPageChild, haswebpagechild.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByChild(org.semanticwb.model.WebPage haswebpagechild)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(haswebpagechild.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasWebPageChild,haswebpagechild.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.WebPage getChild()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_hasWebPageChild);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef> listCalendarRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef>(getSemanticObject().listObjectProperties(ClassMgr.swb_hasCalendarRef));
    }

    public boolean hasCalendarRef(org.semanticwb.model.CalendarRef calendarref)
    {
        if(calendarref==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swb_hasCalendarRef,calendarref.getSemanticObject());
    }

    public void addCalendarRef(org.semanticwb.model.CalendarRef value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swb_hasCalendarRef, value.getSemanticObject());
    }

    public void removeAllCalendarRef()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_hasCalendarRef);
    }

    public void removeCalendarRef(org.semanticwb.model.CalendarRef calendarref)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swb_hasCalendarRef,calendarref.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByCalendarRef(org.semanticwb.model.CalendarRef hascalendarref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasCalendarRef, hascalendarref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByCalendarRef(org.semanticwb.model.CalendarRef hascalendarref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(hascalendarref.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasCalendarRef,hascalendarref.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.CalendarRef getCalendarRef()
    {
         org.semanticwb.model.CalendarRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_hasCalendarRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.CalendarRef)obj.createGenericInstance();
         }
         return ret;
    }

    public void setParent(org.semanticwb.model.WebPage value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swb_webPageParent, value.getSemanticObject());
    }

    public void removeParent()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_webPageParent);
    }

   public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByParent(org.semanticwb.model.WebPage webpageparent,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_webPageParent, webpageparent.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByParent(org.semanticwb.model.WebPage webpageparent)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(webpageparent.getSemanticObject().getModel().listSubjects(ClassMgr.swb_webPageParent,webpageparent.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.WebPage getParent()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_webPageParent);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isUndeleteable()
    {
        return getSemanticObject().getBooleanProperty(ClassMgr.swb_undeleteable);
    }

    public void setUndeleteable(boolean value)
    {
        getSemanticObject().setBooleanProperty(ClassMgr.swb_undeleteable, value);
    }

    public long getMaxViews()
    {
        return getSemanticObject().getLongProperty(ClassMgr.swb_maxViews);
    }

    public void setMaxViews(long value)
    {
        getSemanticObject().setLongProperty(ClassMgr.swb_maxViews, value);
    }

    public java.util.Date getExpiration()
    {
        return getSemanticObject().getDateProperty(ClassMgr.swb_expiration);
    }

    public void setExpiration(java.util.Date value)
    {
        getSemanticObject().setDateProperty(ClassMgr.swb_expiration, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> listResources()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource>(getSemanticObject().listObjectProperties(ClassMgr.swb_hasResource));
    }

    public boolean hasResource(org.semanticwb.model.Resource resource)
    {
        if(resource==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swb_hasResource,resource.getSemanticObject());
    }

    public void addResource(org.semanticwb.model.Resource value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swb_hasResource, value.getSemanticObject());
    }

    public void removeAllResource()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_hasResource);
    }

    public void removeResource(org.semanticwb.model.Resource resource)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swb_hasResource,resource.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByResource(org.semanticwb.model.Resource hasresource,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasResource, hasresource.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByResource(org.semanticwb.model.Resource hasresource)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(hasresource.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasResource,hasresource.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.Resource getResource()
    {
         org.semanticwb.model.Resource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_hasResource);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resource)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isHidden()
    {
        return getSemanticObject().getBooleanProperty(ClassMgr.swb_hidden);
    }

    public void setHidden(boolean value)
    {
        getSemanticObject().setBooleanProperty(ClassMgr.swb_hidden, value);
    }

    public long getDiskUsage()
    {
        //Override this method in WebPage object
        return getSemanticObject().getLongProperty(ClassMgr.swb_webPageDiskUsage,false);
    }

    public void setDiskUsage(long value)
    {
        //Override this method in WebPage object
        getSemanticObject().setLongProperty(ClassMgr.swb_webPageDiskUsage, value,false);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> listRoleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef>(getSemanticObject().listObjectProperties(ClassMgr.swb_hasRoleRef));
    }

    public boolean hasRoleRef(org.semanticwb.model.RoleRef roleref)
    {
        if(roleref==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swb_hasRoleRef,roleref.getSemanticObject());
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> listInheritRoleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef>(getSemanticObject().listInheritProperties(ClassMgr.swb_hasRoleRef));
    }

    public void addRoleRef(org.semanticwb.model.RoleRef value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swb_hasRoleRef, value.getSemanticObject());
    }

    public void removeAllRoleRef()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_hasRoleRef);
    }

    public void removeRoleRef(org.semanticwb.model.RoleRef roleref)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swb_hasRoleRef,roleref.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByRoleRef(org.semanticwb.model.RoleRef hasroleref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasRoleRef, hasroleref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByRoleRef(org.semanticwb.model.RoleRef hasroleref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(hasroleref.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasRoleRef,hasroleref.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.RoleRef getRoleRef()
    {
         org.semanticwb.model.RoleRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_hasRoleRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.RoleRef)obj.createGenericInstance();
         }
         return ret;
    }

    public long getViews()
    {
        //Override this method in WebPage object
        return getSemanticObject().getLongProperty(ClassMgr.swb_views,false);
    }

    public void setViews(long value)
    {
        //Override this method in WebPage object
        getSemanticObject().setLongProperty(ClassMgr.swb_views, value,false);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef> listRuleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef>(getSemanticObject().listObjectProperties(ClassMgr.swb_hasRuleRef));
    }

    public boolean hasRuleRef(org.semanticwb.model.RuleRef ruleref)
    {
        if(ruleref==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swb_hasRuleRef,ruleref.getSemanticObject());
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef> listInheritRuleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef>(getSemanticObject().listInheritProperties(ClassMgr.swb_hasRuleRef));
    }

    public void addRuleRef(org.semanticwb.model.RuleRef value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swb_hasRuleRef, value.getSemanticObject());
    }

    public void removeAllRuleRef()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_hasRuleRef);
    }

    public void removeRuleRef(org.semanticwb.model.RuleRef ruleref)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swb_hasRuleRef,ruleref.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByRuleRef(org.semanticwb.model.RuleRef hasruleref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasRuleRef, hasruleref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByRuleRef(org.semanticwb.model.RuleRef hasruleref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(hasruleref.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasRuleRef,hasruleref.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.RuleRef getRuleRef()
    {
         org.semanticwb.model.RuleRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_hasRuleRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.RuleRef)obj.createGenericInstance();
         }
         return ret;
    }

    public String getWebPageURL()
    {
        return getSemanticObject().getProperty(ClassMgr.swb_webPageURL);
    }

    public void setWebPageURL(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swb_webPageURL, value);
    }

    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
