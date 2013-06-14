package org.semanticwb.bsc.base;

   /**
   * Cualquier elemento BSC que utilice prioridades 
   */
public interface PreferenceBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty bsc_priority=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#priority");
   /**
   * Cualquier elemento BSC que utilice prioridades 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Preference=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Preference");

    public String getPriority();

    public void setPriority(String value);
}
