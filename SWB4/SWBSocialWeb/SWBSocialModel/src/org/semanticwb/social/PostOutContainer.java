package org.semanticwb.social;


   /**
   * Clase a Cambiar despues por "Relacional". Clase que contiene TODOS los post de salida (PostOut) que han sido enviados a una determinada red social. La intención de crear esta clase es para que se agrupen los Post de cada red social por mes y año, y de esta manera sea mucho mas sencillo, optimo y rapido realizar las busquedas. De acuerdo al PostOut, puede buscar si este existe en la clase PostListenerContainerBase, si si existe, quiere decir que es un PostOut que se origino mediante la contestación de un PostIn, si no existe en esa clase, es que es un PostOut que se origino como nuevo, sin que haya sido contestación de un PostIn. 
   */
public class PostOutContainer extends org.semanticwb.social.base.PostOutContainerBase 
{
    public PostOutContainer(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
