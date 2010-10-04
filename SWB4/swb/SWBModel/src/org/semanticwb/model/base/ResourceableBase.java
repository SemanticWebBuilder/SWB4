package org.semanticwb.model.base;

   /**
   * Interfaz que define propiedades para los elementos que pueden contener recursos 
   */
public interface ResourceableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Un recurso es un componente en una Página Web con el cual el usuario tiene interacción 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_hasResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasResource");
   /**
   * Interfaz que define propiedades para los elementos que pueden contener recursos 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Resourceable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resourceable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> listResources();
    public boolean hasResource(org.semanticwb.model.Resource value);

   /**
   * Adds the Resource
   * @param value An instance of org.semanticwb.model.Resource
   */
    public void addResource(org.semanticwb.model.Resource value);

   /**
   * Remove all the values for the property Resource
   */
    public void removeAllResource();

   /**
   * Remove a value from the property Resource
   * @param value An instance of org.semanticwb.model.Resource
   */
    public void removeResource(org.semanticwb.model.Resource value);

/**
* Gets the Resource
* @return a instance of org.semanticwb.model.Resource
*/
    public org.semanticwb.model.Resource getResource();
}
