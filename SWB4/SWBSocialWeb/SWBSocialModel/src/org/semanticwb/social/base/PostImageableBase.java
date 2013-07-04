package org.semanticwb.social.base;

   /**
   * Interface que contine propiedades necesarias para el manejo de un post que contenga una imagen o foto. 
   */
public interface PostImageableBase extends org.semanticwb.social.PostDataable
{
    public static final org.semanticwb.platform.SemanticClass social_PhotoImg=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PhotoImg");
   /**
   * Imagenes 
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasPhoto=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasPhoto");
   /**
   * Interface que contine propiedades necesarias para el manejo de un post que contenga una imagen o foto. 
   */
    public static final org.semanticwb.platform.SemanticClass social_PostImageable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostImageable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PhotoImg> listPhotos();
    public boolean hasPhoto(org.semanticwb.social.PhotoImg value);

   /**
   * Adds the Photo
   * @param value An instance of org.semanticwb.social.PhotoImg
   */
    public void addPhoto(org.semanticwb.social.PhotoImg value);

   /**
   * Remove all the values for the property Photo
   */
    public void removeAllPhoto();

   /**
   * Remove a value from the property Photo
   * @param value An instance of org.semanticwb.social.PhotoImg
   */
    public void removePhoto(org.semanticwb.social.PhotoImg value);

/**
* Gets the Photo
* @return a instance of org.semanticwb.social.PhotoImg
*/
    public org.semanticwb.social.PhotoImg getPhoto();
}
