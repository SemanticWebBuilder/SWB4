package org.semanticwb.social.base;

   /**
   * Interfaz que define propiedades para elementos que pueden tener referencia a flujos de publicacion 
   */
public interface SocialPFlowRefableBase extends org.semanticwb.model.Referensable
{
   /**
   * Referencia a un objeto de tipo SocialPFlow 
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialPFlowRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialPFlowRef");
    public static final org.semanticwb.platform.SemanticProperty social_hasPFlowRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasPFlowRef");
   /**
   * Interfaz que define propiedades para elementos que pueden tener referencia a flujos de publicacion 
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialPFlowRefable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialPFlowRefable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPFlowRef> listPFlowRefs();
    public boolean hasPFlowRef(org.semanticwb.social.SocialPFlowRef value);
    public org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPFlowRef> listInheritPFlowRefs();

   /**
   * Adds the PFlowRef
   * @param value An instance of org.semanticwb.social.SocialPFlowRef
   */
    public void addPFlowRef(org.semanticwb.social.SocialPFlowRef value);

   /**
   * Remove all the values for the property PFlowRef
   */
    public void removeAllPFlowRef();

   /**
   * Remove a value from the property PFlowRef
   * @param value An instance of org.semanticwb.social.SocialPFlowRef
   */
    public void removePFlowRef(org.semanticwb.social.SocialPFlowRef value);

/**
* Gets the PFlowRef
* @return a instance of org.semanticwb.social.SocialPFlowRef
*/
    public org.semanticwb.social.SocialPFlowRef getPFlowRef();
}
