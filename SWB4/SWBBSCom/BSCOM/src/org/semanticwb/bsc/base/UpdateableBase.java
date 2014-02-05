package org.semanticwb.bsc.base;

   /**
   * Cualquier elemento BSC que se pueda actualizar periodicamente 
   */
public interface UpdateableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Indicación de las recomendaciones para el elemento correspondiente 
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_recommendations=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#recommendations");
   /**
   * Anotaciones del análisis realizado al estado del elemento correspondiente 
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_analysis=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#analysis");
   /**
   * Cualquier elemento BSC que se pueda actualizar periodicamente 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Updateable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Updateable");

    public String getRecommendations();

    public void setRecommendations(String value);

    public String getAnalysis();

    public void setAnalysis(String value);
}
