package org.semanticwb.bsc.base;

   /**
   * Define la posibilidad de que un usuario pueda asignar  directamente un estado a un objeto 
   */
public interface StatusManuallyAssignableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Un estado define la situación de una medición  en un indicador respecto de las metas de su objetivo. 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_State=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#State");
   /**
   * Estatus asignado por el usuario 
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_statusAssigned=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#statusAssigned");
   /**
   * Define la posibilidad de que un usuario pueda asignar  directamente un estado a un objeto 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_StatusManuallyAssignable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#StatusManuallyAssignable");

   /**
   * Sets a value from the property StatusAssigned
   * @param value An instance of org.semanticwb.bsc.accessory.State
   */
    public void setStatusAssigned(org.semanticwb.bsc.accessory.State value);

   /**
   * Remove the value from the property StatusAssigned
   */
    public void removeStatusAssigned();

    public org.semanticwb.bsc.accessory.State getStatusAssigned();
}
