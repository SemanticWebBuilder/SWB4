package org.semanticwb.bsc.base;

   /**
   * Interface que permite agregar sólo una medida a un objeto 
   */
public interface FixedMeasurableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Las frecuencias de medición, definen bloques de períodos para determinar cuándo se requiere la captura de información. 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_MeasurementFrequency=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#MeasurementFrequency");
   /**
   * Persiste la periodicidad (frecuencia de medida) de un indicador 
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_periodicity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#periodicity");
   /**
   * Interface que permite agregar sólo una medida a un objeto 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_FixedMeasurable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#FixedMeasurable");

   /**
   * Sets a value from the property Periodicity
   * @param value An instance of org.semanticwb.bsc.accessory.MeasurementFrequency
   */
    public void setPeriodicity(org.semanticwb.bsc.accessory.MeasurementFrequency value);

   /**
   * Remove the value from the property Periodicity
   */
    public void removePeriodicity();

    public org.semanticwb.bsc.accessory.MeasurementFrequency getPeriodicity();
}
