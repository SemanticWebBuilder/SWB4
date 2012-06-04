package org.semanticwb.social.base;

   /**
   * Interface que es utilizada para definir metodos necesarios para el envio y recepción de Post de tipo Mensaje en las diferentes redes sociales que así lo requieran. 
   */
public interface MessageableBase extends org.semanticwb.social.SocialNetPostable
{
   /**
   * Interface que es utilizada para definir metodos necesarios para el envio y recepción de Post de tipo Mensaje en las diferentes redes sociales que así lo requieran. 
   */
    public static final org.semanticwb.platform.SemanticClass social_Messageable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Messageable");
}
