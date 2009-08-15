package org.semanticwb.portal.community.base;


public class MicroSiteWebPageUtilBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.TemplateRefable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Referensable,org.semanticwb.model.Activeable,org.semanticwb.model.Expirable,org.semanticwb.model.Filterable,org.semanticwb.model.Traceable,org.semanticwb.model.Indexable,org.semanticwb.model.Hiddenable,org.semanticwb.model.Viewable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Rankable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.RuleRefable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.Trashable,org.semanticwb.model.Resourceable
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteUtil=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteUtil");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_microSiteUtil=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#microSiteUtil");
    public static final org.semanticwb.platform.SemanticClass swbcomm_Member=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Member");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasSubscribedMembersInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasSubscribedMembersInv");
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSite");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_microSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#microSite");
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteWebPageUtil=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteWebPageUtil");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteWebPageUtil");

    public MicroSiteWebPageUtilBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteWebPageUtil> listMicroSiteWebPageUtils(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteWebPageUtil>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteWebPageUtil> listMicroSiteWebPageUtils()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteWebPageUtil>(it, true);
    }

    public static org.semanticwb.portal.community.MicroSiteWebPageUtil getMicroSiteWebPageUtil(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.MicroSiteWebPageUtil)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.MicroSiteWebPageUtil createMicroSiteWebPageUtil(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.MicroSiteWebPageUtil)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeMicroSiteWebPageUtil(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasMicroSiteWebPageUtil(String id, org.semanticwb.model.SWBModel model)
    {
        return (getMicroSiteWebPageUtil(id, model)!=null);
    }

    public void setMicroSiteUtil(org.semanticwb.portal.community.MicroSiteUtil value)
    {
        getSemanticObject().setObjectProperty(swbcomm_microSiteUtil, value.getSemanticObject());
    }

    public void removeMicroSiteUtil()
    {
        getSemanticObject().removeProperty(swbcomm_microSiteUtil);
    }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteWebPageUtil> listMicroSiteWebPageUtilByMicroSiteUtil(org.semanticwb.portal.community.MicroSiteUtil micrositeutil,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteWebPageUtil> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_microSiteUtil, micrositeutil.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteWebPageUtil> listMicroSiteWebPageUtilByMicroSiteUtil(org.semanticwb.portal.community.MicroSiteUtil micrositeutil)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteWebPageUtil> it=new org.semanticwb.model.GenericIterator(micrositeutil.getSemanticObject().getModel().listSubjects(swbcomm_microSiteUtil,micrositeutil.getSemanticObject()));
       return it;
   }

    public org.semanticwb.portal.community.MicroSiteUtil getMicroSiteUtil()
    {
         org.semanticwb.portal.community.MicroSiteUtil ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_microSiteUtil);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.MicroSiteUtil)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Member> listSubscribedMembers()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Member>(getSemanticObject().listObjectProperties(swbcomm_hasSubscribedMembersInv));
    }

    public boolean hasSubscribedMember(org.semanticwb.portal.community.Member member)
    {
        if(member==null)return false;
        return getSemanticObject().hasObjectProperty(swbcomm_hasSubscribedMembersInv,member.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteWebPageUtil> listMicroSiteWebPageUtilBySubscribedMember(org.semanticwb.portal.community.Member hassubscribedmembersinv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteWebPageUtil> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasSubscribedMembersInv, hassubscribedmembersinv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteWebPageUtil> listMicroSiteWebPageUtilBySubscribedMember(org.semanticwb.portal.community.Member hassubscribedmembersinv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteWebPageUtil> it=new org.semanticwb.model.GenericIterator(hassubscribedmembersinv.getSemanticObject().getModel().listSubjects(swbcomm_hasSubscribedMembersInv,hassubscribedmembersinv.getSemanticObject()));
       return it;
   }

    public org.semanticwb.portal.community.Member getSubscribedMember()
    {
         org.semanticwb.portal.community.Member ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_hasSubscribedMembersInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.Member)obj.createGenericInstance();
         }
         return ret;
    }

    public void setMicroSite(org.semanticwb.portal.community.MicroSite value)
    {
        getSemanticObject().setObjectProperty(swbcomm_microSite, value.getSemanticObject());
    }

    public void removeMicroSite()
    {
        getSemanticObject().removeProperty(swbcomm_microSite);
    }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteWebPageUtil> listMicroSiteWebPageUtilByMicroSite(org.semanticwb.portal.community.MicroSite microsite,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteWebPageUtil> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_microSite, microsite.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteWebPageUtil> listMicroSiteWebPageUtilByMicroSite(org.semanticwb.portal.community.MicroSite microsite)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteWebPageUtil> it=new org.semanticwb.model.GenericIterator(microsite.getSemanticObject().getModel().listSubjects(swbcomm_microSite,microsite.getSemanticObject()));
       return it;
   }

    public org.semanticwb.portal.community.MicroSite getMicroSite()
    {
         org.semanticwb.portal.community.MicroSite ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_microSite);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.MicroSite)obj.createGenericInstance();
         }
         return ret;
    }
}
