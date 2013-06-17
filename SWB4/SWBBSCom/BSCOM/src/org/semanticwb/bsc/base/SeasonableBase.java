package org.semanticwb.bsc.base;

   /**
   * Cualquier elemento BSC al que se le puedan asignar períodos de medición 
   */
public interface SeasonableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Período de medición. 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Period=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Period");
   /**
   * Atributo que almacena un periodo 
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_hasPeriodObjetive=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasPeriodObjetive");
   /**
   * Cualquier elemento BSC al que se le puedan asignar períodos de medición 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Seasonable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Seasonable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Period> listPeriodObjetives();
    public boolean hasPeriodObjetive(org.semanticwb.bsc.accessory.Period value);

   /**
   * Adds the PeriodObjetive
   * @param value An instance of org.semanticwb.bsc.accessory.Period
   */
    public void addPeriodObjetive(org.semanticwb.bsc.accessory.Period value);

   /**
   * Remove all the values for the property PeriodObjetive
   */
    public void removeAllPeriodObjetive();

   /**
   * Remove a value from the property PeriodObjetive
   * @param value An instance of org.semanticwb.bsc.accessory.Period
   */
    public void removePeriodObjetive(org.semanticwb.bsc.accessory.Period value);

/**
* Gets the PeriodObjetive
* @return a instance of org.semanticwb.bsc.accessory.Period
*/
    public org.semanticwb.bsc.accessory.Period getPeriodObjetive();
}
