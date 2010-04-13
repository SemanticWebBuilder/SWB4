package org.semanticwb.process.model.base;

public interface CollectionableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swp_collection=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#collection");
    public static final org.semanticwb.platform.SemanticClass swp_Collectionable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Collectionable");

    public boolean isCollection();

    public void setCollection(boolean value);
}
