package org.semanticwb.social.base;

   /**
   * Interface que contine propiedades necesarias para el manejo de un post que contenga una imagen o foto. 
   */
public interface PostImageableBase extends org.semanticwb.social.PostDataable
{
   /**
   * Photo 
   */
    public static final org.semanticwb.platform.SemanticProperty social_photo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#photo");
   /**
   * Interface que contine propiedades necesarias para el manejo de un post que contenga una imagen o foto. 
   */
    public static final org.semanticwb.platform.SemanticClass social_PostImageable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostImageable");

    public String getPhoto();

    public void setPhoto(String value);
}
