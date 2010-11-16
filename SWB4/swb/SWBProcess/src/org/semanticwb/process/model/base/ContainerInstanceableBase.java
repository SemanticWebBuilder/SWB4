package org.semanticwb.process.model.base;

public interface ContainerInstanceableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_ProcessObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessObject");
    public static final org.semanticwb.platform.SemanticProperty swp_hasProcessObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasProcessObject");
    public static final org.semanticwb.platform.SemanticClass swp_FlowNodeInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowNodeInstance");
    public static final org.semanticwb.platform.SemanticProperty swp_hasFlowNodeInstancesInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasFlowNodeInstancesInv");
    public static final org.semanticwb.platform.SemanticClass swp_ContainerInstanceable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ContainerInstanceable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObject> listProcessObjects();
    public boolean hasProcessObject(org.semanticwb.process.model.ProcessObject value);

   /**
   * Adds the ProcessObject
   * @param value An instance of org.semanticwb.process.model.ProcessObject
   */
    public void addProcessObject(org.semanticwb.process.model.ProcessObject value);

   /**
   * Remove all the values for the property ProcessObject
   */
    public void removeAllProcessObject();

   /**
   * Remove a value from the property ProcessObject
   * @param value An instance of org.semanticwb.process.model.ProcessObject
   */
    public void removeProcessObject(org.semanticwb.process.model.ProcessObject value);

/**
* Gets the ProcessObject
* @return a instance of org.semanticwb.process.model.ProcessObject
*/
    public org.semanticwb.process.model.ProcessObject getProcessObject();

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstances();
    public boolean hasFlowNodeInstance(org.semanticwb.process.model.FlowNodeInstance value);
}
