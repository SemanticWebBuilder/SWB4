package org.semanticwb.portal.community.base;


public class HotelBase extends org.semanticwb.portal.community.Commerce implements org.semanticwb.portal.community.FacilitiesEnable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.portal.community.Addressable,org.semanticwb.portal.community.Contactable,org.semanticwb.model.Geolocalizable
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_Hotel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Hotel");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Hotel");

    public HotelBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.Hotel> listHotels(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Hotel>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.Hotel> listHotels()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Hotel>(it, true);
    }

    public static org.semanticwb.portal.community.Hotel createHotel(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.portal.community.Hotel.createHotel(String.valueOf(id), model);
    }

    public static org.semanticwb.portal.community.Hotel getHotel(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.Hotel)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.Hotel createHotel(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.Hotel)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeHotel(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasHotel(String id, org.semanticwb.model.SWBModel model)
    {
        return (getHotel(id, model)!=null);
    }
}
