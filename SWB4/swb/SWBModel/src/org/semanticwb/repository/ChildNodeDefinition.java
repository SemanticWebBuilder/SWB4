package org.semanticwb.repository;

public interface ChildNodeDefinition extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty jcr_onParentVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#onParentVersion");
    public static final org.semanticwb.platform.SemanticProperty jcr_defaultPrimaryType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#defaultPrimaryType");
    public static final org.semanticwb.platform.SemanticProperty jcr_autoCreated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#autoCreated");
    public static final org.semanticwb.platform.SemanticProperty jcr_requiredPrimaryTypes=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#requiredPrimaryTypes");
    public static final org.semanticwb.platform.SemanticProperty jcr_sameNameSiblings=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#sameNameSiblings");
    public static final org.semanticwb.platform.SemanticProperty jcr_mandatory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#mandatory");
    public static final org.semanticwb.platform.SemanticProperty jcr_protected=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#protected");
    public static final org.semanticwb.platform.SemanticProperty jcr_name=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#name");
    public static final org.semanticwb.platform.SemanticClass nt_ChildNodeDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#childNodeDefinition");
    public String getOnParentVersion();
    public void setOnParentVersion(String onParentVersion);
    public boolean isAutoCreated();
    public void setAutoCreated(boolean autoCreated);
    public boolean isMandatory();
    public void setMandatory(boolean mandatory);
    public boolean isProtected();
    public void setProtected(boolean _protected);
    public String getName();
    public void setName(String name);
}
