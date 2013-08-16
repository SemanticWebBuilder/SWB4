package org.semanticwb.process.model.base;

   /**
   * Comportamiento que incluye las propiedades para elementos del BPMS que pueden serializarse en algún formato 
   */
public interface BPMNSerializableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Mime-type para los datos de la serialización de un elemento de BPMN 
   */
    public static final org.semanticwb.platform.SemanticProperty swp_serializationFormat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#serializationFormat");
   /**
   * Stores serialization data for a BPMN element 
   */
    public static final org.semanticwb.platform.SemanticProperty swp_serializationData=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#serializationData");
   /**
   * Comportamiento que incluye las propiedades para elementos del BPMS que pueden serializarse en algún formato 
   */
    public static final org.semanticwb.platform.SemanticClass swp_BPMNSerializable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#BPMNSerializable");

    public String getFormat();

    public void setFormat(String value);

    public String getData();

    public void setData(String value);
}
