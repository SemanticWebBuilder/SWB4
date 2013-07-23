package org.semanticwb.social.base;

   /**
   * Interface para las redes sociales que pueden mantener abierta la conección para realizar las busquedas (listener) como es el caso de Twitter con su Streaming Api. 
   */
public interface KeepAliveListenerableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Interface para las redes sociales que pueden mantener abierta la conección para realizar las busquedas (listener) como es el caso de Twitter con su Streaming Api. 
   */
    public static final org.semanticwb.platform.SemanticClass social_KeepAliveListenerable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#KeepAliveListenerable");
}
