package org.semanticwb.portal.community.base;


public class MicroSiteBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.CalendarRefable,org.semanticwb.model.Rankable,org.semanticwb.model.Filterable,org.semanticwb.model.Expirable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.model.Indexable,org.semanticwb.model.Activeable,org.semanticwb.model.Resourceable,org.semanticwb.model.Referensable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Viewable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Trashable,org.semanticwb.model.Hiddenable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.RoleRefable,org.semanticwb.model.TemplateRefable
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticClass swb_UserGroupRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroupRef");
       public static final org.semanticwb.platform.SemanticProperty swb_hasUserGroupRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasUserGroupRef");
       public static final org.semanticwb.platform.SemanticClass swb_AssMember=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AssMember");
       public static final org.semanticwb.platform.SemanticProperty swb_hasAssMemberInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasAssMemberInv");
       public static final org.semanticwb.platform.SemanticProperty swb_reviews=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#reviews");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_moderate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#moderate");
       public static final org.semanticwb.platform.SemanticProperty swb_rank=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#rank");
       public static final org.semanticwb.platform.SemanticProperty swb_webPageSortName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageSortName");
       public static final org.semanticwb.platform.SemanticProperty swb_tags=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#tags");
       public static final org.semanticwb.platform.SemanticProperty swb_webPageURLType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageURLType");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_photo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#photo");
       public static final org.semanticwb.platform.SemanticProperty swb_active=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#active");
       public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
       public static final org.semanticwb.platform.SemanticProperty swb_hasWebPageVirtualParent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasWebPageVirtualParent");
       public static final org.semanticwb.platform.SemanticProperty swb_deleted=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#deleted");
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
       public static final org.semanticwb.platform.SemanticProperty swbcomm_private=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#private");
       public static final org.semanticwb.platform.SemanticProperty swb_undeleteable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#undeleteable");
       public static final org.semanticwb.platform.SemanticProperty swb_maxViews=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#maxViews");
       public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteWebPageUtil=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteWebPageUtil");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_hasMicroSiteUtilsInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasMicroSiteUtilsInv");
       public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
       public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
       public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
       public static final org.semanticwb.platform.SemanticProperty swb_expiration=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#expiration");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_about=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#about");
       public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
       public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
       public static final org.semanticwb.platform.SemanticProperty swb_hasResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasResource");
       public static final org.semanticwb.platform.SemanticClass swbcomm_Member=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Member");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_hasMSMembersInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasMSMembersInv");
       public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
       public static final org.semanticwb.platform.SemanticProperty swb_hidden=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hidden");
       public static final org.semanticwb.platform.SemanticProperty swb_indexable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#indexable");
       public static final org.semanticwb.platform.SemanticProperty swb_webPageDiskUsage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageDiskUsage");
       public static final org.semanticwb.platform.SemanticClass swb_RoleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RoleRef");
       public static final org.semanticwb.platform.SemanticProperty swb_hasRoleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasRoleRef");
       public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteType");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_type=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#type");
       public static final org.semanticwb.platform.SemanticProperty swb_hasThisRoleAssMemberInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasThisRoleAssMemberInv");
       public static final org.semanticwb.platform.SemanticProperty swb_views=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#views");
       public static final org.semanticwb.platform.SemanticClass swb_RuleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RuleRef");
       public static final org.semanticwb.platform.SemanticProperty swb_hasRuleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasRuleRef");
       public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
       public static final org.semanticwb.platform.SemanticProperty swb_webPageURL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageURL");
       public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
       public static final org.semanticwb.platform.SemanticClass swb_Association=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Association");
       public static final org.semanticwb.platform.SemanticProperty swb_hasThisTypeAssociationInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasThisTypeAssociationInv");
       public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSite");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSite");

       public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSites(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSites()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite>(it, true);
       }

       public static org.semanticwb.portal.community.MicroSite getMicroSite(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.portal.community.MicroSite)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.portal.community.MicroSite createMicroSite(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.portal.community.MicroSite)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeMicroSite(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasMicroSite(String id, org.semanticwb.model.SWBModel model)
       {
           return (getMicroSite(id, model)!=null);
       }
    }

    public MicroSiteBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public boolean isModerate()
    {
        return getSemanticObject().getBooleanProperty(ClassMgr.swbcomm_moderate);
    }

    public void setModerate(boolean value)
    {
        getSemanticObject().setBooleanProperty(ClassMgr.swbcomm_moderate, value);
    }

    public String getPhoto()
    {
        return getSemanticObject().getProperty(ClassMgr.swbcomm_photo);
    }

    public void setPhoto(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swbcomm_photo, value);
    }

    public boolean isPrivate()
    {
        return getSemanticObject().getBooleanProperty(ClassMgr.swbcomm_private);
    }

    public void setPrivate(boolean value)
    {
        getSemanticObject().setBooleanProperty(ClassMgr.swbcomm_private, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteWebPageUtil> listMicroSiteUtils()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteWebPageUtil>(getSemanticObject().listObjectProperties(ClassMgr.swbcomm_hasMicroSiteUtilsInv));
    }

    public boolean hasMicroSiteUtil(org.semanticwb.portal.community.MicroSiteWebPageUtil micrositewebpageutil)
    {
        if(micrositewebpageutil==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swbcomm_hasMicroSiteUtilsInv,micrositewebpageutil.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByMicroSiteUtil(org.semanticwb.portal.community.MicroSiteWebPageUtil hasmicrositeutilsinv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_hasMicroSiteUtilsInv, hasmicrositeutilsinv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByMicroSiteUtil(org.semanticwb.portal.community.MicroSiteWebPageUtil hasmicrositeutilsinv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(hasmicrositeutilsinv.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_hasMicroSiteUtilsInv,hasmicrositeutilsinv.getSemanticObject()));
       return it;
   }

    public org.semanticwb.portal.community.MicroSiteWebPageUtil getMicroSiteUtil()
    {
         org.semanticwb.portal.community.MicroSiteWebPageUtil ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbcomm_hasMicroSiteUtilsInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.MicroSiteWebPageUtil)obj.createGenericInstance();
         }
         return ret;
    }

    public void setAbout(org.semanticwb.model.WebPage value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swbcomm_about, value.getSemanticObject());
    }

    public void removeAbout()
    {
        getSemanticObject().removeProperty(ClassMgr.swbcomm_about);
    }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByAbout(org.semanticwb.model.WebPage about,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_about, about.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByAbout(org.semanticwb.model.WebPage about)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(about.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_about,about.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.WebPage getAbout()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbcomm_about);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Member> listMembers()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Member>(getSemanticObject().listObjectProperties(ClassMgr.swbcomm_hasMSMembersInv));
    }

    public boolean hasMember(org.semanticwb.portal.community.Member member)
    {
        if(member==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swbcomm_hasMSMembersInv,member.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByMember(org.semanticwb.portal.community.Member hasmsmembersinv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_hasMSMembersInv, hasmsmembersinv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByMember(org.semanticwb.portal.community.Member hasmsmembersinv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(hasmsmembersinv.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_hasMSMembersInv,hasmsmembersinv.getSemanticObject()));
       return it;
   }

    public org.semanticwb.portal.community.Member getMember()
    {
         org.semanticwb.portal.community.Member ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbcomm_hasMSMembersInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.Member)obj.createGenericInstance();
         }
         return ret;
    }

    public void setType(org.semanticwb.portal.community.MicroSiteType value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swbcomm_type, value.getSemanticObject());
    }

    public void removeType()
    {
        getSemanticObject().removeProperty(ClassMgr.swbcomm_type);
    }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByType(org.semanticwb.portal.community.MicroSiteType type,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_type, type.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByType(org.semanticwb.portal.community.MicroSiteType type)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(type.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_type,type.getSemanticObject()));
       return it;
   }

    public org.semanticwb.portal.community.MicroSiteType getType()
    {
         org.semanticwb.portal.community.MicroSiteType ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbcomm_type);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.MicroSiteType)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.portal.community.MicrositeContainer getMicrositeContainer()
    {
        return (org.semanticwb.portal.community.MicrositeContainer)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
