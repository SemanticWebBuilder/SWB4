package org.semanticwb.process.model.base;

public interface MonitorableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_Monitoring=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Monitoring");
    public static final org.semanticwb.platform.SemanticProperty swp_monitoring=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#monitoring");
    public static final org.semanticwb.platform.SemanticClass swp_Monitorable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Monitorable");

    public void setMonitoring(org.semanticwb.process.model.Monitoring monitoring);

    public void removeMonitoring();

    public org.semanticwb.process.model.Monitoring getMonitoring();
}
