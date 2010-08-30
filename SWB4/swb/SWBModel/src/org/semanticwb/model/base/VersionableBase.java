package org.semanticwb.model.base;

   /**
   * Interfaz que define propiedades para los elementos que pueden ser versionados 
   */
public interface VersionableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swb_VersionInfo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#VersionInfo");
    public static final org.semanticwb.platform.SemanticProperty swb_actualVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#actualVersion");
    public static final org.semanticwb.platform.SemanticProperty swb_lastVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#lastVersion");
   /**
   * Interfaz que define propiedades para los elementos que pueden ser versionados 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Versionable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Versionable");

   /**
   * Sets a value from the property ActualVersion
   * @param value An instance of org.semanticwb.model.VersionInfo
   */
    public void setActualVersion(org.semanticwb.model.VersionInfo value);

   /**
   * Remove the value from the property ActualVersion
   */
    public void removeActualVersion();

    public org.semanticwb.model.VersionInfo getActualVersion();

   /**
   * Sets a value from the property LastVersion
   * @param value An instance of org.semanticwb.model.VersionInfo
   */
    public void setLastVersion(org.semanticwb.model.VersionInfo value);

   /**
   * Remove the value from the property LastVersion
   */
    public void removeLastVersion();

    public org.semanticwb.model.VersionInfo getLastVersion();
}
