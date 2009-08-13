package org.semanticwb.portal.community.base;


public class CompanySiteBase extends org.semanticwb.portal.community.MicroSiteClass 
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_CompanySite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#CompanySite");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#CompanySite");

    public CompanySiteBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.CompanySite> listCompanySites(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.CompanySite>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.CompanySite> listCompanySites()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.CompanySite>(it, true);
    }

    public static org.semanticwb.portal.community.CompanySite getCompanySite(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.CompanySite)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.CompanySite createCompanySite(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.CompanySite)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeCompanySite(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasCompanySite(String id, org.semanticwb.model.SWBModel model)
    {
        return (getCompanySite(id, model)!=null);
    }

    public org.semanticwb.portal.community.MicrositeContainer getMicrositeContainer()
    {
        return (org.semanticwb.portal.community.MicrositeContainer)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
