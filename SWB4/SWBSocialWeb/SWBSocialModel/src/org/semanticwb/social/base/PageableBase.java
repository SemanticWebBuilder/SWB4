package org.semanticwb.social.base;

   /**
   * Interface that is used to those SocialNets that supports the manage of FanPages. (Just Facebook at this momment) 
   */
public interface PageableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Indica si esta cuenta de Facebook se comporta como FanPage 
   */
    public static final org.semanticwb.platform.SemanticProperty social_isFanPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#isFanPage");
   /**
   * Clase que engloba a las diferentes clases que representan cada una de las redes sociales. 
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetwork");
    public static final org.semanticwb.platform.SemanticProperty social_hasFanPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasFanPage");
   /**
   * Interface that is used to those SocialNets that supports the manage of FanPages. (Just Facebook at this momment) 
   */
    public static final org.semanticwb.platform.SemanticClass social_Pageable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Pageable");

    public boolean isIsFanPage();

    public void setIsFanPage(boolean value);

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> listFanPages();
    public boolean hasFanPage(org.semanticwb.social.SocialNetwork value);

   /**
   * Adds the FanPage
   * @param value An instance of org.semanticwb.social.SocialNetwork
   */
    public void addFanPage(org.semanticwb.social.SocialNetwork value);

   /**
   * Remove all the values for the property FanPage
   */
    public void removeAllFanPage();

   /**
   * Remove a value from the property FanPage
   * @param value An instance of org.semanticwb.social.SocialNetwork
   */
    public void removeFanPage(org.semanticwb.social.SocialNetwork value);

/**
* Gets the FanPage
* @return a instance of org.semanticwb.social.SocialNetwork
*/
    public org.semanticwb.social.SocialNetwork getFanPage();
}
