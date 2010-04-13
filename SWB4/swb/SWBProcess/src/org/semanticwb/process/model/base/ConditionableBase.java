package org.semanticwb.process.model.base;

public interface ConditionableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_FormalExpression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#FormalExpression");
    public static final org.semanticwb.platform.SemanticProperty swp_condition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#condition");
    public static final org.semanticwb.platform.SemanticClass swp_Conditionable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Conditionable");

    public void setCondition(org.semanticwb.process.model.FormalExpression formalexpression);

    public void removeCondition();

    public org.semanticwb.process.model.FormalExpression getCondition();
}
