package org.semanticwb.social;


   /**
   * Clase a Cambiar despues por "Relacional".  Clase que va ha contener los Post que han sido tomados como base (es decir, que llegan por el listener y que se guardan en la clase PostListenerContainer) para crear un nuevo Post desde la herramienta y que se envía hacia las redes sociales.Si se elimina un post que ha sido tomado como base(PostIn), se debe de eliminar la instancia asociada de esta clase (en la propiedad plcb_Post). 
   */
public class PostOutListenerContainerBase extends org.semanticwb.social.base.PostOutListenerContainerBaseBase 
{
    public PostOutListenerContainerBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
