package org.semanticwb.portal.community.base;


public class MicrositeContainerBase extends org.semanticwb.model.WebSite implements org.semanticwb.model.Filterable,org.semanticwb.model.Trashable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.model.Localeable,org.semanticwb.model.Indexable,org.semanticwb.model.Activeable
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticProperty swb_deleted=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#deleted");
       public static final org.semanticwb.platform.SemanticProperty swb_active=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#active");
       public static final org.semanticwb.platform.SemanticClass swb_Language=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Language");
       public static final org.semanticwb.platform.SemanticProperty swb_language=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#language");
       public static final org.semanticwb.platform.SemanticClass swb_SWBModel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBModel");
       public static final org.semanticwb.platform.SemanticProperty swb_hasSubModel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasSubModel");
       public static final org.semanticwb.platform.SemanticProperty swb_undeleteable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#undeleteable");
       public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
       public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
       public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
       public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
       public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
       public static final org.semanticwb.platform.SemanticClass swb_UserRepository=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserRepository");
       public static final org.semanticwb.platform.SemanticProperty swb_userRepository=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepository");
       public static final org.semanticwb.platform.SemanticProperty swb_indexable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#indexable");
       public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
       public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
       public static final org.semanticwb.platform.SemanticProperty swb_homePage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#homePage");
       public static final org.semanticwb.platform.SemanticClass swb_Template=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Template");
       public static final org.semanticwb.platform.SemanticProperty swb_defaultTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#defaultTemplate");
       public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
       public static final org.semanticwb.platform.SemanticClass swbcomm_OrganizationComm=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#OrganizationComm");
       public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSite");
       public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteType");
       public static final org.semanticwb.platform.SemanticClass swbcomm_CompanySite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#CompanySite");
       public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteUtil=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteUtil");
       public static final org.semanticwb.platform.SemanticClass swbcomm_PersonalComm=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#PersonalComm");
       public static final org.semanticwb.platform.SemanticClass swbcomm_Comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Comment");
       public static final org.semanticwb.platform.SemanticClass swbcomm_Community=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Community");
       public static final org.semanticwb.platform.SemanticClass swbcomm_TematicComm=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#TematicComm");
       public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteElement");
       public static final org.semanticwb.platform.SemanticClass swbcomm_MicrositeContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicrositeContainer");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicrositeContainer");

       public static java.util.Iterator<org.semanticwb.portal.community.MicrositeContainer> listMicrositeContainers(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicrositeContainer>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.portal.community.MicrositeContainer> listMicrositeContainers()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicrositeContainer>(it, true);
       }

       public static org.semanticwb.portal.community.MicrositeContainer getMicrositeContainer(String id)
       {
           org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
           org.semanticwb.portal.community.MicrositeContainer ret=null;
           org.semanticwb.platform.SemanticModel model=mgr.getModel(id);
           if(model!=null)
           {
               org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(model.getObjectUri(id,sclass));
               if(obj!=null)
               {
                   ret=(org.semanticwb.portal.community.MicrositeContainer)obj.createGenericInstance();
               }
           }
           return ret;
       }

       public static org.semanticwb.portal.community.MicrositeContainer createMicrositeContainer(String id, String namespace)
       {
           org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
           org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
           return (org.semanticwb.portal.community.MicrositeContainer)model.createGenericObject(model.getObjectUri(id, sclass), sclass);
       }

       public static void removeMicrositeContainer(String id)
       {
           org.semanticwb.portal.community.MicrositeContainer obj=getMicrositeContainer(id);
           if(obj!=null)
           {
               obj.remove();
           }
       }

       public static boolean hasMicrositeContainer(String id)
       {
           return (getMicrositeContainer(id)!=null);
       }
    }

    public MicrositeContainerBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.portal.community.OrganizationComm getOrganizationComm(String id)
    {
        return org.semanticwb.portal.community.OrganizationComm.ClassMgr.getOrganizationComm(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.community.OrganizationComm> listOrganizationComms()
    {
        return org.semanticwb.portal.community.OrganizationComm.ClassMgr.listOrganizationComms(this);
    }

    public org.semanticwb.portal.community.OrganizationComm createOrganizationComm(String id)
    {
        return org.semanticwb.portal.community.OrganizationComm.ClassMgr.createOrganizationComm(id,this);
    }

    public void removeOrganizationComm(String id)
    {
        org.semanticwb.portal.community.OrganizationComm.ClassMgr.removeOrganizationComm(id, this);
    }
    public boolean hasOrganizationComm(String id)
    {
        return org.semanticwb.portal.community.OrganizationComm.ClassMgr.hasOrganizationComm(id, this);
    }

    public org.semanticwb.portal.community.MicroSite getMicroSite(String id)
    {
        return org.semanticwb.portal.community.MicroSite.ClassMgr.getMicroSite(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSites()
    {
        return org.semanticwb.portal.community.MicroSite.ClassMgr.listMicroSites(this);
    }

    public org.semanticwb.portal.community.MicroSite createMicroSite(String id)
    {
        return org.semanticwb.portal.community.MicroSite.ClassMgr.createMicroSite(id,this);
    }

    public void removeMicroSite(String id)
    {
        org.semanticwb.portal.community.MicroSite.ClassMgr.removeMicroSite(id, this);
    }
    public boolean hasMicroSite(String id)
    {
        return org.semanticwb.portal.community.MicroSite.ClassMgr.hasMicroSite(id, this);
    }

    public org.semanticwb.portal.community.MicroSiteType getMicroSiteType(String id)
    {
        return org.semanticwb.portal.community.MicroSiteType.ClassMgr.getMicroSiteType(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypes()
    {
        return org.semanticwb.portal.community.MicroSiteType.ClassMgr.listMicroSiteTypes(this);
    }

    public org.semanticwb.portal.community.MicroSiteType createMicroSiteType(String id)
    {
        return org.semanticwb.portal.community.MicroSiteType.ClassMgr.createMicroSiteType(id,this);
    }

    public void removeMicroSiteType(String id)
    {
        org.semanticwb.portal.community.MicroSiteType.ClassMgr.removeMicroSiteType(id, this);
    }
    public boolean hasMicroSiteType(String id)
    {
        return org.semanticwb.portal.community.MicroSiteType.ClassMgr.hasMicroSiteType(id, this);
    }

    public org.semanticwb.portal.community.CompanySite getCompanySite(String id)
    {
        return org.semanticwb.portal.community.CompanySite.ClassMgr.getCompanySite(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.community.CompanySite> listCompanySites()
    {
        return org.semanticwb.portal.community.CompanySite.ClassMgr.listCompanySites(this);
    }

    public org.semanticwb.portal.community.CompanySite createCompanySite(String id)
    {
        return org.semanticwb.portal.community.CompanySite.ClassMgr.createCompanySite(id,this);
    }

    public void removeCompanySite(String id)
    {
        org.semanticwb.portal.community.CompanySite.ClassMgr.removeCompanySite(id, this);
    }
    public boolean hasCompanySite(String id)
    {
        return org.semanticwb.portal.community.CompanySite.ClassMgr.hasCompanySite(id, this);
    }

    public org.semanticwb.portal.community.MicroSiteUtil getMicroSiteUtil(String id)
    {
        return org.semanticwb.portal.community.MicroSiteUtil.ClassMgr.getMicroSiteUtil(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.community.MicroSiteUtil> listMicroSiteUtils()
    {
        return org.semanticwb.portal.community.MicroSiteUtil.ClassMgr.listMicroSiteUtils(this);
    }

    public org.semanticwb.portal.community.MicroSiteUtil createMicroSiteUtil(String id)
    {
        return org.semanticwb.portal.community.MicroSiteUtil.ClassMgr.createMicroSiteUtil(id,this);
    }

    public void removeMicroSiteUtil(String id)
    {
        org.semanticwb.portal.community.MicroSiteUtil.ClassMgr.removeMicroSiteUtil(id, this);
    }
    public boolean hasMicroSiteUtil(String id)
    {
        return org.semanticwb.portal.community.MicroSiteUtil.ClassMgr.hasMicroSiteUtil(id, this);
    }

    public org.semanticwb.portal.community.PersonalComm getPersonalComm(String id)
    {
        return org.semanticwb.portal.community.PersonalComm.ClassMgr.getPersonalComm(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.community.PersonalComm> listPersonalComms()
    {
        return org.semanticwb.portal.community.PersonalComm.ClassMgr.listPersonalComms(this);
    }

    public org.semanticwb.portal.community.PersonalComm createPersonalComm(String id)
    {
        return org.semanticwb.portal.community.PersonalComm.ClassMgr.createPersonalComm(id,this);
    }

    public void removePersonalComm(String id)
    {
        org.semanticwb.portal.community.PersonalComm.ClassMgr.removePersonalComm(id, this);
    }
    public boolean hasPersonalComm(String id)
    {
        return org.semanticwb.portal.community.PersonalComm.ClassMgr.hasPersonalComm(id, this);
    }

    public org.semanticwb.portal.community.Comment getComment(String id)
    {
        return org.semanticwb.portal.community.Comment.ClassMgr.getComment(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.community.Comment> listComments()
    {
        return org.semanticwb.portal.community.Comment.ClassMgr.listComments(this);
    }

    public org.semanticwb.portal.community.Comment createComment(String id)
    {
        return org.semanticwb.portal.community.Comment.ClassMgr.createComment(id,this);
    }

    public org.semanticwb.portal.community.Comment createComment()
    {
        long id=getSemanticObject().getModel().getCounter(ClassMgr.swbcomm_Comment);
        return org.semanticwb.portal.community.Comment.ClassMgr.createComment(String.valueOf(id),this);
    } 

    public void removeComment(String id)
    {
        org.semanticwb.portal.community.Comment.ClassMgr.removeComment(id, this);
    }
    public boolean hasComment(String id)
    {
        return org.semanticwb.portal.community.Comment.ClassMgr.hasComment(id, this);
    }

    public org.semanticwb.portal.community.Community getCommunity(String id)
    {
        return org.semanticwb.portal.community.Community.ClassMgr.getCommunity(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.community.Community> listCommunities()
    {
        return org.semanticwb.portal.community.Community.ClassMgr.listCommunities(this);
    }

    public org.semanticwb.portal.community.Community createCommunity(String id)
    {
        return org.semanticwb.portal.community.Community.ClassMgr.createCommunity(id,this);
    }

    public void removeCommunity(String id)
    {
        org.semanticwb.portal.community.Community.ClassMgr.removeCommunity(id, this);
    }
    public boolean hasCommunity(String id)
    {
        return org.semanticwb.portal.community.Community.ClassMgr.hasCommunity(id, this);
    }

    public org.semanticwb.portal.community.TematicComm getTematicComm(String id)
    {
        return org.semanticwb.portal.community.TematicComm.ClassMgr.getTematicComm(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.community.TematicComm> listTematicComms()
    {
        return org.semanticwb.portal.community.TematicComm.ClassMgr.listTematicComms(this);
    }

    public org.semanticwb.portal.community.TematicComm createTematicComm(String id)
    {
        return org.semanticwb.portal.community.TematicComm.ClassMgr.createTematicComm(id,this);
    }

    public void removeTematicComm(String id)
    {
        org.semanticwb.portal.community.TematicComm.ClassMgr.removeTematicComm(id, this);
    }
    public boolean hasTematicComm(String id)
    {
        return org.semanticwb.portal.community.TematicComm.ClassMgr.hasTematicComm(id, this);
    }

    public org.semanticwb.portal.community.MicroSiteElement getMicroSiteElement(String id)
    {
        return org.semanticwb.portal.community.MicroSiteElement.ClassMgr.getMicroSiteElement(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.community.MicroSiteElement> listMicroSiteElements()
    {
        return org.semanticwb.portal.community.MicroSiteElement.ClassMgr.listMicroSiteElements(this);
    }

    public org.semanticwb.portal.community.MicroSiteElement createMicroSiteElement(String id)
    {
        return org.semanticwb.portal.community.MicroSiteElement.ClassMgr.createMicroSiteElement(id,this);
    }

    public void removeMicroSiteElement(String id)
    {
        org.semanticwb.portal.community.MicroSiteElement.ClassMgr.removeMicroSiteElement(id, this);
    }
    public boolean hasMicroSiteElement(String id)
    {
        return org.semanticwb.portal.community.MicroSiteElement.ClassMgr.hasMicroSiteElement(id, this);
    }
}
