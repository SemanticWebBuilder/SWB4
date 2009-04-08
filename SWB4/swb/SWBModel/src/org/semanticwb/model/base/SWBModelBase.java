package org.semanticwb.model.base;


public class SWBModelBase extends org.semanticwb.model.base.GenericObjectBase 
{
    public static final org.semanticwb.platform.SemanticClass swb_SWBModel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBModel");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBModel");

    public SWBModelBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.model.SWBModel> listSWBModels(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SWBModel>(org.semanticwb.model.SWBModel.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.SWBModel> listSWBModels()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SWBModel>(org.semanticwb.model.SWBModel.class, it, true);
    }

    public static org.semanticwb.model.SWBModel getSWBModel(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.SWBModel)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.model.SWBModel createSWBModel(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.SWBModel)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeSWBModel(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasSWBModel(String id, org.semanticwb.model.SWBModel model)
    {
        return (getSWBModel(id, model)!=null);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator((org.semanticwb.platform.SemanticClass)null, getSemanticObject().listRelatedObjects(),true);
    }
}
