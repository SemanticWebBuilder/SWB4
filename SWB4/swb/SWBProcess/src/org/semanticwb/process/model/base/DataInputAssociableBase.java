package org.semanticwb.process.model.base;

public interface DataInputAssociableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_DataInputAssociation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataInputAssociation");
    public static final org.semanticwb.platform.SemanticProperty swp_hasDataInputAssociation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasDataInputAssociation");
    public static final org.semanticwb.platform.SemanticClass swp_DataInputAssociable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataInputAssociable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInputAssociation> listDataInputAssociations();
    public boolean hasDataInputAssociation(org.semanticwb.process.model.DataInputAssociation value);

    public void addDataInputAssociation(org.semanticwb.process.model.DataInputAssociation value);

    public void removeAllDataInputAssociation();

    public void removeDataInputAssociation(org.semanticwb.process.model.DataInputAssociation value);

    public org.semanticwb.process.model.DataInputAssociation getDataInputAssociation();
}
