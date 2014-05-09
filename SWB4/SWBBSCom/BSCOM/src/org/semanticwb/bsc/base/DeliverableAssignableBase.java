package org.semanticwb.bsc.base;

   /**
   * Define que puede tener entregables 
   */
public interface DeliverableAssignableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Representa un archivo físico utilizado a manera de evidencia sobre la realización de alguna actividad. 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Deliverable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Deliverable");
   /**
   * Lista de los entregables asociados a la iniciativa 
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_hasDeliverable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasDeliverable");
   /**
   * Define que puede tener entregables 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_DeliverableAssignable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#DeliverableAssignable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Deliverable> listDeliverables();
    public boolean hasDeliverable(org.semanticwb.bsc.element.Deliverable value);

   /**
   * Adds the Deliverable
   * @param value An instance of org.semanticwb.bsc.element.Deliverable
   */
    public void addDeliverable(org.semanticwb.bsc.element.Deliverable value);

   /**
   * Remove all the values for the property Deliverable
   */
    public void removeAllDeliverable();

   /**
   * Remove a value from the property Deliverable
   * @param value An instance of org.semanticwb.bsc.element.Deliverable
   */
    public void removeDeliverable(org.semanticwb.bsc.element.Deliverable value);

/**
* Gets the Deliverable
* @return a instance of org.semanticwb.bsc.element.Deliverable
*/
    public org.semanticwb.bsc.element.Deliverable getDeliverable();
}
