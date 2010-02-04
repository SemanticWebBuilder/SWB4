package org.semanticwb.process.model.base;

public interface IOAbleBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_InputSet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#InputSet");
    public static final org.semanticwb.platform.SemanticProperty swp_hasInputSet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasInputSet");
    public static final org.semanticwb.platform.SemanticClass swp_OutputSet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#OutputSet");
    public static final org.semanticwb.platform.SemanticProperty swp_hasOutputSet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasOutputSet");
    public static final org.semanticwb.platform.SemanticClass swp_IOAble=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#IOAble");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet> listInputSets();
    public boolean hasInputSet(org.semanticwb.process.model.InputSet inputset);

    public void addInputSet(org.semanticwb.process.model.InputSet inputset);

    public void removeAllInputSet();

    public void removeInputSet(org.semanticwb.process.model.InputSet inputset);

    public org.semanticwb.process.model.InputSet getInputSet();

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet> listOutputSets();
    public boolean hasOutputSet(org.semanticwb.process.model.OutputSet outputset);

    public void addOutputSet(org.semanticwb.process.model.OutputSet outputset);

    public void removeAllOutputSet();

    public void removeOutputSet(org.semanticwb.process.model.OutputSet outputset);

    public org.semanticwb.process.model.OutputSet getOutputSet();
}
