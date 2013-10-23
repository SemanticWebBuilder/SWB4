package org.semanticwb.model.base;

   /**
   * Interfaz que define propiedades para elementos que pueden referencia a flujos de publicacion 
   */
public interface PFlowRefableBase extends org.semanticwb.model.Referensable
{
    public static final org.semanticwb.platform.SemanticProperty swb_notInheritPFlowRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#notInheritPFlowRef");
   /**
   * Referencia a un objeto de tipo PFlow 
   */
    public static final org.semanticwb.platform.SemanticClass swb_PFlowRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowRef");
    public static final org.semanticwb.platform.SemanticProperty swb_hasPFlowRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasPFlowRef");
   /**
   * Interfaz que define propiedades para elementos que pueden referencia a flujos de publicacion 
   */
    public static final org.semanticwb.platform.SemanticClass swb_PFlowRefable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowRefable");

    public boolean isNotInheritPFlowRef();

    public void setNotInheritPFlowRef(boolean value);

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef> listPFlowRefs();
    public boolean hasPFlowRef(org.semanticwb.model.PFlowRef value);
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef> listInheritPFlowRefs();

   /**
   * Adds the PFlowRef
   * @param value An instance of org.semanticwb.model.PFlowRef
   */
    public void addPFlowRef(org.semanticwb.model.PFlowRef value);

   /**
   * Remove all the values for the property PFlowRef
   */
    public void removeAllPFlowRef();

   /**
   * Remove a value from the property PFlowRef
   * @param value An instance of org.semanticwb.model.PFlowRef
   */
    public void removePFlowRef(org.semanticwb.model.PFlowRef value);

/**
* Gets the PFlowRef
* @return a instance of org.semanticwb.model.PFlowRef
*/
    public org.semanticwb.model.PFlowRef getPFlowRef();
}
