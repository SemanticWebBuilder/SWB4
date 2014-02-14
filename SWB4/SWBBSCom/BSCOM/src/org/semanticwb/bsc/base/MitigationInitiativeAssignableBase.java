package org.semanticwb.bsc.base;

   /**
   * Definira si una Iniciativa de Mitigación puede ser asignable 
   */
public interface MitigationInitiativeAssignableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass bsc_Initiative=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Initiative");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasInitiativeRisk=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasInitiativeRisk");
   /**
   * Definira si una Iniciativa de Mitigación puede ser asignable 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_MitigationInitiativeAssignable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#MitigationInitiativeAssignable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> listInitiativeRisks();
    public boolean hasInitiativeRisk(org.semanticwb.bsc.element.Initiative value);

   /**
   * Adds the InitiativeRisk
   * @param value An instance of org.semanticwb.bsc.element.Initiative
   */
    public void addInitiativeRisk(org.semanticwb.bsc.element.Initiative value);

   /**
   * Remove all the values for the property InitiativeRisk
   */
    public void removeAllInitiativeRisk();

   /**
   * Remove a value from the property InitiativeRisk
   * @param value An instance of org.semanticwb.bsc.element.Initiative
   */
    public void removeInitiativeRisk(org.semanticwb.bsc.element.Initiative value);

/**
* Gets the InitiativeRisk
* @return a instance of org.semanticwb.bsc.element.Initiative
*/
    public org.semanticwb.bsc.element.Initiative getInitiativeRisk();
}
