package org.semanticwb.process.model.base;

public interface ConditionableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_Expression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Expression");
    public static final org.semanticwb.platform.SemanticProperty swp_conditionExpression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#conditionExpression");
    public static final org.semanticwb.platform.SemanticClass swp_Conditionable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Conditionable");

    public void setConditionExpression(org.semanticwb.process.model.Expression expression);

    public void removeConditionExpression();

    public org.semanticwb.process.model.Expression getConditionExpression();
}
