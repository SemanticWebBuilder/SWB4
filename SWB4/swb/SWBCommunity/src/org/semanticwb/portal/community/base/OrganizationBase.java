package org.semanticwb.portal.community.base;


public class OrganizationBase extends org.semanticwb.portal.community.DirectoryObject implements org.semanticwb.model.Rankable,org.semanticwb.portal.community.Contactable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable,org.semanticwb.portal.community.Addressable,org.semanticwb.model.Geolocalizable,org.semanticwb.portal.community.Interactiveable
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticProperty swbcomm_extNumber=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#extNumber");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_contactPhoneNumber=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#contactPhoneNumber");
       public static final org.semanticwb.platform.SemanticProperty swb_reviews=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#reviews");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_contactName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#contactName");
       public static final org.semanticwb.platform.SemanticProperty swb_geoStep=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#geoStep");
       public static final org.semanticwb.platform.SemanticProperty swb_rank=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#rank");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_webSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#webSite");
       public static final org.semanticwb.platform.SemanticProperty swb_tags=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#tags");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_abused=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#abused");
       public static final org.semanticwb.platform.SemanticClass swbcomm_DirectoryResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#DirectoryResource");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_directoryResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#directoryResource");
       public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_hasDirProfileWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasDirProfileWebPage");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_intNumber=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#intNumber");
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
       public static final org.semanticwb.platform.SemanticProperty swbcomm_dirPhoto=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#dirPhoto");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_city=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#city");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_contactEmail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#contactEmail");
       public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
       public static final org.semanticwb.platform.SemanticClass swbcomm_Comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Comment");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_hasComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasComment");
       public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
       public static final org.semanticwb.platform.SemanticClass swbcomm_Organization=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Organization");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Organization");

       public static java.util.Iterator<org.semanticwb.portal.community.Organization> listOrganizations(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Organization>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.portal.community.Organization> listOrganizations()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Organization>(it, true);
       }

       public static org.semanticwb.portal.community.Organization createOrganization(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.portal.community.Organization.ClassMgr.createOrganization(String.valueOf(id), model);
       }

       public static org.semanticwb.portal.community.Organization getOrganization(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.portal.community.Organization)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.portal.community.Organization createOrganization(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.portal.community.Organization)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeOrganization(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasOrganization(String id, org.semanticwb.model.SWBModel model)
       {
           return (getOrganization(id, model)!=null);
       }
    }

    public OrganizationBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getExtNumber()
    {
        return getSemanticObject().getProperty(ClassMgr.swbcomm_extNumber);
    }

    public void setExtNumber(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swbcomm_extNumber, value);
    }

    public String getContactPhoneNumber()
    {
        return getSemanticObject().getProperty(ClassMgr.swbcomm_contactPhoneNumber);
    }

    public void setContactPhoneNumber(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swbcomm_contactPhoneNumber, value);
    }

    public String getContactName()
    {
        return getSemanticObject().getProperty(ClassMgr.swbcomm_contactName);
    }

    public void setContactName(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swbcomm_contactName, value);
    }

    public int getStep()
    {
        return getSemanticObject().getIntProperty(ClassMgr.swb_geoStep);
    }

    public void setStep(int value)
    {
        getSemanticObject().setIntProperty(ClassMgr.swb_geoStep, value);
    }

    public String getWebSite()
    {
        return getSemanticObject().getProperty(ClassMgr.swbcomm_webSite);
    }

    public void setWebSite(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swbcomm_webSite, value);
    }

    public String getIntNumber()
    {
        return getSemanticObject().getProperty(ClassMgr.swbcomm_intNumber);
    }

    public void setIntNumber(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swbcomm_intNumber, value);
    }

    public String getServiceHours()
    {
        return getSemanticObject().getProperty(ClassMgr.swbcomm_serviceHours);
    }

    public void setServiceHours(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swbcomm_serviceHours, value);
    }

    public String getState()
    {
        return getSemanticObject().getProperty(ClassMgr.swbcomm_state);
    }

    public void setState(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swbcomm_state, value);
    }

    public double getLatitude()
    {
        return getSemanticObject().getDoubleProperty(ClassMgr.swb_latitude);
    }

    public void setLatitude(double value)
    {
        getSemanticObject().setDoubleProperty(ClassMgr.swb_latitude, value);
    }

    public double getLongitude()
    {
        return getSemanticObject().getDoubleProperty(ClassMgr.swb_longitude);
    }

    public void setLongitude(double value)
    {
        getSemanticObject().setDoubleProperty(ClassMgr.swb_longitude, value);
    }

    public String getStreetName()
    {
        return getSemanticObject().getProperty(ClassMgr.swbcomm_streetName);
    }

    public void setStreetName(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swbcomm_streetName, value);
    }

    public String getCityCouncil()
    {
        return getSemanticObject().getProperty(ClassMgr.swbcomm_cityCouncil);
    }

    public void setCityCouncil(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swbcomm_cityCouncil, value);
    }

    public String getCity()
    {
        return getSemanticObject().getProperty(ClassMgr.swbcomm_city);
    }

    public void setCity(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swbcomm_city, value);
    }

    public String getContactEmail()
    {
        return getSemanticObject().getProperty(ClassMgr.swbcomm_contactEmail);
    }

    public void setContactEmail(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swbcomm_contactEmail, value);
    }
}
