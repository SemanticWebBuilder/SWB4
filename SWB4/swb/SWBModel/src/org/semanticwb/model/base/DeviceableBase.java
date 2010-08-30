package org.semanticwb.model.base;

public interface DeviceableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Un dispositivo es un elemento que tiene la capacidad de leer una Página Web, por ejemplo: un PDA, una PC o un celular. En SemanticWebBuilder se pueden encontrar algunos dispositivos ya definidos. 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Device=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Device");
    public static final org.semanticwb.platform.SemanticProperty swb_device=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#device");
    public static final org.semanticwb.platform.SemanticClass swb_Deviceable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Deviceable");

   /**
   * Sets a value from the property Device
   * @param value An instance of org.semanticwb.model.Device
   */
    public void setDevice(org.semanticwb.model.Device value);

   /**
   * Remove the value from the property Device
   */
    public void removeDevice();

    public org.semanticwb.model.Device getDevice();
}
