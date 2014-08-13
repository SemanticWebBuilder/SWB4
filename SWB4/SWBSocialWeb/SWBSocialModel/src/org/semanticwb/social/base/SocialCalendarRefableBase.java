package org.semanticwb.social.base;

   /**
   * Interfaz que define propiedades para elementos que pueden referenciar a calendarios sociales 
   */
public interface SocialCalendarRefableBase extends org.semanticwb.model.Referensable
{
    public static final org.semanticwb.platform.SemanticClass social_SocialCalendarRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialCalendarRef");
    public static final org.semanticwb.platform.SemanticProperty social_hasSocialCalendarRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasSocialCalendarRef");
   /**
   * Interfaz que define propiedades para elementos que pueden referenciar a calendarios sociales 
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialCalendarRefable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialCalendarRefable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialCalendarRef> listSocialCalendarRefs();
    public boolean hasSocialCalendarRef(org.semanticwb.social.SocialCalendarRef value);

   /**
   * Adds the SocialCalendarRef
   * @param value An instance of org.semanticwb.social.SocialCalendarRef
   */
    public void addSocialCalendarRef(org.semanticwb.social.SocialCalendarRef value);

   /**
   * Remove all the values for the property SocialCalendarRef
   */
    public void removeAllSocialCalendarRef();

   /**
   * Remove a value from the property SocialCalendarRef
   * @param value An instance of org.semanticwb.social.SocialCalendarRef
   */
    public void removeSocialCalendarRef(org.semanticwb.social.SocialCalendarRef value);

/**
* Gets the SocialCalendarRef
* @return a instance of org.semanticwb.social.SocialCalendarRef
*/
    public org.semanticwb.social.SocialCalendarRef getSocialCalendarRef();
}
