package org.semanticwb.repository.base;

public interface PropertyDefinitionBase extends org.semanticwb.repository.CommonPropertiesforDefinition
{
    public static final org.semanticwb.platform.SemanticProperty jcr_valueConstraints=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#valueConstraints");
    public static final org.semanticwb.platform.SemanticProperty jcr_defaultValues=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#defaultValues");
    public static final org.semanticwb.platform.SemanticProperty jcr_requiredType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#requiredType");
    public static final org.semanticwb.platform.SemanticProperty jcr_multiple=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#multiple");
    public static final org.semanticwb.platform.SemanticProperty swbrep_internal=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#internal");
    public static final org.semanticwb.platform.SemanticClass swbrep_PropertyDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#PropertyDefinition");
}
