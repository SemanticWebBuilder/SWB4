package org.semanticwb.model.base;

   /**
   * Interfaz que define propiedades para elementos que pueden referencia a plantillas 
   */
public interface TemplateRefableBase extends org.semanticwb.model.Referensable
{
    public static final org.semanticwb.platform.SemanticProperty swb_notInheritTemplateRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#notInheritTemplateRef");
   /**
   * Referencia a un objeto de tipo Template 
   */
    public static final org.semanticwb.platform.SemanticClass swb_TemplateRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateRef");
    public static final org.semanticwb.platform.SemanticProperty swb_hasTemplateRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasTemplateRef");
   /**
   * Interfaz que define propiedades para elementos que pueden referencia a plantillas 
   */
    public static final org.semanticwb.platform.SemanticClass swb_TemplateRefable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateRefable");

    public boolean isNotInheritTemplateRef();

    public void setNotInheritTemplateRef(boolean value);

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef> listTemplateRefs();
    public boolean hasTemplateRef(org.semanticwb.model.TemplateRef value);
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef> listInheritTemplateRefs();

   /**
   * Adds the TemplateRef
   * @param value An instance of org.semanticwb.model.TemplateRef
   */
    public void addTemplateRef(org.semanticwb.model.TemplateRef value);

   /**
   * Remove all the values for the property TemplateRef
   */
    public void removeAllTemplateRef();

   /**
   * Remove a value from the property TemplateRef
   * @param value An instance of org.semanticwb.model.TemplateRef
   */
    public void removeTemplateRef(org.semanticwb.model.TemplateRef value);

/**
* Gets the TemplateRef
* @return a instance of org.semanticwb.model.TemplateRef
*/
    public org.semanticwb.model.TemplateRef getTemplateRef();
}
