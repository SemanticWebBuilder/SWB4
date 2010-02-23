package org.semanticwb.repository.base;

public interface TraceableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty jcr_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#created");
    public static final org.semanticwb.platform.SemanticClass swbrep_Traceable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#Traceable");

    public java.util.Date getCreated();

    public void setCreated(java.util.Date value);
}
