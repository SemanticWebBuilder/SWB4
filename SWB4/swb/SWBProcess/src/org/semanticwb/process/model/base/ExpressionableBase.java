package org.semanticwb.process.model.base;

public interface ExpressionableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_Expression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Expression");
    public static final org.semanticwb.platform.SemanticProperty swp_expression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#expression");
    public static final org.semanticwb.platform.SemanticClass swp_Expressionable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Expressionable");

    public void setExpression(org.semanticwb.process.model.Expression expression);

    public void removeExpression();

    public org.semanticwb.process.model.Expression getExpression();
}
