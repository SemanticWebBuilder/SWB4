package org.semanticwb.process.model.base;

public interface ThrowMessageableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_ItemAware=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ItemAware");
    public static final org.semanticwb.platform.SemanticProperty swp_hasMessageItemAware=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasMessageItemAware");
    public static final org.semanticwb.platform.SemanticClass swp_ThrowMessageable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ThrowMessageable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAware> listMessageItemAwares();
    public boolean hasMessageItemAware(org.semanticwb.process.model.ItemAware value);

   /**
   * Adds the MessageItemAware
   * @param value An instance of org.semanticwb.process.model.ItemAware
   */
    public void addMessageItemAware(org.semanticwb.process.model.ItemAware value);

   /**
   * Remove all the values for the property MessageItemAware
   */
    public void removeAllMessageItemAware();

   /**
   * Remove a value from the property MessageItemAware
   * @param value An instance of org.semanticwb.process.model.ItemAware
   */
    public void removeMessageItemAware(org.semanticwb.process.model.ItemAware value);

/**
* Gets the MessageItemAware
* @return a instance of org.semanticwb.process.model.ItemAware
*/
    public org.semanticwb.process.model.ItemAware getMessageItemAware();
}
