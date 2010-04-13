package org.semanticwb.process.model.base;

public interface DataInputReferensableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_DataInput=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataInput");
    public static final org.semanticwb.platform.SemanticProperty swp_hasDataInputRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasDataInputRef");
    public static final org.semanticwb.platform.SemanticClass swp_DataInputReferensable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataInputReferensable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> listDataInputRefs();
    public boolean hasDataInputRef(org.semanticwb.process.model.DataInput value);

    public void addDataInputRef(org.semanticwb.process.model.DataInput value);

    public void removeAllDataInputRef();

    public void removeDataInputRef(org.semanticwb.process.model.DataInput value);

    public org.semanticwb.process.model.DataInput getDataInputRef();
}
