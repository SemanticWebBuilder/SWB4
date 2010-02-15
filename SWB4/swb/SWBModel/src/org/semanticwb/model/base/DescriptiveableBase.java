package org.semanticwb.model.base;

public interface DescriptiveableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
    public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
    public static final org.semanticwb.platform.SemanticClass swb_Descriptiveable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Descriptiveable");

    public String getTitle();

    public void setTitle(String value);

    public String getTitle(String lang);

    public String getDisplayTitle(String lang);

    public void setTitle(String title, String lang);

    public String getDescription();

    public void setDescription(String value);

    public String getDescription(String lang);

    public String getDisplayDescription(String lang);

    public void setDescription(String description, String lang);
}
