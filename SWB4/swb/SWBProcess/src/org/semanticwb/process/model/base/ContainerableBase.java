package org.semanticwb.process.model.base;

public interface ContainerableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_GraphicalElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#GraphicalElement");
    public static final org.semanticwb.platform.SemanticProperty swp_hasContainedInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasContainedInv");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessClass");
    public static final org.semanticwb.platform.SemanticProperty swp_hasProcessClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasProcessClass");
    public static final org.semanticwb.platform.SemanticClass swp_Containerable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Containerable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> listContaineds();
    public boolean hasContained(org.semanticwb.process.model.GraphicalElement value);

    public org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject> listProcessClasses();

    public void addProcessClass(org.semanticwb.platform.SemanticObject value);

    public void removeAllProcessClass();

    public void removeProcessClass(org.semanticwb.platform.SemanticObject value);

    public org.semanticwb.platform.SemanticObject getProcessClass();
}
