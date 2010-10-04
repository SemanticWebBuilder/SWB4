package org.semanticwb.model.base;

   /**
   * Interfaz que define propiedades para los elementos que pueden tener asociado una o más plantillas 
   */
public interface TemplateableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Las Plantillas son documentos HTML que sirven de base a SemanticWebBuilder para poder mostrar el "look & feel" del sitio, así como la distribución de todos los elementos en la pagina. 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Template=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Template");
    public static final org.semanticwb.platform.SemanticProperty swb_hasTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasTemplate");
   /**
   * Interfaz que define propiedades para los elementos que pueden tener asociado una o más plantillas 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Templateable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Templateable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Template> listTemplates();
    public boolean hasTemplate(org.semanticwb.model.Template value);

   /**
   * Adds the Template
   * @param value An instance of org.semanticwb.model.Template
   */
    public void addTemplate(org.semanticwb.model.Template value);

   /**
   * Remove all the values for the property Template
   */
    public void removeAllTemplate();

   /**
   * Remove a value from the property Template
   * @param value An instance of org.semanticwb.model.Template
   */
    public void removeTemplate(org.semanticwb.model.Template value);

/**
* Gets the Template
* @return a instance of org.semanticwb.model.Template
*/
    public org.semanticwb.model.Template getTemplate();
}
