package org.semanticwb.social.base;

public interface YoutubeCatableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Categorias a llenar en una colección. 
   */
    public static final org.semanticwb.platform.SemanticClass social_YouTubeCategory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#YouTubeCategory");
   /**
   * Categoria de Youtube 
   */
    public static final org.semanticwb.platform.SemanticProperty social_youtubeCategory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#youtubeCategory");
    public static final org.semanticwb.platform.SemanticClass social_YoutubeCatable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#YoutubeCatable");

   /**
   * Sets a value from the property YoutubeCategory
   * @param value An instance of org.semanticwb.social.YouTubeCategory
   */
    public void setYoutubeCategory(org.semanticwb.social.YouTubeCategory value);

   /**
   * Remove the value from the property YoutubeCategory
   */
    public void removeYoutubeCategory();

    public org.semanticwb.social.YouTubeCategory getYoutubeCategory();
}
