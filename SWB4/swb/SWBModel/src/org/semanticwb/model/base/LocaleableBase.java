package org.semanticwb.model.base;

public interface LocaleableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Un Lenguaje en SemanticWebBuilder es la definición de un Idioma para despliegue de las páginas y recursos. Al definir un lenguaje nuevo es posible definir el título y la descripción de páginas y recursos en él. 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Language=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Language");
    public static final org.semanticwb.platform.SemanticProperty swb_language=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#language");
    public static final org.semanticwb.platform.SemanticClass swb_Localeable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Localeable");

   /**
   * Sets a value from the property Language
   * @param value An instance of org.semanticwb.model.Language
   */
    public void setLanguage(org.semanticwb.model.Language value);

   /**
   * Remove the value from the property Language
   */
    public void removeLanguage();

    public org.semanticwb.model.Language getLanguage();
}
