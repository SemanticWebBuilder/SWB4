package org.semanticwb.social.base;

   /**
   * Interface que se les agrega a las redes sociales a las cuales se pueda leer el klout de sus usuarios. Segun Klout, en este momento solo soporta twitter y google+ 
   */
public interface KloutableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Interface que se les agrega a las redes sociales a las cuales se pueda leer el klout de sus usuarios. Segun Klout, en este momento solo soporta twitter y google+ 
   */
    public static final org.semanticwb.platform.SemanticClass social_Kloutable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Kloutable");
}
