package org.semanticwb.social.base;

public interface SecreatableBase extends org.semanticwb.model.GenericObject
{
   /**
   * LLave Secreta de la Aplicación para conectarse a la Red Social (Esta es proporcionada por la red social a una determinada aplicación, cada instancia de SWBSocial debe obtener una de estas en cada red social que así lo solicite) 
   */
    public static final org.semanticwb.platform.SemanticProperty social_secreatKey=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.owl-ontologies.com/socialNet#secreatKey");
    public static final org.semanticwb.platform.SemanticClass social_Secreatable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.owl-ontologies.com/socialNet#Secreatable");

    public String getSecreatKey();

    public void setSecreatKey(String value);
}
