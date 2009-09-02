package org.semanticwb.portal.community.base;


public class ClasifiedVehicleBase extends org.semanticwb.portal.community.ClasifiedBuySell implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.portal.community.Contactable
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_ClasifiedVehicle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#ClasifiedVehicle");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#ClasifiedVehicle");

    public ClasifiedVehicleBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedVehicle> listClasifiedVehicles(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedVehicle>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedVehicle> listClasifiedVehicles()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedVehicle>(it, true);
    }

    public static org.semanticwb.portal.community.ClasifiedVehicle createClasifiedVehicle(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.portal.community.ClasifiedVehicle.createClasifiedVehicle(String.valueOf(id), model);
    }

    public static org.semanticwb.portal.community.ClasifiedVehicle getClasifiedVehicle(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.ClasifiedVehicle)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.ClasifiedVehicle createClasifiedVehicle(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.ClasifiedVehicle)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeClasifiedVehicle(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasClasifiedVehicle(String id, org.semanticwb.model.SWBModel model)
    {
        return (getClasifiedVehicle(id, model)!=null);
    }
}
