package org.semanticwb.bsc.base;

public interface SerializableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Persiste la serialización de la abreviatura de elementos 
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_serial=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#serial");
    public static final org.semanticwb.platform.SemanticClass bsc_Serializable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Serializable");

    public int getSerial();

    public void setSerial(int value);
}
