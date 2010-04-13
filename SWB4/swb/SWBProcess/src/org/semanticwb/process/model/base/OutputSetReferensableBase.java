package org.semanticwb.process.model.base;

public interface OutputSetReferensableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_OutputSet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#OutputSet");
    public static final org.semanticwb.platform.SemanticProperty swp_hasOutputSetRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasOutputSetRef");
    public static final org.semanticwb.platform.SemanticClass swp_OutputSetReferensable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#OutputSetReferensable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet> listOutputSetRefs();
    public boolean hasOutputSetRef(org.semanticwb.process.model.OutputSet value);

    public void addOutputSetRef(org.semanticwb.process.model.OutputSet value);

    public void removeAllOutputSetRef();

    public void removeOutputSetRef(org.semanticwb.process.model.OutputSet value);

    public org.semanticwb.process.model.OutputSet getOutputSetRef();
}
