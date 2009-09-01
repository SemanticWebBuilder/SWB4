package org.semanticwb.portal.community.base;


public class RestaurantBase extends org.semanticwb.portal.community.Organization implements org.semanticwb.model.Geolocalizable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.portal.community.Addressable
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_Restaurant=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Restaurant");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Restaurant");

    public RestaurantBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.Restaurant> listRestaurants(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Restaurant>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.Restaurant> listRestaurants()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Restaurant>(it, true);
    }

    public static org.semanticwb.portal.community.Restaurant createRestaurant(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.portal.community.Restaurant.createRestaurant(String.valueOf(id), model);
    }

    public static org.semanticwb.portal.community.Restaurant getRestaurant(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.Restaurant)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.Restaurant createRestaurant(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.Restaurant)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeRestaurant(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasRestaurant(String id, org.semanticwb.model.SWBModel model)
    {
        return (getRestaurant(id, model)!=null);
    }
}
