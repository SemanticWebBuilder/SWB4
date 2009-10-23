package org.semanticwb.portal.community.base;


public class MemberBase extends org.semanticwb.model.SWBClass 
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSite");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_memMicroSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#memMicroSite");
       public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_memUser=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#memUser");
       public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteWebPageUtil=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteWebPageUtil");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_hasSubscriptions=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasSubscriptions");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_memAccessLevel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#memAccessLevel");
       public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
       public static final org.semanticwb.platform.SemanticClass swbcomm_Member=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Member");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Member");

       public static java.util.Iterator<org.semanticwb.portal.community.Member> listMembers(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Member>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.portal.community.Member> listMembers()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Member>(it, true);
       }

       public static org.semanticwb.portal.community.Member createMember(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.portal.community.Member.ClassMgr.createMember(String.valueOf(id), model);
       }

       public static org.semanticwb.portal.community.Member getMember(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.portal.community.Member)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.portal.community.Member createMember(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.portal.community.Member)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeMember(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasMember(String id, org.semanticwb.model.SWBModel model)
       {
           return (getMember(id, model)!=null);
       }
    }

    public MemberBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setMicroSite(org.semanticwb.portal.community.MicroSite value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swbcomm_memMicroSite, value.getSemanticObject());
    }

    public void removeMicroSite()
    {
        getSemanticObject().removeProperty(ClassMgr.swbcomm_memMicroSite);
    }

   public static java.util.Iterator<org.semanticwb.portal.community.Member> listMemberByMicroSite(org.semanticwb.portal.community.MicroSite memmicrosite,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Member> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_memMicroSite, memmicrosite.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.Member> listMemberByMicroSite(org.semanticwb.portal.community.MicroSite memmicrosite)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Member> it=new org.semanticwb.model.GenericIterator(memmicrosite.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_memMicroSite,memmicrosite.getSemanticObject()));
       return it;
   }

    public org.semanticwb.portal.community.MicroSite getMicroSite()
    {
         org.semanticwb.portal.community.MicroSite ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbcomm_memMicroSite);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.MicroSite)obj.createGenericInstance();
         }
         return ret;
    }

    public void setUser(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swbcomm_memUser, value.getSemanticObject());
    }

    public void removeUser()
    {
        getSemanticObject().removeProperty(ClassMgr.swbcomm_memUser);
    }

   public static java.util.Iterator<org.semanticwb.portal.community.Member> listMemberByUser(org.semanticwb.model.User memuser,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Member> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_memUser, memuser.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.Member> listMemberByUser(org.semanticwb.model.User memuser)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Member> it=new org.semanticwb.model.GenericIterator(memuser.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_memUser,memuser.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.User getUser()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbcomm_memUser);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteWebPageUtil> listSubscriptionses()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteWebPageUtil>(getSemanticObject().listObjectProperties(ClassMgr.swbcomm_hasSubscriptions));
    }

    public boolean hasSubscriptions(org.semanticwb.portal.community.MicroSiteWebPageUtil micrositewebpageutil)
    {
        if(micrositewebpageutil==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swbcomm_hasSubscriptions,micrositewebpageutil.getSemanticObject());
    }

    public void addSubscriptions(org.semanticwb.portal.community.MicroSiteWebPageUtil value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swbcomm_hasSubscriptions, value.getSemanticObject());
    }

    public void removeAllSubscriptions()
    {
        getSemanticObject().removeProperty(ClassMgr.swbcomm_hasSubscriptions);
    }

    public void removeSubscriptions(org.semanticwb.portal.community.MicroSiteWebPageUtil micrositewebpageutil)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swbcomm_hasSubscriptions,micrositewebpageutil.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.portal.community.Member> listMemberBySubscriptions(org.semanticwb.portal.community.MicroSiteWebPageUtil hassubscriptions,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Member> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_hasSubscriptions, hassubscriptions.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.Member> listMemberBySubscriptions(org.semanticwb.portal.community.MicroSiteWebPageUtil hassubscriptions)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Member> it=new org.semanticwb.model.GenericIterator(hassubscriptions.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_hasSubscriptions,hassubscriptions.getSemanticObject()));
       return it;
   }

    public org.semanticwb.portal.community.MicroSiteWebPageUtil getSubscriptions()
    {
         org.semanticwb.portal.community.MicroSiteWebPageUtil ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbcomm_hasSubscriptions);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.MicroSiteWebPageUtil)obj.createGenericInstance();
         }
         return ret;
    }

    public int getAccessLevel()
    {
        return getSemanticObject().getIntProperty(ClassMgr.swbcomm_memAccessLevel);
    }

    public void setAccessLevel(int value)
    {
        getSemanticObject().setIntProperty(ClassMgr.swbcomm_memAccessLevel, value);
    }
}
