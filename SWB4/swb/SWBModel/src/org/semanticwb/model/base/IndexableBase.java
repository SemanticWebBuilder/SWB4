package org.semanticwb.model.base;

   /**
   * Superclase de todos los objetos que pueden ser indexados en SemanticWebBuilder 
   */
public interface IndexableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Indica si el objeto puede ser indexado 
   */
    public static final org.semanticwb.platform.SemanticProperty swb_indexable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#indexable");
   /**
   * Superclase de todos los objetos que pueden ser indexados en SemanticWebBuilder 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Indexable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Indexable");

    public boolean isIndexable();

    public void setIndexable(boolean value);
}
