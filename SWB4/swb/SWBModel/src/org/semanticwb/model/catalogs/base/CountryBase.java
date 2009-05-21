package org.semanticwb.model.catalogs.base;


public class CountryBase extends org.semanticwb.model.catalogs.LocationEntity implements org.semanticwb.model.catalogs.GeoTaggable,org.semanticwb.model.Viewable,org.semanticwb.model.Rankable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Trashable,org.semanticwb.model.Undeleteable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.Filterable,org.semanticwb.model.Calendarable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.Hiddenable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Indexable,org.semanticwb.model.Activeable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Referensable,org.semanticwb.model.Resourceable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swbc_State=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#State");
    public static final org.semanticwb.platform.SemanticProperty swbc_hasStateInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#hasStateInv");
    public static final org.semanticwb.platform.SemanticProperty swbc_countryCode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#countryCode");
    public static final org.semanticwb.platform.SemanticClass swbc_Country=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#Country");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#Country");

    public CountryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.model.catalogs.Country> listCountrys(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.catalogs.Country>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.catalogs.Country> listCountrys()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.catalogs.Country>(it, true);
    }

    public static org.semanticwb.model.catalogs.Country getCountry(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.catalogs.Country)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.model.catalogs.Country createCountry(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.catalogs.Country)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeCountry(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasCountry(String id, org.semanticwb.model.SWBModel model)
    {
        return (getCountry(id, model)!=null);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.catalogs.State> listStates()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.catalogs.State>(getSemanticObject().listObjectProperties(swbc_hasStateInv));
    }

    public boolean hasState(org.semanticwb.model.catalogs.State state)
    {
        if(state==null)return false;        return getSemanticObject().hasObjectProperty(swbc_hasStateInv,state.getSemanticObject());
    }

    public org.semanticwb.model.catalogs.State getState()
    {
         org.semanticwb.model.catalogs.State ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbc_hasStateInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.catalogs.State)obj.createGenericInstance();
         }
         return ret;
    }

    public String getCountryCode()
    {
        return getSemanticObject().getProperty(swbc_countryCode);
    }

    public void setCountryCode(String countryCode)
    {
        getSemanticObject().setProperty(swbc_countryCode, countryCode);
    }
}
