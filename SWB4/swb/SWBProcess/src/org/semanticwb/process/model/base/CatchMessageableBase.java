package org.semanticwb.process.model.base;

public interface CatchMessageableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_ItemAwareMapping=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ItemAwareMapping");
    public static final org.semanticwb.platform.SemanticProperty swp_hasItemAwareMapping=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasItemAwareMapping");
    public static final org.semanticwb.platform.SemanticClass swp_CatchMessageable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CatchMessageable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAwareMapping> listItemAwareMappings();
    public boolean hasItemAwareMapping(org.semanticwb.process.model.ItemAwareMapping value);

   /**
   * Adds the ItemAwareMapping
   * @param value An instance of org.semanticwb.process.model.ItemAwareMapping
   */
    public void addItemAwareMapping(org.semanticwb.process.model.ItemAwareMapping value);

   /**
   * Remove all the values for the property ItemAwareMapping
   */
    public void removeAllItemAwareMapping();

   /**
   * Remove a value from the property ItemAwareMapping
   * @param value An instance of org.semanticwb.process.model.ItemAwareMapping
   */
    public void removeItemAwareMapping(org.semanticwb.process.model.ItemAwareMapping value);

/**
* Gets the ItemAwareMapping
* @return a instance of org.semanticwb.process.model.ItemAwareMapping
*/
    public org.semanticwb.process.model.ItemAwareMapping getItemAwareMapping();
}
