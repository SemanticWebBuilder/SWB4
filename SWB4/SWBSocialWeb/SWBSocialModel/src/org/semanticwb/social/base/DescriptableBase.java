package org.semanticwb.social.base;

   /**
   * Descripción, cree esta interfaz y no utilicé swb:Descriptable, porque solo utilizo el campo description, no así el de título. 
   */
public interface DescriptableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Descripción 
   */
    public static final org.semanticwb.platform.SemanticProperty social_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#description");
   /**
   * Descripción, cree esta interfaz y no utilicé swb:Descriptable, porque solo utilizo el campo description, no así el de título. 
   */
    public static final org.semanticwb.platform.SemanticClass social_Descriptable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Descriptable");

    public String getDescription();

    public void setDescription(String value);
}
