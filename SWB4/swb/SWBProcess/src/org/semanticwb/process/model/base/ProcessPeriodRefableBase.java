package org.semanticwb.process.model.base;

public interface ProcessPeriodRefableBase extends org.semanticwb.model.Referensable
{
    public static final org.semanticwb.platform.SemanticClass swp_ProcessPeriodRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessPeriodRef");
    public static final org.semanticwb.platform.SemanticProperty swp_hasProcessPeriodRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasProcessPeriodRef");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessPeriodRefable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessPeriodRefable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessPeriodRef> listProcessPeriodRefs();
    public boolean hasProcessPeriodRef(org.semanticwb.process.model.ProcessPeriodRef value);

   /**
   * Adds the ProcessPeriodRef
   * @param value An instance of org.semanticwb.process.model.ProcessPeriodRef
   */
    public void addProcessPeriodRef(org.semanticwb.process.model.ProcessPeriodRef value);

   /**
   * Remove all the values for the property ProcessPeriodRef
   */
    public void removeAllProcessPeriodRef();

   /**
   * Remove a value from the property ProcessPeriodRef
   * @param value An instance of org.semanticwb.process.model.ProcessPeriodRef
   */
    public void removeProcessPeriodRef(org.semanticwb.process.model.ProcessPeriodRef value);

/**
* Gets the ProcessPeriodRef
* @return a instance of org.semanticwb.process.model.ProcessPeriodRef
*/
    public org.semanticwb.process.model.ProcessPeriodRef getProcessPeriodRef();
}
