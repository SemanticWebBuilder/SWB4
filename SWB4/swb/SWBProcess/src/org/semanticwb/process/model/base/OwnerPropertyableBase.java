package org.semanticwb.process.model.base;

public interface OwnerPropertyableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_OwnerProperty=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#OwnerProperty");
    public static final org.semanticwb.platform.SemanticProperty swp_hasOwnerProperty=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasOwnerProperty");
    public static final org.semanticwb.platform.SemanticClass swp_OwnerPropertyable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#OwnerPropertyable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OwnerProperty> listOwnerProperties();
    public boolean hasOwnerProperty(org.semanticwb.process.model.OwnerProperty value);

   /**
   * Adds the OwnerProperty
   * @param value An instance of org.semanticwb.process.model.OwnerProperty
   */
    public void addOwnerProperty(org.semanticwb.process.model.OwnerProperty value);

   /**
   * Remove all the values for the property OwnerProperty
   */
    public void removeAllOwnerProperty();

   /**
   * Remove a value from the property OwnerProperty
   * @param value An instance of org.semanticwb.process.model.OwnerProperty
   */
    public void removeOwnerProperty(org.semanticwb.process.model.OwnerProperty value);

/**
* Gets the OwnerProperty
* @return a instance of org.semanticwb.process.model.OwnerProperty
*/
    public org.semanticwb.process.model.OwnerProperty getOwnerProperty();
}
