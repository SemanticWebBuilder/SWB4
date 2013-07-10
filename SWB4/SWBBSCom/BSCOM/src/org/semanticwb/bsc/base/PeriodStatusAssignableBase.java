package org.semanticwb.bsc.base;

   /**
   * Cualquier elemento BSC al que se le puedan agregar mediciones 
   */
public interface PeriodStatusAssignableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass bsc_PeriodStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#PeriodStatus");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasPeriodStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasPeriodStatus");
   /**
   * Cualquier elemento BSC al que se le puedan agregar mediciones 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_PeriodStatusAssignable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#PeriodStatusAssignable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.PeriodStatus> listPeriodStatuses();
    public boolean hasPeriodStatus(org.semanticwb.bsc.element.PeriodStatus value);

   /**
   * Adds the PeriodStatus
   * @param value An instance of org.semanticwb.bsc.element.PeriodStatus
   */
    public void addPeriodStatus(org.semanticwb.bsc.element.PeriodStatus value);

   /**
   * Remove all the values for the property PeriodStatus
   */
    public void removeAllPeriodStatus();

   /**
   * Remove a value from the property PeriodStatus
   * @param value An instance of org.semanticwb.bsc.element.PeriodStatus
   */
    public void removePeriodStatus(org.semanticwb.bsc.element.PeriodStatus value);

/**
* Gets the PeriodStatus
* @return a instance of org.semanticwb.bsc.element.PeriodStatus
*/
    public org.semanticwb.bsc.element.PeriodStatus getPeriodStatus();
}
