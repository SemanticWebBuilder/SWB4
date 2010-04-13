package org.semanticwb.process.model.base;

public interface InteractableBase extends org.semanticwb.process.model.MessageFlowable,org.semanticwb.process.model.ParticipantReferensable,org.semanticwb.process.model.Documentable
{
    public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
    public static final org.semanticwb.platform.SemanticClass swp_ExtensionDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ExtensionDefinition");
    public static final org.semanticwb.platform.SemanticProperty swp_hasExtensionDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasExtensionDefinition");
    public static final org.semanticwb.platform.SemanticClass swp_ExtensionAttributeValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ExtensionAttributeValue");
    public static final org.semanticwb.platform.SemanticProperty swp_hasExtensionValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasExtensionValue");
    public static final org.semanticwb.platform.SemanticClass swp_Interactable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Interactable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Documentation> listDocumentations();
    public boolean hasDocumentation(org.semanticwb.process.model.Documentation value);

    public void addDocumentation(org.semanticwb.process.model.Documentation value);

    public void removeAllDocumentation();

    public void removeDocumentation(org.semanticwb.process.model.Documentation value);

    public org.semanticwb.process.model.Documentation getDocumentation();

    public boolean isValid();

    public void setValid(boolean value);

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExtensionDefinition> listExtensionDefinitions();
    public boolean hasExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value);

    public void addExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value);

    public void removeAllExtensionDefinition();

    public void removeExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value);

    public org.semanticwb.process.model.ExtensionDefinition getExtensionDefinition();

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExtensionAttributeValue> listExtensionValues();
    public boolean hasExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value);

    public void addExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value);

    public void removeAllExtensionValue();

    public void removeExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value);

    public org.semanticwb.process.model.ExtensionAttributeValue getExtensionValue();
}
