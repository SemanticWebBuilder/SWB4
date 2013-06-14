package org.semanticwb.bsc.base;

   /**
   * Cualquier elemento BSC que se pueda identificar mediante un prefijo 
   */
public interface RecognizableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Abreviatura del elemento 
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_prefix=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#prefix");
   /**
   * Cualquier elemento BSC que se pueda identificar mediante un prefijo 
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Recognizable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Recognizable");

    public String getPrefix();

    public void setPrefix(String value);
}
