package org.semanticwb.social;

   /**
   * Interface que sirve para definir metodos que son utiles para el monitoreo de PostOuts en las redes sociales. Se monitorea si se tienen comentarios acerca de los PostOuts publicados en las mismas mediante SWBSocial. 
   */
public interface PostOutMonitorable extends org.semanticwb.social.base.PostOutMonitorableBase
{
    
    public int monitorPostOutResponses(PostOut postOut);
}
