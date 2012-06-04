package org.semanticwb.social.base;

   /**
   * Interface que se le agrega a las clases de tipo SocialNetwork a las que sea necesaria la autenticación mediante OAuth. 
   */
public interface OauthableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Almacena el access Token autorizado para hacer publicaciones, por conveniencia no utiliza displayProperty 
   */
    public static final org.semanticwb.platform.SemanticProperty social_accessToken=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#accessToken");
   /**
   * Almacena la fecha de expiración del token autorizado para hacer publicaciones, por conveniencia no utiliza displayProperty 
   */
    public static final org.semanticwb.platform.SemanticProperty social_tokenExpirationDate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#tokenExpirationDate");
   /**
   * Almacena un token secreto para realizar publicaciones, por conveniencia no utiliza displayProperty 
   */
    public static final org.semanticwb.platform.SemanticProperty social_accessTokenSecret=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#accessTokenSecret");
   /**
   * Interface que se le agrega a las clases de tipo SocialNetwork a las que sea necesaria la autenticación mediante OAuth. 
   */
    public static final org.semanticwb.platform.SemanticClass social_Oauthable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Oauthable");

    public String getAccessToken();

    public void setAccessToken(String value);

    public java.util.Date getTokenExpirationDate();

    public void setTokenExpirationDate(java.util.Date value);

    public String getAccessTokenSecret();

    public void setAccessTokenSecret(String value);
}
