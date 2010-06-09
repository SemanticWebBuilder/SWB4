package org.semanticwb.model.base;


public abstract class SelectMultipleBase extends org.semanticwb.model.base.FormElementBase 
{
    public static final org.semanticwb.platform.SemanticProperty swbxf_sm_userRepository=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#sm_userRepository");
    public static final org.semanticwb.platform.SemanticProperty swbxf_sm_globalScope=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#sm_globalScope");
    public static final org.semanticwb.platform.SemanticProperty swbxf_sm_nullSuport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#sm_nullSuport");
    public static final org.semanticwb.platform.SemanticClass swbxf_SelectMultiple=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#SelectMultiple");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#SelectMultiple");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.SelectMultiple> listSelectMultiples(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SelectMultiple>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.SelectMultiple> listSelectMultiples()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SelectMultiple>(it, true);
        }

        public static org.semanticwb.model.SelectMultiple getSelectMultiple(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SelectMultiple)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.SelectMultiple createSelectMultiple(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SelectMultiple)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeSelectMultiple(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasSelectMultiple(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSelectMultiple(id, model)!=null);
        }
    }

    public SelectMultipleBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public boolean isSm_userRepository()
    {
        return getSemanticObject().getBooleanProperty(swbxf_sm_userRepository);
    }

    public void setSm_userRepository(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_sm_userRepository, value);
    }

    public boolean isGlobalScope()
    {
        return getSemanticObject().getBooleanProperty(swbxf_sm_globalScope);
    }

    public void setGlobalScope(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_sm_globalScope, value);
    }

    public boolean isNullSupport()
    {
        return getSemanticObject().getBooleanProperty(swbxf_sm_nullSuport);
    }

    public void setNullSupport(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_sm_nullSuport, value);
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
