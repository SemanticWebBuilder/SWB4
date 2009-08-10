package org.semanticwb.portal.community.base;


public class MicrositeContainerBase extends org.semanticwb.model.WebSite implements org.semanticwb.model.Activeable,org.semanticwb.model.Trashable,org.semanticwb.model.Traceable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Localeable,org.semanticwb.model.Filterable,org.semanticwb.model.Indexable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_CommUtil=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#CommUtil");
    public static final org.semanticwb.platform.SemanticClass swbcomm_CommunityElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#CommunityElement");
    public static final org.semanticwb.platform.SemanticClass swbcomm_OrganizationComm=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#OrganizationComm");
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSite");
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteType");
    public static final org.semanticwb.platform.SemanticClass swbcomm_CompanySite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#CompanySite");
    public static final org.semanticwb.platform.SemanticClass swbcomm_PersonalComm=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#PersonalComm");
    public static final org.semanticwb.platform.SemanticClass swbcomm_Comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Comment");
    public static final org.semanticwb.platform.SemanticClass swbcomm_Community=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Community");
    public static final org.semanticwb.platform.SemanticClass swbcomm_TematicComm=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#TematicComm");
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicrositeContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicrositeContainer");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicrositeContainer");

    public MicrositeContainerBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

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

    public org.semanticwb.portal.community.CommUtil getCommUtil(String id)
    {
        return org.semanticwb.portal.community.CommUtil.getCommUtil(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.community.CommUtil> listCommUtils()
    {
        return org.semanticwb.portal.community.CommUtil.listCommUtils(this);
    }

    public org.semanticwb.portal.community.CommUtil createCommUtil(String id)
    {
        return org.semanticwb.portal.community.CommUtil.createCommUtil(id,this);
    }

    public void removeCommUtil(String id)
    {
        org.semanticwb.portal.community.CommUtil.removeCommUtil(id, this);
    }
    public boolean hasCommUtil(String id)
    {
        return org.semanticwb.portal.community.CommUtil.hasCommUtil(id, this);
    }

    public org.semanticwb.portal.community.CommunityElement getCommunityElement(String id)
    {
        return org.semanticwb.portal.community.CommunityElement.getCommunityElement(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.community.CommunityElement> listCommunityElements()
    {
        return org.semanticwb.portal.community.CommunityElement.listCommunityElements(this);
    }

    public org.semanticwb.portal.community.CommunityElement createCommunityElement(String id)
    {
        return org.semanticwb.portal.community.CommunityElement.createCommunityElement(id,this);
    }

    public void removeCommunityElement(String id)
    {
        org.semanticwb.portal.community.CommunityElement.removeCommunityElement(id, this);
    }
    public boolean hasCommunityElement(String id)
    {
        return org.semanticwb.portal.community.CommunityElement.hasCommunityElement(id, this);
    }

    public org.semanticwb.portal.community.OrganizationComm getOrganizationComm(String id)
    {
        return org.semanticwb.portal.community.OrganizationComm.getOrganizationComm(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.community.OrganizationComm> listOrganizationComms()
    {
        return org.semanticwb.portal.community.OrganizationComm.listOrganizationComms(this);
    }

    public org.semanticwb.portal.community.OrganizationComm createOrganizationComm(String id)
    {
        return org.semanticwb.portal.community.OrganizationComm.createOrganizationComm(id,this);
    }

    public void removeOrganizationComm(String id)
    {
        org.semanticwb.portal.community.OrganizationComm.removeOrganizationComm(id, this);
    }
    public boolean hasOrganizationComm(String id)
    {
        return org.semanticwb.portal.community.OrganizationComm.hasOrganizationComm(id, this);
    }

    public org.semanticwb.portal.community.MicroSite getMicroSite(String id)
    {
        return org.semanticwb.portal.community.MicroSite.getMicroSite(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSites()
    {
        return org.semanticwb.portal.community.MicroSite.listMicroSites(this);
    }

    public org.semanticwb.portal.community.MicroSite createMicroSite(String id)
    {
        return org.semanticwb.portal.community.MicroSite.createMicroSite(id,this);
    }

    public void removeMicroSite(String id)
    {
        org.semanticwb.portal.community.MicroSite.removeMicroSite(id, this);
    }
    public boolean hasMicroSite(String id)
    {
        return org.semanticwb.portal.community.MicroSite.hasMicroSite(id, this);
    }

    public org.semanticwb.portal.community.MicroSiteType getMicroSiteType(String id)
    {
        return org.semanticwb.portal.community.MicroSiteType.getMicroSiteType(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypes()
    {
        return org.semanticwb.portal.community.MicroSiteType.listMicroSiteTypes(this);
    }

    public org.semanticwb.portal.community.MicroSiteType createMicroSiteType(String id)
    {
        return org.semanticwb.portal.community.MicroSiteType.createMicroSiteType(id,this);
    }

    public void removeMicroSiteType(String id)
    {
        org.semanticwb.portal.community.MicroSiteType.removeMicroSiteType(id, this);
    }
    public boolean hasMicroSiteType(String id)
    {
        return org.semanticwb.portal.community.MicroSiteType.hasMicroSiteType(id, this);
    }

    public org.semanticwb.portal.community.CompanySite getCompanySite(String id)
    {
        return org.semanticwb.portal.community.CompanySite.getCompanySite(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.community.CompanySite> listCompanySites()
    {
        return org.semanticwb.portal.community.CompanySite.listCompanySites(this);
    }

    public org.semanticwb.portal.community.CompanySite createCompanySite(String id)
    {
        return org.semanticwb.portal.community.CompanySite.createCompanySite(id,this);
    }

    public void removeCompanySite(String id)
    {
        org.semanticwb.portal.community.CompanySite.removeCompanySite(id, this);
    }
    public boolean hasCompanySite(String id)
    {
        return org.semanticwb.portal.community.CompanySite.hasCompanySite(id, this);
    }

    public org.semanticwb.portal.community.PersonalComm getPersonalComm(String id)
    {
        return org.semanticwb.portal.community.PersonalComm.getPersonalComm(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.community.PersonalComm> listPersonalComms()
    {
        return org.semanticwb.portal.community.PersonalComm.listPersonalComms(this);
    }

    public org.semanticwb.portal.community.PersonalComm createPersonalComm(String id)
    {
        return org.semanticwb.portal.community.PersonalComm.createPersonalComm(id,this);
    }

    public void removePersonalComm(String id)
    {
        org.semanticwb.portal.community.PersonalComm.removePersonalComm(id, this);
    }
    public boolean hasPersonalComm(String id)
    {
        return org.semanticwb.portal.community.PersonalComm.hasPersonalComm(id, this);
    }

    public org.semanticwb.portal.community.Comment getComment(String id)
    {
        return org.semanticwb.portal.community.Comment.getComment(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.community.Comment> listComments()
    {
        return org.semanticwb.portal.community.Comment.listComments(this);
    }

    public org.semanticwb.portal.community.Comment createComment(String id)
    {
        return org.semanticwb.portal.community.Comment.createComment(id,this);
    }

    public void removeComment(String id)
    {
        org.semanticwb.portal.community.Comment.removeComment(id, this);
    }
    public boolean hasComment(String id)
    {
        return org.semanticwb.portal.community.Comment.hasComment(id, this);
    }

    public org.semanticwb.portal.community.Community getCommunity(String id)
    {
        return org.semanticwb.portal.community.Community.getCommunity(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.community.Community> listCommunitys()
    {
        return org.semanticwb.portal.community.Community.listCommunitys(this);
    }

    public org.semanticwb.portal.community.Community createCommunity(String id)
    {
        return org.semanticwb.portal.community.Community.createCommunity(id,this);
    }

    public void removeCommunity(String id)
    {
        org.semanticwb.portal.community.Community.removeCommunity(id, this);
    }
    public boolean hasCommunity(String id)
    {
        return org.semanticwb.portal.community.Community.hasCommunity(id, this);
    }

    public org.semanticwb.portal.community.TematicComm getTematicComm(String id)
    {
        return org.semanticwb.portal.community.TematicComm.getTematicComm(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.community.TematicComm> listTematicComms()
    {
        return org.semanticwb.portal.community.TematicComm.listTematicComms(this);
    }

    public org.semanticwb.portal.community.TematicComm createTematicComm(String id)
    {
        return org.semanticwb.portal.community.TematicComm.createTematicComm(id,this);
    }

    public void removeTematicComm(String id)
    {
        org.semanticwb.portal.community.TematicComm.removeTematicComm(id, this);
    }
    public boolean hasTematicComm(String id)
    {
        return org.semanticwb.portal.community.TematicComm.hasTematicComm(id, this);
    }
}
