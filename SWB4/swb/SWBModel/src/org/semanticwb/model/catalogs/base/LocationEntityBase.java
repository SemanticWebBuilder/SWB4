package org.semanticwb.model.catalogs.base;


public class LocationEntityBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass swbc_LocationEntity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#LocationEntity");
    public static final org.semanticwb.platform.SemanticProperty swbc_belongsTo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#belongsTo");
    public static final org.semanticwb.platform.SemanticProperty swbc_name=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#name");
    public static final org.semanticwb.platform.SemanticProperty swbc_hasLocation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#hasLocation");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#LocationEntity");

    public LocationEntityBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.model.catalogs.LocationEntity> listLocationEntitys(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.catalogs.LocationEntity>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.catalogs.LocationEntity> listLocationEntitys()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.catalogs.LocationEntity>(it, true);
    }

    public static org.semanticwb.model.catalogs.LocationEntity getLocationEntity(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.catalogs.LocationEntity)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.model.catalogs.LocationEntity createLocationEntity(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.catalogs.LocationEntity)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeLocationEntity(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasLocationEntity(String id, org.semanticwb.model.SWBModel model)
    {
        return (getLocationEntity(id, model)!=null);
    }

    public String getName()
    {
        return getSemanticObject().getProperty(swbc_name);
    }

    public void setName(String name)
    {
        getSemanticObject().setProperty(swbc_name, name);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.catalogs.LocationEntity> listLocations()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.catalogs.LocationEntity>(getSemanticObject().listObjectProperties(swbc_hasLocation));
    }

    public boolean hasLocation(org.semanticwb.model.catalogs.LocationEntity locationentity)
    {
        if(locationentity==null)return false;        return getSemanticObject().hasObjectProperty(swbc_hasLocation,locationentity.getSemanticObject());
    }

    public org.semanticwb.model.catalogs.LocationEntity getLocation()
    {
         org.semanticwb.model.catalogs.LocationEntity ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbc_hasLocation);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.catalogs.LocationEntity)obj.createGenericInstance();
         }
         return ret;
    }
}
