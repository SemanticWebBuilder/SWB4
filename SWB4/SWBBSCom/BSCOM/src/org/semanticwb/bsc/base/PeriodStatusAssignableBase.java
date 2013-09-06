package org.semanticwb.bsc.base;

   /**
   * Interface que permite asignar más de un objeto de tipo "PeriodStatus" a un elemento. 
   */
public interface PeriodStatusAssignableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Clase que define el valor de un estado en un periodo. Ejemplo: Para el periodo "Enero 2013" - Estado "En Riesgo" 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_PeriodStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#PeriodStatus");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasPeriodStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasPeriodStatus");
   /**
   * Interface que permite asignar más de un objeto de tipo "PeriodStatus" a un elemento. 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_PeriodStatusAssignable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#PeriodStatusAssignable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.PeriodStatus> listPeriodStatuses();
    public boolean hasPeriodStatus(org.semanticwb.bsc.tracing.PeriodStatus value);

   /**
   * Adds the PeriodStatus
   * @param value An instance of org.semanticwb.bsc.tracing.PeriodStatus
   */
    public void addPeriodStatus(org.semanticwb.bsc.tracing.PeriodStatus value);

   /**
   * Remove all the values for the property PeriodStatus
   */
    public void removeAllPeriodStatus();

   /**
   * Remove a value from the property PeriodStatus
   * @param value An instance of org.semanticwb.bsc.tracing.PeriodStatus
   */
    public void removePeriodStatus(org.semanticwb.bsc.tracing.PeriodStatus value);

/**
* Gets the PeriodStatus
* @return a instance of org.semanticwb.bsc.tracing.PeriodStatus
*/
    public org.semanticwb.bsc.tracing.PeriodStatus getPeriodStatus();
}
