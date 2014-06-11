package org.semanticwb.bsc.base;

public interface SMBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass bsc_Series=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Series");
   /**
   * Series taken as reference. Indica qué serie usará el indicador para evaluar el período. Usualmente se ocupa la serie Actual. 
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_star=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#star");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasSeries=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasSeries");
    public static final org.semanticwb.platform.SemanticClass bsc_SM=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#SM");

   /**
   * Sets a value from the property Star
   * @param value An instance of org.semanticwb.bsc.tracing.Series
   */
    public void setStar(org.semanticwb.bsc.tracing.Series value);

   /**
   * Remove the value from the property Star
   */
    public void removeStar();

    public org.semanticwb.bsc.tracing.Series getStar();

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Series> listSerieses();
    public boolean hasSeries(org.semanticwb.bsc.tracing.Series value);

   /**
   * Adds the Series
   * @param value An instance of org.semanticwb.bsc.tracing.Series
   */
    public void addSeries(org.semanticwb.bsc.tracing.Series value);

   /**
   * Remove all the values for the property Series
   */
    public void removeAllSeries();

   /**
   * Remove a value from the property Series
   * @param value An instance of org.semanticwb.bsc.tracing.Series
   */
    public void removeSeries(org.semanticwb.bsc.tracing.Series value);

/**
* Gets the Series
* @return a instance of org.semanticwb.bsc.tracing.Series
*/
    public org.semanticwb.bsc.tracing.Series getSeries();
}
