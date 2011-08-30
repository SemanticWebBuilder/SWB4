package org.semanticwb.model.base;

   /**
   * Interfaz usada para agregar prefijos de los namespace usados para filtar las opcines de la administración dependiendo de las ontologias instaladas 
   */
public interface NSPrefixFilterableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swbxf_nsPrefixFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#nsPrefixFilter");
   /**
   * Interfaz usada para agregar prefijos de los namespace usados para filtar las opcines de la administración dependiendo de las ontologias instaladas 
   */
    public static final org.semanticwb.platform.SemanticClass swbxf_NSPrefixFilterable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#NSPrefixFilterable");

    public String getNsPrefixFilter();

    public void setNsPrefixFilter(String value);
}
