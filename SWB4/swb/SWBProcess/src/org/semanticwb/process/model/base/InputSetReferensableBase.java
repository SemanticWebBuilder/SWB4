package org.semanticwb.process.model.base;

public interface InputSetReferensableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_InputSet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#InputSet");
    public static final org.semanticwb.platform.SemanticProperty swp_hasInputSetRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasInputSetRef");
    public static final org.semanticwb.platform.SemanticClass swp_InputSetReferensable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#InputSetReferensable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet> listInputSetRefs();
    public boolean hasInputSetRef(org.semanticwb.process.model.InputSet value);

    public void addInputSetRef(org.semanticwb.process.model.InputSet value);

    public void removeAllInputSetRef();

    public void removeInputSetRef(org.semanticwb.process.model.InputSet value);

    public org.semanticwb.process.model.InputSet getInputSetRef();
}
