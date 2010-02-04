package org.semanticwb.process.model.base;

public interface AdHocableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_Expression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Expression");
    public static final org.semanticwb.platform.SemanticProperty swp_adHocCompletionCondition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#adHocCompletionCondition");
    public static final org.semanticwb.platform.SemanticProperty swp_adHocOrdering=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#adHocOrdering");
    public static final org.semanticwb.platform.SemanticProperty swp_adHoc=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#adHoc");
    public static final org.semanticwb.platform.SemanticClass swp_AdHocable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#AdHocable");

    public void setAdHocCompletionCondition(org.semanticwb.process.model.Expression expression);

    public void removeAdHocCompletionCondition();

    public org.semanticwb.process.model.Expression getAdHocCompletionCondition();

    public String getAdHocOrdering();

    public void setAdHocOrdering(String value);

    public boolean isAdHoc();

    public void setAdHoc(boolean value);
}
