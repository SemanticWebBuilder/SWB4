package org.semanticwb.model.base;


public abstract class CodeEditorBase extends org.semanticwb.model.TextArea 
{
    public static final org.semanticwb.platform.SemanticProperty swbxf_codeEditorLanguage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#codeEditorLanguage");
    public static final org.semanticwb.platform.SemanticClass swbxf_CodeEditor=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#CodeEditor");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#CodeEditor");

    public static class ClassMgr
    {
       /**
       * Returns a list of CodeEditor for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.CodeEditor
       */

        public static java.util.Iterator<org.semanticwb.model.CodeEditor> listCodeEditors(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.CodeEditor>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.CodeEditor for all models
       * @return Iterator of org.semanticwb.model.CodeEditor
       */

        public static java.util.Iterator<org.semanticwb.model.CodeEditor> listCodeEditors()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.CodeEditor>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.CodeEditor
       * @param id Identifier for org.semanticwb.model.CodeEditor
       * @param model Model of the org.semanticwb.model.CodeEditor
       * @return A org.semanticwb.model.CodeEditor
       */
        public static org.semanticwb.model.CodeEditor getCodeEditor(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.CodeEditor)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.CodeEditor
       * @param id Identifier for org.semanticwb.model.CodeEditor
       * @param model Model of the org.semanticwb.model.CodeEditor
       * @return A org.semanticwb.model.CodeEditor
       */
        public static org.semanticwb.model.CodeEditor createCodeEditor(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.CodeEditor)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.CodeEditor
       * @param id Identifier for org.semanticwb.model.CodeEditor
       * @param model Model of the org.semanticwb.model.CodeEditor
       */
        public static void removeCodeEditor(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.CodeEditor
       * @param id Identifier for org.semanticwb.model.CodeEditor
       * @param model Model of the org.semanticwb.model.CodeEditor
       * @return true if the org.semanticwb.model.CodeEditor exists, false otherwise
       */

        public static boolean hasCodeEditor(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCodeEditor(id, model)!=null);
        }
    }

   /**
   * Constructs a CodeEditorBase with a SemanticObject
   * @param base The SemanticObject with the properties for the CodeEditor
   */
    public CodeEditorBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Language property
* @return String with the Language
*/
    public String getLanguage()
    {
        return getSemanticObject().getProperty(swbxf_codeEditorLanguage);
    }

/**
* Sets the Language property
* @param value long with the Language
*/
    public void setLanguage(String value)
    {
        getSemanticObject().setProperty(swbxf_codeEditorLanguage, value);
    }
}
