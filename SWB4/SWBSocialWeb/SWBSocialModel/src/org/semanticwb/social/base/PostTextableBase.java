package org.semanticwb.social.base;

   /**
   * Interface que contine propiedades necesarias para el manejo de un post que contenga un texto o Mensaje. 
   */
public interface PostTextableBase extends org.semanticwb.social.PostDataable
{
   /**
   * Un Lenguaje en SemanticWebBuilder es la definición de un Idioma para despliegue de las páginas y recursos. Al definir un lenguaje nuevo es posible definir el título y la descripción de páginas y recursos en él. 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Language=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Language");
   /**
   * Lenguaje en el que esta escrito el mensaje 
   */
    public static final org.semanticwb.platform.SemanticProperty social_msg_lang=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#msg_lang");
   /**
   * Propiedad que almacena un texto o mensaje en un objeto Post que lo requiera. 
   */
    public static final org.semanticwb.platform.SemanticProperty social_msg_Text=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#msg_Text");
   /**
   * Interface que contine propiedades necesarias para el manejo de un post que contenga un texto o Mensaje. 
   */
    public static final org.semanticwb.platform.SemanticClass social_PostTextable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostTextable");

   /**
   * Sets a value from the property Msg_lang
   * @param value An instance of org.semanticwb.model.Language
   */
    public void setMsg_lang(org.semanticwb.model.Language value);

   /**
   * Remove the value from the property Msg_lang
   */
    public void removeMsg_lang();

    public org.semanticwb.model.Language getMsg_lang();

    public String getMsg_Text();

    public void setMsg_Text(String value);
}
