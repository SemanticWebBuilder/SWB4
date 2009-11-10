package org.semanticwb.process.base;

public interface TaskBase extends org.semanticwb.process.FlowObject,org.semanticwb.process.Activity,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty swbps_keepOpen=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#keepOpen");
    public static final org.semanticwb.platform.SemanticClass swbps_Task=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Task");
    public boolean isKeepOpen();
    public void setKeepOpen(boolean keepOpen);
}
