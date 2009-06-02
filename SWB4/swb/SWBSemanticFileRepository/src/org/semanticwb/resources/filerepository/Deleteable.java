package org.semanticwb.resources.filerepository;

public interface Deleteable extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swbfilerep_deleted=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/filerepository#deleted");
    public static final org.semanticwb.platform.SemanticClass swbfilerep_Deleteable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/filerepository#Deleteable");
    public boolean isDeleted();
    public void setDeleted(boolean deleted);
}
