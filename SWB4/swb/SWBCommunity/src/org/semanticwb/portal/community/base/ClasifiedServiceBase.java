package org.semanticwb.portal.community.base;


public class ClasifiedServiceBase extends org.semanticwb.portal.community.Clasified implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.portal.community.Contactable
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_ClasifiedService=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#ClasifiedService");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#ClasifiedService");

    public ClasifiedServiceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedService> listClasifiedServices(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedService>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedService> listClasifiedServices()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedService>(it, true);
    }

    public static org.semanticwb.portal.community.ClasifiedService createClasifiedService(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.portal.community.ClasifiedService.createClasifiedService(String.valueOf(id), model);
    }

    public static org.semanticwb.portal.community.ClasifiedService getClasifiedService(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.ClasifiedService)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.ClasifiedService createClasifiedService(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.ClasifiedService)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeClasifiedService(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasClasifiedService(String id, org.semanticwb.model.SWBModel model)
    {
        return (getClasifiedService(id, model)!=null);
    }
}
