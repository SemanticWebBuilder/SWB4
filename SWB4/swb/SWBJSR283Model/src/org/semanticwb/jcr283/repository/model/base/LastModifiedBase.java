package org.semanticwb.jcr283.repository.model.base;

public interface LastModifiedBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass xsd_DateTime=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.w3.org/2001/XMLSchema#dateTime");
    public static final org.semanticwb.platform.SemanticProperty jcr_lastModified=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#lastModified");
    public static final org.semanticwb.platform.SemanticClass xsd_String=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.w3.org/2001/XMLSchema#string");
    public static final org.semanticwb.platform.SemanticProperty jcr_name=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#name");
    public static final org.semanticwb.platform.SemanticProperty jcr_lastModifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#lastModifiedBy");
    public static final org.semanticwb.platform.SemanticClass mix_LastModified=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/mix/1.0#lastModified");
    public java.util.Date getLastModified();
    public void setLastModified(java.util.Date lastModified);
    public String getName();
    public void setName(String name);
    public String getLastModifiedBy();
    public void setLastModifiedBy(String lastModifiedBy);
}
