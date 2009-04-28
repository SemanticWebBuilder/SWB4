package org.semanticwb.catalogs.base;


public class PostalCodeBase extends org.semanticwb.catalogs.Catalogs 
{
    public static final org.semanticwb.platform.SemanticClass swbc_City=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#City");
    public static final org.semanticwb.platform.SemanticProperty swbc_hasCity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#hasCity");
    public static final org.semanticwb.platform.SemanticProperty swbc_id=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#id");
    public static final org.semanticwb.platform.SemanticClass swbc_PostalCode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#PostalCode");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#PostalCode");

    public PostalCodeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.catalogs.PostalCode> listPostalCodes(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.catalogs.PostalCode>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.catalogs.PostalCode> listPostalCodes()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.catalogs.PostalCode>(it, true);
    }

    public static org.semanticwb.catalogs.PostalCode getPostalCode(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.catalogs.PostalCode)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.catalogs.PostalCode createPostalCode(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.catalogs.PostalCode)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removePostalCode(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasPostalCode(String id, org.semanticwb.model.SWBModel model)
    {
        return (getPostalCode(id, model)!=null);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.catalogs.City> listCitys()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.catalogs.City>(getSemanticObject().listObjectProperties(swbc_hasCity));
    }

    public boolean hasCity(org.semanticwb.catalogs.City city)
    {
        if(city==null)return false;        return getSemanticObject().hasObjectProperty(swbc_hasCity,city.getSemanticObject());
    }

    public void addCity(org.semanticwb.catalogs.City city)
    {
        getSemanticObject().addObjectProperty(swbc_hasCity, city.getSemanticObject());
    }

    public void removeAllCity()
    {
        getSemanticObject().removeProperty(swbc_hasCity);
    }

    public void removeCity(org.semanticwb.catalogs.City city)
    {
        getSemanticObject().removeObjectProperty(swbc_hasCity,city.getSemanticObject());
    }

    public org.semanticwb.catalogs.City getCity()
    {
         org.semanticwb.catalogs.City ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbc_hasCity);
         if(obj!=null)
         {
             ret=(org.semanticwb.catalogs.City)obj.createGenericInstance();
         }
         return ret;
    }

    public String getId()
    {
        return getSemanticObject().getProperty(swbc_id);
    }

    public void setId(String id)
    {
        getSemanticObject().setProperty(swbc_id, id);
    }
}
