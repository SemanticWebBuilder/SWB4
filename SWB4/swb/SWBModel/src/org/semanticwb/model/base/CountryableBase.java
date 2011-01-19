package org.semanticwb.model.base;

public interface CountryableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Catalogo de paises 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Country=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Country");
    public static final org.semanticwb.platform.SemanticProperty swb_country=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#country");
    public static final org.semanticwb.platform.SemanticClass swb_Countryable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Countryable");

   /**
   * Sets a value from the property Country
   * @param value An instance of org.semanticwb.model.Country
   */
    public void setCountry(org.semanticwb.model.Country value);

   /**
   * Remove the value from the property Country
   */
    public void removeCountry();

    public org.semanticwb.model.Country getCountry();
}
