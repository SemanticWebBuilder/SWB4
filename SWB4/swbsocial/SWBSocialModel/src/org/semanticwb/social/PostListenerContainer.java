package org.semanticwb.social;


   /**
   * En esta clase se guardan todos los post que lleguan por el listener, se estima que toda la info. que se guarde en este objeto debe de eliminarse aproximadamente c/mes, siendo este parametro configurable de acuerdo al tiempo que la organización quiera guardar  la información sobre los mensajes que lleguen por el listener. Cuando un post que llegue por el listener sea tomado como base para crear un nuevo post por la organización, se cree que debe copiarse la información de dicho post de esta clase hacia la clase PostListenerContainerBase. 
   */
public class PostListenerContainer extends org.semanticwb.social.base.PostListenerContainerBase 
{
    public PostListenerContainer(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
