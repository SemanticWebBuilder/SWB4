package org.semanticwb.catalogs.base;


public class StateBase extends org.semanticwb.catalogs.LocationEntity 
{
    public static final org.semanticwb.platform.SemanticClass swbc_LocationEntity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#LocationEntity");
    public static final org.semanticwb.platform.SemanticProperty swbc_stateBelongsTo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#stateBelongsTo");
    public static final org.semanticwb.platform.SemanticProperty swbc_stateName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#stateName");
    public static final org.semanticwb.platform.SemanticProperty swbc_stateCode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org/swbcatalogs#stateCode");
    public static final org.semanticwb.platform.SemanticClass swbc_State=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#State");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#State");

    public StateBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.catalogs.State> listStates(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.catalogs.State>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.catalogs.State> listStates()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.catalogs.State>(it, true);
    }

    public static org.semanticwb.catalogs.State getState(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.catalogs.State)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.catalogs.State createState(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.catalogs.State)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeState(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasState(String id, org.semanticwb.model.SWBModel model)
    {
        return (getState(id, model)!=null);
    }

    public void setStateBelongsTo(org.semanticwb.catalogs.LocationEntity locationentity)
    {
        getSemanticObject().setObjectProperty(swbc_stateBelongsTo, locationentity.getSemanticObject());
    }

    public void removeStateBelongsTo()
    {
        getSemanticObject().removeProperty(swbc_stateBelongsTo);
    }

    public org.semanticwb.catalogs.LocationEntity getStateBelongsTo()
    {
         org.semanticwb.catalogs.LocationEntity ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbc_stateBelongsTo);
         if(obj!=null)
         {
             ret=(org.semanticwb.catalogs.LocationEntity)obj.createGenericInstance();
         }
         return ret;
    }

    public String getStateName()
    {
        return getSemanticObject().getProperty(swbc_stateName);
    }

    public void setStateName(String stateName)
    {
        getSemanticObject().setProperty(swbc_stateName, stateName);
    }

    public String getStateCode()
    {
        return getSemanticObject().getProperty(swbc_stateCode);
    }

    public void setStateCode(String stateCode)
    {
        getSemanticObject().setProperty(swbc_stateCode, stateCode);
    }
}
