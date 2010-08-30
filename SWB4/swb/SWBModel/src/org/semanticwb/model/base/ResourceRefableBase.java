package org.semanticwb.model.base;

public interface ResourceRefableBase extends org.semanticwb.model.Referensable
{
   /**
   * Referencia a un objeto de tipo Resource 
   */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceRef");
    public static final org.semanticwb.platform.SemanticProperty swb_hasResourceRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasResourceRef");
    public static final org.semanticwb.platform.SemanticClass swb_ResourceRefable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceRefable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceRef> listResourceRefs();
    public boolean hasResourceRef(org.semanticwb.model.ResourceRef value);

   /**
   * Adds the ResourceRef
   * @param value An instance of org.semanticwb.model.ResourceRef
   */
    public void addResourceRef(org.semanticwb.model.ResourceRef value);

   /**
   * Remove all the values for the property ResourceRef
   */
    public void removeAllResourceRef();

   /**
   * Remove a value from the property ResourceRef
   * @param value An instance of org.semanticwb.model.ResourceRef
   */
    public void removeResourceRef(org.semanticwb.model.ResourceRef value);

/**
* Gets the ResourceRef
* @return a instance of org.semanticwb.model.ResourceRef
*/
    public org.semanticwb.model.ResourceRef getResourceRef();
}
