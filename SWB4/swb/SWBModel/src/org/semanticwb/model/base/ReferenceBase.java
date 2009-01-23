package org.semanticwb.model.base;


public class ReferenceBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Activeable
{
    public static final org.semanticwb.platform.SemanticProperty swb_active=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#active");
    public static final org.semanticwb.platform.SemanticClass swb_Reference=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Reference");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Reference");

    public ReferenceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.model.Reference getReference(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.Reference)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.model.Reference> listReferences(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Reference>(org.semanticwb.model.Reference.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.Reference> listReferences()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Reference>(org.semanticwb.model.Reference.class, it, true);
    }

    public static org.semanticwb.model.Reference createReference(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.Reference)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static org.semanticwb.model.Reference createReference(org.semanticwb.model.SWBModel model)
    {
        long id=org.semanticwb.SWBPlatform.getSemanticMgr().getCounter(model.getSemanticObject().getModel().getName()+"/"+sclass.getName());
        return org.semanticwb.model.Reference.createReference(String.valueOf(id), model);
    }

    public static void removeReference(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasReference(String id, org.semanticwb.model.SWBModel model)
    {
        return (getReference(id, model)!=null);
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

    public void setActive(boolean active)
    {
        getSemanticObject().setBooleanProperty(swb_active, active);
    }
}
