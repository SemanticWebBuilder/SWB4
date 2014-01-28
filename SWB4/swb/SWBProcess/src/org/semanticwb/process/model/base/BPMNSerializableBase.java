package org.semanticwb.process.model.base;

   /**
   * Behavior that includes properties for serializable BPMS elements 
   */
public interface BPMNSerializableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Mime-type for a BPMN element's serialization data 
   */
    public static final org.semanticwb.platform.SemanticProperty swp_serializationFormat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#serializationFormat");
   /**
   * Almacena los datos de la serialización del elementp de BPMN 
   */
    public static final org.semanticwb.platform.SemanticProperty swp_serializationData=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#serializationData");
   /**
   * Behavior that includes properties for serializable BPMS elements 
   */
    public static final org.semanticwb.platform.SemanticClass swp_BPMNSerializable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#BPMNSerializable");

    public String getFormat();

    public void setFormat(String value);

    public String getData();

    public void setData(String value);
}
