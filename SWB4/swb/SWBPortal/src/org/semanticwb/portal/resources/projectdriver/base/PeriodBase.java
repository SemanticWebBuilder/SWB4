package org.semanticwb.portal.resources.projectdriver.base;

public interface PeriodBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swbproy_startDate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbproy#startDate");
    public static final org.semanticwb.platform.SemanticProperty swbproy_currentHour=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbproy#currentHour");
    public static final org.semanticwb.platform.SemanticProperty swbproy_currentPercentage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbproy#currentPercentage");
    public static final org.semanticwb.platform.SemanticProperty swbproy_plannedHour=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbproy#plannedHour");
    public static final org.semanticwb.platform.SemanticProperty swbproy_endDate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbproy#endDate");
    public static final org.semanticwb.platform.SemanticClass swbproy_Period=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbproy#Period");

    public java.util.Date getStartDate();

    public void setStartDate(java.util.Date value);

    public int getCurrentHour();

    public void setCurrentHour(int value);

    public float getCurrentPercentage();

    public void setCurrentPercentage(float value);

    public int getPlannedHour();

    public void setPlannedHour(int value);

    public java.util.Date getEndDate();

    public void setEndDate(java.util.Date value);
}
