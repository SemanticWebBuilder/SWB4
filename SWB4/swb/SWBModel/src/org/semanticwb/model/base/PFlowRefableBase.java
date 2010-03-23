package org.semanticwb.model.base;

public interface PFlowRefableBase extends org.semanticwb.model.Referensable
{
    public static final org.semanticwb.platform.SemanticClass swb_PFlowRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowRef");
    public static final org.semanticwb.platform.SemanticProperty swb_hasPFlowRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasPFlowRef");
    public static final org.semanticwb.platform.SemanticClass swb_PFlowRefable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowRefable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef> listPFlowRefs();
    public boolean hasPFlowRef(org.semanticwb.model.PFlowRef pflowref);
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef> listInheritPFlowRefs();

    public void addPFlowRef(org.semanticwb.model.PFlowRef pflowref);

    public void removeAllPFlowRef();

    public void removePFlowRef(org.semanticwb.model.PFlowRef pflowref);

    public org.semanticwb.model.PFlowRef getPFlowRef();
}
