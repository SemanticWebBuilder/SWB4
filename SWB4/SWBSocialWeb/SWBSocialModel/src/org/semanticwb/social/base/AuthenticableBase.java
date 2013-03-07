package org.semanticwb.social.base;

public interface AuthenticableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Password o contraseña con la cual una organización se puede conectar a una determinada instancia de Red Social. 
   */
    public static final org.semanticwb.platform.SemanticProperty social_password=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#password");
   /**
   * Login o usuario con la cual una organización se puede conectar a una determinada instancia de Red Social. 
   */
    public static final org.semanticwb.platform.SemanticProperty social_login=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#login");
    public static final org.semanticwb.platform.SemanticClass social_Authenticable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Authenticable");

    public String getPassword();

    public void setPassword(String value);

    public String getLogin();

    public void setLogin(String value);
}
