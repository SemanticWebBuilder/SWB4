package org.semanticwb.social.base;

   /**
   * Interface que sirve para definir metodos que son utiles para el monitoreo de PostOuts en las redes sociales. Se monitorea si se tienen comentarios acerca de los PostOuts publicados en las mismas mediante SWBSocial. 
   */
public interface PostOutMonitorableBase extends org.semanticwb.social.SocialNetPostable
{
   /**
   * Interface que sirve para definir metodos que son utiles para el monitoreo de PostOuts en las redes sociales. Se monitorea si se tienen comentarios acerca de los PostOuts publicados en las mismas mediante SWBSocial. 
   */
    public static final org.semanticwb.platform.SemanticClass social_PostOutMonitorable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOutMonitorable");
}
