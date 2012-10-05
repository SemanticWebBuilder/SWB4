package org.semanticwb.social.base;

   /**
   * Interface que contiene ciertas propiedades utiles para la autenticación de ciertas redes sociales (ver mas adelante si se unifica con Oauthable) 
   */
public interface SecreteableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Propiedad que almacena el Aplication Key de una determinada Red Social que requiera de este para su conección. 
   */
    public static final org.semanticwb.platform.SemanticProperty social_appKey=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#appKey");
   /**
   * Propiedad que almacena el SecretKey de una determinada Red Social que requiera de este para su conección. 
   */
    public static final org.semanticwb.platform.SemanticProperty social_secretKey=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#secretKey");
   /**
   * Interface que contiene ciertas propiedades utiles para la autenticación de ciertas redes sociales (ver mas adelante si se unifica con Oauthable) 
   */
    public static final org.semanticwb.platform.SemanticClass social_Secreteable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Secreteable");

    public String getAppKey();

    public void setAppKey(String value);

    public String getSecretKey();

    public void setSecretKey(String value);
}
