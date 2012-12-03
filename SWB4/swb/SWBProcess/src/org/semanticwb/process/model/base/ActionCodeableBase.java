package org.semanticwb.process.model.base;

public interface ActionCodeableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swp_actionCode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#actionCode");
    public static final org.semanticwb.platform.SemanticClass swp_ActionCodeable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ActionCodeable");

    public String getActionCode();

    public void setActionCode(String value);
}
