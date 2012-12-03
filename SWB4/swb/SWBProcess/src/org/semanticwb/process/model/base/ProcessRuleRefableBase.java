package org.semanticwb.process.model.base;

public interface ProcessRuleRefableBase extends org.semanticwb.model.Referensable
{
    public static final org.semanticwb.platform.SemanticClass swp_ProcessRuleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessRuleRef");
    public static final org.semanticwb.platform.SemanticProperty swp_hasProcessRuleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasProcessRuleRef");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessRuleRefable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessRuleRefable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRuleRef> listProcessRuleRefs();
    public boolean hasProcessRuleRef(org.semanticwb.process.model.ProcessRuleRef value);

   /**
   * Adds the ProcessRuleRef
   * @param value An instance of org.semanticwb.process.model.ProcessRuleRef
   */
    public void addProcessRuleRef(org.semanticwb.process.model.ProcessRuleRef value);

   /**
   * Remove all the values for the property ProcessRuleRef
   */
    public void removeAllProcessRuleRef();

   /**
   * Remove a value from the property ProcessRuleRef
   * @param value An instance of org.semanticwb.process.model.ProcessRuleRef
   */
    public void removeProcessRuleRef(org.semanticwb.process.model.ProcessRuleRef value);

/**
* Gets the ProcessRuleRef
* @return a instance of org.semanticwb.process.model.ProcessRuleRef
*/
    public org.semanticwb.process.model.ProcessRuleRef getProcessRuleRef();
}
