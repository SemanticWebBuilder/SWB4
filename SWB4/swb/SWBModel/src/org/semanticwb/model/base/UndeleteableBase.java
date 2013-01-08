package org.semanticwb.model.base;

   /**
   * Interfaz que define propiedades para los elementos que pueden ser no eliminables 
   */
public interface UndeleteableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swb_undeleteable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#undeleteable");
   /**
   * Interfaz que define propiedades para los elementos que pueden ser no eliminables 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Undeleteable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Undeleteable");

    public boolean isUndeleteable();

    public void setUndeleteable(boolean value);
}
