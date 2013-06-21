package org.semanticwb.social.base;

   /**
   * Propiedad Developer Key, para redes sociales de Google por el momento (youtube, google+, etc), sin embargo, despues podemos querer integrar alguna otra red social que pueda utilizar este campo. 
   */
public interface DeveloperKeyableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Developer Key 
   */
    public static final org.semanticwb.platform.SemanticProperty social_developerKey=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#developerKey");
   /**
   * Propiedad Developer Key, para redes sociales de Google por el momento (youtube, google+, etc), sin embargo, despues podemos querer integrar alguna otra red social que pueda utilizar este campo. 
   */
    public static final org.semanticwb.platform.SemanticClass social_DeveloperKeyable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#DeveloperKeyable");

    public String getDeveloperKey();

    public void setDeveloperKey(String value);
}
