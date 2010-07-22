package org.semanticwb.model.base;

public interface DeviceableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swb_Device=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Device");
    public static final org.semanticwb.platform.SemanticProperty swb_device=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#device");
    public static final org.semanticwb.platform.SemanticClass swb_Deviceable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Deviceable");

    public void setDevice(org.semanticwb.model.Device value);

    public void removeDevice();

    public org.semanticwb.model.Device getDevice();
}
