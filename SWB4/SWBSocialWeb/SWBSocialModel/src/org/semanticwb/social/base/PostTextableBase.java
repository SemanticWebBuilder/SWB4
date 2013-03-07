package org.semanticwb.social.base;

   /**
   * Interface que contine propiedades necesarias para el manejo de un post que contenga un texto o Mensaje. 
   */
public interface PostTextableBase extends org.semanticwb.social.PostDataable
{
   /**
   * Propiedad que almacena un texto o mensaje en un objeto Post que lo requiera. 
   */
    public static final org.semanticwb.platform.SemanticProperty social_msg_Text=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#msg_Text");
   /**
   * Interface que contine propiedades necesarias para el manejo de un post que contenga un texto o Mensaje. 
   */
    public static final org.semanticwb.platform.SemanticClass social_PostTextable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostTextable");

    public String getMsg_Text();

    public void setMsg_Text(String value);
}
