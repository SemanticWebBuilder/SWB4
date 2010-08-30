package org.semanticwb.model.base;

   /**
   * Interfaz que define propiedades para los elementos que pueden regirse por una o más reglas 
   */
public interface RuleableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swb_Rule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Rule");
    public static final org.semanticwb.platform.SemanticProperty swb_hasRule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasRule");
   /**
   * Interfaz que define propiedades para los elementos que pueden regirse por una o más reglas 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Ruleable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Ruleable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Rule> listRules();
    public boolean hasRule(org.semanticwb.model.Rule value);

   /**
   * Adds the Rule
   * @param value An instance of org.semanticwb.model.Rule
   */
    public void addRule(org.semanticwb.model.Rule value);

   /**
   * Remove all the values for the property Rule
   */
    public void removeAllRule();

   /**
   * Remove a value from the property Rule
   * @param value An instance of org.semanticwb.model.Rule
   */
    public void removeRule(org.semanticwb.model.Rule value);

/**
* Gets the Rule
* @return a instance of org.semanticwb.model.Rule
*/
    public org.semanticwb.model.Rule getRule();
}
