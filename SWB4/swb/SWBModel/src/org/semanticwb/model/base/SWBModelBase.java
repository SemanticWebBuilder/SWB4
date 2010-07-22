package org.semanticwb.model.base;


public abstract class SWBModelBase extends org.semanticwb.model.base.GenericObjectBase 
{
    public static final org.semanticwb.platform.SemanticClass swb_WebSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebSite");
    public static final org.semanticwb.platform.SemanticProperty swb_parentWebSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#parentWebSite");
    public static final org.semanticwb.platform.SemanticClass swb_SWBModel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBModel");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBModel");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.SWBModel> listSWBModels(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SWBModel>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.SWBModel> listSWBModels()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SWBModel>(it, true);
        }

        public static org.semanticwb.model.SWBModel getSWBModel(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SWBModel)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.SWBModel createSWBModel(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SWBModel)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeSWBModel(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasSWBModel(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSWBModel(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.model.SWBModel> listSWBModelByParentWebSite(org.semanticwb.model.WebSite value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.SWBModel> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.SWBModel> listSWBModelByParentWebSite(org.semanticwb.model.WebSite value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.SWBModel> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public SWBModelBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setParentWebSite(org.semanticwb.model.WebSite value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_parentWebSite, value.getSemanticObject());
        }else
        {
            removeParentWebSite();
        }
    }

    public void removeParentWebSite()
    {
        getSemanticObject().removeProperty(swb_parentWebSite);
    }

    public org.semanticwb.model.WebSite getParentWebSite()
    {
         org.semanticwb.model.WebSite ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_parentWebSite);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebSite)obj.createGenericInstance();
         }
         return ret;
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
