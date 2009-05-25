package org.semanticwb.model.comm.base;


public class RestaurantBase extends org.semanticwb.model.comm.CompanySite implements org.semanticwb.model.Viewable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Activeable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.Trashable,org.semanticwb.model.Rankable,org.semanticwb.model.Indexable,org.semanticwb.model.RoleRefable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.Referensable,org.semanticwb.model.Calendarable,org.semanticwb.model.Resourceable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Hiddenable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Filterable,org.semanticwb.model.Traceable,org.semanticwb.model.UserGroupRefable
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_Restaurant=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Restaurant");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Restaurant");

    public RestaurantBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.model.comm.Restaurant> listRestaurants(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.comm.Restaurant>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.comm.Restaurant> listRestaurants()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.comm.Restaurant>(it, true);
    }

    public static org.semanticwb.model.comm.Restaurant getRestaurant(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.comm.Restaurant)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.model.comm.Restaurant createRestaurant(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.comm.Restaurant)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
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
