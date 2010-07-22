package org.semanticwb.model.base;


public abstract class ReferenceBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Activeable
{
    public static final org.semanticwb.platform.SemanticClass swb_Reference=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Reference");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Reference");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.Reference> listReferences(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Reference>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.Reference> listReferences()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Reference>(it, true);
        }

        public static org.semanticwb.model.Reference createReference(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.Reference.ClassMgr.createReference(String.valueOf(id), model);
        }

        public static org.semanticwb.model.Reference getReference(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Reference)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.Reference createReference(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Reference)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeReference(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasReference(String id, org.semanticwb.model.SWBModel model)
        {
            return (getReference(id, model)!=null);
        }
    }

    public ReferenceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_active, value);
    }
}
