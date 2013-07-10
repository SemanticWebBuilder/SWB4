package org.semanticwb.social;

   /**
   * Interface que sera agregada a todas las redes sociales que se requieran monitorear. Esto es normalmente porque no se publica hacia las mismas al momento, como lo es el caso de YouTube, en el que envía que la publicación se encuentra en estatus de "En Proceso" y este estatus puede durar varios minutos antes de que envíe "Publicado" o talvez algún error. 
   */
public interface SocialMonitorable extends org.semanticwb.social.base.SocialMonitorableBase
{
    public boolean isPublished(PostOutNet postOutNet);
}
