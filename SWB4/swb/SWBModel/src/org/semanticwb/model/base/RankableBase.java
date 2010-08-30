package org.semanticwb.model.base;

   /**
   * Interfaz que define propiedades para los elementos que pueden ser valorados 
   */
public interface RankableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swb_reviews=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#reviews");
    public static final org.semanticwb.platform.SemanticProperty swb_rank=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#rank");
   /**
   * Interfaz que define propiedades para los elementos que pueden ser valorados 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Rankable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Rankable");

    public long getReviews();

    public void setReviews(long value);

    public double getRank();

    public void setRank(double value);
}
