package org.semanticwb.jcr283.repository.model.base;


public abstract class PropertyDefinitionBase extends org.semanticwb.jcr283.repository.model.NodeTypes 
{
       public static final org.semanticwb.platform.SemanticClass nt_PropertyDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#propertyDefinition");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#propertyDefinition");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.jcr283.repository.model.PropertyDefinition> listPropertyDefinitions(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.PropertyDefinition>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.jcr283.repository.model.PropertyDefinition> listPropertyDefinitions()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.PropertyDefinition>(it, true);
       }

       public static org.semanticwb.jcr283.repository.model.PropertyDefinition getPropertyDefinition(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.jcr283.repository.model.PropertyDefinition)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.jcr283.repository.model.PropertyDefinition createPropertyDefinition(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.jcr283.repository.model.PropertyDefinition)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removePropertyDefinition(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasPropertyDefinition(String id, org.semanticwb.model.SWBModel model)
       {
           return (getPropertyDefinition(id, model)!=null);
       }
    }

    public PropertyDefinitionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
