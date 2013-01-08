package org.semanticwb.model.base;

   /**
   * Interfaz que define propiedades para los elementos que pueden tener un campo XML 
   */
public interface XMLableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swb_xml=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#xml");
   /**
   * Interfaz que define propiedades para los elementos que pueden tener un campo XML 
   */
    public static final org.semanticwb.platform.SemanticClass swb_XMLable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#XMLable");

    public String getXml();

    public void setXml(String value);
}
