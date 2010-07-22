package org.semanticwb.model.base;


public abstract class SWBClassBase extends org.semanticwb.model.base.GenericObjectBase 
{
    public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
    public static final org.semanticwb.platform.SemanticClass swb_SWBClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBClass");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBClass");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.SWBClass> listSWBClasses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SWBClass>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.SWBClass> listSWBClasses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SWBClass>(it, true);
        }

        public static org.semanticwb.model.SWBClass getSWBClass(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SWBClass)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.SWBClass createSWBClass(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SWBClass)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeSWBClass(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasSWBClass(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSWBClass(id, model)!=null);
        }
    }

    public SWBClassBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public boolean isValid()
    {
        //Override this method in SWBClass object
        return getSemanticObject().getBooleanProperty(swb_valid,false);
    }

    public void setValid(boolean value)
    {
        //Override this method in SWBClass object
        getSemanticObject().setBooleanProperty(swb_valid, value,false);
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
