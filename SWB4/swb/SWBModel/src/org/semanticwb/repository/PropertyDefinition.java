package org.semanticwb.repository;

public interface PropertyDefinition extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty jcr_autoCreated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#autoCreated");
    public static final org.semanticwb.platform.SemanticProperty jcr_onParentVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#onParentVersion");
    public static final org.semanticwb.platform.SemanticProperty jcr_multiple=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#multiple");
    public static final org.semanticwb.platform.SemanticProperty jcr_protected=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#protected");
    public static final org.semanticwb.platform.SemanticProperty jcr_valueConstraints=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#valueConstraints");
    public static final org.semanticwb.platform.SemanticProperty jcr_defaultValues=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#defaultValues");
    public static final org.semanticwb.platform.SemanticProperty jcr_mandatory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#mandatory");
    public static final org.semanticwb.platform.SemanticProperty jcr_requiredType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#requiredType");
    public static final org.semanticwb.platform.SemanticProperty swbrep_internal=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#internal");
    public static final org.semanticwb.platform.SemanticClass swbrep_PropertyDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#PropertyDefinition");
    public boolean isAutoCreated();
    public void setAutoCreated(boolean autoCreated);
    public String getOnParentVersion();
    public void setOnParentVersion(String onParentVersion);
    public boolean isProtected();
    public void setProtected(boolean _protected);
    public boolean isMandatory();
    public void setMandatory(boolean mandatory);
}
