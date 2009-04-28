package org.semanticwb.catalogs.base;


public class CountyBase extends org.semanticwb.catalogs.LocationEntity 
{
    public static final org.semanticwb.platform.SemanticClass swbc_LocationEntity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#LocationEntity");
    public static final org.semanticwb.platform.SemanticProperty swbc_countyBelongsTo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#countyBelongsTo");
    public static final org.semanticwb.platform.SemanticProperty swbc_countyName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#countyName");
    public static final org.semanticwb.platform.SemanticProperty swbc_countyCode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#countyCode");
    public static final org.semanticwb.platform.SemanticClass swbc_County=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#County");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#County");

    public CountyBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.catalogs.County> listCountys(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.catalogs.County>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.catalogs.County> listCountys()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.catalogs.County>(it, true);
    }

    public static org.semanticwb.catalogs.County getCounty(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.catalogs.County)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.catalogs.County createCounty(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.catalogs.County)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeCounty(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasCounty(String id, org.semanticwb.model.SWBModel model)
    {
        return (getCounty(id, model)!=null);
    }

    public void setCountyBelongsTo(org.semanticwb.catalogs.LocationEntity locationentity)
    {
        getSemanticObject().setObjectProperty(swbc_countyBelongsTo, locationentity.getSemanticObject());
    }

    public void removeCountyBelongsTo()
    {
        getSemanticObject().removeProperty(swbc_countyBelongsTo);
    }

    public org.semanticwb.catalogs.LocationEntity getCountyBelongsTo()
    {
         org.semanticwb.catalogs.LocationEntity ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbc_countyBelongsTo);
         if(obj!=null)
         {
             ret=(org.semanticwb.catalogs.LocationEntity)obj.createGenericInstance();
         }
         return ret;
    }

    public String getCountyName()
    {
        return getSemanticObject().getProperty(swbc_countyName);
    }

    public void setCountyName(String countyName)
    {
        getSemanticObject().setProperty(swbc_countyName, countyName);
    }

    public String getCountyCode()
    {
        return getSemanticObject().getProperty(swbc_countyCode);
    }

    public void setCountyCode(String countyCode)
    {
        getSemanticObject().setProperty(swbc_countyCode, countyCode);
    }
}
