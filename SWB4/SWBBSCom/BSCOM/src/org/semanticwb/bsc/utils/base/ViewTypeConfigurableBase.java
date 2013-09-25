package org.semanticwb.bsc.utils.base;

   /**
   * Agrupa las propiedades en común de los diferentes tipos de vistas (vista y detalle) para los elementos del BSC. 
   */
public interface ViewTypeConfigurableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Indica el tipo de objetos que se mostrarán en la vista resumen 
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_objectsType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#objectsType");
   /**
   * Agrupa las propiedades en común de los diferentes tipos de vistas (vista y detalle) para los elementos del BSC. 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_ViewTypeConfigurable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#ViewTypeConfigurable");

    public String getObjectsType();

    public void setObjectsType(String value);
}
