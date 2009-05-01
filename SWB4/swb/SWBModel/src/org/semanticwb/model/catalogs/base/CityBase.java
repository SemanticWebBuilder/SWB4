package org.semanticwb.model.catalogs.base;


public class CityBase extends org.semanticwb.model.catalogs.LocationEntity 
{
    public static final org.semanticwb.platform.SemanticClass swbc_PostalCode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#PostalCode");
    public static final org.semanticwb.platform.SemanticProperty swbc_postalCode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#postalCode");
    public static final org.semanticwb.platform.SemanticClass swbc_County=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#County");
    public static final org.semanticwb.platform.SemanticProperty swbc_county=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#county");
    public static final org.semanticwb.platform.SemanticClass swbc_City=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#City");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#City");

    public CityBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.model.catalogs.City> listCitys(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.catalogs.City>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.catalogs.City> listCitys()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.catalogs.City>(it, true);
    }

    public static org.semanticwb.model.catalogs.City createCity(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.model.catalogs.City.createCity(String.valueOf(id), model);
    }

    public static org.semanticwb.model.catalogs.City getCity(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.catalogs.City)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.model.catalogs.City createCity(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.catalogs.City)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeCity(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasCity(String id, org.semanticwb.model.SWBModel model)
    {
        return (getCity(id, model)!=null);
    }

    public void setPostalCode(org.semanticwb.model.catalogs.PostalCode postalcode)
    {
        getSemanticObject().setObjectProperty(swbc_postalCode, postalcode.getSemanticObject());
    }

    public void removePostalCode()
    {
        getSemanticObject().removeProperty(swbc_postalCode);
    }

    public org.semanticwb.model.catalogs.PostalCode getPostalCode()
    {
         org.semanticwb.model.catalogs.PostalCode ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbc_postalCode);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.catalogs.PostalCode)obj.createGenericInstance();
         }
         return ret;
    }

    public void setCounty(org.semanticwb.model.catalogs.County county)
    {
        getSemanticObject().setObjectProperty(swbc_county, county.getSemanticObject());
    }

    public void removeCounty()
    {
        getSemanticObject().removeProperty(swbc_county);
    }

    public org.semanticwb.model.catalogs.County getCounty()
    {
         org.semanticwb.model.catalogs.County ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbc_county);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.catalogs.County)obj.createGenericInstance();
         }
         return ret;
    }
}
