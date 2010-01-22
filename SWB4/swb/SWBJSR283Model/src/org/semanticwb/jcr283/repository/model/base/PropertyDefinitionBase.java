package org.semanticwb.jcr283.repository.model.base;


public abstract class PropertyDefinitionBase extends org.semanticwb.jcr283.repository.model.NodeTypes 
{
       public static final org.semanticwb.platform.SemanticClass xsd_Boolean=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.w3.org/2001/XMLSchema#boolean");
       public static final org.semanticwb.platform.SemanticProperty jcr_autoCreated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#autoCreated");
       public static final org.semanticwb.platform.SemanticProperty jcr_sameNameSiblings=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#sameNameSiblings");
       public static final org.semanticwb.platform.SemanticProperty jcr_mandatory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#mandatory");
       public static final org.semanticwb.platform.SemanticClass xsd_String=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.w3.org/2001/XMLSchema#string");
       public static final org.semanticwb.platform.SemanticProperty jcr_onParentVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#onParentVersion");
       public static final org.semanticwb.platform.SemanticProperty jcr_multiple=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#multiple");
       public static final org.semanticwb.platform.SemanticProperty jcr_protected=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#protected");
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

    public boolean isAutoCreated()
    {
        return getSemanticObject().getBooleanProperty(jcr_autoCreated);
    }

    public void setAutoCreated(boolean value)
    {
        getSemanticObject().setBooleanProperty(jcr_autoCreated, value);
    }

    public boolean isSameNameSiblings()
    {
        return getSemanticObject().getBooleanProperty(jcr_sameNameSiblings);
    }

    public void setSameNameSiblings(boolean value)
    {
        getSemanticObject().setBooleanProperty(jcr_sameNameSiblings, value);
    }

    public boolean isMandatory()
    {
        return getSemanticObject().getBooleanProperty(jcr_mandatory);
    }

    public void setMandatory(boolean value)
    {
        getSemanticObject().setBooleanProperty(jcr_mandatory, value);
    }

    public String getOnParentVersion()
    {
        return getSemanticObject().getProperty(jcr_onParentVersion);
    }

    public void setOnParentVersion(String value)
    {
        getSemanticObject().setProperty(jcr_onParentVersion, value);
    }

    public boolean isMultiple()
    {
        return getSemanticObject().getBooleanProperty(jcr_multiple);
    }

    public void setMultiple(boolean value)
    {
        getSemanticObject().setBooleanProperty(jcr_multiple, value);
    }

    public boolean isProtected()
    {
        return getSemanticObject().getBooleanProperty(jcr_protected);
    }

    public void setProtected(boolean value)
    {
        getSemanticObject().setBooleanProperty(jcr_protected, value);
    }
}
