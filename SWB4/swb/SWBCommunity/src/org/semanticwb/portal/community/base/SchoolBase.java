package org.semanticwb.portal.community.base;


public class SchoolBase extends org.semanticwb.portal.community.Organization implements org.semanticwb.portal.community.FacilitiesEnable,org.semanticwb.model.Rankable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.portal.community.Addressable,org.semanticwb.portal.community.Contactable,org.semanticwb.model.Geolocalizable,org.semanticwb.portal.community.Interactiveable
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticProperty swbcomm_extNumber=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#extNumber");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_contactPhoneNumber=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#contactPhoneNumber");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_bilingual=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#bilingual");
       public static final org.semanticwb.platform.SemanticProperty swb_reviews=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#reviews");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_contactName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#contactName");
       public static final org.semanticwb.platform.SemanticProperty swb_geoStep=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#geoStep");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_webSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#webSite");
       public static final org.semanticwb.platform.SemanticProperty swb_rank=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#rank");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_abused=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#abused");
       public static final org.semanticwb.platform.SemanticProperty swb_tags=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#tags");
       public static final org.semanticwb.platform.SemanticClass swbcomm_DirectoryResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#DirectoryResource");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_directoryResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#directoryResource");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_foodCourt=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#foodCourt");
       public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_hasDirProfileWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasDirProfileWebPage");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_intNumber=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#intNumber");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_schoolType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#schoolType");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_elevator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#elevator");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_serviceHours=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#serviceHours");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_dirHasExtraPhoto=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#dirHasExtraPhoto");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_state=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#state");
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
       public static final org.semanticwb.platform.SemanticProperty swbcomm_cityCouncil=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#cityCouncil");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_extraCurricularClasses=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#extraCurricularClasses");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_dirPhoto=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#dirPhoto");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_city=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#city");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_isPrivate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#isPrivate");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_contactEmail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#contactEmail");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_parkingLot=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#parkingLot");
       public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_sportsFaciliies=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#sportsFaciliies");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_impairedPeopleAccessible=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#impairedPeopleAccessible");
       public static final org.semanticwb.platform.SemanticClass swbcomm_Comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Comment");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_hasComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasComment");
       public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
       public static final org.semanticwb.platform.SemanticClass swbcomm_School=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#School");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#School");

       public static java.util.Iterator<org.semanticwb.portal.community.School> listSchools(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.School>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.portal.community.School> listSchools()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.School>(it, true);
       }

       public static org.semanticwb.portal.community.School createSchool(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.portal.community.School.ClassMgr.createSchool(String.valueOf(id), model);
       }

       public static org.semanticwb.portal.community.School getSchool(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.portal.community.School)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.portal.community.School createSchool(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.portal.community.School)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeSchool(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasSchool(String id, org.semanticwb.model.SWBModel model)
       {
           return (getSchool(id, model)!=null);
       }
    }

    public SchoolBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public boolean isBilingual()
    {
        return getSemanticObject().getBooleanProperty(ClassMgr.swbcomm_bilingual);
    }

    public void setBilingual(boolean value)
    {
        getSemanticObject().setBooleanProperty(ClassMgr.swbcomm_bilingual, value);
    }

    public boolean isFoodCourt()
    {
        return getSemanticObject().getBooleanProperty(ClassMgr.swbcomm_foodCourt);
    }

    public void setFoodCourt(boolean value)
    {
        getSemanticObject().setBooleanProperty(ClassMgr.swbcomm_foodCourt, value);
    }

    public int getSchoolType()
    {
        return getSemanticObject().getIntProperty(ClassMgr.swbcomm_schoolType);
    }

    public void setSchoolType(int value)
    {
        getSemanticObject().setIntProperty(ClassMgr.swbcomm_schoolType, value);
    }

    public boolean isElevator()
    {
        return getSemanticObject().getBooleanProperty(ClassMgr.swbcomm_elevator);
    }

    public void setElevator(boolean value)
    {
        getSemanticObject().setBooleanProperty(ClassMgr.swbcomm_elevator, value);
    }

    public boolean isExtraCurricularClasses()
    {
        return getSemanticObject().getBooleanProperty(ClassMgr.swbcomm_extraCurricularClasses);
    }

    public void setExtraCurricularClasses(boolean value)
    {
        getSemanticObject().setBooleanProperty(ClassMgr.swbcomm_extraCurricularClasses, value);
    }

    public boolean isIsPrivate()
    {
        return getSemanticObject().getBooleanProperty(ClassMgr.swbcomm_isPrivate);
    }

    public void setIsPrivate(boolean value)
    {
        getSemanticObject().setBooleanProperty(ClassMgr.swbcomm_isPrivate, value);
    }

    public boolean isParkingLot()
    {
        return getSemanticObject().getBooleanProperty(ClassMgr.swbcomm_parkingLot);
    }

    public void setParkingLot(boolean value)
    {
        getSemanticObject().setBooleanProperty(ClassMgr.swbcomm_parkingLot, value);
    }

    public boolean isSportsFaciliies()
    {
        return getSemanticObject().getBooleanProperty(ClassMgr.swbcomm_sportsFaciliies);
    }

    public void setSportsFaciliies(boolean value)
    {
        getSemanticObject().setBooleanProperty(ClassMgr.swbcomm_sportsFaciliies, value);
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
