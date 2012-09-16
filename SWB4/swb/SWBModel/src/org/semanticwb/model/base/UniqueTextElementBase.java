package org.semanticwb.model.base;


public abstract class UniqueTextElementBase extends org.semanticwb.model.Text 
{
   /**
   * Texto restringido con caracteres validos para Identificador
   */
    public static final org.semanticwb.platform.SemanticProperty swbxf_restrict4Id=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#restrict4Id");
   /**
   * Elementos separados por comas
   */
    public static final org.semanticwb.platform.SemanticProperty swbxf_reservedWords=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#reservedWords");
    public static final org.semanticwb.platform.SemanticClass swbxf_UniqueTextElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#UniqueTextElement");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#UniqueTextElement");

    public static class ClassMgr
    {
       /**
       * Returns a list of UniqueTextElement for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.UniqueTextElement
       */

        public static java.util.Iterator<org.semanticwb.model.UniqueTextElement> listUniqueTextElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UniqueTextElement>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.UniqueTextElement for all models
       * @return Iterator of org.semanticwb.model.UniqueTextElement
       */

        public static java.util.Iterator<org.semanticwb.model.UniqueTextElement> listUniqueTextElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UniqueTextElement>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.UniqueTextElement
       * @param id Identifier for org.semanticwb.model.UniqueTextElement
       * @param model Model of the org.semanticwb.model.UniqueTextElement
       * @return A org.semanticwb.model.UniqueTextElement
       */
        public static org.semanticwb.model.UniqueTextElement getUniqueTextElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.UniqueTextElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.UniqueTextElement
       * @param id Identifier for org.semanticwb.model.UniqueTextElement
       * @param model Model of the org.semanticwb.model.UniqueTextElement
       * @return A org.semanticwb.model.UniqueTextElement
       */
        public static org.semanticwb.model.UniqueTextElement createUniqueTextElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.UniqueTextElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.UniqueTextElement
       * @param id Identifier for org.semanticwb.model.UniqueTextElement
       * @param model Model of the org.semanticwb.model.UniqueTextElement
       */
        public static void removeUniqueTextElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.UniqueTextElement
       * @param id Identifier for org.semanticwb.model.UniqueTextElement
       * @param model Model of the org.semanticwb.model.UniqueTextElement
       * @return true if the org.semanticwb.model.UniqueTextElement exists, false otherwise
       */

        public static boolean hasUniqueTextElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getUniqueTextElement(id, model)!=null);
        }
    }

    public static UniqueTextElementBase.ClassMgr getUniqueTextElementClassMgr()
    {
        return new UniqueTextElementBase.ClassMgr();
    }

   /**
   * Constructs a UniqueTextElementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the UniqueTextElement
   */
    public UniqueTextElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Restrict4Id property
* @return boolean with the Restrict4Id
*/
    public boolean isRestrict4Id()
    {
        return getSemanticObject().getBooleanProperty(swbxf_restrict4Id);
    }

/**
* Sets the Restrict4Id property
* @param value long with the Restrict4Id
*/
    public void setRestrict4Id(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_restrict4Id, value);
    }

/**
* Gets the ReservedWords property
* @return String with the ReservedWords
*/
    public String getReservedWords()
    {
        return getSemanticObject().getProperty(swbxf_reservedWords);
    }

/**
* Sets the ReservedWords property
* @param value long with the ReservedWords
*/
    public void setReservedWords(String value)
    {
        getSemanticObject().setProperty(swbxf_reservedWords, value);
    }
}
