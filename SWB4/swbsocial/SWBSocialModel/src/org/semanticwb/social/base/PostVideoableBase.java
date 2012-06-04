package org.semanticwb.social.base;

   /**
   * Interface que contine propiedades necesarias para el manejo de un post que contenga un Video. 
   */
public interface PostVideoableBase extends org.semanticwb.social.PostDataable
{
   /**
   * Propiedad que almacena el nombre(referencia) de un video en un objeto Post que lo requiera. 
   */
    public static final org.semanticwb.platform.SemanticProperty social_video=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#video");
   /**
   * Interface que contine propiedades necesarias para el manejo de un post que contenga un Video. 
   */
    public static final org.semanticwb.platform.SemanticClass social_PostVideoable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostVideoable");

    public String getVideo();

    public void setVideo(String value);
}
