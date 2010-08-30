package org.semanticwb.model.base;

   /**
   * Interfaz que define propiedades para elementos que pueden ser activados / desactivados 
   */
public interface ActiveableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Indica si el elemento está activo 
   */
    public static final org.semanticwb.platform.SemanticProperty swb_active=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#active");
   /**
   * Interfaz que define propiedades para elementos que pueden ser activados / desactivados 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Activeable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Activeable");

    public boolean isActive();

    public void setActive(boolean value);
}
