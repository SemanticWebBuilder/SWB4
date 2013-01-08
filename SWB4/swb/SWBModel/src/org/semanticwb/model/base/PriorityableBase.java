package org.semanticwb.model.base;

   /**
   * Interfaz que define propiedades para los elementos que pueden ser priorizables 
   */
public interface PriorityableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swb_priority=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#priority");
   /**
   * Interfaz que define propiedades para los elementos que pueden ser priorizables 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Priorityable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Priorityable");

    public int getPriority();

    public void setPriority(int value);
}
