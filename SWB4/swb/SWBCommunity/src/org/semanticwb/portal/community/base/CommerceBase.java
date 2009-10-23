package org.semanticwb.portal.community.base;


public class CommerceBase extends org.semanticwb.portal.community.Organization implements org.semanticwb.portal.community.FacilitiesEnable,org.semanticwb.portal.community.Addressable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Geolocalizable,org.semanticwb.portal.community.Contactable,org.semanticwb.model.Traceable
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticProperty swbcomm_extNumber=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#extNumber");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_contactPhoneNumber=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#contactPhoneNumber");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_contactName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#contactName");
       public static final org.semanticwb.platform.SemanticProperty swb_geoStep=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#geoStep");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_paymentType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#paymentType");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_webSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#webSite");
       public static final org.semanticwb.platform.SemanticProperty swb_tags=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#tags");
       public static final org.semanticwb.platform.SemanticClass swbcomm_DirectoryResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#DirectoryResource");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_directoryResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#directoryResource");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_foodCourt=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#foodCourt");
       public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_hasDirProfileWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasDirProfileWebPage");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_intNumber=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#intNumber");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_serviceHours=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#serviceHours");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_elevator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#elevator");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_dirHasExtraPhoto=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#dirHasExtraPhoto");
       public static final org.semanticwb.platform.SemanticProperty swb_latitude=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#latitude");
       public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
       public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
       public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
       public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_dirWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#dirWebPage");
       public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
       public static final org.semanticwb.platform.SemanticProperty swb_longitude=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#longitude");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_streetName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#streetName");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_hasDirTopicWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasDirTopicWebPage");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_dirPhoto=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#dirPhoto");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_city=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#city");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_contactEmail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#contactEmail");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_parkingLot=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#parkingLot");
       public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_impairedPeopleAccessible=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#impairedPeopleAccessible");
       public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
       public static final org.semanticwb.platform.SemanticClass swbcomm_Commerce=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Commerce");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Commerce");

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
           return org.semanticwb.portal.community.Commerce.ClassMgr.createCommerce(String.valueOf(id), model);
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
    }

    public CommerceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public int getPaymentType()
    {
        return getSemanticObject().getIntProperty(ClassMgr.swbcomm_paymentType);
    }

    public void setPaymentType(int value)
    {
        getSemanticObject().setIntProperty(ClassMgr.swbcomm_paymentType, value);
    }

    public boolean isFoodCourt()
    {
        return getSemanticObject().getBooleanProperty(ClassMgr.swbcomm_foodCourt);
    }

    public void setFoodCourt(boolean value)
    {
        getSemanticObject().setBooleanProperty(ClassMgr.swbcomm_foodCourt, value);
    }

    public boolean isElevator()
    {
        return getSemanticObject().getBooleanProperty(ClassMgr.swbcomm_elevator);
    }

    public void setElevator(boolean value)
    {
        getSemanticObject().setBooleanProperty(ClassMgr.swbcomm_elevator, value);
    }

    public boolean isParkingLot()
    {
        return getSemanticObject().getBooleanProperty(ClassMgr.swbcomm_parkingLot);
    }

    public void setParkingLot(boolean value)
    {
        getSemanticObject().setBooleanProperty(ClassMgr.swbcomm_parkingLot, value);
    }

    public boolean isImpairedPeopleAccessible()
    {
        return getSemanticObject().getBooleanProperty(ClassMgr.swbcomm_impairedPeopleAccessible);
    }

    public void setImpairedPeopleAccessible(boolean value)
    {
        getSemanticObject().setBooleanProperty(ClassMgr.swbcomm_impairedPeopleAccessible, value);
    }
}
