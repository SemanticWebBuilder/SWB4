package org.semanticwb.social.base;

   /**
   * Interface que sera agregada a todas las redes sociales que se requieran monitorear. Esto es normalmente porque no se publica hacia las mismas al momento, como lo es el caso de YouTube, en el que envía que la publicación se encuentra en estatus de "En Proceso" y este estatus puede durar varios minutos antes de que envíe "Publicado" o talvez algún error. 
   */
public interface SocialMonitorableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Interface que sera agregada a todas las redes sociales que se requieran monitorear. Esto es normalmente porque no se publica hacia las mismas al momento, como lo es el caso de YouTube, en el que envía que la publicación se encuentra en estatus de "En Proceso" y este estatus puede durar varios minutos antes de que envíe "Publicado" o talvez algún error. 
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialMonitorable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialMonitorable");
}
