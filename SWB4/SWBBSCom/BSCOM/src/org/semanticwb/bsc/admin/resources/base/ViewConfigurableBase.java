package org.semanticwb.bsc.admin.resources.base;

   /**
   * Mantiene las propiedades en común de los diferentes tipos de recursos para las vistas (resumen y detalle) de información del BSC. 
   */
public interface ViewConfigurableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swb_Class=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Class");
   /**
   * Indica la clase de objetos a presentar en las vistas resumen. 
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_workClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#workClass");
   /**
   * Mantiene las propiedades en común de los diferentes tipos de recursos para las vistas (resumen y detalle) de información del BSC. 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_ViewConfigurable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#ViewConfigurable");

   /**
   * Sets a value from the property WorkClass
   * @param value An instance of org.semanticwb.platform.SemanticObject
   */
    public void setWorkClass(org.semanticwb.platform.SemanticObject value);

   /**
   * Remove the value from the property WorkClass
   */
    public void removeWorkClass();

    public org.semanticwb.platform.SemanticObject getWorkClass();
}
