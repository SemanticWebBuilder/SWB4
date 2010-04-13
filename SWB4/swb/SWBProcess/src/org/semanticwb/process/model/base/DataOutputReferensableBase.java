package org.semanticwb.process.model.base;

public interface DataOutputReferensableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_DataOutput=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataOutput");
    public static final org.semanticwb.platform.SemanticProperty swp_hasDataOutputRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasDataOutputRef");
    public static final org.semanticwb.platform.SemanticClass swp_DataOutputReferensable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataOutputReferensable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> listDataOutputRefs();
    public boolean hasDataOutputRef(org.semanticwb.process.model.DataOutput value);

    public void addDataOutputRef(org.semanticwb.process.model.DataOutput value);

    public void removeAllDataOutputRef();

    public void removeDataOutputRef(org.semanticwb.process.model.DataOutput value);

    public org.semanticwb.process.model.DataOutput getDataOutputRef();
}
