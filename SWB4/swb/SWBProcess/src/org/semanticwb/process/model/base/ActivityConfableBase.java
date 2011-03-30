package org.semanticwb.process.model.base;

public interface ActivityConfableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swp_forCompensation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#forCompensation");
    public static final org.semanticwb.platform.SemanticClass swp_LoopCharacteristics=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#LoopCharacteristics");
    public static final org.semanticwb.platform.SemanticProperty swp_loopCharacteristics=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#loopCharacteristics");
    public static final org.semanticwb.platform.SemanticClass swp_ActivityConfable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ActivityConfable");

    public boolean isForCompensation();

    public void setForCompensation(boolean value);

   /**
   * Sets a value from the property LoopCharacteristics
   * @param value An instance of org.semanticwb.process.model.LoopCharacteristics
   */
    public void setLoopCharacteristics(org.semanticwb.process.model.LoopCharacteristics value);

   /**
   * Remove the value from the property LoopCharacteristics
   */
    public void removeLoopCharacteristics();

    public org.semanticwb.process.model.LoopCharacteristics getLoopCharacteristics();
}
