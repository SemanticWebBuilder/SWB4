package org.semanticwb.model.base;

   /**
   * Interfaz que define propiedades para elementos que pueden ser representados con un ícono 
   */
public interface IconableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swb_iconClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#iconClass");
   /**
   * Interfaz que define propiedades para elementos que pueden ser representados con un ícono 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Iconable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Iconable");

    public String getIconClass();

    public void setIconClass(String value);
}
