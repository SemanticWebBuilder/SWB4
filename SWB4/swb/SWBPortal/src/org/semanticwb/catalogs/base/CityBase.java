package org.semanticwb.catalogs.base;


public class CityBase extends org.semanticwb.catalogs.LocationEntity 
{
    public static final org.semanticwb.platform.SemanticProperty swbc_cityName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#cityName");
    public static final org.semanticwb.platform.SemanticClass swbc_LocationEntity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#LocationEntity");
    public static final org.semanticwb.platform.SemanticProperty swbc_cityBelogsTo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#cityBelogsTo");
    public static final org.semanticwb.platform.SemanticClass swbc_City=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#City");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#City");

    public CityBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.catalogs.City> listCitys(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.catalogs.City>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.catalogs.City> listCitys()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.catalogs.City>(it, true);
    }

    public static org.semanticwb.catalogs.City createCity(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.catalogs.City.createCity(String.valueOf(id), model);
    }

    public static org.semanticwb.catalogs.City getCity(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.catalogs.City)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.catalogs.City createCity(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.catalogs.City)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeCity(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasCity(String id, org.semanticwb.model.SWBModel model)
    {
        return (getCity(id, model)!=null);
    }

    public String getCityName()
    {
        return getSemanticObject().getProperty(swbc_cityName);
    }

    public void setCityName(String cityName)
    {
        getSemanticObject().setProperty(swbc_cityName, cityName);
    }

    public void setCityBelogsTo(org.semanticwb.catalogs.LocationEntity locationentity)
    {
        getSemanticObject().setObjectProperty(swbc_cityBelogsTo, locationentity.getSemanticObject());
    }

    public void removeCityBelogsTo()
    {
        getSemanticObject().removeProperty(swbc_cityBelogsTo);
    }

    public org.semanticwb.catalogs.LocationEntity getCityBelogsTo()
    {
         org.semanticwb.catalogs.LocationEntity ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbc_cityBelogsTo);
         if(obj!=null)
         {
             ret=(org.semanticwb.catalogs.LocationEntity)obj.createGenericInstance();
         }
         return ret;
    }
}
