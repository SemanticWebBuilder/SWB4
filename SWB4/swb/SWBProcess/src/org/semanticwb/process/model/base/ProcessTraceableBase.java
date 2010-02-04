package org.semanticwb.process.model.base;

public interface ProcessTraceableBase extends org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty swp_ended=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#ended");
    public static final org.semanticwb.platform.SemanticProperty swp_endedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#endedBy");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessTraceable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ProcessTraceable");

    public java.util.Date getEnded();

    public void setEnded(java.util.Date value);

    public void setEndedBy(org.semanticwb.model.User user);

    public void removeEndedBy();

    public org.semanticwb.model.User getEndedBy();
}
