package org.semanticwb.model.base;

   /**
   * Interfaz que define propiedades para elementos que pueden ser contables 
   */
public interface HitableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swb_maxHits=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#maxHits");
    public static final org.semanticwb.platform.SemanticProperty swb_hits=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hits");
   /**
   * Interfaz que define propiedades para elementos que pueden ser contables 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Hitable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Hitable");

    public long getMaxHits();

    public void setMaxHits(long value);

    public long getHits();

    public void setHits(long value);
}
