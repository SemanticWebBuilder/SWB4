package org.semanticwb.process.model.base;

public interface ProcessReferensableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_Process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Process");
    public static final org.semanticwb.platform.SemanticProperty swp_processRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#processRef");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessReferensable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ProcessReferensable");

    public void setProcessRef(org.semanticwb.process.model.Process process);

    public void removeProcessRef();

    public org.semanticwb.process.model.Process getProcessRef();
}
