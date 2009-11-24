package org.semanticwb.process.base;

public interface ActivityBase extends org.semanticwb.model.Traceable,org.semanticwb.process.FlowObject,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty swbps_executions=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#executions");
    public static final org.semanticwb.platform.SemanticClass swbps_Activity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Activity");
    public int getExecutions();
    public void setExecutions(int executions);
}
