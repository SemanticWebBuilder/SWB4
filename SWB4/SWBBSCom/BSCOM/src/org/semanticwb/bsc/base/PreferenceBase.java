package org.semanticwb.bsc.base;

   /**
   * Cualquier elemento BSC que utilice prioridades 
   */
public interface PreferenceBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass bsc_Priority=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Priority");
    public static final org.semanticwb.platform.SemanticProperty bsc_priority=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#priority");
   /**
   * Cualquier elemento BSC que utilice prioridades 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Preference=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Preference");

   /**
   * Sets a value from the property Priority
   * @param value An instance of org.semanticwb.bsc.catalogs.Priority
   */
    public void setPriority(org.semanticwb.bsc.catalogs.Priority value);

   /**
   * Remove the value from the property Priority
   */
    public void removePriority();

    public org.semanticwb.bsc.catalogs.Priority getPriority();
}
