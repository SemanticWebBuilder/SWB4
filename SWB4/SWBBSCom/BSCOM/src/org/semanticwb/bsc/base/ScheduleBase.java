package org.semanticwb.bsc.base;

public interface ScheduleBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty bsc_plannedStart=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#plannedStart");
    public static final org.semanticwb.platform.SemanticProperty bsc_actualEnd=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#actualEnd");
    public static final org.semanticwb.platform.SemanticProperty bsc_plannedEnd=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#plannedEnd");
    public static final org.semanticwb.platform.SemanticProperty bsc_actualStart=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#actualStart");
    public static final org.semanticwb.platform.SemanticClass bsc_Schedule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Schedule");

    public java.util.Date getPlannedStart();

    public void setPlannedStart(java.util.Date value);

    public java.util.Date getActualEnd();

    public void setActualEnd(java.util.Date value);

    public java.util.Date getPlannedEnd();

    public void setPlannedEnd(java.util.Date value);

    public java.util.Date getActualStart();

    public void setActualStart(java.util.Date value);
}
