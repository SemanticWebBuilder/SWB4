package org.semanticwb.model.base;

public interface WebPageableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Una Página Web es el elemento de SemanticWebBuilder a través del cual se estructura la información del portal. 
   */
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty swb_webPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPage");
    public static final org.semanticwb.platform.SemanticClass swb_WebPageable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPageable");

   /**
   * Sets a value from the property WebPage
   * @param value An instance of org.semanticwb.model.WebPage
   */
    public void setWebPage(org.semanticwb.model.WebPage value);

   /**
   * Remove the value from the property WebPage
   */
    public void removeWebPage();

    public org.semanticwb.model.WebPage getWebPage();
}
