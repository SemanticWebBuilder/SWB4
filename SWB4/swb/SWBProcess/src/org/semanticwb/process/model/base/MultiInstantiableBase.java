package org.semanticwb.process.model.base;

public interface MultiInstantiableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_Expression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Expression");
    public static final org.semanticwb.platform.SemanticProperty swp_complexMIFlowCondition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#complexMIFlowCondition");
    public static final org.semanticwb.platform.SemanticProperty swp_mICondition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#mICondition");
    public static final org.semanticwb.platform.SemanticProperty swp_mIOrdering=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#mIOrdering");
    public static final org.semanticwb.platform.SemanticProperty swp_mIFlowCondition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#mIFlowCondition");
    public static final org.semanticwb.platform.SemanticClass swp_MultiInstantiable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#MultiInstantiable");

    public void setComplexMIFlowCondition(org.semanticwb.process.model.Expression expression);

    public void removeComplexMIFlowCondition();

    public org.semanticwb.process.model.Expression getComplexMIFlowCondition();

    public void setMICondition(org.semanticwb.process.model.Expression expression);

    public void removeMICondition();

    public org.semanticwb.process.model.Expression getMICondition();

    public String getMIOrdering();

    public void setMIOrdering(String value);

    public String getMIFlowCondition();

    public void setMIFlowCondition(String value);
}
