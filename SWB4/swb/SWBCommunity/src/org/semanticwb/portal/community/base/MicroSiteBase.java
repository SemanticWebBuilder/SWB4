package org.semanticwb.portal.community.base;


public class MicroSiteBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.Resourceable,org.semanticwb.model.Undeleteable,org.semanticwb.model.RuleRefable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Trashable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Expirable,org.semanticwb.model.Filterable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Activeable,org.semanticwb.model.Rankable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Referensable,org.semanticwb.model.Viewable,org.semanticwb.model.Hiddenable,org.semanticwb.model.Traceable,org.semanticwb.model.Indexable,org.semanticwb.model.TemplateRefable
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteWebPageUtil=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteWebPageUtil");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasMicroSiteUtilsInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasMicroSiteUtilsInv");
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_about=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#about");
    public static final org.semanticwb.platform.SemanticClass swbcomm_Member=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Member");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasMSMembersInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasMSMembersInv");
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteType");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_type=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#type");
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSite");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSite");

    public MicroSiteBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

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

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteWebPageUtil> listMicroSiteUtils()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteWebPageUtil>(getSemanticObject().listObjectProperties(swbcomm_hasMicroSiteUtilsInv));
    }

    public boolean hasMicroSiteUtil(org.semanticwb.portal.community.MicroSiteWebPageUtil micrositewebpageutil)
    {
        if(micrositewebpageutil==null)return false;
        return getSemanticObject().hasObjectProperty(swbcomm_hasMicroSiteUtilsInv,micrositewebpageutil.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByHasMicroSiteUtil(org.semanticwb.portal.community.MicroSiteWebPageUtil hasmicrositeutilsinv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasMicroSiteUtilsInv, hasmicrositeutilsinv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByHasMicroSiteUtil(org.semanticwb.portal.community.MicroSiteWebPageUtil hasmicrositeutilsinv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(hasmicrositeutilsinv.getSemanticObject().getModel().listSubjects(swbcomm_hasMicroSiteUtilsInv,hasmicrositeutilsinv.getSemanticObject()));
       return it;
   }

    public org.semanticwb.portal.community.MicroSiteWebPageUtil getMicroSiteUtil()
    {
         org.semanticwb.portal.community.MicroSiteWebPageUtil ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_hasMicroSiteUtilsInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.MicroSiteWebPageUtil)obj.createGenericInstance();
         }
         return ret;
    }

    public void setAbout(org.semanticwb.model.WebPage webpage)
    {
        getSemanticObject().setObjectProperty(swbcomm_about, webpage.getSemanticObject());
    }

    public void removeAbout()
    {
        getSemanticObject().removeProperty(swbcomm_about);
    }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByAbout(org.semanticwb.model.WebPage about,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_about, about.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByAbout(org.semanticwb.model.WebPage about)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(about.getSemanticObject().getModel().listSubjects(swbcomm_about,about.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.WebPage getAbout()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_about);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Member> listMemberss()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Member>(getSemanticObject().listObjectProperties(swbcomm_hasMSMembersInv));
    }

    public boolean hasMembers(org.semanticwb.portal.community.Member member)
    {
        if(member==null)return false;
        return getSemanticObject().hasObjectProperty(swbcomm_hasMSMembersInv,member.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByHasMembers(org.semanticwb.portal.community.Member hasmsmembersinv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasMSMembersInv, hasmsmembersinv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByHasMembers(org.semanticwb.portal.community.Member hasmsmembersinv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(hasmsmembersinv.getSemanticObject().getModel().listSubjects(swbcomm_hasMSMembersInv,hasmsmembersinv.getSemanticObject()));
       return it;
   }

    public org.semanticwb.portal.community.Member getMembers()
    {
         org.semanticwb.portal.community.Member ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_hasMSMembersInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.Member)obj.createGenericInstance();
         }
         return ret;
    }

    public void setType(org.semanticwb.portal.community.MicroSiteType micrositetype)
    {
        getSemanticObject().setObjectProperty(swbcomm_type, micrositetype.getSemanticObject());
    }

    public void removeType()
    {
        getSemanticObject().removeProperty(swbcomm_type);
    }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByType(org.semanticwb.portal.community.MicroSiteType type,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_type, type.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByType(org.semanticwb.portal.community.MicroSiteType type)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(type.getSemanticObject().getModel().listSubjects(swbcomm_type,type.getSemanticObject()));
       return it;
   }

    public org.semanticwb.portal.community.MicroSiteType getType()
    {
         org.semanticwb.portal.community.MicroSiteType ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_type);
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
