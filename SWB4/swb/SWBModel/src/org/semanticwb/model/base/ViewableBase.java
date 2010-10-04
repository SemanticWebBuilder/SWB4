package org.semanticwb.model.base;

   /**
   * Interfaz que define propiedades para los elementos que pueden tener conteo de apariciones 
   */
public interface ViewableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swb_views=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#views");
    public static final org.semanticwb.platform.SemanticProperty swb_maxViews=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#maxViews");
   /**
   * Interfaz que define propiedades para los elementos que pueden tener conteo de apariciones 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Viewable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Viewable");

    public long getViews();

    public void setViews(long value);

    public long getMaxViews();

    public void setMaxViews(long value);
}
