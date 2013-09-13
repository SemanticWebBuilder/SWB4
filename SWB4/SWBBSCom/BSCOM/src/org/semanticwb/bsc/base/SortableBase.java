package org.semanticwb.bsc.base;

   /**
   * Interfaz que define un valor entero para ordenar 
   */
public interface SortableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty bsc_index=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#index");
   /**
   * Interfaz que define un valor entero para ordenar 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Sortable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Sortable");

    public int getIndex();

    public void setIndex(int value);
}
