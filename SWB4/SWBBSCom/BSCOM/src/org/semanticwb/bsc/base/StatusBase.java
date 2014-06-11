package org.semanticwb.bsc.base;

   /**
   * Define que puede tener  estados de semaforización asignados 
   */
public interface StatusBase extends org.semanticwb.model.GenericObject
{
   /**
   * Un estado define la situación de una medición  en un indicador respecto de las metas de su objetivo. 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_State=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#State");
   /**
   * Lista de estados del grupo 
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_hasState=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasState");
   /**
   * Define que puede tener  estados de semaforización asignados 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Status=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Status");

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.State> listStates();
    public boolean hasState(org.semanticwb.bsc.accessory.State value);

   /**
   * Adds the State
   * @param value An instance of org.semanticwb.bsc.accessory.State
   */
    public void addState(org.semanticwb.bsc.accessory.State value);

   /**
   * Remove all the values for the property State
   */
    public void removeAllState();

   /**
   * Remove a value from the property State
   * @param value An instance of org.semanticwb.bsc.accessory.State
   */
    public void removeState(org.semanticwb.bsc.accessory.State value);

/**
* Gets the State
* @return a instance of org.semanticwb.bsc.accessory.State
*/
    public org.semanticwb.bsc.accessory.State getState();
}
