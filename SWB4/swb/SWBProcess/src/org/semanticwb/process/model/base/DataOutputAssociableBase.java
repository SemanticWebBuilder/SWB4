package org.semanticwb.process.model.base;

public interface DataOutputAssociableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_DataOutputAssociation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataOutputAssociation");
    public static final org.semanticwb.platform.SemanticProperty swp_hasDataOutputAssociation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasDataOutputAssociation");
    public static final org.semanticwb.platform.SemanticClass swp_DataOutputAssociable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataOutputAssociable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutputAssociation> listDataOutputAssociations();
    public boolean hasDataOutputAssociation(org.semanticwb.process.model.DataOutputAssociation value);

    public void addDataOutputAssociation(org.semanticwb.process.model.DataOutputAssociation value);

    public void removeAllDataOutputAssociation();

    public void removeDataOutputAssociation(org.semanticwb.process.model.DataOutputAssociation value);

    public org.semanticwb.process.model.DataOutputAssociation getDataOutputAssociation();
}
