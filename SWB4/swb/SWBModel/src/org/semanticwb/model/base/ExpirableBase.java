package org.semanticwb.model.base;

   /**
   * Interfaz que define propiedades para elementos que tienen una fecha de expiración 
   */
public interface ExpirableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Expiration date 
   */
    public static final org.semanticwb.platform.SemanticProperty swb_expiration=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#expiration");
   /**
   * Interfaz que define propiedades para elementos que tienen una fecha de expiración 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Expirable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Expirable");

    public java.util.Date getExpiration();

    public void setExpiration(java.util.Date value);
}
