package org.semanticwb.process.model.base;

public interface FlowElementsContainerableBase extends org.semanticwb.process.model.Artifactable
{
    public static final org.semanticwb.platform.SemanticClass swp_FlowElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#FlowElement");
    public static final org.semanticwb.platform.SemanticProperty swp_hasFlowElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasFlowElement");
    public static final org.semanticwb.platform.SemanticClass swp_FlowElementsContainerable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#FlowElementsContainerable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowElement> listFlowElements();
    public boolean hasFlowElement(org.semanticwb.process.model.FlowElement value);

    public void addFlowElement(org.semanticwb.process.model.FlowElement value);

    public void removeAllFlowElement();

    public void removeFlowElement(org.semanticwb.process.model.FlowElement value);

    public org.semanticwb.process.model.FlowElement getFlowElement();
}
