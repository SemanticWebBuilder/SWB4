package org.semanticwb.process.model.base;

public interface GateableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swp_gatewayType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#gatewayType");
    public static final org.semanticwb.platform.SemanticClass swp_Gate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Gate");
    public static final org.semanticwb.platform.SemanticProperty swp_hasGate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasGate");
    public static final org.semanticwb.platform.SemanticProperty swp_exclusiveType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#exclusiveType");
    public static final org.semanticwb.platform.SemanticProperty swp_defaultGate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#defaultGate");
    public static final org.semanticwb.platform.SemanticClass swp_Gateable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Gateable");

    public String getGatewayType();

    public void setGatewayType(String value);

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Gate> listGates();
    public boolean hasGate(org.semanticwb.process.model.Gate gate);

    public void addGate(org.semanticwb.process.model.Gate gate);

    public void removeAllGate();

    public void removeGate(org.semanticwb.process.model.Gate gate);

    public org.semanticwb.process.model.Gate getGate();

    public String getExclusiveType();

    public void setExclusiveType(String value);

    public void setDefaultGate(org.semanticwb.process.model.Gate gate);

    public void removeDefaultGate();

    public org.semanticwb.process.model.Gate getDefaultGate();
}
