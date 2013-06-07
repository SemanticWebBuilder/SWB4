package org.semanticwb.social;


   /**
   * En este objeto se guardara el identificador que es asignado para cada post en cada una de las redes sociales, es decir, si un mismo post se envía hacia mas de una red social, cada una de esas redes sociales daran un identificador unico para ese post en esa red social, este lo tenemos que guardar nosotros en este objeto para fines de monitoreo de estatus del post en esa red social (En Proceso, Revisado, Publicado, etc), como nosotros para un post, independientemente de a cuantas redes sociales se envíe, solo creamos un objeto Post (Message, Photo, Video), tuvimos que crear esta clase para guardar el identificador de ese post para c/red social. 
   */
public class PostOutNet extends org.semanticwb.social.base.PostOutNetBase 
{
    public PostOutNet(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
