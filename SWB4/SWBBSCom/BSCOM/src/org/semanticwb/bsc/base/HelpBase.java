package org.semanticwb.bsc.base;

   /**
   * Provee una propiedad para mostrar ayuda en línea en contexto 
   */
public interface HelpBase extends org.semanticwb.model.GenericObject
{
   /**
   * Ayuda en línea 
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_help=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#help");
   /**
   * Provee una propiedad para mostrar ayuda en línea en contexto 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Help=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Help");

    public String getHelp();

    public void setHelp(String value);
}
