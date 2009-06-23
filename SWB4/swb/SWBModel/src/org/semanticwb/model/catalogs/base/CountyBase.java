package org.semanticwb.model.catalogs.base;


public class CountyBase extends org.semanticwb.model.catalogs.LocationEntity implements org.semanticwb.model.Viewable,org.semanticwb.model.catalogs.GeoTaggable,org.semanticwb.model.Rankable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Trashable,org.semanticwb.model.Undeleteable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.Filterable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.Hiddenable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Indexable,org.semanticwb.model.Activeable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Referensable,org.semanticwb.model.Resourceable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swbc_City=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#City");
    public static final org.semanticwb.platform.SemanticProperty swbc_hasCityInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#hasCityInv");
    public static final org.semanticwb.platform.SemanticClass swbc_State=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#State");
    public static final org.semanticwb.platform.SemanticProperty swbc_state=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#state");
    public static final org.semanticwb.platform.SemanticProperty swbc_countyCode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#countyCode");
    public static final org.semanticwb.platform.SemanticClass swbc_County=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#County");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#County");

    public CountyBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.model.catalogs.County> listCountys(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.catalogs.County>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.catalogs.County> listCountys()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.catalogs.County>(it, true);
    }

    public static org.semanticwb.model.catalogs.County getCounty(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.catalogs.County)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.model.catalogs.County createCounty(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.catalogs.County)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeCounty(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasCounty(String id, org.semanticwb.model.SWBModel model)
    {
        return (getCounty(id, model)!=null);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.catalogs.City> listCitys()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.catalogs.City>(getSemanticObject().listObjectProperties(swbc_hasCityInv));
    }

    public boolean hasCity(org.semanticwb.model.catalogs.City city)
    {
        if(city==null)return false;        return getSemanticObject().hasObjectProperty(swbc_hasCityInv,city.getSemanticObject());
    }

    public org.semanticwb.model.catalogs.City getCity()
    {
         org.semanticwb.model.catalogs.City ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbc_hasCityInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.catalogs.City)obj.createGenericInstance();
         }
         return ret;
    }

    public void setState(org.semanticwb.model.catalogs.State state)
    {
        getSemanticObject().setObjectProperty(swbc_state, state.getSemanticObject());
    }

    public void removeState()
    {
        getSemanticObject().removeProperty(swbc_state);
    }

    public org.semanticwb.model.catalogs.State getState()
    {
         org.semanticwb.model.catalogs.State ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbc_state);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.catalogs.State)obj.createGenericInstance();
         }
         return ret;
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
