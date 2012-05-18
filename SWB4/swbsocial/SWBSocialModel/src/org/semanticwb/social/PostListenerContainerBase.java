package org.semanticwb.social;


   /**
   * Clase que va ha contener los Post que han sido tomados como base (es decir, que llegan por el listener y que se guardan en la clase PostListenerContainer) para crear un nuevo Post desde la herramienta y que se envía hacia las redes sociales. 
   */
public class PostListenerContainerBase extends org.semanticwb.social.base.PostListenerContainerBaseBase 
{
    public PostListenerContainerBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
