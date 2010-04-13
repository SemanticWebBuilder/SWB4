package org.semanticwb.process.model.base;

public interface DocumentableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_Documentation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Documentation");
    public static final org.semanticwb.platform.SemanticProperty swp_hasDocumentation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasDocumentation");
    public static final org.semanticwb.platform.SemanticClass swp_Documentable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Documentable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Documentation> listDocumentations();
    public boolean hasDocumentation(org.semanticwb.process.model.Documentation value);

    public void addDocumentation(org.semanticwb.process.model.Documentation value);

    public void removeAllDocumentation();

    public void removeDocumentation(org.semanticwb.process.model.Documentation value);

    public org.semanticwb.process.model.Documentation getDocumentation();
}
