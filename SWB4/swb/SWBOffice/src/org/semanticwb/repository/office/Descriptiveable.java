package org.semanticwb.repository.office;

public interface Descriptiveable extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swboffice_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#title");
    public static final org.semanticwb.platform.SemanticProperty swboffice_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#description");
    public static final org.semanticwb.platform.SemanticClass swboffice_Descriptiveable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#Descriptiveable");
    public String getTitle();
    public void setTitle(String title);
    public String getDescription();
    public void setDescription(String description);
}
