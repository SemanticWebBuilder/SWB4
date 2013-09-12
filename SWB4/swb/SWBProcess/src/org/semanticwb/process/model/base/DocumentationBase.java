package org.semanticwb.process.model.base;


   /**
   * Objeto que almacena una descripción textual de un elemento de procesos 
   */
public abstract class DocumentationBase extends org.semanticwb.process.model.BaseElement 
{
   /**
   * Mime-type for the text documentation
   */
    public static final org.semanticwb.platform.SemanticProperty swp_textFormat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#textFormat");
   /**
   * Almacena el texto de la documentación
   */
    public static final org.semanticwb.platform.SemanticProperty swp_text=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#text");
   /**
   * Objeto que almacena una descripción textual de un elemento de procesos
   */
    public static final org.semanticwb.platform.SemanticClass swp_Documentation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Documentation");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Documentation");

    public static class ClassMgr
    {
       /**
       * Returns a list of Documentation for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.Documentation
       */

        public static java.util.Iterator<org.semanticwb.process.model.Documentation> listDocumentations(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Documentation>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.Documentation for all models
       * @return Iterator of org.semanticwb.process.model.Documentation
       */

        public static java.util.Iterator<org.semanticwb.process.model.Documentation> listDocumentations()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Documentation>(it, true);
        }

        public static org.semanticwb.process.model.Documentation createDocumentation(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.Documentation.ClassMgr.createDocumentation(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.Documentation
       * @param id Identifier for org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.Documentation
       * @return A org.semanticwb.process.model.Documentation
       */
        public static org.semanticwb.process.model.Documentation getDocumentation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Documentation)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.Documentation
       * @param id Identifier for org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.Documentation
       * @return A org.semanticwb.process.model.Documentation
       */
        public static org.semanticwb.process.model.Documentation createDocumentation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Documentation)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.Documentation
       * @param id Identifier for org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.Documentation
       */
        public static void removeDocumentation(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.Documentation
       * @param id Identifier for org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.Documentation
       * @return true if the org.semanticwb.process.model.Documentation exists, false otherwise
       */

        public static boolean hasDocumentation(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDocumentation(id, model)!=null);
        }
    }

    public static DocumentationBase.ClassMgr getDocumentationClassMgr()
    {
        return new DocumentationBase.ClassMgr();
    }

   /**
   * Constructs a DocumentationBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Documentation
   */
    public DocumentationBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the TextFormat property
* @return String with the TextFormat
*/
    public String getTextFormat()
    {
        return getSemanticObject().getProperty(swp_textFormat);
    }

/**
* Sets the TextFormat property
* @param value long with the TextFormat
*/
    public void setTextFormat(String value)
    {
        getSemanticObject().setProperty(swp_textFormat, value);
    }

/**
* Gets the Text property
* @return String with the Text
*/
    public String getText()
    {
        return getSemanticObject().getProperty(swp_text);
    }

/**
* Sets the Text property
* @param value long with the Text
*/
    public void setText(String value)
    {
        getSemanticObject().setProperty(swp_text, value);
    }
}
