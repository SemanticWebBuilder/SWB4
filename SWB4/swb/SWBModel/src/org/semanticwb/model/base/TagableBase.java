package org.semanticwb.model.base;

   /**
   * Interfaz que define propiedades para los elementos que pueden etiquetarse mediante palabras clave 
   */
public interface TagableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swb_tags=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#tags");
   /**
   * Interfaz que define propiedades para los elementos que pueden etiquetarse mediante palabras clave 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Tagable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Tagable");

    public String getTags();

    public void setTags(String value);
}
