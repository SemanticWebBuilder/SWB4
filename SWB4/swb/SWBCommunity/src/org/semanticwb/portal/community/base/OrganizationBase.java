package org.semanticwb.portal.community.base;


public class OrganizationBase extends org.semanticwb.portal.community.DirectoryObject implements org.semanticwb.model.Descriptiveable,org.semanticwb.portal.community.Addressable,org.semanticwb.model.Traceable,org.semanticwb.portal.community.Contactable,org.semanticwb.model.Geolocalizable
{
    public static final org.semanticwb.platform.SemanticProperty swbcomm_webSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#webSite");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_serviceHours=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#serviceHours");
    public static final org.semanticwb.platform.SemanticClass swbcomm_Organization=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Organization");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Organization");

    public OrganizationBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

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
        return org.semanticwb.portal.community.Organization.createOrganization(String.valueOf(id), model);
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

    public String getExtNumber()
    {
        return getSemanticObject().getProperty(swbcomm_extNumber);
    }

    public void setExtNumber(String value)
    {
        getSemanticObject().setProperty(swbcomm_extNumber, value);
    }

    public String getContactPhoneNumber()
    {
        return getSemanticObject().getProperty(swbcomm_contactPhoneNumber);
    }

    public void setContactPhoneNumber(String value)
    {
        getSemanticObject().setProperty(swbcomm_contactPhoneNumber, value);
    }

    public String getContactName()
    {
        return getSemanticObject().getProperty(swbcomm_contactName);
    }

    public void setContactName(String value)
    {
        getSemanticObject().setProperty(swbcomm_contactName, value);
    }

    public int getStep()
    {
        return getSemanticObject().getIntProperty(swb_geoStep);
    }

    public void setStep(int value)
    {
        getSemanticObject().setIntProperty(swb_geoStep, value);
    }

    public String getWebSite()
    {
        return getSemanticObject().getProperty(swbcomm_webSite);
    }

    public void setWebSite(String value)
    {
        getSemanticObject().setProperty(swbcomm_webSite, value);
    }

    public String getIntNumber()
    {
        return getSemanticObject().getProperty(swbcomm_intNumber);
    }

    public void setIntNumber(String value)
    {
        getSemanticObject().setProperty(swbcomm_intNumber, value);
    }

    public String getServiceHours()
    {
        return getSemanticObject().getProperty(swbcomm_serviceHours);
    }

    public void setServiceHours(String value)
    {
        getSemanticObject().setProperty(swbcomm_serviceHours, value);
    }

    public double getLatitude()
    {
        return getSemanticObject().getDoubleProperty(swb_latitude);
    }

    public void setLatitude(double value)
    {
        getSemanticObject().setDoubleProperty(swb_latitude, value);
    }

    public double getLongitude()
    {
        return getSemanticObject().getDoubleProperty(swb_longitude);
    }

    public void setLongitude(double value)
    {
        getSemanticObject().setDoubleProperty(swb_longitude, value);
    }

    public String getStreetName()
    {
        return getSemanticObject().getProperty(swbcomm_streetName);
    }

    public void setStreetName(String value)
    {
        getSemanticObject().setProperty(swbcomm_streetName, value);
    }

    public String getCity()
    {
        return getSemanticObject().getProperty(swbcomm_city);
    }

    public void setCity(String value)
    {
        getSemanticObject().setProperty(swbcomm_city, value);
    }

    public String getContactEmail()
    {
        return getSemanticObject().getProperty(swbcomm_contactEmail);
    }

    public void setContactEmail(String value)
    {
        getSemanticObject().setProperty(swbcomm_contactEmail, value);
    }
}
