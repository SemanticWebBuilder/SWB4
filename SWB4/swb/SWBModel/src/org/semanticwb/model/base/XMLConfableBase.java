package org.semanticwb.model.base;

   /**
   * Interfaz que define propiedades para los elementos que pueden tener un campo XML 
   */
public interface XMLConfableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swb_xmlConf=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#xmlConf");
   /**
   * Interfaz que define propiedades para los elementos que pueden tener un campo XML 
   */
    public static final org.semanticwb.platform.SemanticClass swb_XMLConfable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#XMLConfable");

    public String getXmlConf();

    public void setXmlConf(String value);
}
