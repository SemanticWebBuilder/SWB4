package org.semanticwb.catalogs.base;


public class CountryBase extends org.semanticwb.catalogs.LocationEntity 
{
    public static final org.semanticwb.platform.SemanticProperty swbc_countryCode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#countryCode");
    public static final org.semanticwb.platform.SemanticProperty swbc_countryName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#countryName");
    public static final org.semanticwb.platform.SemanticClass swbc_Country=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#Country");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#Country");

    public CountryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.catalogs.Country> listCountrys(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.catalogs.Country>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.catalogs.Country> listCountrys()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.catalogs.Country>(it, true);
    }

    public static org.semanticwb.catalogs.Country getCountry(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.catalogs.Country)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.catalogs.Country createCountry(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.catalogs.Country)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeCountry(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasCountry(String id, org.semanticwb.model.SWBModel model)
    {
        return (getCountry(id, model)!=null);
    }

    public String getCountryCode()
    {
        return getSemanticObject().getProperty(swbc_countryCode);
    }

    public void setCountryCode(String countryCode)
    {
        getSemanticObject().setProperty(swbc_countryCode, countryCode);
    }

    public String getCountryName()
    {
        return getSemanticObject().getProperty(swbc_countryName);
    }

    public void setCountryName(String countryName)
    {
        getSemanticObject().setProperty(swbc_countryName, countryName);
    }
}
