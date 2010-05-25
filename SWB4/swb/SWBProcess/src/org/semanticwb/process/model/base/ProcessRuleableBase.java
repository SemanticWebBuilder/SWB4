package org.semanticwb.process.model.base;

public interface ProcessRuleableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swp_rule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#rule");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessRuleable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessRuleable");

    public String getRule();

    public void setRule(String value);
}
