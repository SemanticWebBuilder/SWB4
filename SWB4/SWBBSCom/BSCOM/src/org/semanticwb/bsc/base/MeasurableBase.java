package org.semanticwb.bsc.base;

   /**
   * Cualquier elemento BSC al que se le puedan agregar mediciones 
   */
public interface MeasurableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass bsc_Measure=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Measure");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasMeasure=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasMeasure");
   /**
   * Cualquier elemento BSC al que se le puedan agregar mediciones 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Measurable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Measurable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Measure> listMeasures();
    public boolean hasMeasure(org.semanticwb.bsc.element.Measure value);

   /**
   * Adds the Measure
   * @param value An instance of org.semanticwb.bsc.element.Measure
   */
    public void addMeasure(org.semanticwb.bsc.element.Measure value);

   /**
   * Remove all the values for the property Measure
   */
    public void removeAllMeasure();

   /**
   * Remove a value from the property Measure
   * @param value An instance of org.semanticwb.bsc.element.Measure
   */
    public void removeMeasure(org.semanticwb.bsc.element.Measure value);

/**
* Gets the Measure
* @return a instance of org.semanticwb.bsc.element.Measure
*/
    public org.semanticwb.bsc.element.Measure getMeasure();
}
