package org.semanticwb.model.base;

   /**
   * Interfaz que define propiedades para los elementos que pueden tener uno o mas objetos HerarquicalNode 
   */
public interface HerarquicalNodeableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Objeto utilizado para definir un node del arbol de navegacion dentro de la administración de SWB, que contiene elemento de una clase definida. 
   */
    public static final org.semanticwb.platform.SemanticClass swbxf_HerarquicalNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#HerarquicalNode");
    public static final org.semanticwb.platform.SemanticProperty swb_hasHerarquicalNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasHerarquicalNode");
   /**
   * Interfaz que define propiedades para los elementos que pueden tener uno o mas objetos HerarquicalNode 
   */
    public static final org.semanticwb.platform.SemanticClass swbxf_HerarquicalNodeable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#HerarquicalNodeable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.HerarquicalNode> listHerarquicalNodes();
    public boolean hasHerarquicalNode(org.semanticwb.model.HerarquicalNode value);

   /**
   * Adds the HerarquicalNode
   * @param value An instance of org.semanticwb.model.HerarquicalNode
   */
    public void addHerarquicalNode(org.semanticwb.model.HerarquicalNode value);

   /**
   * Remove all the values for the property HerarquicalNode
   */
    public void removeAllHerarquicalNode();

   /**
   * Remove a value from the property HerarquicalNode
   * @param value An instance of org.semanticwb.model.HerarquicalNode
   */
    public void removeHerarquicalNode(org.semanticwb.model.HerarquicalNode value);

/**
* Gets the HerarquicalNode
* @return a instance of org.semanticwb.model.HerarquicalNode
*/
    public org.semanticwb.model.HerarquicalNode getHerarquicalNode();
}
