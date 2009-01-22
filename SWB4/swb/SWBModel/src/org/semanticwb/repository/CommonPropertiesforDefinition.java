package org.semanticwb.repository;

public interface CommonPropertiesforDefinition extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty jcr_autoCreated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#autoCreated");
    public static final org.semanticwb.platform.SemanticProperty jcr_mandatory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#mandatory");
    public static final org.semanticwb.platform.SemanticProperty jcr_onParentVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#onParentVersion");
    public static final org.semanticwb.platform.SemanticProperty jcr_protected=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#protected");
    public static final org.semanticwb.platform.SemanticClass swbrep_CommonPropertiesforDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#CommonPropertiesforDefinition");
    public boolean isAutoCreated();
    public void setAutoCreated(boolean autoCreated);
    public boolean isMandatory();
    public void setMandatory(boolean mandatory);
    public String getOnParentVersion();
    public void setOnParentVersion(String onParentVersion);
    public boolean isProtected();
    public void setProtected(boolean _protected);
}
