package org.semanticwb.repository;

public interface ChildNodeDefinition extends org.semanticwb.repository.CommonPropertiesforDefinition,org.semanticwb.repository.Nameable
{
    public static final org.semanticwb.platform.SemanticProperty jcr_defaultPrimaryType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#defaultPrimaryType");
    public static final org.semanticwb.platform.SemanticProperty jcr_sameNameSiblings=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#sameNameSiblings");
    public static final org.semanticwb.platform.SemanticProperty jcr_requiredPrimaryTypes=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#requiredPrimaryTypes");
    public static final org.semanticwb.platform.SemanticClass swbrep_ChildNodeDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#ChildNodeDefinition");
}
