package org.semanticwb.social.base;

   /**
   * Esta interfaz se le agrega a las clases de tipo SocialNetwork que se va ha desear que escuchen en las redes sociales, lo cual en teoría deberían de ser a todas, pero por si en un futuro de desea que no sea así. 
   */
public interface ListenerableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Esta interfaz se le agrega a las clases de tipo SocialNetwork que se va ha desear que escuchen en las redes sociales, lo cual en teoría deberían de ser a todas, pero por si en un futuro de desea que no sea así. 
   */
    public static final org.semanticwb.platform.SemanticClass social_Listenerable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Listenerable");
}
