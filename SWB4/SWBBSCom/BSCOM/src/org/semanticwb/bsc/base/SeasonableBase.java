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
    public static final org.semanticwb.platform.SemanticProperty bsc_hasPeriod=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasPeriod");
   /**
   * Cualquier elemento BSC al que se le puedan asignar períodos de medición 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Seasonable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Seasonable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Period> listPeriods();
    public boolean hasPeriod(org.semanticwb.bsc.accessory.Period value);

   /**
   * Adds the Period
   * @param value An instance of org.semanticwb.bsc.accessory.Period
   */
    public void addPeriod(org.semanticwb.bsc.accessory.Period value);

   /**
   * Remove all the values for the property Period
   */
    public void removeAllPeriod();

   /**
   * Remove a value from the property Period
   * @param value An instance of org.semanticwb.bsc.accessory.Period
   */
    public void removePeriod(org.semanticwb.bsc.accessory.Period value);

/**
* Gets the Period
* @return a instance of org.semanticwb.bsc.accessory.Period
*/
    public org.semanticwb.bsc.accessory.Period getPeriod();
}
