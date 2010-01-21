package org.semanticwb.jcr283.repository.model.base;

public interface LanguageBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass xsd_String=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.w3.org/2001/XMLSchema#string");
    public static final org.semanticwb.platform.SemanticProperty jcr_name=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#name");
    public static final org.semanticwb.platform.SemanticProperty jcr_language=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#language");
    public static final org.semanticwb.platform.SemanticClass mix_Language=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/mix/1.0#language");
    public String getName();
    public void setName(String name);
    public String getLanguage();
    public void setLanguage(String language);
}
