package org.semanticwb.jcr283.repository.model.base;


public abstract class ChildNodeDefinitionBase extends org.semanticwb.jcr283.repository.model.NodeTypes 
{
       public static final org.semanticwb.platform.SemanticClass xsd_String=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.w3.org/2001/XMLSchema#string");
       public static final org.semanticwb.platform.SemanticProperty jcr_defaultPrimaryType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#defaultPrimaryType");
       public static final org.semanticwb.platform.SemanticProperty jcr_requiredPrimaryTypes=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#requiredPrimaryTypes");
       public static final org.semanticwb.platform.SemanticClass nt_ChildNodeDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#childNodeDefinition");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#childNodeDefinition");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.jcr283.repository.model.ChildNodeDefinition> listChildNodeDefinitions(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.ChildNodeDefinition>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.jcr283.repository.model.ChildNodeDefinition> listChildNodeDefinitions()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.ChildNodeDefinition>(it, true);
       }

       public static org.semanticwb.jcr283.repository.model.ChildNodeDefinition getChildNodeDefinition(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.jcr283.repository.model.ChildNodeDefinition)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.jcr283.repository.model.ChildNodeDefinition createChildNodeDefinition(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.jcr283.repository.model.ChildNodeDefinition)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeChildNodeDefinition(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasChildNodeDefinition(String id, org.semanticwb.model.SWBModel model)
       {
           return (getChildNodeDefinition(id, model)!=null);
       }
    }

    public ChildNodeDefinitionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getDefaultPrimaryType()
    {
        return getSemanticObject().getProperty(jcr_defaultPrimaryType);
    }

    public void setDefaultPrimaryType(String value)
    {
        getSemanticObject().setProperty(jcr_defaultPrimaryType, value);
    }

    public String getRequiredPrimaryTypes()
    {
        return getSemanticObject().getProperty(jcr_requiredPrimaryTypes);
    }

    public void setRequiredPrimaryTypes(String value)
    {
        getSemanticObject().setProperty(jcr_requiredPrimaryTypes, value);
    }
}
