package org.semanticwb.jcr283.repository.model.base;

public interface CreatedBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass xsd_String=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.w3.org/2001/XMLSchema#string");
    public static final org.semanticwb.platform.SemanticProperty jcr_name=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#name");
    public static final org.semanticwb.platform.SemanticProperty jcr_createdBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#createdBy");
    public static final org.semanticwb.platform.SemanticClass mix_Created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/mix/1.0#created");

    public String getName();

    public void setName(String value);

    public String getCreatedBy();

    public void setCreatedBy(String value);
}
