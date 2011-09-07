package org.semanticwb.model.base;

public interface MetaTagableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swb_MetaTagValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#MetaTagValue");
    public static final org.semanticwb.platform.SemanticProperty swb_hasMetaTagsValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasMetaTagsValue");
    public static final org.semanticwb.platform.SemanticClass swb_MetaTagable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#MetaTagable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.MetaTagValue> listMetaTagsValues();
    public boolean hasMetaTagsValue(org.semanticwb.model.MetaTagValue value);

   /**
   * Adds the MetaTagsValue
   * @param value An instance of org.semanticwb.model.MetaTagValue
   */
    public void addMetaTagsValue(org.semanticwb.model.MetaTagValue value);

   /**
   * Remove all the values for the property MetaTagsValue
   */
    public void removeAllMetaTagsValue();

   /**
   * Remove a value from the property MetaTagsValue
   * @param value An instance of org.semanticwb.model.MetaTagValue
   */
    public void removeMetaTagsValue(org.semanticwb.model.MetaTagValue value);

/**
* Gets the MetaTagsValue
* @return a instance of org.semanticwb.model.MetaTagValue
*/
    public org.semanticwb.model.MetaTagValue getMetaTagsValue();
}
