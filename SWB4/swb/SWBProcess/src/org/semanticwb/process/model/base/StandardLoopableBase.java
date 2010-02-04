package org.semanticwb.process.model.base;

public interface StandardLoopableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swp_testTime=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#testTime");
    public static final org.semanticwb.platform.SemanticProperty swp_loopCounter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#loopCounter");
    public static final org.semanticwb.platform.SemanticProperty swp_loopMaximum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#loopMaximum");
    public static final org.semanticwb.platform.SemanticProperty swp_loopType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#loopType");
    public static final org.semanticwb.platform.SemanticClass swp_Expression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Expression");
    public static final org.semanticwb.platform.SemanticProperty swp_loopCondition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#loopCondition");
    public static final org.semanticwb.platform.SemanticClass swp_StandardLoopable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#StandardLoopable");

    public String getTestTime();

    public void setTestTime(String value);

    public int getLoopCounter();

    public void setLoopCounter(int value);

    public int getLoopMaximum();

    public void setLoopMaximum(int value);

    public String getLoopType();

    public void setLoopType(String value);

    public void setLoopCondition(org.semanticwb.process.model.Expression expression);

    public void removeLoopCondition();

    public org.semanticwb.process.model.Expression getLoopCondition();
}
