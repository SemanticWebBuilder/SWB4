package org.semanticwb.model.base;


public abstract class WebPageBase extends org.semanticwb.model.Topic implements org.semanticwb.model.Filterable,org.semanticwb.model.Trashable,org.semanticwb.model.Activeable,org.semanticwb.model.Expirable,org.semanticwb.model.Traceable,org.semanticwb.model.Resourceable,org.semanticwb.model.Tagable,org.semanticwb.model.Localeable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Undeleteable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.Viewable,org.semanticwb.model.Indexable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Hiddenable,org.semanticwb.model.Searchable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Rankable,org.semanticwb.model.Referensable,org.semanticwb.model.FilterableClass,org.semanticwb.model.RuleRefable,org.semanticwb.model.UserGroupRefable
{
    public static final org.semanticwb.platform.SemanticProperty swb_webPageSortName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageSortName");
    public static final org.semanticwb.platform.SemanticProperty swb_webPageURLType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageURLType");
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty swb_hasWebPageVirtualParent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasWebPageVirtualParent");
    public static final org.semanticwb.platform.SemanticProperty swb_webPageTarget=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageTarget");
    public static final org.semanticwb.platform.SemanticProperty swb_hasWebPageVirtualChild=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasWebPageVirtualChild");
    public static final org.semanticwb.platform.SemanticProperty swb_webPageIconClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageIconClass");
    public static final org.semanticwb.platform.SemanticProperty swb_hasWebPageChild=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasWebPageChild");
    public static final org.semanticwb.platform.SemanticProperty swb_webPageParent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageParent");
    public static final org.semanticwb.platform.SemanticProperty swb_webPageDiskUsage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageDiskUsage");
    public static final org.semanticwb.platform.SemanticProperty swb_webPageURL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageURL");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");

    public static class ClassMgr
    {

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
            return (org.semanticwb.model.WebPage)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeWebPage(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasWebPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (getWebPage(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByVirtualParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByVirtualParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByWebPageVirtualChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByWebPageVirtualChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByTemplateRef(org.semanticwb.model.TemplateRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByTemplateRef(org.semanticwb.model.TemplateRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByPFlowRef(org.semanticwb.model.PFlowRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByPFlowRef(org.semanticwb.model.PFlowRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByThisRoleAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByThisRoleAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByThisTypeAssociation(org.semanticwb.model.Association value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByThisTypeAssociation(org.semanticwb.model.Association value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public WebPageBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef> listUserGroupRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef>(getSemanticObject().listObjectProperties(swb_hasUserGroupRef));
    }

    public boolean hasUserGroupRef(org.semanticwb.model.UserGroupRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasUserGroupRef,value.getSemanticObject());
        }
        return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef> listInheritUserGroupRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef>(getSemanticObject().listInheritProperties(swb_hasUserGroupRef));
    }

    public void addUserGroupRef(org.semanticwb.model.UserGroupRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasUserGroupRef, value.getSemanticObject());
    }

    public void removeAllUserGroupRef()
    {
        getSemanticObject().removeProperty(swb_hasUserGroupRef);
    }

    public void removeUserGroupRef(org.semanticwb.model.UserGroupRef value)
    {
        getSemanticObject().removeObjectProperty(swb_hasUserGroupRef,value.getSemanticObject());
    }

    public org.semanticwb.model.UserGroupRef getUserGroupRef()
    {
         org.semanticwb.model.UserGroupRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasUserGroupRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserGroupRef)obj.createGenericInstance();
         }
         return ret;
    }

    public long getReviews()
    {
        return getSemanticObject().getLongProperty(swb_reviews);
    }

    public void setReviews(long value)
    {
        getSemanticObject().setLongProperty(swb_reviews, value);
    }

    public double getRank()
    {
        return getSemanticObject().getDoubleProperty(swb_rank);
    }

    public void setRank(double value)
    {
        getSemanticObject().setDoubleProperty(swb_rank, value);
    }

    public String getSortName()
    {
        return getSemanticObject().getProperty(swb_webPageSortName);
    }

    public void setSortName(String value)
    {
        getSemanticObject().setProperty(swb_webPageSortName, value);
    }

    public int getWebPageURLType()
    {
        return getSemanticObject().getIntProperty(swb_webPageURLType);
    }

    public void setWebPageURLType(int value)
    {
        getSemanticObject().setIntProperty(swb_webPageURLType, value);
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_active, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> listVirtualParents()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage>(getSemanticObject().listObjectProperties(swb_hasWebPageVirtualParent));
    }

    public boolean hasVirtualParent(org.semanticwb.model.WebPage value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasWebPageVirtualParent,value.getSemanticObject());
        }
        return ret;
    }

    public void addVirtualParent(org.semanticwb.model.WebPage value)
    {
        getSemanticObject().addObjectProperty(swb_hasWebPageVirtualParent, value.getSemanticObject());
    }

    public void removeAllVirtualParent()
    {
        getSemanticObject().removeProperty(swb_hasWebPageVirtualParent);
    }

    public void removeVirtualParent(org.semanticwb.model.WebPage value)
    {
        getSemanticObject().removeObjectProperty(swb_hasWebPageVirtualParent,value.getSemanticObject());
    }

    public org.semanticwb.model.WebPage getVirtualParent()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasWebPageVirtualParent);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(swb_deleted);
    }

    public void setDeleted(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_deleted, value);
    }

    public String getTarget()
    {
        return getSemanticObject().getProperty(swb_webPageTarget);
    }

    public void setTarget(String value)
    {
        getSemanticObject().setProperty(swb_webPageTarget, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> listWebPageVirtualChilds()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage>(getSemanticObject().listObjectProperties(swb_hasWebPageVirtualChild));
    }

    public boolean hasWebPageVirtualChild(org.semanticwb.model.WebPage value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasWebPageVirtualChild,value.getSemanticObject());
        }
        return ret;
    }

    public org.semanticwb.model.WebPage getWebPageVirtualChild()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasWebPageVirtualChild);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    public String getIconClass()
    {
        return getSemanticObject().getProperty(swb_webPageIconClass);
    }

    public void setIconClass(String value)
    {
        getSemanticObject().setProperty(swb_webPageIconClass, value);
    }

    public void setLanguage(org.semanticwb.model.Language value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_language, value.getSemanticObject());
        }else
        {
            removeLanguage();
        }
    }

    public void removeLanguage()
    {
        getSemanticObject().removeProperty(swb_language);
    }

    public org.semanticwb.model.Language getLanguage()
    {
         org.semanticwb.model.Language ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_language);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Language)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef> listTemplateRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef>(getSemanticObject().listObjectProperties(swb_hasTemplateRef));
    }

    public boolean hasTemplateRef(org.semanticwb.model.TemplateRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasTemplateRef,value.getSemanticObject());
        }
        return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef> listInheritTemplateRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef>(getSemanticObject().listInheritProperties(swb_hasTemplateRef));
    }

    public void addTemplateRef(org.semanticwb.model.TemplateRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasTemplateRef, value.getSemanticObject());
    }

    public void removeAllTemplateRef()
    {
        getSemanticObject().removeProperty(swb_hasTemplateRef);
    }

    public void removeTemplateRef(org.semanticwb.model.TemplateRef value)
    {
        getSemanticObject().removeObjectProperty(swb_hasTemplateRef,value.getSemanticObject());
    }

    public org.semanticwb.model.TemplateRef getTemplateRef()
    {
         org.semanticwb.model.TemplateRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasTemplateRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.TemplateRef)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef> listPFlowRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef>(getSemanticObject().listObjectProperties(swb_hasPFlowRef));
    }

    public boolean hasPFlowRef(org.semanticwb.model.PFlowRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasPFlowRef,value.getSemanticObject());
        }
        return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef> listInheritPFlowRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef>(getSemanticObject().listInheritProperties(swb_hasPFlowRef));
    }

    public void addPFlowRef(org.semanticwb.model.PFlowRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasPFlowRef, value.getSemanticObject());
    }

    public void removeAllPFlowRef()
    {
        getSemanticObject().removeProperty(swb_hasPFlowRef);
    }

    public void removePFlowRef(org.semanticwb.model.PFlowRef value)
    {
        getSemanticObject().removeObjectProperty(swb_hasPFlowRef,value.getSemanticObject());
    }

    public org.semanticwb.model.PFlowRef getPFlowRef()
    {
         org.semanticwb.model.PFlowRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasPFlowRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.PFlowRef)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> listChilds()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage>(getSemanticObject().listObjectProperties(swb_hasWebPageChild));
    }

    public boolean hasChild(org.semanticwb.model.WebPage value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasWebPageChild,value.getSemanticObject());
        }
        return ret;
    }

    public org.semanticwb.model.WebPage getChild()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasWebPageChild);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef> listCalendarRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef>(getSemanticObject().listObjectProperties(swb_hasCalendarRef));
    }

    public boolean hasCalendarRef(org.semanticwb.model.CalendarRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasCalendarRef,value.getSemanticObject());
        }
        return ret;
    }

    public void addCalendarRef(org.semanticwb.model.CalendarRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasCalendarRef, value.getSemanticObject());
    }

    public void removeAllCalendarRef()
    {
        getSemanticObject().removeProperty(swb_hasCalendarRef);
    }

    public void removeCalendarRef(org.semanticwb.model.CalendarRef value)
    {
        getSemanticObject().removeObjectProperty(swb_hasCalendarRef,value.getSemanticObject());
    }

    public org.semanticwb.model.CalendarRef getCalendarRef()
    {
         org.semanticwb.model.CalendarRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasCalendarRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.CalendarRef)obj.createGenericInstance();
         }
         return ret;
    }

    public void setParent(org.semanticwb.model.WebPage value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_webPageParent, value.getSemanticObject());
        }else
        {
            removeParent();
        }
    }

    public void removeParent()
    {
        getSemanticObject().removeProperty(swb_webPageParent);
    }

    public org.semanticwb.model.WebPage getParent()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_webPageParent);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isUndeleteable()
    {
        return getSemanticObject().getBooleanProperty(swb_undeleteable);
    }

    public void setUndeleteable(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_undeleteable, value);
    }

    public long getMaxViews()
    {
        return getSemanticObject().getLongProperty(swb_maxViews);
    }

    public void setMaxViews(long value)
    {
        getSemanticObject().setLongProperty(swb_maxViews, value);
    }

    public java.util.Date getExpiration()
    {
        return getSemanticObject().getDateProperty(swb_expiration);
    }

    public void setExpiration(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_expiration, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> listResources()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource>(getSemanticObject().listObjectProperties(swb_hasResource));
    }

    public boolean hasResource(org.semanticwb.model.Resource value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasResource,value.getSemanticObject());
        }
        return ret;
    }

    public void addResource(org.semanticwb.model.Resource value)
    {
        getSemanticObject().addObjectProperty(swb_hasResource, value.getSemanticObject());
    }

    public void removeAllResource()
    {
        getSemanticObject().removeProperty(swb_hasResource);
    }

    public void removeResource(org.semanticwb.model.Resource value)
    {
        getSemanticObject().removeObjectProperty(swb_hasResource,value.getSemanticObject());
    }

    public org.semanticwb.model.Resource getResource()
    {
         org.semanticwb.model.Resource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasResource);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resource)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isHidden()
    {
        return getSemanticObject().getBooleanProperty(swb_hidden);
    }

    public void setHidden(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_hidden, value);
    }

    public boolean isIndexable()
    {
        return getSemanticObject().getBooleanProperty(swb_indexable);
    }

    public void setIndexable(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_indexable, value);
    }

    public long getDiskUsage()
    {
        //Override this method in WebPage object
        return getSemanticObject().getLongProperty(swb_webPageDiskUsage,false);
    }

    public void setDiskUsage(long value)
    {
        //Override this method in WebPage object
        getSemanticObject().setLongProperty(swb_webPageDiskUsage, value,false);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> listRoleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef>(getSemanticObject().listObjectProperties(swb_hasRoleRef));
    }

    public boolean hasRoleRef(org.semanticwb.model.RoleRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasRoleRef,value.getSemanticObject());
        }
        return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> listInheritRoleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef>(getSemanticObject().listInheritProperties(swb_hasRoleRef));
    }

    public void addRoleRef(org.semanticwb.model.RoleRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasRoleRef, value.getSemanticObject());
    }

    public void removeAllRoleRef()
    {
        getSemanticObject().removeProperty(swb_hasRoleRef);
    }

    public void removeRoleRef(org.semanticwb.model.RoleRef value)
    {
        getSemanticObject().removeObjectProperty(swb_hasRoleRef,value.getSemanticObject());
    }

    public org.semanticwb.model.RoleRef getRoleRef()
    {
         org.semanticwb.model.RoleRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasRoleRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.RoleRef)obj.createGenericInstance();
         }
         return ret;
    }

    public long getViews()
    {
        //Override this method in WebPage object
        return getSemanticObject().getLongProperty(swb_views,false);
    }

    public void setViews(long value)
    {
        //Override this method in WebPage object
        getSemanticObject().setLongProperty(swb_views, value,false);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef> listRuleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef>(getSemanticObject().listObjectProperties(swb_hasRuleRef));
    }

    public boolean hasRuleRef(org.semanticwb.model.RuleRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasRuleRef,value.getSemanticObject());
        }
        return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef> listInheritRuleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef>(getSemanticObject().listInheritProperties(swb_hasRuleRef));
    }

    public void addRuleRef(org.semanticwb.model.RuleRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasRuleRef, value.getSemanticObject());
    }

    public void removeAllRuleRef()
    {
        getSemanticObject().removeProperty(swb_hasRuleRef);
    }

    public void removeRuleRef(org.semanticwb.model.RuleRef value)
    {
        getSemanticObject().removeObjectProperty(swb_hasRuleRef,value.getSemanticObject());
    }

    public org.semanticwb.model.RuleRef getRuleRef()
    {
         org.semanticwb.model.RuleRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasRuleRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.RuleRef)obj.createGenericInstance();
         }
         return ret;
    }

    public String getWebPageURL()
    {
        return getSemanticObject().getProperty(swb_webPageURL);
    }

    public void setWebPageURL(String value)
    {
        getSemanticObject().setProperty(swb_webPageURL, value);
    }

    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
