package org.semanticwb.process.model.base;

public interface EventDefinibleBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_EventDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#EventDefinition");
    public static final org.semanticwb.platform.SemanticProperty swp_hasEventDefinitionRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasEventDefinitionRef");
    public static final org.semanticwb.platform.SemanticProperty swp_hasEventDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasEventDefinition");
    public static final org.semanticwb.platform.SemanticClass swp_EventDefinible=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#EventDefinible");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventDefinition> listEventDefinitionRefs();
    public boolean hasEventDefinitionRef(org.semanticwb.process.model.EventDefinition value);

    public void addEventDefinitionRef(org.semanticwb.process.model.EventDefinition value);

    public void removeAllEventDefinitionRef();

    public void removeEventDefinitionRef(org.semanticwb.process.model.EventDefinition value);

    public org.semanticwb.process.model.EventDefinition getEventDefinitionRef();

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventDefinition> listEventDefinitions();
    public boolean hasEventDefinition(org.semanticwb.process.model.EventDefinition value);

    public void addEventDefinition(org.semanticwb.process.model.EventDefinition value);

    public void removeAllEventDefinition();

    public void removeEventDefinition(org.semanticwb.process.model.EventDefinition value);

    public org.semanticwb.process.model.EventDefinition getEventDefinition();
}
