package org.semanticwb.social.base;

   /**
   * Interface para las redes sociales que pueden mantener abierta la conección para realizar las busquedas (listener) como es el caso de Twitter con su Streaming Api. 
   */
public interface KeepAliveListenerableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Propiedad en la que se puede definir si la red social va ha mantener la conección abierta (mediante Stream Api) o no lo va ha ser (Poolean de información por el listener c/x tiempo),  por defecto es false (Es decir sera por pooleo). 
   */
    public static final org.semanticwb.platform.SemanticProperty social_isKeepingConnection=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#isKeepingConnection");
   /**
   * Interface para las redes sociales que pueden mantener abierta la conección para realizar las busquedas (listener) como es el caso de Twitter con su Streaming Api. 
   */
    public static final org.semanticwb.platform.SemanticClass social_KeepAliveListenerable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#KeepAliveListenerable");

    public boolean isIsKeepingConnection();

    public void setIsKeepingConnection(boolean value);
}
