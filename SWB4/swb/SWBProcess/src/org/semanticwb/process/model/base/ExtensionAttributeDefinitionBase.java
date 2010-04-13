package org.semanticwb.process.model.base;


public abstract class ExtensionAttributeDefinitionBase extends org.semanticwb.process.model.BPMNElement implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty swp_attributeType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#attributeType");
    public static final org.semanticwb.platform.SemanticProperty swp_reference=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#reference");
    public static final org.semanticwb.platform.SemanticClass swp_ExtensionAttributeDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ExtensionAttributeDefinition");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ExtensionAttributeDefinition");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.ExtensionAttributeDefinition> listExtensionAttributeDefinitions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExtensionAttributeDefinition>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ExtensionAttributeDefinition> listExtensionAttributeDefinitions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExtensionAttributeDefinition>(it, true);
        }

        public static org.semanticwb.process.model.ExtensionAttributeDefinition createExtensionAttributeDefinition(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ExtensionAttributeDefinition.ClassMgr.createExtensionAttributeDefinition(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.ExtensionAttributeDefinition getExtensionAttributeDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ExtensionAttributeDefinition)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.ExtensionAttributeDefinition createExtensionAttributeDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ExtensionAttributeDefinition)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeExtensionAttributeDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasExtensionAttributeDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            return (getExtensionAttributeDefinition(id, model)!=null);
        }
    }

    public ExtensionAttributeDefinitionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    public String getAttributeType()
    {
        return getSemanticObject().getProperty(swp_attributeType);
    }

    public void setAttributeType(String value)
    {
        getSemanticObject().setProperty(swp_attributeType, value);
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

    public boolean isReference()
    {
        return getSemanticObject().getBooleanProperty(swp_reference);
    }

    public void setReference(boolean value)
    {
        getSemanticObject().setBooleanProperty(swp_reference, value);
    }
}
