package org.semanticwb.model.base;

   /**
   * Interfaz que define propiedades para elementos que se pueden mostrar/ocultar 
   */
public interface HiddenableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Indica si el elemento es visible o no 
   */
    public static final org.semanticwb.platform.SemanticProperty swb_hidden=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hidden");
   /**
   * Interfaz que define propiedades para elementos que se pueden mostrar/ocultar 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Hiddenable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Hiddenable");

    public boolean isHidden();

    public void setHidden(boolean value);
}
