package org.semanticwb.model.base;

   /**
   * Interfaz que define propiedades para los elementos que se pueden trazar (monitorear) 
   */
public interface TraceableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso. 
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
    public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
    public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
    public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
   /**
   * Interfaz que define propiedades para los elementos que se pueden trazar (monitorear) 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Traceable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Traceable");

   /**
   * Sets a value from the property ModifiedBy
   * @param value An instance of org.semanticwb.model.User
   */
    public void setModifiedBy(org.semanticwb.model.User value);

   /**
   * Remove the value from the property ModifiedBy
   */
    public void removeModifiedBy();

    public org.semanticwb.model.User getModifiedBy();

   /**
   * Sets a value from the property Creator
   * @param value An instance of org.semanticwb.model.User
   */
    public void setCreator(org.semanticwb.model.User value);

   /**
   * Remove the value from the property Creator
   */
    public void removeCreator();

    public org.semanticwb.model.User getCreator();

    public java.util.Date getCreated();

    public void setCreated(java.util.Date value);

    public java.util.Date getUpdated();

    public void setUpdated(java.util.Date value);
}
