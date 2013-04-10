package org.semanticwb.social.base;

   /**
   * Interface que contiene propiedades que sirven para el envío de emails 
   */
public interface EmailableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Email de una o varias personas 
   */
    public static final org.semanticwb.platform.SemanticProperty social_email=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#email");
   /**
   * Interface que contiene propiedades que sirven para el envío de emails 
   */
    public static final org.semanticwb.platform.SemanticClass social_Emailable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Emailable");

    public String getEmail();

    public void setEmail(String value);
}
