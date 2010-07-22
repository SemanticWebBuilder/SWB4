package org.semanticwb.model.base;


public abstract class SelectOneBase extends org.semanticwb.model.base.FormElementBase 
{
    public static final org.semanticwb.platform.SemanticProperty swbxf_so_globalScope=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#so_globalScope");
    public static final org.semanticwb.platform.SemanticProperty swbxf_so_userRepository=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#so_userRepository");
    public static final org.semanticwb.platform.SemanticProperty swbxf_so_nullSuport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#so_nullSuport");
    public static final org.semanticwb.platform.SemanticClass swbxf_SelectOne=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#SelectOne");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#SelectOne");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.SelectOne> listSelectOnes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SelectOne>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.SelectOne> listSelectOnes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SelectOne>(it, true);
        }

        public static org.semanticwb.model.SelectOne getSelectOne(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SelectOne)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.SelectOne createSelectOne(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SelectOne)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeSelectOne(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasSelectOne(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSelectOne(id, model)!=null);
        }
    }

    public SelectOneBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public boolean isGlobalScope()
    {
        return getSemanticObject().getBooleanProperty(swbxf_so_globalScope);
    }

    public void setGlobalScope(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_so_globalScope, value);
    }

    public boolean isUserRepository()
    {
        return getSemanticObject().getBooleanProperty(swbxf_so_userRepository);
    }

    public void setUserRepository(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_so_userRepository, value);
    }

    public boolean isBlankSuport()
    {
        return getSemanticObject().getBooleanProperty(swbxf_so_nullSuport);
    }

    public void setBlankSuport(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_so_nullSuport, value);
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
