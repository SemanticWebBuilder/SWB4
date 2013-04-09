package org.semanticwb.social.base;

   /**
   * Interfaz que define propiedades para elementos que pueden referencia a reglas sociales 
   */
public interface SocialRuleRefableBase extends org.semanticwb.model.Referensable
{
    public static final org.semanticwb.platform.SemanticClass social_SocialRuleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialRuleRef");
    public static final org.semanticwb.platform.SemanticProperty social_hasSocialRuleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasSocialRuleRef");
   /**
   * Interfaz que define propiedades para elementos que pueden referencia a reglas sociales 
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialRuleRefable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialRuleRefable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialRuleRef> listSocialRuleRefs();
    public boolean hasSocialRuleRef(org.semanticwb.social.SocialRuleRef value);

   /**
   * Adds the SocialRuleRef
   * @param value An instance of org.semanticwb.social.SocialRuleRef
   */
    public void addSocialRuleRef(org.semanticwb.social.SocialRuleRef value);

   /**
   * Remove all the values for the property SocialRuleRef
   */
    public void removeAllSocialRuleRef();

   /**
   * Remove a value from the property SocialRuleRef
   * @param value An instance of org.semanticwb.social.SocialRuleRef
   */
    public void removeSocialRuleRef(org.semanticwb.social.SocialRuleRef value);

/**
* Gets the SocialRuleRef
* @return a instance of org.semanticwb.social.SocialRuleRef
*/
    public org.semanticwb.social.SocialRuleRef getSocialRuleRef();
}
