package org.semanticwb.portal.community.base;


public class OrganizationCommBase extends org.semanticwb.portal.community.Community 
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_OrganizationComm=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#OrganizationComm");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#OrganizationComm");

    public OrganizationCommBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.OrganizationComm> listOrganizationComms(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.OrganizationComm>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.OrganizationComm> listOrganizationComms()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.OrganizationComm>(it, true);
    }

    public static org.semanticwb.portal.community.OrganizationComm getOrganizationComm(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.OrganizationComm)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.OrganizationComm createOrganizationComm(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.OrganizationComm)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeOrganizationComm(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasOrganizationComm(String id, org.semanticwb.model.SWBModel model)
    {
        return (getOrganizationComm(id, model)!=null);
    }

    public org.semanticwb.portal.community.MicrositeContainer getMicrositeContainer()
    {
        return (org.semanticwb.portal.community.MicrositeContainer)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
