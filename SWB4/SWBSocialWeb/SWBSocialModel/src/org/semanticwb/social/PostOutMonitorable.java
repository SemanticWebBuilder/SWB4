package org.semanticwb.social;

import java.util.HashMap;

   /**
   * Interface que sirve para definir metodos que son utiles para el monitoreo de PostOuts en las redes sociales. Se monitorea si se tienen comentarios acerca de los PostOuts publicados en las mismas mediante SWBSocial. 
   */
public interface PostOutMonitorable extends org.semanticwb.social.base.PostOutMonitorableBase
{
    
    public HashMap<String, Long> monitorPostOutResponses(PostOut postOut);
}
