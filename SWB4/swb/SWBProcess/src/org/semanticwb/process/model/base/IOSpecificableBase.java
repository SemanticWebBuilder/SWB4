package org.semanticwb.process.model.base;

public interface IOSpecificableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_InputOutputSpecification=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#InputOutputSpecification");
    public static final org.semanticwb.platform.SemanticProperty swp_ioSpecification=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#ioSpecification");
    public static final org.semanticwb.platform.SemanticClass swp_IOSpecificable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#IOSpecificable");

    public void setIoSpecification(org.semanticwb.process.model.InputOutputSpecification inputoutputspecification);

    public void removeIoSpecification();

    public org.semanticwb.process.model.InputOutputSpecification getIoSpecification();
}
