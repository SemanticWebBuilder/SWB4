package org.semanticwb.portal.community.base;


public class CommerceBase extends org.semanticwb.portal.community.Organization implements org.semanticwb.portal.community.FacilitiesEnable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.portal.community.Addressable,org.semanticwb.portal.community.Contactable,org.semanticwb.model.Geolocalizable
{
    public static final org.semanticwb.platform.SemanticProperty swbcomm_paymentType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#paymentType");
    public static final org.semanticwb.platform.SemanticClass swbcomm_Commerce=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Commerce");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Commerce");

    public CommerceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.Commerce> listCommerces(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Commerce>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.Commerce> listCommerces()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Commerce>(it, true);
    }

    public static org.semanticwb.portal.community.Commerce createCommerce(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.portal.community.Commerce.createCommerce(String.valueOf(id), model);
    }

    public static org.semanticwb.portal.community.Commerce getCommerce(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.Commerce)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.Commerce createCommerce(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.Commerce)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeCommerce(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasCommerce(String id, org.semanticwb.model.SWBModel model)
    {
        return (getCommerce(id, model)!=null);
    }

    public int getPaymentType()
    {
        return getSemanticObject().getIntProperty(swbcomm_paymentType);
    }

    public void setPaymentType(int value)
    {
        getSemanticObject().setIntProperty(swbcomm_paymentType, value);
    }

    public boolean isFoodCourt()
    {
        return getSemanticObject().getBooleanProperty(swbcomm_foodCourt);
    }

    public void setFoodCourt(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbcomm_foodCourt, value);
    }

    public boolean isElevator()
    {
        return getSemanticObject().getBooleanProperty(swbcomm_elevator);
    }

    public void setElevator(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbcomm_elevator, value);
    }

    public boolean isParkingLot()
    {
        return getSemanticObject().getBooleanProperty(swbcomm_parkingLot);
    }

    public void setParkingLot(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbcomm_parkingLot, value);
    }

    public boolean isImpairedPeopleAccessible()
    {
        return getSemanticObject().getBooleanProperty(swbcomm_impairedPeopleAccessible);
    }

    public void setImpairedPeopleAccessible(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbcomm_impairedPeopleAccessible, value);
    }
}
