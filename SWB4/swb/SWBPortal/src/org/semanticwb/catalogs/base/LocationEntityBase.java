package org.semanticwb.catalogs.base;


public class LocationEntityBase extends org.semanticwb.catalogs.Catalogs 
{
    public static final org.semanticwb.platform.SemanticClass swbc_LocationEntity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#LocationEntity");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#LocationEntity");

    public LocationEntityBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.catalogs.LocationEntity> listLocationEntitys(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.catalogs.LocationEntity>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.catalogs.LocationEntity> listLocationEntitys()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.catalogs.LocationEntity>(it, true);
    }

    public static org.semanticwb.catalogs.LocationEntity getLocationEntity(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.catalogs.LocationEntity)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.catalogs.LocationEntity createLocationEntity(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.catalogs.LocationEntity)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeLocationEntity(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasLocationEntity(String id, org.semanticwb.model.SWBModel model)
    {
        return (getLocationEntity(id, model)!=null);
    }
}
