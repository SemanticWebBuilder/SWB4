package org.semanticwb.model.base;

   /**
   * Interfaz que define propiedades para elementos que pertenecen a una campaña 
   */
public interface CampableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Define una campaña de recursos o componentes de SemanticWebBuilder (No usada de momento) 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Camp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Camp");
    public static final org.semanticwb.platform.SemanticProperty swb_camp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#camp");
   /**
   * Interfaz que define propiedades para elementos que pertenecen a una campaña 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Campable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Campable");

   /**
   * Sets a value from the property Camp
   * @param value An instance of org.semanticwb.model.Camp
   */
    public void setCamp(org.semanticwb.model.Camp value);

   /**
   * Remove the value from the property Camp
   */
    public void removeCamp();

    public org.semanticwb.model.Camp getCamp();
}
