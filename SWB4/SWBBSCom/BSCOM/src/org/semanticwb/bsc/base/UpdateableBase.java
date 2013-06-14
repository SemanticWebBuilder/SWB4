package org.semanticwb.bsc.base;

   /**
   * Cualquier elemento BSC que se pueda actualizar periodicamente 
   */
public interface UpdateableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty bsc_percentageProgress=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#percentageProgress");
    public static final org.semanticwb.platform.SemanticProperty bsc_analysis=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#analysis");
    public static final org.semanticwb.platform.SemanticProperty bsc_recommendations=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#recommendations");
   /**
   * Cualquier elemento BSC que se pueda actualizar periodicamente 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Updateable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Updateable");

    public float getPercentageProgress();

    public void setPercentageProgress(float value);

    public String getAnalysis();

    public void setAnalysis(String value);

    public String getRecommendations();

    public void setRecommendations(String value);
}
