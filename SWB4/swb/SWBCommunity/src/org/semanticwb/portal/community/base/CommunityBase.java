package org.semanticwb.portal.community.base;


public class CommunityBase extends org.semanticwb.portal.community.MicroSiteClass 
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_Community=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Community");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Community");

    public CommunityBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.Community> listCommunitys(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Community>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.Community> listCommunitys()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Community>(it, true);
    }

    public static org.semanticwb.portal.community.Community getCommunity(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.Community)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.Community createCommunity(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.Community)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeCommunity(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasCommunity(String id, org.semanticwb.model.SWBModel model)
    {
        return (getCommunity(id, model)!=null);
    }

    public org.semanticwb.portal.community.MicrositeContainer getMicrositeContainer()
    {
        return (org.semanticwb.portal.community.MicrositeContainer)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
