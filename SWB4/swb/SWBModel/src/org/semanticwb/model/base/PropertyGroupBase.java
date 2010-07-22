package org.semanticwb.model.base;


public abstract class PropertyGroupBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Sortable
{
    public static final org.semanticwb.platform.SemanticClass swbxf_PropertyGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#PropertyGroup");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#PropertyGroup");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.PropertyGroup> listPropertyGroups(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PropertyGroup>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.PropertyGroup> listPropertyGroups()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PropertyGroup>(it, true);
        }

        public static org.semanticwb.model.PropertyGroup getPropertyGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.PropertyGroup)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.PropertyGroup createPropertyGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.PropertyGroup)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removePropertyGroup(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasPropertyGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPropertyGroup(id, model)!=null);
        }
    }

    public PropertyGroupBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public int getIndex()
    {
        return getSemanticObject().getIntProperty(swb_index);
    }

    public void setIndex(int value)
    {
        getSemanticObject().setIntProperty(swb_index, value);
    }
}
