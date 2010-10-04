package org.semanticwb.model.base;

   /**
   * Interfaz que define propiedades para los elementos que tienen un índice de ordenamiento 
   */
public interface SortableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swb_index=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#index");
   /**
   * Interfaz que define propiedades para los elementos que tienen un índice de ordenamiento 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Sortable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Sortable");

    public int getIndex();

    public void setIndex(int value);
}
