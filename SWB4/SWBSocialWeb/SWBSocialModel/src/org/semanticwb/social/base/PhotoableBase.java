package org.semanticwb.social.base;

   /**
   * Interface que es utilizada para definir metodos necesarios para el envio y recepción de Post de tipo Foto en las diferentes redes sociales que así lo requieran. 
   */
public interface PhotoableBase extends org.semanticwb.social.SocialNetPostable
{
   /**
   * Interface que es utilizada para definir metodos necesarios para el envio y recepción de Post de tipo Foto en las diferentes redes sociales que así lo requieran. 
   */
    public static final org.semanticwb.platform.SemanticClass social_Photoable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Photoable");
}
