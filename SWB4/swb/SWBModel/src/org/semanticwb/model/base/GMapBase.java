package org.semanticwb.model.base;


public abstract class GMapBase extends org.semanticwb.model.base.FormElementBase 
{
    public static final org.semanticwb.platform.SemanticProperty swbxf_initLatitude=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#initLatitude");
    public static final org.semanticwb.platform.SemanticProperty swb_initLongitude=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#initLongitude");
    public static final org.semanticwb.platform.SemanticProperty swb_initStep=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#initStep");
    public static final org.semanticwb.platform.SemanticClass swbxf_GMap=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#GMap");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#GMap");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.GMap> listGMaps(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.GMap>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.GMap> listGMaps()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.GMap>(it, true);
        }

        public static org.semanticwb.model.GMap getGMap(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.GMap)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.GMap createGMap(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.GMap)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeGMap(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasGMap(String id, org.semanticwb.model.SWBModel model)
        {
            return (getGMap(id, model)!=null);
        }
    }

    public GMapBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public double getInitLatitude()
    {
        return getSemanticObject().getDoubleProperty(swbxf_initLatitude);
    }

    public void setInitLatitude(double value)
    {
        getSemanticObject().setDoubleProperty(swbxf_initLatitude, value);
    }

    public double getInitLongitude()
    {
        return getSemanticObject().getDoubleProperty(swb_initLongitude);
    }

    public void setInitLongitude(double value)
    {
        getSemanticObject().setDoubleProperty(swb_initLongitude, value);
    }

    public int getInitStep()
    {
        return getSemanticObject().getIntProperty(swb_initStep);
    }

    public void setInitStep(int value)
    {
        getSemanticObject().setIntProperty(swb_initStep, value);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);
    }
}
