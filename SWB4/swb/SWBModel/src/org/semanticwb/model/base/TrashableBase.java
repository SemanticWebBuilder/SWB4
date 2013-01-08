package org.semanticwb.model.base;

   /**
   * Interfaz que define propiedades para los elementos que puedenenviados a la papelera de reciclaje 
   */
public interface TrashableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swb_deleted=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#deleted");
   /**
   * Interfaz que define propiedades para los elementos que puedenenviados a la papelera de reciclaje 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Trashable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Trashable");

    public boolean isDeleted();

    public void setDeleted(boolean value);
}
