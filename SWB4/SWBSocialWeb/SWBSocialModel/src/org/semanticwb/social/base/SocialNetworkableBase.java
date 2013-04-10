package org.semanticwb.social.base;

   /**
   * Interface que define propiedades utiles para que se realice ciertas acciones en ciertas redes sociales. 
   */
public interface SocialNetworkableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Clase que engloba a las diferentes clases que representan cada una de las redes sociales. 
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetwork");
   /**
   * Esta propiedad contiene ciertas intencias de redes sociales de las existentes en una marca 
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasSocialNetworks=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasSocialNetworks");
   /**
   * Interface que define propiedades utiles para que se realice ciertas acciones en ciertas redes sociales. 
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetworkable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetworkable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> listSocialNetworkses();
    public boolean hasSocialNetworks(org.semanticwb.social.SocialNetwork value);

   /**
   * Adds the SocialNetworks
   * @param value An instance of org.semanticwb.social.SocialNetwork
   */
    public void addSocialNetworks(org.semanticwb.social.SocialNetwork value);

   /**
   * Remove all the values for the property SocialNetworks
   */
    public void removeAllSocialNetworks();

   /**
   * Remove a value from the property SocialNetworks
   * @param value An instance of org.semanticwb.social.SocialNetwork
   */
    public void removeSocialNetworks(org.semanticwb.social.SocialNetwork value);

/**
* Gets the SocialNetworks
* @return a instance of org.semanticwb.social.SocialNetwork
*/
    public org.semanticwb.social.SocialNetwork getSocialNetworks();
}
