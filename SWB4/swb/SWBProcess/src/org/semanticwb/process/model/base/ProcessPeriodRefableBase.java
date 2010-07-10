package org.semanticwb.process.model.base;

public interface ProcessPeriodRefableBase extends org.semanticwb.model.Referensable
{
    public static final org.semanticwb.platform.SemanticClass swp_ProcessPeriodRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessPeriodRef");
    public static final org.semanticwb.platform.SemanticProperty swp_hasProcessPeriodRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasProcessPeriodRef");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessPeriodRefable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessPeriodRefable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessPeriodRef> listProcessPeriodRefs();
    public boolean hasProcessPeriodRef(org.semanticwb.process.model.ProcessPeriodRef value);

    public void addProcessPeriodRef(org.semanticwb.process.model.ProcessPeriodRef value);

    public void removeAllProcessPeriodRef();

    public void removeProcessPeriodRef(org.semanticwb.process.model.ProcessPeriodRef value);

    public org.semanticwb.process.model.ProcessPeriodRef getProcessPeriodRef();
}
