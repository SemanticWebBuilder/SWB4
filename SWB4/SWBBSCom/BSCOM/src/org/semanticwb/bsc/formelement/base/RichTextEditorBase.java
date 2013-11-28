package org.semanticwb.bsc.formelement.base;


   /**
   * Uso del editor de texto rico de Dojo para la presentación del FormElement 
   */
public abstract class RichTextEditorBase extends org.semanticwb.model.TextArea 
{
   /**
   * Uso del editor de texto rico de Dojo para la presentación del FormElement
   */
    public static final org.semanticwb.platform.SemanticClass bsc_RichTextEditor=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#RichTextEditor");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#RichTextEditor");

    public static class ClassMgr
    {
       /**
       * Returns a list of RichTextEditor for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.RichTextEditor
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.RichTextEditor> listRichTextEditors(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.RichTextEditor>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.RichTextEditor for all models
       * @return Iterator of org.semanticwb.bsc.formelement.RichTextEditor
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.RichTextEditor> listRichTextEditors()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.RichTextEditor>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.RichTextEditor
       * @param id Identifier for org.semanticwb.bsc.formelement.RichTextEditor
       * @param model Model of the org.semanticwb.bsc.formelement.RichTextEditor
       * @return A org.semanticwb.bsc.formelement.RichTextEditor
       */
        public static org.semanticwb.bsc.formelement.RichTextEditor getRichTextEditor(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.RichTextEditor)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.RichTextEditor
       * @param id Identifier for org.semanticwb.bsc.formelement.RichTextEditor
       * @param model Model of the org.semanticwb.bsc.formelement.RichTextEditor
       * @return A org.semanticwb.bsc.formelement.RichTextEditor
       */
        public static org.semanticwb.bsc.formelement.RichTextEditor createRichTextEditor(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.RichTextEditor)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.RichTextEditor
       * @param id Identifier for org.semanticwb.bsc.formelement.RichTextEditor
       * @param model Model of the org.semanticwb.bsc.formelement.RichTextEditor
       */
        public static void removeRichTextEditor(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.RichTextEditor
       * @param id Identifier for org.semanticwb.bsc.formelement.RichTextEditor
       * @param model Model of the org.semanticwb.bsc.formelement.RichTextEditor
       * @return true if the org.semanticwb.bsc.formelement.RichTextEditor exists, false otherwise
       */

        public static boolean hasRichTextEditor(String id, org.semanticwb.model.SWBModel model)
        {
            return (getRichTextEditor(id, model)!=null);
        }
    }

    public static RichTextEditorBase.ClassMgr getRichTextEditorClassMgr()
    {
        return new RichTextEditorBase.ClassMgr();
    }

   /**
   * Constructs a RichTextEditorBase with a SemanticObject
   * @param base The SemanticObject with the properties for the RichTextEditor
   */
    public RichTextEditorBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
