package org.semanticwb.process.model.base;

public interface ShapeableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_BPMNShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNShape");
    public static final org.semanticwb.platform.SemanticProperty swp_hasBPMNShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasBPMNShape");
    public static final org.semanticwb.platform.SemanticClass swp_Shapeable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Shapeable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNShape> listBPMNShapes();
    public boolean hasBPMNShape(org.semanticwb.process.model.BPMNShape value);

    public void addBPMNShape(org.semanticwb.process.model.BPMNShape value);

    public void removeAllBPMNShape();

    public void removeBPMNShape(org.semanticwb.process.model.BPMNShape value);

    public org.semanticwb.process.model.BPMNShape getBPMNShape();
}
