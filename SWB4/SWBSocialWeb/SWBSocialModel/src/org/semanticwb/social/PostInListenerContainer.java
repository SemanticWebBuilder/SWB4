package org.semanticwb.social;


   /**
   * Clase a Cambiar despues por "Relacional".  En esta clase se guardan todos los post que lleguan por el listener, se estima que toda la info. que se guarde en este objeto debe de eliminarse aproximadamente c/mes, siendo este parametro configurable de acuerdo al tiempo que la organización quiera guardar  la información sobre los mensajes que lleguen por el listener. Cuando un post que llegue por el listener sea tomado como base para crear un nuevo post por la organización, se cree que debe copiarse la información de dicho post de esta clase hacia la clase PostListenerContainerBase.Se almacenan por mes y año, de esta manera sera mucho mas rapido hacer las busquedas sobre las instancias de esta clase. 
   */
public class PostInListenerContainer extends org.semanticwb.social.base.PostInListenerContainerBase 
{
    public PostInListenerContainer(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
