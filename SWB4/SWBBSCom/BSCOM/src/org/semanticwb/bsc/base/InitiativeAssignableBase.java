package org.semanticwb.bsc.base;

   /**
   * Define que puede tener iniciativas asignadas 
   */
public interface InitiativeAssignableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass bsc_Initiative=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Initiative");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasInitiative=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasInitiative");
   /**
   * Define que puede tener iniciativas asignadas 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_InitiativeAssignable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#InitiativeAssignable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> listInitiatives();
    public boolean hasInitiative(org.semanticwb.bsc.element.Initiative value);

   /**
   * Adds the Initiative
   * @param value An instance of org.semanticwb.bsc.element.Initiative
   */
    public void addInitiative(org.semanticwb.bsc.element.Initiative value);

   /**
   * Remove all the values for the property Initiative
   */
    public void removeAllInitiative();

   /**
   * Remove a value from the property Initiative
   * @param value An instance of org.semanticwb.bsc.element.Initiative
   */
    public void removeInitiative(org.semanticwb.bsc.element.Initiative value);

/**
* Gets the Initiative
* @return a instance of org.semanticwb.bsc.element.Initiative
*/
    public org.semanticwb.bsc.element.Initiative getInitiative();
}
